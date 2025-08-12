package com.naver.maps.map;

import android.graphics.PointF;
import android.view.MotionEvent;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.a.c;
import com.naver.maps.map.a.d;
import com.naver.maps.map.a.g;

/* loaded from: classes.dex */
final class g implements c.b, d.a, g.a, g.b {
    private final NaverMap a;
    private final com.naver.maps.map.a.a b;
    private final com.naver.maps.map.a.f c;
    private final float d;
    private com.naver.maps.map.a.c e;
    private com.naver.maps.map.a.g f;
    private com.naver.maps.map.a.d g;
    private double i;
    private double j;
    private a h = null;
    private boolean k = false;
    private CameraUpdate.FinishCallback l = new CameraUpdate.FinishCallback() { // from class: com.naver.maps.map.g.1
        @Override // com.naver.maps.map.CameraUpdate.FinishCallback
        public void onCameraUpdateFinish() {
            g.this.k = false;
        }
    };
    private CameraUpdate.CancelCallback m = new CameraUpdate.CancelCallback() { // from class: com.naver.maps.map.g.2
        @Override // com.naver.maps.map.CameraUpdate.CancelCallback
        public void onCameraUpdateCancel() {
            g.this.k = false;
        }
    };

    public enum a {
        TOUCHCANCEL,
        TOUCHSTART,
        TOUCHMOVE,
        TOUCHEND,
        TAP,
        DOUBLETAP,
        LONGPRESS,
        DRAGSTART,
        DRAG,
        DRAGEND,
        PINCHSTART,
        PINCH,
        PINCHEND,
        TILTSTART,
        TILT,
        TILTEND,
        TWOFINGER_TAP,
        ROTATESTART,
        ROTATE,
        ROTATEEND,
        QUICKSCALE,
        QUICKSCALEEND
    }

    private static boolean a(a aVar) {
        if (aVar == null) {
            return false;
        }
        switch (aVar) {
        }
        return false;
    }

    g(NaverMap naverMap) {
        this.a = naverMap;
        this.b = new com.naver.maps.map.a.a(naverMap.getUiSettings());
        this.c = new com.naver.maps.map.a.f(naverMap.getUiSettings());
        this.d = naverMap.e().getResources().getDisplayMetrics().density * 100.0f;
        com.naver.maps.map.a.b bVarA = com.naver.maps.map.a.b.a(naverMap.e());
        this.e = new com.naver.maps.map.a.c(bVarA, this);
        this.f = new com.naver.maps.map.a.g(bVarA);
        this.g = new com.naver.maps.map.a.d(bVarA, this);
        this.f.a((g.b) this);
        this.f.a((g.a) this);
    }

    private void h(MotionEvent motionEvent) {
        a aVar = this.h;
        if (aVar == null) {
        }
        switch (aVar) {
            case LONGPRESS:
                this.a.cancelTransitions(-1);
                motionEvent.setAction(1);
                this.h = a.TOUCHEND;
                break;
            case DRAGSTART:
            case DRAG:
                this.a.cancelTransitions(-1);
                this.h = a.DRAGEND;
                break;
        }
    }

    private boolean c() {
        return this.f.a();
    }

    private boolean d() {
        return this.g.i();
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean a(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean b(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return false;
        }
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        if (this.a.c(pointF)) {
            return true;
        }
        if (!this.a.getUiSettings().isZoomGesturesEnabled()) {
            return false;
        }
        if (!this.k) {
            this.j = this.a.getCameraPosition().zoom;
            this.k = true;
        }
        this.j += 1.0d;
        CameraUpdate cameraUpdateCancelCallback = CameraUpdate.zoomTo(this.j).animate(CameraAnimation.Easing).reason(-1).finishCallback(this.l).cancelCallback(this.m);
        if (this.a.getUiSettings().isScrollGesturesEnabled()) {
            cameraUpdateCancelCallback.a(pointF);
        }
        this.a.moveCamera(cameraUpdateCancelCallback);
        this.h = a.DOUBLETAP;
        return true;
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean a(MotionEvent motionEvent, float f) {
        if (!this.a.getUiSettings().isZoomGesturesEnabled()) {
            return false;
        }
        float f2 = f / this.d;
        this.c.a(motionEvent.getEventTime(), 0.0f, f2);
        this.a.moveCamera(CameraUpdate.zoomBy(f2).reason(-1));
        this.h = a.QUICKSCALE;
        return true;
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean b(MotionEvent motionEvent, float f) {
        if (!this.a.getUiSettings().isZoomGesturesEnabled()) {
            return false;
        }
        if (this.c.a(motionEvent.getEventTime())) {
            this.a.moveCamera(CameraUpdate.zoomBy(this.c.a()).animate(CameraAnimation.Easing, this.c.c()).reason(-1));
        }
        this.h = a.QUICKSCALEEND;
        return true;
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean c(MotionEvent motionEvent) {
        return true;
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.a.getUiSettings().isScrollGesturesEnabled()) {
            return false;
        }
        if (this.b.a(motionEvent2.getEventTime())) {
            this.a.moveCamera(CameraUpdate.scrollBy(this.b.a((float) this.a.getCameraPosition().tilt)).a(new PointF(motionEvent2.getX(), motionEvent2.getY())).reason(-1).animate(CameraAnimation.Easing, this.b.a()));
            return true;
        }
        return true;
    }

    @Override // com.naver.maps.map.a.c.b
    public void d(MotionEvent motionEvent) {
        if (e()) {
            return;
        }
        this.h = a.LONGPRESS;
        this.a.b(new PointF(motionEvent.getX(), motionEvent.getY()));
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean b(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (motionEvent2.getPointerCount() != 1 || !this.a.getUiSettings().isScrollGesturesEnabled() || c()) {
            return false;
        }
        if (this.h == null) {
            this.h = a.DRAGSTART;
        } else {
            switch (this.h) {
                case DRAGSTART:
                case DRAG:
                    this.h = a.DRAG;
                    break;
                default:
                    this.h = a.DRAGSTART;
                    break;
            }
        }
        if (this.h == a.DRAGSTART) {
            f = 0.0f;
            f2 = 0.0f;
        }
        a(motionEvent2, f, f2);
        return true;
    }

    private void a(MotionEvent motionEvent, float f, float f2) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        this.b.a(motionEvent.getEventTime(), pointF);
        this.a.moveCamera(CameraUpdate.scrollBy(new PointF(-f, -f2)).a(pointF).reason(-1));
    }

    @Override // com.naver.maps.map.a.c.b
    public void e(MotionEvent motionEvent) {
        this.h = a.TAP;
        this.a.a(new PointF(motionEvent.getX(), motionEvent.getY()));
    }

    @Override // com.naver.maps.map.a.c.b
    public boolean f(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.naver.maps.map.a.g.a
    public boolean a() {
        if (d() || !this.a.getUiSettings().isTiltGesturesEnabled()) {
            return false;
        }
        this.g.a();
        this.h = a.TILTSTART;
        return true;
    }

    @Override // com.naver.maps.map.a.g.a
    public void b() {
        double dClamp;
        double d = this.a.getCameraPosition().tilt;
        double maxTilt = this.a.getMaxTilt();
        if (d < 10.0d) {
            dClamp = 0.0d;
        } else {
            dClamp = 25.0d;
            if (d >= 25.0d) {
                dClamp = maxTilt - 3.0d;
                if (d <= dClamp) {
                    dClamp = MathUtils.clamp(d + (this.i * 2.0d), 0.0d, maxTilt - 10.0d);
                }
            } else if (this.i <= 0.0d) {
                dClamp = 0.0d;
            }
        }
        this.a.moveCamera(CameraUpdate.withParams(new CameraUpdateParams().tiltTo(dClamp)).animate(CameraAnimation.Easing).reason(-1));
        this.i = 0.0d;
        this.h = a.TILTEND;
    }

    @Override // com.naver.maps.map.a.g.a
    public void a(float f) {
        this.h = a.TILT;
        double d = this.a.getCameraPosition().tilt;
        if (d > 53.0d) {
            f = (float) (f / (11.0d - (63.0d - d)));
        }
        double d2 = f;
        this.i = d2;
        this.a.moveCamera(CameraUpdate.withParams(new CameraUpdateParams().tiltBy(d2)).reason(-1));
    }

    public boolean g(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 2) {
            return false;
        }
        if (motionEvent.getActionMasked() == 0 && !this.k) {
            this.h = a.TOUCHSTART;
            this.a.j().a(true);
            if (this.a.getUiSettings().isStopGesturesEnabled()) {
                this.a.cancelTransitions(-1);
            }
        }
        if (motionEvent.getActionMasked() == 1 && this.h == a.TOUCHSTART) {
            this.h = a.TOUCHEND;
        }
        if (motionEvent.getActionMasked() != 2) {
            h(motionEvent);
        }
        boolean zA = this.e.a(motionEvent) | this.f.a(motionEvent) | this.g.a(motionEvent);
        this.a.j().a(a(this.h));
        return zA;
    }

    private boolean e() {
        return this.g.j() || c();
    }

    @Override // com.naver.maps.map.a.g.b
    public boolean a(PointF pointF) {
        if (this.a.d(pointF)) {
            return true;
        }
        if (!this.a.getUiSettings().isZoomGesturesEnabled()) {
            return false;
        }
        this.g.a();
        if (!this.k) {
            this.j = this.a.getCameraPosition().zoom;
            this.k = true;
        }
        this.j -= 1.0d;
        CameraUpdate cameraUpdateCancelCallback = CameraUpdate.zoomTo(this.j).animate(CameraAnimation.Easing).reason(-1).finishCallback(this.l).cancelCallback(this.m);
        if (this.a.getUiSettings().isScrollGesturesEnabled()) {
            cameraUpdateCancelCallback.a(pointF);
        }
        this.a.moveCamera(cameraUpdateCancelCallback);
        this.h = a.TWOFINGER_TAP;
        return true;
    }

    @Override // com.naver.maps.map.a.d.a
    public void a(com.naver.maps.map.a.d dVar) {
        if (c()) {
            return;
        }
        this.h = a.PINCH;
        float fLog = (float) (Math.log(dVar.c()) / Math.log(2.0d));
        this.c.a(dVar.b(), dVar.f(), fLog);
        CameraUpdate cameraUpdateA = a(dVar, dVar.f(), fLog);
        if (cameraUpdateA != null) {
            this.a.moveCamera(cameraUpdateA);
        }
    }

    @Override // com.naver.maps.map.a.d.a
    public boolean b(com.naver.maps.map.a.d dVar) {
        if (c()) {
            return false;
        }
        this.h = a.PINCHSTART;
        return true;
    }

    @Override // com.naver.maps.map.a.d.a
    public void c(com.naver.maps.map.a.d dVar) {
        double d;
        double dAbs = Math.abs(MathUtils.wrap(this.a.getCameraPosition().bearing, -180.0d, 180.0d));
        if (this.c.a(dVar.b())) {
            double dB = this.c.b();
            if (dB != 0.0d && dB < 10.0d && dAbs < 10.0d) {
                d = Double.NaN;
            } else {
                d = dB;
            }
            CameraUpdate cameraUpdateA = a(dVar, d, this.c.a());
            if (cameraUpdateA != null) {
                this.a.moveCamera(cameraUpdateA.animate(CameraAnimation.Easing, this.c.c()));
            }
        } else if (dAbs != 0.0d && dAbs < 10.0d && this.a.getUiSettings().isRotateGesturesEnabled()) {
            this.a.moveCamera(CameraUpdate.withParams(new CameraUpdateParams().rotateTo(0.0d)).animate(CameraAnimation.Easing).reason(-1));
        }
        this.h = a.PINCHEND;
    }

    private CameraUpdate a(com.naver.maps.map.a.d dVar, double d, double d2) {
        CameraUpdateParams cameraUpdateParams;
        if (dVar.d() != 0.0f && dVar.e() != 0.0f && this.a.getUiSettings().isScrollGesturesEnabled()) {
            cameraUpdateParams = new CameraUpdateParams();
            cameraUpdateParams.scrollBy(new PointF(-dVar.d(), -dVar.e()));
        } else {
            cameraUpdateParams = null;
        }
        if (d != 0.0d && this.a.getUiSettings().isRotateGesturesEnabled()) {
            if (cameraUpdateParams == null) {
                cameraUpdateParams = new CameraUpdateParams();
            }
            if (Double.isNaN(d)) {
                cameraUpdateParams.rotateTo(0.0d);
            } else {
                cameraUpdateParams.rotateBy(d);
            }
        }
        if (d2 != 0.0d && this.a.getUiSettings().isZoomGesturesEnabled()) {
            if (cameraUpdateParams == null) {
                cameraUpdateParams = new CameraUpdateParams();
            }
            cameraUpdateParams.zoomBy(d2);
        }
        if (cameraUpdateParams == null) {
            return null;
        }
        CameraUpdate cameraUpdateReason = CameraUpdate.withParams(cameraUpdateParams).reason(-1);
        if (this.a.getUiSettings().isScrollGesturesEnabled()) {
            cameraUpdateReason.a(new PointF(dVar.g(), dVar.h()));
        }
        return cameraUpdateReason;
    }
}
