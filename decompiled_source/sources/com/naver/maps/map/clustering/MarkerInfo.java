package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public abstract class MarkerInfo {
    private final long a;
    private final Object b;
    private final WebMercatorCoord c;
    private final LatLng d;
    private final int e;
    private final int f;

    MarkerInfo(long id, Object tag, WebMercatorCoord coord, LatLng position, int minZoom, int maxZoom) {
        this.a = id;
        this.b = tag;
        this.c = coord;
        this.d = position;
        this.e = minZoom;
        this.f = maxZoom;
    }

    long a() {
        return this.a;
    }

    public Object getTag() {
        return this.b;
    }

    WebMercatorCoord b() {
        return this.c;
    }

    public LatLng getPosition() {
        return this.d;
    }

    public int getMinZoom() {
        return this.e;
    }

    public int getMaxZoom() {
        return this.f;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarkerInfo markerInfo = (MarkerInfo) o;
        if (this.a != markerInfo.a || this.e != markerInfo.e || this.f != markerInfo.f) {
            return false;
        }
        if (this.b == null ? markerInfo.b != null : !this.b.equals(markerInfo.b)) {
            return false;
        }
        if (!this.c.equals(markerInfo.c)) {
            return false;
        }
        return this.d.equals(markerInfo.d);
    }

    public int hashCode() {
        return (((((((((((int) (this.a ^ (this.a >>> 32))) * 31) + (this.b != null ? this.b.hashCode() : 0)) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e) * 31) + this.f;
    }
}
