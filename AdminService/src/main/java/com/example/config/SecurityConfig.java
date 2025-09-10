package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/h2-console/**").permitAll()  // H2 console
	            .requestMatchers("/admin/login", "/css/**", "/js/**").permitAll() // allow login page and static resources
	            .requestMatchers("/api/**").permitAll() // allow API requests (like /api/songs) for UserService
	            .anyRequest().authenticated() // everything else requires authentication
	        )
	        .formLogin(form -> form
	            .loginPage("/admin/login")    // custom login page
	            .loginProcessingUrl("/admin/login") // URL to submit username/password
	            .defaultSuccessUrl("/admin/dashboard", true) // after login
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/admin/logout")
	            .logoutSuccessUrl("/admin/login?logout")
	            .permitAll()
	        )
	        .csrf(csrf -> csrf.disable())  // disable CSRF protection if needed
	        .headers(headers -> headers.frameOptions().disable()); // allow frames for H2 console

	    return http.build();
	}


    }
    
