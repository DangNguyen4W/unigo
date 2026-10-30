package com.unigo.bootstrap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.unigo.entity.Permission;
import com.unigo.repository.PermissionRepository;
import com.unigo.security.permissions.Permissions;

@Component
public class PermissionBootStrapService {
  private final PermissionRepository permissionRepository;
  private final Permissions permissions;

  public PermissionBootStrapService(PermissionRepository permissionRepository, Permissions permissions) {
    this.permissionRepository = permissionRepository;
    this.permissions = permissions;
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

    if (newPermissions.isEmpty()) {
      System.out.println("No new any permissions, bootstrap skipped!");
      return;
    }
    List<Permission> savedPermissions = permissionRepository.saveAllAndFlush(newPermissions);
    System.out.println("Saved permissions: ");
    System.out.println(savedPermissions.stream().map(permission -> permission.getCode()).toList());
  }

}
