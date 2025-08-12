package com.naver.maps.map.clustering;

/* loaded from: classes.dex */
class b implements ThresholdStrategy {
    private final ThresholdStrategy a;
    private final int b;

    public b(ThresholdStrategy thresholdStrategy, int i) {
        this.a = thresholdStrategy;
        this.b = i;
    }

    @Override // com.naver.maps.map.clustering.ThresholdStrategy
    public double getThreshold(int ignore) {
        return this.a.getThreshold(this.b);
    }
}
