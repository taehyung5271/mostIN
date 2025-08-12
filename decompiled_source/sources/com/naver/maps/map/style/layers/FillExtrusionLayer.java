package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class FillExtrusionLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetFillExtrusionBase();

    private native TransitionOptions nativeGetFillExtrusionBaseTransition();

    private native Object nativeGetFillExtrusionCapColor();

    private native TransitionOptions nativeGetFillExtrusionCapColorTransition();

    private native Object nativeGetFillExtrusionColor();

    private native TransitionOptions nativeGetFillExtrusionColorTransition();

    private native Object nativeGetFillExtrusionGradationBottomColor();

    private native TransitionOptions nativeGetFillExtrusionGradationBottomColorTransition();

    private native Object nativeGetFillExtrusionGradationTopColor();

    private native TransitionOptions nativeGetFillExtrusionGradationTopColorTransition();

    private native Object nativeGetFillExtrusionHeight();

    private native TransitionOptions nativeGetFillExtrusionHeightTransition();

    private native Object nativeGetFillExtrusionOpacity();

    private native TransitionOptions nativeGetFillExtrusionOpacityTransition();

    private native Object nativeGetFillExtrusionPattern();

    private native Object nativeGetFillExtrusionPatternOpacity();

    private native TransitionOptions nativeGetFillExtrusionPatternOpacityTransition();

    private native Object nativeGetFillExtrusionPatternStretch();

    private native TransitionOptions nativeGetFillExtrusionPatternTransition();

    private native Object nativeGetFillExtrusionTranslate();

    private native Object nativeGetFillExtrusionTranslateAnchor();

    private native TransitionOptions nativeGetFillExtrusionTranslateTransition();

    private native Object nativeGetFillExtrusionVerticalGradient();

    private native void nativeSetFillExtrusionBase(Object obj);

    private native void nativeSetFillExtrusionBaseTransition(long j, long j2);

    private native void nativeSetFillExtrusionCapColor(Object obj);

    private native void nativeSetFillExtrusionCapColorTransition(long j, long j2);

    private native void nativeSetFillExtrusionColor(Object obj);

    private native void nativeSetFillExtrusionColorTransition(long j, long j2);

    private native void nativeSetFillExtrusionGradationBottomColor(Object obj);

    private native void nativeSetFillExtrusionGradationBottomColorTransition(long j, long j2);

    private native void nativeSetFillExtrusionGradationTopColor(Object obj);

    private native void nativeSetFillExtrusionGradationTopColorTransition(long j, long j2);

    private native void nativeSetFillExtrusionHeight(Object obj);

    private native void nativeSetFillExtrusionHeightTransition(long j, long j2);

    private native void nativeSetFillExtrusionOpacity(Object obj);

    private native void nativeSetFillExtrusionOpacityTransition(long j, long j2);

    private native void nativeSetFillExtrusionPattern(Object obj);

    private native void nativeSetFillExtrusionPatternOpacity(Object obj);

    private native void nativeSetFillExtrusionPatternOpacityTransition(long j, long j2);

    private native void nativeSetFillExtrusionPatternStretch(Object obj);

    private native void nativeSetFillExtrusionPatternTransition(long j, long j2);

    private native void nativeSetFillExtrusionTranslate(Object obj);

    private native void nativeSetFillExtrusionTranslateAnchor(Object obj);

    private native void nativeSetFillExtrusionTranslateTransition(long j, long j2);

    private native void nativeSetFillExtrusionVerticalGradient(Object obj);

    FillExtrusionLayer(long handle) {
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
