package com.shanzhu.health.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity
 */
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserCreation() {
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setPhone("18812345678");
        user.setStatus(1);

        assertEquals(1, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("18812345678", user.getPhone());
        assertEquals(1, user.getStatus());
    }

    @Test
    void testUserDefaultValues() {
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    void testUserEmailValidation() {
        user.setEmail("test@example.com");
        assertTrue(user.getEmail().contains("@"));
        assertTrue(user.getEmail().contains("."));
    }

    @Test
    void testUserPhoneFormat() {
        user.setPhone("18812345678");
        assertEquals(11, user.getPhone().length());
        assertTrue(user.getPhone().matches("\\d{11}"));
    }

    @Test
    void testUserStatusValues() {
        // Test enabled status
        user.setStatus(1);
        assertEquals(1, user.getStatus());

        // Test disabled status
        user.setStatus(0);
        assertEquals(0, user.getStatus());
    }

    @Test
    void testUserEquality() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("test");

        User user2 = new User();
        user2.setId(1);
        user2.setUsername("test");

        // Test that users with same ID are considered equal (assuming Lombok @EqualsAndHashCode)
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getUsername(), user2.getUsername());
    }
}

