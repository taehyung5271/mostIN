package com.example.mostin.utils;

import static org.junit.Assert.*;

import com.example.mostin.utils.OrderTextParser.ParsedOrder;

import org.junit.Test;

import java.util.List;

/**
 * Unit tests for OrderTextParser utility class
 */
public class OrderTextParserTest {

    @Test
    public void should_format_order_line_correctly() {
        // When & Then
        assertEquals("ABC123 5 박스", OrderTextParser.formatOrderLine("ABC123", 5));
        assertEquals("XYZ789 1 박스", OrderTextParser.formatOrderLine("XYZ789", 1));
        assertEquals("DEF456 100 박스", OrderTextParser.formatOrderLine("DEF456", 100));
    }

    @Test
    public void should_return_empty_string_for_invalid_barcode() {
        // When & Then
        assertEquals("", OrderTextParser.formatOrderLine(null, 5));
        assertEquals("", OrderTextParser.formatOrderLine("", 5));
        assertEquals("", OrderTextParser.formatOrderLine("   ", 5));
    }

    @Test
    public void should_return_empty_string_for_invalid_box_count() {
        // When & Then
        assertEquals("", OrderTextParser.formatOrderLine("ABC123", 0));
        assertEquals("", OrderTextParser.formatOrderLine("ABC123", -1));
        assertEquals("", OrderTextParser.formatOrderLine("ABC123", -100));
    }

    @Test
    public void should_trim_barcode_in_formatting() {
        // When
        String result = OrderTextParser.formatOrderLine("  ABC123  ", 5);

        // Then
        assertEquals("ABC123 5 박스", result);
    }

    @Test
    public void should_append_order_line_to_empty_text() {
        // Given
        String currentText = "";
        String newOrderLine = "ABC123 5 박스";

        // When
        String result = OrderTextParser.appendOrderLine(currentText, newOrderLine);

        // Then
        assertEquals("ABC123 5 박스", result);
    }

    @Test
    public void should_append_order_line_to_existing_text() {
        // Given
        String currentText = "ABC123 5 박스";
        String newOrderLine = "XYZ789 3 박스";

        // When
        String result = OrderTextParser.appendOrderLine(currentText, newOrderLine);

        // Then
        assertEquals("ABC123 5 박스\nXYZ789 3 박스", result);
    }

    @Test
    public void should_handle_null_current_text() {
        // Given
        String currentText = null;
        String newOrderLine = "ABC123 5 박스";

        // When
        String result = OrderTextParser.appendOrderLine(currentText, newOrderLine);

        // Then
        assertEquals("ABC123 5 박스", result);
    }

    @Test
    public void should_handle_null_new_order_line() {
        // Given
        String currentText = "ABC123 5 박스";
        String newOrderLine = null;

        // When
        String result = OrderTextParser.appendOrderLine(currentText, newOrderLine);

        // Then
        assertEquals("ABC123 5 박스", result);
    }

    @Test
    public void should_trim_both_texts_when_appending() {
        // Given
        String currentText = "  ABC123 5 박스  ";
        String newOrderLine = "  XYZ789 3 박스  ";

        // When
        String result = OrderTextParser.appendOrderLine(currentText, newOrderLine);

        // Then
        assertEquals("ABC123 5 박스\nXYZ789 3 박스", result);
    }

    @Test
    public void should_return_empty_list_for_null_order_text() {
        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(null);

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_return_empty_list_for_empty_order_text() {
        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText("");

        // Then
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void should_parse_single_valid_order_line() {
        // Given
        String orderText = "ABC123 5 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertTrue("Order should be valid", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertEquals(5, order.getBoxCount());
        assertNull("No error message for valid order", order.getErrorMessage());
    }

    @Test
    public void should_parse_multiple_valid_order_lines() {
        // Given
        String orderText = "ABC123 5 박스\nXYZ789 3 박스\nDEF456 10 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 3 orders", 3, result.size());
        
        ParsedOrder order1 = result.get(0);
        assertTrue("First order should be valid", order1.isValid());
        assertEquals("ABC123", order1.getBarcode());
        assertEquals(5, order1.getBoxCount());
        
        ParsedOrder order2 = result.get(1);
        assertTrue("Second order should be valid", order2.isValid());
        assertEquals("XYZ789", order2.getBarcode());
        assertEquals(3, order2.getBoxCount());
        
        ParsedOrder order3 = result.get(2);
        assertTrue("Third order should be valid", order3.isValid());
        assertEquals("DEF456", order3.getBarcode());
        assertEquals(10, order3.getBoxCount());
    }

    @Test
    public void should_parse_order_without_box_keyword() {
        // Given
        String orderText = "ABC123 5";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertTrue("Order should be valid even without '박스' keyword", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertEquals(5, order.getBoxCount());
    }

    @Test
    public void should_reject_order_with_wrong_box_keyword() {
        // Given
        String orderText = "ABC123 5 상자";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid with wrong keyword", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertEquals(5, order.getBoxCount());
        assertTrue("Should have error message", order.getErrorMessage().contains("세 번째 단어는 '박스'여야 합니다"));
    }

    @Test
    public void should_reject_order_with_insufficient_parts() {
        // Given
        String orderText = "ABC123";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid", order.isValid());
        assertTrue("Should have error message about format", order.getErrorMessage().contains("형식이 잘못되었습니다"));
    }

    @Test
    public void should_reject_order_with_empty_barcode() {
        // Given
        String orderText = " 5 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid", order.isValid());
        assertTrue("Should have error message about empty barcode", order.getErrorMessage().contains("바코드가 비어있습니다"));
    }

    @Test
    public void should_reject_order_with_invalid_box_count() {
        // Given
        String orderText = "ABC123 abc 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertTrue("Should have error message about invalid number", order.getErrorMessage().contains("올바른 숫자가 아닙니다"));
    }

    @Test
    public void should_reject_order_with_zero_box_count() {
        // Given
        String orderText = "ABC123 0 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertEquals(0, order.getBoxCount());
        assertTrue("Should have error message about minimum boxes", order.getErrorMessage().contains("1개 이상이어야 합니다"));
    }

    @Test
    public void should_reject_order_with_negative_box_count() {
        // Given
        String orderText = "ABC123 -5 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 1 order", 1, result.size());
        ParsedOrder order = result.get(0);
        assertFalse("Order should be invalid", order.isValid());
        assertEquals("ABC123", order.getBarcode());
        assertEquals(-5, order.getBoxCount());
        assertTrue("Should have error message about minimum boxes", order.getErrorMessage().contains("1개 이상이어야 합니다"));
    }

    @Test
    public void should_skip_empty_lines() {
        // Given
        String orderText = "ABC123 5 박스\n\nXYZ789 3 박스\n\n\nDEF456 10 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 3 orders, skipping empty lines", 3, result.size());
        assertTrue("All orders should be valid", result.stream().allMatch(ParsedOrder::isValid));
    }

    @Test
    public void should_validate_barcode_format() {
        // When & Then
        assertTrue("Alphanumeric barcode should be valid", OrderTextParser.isValidBarcode("ABC123"));
        assertTrue("Numeric barcode should be valid", OrderTextParser.isValidBarcode("123456"));
        assertTrue("Alpha barcode should be valid", OrderTextParser.isValidBarcode("ABCDEF"));
        assertTrue("Mixed case should be valid", OrderTextParser.isValidBarcode("AbC123"));
        
        assertFalse("Null barcode should be invalid", OrderTextParser.isValidBarcode(null));
        assertFalse("Empty barcode should be invalid", OrderTextParser.isValidBarcode(""));
        assertFalse("Whitespace barcode should be invalid", OrderTextParser.isValidBarcode("   "));
        assertFalse("Special characters should be invalid", OrderTextParser.isValidBarcode("ABC-123"));
        assertFalse("Too short barcode should be invalid", OrderTextParser.isValidBarcode("AB"));
        assertFalse("Too long barcode should be invalid", OrderTextParser.isValidBarcode("A".repeat(51)));
    }

    @Test
    public void should_count_valid_orders() {
        // Given
        String orderText = "ABC123 5 박스\nINVALID\nXYZ789 3 박스\n 0 박스";

        // When
        List<ParsedOrder> parsedOrders = OrderTextParser.parseOrderText(orderText);
        int validCount = OrderTextParser.countValidOrders(parsedOrders);

        // Then
        assertEquals("Should count 2 valid orders", 2, validCount);
    }

    @Test
    public void should_count_zero_for_null_list() {
        // When
        int result = OrderTextParser.countValidOrders(null);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void should_count_total_boxes() {
        // Given
        String orderText = "ABC123 5 박스\nXYZ789 3 박스\nDEF456 10 박스";

        // When
        List<ParsedOrder> parsedOrders = OrderTextParser.parseOrderText(orderText);
        int totalBoxes = OrderTextParser.countTotalBoxes(parsedOrders);

        // Then
        assertEquals("Should count 18 total boxes", 18, totalBoxes);
    }

    @Test
    public void should_count_only_valid_order_boxes() {
        // Given
        String orderText = "ABC123 5 박스\nINVALID 10 박스\nXYZ789 3 박스";

        // When
        List<ParsedOrder> parsedOrders = OrderTextParser.parseOrderText(orderText);
        int totalBoxes = OrderTextParser.countTotalBoxes(parsedOrders);

        // Then
        assertEquals("Should count boxes from all valid orders", 18, totalBoxes);
    }

    @Test
    public void should_get_error_messages() {
        // Given
        String orderText = "ABC123 5 박스\nINVALID\n 0 박스";

        // When
        List<ParsedOrder> parsedOrders = OrderTextParser.parseOrderText(orderText);
        List<String> errors = OrderTextParser.getErrorMessages(parsedOrders);

        // Then
        assertEquals("Should have 2 error messages", 2, errors.size());
        assertTrue("Should contain format error", errors.stream().anyMatch(e -> e.contains("형식이 잘못되었습니다")));
        assertTrue("Should contain barcode empty error", errors.stream().anyMatch(e -> e.contains("바코드가 비어있습니다")));
    }

    @Test
    public void should_handle_mixed_valid_and_invalid_orders() {
        // Given
        String orderText = "ABC123 5 박스\nINVALID_FORMAT\nXYZ789 abc 박스\nDEF456 10 박스\n 0 박스";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 5 orders", 5, result.size());
        assertEquals("Should have 2 valid orders", 2, OrderTextParser.countValidOrders(result));
        assertEquals("Should count 15 total boxes from valid orders", 15, OrderTextParser.countTotalBoxes(result));
        assertEquals("Should have 3 error messages", 3, OrderTextParser.getErrorMessages(result).size());
    }

    @Test
    public void should_handle_whitespace_in_order_text() {
        // Given
        String orderText = "  ABC123   5   박스  \n  XYZ789  3  박스  ";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);

        // Then
        assertEquals("Should parse 2 orders", 2, result.size());
        assertTrue("Both orders should be valid", result.stream().allMatch(ParsedOrder::isValid));
        assertEquals("First barcode should be trimmed", "ABC123", result.get(0).getBarcode());
        assertEquals("Second barcode should be trimmed", "XYZ789", result.get(1).getBarcode());
    }

    @Test
    public void should_include_line_numbers_in_error_messages() {
        // Given
        String orderText = "ABC123 5 박스\nINVALID\nXYZ789 3 박스\nANOTHER_INVALID";

        // When
        List<ParsedOrder> result = OrderTextParser.parseOrderText(orderText);
        List<String> errors = OrderTextParser.getErrorMessages(result);

        // Then
        assertTrue("Should include line 2 in error", errors.stream().anyMatch(e -> e.contains("라인 2")));
        assertTrue("Should include line 4 in error", errors.stream().anyMatch(e -> e.contains("라인 4")));
    }
}