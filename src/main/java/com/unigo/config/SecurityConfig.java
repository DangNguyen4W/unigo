package com.unigo.config;

import com.unigo.security.permissions.organism.RolesPermission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

  private final RolesPermission rolesPermission;

  SecurityConfig(RolesPermission rolesPermission) {
    this.rolesPermission = rolesPermission;
  }

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(
                (request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .accessDeniedHandler((request, response, ex) -> response.sendError(HttpServletResponse.SC_FORBIDDEN)))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST,
                "/auth/register",
                "/auth/login")
            .permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/roles/assign-permissions").hasRole(rolesPermission.assignPermissions())
            .anyRequest().authenticated())
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
