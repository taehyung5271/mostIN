package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public class RasterDemSource extends TilesetSource {
    private native void nativeCreate(String str, Object obj, int i);

    private native void nativeDestroy() throws Throwable;

    RasterDemSource(long handle) {
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
