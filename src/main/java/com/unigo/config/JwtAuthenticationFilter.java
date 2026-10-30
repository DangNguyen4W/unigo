package com.unigo.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unigo.entity.User;
import com.unigo.repository.UserRepository;
import com.unigo.security.permissions.Permissions;
import com.unigo.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final Permissions permissions;

  public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository, Permissions permissions) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.permissions = permissions;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorization = request.getHeader("Authorization");
    if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }
    final String token = authorization.substring(BEARER_PREFIX.length());

    try {

      Claims claims = jwtService.parseAccessToken(token);
      UUID userId = UUID.fromString(claims.getSubject());
      userRepository.findById(userId).filter(User::getIsActive).ifPresent(this::authenticate);
    } catch (JwtException | IllegalArgumentException exception) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);

  }

  private void authenticate(User user) {
    System.out.println("Alls Permissions: ");
    System.out.println(permissions.all());
    String authorityName = "ROLE_" + user.getRole().getName();
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user,
        null,
        List.of(new SimpleGrantedAuthority(authorityName)));

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);

  }
}
