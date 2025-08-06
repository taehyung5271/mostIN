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
public final class ItemOrderDetailBinding implements ViewBinding {
    public final TextView boxCount;
    public final TextView goodsName;
    private final LinearLayout rootView;

    private ItemOrderDetailBinding(LinearLayout rootView, TextView boxCount, TextView goodsName) {
        this.rootView = rootView;
        this.boxCount = boxCount;
        this.goodsName = goodsName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemOrderDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemOrderDetailBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_order_detail, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemOrderDetailBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.boxCount;
        TextView boxCount = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (boxCount != null) {
            id = R.id.goodsName;
            TextView goodsName = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (goodsName != null) {
                return new ItemOrderDetailBinding((LinearLayout) rootView, boxCount, goodsName);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
