package com.unigo.security.permissions.molecule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.unigo.security.permissions.atom.CreatePermission;
import com.unigo.security.permissions.atom.DeletePermission;
import com.unigo.security.permissions.atom.ReadPermission;
import com.unigo.security.permissions.atom.UpdatePermission;
import com.unigo.security.permissions.util.AdditionalPermission;
import com.unigo.security.permissions.util.PermissionGenerator;
import com.unigo.security.permissions.util.PermissionSet;

public class CRUDPermission
    implements CreatePermission, ReadPermission, UpdatePermission, DeletePermission, PermissionSet,
    AdditionalPermission {
  protected final PermissionGenerator generatePermission;

  public CRUDPermission(String name) {
    this.generatePermission = new PermissionGenerator(name);
  }

  @Override
  public String create() {
    return generatePermission.generate("create");
  }

  @Override
  public String read() {
    return generatePermission.generate("read");
  }

  @Override
  public String update() {
    return generatePermission.generate("update");
  }

  @Override
  public String delete() {
    return generatePermission.generate("delete");
  }

  @Override
  public Collection<String> additionalPermission() {
    return List.of();
  }

  @Override
  public Collection<String> all() {
    return Stream.concat(Stream.of(create(), read(), update(), delete()), additionalPermission().stream()).toList();
  }

}
