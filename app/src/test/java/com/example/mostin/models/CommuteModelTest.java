package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class CommuteModelTest {

    @Test
    public void should_createCommuteModel_when_validDataProvided() {
        // Given
        String expectedCommuteDay = "2024-03-15";
        String expectedEmployeeId = "EMP001";
        String expectedEmployeeName = "김철수";
        String expectedStartTime = "09:00:00";
        String expectedEndTime = "18:00:00";
        String expectedWorkPlaceName = "서울지점";

        // When
        CommuteModel commute = new CommuteModel(
            expectedCommuteDay, expectedEmployeeId, expectedEmployeeName,
            expectedStartTime, expectedEndTime, expectedWorkPlaceName
        );

        // Then
        assertEquals(expectedCommuteDay, commute.getCommuteDay());
        assertEquals(expectedEmployeeId, commute.getEmployeeId());
        assertEquals(expectedEmployeeName, commute.getEmployeeName());
        assertEquals(expectedStartTime, commute.getStartTime());
        assertEquals(expectedEndTime, commute.getEndTime());
        assertEquals(expectedWorkPlaceName, commute.getWorkPlaceName());
    }

    @Test
    public void should_allowSetterModifications_when_usingSetters() {
        // Given
        CommuteModel commute = new CommuteModel(
            "2024-03-15", "EMP001", "김철수", "09:00:00", "18:00:00", "서울지점"
        );

        String newCommuteDay = "2024-03-16";
        String newEmployeeId = "EMP002";
        String newEmployeeName = "이영희";
        String newStartTime = "08:30:00";
        String newEndTime = "17:30:00";
        String newWorkPlaceName = "부산지점";

        // When
        commute.setCommuteDay(newCommuteDay);
        commute.setEmployeeId(newEmployeeId);
        commute.setEmployeeName(newEmployeeName);
        commute.setStartTime(newStartTime);
        commute.setEndTime(newEndTime);
        commute.setWorkPlaceName(newWorkPlaceName);

        // Then
        assertEquals(newCommuteDay, commute.getCommuteDay());
        assertEquals(newEmployeeId, commute.getEmployeeId());
        assertEquals(newEmployeeName, commute.getEmployeeName());
        assertEquals(newStartTime, commute.getStartTime());
        assertEquals(newEndTime, commute.getEndTime());
        assertEquals(newWorkPlaceName, commute.getWorkPlaceName());
    }

    @Test
    public void should_handleNullValues_when_nullDataProvided() {
        // Given & When
        CommuteModel commute = new CommuteModel(null, null, null, null, null, null);

        // Then
        assertNull(commute.getCommuteDay());
        assertNull(commute.getEmployeeId());
        assertNull(commute.getEmployeeName());
        assertNull(commute.getStartTime());
        assertNull(commute.getEndTime());
        assertNull(commute.getWorkPlaceName());
    }

    @Test
    public void should_handleNullSetters_when_settingNullValues() {
        // Given
        CommuteModel commute = new CommuteModel(
            "2024-03-15", "EMP001", "김철수", "09:00:00", "18:00:00", "서울지점"
        );

        // When
        commute.setCommuteDay(null);
        commute.setEmployeeId(null);
        commute.setEmployeeName(null);
        commute.setStartTime(null);
        commute.setEndTime(null);
        commute.setWorkPlaceName(null);

        // Then
        assertNull(commute.getCommuteDay());
        assertNull(commute.getEmployeeId());
        assertNull(commute.getEmployeeName());
        assertNull(commute.getStartTime());
        assertNull(commute.getEndTime());
        assertNull(commute.getWorkPlaceName());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvided() {
        // Given & When
        CommuteModel commute = new CommuteModel("", "", "", "", "", "");

        // Then
        assertEquals("", commute.getCommuteDay());
        assertEquals("", commute.getEmployeeId());
        assertEquals("", commute.getEmployeeName());
        assertEquals("", commute.getStartTime());
        assertEquals("", commute.getEndTime());
        assertEquals("", commute.getWorkPlaceName());
    }

    @Test
    public void should_handlePartialEndTime_when_endTimeIsNull() {
        // Given - 출근 기록만 있고 퇴근 기록이 없는 경우
        CommuteModel commute = new CommuteModel(
            "2024-03-15", "EMP001", "김철수", "09:00:00", null, "서울지점"
        );

        // Then
        assertEquals("2024-03-15", commute.getCommuteDay());
        assertEquals("EMP001", commute.getEmployeeId());
        assertEquals("김철수", commute.getEmployeeName());
        assertEquals("09:00:00", commute.getStartTime());
        assertNull(commute.getEndTime());
        assertEquals("서울지점", commute.getWorkPlaceName());
    }
}