package com.naver.maps.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.collection.LongSparseArray;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.indoor.IndoorRegion;
import com.naver.maps.map.indoor.IndoorView;
import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.internal.NativeCallback;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.renderer.MapRenderer;
import com.naver.maps.map.style.layers.CannotAddLayerException;
import com.naver.maps.map.style.layers.Layer;
import com.naver.maps.map.style.light.Light;
import com.naver.maps.map.style.sources.CannotAddSourceException;
import com.naver.maps.map.style.sources.Source;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
final class NativeMapView {
    private final Context a;
    private final e b;
    private final MapRenderer c;
    private final float d;
    private final float e;
    private final LongSparseArray<Overlay> f;
    private final Thread g;
    private Locale h;
    private long handle;
    private int i;
    private int j;
    private boolean k;

    private native void nativeAddImage(String str, Bitmap bitmap, float f, boolean z);

    private native void nativeAddLayer(long j, String str) throws CannotAddLayerException;

    private native void nativeAddLayerAbove(long j, String str) throws CannotAddLayerException;

    private native void nativeAddLayerAt(long j, int i) throws CannotAddLayerException;

    private native void nativeAddOverlay(long j);

    private native void nativeAddSource(Source source, long j) throws CannotAddSourceException;

    private native void nativeCancelTransitions(int i);

    private native void nativeCreate(NativeMapView nativeMapView, FileSource fileSource, MapRenderer mapRenderer, float f, float f2, String str);

    private native void nativeCycleDebugOptions();

    private native void nativeDestroy();

    private native void nativeForceRefresh();

    private native LatLng nativeFromProjectedPoint(float f, float f2, double d);

    private native LatLng nativeFromScreenLocation(float f, float f2);

    private native LatLng nativeFromScreenLocationAt(float f, float f2, double d, double d2, double d3, boolean z);

    private native float nativeGetBuildingHeight();

    private native CameraPosition nativeGetCameraPosition();

    private native LatLngBounds nativeGetContentBounds();

    private native LatLng[] nativeGetContentRegion();

    private native LatLngBounds nativeGetCoveringBounds();

    private native LatLng[] nativeGetCoveringRegion();

    private native long[] nativeGetCoveringTileIds();

    private native long[] nativeGetCoveringTileIdsAtZoom(int i);

    private native boolean nativeGetDebug();

    private native String[] nativeGetEnabledLayerGroups();

    private native LatLngBounds nativeGetExtent();

    private native Bitmap nativeGetImage(String str);

    private native double nativeGetIndoorFocusRadius();

    private native IndoorView nativeGetIndoorView();

    private native Layer nativeGetLayer(String str);

    private native Layer[] nativeGetLayers();

    private native Light nativeGetLight();

    private native float nativeGetLightness();

    private native String nativeGetMapType();

    private native double nativeGetMaxTilt();

    private native double nativeGetMaxZoom();

    private native double nativeGetMinZoom();

    private native Source nativeGetSource(String str);

    private native Source[] nativeGetSources();

    private native String nativeGetStyleUrl();

    private native float nativeGetSymbolPerspectiveRatio();

    private native float nativeGetSymbolScale();

    private native Symbol[] nativeGetSymbols(String str, double d, double d2, double d3, double d4);

    private native long nativeGetTransitionDelay();

    private native long nativeGetTransitionDuration();

    private native boolean nativeIsNightModeEnabled();

    private native boolean nativeIsObject3dEnabled();

    private native boolean nativeLoadSource(Source source, long j);

    private native void nativeMoveCamera(double d, double d2, double d3, double d4, double d5, double d6, double d7, int i, int i2, long j, boolean z);

    private native void nativeOnLowMemory();

    private native Object nativePick(float f, float f2, float f3);

    private native Object[] nativePickAll(float f, float f2, float f3);

    private native void nativeQueryAddress(float f, float f2, NativeCallback nativeCallback);

    private native void nativeQueryRenderedFeaturesForBox(float f, float f2, float f3, float f4, int i, String[] strArr, Object[] objArr, NativeCallback nativeCallback);

    private native void nativeQueryRenderedFeaturesForPoint(float f, float f2, int i, String[] strArr, Object[] objArr, NativeCallback nativeCallback);

    private native void nativeReinit();

    private native void nativeRemoveImage(String str);

    private native boolean nativeRemoveLayer(long j);

    private native boolean nativeRemoveLayerAt(int i);

    private native void nativeRemoveOverlay(long j);

    private native boolean nativeRemoveSource(Source source, long j);

    private native void nativeResizeView(float f, float f2);

    private native void nativeSetBackgroundColor(int i);

    private native void nativeSetBackgroundImage(Bitmap bitmap, float f);

    private native void nativeSetBuildingHeight(float f);

    private native void nativeSetContentPadding(double d, double d2, double d3, double d4, boolean z);

    private native void nativeSetDebug(boolean z);

    private native void nativeSetExtent(LatLngBounds latLngBounds);

    private native void nativeSetIndoorFocusCallbackEnabled(boolean z);

    private native void nativeSetIndoorFocusRadius(double d);

    private native void nativeSetIndoorView(IndoorView indoorView);

    private native void nativeSetLanguageTag(String str);

    private native void nativeSetLayerGroupEnabled(String str, boolean z);

    private native void nativeSetLightness(float f);

    private native void nativeSetMapType(String str);

    private native void nativeSetMaxTilt(double d);

    private native void nativeSetMaxZoom(double d);

    private native void nativeSetMinZoom(double d);

    private native void nativeSetNightModeEnabled(boolean z);

    private native void nativeSetObject3dEnabled(boolean z);

    private native void nativeSetReachability(boolean z);

    private native void nativeSetStyleJson(String str);

    private native void nativeSetStyleUrl(String str);

    private native void nativeSetSymbolHidden(String str, String str2, boolean z);

    private native void nativeSetSymbolPerspectiveRatio(float f);

    private native void nativeSetSymbolScale(float f);

    private native void nativeSetTransitionDelay(long j);

    private native void nativeSetTransitionDuration(long j);

    private native void nativeStart();

    private native void nativeStop();

    private native void nativeTakeSnapshot(boolean z);

    private native PointF nativeToProjectedPoint(double d, double d2, double d3);

    private native PointF nativeToScreenLocation(double d, double d2);

    private native PointF nativeToScreenLocationAt(double d, double d2, double d3, double d4, double d5, boolean z);

    static {
        com.naver.maps.map.internal.a.a();
    }

    NativeMapView(Context context, e mapViewDelegate, MapRenderer mapRenderer, Locale locale, float mapScale) {
        this.a = context;
        this.b = mapViewDelegate;
        this.c = mapRenderer;
        this.d = mapScale <= 0.0f ? 1.0f : mapScale;
        float f = context.getResources().getDisplayMetrics().density;
        this.e = this.d * f;
        this.f = new LongSparseArray<>();
        this.g = Thread.currentThread();
        this.h = locale;
        nativeCreate(this, FileSource.a(context), mapRenderer, f, this.d, com.naver.maps.map.internal.util.d.a(context, locale));
    }

    private boolean e(String str) {
        if (this.k) {
            com.naver.maps.map.log.c.c("Cannot execute %s(): NaverMap was already destroyed.", str);
        }
        return this.k;
    }

    boolean a() {
        return this.k;
    }

    float b() {
        return this.e;
    }

    Thread c() {
        return this.g;
    }

    void d() {
        nativeReinit();
    }

    void e() {
        if (this.k) {
            return;
        }
        LongSparseArray<Overlay> longSparseArrayM32clone = this.f.m32clone();
        for (int i = 0; i < longSparseArrayM32clone.size(); i++) {
            longSparseArrayM32clone.valueAt(i).setMap(null);
        }
        this.f.clear();
        nativeDestroy();
        this.k = true;
    }

    void f() {
        if (e("start")) {
            return;
        }
        nativeStart();
        this.c.requestRender();
    }

    void g() {
        if (e("stop")) {
            return;
        }
        nativeStop();
    }

    void h() {
        nativeForceRefresh();
    }

    void a(int i, int i2) {
        if (e("resizeView")) {
            return;
        }
        if (i < 0) {
            throw new IllegalArgumentException("width cannot be negative.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("height cannot be negative.");
        }
        this.i = i;
        this.j = i2;
        float f = i / this.e;
        float f2 = i2 / this.e;
        if (f > 65535.0f) {
            f = 65535.0f;
        }
        if (f2 > 65535.0f) {
            f2 = 65535.0f;
        }
        nativeResizeView(f, f2);
    }

    void a(String str) {
        if (e("setStyleUrl")) {
            return;
        }
        nativeSetStyleUrl(str);
    }

    void b(String str) {
        if (e("setStyleJson")) {
            return;
        }
        nativeSetStyleJson(str);
    }

    void a(int i) {
        if (e("cancelTransitions")) {
            return;
        }
        nativeCancelTransitions(i);
    }

    LatLngBounds i() {
        if (e("getExtent")) {
            return null;
        }
        return nativeGetExtent();
    }

    void a(LatLngBounds latLngBounds) {
        if (e("setExtent")) {
            return;
        }
        nativeSetExtent(latLngBounds);
    }

    double j() {
        if (e("getMinZoom")) {
            return 0.0d;
        }
        return nativeGetMinZoom();
    }

    void a(double d) {
        if (e("setMinZoom")) {
            return;
        }
        nativeSetMinZoom(d);
    }

    double k() {
        if (e("getMaxZoom")) {
            return 0.0d;
        }
        return nativeGetMaxZoom();
    }

    void b(double d) {
        if (e("setMaxZoom")) {
            return;
        }
        nativeSetMaxZoom(d);
    }

    double l() {
        if (e("getMaxTilt")) {
            return 0.0d;
        }
        return nativeGetMaxTilt();
    }

    void c(double d) {
        if (e("setMaxTilt")) {
            return;
        }
        nativeSetMaxTilt(d);
    }

    void a(int[] iArr, boolean z) {
        if (e("setContentPadding")) {
            return;
        }
        nativeSetContentPadding(iArr[1] / this.e, iArr[0] / this.e, iArr[3] / this.e, iArr[2] / this.e, z);
    }

    void m() {
        if (e("onLowMemory")) {
            return;
        }
        nativeOnLowMemory();
    }

    void a(boolean z) {
        if (e("setReachability")) {
            return;
        }
        nativeSetReachability(z);
    }

    LatLng a(PointF pointF) {
        if (e("fromScreenLocation")) {
            return LatLng.INVALID;
        }
        return nativeFromScreenLocation(pointF.x / this.e, pointF.y / this.e);
    }

    LatLng a(PointF pointF, double d, double d2, double d3, boolean z) {
        if (!e("fromScreenLocationAt")) {
            return nativeFromScreenLocationAt(pointF.x / this.e, pointF.y / this.e, d, d2, d3, z);
        }
        return LatLng.INVALID;
    }

    PointF a(LatLng latLng) {
        if (e("toScreenLocation")) {
            return new PointF();
        }
        PointF pointFNativeToScreenLocation = nativeToScreenLocation(latLng.latitude, latLng.longitude);
        pointFNativeToScreenLocation.set(pointFNativeToScreenLocation.x * this.e, pointFNativeToScreenLocation.y * this.e);
        return pointFNativeToScreenLocation;
    }

    PointF a(LatLng latLng, double d, double d2, double d3, boolean z) {
        if (e("toScreenLocationAt")) {
            return new PointF();
        }
        PointF pointFNativeToScreenLocationAt = nativeToScreenLocationAt(latLng.latitude, latLng.longitude, d, d2, d3, z);
        pointFNativeToScreenLocationAt.set(pointFNativeToScreenLocationAt.x * this.e, pointFNativeToScreenLocationAt.y * this.e);
        return pointFNativeToScreenLocationAt;
    }

    LatLng a(PointF pointF, double d) {
        if (e("fromProjectedPoint")) {
            return LatLng.INVALID;
        }
        return nativeFromProjectedPoint(pointF.x / this.e, pointF.y / this.e, d);
    }

    PointF a(LatLng latLng, double d) {
        if (e("toProjectedPoint")) {
            return new PointF();
        }
        PointF pointFNativeToProjectedPoint = nativeToProjectedPoint(latLng.latitude, latLng.longitude, d);
        pointFNativeToProjectedPoint.set(pointFNativeToProjectedPoint.x * this.e, pointFNativeToProjectedPoint.y * this.e);
        return pointFNativeToProjectedPoint;
    }

    void a(LatLng latLng, double d, double d2, double d3, PointF pointF, int i, CameraAnimation cameraAnimation, long j, boolean z) {
        double d4;
        double d5;
        if (e("moveCamera")) {
            return;
        }
        double d6 = latLng.latitude;
        double d7 = latLng.longitude;
        if (pointF != null) {
            d4 = pointF.x / this.e;
        } else {
            d4 = Double.NaN;
        }
        if (pointF != null) {
            d5 = pointF.y / this.e;
        } else {
            d5 = Double.NaN;
        }
        nativeMoveCamera(d6, d7, d, d2, d3, d4, d5, i, cameraAnimation.ordinal(), j, z);
    }

    CameraPosition n() {
        if (e("getCameraValues")) {
            return CameraPosition.INVALID;
        }
        return nativeGetCameraPosition();
    }

    LatLngBounds o() {
        if (e("getContentBounds")) {
            return LatLngBounds.INVALID;
        }
        return nativeGetContentBounds();
    }

    LatLng[] p() {
        if (e("getContentRegion")) {
            return new LatLng[]{LatLng.INVALID, LatLng.INVALID, LatLng.INVALID, LatLng.INVALID, LatLng.INVALID};
        }
        return nativeGetContentRegion();
    }

    LatLngBounds q() {
        if (e("getCoveringBounds")) {
            return LatLngBounds.INVALID;
        }
        return nativeGetCoveringBounds();
    }

    LatLng[] r() {
        if (e("getCoveringRegion")) {
            return new LatLng[]{LatLng.INVALID, LatLng.INVALID, LatLng.INVALID, LatLng.INVALID, LatLng.INVALID};
        }
        return nativeGetCoveringRegion();
    }

    long[] s() {
        if (e("getCoveringTileIds")) {
            return new long[0];
        }
        return nativeGetCoveringTileIds();
    }

    long[] b(int i) {
        if (e("getCoveringTileIdsAtZoom")) {
            return new long[0];
        }
        return nativeGetCoveringTileIdsAtZoom(i);
    }

    void a(Overlay overlay, long j) {
        if (e("addOverlay")) {
            return;
        }
        this.f.put(j, overlay);
        nativeAddOverlay(j);
    }

    void b(Overlay overlay, long j) {
        if (e("removeOverlay")) {
            return;
        }
        this.f.remove(j);
        nativeRemoveOverlay(j);
    }

    Pickable a(PointF pointF, int i) {
        if (e("pick")) {
            return null;
        }
        return a(nativePick(pointF.x / this.e, pointF.y / this.e, i / this.e));
    }

    List<Pickable> b(PointF pointF, int i) {
        if (e("pickAll")) {
            return Collections.emptyList();
        }
        Object[] objArrNativePickAll = nativePickAll(pointF.x / this.e, pointF.y / this.e, i / this.e);
        ArrayList arrayList = new ArrayList(objArrNativePickAll.length);
        for (Object obj : objArrNativePickAll) {
            Pickable pickableA = a(obj);
            if (pickableA != null) {
                arrayList.add(pickableA);
            }
        }
        return arrayList;
    }

    private Pickable a(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Pickable) {
            return (Pickable) obj;
        }
        if (!(obj instanceof Long)) {
            return null;
        }
        return this.f.get(((Long) obj).longValue());
    }

    Locale t() {
        return this.h;
    }

    void a(Locale locale) {
        if (e("setLocale")) {
            return;
        }
        this.h = locale;
        nativeSetLanguageTag(com.naver.maps.map.internal.util.d.a(this.a, locale));
    }

    void c(int i) {
        if (e("setBackgroundColor")) {
            return;
        }
        nativeSetBackgroundColor(i);
    }

    void d(int i) {
        if (e("setBackgroundResource")) {
            return;
        }
        a(com.naver.maps.map.internal.util.a.a(this.a, i));
    }

    void a(Drawable drawable) {
        if (e("setBackground")) {
            return;
        }
        if (drawable == null) {
            a((Bitmap) null);
        } else if (drawable instanceof ColorDrawable) {
            c(((ColorDrawable) drawable).getColor());
        } else {
            a(com.naver.maps.map.internal.util.a.a(this.a, drawable));
        }
    }

    void a(Bitmap bitmap) {
        if (e("setMapBackgroundBitmap")) {
            return;
        }
        float f = 1.0f;
        if (bitmap != null) {
            if (bitmap.getConfig() != Bitmap.Config.ARGB_8888 && (bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)) == null) {
                com.naver.maps.map.log.c.d("Failed to copy a bitmap.", new Object[0]);
                return;
            } else {
                float density = bitmap.getDensity();
                if (density != 0.0f) {
                    f = density / 160.0f;
                }
            }
        }
        nativeSetBackgroundImage(bitmap, f);
    }

    String u() {
        if (e("getMapType")) {
            return "basic";
        }
        return nativeGetMapType();
    }

    void c(String str) {
        if (e("setMapType")) {
            return;
        }
        nativeSetMapType(str);
    }

    void a(String str, boolean z) {
        if (e("setLayerGroupEnabled")) {
            return;
        }
        nativeSetLayerGroupEnabled(str, z);
    }

    boolean v() {
        if (e("isNightModeEnabled")) {
            return false;
        }
        return nativeIsNightModeEnabled();
    }

    void b(boolean z) {
        if (e("setNightModeEnabled")) {
            return;
        }
        nativeSetNightModeEnabled(z);
    }

    float w() {
        if (e("getBuildingHeight")) {
            return 1.0f;
        }
        return nativeGetBuildingHeight();
    }

    void a(float f) {
        if (e("setBuildingHeight")) {
            return;
        }
        nativeSetBuildingHeight(f);
    }

    float x() {
        if (e("getLightness")) {
            return 0.0f;
        }
        return nativeGetLightness();
    }

    void b(float f) {
        if (e("setLightness")) {
            return;
        }
        nativeSetLightness(f);
    }

    void a(String str, String str2, boolean z) {
        if (e("setSymbolHidden")) {
            return;
        }
        nativeSetSymbolHidden(str, str2, z);
    }

    void c(float f) {
        if (e("setSymbolScale")) {
            return;
        }
        nativeSetSymbolScale(f);
    }

    float y() {
        if (e("getSymbolScale")) {
            return 0.0f;
        }
        return nativeGetSymbolScale();
    }

    void d(float f) {
        if (e("setSymbolPerspectiveRatio")) {
            return;
        }
        nativeSetSymbolPerspectiveRatio(f);
    }

    float z() {
        if (e("getSymbolPerspectiveRatio")) {
            return 0.0f;
        }
        return nativeGetSymbolPerspectiveRatio();
    }

    boolean A() {
        if (e("isObject3dEnabled")) {
            return false;
        }
        return nativeIsObject3dEnabled();
    }

    void c(boolean z) {
        if (e("setObject3dEnabled")) {
            return;
        }
        nativeSetObject3dEnabled(z);
    }

    void d(boolean z) {
        if (e("setIndoorFocusCallbackEnabled")) {
            return;
        }
        nativeSetIndoorFocusCallbackEnabled(z);
    }

    int B() {
        if (e("getIndoorFocusRadius")) {
            return 0;
        }
        return (int) (nativeGetIndoorFocusRadius() * this.e);
    }

    void e(int i) {
        if (e("getIndoorFocusRadius")) {
            return;
        }
        nativeSetIndoorFocusRadius(i / this.e);
    }

    void a(IndoorView indoorView) {
        if (e("setIndoorView")) {
            return;
        }
        nativeSetIndoorView(indoorView);
    }

    Source d(String str) {
        if (e("getSource")) {
            return null;
        }
        return nativeGetSource(str);
    }

    int C() {
        if (e("getWidth()")) {
            return 0;
        }
        return this.i;
    }

    int D() {
        if (e("getHeight()")) {
            return 0;
        }
        return this.j;
    }

    void f(int i) {
        this.b.a(i);
    }

    int E() {
        return this.b.l();
    }

    void e(boolean z) {
        if (e("takeSnapshot")) {
            return;
        }
        nativeTakeSnapshot(z);
    }

    private void onMapLoad() {
        if (this.k) {
            return;
        }
        this.b.j();
    }

    private void onMapRendered(boolean fully, boolean stable) {
        if (this.k) {
            return;
        }
        this.b.a(fully, stable);
    }

    private void onStyleLoad() {
        if (this.k) {
            return;
        }
        this.b.k();
    }

    private void onSourceLoad(String sourceId) {
        if (this.k) {
            return;
        }
        this.b.a(sourceId);
    }

    private void onCameraChange(int type, int reason) {
        if (this.k) {
            return;
        }
        this.b.b(type, reason);
    }

    private void onIndoorRegionFocus(IndoorRegion region) {
        if (this.k) {
            return;
        }
        this.b.a(region);
    }

    private void onSnapshotReady(Bitmap snapshot, boolean showControls) throws Resources.NotFoundException {
        if (e("OnSnapshotReady")) {
            return;
        }
        this.b.a(snapshot, showControls);
    }
}
