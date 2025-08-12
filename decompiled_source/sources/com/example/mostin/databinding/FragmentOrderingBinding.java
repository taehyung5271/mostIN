package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentOrderingBinding implements ViewBinding {
    public final Button btnCopyAll;
    public final EditText editOrderSummary;
    public final RecyclerView recyclerGoods;
    private final ScrollView rootView;

    private FragmentOrderingBinding(ScrollView rootView, Button btnCopyAll, EditText editOrderSummary, RecyclerView recyclerGoods) {
        this.rootView = rootView;
        this.btnCopyAll = btnCopyAll;
        this.editOrderSummary = editOrderSummary;
        this.recyclerGoods = recyclerGoods;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentOrderingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentOrderingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_ordering, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentOrderingBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_copy_all;
        Button btnCopyAll = (Button) ViewBindings.findChildViewById(rootView, id);
        if (btnCopyAll != null) {
            id = R.id.edit_order_summary;
            EditText editOrderSummary = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editOrderSummary != null) {
                id = R.id.recycler_goods;
                RecyclerView recyclerGoods = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                if (recyclerGoods != null) {
                    return new FragmentOrderingBinding((ScrollView) rootView, btnCopyAll, editOrderSummary, recyclerGoods);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
