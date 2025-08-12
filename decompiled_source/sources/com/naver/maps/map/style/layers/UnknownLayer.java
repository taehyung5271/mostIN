package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class UnknownLayer extends Layer {
    private native void nativeCreate();

    private native void nativeDestroy() throws Throwable;

    UnknownLayer(long handle) {
        super(handle);
        nativeCreate();
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
