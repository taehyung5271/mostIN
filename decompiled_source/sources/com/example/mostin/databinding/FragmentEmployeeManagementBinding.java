package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentEmployeeManagementBinding implements ViewBinding {
    public final Button btnAddEmployee;
    public final EditText editSearch;
    public final RecyclerView recyclerEmployees;
    private final LinearLayout rootView;

    private FragmentEmployeeManagementBinding(LinearLayout rootView, Button btnAddEmployee, EditText editSearch, RecyclerView recyclerEmployees) {
        this.rootView = rootView;
        this.btnAddEmployee = btnAddEmployee;
        this.editSearch = editSearch;
        this.recyclerEmployees = recyclerEmployees;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentEmployeeManagementBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentEmployeeManagementBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_employee_management, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentEmployeeManagementBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_add_employee;
        Button btnAddEmployee = (Button) ViewBindings.findChildViewById(rootView, id);
        if (btnAddEmployee != null) {
            id = R.id.edit_search;
            EditText editSearch = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editSearch != null) {
                id = R.id.recycler_employees;
                RecyclerView recyclerEmployees = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                if (recyclerEmployees != null) {
                    return new FragmentEmployeeManagementBinding((LinearLayout) rootView, btnAddEmployee, editSearch, recyclerEmployees);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
