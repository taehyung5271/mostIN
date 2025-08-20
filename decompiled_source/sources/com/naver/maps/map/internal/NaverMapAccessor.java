package com.naver.maps.map.internal;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Overlay;

/* loaded from: classes.dex */
public interface NaverMapAccessor {
    void addOverlay(NaverMap naverMap, Overlay overlay, long j);

    Thread getThread(NaverMap naverMap);

    void removeOverlay(NaverMap naverMap, Overlay overlay, long j);
}
