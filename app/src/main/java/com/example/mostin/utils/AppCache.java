package com.example.mostin.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppCache {
    private static AppCache instance;
    private Map<String, List<?>> cacheMap;

    private AppCache() {
        cacheMap = new HashMap<>();
    }

    public static synchronized AppCache getInstance() {
        if (instance == null) {
            instance = new AppCache();
        }
        return instance;
    }

    public <T> List<T> getCachedList(String key, Class<T> type) {
        if (cacheMap.containsKey(key)) {
            return (List<T>) cacheMap.get(key);
        }
        return null;
    }

    public <T> void putCachedList(String key, List<T> list) {
        cacheMap.put(key, list);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }

    public void clearCache(String key) {
        cacheMap.remove(key);
    }

    public void clearAllCaches() {
        cacheMap.clear();
    }
}