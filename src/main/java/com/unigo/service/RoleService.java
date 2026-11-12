package com.unigo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.unigo.dto.request.AssignPermissionsRequest;
import com.unigo.dto.request.CreateRole;
import com.unigo.dto.response.PermissionResponse;
import com.unigo.dto.response.RoleResponse;
import com.unigo.dto.response.RoleResponseDetail;
import com.unigo.dto.response.RolesResponse;
import com.unigo.entity.Permission;
import com.unigo.entity.Role;
import com.unigo.repository.PermissionRepository;
import com.unigo.repository.RoleRepository;

@Service
public class RoleService {
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  private RoleResponseDetail createRoleResponseDetailFromRole(Role role) {
    Set<PermissionResponse> permissions = new HashSet<>(role
        .getPermissions().stream().<PermissionResponse>map(permission -> new PermissionResponse(permission.getId(),
            permission.getCode(), permission.isActive(), permission.getCreatedAt(), permission.getUpdatedAt()))
        .toList());

    return new RoleResponseDetail(role.getId(),
        role.getName(), role.getIsActive(), permissions, role.getCreatedAt(),
        role.getUpdatedAt());
  }

  @Transactional 
  public RolesResponse getRoles() {
    return new RolesResponse(roleRepository.findAll().stream().<RoleResponse>map(role -> new RoleResponse(role.getId(),
        role.getName(), role.getIsActive(), role.getCreatedAt(), role.getUpdatedAt())).toList());
  }

  @Transactional 
  public RoleResponseDetail createRole(CreateRole body) {
    String name = body.name();
    if (roleRepository.existsByName(name)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Role name already exists");
    }
    Role role = new Role(name);
    Role savedRole = roleRepository.save(role);

    return createRoleResponseDetailFromRole(savedRole);

  }

  @Transactional 
  public RoleResponseDetail getRole(Integer id) {
    Role role = roleRepository.findById(id).orElseThrow(
        () -> new IllegalStateException("Role not found"));

    return createRoleResponseDetailFromRole(role);
  }

  @Transactional
  public RoleResponseDetail assignPermissions(AssignPermissionsRequest body) {
    Role role = roleRepository.findById(body.roleId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    Set<Permission> permissions = new HashSet<>(
        permissionRepository.findAllById(body.permissionIds()).stream().toList());
    if (permissions.size() != body.permissionIds().size()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more permission not found");
    }
    role.getPermissions().addAll(permissions);
    Role savedRole = roleRepository.save(role);
    return createRoleResponseDetailFromRole(savedRole);
  }
}
