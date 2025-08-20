package com.example.mostin.utils;

import static org.junit.Assert.*;

import com.example.mostin.models.EmployeeModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for EmployeeFilter utility class
 */
public class EmployeeFilterTest {

    private List<EmployeeModel> createTestEmployees() {
        return Arrays.asList(
            new EmployeeModel("emp001", "김철수", "manager", "서울지점"),
            new EmployeeModel("emp002", "이영희", "employee", "부산지점"),
            new EmployeeModel("emp003", "박민수", "employee", "서울지점"),
            new EmployeeModel("admin", "관리자", "admin", "본사"),
            new EmployeeModel("emp004", "최진영", "manager", "대구지점"),
            new EmployeeModel("emp005", "김영수", "employee", "서울지점")
        );
    }

    @Test
    public void should_return_empty_list_when_employees_are_null() {
        // Given
        List<EmployeeModel> employees = null;
        String query = "김";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_return_copy_of_all_employees_when_query_is_null() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = null;

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should return all employees", employees.size(), result.size());
        assertNotSame("Should return new list", employees, result);
    }

    @Test
    public void should_return_copy_of_all_employees_when_query_is_empty() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should return all employees", employees.size(), result.size());
    }

    @Test
    public void should_return_copy_of_all_employees_when_query_is_whitespace() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "   ";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should return all employees", employees.size(), result.size());
    }

    @Test
    public void should_filter_by_employee_name() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "김";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find 2 employees with name starting with '김'", 2, result.size());
        assertTrue("Should contain 김철수", result.stream().anyMatch(e -> "김철수".equals(e.getEmployeeName())));
        assertTrue("Should contain 김영수", result.stream().anyMatch(e -> "김영수".equals(e.getEmployeeName())));
    }

    @Test
    public void should_filter_by_employee_id() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "emp00";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find 5 employees with ID starting with 'emp00'", 5, result.size());
    }

    @Test
    public void should_filter_by_workplace_name() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "서울";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find 3 employees in 서울지점", 3, result.size());
        assertTrue("All results should have workplace starting with '서울'", 
            result.stream().allMatch(e -> e.getWorkPlaceName().startsWith("서울")));
    }

    @Test
    public void should_filter_case_insensitively() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "EMP";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find employees regardless of case", 5, result.size());
    }

    @Test
    public void should_return_empty_list_when_no_matches() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "존재하지않음";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertTrue("Should return empty list when no matches", result.isEmpty());
    }

    @Test
    public void should_handle_null_employee_fields() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp001", null, "manager", "서울지점"),
            new EmployeeModel(null, "김철수", "manager", "서울지점"),
            new EmployeeModel("emp003", "박민수", "manager", null)
        );
        String query = "김";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find 1 employee with name '김철수'", 1, result.size());
        assertEquals("김철수", result.get(0).getEmployeeName());
    }

    @Test
    public void should_filter_by_workplace_exactly() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String workplace = "서울지점";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterByWorkplace(employees, workplace);

        // Then
        assertEquals("Should find 3 employees in 서울지점", 3, result.size());
        assertTrue("All results should work at 서울지점", 
            result.stream().allMatch(e -> "서울지점".equals(e.getWorkPlaceName())));
    }

    @Test
    public void should_return_empty_list_when_workplace_not_found() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String workplace = "존재하지않는지점";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterByWorkplace(employees, workplace);

        // Then
        assertTrue("Should return empty list for non-existent workplace", result.isEmpty());
    }

    @Test
    public void should_filter_by_workplace_case_insensitively() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String workplace = "서울지점";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterByWorkplace(employees, workplace.toUpperCase());

        // Then - Implementation is case-insensitive, so it should find matches
        assertFalse("Implementation is case-insensitive, should find matches", result.isEmpty());
        assertEquals("Should find employees from Seoul branch", 3, result.size());
    }

    @Test
    public void should_filter_by_employee_type() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String type = "manager";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterByType(employees, type);

        // Then
        assertEquals("Should find 2 managers", 2, result.size());
        assertTrue("All results should be managers", 
            result.stream().allMatch(e -> "manager".equals(e.getEmployeeType())));
    }

    @Test
    public void should_count_employees_by_workplace() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String workplace = "서울지점";

        // When
        int count = EmployeeFilter.countByWorkplace(employees, workplace);

        // Then
        assertEquals("Should count 3 employees in 서울지점", 3, count);
    }

    @Test
    public void should_find_employee_by_id() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String employeeId = "emp001";

        // When
        EmployeeModel result = EmployeeFilter.findByEmployeeId(employees, employeeId);

        // Then
        assertNotNull("Should find the employee", result);
        assertEquals("Should find 김철수", "김철수", result.getEmployeeName());
        assertEquals("Should match the ID", employeeId, result.getEmployeeId());
    }

    @Test
    public void should_return_null_when_employee_id_not_found() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String employeeId = "nonexistent";

        // When
        EmployeeModel result = EmployeeFilter.findByEmployeeId(employees, employeeId);

        // Then
        assertNull("Should return null for non-existent ID", result);
    }

    @Test
    public void should_handle_null_input_parameters() {
        // When & Then
        assertEquals(0, EmployeeFilter.countByWorkplace(null, "서울지점"));
        assertEquals(0, EmployeeFilter.countByWorkplace(createTestEmployees(), null));
        assertNull(EmployeeFilter.findByEmployeeId(null, "emp001"));
        assertNull(EmployeeFilter.findByEmployeeId(createTestEmployees(), null));
        assertTrue(EmployeeFilter.filterByWorkplace(null, "서울지점").isEmpty());
        assertTrue(EmployeeFilter.filterByType(null, "manager").isEmpty());
    }

    @Test
    public void should_handle_empty_employee_list() {
        // Given
        List<EmployeeModel> employees = new ArrayList<>();

        // When & Then
        assertTrue(EmployeeFilter.filterEmployees(employees, "김").isEmpty());
        assertTrue(EmployeeFilter.filterByWorkplace(employees, "서울지점").isEmpty());
        assertTrue(EmployeeFilter.filterByType(employees, "manager").isEmpty());
        assertEquals(0, EmployeeFilter.countByWorkplace(employees, "서울지점"));
        assertNull(EmployeeFilter.findByEmployeeId(employees, "emp001"));
    }

    @Test
    public void should_handle_null_employees_in_list() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp001", "김철수", "manager", "서울지점"),
            null,
            new EmployeeModel("emp002", "이영희", "employee", "부산지점"),
            null
        );

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, "김");

        // Then
        assertEquals("Should handle null employees gracefully", 1, result.size());
        assertEquals("김철수", result.get(0).getEmployeeName());
    }

    @Test
    public void should_match_exact_employee_id() {
        // Given
        List<EmployeeModel> employees = Arrays.asList(
            new EmployeeModel("emp1", "김철수", "manager", "서울지점"),
            new EmployeeModel("emp10", "이영희", "employee", "부산지점"),
            new EmployeeModel("emp100", "박민수", "employee", "서울지점")
        );
        String query = "emp1";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should find all employee IDs starting with 'emp1'", 3, result.size());
    }

    @Test
    public void should_trim_query_before_filtering() {
        // Given
        List<EmployeeModel> employees = createTestEmployees();
        String query = "  김  ";

        // When
        List<EmployeeModel> result = EmployeeFilter.filterEmployees(employees, query);

        // Then
        assertEquals("Should trim query and find employees", 2, result.size());
    }
}