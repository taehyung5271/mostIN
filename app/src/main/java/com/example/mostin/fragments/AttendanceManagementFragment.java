package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.widget.TextView;
import com.example.mostin.adapters.CategoryAdapter;

import java.util.ArrayList;

import com.example.mostin.R;
import com.example.mostin.adapters.AttendanceRecordAdapter;
import com.example.mostin.models.AttendanceRecordModel;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.CommuteModel;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.Toast;

public class AttendanceManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private AttendanceRecordAdapter adapter;
    private Calendar currentCalendar;
    private MaterialAutoCompleteTextView spinnerEmployee;
    private TextView textCurrentMonth;
    private TextView textSummary;
    private String selectedEmployeeId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_management, container, false);

        currentCalendar = Calendar.getInstance();

        // 뷰 초기화
        initializeViews(view);
        // 직원 스피너 설정
        setupEmployeeSpinner();
        // 초기 데이터 로드
        updateMonthDisplay();
        // loadAttendanceData() will be called after employee selection

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
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EmployeeModel> employees = response.body();
                    // EmployeeModel을 String으로 변환
                    List<String> employeeNames = new ArrayList<>();
                    for (EmployeeModel employee : employees) {
                        employeeNames.add(employee.toString());
                    }
                    CategoryAdapter spinnerAdapter = new CategoryAdapter(requireContext(), employeeNames);
                    spinnerEmployee.setAdapter(spinnerAdapter);

                    // 기본값 설정
                    if (!employees.isEmpty()) {
                        spinnerEmployee.setText(employees.get(0).toString(), false);
                        selectedEmployeeId = employees.get(0).getEmployeeId();
                    }

                    spinnerEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // position으로 원본 EmployeeModel에 접근
                            EmployeeModel employee = employees.get(position);
                            selectedEmployeeId = employee.getEmployeeId();
                            loadAttendanceData();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "직원 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("AttendanceManagementFragment", "Failed to fetch employees: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AttendanceManagementFragment", "API call failed: " + t.getMessage(), t);
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
        int month = currentCalendar.get(Calendar.MONTH) + 1; // Month is 0-indexed in Calendar, 1-indexed in API

        ApiService apiService = ApiClient.getApiService();
        apiService.getMonthlyCommute(selectedEmployeeId, year, month).enqueue(new Callback<List<CommuteModel>>() {
            @Override
            public void onResponse(Call<List<CommuteModel>> call, Response<List<CommuteModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommuteModel> commuteModels = response.body();
                    List<AttendanceRecordModel> records = new ArrayList<>();
                    for (CommuteModel commute : commuteModels) {
                        // Map CommuteModel to AttendanceRecordModel
                        records.add(new AttendanceRecordModel(
                                commute.getCommuteDay(),
                                commute.getEmployeeId(),
                                commute.getEmployeeName(),
                                commute.getStartTime(),
                                commute.getEndTime(),
                                commute.getWorkPlaceName()
                        ));
                    }
                    adapter.setRecords(records);
                    updateAttendanceSummary(records);
                } else {
                    Toast.makeText(getContext(), "근태 기록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("AttendanceManagementFragment", "Failed to fetch attendance: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommuteModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AttendanceManagementFragment", "API call failed: " + t.getMessage(), t);
            }
        });
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