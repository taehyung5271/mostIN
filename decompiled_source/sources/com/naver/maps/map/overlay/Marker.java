package com.naver.maps.map.overlay;

import android.graphics.PointF;
import androidx.core.util.ObjectsCompat;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.util.MarkerIcons;

/* loaded from: classes.dex */
public final class Marker extends Overlay {
    public static final float DEFAULT_CAPTION_TEXT_SIZE = 12.0f;
    public static final int DEFAULT_GLOBAL_Z_INDEX = 200000;
    public static final int SIZE_AUTO = 0;
    private OverlayImage a;
    private Align[] b = DEFAULT_CAPTION_ALIGNS;
    private InfoWindow c;
    public static final OverlayImage DEFAULT_ICON = MarkerIcons.GREEN;
    public static final PointF DEFAULT_ANCHOR = new PointF(0.5f, 1.0f);
    public static final Align[] DEFAULT_CAPTION_ALIGNS = {Align.Bottom};

    private native void nativeCreate();

    private native void nativeDestroy();

    private native float nativeGetAlpha();

    private native PointF nativeGetAnchor();

    private native float nativeGetAngle();

    private native int nativeGetCaptionColor();

    private native String[] nativeGetCaptionFontFamily();

    private native int nativeGetCaptionHaloColor();

    private native int nativeGetCaptionOffset();

    private native int nativeGetCaptionRequestedWidth();

    private native String nativeGetCaptionText();

    private native float nativeGetCaptionTextSize();

    private native int nativeGetHeight();

    private native int nativeGetIconTintColor();

    private native LatLng nativeGetPosition();

    private native int nativeGetSubCaptionColor();

    private native String[] nativeGetSubCaptionFontFamily();

    private native int nativeGetSubCaptionHaloColor();

    private native int nativeGetSubCaptionRequestedWidth();

    private native String nativeGetSubCaptionText();

    private native float nativeGetSubCaptionTextSize();

    private native int nativeGetWidth();

    private native boolean nativeIsCaptionPerspectiveEnabled();

    private native boolean nativeIsFlat();

    private native boolean nativeIsForceShowCaption();

    private native boolean nativeIsForceShowIcon();

    private native boolean nativeIsHideCollidedCaptions();

    private native boolean nativeIsHideCollidedMarkers();

    private native boolean nativeIsHideCollidedSymbols();

    private native boolean nativeIsIconPerspectiveEnabled();

    private native boolean nativeIsOccupySpaceOnCollision();

    private native void nativeSetAlpha(float f);

    private native void nativeSetAnchor(float f, float f2);

    private native void nativeSetAngle(float f);

    private native void nativeSetCaptionAligns(int[] iArr);

    private native void nativeSetCaptionColor(int i);

    private native void nativeSetCaptionFontFamily(String[] strArr);

    private native void nativeSetCaptionHaloColor(int i);

    private native void nativeSetCaptionOffset(int i);

    private native void nativeSetCaptionPerspectiveEnabled(boolean z);

    private native void nativeSetCaptionRequestedWidth(int i);

    private native void nativeSetCaptionText(String str);

    private native void nativeSetCaptionTextSize(float f);

    private native void nativeSetFlat(boolean z);

    private native void nativeSetForceShowCaption(boolean z);

    private native void nativeSetForceShowIcon(boolean z);

    private native void nativeSetHeight(int i);

    private native void nativeSetHideCollidedCaptions(boolean z);

    private native void nativeSetHideCollidedMarkers(boolean z);

    private native void nativeSetHideCollidedSymbols(boolean z);

    private native void nativeSetIcon(OverlayImage overlayImage);

    private native void nativeSetIconPerspectiveEnabled(boolean z);

    private native void nativeSetIconTintColor(int i);

    private native void nativeSetOccupySpaceOnCollision(boolean z);

    private native void nativeSetPosition(double d, double d2);

    private native void nativeSetSubCaptionColor(int i);

    private native void nativeSetSubCaptionFontFamily(String[] strArr);

    private native void nativeSetSubCaptionHaloColor(int i);

    private native void nativeSetSubCaptionRequestedWidth(int i);

    private native void nativeSetSubCaptionText(String str);

    private native void nativeSetSubCaptionTextSize(float f);

    private native void nativeSetWidth(int i);

    protected native double nativeGetCaptionMaxZoom();

    protected native double nativeGetCaptionMinZoom();

    protected native double nativeGetSubCaptionMaxZoom();

    protected native double nativeGetSubCaptionMinZoom();

    protected native void nativeSetCaptionMaxZoom(double d);

    protected native void nativeSetCaptionMinZoom(double d);

    protected native void nativeSetSubCaptionMaxZoom(double d);

    protected native void nativeSetSubCaptionMinZoom(double d);

    public Marker() {
        setIcon(DEFAULT_ICON);
    }

    public Marker(LatLng position) {
        setPosition(position);
        setIcon(DEFAULT_ICON);
    }

    public Marker(OverlayImage icon) {
        setIcon(icon);
    }

    public Marker(LatLng position, OverlayImage icon) {
        setPosition(position);
        setIcon(icon);
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

    public LatLng getPosition() {
        e();
        return nativeGetPosition();
    }

    public void setPosition(LatLng position) {
        e();
        a("position", position);
        nativeSetPosition(position.latitude, position.longitude);
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

    public int getIconTintColor() {
        e();
        return nativeGetIconTintColor();
    }

    public void setIconTintColor(int color) {
        e();
        nativeSetIconTintColor(color);
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

    public String getCaptionText() {
        e();
        return nativeGetCaptionText();
    }

    public void setCaptionText(String caption) {
        e();
        nativeSetCaptionText(caption == null ? "" : caption);
    }

    public float getCaptionTextSize() {
        e();
        return nativeGetCaptionTextSize();
    }

    public void setCaptionTextSize(float size) {
        e();
        nativeSetCaptionTextSize(size);
    }

    public int getCaptionColor() {
        e();
        return nativeGetCaptionColor();
    }

    public void setCaptionColor(int color) {
        e();
        nativeSetCaptionColor(color);
    }

    public int getCaptionHaloColor() {
        e();
        return nativeGetCaptionHaloColor();
    }

    public void setCaptionHaloColor(int strokeColor) {
        e();
        nativeSetCaptionHaloColor(strokeColor);
    }

    public int getCaptionRequestedWidth() {
        e();
        return nativeGetCaptionRequestedWidth();
    }

    public void setCaptionRequestedWidth(int requestedWidth) {
        e();
        nativeSetCaptionRequestedWidth(requestedWidth);
    }

    public double getCaptionMinZoom() {
        e();
        return nativeGetCaptionMinZoom();
    }

    public void setCaptionMinZoom(double minZoom) {
        e();
        nativeSetCaptionMinZoom(minZoom);
    }

    public double getCaptionMaxZoom() {
        e();
        return nativeGetCaptionMaxZoom();
    }

    public void setCaptionMaxZoom(double maxZoom) {
        e();
        nativeSetCaptionMaxZoom(maxZoom);
    }

    public String getSubCaptionText() {
        e();
        return nativeGetSubCaptionText();
    }

    public void setSubCaptionText(String caption) {
        e();
        nativeSetSubCaptionText(caption == null ? "" : caption);
    }

    public float getSubCaptionTextSize() {
        e();
        return nativeGetSubCaptionTextSize();
    }

    public void setSubCaptionTextSize(float size) {
        e();
        nativeSetSubCaptionTextSize(size);
    }

    public int getSubCaptionColor() {
        e();
        return nativeGetSubCaptionColor();
    }

    public void setSubCaptionColor(int color) {
        e();
        nativeSetSubCaptionColor(color);
    }

    public int getSubCaptionHaloColor() {
        e();
        return nativeGetSubCaptionHaloColor();
    }

    public void setSubCaptionHaloColor(int strokeColor) {
        e();
        nativeSetSubCaptionHaloColor(strokeColor);
    }

    public String[] getSubCaptionFontFamily() {
        e();
        return nativeGetSubCaptionFontFamily();
    }

    public void setSubCaptionFontFamily(String... fontFamily) {
        e();
        nativeSetSubCaptionFontFamily(fontFamily);
    }

    public int getSubCaptionRequestedWidth() {
        e();
        return nativeGetSubCaptionRequestedWidth();
    }

    public void setSubCaptionRequestedWidth(int requestedWidth) {
        e();
        nativeSetSubCaptionRequestedWidth(requestedWidth);
    }

    public double getSubCaptionMinZoom() {
        e();
        return nativeGetSubCaptionMinZoom();
    }

    public void setSubCaptionMinZoom(double minZoom) {
        e();
        nativeSetSubCaptionMinZoom(minZoom);
    }

    public double getSubCaptionMaxZoom() {
        e();
        return nativeGetSubCaptionMaxZoom();
    }

    public void setSubCaptionMaxZoom(double maxZoom) {
        e();
        nativeSetSubCaptionMaxZoom(maxZoom);
    }

    @Deprecated
    public Align getCaptionAlign() {
        return getCaptionAligns()[0];
    }

    @Deprecated
    public void setCaptionAlign(Align align) {
        setCaptionAligns(align);
    }

    public Align[] getCaptionAligns() {
        e();
        return this.b;
    }

    public void setCaptionAligns(Align... aligns) {
        if (aligns.length == 0) {
            throw new IllegalArgumentException();
        }
        e();
        int[] iArr = new int[aligns.length];
        for (int i = 0; i < aligns.length; i++) {
            iArr[i] = aligns[i].ordinal();
        }
        this.b = aligns;
        nativeSetCaptionAligns(iArr);
    }

    public int getCaptionOffset() {
        e();
        return nativeGetCaptionOffset();
    }

    public void setCaptionOffset(int offset) {
        e();
        nativeSetCaptionOffset(offset);
    }

    public float getAlpha() {
        e();
        return nativeGetAlpha();
    }

    public void setAlpha(float alpha) {
        e();
        nativeSetAlpha(alpha);
    }

    public float getAngle() {
        e();
        return nativeGetAngle();
    }

    public void setAngle(float angle) {
        e();
        nativeSetAngle(angle);
    }

    public boolean isFlat() {
        e();
        return nativeIsFlat();
    }

    public void setFlat(boolean flat) {
        e();
        nativeSetFlat(flat);
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

    public boolean isForceShowIcon() {
        e();
        return nativeIsForceShowIcon();
    }

    public void setForceShowIcon(boolean forceShowIcon) {
        e();
        nativeSetForceShowIcon(forceShowIcon);
    }

    public boolean isForceShowCaption() {
        e();
        return nativeIsForceShowCaption();
    }

    public void setForceShowCaption(boolean forceShowCaption) {
        e();
        nativeSetForceShowCaption(forceShowCaption);
    }

    public boolean isOccupySpaceOnCollision() {
        e();
        return nativeIsOccupySpaceOnCollision();
    }

    public void setOccupySpaceOnCollision(boolean occupySpaceOnCollision) {
        e();
        nativeSetOccupySpaceOnCollision(occupySpaceOnCollision);
    }

    public boolean isIconPerspectiveEnabled() {
        e();
        return nativeIsIconPerspectiveEnabled();
    }

    public void setIconPerspectiveEnabled(boolean enabled) {
        e();
        nativeSetIconPerspectiveEnabled(enabled);
    }

    public boolean isCaptionPerspectiveEnabled() {
        e();
        return nativeIsCaptionPerspectiveEnabled();
    }

    public void setCaptionPerspectiveEnabled(boolean enabled) {
        e();
        nativeSetCaptionPerspectiveEnabled(enabled);
    }

    public boolean hasInfoWindow() {
        return this.c != null;
    }

    public InfoWindow getInfoWindow() {
        return this.c;
    }

    void a(InfoWindow infoWindow) {
        if (!isAdded() || this.c == infoWindow) {
            return;
        }
        this.c = infoWindow;
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        a("position", getPosition());
        super.a(naverMap);
        nativeSetIcon(this.a);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        if (this.c != null) {
            this.c.close();
        }
        nativeSetIcon(null);
        super.b(naverMap);
    }
}
