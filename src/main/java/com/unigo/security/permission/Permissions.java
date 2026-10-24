package com.unigo.security.permission;

import com.unigo.security.permission.organism.UsersPermission;

public final class Permissions {
  public static final UsersPermission USERS = new UsersPermission();

  private Permissions() {
  }
}
