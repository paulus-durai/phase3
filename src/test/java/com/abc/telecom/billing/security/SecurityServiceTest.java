package com.abc.telecom.billing.security;

import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.entity.Role;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.repository.AccountRepository;
import com.abc.telecom.billing.repository.CustomerRepository;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.repository.TelecomServiceRepository;
import com.abc.telecom.billing.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private TelecomServiceRepository telecomServiceRepository;

    @InjectMocks
    private SecurityService securityService;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void isCustomerOwner_returnsTrueForAdmin() {
        when(authentication.getName()).thenReturn("adminUser");
        User admin = new User();
        admin.setUsername("adminUser");
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(Role.RoleName.ADMIN));
        admin.setRoles(roles);

        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(admin));

        boolean ok = securityService.isCustomerOwner(123L);
        assertThat(ok).isTrue();
    }

    @Test
    void isCustomerOwner_returnsTrueForLinkedUser() {
        when(authentication.getName()).thenReturn("ownerUser");
        User user = new User();
        user.setUsername("ownerUser");
        when(userRepository.findByUsername("ownerUser")).thenReturn(Optional.of(user));

        Customer customer = new Customer();
        customer.setId(10L);
        User linked = new User();
        linked.setUsername("ownerUser");
        customer.setUser(linked);

        when(customerRepository.findWithUserById(10L)).thenReturn(Optional.of(customer));

        boolean ok = securityService.isCustomerOwner(10L);
        assertThat(ok).isTrue();
    }

    @Test
    void isCustomerOwner_returnsFalseForOtherUser() {
        when(authentication.getName()).thenReturn("otherUser");
        User user = new User();
        user.setUsername("otherUser");
        when(userRepository.findByUsername("otherUser")).thenReturn(Optional.of(user));

        Customer customer = new Customer();
        customer.setId(11L);
        User linked = new User();
        linked.setUsername("someoneElse");
        customer.setUser(linked);

        when(customerRepository.findWithUserById(11L)).thenReturn(Optional.of(customer));

        boolean ok = securityService.isCustomerOwner(11L);
        assertThat(ok).isFalse();
    }
}
