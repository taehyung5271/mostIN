package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentAdminGoodsBinding implements ViewBinding {
    public final Button actionBtn;
    public final RecyclerView adminGoodsRecyclerView;
    public final LinearLayout buttonContainer;
    public final Button deleteBtn;
    public final Button editBtn;
    public final Button insertBtn;
    private final RelativeLayout rootView;

    private FragmentAdminGoodsBinding(RelativeLayout rootView, Button actionBtn, RecyclerView adminGoodsRecyclerView, LinearLayout buttonContainer, Button deleteBtn, Button editBtn, Button insertBtn) {
        this.rootView = rootView;
        this.actionBtn = actionBtn;
        this.adminGoodsRecyclerView = adminGoodsRecyclerView;
        this.buttonContainer = buttonContainer;
        this.deleteBtn = deleteBtn;
        this.editBtn = editBtn;
        this.insertBtn = insertBtn;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAdminGoodsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAdminGoodsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_admin_goods, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentAdminGoodsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.actionBtn;
        Button actionBtn = (Button) ViewBindings.findChildViewById(rootView, id);
        if (actionBtn != null) {
            id = R.id.adminGoodsRecyclerView;
            RecyclerView adminGoodsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
            if (adminGoodsRecyclerView != null) {
                id = R.id.buttonContainer;
                LinearLayout buttonContainer = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                if (buttonContainer != null) {
                    id = R.id.deleteBtn;
                    Button deleteBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (deleteBtn != null) {
                        id = R.id.editBtn;
                        Button editBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                        if (editBtn != null) {
                            id = R.id.insertBtn;
                            Button insertBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                            if (insertBtn != null) {
                                return new FragmentAdminGoodsBinding((RelativeLayout) rootView, actionBtn, adminGoodsRecyclerView, buttonContainer, deleteBtn, editBtn, insertBtn);
                            }
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
