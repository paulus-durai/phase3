package com.abc.telecom.billing.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "usage_records")
public class UsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "usage_date", nullable = false)
    private LocalDate usageDate;

    @Column(name = "usage_amount", nullable = false)
    private BigDecimal usageAmount;

    @Column(name = "unit", nullable = false)
    private String unit;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    public LocalDate getUsageDate() { return usageDate; }
    public void setUsageDate(LocalDate usageDate) { this.usageDate = usageDate; }
    public BigDecimal getUsageAmount() { return usageAmount; }
    public void setUsageAmount(BigDecimal usageAmount) { this.usageAmount = usageAmount; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
