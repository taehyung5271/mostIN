package com.naver.maps.map.a;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class b {
    static final SparseArray<b> a = new SparseArray<>(2);
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private final int i;
    private final int j;
    private final int k;
    private final int l;
    private final int m;
    private final int n;

    private b(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        float f = displayMetrics.density;
        f = configuration.isLayoutSizeAtLeast(4) ? f * 1.5f : f;
        this.b = (int) ((15.0f * f) + 0.5f);
        this.c = (int) ((48.0f * f) + 0.5f);
        this.d = (int) ((27.0f * f) + 0.5f);
        int i = (int) ((8.0f * f) + 0.5f);
        this.e = i;
        this.f = i;
        this.g = (int) ((100.0f * f) + 0.5f);
        int i2 = (int) ((2.0f * f) + 0.5f);
        this.k = i2;
        this.l = (int) ((3.0f * f) + 0.5f);
        this.h = (int) ((150.0f * f) + 0.5f);
        this.i = (int) ((63.0f * f) + 0.5f);
        this.j = (int) ((250.0f * f) + 0.5f);
        this.m = (int) ((f * 4.0f) + 0.5f);
        this.n = i2;
    }

    public static b a(Context context) {
        int i = (int) (context.getResources().getDisplayMetrics().density * 100.0f);
        b bVar = a.get(i);
        if (bVar == null) {
            b bVar2 = new b(context);
            a.put(i, bVar2);
            return bVar2;
        }
        return bVar;
    }

    int a() {
        return this.n;
    }

    int b() {
        return this.c;
    }

    int c() {
        return this.d;
    }

    int d() {
        return this.e;
    }

    int e() {
        return this.f;
    }

    int f() {
        return this.g;
    }

    int g() {
        return this.h;
    }

    int h() {
        return this.i;
    }

    int i() {
        return this.j;
    }

    int j() {
        return this.l;
    }

    int k() {
        return this.m;
    }

    int l() {
        return this.b;
    }

    int m() {
        return this.k;
    }
}
