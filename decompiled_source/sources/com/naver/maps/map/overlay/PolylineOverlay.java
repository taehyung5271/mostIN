package com.naver.maps.map.overlay;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class PolylineOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -200000;
    private List<LatLng> a = Collections.emptyList();

    public enum LineCap {
        Round,
        Butt,
        Square
    }

    public enum LineJoin {
        Miter,
        Bevel,
        Round
    }

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native int nativeGetCapType();

    private native int nativeGetColor();

    private native int nativeGetJoinType();

    private native int[] nativeGetPattern();

    private native int nativeGetWidth();

    private native void nativeSetCapType(int i);

    private native void nativeSetColor(int i);

    private native void nativeSetCoords(double[] dArr);

    private native void nativeSetJoinType(int i);

    private native void nativeSetPattern(int[] iArr);

    private native void nativeSetWidth(int i);

    public PolylineOverlay() {
    }

    public PolylineOverlay(List<LatLng> coords) {
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

    public int getColor() {
        e();
        return nativeGetColor();
    }

    public void setColor(int color) {
        e();
        nativeSetColor(color);
    }

    public int[] getPattern() {
        e();
        return nativeGetPattern();
    }

    public void setPattern(int... pattern) {
        e();
        nativeSetPattern(pattern);
    }

    public LineCap getCapType() {
        e();
        return LineCap.values()[nativeGetCapType()];
    }

    public void setCapType(LineCap capType) {
        e();
        nativeSetCapType(capType.ordinal());
    }

    public LineJoin getJoinType() {
        e();
        return LineJoin.values()[nativeGetJoinType()];
    }

    public void setJoinType(LineJoin joinType) {
        e();
        nativeSetJoinType(joinType.ordinal());
    }
}
