package com.naver.maps.map;

import android.content.Context;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class UiSettings {
    private final float a;
    private final MapControlsView b;
    private boolean o;
    private boolean p;
    private int c = 0;
    private boolean d = true;
    private boolean e = true;
    private boolean f = true;
    private boolean g = true;
    private boolean h = true;
    private float i = 0.088f;
    private float j = 0.12375f;
    private float k = 0.19333f;
    private boolean l = true;
    private boolean m = true;
    private boolean n = true;
    private boolean q = true;
    private boolean r = true;

    UiSettings(Context context, MapControlsView controls) {
        this.a = context.getResources().getDisplayMetrics().density;
        this.b = controls;
    }

    public int getPickTolerance() {
        return this.c;
    }

    public void setPickTolerance(int tolerance) {
        this.c = tolerance;
    }

    public boolean isScrollGesturesEnabled() {
        return this.d;
    }

    public void setScrollGesturesEnabled(boolean enabled) {
        this.d = enabled;
    }

    public boolean isZoomGesturesEnabled() {
        return this.e;
    }

    public void setZoomGesturesEnabled(boolean enabled) {
        this.e = enabled;
    }

    public boolean isTiltGesturesEnabled() {
        return this.f;
    }

    public void setTiltGesturesEnabled(boolean enabled) {
        this.f = enabled;
    }

    public boolean isRotateGesturesEnabled() {
        return this.g;
    }

    public void setRotateGesturesEnabled(boolean enabled) {
        this.g = enabled;
    }

    public boolean isStopGesturesEnabled() {
        return this.h;
    }

    public void setStopGesturesEnabled(boolean enabled) {
        this.h = enabled;
    }

    public void setAllGesturesEnabled(boolean enabled) {
        setScrollGesturesEnabled(enabled);
        setRotateGesturesEnabled(enabled);
        setTiltGesturesEnabled(enabled);
        setZoomGesturesEnabled(enabled);
        setStopGesturesEnabled(enabled);
    }

    public float getScrollGesturesFriction() {
        return this.i;
    }

    public void setScrollGesturesFriction(float friction) {
        this.i = friction;
    }

    public float getZoomGesturesFriction() {
        return this.j;
    }

    public void setZoomGesturesFriction(float friction) {
        this.j = friction;
    }

    public float getRotateGesturesFriction() {
        return this.k;
    }

    public void setRotateGesturesFriction(float friction) {
        this.k = friction;
    }

    public boolean isCompassEnabled() {
        return this.l;
    }

    public void setCompassEnabled(boolean enabled) {
        this.l = enabled;
        if (this.b != null) {
            this.b.a(enabled);
        }
    }

    public boolean isScaleBarEnabled() {
        return this.m;
    }

    public void setScaleBarEnabled(boolean enabled) {
        this.m = enabled;
        if (this.b != null) {
            this.b.b(enabled);
        }
    }

    public boolean isZoomControlEnabled() {
        return this.n;
    }

    public void setZoomControlEnabled(boolean enabled) {
        this.n = enabled;
        if (this.b != null) {
            this.b.c(enabled);
        }
    }

    public boolean isIndoorLevelPickerEnabled() {
        return this.o;
    }

    public void setIndoorLevelPickerEnabled(boolean enabled) {
        this.o = enabled;
        if (this.b != null) {
            this.b.d(enabled);
        }
    }

    public boolean isLocationButtonEnabled() {
        return this.p;
    }

    public void setLocationButtonEnabled(boolean enabled) {
        this.p = enabled;
        if (this.b != null) {
            this.b.e(enabled);
        }
    }

    private void a(boolean z) {
        this.q = z;
        if (this.b != null) {
            this.b.f(z);
        }
    }

    public boolean isLogoClickEnabled() {
        return this.r;
    }

    public void setLogoClickEnabled(boolean enabled) {
        this.r = enabled;
        if (this.b != null) {
            this.b.g(enabled);
        }
    }

    public int getLogoGravity() {
        if (this.b != null) {
            return this.b.a();
        }
        return 0;
    }

    public void setLogoGravity(int gravity) {
        if (this.b != null) {
            this.b.a(gravity);
        }
    }

    public int[] getLogoMargin() {
        if (this.b != null) {
            return this.b.b();
        }
        return new int[]{0, 0, 0, 0};
    }

    public void setLogoMargin(int start, int top, int end, int bottom) {
        if (this.b != null) {
            this.b.a(start, top, end, bottom);
        }
    }

    void a(int i, int i2, int i3, int i4) {
        if (this.b != null) {
            this.b.setPadding(i, i2, i3, i4);
        }
    }

    void a(NaverMapOptions naverMapOptions) {
        int pickTolerance = naverMapOptions.getPickTolerance();
        if (pickTolerance < 0) {
            pickTolerance = Math.round(this.a * 2.0f);
        }
        setPickTolerance(pickTolerance);
        setScrollGesturesEnabled(naverMapOptions.isScrollGesturesEnabled());
        setZoomGesturesEnabled(naverMapOptions.isZoomGesturesEnabled());
        setTiltGesturesEnabled(naverMapOptions.isTiltGesturesEnabled());
        setRotateGesturesEnabled(naverMapOptions.isRotateGesturesEnabled());
        setStopGesturesEnabled(naverMapOptions.isStopGesturesEnabled());
        setScrollGesturesFriction(naverMapOptions.getScrollGesturesFriction());
        setZoomGesturesFriction(naverMapOptions.getZoomGesturesFriction());
        setRotateGesturesFriction(naverMapOptions.getRotateGesturesFriction());
        setCompassEnabled(naverMapOptions.isCompassEnabled());
        setScaleBarEnabled(naverMapOptions.isScaleBarEnabled());
        setZoomControlEnabled(naverMapOptions.isZoomControlEnabled());
        setIndoorLevelPickerEnabled(naverMapOptions.isIndoorLevelPickerEnabled());
        setLocationButtonEnabled(naverMapOptions.isLocationButtonEnabled());
        a(naverMapOptions.c());
        setLogoClickEnabled(naverMapOptions.isLogoClickEnabled());
        int logoGravity = naverMapOptions.getLogoGravity();
        if (logoGravity != 0) {
            setLogoGravity(logoGravity);
        }
        int[] logoMargin = naverMapOptions.getLogoMargin();
        if (logoMargin != null) {
            setLogoMargin(logoMargin[0], logoMargin[1], logoMargin[2], logoMargin[3]);
        }
    }

    void a(Bundle bundle) {
        bundle.putInt("UiSettings00", this.c);
        bundle.putBoolean("UiSettings01", this.d);
        bundle.putBoolean("UiSettings02", this.e);
        bundle.putBoolean("UiSettings03", this.f);
        bundle.putBoolean("UiSettings04", this.g);
        bundle.putBoolean("UiSettings05", this.h);
        bundle.putFloat("UiSettings06", this.i);
        bundle.putFloat("UiSettings07", this.j);
        bundle.putFloat("UiSettings08", this.k);
        bundle.putBoolean("UiSettings09", this.l);
        bundle.putBoolean("UiSettings10", this.m);
        bundle.putBoolean("UiSettings11", this.n);
        bundle.putBoolean("UiSettings12", this.o);
        bundle.putBoolean("UiSettings13", this.p);
        bundle.putBoolean("UiSettings14", this.q);
        bundle.putBoolean("UiSettings15", this.r);
        bundle.putInt("UiSettings16", getLogoGravity());
        bundle.putIntArray("UiSettings17", getLogoMargin());
    }

    void b(Bundle bundle) {
        setPickTolerance(bundle.getInt("UiSettings00"));
        setScrollGesturesEnabled(bundle.getBoolean("UiSettings01"));
        setZoomGesturesEnabled(bundle.getBoolean("UiSettings02"));
        setTiltGesturesEnabled(bundle.getBoolean("UiSettings03"));
        setRotateGesturesEnabled(bundle.getBoolean("UiSettings04"));
        setStopGesturesEnabled(bundle.getBoolean("UiSettings05"));
        setScrollGesturesFriction(bundle.getFloat("UiSettings06"));
        setZoomGesturesFriction(bundle.getFloat("UiSettings07"));
        setRotateGesturesFriction(bundle.getFloat("UiSettings08"));
        setCompassEnabled(bundle.getBoolean("UiSettings09"));
        setScaleBarEnabled(bundle.getBoolean("UiSettings10"));
        setZoomControlEnabled(bundle.getBoolean("UiSettings11"));
        setIndoorLevelPickerEnabled(bundle.getBoolean("UiSettings12"));
        setLocationButtonEnabled(bundle.getBoolean("UiSettings13"));
        a(bundle.getBoolean("UiSettings14"));
        setLogoClickEnabled(bundle.getBoolean("UiSettings15"));
        setLogoGravity(bundle.getInt("UiSettings16"));
        int[] intArray = bundle.getIntArray("UiSettings17");
        if (intArray != null) {
            setLogoMargin(intArray[0], intArray[1], intArray[2], intArray[3]);
        }
    }
}
