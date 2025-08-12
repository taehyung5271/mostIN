package com.example.mostin.models;

/* loaded from: classes6.dex */
public class OrderDetailModel {
    private int boxCount;
    private String goodsName;

    public OrderDetailModel(String goodsName, int boxCount) {
        this.goodsName = goodsName;
        this.boxCount = boxCount;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public int getBoxCount() {
        return this.boxCount;
    }
}
