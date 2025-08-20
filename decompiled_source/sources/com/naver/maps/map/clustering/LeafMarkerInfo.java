package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public class LeafMarkerInfo extends MarkerInfo {
    private final ClusteringKey a;

    LeafMarkerInfo(long id, Object tag, WebMercatorCoord coord, LatLng position, int minZoom, int maxZoom, ClusteringKey key) {
        super(id, tag, coord, position, minZoom, maxZoom);
        this.a = key;
    }

    public ClusteringKey getKey() {
        return this.a;
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public Object getTag() {
        return super.getTag();
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public LatLng getPosition() {
        return super.getPosition();
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || !super.equals(o)) {
            return false;
        }
        return this.a.equals(((LeafMarkerInfo) o).a);
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public int hashCode() {
        return (super.hashCode() * 31) + this.a.hashCode();
    }
}
