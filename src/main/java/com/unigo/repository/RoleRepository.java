package com.unigo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unigo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Optional<Role> findByName(String name);
}
