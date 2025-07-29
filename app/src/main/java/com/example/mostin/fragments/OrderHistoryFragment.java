package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderHistoryAdapter;
import com.example.mostin.adapters.OrderDetailAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.OrderDetailModel;
import com.example.mostin.models.Ordering;
import com.example.mostin.models.WorkPlace;
import com.example.mostin.utils.AppCache; // AppCache import 추가

import android.util.Log;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryFragment extends Fragment {
    private Spinner employeeSpinner;
    private RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;
    private List<EmployeeModel> nonAdminEmployees; // 이 줄을 추가합니다.
   

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        
        employeeSpinner = view.findViewById(R.id.spinner_employee);
        recyclerView = view.findViewById(R.id.orderHistoryRecyclerView);
        

        // RecyclerView 설정을 먼저 합니다
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OrderHistoryAdapter(order -> showOrderDetailDialog(order));
        recyclerView.setAdapter(adapter);

        // 직원 목록 가져오기
        // Check cache first
        if (AppCache.getInstance().containsKey("employeeList")) {
            Log.d("OrderHistoryFragment", "Loading employees from cache.");
            List<EmployeeModel> employees = AppCache.getInstance().getCachedList("employeeList", EmployeeModel.class);
            handleEmployeeResponse(employees);
            return;
        }

        Log.d("OrderHistoryFragment", "Loading employees from API.");
        ApiService apiService = ApiClient.getApiService();
        Call<List<EmployeeModel>> call = apiService.getAllEmployees();

        call.enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EmployeeModel> employees = response.body();
                    AppCache.getInstance().putCachedList("employeeList", employees); // Cache the data
                    handleEmployeeResponse(employees);
                } else {
                    showEmptyEmployeeMessage();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Log.e("OrderHistoryFragment", "Error loading employees", t);
                Toast.makeText(getContext(), "직원 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                showEmptyEmployeeMessage();
            }
        });
        
        return view;
    }

    private void handleEmployeeResponse(List<EmployeeModel> employees) {
        nonAdminEmployees = new ArrayList<>(); // 필터링된 직원 목록을 저장할 변수

        if (employees == null || employees.isEmpty()) {
            showEmptyEmployeeMessage();
            return;
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (EmployeeModel emp : employees) {
            // "admin" 유형이 아닌 직원만 추가
            if (emp != null && emp.getEmployeeName() != null && emp.getWorkPlaceName() != null && !"admin".equals(emp.getEmployeeType())) {
                nonAdminEmployees.add(emp); // 필터링된 리스트에 추가
                spinnerAdapter.add(String.format("%s (%s)",
                        emp.getEmployeeName(),
                        emp.getWorkPlaceName()));
            }
        }

        if (nonAdminEmployees.isEmpty()) {
            showEmptyEmployeeMessage();
            return;
        }

        employeeSpinner.setAdapter(spinnerAdapter);

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < nonAdminEmployees.size()) { // 필터링된 리스트 사용
                    EmployeeModel selectedEmployee = nonAdminEmployees.get(position);
                    loadOrderHistory(selectedEmployee);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        }); 