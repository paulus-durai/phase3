package com.abc.telecom.billing.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abc.telecom.billing.dto.CustomerDto;
import com.abc.telecom.billing.dto.TelecomServiceDto;
import com.abc.telecom.billing.entity.Customer;
import com.abc.telecom.billing.entity.TelecomService;
import com.abc.telecom.billing.service.CustomerService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private com.abc.telecom.billing.service.TelecomServiceService telecomServiceService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDto> dtos = customers.stream()
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#id)")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerDto.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Customer entity = modelMapper.map(customerDto, Customer.class);
        Customer createdCustomer = customerService.createCustomer(entity);
        return new ResponseEntity<>(modelMapper.map(createdCustomer, CustomerDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#id)")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) {
        Customer entity = modelMapper.map(customerDto, Customer.class);
        Customer updatedCustomer = customerService.updateCustomer(id, entity);
        return new ResponseEntity<>(modelMapper.map(updatedCustomer, CustomerDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#id)")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/services")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#id)")
    public ResponseEntity<List<TelecomServiceDto>> getServicesForCustomer(@PathVariable Long id) {
        List<TelecomService> services = telecomServiceService.getServicesByCustomerId(id);
        List<TelecomServiceDto> dtos = services.stream()
                .map(s -> modelMapper.map(s, TelecomServiceDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{id}/services")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @securityService.isCustomerOwner(#id)")
    public ResponseEntity<TelecomServiceDto> createServiceForCustomer(@PathVariable Long id, @Valid @RequestBody TelecomServiceDto serviceDto) {
        TelecomService serviceEntity = modelMapper.map(serviceDto, TelecomService.class);
        TelecomService created = telecomServiceService.createServiceForCustomer(id, serviceEntity);
        return ResponseEntity.status(201).body(modelMapper.map(created, TelecomServiceDto.class));
    }
}