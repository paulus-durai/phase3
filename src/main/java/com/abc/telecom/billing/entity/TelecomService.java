package com.abc.telecom.billing.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "services")
public class TelecomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "status", nullable = false)
    private String status;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
