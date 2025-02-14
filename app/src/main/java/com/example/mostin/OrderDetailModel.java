package com.example.mostin;

public class OrderDetailModel {
    private String goodsName;
    private int boxCount;

    public OrderDetailModel(String goodsName, int boxCount) {
        this.goodsName = goodsName;
        this.boxCount = boxCount;
    }

    public String getGoodsName() { return goodsName; }
    public int getBoxCount() { return boxCount; }
} 