package com.naver.maps.map.widget;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;

/* loaded from: classes.dex */
public class LocationButtonView extends FrameLayout {
    private final NaverMap.OnOptionChangeListener a;
    private final NaverMap.OnLocationChangeListener b;
    private ImageView c;
    private View d;
    private CircularProgressDrawable e;
    private NaverMap f;

    private static int a(NaverMap naverMap) {
        switch (naverMap.getLocationTrackingMode()) {
            case None:
                return R.drawable.navermap_location_none;
            case NoFollow:
                return R.drawable.navermap_location_no_follow;
            case Follow:
                return R.drawable.navermap_location_follow;
            case Face:
                return R.drawable.navermap_location_face;
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LocationTrackingMode b(LocationTrackingMode locationTrackingMode) {
        switch (locationTrackingMode) {
            case None:
            case NoFollow:
                return LocationTrackingMode.Follow;
            case Follow:
                return LocationTrackingMode.Face;
            case Face:
                return LocationTrackingMode.None;
            default:
                throw new AssertionError();
        }
    }

    public LocationButtonView(Context context) {
        super(context);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.b(LocationButtonView.this.f);
                }
            }
        };
        this.b = new NaverMap.OnLocationChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.2
            @Override // com.naver.maps.map.NaverMap.OnLocationChangeListener
            public void onLocationChange(Location location) {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.c();
                }
            }
        };
        a();
    }

    public LocationButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.b(LocationButtonView.this.f);
                }
            }
        };
        this.b = new NaverMap.OnLocationChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.2
            @Override // com.naver.maps.map.NaverMap.OnLocationChangeListener
            public void onLocationChange(Location location) {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.c();
                }
            }
        };
        a();
    }

    public LocationButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = new NaverMap.OnOptionChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.1
            @Override // com.naver.maps.map.NaverMap.OnOptionChangeListener
            public void onOptionChange() {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.b(LocationButtonView.this.f);
                }
            }
        };
        this.b = new NaverMap.OnLocationChangeListener() { // from class: com.naver.maps.map.widget.LocationButtonView.2
            @Override // com.naver.maps.map.NaverMap.OnLocationChangeListener
            public void onLocationChange(Location location) {
                if (LocationButtonView.this.f != null) {
                    LocationButtonView.this.c();
                }
            }
        };
        a();
    }

    private void a() {
        inflate(getContext(), R.layout.navermap_location_button_view, this);
        this.c = (ImageView) findViewById(R.id.navermap_location_icon);
        this.d = findViewById(R.id.navermap_location_icon_progress_overlay);
        this.e = new CircularProgressDrawable(getContext());
        this.e.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.navermap_location_button_progress, getContext().getTheme()));
        ViewCompat.setBackground(this.d, this.e);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.naver.maps.map.widget.LocationButtonView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LocationButtonView.this.f != null && LocationButtonView.this.f.getLocationSource() != null) {
                    LocationTrackingMode locationTrackingMode = LocationButtonView.this.f.getLocationTrackingMode();
                    LocationTrackingMode locationTrackingModeB = LocationButtonView.b(locationTrackingMode);
                    if (locationTrackingMode == LocationTrackingMode.None) {
                        LocationButtonView.this.b();
                    } else if (locationTrackingModeB == LocationTrackingMode.None) {
                        LocationButtonView.this.c();
                    }
                    LocationButtonView.this.f.setLocationTrackingMode(locationTrackingModeB);
                }
            }
        });
    }

    public NaverMap getMap() {
        return this.f;
    }

    public void setMap(NaverMap map) {
        if (this.f == map) {
            return;
        }
        if (map == null) {
            setVisibility(8);
            this.f.removeOnOptionChangeListener(this.a);
        } else {
            setVisibility(0);
            map.addOnOptionChangeListener(this.a);
            b(map);
        }
        this.f = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(NaverMap naverMap) {
        if (naverMap.getLocationTrackingMode() == LocationTrackingMode.None) {
            c();
        }
        if (naverMap.getLocationSource() == null) {
            this.c.setImageResource(R.drawable.navermap_location_disabled);
            this.c.setEnabled(false);
        } else {
            this.c.setImageResource(a(naverMap));
            this.c.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.d.setVisibility(0);
        this.e.start();
        if (this.f != null) {
            this.f.addOnLocationChangeListener(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.e.stop();
        this.d.setVisibility(8);
        if (this.f != null) {
            this.f.removeOnLocationChangeListener(this.b);
        }
    }
}
