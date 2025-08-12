package com.example.mostin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.AttendanceRecordAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.AttendanceRecordModel;
import com.example.mostin.models.CommuteModel;
import com.example.mostin.models.EmployeeModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class AttendanceManagementFragment extends Fragment {
    private AttendanceRecordAdapter adapter;
    private Calendar currentCalendar;
    private RecyclerView recyclerView;
    private String selectedEmployeeId = null;
    private Spinner spinnerEmployee;
    private TextView textCurrentMonth;
    private TextView textSummary;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_management, container, false);
        this.currentCalendar = Calendar.getInstance();
        initializeViews(view);
        setupEmployeeSpinner();
        updateMonthDisplay();
        return view;
    }

    private void initializeViews(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_attendance);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.adapter = new AttendanceRecordAdapter(requireContext());
        this.recyclerView.setAdapter(this.adapter);
        this.spinnerEmployee = (Spinner) view.findViewById(R.id.spinner_employee);
        this.textCurrentMonth = (TextView) view.findViewById(R.id.text_current_month);
        this.textSummary = (TextView) view.findViewById(R.id.text_summary);
        view.findViewById(R.id.btn_previous_month).setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AttendanceManagementFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m114xbc972365(view2);
            }
        });
        view.findViewById(R.id.btn_next_month).setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AttendanceManagementFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m115xcd4cf026(view2);
            }
        });
    }

    /* renamed from: lambda$initializeViews$0$com-example-mostin-fragments-AttendanceManagementFragment, reason: not valid java name */
    /* synthetic */ void m114xbc972365(View v) {
        changeMonth(-1);
    }

    /* renamed from: lambda$initializeViews$1$com-example-mostin-fragments-AttendanceManagementFragment, reason: not valid java name */
    /* synthetic */ void m115xcd4cf026(View v) {
        changeMonth(1);
    }

    private void setupEmployeeSpinner() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() { // from class: com.example.mostin.fragments.AttendanceManagementFragment.1
            @Override // retrofit2.Callback
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EmployeeModel> employees = response.body();
                    ArrayAdapter<EmployeeModel> spinnerAdapter = new ArrayAdapter<>(AttendanceManagementFragment.this.requireContext(), android.R.layout.simple_spinner_item, employees);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    AttendanceManagementFragment.this.spinnerEmployee.setAdapter((SpinnerAdapter) spinnerAdapter);
                    AttendanceManagementFragment.this.spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.example.mostin.fragments.AttendanceManagementFragment.1.1
                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            EmployeeModel employee = (EmployeeModel) parent.getItemAtPosition(position);
                            AttendanceManagementFragment.this.selectedEmployeeId = employee.getEmployeeId();
                            AttendanceManagementFragment.this.loadAttendanceData();
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> parent) {
                            AttendanceManagementFragment.this.selectedEmployeeId = null;
                        }
                    });
                    return;
                }
                Toast.makeText(AttendanceManagementFragment.this.getContext(), "직원 목록을 불러오는데 실패했습니다.", 0).show();
                Log.e("AttendanceManagementFragment", "Failed to fetch employees: " + response.message());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Toast.makeText(AttendanceManagementFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                Log.e("AttendanceManagementFragment", "API call failed: " + t.getMessage(), t);
            }
        });
    }

    private void changeMonth(int offset) {
        this.currentCalendar.add(2, offset);
        updateMonthDisplay();
        loadAttendanceData();
    }

    private void updateMonthDisplay() {
        String monthYear = String.format(Locale.getDefault(), "%d년 %d월", Integer.valueOf(this.currentCalendar.get(1)), Integer.valueOf(this.currentCalendar.get(2) + 1));
        this.textCurrentMonth.setText(monthYear);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAttendanceData() {
        if (this.selectedEmployeeId == null) {
            return;
        }
        int year = this.currentCalendar.get(1);
        int month = this.currentCalendar.get(2) + 1;
        ApiService apiService = ApiClient.getApiService();
        apiService.getMonthlyCommute(this.selectedEmployeeId, year, month).enqueue(new Callback<List<CommuteModel>>() { // from class: com.example.mostin.fragments.AttendanceManagementFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<List<CommuteModel>> call, Response<List<CommuteModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommuteModel> commuteModels = response.body();
                    List<AttendanceRecordModel> records = new ArrayList<>();
                    for (CommuteModel commute : commuteModels) {
                        records.add(new AttendanceRecordModel(commute.getCommuteDay(), commute.getEmployeeId(), commute.getEmployeeName(), commute.getStartTime(), commute.getEndTime(), commute.getWorkPlaceName()));
                    }
                    AttendanceManagementFragment.this.adapter.setRecords(records);
                    AttendanceManagementFragment.this.updateAttendanceSummary(records);
                    return;
                }
                Toast.makeText(AttendanceManagementFragment.this.getContext(), "근태 기록을 불러오는데 실패했습니다.", 0).show();
                Log.e("AttendanceManagementFragment", "Failed to fetch attendance: " + response.message());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<CommuteModel>> call, Throwable t) {
                Toast.makeText(AttendanceManagementFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                Log.e("AttendanceManagementFragment", "API call failed: " + t.getMessage(), t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAttendanceSummary(List<AttendanceRecordModel> records) {
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
        String summary = String.format(Locale.getDefault(), "총 출근일: %d일\n정상 출퇴근: %d일\n미완료: %d일", Integer.valueOf(totalDays), Integer.valueOf(completeDays), Integer.valueOf(incompleteDays));
        this.textSummary.setText(summary);
    }
}
