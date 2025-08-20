package com.example.mostin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class SubMenuItemBinding implements ViewBinding {
    private final TextView rootView;
    public final TextView subMenuItem;

    private SubMenuItemBinding(TextView rootView, TextView subMenuItem) {
        this.rootView = rootView;
        this.subMenuItem = subMenuItem;
    }

    @Override // androidx.viewbinding.ViewBinding
    public TextView getRoot() {
        return this.rootView;
    }

    public static SubMenuItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SubMenuItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.sub_menu_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static SubMenuItemBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        TextView subMenuItem = (TextView) rootView;
        return new SubMenuItemBinding((TextView) rootView, subMenuItem);
    }
}
