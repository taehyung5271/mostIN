package com.example.mostin.adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;

import com.example.mostin.models.AttendanceRecordModel;

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
public class AttendanceRecordAdapterTest {

    private AttendanceRecordAdapter adapter;
    private List<AttendanceRecordModel> mockRecordsList;

    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockRecordsList = new ArrayList<>();
        mockRecordsList.add(new AttendanceRecordModel("2024-01-15", "EMP001", "Employee Name", "09:00:00", "18:00:00", "Seoul Office"));
        mockRecordsList.add(new AttendanceRecordModel("2024-01-16", "EMP001", "Employee Name", "08:30:00", "17:30:00", "Busan Office"));
        mockRecordsList.add(new AttendanceRecordModel("2024-01-17", "EMP001", "Employee Name", "09:15:00", null, "Daegu Office"));
        
        adapter = new AttendanceRecordAdapter(mockContext);
    }

    @Test
    public void should_returnZero_when_adapterInitialized() {
        // Given & When
        int initialCount = adapter.getItemCount();
        
        // Then
        assertEquals(0, initialCount);
    }

    @Test
    public void should_returnCorrectItemCount_when_recordsSet() {
        // When
        adapter.setRecords(mockRecordsList);
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_setRecordsCalledWithEmptyList() {
        // Given
        List<AttendanceRecordModel> emptyList = new ArrayList<>();
        
        // When
        adapter.setRecords(emptyList);
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_setRecordsCalledWithNull() {
        // Given
        adapter.setRecords(mockRecordsList);
        assertEquals(3, adapter.getItemCount());
        
        // When
        adapter.setRecords(null);
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_updateItemCount_when_setRecordsCalledMultipleTimes() {
        // Given
        adapter.setRecords(mockRecordsList);
        assertEquals(3, adapter.getItemCount());
        
        List<AttendanceRecordModel> newRecords = Arrays.asList(
            new AttendanceRecordModel("2024-01-20", "EMP001", "Employee Name", "08:45:00", "17:45:00", "Incheon Office")
        );
        
        // When
        adapter.setRecords(newRecords);
        
        // Then
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void should_replaceAllRecords_when_setRecordsCalledWithDifferentList() {
        // Given
        adapter.setRecords(mockRecordsList);
        assertEquals(3, adapter.getItemCount());
        
        List<AttendanceRecordModel> differentRecords = Arrays.asList(
            new AttendanceRecordModel("2024-02-01", "EMP001", "Employee Name", "09:30:00", "18:30:00", "Gwangju Office"),
            new AttendanceRecordModel("2024-02-02", "EMP001", "Employee Name", "08:00:00", "16:00:00", "Ulsan Office"),
            new AttendanceRecordModel("2024-02-03", "EMP001", "Employee Name", "10:00:00", "19:00:00", "Jeju Office"),
            new AttendanceRecordModel("2024-02-04", "EMP001", "Employee Name", "09:00:00", null, "Pohang Office"),
            new AttendanceRecordModel("2024-02-05", "EMP001", "Employee Name", "08:30:00", "17:00:00", "Changwon Office")
        );
        
        // When
        adapter.setRecords(differentRecords);
        
        // Then
        assertEquals(5, adapter.getItemCount());
    }

    @Test
    public void should_handleRecordWithNullClockOut_when_recordHasNullClockOut() {
        // Given
        List<AttendanceRecordModel> recordsWithNullClockOut = Arrays.asList(
            new AttendanceRecordModel("2024-01-10", "EMP001", "Employee Name", "09:00:00", null, "Test Office")
        );
        
        // When
        adapter.setRecords(recordsWithNullClockOut);
        
        // Then
        assertEquals(1, adapter.getItemCount());
        // Note: The actual UI display of null clock out (showing "-") would be tested in UI tests
    }

    @Test
    public void should_handleRecordWithEmptyStrings_when_recordHasEmptyValues() {
        // Given
        List<AttendanceRecordModel> recordsWithEmptyValues = Arrays.asList(
            new AttendanceRecordModel("", "EMP001", "Employee Name", "", "", ""),
            new AttendanceRecordModel("2024-01-11", "EMP001", "Employee Name", "09:00:00", "18:00:00", "")
        );
        
        // When
        adapter.setRecords(recordsWithEmptyValues);
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_maintainDataIntegrity_when_originalListModifiedAfterSet() {
        // Given
        List<AttendanceRecordModel> originalList = new ArrayList<>(mockRecordsList);
        adapter.setRecords(originalList);
        assertEquals(3, adapter.getItemCount());
        
        // When - Modify the original list
        originalList.add(new AttendanceRecordModel("2024-01-18", "EMP001", "Employee Name", "10:00:00", "19:00:00", "New Office"));
        
        // Then - Adapter should not be affected (depends on implementation)
        // This test verifies the current behavior - adapter uses reference to the same list
        assertEquals(3, adapter.getItemCount()); // Should remain 3 if implementation copies the list
        // Note: The actual behavior depends on whether setRecords copies the list or keeps reference
    }

    @Test
    public void should_handleMultipleNullClockOuts_when_recordsHaveMultipleNullClockOuts() {
        // Given
        List<AttendanceRecordModel> recordsWithMultipleNulls = Arrays.asList(
            new AttendanceRecordModel("2024-01-01", "EMP001", "Employee Name", "09:00:00", null, "Office A"),
            new AttendanceRecordModel("2024-01-02", "EMP001", "Employee Name", "08:30:00", null, "Office B"),
            new AttendanceRecordModel("2024-01-03", "EMP001", "Employee Name", "10:00:00", "18:00:00", "Office C"),
            new AttendanceRecordModel("2024-01-04", "EMP001", "Employee Name", "09:15:00", null, "Office D")
        );
        
        // When
        adapter.setRecords(recordsWithMultipleNulls);
        
        // Then
        assertEquals(4, adapter.getItemCount());
    }
}