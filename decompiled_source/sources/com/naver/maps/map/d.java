package com.naver.maps.map;

import android.graphics.PointF;
import android.view.MotionEvent;

/* loaded from: classes.dex */
final class d {
    private final NaverMap a;

    d(NaverMap naverMap) {
        this.a = naverMap;
    }

    boolean a(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 2) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 8:
                this.a.moveCamera(CameraUpdate.zoomBy(motionEvent.getAxisValue(9)).a(new PointF(motionEvent.getX(), motionEvent.getY())));
                return true;
            default:
                return false;
        }
    }
}
