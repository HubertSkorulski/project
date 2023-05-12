package com.finalproject.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TokenService {
    @Value("${secret.key}")
    private String KEY;

    public String generateToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority) .collect(Collectors.toList()))
                .sign(algorithm);
    }
}
