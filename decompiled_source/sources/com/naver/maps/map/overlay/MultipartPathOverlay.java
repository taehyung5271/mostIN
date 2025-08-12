package com.naver.maps.map.overlay;

import androidx.core.util.ObjectsCompat;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class MultipartPathOverlay extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = -100000;
    private List<List<LatLng>> a = Collections.emptyList();
    private List<ColorPart> b = Collections.emptyList();
    private OverlayImage c;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native LatLngBounds nativeGetBounds();

    private native int nativeGetOutlineWidth();

    private native int nativeGetPatternInterval();

    private native double nativeGetProgress();

    private native int nativeGetWidth();

    private native boolean nativeIsHideCollidedCaptions();

    private native boolean nativeIsHideCollidedMarkers();

    private native boolean nativeIsHideCollidedSymbols();

    private native void nativeSetColorParts(ColorPart[] colorPartArr);

    private native void nativeSetCoordParts(Object[] objArr);

    private native void nativeSetHideCollidedCaptions(boolean z);

    private native void nativeSetHideCollidedMarkers(boolean z);

    private native void nativeSetHideCollidedSymbols(boolean z);

    private native void nativeSetOutlineWidth(int i);

    private native void nativeSetPatternImage(OverlayImage overlayImage);

    private native void nativeSetPatternInterval(int i);

    private native void nativeSetProgress(double d);

    private native void nativeSetWidth(int i);

    public static final class ColorPart {
        private int color;
        private int outlineColor;
        private int passedColor;
        private int passedOutlineColor;

        public ColorPart() {
            this.color = 0;
            this.outlineColor = 0;
            this.passedColor = 0;
            this.passedOutlineColor = 0;
        }

        public ColorPart(int color, int outlineColor, int passedColor, int passedOutlineColor) {
            this.color = color;
            this.outlineColor = outlineColor;
            this.passedColor = passedColor;
            this.passedOutlineColor = passedOutlineColor;
        }

        public int getColor() {
            return this.color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getOutlineColor() {
            return this.outlineColor;
        }

        public void setOutlineColor(int outlineColor) {
            this.outlineColor = outlineColor;
        }

        public int getPassedColor() {
            return this.passedColor;
        }

        public void setPassedColor(int passedColor) {
            this.passedColor = passedColor;
        }

        public int getPassedOutlineColor() {
            return this.passedOutlineColor;
        }

        public void setPassedOutlineColor(int passedOutlineColor) {
            this.passedOutlineColor = passedOutlineColor;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ColorPart colorPart = (ColorPart) obj;
            if (colorPart.color == this.color && colorPart.outlineColor == this.outlineColor && colorPart.passedColor == this.passedColor && colorPart.passedOutlineColor == this.passedOutlineColor) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((this.color * 31) + this.outlineColor) * 31) + this.passedColor) * 31) + this.passedOutlineColor;
        }

        public String toString() {
            return "ColorPart{color=" + String.format("#%08X", Integer.valueOf(this.color)) + ", outlineColor=" + String.format("#%08X", Integer.valueOf(this.outlineColor)) + ", passedColor=" + String.format("#%08X", Integer.valueOf(this.passedColor)) + ", passedOutlineColor=" + String.format("#%08X", Integer.valueOf(this.passedOutlineColor)) + '}';
        }
    }

    public MultipartPathOverlay() {
    }

    public MultipartPathOverlay(List<List<LatLng>> coordParts, List<ColorPart> colorParts) {
        setCoordParts(coordParts);
        setColorParts(colorParts);
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

    public List<List<LatLng>> getCoordParts() {
        e();
        return this.a;
    }

    public void setCoordParts(List<List<LatLng>> coordParts) {
        e();
        if (coordParts.size() < 1) {
            throw new IllegalArgumentException("coordParts.size() < 1");
        }
        double[][] dArr = new double[coordParts.size()][];
        Iterator<List<LatLng>> it = coordParts.iterator();
        int i = 0;
        while (it.hasNext()) {
            dArr[i] = a("coordParts[" + i + "]", it.next(), 2);
            i++;
        }
        nativeSetCoordParts(dArr);
        this.a = coordParts;
    }

    public LatLngBounds getBounds() {
        e();
        return nativeGetBounds();
    }

    public List<ColorPart> getColorParts() {
        e();
        return this.b;
    }

    public void setColorParts(List<ColorPart> colorParts) {
        e();
        if (colorParts.size() < 1) {
            throw new IllegalArgumentException("colorParts.size() < 1");
        }
        Iterator<ColorPart> it = colorParts.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new IllegalArgumentException("colorParts[" + i + "] is null");
            }
            i++;
        }
        nativeSetColorParts((ColorPart[]) colorParts.toArray(new ColorPart[0]));
        this.b = colorParts;
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

    public OverlayImage getPatternImage() {
        e();
        return this.c;
    }

    public void setPatternImage(OverlayImage pattern) {
        e();
        if (ObjectsCompat.equals(this.c, pattern)) {
            return;
        }
        this.c = pattern;
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
        if (this.a.size() < 1) {
            throw new IllegalStateException("coordParts.size() < 1");
        }
        super.a(naverMap);
        nativeSetPatternImage(this.c);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        nativeSetPatternImage(null);
        super.b(naverMap);
    }
}
