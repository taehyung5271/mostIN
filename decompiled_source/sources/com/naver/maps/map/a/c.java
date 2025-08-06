package com.naver.maps.map.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;

/* loaded from: classes.dex */
public class c {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private final Handler i;
    private final b j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private MotionEvent q;
    private MotionEvent r;
    private boolean s;
    private boolean t;
    private float u;
    private float v;
    private float w;
    private float x;
    private VelocityTracker y;

    public interface b {
        boolean a(MotionEvent motionEvent);

        boolean a(MotionEvent motionEvent, float f);

        boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        boolean b(MotionEvent motionEvent);

        boolean b(MotionEvent motionEvent, float f);

        boolean b(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        boolean c(MotionEvent motionEvent);

        void d(MotionEvent motionEvent);

        void e(MotionEvent motionEvent);

        boolean f(MotionEvent motionEvent);
    }

    private class a extends Handler {
        a() {
            super(Looper.getMainLooper());
        }

        a(Handler handler) {
            super(handler.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!c.this.t) {
                        c.this.c();
                        break;
                    }
                    break;
                case 2:
                    if (c.this.j != null) {
                        if (!c.this.k) {
                            c.this.j.e(c.this.q);
                            break;
                        } else {
                            c.this.l = true;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public c(com.naver.maps.map.a.b bVar, b bVar2) {
        this(bVar, bVar2, null);
    }

    public c(com.naver.maps.map.a.b bVar, b bVar2, Handler handler) {
        this.d = 0;
        this.e = 1000;
        if (handler != null) {
            this.i = new a(handler);
        } else {
            this.i = new a();
        }
        this.j = bVar2;
        a(bVar);
    }

    private void a(com.naver.maps.map.a.b bVar) {
        if (this.j == null) {
            throw new NullPointerException("Listener must not be null");
        }
        int iD = bVar.d();
        int iE = bVar.e();
        int iF = bVar.f();
        this.h = bVar.j();
        this.a = iD * iD;
        this.b = iE * iE;
        this.c = iF * iF;
        this.f = bVar.i();
        this.g = bVar.h();
    }

    public boolean a(MotionEvent motionEvent) {
        boolean zA;
        boolean zA2;
        boolean zB;
        int actionMasked = motionEvent.getActionMasked();
        if (this.y == null) {
            this.y = VelocityTracker.obtain();
        }
        this.y.addMovement(motionEvent);
        int actionIndex = actionMasked == 6 ? motionEvent.getActionIndex() : -1;
        int pointerCount = motionEvent.getPointerCount();
        float x = 0.0f;
        float y = 0.0f;
        for (int i = 0; i < pointerCount; i++) {
            if (actionIndex != i) {
                x += motionEvent.getX(i);
                y += motionEvent.getY(i);
            }
        }
        float f = actionMasked == 6 ? pointerCount - 1 : pointerCount;
        float f2 = x / f;
        float f3 = y / f;
        switch (actionMasked) {
            case 0:
                if (this.j != null) {
                    boolean zHasMessages = this.i.hasMessages(2);
                    if (zHasMessages) {
                        this.i.removeMessages(2);
                    }
                    if (this.q != null && this.r != null && zHasMessages && a(this.q, this.r, motionEvent)) {
                        this.s = true;
                        zA = this.j.a(this.q) | false | this.j.b(motionEvent);
                    } else {
                        this.i.sendEmptyMessageDelayed(2, 300L);
                        zA = false;
                    }
                } else {
                    zA = false;
                }
                this.u = f2;
                this.w = f2;
                this.v = f3;
                this.x = f3;
                if (this.q != null) {
                    this.q.recycle();
                }
                this.q = MotionEvent.obtain(motionEvent);
                this.n = true;
                this.o = true;
                this.k = true;
                this.m = false;
                this.p = true;
                this.l = false;
                this.i.removeMessages(1);
                this.i.sendEmptyMessageAtTime(1, this.q.getDownTime() + 180 + 500);
                return zA | this.j.c(motionEvent);
            case 1:
                this.k = false;
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                if (!this.t) {
                    zA2 = false;
                } else {
                    zA2 = this.j.b(motionEvent, f3 - this.v) | false;
                }
                if (this.s && a(this.q, this.r, motionEvent)) {
                    zA2 |= this.j.b(motionEvent);
                } else if (this.m) {
                    this.i.removeMessages(2);
                    this.m = false;
                } else if (this.n) {
                    zA2 = this.j.f(motionEvent);
                    if (this.l && this.j != null) {
                        this.j.e(motionEvent);
                    }
                } else {
                    VelocityTracker velocityTracker = this.y;
                    int pointerId = motionEvent.getPointerId(0);
                    velocityTracker.computeCurrentVelocity(1000, this.e);
                    float yVelocity = velocityTracker.getYVelocity(pointerId);
                    float xVelocity = velocityTracker.getXVelocity(pointerId);
                    if (Math.abs(yVelocity) > this.d || Math.abs(xVelocity) > this.d) {
                        zA2 = this.j.a(this.q, motionEvent, xVelocity, yVelocity);
                    }
                }
                if (this.r != null) {
                    this.r.recycle();
                }
                this.r = motionEventObtain;
                if (this.y != null) {
                    this.y.recycle();
                    this.y = null;
                }
                this.s = false;
                this.t = false;
                this.l = false;
                this.i.removeMessages(1);
                return zA2;
            case 2:
                float f4 = this.u - f2;
                float f5 = this.v - f3;
                if (!this.t) {
                    zB = false;
                } else {
                    zB = this.j.a(motionEvent, f3 - this.v) | false;
                    this.u = f2;
                    this.v = f3;
                    if (zB) {
                        this.s = false;
                        return zB;
                    }
                }
                if (this.s) {
                    if (this.h < Math.abs(f3 - this.x)) {
                        this.t = true;
                    }
                    return zB | this.j.b(motionEvent);
                }
                if (this.i.hasMessages(3)) {
                    return zB;
                }
                int i2 = (int) (f2 - this.w);
                int i3 = (int) (f3 - this.x);
                int i4 = (i2 * i2) + (i3 * i3);
                if (this.m || this.n) {
                    if (i4 > this.a) {
                        zB = this.j.b(this.q, motionEvent, f4, f5);
                        this.u = f2;
                        this.v = f3;
                        if (this.n) {
                            this.n = false;
                            this.i.removeMessages(2);
                            this.i.removeMessages(1);
                        }
                    }
                } else if (Math.abs(f4) >= 1.0f || Math.abs(f5) >= 1.0f) {
                    zB = this.j.b(this.q, motionEvent, f4, f5);
                    this.u = f2;
                    this.v = f3;
                }
                if (this.o && i4 > this.b) {
                    this.o = false;
                }
                if (!this.m && this.p) {
                    if (i4 > this.f) {
                        this.i.removeMessages(1);
                        this.p = false;
                    } else if (((int) ((f4 * f4) + (f5 * f5))) > this.g) {
                        this.i.removeMessages(1);
                    } else if (this.q == null) {
                        throw new NullPointerException("mCurrentDownEvent is null");
                    }
                }
                return zB;
            case 3:
                this.t = false;
                a();
                return false;
            case 4:
            default:
                return false;
            case 5:
                b();
                return false;
            case 6:
                this.u = f2;
                this.w = f2;
                this.v = f3;
                this.x = f3;
                this.n = true;
                this.o = true;
                this.i.removeMessages(3);
                this.i.sendEmptyMessageDelayed(3, 100L);
                this.y.computeCurrentVelocity(1000, this.e);
                int actionIndex2 = motionEvent.getActionIndex();
                int pointerId2 = motionEvent.getPointerId(actionIndex2);
                float xVelocity2 = this.y.getXVelocity(pointerId2);
                float yVelocity2 = this.y.getYVelocity(pointerId2);
                for (int i5 = 0; i5 < pointerCount; i5++) {
                    if (i5 != actionIndex2) {
                        int pointerId3 = motionEvent.getPointerId(i5);
                        if ((this.y.getXVelocity(pointerId3) * xVelocity2) + (this.y.getYVelocity(pointerId3) * yVelocity2) < 0.0f) {
                            this.y.clear();
                            return false;
                        }
                    }
                }
                return false;
        }
    }

    private void a() {
        this.i.removeMessages(1);
        this.i.removeMessages(2);
        this.i.removeMessages(3);
        this.y.recycle();
        this.y = null;
        this.s = false;
        this.k = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.l = false;
        this.m = false;
    }

    private void b() {
        this.i.removeMessages(1);
        this.i.removeMessages(2);
        this.i.removeMessages(3);
        this.s = false;
        this.t = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.l = false;
        this.m = false;
    }

    private boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
        if (!this.o) {
            return false;
        }
        long eventTime = motionEvent3.getEventTime() - motionEvent2.getEventTime();
        if (eventTime > 300 || eventTime < 40) {
            return false;
        }
        int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
        int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
        return (x * x) + (y * y) < this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        a();
        this.k = true;
        this.m = true;
        this.j.d(this.q);
    }
}
