package com.unigo.security.permission.molecule;

import com.unigo.security.permission.atom.CreatePermission;
import com.unigo.security.permission.atom.DeactivatePermission;
import com.unigo.security.permission.atom.PermissionGenerator;
import com.unigo.security.permission.atom.ReadPermission;
import com.unigo.security.permission.atom.UpdatePermission;

public class CRUDeactivatePermission
    implements CreatePermission, ReadPermission, UpdatePermission, DeactivatePermission {
  private final PermissionGenerator permissionGenerator;

  public CRUDeactivatePermission(String name) {
    this.permissionGenerator = new PermissionGenerator(name);
  }

  @Override
  public String create() {
    return permissionGenerator.generate("create");
  }

  @Override
  public String read() {
    return permissionGenerator.generate("read");
  }

  @Override
  public String update() {
    return permissionGenerator.generate("update");
  }

  @Override
  public String deactivate() {
    return permissionGenerator.generate("deactivate");
  }

  protected final String generatePermission(String action) {
    return permissionGenerator.generate(action);
  }

}
