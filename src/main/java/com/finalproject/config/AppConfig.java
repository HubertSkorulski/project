package com.finalproject.config;

import com.finalproject.auth.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        User user = new User("test1@gmail.com", getBcryptPasswordEncoder().encode("admin123"), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        User admin = new User("test2@gmail.com", getBcryptPasswordEncoder().encode("admin123"), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return new InMemoryUserDetailsManager(user, admin);
    }
    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userDbService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email not found: " + username));
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.authorizeRequests()
                .antMatchers("/v1/user/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder  getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authorizationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
