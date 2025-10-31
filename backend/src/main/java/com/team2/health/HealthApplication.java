package com.team2.health;

import com.team2.health.entity.User;
import com.team2.health.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HealthApplication {
    // Entry point for the backend service
    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
      return args -> {
          userRepository.findByUsername("admin").orElseGet(() -> {
              User admin = new User();
              admin.setUsername("admin");
              admin.setPassword(passwordEncoder.encode("admin123"));
              admin.setRole("ADMIN");
              return userRepository.save(admin);
          });
      };
    }
}
