package com.naver.maps.map.overlay;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;

/* loaded from: classes.dex */
public final class CircleOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -200000;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native LatLng nativeGetCenter();

    private native int nativeGetColor();

    private native int nativeGetOutlineColor();

    private native int nativeGetOutlineWidth();

    private native double nativeGetRadius();

    private native void nativeSetCenter(double d, double d2);

    private native void nativeSetColor(int i);

    private native void nativeSetOutlineColor(int i);

    private native void nativeSetOutlineWidth(int i);

    private native void nativeSetRadius(double d);

    public CircleOverlay() {
    }

    public CircleOverlay(LatLng center, double radius) {
        setCenter(center);
        setRadius(radius);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a() {
        nativeCreate();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b() {
        nativeDestroy();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public void setMap(NaverMap map) {
        super.setMap(map);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public int getGlobalZIndex() {
        return super.getGlobalZIndex();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public void setGlobalZIndex(int globalZIndex) {
        super.setGlobalZIndex(globalZIndex);
    }

    public LatLng getCenter() {
        e();
        return nativeGetCenter();
    }

    public void setCenter(LatLng center) {
        e();
        a("center", center);
        nativeSetCenter(center.latitude, center.longitude);
    }

    public double getRadius() {
        e();
        return nativeGetRadius();
    }

    public void setRadius(double radius) {
        e();
        nativeSetRadius(radius);
    }

    public LatLngBounds getBounds() {
        e();
        return nativeGetBounds();
    }

    public int getColor() {
        e();
        return nativeGetColor();
    }

    public void setColor(int color) {
        e();
        nativeSetColor(color);
    }

    public int getOutlineWidth() {
        e();
        return nativeGetOutlineWidth();
    }

    public void setOutlineWidth(int width) {
        e();
        nativeSetOutlineWidth(width);
    }

    public int getOutlineColor() {
        e();
        return nativeGetOutlineColor();
    }

    public void setOutlineColor(int color) {
        e();
        nativeSetOutlineColor(color);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        a("center", getCenter());
        super.a(naverMap);
    }
}
