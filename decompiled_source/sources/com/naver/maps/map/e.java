package com.naver.maps.map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.core.content.res.ResourcesCompat;
import com.naver.maps.map.indoor.IndoorRegion;
import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.renderer.MapRenderer;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
final class e {
    private final Handler a = new Handler(Looper.getMainLooper());
    private final List<OnMapReadyCallback> b = new CopyOnWriteArrayList();
    private final Context c;
    private final NaverMapOptions d;
    private final MapControlsView e;
    private final OnMapReadyCallback f;
    private MapRenderer g;
    private NativeMapView h;
    private int i;
    private Bundle j;
    private Bundle k;
    private NaverMap l;

    e(Context context, NaverMapOptions naverMapOptions, MapRenderer mapRenderer, MapControlsView mapControlsView, OnMapReadyCallback onMapReadyCallback) {
        this.c = context;
        this.d = naverMapOptions;
        this.g = mapRenderer;
        this.e = mapControlsView;
        this.f = onMapReadyCallback;
        this.i = naverMapOptions.getFpsLimit();
        mapRenderer.a(this.i);
        this.h = new NativeMapView(context, this, mapRenderer, naverMapOptions.getLocale(), naverMapOptions.d());
    }

    MapRenderer a() {
        return this.g;
    }

    NaverMap b() {
        return this.l;
    }

    void a(Runnable runnable) {
        this.a.post(runnable);
    }

    void a(int i, int i2) {
        if (this.h != null) {
            this.h.a(i, i2);
        }
    }

    void c() {
        if (this.h == null) {
            return;
        }
        this.h.d();
        if (this.l != null) {
            return;
        }
        this.l = new NaverMap(this.c, this.h, this.e);
        if (this.f != null) {
            this.f.onMapReady(this.l);
        }
        this.h.a(com.naver.maps.map.internal.net.b.a(this.c).c());
        if (this.j == null) {
            this.l.a(this.d);
        } else {
            this.l.b(this.j);
        }
        this.l.a();
        this.l.c();
        Iterator<OnMapReadyCallback> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().onMapReady(this.l);
        }
        this.b.clear();
        this.l.d();
    }

    void a(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey("MapViewDelegate01")) {
                a(bundle.getInt("MapViewDelegate01"));
            }
            if (bundle.getBoolean("MapViewDelegate00")) {
                this.j = bundle;
            }
        }
    }

    void d() {
        com.naver.maps.map.internal.net.b.a(this.c).a();
        FileSource.a(this.c).a();
        if (this.l != null) {
            this.l.a();
        }
        if (this.g != null) {
            this.g.d();
        }
    }

    void e() {
    }

    void f() {
    }

    void g() {
        if (this.g != null) {
            this.g.c();
        }
        if (this.l != null) {
            this.l.b();
        }
        com.naver.maps.map.internal.net.b.a(this.c).b();
        FileSource.a(this.c).b();
    }

    void h() {
        this.b.clear();
        if (this.l != null) {
            this.l.setLocationTrackingMode(LocationTrackingMode.None);
        }
        if (this.h != null) {
            if (this.l != null) {
                this.h.e();
            }
            this.h = null;
        }
        if (this.g != null) {
            this.g.e();
            this.g = null;
        }
    }

    void b(Bundle bundle) {
        if (this.k != null) {
            bundle.putAll(this.k);
            this.k = null;
            return;
        }
        bundle.putInt("MapViewDelegate01", this.i);
        if (this.l != null && !this.l.isDestroyed()) {
            bundle.putBoolean("MapViewDelegate00", true);
            this.l.a(bundle);
        }
    }

    void i() {
        if (this.h != null && this.l != null) {
            this.h.m();
        }
    }

    void a(OnMapReadyCallback onMapReadyCallback) {
        if (onMapReadyCallback == null) {
            return;
        }
        if (this.l != null) {
            onMapReadyCallback.onMapReady(this.l);
        } else {
            this.b.add(onMapReadyCallback);
        }
    }

    private void a(final RuntimeException runtimeException) {
        a(new Runnable() { // from class: com.naver.maps.map.e.1
            @Override // java.lang.Runnable
            public void run() {
                throw runtimeException;
            }
        });
    }

    void j() {
        if (this.l == null) {
            return;
        }
        try {
            this.l.h();
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void a(boolean z, boolean z2) {
        if (this.l == null) {
            return;
        }
        try {
            this.l.a(z, z2);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void k() {
        if (this.l == null) {
            return;
        }
        try {
            this.l.f().a();
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void a(String str) {
        if (this.l == null) {
            return;
        }
        try {
            this.l.f().a(str);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void b(int i, int i2) {
        if (this.l == null) {
            return;
        }
        try {
            this.l.j().a(i, i2);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void a(IndoorRegion indoorRegion) {
        if (this.l == null) {
            return;
        }
        try {
            this.l.k().a(indoorRegion);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    void a(Bitmap bitmap, boolean z) throws Resources.NotFoundException {
        if (this.l == null) {
            return;
        }
        Bitmap bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmapCopy);
        if (z && this.e != null) {
            this.e.draw(canvas);
        } else {
            Resources resources = this.c.getResources();
            Drawable drawable = ResourcesCompat.getDrawable(resources, this.l.isDark() ? R.drawable.navermap_naver_logo_dark : R.drawable.navermap_naver_logo_light, this.c.getTheme());
            if (drawable != null) {
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.navermap_default_logo_margin_start);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.navermap_default_logo_margin_bottom);
                drawable.setBounds(dimensionPixelSize, (bitmapCopy.getHeight() - dimensionPixelSize2) - drawable.getIntrinsicHeight(), drawable.getIntrinsicWidth() + dimensionPixelSize, bitmapCopy.getHeight() - dimensionPixelSize2);
                drawable.draw(canvas);
            }
        }
        try {
            this.l.a(bitmapCopy);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    int l() {
        return this.i;
    }

    void a(int i) {
        this.i = i;
        final MapRenderer mapRenderer = this.g;
        if (mapRenderer == null) {
            return;
        }
        mapRenderer.queueEvent(new Runnable() { // from class: com.naver.maps.map.e.2
            @Override // java.lang.Runnable
            public void run() {
                mapRenderer.a(e.this.i);
            }
        });
    }
}
