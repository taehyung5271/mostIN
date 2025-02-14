package com.example.mostin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private SQLiteHelper dbHelper;
    private EmployeeAdapter adapter;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_management, container, false);

        dbHelper = new SQLiteHelper(requireContext());
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

    private void filterEmployees(String query) {
        List<EmployeeModel> allEmployees = dbHelper.getAllEmployees();
        List<EmployeeModel> filteredList = new ArrayList<>();

        for (EmployeeModel employee : allEmployees) {
            if (employee.getEmployeeName().toLowerCase().contains(query.toLowerCase()) ||
                employee.getEmployeeId().toLowerCase().contains(query.toLowerCase()) ||
                employee.getWorkPlaceName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(employee);
            }
        }
        adapter.setEmployees(filteredList);
    }

    private void showEditDialog(EmployeeModel employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_employee, null);

        // 기존 정보 표시
        EditText editName = dialogView.findViewById(R.id.edit_employee_name);
        EditText editPhone = dialogView.findViewById(R.id.edit_phone);
        Spinner spinnerWorkPlace = dialogView.findViewById(R.id.spinner_work_place);

        editName.setText(employee.getEmployeeName());
        editPhone.setText(employee.getPhoneNum());

        // 근무지 스피너 설정
        List<String> workPlaces = dbHelper.getAllWorkPlaces();
        ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, workPlaces);
        workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkPlace.setAdapter(workPlaceAdapter);

        // 현재 근무지 선택
        int workPlacePosition = workPlaces.indexOf(employee.getWorkPlaceName());
        if (workPlacePosition >= 0) {
            spinnerWorkPlace.setSelection(workPlacePosition);
        }

        builder.setView(dialogView)
                .setTitle("직원 정보 수정")
                .setPositiveButton("수정", (dialog, which) -> {
                    // 직원 정보 업데이트
                    employee.setEmployeeName(editName.getText().toString());
                    employee.setPhoneNum(editPhone.getText().toString());
                    employee.setWorkPlaceName(spinnerWorkPlace.getSelectedItem().toString());
                    dbHelper.updateEmployee(employee);
                    loadEmployees();
                })
                .setNegativeButton("삭제", (dialog, which) -> {
                    showDeleteConfirmDialog(employee);
                })
                .setNeutralButton("취소", null)
                .show();
    }

    private void showDeleteConfirmDialog(EmployeeModel employee) {
        new AlertDialog.Builder(requireContext())
                .setTitle("직원 삭제")
                .setMessage("정말로 이 직원을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    dbHelper.deleteEmployee(employee.getEmployeeId());
                    loadEmployees();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void loadEmployees() {
        List<EmployeeModel> employees = dbHelper.getAllEmployees();
        adapter.setEmployees(employees);
    }

    private void showAddEmployeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_employee, null);
        
        Spinner spinnerType = dialogView.findViewById(R.id.spinner_employee_type);
        Spinner spinnerWorkPlace = dialogView.findViewById(R.id.spinner_work_place);

        // 직원 유형 스피너 설정
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"MD", "SV"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        // 근무지 스피너 설정
        List<String> workPlaces = dbHelper.getAllWorkPlaces();
        ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                workPlaces);
        workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkPlace.setAdapter(workPlaceAdapter);

        builder.setView(dialogView)
                .setTitle("직원 추가")
                .setPositiveButton("추가", (dialog, which) -> {
                    // 직원 정보 저장
                    String employeeId = ((EditText) dialogView.findViewById(R.id.edit_employee_id)).getText().toString();
                    String name = ((EditText) dialogView.findViewById(R.id.edit_employee_name)).getText().toString();
                    String password = ((EditText) dialogView.findViewById(R.id.edit_employee_password)).getText().toString();
                    String phone = ((EditText) dialogView.findViewById(R.id.edit_phone)).getText().toString();
                    String type = spinnerType.getSelectedItem().toString();
                    String workPlace = spinnerWorkPlace.getSelectedItem().toString();
                    String address = ((EditText) dialogView.findViewById(R.id.edit_address)).getText().toString();

                    dbHelper.addEmployee(employeeId, name, password, phone, type, address, workPlace);
                    loadEmployees(); // 목록 새로고침
                })
                .setNegativeButton("취소", null)
                .show();
    }
} 