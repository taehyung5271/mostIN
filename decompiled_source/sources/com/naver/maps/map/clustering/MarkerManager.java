package com.naver.maps.map.clustering;

import com.naver.maps.map.overlay.Marker;

/* loaded from: classes.dex */
public interface MarkerManager {
    void releaseMarker(MarkerInfo markerInfo, Marker marker);

    Marker retainMarker(MarkerInfo markerInfo);
}
