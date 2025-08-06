package com.naver.maps.map.renderer;

import com.naver.maps.map.internal.a;

/* loaded from: classes.dex */
final class MapRendererRunnable implements Runnable {
    private final long handle;

    private native void doRun();

    private native void nativeCreate();

    private native void nativeDestroy();

    static {
        a.a();
    }

    MapRendererRunnable(long handle) {
        this.handle = handle;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        doRun();
    }
}
