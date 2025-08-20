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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderingAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.models.Ordering;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.utils.AppCache;
import com.example.mostin.utils.SpaceItemDecoration;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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

public class OrderingFragment extends Fragment implements OrderingAdapter.OnCopyClickListener {
    private RecyclerView recyclerView;
    private TextInputEditText orderSummaryEdit;
    private MaterialButton copyAllButton;
    private MaterialButton clearAllButton;
    
    private String employeeId;
    private String employeeName;
    private OrderingAdapter adapter;
    private List<GoodsModel> goodsList;

    public OrderingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);

        // 초기화
        recyclerView = view.findViewById(R.id.recycler_goods);
        orderSummaryEdit = view.findViewById(R.id.edit_order_summary);
        copyAllButton = view.findViewById(R.id.btn_copy_all);
        clearAllButton = view.findViewById(R.id.btn_clear_all);
        

        // 사용자 정보 가져오기
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
        }

        // RecyclerView 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        
        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
            recyclerView.getContext(),
            layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(dividerItemDecoration);
        
        loadGoodsData();

        // 버튼 설정
        copyAllButton.setOnClickListener(v -> copyOrderSummary());
        clearAllButton.setOnClickListener(v -> clearOrderSummary());

        return view;
    }

    // OrderingAdapter.OnCopyClickListener 구현
    @Override
    public void onCopyClick(String barcode, String productName, int quantity) {
        String orderLine = String.format(Locale.getDefault(), "%s %d박스", 
            barcode, quantity);
        
        String currentText = orderSummaryEdit.getText().toString();
        if (!currentText.isEmpty()) {
            orderLine = currentText + "\n" + orderLine;
        }
        
        orderSummaryEdit.setText(orderLine);
        Toast.makeText(requireContext(), "주문 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }
    
    private void copyOrderSummary() {
        String orderText = orderSummaryEdit.getText().toString();
        if (!orderText.isEmpty()) {
            // 클립보드에 복사
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("order_summary", orderText);
            clipboard.setPrimaryClip(clip);

            // DB에 저장
            saveOrderToDB(orderText);

            Toast.makeText(requireContext(), "주문 내용이 복사되고 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "주문 내용이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void clearOrderSummary() {
        orderSummaryEdit.setText("");
        Toast.makeText(requireContext(), "주문 내용이 지워졌습니다.", Toast.LENGTH_SHORT).show();
    }

    private void loadGoodsData() {
        // Check cache first
        if (AppCache.getInstance().containsKey("goodsList")) {
            Log.d("OrderingFragment", "Loading goods data from cache.");
            this.goodsList = AppCache.getInstance().getCachedList("goodsList", GoodsModel.class);
            adapter = new OrderingAdapter(this.goodsList, this);
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
                    goodsList = response.body();
                    AppCache.getInstance().putCachedList("goodsList", goodsList);
                    adapter = new OrderingAdapter(goodsList, OrderingFragment.this);
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
                        // 형식: "바코드 수량박스"
                        String[] parts = line.split(" ");
                        if (parts.length >= 2) {
                            String barcode = parts[0];
                            String quantityPart = parts[1]; // "5박스"
                            
                            // "박스" 제거하고 숫자만 추출
                            if (quantityPart.endsWith("박스")) {
                                String quantityStr = quantityPart.substring(0, quantityPart.length() - 2); // "5박스" -> "5"
                                
                                try {
                                    int quantity = Integer.parseInt(quantityStr);
                                    
                                    // 바코드로 상품명 찾기
                                    String productName = "";
                                    if (goodsList != null) {
                                        for (GoodsModel item : goodsList) {
                                            if (item.getBarcode().equals(barcode)) {
                                                productName = item.getName();
                                                break;
                                            }
                                        }
                                    }
                                    
                                    Log.d("OrderSave", "Saving order: Barcode=" + barcode + ", Quantity=" + quantity + ", ProductName=" + productName);
                                    Ordering newOrder = new Ordering(currentDate, employeeId, barcode, employeeName, quantity, productName);
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
                                } catch (NumberFormatException e) {
                                    Log.e("OrderSave", "Invalid quantity format in: " + line);
                                }
                            }
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