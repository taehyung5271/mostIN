package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class GoodsModelTest {

    @Test
    public void should_createGoodsModel_when_validDataProvided() {
        // Given
        String expectedBarcode = "1234567890123";
        String expectedName = "사과";

        // When
        GoodsModel goods = new GoodsModel(expectedBarcode, expectedName);

        // Then
        assertEquals(expectedBarcode, goods.getBarcode());
        assertEquals(expectedName, goods.getName());
    }

    @Test
    public void should_allowSetterModifications_when_usingSetters() {
        // Given
        GoodsModel goods = new GoodsModel("1111111111111", "바나나");
        String newBarcode = "2222222222222";
        String newName = "오렌지";

        // When
        goods.setBarcode(newBarcode);
        goods.setName(newName);

        // Then
        assertEquals(newBarcode, goods.getBarcode());
        assertEquals(newName, goods.getName());
    }

    @Test
    public void should_handleNullValues_when_nullDataProvided() {
        // Given & When
        GoodsModel goods = new GoodsModel(null, null);

        // Then
        assertNull(goods.getBarcode());
        assertNull(goods.getName());
    }

    @Test
    public void should_handleNullSetters_when_settingNullValues() {
        // Given
        GoodsModel goods = new GoodsModel("3333333333333", "포도");

        // When
        goods.setBarcode(null);
        goods.setName(null);

        // Then
        assertNull(goods.getBarcode());
        assertNull(goods.getName());
    }

    @Test
    public void should_handleEmptyStrings_when_emptyDataProvided() {
        // Given & When
        GoodsModel goods = new GoodsModel("", "");

        // Then
        assertEquals("", goods.getBarcode());
        assertEquals("", goods.getName());
    }

    @Test
    public void should_handleSpecialCharacters_when_specialCharsInData() {
        // Given
        String barcode = "123-456-789-012";
        String name = "특수상품@#$%";

        // When
        GoodsModel goods = new GoodsModel(barcode, name);

        // Then
        assertEquals(barcode, goods.getBarcode());
        assertEquals(name, goods.getName());
    }

    @Test
    public void should_handleLongBarcode_when_longBarcodeProvided() {
        // Given
        String longBarcode = "12345678901234567890123456789012345678901234567890";
        String name = "장바코드상품";

        // When
        GoodsModel goods = new GoodsModel(longBarcode, name);

        // Then
        assertEquals(longBarcode, goods.getBarcode());
        assertEquals(name, goods.getName());
    }

    @Test
    public void should_handleLongName_when_longNameProvided() {
        // Given
        String barcode = "9999999999999";
        String longName = "매우 긴 상품명을 가진 특별한 프리미엄 고급 수입 유기농 과일 제품입니다";

        // When
        GoodsModel goods = new GoodsModel(barcode, longName);

        // Then
        assertEquals(barcode, goods.getBarcode());
        assertEquals(longName, goods.getName());
    }

    @Test
    public void should_preserveWhitespace_when_nameContainsWhitespace() {
        // Given
        String barcode = "4444444444444";
        String name = "  공백이 있는 상품명  ";

        // When
        GoodsModel goods = new GoodsModel(barcode, name);

        // Then
        assertEquals(barcode, goods.getBarcode());
        assertEquals(name, goods.getName());
    }

    @Test
    public void should_handleNumericName_when_nameIsNumeric() {
        // Given
        String barcode = "5555555555555";
        String name = "12345";

        // When
        GoodsModel goods = new GoodsModel(barcode, name);

        // Then
        assertEquals(barcode, goods.getBarcode());
        assertEquals(name, goods.getName());
    }
}