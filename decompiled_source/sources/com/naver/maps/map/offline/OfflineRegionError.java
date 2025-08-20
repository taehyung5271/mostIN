package com.naver.maps.map.offline;

/* loaded from: classes.dex */
public class OfflineRegionError {
    private final String a;
    private final String b;

    private OfflineRegionError(String reason, String message) {
        this.a = reason;
        this.b = message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfflineRegionError offlineRegionError = (OfflineRegionError) o;
        if (!this.a.equals(offlineRegionError.a)) {
            return false;
        }
        return this.b.equals(offlineRegionError.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }
}
