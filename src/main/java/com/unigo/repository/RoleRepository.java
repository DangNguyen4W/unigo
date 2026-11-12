package com.unigo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unigo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String name);

  @EntityGraph (attributePaths = "permissions")
  Optional<Role> findRoleAndPermissionsByName(String name);


  boolean existsByName(String name);
}
