package com.example.health.controller;

import com.example.health.domain.User;
import com.example.health.domain.Role;
import com.example.health.domain.Permission;
import com.example.health.mapper.UserMapper;
import com.example.health.mapper.RoleMapper;
import com.example.health.mapper.PermissionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    public UserController(UserMapper userMapper, RoleMapper roleMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userMapper.findAll();
        // Remove password hashes for security
        users.forEach(user -> user.setPasswordHash(null));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPasswordHash(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable Long id) {
        List<Role> roles = roleMapper.findByUserId(id);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<Permission>> getUserPermissions(@PathVariable Long id) {
        List<Permission> permissions = permissionMapper.findByUserId(id);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<?> assignRoleToUser(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        try {
            Long roleId = payload.get("roleId");
            roleMapper.assignRoleToUser(id, roleId);
            return ResponseEntity.ok(Map.of("message", "Role assigned successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    public ResponseEntity<?> removeRoleFromUser(@PathVariable Long id, @PathVariable Long roleId) {
        try {
            roleMapper.removeRoleFromUser(id, roleId);
            return ResponseEntity.ok(Map.of("message", "Role removed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            int deleted = userMapper.deleteById(id);
            if (deleted > 0) {
                return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
