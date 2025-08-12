package com.naver.maps.map.a;

import android.graphics.PointF;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class g {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private boolean f;
    private boolean g;
    private boolean h;
    private MotionEvent i;
    private MotionEvent j;
    private float k = 0.0f;
    private float l = 0.0f;
    private float m = 0.0f;
    private b n;
    private a o;
    private long p;
    private float q;
    private float r;
    private long s;

    public interface a {
        void a(float f);

        boolean a();

        void b();
    }

    public interface b {
        boolean a(PointF pointF);
    }

    public g(com.naver.maps.map.a.b bVar) {
        this.a = bVar.m();
        int iK = bVar.k();
        this.b = iK * iK;
        this.c = bVar.g();
        int iC = bVar.c();
        this.d = iC * iC;
        this.e = bVar.a();
    }

    private void b() {
        if (this.g) {
            this.o.b();
        }
        this.k = 0.0f;
        this.l = 0.0f;
        this.g = false;
        this.m = 0.0f;
        this.j = null;
    }

    private void c() {
        this.h = false;
        this.i = null;
    }

    public boolean a() {
        return this.g;
    }

    private boolean d() {
        return this.o != null && this.f;
    }

    private boolean b(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2 || !this.h) {
            return false;
        }
        if (motionEvent.getEventTime() - this.i.getEventTime() > 500) {
            return true;
        }
        int actionIndex = motionEvent.getActionIndex();
        float x = motionEvent.getX(actionIndex) - this.i.getX(actionIndex);
        float y = motionEvent.getY(actionIndex) - this.i.getY(actionIndex);
        return (x * x) + (y * y) > ((float) this.b);
    }

    private boolean c(MotionEvent motionEvent) {
        return this.n != null && this.f && motionEvent.getEventTime() - this.s < 500;
    }

    public boolean a(MotionEvent motionEvent) {
        this.p = motionEvent.getEventTime();
        switch (motionEvent.getActionMasked()) {
            case 0:
                c();
                b();
                this.s = this.p;
                break;
            case 1:
            case 3:
            case 4:
            default:
                c();
                b();
                this.f = false;
                break;
            case 2:
                if (b(motionEvent)) {
                    c();
                }
                break;
            case 5:
                this.f = true;
                this.q = (motionEvent.getX(1) + motionEvent.getX(0)) / 2.0f;
                this.r = (motionEvent.getY(1) + motionEvent.getY(0)) / 2.0f;
                g(motionEvent);
                f(motionEvent);
                break;
            case 6:
                boolean zE = e(motionEvent) | false;
                c();
                this.f = false;
                break;
        }
        return false;
    }

    private float a(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (f5 * f5) + (f6 * f6);
    }

    private boolean d(MotionEvent motionEvent) {
        if (!d() || motionEvent.getPointerCount() != 2) {
            return false;
        }
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        float x2 = motionEvent.getX(1);
        float y2 = motionEvent.getY(1);
        float fA = e.a(x, y, x2, y2, 0.0f, 0.0f, x2, 0.0f);
        if (fA < -180.0f) {
            fA += 360.0f;
        }
        if (Math.abs(fA) > 45.0f || Math.abs(y - y2) > this.c || a(x, y, x2, y2) < this.d) {
            return false;
        }
        float f = this.k;
        float f2 = this.l;
        float y3 = this.j.getY(0) - y;
        float y4 = this.j.getY(1) - y2;
        float x3 = this.j.getX(0) - x;
        float x4 = this.j.getX(1) - x2;
        float f3 = (y3 + y4) / 2.0f;
        float f4 = (x3 + x4) / 2.0f;
        if (this.j != null) {
            this.j.recycle();
        }
        this.j = MotionEvent.obtain(motionEvent);
        this.k = f3;
        this.l = f4;
        boolean z = f2 >= 0.0f && x3 >= 0.0f && x4 >= 0.0f;
        boolean z2 = f2 <= 0.0f && x3 <= 0.0f && x4 <= 0.0f;
        if (z || z2) {
            if (!this.g) {
                if ((f > 0.0f && y3 > 0.0f && y4 > 0.0f) || (f < 0.0f && y3 < 0.0f && y4 < 0.0f)) {
                    this.m += f3;
                } else {
                    this.m = 0.0f;
                }
                if (Math.abs(this.m) > this.a) {
                    this.m = 0.0f;
                    this.g = this.o.a();
                    return this.g;
                }
            } else if (this.g) {
                this.o.a(f3 / this.e);
                return true;
            }
        }
        return false;
    }

    private boolean e(MotionEvent motionEvent) {
        return this.h && !b(motionEvent) && this.n.a(new PointF(this.q, this.r));
    }

    public void a(a aVar) {
        this.o = aVar;
    }

    public void a(b bVar) {
        this.n = bVar;
    }

    private void f(MotionEvent motionEvent) {
        if (!d()) {
            return;
        }
        b();
        this.j = MotionEvent.obtain(motionEvent);
    }

    private void g(MotionEvent motionEvent) {
        if (!c(motionEvent)) {
            return;
        }
        this.h = true;
        if (this.i != null) {
            this.i.recycle();
        }
        this.i = MotionEvent.obtain(motionEvent);
    }
}
