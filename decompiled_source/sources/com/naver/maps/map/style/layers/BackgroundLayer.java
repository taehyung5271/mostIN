package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class BackgroundLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetBackgroundColor();

    private native TransitionOptions nativeGetBackgroundColorTransition();

    private native Object nativeGetBackgroundOpacity();

    private native TransitionOptions nativeGetBackgroundOpacityTransition();

    private native Object nativeGetBackgroundPattern();

    private native Object nativeGetBackgroundPatternScale();

    private native TransitionOptions nativeGetBackgroundPatternScaleTransition();

    private native TransitionOptions nativeGetBackgroundPatternTransition();

    private native void nativeSetBackgroundColor(Object obj);

    private native void nativeSetBackgroundColorTransition(long j, long j2);

    private native void nativeSetBackgroundOpacity(Object obj);

    private native void nativeSetBackgroundOpacityTransition(long j, long j2);

    private native void nativeSetBackgroundPattern(Object obj);

    private native void nativeSetBackgroundPatternScale(Object obj);

    private native void nativeSetBackgroundPatternScaleTransition(long j, long j2);

    private native void nativeSetBackgroundPatternTransition(long j, long j2);

    BackgroundLayer(long handle) {
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
