package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemGoodsBinding implements ViewBinding {
    public final Button btnCopy;
    public final EditText editBoxCount;
    private final LinearLayout rootView;
    public final TextView textBarcode;
    public final TextView textGoodsName;

    private ItemGoodsBinding(LinearLayout rootView, Button btnCopy, EditText editBoxCount, TextView textBarcode, TextView textGoodsName) {
        this.rootView = rootView;
        this.btnCopy = btnCopy;
        this.editBoxCount = editBoxCount;
        this.textBarcode = textBarcode;
        this.textGoodsName = textGoodsName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemGoodsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemGoodsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_goods, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemGoodsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_copy;
        Button btnCopy = (Button) ViewBindings.findChildViewById(rootView, id);
        if (btnCopy != null) {
            id = R.id.edit_box_count;
            EditText editBoxCount = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editBoxCount != null) {
                id = R.id.text_barcode;
                TextView textBarcode = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (textBarcode != null) {
                    id = R.id.text_goods_name;
                    TextView textGoodsName = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (textGoodsName != null) {
                        return new ItemGoodsBinding((LinearLayout) rootView, btnCopy, editBoxCount, textBarcode, textGoodsName);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
