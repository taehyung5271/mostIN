package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public class GeoJsonSource extends Source {
    private native void nativeCreate(String str, Object obj);

    private native void nativeDestroy() throws Throwable;

    private native void nativeSetGeoJsonString(String str);

    protected native String nativeGetUrl();

    protected native void nativeSetUrl(String str);

    GeoJsonSource(long handle) {
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
