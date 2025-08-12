package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class MenuItemsTest {

    @Test
    public void should_createMenuItems_when_validDataProvided() {
        // Given
        String expectedTitle = "관리자 메뉴";
        List<String> expectedSubItems = Arrays.asList("직원 관리", "상품 관리", "출근 관리");

        // When
        MenuItems menuItems = new MenuItems(expectedTitle, expectedSubItems);

        // Then
        assertEquals(expectedTitle, menuItems.getTitle());
        assertEquals(expectedSubItems, menuItems.getSubItems());
        assertEquals(3, menuItems.getSubItems().size());
    }

    @Test
    public void should_preserveSubItemOrder_when_multipleSubItemsProvided() {
        // Given
        String title = "사용자 메뉴";
        List<String> subItems = Arrays.asList("첫번째", "두번째", "세번째", "네번째");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals("첫번째", menuItems.getSubItems().get(0));
        assertEquals("두번째", menuItems.getSubItems().get(1));
        assertEquals("세번째", menuItems.getSubItems().get(2));
        assertEquals("네번째", menuItems.getSubItems().get(3));
    }

    @Test
    public void should_handleEmptySubItems_when_emptyListProvided() {
        // Given
        String title = "빈 메뉴";
        List<String> emptySubItems = new ArrayList<>();

        // When
        MenuItems menuItems = new MenuItems(title, emptySubItems);

        // Then
        assertEquals(title, menuItems.getTitle());
        assertEquals(emptySubItems, menuItems.getSubItems());
        assertEquals(0, menuItems.getSubItems().size());
        assertTrue(menuItems.getSubItems().isEmpty());
    }

    @Test
    public void should_handleNullTitle_when_nullTitleProvided() {
        // Given
        String title = null;
        List<String> subItems = Arrays.asList("항목1", "항목2");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertNull(menuItems.getTitle());
        assertEquals(subItems, menuItems.getSubItems());
    }

    @Test
    public void should_handleNullSubItems_when_nullSubItemsProvided() {
        // Given
        String title = "Null SubItems 메뉴";
        List<String> subItems = null;

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals(title, menuItems.getTitle());
        assertNull(menuItems.getSubItems());
    }

    @Test
    public void should_handleNullSubItemsInList_when_listContainsNullItems() {
        // Given
        String title = "Null을 포함한 메뉴";
        List<String> subItems = Arrays.asList("유효항목1", null, "유효항목2", null);

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals(title, menuItems.getTitle());
        assertEquals(4, menuItems.getSubItems().size());
        assertEquals("유효항목1", menuItems.getSubItems().get(0));
        assertNull(menuItems.getSubItems().get(1));
        assertEquals("유효항목2", menuItems.getSubItems().get(2));
        assertNull(menuItems.getSubItems().get(3));
    }

    @Test
    public void should_handleEmptyStringTitle_when_emptyTitleProvided() {
        // Given
        String title = "";
        List<String> subItems = Arrays.asList("항목1", "항목2");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals("", menuItems.getTitle());
        assertEquals(subItems, menuItems.getSubItems());
    }

    @Test
    public void should_handleEmptyStringSubItems_when_emptyStringsInList() {
        // Given
        String title = "빈 문자열 포함 메뉴";
        List<String> subItems = Arrays.asList("", "유효항목", "", "");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals(title, menuItems.getTitle());
        assertEquals(4, menuItems.getSubItems().size());
        assertEquals("", menuItems.getSubItems().get(0));
        assertEquals("유효항목", menuItems.getSubItems().get(1));
        assertEquals("", menuItems.getSubItems().get(2));
        assertEquals("", menuItems.getSubItems().get(3));
    }

    @Test
    public void should_preserveSpecialCharacters_when_specialCharsInTitleAndSubItems() {
        // Given
        String title = "특수문자@#$% 메뉴";
        List<String> subItems = Arrays.asList("항목&*", "항목()[]", "항목{}|");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals("특수문자@#$% 메뉴", menuItems.getTitle());
        assertEquals("항목&*", menuItems.getSubItems().get(0));
        assertEquals("항목()[]", menuItems.getSubItems().get(1));
        assertEquals("항목{}|", menuItems.getSubItems().get(2));
    }

    @Test
    public void should_handleSingleSubItem_when_oneSubItemProvided() {
        // Given
        String title = "단일 항목 메뉴";
        List<String> subItems = Arrays.asList("유일한 항목");

        // When
        MenuItems menuItems = new MenuItems(title, subItems);

        // Then
        assertEquals(title, menuItems.getTitle());
        assertEquals(1, menuItems.getSubItems().size());
        assertEquals("유일한 항목", menuItems.getSubItems().get(0));
    }
}