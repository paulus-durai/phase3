package com.abc.telecom.billing.mapper;

import com.abc.telecom.billing.dto.billing.InvoiceDto;
import com.abc.telecom.billing.entity.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    @Autowired
    private ModelMapper modelMapper;

    public InvoiceDto toDto(Invoice invoice) {
        return modelMapper.map(invoice, InvoiceDto.class);
    }

    public Invoice toEntity(InvoiceDto invoiceDto) {
        return modelMapper.map(invoiceDto, Invoice.class);
    }
}