package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mostin.fragments.UserOrderHistoryFragment;
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
public class UserOrderHistoryFragment extends Fragment {
    private OrderHistoryAdapter adapter;
    private String employeeId;
    private String employeeName;
    private RecyclerView recyclerView;
    private String workPlaceName;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_order_history, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        Bundle args = getArguments();
        if (args != null) {
            this.employeeId = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID);
            this.employeeName = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME);
            this.workPlaceName = args.getString(CommutingRegistrationFragment.ARG_WORK_PLACE_NAME);
        }
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.adapter = new OrderHistoryAdapter(new OrderHistoryAdapter.OnOrderClickListener() { // from class: com.example.mostin.fragments.UserOrderHistoryFragment$$ExternalSyntheticLambda0
            @Override // com.example.mostin.adapters.OrderHistoryAdapter.OnOrderClickListener
            public final void onOrderClick(OrderHistoryModel orderHistoryModel) {
                this.f$0.showOrderDetailDialog(orderHistoryModel);
            }
        });
        this.recyclerView.setAdapter(this.adapter);
        loadOrderHistory();
        return view;
    }

    private void loadOrderHistory() {
        if (this.employeeId == null || this.employeeName == null) {
            Log.e("UserOrderHistory", "Employee info is null - ID: " + this.employeeId + ", Name: " + this.employeeName);
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", 0).show();
        } else {
            Log.d("UserOrderHistory", "Loading orders for - ID: " + this.employeeId + ", Name: " + this.employeeName);
            ApiService apiService = ApiClient.getApiService();
            Call<List<Ordering>> call = apiService.getOrdersByEmployee(this.employeeId);
            call.enqueue(new AnonymousClass1());
        }
    }

    /* renamed from: com.example.mostin.fragments.UserOrderHistoryFragment$1, reason: invalid class name */
    class AnonymousClass1 implements Callback<List<Ordering>> {
        AnonymousClass1() {
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<List<Ordering>> call, Response<List<Ordering>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Ordering> orders = response.body();
                List<OrderHistoryModel> orderHistory = new ArrayList<>();
                Map<String, List<Ordering>> groupedOrders = new LinkedHashMap<>();
                for (Ordering order : orders) {
                    String orderDate = order.getOrderingDay().toString();
                    groupedOrders.computeIfAbsent(orderDate, new Function() { // from class: com.example.mostin.fragments.UserOrderHistoryFragment$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return UserOrderHistoryFragment.AnonymousClass1.lambda$onResponse$0((String) obj);
                        }
                    }).add(order);
                }
                List<String> sortedDates = new ArrayList<>(groupedOrders.keySet());
                sortedDates.sort(new Comparator() { // from class: com.example.mostin.fragments.UserOrderHistoryFragment$1$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return LocalDate.parse((String) obj2).compareTo((ChronoLocalDate) LocalDate.parse((String) obj));
                    }
                });
                for (String date : sortedDates) {
                    orderHistory.add(new OrderHistoryModel(date, UserOrderHistoryFragment.this.employeeId, UserOrderHistoryFragment.this.employeeName, UserOrderHistoryFragment.this.workPlaceName));
                }
                Log.d("UserOrderHistory", "Orders loaded: " + orderHistory.size());
                UserOrderHistoryFragment.this.adapter.setOrderHistory(orderHistory, UserOrderHistoryFragment.this.workPlaceName);
                return;
            }
            Log.e("UserOrderHistory", "Failed to load orders: " + response.code());
            Toast.makeText(UserOrderHistoryFragment.this.getContext(), "발주 내역을 불러오는 데 실패했습니다.", 0).show();
        }

        static /* synthetic */ List lambda$onResponse$0(String k) {
            return new ArrayList();
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<List<Ordering>> call, Throwable t) {
            Log.e("UserOrderHistory", "Error loading orders", t);
            Toast.makeText(UserOrderHistoryFragment.this.getContext(), "발주 내역 로딩 중 오류가 발생했습니다.", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrderDetailDialog(OrderHistoryModel order) {
        try {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_order_detail, (ViewGroup) null);
            TextView workplaceText = (TextView) dialogView.findViewById(R.id.text_workplace);
            TextView dateText = (TextView) dialogView.findViewById(R.id.text_date);
            RecyclerView detailRecyclerView = (RecyclerView) dialogView.findViewById(R.id.recycler_order_detail);
            workplaceText.setText(this.workPlaceName);
            dateText.setText(order.getOrderingDay());
            detailRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            final OrderDetailAdapter detailAdapter = new OrderDetailAdapter();
            detailRecyclerView.setAdapter(detailAdapter);
            ApiService apiService = ApiClient.getApiService();
            Call<List<Ordering>> call = apiService.getOrderDetailsByDate(this.employeeId, order.getOrderingDay());
            call.enqueue(new Callback<List<Ordering>>() { // from class: com.example.mostin.fragments.UserOrderHistoryFragment.2
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
                    Toast.makeText(UserOrderHistoryFragment.this.getContext(), "상세 내역을 불러오는 데 실패했습니다.", 0).show();
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<Ordering>> call2, Throwable t) {
                    Log.e("UserOrderHistory", "Error loading order details", t);
                    Toast.makeText(UserOrderHistoryFragment.this.getContext(), "상세 내역 로딩 중 오류가 발생했습니다.", 0).show();
                }
            });
            new AlertDialog.Builder(requireContext()).setView(dialogView).setPositiveButton("확인", (DialogInterface.OnClickListener) null).show();
        } catch (Exception e) {
            Log.e("UserOrderHistory", "Error showing order detail dialog", e);
            Toast.makeText(requireContext(), "상세 내역을 불러오는 중 오류가 발생했습니다.", 0).show();
        }
    }
}
