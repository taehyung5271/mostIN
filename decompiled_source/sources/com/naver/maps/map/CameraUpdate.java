package com.naver.maps.map;

import android.graphics.PointF;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.util.CameraUtils;

/* loaded from: classes.dex */
public abstract class CameraUpdate {
    public static final int DEFAULT_ANIMATION_DURATION = -1;
    public static final int REASON_CONTENT_PADDING = -4;
    public static final int REASON_CONTROL = -2;
    public static final int REASON_DEVELOPER = 0;
    public static final int REASON_GESTURE = -1;
    public static final int REASON_LOCATION = -3;
    private static final PointF a = new PointF(0.5f, 0.5f);
    private PointF b;
    private PointF c;
    private CameraAnimation d;
    private long e;
    private int f;
    private FinishCallback g;
    private CancelCallback h;

    public interface CancelCallback {
        void onCameraUpdateCancel();
    }

    public interface FinishCallback {
        void onCameraUpdateFinish();
    }

    abstract c a(NaverMap naverMap);

    static final class c {
        public final LatLng a;
        public final double b;
        public final double c;
        public final double d;

        c(LatLng latLng, double d) {
            this(latLng, d, 0.0d, 0.0d);
        }

        c(LatLng latLng, double d, double d2, double d3) {
            this.a = latLng;
            this.b = d;
            this.c = d2;
            this.d = d3;
        }
    }

    private static class b extends CameraUpdate {
        private final CameraUpdateParams a;

        private b(CameraUpdateParams cameraUpdateParams) {
            super();
            this.a = cameraUpdateParams;
        }

        @Override // com.naver.maps.map.CameraUpdate
        c a(NaverMap naverMap) {
            return this.a.a(naverMap, b(naverMap));
        }

        @Override // com.naver.maps.map.CameraUpdate
        boolean a() {
            return !this.a.a();
        }
    }

    private static class d extends CameraUpdate {
        private final CameraPosition a;

        private d(CameraPosition cameraPosition) {
            super();
            this.a = cameraPosition;
        }

        @Override // com.naver.maps.map.CameraUpdate
        c a(NaverMap naverMap) {
            return new c(this.a.target, this.a.zoom, this.a.tilt, a(a(naverMap.getCameraPosition().bearing), a(this.a.bearing)));
        }
    }

    private static class a extends CameraUpdate {
        private static final double a = Math.log(2.0d);
        private final LatLngBounds b;
        private final int c;
        private final int d;
        private final int e;
        private final int f;

        private a(LatLngBounds latLngBounds, int i, int i2, int i3, int i4) {
            super();
            this.b = latLngBounds;
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        @Override // com.naver.maps.map.CameraUpdate
        c a(NaverMap naverMap) {
            double fittableZoom = CameraUtils.getFittableZoom(naverMap, this.b, this.c, this.d, this.e, this.f);
            PointF pointFA = naverMap.getProjection().a(this.b.getCenter(), fittableZoom);
            pointFA.offset((this.e - this.c) / 2.0f, (this.f - this.d) / 2.0f);
            return new c(naverMap.getProjection().a(pointFA, fittableZoom), fittableZoom);
        }
    }

    static double a(double d2) {
        double dWrap = MathUtils.wrap(d2, -180.0d, 180.0d);
        if (dWrap == -180.0d) {
            return 180.0d;
        }
        return dWrap;
    }

    static double a(double d2, double d3) {
        double d4 = d3 - d2;
        if (d4 > 180.0d) {
            return d3 - 360.0d;
        }
        if (d4 < -180.0d) {
            return d3 + 360.0d;
        }
        return d3;
    }

    public static CameraUpdate withParams(CameraUpdateParams params) {
        return new b(params);
    }

    public static CameraUpdate toCameraPosition(CameraPosition position) {
        return new d(position);
    }

    public static CameraUpdate scrollTo(LatLng target) {
        return withParams(new CameraUpdateParams().scrollTo(target));
    }

    public static CameraUpdate scrollBy(PointF delta) {
        return withParams(new CameraUpdateParams().scrollBy(delta));
    }

    public static CameraUpdate zoomTo(double zoom) {
        return withParams(new CameraUpdateParams().zoomTo(zoom));
    }

    public static CameraUpdate zoomBy(double delta) {
        return withParams(new CameraUpdateParams().zoomBy(delta));
    }

    public static CameraUpdate zoomIn() {
        return withParams(new CameraUpdateParams().zoomIn());
    }

    public static CameraUpdate zoomOut() {
        return withParams(new CameraUpdateParams().zoomOut());
    }

    public static CameraUpdate scrollAndZoomTo(LatLng target, double zoom) {
        return withParams(new CameraUpdateParams().scrollTo(target).zoomTo(zoom));
    }

    public static CameraUpdate fitBounds(LatLngBounds bounds) {
        return fitBounds(bounds, 0);
    }

    public static CameraUpdate fitBounds(LatLngBounds bounds, int padding) {
        return fitBounds(bounds, padding, padding, padding, padding);
    }

    public static CameraUpdate fitBounds(LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        return new a(bounds, paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    private CameraUpdate() {
        this.b = a;
        this.d = CameraAnimation.None;
        this.f = 0;
    }

    public CameraUpdate pivot(PointF pivot) {
        this.b = pivot;
        this.c = null;
        return this;
    }

    CameraUpdate a(PointF pointF) {
        this.c = pointF;
        this.b = null;
        return this;
    }

    public CameraUpdate animate(CameraAnimation animation) {
        return animate(animation, -1L);
    }

    public CameraUpdate animate(CameraAnimation animation, long duration) {
        this.d = animation;
        this.e = duration;
        return this;
    }

    public CameraUpdate reason(int reason) {
        this.f = reason;
        return this;
    }

    public CameraUpdate finishCallback(FinishCallback callback) {
        this.g = callback;
        return this;
    }

    public CameraUpdate cancelCallback(CancelCallback callback) {
        this.h = callback;
        return this;
    }

    boolean a() {
        return false;
    }

    CameraAnimation b() {
        return this.d;
    }

    long a(long j) {
        return this.e == -1 ? j : this.e;
    }

    int c() {
        return this.f;
    }

    FinishCallback d() {
        return this.g;
    }

    CancelCallback e() {
        return this.h;
    }

    PointF b(NaverMap naverMap) {
        if (this.c != null) {
            return this.c;
        }
        if (this.b != null && !a.equals(this.b)) {
            int[] contentPadding = naverMap.getContentPadding();
            return new PointF((((naverMap.getWidth() - contentPadding[0]) - contentPadding[2]) * this.b.x) + contentPadding[0], (((naverMap.getHeight() - contentPadding[1]) - contentPadding[3]) * this.b.y) + contentPadding[1]);
        }
        return null;
    }
}
