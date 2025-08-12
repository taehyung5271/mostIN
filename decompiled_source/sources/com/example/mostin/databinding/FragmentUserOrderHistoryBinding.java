package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentUserOrderHistoryBinding implements ViewBinding {
    public final RecyclerView recyclerView;
    private final LinearLayout rootView;

    private FragmentUserOrderHistoryBinding(LinearLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentUserOrderHistoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentUserOrderHistoryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_user_order_history, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentUserOrderHistoryBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.recyclerView;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (recyclerView != null) {
            return new FragmentUserOrderHistoryBinding((LinearLayout) rootView, recyclerView);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
