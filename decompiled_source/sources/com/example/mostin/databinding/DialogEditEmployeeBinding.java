package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class DialogEditEmployeeBinding implements ViewBinding {
    public final EditText editEmployeeName;
    public final EditText editPhone;
    private final LinearLayout rootView;
    public final Spinner spinnerWorkPlace;

    private DialogEditEmployeeBinding(LinearLayout rootView, EditText editEmployeeName, EditText editPhone, Spinner spinnerWorkPlace) {
        this.rootView = rootView;
        this.editEmployeeName = editEmployeeName;
        this.editPhone = editPhone;
        this.spinnerWorkPlace = spinnerWorkPlace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogEditEmployeeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogEditEmployeeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_edit_employee, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogEditEmployeeBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.edit_employee_name;
        EditText editEmployeeName = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (editEmployeeName != null) {
            id = R.id.edit_phone;
            EditText editPhone = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editPhone != null) {
                id = R.id.spinner_work_place;
                Spinner spinnerWorkPlace = (Spinner) ViewBindings.findChildViewById(rootView, id);
                if (spinnerWorkPlace != null) {
                    return new DialogEditEmployeeBinding((LinearLayout) rootView, editEmployeeName, editPhone, spinnerWorkPlace);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
