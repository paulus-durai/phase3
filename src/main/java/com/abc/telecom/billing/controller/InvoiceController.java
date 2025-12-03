package com.abc.telecom.billing.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abc.telecom.billing.dto.InvoiceDto;
import com.abc.telecom.billing.dto.PaymentDto;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.entity.Payment;
import com.abc.telecom.billing.service.BillingService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private BillingService invoiceService;
    @Autowired
    private com.abc.telecom.billing.service.PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<InvoiceDto> dtos = invoices.stream()
                .map(i -> modelMapper.map(i, InvoiceDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(modelMapper.map(invoice, InvoiceDto.class));
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto dto) {
        Invoice entity = modelMapper.map(dto, Invoice.class);
        Invoice created = invoiceService.createInvoice(entity);
        return ResponseEntity.status(201).body(modelMapper.map(created, InvoiceDto.class));
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto dto) {
        Invoice entity = modelMapper.map(dto, Invoice.class);
        Invoice updated = invoiceService.updateInvoice(id, entity);
        return ResponseEntity.ok(modelMapper.map(updated, InvoiceDto.class));
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/payments")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<PaymentDto> addPayment(@PathVariable Long id, @Valid @RequestBody PaymentDto dto) {
        Payment entity = modelMapper.map(dto, Payment.class);
        Payment created = paymentService.createPayment(id, entity);
        return ResponseEntity.status(201).body(modelMapper.map(created, PaymentDto.class));
    }

    @GetMapping("/{id}/payments")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<List<PaymentDto>> getPayments(@PathVariable Long id) {
        List<Payment> payments = paymentService.getPaymentsForInvoice(id);
        List<PaymentDto> dtos = payments.stream().map(p -> modelMapper.map(p, PaymentDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}