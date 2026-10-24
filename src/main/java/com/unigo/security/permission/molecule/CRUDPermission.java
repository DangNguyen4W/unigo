package com.unigo.security.permission.molecule;

import com.unigo.security.permission.atom.CreatePermission;
import com.unigo.security.permission.atom.DeletePermission;
import com.unigo.security.permission.atom.PermissionGenerator;
import com.unigo.security.permission.atom.ReadPermission;
import com.unigo.security.permission.atom.UpdatePermission;

public class CRUDPermission implements CreatePermission, ReadPermission, UpdatePermission, DeletePermission {
  private final PermissionGenerator permissionGenerator;
  // private final String create;

  public CRUDPermission(String name) {
    this.permissionGenerator = new PermissionGenerator(name);
    // create = permissionGenerator.generate("create");
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
  public String delete() {
    return permissionGenerator.generate("delete");
  }

  protected final String generatePermission(String action) {
    return permissionGenerator.generate(action);
  }

}
