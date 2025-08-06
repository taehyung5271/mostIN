package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemOrderHistoryBinding implements ViewBinding {
    public final TextView orderText;
    private final LinearLayout rootView;

    private ItemOrderHistoryBinding(LinearLayout rootView, TextView orderText) {
        this.rootView = rootView;
        this.orderText = orderText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemOrderHistoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemOrderHistoryBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_order_history, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemOrderHistoryBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.orderText;
        TextView orderText = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (orderText != null) {
            return new ItemOrderHistoryBinding((LinearLayout) rootView, orderText);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
