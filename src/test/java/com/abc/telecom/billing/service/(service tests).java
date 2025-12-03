package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.repository.CustomerRepository;
import com.abc.telecom.billing.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer createdCustomer = customerService.createCustomer(customer);
        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
    }

    @Test
    public void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerService.getCustomerById(1L);
        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());
    }

    @Test
    public void testUpdateCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customer.setName("Jane Doe");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer updatedCustomer = customerService.updateCustomer(1L, customer);
        assertNotNull(updatedCustomer);
        assertEquals("Jane Doe", updatedCustomer.getName());
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}