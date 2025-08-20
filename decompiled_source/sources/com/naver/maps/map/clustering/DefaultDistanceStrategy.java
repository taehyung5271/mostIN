package com.naver.maps.map.clustering;

/* loaded from: classes.dex */
public class DefaultDistanceStrategy implements DistanceStrategy {
    @Override // com.naver.maps.map.clustering.DistanceStrategy
    public double getDistance(int zoom, Node node1, Node node2) {
        return node1.getCoord().distanceTo(node2.getCoord()) / f.b[zoom];
    }
}
