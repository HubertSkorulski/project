package com.finalproject.auth;

import lombok.Data;

@Data
public class AuthResponse {

    private final String email;
    private final String accessToken;
}
