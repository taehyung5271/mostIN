package com.naver.maps.map.clustering;

/* loaded from: classes.dex */
class a implements DistanceStrategy {
    private final DistanceStrategy a;
    private final int b;

    public a(DistanceStrategy distanceStrategy, int i) {
        this.a = distanceStrategy;
        this.b = i;
    }

    @Override // com.naver.maps.map.clustering.DistanceStrategy
    public double getDistance(int ignore, Node node1, Node node2) {
        if (node2.d() != null) {
            return Double.POSITIVE_INFINITY;
        }
        return this.a.getDistance(this.b, node1, node2);
    }
}
