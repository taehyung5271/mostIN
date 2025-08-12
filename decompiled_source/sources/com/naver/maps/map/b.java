package com.naver.maps.map;

import android.location.Location;
import android.os.Bundle;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.OverlayImage;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
final class b {
    private final NaverMap a;
    private final List<NaverMap.OnLocationChangeListener> b = new CopyOnWriteArrayList();
    private final LocationSource.OnLocationChangedListener c = new LocationSource.OnLocationChangedListener() { // from class: com.naver.maps.map.b.1
        @Override // com.naver.maps.map.LocationSource.OnLocationChangedListener
        public void onLocationChanged(Location location) {
            b.this.a(location);
        }
    };
    private LocationTrackingMode d = LocationTrackingMode.None;
    private NaverMap.OnCameraChangeListener e;
    private LocationSource f;
    private Location g;

    private static OverlayImage b(LocationTrackingMode locationTrackingMode) {
        switch (locationTrackingMode) {
            case None:
            case NoFollow:
                return null;
            case Follow:
                return LocationOverlay.DEFAULT_SUB_ICON_ARROW;
            case Face:
                return LocationOverlay.DEFAULT_SUB_ICON_CONE;
            default:
                throw new AssertionError();
        }
    }

    b(NaverMap naverMap) {
        this.a = naverMap;
    }

    void a() {
        if (this.e != null) {
            return;
        }
        this.e = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.b.2
            @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
            public void onCameraChange(int reason, boolean animated) {
                if (reason != -3 && b.this.d != LocationTrackingMode.None) {
                    b.this.a.setLocationTrackingMode(LocationTrackingMode.NoFollow);
                }
            }
        };
        this.a.addOnCameraChangeListener(this.e);
    }

    void a(NaverMap.OnLocationChangeListener onLocationChangeListener) {
        this.b.add(onLocationChangeListener);
        if (this.g != null) {
            onLocationChangeListener.onLocationChange(this.g);
        }
    }

    void b(NaverMap.OnLocationChangeListener onLocationChangeListener) {
        this.b.remove(onLocationChangeListener);
    }

    LocationTrackingMode b() {
        return this.d;
    }

    boolean a(LocationTrackingMode locationTrackingMode) {
        if (this.f == null || this.d == locationTrackingMode) {
            return false;
        }
        this.d = locationTrackingMode;
        if (locationTrackingMode == LocationTrackingMode.None) {
            this.g = null;
            this.f.deactivate();
            this.a.getLocationOverlay().setVisible(false);
        } else {
            this.f.activate(this.c);
            if (locationTrackingMode != LocationTrackingMode.NoFollow) {
                this.a.cancelTransitions(-3);
                if (this.a.getLocationOverlay().isVisible()) {
                    this.a.moveCamera(CameraUpdate.scrollTo(this.a.getLocationOverlay().getPosition()).animate(CameraAnimation.Easing).reason(-3));
                }
            }
        }
        this.a.getLocationOverlay().setSubIcon(b(locationTrackingMode));
        return true;
    }

    LocationSource c() {
        return this.f;
    }

    boolean a(LocationSource locationSource) {
        if (this.f == locationSource) {
            return false;
        }
        if (locationSource == null) {
            a(LocationTrackingMode.None);
        } else if (this.d != LocationTrackingMode.None) {
            if (this.f != null) {
                this.f.deactivate();
            }
            locationSource.activate(this.c);
        }
        this.f = locationSource;
        return true;
    }

    void d() {
        if (this.d != LocationTrackingMode.None && this.f != null) {
            this.f.activate(this.c);
        }
    }

    void e() {
        if (this.d != LocationTrackingMode.None && this.f != null) {
            this.f.deactivate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Location location) {
        if (this.d == LocationTrackingMode.None || location == null) {
            return;
        }
        LatLng latLng = new LatLng(location);
        float bearing = location.getBearing();
        LocationOverlay locationOverlay = this.a.getLocationOverlay();
        locationOverlay.setPosition(latLng);
        locationOverlay.setBearing(bearing);
        locationOverlay.setVisible(true);
        if (this.d != LocationTrackingMode.NoFollow) {
            CameraUpdateParams cameraUpdateParamsScrollTo = new CameraUpdateParams().scrollTo(latLng);
            if (this.d == LocationTrackingMode.Face) {
                cameraUpdateParamsScrollTo.rotateTo(bearing);
            }
            this.a.moveCamera(CameraUpdate.withParams(cameraUpdateParamsScrollTo).animate(CameraAnimation.Easing).reason(-3));
        }
        this.g = location;
        Iterator<NaverMap.OnLocationChangeListener> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().onLocationChange(location);
        }
    }

    void a(Bundle bundle) {
        bundle.putSerializable("LocationTracker00", this.d);
    }

    void b(Bundle bundle) {
        LocationTrackingMode locationTrackingMode = (LocationTrackingMode) bundle.getSerializable("LocationTracker00");
        if (locationTrackingMode != null) {
            a(locationTrackingMode);
        }
    }
}
