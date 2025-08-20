package com.naver.maps.map.overlay;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.Pickable;
import com.naver.maps.map.internal.NaverMapAccessor;
import com.naver.maps.map.internal.OverlayAccessor;
import com.naver.maps.map.internal.a;
import com.naver.maps.map.internal.util.c;
import java.util.List;

/* loaded from: classes.dex */
public abstract class Overlay implements Pickable {
    private static NaverMapAccessor naverMapAccessor;
    private NaverMap a;
    private OnClickListener b;
    private Object c;
    private long handle;

    public interface OnClickListener {
        boolean onClick(Overlay overlay);
    }

    protected abstract void a();

    protected abstract void b();

    protected native int nativeGetGlobalZIndex();

    protected native double nativeGetMaxZoom();

    protected native double nativeGetMinZoom();

    protected native int nativeGetZIndex();

    protected native boolean nativeIsMaxZoomInclusive();

    protected native boolean nativeIsMinZoomInclusive();

    protected native boolean nativeIsPickable();

    protected native boolean nativeIsVisible();

    protected native void nativeSetGlobalZIndex(int i);

    protected native void nativeSetMaxZoom(double d);

    protected native void nativeSetMaxZoomInclusive(boolean z);

    protected native void nativeSetMinZoom(double d);

    protected native void nativeSetMinZoomInclusive(boolean z);

    protected native void nativeSetPickable(boolean z);

    protected native void nativeSetVisible(boolean z);

    protected native void nativeSetZIndex(int i);

    static {
        a.a();
    }

    private static class Accessor implements OverlayAccessor {
        private Accessor() {
        }

        @Override // com.naver.maps.map.internal.OverlayAccessor
        public LocationOverlay newLocationOverlay() {
            return new LocationOverlay();
        }
    }

    public static class InvalidCoordinateException extends RuntimeException {
        protected InvalidCoordinateException(String name, LatLng coord) {
            super(name + " is invalid: " + String.valueOf(coord));
        }
    }

    public static class InvalidBoundsException extends RuntimeException {
        protected InvalidBoundsException(String name, LatLngBounds bounds) {
            super(name + " is invalid: " + String.valueOf(bounds));
        }
    }

    protected static void a(String str, LatLng latLng) throws InvalidCoordinateException {
        if (latLng == null || !latLng.isValid()) {
            throw new InvalidCoordinateException(str, latLng);
        }
    }

    protected static void a(String str, LatLngBounds latLngBounds) throws InvalidBoundsException {
        if (latLngBounds == null || latLngBounds.isEmpty()) {
            throw new InvalidBoundsException(str, latLngBounds);
        }
    }

    protected static double[] a(String str, List<LatLng> list, int i) {
        return a(str, list, i, false);
    }

    protected static double[] a(String str, List<LatLng> list, int i, boolean z) throws InvalidCoordinateException {
        if (list == null) {
            throw new IllegalArgumentException(str + " is null");
        }
        int size = list.size();
        if (size < i) {
            throw new IllegalArgumentException(str + ".size() < " + i);
        }
        if (z && !list.get(0).equals(list.get(size - 1))) {
            size++;
        }
        int i2 = size * 2;
        double[] dArr = new double[i2];
        int i3 = 0;
        for (LatLng latLng : list) {
            a(str + "[" + i3 + "]", latLng);
            int i4 = i3 + 1;
            dArr[i3] = latLng.latitude;
            i3 = i4 + 1;
            dArr[i4] = latLng.longitude;
        }
        if (i3 == i2 - 2) {
            dArr[i3] = dArr[0];
            dArr[i3 + 1] = dArr[1];
        }
        return dArr;
    }

    Overlay() {
        a();
    }

    protected void finalize() throws Throwable {
        try {
            b();
        } finally {
            super.finalize();
        }
    }

    long d() {
        return this.handle;
    }

    protected void e() {
        if (this.a != null) {
            c.a(naverMapAccessor.getThread(this.a));
        }
    }

    public boolean isAdded() {
        return this.a != null;
    }

    public NaverMap getMap() {
        return this.a;
    }

    public void setMap(NaverMap map) {
        if (this.a == map) {
            return;
        }
        c.a(naverMapAccessor.getThread(map == null ? this.a : map));
        if (this.a != null) {
            b(this.a);
        }
        this.a = map;
        if (map != null) {
            a(map);
        }
    }

    protected void a(NaverMap naverMap) {
        naverMapAccessor.addOverlay(naverMap, this, this.handle);
    }

    protected void b(NaverMap naverMap) {
        naverMapAccessor.removeOverlay(naverMap, this, this.handle);
    }

    public boolean performClick() {
        e();
        if (this.b == null) {
            return false;
        }
        return this.b.onClick(this);
    }

    public OnClickListener getOnClickListener() {
        e();
        return this.b;
    }

    public void setOnClickListener(OnClickListener listener) {
        e();
        if (this.b == null && listener != null) {
            nativeSetPickable(true);
        } else if (this.b != null && listener == null) {
            nativeSetPickable(false);
        }
        this.b = listener;
    }

    public Object getTag() {
        return this.c;
    }

    public void setTag(Object tag) {
        this.c = tag;
    }

    public boolean isVisible() {
        e();
        return nativeIsVisible();
    }

    public void setVisible(boolean visible) {
        e();
        nativeSetVisible(visible);
    }

    public double getMinZoom() {
        e();
        return nativeGetMinZoom();
    }

    public void setMinZoom(double minZoom) {
        e();
        nativeSetMinZoom(minZoom);
    }

    public double getMaxZoom() {
        e();
        return nativeGetMaxZoom();
    }

    public void setMaxZoom(double maxZoom) {
        e();
        nativeSetMaxZoom(maxZoom);
    }

    public boolean isMinZoomInclusive() {
        e();
        return nativeIsMinZoomInclusive();
    }

    public void setMinZoomInclusive(boolean inclusive) {
        e();
        nativeSetMinZoomInclusive(inclusive);
    }

    public boolean isMaxZoomInclusive() {
        e();
        return nativeIsMaxZoomInclusive();
    }

    public void setMaxZoomInclusive(boolean inclusive) {
        e();
        nativeSetMaxZoomInclusive(inclusive);
    }

    public int getZIndex() {
        e();
        return nativeGetZIndex();
    }

    public void setZIndex(int zIndex) {
        e();
        nativeSetZIndex(zIndex);
    }

    public int getGlobalZIndex() {
        e();
        return nativeGetGlobalZIndex();
    }

    public void setGlobalZIndex(int globalZIndex) {
        e();
        nativeSetGlobalZIndex(globalZIndex);
    }
}
