package com.unigo.security.permissions.organism;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.unigo.security.permissions.molecule.CRUDeactivatePermission;

@Component
public class RolesPermission extends CRUDeactivatePermission {
  public RolesPermission() {
    super("roles");
  }

  public String assignPermissions() {
    return generatePermission.generate("assign-permissions");
  }

  @Override
  public Collection<String> additionalPermission() {
    return List.of(
        assignPermissions());
  }

}
