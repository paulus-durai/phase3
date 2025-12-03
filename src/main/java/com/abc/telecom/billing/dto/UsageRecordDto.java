package com.abc.telecom.billing.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class UsageRecordDto {
    private Long id;

    @NotNull
    private Long serviceId;

    @NotNull
    private LocalDateTime usageTimestamp;

    @NotNull
    @Positive
    private BigDecimal units;

    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public LocalDateTime getUsageTimestamp() { return usageTimestamp; }
    public void setUsageTimestamp(LocalDateTime usageTimestamp) { this.usageTimestamp = usageTimestamp; }

    public BigDecimal getUnits() { return units; }
    public void setUnits(BigDecimal units) { this.units = units; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
