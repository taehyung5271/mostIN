package com.naver.maps.map.clustering;

import android.graphics.PointF;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

/* loaded from: classes.dex */
public class DefaultClusterMarkerUpdater implements ClusterMarkerUpdater {
    public static final PointF DEFAULT_CLUSTER_ANCHOR = new PointF(0.5f, 0.5f);

    @Override // com.naver.maps.map.clustering.ClusterMarkerUpdater
    public void updateClusterMarker(ClusterMarkerInfo info, Marker marker) {
        int size = info.getSize();
        if (size < 10) {
            marker.setIcon(MarkerIcons.CLUSTER_LOW_DENSITY);
        } else if (size < 100) {
            marker.setIcon(MarkerIcons.CLUSTER_MEDIUM_DENSITY);
        } else {
            marker.setIcon(MarkerIcons.CLUSTER_HIGH_DENSITY);
        }
        marker.setAnchor(DEFAULT_CLUSTER_ANCHOR);
        marker.setCaptionText(Integer.toString(size));
        marker.setCaptionAligns(Align.Center);
        marker.setCaptionColor(-1);
        marker.setCaptionHaloColor(0);
        marker.setOnClickListener(new DefaultClusterOnClickListener(info));
    }
}
