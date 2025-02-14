package com.example.mostin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.List;

public class AttendanceCalendarFragment extends Fragment {

    private TextView textCurrentMonth;
    private RecyclerView recyclerCalendar;
    private Calendar currentCalendar;
    private String employeeId;
    private String employeeName;

    public AttendanceCalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_calendar, container, false);

        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
            Log.d("AttendanceCalendar", "Received data - ID: " + employeeId + ", Name: " + employeeName);
        } else {
            Log.e("AttendanceCalendar", "No arguments received");
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            return view;
        }

        textCurrentMonth = view.findViewById(R.id.text_current_month);
        recyclerCalendar = view.findViewById(R.id.recycler_calendar);

        view.findViewById(R.id.btn_previous_month).setOnClickListener(v -> changeMonth(-1));
        view.findViewById(R.id.btn_next_month).setOnClickListener(v -> changeMonth(1));

        currentCalendar = Calendar.getInstance();
        updateCalendar();

        return view;
    }

    private void updateCalendar() {
        textCurrentMonth.setText(String.format("%d년 %d월",
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH) + 1));

        SQLiteHelper dbHelper = new SQLiteHelper(requireContext());
        List<DateModel> attendanceData = dbHelper.getAttendanceForMonth(
                employeeId,
                employeeName,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH)
        );

        List<DateModel> dates = CalendarUtils.generateMonthlyCalendar(currentCalendar);
        
        for (DateModel date : dates) {
            if (date.isCurrentMonth() && date.getDate() != null) {
                for (DateModel attendance : attendanceData) {
                    if (attendance.getDate() != null && 
                        attendance.getDate().equals(date.getDate())) {
                        date.setClockInTime(attendance.getClockInTime());
                        date.setClockOutTime(attendance.getClockOutTime());
                        break;
                    }
                }
            }
        }

        CalendarAdapter adapter = new CalendarAdapter(dates);
        recyclerCalendar.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        recyclerCalendar.setAdapter(adapter);
    }

    private void changeMonth(int offset) {
        currentCalendar.add(Calendar.MONTH, offset);
        updateCalendar();
    }

}