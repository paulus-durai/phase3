package com.abc.telecom.billing.security;

import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.entity.Invoice;
import com.abc.telecom.billing.entity.PostpaidAccount;
import com.abc.telecom.billing.entity.User;
import com.abc.telecom.billing.repository.AccountRepository;
import com.abc.telecom.billing.repository.CustomerRepository;
import com.abc.telecom.billing.repository.InvoiceRepository;
import com.abc.telecom.billing.repository.UserRepository;
import com.abc.telecom.billing.repository.TelecomServiceRepository;
import com.abc.telecom.billing.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Component("securityService")
public class SecurityService {

    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final InvoiceRepository invoiceRepository;
    private final TelecomServiceRepository telecomServiceRepository;

    public SecurityService(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           InvoiceRepository invoiceRepository,
                           TelecomServiceRepository telecomServiceRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.invoiceRepository = invoiceRepository;
        this.telecomServiceRepository = telecomServiceRepository;
    }

    @Transactional(readOnly = true)
    public boolean isCustomerOwner(Long customerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;
        // Admins may access all customers
        if (user.getRoles() != null && user.getRoles().stream().anyMatch(r -> r.getName() == Role.RoleName.ADMIN)) {
            return true;
        }
        Customer customer = customerRepository.findWithUserById(customerId).orElse(null);
        if (customer == null) return false;
        // Primary check: direct FK link between Customer and User
        if (customer.getUser() != null && customer.getUser().getUsername() != null) {
            return username.equalsIgnoreCase(customer.getUser().getUsername());
        }
        // Fallback: user email matches customer email
        if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(customer.getEmail())) {
            return true;
        }
        // Final fallback: username matches customer name
        return username.equalsIgnoreCase(customer.getName());
    }

    public boolean isAccountOwner(Long accountId) {
        PostpaidAccount account = accountRepository.findById(accountId).orElse(null);
        if (account == null) return false;
        // account has customerName field - try to find a customer by that name and reuse isCustomerOwner
        Customer customer = customerRepository.findByName(account.getCustomerName());
        if (customer != null) {
            return isCustomerOwner(customer.getId());
        }
        // As fallback, compare username to account.customerName
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        // allow admin as well
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user != null && user.getRoles() != null && user.getRoles().stream().anyMatch(r -> r.getName() == Role.RoleName.ADMIN)) {
            return true;
        }
        return auth.getName().equalsIgnoreCase(account.getCustomerName());
    }

    public boolean isInvoiceOwner(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null) return false;
        return isAccountOwner(invoice.getAccountId());
    }

    public boolean isServiceOwner(Long serviceId) {
        com.abc.telecom.billing.entity.TelecomService svc = telecomServiceRepository.findById(serviceId).orElse(null);
        if (svc == null) return false;
        return isCustomerOwner(svc.getCustomerId());
    }
}
