package com.team2.health.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import com.team2.health.service.UserService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {
    private final UserService userService;

    public SystemController(UserService userService) {
        this.userService = userService;
    }
    // Simple health check endpoint
    @GetMapping("/api/ping")
    public String ping() {
        return "ok";
    }

    // Return current authenticated user info (requires JWT)
    @GetMapping("/api/me")
    public ResponseEntity<?> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return ResponseEntity.status(401).body("unauthenticated");
        }
        String username = String.valueOf(auth.getPrincipal());
        var uOpt = userService.findByUsername(username);
        if (uOpt.isEmpty()) {
            return ResponseEntity.status(404).body("user not found");
        }
        var u = uOpt.get();
        return ResponseEntity.ok(Map.of(
                "id", u.getId(),
                "username", u.getUsername(),
                "role", u.getRole()
        ));
    }
}
