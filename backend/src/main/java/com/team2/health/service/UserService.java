package com.team2.health.service;

import com.team2.health.entity.User;
import com.team2.health.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user (basic, no hashing for demo)
    public User register(User user) {
        return userRepository.save(user);
    }

    // Simple login validation (placeholder)
    public boolean login(String username, String password) {
        Optional<User> u = userRepository.findByUsername(username);
        return u.map(value -> value.getPassword().equals(password)).orElse(false);
    }
}

