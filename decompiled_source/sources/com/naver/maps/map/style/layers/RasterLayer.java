package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class RasterLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetRasterBrightnessMax();

    private native TransitionOptions nativeGetRasterBrightnessMaxTransition();

    private native Object nativeGetRasterBrightnessMin();

    private native TransitionOptions nativeGetRasterBrightnessMinTransition();

    private native Object nativeGetRasterContrast();

    private native TransitionOptions nativeGetRasterContrastTransition();

    private native Object nativeGetRasterFadeDuration();

    private native Object nativeGetRasterHueRotate();

    private native TransitionOptions nativeGetRasterHueRotateTransition();

    private native Object nativeGetRasterOpacity();

    private native TransitionOptions nativeGetRasterOpacityTransition();

    private native Object nativeGetRasterRemoveNoDataPixel();

    private native Object nativeGetRasterResampling();

    private native Object nativeGetRasterSaturation();

    private native TransitionOptions nativeGetRasterSaturationTransition();

    private native void nativeSetRasterBrightnessMax(Object obj);

    private native void nativeSetRasterBrightnessMaxTransition(long j, long j2);

    private native void nativeSetRasterBrightnessMin(Object obj);

    private native void nativeSetRasterBrightnessMinTransition(long j, long j2);

    private native void nativeSetRasterContrast(Object obj);

    private native void nativeSetRasterContrastTransition(long j, long j2);

    private native void nativeSetRasterFadeDuration(Object obj);

    private native void nativeSetRasterHueRotate(Object obj);

    private native void nativeSetRasterHueRotateTransition(long j, long j2);

    private native void nativeSetRasterOpacity(Object obj);

    private native void nativeSetRasterOpacityTransition(long j, long j2);

    private native void nativeSetRasterRemoveNoDataPixel(Object obj);

    private native void nativeSetRasterResampling(Object obj);

    private native void nativeSetRasterSaturation(Object obj);

    private native void nativeSetRasterSaturationTransition(long j, long j2);

    RasterLayer(long handle) {
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
