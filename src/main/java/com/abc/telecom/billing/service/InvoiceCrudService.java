package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class InvoiceCrudService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceCrudService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
        invoice.setAmount(invoiceDetails.getAmount());
        invoice.setDueDate(invoiceDetails.getDueDate());
        invoice.setStatus(invoiceDetails.getStatus());
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
