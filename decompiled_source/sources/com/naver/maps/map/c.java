package com.naver.maps.map;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes.dex */
final class c {
    private final NaverMap a;
    private final UiSettings b;
    private a c;

    c(NaverMap naverMap) {
        this.a = naverMap;
        this.b = naverMap.getUiSettings();
    }

    boolean a(int i, KeyEvent keyEvent) {
        double d = keyEvent.getRepeatCount() >= 5 ? 50.0d : 10.0d;
        switch (i) {
            case 19:
                this.a.moveCamera(CameraUpdate.scrollBy(new PointF(0.0f, (float) d)));
                break;
            case 20:
                this.a.moveCamera(CameraUpdate.scrollBy(new PointF(0.0f, (float) (-d))));
                break;
            case 21:
                this.a.moveCamera(CameraUpdate.scrollBy(new PointF((float) d, 0.0f)));
                break;
            case 22:
                this.a.moveCamera(CameraUpdate.scrollBy(new PointF((float) (-d), 0.0f)));
                break;
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                keyEvent.startTracking();
                break;
        }
        return true;
    }

    boolean b(int i, KeyEvent keyEvent) {
        switch (i) {
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                if (this.b.isZoomGesturesEnabled()) {
                    this.a.moveCamera(CameraUpdate.zoomOut().a(new PointF(this.a.getWidth() / 2, this.a.getHeight() / 2)));
                    break;
                }
                break;
        }
        return false;
    }

    boolean c(int i, KeyEvent keyEvent) {
        if (keyEvent.isCanceled()) {
            return false;
        }
        switch (i) {
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                if (this.b.isZoomGesturesEnabled()) {
                    this.a.moveCamera(CameraUpdate.zoomIn().a(new PointF(this.a.getWidth() / 2, this.a.getHeight() / 2)));
                    break;
                }
                break;
        }
        return false;
    }

    boolean a(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (this.c != null) {
                    this.c.a();
                    this.c = null;
                }
                this.c = new a();
                new Handler(Looper.getMainLooper()).postDelayed(this.c, ViewConfiguration.getLongPressTimeout());
                return true;
            case 1:
                if (!this.b.isZoomGesturesEnabled()) {
                    return false;
                }
                if (this.c != null) {
                    this.a.moveCamera(CameraUpdate.zoomIn().a(new PointF(this.a.getWidth() / 2, this.a.getHeight() / 2)));
                }
                return true;
            case 2:
                this.a.moveCamera(CameraUpdate.scrollBy(new PointF((float) (motionEvent.getX() * (-10.0d)), (float) (motionEvent.getY() * (-10.0d)))));
                return true;
            case 3:
                if (this.c != null) {
                    this.c.a();
                    this.c = null;
                }
                return true;
            default:
                return false;
        }
    }

    private class a implements Runnable {
        private boolean b = false;

        a() {
        }

        public void a() {
            this.b = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.b) {
                c.this.a.moveCamera(CameraUpdate.zoomOut().a(new PointF(c.this.a.getWidth() / 2, c.this.a.getHeight() / 2)));
                c.this.c = null;
            }
        }
    }
}
