package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.AdminGoodsAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.utils.GoodsCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.Toast;

public class AdminGoodsFragment extends Fragment {
    private static final String TAG = "AdminGoodsFragment";
    
    private RecyclerView recyclerView;
    private AdminGoodsAdapter adapter;
    private Button insertBtn, editBtn, deleteBtn;
    private Button actionBtn;
    private List<GoodsModel> goodsList;
    private int currentMode = 0; // 0: 일반모드, 1: 삽입모드, 2: 수정모드, 3: 삭제모드
    
    // 캐싱 시스템
    private GoodsCache goodsCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_goods, container, false);
        
        // 캐시 시스템 초기화
        goodsCache = GoodsCache.getInstance();
        goodsCache.initialize(getContext());
        
        initializeViews(view);
        setupRecyclerView();
        setupButtons();
        
        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.adminGoodsRecyclerView);
        insertBtn = view.findViewById(R.id.insertBtn);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        actionBtn = view.findViewById(R.id.actionBtn);
        actionBtn.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        // 먼저 캐시에서 데이터 시도 (즉시 실행)
        List<GoodsModel> cachedData = goodsCache.getCachedGoods();
        
        if (cachedData != null) {
            // 캐시된 데이터를 즉시 표시 (0ms 지연)
            Log.d(TAG, "⚡ 캐시에서 즉시 로드: " + goodsCache.getCacheInfo());
            goodsList = cachedData;
            setupAdapterImmediate();
            
            // 백그라운드에서 조용히 업데이트 확인 (사용자는 모름)
            checkForUpdatesInBackground();
        } else {
            // 캐시가 없는 경우만 서버 로딩
            Log.d(TAG, "📡 서버에서 첫 로드");
            loadGoodsFromServer(true);
        }
    }
    
    private void setupAdapterImmediate() {
        // 즉시 어댑터 설정 (지연 없음)
        if (goodsList != null) {
            adapter = new AdminGoodsAdapter(goodsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "⚡ 즉시 UI 표시 완료 (" + goodsList.size() + "개 상품)");
        }
    }
    
    private void setupAdapter() {
        if (goodsList != null) {
            adapter = new AdminGoodsAdapter(goodsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "어댑터 설정 완료 (상품 " + goodsList.size() + "개)");
        }
    }
    
    private void loadGoodsFromServer(boolean showLoading) {
        if (showLoading) {
            // 로딩 표시 (필요시 ProgressBar 추가 가능)
            Log.d(TAG, "서버에서 상품 데이터 로딩 중...");
        }
        
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllGoods().enqueue(new Callback<List<GoodsModel>>() {
            @Override
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    goodsList = response.body();
                    
                    // 캐시에 저장
                    goodsCache.setCachedGoods(goodsList);
                    
                    // UI 업데이트
                    if (adapter == null) {
                        setupAdapter();
                    } else {
                        adapter.updateData(goodsList);
                    }
                    
                    Log.d(TAG, "서버에서 상품 데이터 로드 완료: " + goodsList.size() + "개");
                } else {
                    Toast.makeText(getContext(), "상품 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "상품 로딩 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "네트워크 오류: " + t.getMessage(), t);
            }
        });
    }
    
    private void checkForUpdatesInBackground() {
        // 캐시가 10분 이상 오래된 경우에만 백그라운드 업데이트 (더 자주 갱신)
        long cacheAge = goodsCache.getCacheAge();
        long tenMinutes = 10 * 60 * 1000; // 10분
        
        if (cacheAge > tenMinutes) {
            Log.d(TAG, "🔄 백그라운드 업데이트 실행 (캐시 나이: " + (cacheAge / 60000) + "분)");
            // 사용자 눈에 띄지 않게 조용히 업데이트
            loadGoodsFromServer(false);
        } else {
            Log.d(TAG, "✅ 캐시 신선함 (나이: " + (cacheAge / 60000) + "분, 업데이트 불필요)");
        }
    }

    private void setupButtons() {
        insertBtn.setOnClickListener(v -> handleInsertMode());
        editBtn.setOnClickListener(v -> handleEditMode());
        deleteBtn.setOnClickListener(v -> handleDeleteMode());
        actionBtn.setOnClickListener(v -> handleActionButton());
    }

    private void handleInsertMode() {
        resetMode();
        currentMode = 1;
        actionBtn.setText("삽입완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.setEditMode(true);
        adapter.addNewRow();
        recyclerView.smoothScrollToPosition(goodsList.size());
    }

    private void handleEditMode() {
        resetMode();
        currentMode = 2;
        actionBtn.setText("수정완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.setEditMode(true);
    }

    private void handleDeleteMode() {
        resetMode();
        currentMode = 3;
        actionBtn.setText("삭제완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.showCheckboxes(true);
    }

    private void handleActionButton() {
        ApiService apiService = ApiClient.getApiService();

        switch (currentMode) {
            case 1: // 삽입모드
                GoodsModel newItem = adapter.getNewItem();
                if (newItem != null) {
                    apiService.createGoods(newItem).enqueue(new Callback<GoodsModel>() {
                        @Override
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                GoodsModel createdItem = response.body();
                                
                                // 캐시에 새 상품 추가
                                goodsCache.addGoods(createdItem);
                                
                                // UI 업데이트 (전체 새로고침 대신 캐시 사용)
                                updateUIFromCache();
                                
                                Toast.makeText(getContext(), "상품이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "상품 추가 완료: " + createdItem.getName());
                            } else {
                                Toast.makeText(getContext(), "상품 추가 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "상품 추가 실패: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "상품 추가 네트워크 오류: " + t.getMessage(), t);
                        }
                    });
                }
                break;
                
            case 2: // 수정모드
                List<GoodsModel> updatedItems = adapter.getUpdatedItems();
                int totalUpdates = updatedItems.size();
                final int[] completedUpdates = {0};
                
                for (GoodsModel item : updatedItems) {
                    apiService.updateGoods(item.getBarcode(), item).enqueue(new Callback<GoodsModel>() {
                        @Override
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                GoodsModel updatedItem = response.body();
                                
                                // 캐시에서 해당 상품 업데이트
                                goodsCache.updateGoods(updatedItem);
                                
                                completedUpdates[0]++;
                                
                                // 모든 업데이트가 완료되면 UI 갱신
                                if (completedUpdates[0] == totalUpdates) {
                                    updateUIFromCache();
                                    Toast.makeText(getContext(), "상품이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "상품 수정 완료: " + totalUpdates + "개");
                                }
                            } else {
                                Toast.makeText(getContext(), "상품 수정 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "상품 수정 실패: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "상품 수정 네트워크 오류: " + t.getMessage(), t);
                        }
                    });
                }
                break;
                
            case 3: // 삭제모드
                List<String> selectedBarcodes = adapter.getSelectedItems();
                
                if (selectedBarcodes.isEmpty()) {
                    Toast.makeText(getContext(), "삭제할 상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    resetMode();
                    return;
                }
                
                Log.d(TAG, "삭제 요청 상품들: " + selectedBarcodes.toString());
                
                int totalDeletes = selectedBarcodes.size();
                final int[] completedDeletes = {0};
                final int[] failedDeletes = {0};
                
                for (String barcode : selectedBarcodes) {
                    Log.d(TAG, "삭제 요청: " + barcode);
                    
                    // 빈 바코드 체크
                    if (barcode == null || barcode.trim().isEmpty()) {
                        Log.w(TAG, "빈 바코드 스킵");
                        completedDeletes[0]++;
                        continue;
                    }
                    
                    apiService.deleteGoods(barcode.trim()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // 삭제 성공
                                goodsCache.removeGoods(barcode);
                                Log.d(TAG, "✅ 삭제 성공: " + barcode);
                            } else {
                                // 삭제 실패
                                failedDeletes[0]++;
                                Log.e(TAG, "❌ 삭제 실패: " + barcode + " - " + response.code() + ": " + response.message());
                                
                                // 404 에러인 경우 (이미 삭제된 상품) 캐시에서도 제거하고 성공으로 처리
                                if (response.code() == 404) {
                                    goodsCache.removeGoods(barcode);
                                    failedDeletes[0]--; // 실패 카운트에서 제외
                                    Log.d(TAG, "✅ 404 상품이므로 성공으로 처리: " + barcode);
                                }
                            }
                            
                            completedDeletes[0]++;
                            
                            // 모든 삭제 시도가 완료되면 UI 갱신
                            if (completedDeletes[0] == totalDeletes) {
                                updateUIFromCache();
                                
                                int successCount = totalDeletes - failedDeletes[0];
                                
                                if (failedDeletes[0] == 0) {
                                    Toast.makeText(getContext(), "✅ " + successCount + "개 상품이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "⚠️ " + successCount + "개 삭제 성공, " + failedDeletes[0] + "개 실패", Toast.LENGTH_LONG).show();
                                }
                                
                                Log.d(TAG, "🏁 삭제 완료 - 성공: " + successCount + ", 실패: " + failedDeletes[0]);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // 네트워크 오류
                            failedDeletes[0]++;
                            completedDeletes[0]++;
                            
                            Log.e(TAG, "🌐 네트워크 오류: " + barcode + " - " + t.getMessage(), t);
                            
                            // 모든 삭제 시도가 완료되면 UI 갱신
                            if (completedDeletes[0] == totalDeletes) {
                                updateUIFromCache();
                                
                                int successCount = totalDeletes - failedDeletes[0];
                                
                                if (successCount > 0) {
                                    Toast.makeText(getContext(), "⚠️ " + successCount + "개 삭제 성공, " + failedDeletes[0] + "개 네트워크 오류", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "❌ 삭제 실패: 네트워크 오류", Toast.LENGTH_SHORT).show();
                                }
                                
                                Log.d(TAG, "🏁 삭제 완료 - 성공: " + successCount + ", 네트워크 오류: " + failedDeletes[0]);
                            }
                        }
                    });
                }
                break;
        }
        resetMode();
    }
    
    private void updateUIFromCache() {
        List<GoodsModel> cachedData = goodsCache.getCachedGoods();
        if (cachedData != null) {
            goodsList = cachedData;
            if (adapter != null) {
                // 즉시 업데이트 (지연 없음)
                getActivity().runOnUiThread(() -> {
                    adapter.updateData(goodsList);
                    Log.d(TAG, "⚡ 캐시에서 즉시 UI 업데이트: " + goodsList.size() + "개 상품");
                });
            }
        }
    }

    private void refreshGoodsList() {
        // 개선된 새로고침: 강제로 서버에서 최신 데이터 가져오기
        Log.d(TAG, "상품 목록 강제 새로고침 요청");
        loadGoodsFromServer(true);
    }
    
    // 추가: 수동 새로고침 메서드 (Pull-to-Refresh 등에서 사용 가능)
    public void forceRefresh() {
        Log.d(TAG, "강제 새로고침 - 캐시 클리어 후 서버에서 로드");
        goodsCache.clearCache();
        loadGoodsFromServer(true);
    }

    private void resetMode() {
        currentMode = 0;
        actionBtn.setVisibility(View.GONE);
        adapter.setEditMode(false);
        adapter.showCheckboxes(false);
    }
} 