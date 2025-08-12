package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class HeatmapLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetHeatmapColor();

    private native Object nativeGetHeatmapIntensity();

    private native TransitionOptions nativeGetHeatmapIntensityTransition();

    private native Object nativeGetHeatmapOpacity();

    private native TransitionOptions nativeGetHeatmapOpacityTransition();

    private native Object nativeGetHeatmapRadius();

    private native TransitionOptions nativeGetHeatmapRadiusTransition();

    private native Object nativeGetHeatmapWeight();

    private native void nativeSetHeatmapColor(Object obj);

    private native void nativeSetHeatmapIntensity(Object obj);

    private native void nativeSetHeatmapIntensityTransition(long j, long j2);

    private native void nativeSetHeatmapOpacity(Object obj);

    private native void nativeSetHeatmapOpacityTransition(long j, long j2);

    private native void nativeSetHeatmapRadius(Object obj);

    private native void nativeSetHeatmapRadiusTransition(long j, long j2);

    private native void nativeSetHeatmapWeight(Object obj);

    HeatmapLayer(long handle) {
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
