package com.jaleStore.demo.dto;

import lombok.Data;


    // DTOs
    @Data
    public class LoginRequest {
        private String username;
        private String email;
        private String password;
    }

