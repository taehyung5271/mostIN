package com.naver.maps.map.renderer;

/* loaded from: classes.dex */
public interface MapRendererScheduler {
    void queueEvent(Runnable runnable);

    void requestRender();
}
