package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderHistoryAdapter;
import com.example.mostin.adapters.OrderDetailAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.OrderDetailModel;
import com.example.mostin.models.Ordering;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrderHistoryAdapter adapter;
    
    private String employeeId;
    private String employeeName;
    private String workPlaceName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_history, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        // Bundle에서 사용자 정보 가져오기
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
            workPlaceName = args.getString("work_place_name");
        }

        // RecyclerView 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OrderHistoryAdapter(this::showOrderDetailDialog);
        recyclerView.setAdapter(adapter);

        // SwipeRefreshLayout 설정
        setupSwipeRefresh();

        // 발주 내역 로드
        loadOrderHistory();
        
        return view;
    }

    private void setupSwipeRefresh() {
        // 더 자연스러운 색상으로 변경 (회색 톤)
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.darker_gray,
                android.R.color.black
        );
        
        // 배경색을 투명하게 설정
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadOrderHistory();
        });
    }

    private void loadOrderHistory() {
        if (employeeId == null || employeeName == null) {
            Log.e("UserOrderHistory", "Employee info is null - ID: " + employeeId + ", Name: " + employeeName);
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("UserOrderHistory", "Loading orders for - ID: " + employeeId + ", Name: " + employeeName);

        ApiService apiService = ApiClient.getApiService();
        Call<List<Ordering>> call = apiService.getOrdersByEmployee(employeeId);

        call.enqueue(new Callback<List<Ordering>>() {
            @Override
            public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ordering> orders = response.body();
                    List<OrderHistoryModel> orderHistory = new ArrayList<>();
                    Map<String, List<Ordering>> groupedOrders = new LinkedHashMap<>();

                    for (Ordering order : orders) {
                        String orderDate = order.getOrderingDay().toString();
                        groupedOrders.computeIfAbsent(orderDate, k -> new ArrayList<>()).add(order);
                    }

                    // Get sorted dates from groupedOrders keys
                    List<String> sortedDates = new ArrayList<>(groupedOrders.keySet());
                    sortedDates.sort((d1, d2) -> LocalDate.parse(d2).compareTo(LocalDate.parse(d1))); // Sort dates in descending order

                    for (String date : sortedDates) {
                        List<Ordering> dayOrders = groupedOrders.get(date);
                        int totalItems = dayOrders.size();
                        int totalBoxes = dayOrders.stream()
                                .mapToInt(Ordering::getBoxNum)
                                .sum();
                        
                        orderHistory.add(new OrderHistoryModel(
                                date, 
                                employeeId, 
                                employeeName, 
                                workPlaceName,
                                totalItems,
                                totalBoxes
                        ));
                    }

                    Log.d("UserOrderHistory", "Orders loaded: " + orderHistory.size());
                    adapter.setOrderHistory(orderHistory, workPlaceName);
                } else {
                    Log.e("UserOrderHistory", "Failed to load orders: " + response.code());
                    Toast.makeText(getContext(), "발주 내역을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
                
                // Stop refresh animation
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Ordering>> call, Throwable t) {
                Log.e("UserOrderHistory", "Error loading orders", t);
                Toast.makeText(getContext(), "발주 내역 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                
                // Stop refresh animation
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void showOrderDetailDialog(OrderHistoryModel order) {
        try {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_detail, null);
            TextView workplaceText = dialogView.findViewById(R.id.text_workplace);
            TextView dateText = dialogView.findViewById(R.id.text_date);
            RecyclerView detailRecyclerView = dialogView.findViewById(R.id.recycler_order_detail);

            workplaceText.setText(workPlaceName);
            dateText.setText(order.getOrderingDay());

            detailRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            OrderDetailAdapter detailAdapter = new OrderDetailAdapter();
            detailRecyclerView.setAdapter(detailAdapter);

            ApiService apiService = ApiClient.getApiService();
            Call<List<Ordering>> call = apiService.getOrderDetailsByDate(employeeId, order.getOrderingDay());

            call.enqueue(new Callback<List<Ordering>>() {
                @Override
                public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<OrderDetailModel> details = new ArrayList<>();
                        for (Ordering ordering : response.body()) {
                            details.add(new OrderDetailModel(ordering.getGoodsName(), ordering.getBoxNum()));
                        }
                        if (!details.isEmpty()) {
                            detailAdapter.setOrderDetails(details);
                        }
                    } else {
                        Toast.makeText(getContext(), "상세 내역을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Ordering>> call, Throwable t) {
                    Log.e("UserOrderHistory", "Error loading order details", t);
                    Toast.makeText(getContext(), "상세 내역 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("확인", null)
                .show();
        } catch (Exception e) {
            Log.e("UserOrderHistory", "Error showing order detail dialog", e);
            Toast.makeText(requireContext(), 
                "상세 내역을 불러오는 중 오류가 발생했습니다.", 
                Toast.LENGTH_SHORT).show();
        }
    }
} 