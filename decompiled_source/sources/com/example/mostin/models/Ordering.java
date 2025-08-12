package com.example.mostin.models;

/* loaded from: classes6.dex */
public class Ordering {
    private String barcode;
    private Integer boxNum;
    private String employeeId;
    private String employeeName;
    private String goodsName;
    private String orderingDay;

    public Ordering(String orderingDay, String employeeId, String barcode, String employeeName, Integer boxNum, String goodsName) {
        this.orderingDay = orderingDay;
        this.employeeId = employeeId;
        this.barcode = barcode;
        this.employeeName = employeeName;
        this.boxNum = boxNum;
        this.goodsName = goodsName;
    }

    public String getOrderingDay() {
        return this.orderingDay;
    }

    public void setOrderingDay(String orderingDay) {
        this.orderingDay = orderingDay;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getBoxNum() {
        return this.boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
