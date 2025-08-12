package com.example.mostin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for order text parsing and formatting
 * Extracted from OrderingFragment for better testability
 */
public class OrderTextParser {

    /**
     * Model class to represent parsed order line
     */
    public static class ParsedOrder {
        private String barcode;
        private int boxCount;
        private boolean isValid;
        private String errorMessage;

        public ParsedOrder(String barcode, int boxCount, boolean isValid, String errorMessage) {
            this.barcode = barcode;
            this.boxCount = boxCount;
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public String getBarcode() { return barcode; }
        public int getBoxCount() { return boxCount; }
        public boolean isValid() { return isValid; }
        public String getErrorMessage() { return errorMessage; }
    }

    /**
     * Creates formatted order line in "바코드 박스수 박스" format
     * @param barcode Product barcode
     * @param boxCount Number of boxes
     * @return Formatted order line string
     */
    public static String formatOrderLine(String barcode, int boxCount) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return "";
        }
        
        if (boxCount <= 0) {
            return "";
        }
        
        return String.format(Locale.getDefault(), "%s %d 박스", barcode.trim(), boxCount);
    }

    /**
     * Appends new order line to existing order text
     * @param currentText Current order text
     * @param newOrderLine New order line to append
     * @return Updated order text with new line appended
     */
    public static String appendOrderLine(String currentText, String newOrderLine) {
        if (newOrderLine == null || newOrderLine.trim().isEmpty()) {
            return currentText == null ? "" : currentText;
        }
        
        if (currentText == null || currentText.trim().isEmpty()) {
            return newOrderLine.trim();
        }
        
        return currentText.trim() + "\n" + newOrderLine.trim();
    }

    /**
     * Parses order text into individual order lines and validates format
     * Expected format: "바코드 박스수 박스" per line
     * @param orderText Multi-line order text
     * @return List of ParsedOrder objects
     */
    public static List<ParsedOrder> parseOrderText(String orderText) {
        List<ParsedOrder> orders = new ArrayList<>();
        
        if (orderText == null || orderText.trim().isEmpty()) {
            return orders;
        }
        
        String[] lines = orderText.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue; // Skip empty lines
            }
            
            ParsedOrder parsedOrder = parseSingleOrderLine(line, i + 1);
            orders.add(parsedOrder);
        }
        
        return orders;
    }

    /**
     * Parses a single order line
     * @param line Order line to parse
     * @param lineNumber Line number for error reporting
     * @return ParsedOrder object
     */
    private static ParsedOrder parseSingleOrderLine(String line, int lineNumber) {
        String[] parts = line.trim().split("\\s+"); // Trim first, then split by one or more whitespace characters
        
        if (parts.length < 2) {
            return new ParsedOrder(null, 0, false, 
                String.format("라인 %d: 형식이 잘못되었습니다. '바코드 박스수 박스' 형식을 사용하세요.", lineNumber));
        }
        
        String barcode = parts[0].trim();
        if (barcode.isEmpty()) {
            return new ParsedOrder("", 0, false, 
                String.format("라인 %d: 바코드가 비어있습니다.", lineNumber));
        }
        
        // Check if the first part is purely numeric (likely meant to be the box count)
        // This handles cases like " 5 박스" where the barcode is effectively missing
        try {
            Integer.parseInt(barcode);
            // If the first part is numeric and there's no third part, treat barcode as empty
            if (parts.length == 2 && "박스".equals(parts[1].trim())) {
                return new ParsedOrder("", Integer.parseInt(barcode), false, 
                    String.format("라인 %d: 바코드가 비어있습니다.", lineNumber));
            }
        } catch (NumberFormatException e) {
            // Not numeric, continue with normal processing
        }
        
        String boxCountStr = parts[1].trim();
        int boxCount;
        try {
            boxCount = Integer.parseInt(boxCountStr);
        } catch (NumberFormatException e) {
            return new ParsedOrder(barcode, 0, false, 
                String.format("라인 %d: 박스 수가 올바른 숫자가 아닙니다.", lineNumber));
        }
        
        if (boxCount <= 0) {
            return new ParsedOrder(barcode, boxCount, false, 
                String.format("라인 %d: 박스 수는 1개 이상이어야 합니다.", lineNumber));
        }
        
        // Optional: Check if third part is "박스" (box validation)
        if (parts.length >= 3 && !"박스".equals(parts[2].trim())) {
            return new ParsedOrder(barcode, boxCount, false, 
                String.format("라인 %d: 세 번째 단어는 '박스'여야 합니다.", lineNumber));
        }
        
        return new ParsedOrder(barcode, boxCount, true, null);
    }

    /**
     * Validates if barcode follows expected format
     * @param barcode Barcode to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = barcode.trim();
        
        // Basic validation: should be alphanumeric and reasonable length
        // For now, keep the existing validation to not break other tests
        return trimmed.matches("[A-Za-z0-9]+") && trimmed.length() >= 3 && trimmed.length() <= 50;
    }

    /**
     * Counts total number of valid orders in parsed list
     * @param parsedOrders List of parsed orders
     * @return Number of valid orders
     */
    public static int countValidOrders(List<ParsedOrder> parsedOrders) {
        if (parsedOrders == null) {
            return 0;
        }
        
        int count = 0;
        for (ParsedOrder order : parsedOrders) {
            if (order.isValid()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts total boxes across all valid orders
     * @param parsedOrders List of parsed orders
     * @return Total box count
     */
    public static int countTotalBoxes(List<ParsedOrder> parsedOrders) {
        if (parsedOrders == null) {
            return 0;
        }
        
        int total = 0;
        for (ParsedOrder order : parsedOrders) {
            if (order.isValid()) {
                total += order.getBoxCount();
            }
        }
        return total;
    }

    /**
     * Gets all error messages from invalid orders
     * @param parsedOrders List of parsed orders
     * @return List of error messages
     */
    public static List<String> getErrorMessages(List<ParsedOrder> parsedOrders) {
        List<String> errors = new ArrayList<>();
        
        if (parsedOrders == null) {
            return errors;
        }
        
        for (ParsedOrder order : parsedOrders) {
            if (!order.isValid() && order.getErrorMessage() != null) {
                errors.add(order.getErrorMessage());
            }
        }
        
        return errors;
    }
}