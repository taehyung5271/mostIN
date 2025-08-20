package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class CustomLayer extends Layer {
    private final CustomLayerHost a;

    private native void nativeCreate(String str, long j);

    private native void nativeDestroy() throws Throwable;

    private native void nativeUpdate();

    CustomLayer(long handle) {
        super(handle);
        this.a = null;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
