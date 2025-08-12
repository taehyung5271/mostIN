package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class Cluster extends Node {
    private final PositioningStrategy a;
    private final List<Node> b;
    private int c;
    private WebMercatorCoord d;
    private LatLng e;

    Cluster(long id, PositioningStrategy positioningStrategy) {
        super(id);
        this.a = positioningStrategy;
        this.b = new ArrayList();
    }

    @Override // com.naver.maps.map.clustering.Node
    public Object getTag() {
        return super.getTag();
    }

    public List<Node> getChildren() {
        return this.b;
    }

    @Override // com.naver.maps.map.clustering.Node
    public int getSize() {
        if (this.c < 0) {
            this.c = 0;
            Iterator<Node> it = this.b.iterator();
            while (it.hasNext()) {
                this.c += it.next().getSize();
            }
        }
        return this.c;
    }

    @Override // com.naver.maps.map.clustering.Node
    public WebMercatorCoord getCoord() {
        if (this.d == null) {
            this.d = this.a.getPosition(this);
        }
        return this.d;
    }

    @Override // com.naver.maps.map.clustering.Node
    public LatLng getPosition() {
        if (this.e == null) {
            this.e = getCoord().toLatLng();
        }
        return this.e;
    }

    void a(Node node) {
        if (node.d() == this) {
            return;
        }
        this.b.add(node);
        node.a(this);
        f();
    }

    void b(Node node) {
        if (node.d() != this) {
            return;
        }
        node.a((Cluster) null);
        this.b.remove(node);
        f();
    }

    private void f() {
        this.c = -1;
        this.d = null;
        this.e = null;
        if (d() != null) {
            d().f();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.naver.maps.map.clustering.Node
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ClusterMarkerInfo b() {
        return new ClusterMarkerInfo(c(), getTag(), getCoord(), getPosition(), getMinZoom(), getMaxZoom(), getSize());
    }
}
