package com.unigo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unigo.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
