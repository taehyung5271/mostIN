package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ActivityHomeScreenBinding implements ViewBinding {
    public final DrawerLayout drawerLayout;
    public final RecyclerView drawerMenu;
    public final FragmentContainerView fragmentContainer;
    public final TextView fragmentTitle;
    private final DrawerLayout rootView;
    public final Toolbar toolbar;

    private ActivityHomeScreenBinding(DrawerLayout rootView, DrawerLayout drawerLayout, RecyclerView drawerMenu, FragmentContainerView fragmentContainer, TextView fragmentTitle, Toolbar toolbar) {
        this.rootView = rootView;
        this.drawerLayout = drawerLayout;
        this.drawerMenu = drawerMenu;
        this.fragmentContainer = fragmentContainer;
        this.fragmentTitle = fragmentTitle;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public DrawerLayout getRoot() {
        return this.rootView;
    }

    public static ActivityHomeScreenBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityHomeScreenBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_home_screen, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityHomeScreenBinding bind(View rootView) throws Resources.NotFoundException {
        DrawerLayout drawerLayout = (DrawerLayout) rootView;
        int id = R.id.drawer_menu;
        RecyclerView drawerMenu = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (drawerMenu != null) {
            id = R.id.fragment_container;
            FragmentContainerView fragmentContainer = (FragmentContainerView) ViewBindings.findChildViewById(rootView, id);
            if (fragmentContainer != null) {
                id = R.id.fragment_title;
                TextView fragmentTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (fragmentTitle != null) {
                    int id2 = R.id.toolbar;
                    Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(rootView, id2);
                    if (toolbar == null) {
                        id = id2;
                    } else {
                        return new ActivityHomeScreenBinding((DrawerLayout) rootView, drawerLayout, drawerMenu, fragmentContainer, fragmentTitle, toolbar);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
