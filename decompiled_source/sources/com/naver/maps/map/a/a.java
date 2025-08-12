package com.naver.maps.map.a;

import android.graphics.PointF;
import com.naver.maps.map.UiSettings;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class a {
    private final UiSettings a;
    private List<Long> b = new ArrayList();
    private List<PointF> c = new ArrayList();
    private PointF d;
    private int e;

    public a(UiSettings uiSettings) {
        this.a = uiSettings;
    }

    public void a(long j, PointF pointF) {
        if (!b(j)) {
            c();
        }
        this.b.add(Long.valueOf(j));
        this.c.add(pointF);
    }

    public boolean a(long j) {
        int size = this.b.size();
        if (size < 2) {
            return false;
        }
        if (!b(j)) {
            c();
            return false;
        }
        int iMax = Math.max(size - 5, 0);
        int i = size - 1;
        long jLongValue = this.b.get(i).longValue() - this.b.get(iMax).longValue();
        if (jLongValue / (size - iMax) > 50) {
            return false;
        }
        PointF pointF = this.c.get(i);
        PointF pointF2 = this.c.get(iMax);
        PointF pointF3 = new PointF(pointF.x - pointF2.x, pointF.y - pointF2.y);
        if (b(pointF3) < 2.0d) {
            return false;
        }
        float fMax = 0.5f / Math.max(jLongValue / 1000.0f, 0.05f);
        float fMin = Math.min(a(pointF3) * fMax, 4500.0f) / (b() * 0.5f);
        float f = fMax * (fMin / 2.0f);
        this.d = new PointF(pointF3.x * f, pointF3.y * f);
        this.e = (int) (fMin * 1000.0f);
        return true;
    }

    public PointF a(float f) {
        if (this.d.y > 0.0f && f > 3.0f) {
            float f2 = (f + 20.0f) / 20.0f;
            this.d.x /= f2;
            this.d.y /= f2;
        }
        return this.d;
    }

    public int a() {
        return this.e;
    }

    private float b() {
        return ((int) (this.a.getScrollGesturesFriction() * 50000.0f)) + 100;
    }

    private void c() {
        this.b.clear();
        this.c.clear();
    }

    private boolean b(long j) {
        return this.b.size() <= 0 || j - this.b.get(this.b.size() - 1).longValue() <= 300;
    }

    private float a(PointF pointF) {
        return (float) Math.sqrt(b(pointF));
    }

    private double b(PointF pointF) {
        return (pointF.x * pointF.x) + (pointF.y * pointF.y);
    }
}
