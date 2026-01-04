package com.delivery.logistics.auth.controller;

import com.delivery.logistics.auth.dto.LoginRequest;
import com.delivery.logistics.auth.dto.LoginResponse;
import com.delivery.logistics.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/customers/login")
    public LoginResponse login(@RequestBody  LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
