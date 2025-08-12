package com.naver.maps.map.clustering;

import android.os.Handler;
import android.os.Looper;
import androidx.core.util.Pair;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.clustering.ClusteringKey;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
class e<T extends ClusteringKey> {
    private final Executor a;
    private final Handler b;
    private final d<T> c;
    private final c d;

    e(ThresholdStrategy thresholdStrategy, DistanceStrategy distanceStrategy, PositioningStrategy positioningStrategy, TagMergeStrategy tagMergeStrategy, MarkerManager markerManager, ClusterMarkerUpdater clusterMarkerUpdater, LeafMarkerUpdater leafMarkerUpdater, int i, int i2, int i3, int i4, double d, int i5, boolean z) {
        int iClamp = MathUtils.clamp(i, 0, 21);
        int iClamp2 = MathUtils.clamp(i2, 0, 21);
        int iMin = Math.min(MathUtils.clamp(i3, 0, 21), iClamp);
        int iMax = Math.max(MathUtils.clamp(i4, 0, 21), iClamp2);
        this.a = Executors.newSingleThreadExecutor();
        this.b = new Handler(Looper.getMainLooper());
        this.c = new d<>(this, thresholdStrategy, distanceStrategy, positioningStrategy, tagMergeStrategy, iClamp, iClamp2, iMin, iMax, d, i5 > 0);
        this.d = new c(this, markerManager, clusterMarkerUpdater, leafMarkerUpdater, iMin, iMax, i5, z);
    }

    void a(final NaverMap naverMap) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.1
            @Override // java.lang.Runnable
            public void run() {
                e.this.b.post(new Runnable() { // from class: com.naver.maps.map.clustering.e.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        e.this.d.a(naverMap);
                    }
                });
            }
        });
    }

    void a(final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.4
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a(onClustererUpdateCallback);
            }
        });
    }

    void a(final T t, final Object obj, final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.5
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a((d) t, obj, onClustererUpdateCallback);
            }
        });
    }

    void a(final Map<T, ?> map, final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.6
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a(map, onClustererUpdateCallback);
            }
        });
    }

    void a(final T t, final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a((d) t, onClustererUpdateCallback);
            }
        });
    }

    void a(final Collection<T> collection, final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.8
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a(collection, onClustererUpdateCallback);
            }
        });
    }

    void a() {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.9
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a();
            }
        });
    }

    void a(final int i, final Collection<Long> collection, final Collection<Long> collection2) {
        this.a.execute(new Runnable() { // from class: com.naver.maps.map.clustering.e.10
            @Override // java.lang.Runnable
            public void run() {
                e.this.c.a(i, collection, collection2);
            }
        });
    }

    void b(final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.b.post(new Runnable() { // from class: com.naver.maps.map.clustering.e.11
            @Override // java.lang.Runnable
            public void run() {
                e.this.d.a(onClustererUpdateCallback);
            }
        });
    }

    void b(final Collection<Object> collection, final OnClustererUpdateCallback onClustererUpdateCallback) {
        this.b.post(new Runnable() { // from class: com.naver.maps.map.clustering.e.2
            @Override // java.lang.Runnable
            public void run() {
                e.this.d.a(collection, onClustererUpdateCallback);
            }
        });
    }

    void a(final Collection<Pair<MarkerInfo, Long>> collection, final Collection<Pair<Long, Long>> collection2, final Map<Long, MarkerInfo> map) {
        this.b.post(new Runnable() { // from class: com.naver.maps.map.clustering.e.3
            @Override // java.lang.Runnable
            public void run() {
                e.this.d.a(collection, collection2, map);
            }
        });
    }
}
