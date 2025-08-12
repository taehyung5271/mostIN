package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeModelTest {

    @Test
    public void should_returnCorrectToString_when_validNameAndId() {
        // Given
        String employeeId = "EMP001";
        String employeeName = "김철수";
        String employeeType = "정규직";
        String workPlaceName = "서울지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        String expected = employeeName + " (" + employeeId + ")";
        assertEquals(expected, result);
        assertEquals("김철수 (EMP001)", result);
    }

    @Test
    public void should_returnCorrectToString_when_englishNameAndId() {
        // Given
        String employeeId = "EMP002";
        String employeeName = "John Smith";
        String employeeType = "계약직";
        String workPlaceName = "부산지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        assertEquals("John Smith (EMP002)", result);
    }

    @Test
    public void should_handleNullName_when_employeeNameIsNull() {
        // Given
        String employeeId = "EMP003";
        String employeeName = null;
        String employeeType = "정규직";
        String workPlaceName = "대구지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        String expected = null + " (" + employeeId + ")";
        assertEquals(expected, result);
        assertEquals("null (EMP003)", result);
    }

    @Test
    public void should_handleNullId_when_employeeIdIsNull() {
        // Given
        String employeeId = null;
        String employeeName = "이영희";
        String employeeType = "정규직";
        String workPlaceName = "광주지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        String expected = employeeName + " (" + null + ")";
        assertEquals(expected, result);
        assertEquals("이영희 (null)", result);
    }

    @Test
    public void should_handleBothNull_when_nameAndIdAreNull() {
        // Given
        String employeeId = null;
        String employeeName = null;
        String employeeType = "계약직";
        String workPlaceName = "인천지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        assertEquals("null (null)", result);
    }

    @Test
    public void should_handleEmptyStrings_when_nameAndIdAreEmpty() {
        // Given
        String employeeId = "";
        String employeeName = "";
        String employeeType = "정규직";
        String workPlaceName = "울산지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        assertEquals(" ()", result);
    }

    @Test
    public void should_handleSpecialCharacters_when_nameContainsSpecialChars() {
        // Given
        String employeeId = "EMP-001";
        String employeeName = "김@철#수";
        String employeeType = "정규직";
        String workPlaceName = "서울지점";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, employeeType, workPlaceName);
        String result = employee.toString();
        
        // Then
        assertEquals("김@철#수 (EMP-001)", result);
    }

    @Test
    public void should_testFullConstructor_when_usingAllParameters() {
        // Given
        String employeeId = "EMP004";
        String employeeName = "박민수";
        String password = "password123";
        String phone = "010-1234-5678";
        String type = "정규직";
        String address = "서울시 강남구";
        String workPlace = "본사";
        
        // When
        EmployeeModel employee = new EmployeeModel(employeeId, employeeName, password, phone, type, address, workPlace);
        String result = employee.toString();
        
        // Then
        assertEquals("박민수 (EMP004)", result);
        assertEquals(password, employee.getPassword());
        assertEquals(phone, employee.getPhoneNum());
        // Note: getAddress() method is not available in EmployeeModel
    }

    @Test
    public void should_testGettersAndSetters_when_modifyingProperties() {
        // Given
        EmployeeModel employee = new EmployeeModel("EMP005", "최수정", "정규직", "대전지점");
        
        // When
        employee.setEmployeeName("최수정-수정");
        employee.setEmployeeType("계약직");
        employee.setWorkPlaceName("세종지점");
        employee.setPhoneNum("010-9876-5432");
        
        // Then
        assertEquals("EMP005", employee.getEmployeeId());
        assertEquals("최수정-수정", employee.getEmployeeName());
        assertEquals("계약직", employee.getEmployeeType());
        assertEquals("세종지점", employee.getWorkPlaceName());
        assertEquals("010-9876-5432", employee.getPhoneNum());
        
        // toString 결과도 변경된 이름으로 확인
        assertEquals("최수정-수정 (EMP005)", employee.toString());
    }

    @Test
    public void should_maintainConsistentFormat_when_multipleInstances() {
        // Given - 여러 인스턴스의 toString 형식 일관성 검증
        EmployeeModel[] employees = {
            new EmployeeModel("A001", "김", "정규직", "서울"),
            new EmployeeModel("B002", "이영수영수영수", "계약직", "부산"),
            new EmployeeModel("C003", "Park", "인턴", "대구"),
            new EmployeeModel("LONG_ID_001", "Very Long Name Here", "정규직", "광주")
        };
        
        // When & Then
        for (EmployeeModel emp : employees) {
            String result = emp.toString();
            
            // 형식 검증: "이름 (ID)" 패턴
            assertTrue("toString should contain employee name", result.contains(emp.getEmployeeName()));
            assertTrue("toString should contain employee ID", result.contains(emp.getEmployeeId()));
            assertTrue("toString should start with name", result.startsWith(emp.getEmployeeName()));
            assertTrue("toString should end with ID in parentheses", result.endsWith("(" + emp.getEmployeeId() + ")"));
        }
    }
}