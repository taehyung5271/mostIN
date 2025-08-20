package com.naver.maps.map.overlay;

import androidx.core.util.ObjectsCompat;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public final class GroundOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -300000;
    public static final OverlayImage DEFAULT_IMAGE = OverlayImage.fromResource(R.drawable.navermap_default_ground_overlay_image);
    private OverlayImage a;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native float nativeGetAlpha();

    private native LatLngBounds nativeGetBounds();

    private native void nativeSetAlpha(float f);

    private native void nativeSetBounds(LatLngBounds latLngBounds);

    private native void nativeSetImage(OverlayImage overlayImage);

    public GroundOverlay() {
        setImage(DEFAULT_IMAGE);
    }

    public GroundOverlay(LatLngBounds bounds, OverlayImage image) {
        setBounds(bounds);
        setImage(image);
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

    public LatLngBounds getBounds() {
        e();
        return nativeGetBounds();
    }

    public void setBounds(LatLngBounds bounds) {
        e();
        a("bounds", bounds);
        nativeSetBounds(bounds);
    }

    public OverlayImage getImage() {
        e();
        return this.a;
    }

    public void setImage(OverlayImage image) {
        e();
        if (ObjectsCompat.equals(this.a, image)) {
            return;
        }
        this.a = image;
        if (isAdded()) {
            nativeSetImage(image);
        }
    }

    public float getAlpha() {
        e();
        return nativeGetAlpha();
    }

    public void setAlpha(float alpha) {
        e();
        nativeSetAlpha(alpha);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        a("bounds", getBounds());
        super.a(naverMap);
        nativeSetImage(this.a);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        nativeSetImage(null);
        super.b(naverMap);
    }
}
