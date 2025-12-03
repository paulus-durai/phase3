package com.abc.telecom.billing.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDto {
    private Long id;

    @NotNull
    private Long invoiceId;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String method;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
