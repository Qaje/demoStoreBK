package com.jaleStore.demo.dto.Response;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private java.util.List<String> roles;

    public AuthResponse(String accessToken, String refreshToken, String username, java.util.List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.roles = roles;
    }
}

