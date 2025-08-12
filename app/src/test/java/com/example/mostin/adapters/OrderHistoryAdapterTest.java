package com.example.mostin.adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.mostin.models.OrderHistoryModel;

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
public class OrderHistoryAdapterTest {

    private OrderHistoryAdapter adapter;
    private List<OrderHistoryModel> mockOrderHistory;

    @Mock
    private OrderHistoryAdapter.OnOrderClickListener mockListener;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockOrderHistory = new ArrayList<>();
        mockOrderHistory.add(new OrderHistoryModel("2024-01-15", "EMP001", "Employee Name", "Test WorkPlace"));
        mockOrderHistory.add(new OrderHistoryModel("2024-01-16", "EMP002", "Employee Name", "Test WorkPlace"));
        mockOrderHistory.add(new OrderHistoryModel("2024-01-17", "EMP003", "Employee Name", "Test WorkPlace"));
        
        adapter = new OrderHistoryAdapter(mockListener);
    }

    @Test
    public void should_returnZero_when_adapterInitialized() {
        // Given & When
        int initialCount = adapter.getItemCount();
        
        // Then
        assertEquals(0, initialCount);
    }

    @Test
    public void should_returnCorrectItemCount_when_orderHistorySet() {
        // When
        adapter.setOrderHistory(mockOrderHistory, "Test Branch");
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_setOrderHistoryCalledWithEmptyList() {
        // Given
        List<OrderHistoryModel> emptyList = new ArrayList<>();
        
        // When
        adapter.setOrderHistory(emptyList, "Test Branch");
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_setOrderHistoryCalledWithNullList() {
        // Given
        adapter.setOrderHistory(mockOrderHistory, "Test Branch");
        assertEquals(3, adapter.getItemCount());
        
        // When
        adapter.setOrderHistory(null, "Test Branch");
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_handleNullWorkPlaceName_when_setOrderHistoryCalledWithNullWorkPlace() {
        // When
        adapter.setOrderHistory(mockOrderHistory, null);
        
        // Then
        assertEquals(3, adapter.getItemCount());
        // Note: Display with null workplace name would be tested in UI binding tests
    }

    @Test
    public void should_handleEmptyWorkPlaceName_when_setOrderHistoryCalledWithEmptyWorkPlace() {
        // When
        adapter.setOrderHistory(mockOrderHistory, "");
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_replaceAllData_when_setOrderHistoryCalledMultipleTimes() {
        // Given
        adapter.setOrderHistory(mockOrderHistory, "First Branch");
        assertEquals(3, adapter.getItemCount());
        
        List<OrderHistoryModel> newHistory = Arrays.asList(
            new OrderHistoryModel("2024-02-01", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-02-02", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(newHistory, "Second Branch");
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_clearAllData_when_clearDataCalled() {
        // Given
        adapter.setOrderHistory(mockOrderHistory, "Test Branch");
        assertEquals(3, adapter.getItemCount());
        
        // When
        adapter.clearData();
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_handleSingleOrder_when_setOrderHistoryCalledWithSingleItem() {
        // Given
        List<OrderHistoryModel> singleOrder = Arrays.asList(
            new OrderHistoryModel("2024-01-20", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(singleOrder, "Single Branch");
        
        // Then
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void should_handleLargeOrderHistory_when_setOrderHistoryCalledWithManyItems() {
        // Given
        List<OrderHistoryModel> largeHistory = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            largeHistory.add(new OrderHistoryModel("2024-01-" + String.format("%02d", i % 31 + 1), "EMP001", "Employee Name", "Test WorkPlace"));
        }
        
        // When
        adapter.setOrderHistory(largeHistory, "Large Branch");
        
        // Then
        assertEquals(100, adapter.getItemCount());
    }

    @Test
    public void should_maintainWorkPlaceNameAfterClear_when_clearDataCalledThenSetOrderHistory() {
        // Given
        adapter.setOrderHistory(mockOrderHistory, "Original Branch");
        
        // When
        adapter.clearData();
        adapter.setOrderHistory(mockOrderHistory, "New Branch");
        
        // Then
        assertEquals(3, adapter.getItemCount());
        // Note: Workplace name usage in display would be tested in UI tests
    }

    @Test
    public void should_handleNullOrderingDay_when_orderHistoryModelHasNullDate() {
        // Given
        List<OrderHistoryModel> historyWithNull = Arrays.asList(
            new OrderHistoryModel(null, "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-16", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(historyWithNull, "Test Branch");
        
        // Then
        assertEquals(2, adapter.getItemCount());
        // Note: Null date display would be tested in UI binding tests
    }

    @Test
    public void should_handleEmptyOrderingDay_when_orderHistoryModelHasEmptyDate() {
        // Given
        List<OrderHistoryModel> historyWithEmpty = Arrays.asList(
            new OrderHistoryModel("", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-16", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(historyWithEmpty, "Test Branch");
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_handleDuplicateDates_when_orderHistoryContainsDuplicates() {
        // Given
        List<OrderHistoryModel> duplicateHistory = Arrays.asList(
            new OrderHistoryModel("2024-01-15", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-15", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-16", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-15", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(duplicateHistory, "Duplicate Branch");
        
        // Then
        assertEquals(4, adapter.getItemCount()); // Should include all items, even duplicates
    }

    @Test
    public void should_handleSpecialCharactersInDate_when_orderingDayContainsSpecialCharacters() {
        // Given
        List<OrderHistoryModel> specialCharHistory = Arrays.asList(
            new OrderHistoryModel("2024/01/15", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024.01.16", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("January 17, 2024", "EMP001", "Employee Name", "Test WorkPlace"),
            new OrderHistoryModel("2024-01-18T00:00:00", "EMP001", "Employee Name", "Test WorkPlace")
        );
        
        // When
        adapter.setOrderHistory(specialCharHistory, "Special Branch");
        
        // Then
        assertEquals(4, adapter.getItemCount());
    }

    @Test
    public void should_handleSpecialCharactersInWorkPlace_when_workPlaceNameContainsSpecialCharacters() {
        // Given
        String specialWorkPlace = "Branch@#$% & Company (주)";
        
        // When
        adapter.setOrderHistory(mockOrderHistory, specialWorkPlace);
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleVeryLongWorkPlaceName_when_workPlaceNameIsVeryLong() {
        // Given
        String veryLongWorkPlace = "This is a very long workplace name that might cause display issues if not handled properly in the UI layout and text views";
        
        // When
        adapter.setOrderHistory(mockOrderHistory, veryLongWorkPlace);
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_maintainDataConsistency_when_multipleOperationsPerformed() {
        // Given
        adapter.setOrderHistory(mockOrderHistory, "First Branch");
        assertEquals(3, adapter.getItemCount());
        
        // When - Multiple operations
        adapter.clearData();
        assertEquals(0, adapter.getItemCount());
        
        List<OrderHistoryModel> newHistory = Arrays.asList(
            new OrderHistoryModel("2024-02-01", "EMP001", "Employee Name", "Test WorkPlace")
        );
        adapter.setOrderHistory(newHistory, "Second Branch");
        assertEquals(1, adapter.getItemCount());
        
        adapter.setOrderHistory(mockOrderHistory, "Third Branch");
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_initializeWithListener_when_adapterCreatedWithListener() {
        // Given & When
        OrderHistoryAdapter adapterWithListener = new OrderHistoryAdapter(mockListener);
        
        // Then
        assertEquals(0, adapterWithListener.getItemCount()); // Should start empty
    }

    @Test
    public void should_handleNullListener_when_adapterCreatedWithNullListener() {
        // Given & When
        OrderHistoryAdapter adapterWithNullListener = new OrderHistoryAdapter(null);
        
        // Then
        assertEquals(0, adapterWithNullListener.getItemCount()); // Should work normally
    }
}