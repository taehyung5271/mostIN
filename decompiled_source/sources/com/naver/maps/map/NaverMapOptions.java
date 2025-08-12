package com.naver.maps.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.text.DefaultTypefaceFactory;
import com.naver.maps.map.text.TypefaceFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public final class NaverMapOptions implements Parcelable {
    public static final Parcelable.Creator<NaverMapOptions> CREATOR = new Parcelable.Creator<NaverMapOptions>() { // from class: com.naver.maps.map.NaverMapOptions.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public NaverMapOptions createFromParcel(Parcel parcel) {
            return new NaverMapOptions(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public NaverMapOptions[] newArray(int i) {
            return new NaverMapOptions[i];
        }
    };
    private boolean A;
    private boolean B;
    private float C;
    private float D;
    private float E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I;
    private boolean J;
    private boolean K;
    private boolean L;
    private int M;
    private int[] N;
    private int O;
    private float P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private boolean U;
    private boolean V;
    private boolean W;
    private Class<? extends TypefaceFactory> X;
    private boolean Y;
    private String[] a;
    private String b;
    private Locale c;
    private CameraPosition d;
    private LatLngBounds e;
    private double f;
    private double g;
    private double h;
    private int[] i;
    private int j;
    private NaverMap.MapType k;
    private HashSet<String> l;
    private boolean m;
    private boolean n;
    private float o;
    private float p;
    private float q;
    private float r;
    private boolean s;
    private int t;
    private int u;
    private int v;
    private int w;
    private boolean x;
    private boolean y;
    private boolean z;

    private static LatLngBounds a(TypedArray typedArray, int i) {
        String string = typedArray.getString(i);
        if (string == null) {
            return null;
        }
        String[] strArrSplit = string.split(",");
        if (strArrSplit.length != 4) {
            return null;
        }
        try {
            return new LatLngBounds(new LatLng(Double.parseDouble(strArrSplit[0]), Double.parseDouble(strArrSplit[1])), new LatLng(Double.parseDouble(strArrSplit[2]), Double.parseDouble(strArrSplit[3])));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static NaverMapOptions a(Context context, AttributeSet attributeSet) {
        NaverMapOptions naverMapOptions = new NaverMapOptions();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NaverMap, 0, 0);
        try {
            float f = typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_latitude, Float.NaN);
            float f2 = typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_longitude, Float.NaN);
            if (!Float.isNaN(f) && !Float.isNaN(f2)) {
                naverMapOptions.camera(new CameraPosition(new LatLng(f, f2), typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_zoom, (float) NaverMap.DEFAULT_CAMERA_POSITION.zoom), typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_tilt, 0.0f), typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_bearing, 0.0f)));
            }
            naverMapOptions.extent(a(typedArrayObtainStyledAttributes, R.styleable.NaverMap_navermap_extent));
            naverMapOptions.minZoom(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_minZoom, 0.0f));
            naverMapOptions.maxZoom(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_maxZoom, 21.0f));
            naverMapOptions.maxTilt(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_maxTilt, 60.0f));
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_contentPadding, -1);
            if (dimensionPixelSize >= 0) {
                naverMapOptions.contentPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            } else {
                naverMapOptions.contentPadding(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_contentPaddingLeft, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_contentPaddingTop, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_contentPaddingRight, 0), typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_contentPaddingBottom, 0));
            }
            naverMapOptions.defaultCameraAnimationDuration(typedArrayObtainStyledAttributes.getInt(R.styleable.NaverMap_navermap_defaultCameraAnimationDuration, 200));
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.NaverMap_navermap_mapType);
            if (string != null) {
                naverMapOptions.mapType(NaverMap.MapType.valueOf(string));
            }
            String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.NaverMap_navermap_enabledLayerGroups);
            if (string2 != null) {
                naverMapOptions.l.clear();
                Collections.addAll(naverMapOptions.l, string2.split("\\|"));
            }
            naverMapOptions.liteModeEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_liteModeEnabled, false));
            naverMapOptions.nightModeEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_nightModeEnabled, false));
            naverMapOptions.buildingHeight(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_buildingHeight, 1.0f));
            naverMapOptions.lightness(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_lightness, 0.0f));
            naverMapOptions.symbolScale(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_symbolScale, 1.0f));
            naverMapOptions.symbolPerspectiveRatio(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_symbolPerspectiveRatio, 1.0f));
            naverMapOptions.indoorEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_indoorEnabled, false));
            naverMapOptions.indoorFocusRadius(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_indoorFocusRadius, -1));
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.NaverMap_navermap_background)) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.NaverMap_navermap_background, 0);
                if (resourceId > 0) {
                    naverMapOptions.backgroundResource(resourceId);
                } else {
                    naverMapOptions.backgroundColor(typedArrayObtainStyledAttributes.getColor(R.styleable.NaverMap_navermap_background, -789775));
                }
            } else {
                naverMapOptions.backgroundColor(typedArrayObtainStyledAttributes.getColor(R.styleable.NaverMap_navermap_backgroundColor, -789775));
                naverMapOptions.backgroundResource(typedArrayObtainStyledAttributes.getResourceId(R.styleable.NaverMap_navermap_backgroundImage, NaverMap.DEFAULT_BACKGROUND_DRWABLE_LIGHT));
            }
            naverMapOptions.pickTolerance(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_pickTolerance, -1));
            naverMapOptions.scrollGesturesEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_scrollGesturesEnabled, true));
            naverMapOptions.zoomGesturesEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_zoomGesturesEnabled, true));
            naverMapOptions.tiltGesturesEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_tiltGesturesEnabled, true));
            naverMapOptions.rotateGesturesEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_rotateGesturesEnabled, true));
            naverMapOptions.stopGesturesEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_stopGesturesEnabled, true));
            naverMapOptions.scrollGesturesFriction(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_scrollGesturesFriction, 0.088f));
            naverMapOptions.zoomGesturesFriction(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_zoomGesturesFriction, 0.12375f));
            naverMapOptions.rotateGesturesFriction(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_rotateGesturesFriction, 0.19333f));
            naverMapOptions.compassEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_compassEnabled, true));
            naverMapOptions.scaleBarEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_scaleBarEnabled, true));
            naverMapOptions.zoomControlEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_zoomControlEnabled, true));
            naverMapOptions.indoorLevelPickerEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_indoorLevelPickerEnabled, true));
            naverMapOptions.locationButtonEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_locationButtonEnabled, false));
            naverMapOptions.logoClickEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_logoClickEnabled, true));
            naverMapOptions.logoGravity(typedArrayObtainStyledAttributes.getInt(R.styleable.NaverMap_navermap_logoGravity, 0));
            int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_logoMargin, -1);
            if (dimensionPixelSize2 >= 0) {
                naverMapOptions.logoMargin(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            } else {
                int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_logoMarginStart, -1);
                int dimensionPixelSize4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_logoMarginTop, -1);
                int dimensionPixelSize5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_logoMarginEnd, -1);
                int dimensionPixelSize6 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NaverMap_navermap_logoMarginBottom, -1);
                if (dimensionPixelSize3 >= 0 || dimensionPixelSize4 >= 0 || dimensionPixelSize5 >= 0 || dimensionPixelSize6 >= 0) {
                    naverMapOptions.logoMargin(MathUtils.clamp(dimensionPixelSize3, 0, Integer.MAX_VALUE), MathUtils.clamp(dimensionPixelSize4, 0, Integer.MAX_VALUE), MathUtils.clamp(dimensionPixelSize5, 0, Integer.MAX_VALUE), MathUtils.clamp(dimensionPixelSize6, 0, Integer.MAX_VALUE));
                }
            }
            naverMapOptions.fpsLimit(typedArrayObtainStyledAttributes.getInt(R.styleable.NaverMap_navermap_fpsLimit, 0));
            naverMapOptions.a(typedArrayObtainStyledAttributes.getFloat(R.styleable.NaverMap_navermap_mapScale, 1.0f));
            naverMapOptions.useTextureView(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_useTextureView, false));
            naverMapOptions.a(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_useVulkanView, false));
            naverMapOptions.msaaEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_msaaEnabled, false));
            naverMapOptions.translucentTextureSurface(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_translucentTextureSurface, false));
            naverMapOptions.zOrderMediaOverlay(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_zOrderMediaOverlay, false));
            naverMapOptions.preserveEGLContextOnPause(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_preserveEGLContextOnPause, true));
            naverMapOptions.b(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_object3dEnabled, false));
            String string3 = typedArrayObtainStyledAttributes.getString(R.styleable.NaverMap_navermap_localTypefaceFactoryClass);
            if (!TextUtils.isEmpty(string3)) {
                try {
                    Class<?> cls = Class.forName(string3);
                    if (TypefaceFactory.class.isAssignableFrom(cls)) {
                        naverMapOptions.a((Class<? extends TypefaceFactory>) cls);
                    }
                } catch (Exception e) {
                }
            }
            naverMapOptions.c(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMap_navermap_cjkLocalGlyphRasterizationEnabled, false));
            return naverMapOptions;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public NaverMapOptions() {
        this.f = 0.0d;
        this.g = 21.0d;
        this.h = 63.0d;
        this.i = new int[4];
        this.j = 200;
        this.k = NaverMap.MapType.Basic;
        this.l = new HashSet<>(Collections.singleton(NaverMap.LAYER_GROUP_BUILDING));
        this.m = false;
        this.n = false;
        this.o = 1.0f;
        this.p = 0.0f;
        this.q = 1.0f;
        this.r = 1.0f;
        this.s = false;
        this.t = -1;
        this.u = -789775;
        this.v = NaverMap.DEFAULT_BACKGROUND_DRWABLE_LIGHT;
        this.w = -1;
        this.x = true;
        this.y = true;
        this.z = true;
        this.A = true;
        this.B = true;
        this.C = 0.088f;
        this.D = 0.12375f;
        this.E = 0.19333f;
        this.F = true;
        this.G = true;
        this.H = true;
        this.I = true;
        this.J = false;
        this.K = true;
        this.L = true;
        this.M = 0;
        this.P = 1.0f;
        this.Q = false;
        this.R = false;
        this.S = false;
        this.T = false;
        this.U = false;
        this.V = true;
        this.W = false;
        this.X = DefaultTypefaceFactory.class;
        this.Y = false;
    }

    public NaverMapOptions locale(Locale locale) {
        this.c = locale;
        return this;
    }

    public NaverMapOptions camera(CameraPosition cameraPosition) {
        this.d = cameraPosition;
        return this;
    }

    public NaverMapOptions extent(LatLngBounds extent) {
        this.e = extent;
        return this;
    }

    public NaverMapOptions minZoom(double minZoom) {
        this.f = minZoom;
        return this;
    }

    public NaverMapOptions maxZoom(double maxZoom) {
        this.g = maxZoom;
        return this;
    }

    public NaverMapOptions maxTilt(double maxTilt) {
        this.h = maxTilt;
        return this;
    }

    public NaverMapOptions contentPadding(int left, int top, int right, int bottom) {
        this.i[0] = left;
        this.i[1] = top;
        this.i[2] = right;
        this.i[3] = bottom;
        return this;
    }

    public NaverMapOptions defaultCameraAnimationDuration(int duration) {
        this.j = duration;
        return this;
    }

    public NaverMapOptions mapType(NaverMap.MapType mapType) {
        this.k = mapType;
        return this;
    }

    public NaverMapOptions enabledLayerGroups(String... groups) {
        Collections.addAll(this.l, groups);
        return this;
    }

    public NaverMapOptions disabledLayerGroups(String... groups) {
        for (String str : groups) {
            this.l.remove(str);
        }
        return this;
    }

    public NaverMapOptions liteModeEnabled(boolean enabled) {
        this.m = enabled;
        return this;
    }

    public NaverMapOptions nightModeEnabled(boolean enabled) {
        this.n = enabled;
        return this;
    }

    public NaverMapOptions buildingHeight(float buildingHeight) {
        this.o = buildingHeight;
        return this;
    }

    public NaverMapOptions lightness(float lightness) {
        this.p = lightness;
        return this;
    }

    public NaverMapOptions symbolScale(float scale) {
        this.q = scale;
        return this;
    }

    public NaverMapOptions symbolPerspectiveRatio(float ratio) {
        this.r = ratio;
        return this;
    }

    public NaverMapOptions indoorEnabled(boolean enabled) {
        this.s = enabled;
        return this;
    }

    public NaverMapOptions indoorFocusRadius(int radius) {
        this.t = radius;
        return this;
    }

    public NaverMapOptions backgroundColor(int color) {
        this.u = color;
        return this;
    }

    public NaverMapOptions backgroundResource(int resId) {
        this.v = resId;
        return this;
    }

    public NaverMapOptions pickTolerance(int tolerance) {
        this.w = tolerance;
        return this;
    }

    public NaverMapOptions scrollGesturesEnabled(boolean enabled) {
        this.x = enabled;
        return this;
    }

    public NaverMapOptions zoomGesturesEnabled(boolean enabled) {
        this.y = enabled;
        return this;
    }

    public NaverMapOptions tiltGesturesEnabled(boolean enabled) {
        this.z = enabled;
        return this;
    }

    public NaverMapOptions rotateGesturesEnabled(boolean enabled) {
        this.A = enabled;
        return this;
    }

    public NaverMapOptions stopGesturesEnabled(boolean enabled) {
        this.B = enabled;
        return this;
    }

    public NaverMapOptions allGesturesEnabled(boolean enabled) {
        this.A = enabled;
        this.x = enabled;
        this.z = enabled;
        this.y = enabled;
        this.B = enabled;
        return this;
    }

    public NaverMapOptions scrollGesturesFriction(float friction) {
        this.C = friction;
        return this;
    }

    public NaverMapOptions zoomGesturesFriction(float friction) {
        this.D = friction;
        return this;
    }

    public NaverMapOptions rotateGesturesFriction(float friction) {
        this.E = friction;
        return this;
    }

    public NaverMapOptions compassEnabled(boolean enabled) {
        this.F = enabled;
        return this;
    }

    public NaverMapOptions scaleBarEnabled(boolean enabled) {
        this.G = enabled;
        return this;
    }

    public NaverMapOptions zoomControlEnabled(boolean enabled) {
        this.H = enabled;
        return this;
    }

    public NaverMapOptions indoorLevelPickerEnabled(boolean enabled) {
        this.I = enabled;
        return this;
    }

    public NaverMapOptions locationButtonEnabled(boolean enabled) {
        this.J = enabled;
        return this;
    }

    public NaverMapOptions logoClickEnabled(boolean enabled) {
        this.L = enabled;
        return this;
    }

    public NaverMapOptions logoGravity(int gravity) {
        this.M = gravity;
        return this;
    }

    public NaverMapOptions logoMargin(int start, int top, int end, int bottom) {
        this.N = new int[]{start, top, end, bottom};
        return this;
    }

    public NaverMapOptions fpsLimit(int fps) {
        this.O = fps;
        return this;
    }

    private NaverMapOptions a(float f) {
        this.P = f;
        return this;
    }

    public NaverMapOptions useTextureView(boolean useTextureView) {
        this.Q = useTextureView;
        return this;
    }

    private NaverMapOptions a(boolean z) {
        this.R = z;
        return this;
    }

    public NaverMapOptions msaaEnabled(boolean enabled) {
        this.S = enabled;
        return this;
    }

    public NaverMapOptions translucentTextureSurface(boolean translucentTextureSurface) {
        this.T = translucentTextureSurface;
        return this;
    }

    public NaverMapOptions zOrderMediaOverlay(boolean enabled) {
        this.U = enabled;
        return this;
    }

    public NaverMapOptions preserveEGLContextOnPause(boolean enabled) {
        this.V = enabled;
        return this;
    }

    private NaverMapOptions b(boolean z) {
        this.W = z;
        return this;
    }

    private NaverMapOptions a(Class<? extends TypefaceFactory> cls) {
        this.X = cls;
        return this;
    }

    private NaverMapOptions c(boolean z) {
        this.Y = z;
        return this;
    }

    String[] a() {
        return this.a;
    }

    String b() {
        return this.b;
    }

    public Locale getLocale() {
        return this.c;
    }

    public CameraPosition getCameraPosition() {
        return this.d;
    }

    public LatLngBounds getExtent() {
        return this.e;
    }

    public double getMinZoom() {
        return this.f;
    }

    public double getMaxZoom() {
        return this.g;
    }

    public double getMaxTilt() {
        return this.h;
    }

    public int[] getContentPadding() {
        return this.i;
    }

    public int getDefaultCameraAnimationDuration() {
        return this.j;
    }

    public NaverMap.MapType getMapType() {
        return this.k;
    }

    public Set<String> getEnabledLayerGroups() {
        return this.l;
    }

    public boolean isLiteModeEnabled() {
        return this.m;
    }

    public boolean isNightModeEnabled() {
        return this.n;
    }

    public float getBuildingHeight() {
        return this.o;
    }

    public float getLightness() {
        return this.p;
    }

    public float getSymbolScale() {
        return this.q;
    }

    public float getSymbolPerspectiveRatio() {
        return this.r;
    }

    public boolean isIndoorEnabled() {
        return this.s;
    }

    public int getIndoorFocusRadius() {
        return this.t;
    }

    public int getBackgroundColor() {
        return this.u;
    }

    public int getBackgroundResource() {
        return this.v;
    }

    public int getPickTolerance() {
        return this.w;
    }

    public boolean isScrollGesturesEnabled() {
        return this.x;
    }

    public boolean isZoomGesturesEnabled() {
        return this.y;
    }

    public boolean isTiltGesturesEnabled() {
        return this.z;
    }

    public boolean isRotateGesturesEnabled() {
        return this.A;
    }

    public boolean isStopGesturesEnabled() {
        return this.B;
    }

    public float getScrollGesturesFriction() {
        return this.C;
    }

    public float getZoomGesturesFriction() {
        return this.D;
    }

    public float getRotateGesturesFriction() {
        return this.E;
    }

    public boolean isCompassEnabled() {
        return this.F;
    }

    public boolean isScaleBarEnabled() {
        return this.G;
    }

    public boolean isZoomControlEnabled() {
        return this.H;
    }

    public boolean isIndoorLevelPickerEnabled() {
        return this.I;
    }

    public boolean isLocationButtonEnabled() {
        return this.J;
    }

    boolean c() {
        return this.K;
    }

    public boolean isLogoClickEnabled() {
        return this.L;
    }

    public int getLogoGravity() {
        return this.M;
    }

    public int[] getLogoMargin() {
        return this.N;
    }

    public int getFpsLimit() {
        return this.O;
    }

    float d() {
        return this.P;
    }

    public boolean isUseTextureView() {
        return this.Q;
    }

    boolean e() {
        return this.R;
    }

    public boolean isMsaaEnabled() {
        return this.S;
    }

    public boolean isTranslucentTextureSurface() {
        return this.T;
    }

    public boolean isZOrderMediaOverlay() {
        return this.U;
    }

    public boolean isPreserveEGLContextOnPause() {
        return this.V;
    }

    boolean f() {
        return this.W;
    }

    Class<? extends TypefaceFactory> g() {
        return this.X;
    }

    boolean h() {
        return this.Y;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NaverMapOptions naverMapOptions = (NaverMapOptions) o;
        if (Double.compare(naverMapOptions.f, this.f) != 0 || Double.compare(naverMapOptions.g, this.g) != 0 || Double.compare(naverMapOptions.h, this.h) != 0 || this.j != naverMapOptions.j || this.m != naverMapOptions.m || this.n != naverMapOptions.n || Float.compare(naverMapOptions.o, this.o) != 0 || Float.compare(naverMapOptions.p, this.p) != 0 || Float.compare(naverMapOptions.q, this.q) != 0 || Float.compare(naverMapOptions.r, this.r) != 0 || this.s != naverMapOptions.s || this.t != naverMapOptions.t || this.u != naverMapOptions.u || this.v != naverMapOptions.v || this.w != naverMapOptions.w || this.x != naverMapOptions.x || this.y != naverMapOptions.y || this.z != naverMapOptions.z || this.A != naverMapOptions.A || this.B != naverMapOptions.B || Float.compare(naverMapOptions.C, this.C) != 0 || Float.compare(naverMapOptions.D, this.D) != 0 || Float.compare(naverMapOptions.E, this.E) != 0 || this.F != naverMapOptions.F || this.G != naverMapOptions.G || this.H != naverMapOptions.H || this.I != naverMapOptions.I || this.J != naverMapOptions.J || this.K != naverMapOptions.K || this.L != naverMapOptions.L || this.M != naverMapOptions.M || this.O != naverMapOptions.O || this.P != naverMapOptions.P || this.Q != naverMapOptions.Q || this.R != naverMapOptions.R || this.S != naverMapOptions.S || this.T != naverMapOptions.T || this.U != naverMapOptions.U || this.V != naverMapOptions.V || this.W != naverMapOptions.W || this.Y != naverMapOptions.Y || !Arrays.equals(this.a, naverMapOptions.a)) {
            return false;
        }
        if (this.b == null ? naverMapOptions.b != null : !this.b.equals(naverMapOptions.b)) {
            return false;
        }
        if (this.c == null ? naverMapOptions.c != null : !this.c.equals(naverMapOptions.c)) {
            return false;
        }
        if (this.d == null ? naverMapOptions.d != null : !this.d.equals(naverMapOptions.d)) {
            return false;
        }
        if (this.e == null ? naverMapOptions.e != null : !this.e.equals(naverMapOptions.e)) {
            return false;
        }
        if (!Arrays.equals(this.i, naverMapOptions.i) || this.k != naverMapOptions.k || !this.l.equals(naverMapOptions.l) || !Arrays.equals(this.N, naverMapOptions.N)) {
            return false;
        }
        return this.X.equals(naverMapOptions.X);
    }

    public int hashCode() {
        int iHashCode = ((((((Arrays.hashCode(this.a) * 31) + (this.b != null ? this.b.hashCode() : 0)) * 31) + (this.c != null ? this.c.hashCode() : 0)) * 31) + (this.d != null ? this.d.hashCode() : 0)) * 31;
        int iHashCode2 = this.e != null ? this.e.hashCode() : 0;
        long jDoubleToLongBits = Double.doubleToLongBits(this.f);
        int i = ((iHashCode + iHashCode2) * 31) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
        long jDoubleToLongBits2 = Double.doubleToLongBits(this.g);
        int i2 = (i * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)));
        long jDoubleToLongBits3 = Double.doubleToLongBits(this.h);
        return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((i2 * 31) + ((int) (jDoubleToLongBits3 ^ (jDoubleToLongBits3 >>> 32)))) * 31) + Arrays.hashCode(this.i)) * 31) + this.j) * 31) + this.k.hashCode()) * 31) + this.l.hashCode()) * 31) + (this.m ? 1 : 0)) * 31) + (this.n ? 1 : 0)) * 31) + (this.o != 0.0f ? Float.floatToIntBits(this.o) : 0)) * 31) + (this.p != 0.0f ? Float.floatToIntBits(this.p) : 0)) * 31) + (this.q != 0.0f ? Float.floatToIntBits(this.q) : 0)) * 31) + (this.r != 0.0f ? Float.floatToIntBits(this.r) : 0)) * 31) + (this.s ? 1 : 0)) * 31) + this.t) * 31) + this.u) * 31) + this.v) * 31) + this.w) * 31) + (this.x ? 1 : 0)) * 31) + (this.y ? 1 : 0)) * 31) + (this.z ? 1 : 0)) * 31) + (this.A ? 1 : 0)) * 31) + (this.B ? 1 : 0)) * 31) + (this.C != 0.0f ? Float.floatToIntBits(this.C) : 0)) * 31) + (this.D != 0.0f ? Float.floatToIntBits(this.D) : 0)) * 31) + (this.E != 0.0f ? Float.floatToIntBits(this.E) : 0)) * 31) + (this.F ? 1 : 0)) * 31) + (this.G ? 1 : 0)) * 31) + (this.H ? 1 : 0)) * 31) + (this.I ? 1 : 0)) * 31) + (this.J ? 1 : 0)) * 31) + (this.K ? 1 : 0)) * 31) + (this.L ? 1 : 0)) * 31) + this.M) * 31) + Arrays.hashCode(this.N)) * 31) + this.O) * 31) + (this.P != 0.0f ? Float.floatToIntBits(this.P) : 0)) * 31) + (this.Q ? 1 : 0)) * 31) + (this.R ? 1 : 0)) * 31) + (this.S ? 1 : 0)) * 31) + (this.T ? 1 : 0)) * 31) + (this.U ? 1 : 0)) * 31) + (this.V ? 1 : 0)) * 31) + (this.W ? 1 : 0)) * 31) + this.X.hashCode()) * 31) + (this.Y ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(this.a);
        parcel.writeString(this.b);
        parcel.writeSerializable(this.c);
        parcel.writeParcelable(this.d, i);
        parcel.writeParcelable(this.e, i);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
        parcel.writeDouble(this.h);
        parcel.writeIntArray(this.i);
        parcel.writeInt(this.j);
        parcel.writeInt(this.k.ordinal());
        parcel.writeSerializable(this.l);
        parcel.writeByte(this.m ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.n ? (byte) 1 : (byte) 0);
        parcel.writeFloat(this.o);
        parcel.writeFloat(this.p);
        parcel.writeFloat(this.q);
        parcel.writeFloat(this.r);
        parcel.writeByte(this.s ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.t);
        parcel.writeInt(this.u);
        parcel.writeInt(this.v);
        parcel.writeInt(this.w);
        parcel.writeByte(this.x ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.y ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.z ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.A ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.B ? (byte) 1 : (byte) 0);
        parcel.writeFloat(this.C);
        parcel.writeFloat(this.D);
        parcel.writeFloat(this.E);
        parcel.writeByte(this.F ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.G ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.H ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.I ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.J ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.K ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.L ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.M);
        parcel.writeIntArray(this.N);
        parcel.writeInt(this.O);
        parcel.writeFloat(this.P);
        parcel.writeByte(this.Q ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.R ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.S ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.T ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.U ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.V ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.W ? (byte) 1 : (byte) 0);
        parcel.writeSerializable(this.X);
        parcel.writeByte(this.Y ? (byte) 1 : (byte) 0);
    }

    protected NaverMapOptions(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        boolean z17;
        boolean z18;
        boolean z19;
        boolean z20;
        boolean z21;
        boolean z22;
        this.f = 0.0d;
        this.g = 21.0d;
        this.h = 63.0d;
        this.i = new int[4];
        this.j = 200;
        this.k = NaverMap.MapType.Basic;
        this.l = new HashSet<>(Collections.singleton(NaverMap.LAYER_GROUP_BUILDING));
        this.m = false;
        this.n = false;
        this.o = 1.0f;
        this.p = 0.0f;
        this.q = 1.0f;
        this.r = 1.0f;
        this.s = false;
        this.t = -1;
        this.u = -789775;
        this.v = NaverMap.DEFAULT_BACKGROUND_DRWABLE_LIGHT;
        this.w = -1;
        this.x = true;
        this.y = true;
        this.z = true;
        this.A = true;
        this.B = true;
        this.C = 0.088f;
        this.D = 0.12375f;
        this.E = 0.19333f;
        this.F = true;
        this.G = true;
        this.H = true;
        this.I = true;
        this.J = false;
        this.K = true;
        this.L = true;
        this.M = 0;
        this.P = 1.0f;
        this.Q = false;
        this.R = false;
        this.S = false;
        this.T = false;
        this.U = false;
        this.V = true;
        this.W = false;
        this.X = DefaultTypefaceFactory.class;
        this.Y = false;
        this.a = in.createStringArray();
        this.b = in.readString();
        this.c = (Locale) in.readSerializable();
        this.d = (CameraPosition) in.readParcelable(CameraPosition.class.getClassLoader());
        this.e = (LatLngBounds) in.readParcelable(LatLngBounds.class.getClassLoader());
        this.f = in.readDouble();
        this.g = in.readDouble();
        this.h = in.readDouble();
        this.i = in.createIntArray();
        this.j = in.readInt();
        int i = in.readInt();
        this.k = i == -1 ? null : NaverMap.MapType.values()[i];
        this.l = (HashSet) in.readSerializable();
        if (in.readByte() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.m = z;
        if (in.readByte() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.n = z2;
        this.o = in.readFloat();
        this.p = in.readFloat();
        this.q = in.readFloat();
        this.r = in.readFloat();
        if (in.readByte() == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.s = z3;
        this.t = in.readInt();
        this.u = in.readInt();
        this.v = in.readInt();
        this.w = in.readInt();
        if (in.readByte() == 0) {
            z4 = false;
        } else {
            z4 = true;
        }
        this.x = z4;
        if (in.readByte() == 0) {
            z5 = false;
        } else {
            z5 = true;
        }
        this.y = z5;
        if (in.readByte() == 0) {
            z6 = false;
        } else {
            z6 = true;
        }
        this.z = z6;
        if (in.readByte() == 0) {
            z7 = false;
        } else {
            z7 = true;
        }
        this.A = z7;
        if (in.readByte() == 0) {
            z8 = false;
        } else {
            z8 = true;
        }
        this.B = z8;
        this.C = in.readFloat();
        this.D = in.readFloat();
        this.E = in.readFloat();
        if (in.readByte() == 0) {
            z9 = false;
        } else {
            z9 = true;
        }
        this.F = z9;
        if (in.readByte() == 0) {
            z10 = false;
        } else {
            z10 = true;
        }
        this.G = z10;
        if (in.readByte() == 0) {
            z11 = false;
        } else {
            z11 = true;
        }
        this.H = z11;
        if (in.readByte() == 0) {
            z12 = false;
        } else {
            z12 = true;
        }
        this.I = z12;
        if (in.readByte() == 0) {
            z13 = false;
        } else {
            z13 = true;
        }
        this.J = z13;
        if (in.readByte() == 0) {
            z14 = false;
        } else {
            z14 = true;
        }
        this.K = z14;
        if (in.readByte() == 0) {
            z15 = false;
        } else {
            z15 = true;
        }
        this.L = z15;
        this.M = in.readInt();
        this.N = in.createIntArray();
        this.O = in.readInt();
        this.P = in.readFloat();
        if (in.readByte() == 0) {
            z16 = false;
        } else {
            z16 = true;
        }
        this.Q = z16;
        if (in.readByte() == 0) {
            z17 = false;
        } else {
            z17 = true;
        }
        this.R = z17;
        if (in.readByte() == 0) {
            z18 = false;
        } else {
            z18 = true;
        }
        this.S = z18;
        if (in.readByte() == 0) {
            z19 = false;
        } else {
            z19 = true;
        }
        this.T = z19;
        if (in.readByte() == 0) {
            z20 = false;
        } else {
            z20 = true;
        }
        this.U = z20;
        if (in.readByte() == 0) {
            z21 = false;
        } else {
            z21 = true;
        }
        this.V = z21;
        if (in.readByte() == 0) {
            z22 = false;
        } else {
            z22 = true;
        }
        this.W = z22;
        this.X = (Class) in.readSerializable();
        this.Y = in.readByte() != 0;
    }
}
