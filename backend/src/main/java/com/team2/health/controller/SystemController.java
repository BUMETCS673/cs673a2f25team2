package com.team2.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {
    // Simple health check endpoint
    @GetMapping("/api/ping")
    public String ping() {
        return "ok";
    }
}

