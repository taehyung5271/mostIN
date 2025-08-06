package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public class RasterSource extends TilesetSource {
    private native void nativeCreate(String str, Object obj, int i);

    private native void nativeDestroy() throws Throwable;

    RasterSource(long handle) {
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
