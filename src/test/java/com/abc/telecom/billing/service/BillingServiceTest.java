package com.abc.telecom.billing.service;

import com.abc.telecom.billing.config.BillingProperties;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.entity.TelecomService;
import com.abc.telecom.billing.entity.UsageRecord;
import com.abc.telecom.billing.repository.AccountRepository;
import com.abc.telecom.billing.repository.CustomerRepository;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.repository.TelecomServiceRepository;
import com.abc.telecom.billing.repository.UsageRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private TelecomServiceRepository telecomServiceRepository;
    @Mock
    private UsageRecordRepository usageRecordRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BillingProperties billingProperties;

    @InjectMocks
    private BillingService billingService;

    @Captor
    ArgumentCaptor<Invoice> invoiceCaptor;

    @Test
    void generateInvoiceForCustomer_calculatesTotalAndLinksAccount() {
        Long customerId = 42L;
        // customer
        com.abc.telecom.billing.entity.Customer customer = new com.abc.telecom.billing.entity.Customer();
        customer.setId(customerId);
        customer.setName("Acme Co");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // services
        TelecomService svc = new TelecomService();
        svc.setId(1L);
        svc.setCustomerId(customerId);
        svc.setServiceType("MOBILE");
        when(telecomServiceRepository.findByCustomerId(customerId)).thenReturn(List.of(svc));

        // usage: 10 units
        UsageRecord usage = new UsageRecord();
        usage.setId(100L);
        usage.setServiceId(1L);
        usage.setUsageDate(LocalDate.now());
        usage.setUsageAmount(new BigDecimal("10"));
        when(usageRecordRepository.findByCustomerIdAndUsageDateBetween(customerId, LocalDate.now().minusDays(30), LocalDate.now()))
            .thenReturn(List.of(usage));

        // rates: MOBILE -> 0.10
        HashMap<String, BigDecimal> rates = new HashMap<>();
        rates.put("MOBILE", new BigDecimal("0.10"));
        when(billingProperties.getRates()).thenReturn(rates);

        // account
        PostpaidAccount account = new PostpaidAccount();
        account.setId(999L);
        account.setCustomerName("Acme Co");
        when(accountRepository.findByCustomerName("Acme Co")).thenReturn(account);

        // invoice save returns same invoice with id
        when(invoiceRepository.save(org.mockito.ArgumentMatchers.any(Invoice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LocalDate from = LocalDate.now().minusDays(30);
        LocalDate to = LocalDate.now();
        Invoice invoice = billingService.generateInvoiceForCustomer(customerId, from, to);

        assertThat(invoice).isNotNull();
        assertThat(invoice.getAmount()).isEqualByComparingTo(new BigDecimal("1.0")); // 10 * 0.10
        assertThat(invoice.getAccountId()).isEqualTo(999L);
    }
}
