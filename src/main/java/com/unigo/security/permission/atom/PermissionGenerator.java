package com.unigo.security.permission.atom;

public class PermissionGenerator {
  private static final String PREFIX = "permission";
  private final String name;

  public PermissionGenerator(String name) {
    this.name = name;
  }

  public String generate(String action) {
    return "%s.%s.%s".formatted(PREFIX, name, action);
  }
}
