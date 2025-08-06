package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class FillLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetFillAntialias();

    private native Object nativeGetFillColor();

    private native TransitionOptions nativeGetFillColorTransition();

    private native Object nativeGetFillOpacity();

    private native TransitionOptions nativeGetFillOpacityTransition();

    private native Object nativeGetFillOutlineColor();

    private native TransitionOptions nativeGetFillOutlineColorTransition();

    private native Object nativeGetFillPattern();

    private native TransitionOptions nativeGetFillPatternTransition();

    private native Object nativeGetFillSortKey();

    private native Object nativeGetFillTranslate();

    private native Object nativeGetFillTranslateAnchor();

    private native TransitionOptions nativeGetFillTranslateTransition();

    private native void nativeSetFillAntialias(Object obj);

    private native void nativeSetFillColor(Object obj);

    private native void nativeSetFillColorTransition(long j, long j2);

    private native void nativeSetFillOpacity(Object obj);

    private native void nativeSetFillOpacityTransition(long j, long j2);

    private native void nativeSetFillOutlineColor(Object obj);

    private native void nativeSetFillOutlineColorTransition(long j, long j2);

    private native void nativeSetFillPattern(Object obj);

    private native void nativeSetFillPatternTransition(long j, long j2);

    private native void nativeSetFillSortKey(Object obj);

    private native void nativeSetFillTranslate(Object obj);

    private native void nativeSetFillTranslateAnchor(Object obj);

    private native void nativeSetFillTranslateTransition(long j, long j2);

    FillLayer(long handle) {
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
