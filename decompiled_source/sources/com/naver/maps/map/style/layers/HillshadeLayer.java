package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class HillshadeLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetHillshadeAccentColor();

    private native TransitionOptions nativeGetHillshadeAccentColorTransition();

    private native Object nativeGetHillshadeExaggeration();

    private native TransitionOptions nativeGetHillshadeExaggerationTransition();

    private native Object nativeGetHillshadeHighlightColor();

    private native TransitionOptions nativeGetHillshadeHighlightColorTransition();

    private native Object nativeGetHillshadeIlluminationAnchor();

    private native Object nativeGetHillshadeIlluminationDirection();

    private native Object nativeGetHillshadeShadowColor();

    private native TransitionOptions nativeGetHillshadeShadowColorTransition();

    private native void nativeSetHillshadeAccentColor(Object obj);

    private native void nativeSetHillshadeAccentColorTransition(long j, long j2);

    private native void nativeSetHillshadeExaggeration(Object obj);

    private native void nativeSetHillshadeExaggerationTransition(long j, long j2);

    private native void nativeSetHillshadeHighlightColor(Object obj);

    private native void nativeSetHillshadeHighlightColorTransition(long j, long j2);

    private native void nativeSetHillshadeIlluminationAnchor(Object obj);

    private native void nativeSetHillshadeIlluminationDirection(Object obj);

    private native void nativeSetHillshadeShadowColor(Object obj);

    private native void nativeSetHillshadeShadowColorTransition(long j, long j2);

    HillshadeLayer(long handle) {
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
