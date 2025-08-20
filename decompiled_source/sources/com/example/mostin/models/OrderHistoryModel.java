package com.example.mostin.models;

/* loaded from: classes6.dex */
public class OrderHistoryModel {
    private String barcode;
    private int boxCount;
    private String employeeId;
    private String employeeName;
    private String goodsName;
    private String orderingDay;
    private String workPlaceName;

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

    public String getOrderingDay() {
        return this.orderingDay;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public String getWorkPlaceName() {
        return this.workPlaceName;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public int getBoxCount() {
        return this.boxCount;
    }
}
