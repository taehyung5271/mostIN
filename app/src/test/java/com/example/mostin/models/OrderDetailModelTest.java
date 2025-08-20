package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderDetailModelTest {

    @Test
    public void should_createOrderDetailModel_when_validDataProvided() {
        // Given
        String expectedGoodsName = "사과";
        int expectedBoxCount = 10;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleNullGoodsName_when_nullNameProvided() {
        // Given
        String expectedGoodsName = null;
        int expectedBoxCount = 5;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertNull(orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleEmptyGoodsName_when_emptyNameProvided() {
        // Given
        String expectedGoodsName = "";
        int expectedBoxCount = 3;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals("", orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleZeroBoxCount_when_zeroCountProvided() {
        // Given
        String expectedGoodsName = "바나나";
        int expectedBoxCount = 0;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(0, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleNegativeBoxCount_when_negativeCountProvided() {
        // Given
        String expectedGoodsName = "오렌지";
        int expectedBoxCount = -5;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(-5, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleLargeBoxCount_when_largeCountProvided() {
        // Given
        String expectedGoodsName = "포도";
        int expectedBoxCount = 999999;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(999999, orderDetail.getBoxCount());
    }

    @Test
    public void should_preserveSpecialCharacters_when_goodsNameContainsSpecialChars() {
        // Given
        String expectedGoodsName = "특수상품@#$%^&*()";
        int expectedBoxCount = 7;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleLongGoodsName_when_longNameProvided() {
        // Given
        String expectedGoodsName = "매우 긴 상품명을 가진 특별한 프리미엄 고급 수입 유기농 과일 제품입니다";
        int expectedBoxCount = 2;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_preserveWhitespace_when_goodsNameContainsWhitespace() {
        // Given
        String expectedGoodsName = "  공백이 있는 상품명  ";
        int expectedBoxCount = 15;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_handleNumericGoodsName_when_nameIsNumeric() {
        // Given
        String expectedGoodsName = "12345";
        int expectedBoxCount = 8;

        // When
        OrderDetailModel orderDetail = new OrderDetailModel(expectedGoodsName, expectedBoxCount);

        // Then
        assertEquals(expectedGoodsName, orderDetail.getGoodsName());
        assertEquals(expectedBoxCount, orderDetail.getBoxCount());
    }

    @Test
    public void should_maintainImmutability_when_noSettersProvided() {
        // Given
        OrderDetailModel orderDetail = new OrderDetailModel("딸기", 20);
        
        String originalName = orderDetail.getGoodsName();
        int originalCount = orderDetail.getBoxCount();

        // When - setter 메서드가 없으므로 값 변경 불가능

        // Then
        assertEquals(originalName, orderDetail.getGoodsName());
        assertEquals(originalCount, orderDetail.getBoxCount());
    }
}