package com.delivery.logistics.customer.service;

import com.delivery.logistics.common.exception.CustomerNotFoundException;
import com.delivery.logistics.common.exception.EmailAlreadyExistsException;
import com.delivery.logistics.common.security.PasswordHasher;
import com.delivery.logistics.customer.dto.CreateCustomerRequest;
import com.delivery.logistics.customer.dto.UpdateCustomerRequest;
import com.delivery.logistics.customer.model.Customer;
import com.delivery.logistics.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;

    public CustomerService(CustomerRepository customerRepository, PasswordHasher passwordHasher)
    {
        this.passwordHasher = passwordHasher;
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerRequest request)
    {
        boolean isEmailExist = customerRepository.existsByEmail(request.getEmail());
        if(isEmailExist)
            throw new EmailAlreadyExistsException("Email already exists");

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        String hashedPassword = passwordHasher.hashPassword(request.getPassword());
        customer.setPassword(hashedPassword);
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());

        return customerRepository.save(customer);
    }

    public  Customer updateCustomer(UUID id, UpdateCustomerRequest request)
    {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if(request.getFirstName() != null)
        {
            customer.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null)
        {
            customer.setLastName(request.getLastName());
        }
        if(request.getDateOfBirth() != null)
        {
            customer.setDateOfBirth(request.getDateOfBirth());
        }
        if(request.getPhone() != null)
        {
            customer.setPhone(request.getPhone());
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID id)
    {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);

    }

    public Optional<Customer> findCustomerByEmail(String email)
    {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> findAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

}
