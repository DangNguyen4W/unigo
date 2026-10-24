package com.unigo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.unigo.dto.request.role.CreateRole;
import com.unigo.dto.response.RoleResponse;
import com.unigo.dto.response.RolesResponse;
import com.unigo.entity.Role;
import com.unigo.repository.RoleRepository;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public RolesResponse getRoles() {
    return new RolesResponse(roleRepository.findAll().stream().map(role -> new RoleResponse(role.getId(),
        role.getName(), role.getIsActive(), role.getCreatedAt(), role.getUpdatedAt())).toList());
  }

  public RoleResponse createRole(CreateRole body) {
    String name = body.name();
    if (roleRepository.existsByName(name)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Role name already exists");
    }
    Role role = new Role(name);
    Role savedRole = roleRepository.save(role);
    return new RoleResponse(savedRole.getId(),
        savedRole.getName(), savedRole.getIsActive(), savedRole.getCreatedAt(), savedRole.getUpdatedAt());

  }

  public RoleResponse getRole(Integer id) {
    Role role = roleRepository.findById(id).orElseThrow(
        () -> new IllegalStateException("Role not found"));

    return new RoleResponse(role.getId(),
        role.getName(), role.getIsActive(), role.getCreatedAt(), role.getUpdatedAt());
  }
}
