package com.delivery.logistics.auth.service;

import com.delivery.logistics.auth.dto.LoginRequest;
import com.delivery.logistics.auth.dto.LoginResponse;

import com.delivery.logistics.common.enums.Role;
import com.delivery.logistics.common.exception.UnauthorizedException;
import com.delivery.logistics.common.security.JwtUtil;
import com.delivery.logistics.common.security.PasswordHasher;
import com.delivery.logistics.customer.model.Customer;
import com.delivery.logistics.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final  CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;
    private final JwtUtil  jwtUtil;

    public AuthService(CustomerRepository customerRepository, PasswordHasher passwordHasher, JwtUtil jwtUtil) {
        this.passwordHasher = passwordHasher;
        this.jwtUtil = jwtUtil;
        this.customerRepository = customerRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
            Customer customer = customerRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

            if(passwordHasher.checkPassword(loginRequest.getPassword(), customer.getPassword())) {
                String token = jwtUtil.generateJwtToken(customer.getId(), loginRequest.getEmail(), Role.CUSTOMER);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(token);
                loginResponse.setCustomerId(customer.getId());
                return loginResponse;
            }
            throw new UnauthorizedException("Invalid credentials");

    }
}
