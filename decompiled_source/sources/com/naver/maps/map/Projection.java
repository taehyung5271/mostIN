package com.naver.maps.map;

import android.graphics.PointF;
import com.naver.maps.geometry.LatLng;

/* loaded from: classes.dex */
public final class Projection {
    private final NaverMap a;
    private final NativeMapView b;

    public static double getMetersPerDp(double latitude, double zoom) {
        return (((Math.cos(Math.toRadians(latitude)) * 3.141592653589793d) * 6378137.0d) / Math.pow(2.0d, zoom)) / 256.0d;
    }

    Projection(NaverMap naverMap, NativeMapView nativeMapView) {
        this.a = naverMap;
        this.b = nativeMapView;
    }

    public double getMetersPerDp() {
        return getMetersPerDp(this.a.getCameraPosition().target.latitude, this.a.getCameraPosition().zoom);
    }

    public double getMetersPerPixel() {
        return getMetersPerDp() / this.b.b();
    }

    public double getMetersPerPixel(double latitude, double zoom) {
        return getMetersPerDp(latitude, zoom) / this.b.b();
    }

    public LatLng fromScreenLocation(PointF point) {
        return this.b.a(point);
    }

    public LatLng fromScreenLocationAt(PointF point, double zoom, double tilt, double bearing, boolean wrap) {
        return this.b.a(point, zoom, tilt, bearing, wrap);
    }

    public PointF toScreenLocation(LatLng coord) {
        return this.b.a(coord);
    }

    public PointF toScreenLocationAt(LatLng coord, double zoom, double bearing, double tilt, boolean shortestPath) {
        return this.b.a(coord, zoom, bearing, tilt, shortestPath);
    }

    LatLng a(PointF pointF, double d) {
        return this.b.a(pointF, d);
    }

    PointF a(LatLng latLng, double d) {
        return this.b.a(latLng, d);
    }
}
