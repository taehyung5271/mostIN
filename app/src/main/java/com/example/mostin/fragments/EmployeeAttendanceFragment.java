package com.example.mostin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mostin.R;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAttendanceFragment extends Fragment {

    private static final String TAG = "EmployeeAttendanceFragment";

    private Spinner employeeSpinner;
    private ApiService apiService;
    private List<EmployeeModel> employeeList = new ArrayList<>();
    private EmployeeModel selectedEmployee;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_attendance, container, false);
        initializeViews(view);
        loadEmployees();
        return view;
    }

    private void initializeViews(View view) {
        employeeSpinner = view.findViewById(R.id.spinner_employee);
        
        // 스피너 선택 리스너 설정
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && position <= employeeList.size()) {
                    selectedEmployee = employeeList.get(position - 1);
                    Log.d(TAG, "Selected employee: " + selectedEmployee.getEmployeeName());
                    loadAttendanceCalendar(selectedEmployee);
                } else {
                    selectedEmployee = null;
                    clearAttendanceCalendar();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEmployee = null;
                clearAttendanceCalendar();
            }
        });
    }

    private void loadEmployees() {
        Log.d(TAG, "Loading employees for attendance management...");
        
        apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // admin 직원을 제외하고 일반 직원들만 필터링
                    employeeList = response.body().stream()
                            .filter(employee -> !"admin".equals(employee.getEmployeeId()))
                            .collect(Collectors.toList());
                    
                    Log.d(TAG, "Loaded " + employeeList.size() + " employees (excluding admin)");
                    setupEmployeeSpinner();
                } else {
                    Log.e(TAG, "Failed to load employees: " + response.code());
                    Toast.makeText(getContext(), "직원 목록을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Log.e(TAG, "Error loading employees", t);
                Toast.makeText(getContext(), "네트워크 오류: 직원 목록을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEmployeeSpinner() {
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("직원을 선택하세요");
        
        for (EmployeeModel employee : employeeList) {
            String displayName = employee.getEmployeeName() + " (" + employee.getWorkPlaceName() + ")";
            spinnerItems.add(displayName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), 
                android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(adapter);
        
        Log.d(TAG, "Employee spinner setup completed with " + spinnerItems.size() + " items");
    }

    private void loadAttendanceCalendar(EmployeeModel employee) {
        Log.d(TAG, "Loading attendance calendar for: " + employee.getEmployeeName());
        
        // AttendanceCalendarFragment를 생성하고 직원 정보 전달
        AttendanceCalendarFragment calendarFragment = new AttendanceCalendarFragment();
        Bundle args = new Bundle();
        args.putString("employee_id", employee.getEmployeeId());
        args.putString("employee_name", employee.getEmployeeName());
        calendarFragment.setArguments(args);
        
        // FrameLayout에 Fragment 추가
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar_container, calendarFragment);
        transaction.commit();
        
        Log.d(TAG, "AttendanceCalendarFragment loaded for employee: " + employee.getEmployeeName());
    }

    private void clearAttendanceCalendar() {
        Log.d(TAG, "Clearing attendance calendar");
        
        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.calendar_container);
        if (currentFragment != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.remove(currentFragment);
            transaction.commit();
        }
    }
}