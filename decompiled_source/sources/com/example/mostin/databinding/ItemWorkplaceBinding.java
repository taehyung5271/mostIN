package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemWorkplaceBinding implements ViewBinding {
    private final CardView rootView;
    public final TextView textLocation;
    public final TextView textWorkplaceName;

    private ItemWorkplaceBinding(CardView rootView, TextView textLocation, TextView textWorkplaceName) {
        this.rootView = rootView;
        this.textLocation = textLocation;
        this.textWorkplaceName = textWorkplaceName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static ItemWorkplaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemWorkplaceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_workplace, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemWorkplaceBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.text_location;
        TextView textLocation = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (textLocation != null) {
            id = R.id.text_workplace_name;
            TextView textWorkplaceName = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (textWorkplaceName != null) {
                return new ItemWorkplaceBinding((CardView) rootView, textLocation, textWorkplaceName);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
