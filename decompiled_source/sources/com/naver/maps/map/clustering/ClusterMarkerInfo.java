package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public class ClusterMarkerInfo extends MarkerInfo {
    private final int a;

    ClusterMarkerInfo(long id, Object tag, WebMercatorCoord coord, LatLng position, int minZoom, int maxZoom, int size) {
        super(id, tag, coord, position, minZoom, maxZoom);
        this.a = size;
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public Object getTag() {
        return super.getTag();
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public LatLng getPosition() {
        return super.getPosition();
    }

    public int getSize() {
        return this.a;
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && super.equals(o) && this.a == ((ClusterMarkerInfo) o).a) {
            return true;
        }
        return false;
    }

    @Override // com.naver.maps.map.clustering.MarkerInfo
    public int hashCode() {
        return (super.hashCode() * 31) + this.a;
    }
}
