package com.example.health.controller;

import com.example.health.domain.Role;
import com.example.health.domain.Permission;
import com.example.health.mapper.RoleMapper;
import com.example.health.mapper.PermissionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleMapper roleMapper;
    public RoleController(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleMapper.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        Role role = roleMapper.findById(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<Permission>> getRolePermissions(@PathVariable Long id) {
        List<Permission> permissions = roleMapper.findPermissionsByRoleId(id);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Map<String, String> payload) {
        try {
            String name = payload.get("name");
            String description = payload.get("description");
            
            if (name == null || name.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Role name is required"));
            }
            
            Role existingRole = roleMapper.findByName(name);
            if (existingRole != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Role name already exists"));
            }
            
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            role.setCreatedAt(Instant.now().toString());
            
            roleMapper.insert(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            Role role = roleMapper.findById(id);
            if (role == null) {
                return ResponseEntity.notFound().build();
            }
            
            String name = payload.get("name");
            String description = payload.get("description");
            
            if (name != null && !name.equals(role.getName())) {
                Role existingRole = roleMapper.findByName(name);
                if (existingRole != null && !existingRole.getId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Role name already exists"));
                }
                role.setName(name);
            }
            
            if (description != null) {
                role.setDescription(description);
            }
            
            roleMapper.update(role);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/permissions")
    public ResponseEntity<?> assignPermissionToRole(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        try {
            Long permissionId = payload.get("permissionId");
            roleMapper.assignPermissionToRole(id, permissionId);
            return ResponseEntity.ok(Map.of("message", "Permission assigned successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/permissions/{permissionId}")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable Long id, @PathVariable Long permissionId) {
        try {
            roleMapper.removePermissionFromRole(id, permissionId);
            return ResponseEntity.ok(Map.of("message", "Permission removed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            int deleted = roleMapper.deleteById(id);
            if (deleted > 0) {
                return ResponseEntity.ok(Map.of("message", "Role deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
