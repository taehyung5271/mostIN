package com.example.mostin;

public class GoodsItem {
    private final int imageRes;
    private final String name;

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
