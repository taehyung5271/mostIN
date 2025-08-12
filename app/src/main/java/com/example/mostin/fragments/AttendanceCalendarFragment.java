package com.example.mostin.fragments;

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

import com.example.mostin.R;
import com.example.mostin.models.DateModel;
import com.example.mostin.utils.CalendarUtils;
import com.example.mostin.adapters.CalendarAdapter;
import com.example.mostin.utils.NonScrollingGridLayoutManager;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.CommuteModel;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            // 전달받은 연도와 월 정보로 달력 설정
            currentCalendar = Calendar.getInstance();
            if (args.containsKey("year") && args.containsKey("month")) {
                currentCalendar.set(Calendar.YEAR, args.getInt("year"));
                currentCalendar.set(Calendar.MONTH, args.getInt("month"));
            }
        } else {
            Log.e("AttendanceCalendar", "No arguments received");
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            currentCalendar = Calendar.getInstance(); // 비상시 현재 날짜로 설정
        }

        textCurrentMonth = view.findViewById(R.id.text_current_month);
        recyclerCalendar = view.findViewById(R.id.recycler_calendar);

        view.findViewById(R.id.btn_previous_month).setOnClickListener(v -> changeMonth(-1));
        view.findViewById(R.id.btn_next_month).setOnClickListener(v -> changeMonth(1));

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

        ApiService apiService = ApiClient.getApiService();
        apiService.getMonthlyCommute(
                employeeId,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH) + 1 // Month is 0-indexed in Calendar, 1-indexed in API
        ).enqueue(new Callback<List<CommuteModel>>() {
            @Override
            public void onResponse(Call<List<CommuteModel>> call, Response<List<CommuteModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommuteModel> attendanceData = response.body();
                    List<DateModel> dates = CalendarUtils.generateMonthlyCalendar(currentCalendar);

                    for (DateModel date : dates) {
                        if (date.isCurrentMonth() && date.getDate() != null) {
                            for (CommuteModel attendance : attendanceData) {
                                if (attendance.getCommuteDay() != null &&
                                    attendance.getCommuteDay().equals(date.getDate())) {
                                    date.setClockInTime(attendance.getStartTime());
                                    date.setClockOutTime(attendance.getEndTime());
                                    break;
                                }
                            }
                        }
                    }

                    CalendarAdapter adapter = new CalendarAdapter(dates);
                    recyclerCalendar.setLayoutManager(new NonScrollingGridLayoutManager(requireContext(), 7));
                    recyclerCalendar.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "출근 기록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("AttendanceCalendarFragment", "Failed to fetch attendance: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommuteModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AttendanceCalendarFragment", "API call failed: " + t.getMessage(), t);
            }
        });
    }

    private void changeMonth(int offset) {
        Animation inAnimation;
        Animation outAnimation;

        if (offset < 0) { // 이전 달로 이동 (오른쪽 스와이프)
            inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left);
            outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right);
        } else { // 다음 달로 이동 (왼쪽 스와이프)
            inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right);
            outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left);
        }

        // 현재 뷰에 나가는 애니메이션 적용
        recyclerCalendar.startAnimation(outAnimation);

        // 달력 데이터 업데이트
        currentCalendar.add(Calendar.MONTH, offset);
        updateCalendar();

        // 새로운 뷰에 들어오는 애니메이션 적용
        recyclerCalendar.startAnimation(inAnimation);
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