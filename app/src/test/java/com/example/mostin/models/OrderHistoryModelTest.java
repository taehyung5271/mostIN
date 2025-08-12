package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderHistoryModelTest {

    @Test
    public void should_createOrderHistoryWithFirstConstructor_when_goodsDataProvided() {
        // Given
        String expectedOrderingDay = "2024-03-15";
        String expectedBarcode = "1234567890123";
        String expectedGoodsName = "사과";
        int expectedBoxCount = 10;

        // When
        OrderHistoryModel orderHistory = new OrderHistoryModel(
            expectedOrderingDay, expectedBarcode, expectedGoodsName, expectedBoxCount
        );

        // Then
        assertEquals(expectedOrderingDay, orderHistory.getOrderingDay());
        assertEquals(expectedBarcode, orderHistory.getBarcode());
        assertEquals(expectedGoodsName, orderHistory.getGoodsName());
        assertEquals(expectedBoxCount, orderHistory.getBoxCount());
        
        // 첫 번째 생성자에서는 직원 정보가 설정되지 않음
        assertNull(orderHistory.getEmployeeId());
        assertNull(orderHistory.getEmployeeName());
        assertNull(orderHistory.getWorkPlaceName());
    }

    @Test
    public void should_createOrderHistoryWithSecondConstructor_when_employeeDataProvided() {
        // Given
        String expectedOrderingDay = "2024-03-15";
        String expectedEmployeeId = "EMP001";
        String expectedEmployeeName = "김철수";
        String expectedWorkPlaceName = "서울지점";

        // When
        OrderHistoryModel orderHistory = new OrderHistoryModel(
            expectedOrderingDay, expectedEmployeeId, expectedEmployeeName, expectedWorkPlaceName
        );

        // Then
        assertEquals(expectedOrderingDay, orderHistory.getOrderingDay());
        assertEquals(expectedEmployeeId, orderHistory.getEmployeeId());
        assertEquals(expectedEmployeeName, orderHistory.getEmployeeName());
        assertEquals(expectedWorkPlaceName, orderHistory.getWorkPlaceName());
        
        // 두 번째 생성자에서는 상품 정보가 설정되지 않음
        assertNull(orderHistory.getBarcode());
        assertNull(orderHistory.getGoodsName());
        assertEquals(0, orderHistory.getBoxCount()); // int 기본값
    }

    @Test
    public void should_handleNullValues_when_nullDataProvidedInFirstConstructor() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel(null, null, null, 5);

        // Then
        assertNull(orderHistory.getOrderingDay());
        assertNull(orderHistory.getBarcode());
        assertNull(orderHistory.getGoodsName());
        assertEquals(5, orderHistory.getBoxCount());
    }

    @Test
    public void should_handleNullValues_when_nullDataProvidedInSecondConstructor() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel(null, null, null, null);

        // Then
        assertNull(orderHistory.getOrderingDay());
        assertNull(orderHistory.getEmployeeId());
        assertNull(orderHistory.getEmployeeName());
        assertNull(orderHistory.getWorkPlaceName());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvidedInFirstConstructor() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel("", "", "", 0);

        // Then
        assertEquals("", orderHistory.getOrderingDay());
        assertEquals("", orderHistory.getBarcode());
        assertEquals("", orderHistory.getGoodsName());
        assertEquals(0, orderHistory.getBoxCount());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvidedInSecondConstructor() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel("", "", "", "");

        // Then
        assertEquals("", orderHistory.getOrderingDay());
        assertEquals("", orderHistory.getEmployeeId());
        assertEquals("", orderHistory.getEmployeeName());
        assertEquals("", orderHistory.getWorkPlaceName());
    }

    @Test
    public void should_handleNegativeBoxCount_when_negativeCountProvided() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel(
            "2024-03-15", "1111111111111", "바나나", -10
        );

        // Then
        assertEquals("2024-03-15", orderHistory.getOrderingDay());
        assertEquals("1111111111111", orderHistory.getBarcode());
        assertEquals("바나나", orderHistory.getGoodsName());
        assertEquals(-10, orderHistory.getBoxCount());
    }

    @Test
    public void should_handleLargeBoxCount_when_largeCountProvided() {
        // Given & When
        OrderHistoryModel orderHistory = new OrderHistoryModel(
            "2024-03-15", "2222222222222", "오렌지", 999999
        );

        // Then
        assertEquals("2024-03-15", orderHistory.getOrderingDay());
        assertEquals("2222222222222", orderHistory.getBarcode());
        assertEquals("오렌지", orderHistory.getGoodsName());
        assertEquals(999999, orderHistory.getBoxCount());
    }

    @Test
    public void should_preserveSpecialCharacters_when_specialCharsInData() {
        // Given
        String orderingDay = "2024-12-25";
        String barcode = "123-456-789-012";
        String goodsName = "특수상품@#$%";
        int boxCount = 7;

        // When
        OrderHistoryModel orderHistory = new OrderHistoryModel(orderingDay, barcode, goodsName, boxCount);

        // Then
        assertEquals("2024-12-25", orderHistory.getOrderingDay());
        assertEquals("123-456-789-012", orderHistory.getBarcode());
        assertEquals("특수상품@#$%", orderHistory.getGoodsName());
        assertEquals(7, orderHistory.getBoxCount());
    }

    @Test
    public void should_handleLongWorkPlaceName_when_longNameProvided() {
        // Given
        String longWorkPlace = "서울특별시 강남구 테헤란로 123길 45-67 ABC빌딩 5층 개발팀";
        
        // When
        OrderHistoryModel orderHistory = new OrderHistoryModel(
            "2024-03-15", "EMP001", "김철수", longWorkPlace
        );

        // Then
        assertEquals("2024-03-15", orderHistory.getOrderingDay());
        assertEquals("EMP001", orderHistory.getEmployeeId());
        assertEquals("김철수", orderHistory.getEmployeeName());
        assertEquals(longWorkPlace, orderHistory.getWorkPlaceName());
    }

    @Test
    public void should_maintainDataIntegrity_when_usingDifferentConstructors() {
        // Given & When
        OrderHistoryModel goodsOrder = new OrderHistoryModel("2024-03-15", "1111111111111", "사과", 10);
        OrderHistoryModel employeeOrder = new OrderHistoryModel("2024-03-15", "EMP001", "김철수", "서울지점");

        // Then - 각 생성자의 데이터가 독립적으로 유지됨
        assertNull(goodsOrder.getEmployeeId());
        assertNull(employeeOrder.getBarcode());
        
        assertEquals("2024-03-15", goodsOrder.getOrderingDay());
        assertEquals("2024-03-15", employeeOrder.getOrderingDay());
    }
}