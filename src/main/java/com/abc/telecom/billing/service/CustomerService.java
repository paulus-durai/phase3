package com.abc.telecom.billing.service;

import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abc.telecom.billing.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findAllWithUser();
        } catch (java.lang.AbstractMethodError | java.lang.UnsupportedOperationException ex) {
            return customerRepository.findAll();
        }
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException("Customer not found with id " + id, HttpStatus.NOT_FOUND));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ApiException("Customer not found with id " + id, HttpStatus.NOT_FOUND));
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setAddress(customerDetails.getAddress());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}