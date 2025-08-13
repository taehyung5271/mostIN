package com.example.mostin.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderingAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.models.Ordering;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.utils.AppCache; // Import AppCache
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderingFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText orderSummaryEdit;
    private Button copyAllButton;
    
    private String employeeId;
    private String employeeName;
    private OrderingAdapter adapter;
    private List<GoodsModel> goodsList; // Add this line

    public OrderingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);

        // 초기화
        recyclerView = view.findViewById(R.id.recycler_goods);
        orderSummaryEdit = view.findViewById(R.id.edit_order_summary);
        copyAllButton = view.findViewById(R.id.btn_copy_all);
        

        // 사용자 정보 가져오기
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
        }

        // RecyclerView 설정 - 출근 근무표와 동일한 방식 사용
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadGoodsData();

        // 전체 복사 버튼 설정
        copyAllButton.setOnClickListener(v -> {
            String orderText = orderSummaryEdit.getText().toString();
            if (!orderText.isEmpty()) {
                // 클립보드에 복사
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("order_summary", orderText);
                clipboard.setPrimaryClip(clip);

                // DB에 저장
                saveOrderToDB(orderText);

                Toast.makeText(requireContext(), "주문 내용이 복사되고 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadGoodsData() {
        // Check cache first
        if (AppCache.getInstance().containsKey("goodsList")) {
            Log.d("OrderingFragment", "Loading goods data from cache.");
            OrderingFragment.this.goodsList = AppCache.getInstance().getCachedList("goodsList", GoodsModel.class);
            adapter = new OrderingAdapter(OrderingFragment.this.goodsList, (barcode, boxCount) -> {
                String orderLine = String.format(Locale.getDefault(), "%s %d 박스", barcode, boxCount);
                String currentText = orderSummaryEdit.getText().toString();
                if (!currentText.isEmpty()) {
                    orderLine = currentText + "\n" + orderLine;
                }
                orderSummaryEdit.setText(orderLine);
            });
            recyclerView.setAdapter(adapter);
            return;
        }

        Log.d("OrderingFragment", "Loading goods data from API.");
        ApiService apiService = ApiClient.getApiService();
        Call<List<GoodsModel>> call = apiService.getAllGoods();

        call.enqueue(new Callback<List<GoodsModel>>() {
            @Override
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderingFragment.this.goodsList = response.body(); // Assign to member variable
                    AppCache.getInstance().putCachedList("goodsList", OrderingFragment.this.goodsList); // Cache the data
                    adapter = new OrderingAdapter(OrderingFragment.this.goodsList, (barcode, boxCount) -> {
                        String orderLine = String.format(Locale.getDefault(), "%s %d 박스", barcode, boxCount);
                        String currentText = orderSummaryEdit.getText().toString();
                        if (!currentText.isEmpty()) {
                            orderLine = currentText + "\n" + orderLine;
                        }
                        orderSummaryEdit.setText(orderLine);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "상품 목록을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Log.e("OrderingFragment", "Error loading goods data", t);
                Toast.makeText(getContext(), "상품 목록 로딩 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOrderToDB(String orderText) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String[] orderLines = orderText.split("\n");

        Log.d("OrderSave", "Saving orders for " + employeeName + " on " + currentDate);
        Log.d("OrderSave", "Total orders to save: " + orderLines.length);

        ApiService apiService = ApiClient.getApiService();

        // First, delete previous orders for the current date
        Call<Void> deleteCall = apiService.deleteOrdersByDate(employeeId, currentDate);
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("OrderSave", "Previous orders deleted successfully.");
                    // Then, save all new orders
                    for (String line : orderLines) {
                        String[] parts = line.split(" ");
                        if (parts.length >= 2) {
                            String barcode = parts[0];
                            int boxCount = Integer.parseInt(parts[1]);

                            // Find goodsName from goodsList
                            String goodsName = "";
                            if (goodsList != null) {
                                for (GoodsModel item : goodsList) {
                                    if (item.getBarcode().equals(barcode)) {
                                        goodsName = item.getName();
                                        break;
                                    }
                                }
                            }

                            Log.d("OrderSave", "Saving order: Barcode=" + barcode + ", BoxCount=" + boxCount + ", GoodsName=" + goodsName);
                            Ordering newOrder = new Ordering(currentDate, employeeId, barcode, employeeName, boxCount, goodsName); // Pass goodsName here
                            Call<Ordering> createCall = apiService.createOrder(newOrder);
                            createCall.enqueue(new Callback<Ordering>() {
                                @Override
                                public void onResponse(Call<Ordering> call, Response<Ordering> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("OrderSave", "Order saved successfully: " + barcode);
                                    } else {
                                        Log.e("OrderSave", "Failed to save order: " + barcode + ", Code: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Ordering> call, Throwable t) {
                                    Log.e("OrderSave", "Error saving order: " + barcode, t);
                                }
                            });
                        }
                    }
                } else {
                    Log.e("OrderSave", "Failed to delete previous orders, Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("OrderSave", "Error deleting previous orders", t);
            }
        });
    }
} 