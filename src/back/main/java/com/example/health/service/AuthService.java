package com.example.health.service;

import com.example.health.domain.User;
import com.example.health.mapper.UserMapper;
import com.example.health.mapper.RoleMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    public User register(String username, String rawPassword, String email, String fullName) {
        if (username == null || username.isBlank() || rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        
        User existing = userMapper.findByUsername(username);
        if (existing != null) {
            throw new IllegalStateException("Username already exists");
        }
        
        if (email != null && !email.isBlank()) {
            User existingEmail = userMapper.findByEmail(email);
            if (existingEmail != null) {
                throw new IllegalStateException("Email already exists");
            }
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setEmail(email);
        user.setFullName(fullName);
        user.setLoginType("PASSWORD");
        user.setIsActive(true);
        user.setCreatedAt(Instant.now().toString());
        user.setUpdatedAt(Instant.now().toString());
        
        userMapper.insert(user);
        
        // Assign default USER role
        try {
            var userRole = roleMapper.findByName("USER");
            if (userRole != null) {
                roleMapper.assignRoleToUser(user.getId(), userRole.getId());
            }
        } catch (Exception e) {
            // Log error but don't fail registration
            System.err.println("Failed to assign default role: " + e.getMessage());
        }
        
        user.setPasswordHash(null);
        return user;
    }

    public User login(String username, String rawPassword) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        if (!user.getIsActive()) {
            throw new IllegalStateException("Account is deactivated");
        }
        user.setPasswordHash(null);
        return user;
    }

    public User loginWithGoogle(String googleId, String email, String fullName, String avatar) {
        User user = userMapper.findByGoogleId(googleId);
        
        if (user == null) {
            // Check if user exists with this email
            if (email != null) {
                user = userMapper.findByEmail(email);
                if (user != null) {
                    // Link Google account to existing user
                    user.setGoogleId(googleId);
                    user.setLoginType("GOOGLE");
                    user.setUpdatedAt(Instant.now().toString());
                    userMapper.update(user);
                }
            }
            
            if (user == null) {
                // Create new user
                user = new User();
                user.setGoogleId(googleId);
                user.setEmail(email);
                user.setFullName(fullName);
                user.setAvatar(avatar);
                user.setUsername(email != null ? email : "user_" + googleId);
                user.setLoginType("GOOGLE");
                user.setIsActive(true);
                user.setCreatedAt(Instant.now().toString());
                user.setUpdatedAt(Instant.now().toString());
                
                userMapper.insert(user);
                
                // Assign default USER role
                try {
                    var userRole = roleMapper.findByName("USER");
                    if (userRole != null) {
                        roleMapper.assignRoleToUser(user.getId(), userRole.getId());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to assign default role: " + e.getMessage());
                }
            }
        }
        
        if (!user.getIsActive()) {
            throw new IllegalStateException("Account is deactivated");
        }
        
        return user;
    }

    public User updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        if (!"PASSWORD".equals(user.getLoginType())) {
            throw new IllegalStateException("Cannot change password for OAuth users");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid old password");
        }
        
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }
        
        String newPasswordHash = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(userId, newPasswordHash);
        
        user.setPasswordHash(null);
        return user;
    }

    public User updateProfile(Long userId, String email, String fullName, Integer age, String gender, String phone) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Check email uniqueness if email is being changed
        if (email != null && !email.equals(user.getEmail())) {
            User existingEmail = userMapper.findByEmail(email);
            if (existingEmail != null && !existingEmail.getId().equals(userId)) {
                throw new IllegalStateException("Email already exists");
            }
        }
        
        user.setEmail(email);
        user.setFullName(fullName);
        user.setAge(age);
        user.setGender(gender);
        user.setPhone(phone);
        user.setUpdatedAt(Instant.now().toString());
        
        userMapper.update(user);
        user.setPasswordHash(null);
        return user;
    }
}


