package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.CustomDropdownAdapter;
import com.example.mostin.adapters.EmployeeAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.WorkPlace;
import com.example.mostin.utils.AppCache;
import com.example.mostin.activities.AdminHomeScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeManagementFragment extends Fragment {

    private static final String TAG = "EmployeeManagement";
    private static final String CACHE_KEY_EMPLOYEES = "employeeList";

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private EditText searchEditText;
    private ApiService apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_management, container, false);
        initializeViews(view);
        setupSearch();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmployees();
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_employees);
        searchEditText = view.findViewById(R.id.edit_search);
        Button btnAddEmployee = view.findViewById(R.id.btn_add_employee);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new EmployeeAdapter(requireContext());
        adapter.setOnItemClickListener((employee, position) -> showEditEmployeeDialog(employee));
        recyclerView.setAdapter(adapter);

        btnAddEmployee.setOnClickListener(v -> showAddEmployeeDialog());
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEmployees(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterEmployees(String query) {
        List<EmployeeModel> allEmployees = adapter.getAllEmployees();
        if (query.isEmpty()) {
            adapter.updateDisplayedEmployees(allEmployees);
            return;
        }

        String lowerCaseQuery = query.toLowerCase();
        List<EmployeeModel> filteredList = allEmployees.stream()
                .filter(emp -> (emp.getEmployeeName() != null && emp.getEmployeeName().toLowerCase().startsWith(lowerCaseQuery)) ||
                               (emp.getEmployeeId() != null && emp.getEmployeeId().toLowerCase().startsWith(lowerCaseQuery)) ||
                               (emp.getWorkPlaceName() != null && emp.getWorkPlaceName().toLowerCase().startsWith(lowerCaseQuery)))
                .collect(Collectors.toList());

        adapter.updateDisplayedEmployees(filteredList);
    }

    private void loadEmployees() {
        if (AppCache.getInstance().containsKey(CACHE_KEY_EMPLOYEES)) {
            Log.d(TAG, "Loading employees from cache.");
            List<EmployeeModel> cachedEmployees = AppCache.getInstance().getCachedList(CACHE_KEY_EMPLOYEES, EmployeeModel.class);
            adapter.setOriginalEmployees(cachedEmployees);
            return;
        }

        Log.d(TAG, "Loading employees from API.");
        apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EmployeeModel> employees = response.body();
                    AppCache.getInstance().putCachedList(CACHE_KEY_EMPLOYEES, employees);
                    adapter.setOriginalEmployees(employees);
                } else {
                    Toast.makeText(getContext(), "직원 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Log.e(TAG, "Error loading employees", t);
                Toast.makeText(getContext(), "직원 목록 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddEmployeeDialog() {
        showEmployeeDialog(null);
    }

    private void showEditEmployeeDialog(EmployeeModel employee) {
        showEmployeeDialog(employee);
    }

    private void showEmployeeDialog(EmployeeModel employee) {
        boolean isEditMode = employee != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(isEditMode ? R.layout.dialog_edit_employee : R.layout.dialog_add_employee, null);

        EditText editName = dialogView.findViewById(R.id.edit_employee_name);
        EditText editPhone = dialogView.findViewById(R.id.edit_phone);
        Spinner spinnerWorkPlace = dialogView.findViewById(R.id.spinner_work_place);

        if (isEditMode) {
            editName.setText(employee.getEmployeeName());
            editPhone.setText(employee.getPhoneNum());
        }

        // 직원 유형 스피너 설정 (신규 추가 시에만)
        if (!isEditMode) {
            Spinner spinnerType = dialogView.findViewById(R.id.spinner_employee_type);
            CustomDropdownAdapter<String> typeAdapter = new CustomDropdownAdapter<>(requireContext(),
                    java.util.Arrays.asList("MD", "SV"));
            spinnerType.setAdapter(typeAdapter);
        }

        loadWorkplacesIntoSpinner(spinnerWorkPlace, isEditMode ? employee.getWorkPlaceName() : null);

        builder.setView(dialogView)
                .setTitle(isEditMode ? "직원 정보 수정" : "직원 추가")
                .setPositiveButton(isEditMode ? "수정" : "추가", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String phone = editPhone.getText().toString().trim();
                    String workPlace = spinnerWorkPlace.getSelectedItem() != null ? spinnerWorkPlace.getSelectedItem().toString() : "";

                    // 기본 입력 값 검증
                    if (name.isEmpty() || phone.isEmpty() || workPlace.isEmpty()) {
                        Toast.makeText(getContext(), "필수 항목을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (isEditMode) {
                        employee.setEmployeeName(name);
                        employee.setPhoneNum(phone);
                        employee.setWorkPlaceName(workPlace);
                        updateEmployee(employee);
                    } else {
                        EditText editId = dialogView.findViewById(R.id.edit_employee_id);
                        EditText editPassword = dialogView.findViewById(R.id.edit_employee_password);
                        Spinner spinnerType = dialogView.findViewById(R.id.spinner_employee_type);
                        EditText editAddress = dialogView.findViewById(R.id.edit_address);

                        String id = editId.getText().toString().trim();
                        String password = editPassword.getText().toString().trim();
                        String type = spinnerType.getSelectedItem() != null ? spinnerType.getSelectedItem().toString() : "";
                        String address = editAddress.getText().toString().trim();

                        // 입력 값 검증
                        if (id.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                            Toast.makeText(getContext(), "필수 항목을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            EmployeeModel newEmployee = new EmployeeModel(id, name, password, phone, type, address, workPlace);
                            createEmployee(newEmployee);
                        } catch (Exception e) {
                            Log.e(TAG, "Error creating employee object", e);
                            Toast.makeText(getContext(), "직원 정보 생성 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("취소", null);

        if (isEditMode) {
            builder.setNeutralButton("삭제", (dialog, which) -> showDeleteConfirmDialog(employee));
        }

        builder.show();
    }

    private void loadWorkplacesIntoSpinner(Spinner spinner, @Nullable String currentWorkplace) {
        apiService.getAllWorkPlaces().enqueue(new Callback<List<WorkPlace>>() {
            @Override
            public void onResponse(Call<List<WorkPlace>> call, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> workPlaces = response.body().stream()
                            .map(WorkPlace::getWorkPlaceName)
                            .collect(Collectors.toList());
                    CustomDropdownAdapter<String> adapter = new CustomDropdownAdapter<>(requireContext(), workPlaces);
                    spinner.setAdapter(adapter);
                    if (currentWorkplace != null) {
                        int position = workPlaces.indexOf(currentWorkplace);
                        if (position >= 0) {
                            spinner.setSelection(position);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "근무지 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WorkPlace>> call, Throwable t) {
                Log.e(TAG, "Error loading workplaces", t);
                Toast.makeText(getContext(), "근무지 목록 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createEmployee(EmployeeModel employee) {
        if (employee == null) {
            Toast.makeText(getContext(), "직원 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Creating employee: " + employee.getEmployeeId());
        
        apiService.createEmployee(employee).enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Employee created successfully");
                    AppCache.getInstance().clearCache(CACHE_KEY_EMPLOYEES);
                    loadEmployees();
                    refreshAdminHomeStats(); // 통계 새로고침
                    Toast.makeText(getContext(), "직원이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to create employee. Response code: " + response.code());
                    Toast.makeText(getContext(), "직원 추가에 실패했습니다. (코드: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Log.e(TAG, "Error adding employee", t);
                Toast.makeText(getContext(), "직원 추가 중 네트워크 오류가 발생했습니다: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateEmployee(EmployeeModel employee) {
        apiService.updateEmployee(employee.getEmployeeId(), employee).enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    AppCache.getInstance().clearCache(CACHE_KEY_EMPLOYEES);
                    loadEmployees();
                    refreshAdminHomeStats(); // 통계 새로고침
                    Toast.makeText(getContext(), "직원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "직원 정보 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Log.e(TAG, "Error updating employee", t);
                Toast.makeText(getContext(), "직원 정보 수정 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmDialog(EmployeeModel employee) {
        new AlertDialog.Builder(requireContext())
                .setTitle("직원 삭제")
                .setMessage("정말로 이 직원을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> deleteEmployee(employee.getEmployeeId()))
                .setNegativeButton("취소", null)
                .show();
    }

    private void deleteEmployee(String employeeId) {
        apiService.deleteEmployee(employeeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppCache.getInstance().clearCache(CACHE_KEY_EMPLOYEES);
                    loadEmployees();
                    refreshAdminHomeStats(); // 통계 새로고침
                    Toast.makeText(getContext(), "직원이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "직원 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error deleting employee", t);
                Toast.makeText(getContext(), "직원 삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * AdminHomeScreen의 통계 새로고침
     */
    private void refreshAdminHomeStats() {
        if (getActivity() instanceof AdminHomeScreen) {
            ((AdminHomeScreen) getActivity()).refreshDashboardStats();
        }
    }
}