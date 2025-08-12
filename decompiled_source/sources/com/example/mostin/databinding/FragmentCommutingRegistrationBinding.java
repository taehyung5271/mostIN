package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;
import com.naver.maps.map.MapView;

/* loaded from: classes5.dex */
public final class FragmentCommutingRegistrationBinding implements ViewBinding {
    public final View binder1;
    public final View binder2;
    public final View binder3;
    public final Button btnClockIn;
    public final TextView commutingWorkPlace;
    public final MapView mapView;
    public final TextView recentDeparture;
    public final TextView recentEntry;
    private final FrameLayout rootView;

    private FragmentCommutingRegistrationBinding(FrameLayout rootView, View binder1, View binder2, View binder3, Button btnClockIn, TextView commutingWorkPlace, MapView mapView, TextView recentDeparture, TextView recentEntry) {
        this.rootView = rootView;
        this.binder1 = binder1;
        this.binder2 = binder2;
        this.binder3 = binder3;
        this.btnClockIn = btnClockIn;
        this.commutingWorkPlace = commutingWorkPlace;
        this.mapView = mapView;
        this.recentDeparture = recentDeparture;
        this.recentEntry = recentEntry;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static FragmentCommutingRegistrationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentCommutingRegistrationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_commuting_registration, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentCommutingRegistrationBinding bind(View rootView) throws Resources.NotFoundException {
        View binder2;
        View binder3;
        int id = R.id.binder_1;
        View binder1 = ViewBindings.findChildViewById(rootView, id);
        if (binder1 != null && (binder2 = ViewBindings.findChildViewById(rootView, (id = R.id.binder_2))) != null && (binder3 = ViewBindings.findChildViewById(rootView, (id = R.id.binder_3))) != null) {
            id = R.id.btn_clock_in;
            Button btnClockIn = (Button) ViewBindings.findChildViewById(rootView, id);
            if (btnClockIn != null) {
                id = R.id.commuting_work_place;
                TextView commutingWorkPlace = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (commutingWorkPlace != null) {
                    id = R.id.map_view;
                    MapView mapView = (MapView) ViewBindings.findChildViewById(rootView, id);
                    if (mapView != null) {
                        id = R.id.recentDeparture;
                        TextView recentDeparture = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (recentDeparture != null) {
                            id = R.id.recentEntry;
                            TextView recentEntry = (TextView) ViewBindings.findChildViewById(rootView, id);
                            if (recentEntry != null) {
                                return new FragmentCommutingRegistrationBinding((FrameLayout) rootView, binder1, binder2, binder3, btnClockIn, commutingWorkPlace, mapView, recentDeparture, recentEntry);
                            }
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
