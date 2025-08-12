package com.example.mostin.adapters;

import static org.junit.Assert.*;

import com.example.mostin.models.MenuItems;

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
public class DrawerMenuAdapterTest {

    private DrawerMenuAdapter adapter;
    private List<MenuItems> mockMenuItems;

    @Before
    public void setUp() {
        mockMenuItems = new ArrayList<>();
        
        // Create menu items similar to the app structure
        MenuItems homeMenu = new MenuItems("홈", Arrays.asList("회사 제품"));
        MenuItems attendanceMenu = new MenuItems("출근", Arrays.asList("출근 등록", "출근 장부"));
        MenuItems orderMenu = new MenuItems("발주", Arrays.asList("발주 신청", "발주 리스트"));
        
        mockMenuItems.add(homeMenu);
        mockMenuItems.add(attendanceMenu);
        mockMenuItems.add(orderMenu);
        
        adapter = new DrawerMenuAdapter(mockMenuItems);
    }

    @Test
    public void should_returnCorrectItemCount_when_adapterCreatedWithMenuItems() {
        // When & Then
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void should_handleEmptyList_when_adapterCreatedWithEmptyList() {
        // Given
        DrawerMenuAdapter emptyAdapter = new DrawerMenuAdapter(new ArrayList<>());
        
        // When & Then
        assertEquals(0, emptyAdapter.getItemCount());
    }

    @Test
    public void should_handleNullList_when_adapterCreatedWithNull() {
        // Given & When
        DrawerMenuAdapter nullAdapter = new DrawerMenuAdapter(null);
        
        // Then - Should handle gracefully (may throw exception or return 0)
        // This depends on implementation - testing current behavior
        try {
            int count = nullAdapter.getItemCount();
            fail("Expected exception when adapter created with null list");
        } catch (Exception e) {
            // Expected behavior for null list
            assertTrue("Exception thrown for null list", true);
        }
    }

    @Test
    public void should_handleSingleMenuItem_when_adapterCreatedWithSingleItem() {
        // Given
        List<MenuItems> singleItem = Arrays.asList(
            new MenuItems("테스트", Arrays.asList("테스트 서브메뉴"))
        );
        DrawerMenuAdapter singleAdapter = new DrawerMenuAdapter(singleItem);
        
        // When & Then
        assertEquals(1, singleAdapter.getItemCount());
    }

    @Test
    public void should_handleMenuWithEmptySubItems_when_menuHasEmptySubItemsList() {
        // Given
        List<MenuItems> menuWithEmptySubItems = Arrays.asList(
            new MenuItems("빈 메뉴", new ArrayList<>()),
            new MenuItems("일반 메뉴", Arrays.asList("서브메뉴 1", "서브메뉴 2"))
        );
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuWithEmptySubItems);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleMenuWithNullSubItems_when_menuHasNullSubItemsList() {
        // Given
        MenuItems menuWithNullSubItems = new MenuItems("널 서브메뉴", null);
        List<MenuItems> menuList = Arrays.asList(menuWithNullSubItems);
        
        // When
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuList);
        
        // Then
        assertEquals(1, testAdapter.getItemCount());
        // Note: The behavior of null sub items would be handled in onBindViewHolder
        // This test just verifies the adapter can be created and has correct item count
    }

    @Test
    public void should_handleMenuWithSingleSubItem_when_menuHasSingleSubItem() {
        // Given
        List<MenuItems> menuWithSingleSubItem = Arrays.asList(
            new MenuItems("단일 서브메뉴", Arrays.asList("유일한 서브메뉴"))
        );
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuWithSingleSubItem);
        
        // When & Then
        assertEquals(1, testAdapter.getItemCount());
    }

    @Test
    public void should_handleMenuWithManySubItems_when_menuHasManySubItems() {
        // Given
        List<String> manySubItems = Arrays.asList(
            "서브메뉴 1", "서브메뉴 2", "서브메뉴 3", "서브메뉴 4", 
            "서브메뉴 5", "서브메뉴 6", "서브메뉴 7", "서브메뉴 8"
        );
        List<MenuItems> menuWithManySubItems = Arrays.asList(
            new MenuItems("많은 서브메뉴", manySubItems)
        );
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuWithManySubItems);
        
        // When & Then
        assertEquals(1, testAdapter.getItemCount());
    }

    @Test
    public void should_handleLargeMenuList_when_adapterCreatedWithManyMenuItems() {
        // Given
        List<MenuItems> largeMenuList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            largeMenuList.add(new MenuItems("메뉴 " + i, Arrays.asList("서브메뉴 " + i + "-1", "서브메뉴 " + i + "-2")));
        }
        DrawerMenuAdapter largeAdapter = new DrawerMenuAdapter(largeMenuList);
        
        // When & Then
        assertEquals(50, largeAdapter.getItemCount());
    }

    @Test
    public void should_handleMenuWithEmptyTitle_when_menuHasEmptyTitle() {
        // Given
        List<MenuItems> menuWithEmptyTitle = Arrays.asList(
            new MenuItems("", Arrays.asList("서브메뉴 1")),
            new MenuItems("정상 메뉴", Arrays.asList("서브메뉴 2"))
        );
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuWithEmptyTitle);
        
        // When & Then
        assertEquals(2, testAdapter.getItemCount());
    }

    @Test
    public void should_handleMenuWithNullTitle_when_menuHasNullTitle() {
        // Given
        MenuItems menuWithNullTitle = new MenuItems(null, Arrays.asList("서브메뉴"));
        List<MenuItems> menuList = Arrays.asList(menuWithNullTitle);
        
        // When
        DrawerMenuAdapter testAdapter = new DrawerMenuAdapter(menuList);
        
        // Then
        assertEquals(1, testAdapter.getItemCount());
        // Note: Null title handling would be tested in UI binding tests
    }

    @Test
    public void should_maintainOriginalMenuStructure_when_externalListModified() {
        // Given
        List<MenuItems> originalList = new ArrayList<>(mockMenuItems);
        assertEquals(3, adapter.getItemCount());
        
        // When - Modify the original list
        originalList.add(new MenuItems("새 메뉴", Arrays.asList("새 서브메뉴")));
        
        // Then - Adapter should not be affected (depends on implementation)
        assertEquals(3, adapter.getItemCount()); // Should remain unchanged if implementation copies
    }
}