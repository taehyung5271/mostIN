package com.example.mostin.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mostin.R;

/* loaded from: classes5.dex */
public final class ItemCalendarDayBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView textClockIn;
    public final TextView textClockOut;
    public final TextView textDay;

    private ItemCalendarDayBinding(LinearLayout rootView, TextView textClockIn, TextView textClockOut, TextView textDay) {
        this.rootView = rootView;
        this.textClockIn = textClockIn;
        this.textClockOut = textClockOut;
        this.textDay = textDay;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemCalendarDayBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemCalendarDayBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_calendar_day, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemCalendarDayBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.text_clock_in;
        TextView textClockIn = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (textClockIn != null) {
            id = R.id.text_clock_out;
            TextView textClockOut = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (textClockOut != null) {
                id = R.id.text_day;
                TextView textDay = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (textDay != null) {
                    return new ItemCalendarDayBinding((LinearLayout) rootView, textClockIn, textClockOut, textDay);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
