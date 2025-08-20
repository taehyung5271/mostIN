package com.example.mostin.utils;

import com.example.mostin.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for employee filtering functionality
 * Extracted from EmployeeManagementFragment for better testability
 */
public class EmployeeFilter {

    /**
     * Filters employee list based on search query
     * Searches through employee name, employee ID, and workplace name (case-insensitive)
     * @param employees List of employees to filter
     * @param query Search query string
     * @return Filtered list of employees matching the query
     */
    public static List<EmployeeModel> filterEmployees(List<EmployeeModel> employees, String query) {
        if (employees == null) {
            return new ArrayList<>();
        }
        
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(employees);
        }

        String lowerCaseQuery = query.toLowerCase().trim();
        List<EmployeeModel> filteredList = new ArrayList<>();

        for (EmployeeModel employee : employees) {
            if (matchesQuery(employee, lowerCaseQuery)) {
                filteredList.add(employee);
            }
        }

        return filteredList;
    }

    /**
     * Checks if an employee matches the search query
     * @param employee Employee to check
     * @param lowerCaseQuery Lowercase search query
     * @return true if employee matches the query, false otherwise
     */
    private static boolean matchesQuery(EmployeeModel employee, String lowerCaseQuery) {
        if (employee == null) {
            return false;
        }

        // Check employee name
        if (employee.getEmployeeName() != null && 
            employee.getEmployeeName().toLowerCase().startsWith(lowerCaseQuery)) {
            return true;
        }

        // Check employee ID
        if (employee.getEmployeeId() != null && 
            employee.getEmployeeId().toLowerCase().startsWith(lowerCaseQuery)) {
            return true;
        }

        // Check workplace name
        if (employee.getWorkPlaceName() != null && 
            employee.getWorkPlaceName().toLowerCase().startsWith(lowerCaseQuery)) {
            return true;
        }

        return false;
    }

    /**
     * Filters employees by workplace name only
     * @param employees List of employees to filter
     * @param workplaceName Workplace name to filter by
     * @return List of employees working at the specified workplace
     */
    public static List<EmployeeModel> filterByWorkplace(List<EmployeeModel> employees, String workplaceName) {
        if (employees == null || workplaceName == null) {
            return new ArrayList<>();
        }

        List<EmployeeModel> filteredList = new ArrayList<>();
        String lowerCaseWorkplace = workplaceName.toLowerCase().trim();

        for (EmployeeModel employee : employees) {
            if (employee != null && employee.getWorkPlaceName() != null && 
                employee.getWorkPlaceName().toLowerCase().equals(lowerCaseWorkplace)) {
                filteredList.add(employee);
            }
        }

        return filteredList;
    }

    /**
     * Filters employees by employee type
     * @param employees List of employees to filter
     * @param employeeType Employee type to filter by
     * @return List of employees with the specified type
     */
    public static List<EmployeeModel> filterByType(List<EmployeeModel> employees, String employeeType) {
        if (employees == null || employeeType == null) {
            return new ArrayList<>();
        }

        List<EmployeeModel> filteredList = new ArrayList<>();
        String lowerCaseType = employeeType.toLowerCase().trim();

        for (EmployeeModel employee : employees) {
            if (employee != null && employee.getEmployeeType() != null && 
                employee.getEmployeeType().toLowerCase().equals(lowerCaseType)) {
                filteredList.add(employee);
            }
        }

        return filteredList;
    }

    /**
     * Counts employees by workplace
     * @param employees List of employees
     * @param workplaceName Workplace name to count
     * @return Number of employees at the specified workplace
     */
    public static int countByWorkplace(List<EmployeeModel> employees, String workplaceName) {
        return filterByWorkplace(employees, workplaceName).size();
    }

    /**
     * Searches for exact employee by ID
     * @param employees List of employees
     * @param employeeId Employee ID to search for
     * @return EmployeeModel if found, null otherwise
     */
    public static EmployeeModel findByEmployeeId(List<EmployeeModel> employees, String employeeId) {
        if (employees == null || employeeId == null) {
            return null;
        }

        for (EmployeeModel employee : employees) {
            if (employee != null && employeeId.equals(employee.getEmployeeId())) {
                return employee;
            }
        }

        return null;
    }
}