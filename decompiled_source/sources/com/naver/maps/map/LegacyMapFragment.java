package com.naver.maps.map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public class LegacyMapFragment extends Fragment {
    public static final String ARGUMENT_KEY_OPTIONS = "NaverMapOptions";
    public static final String STATE_MAP_VIEW = "com.naver.maps.map.LegacyMapFragment.MAP_VIEW_STATE";
    private final List<OnMapReadyCallback> a = new ArrayList();
    private MapView b;
    private Bundle c;

    public static LegacyMapFragment newInstance() {
        return new LegacyMapFragment();
    }

    public static LegacyMapFragment newInstance(NaverMapOptions options) {
        LegacyMapFragment legacyMapFragment = new LegacyMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("NaverMapOptions", options);
        legacyMapFragment.setArguments(bundle);
        return legacyMapFragment;
    }

    @Override // android.app.Fragment
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            setArguments(arguments);
        }
        if (arguments.getParcelable("NaverMapOptions") == null) {
            arguments.putParcelable("NaverMapOptions", NaverMapOptions.a(activity, attrs));
        }
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NaverMapOptions naverMapOptions;
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments == null) {
            naverMapOptions = null;
        } else {
            naverMapOptions = (NaverMapOptions) arguments.getParcelable("NaverMapOptions");
        }
        this.b = new MapView(inflater.getContext(), naverMapOptions);
        this.b.setId(R.id.navermap_map_view);
        return this.b;
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.c == null && savedInstanceState != null) {
            this.c = savedInstanceState.getBundle(STATE_MAP_VIEW);
        }
        this.b.onCreate(this.c);
        Iterator<OnMapReadyCallback> it = this.a.iterator();
        while (it.hasNext()) {
            this.b.getMapAsync(it.next());
        }
        this.a.clear();
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.b.onStart();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.b.onResume();
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        this.b.onPause();
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.b != null) {
            this.c = new Bundle();
            this.b.onSaveInstanceState(this.c);
        }
        outState.putBundle(STATE_MAP_VIEW, this.c);
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.b.onStop();
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.c = new Bundle();
        this.b.onSaveInstanceState(this.c);
        this.b.onDestroy();
        this.b = null;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        if (this.b != null) {
            this.b.onLowMemory();
        }
    }

    public void getMapAsync(OnMapReadyCallback callback) {
        if (this.b == null) {
            this.a.add(callback);
        } else {
            this.b.getMapAsync(callback);
        }
    }

    public MapView getMapView() {
        return this.b;
    }
}
