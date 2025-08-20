package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public abstract class Node {
    private final long a;
    private Object b;
    private int c;
    private int d;
    private Cluster e;
    private WebMercatorCoord f;

    abstract MarkerInfo b();

    public abstract WebMercatorCoord getCoord();

    public abstract LatLng getPosition();

    public abstract int getSize();

    Node(long id) {
        this.a = id;
    }

    long c() {
        return this.a;
    }

    public Object getTag() {
        return this.b;
    }

    void a(Object obj) {
        this.b = obj;
    }

    public int getMinZoom() {
        return this.c;
    }

    void a(int i) {
        this.c = i;
    }

    public int getMaxZoom() {
        return this.d;
    }

    void b(int i) {
        this.d = i;
    }

    Cluster d() {
        return this.e;
    }

    void a(Cluster cluster) {
        this.e = cluster;
    }

    WebMercatorCoord e() {
        if (this.f == null) {
            this.f = getCoord();
        }
        return this.f;
    }
}
