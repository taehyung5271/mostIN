package com.example.mostin;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
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
    private GestureDetectorCompat gestureDetector;

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

        gestureDetector = new GestureDetectorCompat(requireContext(), new RecyclerViewOnGestureListener());
        recyclerCalendar.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

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
        recyclerCalendar.setLayoutManager(new NonScrollingGridLayoutManager(requireContext(), 7));
        recyclerCalendar.setAdapter(adapter);
    }

    private void changeMonth(int offset) {
        Animation animation;
        if (offset < 0) {
            animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left);
        } else {
            animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right);
        }
        recyclerCalendar.startAnimation(animation);
        currentCalendar.add(Calendar.MONTH, offset);
        updateCalendar();
    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        changeMonth(-1);
                    } else {
                        changeMonth(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }
}