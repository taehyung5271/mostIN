package com.example.mostin.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.DateModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Calendar;

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
                showAttendanceDialog(v, date);
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

    private void showAttendanceDialog(View view, DateModel date) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_attendance_detail);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        
        // 다이얼로그 뷰 요소 참조
        TextView textDialogDate = dialog.findViewById(R.id.text_dialog_date);
        TextView textClockInTime = dialog.findViewById(R.id.text_clock_in_time);
        TextView textClockOutTime = dialog.findViewById(R.id.text_clock_out_time);
        MaterialCardView cardClockIn = dialog.findViewById(R.id.card_clock_in);
        MaterialCardView cardClockOut = dialog.findViewById(R.id.card_clock_out);
        MaterialCardView cardNoAttendance = dialog.findViewById(R.id.card_no_attendance);
        MaterialButton btnConfirm = dialog.findViewById(R.id.btn_dialog_confirm);
        
        // 현재 연도와 월 계산 (DateModel에서 가져오기)
        Calendar calendar = Calendar.getInstance();
        if (date.getDate() != null) {
            // 날짜 문자열 파싱 (yyyy-MM-dd 형식 가정)
            String[] dateParts = date.getDate().split("-");
            if (dateParts.length == 3) {
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                textDialogDate.setText(String.format("%d년 %d월 %d일", year, month, day));
            }
        }
        
        // 출근 정보 표시
        boolean hasClockIn = date.getClockInTime() != null && !date.getClockInTime().isEmpty() && date.getClockInTime().length() >= 5;
        boolean hasClockOut = date.getClockOutTime() != null && !date.getClockOutTime().isEmpty() && date.getClockOutTime().length() >= 5;
        
        if (hasClockIn) {
            // 출근 정보가 있는 경우
            cardNoAttendance.setVisibility(View.GONE);
            cardClockIn.setVisibility(View.VISIBLE);
            textClockInTime.setText(date.getClockInTime().substring(0, 5));
            
            if (hasClockOut) {
                cardClockOut.setVisibility(View.VISIBLE);
                textClockOutTime.setText(date.getClockOutTime().substring(0, 5));
            } else {
                cardClockOut.setVisibility(View.GONE);
            }
        } else {
            // 출근 정보가 없는 경우
            cardNoAttendance.setVisibility(View.VISIBLE);
            cardClockIn.setVisibility(View.GONE);
            cardClockOut.setVisibility(View.GONE);
        }
        
        // 확인 버튼 클릭 리스너
        btnConfirm.setOnClickListener(v -> dialog.dismiss());
        
        dialog.show();
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