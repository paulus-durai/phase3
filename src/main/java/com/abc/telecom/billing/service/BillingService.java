package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.entity.TelecomService;
import com.abc.telecom.billing.entity.UsageRecord;
import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.repository.TelecomServiceRepository;
import com.abc.telecom.billing.repository.UsageRecordRepository;
import com.abc.telecom.billing.repository.AccountRepository;
import com.abc.telecom.billing.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.telecom.billing.config.BillingProperties;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BillingService {

    private final InvoiceRepository invoiceRepository;

    private final BillingProperties billingProperties;

    @Autowired
    private TelecomServiceRepository telecomServiceRepository;

    @Autowired
    private UsageRecordRepository usageRecordRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public BillingService(InvoiceRepository invoiceRepository, BillingProperties billingProperties) {
        this.invoiceRepository = invoiceRepository;
        this.billingProperties = billingProperties;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    /**
     * Generate an invoice for a customer by aggregating usage between two dates.
     * This is a simple example with fixed per-service-type rates.
     */
    public Invoice generateInvoiceForCustomer(Long customerId, java.time.LocalDate from, java.time.LocalDate to) {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        // Ensure customer exists
        com.abc.telecom.billing.entity.Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new ApiException("Customer not found: " + customerId, HttpStatus.NOT_FOUND);
        }

        // Load services once and usages in a single query to avoid N+1
        List<TelecomService> services = telecomServiceRepository.findByCustomerId(customerId);
        java.util.Map<Long, TelecomService> svcById = new java.util.HashMap<>();
        for (TelecomService s : services) svcById.put(s.getId(), s);

        java.util.List<UsageRecord> usages = usageRecordRepository.findByCustomerIdAndUsageDateBetween(customerId, from, to);
        for (UsageRecord u : usages) {
            TelecomService svc = svcById.get(u.getServiceId());
            if (svc == null) continue; // skip unknown service
            BigDecimal rate = getRateForServiceType(svc.getServiceType());
            BigDecimal amount = u.getUsageAmount().multiply(rate);
            total = total.add(amount);
        }

        // Find an account for this customer (try by customer entity -> customerName)
        PostpaidAccount account = null;
        if (customer != null) {
            account = accountRepository.findByCustomerName(customer.getName());
        }

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setAccountId(account != null ? account.getId() : null);
        invoice.setAmount(total);
        invoice.setDueDate(to.plusDays(30));
        invoice.setStatus("PENDING");

        return invoiceRepository.save(invoice);
    }

    private java.math.BigDecimal getRateForServiceType(String serviceType) {
        Map<String, BigDecimal> rates = billingProperties != null ? billingProperties.getRates() : null;
        if (serviceType == null) return rates != null && rates.containsKey("DEFAULT") ? rates.get("DEFAULT") : new BigDecimal("0.10");
        String key = serviceType.toUpperCase();
        if (rates != null) {
            // try exact key
            if (rates.containsKey(key)) return rates.get(key);
            // try common normalized keys
            if (key.startsWith("POSTPAID_") && rates.containsKey(key.substring(9))) return rates.get(key.substring(9));
            if (rates.containsKey("DEFAULT")) return rates.get("DEFAULT");
        }
        // Fallback hardcoded
        switch (key) {
            case "POSTPAID_MOBILE":
            case "MOBILE":
            case "MOBILE_SERVICE":
                return new BigDecimal("0.10");
            case "POSTPAID_BROADBAND":
            case "INTERNET":
            case "BROADBAND":
                return new BigDecimal("0.05");
            case "TV":
            case "CABLE_TV":
                return new BigDecimal("0.20");
            default:
                return new BigDecimal("0.10");
        }
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
        invoice.setAmount(invoiceDetails.getAmount());
        invoice.setDueDate(invoiceDetails.getDueDate());
        invoice.setStatus(invoiceDetails.getStatus());
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}