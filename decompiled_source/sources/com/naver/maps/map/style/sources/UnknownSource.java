package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public class UnknownSource extends Source {
    private native void nativeCreate();

    private native void nativeDestroy() throws Throwable;

    UnknownSource(long handle) {
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
