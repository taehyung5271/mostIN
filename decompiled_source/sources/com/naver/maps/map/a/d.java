package com.naver.maps.map.a;

import android.os.SystemClock;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class d {
    private float A;
    private long B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private float H;
    private float I;
    private float J;
    private int K;
    private long L;
    private a a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private float f;
    private float g;
    private float h;
    private float i;
    private float j;
    private float k;
    private float l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private float y;
    private float z;

    public interface a {
        void a(d dVar);

        boolean b(d dVar);

        void c(d dVar);
    }

    public d(b bVar, a aVar) {
        this.a = aVar;
        this.D = bVar.l();
        this.E = this.D * 4;
        this.G = bVar.b();
        this.F = bVar.c();
        int iD = bVar.d() * 2;
        this.C = iD * iD;
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean a(android.view.MotionEvent r27) {
        /*
            Method dump skipped, instructions count: 527
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naver.maps.map.a.d.a(android.view.MotionEvent):boolean");
    }

    public void a() {
        if (this.b) {
            this.a.c(this);
            this.b = false;
            this.c = false;
            this.d = false;
            this.e = false;
        }
    }

    private void b(MotionEvent motionEvent) {
        float touchMajor;
        int iSignum;
        long jUptimeMillis = SystemClock.uptimeMillis();
        int pointerCount = motionEvent.getPointerCount();
        boolean z = jUptimeMillis - this.L >= 128;
        float f = 0.0f;
        int i = 0;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            boolean z2 = !Float.isNaN(this.J);
            int historySize = motionEvent.getHistorySize();
            int i3 = historySize + 1;
            int i4 = 0;
            while (i4 < i3) {
                if (i4 < historySize) {
                    touchMajor = motionEvent.getHistoricalTouchMajor(i2, i4);
                } else {
                    touchMajor = motionEvent.getTouchMajor(i2);
                }
                if (touchMajor < this.G) {
                    touchMajor = this.G;
                }
                f += touchMajor;
                if (Float.isNaN(this.H) || touchMajor > this.H) {
                    this.H = touchMajor;
                }
                if (Float.isNaN(this.I) || touchMajor < this.I) {
                    this.I = touchMajor;
                }
                if (z2 && ((iSignum = (int) Math.signum(touchMajor - this.J)) != this.K || (iSignum == 0 && this.K == 0))) {
                    this.K = iSignum;
                    this.L = i4 < historySize ? motionEvent.getHistoricalEventTime(i4) : motionEvent.getEventTime();
                    z = false;
                }
                i4++;
            }
            i += i3;
        }
        float f2 = f / i;
        if (z) {
            float f3 = ((this.H + this.I) + f2) / 3.0f;
            this.H = (this.H + f3) / 2.0f;
            this.I = (this.I + f3) / 2.0f;
            this.J = f3;
            this.K = 0;
            this.L = motionEvent.getEventTime();
        }
    }

    private void k() {
        this.H = Float.NaN;
        this.I = Float.NaN;
        this.J = Float.NaN;
        this.K = 0;
        this.L = 0L;
    }

    public long b() {
        return this.B;
    }

    public float c() {
        if (!this.c || this.p <= 0.0f) {
            return 1.0f;
        }
        return this.q / this.p;
    }

    public float d() {
        if (this.e) {
            return this.j;
        }
        return 0.0f;
    }

    public float e() {
        if (this.e) {
            return this.k;
        }
        return 0.0f;
    }

    public float f() {
        if (this.d) {
            return this.A;
        }
        return 0.0f;
    }

    public float g() {
        return this.f;
    }

    public float h() {
        return this.g;
    }

    public boolean i() {
        return this.d;
    }

    public boolean j() {
        return this.b;
    }
}
