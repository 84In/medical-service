package com.vasd.medical_service.auth.init;

import com.vasd.medical_service.auth.entities.Role;
import com.vasd.medical_service.auth.repository.RoleRepository;
import com.vasd.medical_service.auth.entities.User;
import com.vasd.medical_service.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.saveAll(List.of(adminRole, userRole));
            log.info("Roles initialized");
        }

        if (userRepository.findUserByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@gmail.com");
            admin.setPhone("0776972152");
            admin.setRole(roleRepository.findRoleByName("ADMIN"));

            log.info("User admin initialized with username {} and password {}", admin.getUsername(), "admin123");
            userRepository.save(admin);
        }
    }
}
