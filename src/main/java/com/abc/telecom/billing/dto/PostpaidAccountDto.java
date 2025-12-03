package com.abc.telecom.billing.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class PostpaidAccountDto {
    private Long id;

    @NotNull
    private Long customerId;

    @NotBlank
    private String accountNumber;

    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
