package com.naver.maps.map.overlay;

import androidx.core.util.ObjectsCompat;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class PathOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -100000;
    private List<LatLng> a = Collections.emptyList();
    private OverlayImage b;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native int nativeGetColor();

    private native int nativeGetOutlineColor();

    private native int nativeGetOutlineWidth();

    private native int nativeGetPassedColor();

    private native int nativeGetPassedOutlineColor();

    private native int nativeGetPatternInterval();

    private native double nativeGetProgress();

    private native int nativeGetWidth();

    private native boolean nativeIsHideCollidedCaptions();

    private native boolean nativeIsHideCollidedMarkers();

    private native boolean nativeIsHideCollidedSymbols();

    private native void nativeSetColor(int i);

    private native void nativeSetCoords(double[] dArr);

    private native void nativeSetHideCollidedCaptions(boolean z);

    private native void nativeSetHideCollidedMarkers(boolean z);

    private native void nativeSetHideCollidedSymbols(boolean z);

    private native void nativeSetOutlineColor(int i);

    private native void nativeSetOutlineWidth(int i);

    private native void nativeSetPassedColor(int i);

    private native void nativeSetPassedOutlineColor(int i);

    private native void nativeSetPatternImage(OverlayImage overlayImage);

    private native void nativeSetPatternInterval(int i);

    private native void nativeSetProgress(double d);

    private native void nativeSetWidth(int i);

    public PathOverlay() {
    }

    public PathOverlay(List<LatLng> coords) {
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

    public double getProgress() {
        e();
        return nativeGetProgress();
    }

    public void setProgress(double progress) {
        e();
        nativeSetProgress(progress);
    }

    public int getWidth() {
        e();
        return nativeGetWidth();
    }

    public void setWidth(int width) {
        e();
        nativeSetWidth(width);
    }

    public int getOutlineWidth() {
        e();
        return nativeGetOutlineWidth();
    }

    public void setOutlineWidth(int width) {
        e();
        nativeSetOutlineWidth(width);
    }

    public int getColor() {
        e();
        return nativeGetColor();
    }

    public void setColor(int color) {
        e();
        nativeSetColor(color);
    }

    public int getOutlineColor() {
        e();
        return nativeGetOutlineColor();
    }

    public void setOutlineColor(int outlineColor) {
        e();
        nativeSetOutlineColor(outlineColor);
    }

    public int getPassedColor() {
        e();
        return nativeGetPassedColor();
    }

    public void setPassedColor(int passedColor) {
        e();
        nativeSetPassedColor(passedColor);
    }

    public int getPassedOutlineColor() {
        e();
        return nativeGetPassedOutlineColor();
    }

    public void setPassedOutlineColor(int passedOutlineColor) {
        e();
        nativeSetPassedOutlineColor(passedOutlineColor);
    }

    public OverlayImage getPatternImage() {
        e();
        return this.b;
    }

    public void setPatternImage(OverlayImage pattern) {
        e();
        if (ObjectsCompat.equals(this.b, pattern)) {
            return;
        }
        this.b = pattern;
        if (isAdded()) {
            nativeSetPatternImage(pattern);
        }
    }

    public int getPatternInterval() {
        e();
        return nativeGetPatternInterval();
    }

    public void setPatternInterval(int interval) {
        e();
        nativeSetPatternInterval(interval);
    }

    public boolean isHideCollidedSymbols() {
        e();
        return nativeIsHideCollidedSymbols();
    }

    public void setHideCollidedSymbols(boolean hide) {
        e();
        nativeSetHideCollidedSymbols(hide);
    }

    public boolean isHideCollidedMarkers() {
        e();
        return nativeIsHideCollidedMarkers();
    }

    public void setHideCollidedMarkers(boolean hide) {
        e();
        nativeSetHideCollidedMarkers(hide);
    }

    public boolean isHideCollidedCaptions() {
        e();
        return nativeIsHideCollidedCaptions();
    }

    public void setHideCollidedCaptions(boolean hide) {
        e();
        nativeSetHideCollidedCaptions(hide);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        if (getCoords().size() < 2) {
            throw new IllegalStateException("coords.size() < 2");
        }
        super.a(naverMap);
        nativeSetPatternImage(this.b);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        nativeSetPatternImage(null);
        super.b(naverMap);
    }
}
