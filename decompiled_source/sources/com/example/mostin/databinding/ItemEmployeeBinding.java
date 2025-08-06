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
public final class ItemEmployeeBinding implements ViewBinding {
    private final CardView rootView;
    public final TextView textEmployeeId;
    public final TextView textEmployeeName;
    public final TextView textEmployeeType;
    public final TextView textPhone;
    public final TextView textWorkPlace;

    private ItemEmployeeBinding(CardView rootView, TextView textEmployeeId, TextView textEmployeeName, TextView textEmployeeType, TextView textPhone, TextView textWorkPlace) {
        this.rootView = rootView;
        this.textEmployeeId = textEmployeeId;
        this.textEmployeeName = textEmployeeName;
        this.textEmployeeType = textEmployeeType;
        this.textPhone = textPhone;
        this.textWorkPlace = textWorkPlace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static ItemEmployeeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemEmployeeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_employee, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemEmployeeBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.text_employee_id;
        TextView textEmployeeId = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (textEmployeeId != null) {
            id = R.id.text_employee_name;
            TextView textEmployeeName = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (textEmployeeName != null) {
                id = R.id.text_employee_type;
                TextView textEmployeeType = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (textEmployeeType != null) {
                    id = R.id.text_phone;
                    TextView textPhone = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (textPhone != null) {
                        id = R.id.text_work_place;
                        TextView textWorkPlace = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (textWorkPlace != null) {
                            return new ItemEmployeeBinding((CardView) rootView, textEmployeeId, textEmployeeName, textEmployeeType, textPhone, textWorkPlace);
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
