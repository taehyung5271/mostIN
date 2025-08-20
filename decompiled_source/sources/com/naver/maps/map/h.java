package com.naver.maps.map;

import android.graphics.PointF;
import android.os.Bundle;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
final class h {
    private final NativeMapView a;
    private final List<NaverMap.OnCameraChangeListener> b = new CopyOnWriteArrayList();
    private final List<NaverMap.OnCameraIdleListener> c = new CopyOnWriteArrayList();
    private final int[] d = new int[4];
    private int e = 200;
    private CameraPosition f;
    private LatLngBounds g;
    private LatLng[] h;
    private LatLngBounds i;
    private LatLng[] j;
    private long[] k;
    private CameraUpdate.FinishCallback l;
    private CameraUpdate.CancelCallback m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;

    private enum a {
        Changing(false, false, true),
        Changed(true, true, false),
        ChangedAnimated(true, true, true),
        ChangeCancelled(true, false, false);

        public final boolean e;
        public final boolean f;
        public final boolean g;

        a(boolean z, boolean z2, boolean z3) {
            this.e = z;
            this.f = z2;
            this.g = z3;
        }
    }

    h(NativeMapView nativeMapView) {
        this.a = nativeMapView;
    }

    void a() {
        b(0, false);
        o();
    }

    void a(boolean z) {
        this.p = z;
        p();
    }

    boolean b() {
        return this.q;
    }

    void b(boolean z) {
        this.q = z;
        p();
    }

    private void b(int i, boolean z) {
        q();
        Iterator<NaverMap.OnCameraChangeListener> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().onCameraChange(i, z);
        }
    }

    private void o() {
        Iterator<NaverMap.OnCameraIdleListener> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().onCameraIdle();
        }
    }

    private void p() {
        if (this.n || this.p || this.q || !this.o) {
            return;
        }
        this.o = false;
        o();
    }

    private void q() {
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
    }

    CameraPosition c() {
        if (this.f == null) {
            this.f = this.a.n();
        }
        return this.f;
    }

    LatLngBounds d() {
        if (this.g == null) {
            this.g = this.a.o();
        }
        return this.g;
    }

    LatLng[] e() {
        if (this.h == null) {
            this.h = this.a.p();
        }
        return this.h;
    }

    LatLngBounds f() {
        if (this.i == null) {
            this.i = this.a.q();
        }
        return this.i;
    }

    LatLng[] g() {
        if (this.j == null) {
            this.j = this.a.r();
        }
        return this.j;
    }

    long[] h() {
        if (this.k == null) {
            this.k = this.a.s();
        }
        return this.k;
    }

    void a(NaverMap naverMap, CameraUpdate cameraUpdate) {
        if (this.n) {
            a(cameraUpdate.c(), true);
        }
        CameraUpdate.c cVarA = cameraUpdate.a(naverMap);
        PointF pointFB = cameraUpdate.b(naverMap);
        this.l = cameraUpdate.d();
        this.m = cameraUpdate.e();
        this.n = true;
        this.o = true;
        this.a.a(cVarA.a, cVarA.b, cVarA.c, cVarA.d, pointFB, cameraUpdate.c(), cameraUpdate.b(), cameraUpdate.a(this.e), cameraUpdate.a());
    }

    void a(int i, boolean z) {
        this.a.a(i);
        this.l = null;
        if (this.m != null) {
            CameraUpdate.CancelCallback cancelCallback = this.m;
            this.m = null;
            cancelCallback.onCameraUpdateCancel();
        }
        if (!z) {
            p();
        }
    }

    void a(NaverMap.OnCameraChangeListener onCameraChangeListener) {
        this.b.add(onCameraChangeListener);
    }

    void b(NaverMap.OnCameraChangeListener onCameraChangeListener) {
        this.b.remove(onCameraChangeListener);
    }

    void a(NaverMap.OnCameraIdleListener onCameraIdleListener) {
        this.c.add(onCameraIdleListener);
    }

    void b(NaverMap.OnCameraIdleListener onCameraIdleListener) {
        this.c.remove(onCameraIdleListener);
    }

    LatLngBounds i() {
        return this.a.i();
    }

    void a(LatLngBounds latLngBounds) {
        this.a.a(latLngBounds);
    }

    void a(double d) {
        this.a.a(MathUtils.clamp(d, 0.0d, 21.0d));
    }

    double j() {
        return this.a.j();
    }

    void b(double d) {
        this.a.b(MathUtils.clamp(d, 0.0d, 21.0d));
    }

    double k() {
        return this.a.k();
    }

    void c(double d) {
        this.a.c(MathUtils.clamp(d, 0.0d, 63.0d));
    }

    double l() {
        return this.a.l();
    }

    int[] m() {
        return new int[]{this.d[0], this.d[1], this.d[2], this.d[3]};
    }

    void a(int i, int i2, int i3, int i4, boolean z, int i5) {
        this.d[0] = i;
        this.d[1] = i2;
        this.d[2] = i3;
        this.d[3] = i4;
        this.a.a(this.d, z);
        if (z) {
            q();
            return;
        }
        b(i5, false);
        this.o = true;
        p();
    }

    int n() {
        return this.e;
    }

    void a(int i) {
        this.e = i;
    }

    void a(int i, int i2) {
        a aVar = a.values()[i];
        b(i2, aVar.g);
        if (aVar.e) {
            this.n = false;
        } else {
            this.n = true;
            this.o = true;
        }
        if (aVar.f) {
            this.m = null;
            if (this.l != null) {
                CameraUpdate.FinishCallback finishCallback = this.l;
                this.l = null;
                finishCallback.onCameraUpdateFinish();
            }
            p();
        }
    }

    void a(NaverMap naverMap, NaverMapOptions naverMapOptions) {
        a(naverMapOptions.getExtent());
        a(naverMapOptions.getMinZoom());
        b(naverMapOptions.getMaxZoom());
        c(naverMapOptions.getMaxTilt());
        int[] contentPadding = naverMapOptions.getContentPadding();
        naverMap.setContentPadding(contentPadding[0], contentPadding[1], contentPadding[2], contentPadding[3]);
        a(naverMapOptions.getDefaultCameraAnimationDuration());
        CameraPosition cameraPosition = naverMapOptions.getCameraPosition();
        if (cameraPosition != null && cameraPosition.target.isValid()) {
            naverMap.setCameraPosition(cameraPosition);
        } else {
            naverMap.setCameraPosition(NaverMap.DEFAULT_CAMERA_POSITION);
        }
    }

    void a(NaverMap naverMap, Bundle bundle) {
        bundle.putParcelable("Transform00", naverMap.getCameraPosition());
        bundle.putParcelable("Transform01", i());
        bundle.putDouble("Transform02", j());
        bundle.putDouble("Transform03", k());
        bundle.putIntArray("Transform04", this.d);
        bundle.putInt("Transform05", n());
        bundle.putDouble("Transform06", l());
    }

    void b(NaverMap naverMap, Bundle bundle) {
        CameraPosition cameraPosition = (CameraPosition) bundle.getParcelable("Transform00");
        if (cameraPosition != null) {
            naverMap.setCameraPosition(cameraPosition);
        }
        a((LatLngBounds) bundle.getParcelable("Transform01"));
        a(bundle.getDouble("Transform02"));
        b(bundle.getDouble("Transform03"));
        int[] intArray = bundle.getIntArray("Transform04");
        if (intArray != null) {
            naverMap.setContentPadding(intArray[0], intArray[1], intArray[2], intArray[3]);
        }
        a(bundle.getInt("Transform05"));
        c(bundle.getDouble("Transform06"));
    }
}
