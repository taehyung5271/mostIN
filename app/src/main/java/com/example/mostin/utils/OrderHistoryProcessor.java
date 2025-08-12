package com.example.mostin.utils;

import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.Ordering;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for processing order history data
 * Extracted from OrderHistoryFragment for better testability
 */
public class OrderHistoryProcessor {

    /**
     * Groups orders by date and creates OrderHistoryModel objects
     * @param orders List of Ordering objects
     * @param employee Employee information
     * @return List of OrderHistoryModel objects grouped by date, sorted in descending order
     */
    public static List<OrderHistoryModel> processOrderHistory(List<Ordering> orders, EmployeeModel employee) {
        if (orders == null || employee == null) {
            return new ArrayList<>();
        }

        Map<String, List<Ordering>> groupedOrders = groupOrdersByDate(orders);
        List<String> sortedDates = sortDatesByDescending(groupedOrders.keySet());
        
        List<OrderHistoryModel> orderHistory = new ArrayList<>();
        for (String date : sortedDates) {
            orderHistory.add(new OrderHistoryModel(
                date, 
                employee.getEmployeeId(), 
                employee.getEmployeeName(), 
                employee.getWorkPlaceName()
            ));
        }
        
        return orderHistory;
    }

    /**
     * Groups orders by date
     * @param orders List of orders to group
     * @return Map with date as key and list of orders as value
     */
    public static Map<String, List<Ordering>> groupOrdersByDate(List<Ordering> orders) {
        Map<String, List<Ordering>> grouped = new HashMap<>();
        
        if (orders == null) {
            return grouped;
        }
        
        for (Ordering order : orders) {
            if (order != null && order.getOrderingDay() != null) {
                String date = order.getOrderingDay();
                grouped.computeIfAbsent(date, k -> new ArrayList<>()).add(order);
            }
        }
        
        return grouped;
    }

    /**
     * Sorts dates in descending order (most recent first)
     * @param dates Set of date strings
     * @return List of dates sorted in descending order
     */
    public static List<String> sortDatesByDescending(Iterable<String> dates) {
        List<String> dateList = new ArrayList<>();
        
        if (dates == null) {
            return dateList;
        }
        
        for (String date : dates) {
            if (date != null && isValidDateFormat(date)) {
                dateList.add(date);
            }
        }
        
        dateList.sort((d1, d2) -> {
            try {
                LocalDate date1 = LocalDate.parse(d1);
                LocalDate date2 = LocalDate.parse(d2);
                return date2.compareTo(date1); // Descending order
            } catch (DateTimeParseException e) {
                return d2.compareTo(d1); // Fallback to string comparison
            }
        });
        
        return dateList;
    }

    /**
     * Sorts dates in ascending order (oldest first)
     * @param dates Set of date strings
     * @return List of dates sorted in ascending order
     */
    public static List<String> sortDatesByAscending(Iterable<String> dates) {
        List<String> dateList = new ArrayList<>();
        
        if (dates == null) {
            return dateList;
        }
        
        for (String date : dates) {
            if (date != null && isValidDateFormat(date)) {
                dateList.add(date);
            }
        }
        
        dateList.sort((d1, d2) -> {
            try {
                LocalDate date1 = LocalDate.parse(d1);
                LocalDate date2 = LocalDate.parse(d2);
                return date1.compareTo(date2); // Ascending order
            } catch (DateTimeParseException e) {
                return d1.compareTo(d2); // Fallback to string comparison
            }
        });
        
        return dateList;
    }

    /**
     * Validates if date string is in valid format (YYYY-MM-DD)
     * @param dateString Date string to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }
        
        try {
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Calculates total box count for orders on a specific date
     * @param orders List of orders
     * @param targetDate Date to calculate total for
     * @return Total box count for the specified date
     */
    public static int calculateTotalBoxesForDate(List<Ordering> orders, String targetDate) {
        if (orders == null || targetDate == null) {
            return 0;
        }
        
        int total = 0;
        for (Ordering order : orders) {
            if (order != null && 
                targetDate.equals(order.getOrderingDay()) && 
                order.getBoxNum() != null) {
                total += order.getBoxNum();
            }
        }
        
        return total;
    }

    /**
     * Counts distinct products (barcodes) for orders on a specific date
     * @param orders List of orders
     * @param targetDate Date to count products for
     * @return Number of distinct products ordered on the date
     */
    public static int countDistinctProductsForDate(List<Ordering> orders, String targetDate) {
        if (orders == null || targetDate == null) {
            return 0;
        }
        
        List<String> distinctBarcodes = new ArrayList<>();
        for (Ordering order : orders) {
            if (order != null && 
                targetDate.equals(order.getOrderingDay()) && 
                order.getBarcode() != null &&
                !distinctBarcodes.contains(order.getBarcode())) {
                distinctBarcodes.add(order.getBarcode());
            }
        }
        
        return distinctBarcodes.size();
    }

    /**
     * Filters orders by date range (inclusive)
     * @param orders List of orders to filter
     * @param startDate Start date (YYYY-MM-DD format)
     * @param endDate End date (YYYY-MM-DD format)
     * @return List of orders within the date range
     */
    public static List<Ordering> filterOrdersByDateRange(List<Ordering> orders, String startDate, String endDate) {
        List<Ordering> filtered = new ArrayList<>();
        
        if (orders == null || !isValidDateFormat(startDate) || !isValidDateFormat(endDate)) {
            return filtered;
        }
        
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            if (start.isAfter(end)) {
                return filtered; // Invalid range
            }
            
            for (Ordering order : orders) {
                if (order != null && order.getOrderingDay() != null) {
                    try {
                        LocalDate orderDate = LocalDate.parse(order.getOrderingDay());
                        if (!orderDate.isBefore(start) && !orderDate.isAfter(end)) {
                            filtered.add(order);
                        }
                    } catch (DateTimeParseException e) {
                        // Skip orders with invalid dates
                    }
                }
            }
        } catch (DateTimeParseException e) {
            // Return empty list for invalid date parameters
        }
        
        return filtered;
    }

    /**
     * Gets all unique dates from orders list
     * @param orders List of orders
     * @return List of unique dates in ascending order
     */
    public static List<String> getUniqueDates(List<Ordering> orders) {
        List<String> uniqueDates = new ArrayList<>();
        
        if (orders == null) {
            return uniqueDates;
        }
        
        for (Ordering order : orders) {
            if (order != null && order.getOrderingDay() != null) {
                String date = order.getOrderingDay();
                if (isValidDateFormat(date) && !uniqueDates.contains(date)) {
                    uniqueDates.add(date);
                }
            }
        }
        
        return sortDatesByAscending(uniqueDates);
    }

    /**
     * Filters out admin employees from the list
     * @param employees List of employees
     * @return List with admin employees removed
     */
    public static List<EmployeeModel> filterOutAdminEmployees(List<EmployeeModel> employees) {
        List<EmployeeModel> filtered = new ArrayList<>();
        
        if (employees == null) {
            return filtered;
        }
        
        for (EmployeeModel employee : employees) {
            if (employee != null && 
                employee.getEmployeeId() != null && 
                !"admin".equals(employee.getEmployeeId().toLowerCase())) {
                filtered.add(employee);
            }
        }
        
        return filtered;
    }
}