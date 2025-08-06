package com.naver.maps.map.overlay;

import android.graphics.PointF;
import androidx.core.util.ObjectsCompat;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public final class ViewportOverlay extends Overlay {
    public static final PointF DEFAULT_ANCHOR = new PointF(0.0f, 0.0f);
    public static final OverlayImage a = OverlayImage.fromResource(R.drawable.navermap_default_ground_overlay_image);
    private OverlayImage b;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native int nativeGetAlign();

    private native float nativeGetAlpha();

    private native PointF nativeGetAnchor();

    private native int nativeGetHeight();

    private native int nativeGetOffsetX();

    private native int nativeGetOffsetY();

    private native int nativeGetWidth();

    private native void nativeSetAlign(int i);

    private native void nativeSetAlpha(float f);

    private native void nativeSetAnchor(float f, float f2);

    private native void nativeSetHeight(int i);

    private native void nativeSetImage(OverlayImage overlayImage);

    private native void nativeSetOffsetX(int i);

    private native void nativeSetOffsetY(int i);

    private native void nativeSetWidth(int i);

    public ViewportOverlay() {
        setImage(a);
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
    public int getGlobalZIndex() {
        return super.getGlobalZIndex();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public void setGlobalZIndex(int globalZIndex) {
        super.setGlobalZIndex(globalZIndex);
    }

    public OverlayImage getImage() {
        e();
        return this.b;
    }

    public void setImage(OverlayImage image) {
        e();
        if (ObjectsCompat.equals(this.b, image)) {
            return;
        }
        this.b = image;
        if (isAdded()) {
            nativeSetImage(image);
        }
    }

    public int getWidth() {
        e();
        return nativeGetWidth();
    }

    public void setWidth(int width) {
        e();
        nativeSetWidth(width);
    }

    public int getHeight() {
        e();
        return nativeGetHeight();
    }

    public void setHeight(int height) {
        e();
        nativeSetHeight(height);
    }

    public PointF getAnchor() {
        e();
        return nativeGetAnchor();
    }

    public void setAnchor(PointF anchor) {
        e();
        nativeSetAnchor(anchor.x, anchor.y);
    }

    public Align getAlign() {
        return Align.values()[nativeGetAlign()];
    }

    public void setAlign(Align align) {
        nativeSetAlign(align.ordinal());
    }

    public int getOffsetX() {
        e();
        return nativeGetOffsetX();
    }

    public void setOffsetX(int offset) {
        e();
        nativeSetOffsetX(offset);
    }

    public int getOffsetY() {
        e();
        return nativeGetOffsetY();
    }

    public void setOffsetY(int offset) {
        e();
        nativeSetOffsetY(offset);
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
        super.a(naverMap);
        nativeSetImage(this.b);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        nativeSetImage(null);
        super.b(naverMap);
    }
}
