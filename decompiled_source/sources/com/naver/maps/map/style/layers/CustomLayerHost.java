package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public abstract class CustomLayerHost {
    private long handle;

    protected abstract void a();

    protected abstract void b();

    protected CustomLayerHost() {
        a();
    }

    protected void finalize() throws Throwable {
        try {
            b();
        } finally {
            super.finalize();
        }
    }
}
