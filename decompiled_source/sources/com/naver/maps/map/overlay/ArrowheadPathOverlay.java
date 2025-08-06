package com.naver.maps.map.overlay;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class ArrowheadPathOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = 100000;
    private List<LatLng> a = Collections.emptyList();

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native int nativeGetColor();

    private native int nativeGetElevation();

    private native float nativeGetHeadSizeRatio();

    private native int nativeGetOutlineColor();

    private native int nativeGetOutlineWidth();

    private native int nativeGetWidth();

    private native void nativeSetColor(int i);

    private native void nativeSetCoords(double[] dArr);

    private native void nativeSetElevation(int i);

    private native void nativeSetHeadSizeRatio(float f);

    private native void nativeSetOutlineColor(int i);

    private native void nativeSetOutlineWidth(int i);

    private native void nativeSetWidth(int i);

    public ArrowheadPathOverlay() {
    }

    public ArrowheadPathOverlay(List<LatLng> coords) {
        setCoords(coords);
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

    public List<LatLng> getCoords() {
        e();
        return this.a;
    }

    public void setCoords(List<LatLng> coords) {
        e();
        nativeSetCoords(a("coords", coords, 2));
        this.a = coords;
    }

    public LatLngBounds getBounds() {
        e();
        return nativeGetBounds();
    }

    public int getWidth() {
        e();
        return nativeGetWidth();
    }

    public void setWidth(int width) {
        e();
        nativeSetWidth(width);
    }

    public float getHeadSizeRatio() {
        e();
        return nativeGetHeadSizeRatio();
    }

    public void setHeadSizeRatio(float headSizeRatio) {
        e();
        nativeSetHeadSizeRatio(headSizeRatio);
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

    public int getElevation() {
        e();
        return nativeGetElevation();
    }

    public void setElevation(int elevation) {
        e();
        nativeSetElevation(elevation);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        if (getCoords().size() < 2) {
            throw new IllegalStateException("coords.size() < 2");
        }
        super.a(naverMap);
    }
}
