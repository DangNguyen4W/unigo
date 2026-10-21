package com.unigo.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.unigo.entity.Role;
import com.unigo.entity.User;
import com.unigo.repository.RoleRepository;
import com.unigo.repository.UserRepository;

@Component
@ConditionalOnProperty(prefix = "app.bootstrap-admin", name = "enabled", havingValue = "true")
public class AdminBootstrapRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final Environment environment;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrapRunner(UserRepository userRepository, Environment environment, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.environment = environment;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments arg) {
        boolean isAdminExist = userRepository.existsByRole_Name("SUPER_ADMIN");
        if (isAdminExist) {
            System.out.println("SUPER_ADMIN already exists. Bootstrap skipped.");
            return;
        }

        Role role = roleRepository.findByName("SUPER_ADMIN")
                .orElseThrow(() -> new IllegalStateException("SUPER_ADMIN role is not exist"));
        String username = environment.getProperty(
                "app.bootstrap-admin.username");
        String email = environment.getProperty(
                "app.bootstrap-admin.email");
        String password = environment.getProperty(
                "app.bootstrap-admin.password");
        String phoneNumber = environment.getProperty("app.bootstrap-admin.phone_number");
        if (!StringUtils.hasText(username)
                || !StringUtils.hasText(email)
                || !StringUtils.hasText(password)
                || !StringUtils.hasText(phoneNumber)) {

            throw new IllegalStateException(
                    "Bootstrap username, email, password, and phone number must not be blank");
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalStateException("Bootstrap phone number already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Bootstrap email already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Bootstrap username already exists");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User admin = new User();
        admin.setEmail(email);
        admin.setUsername(username);
        admin.setPhoneNumber(phoneNumber);
        admin.setPassword(hashedPassword);
        admin.setRole(role);
        userRepository.save(admin);
        System.out.println("SUPER_ADMIN created successfully.");
    }

}
