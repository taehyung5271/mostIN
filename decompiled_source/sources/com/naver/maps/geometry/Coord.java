package com.naver.maps.geometry;

/* loaded from: classes.dex */
public interface Coord {
    boolean isValid();

    boolean isWithinCoverage();

    LatLng toLatLng();
}
