package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentOrderHistoryBinding implements ViewBinding {
    public final RecyclerView orderHistoryRecyclerView;
    private final LinearLayout rootView;
    public final Spinner spinnerEmployee;

    private FragmentOrderHistoryBinding(LinearLayout rootView, RecyclerView orderHistoryRecyclerView, Spinner spinnerEmployee) {
        this.rootView = rootView;
        this.orderHistoryRecyclerView = orderHistoryRecyclerView;
        this.spinnerEmployee = spinnerEmployee;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentOrderHistoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentOrderHistoryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_order_history, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentOrderHistoryBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.orderHistoryRecyclerView;
        RecyclerView orderHistoryRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (orderHistoryRecyclerView != null) {
            id = R.id.spinner_employee;
            Spinner spinnerEmployee = (Spinner) ViewBindings.findChildViewById(rootView, id);
            if (spinnerEmployee != null) {
                return new FragmentOrderHistoryBinding((LinearLayout) rootView, orderHistoryRecyclerView, spinnerEmployee);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
