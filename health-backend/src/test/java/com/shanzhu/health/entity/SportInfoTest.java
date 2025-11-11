package com.shanzhu.health.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SportInfo entity
 */
class SportInfoTest {

    private SportInfo sportInfo;

    @BeforeEach
    void setUp() {
        sportInfo = new SportInfo();
    }

    @Test
    void testSportInfoCreation() {
        sportInfo.setId(1);
        sportInfo.setSportType("Running");
        sportInfo.setSuitableTime("Morning");
        sportInfo.setSuitableHeartRate("120-150 beats/min");
        sportInfo.setSuitableFrequency("3-4 times/week");
        sportInfo.setRecommendedSpeed("6-8 km/h");

        assertEquals(1, sportInfo.getId());
        assertEquals("Running", sportInfo.getSportType());
        assertEquals("Morning", sportInfo.getSuitableTime());
        assertEquals("120-150 beats/min", sportInfo.getSuitableHeartRate());
        assertEquals("3-4 times/week", sportInfo.getSuitableFrequency());
        assertEquals("6-8 km/h", sportInfo.getRecommendedSpeed());
    }

    @Test
    void testSportInfoDefaultValues() {
        assertNull(sportInfo.getId());
        assertNull(sportInfo.getSportType());
        assertNull(sportInfo.getSuitableTime());
    }

    @Test
    void testSportTypeNotEmpty() {
        sportInfo.setSportType("Swimming");
        assertNotNull(sportInfo.getSportType());
        assertFalse(sportInfo.getSportType().isEmpty());
    }

    @Test
    void testHeartRateFormat() {
        sportInfo.setSuitableHeartRate("120-150 beats/min");
        assertTrue(sportInfo.getSuitableHeartRate().contains("-"));
        assertTrue(sportInfo.getSuitableHeartRate().contains("beats"));
    }

    @Test
    void testFrequencyFormat() {
        sportInfo.setSuitableFrequency("3-4 times/week");
        assertTrue(sportInfo.getSuitableFrequency().contains("times"));
        assertTrue(sportInfo.getSuitableFrequency().contains("week"));
    }

    @Test
    void testMultipleSportTypes() {
        String[] sportTypes = {"Running", "Swimming", "Cycling", "Yoga", "Weightlifting"};

        for (String type : sportTypes) {
            sportInfo.setSportType(type);
            assertEquals(type, sportInfo.getSportType());
            assertNotNull(sportInfo.getSportType());
        }
    }
}

