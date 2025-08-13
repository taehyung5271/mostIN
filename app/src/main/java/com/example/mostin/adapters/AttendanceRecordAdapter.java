package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.AttendanceRecordModel;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordAdapter extends RecyclerView.Adapter<AttendanceRecordAdapter.AttendanceViewHolder> {
    private List<AttendanceRecordModel> records;
    private Context context;

    public AttendanceRecordAdapter(Context context) {
        this.context = context;
        this.records = new ArrayList<>();
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attendance_record, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceRecordModel record = records.get(position);
        holder.textDate.setText(record.getDate());
        holder.textClockIn.setText(record.getClockIn());
        holder.textClockOut.setText(record.getClockOut() != null ? record.getClockOut() : "-");
        holder.textWorkPlace.setText("근무지: " + record.getWorkPlace());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void setRecords(List<AttendanceRecordModel> records) {
        if (records != null) {
            this.records = new ArrayList<>(records);
        } else {
            this.records = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        TextView textClockIn;
        TextView textClockOut;
        TextView textWorkPlace;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.text_date);
            textClockIn = itemView.findViewById(R.id.text_clock_in);
            textClockOut = itemView.findViewById(R.id.text_clock_out);
            textWorkPlace = itemView.findViewById(R.id.text_work_place);
        }
    }
} 