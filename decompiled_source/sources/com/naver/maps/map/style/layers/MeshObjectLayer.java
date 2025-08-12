package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class MeshObjectLayer extends Layer {
    private native void nativeCreate(String str, String str2);

    private native void nativeDestroy() throws Throwable;

    private native Object nativeGetContentUrl();

    private native Object nativeGetMeshObjectCenter();

    private native Object nativeGetMeshObjectColor();

    private native TransitionOptions nativeGetMeshObjectColorTransition();

    private native Object nativeGetMeshObjectHalfHeight();

    private native Object nativeGetMeshObjectHalfWidth();

    private native Object nativeGetMeshObjectHeightRatio();

    private native TransitionOptions nativeGetMeshObjectHeightRatioTransition();

    private native Object nativeGetMeshObjectOpacity();

    private native TransitionOptions nativeGetMeshObjectOpacityTransition();

    private native Object nativeGetMeshObjectRotate();

    private native Object nativeGetMeshObjectScale();

    private native Object nativeGetMeshObjectTranslate();

    private native Object nativeGetMeshObjectTranslateAnchor();

    private native TransitionOptions nativeGetMeshObjectTranslateTransition();

    private native void nativeSetContentUrl(Object obj);

    private native void nativeSetMeshObjectCenter(Object obj);

    private native void nativeSetMeshObjectColor(Object obj);

    private native void nativeSetMeshObjectColorTransition(long j, long j2);

    private native void nativeSetMeshObjectHalfHeight(Object obj);

    private native void nativeSetMeshObjectHalfWidth(Object obj);

    private native void nativeSetMeshObjectHeightRatio(Object obj);

    private native void nativeSetMeshObjectHeightRatioTransition(long j, long j2);

    private native void nativeSetMeshObjectOpacity(Object obj);

    private native void nativeSetMeshObjectOpacityTransition(long j, long j2);

    private native void nativeSetMeshObjectRotate(Object obj);

    private native void nativeSetMeshObjectScale(Object obj);

    private native void nativeSetMeshObjectTranslate(Object obj);

    private native void nativeSetMeshObjectTranslateAnchor(Object obj);

    private native void nativeSetMeshObjectTranslateTransition(long j, long j2);

    MeshObjectLayer(long handle) {
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
