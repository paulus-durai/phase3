package com.abc.telecom.billing.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.abc.telecom.billing.dto.InvoiceDto;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.service.BillingService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<Invoice> invoices = billingService.getAllInvoices();
        List<InvoiceDto> dtos = invoices.stream().map(i -> modelMapper.map(i, InvoiceDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = billingService.getInvoiceById(id);
        return ResponseEntity.ok(modelMapper.map(invoice, InvoiceDto.class));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceDto> createInvoice(@Valid @RequestBody InvoiceDto dto) {
        Invoice entity = modelMapper.map(dto, Invoice.class);
        Invoice created = billingService.createInvoice(entity);
        return ResponseEntity.status(201).body(modelMapper.map(created, InvoiceDto.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Long id, @Valid @RequestBody InvoiceDto dto) {
        Invoice entity = modelMapper.map(dto, Invoice.class);
        Invoice updated = billingService.updateInvoice(id, entity);
        return ResponseEntity.ok(modelMapper.map(updated, InvoiceDto.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isInvoiceOwner(#id)")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        billingService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#customerId)")
    public ResponseEntity<InvoiceDto> generateInvoiceForCustomer(@PathVariable Long customerId,
                                                                 @RequestParam("from") java.time.LocalDate from,
                                                                 @RequestParam("to") java.time.LocalDate to) {
        Invoice generated = billingService.generateInvoiceForCustomer(customerId, from, to);
        return ResponseEntity.status(201).body(modelMapper.map(generated, InvoiceDto.class));
    }
}