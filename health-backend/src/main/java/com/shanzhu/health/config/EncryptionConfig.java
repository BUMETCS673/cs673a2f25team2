package com.shanzhu.health.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Encryption configuration class
 * Reads encryption key from configuration file and sets it to system properties for DataEncryptionUtil to use
 */
@Configuration
public class EncryptionConfig {
    
    @Value("${encryption.key:HealthSystem2024Key}")
    private String encryptionKey;
    
    @PostConstruct
    public void init() {
        // Set configured key to system properties for DataEncryptionUtil to use
        if (!System.getProperties().containsKey("encryption.key")) {
            System.setProperty("encryption.key", encryptionKey);
        }
    }
}

