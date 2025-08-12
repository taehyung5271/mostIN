package com.example.mostin.utils;

import com.example.mostin.models.AttendanceRecordModel;

import java.util.List;
import java.util.Locale;

/**
 * Utility class for attendance-related calculations
 * Extracted from AttendanceManagementFragment for better testability
 */
public class AttendanceCalculator {

    /**
     * Calculates attendance summary statistics
     * @param records List of attendance records
     * @return Formatted summary string containing total days, complete days, and incomplete days
     */
    public static String calculateAttendanceSummary(List<AttendanceRecordModel> records) {
        if (records == null) {
            return "총 출근일: 0일\n정상 출퇴근: 0일\n미완료: 0일";
        }

        int totalDays = records.size();
        int completeDays = 0;
        int incompleteDays = 0;

        for (AttendanceRecordModel record : records) {
            if (record.getClockOut() != null && !record.getClockOut().trim().isEmpty()) {
                completeDays++;
            } else {
                incompleteDays++;
            }
        }

        return String.format(Locale.getDefault(),
                "총 출근일: %d일\n정상 출퇴근: %d일\n미완료: %d일",
                totalDays, completeDays, incompleteDays);
    }

    /**
     * Counts complete attendance records (those with both clock-in and clock-out times)
     * @param records List of attendance records
     * @return Number of complete attendance records
     */
    public static int countCompleteAttendance(List<AttendanceRecordModel> records) {
        if (records == null) {
            return 0;
        }

        int completeDays = 0;
        for (AttendanceRecordModel record : records) {
            if (record.getClockOut() != null && !record.getClockOut().trim().isEmpty()) {
                completeDays++;
            }
        }
        return completeDays;
    }

    /**
     * Counts incomplete attendance records (those without clock-out times)
     * @param records List of attendance records
     * @return Number of incomplete attendance records
     */
    public static int countIncompleteAttendance(List<AttendanceRecordModel> records) {
        if (records == null) {
            return 0;
        }

        int incompleteDays = 0;
        for (AttendanceRecordModel record : records) {
            if (record.getClockOut() == null || record.getClockOut().trim().isEmpty()) {
                incompleteDays++;
            }
        }
        return incompleteDays;
    }
}