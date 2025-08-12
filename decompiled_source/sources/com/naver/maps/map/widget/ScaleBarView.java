package com.naver.maps.map.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public class ScaleBarView extends FrameLayout {
    private static final int[] a = {5, 2, 1};
    private final NaverMap.OnOptionChangeListener b;
    private final NaverMap.OnCameraChangeListener c;
    private TextView d;
    private View e;
    private TextView f;
    private TextView g;
    private View h;
    private int i;
    private boolean j;
    private NaverMap k;
    private boolean l;

    private static int a(int i) {
        for (int i2 : a) {
            if (i >= i2) {
                return i2;
            }
        }
        return a[a.length - 1];
    }

    public ScaleBarView(Context context) {
        super(context);
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() throws Resources.NotFoundException {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.b(ScaleBarView.this.k);
                }
            }
        };
        this.c = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.a(ScaleBarView.this.k);
                }
            }
        };
        a((AttributeSet) null, 0);
    }

    public ScaleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() throws Resources.NotFoundException {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.b(ScaleBarView.this.k);
                }
            }
        };
        this.c = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.a(ScaleBarView.this.k);
                }
            }
        };
        a(attrs, 0);
    }

    public ScaleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.b = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() throws Resources.NotFoundException {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.b(ScaleBarView.this.k);
                }
            }
        };
        this.c = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.widget.ScaleBarView.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (ScaleBarView.this.k != null) {
                    ScaleBarView.this.a(ScaleBarView.this.k);
                }
            }
        };
        a(attrs, defStyleAttr);
    }

    private void a(AttributeSet attributeSet, int i) {
        boolean z;
        inflate(getContext(), R.layout.navermap_scale_bar_view, this);
        this.d = (TextView) findViewById(R.id.navermap_zero);
        this.e = findViewById(R.id.navermap_scale_container);
        this.f = (TextView) findViewById(R.id.navermap_value);
        this.g = (TextView) findViewById(R.id.navermap_unit);
        this.h = findViewById(R.id.navermap_bar);
        this.i = getResources().getDimensionPixelSize(R.dimen.navermap_scale_bar_min_width);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.NaverMapScaleBarView, i, 0);
            try {
                z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NaverMapScaleBarView_navermap_rtlEnabled, false);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        } else {
            z = true;
        }
        setRightToLeftEnabled(z);
    }

    public boolean isRightToLeftEnabled() {
        return this.j;
    }

    public void setRightToLeftEnabled(boolean enabled) {
        this.j = enabled;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.d.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.e.getLayoutParams();
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.h.getLayoutParams();
        if (this.j) {
            layoutParams.gravity = 5;
            layoutParams3.gravity = 5;
            layoutParams2.gravity = 5;
            ViewGroup.LayoutParams layoutParams4 = this.f.getLayoutParams();
            layoutParams4.width = -2;
            this.f.setLayoutParams(layoutParams4);
        } else {
            layoutParams.gravity = 3;
            layoutParams3.gravity = 3;
            layoutParams2.gravity = 3;
            ViewGroup.LayoutParams layoutParams5 = this.f.getLayoutParams();
            layoutParams5.width = -2;
            this.f.setLayoutParams(layoutParams5);
        }
        this.d.setLayoutParams(layoutParams);
        this.h.setLayoutParams(layoutParams3);
        this.e.setLayoutParams(layoutParams2);
        if (this.k != null) {
            a(this.k);
        }
    }

    public NaverMap getMap() {
        return this.k;
    }

    public void setMap(NaverMap map) {
        if (this.k == map) {
            return;
        }
        if (map == null) {
            setVisibility(8);
            this.k.removeOnOptionChangeListener(this.b);
            this.k.removeOnCameraChangeListener(this.c);
        } else {
            setVisibility(0);
            map.addOnOptionChangeListener(this.b);
            map.addOnCameraChangeListener(this.c);
            a(map);
        }
        this.k = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(NaverMap naverMap) {
        int i;
        double metersPerPixel = naverMap.getProjection().getMetersPerPixel() * this.i;
        int iFloor = ((int) Math.floor(Math.log10(metersPerPixel))) + 1;
        int iPow = (int) Math.pow(10.0d, iFloor - 1);
        double d = metersPerPixel / iPow;
        int iA = a((int) d);
        int i2 = iPow * iA;
        if (iFloor >= 4) {
            i2 /= 1000;
            i = R.string.navermap_scale_km;
        } else {
            i = R.string.navermap_scale_m;
        }
        this.f.setText(String.valueOf(i2));
        this.g.setText(i);
        int i3 = (int) (this.i * (iA / d));
        TextView textView = this.j ? this.g : this.f;
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = i3;
        textView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.h.getLayoutParams();
        layoutParams2.width = i3 + this.h.getPaddingLeft() + this.h.getPaddingRight();
        this.h.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(NaverMap naverMap) throws Resources.NotFoundException {
        if (this.l == naverMap.isDark()) {
            return;
        }
        this.l = !this.l;
        int color = ResourcesCompat.getColor(getResources(), this.l ? R.color.navermap_scale_bar_text_dark : R.color.navermap_scale_bar_text_light, getContext().getTheme());
        this.d.setTextColor(color);
        this.f.setTextColor(color);
        this.g.setTextColor(color);
        this.h.setBackgroundResource(this.l ? R.drawable.navermap_scale_bar_dark : R.drawable.navermap_scale_bar_light);
    }
}
