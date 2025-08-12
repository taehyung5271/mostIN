package com.naver.maps.map.clustering;

import com.naver.maps.map.overlay.Marker;
import java.util.ArrayDeque;
import java.util.Deque;

/* loaded from: classes.dex */
public class DefaultMarkerManager implements MarkerManager {
    public static final int DEFAULT_MAX_POOL_SIZE = 200;
    private final int a;
    private final Deque<Marker> b;

    public DefaultMarkerManager() {
        this(200);
    }

    public DefaultMarkerManager(int maxPoolSize) {
        this.a = maxPoolSize;
        this.b = new ArrayDeque(maxPoolSize);
    }

    @Override // com.naver.maps.map.clustering.MarkerManager
    public final Marker retainMarker(MarkerInfo info) {
        Marker markerPoll = this.b.poll();
        if (markerPoll != null) {
            return markerPoll;
        }
        return createMarker();
    }

    @Override // com.naver.maps.map.clustering.MarkerManager
    public final void releaseMarker(MarkerInfo info, Marker marker) {
        if (this.b.size() < this.a) {
            this.b.push(marker);
        }
    }

    public Marker createMarker() {
        return new Marker();
    }
}
