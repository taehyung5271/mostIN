package com.naver.maps.map;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MapFragment extends Fragment {
    public static final String ARGUMENT_KEY_OPTIONS = "NaverMapOptions";
    public static final String STATE_MAP_VIEW = "com.naver.maps.map.MapFragment.MAP_VIEW_STATE";
    private final List<OnMapReadyCallback> a = new ArrayList();
    private MapView b;
    private Bundle c;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(NaverMapOptions options) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("NaverMapOptions", options);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            setArguments(arguments);
        }
        if (arguments.getParcelable("NaverMapOptions") == null) {
            arguments.putParcelable("NaverMapOptions", NaverMapOptions.a(context, attrs));
        }
    }

    @Override // androidx.fragment.app.Fragment
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

    @Override // androidx.fragment.app.Fragment
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

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.b.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.b.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.b.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.b != null) {
            this.c = new Bundle();
            this.b.onSaveInstanceState(this.c);
        }
        outState.putBundle(STATE_MAP_VIEW, this.c);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.b.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.c = new Bundle();
        this.b.onSaveInstanceState(this.c);
        this.b.onDestroy();
        this.b = null;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
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
