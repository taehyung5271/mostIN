package com.example.mostin.utils;

import static org.junit.Assert.*;

import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.Ordering;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for OrderHistoryProcessor utility class
 */
public class OrderHistoryProcessorTest {

    private EmployeeModel createTestEmployee() {
        return new EmployeeModel("emp001", "김철수", "employee", "서울지점");
    }

    private List<Ordering> createTestOrders() {
        return Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering("2024-01-15", "emp001", "XYZ789", "김철수", 3, "상품B"),
            new Ordering("2024-01-10", "emp001", "DEF456", "김철수", 2, "상품C"),
            new Ordering("2024-01-20", "emp001", "GHI789", "김철수", 10, "상품D"),
            new Ordering("2024-01-10", "emp001", "JKL012", "김철수", 7, "상품E")
        );
    }

    @Test
    public void should_return_empty_list_when_orders_are_null() {
        // Given
        List<Ordering> orders = null;
        EmployeeModel employee = createTestEmployee();

        // When
        List<OrderHistoryModel> result = OrderHistoryProcessor.processOrderHistory(orders, employee);

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_return_empty_list_when_employee_is_null() {
        // Given
        List<Ordering> orders = createTestOrders();
        EmployeeModel employee = null;

        // When
        List<OrderHistoryModel> result = OrderHistoryProcessor.processOrderHistory(orders, employee);

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_process_order_history_correctly() {
        // Given
        List<Ordering> orders = createTestOrders();
        EmployeeModel employee = createTestEmployee();

        // When
        List<OrderHistoryModel> result = OrderHistoryProcessor.processOrderHistory(orders, employee);

        // Then
        assertEquals("Should have 3 unique dates", 3, result.size());
        
        // Check if dates are sorted in descending order
        assertEquals("First date should be most recent", "2024-01-20", result.get(0).getOrderingDay());
        assertEquals("Second date should be middle", "2024-01-15", result.get(1).getOrderingDay());
        assertEquals("Third date should be oldest", "2024-01-10", result.get(2).getOrderingDay());
        
        // Check if employee info is correct
        for (OrderHistoryModel history : result) {
            assertEquals("emp001", history.getEmployeeId());
            assertEquals("김철수", history.getEmployeeName());
            assertEquals("서울지점", history.getWorkPlaceName());
        }
    }

    @Test
    public void should_group_orders_by_date_correctly() {
        // Given
        List<Ordering> orders = createTestOrders();

        // When
        Map<String, List<Ordering>> result = OrderHistoryProcessor.groupOrdersByDate(orders);

        // Then
        assertEquals("Should have 3 groups", 3, result.size());
        assertTrue("Should contain 2024-01-10", result.containsKey("2024-01-10"));
        assertTrue("Should contain 2024-01-15", result.containsKey("2024-01-15"));
        assertTrue("Should contain 2024-01-20", result.containsKey("2024-01-20"));
        
        assertEquals("2024-01-10 should have 2 orders", 2, result.get("2024-01-10").size());
        assertEquals("2024-01-15 should have 2 orders", 2, result.get("2024-01-15").size());
        assertEquals("2024-01-20 should have 1 order", 1, result.get("2024-01-20").size());
    }

    @Test
    public void should_return_empty_map_when_orders_null_for_grouping() {
        // Given
        List<Ordering> orders = null;

        // When
        Map<String, List<Ordering>> result = OrderHistoryProcessor.groupOrdersByDate(orders);

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_handle_null_orders_in_list_for_grouping() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            null,
            new Ordering("2024-01-10", "emp001", "DEF456", "김철수", 2, "상품C"),
            null
        );

        // When
        Map<String, List<Ordering>> result = OrderHistoryProcessor.groupOrdersByDate(orders);

        // Then
        assertEquals("Should have 2 groups", 2, result.size());
        assertEquals("2024-01-15 should have 1 order", 1, result.get("2024-01-15").size());
        assertEquals("2024-01-10 should have 1 order", 1, result.get("2024-01-10").size());
    }

    @Test
    public void should_handle_orders_with_null_dates_for_grouping() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering(null, "emp001", "XYZ789", "김철수", 3, "상품B"),
            new Ordering("2024-01-10", "emp001", "DEF456", "김철수", 2, "상품C")
        );

        // When
        Map<String, List<Ordering>> result = OrderHistoryProcessor.groupOrdersByDate(orders);

        // Then
        assertEquals("Should have 2 groups (null dates ignored)", 2, result.size());
        assertTrue("Should contain 2024-01-15", result.containsKey("2024-01-15"));
        assertTrue("Should contain 2024-01-10", result.containsKey("2024-01-10"));
        assertFalse("Should not contain null key", result.containsKey(null));
    }

    @Test
    public void should_sort_dates_in_descending_order() {
        // Given
        List<String> dates = Arrays.asList("2024-01-10", "2024-01-20", "2024-01-15", "2023-12-31", "2024-02-01");

        // When
        List<String> result = OrderHistoryProcessor.sortDatesByDescending(dates);

        // Then
        assertEquals("Should have 5 dates", 5, result.size());
        assertEquals("2024-02-01", result.get(0));
        assertEquals("2024-01-20", result.get(1));
        assertEquals("2024-01-15", result.get(2));
        assertEquals("2024-01-10", result.get(3));
        assertEquals("2023-12-31", result.get(4));
    }

    @Test
    public void should_sort_dates_in_ascending_order() {
        // Given
        List<String> dates = Arrays.asList("2024-01-10", "2024-01-20", "2024-01-15", "2023-12-31", "2024-02-01");

        // When
        List<String> result = OrderHistoryProcessor.sortDatesByAscending(dates);

        // Then
        assertEquals("Should have 5 dates", 5, result.size());
        assertEquals("2023-12-31", result.get(0));
        assertEquals("2024-01-10", result.get(1));
        assertEquals("2024-01-15", result.get(2));
        assertEquals("2024-01-20", result.get(3));
        assertEquals("2024-02-01", result.get(4));
    }

    @Test
    public void should_handle_invalid_date_formats_in_sorting() {
        // Given
        List<String> dates = Arrays.asList("2024-01-15", "invalid-date", "2024-01-10", "2024/01/20", "");

        // When
        List<String> resultDesc = OrderHistoryProcessor.sortDatesByDescending(dates);
        List<String> resultAsc = OrderHistoryProcessor.sortDatesByAscending(dates);

        // Then
        assertEquals("Should include only valid dates", 2, resultDesc.size());
        assertEquals("Should include only valid dates", 2, resultAsc.size());
        assertTrue("Should contain valid dates", resultDesc.contains("2024-01-15"));
        assertTrue("Should contain valid dates", resultDesc.contains("2024-01-10"));
    }

    @Test
    public void should_return_empty_list_for_null_dates_in_sorting() {
        // When
        List<String> resultDesc = OrderHistoryProcessor.sortDatesByDescending(null);
        List<String> resultAsc = OrderHistoryProcessor.sortDatesByAscending(null);

        // Then
        assertNotNull("Result should not be null", resultDesc);
        assertNotNull("Result should not be null", resultAsc);
        assertTrue("Result should be empty", resultDesc.isEmpty());
        assertTrue("Result should be empty", resultAsc.isEmpty());
    }

    @Test
    public void should_validate_date_format_correctly() {
        // When & Then
        assertTrue("Valid date should return true", OrderHistoryProcessor.isValidDateFormat("2024-01-15"));
        assertTrue("Valid date should return true", OrderHistoryProcessor.isValidDateFormat("2023-12-31"));
        assertTrue("Valid date should return true", OrderHistoryProcessor.isValidDateFormat("2024-02-29")); // Leap year
        
        assertFalse("Null should return false", OrderHistoryProcessor.isValidDateFormat(null));
        assertFalse("Empty string should return false", OrderHistoryProcessor.isValidDateFormat(""));
        assertFalse("Invalid format should return false", OrderHistoryProcessor.isValidDateFormat("2024/01/15"));
        assertFalse("Invalid format should return false", OrderHistoryProcessor.isValidDateFormat("15-01-2024"));
        assertFalse("Invalid date should return false", OrderHistoryProcessor.isValidDateFormat("2024-13-01"));
        assertFalse("Invalid date should return false", OrderHistoryProcessor.isValidDateFormat("2024-02-30"));
    }

    @Test
    public void should_calculate_total_boxes_for_date() {
        // Given
        List<Ordering> orders = createTestOrders();
        String targetDate = "2024-01-15";

        // When
        int result = OrderHistoryProcessor.calculateTotalBoxesForDate(orders, targetDate);

        // Then
        assertEquals("Should calculate total boxes for 2024-01-15", 8, result); // 5 + 3
    }

    @Test
    public void should_return_zero_for_non_existent_date() {
        // Given
        List<Ordering> orders = createTestOrders();
        String targetDate = "2024-01-25";

        // When
        int result = OrderHistoryProcessor.calculateTotalBoxesForDate(orders, targetDate);

        // Then
        assertEquals("Should return 0 for non-existent date", 0, result);
    }

    @Test
    public void should_handle_null_parameters_in_box_calculation() {
        // When & Then
        assertEquals(0, OrderHistoryProcessor.calculateTotalBoxesForDate(null, "2024-01-15"));
        assertEquals(0, OrderHistoryProcessor.calculateTotalBoxesForDate(createTestOrders(), null));
    }

    @Test
    public void should_handle_null_box_numbers() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering("2024-01-15", "emp001", "XYZ789", "김철수", null, "상품B")
        );
        String targetDate = "2024-01-15";

        // When
        int result = OrderHistoryProcessor.calculateTotalBoxesForDate(orders, targetDate);

        // Then
        assertEquals("Should handle null box numbers", 5, result);
    }

    @Test
    public void should_count_distinct_products_for_date() {
        // Given
        List<Ordering> orders = createTestOrders();
        String targetDate = "2024-01-15";

        // When
        int result = OrderHistoryProcessor.countDistinctProductsForDate(orders, targetDate);

        // Then
        assertEquals("Should count 2 distinct products for 2024-01-15", 2, result);
    }

    @Test
    public void should_return_zero_distinct_products_for_non_existent_date() {
        // Given
        List<Ordering> orders = createTestOrders();
        String targetDate = "2024-01-25";

        // When
        int result = OrderHistoryProcessor.countDistinctProductsForDate(orders, targetDate);

        // Then
        assertEquals("Should return 0 for non-existent date", 0, result);
    }

    @Test
    public void should_handle_null_barcodes_in_distinct_count() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering("2024-01-15", "emp001", null, "김철수", 3, "상품B"),
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 2, "상품A")
        );
        String targetDate = "2024-01-15";

        // When
        int result = OrderHistoryProcessor.countDistinctProductsForDate(orders, targetDate);

        // Then
        assertEquals("Should count 1 distinct product (null barcodes ignored)", 1, result);
    }

    @Test
    public void should_filter_orders_by_date_range() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-05", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering("2024-01-10", "emp001", "XYZ789", "김철수", 3, "상품B"),
            new Ordering("2024-01-15", "emp001", "DEF456", "김철수", 2, "상품C"),
            new Ordering("2024-01-20", "emp001", "GHI789", "김철수", 10, "상품D"),
            new Ordering("2024-01-25", "emp001", "JKL012", "김철수", 7, "상품E")
        );
        String startDate = "2024-01-10";
        String endDate = "2024-01-20";

        // When
        List<Ordering> result = OrderHistoryProcessor.filterOrdersByDateRange(orders, startDate, endDate);

        // Then
        assertEquals("Should filter 3 orders in range", 3, result.size());
        assertTrue("Should include start date", result.stream().anyMatch(o -> "2024-01-10".equals(o.getOrderingDay())));
        assertTrue("Should include end date", result.stream().anyMatch(o -> "2024-01-20".equals(o.getOrderingDay())));
        assertTrue("Should include middle date", result.stream().anyMatch(o -> "2024-01-15".equals(o.getOrderingDay())));
        assertFalse("Should exclude dates before range", result.stream().anyMatch(o -> "2024-01-05".equals(o.getOrderingDay())));
        assertFalse("Should exclude dates after range", result.stream().anyMatch(o -> "2024-01-25".equals(o.getOrderingDay())));
    }

    @Test
    public void should_return_empty_list_for_invalid_date_range() {
        // Given
        List<Ordering> orders = createTestOrders();
        String startDate = "2024-01-20";
        String endDate = "2024-01-10"; // Start after end

        // When
        List<Ordering> result = OrderHistoryProcessor.filterOrdersByDateRange(orders, startDate, endDate);

        // Then
        assertTrue("Should return empty list for invalid range", result.isEmpty());
    }

    @Test
    public void should_handle_null_parameters_in_date_range_filter() {
        // Given
        List<Ordering> orders = createTestOrders();

        // When & Then
        assertTrue("Should handle null orders", OrderHistoryProcessor.filterOrdersByDateRange(null, "2024-01-10", "2024-01-20").isEmpty());
        assertTrue("Should handle null start date", OrderHistoryProcessor.filterOrdersByDateRange(orders, null, "2024-01-20").isEmpty());
        assertTrue("Should handle null end date", OrderHistoryProcessor.filterOrdersByDateRange(orders, "2024-01-10", null).isEmpty());
    }

    @Test
    public void should_get_unique_dates_in_ascending_order() {
        // Given
        List<Ordering> orders = Arrays.asList(
            new Ordering("2024-01-15", "emp001", "ABC123", "김철수", 5, "상품A"),
            new Ordering("2024-01-10", "emp001", "XYZ789", "김철수", 3, "상품B"),
            new Ordering("2024-01-15", "emp001", "DEF456", "김철수", 2, "상품C"), // Duplicate date
            new Ordering("2024-01-20", "emp001", "GHI789", "김철수", 10, "상품D")
        );

        // When
        List<String> result = OrderHistoryProcessor.getUniqueDates(orders);

        // Then
        assertEquals("Should have 3 unique dates", 3, result.size());
        assertEquals("2024-01-10", result.get(0));
        assertEquals("2024-01-15", result.get(1));
        assertEquals("2024-01-20", result.get(2));
    }

    @Test
    public void should_filter_out_admin_employees() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp001", "김철수", "employee", "서울지점"),
            new EmployeeModel("admin", "관리자", "admin", "본사"),
            new EmployeeModel("emp002", "이영희", "employee", "부산지점"),
            new EmployeeModel("ADMIN", "관리자2", "admin", "본사") // Different case
        );

        // When
        List<EmployeeModel> result = OrderHistoryProcessor.filterOutAdminEmployees(employees);

        // Then
        assertEquals("Should filter out admin employees", 2, result.size());
        assertFalse("Should not contain admin", result.stream().anyMatch(e -> "admin".equalsIgnoreCase(e.getEmployeeId())));
        assertTrue("Should contain regular employees", result.stream().anyMatch(e -> "emp001".equals(e.getEmployeeId())));
        assertTrue("Should contain regular employees", result.stream().anyMatch(e -> "emp002".equals(e.getEmployeeId())));
    }

    @Test
    public void should_handle_null_employees_in_admin_filter() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp001", "김철수", "employee", "서울지점"),
            null,
            new EmployeeModel("admin", "관리자", "admin", "본사")
        );

        // When
        List<EmployeeModel> result = OrderHistoryProcessor.filterOutAdminEmployees(employees);

        // Then
        assertEquals("Should handle null employees", 1, result.size());
        assertEquals("emp001", result.get(0).getEmployeeId());
    }

    @Test
    public void should_handle_null_employee_ids_in_admin_filter() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp001", "김철수", "employee", "서울지점"),
            new EmployeeModel(null, "무명", "employee", "지점")
        );

        // When
        List<EmployeeModel> result = OrderHistoryProcessor.filterOutAdminEmployees(employees);

        // Then
        assertEquals("Should handle null employee IDs", 1, result.size());
        assertEquals("emp001", result.get(0).getEmployeeId());
    }
}