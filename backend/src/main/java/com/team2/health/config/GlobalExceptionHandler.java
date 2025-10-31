package com.team2.health.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception for {} {}: {}", req.getMethod(), req.getRequestURI(), ex.getMessage(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", java.time.Instant.now().toString());
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getClass().getSimpleName() + ": " + ex.getMessage());
        body.put("path", req.getRequestURI());
        return ResponseEntity.internalServerError().body(body);
    }
}

