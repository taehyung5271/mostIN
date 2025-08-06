package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemAdminGoodsBinding implements ViewBinding {
    public final EditText barcodeEdit;
    public final CheckBox checkbox;
    public final EditText nameEdit;
    private final LinearLayout rootView;

    private ItemAdminGoodsBinding(LinearLayout rootView, EditText barcodeEdit, CheckBox checkbox, EditText nameEdit) {
        this.rootView = rootView;
        this.barcodeEdit = barcodeEdit;
        this.checkbox = checkbox;
        this.nameEdit = nameEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemAdminGoodsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemAdminGoodsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_admin_goods, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemAdminGoodsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.barcodeEdit;
        EditText barcodeEdit = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (barcodeEdit != null) {
            id = R.id.checkbox;
            CheckBox checkbox = (CheckBox) ViewBindings.findChildViewById(rootView, id);
            if (checkbox != null) {
                id = R.id.nameEdit;
                EditText nameEdit = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (nameEdit != null) {
                    return new ItemAdminGoodsBinding((LinearLayout) rootView, barcodeEdit, checkbox, nameEdit);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
