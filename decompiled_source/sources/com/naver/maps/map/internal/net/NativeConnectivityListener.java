package com.naver.maps.map.internal.net;

/* loaded from: classes.dex */
final class NativeConnectivityListener implements a {
    private long handle;

    private native void nativeCreate();

    private native void nativeDestroy();

    private native void nativeOnConnectivityStateChanged(boolean z);

    static {
        com.naver.maps.map.internal.a.a();
    }

    private NativeConnectivityListener(long handle) {
        this.handle = handle;
    }

    NativeConnectivityListener() {
        nativeCreate();
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    @Override // com.naver.maps.map.internal.net.a
    public void a(boolean z) {
        nativeOnConnectivityStateChanged(z);
    }
}
