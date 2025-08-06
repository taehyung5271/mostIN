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
public final class DialogAddEmployeeBinding implements ViewBinding {
    public final EditText editAddress;
    public final EditText editEmployeeId;
    public final EditText editEmployeeName;
    public final EditText editEmployeePassword;
    public final EditText editPhone;
    private final LinearLayout rootView;
    public final Spinner spinnerEmployeeType;
    public final Spinner spinnerWorkPlace;

    private DialogAddEmployeeBinding(LinearLayout rootView, EditText editAddress, EditText editEmployeeId, EditText editEmployeeName, EditText editEmployeePassword, EditText editPhone, Spinner spinnerEmployeeType, Spinner spinnerWorkPlace) {
        this.rootView = rootView;
        this.editAddress = editAddress;
        this.editEmployeeId = editEmployeeId;
        this.editEmployeeName = editEmployeeName;
        this.editEmployeePassword = editEmployeePassword;
        this.editPhone = editPhone;
        this.spinnerEmployeeType = spinnerEmployeeType;
        this.spinnerWorkPlace = spinnerWorkPlace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogAddEmployeeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogAddEmployeeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_add_employee, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogAddEmployeeBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.edit_address;
        EditText editAddress = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (editAddress != null) {
            id = R.id.edit_employee_id;
            EditText editEmployeeId = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editEmployeeId != null) {
                id = R.id.edit_employee_name;
                EditText editEmployeeName = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (editEmployeeName != null) {
                    id = R.id.edit_employee_password;
                    EditText editEmployeePassword = (EditText) ViewBindings.findChildViewById(rootView, id);
                    if (editEmployeePassword != null) {
                        id = R.id.edit_phone;
                        EditText editPhone = (EditText) ViewBindings.findChildViewById(rootView, id);
                        if (editPhone != null) {
                            id = R.id.spinner_employee_type;
                            Spinner spinnerEmployeeType = (Spinner) ViewBindings.findChildViewById(rootView, id);
                            if (spinnerEmployeeType != null) {
                                id = R.id.spinner_work_place;
                                Spinner spinnerWorkPlace = (Spinner) ViewBindings.findChildViewById(rootView, id);
                                if (spinnerWorkPlace != null) {
                                    return new DialogAddEmployeeBinding((LinearLayout) rootView, editAddress, editEmployeeId, editEmployeeName, editEmployeePassword, editPhone, spinnerEmployeeType, spinnerWorkPlace);
                                }
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
