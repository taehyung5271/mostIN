package com.naver.maps.map;

import android.graphics.PointF;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;

/* loaded from: classes.dex */
public final class CameraUpdateParams {
    private LatLng a;
    private PointF b;
    private double c = Double.NaN;
    private double d = Double.NaN;
    private double e = Double.NaN;
    private double f = Double.NaN;
    private double g = Double.NaN;
    private double h = Double.NaN;

    private static double a(double d, double d2, double d3) {
        if (!Double.isNaN(d2)) {
            return d2;
        }
        if (!Double.isNaN(d3)) {
            return d + d3;
        }
        return d;
    }

    public CameraUpdateParams scrollTo(LatLng target) {
        this.a = target;
        this.b = null;
        return this;
    }

    public CameraUpdateParams scrollBy(PointF delta) {
        this.a = null;
        this.b = delta;
        return this;
    }

    public CameraUpdateParams zoomTo(double zoom) {
        this.c = zoom;
        this.d = Double.NaN;
        return this;
    }

    public CameraUpdateParams zoomBy(double delta) {
        this.d = delta;
        this.c = Double.NaN;
        return this;
    }

    public CameraUpdateParams zoomIn() {
        return zoomBy(1.0d);
    }

    public CameraUpdateParams zoomOut() {
        return zoomBy(-1.0d);
    }

    public CameraUpdateParams tiltTo(double tilt) {
        this.e = tilt;
        this.f = Double.NaN;
        return this;
    }

    public CameraUpdateParams tiltBy(double delta) {
        this.e = Double.NaN;
        this.f = delta;
        return this;
    }

    public CameraUpdateParams rotateTo(double bearing) {
        this.g = bearing;
        this.h = Double.NaN;
        return this;
    }

    public CameraUpdateParams rotateBy(double delta) {
        this.g = Double.NaN;
        this.h = delta;
        return this;
    }

    boolean a() {
        return (this.a == null && this.b == null) ? false : true;
    }

    CameraUpdate.c a(NaverMap naverMap, PointF pointF) {
        LatLng latLngFromScreenLocationAt;
        PointF pointF2;
        double dA;
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        if (this.a != null) {
            latLngFromScreenLocationAt = this.a;
        } else if (this.b != null) {
            if (pointF == null) {
                int[] contentPadding = naverMap.getContentPadding();
                pointF2 = new PointF((((naverMap.getWidth() + contentPadding[0]) - contentPadding[2]) / 2.0f) - this.b.x, (((naverMap.getHeight() + contentPadding[1]) - contentPadding[3]) / 2.0f) - this.b.y);
            } else {
                pointF2 = new PointF(pointF.x - this.b.x, pointF.y - this.b.y);
            }
            latLngFromScreenLocationAt = naverMap.getProjection().fromScreenLocationAt(pointF2, Double.NaN, Double.NaN, Double.NaN, false);
        } else {
            latLngFromScreenLocationAt = cameraPosition.target;
        }
        double dA2 = CameraUpdate.a(cameraPosition.bearing);
        if (!Double.isNaN(this.g)) {
            dA = CameraUpdate.a(dA2, CameraUpdate.a(this.g));
        } else if (!Double.isNaN(this.h)) {
            dA = dA2 + this.h;
        } else {
            dA = dA2;
        }
        return new CameraUpdate.c(latLngFromScreenLocationAt, a(cameraPosition.zoom, this.c, this.d), a(cameraPosition.tilt, this.e, this.f), dA);
    }
}
