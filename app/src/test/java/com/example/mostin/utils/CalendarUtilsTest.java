package com.example.mostin.utils;

import com.example.mostin.models.DateModel;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

public class CalendarUtilsTest {

    @Test
    public void should_generate42Dates_when_generateMonthlyCalendarCalled() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 15); // January 2024
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(calendar);
        
        assertEquals(42, result.size()); // Standard calendar grid is 6 weeks * 7 days = 42
    }

    @Test
    public void should_includeCurrentMonthDates_when_generateMonthlyCalendarCalled() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 15); // January 2024
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(calendar);
        
        // Count current month dates
        long currentMonthDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(31, currentMonthDates); // January has 31 days
    }

    @Test
    public void should_includePreviousMonthDates_when_monthStartsNotOnSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 1); // February 1st, 2024 is a Thursday
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(calendar);
        
        // Count previous month dates (should be 4 dates for Thursday start)
        long prevMonthDates = result.stream()
                .limit(4) // First 4 should be from previous month
                .filter(date -> !date.isCurrentMonth())
                .count();
        
        assertEquals(4, prevMonthDates);
    }

    @Test
    public void should_includeNextMonthDates_when_calendarGridNotFull() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 15); // February 2024 (29 days, leap year)
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(calendar);
        
        // Count next month dates
        long nextMonthDates = result.stream()
                .filter(date -> !date.isCurrentMonth())
                .filter(date -> date.getDay() <= 10) // Next month dates are typically 1-10
                .count();
        
        assertTrue(nextMonthDates > 0);
    }

    @Test
    public void should_handleLeapYear_when_generateMonthlyCalendarCalledForFebruary() {
        Calendar leapYearCalendar = Calendar.getInstance();
        leapYearCalendar.set(2024, Calendar.FEBRUARY, 15); // 2024 is a leap year
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(leapYearCalendar);
        
        // Count February dates in leap year
        long februaryDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(29, februaryDates); // February 2024 has 29 days
    }

    @Test
    public void should_handleNonLeapYear_when_generateMonthlyCalendarCalledForFebruary() {
        Calendar nonLeapYearCalendar = Calendar.getInstance();
        nonLeapYearCalendar.set(2023, Calendar.FEBRUARY, 15); // 2023 is not a leap year
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(nonLeapYearCalendar);
        
        // Count February dates in non-leap year
        long februaryDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(28, februaryDates); // February 2023 has 28 days
    }

    @Test
    public void should_generate42Dates_when_generateCalendarMonthCalled() {
        List<DateModel> result = CalendarUtils.generateCalendarMonth(2024, 6); // June 2024
        
        assertEquals(42, result.size());
    }

    @Test
    public void should_generateCorrectMonthDates_when_generateCalendarMonthCalled() {
        List<DateModel> result = CalendarUtils.generateCalendarMonth(2024, 6); // June 2024
        
        // Count June dates
        long juneDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(30, juneDates); // June has 30 days
    }

    @Test
    public void should_handleDecember_when_generateCalendarMonthCalled() {
        List<DateModel> result = CalendarUtils.generateCalendarMonth(2024, 12); // December 2024
        
        // Count December dates
        long decemberDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(31, decemberDates); // December has 31 days
    }

    @Test
    public void should_handleJanuary_when_generateCalendarMonthCalled() {
        List<DateModel> result = CalendarUtils.generateCalendarMonth(2024, 1); // January 2024
        
        // Count January dates
        long januaryDates = result.stream()
                .filter(DateModel::isCurrentMonth)
                .count();
        
        assertEquals(31, januaryDates); // January has 31 days
    }

    @Test
    public void should_maintainOriginalCalendar_when_generateMonthlyCalendarCalled() {
        Calendar originalCalendar = Calendar.getInstance();
        originalCalendar.set(2024, Calendar.MAY, 15);
        int originalDay = originalCalendar.get(Calendar.DAY_OF_MONTH);
        int originalMonth = originalCalendar.get(Calendar.MONTH);
        int originalYear = originalCalendar.get(Calendar.YEAR);
        
        CalendarUtils.generateMonthlyCalendar(originalCalendar);
        
        // Verify original calendar is unchanged
        assertEquals(originalDay, originalCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(originalMonth, originalCalendar.get(Calendar.MONTH));
        assertEquals(originalYear, originalCalendar.get(Calendar.YEAR));
    }

    @Test
    public void should_generateSequentialDates_when_generateMonthlyCalendarCalled() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15); // March 2024
        
        List<DateModel> result = CalendarUtils.generateMonthlyCalendar(calendar);
        
        // Check that current month dates are in sequence from 1 to 31
        List<Integer> currentMonthDays = result.stream()
                .filter(DateModel::isCurrentMonth)
                .map(DateModel::getDay)
                .sorted()
                .toList();
        
        for (int i = 0; i < currentMonthDays.size(); i++) {
            assertEquals(Integer.valueOf(i + 1), currentMonthDays.get(i));
        }
    }
}