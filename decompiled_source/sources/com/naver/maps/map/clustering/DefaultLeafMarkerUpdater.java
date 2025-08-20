package com.naver.maps.map.clustering;

import androidx.core.view.ViewCompat;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;

/* loaded from: classes.dex */
public class DefaultLeafMarkerUpdater implements LeafMarkerUpdater {
    @Override // com.naver.maps.map.clustering.LeafMarkerUpdater
    public void updateLeafMarker(LeafMarkerInfo info, Marker marker) {
        marker.setIcon(Marker.DEFAULT_ICON);
        marker.setAnchor(Marker.DEFAULT_ANCHOR);
        marker.setCaptionText("");
        marker.setCaptionAligns(Align.Bottom);
        marker.setCaptionColor(ViewCompat.MEASURED_STATE_MASK);
        marker.setCaptionHaloColor(-1);
        marker.setOnClickListener(null);
    }
}
