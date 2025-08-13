package com.example.mostin.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.DateModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final List<DateModel> dates;

    public CalendarAdapter(List<DateModel> dates) {
        this.dates = dates;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        DateModel date = dates.get(position);

        // 날짜 설정 (일자만 표시)
        holder.textDay.setText(String.valueOf(date.getDay()));

        // 요일별 색상 처리 (일요일: 빨간색, 토요일: 파란색)
        int columnIndex = position % 7;
        if (columnIndex == 0) {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
        } else if (columnIndex == 6) {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        } else {
            holder.textDay.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }

        // 현재 월 강조
        holder.textDay.setAlpha(date.isCurrentMonth() ? 1.0f : 0.4f);

        // 출근 시간 표시 (HH:mm)
        if (date.getClockInTime() != null && date.getClockInTime().length() >= 5) {
            holder.textClockIn.setText(date.getClockInTime().substring(0, 5));
        } else {
            holder.textClockIn.setText("");
        }

        // 퇴근 시간 표시 (HH:mm)
        if (date.getClockOutTime() != null && date.getClockOutTime().length() >= 5) {
            holder.textClockOut.setText(date.getClockOutTime().substring(0, 5));
        } else {
            holder.textClockOut.setText("");
        }

        // 클릭 이벤트
        holder.itemView.setOnClickListener(v -> {
            if (date.isCurrentMonth()) {
                new androidx.appcompat.app.AlertDialog.Builder(v.getContext())
                    .setTitle(date.getDay() + "일 출근 정보")
                    .setMessage(getAttendanceInfo(date))
                    .setPositiveButton("확인", null)
                    .show();
            }
        });
    }

    private String getAttendanceInfo(DateModel date) {
        if (date.getClockInTime() == null || date.getClockInTime().isEmpty() || date.getClockInTime().length() < 5) {
            return "출근하지 않았습니다";
        }

        StringBuilder info = new StringBuilder();
        if (date.getClockInTime() != null && date.getClockInTime().length() >= 5) {
            info.append("출근시간 - ").append(date.getClockInTime().substring(0, 5));
        }

        if (date.getClockOutTime() != null && !date.getClockOutTime().isEmpty() && date.getClockOutTime().length() >= 5) {
            info.append("\n퇴근시간 - ").append(date.getClockOutTime().substring(0, 5));
        }

        return info.toString();
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textDay, textClockIn, textClockOut;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            textDay = itemView.findViewById(R.id.text_day);
            textClockIn = itemView.findViewById(R.id.text_clock_in);
            textClockOut = itemView.findViewById(R.id.text_clock_out);
        }
    }
}