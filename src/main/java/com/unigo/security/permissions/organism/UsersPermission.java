package com.unigo.security.permissions.organism;

import org.springframework.stereotype.Component;

import com.unigo.security.permissions.molecule.CRUDeactivatePermission;

@Component
public class UsersPermission extends CRUDeactivatePermission {
  public UsersPermission() {
    super("users");
  }

}
