package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public class Leaf extends Node {
    private final ClusteringKey a;
    private final WebMercatorCoord b;
    private final LatLng c;

    Leaf(long id, ClusteringKey key, LatLng position) {
        super(id);
        this.a = key;
        this.b = WebMercatorCoord.valueOf(position);
        this.c = position;
    }

    public ClusteringKey getKey() {
        return this.a;
    }

    @Override // com.naver.maps.map.clustering.Node
    public Object getTag() {
        return super.getTag();
    }

    @Override // com.naver.maps.map.clustering.Node
    public int getSize() {
        return 1;
    }

    @Override // com.naver.maps.map.clustering.Node
    public WebMercatorCoord getCoord() {
        return this.b;
    }

    @Override // com.naver.maps.map.clustering.Node
    public LatLng getPosition() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.naver.maps.map.clustering.Node
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public LeafMarkerInfo b() {
        return new LeafMarkerInfo(c(), getTag(), getCoord(), getPosition(), getMinZoom(), getMaxZoom(), getKey());
    }
}
