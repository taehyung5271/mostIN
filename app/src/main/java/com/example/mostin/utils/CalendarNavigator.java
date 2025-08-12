package com.example.mostin.utils;

import java.util.Calendar;

/**
 * Utility class for calendar navigation operations
 * Extracted from AttendanceCalendarFragment for better testability
 */
public class CalendarNavigator {

    /**
     * Changes month by specified offset and handles year boundaries
     * @param currentYear Current year
     * @param currentMonth Current month (1-based, 1=January, 12=December)
     * @param offset Offset to apply (-1 for previous month, +1 for next month)
     * @return Array with [newYear, newMonth] where month is 1-based
     */
    public static int[] changeMonth(int currentYear, int currentMonth, int offset) {
        // Convert to 0-based month for Calendar operations
        int calendarMonth = currentMonth - 1;
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, calendarMonth);
        calendar.add(Calendar.MONTH, offset);
        
        int newYear = calendar.get(Calendar.YEAR);
        int newMonth = calendar.get(Calendar.MONTH) + 1; // Convert back to 1-based
        
        return new int[]{newYear, newMonth};
    }

    /**
     * Moves to previous month, handling year boundary
     * @param currentYear Current year
     * @param currentMonth Current month (1-based)
     * @return Array with [previousYear, previousMonth]
     */
    public static int[] previousMonth(int currentYear, int currentMonth) {
        return changeMonth(currentYear, currentMonth, -1);
    }

    /**
     * Moves to next month, handling year boundary
     * @param currentYear Current year
     * @param currentMonth Current month (1-based)
     * @return Array with [nextYear, nextMonth]
     */
    public static int[] nextMonth(int currentYear, int currentMonth) {
        return changeMonth(currentYear, currentMonth, 1);
    }

    /**
     * Validates if year and month values are reasonable
     * @param year Year to validate
     * @param month Month to validate (1-based)
     * @return true if values are within reasonable range, false otherwise
     */
    public static boolean isValidDate(int year, int month) {
        return year >= 1900 && year <= 3000 && month >= 1 && month <= 12;
    }

    /**
     * Formats year and month as display string
     * @param year Year
     * @param month Month (1-based)
     * @return Formatted string "YYYY년 MM월"
     */
    public static String formatMonthDisplay(int year, int month) {
        if (!isValidDate(year, month)) {
            return "Invalid Date";
        }
        return String.format("%d년 %d월", year, month);
    }

    /**
     * Gets number of days in specified month
     * @param year Year
     * @param month Month (1-based)
     * @return Number of days in the month, or -1 if invalid input
     */
    public static int getDaysInMonth(int year, int month) {
        if (!isValidDate(year, month)) {
            return -1;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Convert to 0-based
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Checks if given year is a leap year
     * @param year Year to check
     * @return true if leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Calculates month difference between two dates
     * @param fromYear Starting year
     * @param fromMonth Starting month (1-based)
     * @param toYear Ending year  
     * @param toMonth Ending month (1-based)
     * @return Number of months difference (positive if to-date is later)
     */
    public static int getMonthDifference(int fromYear, int fromMonth, int toYear, int toMonth) {
        if (!isValidDate(fromYear, fromMonth) || !isValidDate(toYear, toMonth)) {
            return 0;
        }
        
        return (toYear - fromYear) * 12 + (toMonth - fromMonth);
    }

    /**
     * Gets current year and month as array
     * @return Array with [currentYear, currentMonth] where month is 1-based
     */
    public static int[] getCurrentYearMonth() {
        Calendar calendar = Calendar.getInstance();
        return new int[]{
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1 // Convert to 1-based
        };
    }
}