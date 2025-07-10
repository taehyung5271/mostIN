package com.example.mostin;

public class GoodsItem {
    private int imageRes;
    private String name;

    public GoodsItem(int imageRes, String name) {
        this.imageRes = imageRes;
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }
}
