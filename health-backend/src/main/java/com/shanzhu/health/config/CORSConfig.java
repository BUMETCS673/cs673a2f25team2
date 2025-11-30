package com.shanzhu.health.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:5173,http://localhost:3000}")
    private String[] allowedOrigins;

    public void addCorsMappings(CorsRegistry registry) {
        // Set allowed cross-origin paths
        registry.addMapping("/**")
                // Set allowed cross-origin request domains (use specific domain list, don't use *)
                .allowedOriginPatterns(allowedOrigins)
                // Whether to allow cookies
                .allowCredentials(true)
                // Set allowed request methods
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
                // Set allowed header properties
                .allowedHeaders("*")
                // Explicitly exposed headers
                .exposedHeaders("X-Token")
                // Cross-origin allowed time
                .maxAge(3600);
    }
}


