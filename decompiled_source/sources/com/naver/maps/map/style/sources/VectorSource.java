package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public class VectorSource extends TilesetSource {
    private native void nativeCreate(String str, Object obj);

    private native void nativeDestroy() throws Throwable;

    private native void nativeSetDynamicPropertyURLReplacement(String str, String str2);

    VectorSource(long handle) {
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
