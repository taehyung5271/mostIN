package com.naver.maps.map.clustering;

import com.naver.maps.geometry.WebMercatorCoord;

/* loaded from: classes.dex */
public interface PositioningStrategy {
    WebMercatorCoord getPosition(Cluster cluster);
}
