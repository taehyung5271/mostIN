package com.example.mostin.models;

public class OrderHistoryModel {
    private String orderingDay;
    private String employeeId;
    private String employeeName;
    private String workPlaceName;
    private String barcode;
    private String goodsName;
    private int boxCount;
    private int totalItems;
    private int totalBoxes;

    public OrderHistoryModel(String orderingDay, String barcode, String goodsName, int boxCount) {
        this.orderingDay = orderingDay;
        this.barcode = barcode;
        this.goodsName = goodsName;
        this.boxCount = boxCount;
    }

    public OrderHistoryModel(String orderingDay, String employeeId, String employeeName, String workPlaceName) {
        this.orderingDay = orderingDay;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.workPlaceName = workPlaceName;
    }

    public OrderHistoryModel(String orderingDay, String employeeId, String employeeName, String workPlaceName, int totalItems, int totalBoxes) {
        this.orderingDay = orderingDay;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.workPlaceName = workPlaceName;
        this.totalItems = totalItems;
        this.totalBoxes = totalBoxes;
    }

    public String getOrderingDay() { return orderingDay; }
    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getWorkPlaceName() { return workPlaceName; }
    public String getBarcode() { return barcode; }
    public String getGoodsName() { return goodsName; }
    public int getBoxCount() { return boxCount; }
    public int getTotalItems() { return totalItems; }
    public int getTotalBoxes() { return totalBoxes; }

    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
    public void setTotalBoxes(int totalBoxes) { this.totalBoxes = totalBoxes; }
} 