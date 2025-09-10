package com.userService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for JS POSTs
            .authorizeHttpRequests(auth -> auth
                // Allow user pages
                .requestMatchers(
                    "/user/login", 
                    "/user/register",
                    "/user/dashboard", 
                    "/css/**", 
                    "/js/**",
                    "/h2-console/**"
                ).permitAll()
                .requestMatchers(
                        "/api/users/register", 
                        "/api/users/login",
                        "/api/playlists/**" ,    // allow playlists APIs
                        "/api/songs/**"
                		)
                .permitAll()
                // Allow API endpoints for login/register
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable()); // H2 console

        return http.build();
    }
}
