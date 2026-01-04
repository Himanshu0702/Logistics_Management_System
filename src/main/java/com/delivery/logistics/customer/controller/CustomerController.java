package com.delivery.logistics.customer.controller;

import com.delivery.logistics.customer.dto.CreateCustomerRequest;
import com.delivery.logistics.customer.dto.CustomerResponse;
import com.delivery.logistics.customer.dto.UpdateCustomerRequest;
import com.delivery.logistics.customer.model.Customer;
import com.delivery.logistics.customer.service.CustomerService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.delivery.logistics.customer.mapper.CustomerMapper.toResponse;

@RestController
@RequestMapping("/customers")
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Method Implementation below
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
         Customer customer = customerService.createCustomer(createCustomerRequest);
        return toResponse(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponse fetchCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.getCustomerById(id);
        return toResponse(customer);
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable UUID id,@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerService.updateCustomer(id, updateCustomerRequest);
        return toResponse(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
