package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class AttendanceRecordModelTest {

    @Test
    public void should_createAttendanceRecord_when_validDataProvided() {
        // Given
        String expectedDate = "2024-03-15";
        String expectedEmployeeId = "EMP001";
        String expectedEmployeeName = "김철수";
        String expectedClockIn = "09:00:00";
        String expectedClockOut = "18:00:00";
        String expectedWorkPlace = "서울지점";

        // When
        AttendanceRecordModel record = new AttendanceRecordModel(
            expectedDate, expectedEmployeeId, expectedEmployeeName,
            expectedClockIn, expectedClockOut, expectedWorkPlace
        );

        // Then
        assertEquals(expectedDate, record.getDate());
        assertEquals(expectedEmployeeId, record.getEmployeeId());
        assertEquals(expectedEmployeeName, record.getEmployeeName());
        assertEquals(expectedClockIn, record.getClockIn());
        assertEquals(expectedClockOut, record.getClockOut());
        assertEquals(expectedWorkPlace, record.getWorkPlace());
    }

    @Test
    public void should_handleNullValues_when_nullDataProvided() {
        // Given & When
        AttendanceRecordModel record = new AttendanceRecordModel(
            null, null, null, null, null, null
        );

        // Then
        assertNull(record.getDate());
        assertNull(record.getEmployeeId());
        assertNull(record.getEmployeeName());
        assertNull(record.getClockIn());
        assertNull(record.getClockOut());
        assertNull(record.getWorkPlace());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvided() {
        // Given & When
        AttendanceRecordModel record = new AttendanceRecordModel(
            "", "", "", "", "", ""
        );

        // Then
        assertEquals("", record.getDate());
        assertEquals("", record.getEmployeeId());
        assertEquals("", record.getEmployeeName());
        assertEquals("", record.getClockIn());
        assertEquals("", record.getClockOut());
        assertEquals("", record.getWorkPlace());
    }

    @Test
    public void should_preserveSpecialCharacters_when_specialCharsInData() {
        // Given
        String date = "2024-12-25";
        String employeeId = "EMP@001";
        String employeeName = "김철수#";
        String clockIn = "08:30:45";
        String clockOut = "17:45:30";
        String workPlace = "서울-강남지점";

        // When
        AttendanceRecordModel record = new AttendanceRecordModel(
            date, employeeId, employeeName, clockIn, clockOut, workPlace
        );

        // Then
        assertEquals("2024-12-25", record.getDate());
        assertEquals("EMP@001", record.getEmployeeId());
        assertEquals("김철수#", record.getEmployeeName());
        assertEquals("08:30:45", record.getClockIn());
        assertEquals("17:45:30", record.getClockOut());
        assertEquals("서울-강남지점", record.getWorkPlace());
    }

    @Test
    public void should_handlePartialClockOut_when_clockOutIsNull() {
        // Given - 출근은 했지만 퇴근 기록이 없는 경우
        AttendanceRecordModel record = new AttendanceRecordModel(
            "2024-03-15", "EMP001", "김철수", "09:00:00", null, "서울지점"
        );

        // Then
        assertEquals("2024-03-15", record.getDate());
        assertEquals("EMP001", record.getEmployeeId());
        assertEquals("김철수", record.getEmployeeName());
        assertEquals("09:00:00", record.getClockIn());
        assertNull(record.getClockOut());
        assertEquals("서울지점", record.getWorkPlace());
    }

    @Test
    public void should_handleLongWorkPlaceName_when_longNameProvided() {
        // Given
        String longWorkPlace = "서울특별시 강남구 테헤란로 123길 45-67 ABC빌딩 5층 개발팀";
        
        // When
        AttendanceRecordModel record = new AttendanceRecordModel(
            "2024-03-15", "EMP001", "김철수", "09:00:00", "18:00:00", longWorkPlace
        );

        // Then
        assertEquals(longWorkPlace, record.getWorkPlace());
    }
}