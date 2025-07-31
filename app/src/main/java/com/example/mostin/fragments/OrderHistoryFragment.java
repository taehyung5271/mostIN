package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.mostin.adapters.OrderDetailAdapter;
import com.example.mostin.adapters.OrderHistoryAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.fragments.OrderHistoryFragment;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.OrderDetailModel;
import com.example.mostin.models.OrderHistoryModel;
import com.example.mostin.models.Ordering;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class OrderHistoryFragment extends Fragment {
    private OrderHistoryAdapter adapter;
    private Spinner employeeSpinner;
    private RecyclerView recyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        this.employeeSpinner = (Spinner) view.findViewById(R.id.spinner_employee);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.orderHistoryRecyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.adapter = new OrderHistoryAdapter(new OrderHistoryAdapter.OnOrderClickListener() { // from class: com.example.mostin.fragments.OrderHistoryFragment$$ExternalSyntheticLambda0
            @Override // com.example.mostin.adapters.OrderHistoryAdapter.OnOrderClickListener
            public final void onOrderClick(OrderHistoryModel orderHistoryModel) {
                this.f$0.m125x57d6d69b(orderHistoryModel);
            }
        });
        this.recyclerView.setAdapter(this.adapter);
        ApiService apiService = ApiClient.getApiService();
        Call<List<EmployeeModel>> call = apiService.getAllEmployees();
        call.enqueue(new Callback<List<EmployeeModel>>() { // from class: com.example.mostin.fragments.OrderHistoryFragment.1
            @Override // retrofit2.Callback
            public void onResponse(Call<List<EmployeeModel>> call2, Response<List<EmployeeModel>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    OrderHistoryFragment.this.showEmptyEmployeeMessage();
                    return;
                }
                final List<EmployeeModel> employees = response.body();
                if (employees == null || employees.isEmpty()) {
                    OrderHistoryFragment.this.showEmptyEmployeeMessage();
                    return;
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(OrderHistoryFragment.this.requireContext(), android.R.layout.simple_spinner_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                for (EmployeeModel emp : employees) {
                    if (emp != null && emp.getEmployeeName() != null && emp.getWorkPlaceName() != null) {
                        spinnerAdapter.add(String.format("%s (%s)", emp.getEmployeeName(), emp.getWorkPlaceName()));
                    }
                }
                OrderHistoryFragment.this.employeeSpinner.setAdapter((SpinnerAdapter) spinnerAdapter);
                OrderHistoryFragment.this.employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.example.mostin.fragments.OrderHistoryFragment.1.1
                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> parent, View view2, int position, long id) {
                        if (position >= 0 && position < employees.size()) {
                            EmployeeModel selectedEmployee = (EmployeeModel) employees.get(position);
                            OrderHistoryFragment.this.loadOrderHistory(selectedEmployee);
                        }
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<EmployeeModel>> call2, Throwable t) {
                Log.e("OrderHistoryFragment", "Error loading employees", t);
                Toast.makeText(OrderHistoryFragment.this.getContext(), "직원 목록을 불러오는 데 실패했습니다.", 0).show();
                OrderHistoryFragment.this.showEmptyEmployeeMessage();
            }
        });
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyEmployeeMessage() {
        new AlertDialog.Builder(requireContext()).setTitle("알림").setMessage("등록된 직원이 없습니다.").setPositiveButton("확인", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadOrderHistory(EmployeeModel employee) {
        if (employee == null) {
            return;
        }
        ApiService apiService = ApiClient.getApiService();
        Call<List<Ordering>> call = apiService.getOrdersByEmployee(employee.getEmployeeId());
        call.enqueue(new AnonymousClass2(employee));
    }

    /* renamed from: com.example.mostin.fragments.OrderHistoryFragment$2, reason: invalid class name */
    class AnonymousClass2 implements Callback<List<Ordering>> {
        final /* synthetic */ EmployeeModel val$employee;

        AnonymousClass2(EmployeeModel employeeModel) {
            this.val$employee = employeeModel;
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Ordering> orders = response.body();
                List<OrderHistoryModel> orderHistory = new ArrayList<>();
                Map<String, List<Ordering>> groupedOrders = new LinkedHashMap<>();
                for (Ordering order : orders) {
                    String orderDate = order.getOrderingDay().toString();
                    groupedOrders.computeIfAbsent(orderDate, new Function() { // from class: com.example.mostin.fragments.OrderHistoryFragment$2$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return OrderHistoryFragment.AnonymousClass2.lambda$onResponse$0((String) obj);
                        }
                    }).add(order);
                }
                for (Ordering order2 : orders) {
                    String orderDate2 = order2.getOrderingDay().toString();
                    groupedOrders.computeIfAbsent(orderDate2, new Function() { // from class: com.example.mostin.fragments.OrderHistoryFragment$2$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return OrderHistoryFragment.AnonymousClass2.lambda$onResponse$1((String) obj);
                        }
                    }).add(order2);
                }
                List<String> sortedDates = new ArrayList<>(groupedOrders.keySet());
                sortedDates.sort(new Comparator() { // from class: com.example.mostin.fragments.OrderHistoryFragment$2$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return LocalDate.parse((String) obj2).compareTo((ChronoLocalDate) LocalDate.parse((String) obj));
                    }
                });
                for (String date : sortedDates) {
                    orderHistory.add(new OrderHistoryModel(date, this.val$employee.getEmployeeId(), this.val$employee.getEmployeeName(), this.val$employee.getWorkPlaceName()));
                }
                Log.d("OrderHistoryFragment", "Sorted orderHistory list:");
                for (OrderHistoryModel model : orderHistory) {
                    Log.d("OrderHistoryFragment", "  - " + model.getOrderingDay());
                }
                OrderHistoryFragment.this.adapter.setOrderHistory(orderHistory, this.val$employee.getWorkPlaceName());
                return;
            }
            Toast.makeText(OrderHistoryFragment.this.getContext(), "발주 내역을 불러오는 데 실패했습니다.", 0).show();
        }

        static /* synthetic */ List lambda$onResponse$0(String k) {
            return new ArrayList();
        }

        static /* synthetic */ List lambda$onResponse$1(String k) {
            return new ArrayList();
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<List<Ordering>> call, Throwable t) {
            Log.e("OrderHistoryFragment", "Error loading order history", t);
            Toast.makeText(OrderHistoryFragment.this.getContext(), "발주 내역 로딩 중 오류가 발생했습니다.", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showOrderDetailDialog, reason: merged with bridge method [inline-methods] */
    public void m125x57d6d69b(OrderHistoryModel order) {
        try {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_detail, (ViewGroup) null);
            TextView workplaceText = (TextView) dialogView.findViewById(R.id.text_workplace);
            TextView dateText = (TextView) dialogView.findViewById(R.id.text_date);
            RecyclerView detailRecyclerView = (RecyclerView) dialogView.findViewById(R.id.recycler_order_detail);
            workplaceText.setText(order.getWorkPlaceName());
            dateText.setText(order.getOrderingDay());
            detailRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            final OrderDetailAdapter detailAdapter = new OrderDetailAdapter();
            detailRecyclerView.setAdapter(detailAdapter);
            ApiService apiService = ApiClient.getApiService();
            Call<List<Ordering>> call = apiService.getOrderDetailsByDate(order.getEmployeeId(), order.getOrderingDay());
            call.enqueue(new Callback<List<Ordering>>() { // from class: com.example.mostin.fragments.OrderHistoryFragment.3
                @Override // retrofit2.Callback
                public void onResponse(Call<List<Ordering>> call2, Response<List<Ordering>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<OrderDetailModel> details = new ArrayList<>();
                        for (Ordering ordering : response.body()) {
                            details.add(new OrderDetailModel(ordering.getGoodsName(), ordering.getBoxNum().intValue()));
                        }
                        if (!details.isEmpty()) {
                            detailAdapter.setOrderDetails(details);
                            return;
                        }
                        return;
                    }
                    Toast.makeText(OrderHistoryFragment.this.getContext(), "상세 내역을 불러오는 데 실패했습니다.", 0).show();
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<Ordering>> call2, Throwable t) {
                    Log.e("OrderHistoryFragment", "Error loading order details", t);
                    Toast.makeText(OrderHistoryFragment.this.getContext(), "상세 내역 로딩 중 오류가 발생했습니다.", 0).show();
                }
            });
            AlertDialog dialog = new AlertDialog.Builder(requireContext()).setView(dialogView).setPositiveButton("확인", (DialogInterface.OnClickListener) null).create();
            dialog.show();
        } catch (Exception e) {
            Log.e("OrderHistoryFragment", "Error showing order detail dialog", e);
            Toast.makeText(requireContext(), "상세 내역을 불러오는 중 오류가 발생했습니다.", 0).show();
        }
    }
}
