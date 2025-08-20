package com.example.mostin.adapters;

import static org.junit.Assert.*;

import com.example.mostin.models.DateModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class CalendarAdapterTest {

    private CalendarAdapter adapter;
    private List<DateModel> mockDateList;

    @Before
    public void setUp() {
        mockDateList = new ArrayList<>();
        mockDateList.add(new DateModel(1, true, "09:00:00", "18:00:00"));
        mockDateList.add(new DateModel(2, true, "08:30:00", null));
        mockDateList.add(new DateModel(3, true, null, null));
        
        adapter = new CalendarAdapter(mockDateList);
    }

    @Test
    public void should_returnCorrectAttendanceInfo_when_bothTimesAvailable() throws Exception {
        // Given
        DateModel dateWithBothTimes = new DateModel(15, true, "09:30:45", "18:15:30");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithBothTimes);
        
        // Then
        String expected = "출근시간 - 09:30\n퇴근시간 - 18:15";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_returnOnlyClockInInfo_when_onlyClockInTimeAvailable() throws Exception {
        // Given
        DateModel dateWithClockInOnly = new DateModel(10, true, "08:45:00", null);
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithClockInOnly);
        
        // Then
        String expected = "출근시간 - 08:45";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_returnNoAttendanceMessage_when_noClockInTime() throws Exception {
        // Given
        DateModel dateWithNoAttendance = new DateModel(5, true, null, null);
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithNoAttendance);
        
        // Then
        assertEquals("출근하지 않았습니다", attendanceInfo);
    }

    @Test
    public void should_returnCorrectItemCount_when_adapterCreatedWithList() {
        // When & Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_adapterCreatedWithEmptyList() {
        // Given
        CalendarAdapter emptyAdapter = new CalendarAdapter(new ArrayList<>());
        
        // When & Then
        assertEquals(0, emptyAdapter.getItemCount());
    }

    @Test
    public void should_handleShortTimeString_when_timeStringLessThan5Characters() throws Exception {
        // Given
        DateModel dateWithShortTime = new DateModel(20, true, "9:30", "18");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithShortTime);
        
        // Then - Should still handle gracefully
        assertEquals("출근하지 않았습니다", attendanceInfo); // Because clockIn time is less than 5 chars
    }

    @Test
    public void should_handleExactly5CharacterTime_when_timeStringExactly5Characters() throws Exception {
        // Given
        DateModel dateWithExactTime = new DateModel(25, true, "09:30", "18:15");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithExactTime);
        
        // Then
        String expected = "출근시간 - 09:30\n퇴근시간 - 18:15";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_handleLongTimeString_when_timeStringMoreThan5Characters() throws Exception {
        // Given
        DateModel dateWithLongTime = new DateModel(30, true, "09:30:45.123", "18:15:30.456");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithLongTime);
        
        // Then
        String expected = "출근시간 - 09:30\n퇴근시간 - 18:15";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_handleNullClockOutTime_when_clockOutTimeIsNull() throws Exception {
        // Given
        DateModel dateWithNullClockOut = new DateModel(12, true, "09:00:00", null);
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithNullClockOut);
        
        // Then
        String expected = "출근시간 - 09:00";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_handleEmptyClockInTime_when_clockInTimeIsEmpty() throws Exception {
        // Given
        DateModel dateWithEmptyClockIn = new DateModel(8, true, "", "18:00:00");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithEmptyClockIn);
        
        // Then
        assertEquals("출근하지 않았습니다", attendanceInfo);
    }

    @Test
    public void should_handleEmptyClockOutTime_when_clockOutTimeIsEmpty() throws Exception {
        // Given
        DateModel dateWithEmptyClockOut = new DateModel(18, true, "09:00:00", "");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithEmptyClockOut);
        
        // Then
        String expected = "출근시간 - 09:00";
        assertEquals(expected, attendanceInfo);
    }

    @Test
    public void should_buildCorrectAttendanceInfo_when_bothTimesHaveExtraCharacters() throws Exception {
        // Given
        DateModel dateWithExtraChars = new DateModel(28, true, "09:45:30:EXTRA", "17:30:45:MORE");
        
        // When
        String attendanceInfo = getAttendanceInfo(dateWithExtraChars);
        
        // Then
        String expected = "출근시간 - 09:45\n퇴근시간 - 17:30";
        assertEquals(expected, attendanceInfo);
    }

    /**
     * Helper method to access private getAttendanceInfo method using reflection
     */
    private String getAttendanceInfo(DateModel date) {
        try {
            Method method = CalendarAdapter.class.getDeclaredMethod("getAttendanceInfo", DateModel.class);
            method.setAccessible(true);
            return (String) method.invoke(adapter, date);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke getAttendanceInfo method", e);
        }
    }
}