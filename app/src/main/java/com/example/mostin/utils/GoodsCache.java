package com.example.mostin.utils;

import com.example.mostin.models.GoodsModel;
import java.util.List;

public class GoodsCache {
    private static GoodsCache instance;
    private List<GoodsModel> cachedGoods;

    private GoodsCache() {
        // Private constructor to prevent instantiation
    }

    public static synchronized GoodsCache getInstance() {
        if (instance == null) {
            instance = new GoodsCache();
        }
        return instance;
    }

    public List<GoodsModel> getCachedGoods() {
        return cachedGoods;
    }

    public void setCachedGoods(List<GoodsModel> goods) {
        this.cachedGoods = goods;
    }

    public boolean isCacheValid() {
        return cachedGoods != null && !cachedGoods.isEmpty();
    }

    public void clearCache() {
        cachedGoods = null;
    }
}