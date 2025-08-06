package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderDetailAdapter;
import com.example.mostin.adapters.OrderHistoryAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderDetailModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.Ordering;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryFragment extends Fragment {

    private static final String TAG = "OrderHistoryFragment";

    private Spinner employeeSpinner;
    private RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;
    private ApiService apiService;
    private List<EmployeeModel> employeeList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        initializeViews(view);
        loadEmployees();
        return view;
    }

    private void initializeViews(View view) {
        employeeSpinner = view.findViewById(R.id.spinner_employee);
        recyclerView = view.findViewById(R.id.orderHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OrderHistoryAdapter(this::showOrderDetailDialog);
        recyclerView.setAdapter(adapter);
    }

    private void loadEmployees() {
        apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // "admin" ID를 가진 직원을 필터링하여 제외
                    employeeList = response.body().stream()
                            .filter(employee -> !"admin".equals(employee.getEmployeeId()))
                            .collect(Collectors.toList());

                    if (!employeeList.isEmpty()) {
                        setupEmployeeSpinner();
                    } else {
                        showEmptyEmployeeMessage();
                    }
                } else {
                    showEmptyEmployeeMessage();
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                Log.e(TAG, "Error loading employees", t);
                Toast.makeText(getContext(), "직원 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                showEmptyEmployeeMessage();
            }
        });
    }

    private void setupEmployeeSpinner() {
        List<String> employeeNames = employeeList.stream()
                .map(emp -> String.format("%s (%s)", emp.getEmployeeName(), emp.getWorkPlaceName()))
                .collect(Collectors.toList());

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, employeeNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(spinnerAdapter);

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < employeeList.size()) {
                    loadOrderHistory(employeeList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void showEmptyEmployeeMessage() {
        new AlertDialog.Builder(requireContext())
                .setTitle("알림")
                .setMessage("등록된 직원이 없습니다.")
                .setPositiveButton("확인", null)
                .show();
    }

    private void loadOrderHistory(EmployeeModel employee) {
        if (employee == null) return;

        apiService.getOrdersByEmployee(employee.getEmployeeId()).enqueue(new Callback<List<Ordering>>() {
            @Override
            public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processOrderHistoryResponse(response.body(), employee);
                } else {
                    Toast.makeText(getContext(), "발주 내역을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ordering>> call, Throwable t) {
                Log.e(TAG, "Error loading order history", t);
                Toast.makeText(getContext(), "발주 내역 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processOrderHistoryResponse(List<Ordering> orders, EmployeeModel employee) {
        Map<String, List<Ordering>> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getOrderingDay().toString()));

        List<OrderHistoryModel> orderHistory = groupedOrders.keySet().stream()
                .sorted((d1, d2) -> LocalDate.parse(d2).compareTo(LocalDate.parse(d1)))
                .map(date -> new OrderHistoryModel(date, employee.getEmployeeId(), employee.getEmployeeName(), employee.getWorkPlaceName()))
                .collect(Collectors.toList());

        adapter.setOrderHistory(orderHistory, employee.getWorkPlaceName());
    }

    private void showOrderDetailDialog(OrderHistoryModel order) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_detail, null);
        TextView workplaceText = dialogView.findViewById(R.id.text_workplace);
        TextView dateText = dialogView.findViewById(R.id.text_date);
        RecyclerView detailRecyclerView = dialogView.findViewById(R.id.recycler_order_detail);

        workplaceText.setText(order.getWorkPlaceName());
        dateText.setText(order.getOrderingDay());

        detailRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        OrderDetailAdapter detailAdapter = new OrderDetailAdapter();
        detailRecyclerView.setAdapter(detailAdapter);

        loadOrderDetails(order, detailAdapter);

        new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("확인", null)
                .create()
                .show();
    }

    private void loadOrderDetails(OrderHistoryModel order, OrderDetailAdapter adapter) {
        apiService.getOrderDetailsByDate(order.getEmployeeId(), order.getOrderingDay()).enqueue(new Callback<List<Ordering>>() {
            @Override
            public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetailModel> details = response.body().stream()
                            .map(ordering -> new OrderDetailModel(ordering.getGoodsName(), ordering.getBoxNum()))
                            .collect(Collectors.toList());
                    if (!details.isEmpty()) {
                        adapter.setOrderDetails(details);
                    }
                } else {
                    Toast.makeText(getContext(), "상세 내역을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ordering>> call, Throwable t) {
                Log.e(TAG, "Error loading order details", t);
                Toast.makeText(getContext(), "상세 내역 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
