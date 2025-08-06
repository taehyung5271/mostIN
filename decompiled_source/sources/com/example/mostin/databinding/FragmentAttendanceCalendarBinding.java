package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class FragmentAttendanceCalendarBinding implements ViewBinding {
    public final ImageView btnNextMonth;
    public final ImageView btnPreviousMonth;
    public final LinearLayout fragmentAttendanceCalendar;
    public final RecyclerView recyclerCalendar;
    private final LinearLayout rootView;
    public final TextView textCurrentMonth;

    private FragmentAttendanceCalendarBinding(LinearLayout rootView, ImageView btnNextMonth, ImageView btnPreviousMonth, LinearLayout fragmentAttendanceCalendar, RecyclerView recyclerCalendar, TextView textCurrentMonth) {
        this.rootView = rootView;
        this.btnNextMonth = btnNextMonth;
        this.btnPreviousMonth = btnPreviousMonth;
        this.fragmentAttendanceCalendar = fragmentAttendanceCalendar;
        this.recyclerCalendar = recyclerCalendar;
        this.textCurrentMonth = textCurrentMonth;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAttendanceCalendarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentAttendanceCalendarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_attendance_calendar, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentAttendanceCalendarBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.btn_next_month;
        ImageView btnNextMonth = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (btnNextMonth != null) {
            id = R.id.btn_previous_month;
            ImageView btnPreviousMonth = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (btnPreviousMonth != null) {
                LinearLayout fragmentAttendanceCalendar = (LinearLayout) rootView;
                id = R.id.recycler_calendar;
                RecyclerView recyclerCalendar = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                if (recyclerCalendar != null) {
                    id = R.id.text_current_month;
                    TextView textCurrentMonth = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (textCurrentMonth != null) {
                        return new FragmentAttendanceCalendarBinding((LinearLayout) rootView, btnNextMonth, btnPreviousMonth, fragmentAttendanceCalendar, recyclerCalendar, textCurrentMonth);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
