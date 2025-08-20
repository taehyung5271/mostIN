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
public final class FragmentIntroduceGoodsBinding implements ViewBinding {
    public final RecyclerView goodsRecyclerView;
    private final LinearLayout rootView;

    private FragmentIntroduceGoodsBinding(LinearLayout rootView, RecyclerView goodsRecyclerView) {
        this.rootView = rootView;
        this.goodsRecyclerView = goodsRecyclerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentIntroduceGoodsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentIntroduceGoodsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_introduce_goods, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentIntroduceGoodsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.goods_recyclerView;
        RecyclerView goodsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (goodsRecyclerView != null) {
            return new FragmentIntroduceGoodsBinding((LinearLayout) rootView, goodsRecyclerView);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
