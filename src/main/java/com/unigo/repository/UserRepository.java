package com.unigo.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unigo.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByRole_Name (String roleName); 

    @EntityGraph (attributePaths = "role.permissions")
    @Query ("select u from User u where u.id = :id")
    Optional<User> findByIdWithRoleAndPermissions(@Param("id") UUID id);
}
