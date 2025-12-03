package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Additional query methods can be defined here if needed
}