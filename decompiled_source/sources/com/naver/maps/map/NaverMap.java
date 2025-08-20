package com.naver.maps.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.indoor.IndoorSelection;
import com.naver.maps.map.indoor.IndoorView;
import com.naver.maps.map.internal.NaverMapAccessor;
import com.naver.maps.map.internal.OverlayAccessor;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Overlay;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class NaverMap {

    @Deprecated
    public static final int DEFAULT_BACKGROUND_COLOR = -789775;
    public static final int DEFAULT_BACKGROUND_COLOR_DARK = -14276049;
    public static final int DEFAULT_BACKGROUND_COLOR_LIGHT = -789775;
    public static final int DEFAULT_DEFAULT_CAMERA_ANIMATION_DURATION = 200;
    public static final int DEFAULT_INDOOR_FOCUS_RADIUS_DP = 55;
    public static final int DEFAULT_MAXIMUM_TILT = 60;
    public static final int DEFAULT_PICK_TOLERANCE_DP = 2;
    public static final String LAYER_GROUP_BICYCLE = "bike";
    public static final String LAYER_GROUP_BUILDING = "building";
    public static final String LAYER_GROUP_CADASTRAL = "landparcel";
    public static final String LAYER_GROUP_MOUNTAIN = "mountain";
    public static final String LAYER_GROUP_TRAFFIC = "ctt";
    public static final String LAYER_GROUP_TRANSIT = "transit";
    public static final int MAXIMUM_BEARING = 360;
    public static final int MAXIMUM_TILT = 63;
    public static final int MAXIMUM_ZOOM = 21;
    public static final int MINIMUM_BEARING = 0;
    public static final int MINIMUM_TILT = 0;
    public static final int MINIMUM_ZOOM = 0;
    private static OverlayAccessor overlayAccessor;
    private a A;
    private String[] B;
    private String C;
    private b D;
    private String E;
    private final Context a;
    private final NativeMapView b;
    private final UiSettings c;
    private final Projection d;
    private final h e;
    private final f f;
    private final com.naver.maps.map.a g;
    private final List<OnLoadListener> j;
    private final List<OnMapRenderedListener> k;
    private final List<OnOptionChangeListener> l;
    private final HashSet<String> m;
    private final HashSet<c> n;
    private boolean o;
    private int p;
    private int q;
    private boolean r;
    private boolean s;
    private boolean t;
    private OnMapClickListener u;
    private OnMapLongClickListener v;
    private OnMapDoubleTapListener w;
    private OnMapTwoFingerTapListener x;
    private OnSymbolClickListener y;
    private SnapshotReadyCallback z;
    public static final CameraPosition DEFAULT_CAMERA_POSITION = new CameraPosition(new LatLng(37.5666102d, 126.9783881d), 14.0d, 0.0d, 0.0d);
    public static final int DEFAULT_BACKGROUND_DRWABLE_LIGHT = R.drawable.navermap_default_background_light;
    public static final int DEFAULT_BACKGROUND_DRWABLE_DARK = R.drawable.navermap_default_background_dark;
    private final com.naver.maps.map.internal.net.a F = new com.naver.maps.map.internal.net.a() { // from class: com.naver.maps.map.NaverMap.1
        @Override // com.naver.maps.map.internal.net.a
        public void a(boolean z) {
            if (z) {
                NaverMap.this.l();
            }
        }
    };
    private final com.naver.maps.map.b h = new com.naver.maps.map.b(this);
    private final LocationOverlay i = overlayAccessor.newLocationOverlay();

    public interface OnCameraChangeListener {
        void onCameraChange(int i, boolean z);
    }

    public interface OnCameraIdleListener {
        void onCameraIdle();
    }

    public interface OnIndoorSelectionChangeListener {
        void onIndoorSelectionChange(IndoorSelection indoorSelection);
    }

    public interface OnLoadListener {
        void onLoad();
    }

    public interface OnLocationChangeListener {
        void onLocationChange(Location location);
    }

    public interface OnMapClickListener {
        void onMapClick(PointF pointF, LatLng latLng);
    }

    public interface OnMapDoubleTapListener {
        boolean onMapDoubleTap(PointF pointF, LatLng latLng);
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(PointF pointF, LatLng latLng);
    }

    public interface OnMapRenderedListener {
        void onMapRendered(boolean z, boolean z2);
    }

    public interface OnMapTwoFingerTapListener {
        boolean onMapTwoFingerTap(PointF pointF, LatLng latLng);
    }

    public interface OnOptionChangeListener {
        void onOptionChange();
    }

    public interface OnSymbolClickListener {
        boolean onSymbolClick(Symbol symbol);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    private enum a {
        Unauthorized,
        Authorizing,
        Pending,
        Authorized
    }

    public interface b {
        void a();

        void a(Exception exc);
    }

    private static class c implements Serializable {
        private final String a;
        private final String b;

        public c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            c cVar = (c) o;
            if (!this.a.equals(cVar.a)) {
                return false;
            }
            return this.b.equals(cVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }
    }

    private static class Accessor implements NaverMapAccessor {
        private Accessor() {
        }

        @Override // com.naver.maps.map.internal.NaverMapAccessor
        public void addOverlay(NaverMap map, Overlay overlay, long handle) {
            map.b.a(overlay, handle);
        }

        @Override // com.naver.maps.map.internal.NaverMapAccessor
        public void removeOverlay(NaverMap map, Overlay overlay, long handle) {
            map.b.b(overlay, handle);
        }

        @Override // com.naver.maps.map.internal.NaverMapAccessor
        public Thread getThread(NaverMap map) {
            return map.b.c();
        }
    }

    public enum MapType {
        Basic("basic"),
        Navi("navi"),
        Satellite("satellite"),
        Hybrid("hybrid"),
        Terrain("terrain"),
        None("none"),
        NaviHybrid("navi_satellite");

        private final String a;

        MapType(String id) {
            this.a = id;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static MapType b(String str) {
            for (MapType mapType : values()) {
                if (mapType.a.equals(str)) {
                    return mapType;
                }
            }
            return None;
        }
    }

    NaverMap(Context context, NativeMapView nativeMapView, MapControlsView controls) {
        this.a = context;
        this.b = nativeMapView;
        this.c = new UiSettings(context, controls);
        this.d = new Projection(this, nativeMapView);
        this.e = new h(nativeMapView);
        this.f = new f(this, nativeMapView);
        this.g = new com.naver.maps.map.a(this, nativeMapView);
        this.i.setCircleRadius((int) (nativeMapView.b() * 18.0f));
        this.j = new CopyOnWriteArrayList();
        this.k = new CopyOnWriteArrayList();
        this.l = new CopyOnWriteArrayList();
        this.m = new HashSet<>();
        this.n = new HashSet<>();
        this.A = a.Unauthorized;
        l();
    }

    void a(NaverMapOptions naverMapOptions) {
        this.e.a(this, naverMapOptions);
        this.c.a(naverMapOptions);
        this.f.a(naverMapOptions);
        this.g.a(naverMapOptions);
        a(naverMapOptions.b(), (b) null);
        setMapType(naverMapOptions.getMapType());
        Iterator<String> it = naverMapOptions.getEnabledLayerGroups().iterator();
        while (it.hasNext()) {
            setLayerGroupEnabled(it.next(), true);
        }
        setLiteModeEnabled(naverMapOptions.isLiteModeEnabled());
        setNightModeEnabled(naverMapOptions.isNightModeEnabled());
        setBuildingHeight(naverMapOptions.getBuildingHeight());
        setLightness(naverMapOptions.getLightness());
        setSymbolScale(naverMapOptions.getSymbolScale());
        setSymbolPerspectiveRatio(naverMapOptions.getSymbolPerspectiveRatio());
        int indoorFocusRadius = naverMapOptions.getIndoorFocusRadius();
        if (indoorFocusRadius < 0) {
            indoorFocusRadius = Math.round(this.a.getResources().getDisplayMetrics().density * 55.0f);
        }
        setIndoorFocusRadius(indoorFocusRadius);
        setBackgroundColor(naverMapOptions.getBackgroundColor());
        setBackgroundResource(naverMapOptions.getBackgroundResource());
        this.b.c(naverMapOptions.f());
    }

    void a(Bundle bundle) {
        this.e.a(this, bundle);
        this.c.a(bundle);
        this.f.a(bundle);
        this.g.a(bundle);
        this.h.a(bundle);
        bundle.putString("NaverMap00", this.C);
        bundle.putSerializable("NaverMap01", getMapType());
        bundle.putSerializable("NaverMap02", this.m);
        bundle.putSerializable("NaverMap03", this.n);
        bundle.putBoolean("NaverMap04", this.o);
        bundle.putBoolean("NaverMap05", isNightModeEnabled());
        bundle.putFloat("NaverMap06", getBuildingHeight());
        bundle.putFloat("NaverMap07", getLightness());
        bundle.putFloat("NaverMap08", getSymbolScale());
        bundle.putFloat("NaverMap09", getSymbolPerspectiveRatio());
        bundle.putInt("NaverMap10", this.q);
        bundle.putInt("NaverMap11", this.p);
        bundle.putBoolean("NaverMap12", this.b.A());
    }

    void b(Bundle bundle) {
        this.e.b(this, bundle);
        this.c.b(bundle);
        this.f.b(bundle);
        this.g.b(bundle);
        this.h.b(bundle);
        a(bundle.getString("NaverMap00"), (b) null);
        MapType mapType = (MapType) bundle.getSerializable("NaverMap01");
        if (mapType != null) {
            setMapType(mapType);
        }
        HashSet hashSet = (HashSet) bundle.getSerializable("NaverMap02");
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                setLayerGroupEnabled((String) it.next(), true);
            }
        }
        HashSet hashSet2 = (HashSet) bundle.getSerializable("NaverMap03");
        if (hashSet2 != null) {
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                c cVar = (c) it2.next();
                a(cVar.a, cVar.a, true);
            }
        }
        setLiteModeEnabled(bundle.getBoolean("NaverMap04"));
        setNightModeEnabled(bundle.getBoolean("NaverMap05"));
        setBuildingHeight(bundle.getFloat("NaverMap06"));
        setLightness(bundle.getFloat("NaverMap07"));
        setSymbolScale(bundle.getFloat("NaverMap08"));
        setSymbolPerspectiveRatio(bundle.getFloat("NaverMap09"));
        setBackgroundColor(bundle.getInt("NaverMap10"));
        setBackgroundResource(bundle.getInt("NaverMap11"));
        this.b.c(bundle.getBoolean("NaverMap12"));
    }

    void a() {
        this.b.f();
        this.h.d();
        com.naver.maps.map.internal.net.b.a(this.a).a(this.F);
    }

    void b() {
        com.naver.maps.map.internal.net.b.a(this.a).b(this.F);
        this.h.e();
        this.b.g();
    }

    void c() {
        this.i.setPosition(getCameraPosition().target);
        this.i.setMap(this);
    }

    void d() {
        this.e.a();
        this.h.a();
    }

    public boolean isDestroyed() {
        return this.b.a();
    }

    public void forceRefresh() {
        this.b.h();
    }

    public Context e() {
        return this.a;
    }

    public UiSettings getUiSettings() {
        return this.c;
    }

    public Projection getProjection() {
        return this.d;
    }

    public f f() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        if (isDestroyed() || this.A == a.Authorizing || this.A == a.Authorized) {
            return;
        }
        this.A = a.Authorizing;
        NaverMapSdk.getInstance(this.a).a(new NaverMapSdk.b() { // from class: com.naver.maps.map.NaverMap.2
            @Override // com.naver.maps.map.NaverMapSdk.b
            public void a(String[] strArr) {
                NaverMap.this.A = a.Authorized;
                NaverMap.this.a(strArr);
                NaverMap.this.m();
            }

            @Override // com.naver.maps.map.NaverMapSdk.b
            public void a(String[] strArr, Exception exc) {
                NaverMap.this.A = a.Pending;
                NaverMap.this.a(strArr);
                NaverMap.this.m();
            }

            @Override // com.naver.maps.map.NaverMapSdk.b
            public void a(NaverMapSdk.AuthFailedException authFailedException) {
                NaverMap.this.A = a.Unauthorized;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String[] strArr) {
        if (strArr == null || strArr.length != 2 || Arrays.equals(strArr, this.B)) {
            return;
        }
        this.B = strArr;
    }

    private void a(String str, b bVar) {
        this.C = str;
        this.D = bVar;
        if (this.A == a.Pending || this.A == a.Authorized) {
            m();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        if (TextUtils.isEmpty(this.C)) {
            this.E = null;
            g();
        } else {
            NaverMapSdk.getInstance(this.a).a(this.C, new NaverMapSdk.d() { // from class: com.naver.maps.map.NaverMap.3
                @Override // com.naver.maps.map.NaverMapSdk.d
                public void a(String str) {
                    NaverMap.this.E = str;
                    NaverMap.this.g();
                    if (NaverMap.this.D != null) {
                        NaverMap.this.D.a();
                    }
                }

                @Override // com.naver.maps.map.NaverMapSdk.d
                public void a(Exception exc) {
                    NaverMap.this.E = null;
                    NaverMap.this.g();
                    if (NaverMap.this.D != null) {
                        NaverMap.this.D.a(exc);
                    }
                }
            });
        }
    }

    void g() {
        if (this.A == a.Unauthorized || this.A == a.Authorizing) {
            return;
        }
        boolean z = this.o;
        String strA = a(this.f.c(), z ? 1 : 0);
        if (!TextUtils.isEmpty(strA)) {
            this.b.b(strA);
            return;
        }
        String strA2 = a(this.f.b(), z ? 1 : 0);
        if (!TextUtils.isEmpty(strA2)) {
            this.b.a(strA2);
            return;
        }
        if (!TextUtils.isEmpty(this.E)) {
            this.b.a(this.E);
            return;
        }
        String strA3 = a(this.B, z ? 1 : 0);
        if (!TextUtils.isEmpty(strA3)) {
            this.b.a(strA3);
        }
    }

    public Locale getLocale() {
        return this.b.t();
    }

    public void setLocale(Locale locale) {
        this.b.a(locale);
    }

    public CameraPosition getCameraPosition() {
        return this.e.c();
    }

    public LatLngBounds getContentBounds() {
        return this.e.d();
    }

    public LatLng[] getContentRegion() {
        return this.e.e();
    }

    public LatLngBounds getCoveringBounds() {
        return this.e.f();
    }

    public LatLng[] getCoveringRegion() {
        return this.e.g();
    }

    public long[] getCoveringTileIds() {
        return this.e.h();
    }

    public long[] getCoveringTileIdsAtZoom(int zoom) {
        return this.b.b(zoom);
    }

    public void setCameraPosition(CameraPosition cameraPosition) {
        moveCamera(CameraUpdate.toCameraPosition(cameraPosition));
    }

    public void moveCamera(CameraUpdate update) {
        this.e.a(this, update);
    }

    public void cancelTransitions() {
        cancelTransitions(0);
    }

    public void cancelTransitions(int reason) {
        this.e.a(reason, false);
    }

    public int getDefaultCameraAnimationDuration() {
        return this.e.n();
    }

    public void setDefaultCameraAnimationDuration(int duration) {
        this.e.a(duration);
        i();
    }

    public LatLngBounds getExtent() {
        return this.e.i();
    }

    public void setExtent(LatLngBounds extent) {
        this.e.a(extent);
        i();
    }

    public double getMinZoom() {
        return this.e.j();
    }

    public void setMinZoom(double minZoom) {
        this.e.a(minZoom);
        i();
    }

    public double getMaxZoom() {
        return this.e.k();
    }

    public void setMaxZoom(double maxZoom) {
        this.e.b(maxZoom);
        i();
    }

    public double getMaxTilt() {
        return this.e.l();
    }

    public void setMaxTilt(double maxTilt) {
        this.e.c(maxTilt);
        i();
    }

    public MapType getMapType() {
        return MapType.b(this.b.u());
    }

    public void setMapType(MapType mapType) {
        this.b.c(mapType.a);
        i();
    }

    public Set<String> getEnabledLayerGroups() {
        return Collections.unmodifiableSet(this.m);
    }

    public boolean isLayerGroupEnabled(String group) {
        return this.m.contains(group);
    }

    public void setLayerGroupEnabled(String group, boolean enabled) {
        if (enabled) {
            if (this.m.add(group)) {
                this.b.a(group, true);
            }
        } else if (this.m.remove(group)) {
            this.b.a(group, false);
        }
        i();
    }

    public boolean isDark() {
        MapType mapType = getMapType();
        return isNightModeEnabled() || mapType == MapType.Satellite || mapType == MapType.Hybrid || mapType == MapType.NaviHybrid;
    }

    public boolean isLiteModeEnabled() {
        return this.o;
    }

    public void setLiteModeEnabled(boolean enabled) {
        if (this.o == enabled) {
            return;
        }
        this.o = enabled;
        g();
    }

    public boolean isNightModeEnabled() {
        return this.b.v();
    }

    public void setNightModeEnabled(boolean enabled) {
        this.b.b(enabled);
        i();
    }

    public float getBuildingHeight() {
        return this.b.w();
    }

    public void setBuildingHeight(float buildingHeight) {
        this.b.a(buildingHeight);
        i();
    }

    public float getLightness() {
        return this.b.x();
    }

    public void setLightness(float lightness) {
        this.b.b(lightness);
        i();
    }

    public void a(String str, String str2, boolean z) {
        c cVar = new c(str, str2);
        if (z) {
            if (this.n.add(cVar)) {
                this.b.a(str, str2, true);
            }
        } else if (this.n.remove(cVar)) {
            this.b.a(str, str2, false);
        }
    }

    public float getSymbolScale() {
        return this.b.y();
    }

    public void setSymbolScale(float scale) {
        this.b.c(scale);
        i();
    }

    public float getSymbolPerspectiveRatio() {
        return this.b.z();
    }

    public void setSymbolPerspectiveRatio(float ratio) {
        this.b.d(ratio);
        i();
    }

    public boolean isIndoorEnabled() {
        return this.g.a();
    }

    public void setIndoorEnabled(boolean enabled) {
        this.g.a(enabled);
        i();
    }

    public int getIndoorFocusRadius() {
        return this.b.B();
    }

    public void setIndoorFocusRadius(int radius) {
        this.b.e(radius);
        i();
    }

    public void requestIndoorView(IndoorView indoorView) {
        this.g.a(indoorView);
    }

    public IndoorSelection getIndoorSelection() {
        return this.g.b();
    }

    public void addOnIndoorSelectionChangeListener(OnIndoorSelectionChangeListener listener) {
        this.g.a(listener);
    }

    public void removeOnIndoorSelectionChangeListener(OnIndoorSelectionChangeListener listener) {
        this.g.b(listener);
    }

    public int getBackgroundColor() {
        return this.q;
    }

    public void setBackgroundColor(int color) {
        this.q = color;
        this.b.c(color);
        i();
    }

    public void setBackgroundResource(int resId) {
        this.p = resId;
        this.b.d(resId);
        i();
    }

    public void setBackground(Drawable drawable) {
        this.p = 0;
        this.b.a(drawable);
        i();
    }

    public void setBackgroundBitmap(Bitmap bitmap) {
        this.p = 0;
        this.b.a(bitmap);
        i();
    }

    public List<Pickable> pickAll(PointF point) {
        return pickAll(point, 0);
    }

    public List<Pickable> pickAll(PointF point, int radius) {
        return this.b.b(point, radius);
    }

    public LocationOverlay getLocationOverlay() {
        return this.i;
    }

    public LocationTrackingMode getLocationTrackingMode() {
        return this.h.b();
    }

    public void setLocationTrackingMode(LocationTrackingMode mode) {
        if (this.h.a(mode)) {
            i();
        }
    }

    public LocationSource getLocationSource() {
        return this.h.c();
    }

    public void setLocationSource(LocationSource source) {
        if (this.h.a(source)) {
            i();
        }
    }

    public void addOnLocationChangeListener(OnLocationChangeListener listener) {
        this.h.a(listener);
    }

    public void removeOnLocationChangeListener(OnLocationChangeListener listener) {
        this.h.b(listener);
    }

    public int getHeight() {
        return this.b.D();
    }

    public int getWidth() {
        return this.b.C();
    }

    public int getContentWidth() {
        return (getWidth() - getContentPadding()[0]) - getContentPadding()[2];
    }

    public int getContentHeight() {
        return (getHeight() - getContentPadding()[1]) - getContentPadding()[3];
    }

    public Rect getContentRect() {
        return new Rect(getContentPadding()[0], getContentPadding()[1], getWidth() - getContentPadding()[2], getHeight() - getContentPadding()[3]);
    }

    public int[] getContentPadding() {
        return this.e.m();
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        setContentPadding(left, top, right, bottom, -4);
    }

    public void setContentPadding(int left, int top, int right, int bottom, int reason) {
        setContentPadding(left, top, right, bottom, false, reason);
    }

    public void setContentPadding(int left, int top, int right, int bottom, boolean keepCameraTarget) {
        setContentPadding(left, top, right, bottom, keepCameraTarget, -4);
    }

    public void setContentPadding(int left, int top, int right, int bottom, boolean keepCameraTarget, int reason) {
        this.c.a(left, top, right, bottom);
        this.e.a(left, top, right, bottom, keepCameraTarget, reason);
        i();
    }

    public void setFpsLimit(int fps) {
        this.b.f(fps);
        i();
    }

    public int getFpsLimit() {
        return this.b.E();
    }

    public void addOnCameraChangeListener(OnCameraChangeListener listener) {
        this.e.a(listener);
    }

    public void removeOnCameraChangeListener(OnCameraChangeListener listener) {
        this.e.b(listener);
    }

    public void addOnCameraIdleListener(OnCameraIdleListener listener) {
        this.e.a(listener);
    }

    public void removeOnCameraIdleListener(OnCameraIdleListener listener) {
        this.e.b(listener);
    }

    public boolean isCameraIdlePending() {
        return this.e.b();
    }

    public void setCameraIdlePending(boolean cameraIdlePending) {
        this.e.b(cameraIdlePending);
    }

    public boolean isLoaded() {
        return this.r;
    }

    public void addOnLoadListener(OnLoadListener listener) {
        this.j.add(listener);
    }

    public void removeOnLoadListener(OnLoadListener listener) {
        this.j.remove(listener);
    }

    void h() {
        this.r = true;
        Iterator<OnLoadListener> it = this.j.iterator();
        while (it.hasNext()) {
            it.next().onLoad();
        }
    }

    public boolean isFullyRendered() {
        return this.s;
    }

    public boolean isRenderingStable() {
        return this.t;
    }

    public void addOnMapRenderedListener(OnMapRenderedListener listener) {
        this.k.add(listener);
    }

    public void removeOnMapRenderedListener(OnMapRenderedListener listener) {
        this.k.remove(listener);
    }

    void a(boolean z, boolean z2) {
        this.s = z;
        this.t = z2;
        Iterator<OnMapRenderedListener> it = this.k.iterator();
        while (it.hasNext()) {
            it.next().onMapRendered(z, z2);
        }
    }

    public void addOnOptionChangeListener(OnOptionChangeListener listener) {
        this.l.add(listener);
    }

    public void removeOnOptionChangeListener(OnOptionChangeListener listener) {
        this.l.remove(listener);
    }

    void i() {
        Iterator<OnOptionChangeListener> it = this.l.iterator();
        while (it.hasNext()) {
            it.next().onOptionChange();
        }
    }

    public OnMapClickListener getOnMapClickListener() {
        return this.u;
    }

    public void setOnMapClickListener(OnMapClickListener listener) {
        this.u = listener;
    }

    public OnMapLongClickListener getOnMapLongClickListener() {
        return this.v;
    }

    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        this.v = listener;
    }

    public OnMapDoubleTapListener getOnMapDoubleTapListener() {
        return this.w;
    }

    public void setOnMapDoubleTapListener(OnMapDoubleTapListener listener) {
        this.w = listener;
    }

    public OnMapTwoFingerTapListener getOnMapTwoFingerTapListener() {
        return this.x;
    }

    public void setOnMapTwoFingerTapListener(OnMapTwoFingerTapListener listener) {
        this.x = listener;
    }

    public OnSymbolClickListener getOnSymbolClickListener() {
        return this.y;
    }

    public void setOnSymbolClickListener(OnSymbolClickListener listener) {
        this.y = listener;
    }

    boolean a(PointF pointF) {
        Pickable pickableA = this.b.a(pointF, this.c.getPickTolerance());
        if (pickableA != null) {
            if (pickableA instanceof Overlay) {
                if (((Overlay) pickableA).performClick()) {
                    return true;
                }
            } else if ((pickableA instanceof Symbol) && this.y != null && this.y.onSymbolClick((Symbol) pickableA)) {
                return true;
            }
        }
        if (this.u != null) {
            this.u.onMapClick(pointF, this.d.fromScreenLocation(pointF));
            return true;
        }
        return false;
    }

    boolean b(PointF pointF) {
        if (this.v != null) {
            this.v.onMapLongClick(pointF, this.d.fromScreenLocation(pointF));
            return true;
        }
        return false;
    }

    boolean c(PointF pointF) {
        if (this.w != null) {
            this.w.onMapDoubleTap(pointF, this.d.fromScreenLocation(pointF));
            return true;
        }
        return false;
    }

    boolean d(PointF pointF) {
        if (this.x != null) {
            this.x.onMapTwoFingerTap(pointF, this.d.fromScreenLocation(pointF));
            return true;
        }
        return false;
    }

    public void takeSnapshot(SnapshotReadyCallback callback) {
        this.z = callback;
        this.b.e(true);
    }

    public void takeSnapshot(boolean showControls, SnapshotReadyCallback callback) {
        this.z = callback;
        this.b.e(showControls);
    }

    void a(Bitmap bitmap) {
        if (this.z != null) {
            this.z.onSnapshotReady(bitmap);
            this.z = null;
        }
    }

    h j() {
        return this.e;
    }

    com.naver.maps.map.a k() {
        return this.g;
    }

    private static String a(String[] strArr, int i) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        if (strArr.length <= i) {
            return strArr[0];
        }
        return strArr[i];
    }
}
