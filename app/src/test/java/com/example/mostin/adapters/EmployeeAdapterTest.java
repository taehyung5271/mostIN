package com.example.mostin.adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;

import com.example.mostin.models.EmployeeModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class EmployeeAdapterTest {

    private EmployeeAdapter adapter;
    private List<EmployeeModel> mockEmployeeList;

    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new EmployeeModel("EMP001", "John Doe", "ADMIN", "Seoul Branch"));
        mockEmployeeList.add(new EmployeeModel("EMP002", "Jane Smith", "USER", "Busan Branch"));
        
        adapter = new EmployeeAdapter(mockContext);
    }

    @Test
    public void should_setOriginalEmployeesCorrectly_when_setOriginalEmployeesCalled() {
        // When
        adapter.setOriginalEmployees(mockEmployeeList);
        
        // Then
        assertEquals(2, adapter.getItemCount());
        assertEquals(2, adapter.getAllEmployees().size());
    }

    @Test
    public void should_updateDisplayedEmployees_when_updateDisplayedEmployeesCalled() {
        // Given
        adapter.setOriginalEmployees(mockEmployeeList);
        List<EmployeeModel> filteredList = Arrays.asList(
            new EmployeeModel("EMP004", "Alice Brown", "USER", "Daegu Branch")
        );
        
        // When
        adapter.updateDisplayedEmployees(filteredList);
        
        // Then
        assertEquals(1, adapter.getItemCount()); // Displayed count changed
        assertEquals(2, adapter.getAllEmployees().size()); // Original count unchanged
    }

    @Test
    public void should_maintainOriginalList_when_updateDisplayedEmployeesCalled() {
        // Given
        adapter.setOriginalEmployees(mockEmployeeList);
        List<EmployeeModel> newDisplayedList = Arrays.asList(
            new EmployeeModel("EMP005", "Charlie Wilson", "ADMIN", "Incheon Branch")
        );
        
        // When
        adapter.updateDisplayedEmployees(newDisplayedList);
        
        // Then
        List<EmployeeModel> originalEmployees = adapter.getAllEmployees();
        assertEquals(2, originalEmployees.size());
        assertEquals("John Doe", originalEmployees.get(0).getEmployeeName());
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void should_returnCorrectItemCount_when_adapterInitialized() {
        // Given & When
        int initialCount = adapter.getItemCount();
        
        // Then
        assertEquals(0, initialCount);
    }

    @Test
    public void should_handleEmptyList_when_setOriginalEmployeesCalledWithEmptyList() {
        // Given
        List<EmployeeModel> emptyList = new ArrayList<>();
        
        // When
        adapter.setOriginalEmployees(emptyList);
        
        // Then
        assertEquals(0, adapter.getItemCount());
        assertEquals(0, adapter.getAllEmployees().size());
    }

    @Test
    public void should_handleNullList_when_updateDisplayedEmployeesCalledWithNull() {
        // Given
        adapter.setOriginalEmployees(mockEmployeeList);
        
        // When
        adapter.updateDisplayedEmployees(null);
        
        // Then
        assertEquals(0, adapter.getItemCount()); // Should handle null gracefully
        assertEquals(2, adapter.getAllEmployees().size()); // Original should remain unchanged
    }

    @Test
    public void should_returnIndependentCopy_when_getAllEmployeesCalled() {
        // Given
        adapter.setOriginalEmployees(mockEmployeeList);
        
        // When
        List<EmployeeModel> retrievedEmployees = adapter.getAllEmployees();
        
        // Then - Modify the retrieved list should not affect original
        retrievedEmployees.clear();
        assertEquals(2, adapter.getAllEmployees().size()); // Original should remain unchanged
    }

    @Test
    public void should_preserveOriginalData_when_multipleUpdateDisplayedEmployeesCalls() {
        // Given
        adapter.setOriginalEmployees(mockEmployeeList);
        
        List<EmployeeModel> firstFilteredList = Arrays.asList(
            new EmployeeModel("EMP003", "Alice Brown", "USER", "Daegu Branch")
        );
        
        List<EmployeeModel> secondFilteredList = Arrays.asList(
            new EmployeeModel("EMP004", "Bob Green", "ADMIN", "Gwangju Branch"),
            new EmployeeModel("EMP005", "Carol Blue", "USER", "Ulsan Branch")
        );
        
        // When
        adapter.updateDisplayedEmployees(firstFilteredList);
        assertEquals(1, adapter.getItemCount());
        
        adapter.updateDisplayedEmployees(secondFilteredList);
        assertEquals(2, adapter.getItemCount());
        
        // Then - Original data should remain unchanged
        List<EmployeeModel> originalEmployees = adapter.getAllEmployees();
        assertEquals(2, originalEmployees.size());
        assertEquals("John Doe", originalEmployees.get(0).getEmployeeName());
        assertEquals("Jane Smith", originalEmployees.get(1).getEmployeeName());
    }

    @Test
    public void should_createIndependentLists_when_setOriginalEmployeesCalled() {
        // Given
        List<EmployeeModel> originalList = new ArrayList<>(mockEmployeeList);
        
        // When
        adapter.setOriginalEmployees(originalList);
        
        // Modify the original input list
        originalList.add(new EmployeeModel("EMP999", "New Employee", "USER", "New Branch"));
        
        // Then - Adapter's data should not be affected
        assertEquals(2, adapter.getAllEmployees().size());
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_maintainDataConsistency_when_alternatingOperations() {
        // Given
        List<EmployeeModel> initialData = Arrays.asList(
            new EmployeeModel("EMP100", "Test User 1", "ADMIN", "Test Branch 1"),
            new EmployeeModel("EMP200", "Test User 2", "USER", "Test Branch 2"),
            new EmployeeModel("EMP300", "Test User 3", "USER", "Test Branch 3")
        );
        
        // When - Alternate between setting original and updating displayed
        adapter.setOriginalEmployees(initialData);
        assertEquals(3, adapter.getItemCount());
        assertEquals(3, adapter.getAllEmployees().size());
        
        List<EmployeeModel> filteredData = Arrays.asList(initialData.get(0));
        adapter.updateDisplayedEmployees(filteredData);
        assertEquals(1, adapter.getItemCount());
        assertEquals(3, adapter.getAllEmployees().size());
        
        List<EmployeeModel> newOriginalData = Arrays.asList(
            new EmployeeModel("EMP400", "New Test User", "ADMIN", "New Test Branch")
        );
        adapter.setOriginalEmployees(newOriginalData);
        assertEquals(1, adapter.getItemCount()); // Should be updated to new data
        assertEquals(1, adapter.getAllEmployees().size()); // Original data changed
        
        // Then
        assertEquals("New Test User", adapter.getAllEmployees().get(0).getEmployeeName());
    }
}