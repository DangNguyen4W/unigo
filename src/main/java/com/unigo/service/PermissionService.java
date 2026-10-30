package com.unigo.service;

import org.springframework.stereotype.Service;

import com.unigo.dto.response.PermissionResponse;
import com.unigo.dto.response.PermissionsResponse;
import com.unigo.repository.PermissionRepository;

@Service
public class PermissionService {
  private final PermissionRepository permissionRepository;

  public PermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  public PermissionsResponse getPermissions() {

    return new PermissionsResponse(permissionRepository.findAll().stream()
        .<PermissionResponse>map(
            permission -> new PermissionResponse(permission.getId(), permission.getCode(), permission.isActive(),
                permission.getCreatedAt(), permission.getUpdatedAt()))
        .toList());
  }
}
