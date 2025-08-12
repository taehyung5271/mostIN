package com.naver.maps.map;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public class TileId {
    public static int z(long tileId) {
        return (int) (tileId >>> 56);
    }

    public static int x(long tileId) {
        return (int) ((tileId >>> 28) & 268435455);
    }

    public static int y(long tileId) {
        return (int) (268435455 & tileId);
    }

    public static long from(int z, int x, int y) {
        return (z << 56) | (x << 28) | y;
    }

    public static long from(int z, WebMercatorCoord coord) {
        double dA = a(z);
        return from(z, (int) ((coord.x - (-2.0037508342789244E7d)) / dA), (int) ((2.0037508342789244E7d - coord.y) / dA));
    }

    public static long from(int z, LatLng coord) {
        return from(z, WebMercatorCoord.valueOf(coord));
    }

    public static LatLngBounds toLatLngBounds(long tileId) {
        return toLatLngBounds(z(tileId), x(tileId), y(tileId));
    }

    public static LatLngBounds toLatLngBounds(int z, int x, int y) {
        return new LatLngBounds(new LatLng(a(z, y + 1), b(z, x)), new LatLng(a(z, y), b(z, x + 1)));
    }

    public static WebMercatorCoord getCenter(long tileId) {
        return getCenter(z(tileId), x(tileId), y(tileId));
    }

    public static WebMercatorCoord getCenter(int z, int x, int y) {
        return new WebMercatorCoord(((x + 0.5d) * r0) - 2.0037508342789244E7d, 2.0037508342789244E7d - (a(z) * (y + 0.5d)));
    }

    public static CameraPosition getCameraPosition(long tileId) {
        return getCameraPosition(z(tileId), x(tileId), y(tileId));
    }

    public static CameraPosition getCameraPosition(int z, int x, int y) {
        return new CameraPosition(getCenter(z, x, y).toLatLng(), z);
    }

    private static double a(int i) {
        return 4.007501668557849E7d / (1 << i);
    }

    private static double a(int i, int i2) {
        double d = 3.141592653589793d - ((i2 * 6.283185307179586d) / (1 << i));
        return Math.toDegrees(Math.atan((Math.exp(d) - Math.exp(-d)) * 0.5d));
    }

    private static double b(int i, int i2) {
        return ((i2 * 360.0d) / (1 << i)) - 180.0d;
    }

    private TileId() {
    }
}
