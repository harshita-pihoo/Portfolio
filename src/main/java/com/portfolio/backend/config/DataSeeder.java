package com.portfolio.backend.config;

import com.portfolio.backend.model.AdminUser;
import com.portfolio.backend.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminUserRepository.findByUsername("admin").isEmpty()) {
            AdminUser admin = new AdminUser();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            adminUserRepository.save(admin);
            System.out.println("✓ Admin user created successfully.");
        } else {
            System.out.println("✓ Admin user already exists.");
        }
    }
}
