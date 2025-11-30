package com.shanzhu.health.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password encryption utility class
 * Uses BCrypt algorithm for password encryption and verification
 */
public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * Encrypt password
     * @param rawPassword Original password
     * @return Encrypted password
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    /**
     * Verify password
     * @param rawPassword Original password
     * @param encodedPassword Encrypted password
     * @return Whether it matches
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}

