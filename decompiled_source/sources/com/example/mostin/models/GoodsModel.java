package com.example.mostin.models;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class GoodsModel {

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("goodsName")
    private String name;

    public GoodsModel(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
