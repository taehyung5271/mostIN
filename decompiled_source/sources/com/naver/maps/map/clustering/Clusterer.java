package com.naver.maps.map.clustering;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.clustering.ClusteringKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class Clusterer<T extends ClusteringKey> {
    public static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;
    public static final double DEFAULT_SCREEN_DISTANCE = 70.0d;
    private final Set<T> a;
    private final e<T> b;
    private NaverMap c;

    public static class Builder<T extends ClusteringKey> {
        private final ComplexBuilder<T> a = new ComplexBuilder<>();

        public double getScreenDistance() {
            return this.a.getMaxScreenDistance();
        }

        public Builder<T> screenDistance(double screenDistance) {
            this.a.thresholdStrategy(new DefaultThresholdStrategy(screenDistance));
            this.a.maxScreenDistance(screenDistance);
            return this;
        }

        public int getMinZoom() {
            return this.a.getMinClusteringZoom();
        }

        public Builder<T> minZoom(int minZoom) {
            this.a.minClusteringZoom(minZoom);
            return this;
        }

        public int getMaxZoom() {
            return this.a.getMaxClusteringZoom();
        }

        public Builder<T> maxZoom(int maxZoom) {
            this.a.maxClusteringZoom(maxZoom);
            return this;
        }

        public boolean isAnimate() {
            return this.a.getAnimationDuration() > 0;
        }

        public Builder<T> animate(boolean animate) {
            this.a.animationDuration(animate ? 300 : 0);
            return this;
        }

        public ClusterMarkerUpdater getClusterMarkerUpdater() {
            return this.a.getClusterMarkerUpdater();
        }

        public Builder<T> clusterMarkerUpdater(ClusterMarkerUpdater clusterMarkerUpdater) {
            this.a.clusterMarkerUpdater(clusterMarkerUpdater);
            return this;
        }

        public LeafMarkerUpdater getLeafMarkerUpdater() {
            return this.a.getLeafMarkerUpdater();
        }

        public Builder<T> leafMarkerUpdater(LeafMarkerUpdater leafMarkerUpdater) {
            this.a.leafMarkerUpdater(leafMarkerUpdater);
            return this;
        }

        public Clusterer<T> build() {
            return this.a.build();
        }
    }

    public static class ComplexBuilder<T extends ClusteringKey> {
        private ThresholdStrategy a = new DefaultThresholdStrategy(70.0d);
        private DistanceStrategy b = new DefaultDistanceStrategy();
        private PositioningStrategy c = new DefaultPositioningStrategy();
        private TagMergeStrategy d = new DefaultTagMergeStrategy();
        private MarkerManager e = new DefaultMarkerManager();
        private ClusterMarkerUpdater f = new DefaultClusterMarkerUpdater();
        private LeafMarkerUpdater g = new DefaultLeafMarkerUpdater();
        private int h = 0;
        private int i = 20;
        private int j = 0;
        private int k = 20;
        private double l = 70.0d;
        private int m = 300;
        private boolean n = false;

        public ThresholdStrategy getThresholdStrategy() {
            return this.a;
        }

        public ComplexBuilder<T> thresholdStrategy(ThresholdStrategy thresholdStrategy) {
            this.a = thresholdStrategy;
            return this;
        }

        public DistanceStrategy getDistanceStrategy() {
            return this.b;
        }

        public ComplexBuilder<T> distanceStrategy(DistanceStrategy distanceStrategy) {
            this.b = distanceStrategy;
            return this;
        }

        public PositioningStrategy getPositioningStrategy() {
            return this.c;
        }

        public ComplexBuilder<T> positioningStrategy(PositioningStrategy positioningStrategy) {
            this.c = positioningStrategy;
            return this;
        }

        public TagMergeStrategy getTagMergeStrategy() {
            return this.d;
        }

        public ComplexBuilder<T> tagMergeStrategy(TagMergeStrategy tagMergeStrategy) {
            this.d = tagMergeStrategy;
            return this;
        }

        public MarkerManager getMarkerManager() {
            return this.e;
        }

        public ComplexBuilder<T> markerManager(MarkerManager markerManager) {
            this.e = markerManager;
            return this;
        }

        public ClusterMarkerUpdater getClusterMarkerUpdater() {
            return this.f;
        }

        public ComplexBuilder<T> clusterMarkerUpdater(ClusterMarkerUpdater clusterMarkerUpdater) {
            this.f = clusterMarkerUpdater;
            return this;
        }

        public LeafMarkerUpdater getLeafMarkerUpdater() {
            return this.g;
        }

        public ComplexBuilder<T> leafMarkerUpdater(LeafMarkerUpdater leafMarkerUpdater) {
            this.g = leafMarkerUpdater;
            return this;
        }

        public int getMinClusteringZoom() {
            return this.h;
        }

        public ComplexBuilder<T> minClusteringZoom(int minClusteringZoom) {
            this.h = minClusteringZoom;
            return this;
        }

        public int getMaxClusteringZoom() {
            return this.i;
        }

        public ComplexBuilder<T> maxClusteringZoom(int maxClusteringZoom) {
            this.i = maxClusteringZoom;
            return this;
        }

        public int getMinIndexingZoom() {
            return this.j;
        }

        public ComplexBuilder<T> minIndexingZoom(int minIndexingZoom) {
            this.j = minIndexingZoom;
            return this;
        }

        public int getMaxIndexingZoom() {
            return this.k;
        }

        public ComplexBuilder<T> maxIndexingZoom(int maxIndexingZoom) {
            this.k = maxIndexingZoom;
            return this;
        }

        public double getMaxScreenDistance() {
            return this.l;
        }

        public ComplexBuilder<T> maxScreenDistance(double maxScreenDistance) {
            this.l = maxScreenDistance;
            return this;
        }

        public int getAnimationDuration() {
            return this.m;
        }

        public ComplexBuilder<T> animationDuration(int animationDuration) {
            this.m = animationDuration;
            return this;
        }

        public boolean isUpdateOnChange() {
            return this.n;
        }

        public ComplexBuilder<T> updateOnChange(boolean updateOnChange) {
            this.n = updateOnChange;
            return this;
        }

        public Clusterer<T> build() {
            return new Clusterer<>(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n);
        }
    }

    private Clusterer(ThresholdStrategy thresholdStrategy, DistanceStrategy distanceStrategy, PositioningStrategy positioningStrategy, TagMergeStrategy tagMergeStrategy, MarkerManager markerManager, ClusterMarkerUpdater clusterMarkerUpdater, LeafMarkerUpdater leafMarkerUpdater, int minClusteringZoom, int maxClusteringZoom, int minIndexingZoom, int maxIndexingZoom, double maxScreenDistance, int animationDuration, boolean updateOnChange) {
        this.a = new HashSet();
        this.b = new e<>(thresholdStrategy, distanceStrategy, positioningStrategy, tagMergeStrategy, markerManager, clusterMarkerUpdater, leafMarkerUpdater, minClusteringZoom, maxClusteringZoom, minIndexingZoom, maxIndexingZoom, maxScreenDistance, animationDuration, updateOnChange);
    }

    public NaverMap getMap() {
        return this.c;
    }

    public void setMap(NaverMap map) {
        if (this.c == map) {
            return;
        }
        this.c = map;
        this.b.a(map);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean contains(T key) {
        return this.a.contains(key);
    }

    public void clear() {
        clear(null);
    }

    public void clear(OnClustererUpdateCallback callback) {
        this.a.clear();
        this.b.a(callback);
    }

    public void add(T key, Object tag) {
        add(key, tag, null);
    }

    public void add(T key, Object tag, OnClustererUpdateCallback callback) {
        if (contains(key)) {
            return;
        }
        this.a.add(key);
        this.b.a((e<T>) key, tag, callback);
    }

    public void addAll(Map<T, ?> keyTagMap) {
        addAll(keyTagMap, null);
    }

    public void addAll(Map<T, ?> keyTagMap, OnClustererUpdateCallback callback) {
        HashMap map = new HashMap(keyTagMap);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey() == null || contains((ClusteringKey) entry.getKey())) {
                it.remove();
            }
        }
        this.a.addAll(map.keySet());
        this.b.a(keyTagMap, callback);
    }

    public void remove(T key) {
        remove(key, null);
    }

    public void remove(T key, OnClustererUpdateCallback callback) {
        if (!this.a.remove(key)) {
            return;
        }
        this.b.a((e<T>) key, callback);
    }

    public void removeAll(Collection<T> keys) {
        removeAll(keys, null);
    }

    public void removeAll(Collection<T> keys, OnClustererUpdateCallback callback) {
        HashSet hashSet = new HashSet(keys);
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ClusteringKey clusteringKey = (ClusteringKey) it.next();
            if (clusteringKey == null || !contains(clusteringKey)) {
                it.remove();
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            this.a.remove((ClusteringKey) it2.next());
        }
        this.b.a(hashSet, callback);
    }
}
