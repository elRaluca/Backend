package com.example.backend.config;

import com.example.backend.entity.OurUsers;
import com.example.backend.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializerConfig {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            String adminEmail = "Admin@testadmin.com";
            if (!ourUserRepo.findByEmail(adminEmail).isPresent()) {
                OurUsers adminUser = new OurUsers();
                adminUser.setName("Admin");
                adminUser.setEmail(adminEmail);
                adminUser.setPassword(passwordEncoder.encode("Admin.1"));
                adminUser.setRole("ROLE_ADMIN");
                adminUser.setActive(true);
                ourUserRepo.save(adminUser);
            }
        };
    }
}

