package com.naver.maps.map.style.layers;

import android.os.Looper;
import com.google.gson.JsonElement;
import com.naver.maps.map.internal.a;
import com.naver.maps.map.internal.util.c;

/* loaded from: classes.dex */
public abstract class Layer {
    private long handle;

    private native String nativeGetId();

    private native float nativeGetMaxZoom();

    private native float nativeGetMinZoom();

    private native void nativeSetMaxZoom(float f);

    private native void nativeSetMinZoom(float f);

    protected native JsonElement nativeGetFilter();

    protected native String nativeGetSourceId();

    protected native String nativeGetSourceLayer();

    protected native Object nativeGetVisibility();

    protected native void nativeSetFilter(Object[] objArr);

    protected native void nativeSetSourceLayer(String str);

    protected native void nativeSetVisibility(Object obj);

    static {
        a.a();
    }

    protected Layer(long handle) {
        a();
        this.handle = handle;
    }

    public Layer() {
        a();
    }

    protected void a() {
        c.a(Looper.getMainLooper().getThread());
    }
}
