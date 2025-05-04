package com.kiago.api.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthRequest {
    private String email;
    private String password;
    // Getters y setters
    @Autowired
    PasswordEncoder passwordEncoder;


    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
