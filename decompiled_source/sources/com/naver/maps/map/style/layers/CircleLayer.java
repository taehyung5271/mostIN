package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class CircleLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetCircleBlur();

    private native TransitionOptions nativeGetCircleBlurTransition();

    private native Object nativeGetCircleColor();

    private native TransitionOptions nativeGetCircleColorTransition();

    private native Object nativeGetCircleOpacity();

    private native TransitionOptions nativeGetCircleOpacityTransition();

    private native Object nativeGetCirclePitchAlignment();

    private native Object nativeGetCirclePitchScale();

    private native Object nativeGetCircleRadius();

    private native TransitionOptions nativeGetCircleRadiusTransition();

    private native Object nativeGetCircleStrokeColor();

    private native TransitionOptions nativeGetCircleStrokeColorTransition();

    private native Object nativeGetCircleStrokeOpacity();

    private native TransitionOptions nativeGetCircleStrokeOpacityTransition();

    private native Object nativeGetCircleStrokeWidth();

    private native TransitionOptions nativeGetCircleStrokeWidthTransition();

    private native Object nativeGetCircleTranslate();

    private native Object nativeGetCircleTranslateAnchor();

    private native TransitionOptions nativeGetCircleTranslateTransition();

    private native void nativeSetCircleBlur(Object obj);

    private native void nativeSetCircleBlurTransition(long j, long j2);

    private native void nativeSetCircleColor(Object obj);

    private native void nativeSetCircleColorTransition(long j, long j2);

    private native void nativeSetCircleOpacity(Object obj);

    private native void nativeSetCircleOpacityTransition(long j, long j2);

    private native void nativeSetCirclePitchAlignment(Object obj);

    private native void nativeSetCirclePitchScale(Object obj);

    private native void nativeSetCircleRadius(Object obj);

    private native void nativeSetCircleRadiusTransition(long j, long j2);

    private native void nativeSetCircleStrokeColor(Object obj);

    private native void nativeSetCircleStrokeColorTransition(long j, long j2);

    private native void nativeSetCircleStrokeOpacity(Object obj);

    private native void nativeSetCircleStrokeOpacityTransition(long j, long j2);

    private native void nativeSetCircleStrokeWidth(Object obj);

    private native void nativeSetCircleStrokeWidthTransition(long j, long j2);

    private native void nativeSetCircleTranslate(Object obj);

    private native void nativeSetCircleTranslateAnchor(Object obj);

    private native void nativeSetCircleTranslateTransition(long j, long j2);

    CircleLayer(long handle) {
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
