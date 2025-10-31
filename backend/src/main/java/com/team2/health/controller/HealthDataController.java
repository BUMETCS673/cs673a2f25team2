package com.team2.health.controller;

import com.team2.health.dto.HealthDataRequest;
import com.team2.health.dto.HealthDataResponse;
import com.team2.health.entity.HealthData;
import com.team2.health.service.HealthDataService;
import com.team2.health.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health")
public class HealthDataController {
    private final HealthDataService healthDataService;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(HealthDataController.class);

    public HealthDataController(HealthDataService healthDataService, UserService userService) {
        this.healthDataService = healthDataService;
        this.userService = userService;
    }

    // Create a health record
    @PostMapping("/records")
    public ResponseEntity<?> create(@Valid @RequestBody HealthDataRequest req) {
        // 普通用户只能创建“本人”的记录；管理员不受限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
        if (!isAdmin) {
            String username = auth != null ? String.valueOf(auth.getPrincipal()) : null;
            if (username == null) {
                return ResponseEntity.status(401).body("unauthenticated");
            }
            var uOpt = userService.findByUsername(username);
            if (uOpt.isEmpty() || !uOpt.get().getId().equals(req.getUserId())) {
                return ResponseEntity.status(403).body("forbidden");
            }
        }
        Optional<HealthData> hd = healthDataService.create(req.getUserId(), req.getDataType(), req.getValue(), req.getUnit(), req.getNotes());
        return hd.<ResponseEntity<?>>map(h -> ResponseEntity.ok(toResponse(h)))
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid userId"));
    }

    // List all records (debug)
    @GetMapping("/records")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HealthDataResponse>> listAll() {
        List<HealthData> list = healthDataService.listAll();
        return ResponseEntity.ok(list.stream().map(this::toResponse).toList());
    }

    // List records by user id
    @GetMapping("/records/{userId}")
    public ResponseEntity<List<HealthDataResponse>> list(@PathVariable("userId") Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
        if (!isAdmin) {
            String username = auth != null ? String.valueOf(auth.getPrincipal()) : null;
            if (username == null) {
                return ResponseEntity.status(401).body(List.of());
            }
            var uOpt = userService.findByUsername(username);
            if (uOpt.isEmpty() || !uOpt.get().getId().equals(userId)) {
                return ResponseEntity.status(403).body(List.of());
            }
        }
        List<HealthData> list = healthDataService.listByUser(userId);
        return ResponseEntity.ok(list.stream().map(this::toResponse).toList());
    }

    // Debug endpoint to verify security and mapping
    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    private HealthDataResponse toResponse(HealthData h) {
        HealthDataResponse r = new HealthDataResponse();
        r.setId(h.getId());
        r.setUserId(h.getUser().getId());
        r.setDataType(h.getDataType());
        r.setValue(h.getValue());
        r.setUnit(h.getUnit());
        r.setRecordTime(h.getRecordTime() != null ? h.getRecordTime().toString() : null);
        r.setNotes(h.getNotes());
        return r;
    }
}
