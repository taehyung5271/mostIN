package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class DialogOrderDetailBinding implements ViewBinding {
    public final RecyclerView recyclerOrderDetail;
    private final LinearLayout rootView;
    public final TextView textDate;
    public final TextView textWorkplace;

    private DialogOrderDetailBinding(LinearLayout rootView, RecyclerView recyclerOrderDetail, TextView textDate, TextView textWorkplace) {
        this.rootView = rootView;
        this.recyclerOrderDetail = recyclerOrderDetail;
        this.textDate = textDate;
        this.textWorkplace = textWorkplace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogOrderDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogOrderDetailBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_order_detail, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogOrderDetailBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.recycler_order_detail;
        RecyclerView recyclerOrderDetail = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (recyclerOrderDetail != null) {
            id = R.id.text_date;
            TextView textDate = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (textDate != null) {
                id = R.id.text_workplace;
                TextView textWorkplace = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (textWorkplace != null) {
                    return new DialogOrderDetailBinding((LinearLayout) rootView, recyclerOrderDetail, textDate, textWorkplace);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
