package com.naver.maps.map.overlay;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public final class InfoWindow extends Overlay {
    public static final int DEFAULT_GLOBAL_Z_INDEX = 400000;
    private Adapter b = DEFAULT_ADAPTER;
    private Marker c;
    private OverlayImage d;
    private static final OverlayImage a = OverlayImage.fromResource(R.drawable.navermap_default_info_window_icon);
    public static final Adapter DEFAULT_ADAPTER = new Adapter() { // from class: com.naver.maps.map.overlay.InfoWindow.1
        @Override // com.naver.maps.map.overlay.InfoWindow.Adapter
        public OverlayImage getImage(InfoWindow infoWindow) {
            return InfoWindow.a;
        }
    };
    public static final PointF DEFAULT_ANCHOR = new PointF(0.5f, 1.0f);

    public static abstract class Adapter {
        public abstract OverlayImage getImage(InfoWindow infoWindow);
    }

    private native void nativeCreate();

    private native void nativeDestroy();

    private native int nativeGetAlign();

    private native float nativeGetAlpha();

    private native PointF nativeGetAnchor();

    private native int nativeGetOffsetX();

    private native int nativeGetOffsetY();

    private native LatLng nativeGetPosition();

    private native void nativeSetAlign(int i);

    private native void nativeSetAlpha(float f);

    private native void nativeSetAnchor(float f, float f2);

    private native void nativeSetImage(OverlayImage overlayImage);

    private native void nativeSetMarker(long j);

    private native void nativeSetOffsetX(int i);

    private native void nativeSetOffsetY(int i);

    private native void nativeSetPosition(double d, double d2);

    public static abstract class ViewAdapter extends Adapter {
        public abstract View getView(InfoWindow infoWindow);

        @Override // com.naver.maps.map.overlay.InfoWindow.Adapter
        public final OverlayImage getImage(InfoWindow infoWindow) {
            return OverlayImage.fromView(getView(infoWindow));
        }
    }

    public static abstract class DefaultViewAdapter extends ViewAdapter {
        private final Context a;
        private final ViewGroup b;
        private View c;

        protected abstract View getContentView(InfoWindow infoWindow);

        public DefaultViewAdapter(Context context) {
            this.a = context;
            this.b = new LinearLayout(context);
            this.b.setBackgroundResource(R.drawable.navermap_default_info_window_background);
        }

        public final Context getContext() {
            return this.a;
        }

        @Override // com.naver.maps.map.overlay.InfoWindow.ViewAdapter
        public final View getView(InfoWindow infoWindow) {
            View contentView = getContentView(infoWindow);
            if (this.c != contentView) {
                this.c = contentView;
                this.b.removeAllViews();
                this.b.addView(contentView);
            }
            return this.b;
        }
    }

    public static abstract class DefaultTextAdapter extends DefaultViewAdapter {
        private final TextView a;

        public abstract CharSequence getText(InfoWindow infoWindow);

        public DefaultTextAdapter(Context context) {
            super(context);
            this.a = new TextView(context);
            this.a.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }

        @Override // com.naver.maps.map.overlay.InfoWindow.DefaultViewAdapter
        public final View getContentView(InfoWindow infoWindow) {
            this.a.setText(getText(infoWindow));
            return this.a;
        }
    }

    public InfoWindow() {
    }

    public InfoWindow(Adapter adapter) {
        setAdapter(adapter);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a() {
        nativeCreate();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b() {
        nativeDestroy();
    }

    public void open(Marker marker) {
        open(marker, Align.Top);
    }

    public void open(Marker marker, Align align) {
        nativeSetAlign(align.ordinal());
        if (this.c == marker || marker.getMap() == null) {
            return;
        }
        if (this.c != null) {
            this.c.a((InfoWindow) null);
        }
        InfoWindow infoWindow = marker.getInfoWindow();
        if (infoWindow != null && infoWindow != this) {
            infoWindow.close();
        }
        this.c = marker;
        marker.a(this);
        nativeSetMarker(marker.d());
        c(marker.getMap());
    }

    public void open(NaverMap map) {
        if (isAdded()) {
            a("position", getPosition());
        }
        g();
        c(map);
    }

    public void close() {
        if (!isAdded()) {
            return;
        }
        c(null);
    }

    public Marker getMarker() {
        e();
        return this.c;
    }

    public Align getAlign() {
        e();
        return Align.values()[nativeGetAlign()];
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public void setMap(NaverMap map) {
        if (map == null) {
            close();
        } else {
            open(map);
        }
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public int getGlobalZIndex() {
        return super.getGlobalZIndex();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    public void setGlobalZIndex(int globalZIndex) {
        super.setGlobalZIndex(globalZIndex);
    }

    public LatLng getPosition() {
        e();
        return nativeGetPosition();
    }

    public void setPosition(LatLng position) {
        e();
        a("position", position);
        nativeSetPosition(position.latitude, position.longitude);
    }

    public Adapter getAdapter() {
        e();
        return this.b;
    }

    public void setAdapter(Adapter adapter) {
        e();
        this.b = adapter;
        if (isAdded()) {
            f();
        }
    }

    public PointF getAnchor() {
        e();
        return nativeGetAnchor();
    }

    public void setAnchor(PointF anchor) {
        e();
        nativeSetAnchor(anchor.x, anchor.y);
    }

    public int getOffsetX() {
        e();
        return nativeGetOffsetX();
    }

    public void setOffsetX(int offset) {
        e();
        nativeSetOffsetX(offset);
    }

    public int getOffsetY() {
        e();
        return nativeGetOffsetY();
    }

    public void setOffsetY(int offset) {
        e();
        nativeSetOffsetY(offset);
    }

    public float getAlpha() {
        e();
        return nativeGetAlpha();
    }

    public void setAlpha(float alpha) {
        e();
        nativeSetAlpha(alpha);
    }

    public void invalidate() {
        if (isAdded()) {
            e();
            f();
        }
    }

    private void c(NaverMap naverMap) {
        if (naverMap != null && getMap() == naverMap) {
            f();
            return;
        }
        super.setMap(naverMap);
        if (naverMap == null) {
            g();
        }
    }

    private void f() {
        OverlayImage image = this.b.getImage(this);
        if (image.equals(this.d)) {
            return;
        }
        this.d = image;
        nativeSetImage(image);
    }

    private void g() {
        if (this.c == null) {
            return;
        }
        this.c.a((InfoWindow) null);
        this.c = null;
        nativeSetMarker(0L);
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void a(NaverMap naverMap) {
        if (this.c == null) {
            a("position", getPosition());
        }
        super.a(naverMap);
        f();
    }

    @Override // com.naver.maps.map.overlay.Overlay
    protected void b(NaverMap naverMap) {
        this.d = null;
        nativeSetImage(null);
        super.b(naverMap);
    }
}
