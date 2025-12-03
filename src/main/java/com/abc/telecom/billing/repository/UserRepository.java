package com.abc.telecom.billing.repository;

import com.abc.telecom.billing.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"roles"})
    java.util.List<User> findAllWithRoles();
}