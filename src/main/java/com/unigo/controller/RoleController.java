package com.unigo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unigo.dto.request.role.CreateRole;
import com.unigo.dto.response.RoleResponse;
import com.unigo.dto.response.RolesResponse;
import com.unigo.service.RoleService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("roles")
public class RoleController {
  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping()
  public RoleResponse createRole(@Valid @RequestBody CreateRole body) {
    return roleService.createRole(body);
  }

  @GetMapping("/{id}")
  public RoleResponse getRole(@PathVariable Integer id) {

    return roleService.getRole(id);
  }

  @GetMapping()
  public RolesResponse getRoles() {
    return roleService.getRoles();
  }

  @PatchMapping()
  public String updateRole(@RequestBody String body) {
    return body;
  }

}
