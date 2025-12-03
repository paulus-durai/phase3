package com.abc.telecom.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abc.telecom.billing.repository.UserRepository;
import com.abc.telecom.billing.repository.RoleRepository;
import com.abc.telecom.billing.repository.CustomerRepository;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.entity.Role;
import com.abc.telecom.billing.entity.Customer;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AbcTelecomBillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcTelecomBillingApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataInitializer(UserRepository userRepository,
                                             RoleRepository roleRepository,
                                             CustomerRepository customerRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            // Ensure roles exist
            Role adminRole = roleRepository.findByName(Role.RoleName.ADMIN);
            if (adminRole == null) {
                adminRole = roleRepository.save(new Role(Role.RoleName.ADMIN));
            }
            Role customerRole = roleRepository.findByName(Role.RoleName.CUSTOMER);
            if (customerRole == null) {
                customerRole = roleRepository.save(new Role(Role.RoleName.CUSTOMER));
            }

            // Create admin user if missing
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);
                userRepository.save(admin);
            }

            // Create demo customer user if missing
            if (userRepository.findByUsername("customer1").isEmpty()) {
                User customerUser = new User();
                customerUser.setUsername("customer1");
                customerUser.setEmail("john.doe@example.com");
                customerUser.setPassword(passwordEncoder.encode("customer123"));
                Set<Role> roles = new HashSet<>();
                roles.add(customerRole);
                customerUser.setRoles(roles);
                userRepository.save(customerUser);

                // Create a corresponding customer record and link to the user
                Customer customer = new Customer();
                customer.setName("John Doe");
                customer.setEmail("john.doe@example.com");
                customer.setPhoneNumber("1234567890");
                customer.setAddress("123 Elm Street");
                customer.setUser(customerUser);
                customerRepository.save(customer);
            }
        };
    }
}