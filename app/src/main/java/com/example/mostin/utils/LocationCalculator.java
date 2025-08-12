package com.example.mostin.utils;

import android.location.Location;

/**
 * Utility class for location and commuting-related calculations
 * Extracted from CommutingRegistrationFragment for better testability
 */
public class LocationCalculator {

    private static final long WORK_DURATION_MS = 28800000; // 8 hours in milliseconds

    /**
     * Calculates distance between two geographical points
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point  
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance in meters, or Float.MAX_VALUE if any coordinate is null
     */
    public static float calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            return Float.MAX_VALUE;
        }

        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);
        
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);
        
        return loc1.distanceTo(loc2);
    }

    /**
     * Checks if current location is within allowed distance from workplace
     * @param currentLat Current latitude
     * @param currentLon Current longitude
     * @param workplaceLat Workplace latitude
     * @param workplaceLon Workplace longitude
     * @param maxDistanceMeters Maximum allowed distance in meters
     * @return true if within range, false otherwise
     */
    public static boolean isWithinWorkplaceRange(Double currentLat, Double currentLon, 
                                               Double workplaceLat, Double workplaceLon, 
                                               int maxDistanceMeters) {
        float distance = calculateDistance(currentLat, currentLon, workplaceLat, workplaceLon);
        return distance != Float.MAX_VALUE && distance <= maxDistanceMeters;
    }

    /**
     * Checks if employee has worked for minimum required duration (8 hours)
     * @param clockInTimeMs Clock-in time in milliseconds
     * @param currentTimeMs Current time in milliseconds
     * @return true if worked for 8+ hours, false otherwise
     */
    public static boolean hasWorkedMinimumHours(long clockInTimeMs, long currentTimeMs) {
        if (clockInTimeMs <= 0 || currentTimeMs <= 0 || currentTimeMs < clockInTimeMs) {
            return false;
        }
        
        long workedTime = currentTimeMs - clockInTimeMs;
        return workedTime >= WORK_DURATION_MS;
    }

    /**
     * Calculates remaining work time in milliseconds
     * @param clockInTimeMs Clock-in time in milliseconds
     * @param currentTimeMs Current time in milliseconds
     * @return Remaining time in milliseconds, or 0 if already worked enough
     */
    public static long calculateRemainingWorkTime(long clockInTimeMs, long currentTimeMs) {
        if (clockInTimeMs <= 0 || currentTimeMs <= 0 || currentTimeMs < clockInTimeMs) {
            return WORK_DURATION_MS;
        }
        
        long workedTime = currentTimeMs - clockInTimeMs;
        return Math.max(0, WORK_DURATION_MS - workedTime);
    }

    /**
     * Validates coordinate values for reasonable geographical bounds
     * @param latitude Latitude to validate
     * @param longitude Longitude to validate
     * @return true if coordinates are within valid range, false otherwise
     */
    public static boolean areValidCoordinates(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return false;
        }
        
        return latitude >= -90.0 && latitude <= 90.0 && 
               longitude >= -180.0 && longitude <= 180.0;
    }

    /**
     * Gets the work duration requirement in milliseconds
     * @return Work duration in milliseconds (8 hours)
     */
    public static long getWorkDurationMs() {
        return WORK_DURATION_MS;
    }
}