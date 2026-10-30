package com.unigo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unigo.dto.response.PermissionsResponse;
import com.unigo.service.PermissionService;


@RestController
@RequestMapping("permissions")
public class PermissionController {
  private final PermissionService permissionService;

  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping()
  public PermissionsResponse getMethodName() {
    return permissionService.getPermissions();
  }
  
}
