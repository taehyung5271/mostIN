package com.naver.maps.map.style.sources;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class ImageSource extends Source {
    private native void nativeCreate(String str, LatLngQuad latLngQuad);

    private native void nativeDestroy() throws Throwable;

    protected native String nativeGetUrl();

    protected native void nativeSetCoordinates(LatLngQuad latLngQuad);

    protected native void nativeSetImage(Bitmap bitmap);

    protected native void nativeSetUrl(String str);

    ImageSource(long handle) {
        super(handle);
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
