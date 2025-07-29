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

import com.example.mostin.R;
import com.example.mostin.adapters.EmployeeAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.WorkPlace;
import com.example.mostin.utils.AppCache; // AppCache import 추가

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    
    private EmployeeAdapter adapter;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_management, container, false);

        
        recyclerView = view.findViewById(R.id.recycler_employees);
        searchEditText = view.findViewById(R.id.edit_search);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        adapter = new EmployeeAdapter(requireContext());
        adapter.setOnItemClickListener((employee, position) -> showEditDialog(employee));
        recyclerView.setAdapter(adapter);

        Button btnAddEmployee = view.findViewById(R.id.btn_add_employee);
        btnAddEmployee.setOnClickListener(v -> showAddEmployeeDialog());

        // 검색 기능 구현
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

        loadEmployees();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmployees();
    }

    private void filterEmployees(String query) {
        List<EmployeeModel> allEmployees = adapter.getAllEmployees(); // Get all employees from adapter
        List<EmployeeModel> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            adapter.updateDisplayedEmployees(allEmployees);
            return;
        }

        String lowerCaseQuery = query.toLowerCase();

        for (EmployeeModel employee : allEmployees) {
            boolean matches = false;
            if (employee.getEmployeeName() != null && employee.getEmployeeName().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }
            if (!matches && employee.getEmployeeId() != null && employee.getEmployeeId().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }
            if (!matches && employee.getWorkPlaceName() != null && employee.getWorkPlaceName().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }

            if (matches) {
                filteredList.add(employee);
            }
        }
        adapter.updateDisplayedEmployees(filteredList);
    }

    private void showEditDialog(EmployeeModel employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_employee, null);

        // Existing info
        EditText editName = dialogView.findViewById(R.id.edit_employee_name);
        EditText editPhone = dialogView.findViewById(R.id.edit_phone);
        Spinner spinnerWorkPlace = dialogView.findViewById(R.id.spinner_work_place);

        editName.setText(employee.getEmployeeName());
        editPhone.setText(employee.getPhoneNum());

        // Setup workplace spinner
        ApiService apiService = ApiClient.getApiService();
        Call<List<WorkPlace>> call = apiService.getAllWorkPlaces();
        call.enqueue(new Callback<List<WorkPlace>>() {
            @Override
            public void onResponse(Call<List<WorkPlace>> call, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> workPlaces = new ArrayList<>();
                    for (WorkPlace wp : response.body()) {
                        workPlaces.add(wp.getWorkPlaceName());
                    }
                    ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, workPlaces);
                    workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWorkPlace.setAdapter(workPlaceAdapter);

                    int workPlacePosition = workPlaces.indexOf(employee.getWorkPlaceName());
                    if (workPlacePosition >= 0) {
                        spinnerWorkPlace.setSelection(workPlacePosition);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WorkPlace>> call, Throwable t) {
                Toast.makeText(getContext(), "근무지 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(dialogView)
                .setTitle("직원 정보 수정")
                .setPositiveButton("수정", (dialog, which) -> {
                    employee.setEmployeeName(editName.getText().toString());
                    employee.setPhoneNum(editPhone.getText().toString());
                    employee.setWorkPlaceName(spinnerWorkPlace.getSelectedItem().toString());
                    updateEmployee(employee);
                })
                .setNegativeButton("삭제", (dialog, which) -> {
                    showDeleteConfirmDialog(employee);
                })
                .setNeutralButton("취소", null)
                .show();
    }

    private void updateEmployee(EmployeeModel employee) {
        ApiService apiService = ApiClient.getApiService();
        Call<EmployeeModel> call = apiService.updateEmployee(employee.getEmployeeId(), employee);

        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    AppCache.getInstance().clearCache("employeeList"); // Clear cache on update
                    loadEmployees();
                    Toast.makeText(getContext(), "직원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "직원 정보 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Log.e("EmployeeManagement", "Error updating employee", t);
                Toast.makeText(getContext(), "직원 정보 수정 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmDialog(EmployeeModel employee) {
        new AlertDialog.Builder(requireContext())
                .setTitle("직원 삭제")
                .setMessage("정말로 이 직원을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    deleteEmployee(employee.getEmployeeId());
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void deleteEmployee(String employeeId) {
        ApiService apiService = ApiClient.getApiService();
        Call<Void> call = apiService.deleteEmployee(employeeId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    AppCache.getInstance().clearCache("employeeList"); // Clear cache on delete
                    loadEmployees();
                    Toast.makeText(getContext(), "직원이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "직원 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("EmployeeManagement", "Error deleting employee", t);
                Toast.makeText(getContext(), "직원 삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

    private void loadEmployees() {
        // Check cache first
        if (AppCache.getInstance().containsKey("employeeList")) {
            Log.d("EmployeeManagement", "Loading employees from cache.");
            List<EmployeeModel> cachedEmployees = AppCache.getInstance().getCachedList("employeeList", EmployeeModel.class);
            adapter.setOriginalEmployees(cachedEmployees);
            return;
        }

        Log.d("EmployeeManagement", "Loading employees from API.");
        ApiService apiService = ApiClient.getApiService();
        Call<List<EmployeeModel>> call = apiService.getAllEmployees();

        call.enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EmployeeModel> employees = response.body();
                    AppCache.getInstance().putCachedList("employeeList", employees); // Cache the data
                    adapter.setOriginalEmployees(employees); // Set the original list
                } else {
                    Toast.makeText(getContext(), "직원 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Log.e("EmployeeManagement", "Error loading employees", t);
                Toast.makeText(getContext(), "직원 목록 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddEmployeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_employee, null);
        
        Spinner spinnerType = dialogView.findViewById(R.id.spinner_employee_type);
        Spinner spinnerWorkPlace = dialogView.findViewById(R.id.spinner_work_place);

        // Employee type spinner setup
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"MD", "SV"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        // Workplace spinner setup
        ApiService apiService = ApiClient.getApiService();
        Call<List<WorkPlace>> call = apiService.getAllWorkPlaces();
        call.enqueue(new Callback<List<WorkPlace>>() {
            @Override
            public void onResponse(Call<List<WorkPlace>> call, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> workPlaces = new ArrayList<>();
                    for (WorkPlace wp : response.body()) {
                        workPlaces.add(wp.getWorkPlaceName());
                    }
                    ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item,
                            workPlaces);
                    workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWorkPlace.setAdapter(workPlaceAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<WorkPlace>> call, Throwable t) {
                Toast.makeText(getContext(), "근무지 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(dialogView)
                .setTitle("직원 추가")
                .setPositiveButton("추가", (dialog, which) -> {
                    String employeeId = ((EditText) dialogView.findViewById(R.id.edit_employee_id)).getText().toString();
                    String name = ((EditText) dialogView.findViewById(R.id.edit_employee_name)).getText().toString();
                    String password = ((EditText) dialogView.findViewById(R.id.edit_employee_password)).getText().toString();
                    String phone = ((EditText) dialogView.findViewById(R.id.edit_phone)).getText().toString();
                    String type = spinnerType.getSelectedItem().toString();
                    String workPlace = spinnerWorkPlace.getSelectedItem().toString();
                    String address = ((EditText) dialogView.findViewById(R.id.edit_address)).getText().toString();

                    EmployeeModel newEmployee = new EmployeeModel(employeeId, name, password, phone, type, address, workPlace);
                    createEmployee(newEmployee);
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void createEmployee(EmployeeModel employee) {
        ApiService apiService = ApiClient.getApiService();
        Call<EmployeeModel> call = apiService.createEmployee(employee);

        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    AppCache.getInstance().clearCache("employeeList"); // Clear cache on create
                    loadEmployees();
                    Toast.makeText(getContext(), "직원이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "직원 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Log.e("EmployeeManagement", "Error adding employee", t);
                Toast.makeText(getContext(), "직원 추가 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 