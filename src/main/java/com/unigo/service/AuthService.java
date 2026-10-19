package com.unigo.service;

import java.security.SecureRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unigo.dto.request.RegisterRequest;
import com.unigo.dto.response.AuthResponse;
import com.unigo.entity.Role;
import com.unigo.entity.User;
import com.unigo.mapper.UserMapper;
import com.unigo.repository.RoleRepository;
import com.unigo.repository.UserRepository;

@Service
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper,
      RoleRepository roleRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.roleRepository = roleRepository;
    this.jwtService = jwtService;
  }

  public String generateUsername() {
    String USERNAME_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    String username = "user_";
    SecureRandom random = new SecureRandom();

    for (int i = 0; i < 9; i++) {
      username += USERNAME_CHARS.charAt(random.nextInt(USERNAME_CHARS.length()));
    }
    return username;
  }

  @Transactional
  public AuthResponse register(RegisterRequest user) {
    System.out.println("Register run");
    if (userRepository.existsByPhoneNumber(user.phoneNumber())) {
      throw new RuntimeException("This phone number already exist");
    }
    if (userRepository.existsByEmail(user.email())) {
      throw new RuntimeException("This email already exist");
    }
    String username = user.username();
    if (userRepository.existsByUsername(username)) {
      throw new RuntimeException("This username already exist");
    }

    if (username == null || username.isBlank()) {
      do {
        username = generateUsername();
        System.out.println(username);
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("Thread was interrupted", e);
        }

      } while (userRepository.existsByUsername(username));
    }

    String hashedPassword = passwordEncoder.encode(user.password());
    User newUser = userMapper.toEntity(user);
    newUser.setPassword(hashedPassword);
    newUser.setUsername(username);

    Role userRole = roleRepository.findByName("USER")
        .orElseThrow(() -> new IllegalStateException("Default role USER is not exist"));
    newUser.setRole(userRole);

    User savedUser = userRepository.save(newUser);

    return new AuthResponse(
        userMapper.toResponse(savedUser),
        jwtService.generateAccessToken(savedUser));
  }

  @Transactional
  public void login() {
  }
}
