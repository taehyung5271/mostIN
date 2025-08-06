package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemDrawerMenuBinding implements ViewBinding {
    public final ImageView menuOpenClose;
    public final TextView menuTitle;
    private final LinearLayout rootView;
    public final LinearLayout subMenuContainer;

    private ItemDrawerMenuBinding(LinearLayout rootView, ImageView menuOpenClose, TextView menuTitle, LinearLayout subMenuContainer) {
        this.rootView = rootView;
        this.menuOpenClose = menuOpenClose;
        this.menuTitle = menuTitle;
        this.subMenuContainer = subMenuContainer;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemDrawerMenuBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemDrawerMenuBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_drawer_menu, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemDrawerMenuBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.menu_open_close;
        ImageView menuOpenClose = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (menuOpenClose != null) {
            id = R.id.menu_title;
            TextView menuTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (menuTitle != null) {
                id = R.id.sub_menu_container;
                LinearLayout subMenuContainer = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                if (subMenuContainer != null) {
                    return new ItemDrawerMenuBinding((LinearLayout) rootView, menuOpenClose, menuTitle, subMenuContainer);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
