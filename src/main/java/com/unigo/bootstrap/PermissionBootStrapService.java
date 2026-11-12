package com.unigo.bootstrap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.unigo.entity.Permission;
import com.unigo.entity.Role;
import com.unigo.repository.PermissionRepository;
import com.unigo.repository.RoleRepository;
import com.unigo.security.permissions.Permissions;
import com.unigo.security.permissions.organism.RolesPermission;

@Component
public class PermissionBootStrapService {
  private final PermissionRepository permissionRepository;
  private final Permissions permissions;
  private final RolesPermission rolesPermission;
  private final RoleRepository roleRepository;

  public PermissionBootStrapService(PermissionRepository permissionRepository, Permissions permissions,
      RolesPermission rolesPermission, RoleRepository roleRepository) {
    this.permissionRepository = permissionRepository;
    this.permissions = permissions;
    this.rolesPermission = rolesPermission;
    this.roleRepository = roleRepository;
  }

  private Role assignDefaultPermissionToSuperAdmin() {
    Role superAdmin = roleRepository.findRoleAndPermissionsByName("SUPER_ADMIN").orElseThrow(() -> {
      throw new IllegalStateException("Super admin role not found");
    });
    Permission assignPermissionsPermission = permissionRepository.findByCode(rolesPermission.assignPermissions());
    if (assignPermissionsPermission == null) {
      throw new IllegalStateException("Assign permissions permission not found");
    }
    superAdmin.getPermissions().add(assignPermissionsPermission);
    return roleRepository.save(superAdmin);
  }

  public void bootstrap() {
    Set<String> existsPermissions = new HashSet<>(
        permissionRepository.findAll().stream().map(permission -> permission.getCode()).toList());

    Set<String> definedPermissions = new HashSet<>(permissions.all());

    Set<Permission> newPermissions = new HashSet<>(
        definedPermissions.stream().filter(permission -> !existsPermissions.contains(permission)).map(code -> {
          Permission permission = new Permission();
          permission.setIsActive(true);
          permission.setCode(code);
          return permission;
        }).toList());

    // if (newPermissions.isEmpty()) {
    // System.out.println("No new any permissions, bootstrap skipped!");
    // return;
    // }
    List<Permission> savedPermissions = permissionRepository.saveAllAndFlush(newPermissions);
    System.out.println("Saved permissions: ");
    System.out.println(savedPermissions.stream().map(permission -> permission.getCode()).toList());
    assignDefaultPermissionToSuperAdmin();
  }

}
