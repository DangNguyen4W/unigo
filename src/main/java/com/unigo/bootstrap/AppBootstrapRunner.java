package com.unigo.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.bootstrap-runner", name = "enabled", havingValue = "true")
public class AppBootstrapRunner implements ApplicationRunner {

  private final Environment environment;
  private final AdminBootstrapService adminBootstrapService;
  private final PermissionBootStrapService permissionBootStrapService;

  public AppBootstrapRunner(Environment environment, AdminBootstrapService adminBootstrapService,
      PermissionBootStrapService permissionBootStrapService) {
    this.adminBootstrapService = adminBootstrapService;
    this.environment = environment;
    this.permissionBootStrapService = permissionBootStrapService;
  }

  @Override
  public void run(ApplicationArguments arg) {
    boolean enableCreateAdmin = Boolean.parseBoolean(
        environment.getProperty(
            "app.bootstrap-admin.enabled",
            "false"));
    boolean enableUpdatePermissions = Boolean.parseBoolean(
        environment.getProperty(
            "app.bootstrap-permissions.enabled",
            "false"));
    if (enableCreateAdmin) {
      adminBootstrapService.bootstrap();
    }
    if (enableUpdatePermissions) {
      permissionBootStrapService.bootstrap();
    }

  }

}
