package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.TelecomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelecomServiceRepository extends JpaRepository<TelecomService, Long> {
    List<TelecomService> findByCustomerId(Long customerId);
}
