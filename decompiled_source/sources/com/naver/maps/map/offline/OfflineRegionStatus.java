package com.naver.maps.map.offline;

/* loaded from: classes.dex */
public class OfflineRegionStatus {
    private int a;
    private long b;
    private long c;
    private long d;
    private long e;
    private long f;
    private boolean g;

    private OfflineRegionStatus(int downloadState, long completedResourceCount, long completedResourceSize, long completedTileCount, long completedTileSize, long requiredResourceCount, boolean requiredResourceCountIsPrecise) {
        this.a = 0;
        this.b = 0L;
        this.c = 0L;
        this.d = 0L;
        this.e = 0L;
        this.f = 0L;
        this.g = true;
        this.a = downloadState;
        this.b = completedResourceCount;
        this.c = completedResourceSize;
        this.d = completedTileCount;
        this.e = completedTileSize;
        this.f = requiredResourceCount;
        this.g = requiredResourceCountIsPrecise;
    }
}
