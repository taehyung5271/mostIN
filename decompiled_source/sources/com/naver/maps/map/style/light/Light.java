package com.naver.maps.map.style.light;

import android.os.Looper;
import com.naver.maps.map.internal.util.c;
import com.naver.maps.map.style.layers.TransitionOptions;

/* loaded from: classes.dex */
public class Light {
    private final long handle;

    private native String nativeGetAnchor();

    private native String nativeGetColor();

    private native TransitionOptions nativeGetColorTransition();

    private native float nativeGetIntensity();

    private native TransitionOptions nativeGetIntensityTransition();

    private native Position nativeGetPosition();

    private native TransitionOptions nativeGetPositionTransition();

    private native void nativeSetAnchor(String str);

    private native void nativeSetColor(String str);

    private native void nativeSetColorTransition(long j, long j2);

    private native void nativeSetIntensity(float f);

    private native void nativeSetIntensityTransition(long j, long j2);

    private native void nativeSetPosition(Position position);

    private native void nativeSetPositionTransition(long j, long j2);

    Light(long handle) {
        c.a(Looper.getMainLooper().getThread());
        this.handle = handle;
    }
}
