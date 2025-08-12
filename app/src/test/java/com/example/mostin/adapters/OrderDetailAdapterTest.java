package com.example.mostin.adapters;

import static org.junit.Assert.*;

import com.example.mostin.models.OrderDetailModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class OrderDetailAdapterTest {

    private OrderDetailAdapter adapter;
    private List<OrderDetailModel> mockOrderDetails;

    @Before
    public void setUp() {
        mockOrderDetails = new ArrayList<>();
        mockOrderDetails.add(new OrderDetailModel("Product A", 5));
        mockOrderDetails.add(new OrderDetailModel("Product B", 10));
        mockOrderDetails.add(new OrderDetailModel("Product C", 3));
        
        adapter = new OrderDetailAdapter();
    }

    @Test
    public void should_returnZero_when_adapterInitialized() {
        // Given & When
        int initialCount = adapter.getItemCount();
        
        // Then
        assertEquals(0, initialCount);
    }

    @Test
    public void should_returnCorrectItemCount_when_orderDetailsSet() {
        // When
        adapter.setOrderDetails(mockOrderDetails);
        
        // Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_setOrderDetailsCalledWithEmptyList() {
        // Given
        List<OrderDetailModel> emptyList = new ArrayList<>();
        
        // When
        adapter.setOrderDetails(emptyList);
        
        // Then
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_setOrderDetailsCalledWithNull() {
        // Given
        adapter.setOrderDetails(mockOrderDetails);
        assertEquals(3, adapter.getItemCount());
        
        // When
        adapter.setOrderDetails(null);
        
        // Then
        assertEquals(0, adapter.getItemCount()); // Should create empty list
    }

    @Test
    public void should_replaceAllDetails_when_setOrderDetailsCalledMultipleTimes() {
        // Given
        adapter.setOrderDetails(mockOrderDetails);
        assertEquals(3, adapter.getItemCount());
        
        List<OrderDetailModel> newDetails = Arrays.asList(
            new OrderDetailModel("New Product", 7)
        );
        
        // When
        adapter.setOrderDetails(newDetails);
        
        // Then
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void should_handleSingleDetail_when_setOrderDetailsCalledWithSingleItem() {
        // Given
        List<OrderDetailModel> singleDetail = Arrays.asList(
            new OrderDetailModel("Single Product", 1)
        );
        
        // When
        adapter.setOrderDetails(singleDetail);
        
        // Then
        assertEquals(1, adapter.getItemCount());
    }

    @Test
    public void should_handleLargeDetailList_when_setOrderDetailsCalledWithManyItems() {
        // Given
        List<OrderDetailModel> largeDetailList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            largeDetailList.add(new OrderDetailModel("Product " + i, i + 1));
        }
        
        // When
        adapter.setOrderDetails(largeDetailList);
        
        // Then
        assertEquals(50, adapter.getItemCount());
    }

    @Test
    public void should_handleZeroBoxCount_when_detailHasZeroBoxCount() {
        // Given
        List<OrderDetailModel> detailsWithZero = Arrays.asList(
            new OrderDetailModel("Zero Box Product", 0),
            new OrderDetailModel("Normal Product", 5)
        );
        
        // When
        adapter.setOrderDetails(detailsWithZero);
        
        // Then
        assertEquals(2, adapter.getItemCount());
        // Note: Display of "0박스" would be tested in UI tests
    }

    @Test
    public void should_handleNegativeBoxCount_when_detailHasNegativeBoxCount() {
        // Given
        List<OrderDetailModel> detailsWithNegative = Arrays.asList(
            new OrderDetailModel("Negative Box Product", -5),
            new OrderDetailModel("Normal Product", 3)
        );
        
        // When
        adapter.setOrderDetails(detailsWithNegative);
        
        // Then
        assertEquals(2, adapter.getItemCount());
        // Note: Display of negative values would be handled in UI tests
    }

    @Test
    public void should_handleLargeBoxCount_when_detailHasLargeBoxCount() {
        // Given
        List<OrderDetailModel> detailsWithLarge = Arrays.asList(
            new OrderDetailModel("Large Box Product", 999999),
            new OrderDetailModel("Normal Product", 2)
        );
        
        // When
        adapter.setOrderDetails(detailsWithLarge);
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyGoodsName_when_detailHasEmptyGoodsName() {
        // Given
        List<OrderDetailModel> detailsWithEmptyName = Arrays.asList(
            new OrderDetailModel("", 5),
            new OrderDetailModel("Normal Product", 3)
        );
        
        // When
        adapter.setOrderDetails(detailsWithEmptyName);
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_handleNullGoodsName_when_detailHasNullGoodsName() {
        // Given
        List<OrderDetailModel> detailsWithNullName = Arrays.asList(
            new OrderDetailModel(null, 5),
            new OrderDetailModel("Normal Product", 3)
        );
        
        // When
        adapter.setOrderDetails(detailsWithNullName);
        
        // Then
        assertEquals(2, adapter.getItemCount());
        // Note: Null name handling would be tested in UI binding tests
    }

    @Test
    public void should_handleDuplicateProducts_when_detailsContainDuplicates() {
        // Given
        List<OrderDetailModel> duplicateDetails = Arrays.asList(
            new OrderDetailModel("Duplicate Product", 3),
            new OrderDetailModel("Duplicate Product", 5),
            new OrderDetailModel("Unique Product", 2),
            new OrderDetailModel("Duplicate Product", 7)
        );
        
        // When
        adapter.setOrderDetails(duplicateDetails);
        
        // Then
        assertEquals(4, adapter.getItemCount()); // Should include all items, even duplicates
    }

    @Test
    public void should_maintainOrderDetailsData_when_externalListModified() {
        // Given
        List<OrderDetailModel> originalList = new ArrayList<>(mockOrderDetails);
        adapter.setOrderDetails(originalList);
        assertEquals(3, adapter.getItemCount());
        
        // When - Modify the original list
        originalList.add(new OrderDetailModel("New Product", 8));
        
        // Then - Adapter should not be affected since it should create its own copy
        assertEquals(3, adapter.getItemCount()); // Should remain unchanged
    }

    @Test
    public void should_handleSpecialCharactersInName_when_goodsNameContainsSpecialCharacters() {
        // Given
        List<OrderDetailModel> specialCharDetails = Arrays.asList(
            new OrderDetailModel("Product@#$%", 1),
            new OrderDetailModel("상품명한글", 2),
            new OrderDetailModel("Product🎉", 3),
            new OrderDetailModel("Product\nWith\nNewlines", 4)
        );
        
        // When
        adapter.setOrderDetails(specialCharDetails);
        
        // Then
        assertEquals(4, adapter.getItemCount());
    }

    @Test
    public void should_handleVeryLongGoodsName_when_goodsNameIsVeryLong() {
        // Given
        String veryLongName = "This is a very long product name that might cause display issues if not handled properly in the UI layout and text views";
        List<OrderDetailModel> longNameDetails = Arrays.asList(
            new OrderDetailModel(veryLongName, 1),
            new OrderDetailModel("Short", 2)
        );
        
        // When
        adapter.setOrderDetails(longNameDetails);
        
        // Then
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void should_resetToEmptyAfterNull_when_setOrderDetailsCalledWithNullThenWithData() {
        // Given
        adapter.setOrderDetails(mockOrderDetails);
        assertEquals(3, adapter.getItemCount());
        
        // When
        adapter.setOrderDetails(null);
        assertEquals(0, adapter.getItemCount());
        
        List<OrderDetailModel> newDetails = Arrays.asList(
            new OrderDetailModel("Recovery Product", 1)
        );
        adapter.setOrderDetails(newDetails);
        
        // Then
        assertEquals(1, adapter.getItemCount());
    }
}