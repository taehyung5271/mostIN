package com.example.mostin.adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.mostin.models.GoodsModel;

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
public class OrderingAdapterTest {

    private OrderingAdapter adapter;
    private List<GoodsModel> mockGoodsList;

    @Mock
    private OrderingAdapter.OnCopyClickListener mockListener;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockGoodsList = new ArrayList<>();
        mockGoodsList.add(new GoodsModel("123456", "Product A"));
        mockGoodsList.add(new GoodsModel("789012", "Product B"));
        mockGoodsList.add(new GoodsModel("345678", "Product C"));
        
        adapter = new OrderingAdapter(mockGoodsList, mockListener);
    }

    @Test
    public void should_returnCorrectItemCount_when_adapterCreatedWithGoodsList() {
        // When & Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_adapterCreatedWithEmptyList() {
        // Given
        OrderingAdapter emptyAdapter = new OrderingAdapter(new ArrayList<>(), mockListener);
        
        // When & Then
        assertEquals(0, emptyAdapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_adapterCreatedWithNullList() {
        // Given & When
        OrderingAdapter nullAdapter = new OrderingAdapter(null, mockListener);
        
        // Then - Should handle null by creating empty list
        assertEquals(0, nullAdapter.getItemCount());
    }

    @Test
    public void should_handleNullListener_when_adapterCreatedWithNullListener() {
        // Given & When
        OrderingAdapter nullListenerAdapter = new OrderingAdapter(mockGoodsList, null);
        
        // Then
        assertEquals(3, nullListenerAdapter.getItemCount()); // Should work normally
    }

    @Test
    public void should_handleSingleItem_when_adapterCreatedWithSingleItem() {
        // Given
        List<GoodsModel> singleItem = Arrays.asList(
            new GoodsModel("111111", "Single Product")
        );
        OrderingAdapter singleAdapter = new OrderingAdapter(singleItem, mockListener);
        
        // When & Then
        assertEquals(1, singleAdapter.getItemCount());
    }

    @Test
    public void should_handleLargeGoodsList_when_adapterCreatedWithManyItems() {
        // Given
        List<GoodsModel> largeGoodsList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largeGoodsList.add(new GoodsModel("BARCODE" + i, "Product " + i));
        }
        OrderingAdapter largeAdapter = new OrderingAdapter(largeGoodsList, mockListener);
        
        // When & Then
        assertEquals(100, largeAdapter.getItemCount());
    }

    @Test
    public void should_handleGoodsWithEmptyBarcode_when_goodsHasEmptyBarcode() {
        // Given
        List<GoodsModel> goodsWithEmptyBarcode = Arrays.asList(
            new GoodsModel("", "Product with Empty Barcode"),
            new GoodsModel("123456", "Normal Product")
        );
        OrderingAdapter testAdapter = new OrderingAdapter(goodsWithEmptyBarcode, mockListener);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleGoodsWithNullBarcode_when_goodsHasNullBarcode() {
        // Given
        List<GoodsModel> goodsWithNullBarcode = Arrays.asList(
            new GoodsModel(null, "Product with Null Barcode"),
            new GoodsModel("123456", "Normal Product")
        );
        OrderingAdapter testAdapter = new OrderingAdapter(goodsWithNullBarcode, mockListener);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
        // Note: Null barcode handling in UI would be tested in integration tests
    }

    @Test
    public void should_handleGoodsWithEmptyName_when_goodsHasEmptyName() {
        // Given
        List<GoodsModel> goodsWithEmptyName = Arrays.asList(
            new GoodsModel("123456", ""),
            new GoodsModel("789012", "Normal Product")
        );
        OrderingAdapter testAdapter = new OrderingAdapter(goodsWithEmptyName, mockListener);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleGoodsWithNullName_when_goodsHasNullName() {
        // Given
        List<GoodsModel> goodsWithNullName = Arrays.asList(
            new GoodsModel("123456", null),
            new GoodsModel("789012", "Normal Product")
        );
        OrderingAdapter testAdapter = new OrderingAdapter(goodsWithNullName, mockListener);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
        // Note: Null name handling in UI would be tested in integration tests
    }

    @Test
    public void should_handleDuplicateGoods_when_goodsListContainsDuplicates() {
        // Given
        List<GoodsModel> duplicateGoods = Arrays.asList(
            new GoodsModel("123456", "Duplicate Product"),
            new GoodsModel("123456", "Duplicate Product"),
            new GoodsModel("789012", "Unique Product"),
            new GoodsModel("123456", "Duplicate Product")
        );
        OrderingAdapter duplicateAdapter = new OrderingAdapter(duplicateGoods, mockListener);
        
        // When & Then
        assertEquals(4, duplicateAdapter.getItemCount()); // Should include all items, even duplicates
    }

    @Test
    public void should_handleGoodsWithSameBarcode_when_goodsHaveSameBarcodeDifferentName() {
        // Given
        List<GoodsModel> sameBarcodeGoods = Arrays.asList(
            new GoodsModel("123456", "Product A"),
            new GoodsModel("123456", "Product B"),
            new GoodsModel("789012", "Product C")
        );
        OrderingAdapter sameBarcodeAdapter = new OrderingAdapter(sameBarcodeGoods, mockListener);
        
        // When & Then
        assertEquals(3, sameBarcodeAdapter.getItemCount());
    }

    @Test
    public void should_handleGoodsWithSameName_when_goodsHaveSameNameDifferentBarcode() {
        // Given
        List<GoodsModel> sameNameGoods = Arrays.asList(
            new GoodsModel("123456", "Same Product"),
            new GoodsModel("789012", "Same Product"),
            new GoodsModel("345678", "Different Product")
        );
        OrderingAdapter sameNameAdapter = new OrderingAdapter(sameNameGoods, mockListener);
        
        // When & Then
        assertEquals(3, sameNameAdapter.getItemCount());
    }

    @Test
    public void should_handleSpecialCharactersInBarcode_when_barcodeContainsSpecialCharacters() {
        // Given
        List<GoodsModel> specialBarcodeGoods = Arrays.asList(
            new GoodsModel("123-456-789", "Product with Dash Barcode"),
            new GoodsModel("ABC123DEF", "Product with Letter Barcode"),
            new GoodsModel("123@456#789", "Product with Special Char Barcode")
        );
        OrderingAdapter specialBarcodeAdapter = new OrderingAdapter(specialBarcodeGoods, mockListener);
        
        // When & Then
        assertEquals(3, specialBarcodeAdapter.getItemCount());
    }

    @Test
    public void should_handleSpecialCharactersInName_when_nameContainsSpecialCharacters() {
        // Given
        List<GoodsModel> specialNameGoods = Arrays.asList(
            new GoodsModel("123456", "Product@#$%"),
            new GoodsModel("789012", "상품명한글"),
            new GoodsModel("345678", "Product🎉"),
            new GoodsModel("901234", "Product\nWith\nNewlines")
        );
        OrderingAdapter specialNameAdapter = new OrderingAdapter(specialNameGoods, mockListener);
        
        // When & Then
        assertEquals(4, specialNameAdapter.getItemCount());
    }

    @Test
    public void should_handleVeryLongNames_when_goodsHaveVeryLongNames() {
        // Given
        String veryLongName = "This is a very long product name that might cause display issues if not handled properly in the UI layout and text views";
        List<GoodsModel> longNameGoods = Arrays.asList(
            new GoodsModel("123456", veryLongName),
            new GoodsModel("789012", "Short")
        );
        OrderingAdapter longNameAdapter = new OrderingAdapter(longNameGoods, mockListener);
        
        // When & Then
        assertEquals(2, longNameAdapter.getItemCount());
    }

    @Test
    public void should_handleVeryLongBarcodes_when_goodsHaveVeryLongBarcodes() {
        // Given
        String veryLongBarcode = "123456789012345678901234567890123456789012345678901234567890";
        List<GoodsModel> longBarcodeGoods = Arrays.asList(
            new GoodsModel(veryLongBarcode, "Product with Long Barcode"),
            new GoodsModel("123", "Product with Short Barcode")
        );
        OrderingAdapter longBarcodeAdapter = new OrderingAdapter(longBarcodeGoods, mockListener);
        
        // When & Then
        assertEquals(2, longBarcodeAdapter.getItemCount());
    }

    @Test
    public void should_maintainOriginalData_when_externalListModified() {
        // Given
        assertEquals(3, adapter.getItemCount());
        
        // When - Modify the original list that was passed to adapter
        mockGoodsList.add(new GoodsModel("999999", "New Product"));
        
        // Then - Adapter creates a copy, so external modifications don't affect it
        assertEquals(3, adapter.getItemCount()); // Not affected since adapter has its own copy
        
        // Note: This test verifies current behavior - adapter creates defensive copy
    }

    @Test
    public void should_handleEmptyAndNullValues_when_goodsHaveMixedEmptyAndNullValues() {
        // Given
        List<GoodsModel> mixedGoods = Arrays.asList(
            new GoodsModel("", ""),
            new GoodsModel(null, null),
            new GoodsModel("123456", ""),
            new GoodsModel("", "Product Name"),
            new GoodsModel("789012", "Normal Product")
        );
        OrderingAdapter mixedAdapter = new OrderingAdapter(mixedGoods, mockListener);
        
        // When & Then
        assertEquals(5, mixedAdapter.getItemCount());
    }

    @Test
    public void should_initializeCorrectly_when_bothListAndListenerProvided() {
        // Given & When
        OrderingAdapter testAdapter = new OrderingAdapter(mockGoodsList, mockListener);
        
        // Then
        assertEquals(3, testAdapter.getItemCount());
        assertNotNull("Adapter should be created successfully", testAdapter);
    }
}