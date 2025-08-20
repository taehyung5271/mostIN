package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;
import com.google.android.material.navigation.NavigationView;

/* loaded from: classes5.dex */
public final class ActivityAdminHomeScreenBinding implements ViewBinding {
    public final DrawerLayout drawerLayout;
    public final FrameLayout fragmentContainer;
    public final NavigationView navView;
    private final DrawerLayout rootView;
    public final Toolbar toolbar;

    private ActivityAdminHomeScreenBinding(DrawerLayout rootView, DrawerLayout drawerLayout, FrameLayout fragmentContainer, NavigationView navView, Toolbar toolbar) {
        this.rootView = rootView;
        this.drawerLayout = drawerLayout;
        this.fragmentContainer = fragmentContainer;
        this.navView = navView;
        this.toolbar = toolbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public DrawerLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAdminHomeScreenBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAdminHomeScreenBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_admin_home_screen, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAdminHomeScreenBinding bind(View rootView) throws Resources.NotFoundException {
        DrawerLayout drawerLayout = (DrawerLayout) rootView;
        int id = R.id.fragment_container;
        FrameLayout fragmentContainer = (FrameLayout) ViewBindings.findChildViewById(rootView, id);
        if (fragmentContainer != null) {
            id = R.id.nav_view;
            NavigationView navView = (NavigationView) ViewBindings.findChildViewById(rootView, id);
            if (navView != null) {
                int id2 = R.id.toolbar;
                Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(rootView, id2);
                if (toolbar == null) {
                    id = id2;
                } else {
                    return new ActivityAdminHomeScreenBinding((DrawerLayout) rootView, drawerLayout, fragmentContainer, navView, toolbar);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
