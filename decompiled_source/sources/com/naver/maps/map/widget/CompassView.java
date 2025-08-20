package com.naver.maps.map.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.CameraUpdateParams;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public class CompassView extends LinearLayout {
    private final Animator.AnimatorListener a;
    private final NaverMap.OnCameraChangeListener b;
    private View c;
    private NaverMap d;
    private ViewPropertyAnimator e;

    public CompassView(Context context) {
        super(context);
        this.a = new AnimatorListenerAdapter() { // from class: com.naver.maps.map.widget.CompassView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CompassView.this.c.setVisibility(8);
                CompassView.this.c.setAlpha(1.0f);
                CompassView.this.c.setEnabled(true);
                CompassView.this.e = null;
            }
        };
        this.b = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.CompassView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (CompassView.this.d != null) {
                    CompassView.this.a(CompassView.this.d);
                }
            }
        };
        a();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = new AnimatorListenerAdapter() { // from class: com.naver.maps.map.widget.CompassView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CompassView.this.c.setVisibility(8);
                CompassView.this.c.setAlpha(1.0f);
                CompassView.this.c.setEnabled(true);
                CompassView.this.e = null;
            }
        };
        this.b = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.CompassView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (CompassView.this.d != null) {
                    CompassView.this.a(CompassView.this.d);
                }
            }
        };
        a();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = new AnimatorListenerAdapter() { // from class: com.naver.maps.map.widget.CompassView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CompassView.this.c.setVisibility(8);
                CompassView.this.c.setAlpha(1.0f);
                CompassView.this.c.setEnabled(true);
                CompassView.this.e = null;
            }
        };
        this.b = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.CompassView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (CompassView.this.d != null) {
                    CompassView.this.a(CompassView.this.d);
                }
            }
        };
        a();
    }

    private void a() {
        inflate(getContext(), R.layout.navermap_compass_view, this);
        this.c = findViewById(R.id.navermap_compass_icon);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.naver.maps.map.widget.CompassView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (CompassView.this.d == null) {
                    return;
                }
                CompassView.this.d.moveCamera(CameraUpdate.withParams(new CameraUpdateParams().tiltTo(0.0d).rotateTo(0.0d)).animate(CameraAnimation.Easing).reason(-2));
            }
        });
        if (isInEditMode()) {
            this.c.setVisibility(0);
        }
    }

    public NaverMap getMap() {
        return this.d;
    }

    public void setMap(NaverMap map) {
        if (this.d == map) {
            return;
        }
        if (map == null) {
            setVisibility(8);
            this.d.removeOnCameraChangeListener(this.b);
            if (this.e != null) {
                this.e.cancel();
            }
        } else {
            setVisibility(0);
            map.addOnCameraChangeListener(this.b);
            a(map);
        }
        this.d = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        this.c.setRotation(-((float) cameraPosition.bearing));
        this.c.setRotationX(((float) cameraPosition.tilt) * 0.7f);
        if (cameraPosition.tilt == 0.0d && cameraPosition.bearing == 0.0d) {
            if (this.e == null && this.c.getVisibility() == 0) {
                this.c.setEnabled(false);
                this.e = this.c.animate().setStartDelay(500L).setDuration(500L).alpha(0.0f).setListener(this.a);
                return;
            }
            return;
        }
        if (this.e != null) {
            this.e.cancel();
        }
        this.c.setVisibility(0);
    }
}
