package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.entity.Payment;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public Payment createPayment(Long invoiceId, Payment payment) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
        payment.setInvoiceId(invoiceId);
        Payment saved = paymentRepository.save(payment);

        // compute total paid
        List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
        BigDecimal totalPaid = payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPaid.compareTo(invoice.getAmount()) >= 0) {
            invoice.setStatus("PAID");
        } else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus("PARTIALLY_PAID");
        }
        invoiceRepository.save(invoice);
        return saved;
    }

    public List<Payment> getPaymentsForInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }
}
