package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {
    List<UsageRecord> findByServiceIdAndUsageDateBetween(Long serviceId, LocalDate start, LocalDate end);

    // Fetch all usage records for a customer's services in a single query to avoid N+1
    @org.springframework.data.jpa.repository.Query("SELECT u FROM UsageRecord u WHERE u.serviceId IN (SELECT s.id FROM TelecomService s WHERE s.customerId = :customerId) AND u.usageDate BETWEEN :start AND :end")
    List<UsageRecord> findByCustomerIdAndUsageDateBetween(Long customerId, LocalDate start, LocalDate end);
}
