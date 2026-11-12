package com.unigo.security.permissions;

import java.util.List;

import org.springframework.stereotype.Component;

import com.unigo.security.permissions.util.PermissionSet;

@Component
public final class Permissions {
  private final List<PermissionSet> permissionSets;

  public Permissions(List<PermissionSet> permissionSets) {
    this.permissionSets = permissionSets;
  }

  public List<String> all() {
    return permissionSets.stream().flatMap(permissionSet -> permissionSet.all().stream()).distinct().toList();
  }

}
