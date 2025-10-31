package com.team2.health.controller;

import com.team2.health.dto.UserLoginRequest;
import com.team2.health.dto.UserRegisterRequest;
import com.team2.health.entity.User;
import com.team2.health.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register endpoint (demo purposes)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword()); // TODO: add hashing later
        user.setRole(req.getRole());
        User saved = userService.register(user);
        return ResponseEntity.ok(saved.getId());
    }

    // Login endpoint (returns simple boolean)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest req) {
        boolean ok = userService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(ok);
    }
}

