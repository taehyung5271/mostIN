package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mostin.R;
import com.example.mostin.adapters.AttendanceRecordAdapter;
import com.example.mostin.models.AttendanceRecordModel;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.utils.SQLiteHelper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttendanceManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private SQLiteHelper dbHelper;
    private AttendanceRecordAdapter adapter;
    private Calendar currentCalendar;
    private Spinner spinnerEmployee;
    private TextView textCurrentMonth;
    private TextView textSummary;
    private String selectedEmployeeId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_management, container, false);

        dbHelper = new SQLiteHelper(requireContext());
        currentCalendar = Calendar.getInstance();

        // 뷰 초기화
        initializeViews(view);
        // 직원 스피너 설정
        setupEmployeeSpinner();
        // 초기 데이터 로드
        updateMonthDisplay();
        loadAttendanceData();

        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_attendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AttendanceRecordAdapter(requireContext());
        recyclerView.setAdapter(adapter);

        spinnerEmployee = view.findViewById(R.id.spinner_employee);
        textCurrentMonth = view.findViewById(R.id.text_current_month);
        textSummary = view.findViewById(R.id.text_summary);

        view.findViewById(R.id.btn_previous_month).setOnClickListener(v -> changeMonth(-1));
        view.findViewById(R.id.btn_next_month).setOnClickListener(v -> changeMonth(1));
    }

    private void setupEmployeeSpinner() {
        List<EmployeeModel> employees = dbHelper.getAllEmployees();
        ArrayAdapter<EmployeeModel> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                employees);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(spinnerAdapter);

        spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmployeeModel employee = (EmployeeModel) parent.getItemAtPosition(position);
                selectedEmployeeId = employee.getEmployeeId();
                loadAttendanceData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEmployeeId = null;
            }
        });
    }

    private void changeMonth(int offset) {
        currentCalendar.add(Calendar.MONTH, offset);
        updateMonthDisplay();
        loadAttendanceData();
    }

    private void updateMonthDisplay() {
        String monthYear = String.format(Locale.getDefault(), "%d년 %d월",
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH) + 1);
        textCurrentMonth.setText(monthYear);
    }

    private void loadAttendanceData() {
        if (selectedEmployeeId == null) return;

        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH) + 1;

        List<AttendanceRecordModel> records = dbHelper.getMonthlyAttendance(
                selectedEmployeeId, year, month);
        adapter.setRecords(records);

        // 근태 통계 업데이트
        updateAttendanceSummary(records);
    }

    private void updateAttendanceSummary(List<AttendanceRecordModel> records) {
        int totalDays = records.size();
        int completeDays = 0;
        int incompleteDays = 0;

        for (AttendanceRecordModel record : records) {
            if (record.getClockOut() != null && !record.getClockOut().isEmpty()) {
                completeDays++;
            } else {
                incompleteDays++;
            }
        }

        String summary = String.format(Locale.getDefault(),
                "총 출근일: %d일\n정상 출퇴근: %d일\n미완료: %d일",
                totalDays, completeDays, incompleteDays);
        textSummary.setText(summary);
    }
} 