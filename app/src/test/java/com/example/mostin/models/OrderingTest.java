package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderingTest {

    @Test
    public void should_createOrdering_when_validDataProvided() {
        // Given
        String expectedOrderingDay = "2024-03-15";
        String expectedEmployeeId = "EMP001";
        String expectedBarcode = "1234567890123";
        String expectedEmployeeName = "김철수";
        Integer expectedBoxNum = 10;
        String expectedGoodsName = "사과";

        // When
        Ordering ordering = new Ordering(
            expectedOrderingDay, expectedEmployeeId, expectedBarcode, 
            expectedEmployeeName, expectedBoxNum, expectedGoodsName
        );

        // Then
        assertEquals(expectedOrderingDay, ordering.getOrderingDay());
        assertEquals(expectedEmployeeId, ordering.getEmployeeId());
        assertEquals(expectedBarcode, ordering.getBarcode());
        assertEquals(expectedEmployeeName, ordering.getEmployeeName());
        assertEquals(expectedBoxNum, ordering.getBoxNum());
        assertEquals(expectedGoodsName, ordering.getGoodsName());
    }

    @Test
    public void should_allowSetterModifications_when_usingSetters() {
        // Given
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", "김철수", 5, "바나나"
        );

        String newOrderingDay = "2024-03-16";
        String newEmployeeId = "EMP002";
        String newBarcode = "2222222222222";
        String newEmployeeName = "이영희";
        Integer newBoxNum = 15;
        String newGoodsName = "오렌지";

        // When
        ordering.setOrderingDay(newOrderingDay);
        ordering.setEmployeeId(newEmployeeId);
        ordering.setBarcode(newBarcode);
        ordering.setEmployeeName(newEmployeeName);
        ordering.setBoxNum(newBoxNum);
        ordering.setGoodsName(newGoodsName);

        // Then
        assertEquals(newOrderingDay, ordering.getOrderingDay());
        assertEquals(newEmployeeId, ordering.getEmployeeId());
        assertEquals(newBarcode, ordering.getBarcode());
        assertEquals(newEmployeeName, ordering.getEmployeeName());
        assertEquals(newBoxNum, ordering.getBoxNum());
        assertEquals(newGoodsName, ordering.getGoodsName());
    }

    @Test
    public void should_handleNullValues_when_nullDataProvided() {
        // Given & When
        Ordering ordering = new Ordering(null, null, null, null, null, null);

        // Then
        assertNull(ordering.getOrderingDay());
        assertNull(ordering.getEmployeeId());
        assertNull(ordering.getBarcode());
        assertNull(ordering.getEmployeeName());
        assertNull(ordering.getBoxNum());
        assertNull(ordering.getGoodsName());
    }

    @Test
    public void should_handleNullSetters_when_settingNullValues() {
        // Given
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", "김철수", 10, "포도"
        );

        // When
        ordering.setOrderingDay(null);
        ordering.setEmployeeId(null);
        ordering.setBarcode(null);
        ordering.setEmployeeName(null);
        ordering.setBoxNum(null);
        ordering.setGoodsName(null);

        // Then
        assertNull(ordering.getOrderingDay());
        assertNull(ordering.getEmployeeId());
        assertNull(ordering.getBarcode());
        assertNull(ordering.getEmployeeName());
        assertNull(ordering.getBoxNum());
        assertNull(ordering.getGoodsName());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvided() {
        // Given & When
        Ordering ordering = new Ordering("", "", "", "", 0, "");

        // Then
        assertEquals("", ordering.getOrderingDay());
        assertEquals("", ordering.getEmployeeId());
        assertEquals("", ordering.getBarcode());
        assertEquals("", ordering.getEmployeeName());
        assertEquals(Integer.valueOf(0), ordering.getBoxNum());
        assertEquals("", ordering.getGoodsName());
    }

    @Test
    public void should_handleZeroBoxNum_when_zeroBoxProvided() {
        // Given & When
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", "김철수", 0, "망고"
        );

        // Then
        assertEquals(Integer.valueOf(0), ordering.getBoxNum());
    }

    @Test
    public void should_handleNegativeBoxNum_when_negativeBoxProvided() {
        // Given & When
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", "김철수", -5, "키위"
        );

        // Then
        assertEquals(Integer.valueOf(-5), ordering.getBoxNum());
    }

    @Test
    public void should_handleLargeBoxNum_when_largeBoxProvided() {
        // Given & When
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", "김철수", 999999, "딸기"
        );

        // Then
        assertEquals(Integer.valueOf(999999), ordering.getBoxNum());
    }

    @Test
    public void should_preserveSpecialCharacters_when_specialCharsInData() {
        // Given
        String orderingDay = "2024-12-25";
        String employeeId = "EMP@001";
        String barcode = "123-456-789-012";
        String employeeName = "김철수#";
        Integer boxNum = 7;
        String goodsName = "특수상품@#$%";

        // When
        Ordering ordering = new Ordering(
            orderingDay, employeeId, barcode, employeeName, boxNum, goodsName
        );

        // Then
        assertEquals("2024-12-25", ordering.getOrderingDay());
        assertEquals("EMP@001", ordering.getEmployeeId());
        assertEquals("123-456-789-012", ordering.getBarcode());
        assertEquals("김철수#", ordering.getEmployeeName());
        assertEquals(Integer.valueOf(7), ordering.getBoxNum());
        assertEquals("특수상품@#$%", ordering.getGoodsName());
    }

    @Test
    public void should_handleLongStrings_when_longDataProvided() {
        // Given
        String longBarcode = "12345678901234567890123456789012345678901234567890";
        String longEmployeeName = "매우 긴 직원 이름을 가진 사람입니다";
        String longGoodsName = "매우 긴 상품명을 가진 특별한 프리미엄 고급 수입 유기농 과일 제품입니다";

        // When
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", longBarcode, longEmployeeName, 10, longGoodsName
        );

        // Then
        assertEquals(longBarcode, ordering.getBarcode());
        assertEquals(longEmployeeName, ordering.getEmployeeName());
        assertEquals(longGoodsName, ordering.getGoodsName());
    }

    @Test
    public void should_preserveWhitespace_when_stringsContainWhitespace() {
        // Given
        String employeeName = "  김 철 수  ";
        String goodsName = "  공백이 있는 상품명  ";

        // When
        Ordering ordering = new Ordering(
            "2024-03-15", "EMP001", "1111111111111", employeeName, 10, goodsName
        );

        // Then
        assertEquals(employeeName, ordering.getEmployeeName());
        assertEquals(goodsName, ordering.getGoodsName());
    }
}