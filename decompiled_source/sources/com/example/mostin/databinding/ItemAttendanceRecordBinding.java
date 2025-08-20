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
public final class ItemAttendanceRecordBinding implements ViewBinding {
    private final CardView rootView;
    public final TextView textClockIn;
    public final TextView textClockOut;
    public final TextView textDate;
    public final TextView textWorkPlace;

    private ItemAttendanceRecordBinding(CardView rootView, TextView textClockIn, TextView textClockOut, TextView textDate, TextView textWorkPlace) {
        this.rootView = rootView;
        this.textClockIn = textClockIn;
        this.textClockOut = textClockOut;
        this.textDate = textDate;
        this.textWorkPlace = textWorkPlace;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static ItemAttendanceRecordBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemAttendanceRecordBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_attendance_record, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemAttendanceRecordBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.text_clock_in;
        TextView textClockIn = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (textClockIn != null) {
            id = R.id.text_clock_out;
            TextView textClockOut = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (textClockOut != null) {
                id = R.id.text_date;
                TextView textDate = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (textDate != null) {
                    id = R.id.text_work_place;
                    TextView textWorkPlace = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (textWorkPlace != null) {
                        return new ItemAttendanceRecordBinding((CardView) rootView, textClockIn, textClockOut, textDate, textWorkPlace);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
