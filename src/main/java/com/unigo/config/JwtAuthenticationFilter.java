package com.unigo.config;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unigo.entity.Role;
import com.unigo.entity.User;
import com.unigo.repository.UserRepository;
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

  public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
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
      userRepository.findByIdWithRoleAndPermissions(userId).filter(User::getIsActive).ifPresent(this::authenticate);
    } catch (JwtException | IllegalArgumentException exception) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);

  }

  private void authenticate(User user) {
    Role role = user.getRole();
    Collection<? extends GrantedAuthority> permissions = role.getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getCode())).toList();

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user,
        null,
        permissions);

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }
}
