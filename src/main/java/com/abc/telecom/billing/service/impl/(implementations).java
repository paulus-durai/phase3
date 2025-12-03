package com.abc.telecom.billing.service.impl;

import com.abc.telecom.billing.dto.billing.InvoiceDto;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;
import com.abc.telecom.billing.mapper.InvoiceMapper;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public BillingServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(savedInvoice);
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
        return invoiceMapper.toDto(invoice);
    }

    @Override
    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoiceMapper.toDtoList(invoices);
    }

    @Override
    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ApiException("Invoice not found", HttpStatus.NOT_FOUND));
        invoiceMapper.updateEntityFromDto(invoiceDto, existingInvoice);
        Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
        return invoiceMapper.toDto(updatedInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ApiException("Invoice not found", HttpStatus.NOT_FOUND);
        }
        invoiceRepository.deleteById(id);
    }
}