package com.naver.maps.map.clustering;

import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Overlay;

/* loaded from: classes.dex */
public class DefaultClusterOnClickListener implements Overlay.OnClickListener {
    private final MarkerInfo a;

    public DefaultClusterOnClickListener(MarkerInfo info) {
        this.a = info;
    }

    @Override // com.naver.maps.map.overlay.Overlay.OnClickListener
    public boolean onClick(Overlay overlay) {
        NaverMap map = overlay.getMap();
        if (map != null) {
            map.moveCamera(CameraUpdate.scrollAndZoomTo(this.a.getPosition(), this.a.getMaxZoom() + 1).animate(CameraAnimation.Easing));
        }
        return true;
    }
}
