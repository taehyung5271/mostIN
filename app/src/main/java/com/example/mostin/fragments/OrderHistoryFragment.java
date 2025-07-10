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
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.OrderDetailModel;
import com.example.mostin.utils.SQLiteHelper;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {
    private Spinner employeeSpinner;
    private SQLiteHelper dbHelper;

    private RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;
   

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        
        employeeSpinner = view.findViewById(R.id.spinner_employee);
        recyclerView = view.findViewById(R.id.orderHistoryRecyclerView);
        dbHelper = new SQLiteHelper(requireContext());

        // RecyclerView 설정을 먼저 합니다
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OrderHistoryAdapter(order -> showOrderDetailDialog(order));
        recyclerView.setAdapter(adapter);

        // 직원 목록 가져오기
        List<EmployeeModel> employees = dbHelper.getAllEmployees();
        
        // 직원 목록이 비어있는 경우 처리
        if (employees == null || employees.isEmpty()) {
            // 에러 메시지를 보여주거나 적절한 처리를 합니다
            showEmptyEmployeeMessage();
            return view;
        }

        // Spinner 어댑터 설정
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        // 안전하게 데이터 추가
        for (EmployeeModel emp : employees) {
            if (emp != null && emp.getEmployeeName() != null && emp.getWorkPlaceName() != null) {
                spinnerAdapter.add(String.format("%s (%s)", 
                    emp.getEmployeeName(), 
                    emp.getWorkPlaceName()));
            }
        }
        
        employeeSpinner.setAdapter(spinnerAdapter);

        // 스피너 선택 이벤트
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < employees.size()) {
                    EmployeeModel selectedEmployee = employees.get(position);
                    loadOrderHistory(selectedEmployee);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        return view;
    }
    
    // 직원 목록이 비어있을 때 메시지를 보여주는 메소드
    private void showEmptyEmployeeMessage() {
        new AlertDialog.Builder(requireContext())
            .setTitle("알림")
            .setMessage("등록된 직원이 없습니다.")
            .setPositiveButton("확인", null)
            .show();
    }

    private void loadOrderHistory(EmployeeModel employee) {
        if (employee == null) return;
        
        try {
            List<OrderHistoryModel> orderHistory = dbHelper.getOrderHistoryGroupByDate(
                employee.getEmployeeId(), 
                employee.getEmployeeName()
            );
            adapter.setOrderHistory(orderHistory, employee.getWorkPlaceName());
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 사용자에게 알림
            new AlertDialog.Builder(requireContext())
                .setTitle("오류")
                .setMessage("발주 내역을 불러오는 중 오류가 발생했습니다.")
                .setPositiveButton("확인", null)
                .show();
        }
    }
  
    private void showOrderDetailDialog(OrderHistoryModel order) {
        try {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_detail, null);
            TextView workplaceText = dialogView.findViewById(R.id.text_workplace);
            TextView dateText = dialogView.findViewById(R.id.text_date);
            RecyclerView detailRecyclerView = dialogView.findViewById(R.id.recycler_order_detail);

            // 텍스트 설정
            workplaceText.setText(order.getWorkPlaceName());
            dateText.setText(order.getOrderingDay());

            // RecyclerView 설정
            detailRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            OrderDetailAdapter detailAdapter = new OrderDetailAdapter();
            detailRecyclerView.setAdapter(detailAdapter);

            // 상세 데이터 로드
            List<OrderDetailModel> details = dbHelper.getOrderDetails(
                order.getEmployeeId(),
                order.getEmployeeName(),
                order.getOrderingDay()
            );

            // 어댑터에 데이터 설정
            if (details != null && !details.isEmpty()) {
                detailAdapter.setOrderDetails(details);
            }

            // 다이얼로그 생성 및 표시
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("확인", null)
                .create();

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(),
                "상세 내역을 불러오는 중 오류가 발생했습니다.", 
                Toast.LENGTH_SHORT).show();
        }
    }
} 