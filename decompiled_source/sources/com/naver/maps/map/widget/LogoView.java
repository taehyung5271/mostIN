package com.naver.maps.map.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public class LogoView extends AppCompatImageView {
    private final NaverMap.OnOptionChangeListener a;
    private NaverMap b;
    private boolean c;

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LogoView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LogoView.this.b != null) {
                    LogoView.this.a(LogoView.this.b);
                }
            }
        };
        a();
    }

    public LogoView(Context context) {
        super(context);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LogoView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LogoView.this.b != null) {
                    LogoView.this.a(LogoView.this.b);
                }
            }
        };
        a();
    }

    public LogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LogoView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LogoView.this.b != null) {
                    LogoView.this.a(LogoView.this.b);
                }
            }
        };
        a();
    }

    private void a() {
        setContentDescription(getResources().getString(R.string.navermap_naver_logo));
        setImageResource(R.drawable.navermap_naver_logo_light);
        setOnClickListener(new View.OnClickListener() { // from class: com.naver.maps.map.widget.LogoView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                new AlertDialog.Builder(LogoView.this.getContext()).setView(new InfoView(LogoView.this.getContext())).show();
            }
        });
    }

    public void setMap(NaverMap map) {
        if (this.b == map) {
            return;
        }
        if (map == null) {
            setVisibility(8);
            this.b.removeOnOptionChangeListener(this.a);
        } else {
            setVisibility(0);
            map.addOnOptionChangeListener(this.a);
            a(map);
        }
        this.b = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(NaverMap naverMap) {
        if (this.c == naverMap.isDark()) {
            return;
        }
        this.c = !this.c;
        setImageResource(this.c ? R.drawable.navermap_naver_logo_dark : R.drawable.navermap_naver_logo_light);
    }
}
