package com.naver.maps.map.overlay;

import android.graphics.Color;
import android.graphics.PointF;
import androidx.core.util.ObjectsCompat;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public final class LocationOverlay extends Overlay {
    public static final int DEFAULT_CIRCLE_RADIUS_DP = 18;
    public static final int DEFAULT_GLOBAL_Z_INDEX = 300000;
    public static final int SIZE_AUTO = 0;
    private OverlayImage a;
    private OverlayImage b;
    public static final OverlayImage DEFAULT_ICON = OverlayImage.fromResource(R.drawable.navermap_location_overlay_icon);
    public static final OverlayImage DEFAULT_SUB_ICON_ARROW = OverlayImage.fromResource(R.drawable.navermap_default_location_overlay_sub_icon_arrow);
    public static final OverlayImage DEFAULT_SUB_ICON_CONE = OverlayImage.fromResource(R.drawable.navermap_default_location_overlay_sub_icon_cone);
    public static final int DEFAULT_CIRCLE_COLOR = Color.argb(10, 22, 102, 240);
    public static final PointF DEFAULT_ANCHOR = new PointF(0.5f, 0.5f);
    public static final PointF DEFAULT_SUB_ANCHOR = new PointF(0.5f, 1.0f);

    private native void nativeCreate();

    private native void nativeDestroy();

    private native float nativeGetBearing();

    private native int nativeGetCircleColor();

    private native int nativeGetCircleOutlineColor();

    private native int nativeGetCircleOutlineWidth();

    private native int nativeGetCircleRadius();

    private native PointF nativeGetIconAnchor();

    private native int nativeGetIconHeight();

    private native int nativeGetIconWidth();

    private native LatLng nativeGetPosition();

    private native PointF nativeGetSubIconAnchor();

    private native int nativeGetSubIconHeight();

    private native int nativeGetSubIconWidth();

    private native void nativeSetBearing(float f);

    private native void nativeSetCircleColor(int i);

    private native void nativeSetCircleOutlineColor(int i);

    private native void nativeSetCircleOutlineWidth(int i);

    private native void nativeSetCircleRadius(int i);

    private native void nativeSetIcon(OverlayImage overlayImage);

    private native void nativeSetIconAnchor(float f, float f2);

    private native void nativeSetIconHeight(int i);

    private native void nativeSetIconWidth(int i);

    private native void nativeSetPosition(double d, double d2);

    private native void nativeSetSubIcon(OverlayImage overlayImage);

    private native void nativeSetSubIconAnchor(float f, float f2);

    private native void nativeSetSubIconHeight(int i);

    private native void nativeSetSubIconWidth(int i);

    LocationOverlay() {
        setIcon(DEFAULT_ICON);
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
    @Deprecated
    public void setMap(NaverMap map) {
        if (isAdded()) {
            return;
        }
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

    public LatLng getPosition() {
        e();
        return nativeGetPosition();
    }

    public void setPosition(LatLng position) {
        e();
        a("position", position);
        nativeSetPosition(position.latitude, position.longitude);
    }

    public float getBearing() {
        e();
        return nativeGetBearing();
    }

    public void setBearing(float bearing) {
        e();
        nativeSetBearing(bearing);
    }

    public OverlayImage getIcon() {
        e();
        return this.a;
    }

    public void setIcon(OverlayImage icon) {
        e();
        if (ObjectsCompat.equals(this.a, icon)) {
            return;
        }
        this.a = icon;
        if (isAdded()) {
            nativeSetIcon(icon);
        }
    }

    public int getIconWidth() {
        e();
        return nativeGetIconWidth();
    }

    public void setIconWidth(int width) {
        e();
        nativeSetIconWidth(width);
    }

    public int getIconHeight() {
        e();
        return nativeGetIconHeight();
    }

    public void setIconHeight(int height) {
        e();
        nativeSetIconHeight(height);
    }

    public PointF getAnchor() {
        e();
        return nativeGetIconAnchor();
    }

    public void setAnchor(PointF anchor) {
        e();
        nativeSetIconAnchor(anchor.x, anchor.y);
    }

    public OverlayImage getSubIcon() {
        e();
        return this.b;
    }

    public void setSubIcon(OverlayImage subIcon) {
        e();
        if (ObjectsCompat.equals(this.b, subIcon)) {
            return;
        }
        this.b = subIcon;
        if (isAdded()) {
            nativeSetSubIcon(subIcon);
        }
    }

    public int getSubIconWidth() {
        e();
        return nativeGetSubIconWidth();
    }

    public void setSubIconWidth(int width) {
        e();
        nativeSetSubIconWidth(width);
    }

    public int getSubIconHeight() {
        e();
        return nativeGetSubIconHeight();
    }

    public void setSubIconHeight(int height) {
        e();
        nativeSetSubIconHeight(height);
    }

    public PointF getSubAnchor() {
        e();
        return nativeGetSubIconAnchor();
    }

    public void setSubAnchor(PointF anchor) {
        e();
        nativeSetSubIconAnchor(anchor.x, anchor.y);
    }

    public int getCircleRadius() {
        e();
        return nativeGetCircleRadius();
    }

    public void setCircleRadius(int radius) {
        e();
        nativeSetCircleRadius(radius);
    }

    public int getCircleColor() {
        e();
        return nativeGetCircleColor();
    }

    public void setCircleColor(int color) {
        e();
        nativeSetCircleColor(color);
    }

    public int getCircleOutlineWidth() {
        e();
        return nativeGetCircleOutlineWidth();
    }

    public void setCircleOutlineWidth(int width) {
        e();
        nativeSetCircleOutlineWidth(width);
    }

    public int getCircleOutlineColor() {
        e();
        return nativeGetCircleOutlineColor();
    }

    public void setCircleOutlineColor(int color) {
        e();
        nativeSetCircleOutlineColor(color);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        super.a(naverMap);
        nativeSetIcon(this.a);
        nativeSetSubIcon(this.b);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        nativeSetIcon(null);
        nativeSetSubIcon(null);
        super.b(naverMap);
    }
}
