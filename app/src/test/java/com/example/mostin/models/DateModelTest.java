package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateModelTest {

    @Test
    public void should_generateCorrectDateFormat_when_isCurrentMonthTrue() {
        // Given
        int testDay = 15;
        String expectedClockIn = "09:00";
        String expectedClockOut = "18:00";
        
        Calendar expectedCal = Calendar.getInstance();
        expectedCal.set(Calendar.DAY_OF_MONTH, testDay);
        String expectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(expectedCal.getTime());

        // When
        DateModel dateModel = new DateModel(testDay, true, expectedClockIn, expectedClockOut);

        // Then
        assertEquals(testDay, dateModel.getDay());
        assertEquals(expectedDate, dateModel.getDate());
        assertEquals(expectedClockIn, dateModel.getClockInTime());
        assertEquals(expectedClockOut, dateModel.getClockOutTime());
        assertTrue(dateModel.isCurrentMonth());
    }

    @Test
    public void should_returnNullDate_when_isCurrentMonthFalse() {
        // Given
        int testDay = 10;
        String expectedClockIn = "08:30";
        String expectedClockOut = "17:30";

        // When
        DateModel dateModel = new DateModel(testDay, false, expectedClockIn, expectedClockOut);

        // Then
        assertEquals(testDay, dateModel.getDay());
        assertNull(dateModel.getDate());
        assertEquals(expectedClockIn, dateModel.getClockInTime());
        assertEquals(expectedClockOut, dateModel.getClockOutTime());
        assertFalse(dateModel.isCurrentMonth());
    }

    @Test
    public void should_generateCorrectDate_when_dayIsFirstOfMonth() {
        // Given - 월의 첫째 날 테스트
        int testDay = 1;
        
        Calendar expectedCal = Calendar.getInstance();
        expectedCal.set(Calendar.DAY_OF_MONTH, testDay);
        String expectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(expectedCal.getTime());

        // When
        DateModel dateModel = new DateModel(testDay, true, "09:00", "18:00");

        // Then
        assertEquals(expectedDate, dateModel.getDate());
        assertEquals(1, dateModel.getDay());
    }

    @Test
    public void should_generateCorrectDate_when_dayIsLastOfMonth() {
        // Given - 월의 마지막 날 테스트 (31일)
        int testDay = 31;
        
        Calendar expectedCal = Calendar.getInstance();
        expectedCal.set(Calendar.DAY_OF_MONTH, testDay);
        String expectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(expectedCal.getTime());

        // When
        DateModel dateModel = new DateModel(testDay, true, "09:00", "18:00");

        // Then
        assertEquals(expectedDate, dateModel.getDate());
        assertEquals(31, dateModel.getDay());
    }

    @Test
    public void should_handleNullTimes_when_clockTimesAreNull() {
        // Given
        int testDay = 20;

        // When
        DateModel dateModel = new DateModel(testDay, true, null, null);

        // Then
        assertNull(dateModel.getClockInTime());
        assertNull(dateModel.getClockOutTime());
        assertNotNull(dateModel.getDate()); // date는 생성되어야 함
    }

    @Test
    public void should_createModelWithStringConstructor_when_usingAlternativeConstructor() {
        // Given
        String expectedDate = "2024-03-15";
        String expectedClockIn = "08:45";

        // When
        DateModel dateModel = new DateModel(expectedDate, expectedClockIn);

        // Then
        assertEquals(expectedDate, dateModel.getDate());
        assertEquals(expectedClockIn, dateModel.getClockInTime());
        assertNull(dateModel.getClockOutTime()); // null로 설정됨
        assertEquals(0, dateModel.getDay()); // 초기화되지 않음
        assertFalse(dateModel.isCurrentMonth()); // 초기화되지 않음 (false)
    }

    @Test
    public void should_allowSetterModifications_when_usingSetters() {
        // Given
        DateModel dateModel = new DateModel(15, true, "09:00", "18:00");
        String newDate = "2024-12-25";
        int newDay = 25;
        String newClockIn = "08:00";
        String newClockOut = "19:00";

        // When
        dateModel.setDate(newDate);
        dateModel.setDay(newDay);
        dateModel.setClockInTime(newClockIn);
        dateModel.setClockOutTime(newClockOut);
        dateModel.setCurrentMonth(false);

        // Then
        assertEquals(newDate, dateModel.getDate());
        assertEquals(newDay, dateModel.getDay());
        assertEquals(newClockIn, dateModel.getClockInTime());
        assertEquals(newClockOut, dateModel.getClockOutTime());
        assertFalse(dateModel.isCurrentMonth());
    }

    @Test
    public void should_maintainDateFormat_when_currentMonthIsTrue() {
        // Given - 다양한 날짜로 형식 검증
        int[] testDays = {5, 15, 28};
        String datePattern = "\\d{4}-\\d{2}-\\d{2}"; // yyyy-MM-dd 패턴

        for (int day : testDays) {
            // When
            DateModel dateModel = new DateModel(day, true, "09:00", "18:00");

            // Then
            assertNotNull("Date should not be null for day " + day, dateModel.getDate());
            assertTrue("Date format should match yyyy-MM-dd for day " + day, 
                    dateModel.getDate().matches(datePattern));
        }
    }
}