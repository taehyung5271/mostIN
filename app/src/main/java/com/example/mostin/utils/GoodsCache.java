package com.example.mostin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.mostin.models.GoodsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoodsCache {
    private static final String TAG = "GoodsCache";
    private static final String PREFS_NAME = "goods_cache_prefs";
    private static final String KEY_CACHED_GOODS = "cached_goods";
    private static final String KEY_CACHE_TIMESTAMP = "cache_timestamp";
    private static final String KEY_CACHE_VERSION = "cache_version";
    
    // 캐시 유효 시간 (60분으로 연장)
    private static final long CACHE_VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(60);
    
    private static GoodsCache instance;
    private List<GoodsModel> cachedGoods;
    private long cacheTimestamp;
    private int cacheVersion;
    private SharedPreferences prefs;
    private Gson gson;

    private GoodsCache() {
        gson = new Gson();
    }

    public static synchronized GoodsCache getInstance() {
        if (instance == null) {
            instance = new GoodsCache();
        }
        return instance;
    }

    public void initialize(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            loadCacheFromDisk();
        }
    }

    /**
     * 테스트 전용 초기화 메소드 - Android 컴포넌트 없이 테스트 가능
     */
    public void initializeForTest() {
        // 테스트용으로는 SharedPreferences 없이 메모리만 사용
        prefs = null;
        cachedGoods = null;
        cacheTimestamp = 0;
        cacheVersion = 0;
    }

    public List<GoodsModel> getCachedGoods() {
        if (isCacheValid()) {
            // 복사 없이 직접 반환으로 성능 개선 (읽기 전용)
            try {
                Log.d(TAG, "⚡ 메모리 캐시 즉시 반환 (" + cachedGoods.size() + "개)");
            } catch (Exception e) {
                // Log 사용 불가 환경 (테스트)에서는 무시
            }
            return cachedGoods; // 직접 반환으로 성능 향상
        }
        return null;
    }

    public void setCachedGoods(List<GoodsModel> goods) {
        if (goods != null) {
            this.cachedGoods = new ArrayList<>(goods);
            this.cacheTimestamp = System.currentTimeMillis();
            this.cacheVersion++;
            
            saveCacheToDisk();
            try {
                Log.d(TAG, "상품 캐시 업데이트 완료 (총 " + goods.size() + "개, 버전: " + cacheVersion + ")");
            } catch (Exception e) {
                // Log 사용 불가 환경 (테스트)에서는 무시
            }
        } else {
            this.cachedGoods = null;
            this.cacheTimestamp = 0;
            this.cacheVersion = 0;
        }
    }

    public boolean isCacheValid() {
        if (cachedGoods == null || cachedGoods.isEmpty()) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        boolean isValid = (currentTime - cacheTimestamp) < CACHE_VALIDITY_DURATION;
        
        if (!isValid) {
            try {
                Log.d(TAG, "캐시 만료됨 (생성시간: " + cacheTimestamp + ", 현재시간: " + currentTime + ")");
            } catch (Exception e) {
                // Log 사용 불가 환경 (테스트)에서는 무시
            }
        }
        
        return isValid;
    }

    public void clearCache() {
        cachedGoods = null;
        cacheTimestamp = 0;
        cacheVersion = 0;
        
        if (prefs != null) {
            try {
                prefs.edit()
                    .remove(KEY_CACHED_GOODS)
                    .remove(KEY_CACHE_TIMESTAMP)
                    .remove(KEY_CACHE_VERSION)
                    .apply();
            } catch (Exception e) {
                // SharedPreferences 사용 불가 환경 (테스트)에서는 무시
                System.err.println("SharedPreferences 클리어 실패 (테스트 환경에서는 정상): " + e.getMessage());
            }
        }
        
        try {
            Log.d(TAG, "상품 캐시 클리어 완료");
        } catch (Exception e) {
            // Log 사용 불가 환경 (테스트)에서는 무시
            System.out.println("상품 캐시 클리어 완료");
        }
    }

    public void addGoods(GoodsModel newGoods) {
        if (cachedGoods != null && newGoods != null) {
            cachedGoods.add(newGoods);
            cacheVersion++;
            saveCacheToDisk();
            try {
                Log.d(TAG, "캐시에 새 상품 추가: " + newGoods.getName());
            } catch (Exception e) {
                // Log 사용 불가 환경 (테스트)에서는 무시
            }
        }
    }

    public void updateGoods(GoodsModel updatedGoods) {
        if (cachedGoods != null && updatedGoods != null) {
            for (int i = 0; i < cachedGoods.size(); i++) {
                if (cachedGoods.get(i).getBarcode().equals(updatedGoods.getBarcode())) {
                    cachedGoods.set(i, updatedGoods);
                    cacheVersion++;
                    saveCacheToDisk();
                    try {
                        Log.d(TAG, "캐시에서 상품 업데이트: " + updatedGoods.getName());
                    } catch (Exception e) {
                        // Log 사용 불가 환경 (테스트)에서는 무시
                    }
                    return;
                }
            }
        }
    }

    public void removeGoods(String barcode) {
        if (cachedGoods != null && barcode != null) {
            for (int i = 0; i < cachedGoods.size(); i++) {
                if (cachedGoods.get(i).getBarcode().equals(barcode)) {
                    GoodsModel removed = cachedGoods.remove(i);
                    cacheVersion++;
                    saveCacheToDisk();
                    try {
                        Log.d(TAG, "캐시에서 상품 삭제: " + removed.getName());
                    } catch (Exception e) {
                        // Log 사용 불가 환경 (테스트)에서는 무시
                    }
                    return;
                }
            }
        }
    }

    public int getCacheVersion() {
        return cacheVersion;
    }

    public long getCacheAge() {
        return System.currentTimeMillis() - cacheTimestamp;
    }

    public String getCacheInfo() {
        if (cachedGoods == null) {
            return "캐시 없음";
        }
        
        long ageMinutes = getCacheAge() / (1000 * 60);
        return String.format("캐시: %d개 상품, %d분 전, 버전: %d", 
            cachedGoods.size(), ageMinutes, cacheVersion);
    }

    private void saveCacheToDisk() {
        if (prefs != null && cachedGoods != null) {
            try {
                String goodsJson = gson.toJson(cachedGoods);
                prefs.edit()
                    .putString(KEY_CACHED_GOODS, goodsJson)
                    .putLong(KEY_CACHE_TIMESTAMP, cacheTimestamp)
                    .putInt(KEY_CACHE_VERSION, cacheVersion)
                    .apply();
                
                try {
                    Log.d(TAG, "캐시를 디스크에 저장 완료");
                } catch (Exception e) {
                    // Log 사용 불가 환경 (테스트)에서는 무시
                }
            } catch (Exception e) {
                try {
                    Log.e(TAG, "캐시 저장 실패 (테스트 환경에서는 정상)", e);
                } catch (Exception logException) {
                    // Log 사용 불가 환경 (테스트)에서는 무시
                }
            }
        }
    }

    private void loadCacheFromDisk() {
        if (prefs != null) {
            try {
                String goodsJson = prefs.getString(KEY_CACHED_GOODS, null);
                if (goodsJson != null) {
                    Type listType = new TypeToken<List<GoodsModel>>(){}.getType();
                    cachedGoods = gson.fromJson(goodsJson, listType);
                    cacheTimestamp = prefs.getLong(KEY_CACHE_TIMESTAMP, 0);
                    cacheVersion = prefs.getInt(KEY_CACHE_VERSION, 0);
                    
                    try {
                        Log.d(TAG, "디스크에서 캐시 로드 완료: " + getCacheInfo());
                    } catch (Exception e) {
                        // Log 사용 불가 환경 (테스트)에서는 무시
                    }
                }
            } catch (Exception e) {
                try {
                    Log.e(TAG, "캐시 로드 실패", e);
                } catch (Exception logException) {
                    // Log 사용 불가 환경 (테스트)에서는 무시
                }
                clearCache();
            }
        }
    }
}