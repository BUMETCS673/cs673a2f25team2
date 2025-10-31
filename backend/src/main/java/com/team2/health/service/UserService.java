package com.team2.health.service;

import com.team2.health.entity.User;
import com.team2.health.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Register a new user with password hashing
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Validate credentials and return JWT token if ok
    public Optional<String> login(String username, String rawPassword) {
        Optional<User> u = userRepository.findByUsername(username);
        if (u.isPresent() && passwordEncoder.matches(rawPassword, u.get().getPassword())) {
            return Optional.of(jwtService.generateToken(u.get()));
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
