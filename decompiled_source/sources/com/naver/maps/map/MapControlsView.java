package com.naver.maps.map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.IndoorLevelPickerView;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.LogoView;
import com.naver.maps.map.widget.ScaleBarView;
import com.naver.maps.map.widget.ZoomControlView;

/* loaded from: classes.dex */
class MapControlsView extends FrameLayout {
    private CompassView a;
    private ScaleBarView b;
    private ZoomControlView c;
    private LocationButtonView d;
    private IndoorLevelPickerView e;
    private LogoView f;
    private NaverMap g;

    public MapControlsView(Context context) {
        super(context);
        c();
    }

    public MapControlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c();
    }

    public MapControlsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        c();
    }

    private void c() {
        inflate(getContext(), R.layout.navermap_map_controls_view, this);
        this.a = (CompassView) findViewById(R.id.navermap_compass);
        this.b = (ScaleBarView) findViewById(R.id.navermap_scale_bar);
        this.c = (ZoomControlView) findViewById(R.id.navermap_zoom_control);
        this.e = (IndoorLevelPickerView) findViewById(R.id.navermap_indoor_level_picker);
        this.d = (LocationButtonView) findViewById(R.id.navermap_location_button);
        this.f = (LogoView) findViewById(R.id.navermap_logo);
    }

    void a(NaverMap naverMap) {
        this.g = naverMap;
    }

    void a(boolean z) {
        this.a.setMap(z ? this.g : null);
    }

    void b(boolean z) {
        this.b.setMap(z ? this.g : null);
    }

    void c(boolean z) {
        this.c.setMap(z ? this.g : null);
    }

    void d(boolean z) {
        this.e.setMap(z ? this.g : null);
    }

    void e(boolean z) {
        this.d.setMap(z ? this.g : null);
    }

    void f(boolean z) {
        this.f.setMap(z ? this.g : null);
    }

    void g(boolean z) {
        this.f.setClickable(z);
    }

    int a() {
        return ((FrameLayout.LayoutParams) this.f.getLayoutParams()).gravity;
    }

    void a(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.f.getLayoutParams();
        layoutParams.gravity = i;
        this.f.setLayoutParams(layoutParams);
    }

    int[] b() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.f.getLayoutParams();
        return new int[]{layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin};
    }

    void a(int i, int i2, int i3, int i4) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.f.getLayoutParams();
        layoutParams.setMarginStart(i);
        layoutParams.topMargin = i2;
        layoutParams.setMarginEnd(i3);
        layoutParams.bottomMargin = i4;
        this.f.setLayoutParams(layoutParams);
    }
}
