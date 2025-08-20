package com.example.mostin.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Unit tests for LocationCalculator utility class
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class LocationCalculatorTest {

    private static final double SEOUL_LAT = 37.5665;
    private static final double SEOUL_LON = 126.9780;
    private static final double BUSAN_LAT = 35.1796;
    private static final double BUSAN_LON = 129.0756;
    private static final double DELTA = 0.001;

    @Test
    public void should_return_max_value_when_coordinates_are_null() {
        // Given
        Double lat1 = null;
        Double lon1 = 126.9780;
        Double lat2 = 37.5665;
        Double lon2 = null;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        assertEquals(Float.MAX_VALUE, result, 0);
    }

    @Test
    public void should_return_zero_distance_for_same_coordinates() {
        // Given
        Double lat1 = SEOUL_LAT;
        Double lon1 = SEOUL_LON;
        Double lat2 = SEOUL_LAT;
        Double lon2 = SEOUL_LON;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        assertEquals(0.0f, result, DELTA);
    }

    @Test
    public void should_calculate_distance_between_seoul_and_busan() {
        // Given
        Double lat1 = SEOUL_LAT;
        Double lon1 = SEOUL_LON;
        Double lat2 = BUSAN_LAT;
        Double lon2 = BUSAN_LON;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        // Seoul to Busan is approximately 325km
        assertTrue("Distance should be around 325km", result > 300000 && result < 350000);
    }

    @Test
    public void should_calculate_short_distance_accurately() {
        // Given - Two points 100 meters apart (approximately)
        Double lat1 = 37.5665;
        Double lon1 = 126.9780;
        Double lat2 = 37.5675; // About 100m north
        Double lon2 = 126.9780;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        assertTrue("Distance should be around 100 meters", result > 90 && result < 150);
    }

    @Test
    public void should_handle_extreme_coordinates() {
        // Given - North Pole and South Pole
        Double lat1 = 90.0;
        Double lon1 = 0.0;
        Double lat2 = -90.0;
        Double lon2 = 0.0;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        assertTrue("Distance should be approximately half Earth's circumference", result > 19000000);
    }

    @Test
    public void should_return_false_when_outside_workplace_range() {
        // Given
        Double currentLat = SEOUL_LAT;
        Double currentLon = SEOUL_LON;
        Double workplaceLat = BUSAN_LAT;
        Double workplaceLon = BUSAN_LON;
        int maxDistanceMeters = 2000;

        // When
        boolean result = LocationCalculator.isWithinWorkplaceRange(
            currentLat, currentLon, workplaceLat, workplaceLon, maxDistanceMeters
        );

        // Then
        assertFalse("Seoul to Busan should be outside 2km range", result);
    }

    @Test
    public void should_return_true_when_within_workplace_range() {
        // Given
        Double currentLat = SEOUL_LAT;
        Double currentLon = SEOUL_LON;
        Double workplaceLat = SEOUL_LAT;
        Double workplaceLon = SEOUL_LON;
        int maxDistanceMeters = 2000;

        // When
        boolean result = LocationCalculator.isWithinWorkplaceRange(
            currentLat, currentLon, workplaceLat, workplaceLon, maxDistanceMeters
        );

        // Then
        assertTrue("Same location should be within range", result);
    }

    @Test
    public void should_return_false_when_coordinates_are_null_for_range_check() {
        // Given
        Double currentLat = null;
        Double currentLon = SEOUL_LON;
        Double workplaceLat = SEOUL_LAT;
        Double workplaceLon = SEOUL_LON;
        int maxDistanceMeters = 2000;

        // When
        boolean result = LocationCalculator.isWithinWorkplaceRange(
            currentLat, currentLon, workplaceLat, workplaceLon, maxDistanceMeters
        );

        // Then
        assertFalse("Null coordinates should return false", result);
    }

    @Test
    public void should_return_true_when_worked_8_hours() {
        // Given
        long clockInTime = System.currentTimeMillis() - (8 * 60 * 60 * 1000); // 8 hours ago
        long currentTime = System.currentTimeMillis();

        // When
        boolean result = LocationCalculator.hasWorkedMinimumHours(clockInTime, currentTime);

        // Then
        assertTrue("Should return true after 8 hours of work", result);
    }

    @Test
    public void should_return_true_when_worked_more_than_8_hours() {
        // Given
        long clockInTime = System.currentTimeMillis() - (10 * 60 * 60 * 1000); // 10 hours ago
        long currentTime = System.currentTimeMillis();

        // When
        boolean result = LocationCalculator.hasWorkedMinimumHours(clockInTime, currentTime);

        // Then
        assertTrue("Should return true after more than 8 hours of work", result);
    }

    @Test
    public void should_return_false_when_worked_less_than_8_hours() {
        // Given
        long clockInTime = System.currentTimeMillis() - (6 * 60 * 60 * 1000); // 6 hours ago
        long currentTime = System.currentTimeMillis();

        // When
        boolean result = LocationCalculator.hasWorkedMinimumHours(clockInTime, currentTime);

        // Then
        assertFalse("Should return false after less than 8 hours of work", result);
    }

    @Test
    public void should_return_false_when_clock_in_time_is_invalid() {
        // Given
        long clockInTime = 0;
        long currentTime = System.currentTimeMillis();

        // When
        boolean result = LocationCalculator.hasWorkedMinimumHours(clockInTime, currentTime);

        // Then
        assertFalse("Should return false for invalid clock-in time", result);
    }

    @Test
    public void should_return_false_when_current_time_is_before_clock_in() {
        // Given
        long clockInTime = System.currentTimeMillis();
        long currentTime = clockInTime - 1000; // 1 second before

        // When
        boolean result = LocationCalculator.hasWorkedMinimumHours(clockInTime, currentTime);

        // Then
        assertFalse("Should return false when current time is before clock-in", result);
    }

    @Test
    public void should_calculate_remaining_work_time_correctly() {
        // Given
        long clockInTime = System.currentTimeMillis() - (6 * 60 * 60 * 1000); // 6 hours ago
        long currentTime = System.currentTimeMillis();
        long expectedRemaining = 2 * 60 * 60 * 1000; // 2 hours remaining

        // When
        long result = LocationCalculator.calculateRemainingWorkTime(clockInTime, currentTime);

        // Then
        assertTrue("Should have approximately 2 hours remaining", 
            Math.abs(result - expectedRemaining) < 10000); // Allow 10 second tolerance
    }

    @Test
    public void should_return_zero_remaining_time_when_worked_enough() {
        // Given
        long clockInTime = System.currentTimeMillis() - (10 * 60 * 60 * 1000); // 10 hours ago
        long currentTime = System.currentTimeMillis();

        // When
        long result = LocationCalculator.calculateRemainingWorkTime(clockInTime, currentTime);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_return_full_work_duration_for_invalid_times() {
        // Given
        long clockInTime = 0;
        long currentTime = System.currentTimeMillis();

        // When
        long result = LocationCalculator.calculateRemainingWorkTime(clockInTime, currentTime);

        // Then
        assertEquals(LocationCalculator.getWorkDurationMs(), result);
    }

    @Test
    public void should_validate_coordinates_correctly() {
        // When & Then
        assertTrue("Valid coordinates should return true", 
            LocationCalculator.areValidCoordinates(37.5665, 126.9780));
        assertTrue("Boundary coordinates should be valid", 
            LocationCalculator.areValidCoordinates(90.0, 180.0));
        assertTrue("Negative coordinates should be valid", 
            LocationCalculator.areValidCoordinates(-90.0, -180.0));
        
        assertFalse("Null coordinates should be invalid", 
            LocationCalculator.areValidCoordinates(null, 126.9780));
        assertFalse("Out of range latitude should be invalid", 
            LocationCalculator.areValidCoordinates(91.0, 126.9780));
        assertFalse("Out of range longitude should be invalid", 
            LocationCalculator.areValidCoordinates(37.5665, 181.0));
        assertFalse("Negative out of range latitude should be invalid", 
            LocationCalculator.areValidCoordinates(-91.0, 126.9780));
        assertFalse("Negative out of range longitude should be invalid", 
            LocationCalculator.areValidCoordinates(37.5665, -181.0));
    }

    @Test
    public void should_return_correct_work_duration() {
        // When
        long duration = LocationCalculator.getWorkDurationMs();

        // Then
        assertEquals("Work duration should be 8 hours in milliseconds", 
            8 * 60 * 60 * 1000, duration);
    }

    @Test
    public void should_handle_edge_case_coordinates() {
        // Given - International Date Line
        Double lat1 = 0.0;
        Double lon1 = 179.0;
        Double lat2 = 0.0;
        Double lon2 = -179.0;

        // When
        float result = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Then
        assertTrue("Distance across date line should be calculated correctly", result > 0);
    }

    @Test
    public void should_be_symmetric_for_distance_calculation() {
        // Given
        Double lat1 = SEOUL_LAT;
        Double lon1 = SEOUL_LON;
        Double lat2 = BUSAN_LAT;
        Double lon2 = BUSAN_LON;

        // When
        float distance1 = LocationCalculator.calculateDistance(lat1, lon1, lat2, lon2);
        float distance2 = LocationCalculator.calculateDistance(lat2, lon2, lat1, lon1);

        // Then
        assertEquals("Distance calculation should be symmetric", distance1, distance2, DELTA);
    }
}