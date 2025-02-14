package com.example.mostin;

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
import java.util.List;

public class UserOrderHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;
    private SQLiteHelper dbHelper;
    private String employeeId;
    private String employeeName;
    private String workPlaceName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_history, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        dbHelper = new SQLiteHelper(requireContext());

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

        // 발주 내역 로드
        loadOrderHistory();
        
        return view;
    }

    private void loadOrderHistory() {
        try {
            if (employeeId == null || employeeName == null) {
                Log.e("UserOrderHistory", "Employee info is null - ID: " + employeeId + ", Name: " + employeeName);
                Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("UserOrderHistory", "Loading orders for - ID: " + employeeId + ", Name: " + employeeName);
            
            List<OrderHistoryModel> orderHistory = dbHelper.getOrderHistoryGroupByDate(
                employeeId, 
                employeeName
            );
            
            Log.d("UserOrderHistory", "Orders loaded: " + (orderHistory != null ? orderHistory.size() : 0));
            
            adapter.setOrderHistory(orderHistory, workPlaceName);
        } catch (Exception e) {
            Log.e("UserOrderHistory", "Error loading orders: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(requireContext(), 
                "발주 내역을 불러오는 중 오류가 발생했습니다.", 
                Toast.LENGTH_SHORT).show();
        }
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

            List<OrderDetailModel> details = dbHelper.getOrderDetails(
                employeeId,
                employeeName,
                order.getOrderingDay()
            );

            if (details != null && !details.isEmpty()) {
                detailAdapter.setOrderDetails(details);
            }

            new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("확인", null)
                .show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), 
                "상세 내역을 불러오는 중 오류가 발생했습니다.", 
                Toast.LENGTH_SHORT).show();
        }
    }
} 