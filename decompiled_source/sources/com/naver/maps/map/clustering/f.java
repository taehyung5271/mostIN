package com.naver.maps.map.clustering;

/* loaded from: classes.dex */
class f {
    static final double[] a = new double[22];
    static final double[] b = new double[22];

    static {
        for (int i = 0; i < a.length; i++) {
            a[i] = 4.007501668557849E7d / (1 << i);
            b[i] = a[i] / 512.0d;
        }
    }
}
