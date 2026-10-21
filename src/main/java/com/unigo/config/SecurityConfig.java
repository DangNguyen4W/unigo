package com.unigo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                HttpMethod.POST,
                "/auth/register")
            .permitAll()
            .requestMatchers(
                HttpMethod.POST,
                "/auth/login")
            .permitAll()
            .requestMatchers(
                HttpMethod.GET,
                "/users")
            .permitAll()
            .anyRequest().permitAll());
    // .anyRequest().authenticated());

    return http.build();
  }
}
