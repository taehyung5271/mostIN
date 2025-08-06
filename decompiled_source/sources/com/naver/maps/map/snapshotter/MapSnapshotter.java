package com.naver.maps.map.snapshotter;

import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.internal.resource.LocalGlyphRasterizer;
import com.naver.maps.map.internal.resource.OverlayImageLoader;

/* loaded from: classes.dex */
public class MapSnapshotter {
    private b a;
    private a b;
    private long handle;

    public interface a {
        void a(String str);
    }

    public interface b {
        void a(MapSnapshot mapSnapshot);
    }

    private native void nativeCancel();

    private native void nativeCreate(MapSnapshotter mapSnapshotter, FileSource fileSource, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer, int i, int i2, float f, String str, String str2, String str3);

    private native void nativeDestroy();

    private native void nativeResetLayerVisibilities();

    private native void nativeResetVisibleLayerCategories();

    private native void nativeResetVisibleLayerGroups();

    private native void nativeResetVisibleLayers();

    private native void nativeSetBackgroundColor(int i);

    private native void nativeSetBuildingHeight(float f);

    private native void nativeSetCameraPosition(CameraPosition cameraPosition);

    private native void nativeSetDebugFlag(int i);

    private native void nativeSetDynamicPropertyVersion(String str, String str2);

    private native void nativeSetLanguageTag(String str);

    private native void nativeSetLayerCategoryVisible(String str, boolean z);

    private native void nativeSetLayerGroupVisible(String str, boolean z);

    private native void nativeSetLayerVisible(String str, boolean z);

    private native void nativeSetLightness(float f);

    private native void nativeSetMapType(String str);

    private native void nativeSetNightModeEnabled(boolean z);

    private native void nativeSetSize(int i, int i2);

    private native void nativeSetSourceUrlReplacement(String str, String str2, String str3);

    private native void nativeSetStyleJson(String str);

    private native void nativeSetStyleUrl(String str);

    private native void nativeSetSymbolPerspectiveRatio(float f);

    private native void nativeSetSymbolScale(float f);

    private native void nativeStart();

    static {
        com.naver.maps.map.internal.a.a();
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    private void a() {
        this.a = null;
        this.b = null;
    }

    private void onSnapshotReady(MapSnapshot snapshot) {
        if (this.a != null) {
            this.a.a(snapshot);
            a();
        }
    }

    private void onSnapshotFailed(String reason) {
        if (this.b != null) {
            this.b.a(reason);
            a();
        }
    }
}
