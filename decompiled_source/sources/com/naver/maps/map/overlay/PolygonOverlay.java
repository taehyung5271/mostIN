package com.naver.maps.map.overlay;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class PolygonOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -200000;
    private List<LatLng> a = Collections.emptyList();
    private List<List<LatLng>> b = Collections.emptyList();

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native int nativeGetColor();

    private native int nativeGetOutlineColor();

    private native int[] nativeGetOutlinePattern();

    private native int nativeGetOutlineWidth();

    private native void nativeSetColor(int i);

    private native void nativeSetCoords(double[] dArr);

    private native void nativeSetHoles(Object[] objArr);

    private native void nativeSetOutlineColor(int i);

    private native void nativeSetOutlinePattern(int[] iArr);

    private native void nativeSetOutlineWidth(int i);

    public PolygonOverlay() {
    }

    public PolygonOverlay(List<LatLng> coords) {
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
        nativeSetCoords(a("coords", coords, 3, true));
        this.a = coords;
    }

    public LatLngBounds getBounds() {
        e();
        return nativeGetBounds();
    }

    public List<List<LatLng>> getHoles() {
        e();
        return this.b;
    }

    public void setHoles(List<List<LatLng>> holes) {
        e();
        double[][] dArr = new double[holes.size()][];
        Iterator<List<LatLng>> it = holes.iterator();
        int i = 0;
        while (it.hasNext()) {
            dArr[i] = a("holes[" + i + "]", it.next(), 3, true);
            i++;
        }
        nativeSetHoles(dArr);
        this.b = holes;
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

    public int[] getOutlinePattern() {
        e();
        return nativeGetOutlinePattern();
    }

    public void setOutlinePattern(int... pattern) {
        e();
        nativeSetOutlinePattern(pattern);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        if (getCoords().size() < 3) {
            throw new IllegalStateException("coords.size() < 3");
        }
        super.a(naverMap);
    }
}
