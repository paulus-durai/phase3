package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);

    @EntityGraph(attributePaths = {"user"})
    Optional<Customer> findWithUserById(Long id);

    @EntityGraph(attributePaths = {"user"})
    java.util.List<Customer> findAllWithUser();
}