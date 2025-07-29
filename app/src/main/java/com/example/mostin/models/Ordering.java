package com.example.mostin.models;

public class Ordering {

    private String orderingDay;
    private String employeeId;
    private String barcode;
    private String employeeName;
    private Integer boxNum;
    private String goodsName;

    public Ordering(String orderingDay, String employeeId, String barcode, String employeeName, Integer boxNum, String goodsName) {
        this.orderingDay = orderingDay;
        this.employeeId = employeeId;
        this.barcode = barcode;
        this.employeeName = employeeName;
        this.boxNum = boxNum;
        this.goodsName = goodsName;
    }

    public String getOrderingDay() {
        return orderingDay;
    }

    public void setOrderingDay(String orderingDay) {
        this.orderingDay = orderingDay;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
