package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class GridGoodsItemBinding implements ViewBinding {
    public final TextView descriptionText;
    public final ImageView imageView;
    public final FrameLayout overlay;
    private final FrameLayout rootView;

    private GridGoodsItemBinding(FrameLayout rootView, TextView descriptionText, ImageView imageView, FrameLayout overlay) {
        this.rootView = rootView;
        this.descriptionText = descriptionText;
        this.imageView = imageView;
        this.overlay = overlay;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static GridGoodsItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static GridGoodsItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.grid_goods_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static GridGoodsItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.descriptionText;
        TextView descriptionText = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (descriptionText != null) {
            id = R.id.imageView;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (imageView != null) {
                id = R.id.overlay;
                FrameLayout overlay = (FrameLayout) ViewBindings.findChildViewById(rootView, id);
                if (overlay != null) {
                    return new GridGoodsItemBinding((FrameLayout) rootView, descriptionText, imageView, overlay);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
