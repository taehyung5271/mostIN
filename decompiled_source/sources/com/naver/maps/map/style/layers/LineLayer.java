package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class LineLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetLineBlur();

    private native TransitionOptions nativeGetLineBlurTransition();

    private native Object nativeGetLineCap();

    private native Object nativeGetLineColor();

    private native TransitionOptions nativeGetLineColorTransition();

    private native Object nativeGetLineDasharray();

    private native TransitionOptions nativeGetLineDasharrayTransition();

    private native Object nativeGetLineGapWidth();

    private native TransitionOptions nativeGetLineGapWidthTransition();

    private native Object nativeGetLineGradient();

    private native Object nativeGetLineJoin();

    private native Object nativeGetLineMiterLimit();

    private native Object nativeGetLineOffset();

    private native TransitionOptions nativeGetLineOffsetTransition();

    private native Object nativeGetLineOpacity();

    private native TransitionOptions nativeGetLineOpacityTransition();

    private native Object nativeGetLinePattern();

    private native TransitionOptions nativeGetLinePatternTransition();

    private native Object nativeGetLineRoundLimit();

    private native Object nativeGetLineSortKey();

    private native Object nativeGetLineTranslate();

    private native Object nativeGetLineTranslateAnchor();

    private native TransitionOptions nativeGetLineTranslateTransition();

    private native Object nativeGetLineWidth();

    private native TransitionOptions nativeGetLineWidthTransition();

    private native void nativeSetLineBlur(Object obj);

    private native void nativeSetLineBlurTransition(long j, long j2);

    private native void nativeSetLineCap(Object obj);

    private native void nativeSetLineColor(Object obj);

    private native void nativeSetLineColorTransition(long j, long j2);

    private native void nativeSetLineDasharray(Object obj);

    private native void nativeSetLineDasharrayTransition(long j, long j2);

    private native void nativeSetLineGapWidth(Object obj);

    private native void nativeSetLineGapWidthTransition(long j, long j2);

    private native void nativeSetLineGradient(Object obj);

    private native void nativeSetLineJoin(Object obj);

    private native void nativeSetLineMiterLimit(Object obj);

    private native void nativeSetLineOffset(Object obj);

    private native void nativeSetLineOffsetTransition(long j, long j2);

    private native void nativeSetLineOpacity(Object obj);

    private native void nativeSetLineOpacityTransition(long j, long j2);

    private native void nativeSetLinePattern(Object obj);

    private native void nativeSetLinePatternTransition(long j, long j2);

    private native void nativeSetLineRoundLimit(Object obj);

    private native void nativeSetLineSortKey(Object obj);

    private native void nativeSetLineTranslate(Object obj);

    private native void nativeSetLineTranslateAnchor(Object obj);

    private native void nativeSetLineTranslateTransition(long j, long j2);

    private native void nativeSetLineWidth(Object obj);

    private native void nativeSetLineWidthTransition(long j, long j2);

    LineLayer(long handle) {
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
