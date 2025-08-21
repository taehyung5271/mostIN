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
import android.text.TextWatcher;
import android.text.Editable;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
    private TextInputEditText searchEditText;
    private ChipGroup chipGroup;
    private MaterialButton copyAllButton;
    private MaterialButton clearAllButton;
    
    private String employeeId;
    private String employeeName;
    private OrderingAdapter adapter;
    private List<GoodsModel> goodsList;
    private List<GoodsModel> filteredGoodsList;

    public OrderingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);

        // 초기화
        recyclerView = view.findViewById(R.id.recycler_goods);
        orderSummaryEdit = view.findViewById(R.id.edit_order_summary);
        searchEditText = view.findViewById(R.id.edit_search);
        chipGroup = view.findViewById(R.id.chip_group_categories);
        copyAllButton = view.findViewById(R.id.btn_copy_all);
        clearAllButton = view.findViewById(R.id.btn_clear_all);
        
        // 필터링된 리스트 초기화
        filteredGoodsList = new ArrayList<>();
        

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
        setupSearchAndFilter();

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
        Log.d("OrderingFragment", "Loading goods data from API (cache temporarily disabled for debugging).");
        
        // 캐시 체크 임시 비활성화 - 디버깅용
        // if (AppCache.getInstance().containsKey("goodsList")) {
        //     Log.d("OrderingFragment", "Loading goods data from cache.");
        //     this.goodsList = AppCache.getInstance().getCachedList("goodsList", GoodsModel.class);
        //     this.filteredGoodsList = new ArrayList<>(this.goodsList);
        //     adapter = new OrderingAdapter(this.filteredGoodsList, this);
        //     recyclerView.setAdapter(adapter);
        //     // 캐시에서 로드된 후 초기 필터링 적용
        //     filterGoods();
        //     return;
        // }

        Log.d("OrderingFragment", "Loading goods data from API.");
        ApiService apiService = ApiClient.getApiService();
        Call<List<GoodsModel>> call = apiService.getAllGoods();

        call.enqueue(new Callback<List<GoodsModel>>() {
            @Override
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                Log.d("OrderingFragment", "API Response received. Code: " + response.code());
                
                if (response.isSuccessful() && response.body() != null) {
                    goodsList = response.body();
                    
                    Log.d("OrderingFragment", "API returned " + goodsList.size() + " items");
                    for (int i = 0; i < Math.min(goodsList.size(), 3); i++) {
                        GoodsModel item = goodsList.get(i);
                        Log.d("OrderingFragment", "Item " + i + ": " + item.getName() + ", Category: " + item.getCategory());
                    }
                    
                    // API 데이터가 비어있으면 샘플 데이터 사용
                    if (goodsList.isEmpty()) {
                        Log.d("OrderingFragment", "API returned empty list, using sample data");
                        goodsList = createSampleData();
                    }
                    
                    filteredGoodsList = new ArrayList<>(goodsList);
                    AppCache.getInstance().putCachedList("goodsList", goodsList);
                    adapter = new OrderingAdapter(filteredGoodsList, OrderingFragment.this);
                    recyclerView.setAdapter(adapter);
                    // API에서 로드된 후 초기 필터링 적용 (전체 선택)
                    filterGoods();
                } else {
                    Log.e("OrderingFragment", "API failed. Response code: " + response.code());
                    // 실패 시 샘플 데이터 사용
                    goodsList = createSampleData();
                    filteredGoodsList = new ArrayList<>(goodsList);
                    adapter = new OrderingAdapter(filteredGoodsList, OrderingFragment.this);
                    recyclerView.setAdapter(adapter);
                    // 샘플 데이터 로드 후 초기 필터링 적용
                    filterGoods();
                    Toast.makeText(getContext(), "서버 연결 실패. 샘플 데이터를 사용합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Log.e("OrderingFragment", "API call failed", t);
                
                // API 실패 시 샘플 데이터 생성
                goodsList = createSampleData();
                filteredGoodsList = new ArrayList<>(goodsList);
                adapter = new OrderingAdapter(filteredGoodsList, OrderingFragment.this);
                recyclerView.setAdapter(adapter);
                // API 실패 시에도 초기 필터링 적용
                filterGoods();
                
                Toast.makeText(getContext(), "서버 연결 실패. 샘플 데이터를 사용합니다.", Toast.LENGTH_SHORT).show();
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

    private void setupSearchAndFilter() {
        // 검색 기능 설정
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterGoods();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // 칩 클릭 리스너 설정
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> filterGoods());
    }

    private void filterGoods() {
        if (goodsList == null) {
            Log.d("OrderingFragment", "goodsList is null, cannot filter");
            // 어댑터가 있다면 빈 리스트로라도 업데이트
            if (adapter != null) {
                adapter.updateList(new ArrayList<>());
            }
            return;
        }
        
        if (goodsList.isEmpty()) {
            Log.d("OrderingFragment", "goodsList is empty, cannot filter");
            // 어댑터가 있다면 빈 리스트로라도 업데이트
            if (adapter != null) {
                adapter.updateList(new ArrayList<>());
            }
            return;
        }

        filteredGoodsList.clear();
        String searchText = searchEditText.getText().toString().toLowerCase().trim();
        
        // 선택된 카테고리 확인
        GoodsModel.Category selectedCategory = getSelectedCategory();
        
        Log.d("OrderingFragment", "Filtering - Search: '" + searchText + "', Category: " + selectedCategory);
        Log.d("OrderingFragment", "Total goods: " + goodsList.size());
        
        // 첫 번째 아이템의 카테고리 상세 정보 출력
        if (!goodsList.isEmpty()) {
            GoodsModel firstItem = goodsList.get(0);
            Log.d("OrderingFragment", "First item details - Name: " + firstItem.getName() + 
                  ", Category: " + firstItem.getCategory() + 
                  ", Category class: " + (firstItem.getCategory() != null ? firstItem.getCategory().getClass().getName() : "null"));
        }

        for (GoodsModel goods : goodsList) {
            Log.d("OrderingFragment", "Checking goods: " + goods.getName() + ", Category: " + goods.getCategory());
            
            boolean matchesSearch = searchText.isEmpty() || 
                (goods.getName() != null && goods.getName().toLowerCase().contains(searchText));
            
            // 카테고리 필터링 로직 개선
            boolean matchesCategory;
            if (selectedCategory == null) {
                // "전체" 선택 시 모든 상품 표시
                matchesCategory = true;
                Log.d("OrderingFragment", "  - Selected category is NULL (전체), including item");
            } else {
                // 특정 카테고리 선택 시
                if (goods.getCategory() == null) {
                    // 카테고리가 null인 상품들은 "전체"에서만 표시
                    matchesCategory = false;
                    Log.d("OrderingFragment", "  - Item category is NULL, excluding from specific category filter");
                } else {
                    matchesCategory = goods.getCategory().equals(selectedCategory);
                    Log.d("OrderingFragment", "  - Comparing: " + goods.getCategory() + " equals " + selectedCategory + " = " + matchesCategory);
                }
            }

            Log.d("OrderingFragment", "  - Matches search: " + matchesSearch + ", Matches category: " + matchesCategory);

            if (matchesSearch && matchesCategory) {
                filteredGoodsList.add(goods);
                Log.d("OrderingFragment", "  - Added to filtered list");
            }
        }

        Log.d("OrderingFragment", "Filtered result count: " + filteredGoodsList.size());

        // 어댑터 업데이트 - Main Thread에서 실행 보장
        if (adapter != null) {
            Log.d("OrderingFragment", "Calling adapter.updateList() with " + filteredGoodsList.size() + " items");
            
            // UI Thread에서 실행 보장
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    adapter.updateList(filteredGoodsList);
                    Log.d("OrderingFragment", "adapter.updateList() completed on UI thread");
                });
            } else {
                adapter.updateList(filteredGoodsList);
                Log.d("OrderingFragment", "adapter.updateList() completed (no activity)");
            }
        } else {
            Log.e("OrderingFragment", "adapter is NULL! Cannot update RecyclerView");
        }
    }

    private GoodsModel.Category getSelectedCategory() {
        int checkedId = chipGroup.getCheckedChipId();
        Log.d("OrderingFragment", "Checked chip ID: " + checkedId + " (chip_all: " + R.id.chip_all + ")");
        
        if (checkedId == R.id.chip_all || checkedId == View.NO_ID) {
            Log.d("OrderingFragment", "Selected category: ALL (null)");
            return null; // 전체 선택
        } else if (checkedId == R.id.chip_domestic_beer) {
            Log.d("OrderingFragment", "Selected category: DOMESTIC_BEER");
            return GoodsModel.Category.DOMESTIC_BEER;
        } else if (checkedId == R.id.chip_imported_beer) {
            Log.d("OrderingFragment", "Selected category: IMPORTED_BEER");
            return GoodsModel.Category.IMPORTED_BEER;
        } else if (checkedId == R.id.chip_non_alcohol_beer) {
            Log.d("OrderingFragment", "Selected category: NON_ALCOHOL_BEER");
            return GoodsModel.Category.NON_ALCOHOL_BEER;
        }
        
        Log.d("OrderingFragment", "Selected category: UNKNOWN, returning null");
        return null;
    }

    private List<GoodsModel> createSampleData() {
        List<GoodsModel> sampleData = new ArrayList<>();
        
        // 국산맥주 샘플
        GoodsModel domestic1 = new GoodsModel("8801234567001", "카스 프레시 500ml", GoodsModel.Category.DOMESTIC_BEER);
        GoodsModel domestic2 = new GoodsModel("8801234567002", "하이트 엑스트라 콜드 500ml", GoodsModel.Category.DOMESTIC_BEER);
        GoodsModel domestic3 = new GoodsModel("8801234567003", "테라 맥주 500ml", GoodsModel.Category.DOMESTIC_BEER);
        GoodsModel domestic4 = new GoodsModel("8801234567004", "클라우드 생맥주 500ml", GoodsModel.Category.DOMESTIC_BEER);
        
        sampleData.add(domestic1);
        sampleData.add(domestic2);
        sampleData.add(domestic3);
        sampleData.add(domestic4);
        
        // 수입맥주 샘플  
        GoodsModel imported1 = new GoodsModel("8801234567005", "버드와이저 500ml", GoodsModel.Category.IMPORTED_BEER);
        GoodsModel imported2 = new GoodsModel("8801234567006", "산토리 프리미엄 맥주 355ml", GoodsModel.Category.IMPORTED_BEER);
        GoodsModel imported3 = new GoodsModel("8801234567007", "호가든 화이트 맥주 330ml", GoodsModel.Category.IMPORTED_BEER);
        GoodsModel imported4 = new GoodsModel("8801234567008", "스텔라 아르투아 330ml", GoodsModel.Category.IMPORTED_BEER);
        GoodsModel imported5 = new GoodsModel("8801234567009", "코로나 엑스트라 355ml", GoodsModel.Category.IMPORTED_BEER);
        
        sampleData.add(imported1);
        sampleData.add(imported2);
        sampleData.add(imported3);
        sampleData.add(imported4);
        sampleData.add(imported5);
        
        // 무알코올맥주 샘플
        GoodsModel nonAlcohol1 = new GoodsModel("8801234567010", "카스 제로 350ml", GoodsModel.Category.NON_ALCOHOL_BEER);
        GoodsModel nonAlcohol2 = new GoodsModel("8801234567011", "하이트 제로 355ml", GoodsModel.Category.NON_ALCOHOL_BEER);
        GoodsModel nonAlcohol3 = new GoodsModel("8801234567012", "클라우드 제로 355ml", GoodsModel.Category.NON_ALCOHOL_BEER);
        
        sampleData.add(nonAlcohol1);
        sampleData.add(nonAlcohol2);
        sampleData.add(nonAlcohol3);
        
        Log.d("OrderingFragment", "Created " + sampleData.size() + " sample products");
        
        // 샘플 데이터의 카테고리 확인
        for (GoodsModel item : sampleData) {
            Log.d("OrderingFragment", "Sample: " + item.getName() + " -> Category: " + item.getCategory());
        }
        
        return sampleData;
    }
} 