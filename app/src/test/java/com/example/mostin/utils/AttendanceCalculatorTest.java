package com.example.mostin.utils;

import static org.junit.Assert.*;

import com.example.mostin.models.AttendanceRecordModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for AttendanceCalculator utility class
 */
public class AttendanceCalculatorTest {

    @Test
    public void should_return_zero_summary_when_records_are_null() {
        // Given
        List<AttendanceRecordModel> records = null;

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 0일\n정상 출퇴근: 0일\n미완료: 0일", result);
    }

    @Test
    public void should_return_zero_summary_when_records_are_empty() {
        // Given
        List<AttendanceRecordModel> records = new ArrayList<>();

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 0일\n정상 출퇴근: 0일\n미완료: 0일", result);
    }

    @Test
    public void should_calculate_correct_summary_when_all_records_are_complete() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "18:00", "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", "17:45", "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "18:30", "본사")
        );

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 3일\n정상 출퇴근: 3일\n미완료: 0일", result);
    }

    @Test
    public void should_calculate_correct_summary_when_all_records_are_incomplete() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", null, "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", "", "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "  ", "본사")
        );

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 3일\n정상 출퇴근: 0일\n미완료: 3일", result);
    }

    @Test
    public void should_calculate_correct_summary_when_records_are_mixed() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "18:00", "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", null, "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "", "본사"),
            new AttendanceRecordModel("2024-01-04", "emp1", "김직원", "09:00", "17:30", "본사"),
            new AttendanceRecordModel("2024-01-05", "emp1", "김직원", "09:10", "18:15", "본사")
        );

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 5일\n정상 출퇴근: 3일\n미완료: 2일", result);
    }

    @Test
    public void should_treat_empty_string_clockout_as_incomplete() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "", "본사")
        );

        // When
        String result = AttendanceCalculator.calculateAttendanceSummary(records);

        // Then
        assertEquals("총 출근일: 1일\n정상 출퇴근: 0일\n미완료: 1일", result);
    }

    @Test
    public void should_count_zero_complete_attendance_when_records_are_null() {
        // Given
        List<AttendanceRecordModel> records = null;

        // When
        int result = AttendanceCalculator.countCompleteAttendance(records);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_count_zero_complete_attendance_when_records_are_empty() {
        // Given
        List<AttendanceRecordModel> records = new ArrayList<>();

        // When
        int result = AttendanceCalculator.countCompleteAttendance(records);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_count_complete_attendance_correctly() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "18:00", "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", null, "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "17:30", "본사")
        );

        // When
        int result = AttendanceCalculator.countCompleteAttendance(records);

        // Then
        assertEquals(2, result);
    }

    @Test
    public void should_count_zero_incomplete_attendance_when_records_are_null() {
        // Given
        List<AttendanceRecordModel> records = null;

        // When
        int result = AttendanceCalculator.countIncompleteAttendance(records);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_count_zero_incomplete_attendance_when_records_are_empty() {
        // Given
        List<AttendanceRecordModel> records = new ArrayList<>();

        // When
        int result = AttendanceCalculator.countIncompleteAttendance(records);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_count_incomplete_attendance_correctly() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "18:00", "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", null, "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "", "본사"),
            new AttendanceRecordModel("2024-01-04", "emp1", "김직원", "09:00", "17:30", "본사")
        );

        // When
        int result = AttendanceCalculator.countIncompleteAttendance(records);

        // Then
        assertEquals(2, result);
    }

    @Test
    public void should_handle_whitespace_clockout_as_incomplete() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "   ", "본사")
        );

        // When
        int incomplete = AttendanceCalculator.countIncompleteAttendance(records);
        int complete = AttendanceCalculator.countCompleteAttendance(records);

        // Then
        assertEquals(1, incomplete); // Whitespace should be treated as incomplete
        assertEquals(0, complete);
    }

    @Test
    public void should_handle_large_dataset_efficiently() {
        // Given
        List<AttendanceRecordModel> records = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            String clockOut = (i % 3 == 0) ? null : "18:00"; // Every 3rd record is incomplete
            records.add(new AttendanceRecordModel(
                String.format("2024-01-%02d", i % 30 + 1),
                "emp1", "김직원", "09:00", clockOut, "본사"
            ));
        }

        // When
        long startTime = System.currentTimeMillis();
        String summary = AttendanceCalculator.calculateAttendanceSummary(records);
        long endTime = System.currentTimeMillis();

        // Then
        assertTrue("Should complete within reasonable time", (endTime - startTime) < 1000);
        assertEquals("총 출근일: 1000일\n정상 출퇴근: 667일\n미완료: 333일", summary);
    }

    @Test
    public void should_validate_complete_plus_incomplete_equals_total() {
        // Given
        List<AttendanceRecordModel> records = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "emp1", "김직원", "09:00", "18:00", "본사"),
            new AttendanceRecordModel("2024-01-02", "emp1", "김직원", "09:15", null, "본사"),
            new AttendanceRecordModel("2024-01-03", "emp1", "김직원", "08:45", "", "본사"),
            new AttendanceRecordModel("2024-01-04", "emp1", "김직원", "09:00", "17:30", "본사"),
            new AttendanceRecordModel("2024-01-05", "emp1", "김직원", "09:10", null, "본사")
        );

        // When
        int complete = AttendanceCalculator.countCompleteAttendance(records);
        int incomplete = AttendanceCalculator.countIncompleteAttendance(records);
        int total = records.size();

        // Then
        assertEquals("Complete + Incomplete should equal Total", total, complete + incomplete);
    }
}