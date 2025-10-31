package com.team2.health.controller;

import com.team2.health.dto.UserLoginRequest;
import com.team2.health.dto.UserLoginResponse;
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

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());
        User saved = userService.register(user);
        return ResponseEntity.ok(saved.getId());
    }

    // Login endpoint (returns JWT + user info)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest req) {
        var tokenOpt = userService.login(req.getUsername(), req.getPassword());
        if (tokenOpt.isPresent()) {
            User u = userService.findByUsername(req.getUsername()).orElseThrow();
            return ResponseEntity.ok(new UserLoginResponse(u.getId(), u.getUsername(), u.getRole(), tokenOpt.get()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
