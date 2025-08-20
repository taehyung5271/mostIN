package com.naver.maps.map.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public class ZoomControlView extends LinearLayout {
    private final NaverMap.OnCameraChangeListener a;
    private final NaverMap.OnOptionChangeListener b;
    private final CameraUpdate.FinishCallback c;
    private final CameraUpdate.CancelCallback d;
    private View e;
    private View f;
    private NaverMap g;
    private double h;
    private int i;

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        if (this.g == null) {
            return;
        }
        if (this.i != i) {
            this.h = this.g.getCameraPosition().zoom;
        }
        if (i == 1) {
            this.h += 1.0d;
        } else {
            this.h -= 1.0d;
        }
        this.g.moveCamera(CameraUpdate.zoomTo(this.h).animate(CameraAnimation.Easing).reason(-2).finishCallback(this.c).cancelCallback(this.d));
        this.i = i;
    }

    public ZoomControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.1
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.2
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.c = new CameraUpdate.FinishCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.3
            @Override // com.naver.maps.map.CameraUpdate.FinishCallback
            public void onCameraUpdateFinish() {
                ZoomControlView.this.i = 0;
            }
        };
        this.d = new CameraUpdate.CancelCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.4
            @Override // com.naver.maps.map.CameraUpdate.CancelCallback
            public void onCameraUpdateCancel() {
                ZoomControlView.this.i = 0;
            }
        };
        a();
    }

    public ZoomControlView(Context context) {
        super(context);
        this.a = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.1
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.2
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.c = new CameraUpdate.FinishCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.3
            @Override // com.naver.maps.map.CameraUpdate.FinishCallback
            public void onCameraUpdateFinish() {
                ZoomControlView.this.i = 0;
            }
        };
        this.d = new CameraUpdate.CancelCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.4
            @Override // com.naver.maps.map.CameraUpdate.CancelCallback
            public void onCameraUpdateCancel() {
                ZoomControlView.this.i = 0;
            }
        };
        a();
    }

    public ZoomControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.1
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ZoomControlView.2
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (ZoomControlView.this.g != null) {
                    ZoomControlView.this.a(ZoomControlView.this.g);
                }
            }
        };
        this.c = new CameraUpdate.FinishCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.3
            @Override // com.naver.maps.map.CameraUpdate.FinishCallback
            public void onCameraUpdateFinish() {
                ZoomControlView.this.i = 0;
            }
        };
        this.d = new CameraUpdate.CancelCallback() { // from class: com.naver.maps.map.widget.ZoomControlView.4
            @Override // com.naver.maps.map.CameraUpdate.CancelCallback
            public void onCameraUpdateCancel() {
                ZoomControlView.this.i = 0;
            }
        };
        a();
    }

    private void a() {
        inflate(getContext(), R.layout.navermap_zoom_control_view, this);
        setOrientation(1);
        this.e = findViewById(R.id.navermap_zoom_in);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.naver.maps.map.widget.ZoomControlView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ZoomControlView.this.a(1);
            }
        });
        this.f = findViewById(R.id.navermap_zoom_out);
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.naver.maps.map.widget.ZoomControlView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ZoomControlView.this.a(2);
            }
        });
    }

    public NaverMap getMap() {
        return this.g;
    }

    public void setMap(NaverMap map) {
        if (this.g == map) {
            return;
        }
        if (map == null) {
            setVisibility(8);
            this.g.removeOnCameraChangeListener(this.a);
            this.g.removeOnOptionChangeListener(this.b);
        } else {
            setVisibility(0);
            map.addOnCameraChangeListener(this.a);
            map.addOnOptionChangeListener(this.b);
            a(map);
        }
        this.g = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(NaverMap naverMap) {
        double d = naverMap.getCameraPosition().zoom;
        this.e.setEnabled(naverMap.getMaxZoom() > d);
        this.f.setEnabled(naverMap.getMinZoom() < d);
    }
}
