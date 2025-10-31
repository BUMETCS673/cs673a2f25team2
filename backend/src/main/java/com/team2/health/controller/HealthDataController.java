package com.team2.health.controller;

import com.team2.health.dto.HealthDataRequest;
import com.team2.health.entity.HealthData;
import com.team2.health.service.HealthDataService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health")
public class HealthDataController {
    private final HealthDataService healthDataService;

    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    // Create a health record
    @PostMapping("/records")
    public ResponseEntity<?> create(@Valid @RequestBody HealthDataRequest req) {
        Optional<HealthData> hd = healthDataService.create(req.getUserId(), req.getDataType(), req.getValue(), req.getUnit(), req.getNotes());
        return hd.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid userId"));
    }

    // List records by user id
    @GetMapping("/records/{userId}")
    public ResponseEntity<List<HealthData>> list(@PathVariable Long userId) {
        return ResponseEntity.ok(healthDataService.listByUser(userId));
    }
}

