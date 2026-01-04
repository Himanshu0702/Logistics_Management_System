package com.delivery.logistics.common.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }

}
