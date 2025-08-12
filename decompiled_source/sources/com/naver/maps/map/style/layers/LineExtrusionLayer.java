package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class LineExtrusionLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetLineExtrusionBase();

    private native TransitionOptions nativeGetLineExtrusionBaseTransition();

    private native Object nativeGetLineExtrusionColor();

    private native TransitionOptions nativeGetLineExtrusionColorTransition();

    private native Object nativeGetLineExtrusionHeight();

    private native TransitionOptions nativeGetLineExtrusionHeightTransition();

    private native Object nativeGetLineExtrusionOpacity();

    private native TransitionOptions nativeGetLineExtrusionOpacityTransition();

    private native Object nativeGetLineExtrusionPattern();

    private native TransitionOptions nativeGetLineExtrusionPatternTransition();

    private native Object nativeGetLineExtrusionTranslate();

    private native Object nativeGetLineExtrusionTranslateAnchor();

    private native TransitionOptions nativeGetLineExtrusionTranslateTransition();

    private native Object nativeGetLineExtrusionWidth();

    private native TransitionOptions nativeGetLineExtrusionWidthTransition();

    private native void nativeSetLineExtrusionBase(Object obj);

    private native void nativeSetLineExtrusionBaseTransition(long j, long j2);

    private native void nativeSetLineExtrusionColor(Object obj);

    private native void nativeSetLineExtrusionColorTransition(long j, long j2);

    private native void nativeSetLineExtrusionHeight(Object obj);

    private native void nativeSetLineExtrusionHeightTransition(long j, long j2);

    private native void nativeSetLineExtrusionOpacity(Object obj);

    private native void nativeSetLineExtrusionOpacityTransition(long j, long j2);

    private native void nativeSetLineExtrusionPattern(Object obj);

    private native void nativeSetLineExtrusionPatternTransition(long j, long j2);

    private native void nativeSetLineExtrusionTranslate(Object obj);

    private native void nativeSetLineExtrusionTranslateAnchor(Object obj);

    private native void nativeSetLineExtrusionTranslateTransition(long j, long j2);

    private native void nativeSetLineExtrusionWidth(Object obj);

    private native void nativeSetLineExtrusionWidthTransition(long j, long j2);

    LineExtrusionLayer(long handle) {
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
