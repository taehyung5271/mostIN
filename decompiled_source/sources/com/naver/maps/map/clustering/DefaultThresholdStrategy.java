package com.naver.maps.map.clustering;

/* loaded from: classes.dex */
public class DefaultThresholdStrategy implements ThresholdStrategy {
    private final double a;

    public DefaultThresholdStrategy(double threshold) {
        this.a = threshold;
    }

    @Override // com.naver.maps.map.clustering.ThresholdStrategy
    public double getThreshold(int zoom) {
        return this.a;
    }
}
