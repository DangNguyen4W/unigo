package com.unigo.security.permissions.util;

public class PermissionGenerator {
  private static final String PREFIX = "permission";
  private final String name;

  public PermissionGenerator(String name) {
    this.name = name;
  }

  public String generate(String action) {
    if (action == null || action.isBlank()) {
      throw new IllegalArgumentException(
          "Action must not be blank");
    }

    return "%s.%s.%s".formatted(PREFIX, name, action);
  }

}
