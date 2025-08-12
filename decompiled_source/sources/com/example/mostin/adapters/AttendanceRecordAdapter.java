package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.AttendanceRecordModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes7.dex */
public class AttendanceRecordAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {
    private Context context;
    private List<AttendanceRecordModel> records = new ArrayList();

    public AttendanceRecordAdapter(Context context) {
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_attendance_record, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        AttendanceRecordModel record = this.records.get(position);
        holder.textDate.setText(record.getDate());
        holder.textClockIn.setText(record.getClockIn());
        holder.textClockOut.setText(record.getClockOut() != null ? record.getClockOut() : "-");
        holder.textWorkPlace.setText("근무지: " + record.getWorkPlace());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.records.size();
    }

    public void setRecords(List<AttendanceRecordModel> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView textClockIn;
        TextView textClockOut;
        TextView textDate;
        TextView textWorkPlace;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            this.textDate = (TextView) itemView.findViewById(R.id.text_date);
            this.textClockIn = (TextView) itemView.findViewById(R.id.text_clock_in);
            this.textClockOut = (TextView) itemView.findViewById(R.id.text_clock_out);
            this.textWorkPlace = (TextView) itemView.findViewById(R.id.text_work_place);
        }
    }
}
