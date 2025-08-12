package com.naver.maps.map.a;

import com.naver.maps.map.UiSettings;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class f {
    private final UiSettings a;
    private List<Long> b = new ArrayList();
    private List<Float> c = new ArrayList();
    private List<Float> d = new ArrayList();
    private float e;
    private float f;
    private int g;

    public f(UiSettings uiSettings) {
        this.a = uiSettings;
    }

    public void a(long j, float f, float f2) {
        if (!b(j)) {
            f();
        }
        this.b.add(Long.valueOf(j));
        this.c.add(Float.valueOf(f));
        this.d.add(Float.valueOf(f2));
    }

    public boolean a(long j) {
        this.f = 0.0f;
        this.e = 0.0f;
        int size = this.b.size();
        if (size < 2) {
            return false;
        }
        if (!b(j)) {
            f();
            return false;
        }
        int iMax = Math.max(size - 5, 0);
        int i = size - 1;
        long jLongValue = (this.b.get(i).longValue() - this.b.get(iMax).longValue()) / (size - iMax);
        if (jLongValue > 50) {
            return false;
        }
        float fFloatValue = 0.0f;
        for (int i2 = i; i2 >= iMax; i2--) {
            fFloatValue += this.c.get(i2).floatValue();
        }
        float fMax = 0.5f / Math.max(jLongValue / 200.0f, 0.01f);
        float f = fFloatValue * fMax;
        if (Math.abs(fFloatValue / size) > 5.0f && Math.abs(f) > 1.0f) {
            float fAbs = Math.abs((f > 0.0f ? 1 : -1) * Math.min(100.0f, Math.abs(f))) / (d() * 0.5f);
            this.f = fFloatValue * fMax * (fAbs / 2.0f);
            this.g = (int) (fAbs * 1000.0f);
            return true;
        }
        float fFloatValue2 = 0.0f;
        while (i >= iMax) {
            fFloatValue2 += this.d.get(i).floatValue();
            i--;
        }
        float f2 = fFloatValue2 * fMax;
        if (Math.abs(f2) < 1.0f) {
            return false;
        }
        float fMin = (f2 > 0.0f ? 1 : -1) * Math.min(7.0f, Math.abs(f2));
        float fAbs2 = Math.abs(fMin) / (e() * 0.5f);
        this.e = (fMin * fAbs2) / 2.0f;
        this.g = (int) Math.min(700.0f, fAbs2 * 1000.0f);
        return true;
    }

    public float a() {
        return this.e;
    }

    public float b() {
        return this.f;
    }

    public int c() {
        return this.g;
    }

    private float d() {
        return ((int) (this.a.getRotateGesturesFriction() * 800.0f)) + 1;
    }

    private float e() {
        return ((int) (this.a.getZoomGesturesFriction() * 150.0f)) + 1;
    }

    private void f() {
        this.b.clear();
        this.c.clear();
        this.d.clear();
    }

    private boolean b(long j) {
        return this.b.size() <= 0 || j - this.b.get(this.b.size() - 1).longValue() <= 300;
    }
}
