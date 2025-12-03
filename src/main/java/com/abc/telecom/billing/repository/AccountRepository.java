package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.PostpaidAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<PostpaidAccount, Long> {
    PostpaidAccount findByCustomerName(String customerName);
}