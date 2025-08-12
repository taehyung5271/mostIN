package com.example.mostin.fragments;

import android.content.res.Resources;
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
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.CalendarAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.CommuteModel;
import com.example.mostin.models.DateModel;
import com.example.mostin.utils.CalendarUtils;
import com.example.mostin.utils.NonScrollingGridLayoutManager;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class AttendanceCalendarFragment extends Fragment {
    private Calendar currentCalendar;
    private String employeeId;
    private String employeeName;
    private GestureDetectorCompat gestureDetector;
    private RecyclerView recyclerCalendar;
    private TextView textCurrentMonth;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_calendar, container, false);
        Bundle args = getArguments();
        if (args == null) {
            Log.e("AttendanceCalendar", "No arguments received");
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", 0).show();
            return view;
        }
        this.employeeId = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID);
        this.employeeName = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME);
        Log.d("AttendanceCalendar", "Received data - ID: " + this.employeeId + ", Name: " + this.employeeName);
        this.textCurrentMonth = (TextView) view.findViewById(R.id.text_current_month);
        this.recyclerCalendar = (RecyclerView) view.findViewById(R.id.recycler_calendar);
        view.findViewById(R.id.btn_previous_month).setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AttendanceCalendarFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.f$0.m112xef7d85bc(view2);
            }
        });
        view.findViewById(R.id.btn_next_month).setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AttendanceCalendarFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.f$0.m113x15118ebd(view2);
            }
        });
        this.currentCalendar = Calendar.getInstance();
        updateCalendar();
        this.gestureDetector = new GestureDetectorCompat(requireContext(), new RecyclerViewOnGestureListener());
        this.recyclerCalendar.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() { // from class: com.example.mostin.fragments.AttendanceCalendarFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                AttendanceCalendarFragment.this.gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        return view;
    }

    /* renamed from: lambda$onCreateView$0$com-example-mostin-fragments-AttendanceCalendarFragment, reason: not valid java name */
    /* synthetic */ void m112xef7d85bc(View v) throws Resources.NotFoundException {
        changeMonth(-1);
    }

    /* renamed from: lambda$onCreateView$1$com-example-mostin-fragments-AttendanceCalendarFragment, reason: not valid java name */
    /* synthetic */ void m113x15118ebd(View v) throws Resources.NotFoundException {
        changeMonth(1);
    }

    private void updateCalendar() {
        this.textCurrentMonth.setText(String.format("%d년 %d월", Integer.valueOf(this.currentCalendar.get(1)), Integer.valueOf(this.currentCalendar.get(2) + 1)));
        ApiService apiService = ApiClient.getApiService();
        apiService.getMonthlyCommute(this.employeeId, this.currentCalendar.get(1), this.currentCalendar.get(2) + 1).enqueue(new Callback<List<CommuteModel>>() { // from class: com.example.mostin.fragments.AttendanceCalendarFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<List<CommuteModel>> call, Response<List<CommuteModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommuteModel> attendanceData = response.body();
                    List<DateModel> dates = CalendarUtils.generateMonthlyCalendar(AttendanceCalendarFragment.this.currentCalendar);
                    for (DateModel date : dates) {
                        if (date.isCurrentMonth() && date.getDate() != null) {
                            Iterator<CommuteModel> it = attendanceData.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    CommuteModel attendance = it.next();
                                    if (attendance.getCommuteDay() != null && attendance.getCommuteDay().equals(date.getDate())) {
                                        date.setClockInTime(attendance.getStartTime());
                                        date.setClockOutTime(attendance.getEndTime());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    CalendarAdapter adapter = new CalendarAdapter(dates);
                    AttendanceCalendarFragment.this.recyclerCalendar.setLayoutManager(new NonScrollingGridLayoutManager(AttendanceCalendarFragment.this.requireContext(), 7));
                    AttendanceCalendarFragment.this.recyclerCalendar.setAdapter(adapter);
                    return;
                }
                Toast.makeText(AttendanceCalendarFragment.this.getContext(), "출근 기록을 불러오는데 실패했습니다.", 0).show();
                Log.e("AttendanceCalendarFragment", "Failed to fetch attendance: " + response.message());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<CommuteModel>> call, Throwable t) {
                Toast.makeText(AttendanceCalendarFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                Log.e("AttendanceCalendarFragment", "API call failed: " + t.getMessage(), t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeMonth(int offset) throws Resources.NotFoundException {
        Animation inAnimation;
        Animation outAnimation;
        if (offset < 0) {
            inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left);
            outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right);
        } else {
            inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right);
            outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left);
        }
        this.recyclerCalendar.startAnimation(outAnimation);
        this.currentCalendar.add(2, offset);
        updateCalendar();
        this.recyclerCalendar.startAnimation(inAnimation);
    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private RecyclerViewOnGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) throws Resources.NotFoundException {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffX) <= Math.abs(diffY) || Math.abs(diffX) <= 100.0f || Math.abs(velocityX) <= 100.0f) {
                return false;
            }
            if (diffX > 0.0f) {
                AttendanceCalendarFragment.this.changeMonth(-1);
            } else {
                AttendanceCalendarFragment.this.changeMonth(1);
            }
            return true;
        }
    }
}
