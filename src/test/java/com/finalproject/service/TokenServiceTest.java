package com.finalproject.service;

import com.finalproject.auth.JwtFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;


@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtFilter jwtFilter;

    @Test
    void generateTokenTest() {
        //Given
        User user = new User("test@test.pl","123", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        //When
        String token = tokenService.generateToken(user);
        String tokenWithPrefix = "Bearer " + token;
        //Then
        UsernamePasswordAuthenticationToken authenticationToken = jwtFilter.getUsernamePasswordAuthenticationToken(tokenWithPrefix);
        Assertions.assertEquals("test@test.pl",authenticationToken.getPrincipal());
        Assertions.assertTrue(authenticationToken.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}