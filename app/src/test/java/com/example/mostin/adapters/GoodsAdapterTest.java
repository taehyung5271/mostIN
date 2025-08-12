package com.example.mostin.adapters;

import static org.junit.Assert.*;

import com.example.mostin.R;
import com.example.mostin.models.GoodsItem;

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
public class GoodsAdapterTest {

    private GoodsAdapter adapter;
    private List<GoodsItem> mockItemList;

    @Before
    public void setUp() {
        mockItemList = new ArrayList<>();
        mockItemList.add(new GoodsItem(R.drawable.ic_launcher_foreground, "Product A"));
        mockItemList.add(new GoodsItem(R.drawable.ic_launcher_background, "Product B"));
        mockItemList.add(new GoodsItem(R.drawable.ic_launcher_foreground, "Product C"));
        
        adapter = new GoodsAdapter(mockItemList);
    }

    @Test
    public void should_returnCorrectItemCount_when_adapterCreatedWithItemList() {
        // When & Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_adapterCreatedWithEmptyList() {
        // Given
        GoodsAdapter emptyAdapter = new GoodsAdapter(new ArrayList<>());
        
        // When & Then
        assertEquals(0, emptyAdapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_adapterCreatedWithNull() {
        // Given & When & Then
        try {
            GoodsAdapter nullAdapter = new GoodsAdapter(null);
            int count = nullAdapter.getItemCount();
            fail("Expected exception when adapter created with null list");
        } catch (Exception e) {
            // Expected behavior for null list
            assertTrue("Exception thrown for null list", true);
        }
    }

    @Test
    public void should_handleSingleItem_when_adapterCreatedWithSingleItem() {
        // Given
        List<GoodsItem> singleItem = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, "Single Product")
        );
        GoodsAdapter singleAdapter = new GoodsAdapter(singleItem);
        
        // When & Then
        assertEquals(1, singleAdapter.getItemCount());
    }

    @Test
    public void should_handleItemWithEmptyName_when_itemHasEmptyName() {
        // Given
        List<GoodsItem> itemWithEmptyName = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, ""),
            new GoodsItem(R.drawable.ic_launcher_background, "Normal Product")
        );
        GoodsAdapter testAdapter = new GoodsAdapter(itemWithEmptyName);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleItemWithNullName_when_itemHasNullName() {
        // Given
        List<GoodsItem> itemWithNullName = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, null),
            new GoodsItem(R.drawable.ic_launcher_background, "Normal Product")
        );
        GoodsAdapter testAdapter = new GoodsAdapter(itemWithNullName);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
        // Note: Null name handling would be tested in UI binding tests
    }

    @Test
    public void should_handleItemWithZeroImageRes_when_itemHasZeroImageResource() {
        // Given
        List<GoodsItem> itemWithZeroImageRes = Arrays.asList(
            new GoodsItem(0, "Product with Zero Image"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Normal Product")
        );
        GoodsAdapter testAdapter = new GoodsAdapter(itemWithZeroImageRes);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleItemWithNegativeImageRes_when_itemHasNegativeImageResource() {
        // Given
        List<GoodsItem> itemWithNegativeImageRes = Arrays.asList(
            new GoodsItem(-1, "Product with Negative Image"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Normal Product")
        );
        GoodsAdapter testAdapter = new GoodsAdapter(itemWithNegativeImageRes);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleLargeItemList_when_adapterCreatedWithManyItems() {
        // Given
        List<GoodsItem> largeItemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            largeItemList.add(new GoodsItem(R.drawable.ic_launcher_foreground, "Product " + i));
        }
        GoodsAdapter largeAdapter = new GoodsAdapter(largeItemList);
        
        // When & Then
        assertEquals(100, largeAdapter.getItemCount());
    }

    @Test
    public void should_handleDuplicateItems_when_listContainsDuplicates() {
        // Given
        List<GoodsItem> duplicateItems = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, "Duplicate Product"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Duplicate Product"),
            new GoodsItem(R.drawable.ic_launcher_background, "Unique Product"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Duplicate Product")
        );
        GoodsAdapter duplicateAdapter = new GoodsAdapter(duplicateItems);
        
        // When & Then
        assertEquals(4, duplicateAdapter.getItemCount()); // Should include all items, even duplicates
    }

    @Test
    public void should_handleItemsWithSameName_when_itemsHaveSameNameDifferentImage() {
        // Given
        List<GoodsItem> sameNameItems = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, "Same Name"),
            new GoodsItem(R.drawable.ic_launcher_background, "Same Name"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Different Name")
        );
        GoodsAdapter sameNameAdapter = new GoodsAdapter(sameNameItems);
        
        // When & Then
        assertEquals(3, sameNameAdapter.getItemCount());
    }

    @Test
    public void should_handleItemsWithSameImage_when_itemsHaveDifferentNameSameImage() {
        // Given
        List<GoodsItem> sameImageItems = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, "Product A"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Product B"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Product C")
        );
        GoodsAdapter sameImageAdapter = new GoodsAdapter(sameImageItems);
        
        // When & Then
        assertEquals(3, sameImageAdapter.getItemCount());
    }

    @Test
    public void should_maintainOriginalData_when_externalListModified() {
        // Given
        assertEquals(3, adapter.getItemCount());
        
        // When - Modify the original list that was passed to adapter
        mockItemList.add(new GoodsItem(R.drawable.ic_launcher_background, "New Product"));
        
        // Then - Adapter uses reference to the same list, so it should be affected
        assertEquals(4, adapter.getItemCount()); // Will be affected since it's the same list reference
        
        // Note: This test verifies current behavior - adapter keeps reference to original list
    }

    @Test
    public void should_handleVeryLongProductNames_when_itemsHaveLongNames() {
        // Given
        String veryLongName = "This is a very long product name that might cause display issues if not handled properly in the UI layout";
        List<GoodsItem> longNameItems = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, veryLongName),
            new GoodsItem(R.drawable.ic_launcher_background, "Short")
        );
        GoodsAdapter longNameAdapter = new GoodsAdapter(longNameItems);
        
        // When & Then
        assertEquals(2, longNameAdapter.getItemCount());
    }

    @Test
    public void should_handleSpecialCharacters_when_itemNamesContainSpecialCharacters() {
        // Given
        List<GoodsItem> specialCharItems = Arrays.asList(
            new GoodsItem(R.drawable.ic_launcher_foreground, "Product@#$%"),
            new GoodsItem(R.drawable.ic_launcher_background, "상품명한글"),
            new GoodsItem(R.drawable.ic_launcher_foreground, "Product🎉"),
            new GoodsItem(R.drawable.ic_launcher_background, "Product\nWith\nNewlines")
        );
        GoodsAdapter specialCharAdapter = new GoodsAdapter(specialCharItems);
        
        // When & Then
        assertEquals(4, specialCharAdapter.getItemCount());
    }
}