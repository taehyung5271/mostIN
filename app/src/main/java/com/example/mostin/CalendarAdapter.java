package com.example.mostin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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

        // 출근 시간만 간단히 표시
        if (date.getClockInTime() != null) {
            holder.textClockIn.setText(date.getClockInTime());
            holder.textClockIn.setTextColor(Color.BLUE);
        } else {
            holder.textClockIn.setText("");
        }

        // 퇴근 시간 표시 (필요한 경우)
        if (date.getClockOutTime() != null) {
            holder.textClockOut.setText(date.getClockOutTime());
            holder.textClockOut.setTextColor(Color.BLUE);
        } else {
            holder.textClockOut.setText("");
        }

        // 클릭 이벤트
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), date.getDay() + "일 선택됨", Toast.LENGTH_SHORT).show();
        });
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