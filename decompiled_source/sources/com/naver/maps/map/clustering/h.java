package com.naver.maps.map.clustering;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;

/* loaded from: classes.dex */
class h {
    private final c a;
    private final MarkerInfo b;
    private LatLng c;
    private WebMercatorCoord d;
    private Marker e;

    public h(c cVar, MarkerInfo markerInfo) {
        this.a = cVar;
        this.b = markerInfo;
    }

    MarkerInfo a() {
        return this.b;
    }

    void a(WebMercatorCoord webMercatorCoord) {
        this.d = webMercatorCoord;
        this.c = webMercatorCoord == null ? null : webMercatorCoord.toLatLng();
        if (this.e != null) {
            this.e.setPosition(c());
        }
    }

    WebMercatorCoord b() {
        return this.d == null ? this.b.b() : this.d;
    }

    LatLng c() {
        return this.c == null ? this.b.getPosition() : this.c;
    }

    void a(NaverMap naverMap) {
        if (this.e != null && this.e.getMap() != naverMap) {
            this.a.a().releaseMarker(this.b, this.e);
            this.e.setMap(null);
            this.e = null;
        }
        if (this.e == null && naverMap != null) {
            this.e = this.a.a().retainMarker(this.b);
        }
        if (this.e != null) {
            if (this.b instanceof LeafMarkerInfo) {
                this.a.c().updateLeafMarker((LeafMarkerInfo) this.b, this.e);
            } else if (this.b instanceof ClusterMarkerInfo) {
                this.a.b().updateClusterMarker((ClusterMarkerInfo) this.b, this.e);
            }
            this.e.setPosition(c());
            this.e.setMap(naverMap);
        }
    }
}
