package com.unigo.security.permission.molecule;

import com.unigo.security.permission.atom.CreatePermission;
import com.unigo.security.permission.atom.PermissionGenerator;
import com.unigo.security.permission.atom.ReadPermission;

public class CRPermission implements CreatePermission, ReadPermission {
  private final PermissionGenerator permissionGenerator;

  public CRPermission(String name) {
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

  protected final String generatePermission(String action) {
    return permissionGenerator.generate(action);
  }

}
