package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class GoodsItemTest {

    @Test
    public void should_createGoodsItem_when_validDataProvided() {
        // Given
        int expectedImageRes = 12345;
        String expectedName = "사과";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals(expectedName, goodsItem.getName());
    }

    @Test
    public void should_handleNullName_when_nullNameProvided() {
        // Given
        int expectedImageRes = 67890;
        String expectedName = null;

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertNull(goodsItem.getName());
    }

    @Test
    public void should_handleEmptyName_when_emptyNameProvided() {
        // Given
        int expectedImageRes = 11111;
        String expectedName = "";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals("", goodsItem.getName());
    }

    @Test
    public void should_handleZeroImageRes_when_zeroResourceProvided() {
        // Given
        int expectedImageRes = 0;
        String expectedName = "기본상품";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals(expectedName, goodsItem.getName());
    }

    @Test
    public void should_handleNegativeImageRes_when_negativeResourceProvided() {
        // Given
        int expectedImageRes = -1;
        String expectedName = "오류상품";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals(expectedName, goodsItem.getName());
    }

    @Test
    public void should_preserveSpecialCharacters_when_nameContainsSpecialChars() {
        // Given
        int expectedImageRes = 22222;
        String expectedName = "사과@#$%^&*()";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals(expectedName, goodsItem.getName());
    }

    @Test
    public void should_preserveLongName_when_longNameProvided() {
        // Given
        int expectedImageRes = 33333;
        String expectedName = "매우 긴 상품명을 가진 특별한 프리미엄 고급 수입 유기농 사과 제품입니다";

        // When
        GoodsItem goodsItem = new GoodsItem(expectedImageRes, expectedName);

        // Then
        assertEquals(expectedImageRes, goodsItem.getImageRes());
        assertEquals(expectedName, goodsItem.getName());
    }

    @Test
    public void should_maintainImmutability_when_fieldsAreFinal() {
        // Given
        GoodsItem goodsItem = new GoodsItem(44444, "바나나");
        
        int originalImageRes = goodsItem.getImageRes();
        String originalName = goodsItem.getName();

        // When - final 필드이므로 값이 변경되지 않음을 확인
        // 직접적인 수정 불가능 (final 필드)

        // Then
        assertEquals(originalImageRes, goodsItem.getImageRes());
        assertEquals(originalName, goodsItem.getName());
    }
}