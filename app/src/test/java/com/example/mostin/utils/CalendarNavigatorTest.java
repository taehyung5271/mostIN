package com.example.mostin.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for CalendarNavigator utility class
 */
public class CalendarNavigatorTest {

    @Test
    public void should_move_to_next_month_within_year() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, 1);

        // Then
        assertEquals("Year should remain same", 2024, result[0]);
        assertEquals("Month should be July", 7, result[1]);
    }

    @Test
    public void should_move_to_previous_month_within_year() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, -1);

        // Then
        assertEquals("Year should remain same", 2024, result[0]);
        assertEquals("Month should be May", 5, result[1]);
    }

    @Test
    public void should_move_to_next_year_when_december_to_january() {
        // Given
        int currentYear = 2024;
        int currentMonth = 12; // December

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, 1);

        // Then
        assertEquals("Year should increment", 2025, result[0]);
        assertEquals("Month should be January", 1, result[1]);
    }

    @Test
    public void should_move_to_previous_year_when_january_to_december() {
        // Given
        int currentYear = 2024;
        int currentMonth = 1; // January

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, -1);

        // Then
        assertEquals("Year should decrement", 2023, result[0]);
        assertEquals("Month should be December", 12, result[1]);
    }

    @Test
    public void should_handle_multiple_month_offset() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, 8);

        // Then
        assertEquals("Year should increment", 2025, result[0]);
        assertEquals("Month should be February", 2, result[1]);
    }

    @Test
    public void should_handle_negative_multiple_month_offset() {
        // Given
        int currentYear = 2024;
        int currentMonth = 3; // March

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, -8);

        // Then
        assertEquals("Year should decrement", 2023, result[0]);
        assertEquals("Month should be July", 7, result[1]);
    }

    @Test
    public void should_handle_zero_offset() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.changeMonth(currentYear, currentMonth, 0);

        // Then
        assertEquals("Year should remain same", 2024, result[0]);
        assertEquals("Month should remain same", 6, result[1]);
    }

    @Test
    public void should_use_previous_month_helper() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.previousMonth(currentYear, currentMonth);

        // Then
        assertEquals("Year should remain same", 2024, result[0]);
        assertEquals("Month should be May", 5, result[1]);
    }

    @Test
    public void should_use_next_month_helper() {
        // Given
        int currentYear = 2024;
        int currentMonth = 6; // June

        // When
        int[] result = CalendarNavigator.nextMonth(currentYear, currentMonth);

        // Then
        assertEquals("Year should remain same", 2024, result[0]);
        assertEquals("Month should be July", 7, result[1]);
    }

    @Test
    public void should_validate_reasonable_dates() {
        // When & Then
        assertTrue("2024-06 should be valid", CalendarNavigator.isValidDate(2024, 6));
        assertTrue("2000-01 should be valid", CalendarNavigator.isValidDate(2000, 1));
        assertTrue("2999-12 should be valid", CalendarNavigator.isValidDate(2999, 12));
        
        assertFalse("1800 should be invalid", CalendarNavigator.isValidDate(1800, 6));
        assertFalse("3100 should be invalid", CalendarNavigator.isValidDate(3100, 6));
        assertFalse("Month 0 should be invalid", CalendarNavigator.isValidDate(2024, 0));
        assertFalse("Month 13 should be invalid", CalendarNavigator.isValidDate(2024, 13));
        assertFalse("Negative month should be invalid", CalendarNavigator.isValidDate(2024, -1));
    }

    @Test
    public void should_format_month_display_correctly() {
        // When & Then
        assertEquals("2024년 6월", CalendarNavigator.formatMonthDisplay(2024, 6));
        assertEquals("2024년 1월", CalendarNavigator.formatMonthDisplay(2024, 1));
        assertEquals("2024년 12월", CalendarNavigator.formatMonthDisplay(2024, 12));
    }

    @Test
    public void should_return_invalid_date_for_bad_format_input() {
        // When & Then
        assertEquals("Invalid Date", CalendarNavigator.formatMonthDisplay(1800, 6));
        assertEquals("Invalid Date", CalendarNavigator.formatMonthDisplay(2024, 0));
        assertEquals("Invalid Date", CalendarNavigator.formatMonthDisplay(2024, 13));
    }

    @Test
    public void should_get_correct_days_in_month() {
        // When & Then
        assertEquals("January should have 31 days", 31, CalendarNavigator.getDaysInMonth(2024, 1));
        assertEquals("February should have 29 days in 2024 (leap year)", 29, CalendarNavigator.getDaysInMonth(2024, 2));
        assertEquals("February should have 28 days in 2023", 28, CalendarNavigator.getDaysInMonth(2023, 2));
        assertEquals("April should have 30 days", 30, CalendarNavigator.getDaysInMonth(2024, 4));
        assertEquals("December should have 31 days", 31, CalendarNavigator.getDaysInMonth(2024, 12));
    }

    @Test
    public void should_return_negative_one_for_invalid_date_in_days_count() {
        // When & Then
        assertEquals(-1, CalendarNavigator.getDaysInMonth(1800, 6));
        assertEquals(-1, CalendarNavigator.getDaysInMonth(2024, 0));
        assertEquals(-1, CalendarNavigator.getDaysInMonth(2024, 13));
    }

    @Test
    public void should_identify_leap_years_correctly() {
        // When & Then
        assertTrue("2024 should be leap year", CalendarNavigator.isLeapYear(2024));
        assertTrue("2000 should be leap year", CalendarNavigator.isLeapYear(2000));
        assertFalse("2023 should not be leap year", CalendarNavigator.isLeapYear(2023));
        assertFalse("1900 should not be leap year", CalendarNavigator.isLeapYear(1900));
        assertTrue("2400 should be leap year", CalendarNavigator.isLeapYear(2400));
    }

    @Test
    public void should_calculate_month_difference_correctly() {
        // When & Then
        assertEquals("Same month should have 0 difference", 0, 
            CalendarNavigator.getMonthDifference(2024, 6, 2024, 6));
        assertEquals("One month later should have 1 difference", 1, 
            CalendarNavigator.getMonthDifference(2024, 6, 2024, 7));
        assertEquals("One month earlier should have -1 difference", -1, 
            CalendarNavigator.getMonthDifference(2024, 7, 2024, 6));
        assertEquals("One year later should have 12 difference", 12, 
            CalendarNavigator.getMonthDifference(2023, 6, 2024, 6));
        assertEquals("One year earlier should have -12 difference", -12, 
            CalendarNavigator.getMonthDifference(2024, 6, 2023, 6));
        assertEquals("Cross year should work", 1, 
            CalendarNavigator.getMonthDifference(2023, 12, 2024, 1));
    }

    @Test
    public void should_return_zero_for_invalid_dates_in_month_difference() {
        // When & Then
        assertEquals(0, CalendarNavigator.getMonthDifference(1800, 6, 2024, 6));
        assertEquals(0, CalendarNavigator.getMonthDifference(2024, 0, 2024, 6));
        assertEquals(0, CalendarNavigator.getMonthDifference(2024, 6, 2024, 13));
    }

    @Test
    public void should_get_current_year_and_month() {
        // When
        int[] current = CalendarNavigator.getCurrentYearMonth();

        // Then
        assertNotNull("Should return valid array", current);
        assertEquals("Should return array of size 2", 2, current.length);
        assertTrue("Year should be reasonable", current[0] >= 2020 && current[0] <= 3000);
        assertTrue("Month should be valid", current[1] >= 1 && current[1] <= 12);
    }

    @Test
    public void should_handle_edge_case_february_leap_year() {
        // When & Then
        assertEquals("Feb 2024 should have 29 days", 29, CalendarNavigator.getDaysInMonth(2024, 2));
        assertEquals("Feb 2023 should have 28 days", 28, CalendarNavigator.getDaysInMonth(2023, 2));
        assertEquals("Feb 2000 should have 29 days", 29, CalendarNavigator.getDaysInMonth(2000, 2));
        assertEquals("Feb 1900 should have 28 days", 28, CalendarNavigator.getDaysInMonth(1900, 2));
    }

    @Test
    public void should_handle_boundary_years() {
        // When & Then - Testing boundary conditions
        int[] result1 = CalendarNavigator.changeMonth(1900, 1, -1);
        assertEquals("Should handle year boundary at lower end", 1899, result1[0]);
        assertEquals("Month should be December", 12, result1[1]);

        int[] result2 = CalendarNavigator.changeMonth(2999, 12, 1);
        assertEquals("Should handle year boundary at upper end", 3000, result2[0]);
        assertEquals("Month should be January", 1, result2[1]);
    }

    @Test
    public void should_maintain_consistency_between_helpers_and_main_method() {
        // Given
        int year = 2024;
        int month = 6;

        // When
        int[] nextFromHelper = CalendarNavigator.nextMonth(year, month);
        int[] nextFromMain = CalendarNavigator.changeMonth(year, month, 1);
        int[] prevFromHelper = CalendarNavigator.previousMonth(year, month);
        int[] prevFromMain = CalendarNavigator.changeMonth(year, month, -1);

        // Then
        assertArrayEquals("Next month helper should match main method", nextFromMain, nextFromHelper);
        assertArrayEquals("Previous month helper should match main method", prevFromMain, prevFromHelper);
    }

    @Test
    public void should_handle_large_offsets() {
        // Given
        int year = 2024;
        int month = 6;
        int largeOffset = 25; // Over 2 years

        // When
        int[] result = CalendarNavigator.changeMonth(year, month, largeOffset);

        // Then
        assertEquals("Should handle large positive offset", 2026, result[0]);
        assertEquals("Should handle large positive offset", 7, result[1]);
    }

    @Test
    public void should_handle_large_negative_offsets() {
        // Given
        int year = 2024;
        int month = 6;
        int largeOffset = -30; // Over 2 years back

        // When
        int[] result = CalendarNavigator.changeMonth(year, month, largeOffset);

        // Then
        assertEquals("Should handle large negative offset", 2021, result[0]);
        assertEquals("Should handle large negative offset", 12, result[1]);
    }
}