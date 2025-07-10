package com.example.mostin.models;

public class OrderHistoryModel {
    private String orderingDay;
    private String employeeId;
    private String employeeName;
    private String workPlaceName;
    private String barcode;
    private String goodsName;
    private int boxCount;

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

    public String getOrderingDay() { return orderingDay; }
    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getWorkPlaceName() { return workPlaceName; }
    public String getBarcode() { return barcode; }
    public String getGoodsName() { return goodsName; }
    public int getBoxCount() { return boxCount; }
} 