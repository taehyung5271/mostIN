package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentAttendanceManagementBinding implements ViewBinding {
    public final ImageButton btnNextMonth;
    public final ImageButton btnPreviousMonth;
    public final RecyclerView recyclerAttendance;
    private final LinearLayout rootView;
    public final Spinner spinnerEmployee;
    public final TextView textCurrentMonth;
    public final TextView textSummary;

    private FragmentAttendanceManagementBinding(LinearLayout rootView, ImageButton btnNextMonth, ImageButton btnPreviousMonth, RecyclerView recyclerAttendance, Spinner spinnerEmployee, TextView textCurrentMonth, TextView textSummary) {
        this.rootView = rootView;
        this.btnNextMonth = btnNextMonth;
        this.btnPreviousMonth = btnPreviousMonth;
        this.recyclerAttendance = recyclerAttendance;
        this.spinnerEmployee = spinnerEmployee;
        this.textCurrentMonth = textCurrentMonth;
        this.textSummary = textSummary;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAttendanceManagementBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAttendanceManagementBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_attendance_management, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentAttendanceManagementBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_next_month;
        ImageButton btnNextMonth = (ImageButton) ViewBindings.findChildViewById(rootView, id);
        if (btnNextMonth != null) {
            id = R.id.btn_previous_month;
            ImageButton btnPreviousMonth = (ImageButton) ViewBindings.findChildViewById(rootView, id);
            if (btnPreviousMonth != null) {
                id = R.id.recycler_attendance;
                RecyclerView recyclerAttendance = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                if (recyclerAttendance != null) {
                    id = R.id.spinner_employee;
                    Spinner spinnerEmployee = (Spinner) ViewBindings.findChildViewById(rootView, id);
                    if (spinnerEmployee != null) {
                        id = R.id.text_current_month;
                        TextView textCurrentMonth = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (textCurrentMonth != null) {
                            id = R.id.text_summary;
                            TextView textSummary = (TextView) ViewBindings.findChildViewById(rootView, id);
                            if (textSummary != null) {
                                return new FragmentAttendanceManagementBinding((LinearLayout) rootView, btnNextMonth, btnPreviousMonth, recyclerAttendance, spinnerEmployee, textCurrentMonth, textSummary);
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
