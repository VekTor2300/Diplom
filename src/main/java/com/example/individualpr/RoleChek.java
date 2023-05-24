package com.example.individualpr;

import org.springframework.security.core.Authentication;

public class RoleChek {

    public boolean adminCheck(Authentication auth) {
        return auth.getAuthorities().toString().contains("ADMIN");
    }

    public boolean userCheck(Authentication auth) {
        return auth.getAuthorities().toString().contains("USER");
    }

    public boolean employeeCheck(Authentication auth) {
        return auth.getAuthorities().toString().contains("EMPLOYEE");
    }
}