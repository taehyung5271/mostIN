package com.naver.maps.map.snapshotter;

import android.graphics.Bitmap;
import android.graphics.PointF;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.internal.a;

/* loaded from: classes.dex */
public class MapSnapshot {
    private Bitmap a;
    private long handle;

    public native LatLng latLngForPixel(PointF pointF);

    protected native void nativeCreate();

    protected native void nativeDestroy();

    public native PointF pixelForLatLng(LatLng latLng);

    static {
        a.a();
    }

    private MapSnapshot(long handle, Bitmap bitmap) {
        this.handle = handle;
        this.a = bitmap;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
