package com.shanzhu.health.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Data encryption utility class
 * Uses AES algorithm to encrypt and decrypt sensitive information (email, phone)
 * Key is read from system properties or environment variables, defaults to "HealthSystem2024Key"
 */
public class DataEncryptionUtil {
    
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    // Read key from system properties or environment variables, use default value if not available
    private static final String encryptionKey = getEncryptionKey();
    
    /**
     * Get encryption key
     * Priority: system property encryption.key > environment variable ENCRYPTION_KEY > default value
     */
    private static String getEncryptionKey() {
        String key = System.getProperty("encryption.key");
        if (key == null || key.isEmpty()) {
            key = System.getenv("ENCRYPTION_KEY");
        }
        if (key == null || key.isEmpty()) {
            key = "HealthSystem2024Key"; // Default key
        }
        
        // Ensure key length is 16 bytes (AES-128)
        if (key.length() < 16) {
            // If key is too short, pad it
            StringBuilder sb = new StringBuilder(key);
            while (sb.length() < 16) {
                sb.append("0");
            }
            return sb.substring(0, 16);
        } else if (key.length() > 16) {
            // If key is too long, take first 16 characters
            return key.substring(0, 16);
        } else {
            return key;
        }
    }
    
    /**
     * Encrypt data
     * @param data Original data
     * @return Base64 encoded string after encryption, returns original value if input is null or empty string
     */
    public static String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Data encryption failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Decrypt data
     * @param encryptedData Base64 encoded string after encryption
     * @return Decrypted original data, returns original value if input is null or empty string
     */
    public static String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return encryptedData;
        }
        try {
            // Check if it is Base64 format encrypted data
            if (!isBase64Encrypted(encryptedData)) {
                // If not encrypted format, might be old data, return directly
                return encryptedData;
            }
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Decryption failed, might be old data or corrupted data, return original value
            return encryptedData;
        }
    }
    
    /**
     * Check if string is Base64 encoded encrypted data
     * @param data String to check
     * @return Whether it is encrypted data
     */
    private static boolean isBase64Encrypted(String data) {
        try {
            Base64.getDecoder().decode(data);
            // If it can be decoded and length is reasonable (encrypted data is usually longer than original), consider it encrypted data
            return data.length() > 10; // Simple judgment, can be adjusted according to actual situation
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Encrypt data for query (for query conditions)
     * Note: This method is used to encrypt search conditions when querying
     * @param data Original data
     * @return Encrypted data
     */
    public static String encryptForQuery(String data) {
        return encrypt(data);
    }
}

