package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
// NestedScrollView, AppBarLayout, CollapsingToolbarLayout import 제거

import com.example.mostin.R;
import com.example.mostin.adapters.AdminGoodsAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.utils.GoodsCache;
import com.example.mostin.activities.AdminHomeScreen;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Color;
import android.content.res.ColorStateList;

public class AdminGoodsFragment extends Fragment {
    private static final String TAG = "AdminGoodsFragment";
    
    private RecyclerView recyclerView;
    private AdminGoodsAdapter adapter;
    private com.google.android.material.button.MaterialButton insertBtn, editBtn, deleteBtn;
    
    // 새로운 상태 바 관련 변수들
    private LinearLayout statusBar;
    private ImageView statusIcon;
    private TextView statusText;
    private com.google.android.material.button.MaterialButton fixedActionBtn;
    
    // 단순화: AppBarLayout, CollapsingToolbar 제거
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
        initializeStatusBar(view);
        setupRecyclerView();
        setupButtons();
        
        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.adminGoodsRecyclerView);
        insertBtn = view.findViewById(R.id.insertBtn);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        
        // 버튼 클릭 효과 추가
        addButtonClickEffect(insertBtn);
        addButtonClickEffect(editBtn);
        addButtonClickEffect(deleteBtn);
    }

    /**
     * 상태 바 초기화 (단순화 버전)
     */
    private void initializeStatusBar(View view) {
        // 상태 바 참조
        statusBar = view.findViewById(R.id.statusBar);
        statusIcon = view.findViewById(R.id.statusIcon);
        statusText = view.findViewById(R.id.statusText);
        fixedActionBtn = view.findViewById(R.id.fixedActionBtn);
        
        // 고정 액션 버튼 클릭 리스너 설정
        if (fixedActionBtn != null) {
            fixedActionBtn.setOnClickListener(v -> handleActionButton());
        }
        
        // 기본 상태: 숨김
        if (statusBar != null) {
            statusBar.setVisibility(View.GONE);
        }
        
        Log.d(TAG, "고정형 상태 바 초기화 완료");
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
            adapter = new AdminGoodsAdapter(goodsList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "⚡ 즉시 UI 표시 완료 (" + goodsList.size() + "개 상품)");
        }
    }
    
    private void setupAdapter() {
        if (goodsList != null) {
            adapter = new AdminGoodsAdapter(goodsList, this);
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
    }

    /**
     * 상태 바 표시 (스티키 헤더 버전)
     */
    private void showStatusBar(int mode) {
        if (statusBar != null) {
            // 상태 바 콘텐츠 업데이트
            updateStatusBarContent(mode);
            
            // 스티키 헤더로 표시 (자연스러운 애니메이션)
            statusBar.setVisibility(View.VISIBLE);
            statusBar.setAlpha(0.0f);
            statusBar.animate()
                .alpha(1.0f)
                .setDuration(300)
                .start();
                
            Log.d(TAG, "스티키 상태 바 표시: 모드 " + mode);
        }
    }

    /**
     * 상태 바 숨김 (스티키 헤더 버전)
     */
    private void hideStatusBar() {
        if (statusBar != null) {
            statusBar.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(() -> statusBar.setVisibility(View.GONE))
                .start();
                
            Log.d(TAG, "스티키 상태 바 숨김");
        }
    }

    /**
     * 모드별 상태 바 업데이트
     */
    private void updateStatusBarContent(int mode) {
        if (statusIcon == null || statusText == null || fixedActionBtn == null) return;
        
        switch (mode) {
            case 1: // 등록 모드
                statusIcon.setImageResource(R.drawable.ic_add_24);
                statusText.setText("등록 모드");
                statusText.setTextColor(android.graphics.Color.parseColor("#10B981"));
                statusIcon.setColorFilter(android.graphics.Color.parseColor("#10B981"));
                fixedActionBtn.setText("등록 완료");
                fixedActionBtn.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#10B981")));
                break;
                
            case 2: // 편집 모드
                statusIcon.setImageResource(R.drawable.ic_edit_24);
                statusText.setText("편집 모드");
                statusText.setTextColor(android.graphics.Color.parseColor("#F59E0B"));
                statusIcon.setColorFilter(android.graphics.Color.parseColor("#F59E0B"));
                fixedActionBtn.setText("수정 완료");
                fixedActionBtn.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#F59E0B")));
                break;
                
            case 3: // 삭제 모드
                statusIcon.setImageResource(R.drawable.ic_delete_24);
                statusText.setText("삭제 모드");
                statusText.setTextColor(android.graphics.Color.parseColor("#EF4444"));
                statusIcon.setColorFilter(android.graphics.Color.parseColor("#EF4444"));
                fixedActionBtn.setText("삭제 완료");
                fixedActionBtn.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#EF4444")));
                break;
        }
        
        Log.d(TAG, "상태 바 내용 업데이트: 모드 " + mode);
    }

    private void handleInsertMode() {
        resetMode();
        currentMode = 1;
        
        // 기존 빈 데이터 정리 (등록 모드 진입 시) - 주석 처리 (originalDataSize 보존을 위해)
        // if (adapter != null) {
        //     adapter.removeEmptyItems();
        // }
        
        // 버튼 상태 업데이트
        updateButtonStates();
        
        // 상태 바 표시
        showStatusBar(1);
        
        // 어댑터 모드 설정 (1: 등록 모드)
        adapter.setMode(1);
        adapter.addNewRow();
        
    }

    private void handleEditMode() {
        resetMode();
        currentMode = 2;
        
        // 버튼 상태 업데이트
        updateButtonStates();
        
        // 상태 바 표시
        showStatusBar(2);
        
        // 어댑터 모드 설정 (2: 편집 모드)
        adapter.setMode(2);
    }

    private void handleDeleteMode() {
        resetMode();
        currentMode = 3;
        
        // 버튼 상태 업데이트
        updateButtonStates();
        
        // 상태 바 표시
        showStatusBar(3);
        
        // 어댑터 모드 설정 (3: 삭제 모드)
        adapter.setMode(3);
    }

    private void handleActionButton() {
        ApiService apiService = ApiClient.getApiService();

        switch (currentMode) {
            case 1: // 삽입모드 - 다중 상품 등록 지원
                List<GoodsModel> completedItems = adapter.getCompletedNewItems();
                if (!completedItems.isEmpty()) {
                    int totalItems = completedItems.size();
                    final int[] completedRegistrations = {0};
                    final int[] failedRegistrations = {0};
                    
                    Toast.makeText(getContext(), totalItems + "개 상품 등록 중...", Toast.LENGTH_SHORT).show();
                    
                    for (GoodsModel item : completedItems) {
                        apiService.createGoods(item).enqueue(new Callback<GoodsModel>() {
                            @Override
                            public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    GoodsModel createdItem = response.body();
                                    
                                    // 캐시에 새 상품 추가 (중복 방지를 위해 주석 처리 - 완료 후 서버에서 일괄 로드)
                                    // goodsCache.addGoods(createdItem);
                                    completedRegistrations[0]++;
                                    
                                    Log.d(TAG, "✅ 상품 등록 성공: " + createdItem.getName() + " (바코드: " + createdItem.getBarcode() + ")");
                                } else {
                                    failedRegistrations[0]++;
                                    Log.e(TAG, "상품 등록 실패: " + response.message());
                                }
                                
                                // 모든 등록이 완료되면 결과 처리
                                if (completedRegistrations[0] + failedRegistrations[0] == totalItems) {
                                    handleMultipleRegistrationComplete(completedRegistrations[0], failedRegistrations[0], totalItems);
                                }
                            }

                            @Override
                            public void onFailure(Call<GoodsModel> call, Throwable t) {
                                failedRegistrations[0]++;
                                Log.e(TAG, "상품 등록 네트워크 오류: " + t.getMessage(), t);
                                
                                // 모든 등록이 완료되면 결과 처리
                                if (completedRegistrations[0] + failedRegistrations[0] == totalItems) {
                                    handleMultipleRegistrationComplete(completedRegistrations[0], failedRegistrations[0], totalItems);
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "등록할 상품이 없습니다. 바코드와 상품명을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    // 빈 데이터 정리
                    if (adapter != null) {
                        adapter.removeEmptyItems();
                    }
                }
                break;
                
            case 2: // 수정모드
                List<GoodsModel> validItems = adapter.getValidEditedItems();
                List<String> itemsToDelete = adapter.getItemsToDeleteInEditMode();
                
                int totalOperations = validItems.size() + itemsToDelete.size();
                final int[] completedOperations = {0};
                final int[] updateSuccessCount = {0};
                final int[] deleteSuccessCount = {0};
                final int[] failureCount = {0};
                
                if (totalOperations == 0) {
                    Toast.makeText(getContext(), "수정할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                
                // 유효한 아이템들 업데이트
                for (GoodsModel item : validItems) {
                    apiService.updateGoods(item.getBarcode(), item).enqueue(new Callback<GoodsModel>() {
                        @Override
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                GoodsModel updatedItem = response.body();
                                goodsCache.updateGoods(updatedItem);
                                updateSuccessCount[0]++;
                                Log.d(TAG, "✅ 상품 업데이트 성공: " + updatedItem.getBarcode());
                            } else {
                                failureCount[0]++;
                                Log.e(TAG, "❌ 상품 업데이트 실패: " + response.message());
                            }
                            
                            completedOperations[0]++;
                            if (completedOperations[0] == totalOperations) {
                                handleEditModeComplete(updateSuccessCount[0], deleteSuccessCount[0], failureCount[0]);
                            }
                        }

                        @Override
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            failureCount[0]++;
                            completedOperations[0]++;
                            Log.e(TAG, "🌐 상품 업데이트 네트워크 오류: " + t.getMessage(), t);
                            
                            if (completedOperations[0] == totalOperations) {
                                handleEditModeComplete(updateSuccessCount[0], deleteSuccessCount[0], failureCount[0]);
                            }
                        }
                    });
                }
                
                // 빈 데이터가 된 아이템들 삭제
                for (String barcode : itemsToDelete) {
                    apiService.deleteGoods(barcode).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful() || response.code() == 404) {
                                // 삭제 성공 또는 이미 삭제된 경우
                                goodsCache.removeGoods(barcode);
                                deleteSuccessCount[0]++;
                                Log.d(TAG, "✅ 빈 데이터 상품 삭제 성공: " + barcode);
                            } else {
                                failureCount[0]++;
                                Log.e(TAG, "❌ 빈 데이터 상품 삭제 실패: " + barcode + " - " + response.message());
                            }
                            
                            completedOperations[0]++;
                            if (completedOperations[0] == totalOperations) {
                                handleEditModeComplete(updateSuccessCount[0], deleteSuccessCount[0], failureCount[0]);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            failureCount[0]++;
                            completedOperations[0]++;
                            Log.e(TAG, "🌐 빈 데이터 상품 삭제 네트워크 오류: " + barcode + " - " + t.getMessage(), t);
                            
                            if (completedOperations[0] == totalOperations) {
                                handleEditModeComplete(updateSuccessCount[0], deleteSuccessCount[0], failureCount[0]);
                            }
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
                                
                                // 통계 새로고침 (항상 호출 - 캐시와 실제 DB 동기화를 위해)
                                refreshAdminHomeStats();
                                
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
                                
                                // 통계 새로고침 (항상 호출 - 캐시와 실제 DB 동기화를 위해)
                                refreshAdminHomeStats();
                                
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
        
        // 등록 모드나 편집 모드에서는 빈 데이터 정리 후 모드 리셋
        if ((currentMode == 1 || currentMode == 2) && adapter != null) {
            // 등록/편집 완료 또는 취소 시 빈 데이터 항상 정리
            adapter.removeEmptyItems();
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
        
        // 버튼 상태 업데이트
        updateButtonStates();
        
        // 상태 바 숨김
        hideStatusBar();
        
        // 어댑터 상태 리셋
        if (adapter != null) {
            adapter.setMode(0); // 일반 모드로 리셋
        }
    }

    /**
     * 다중 상품 등록 완료 처리
     */
    private void handleMultipleRegistrationComplete(int successCount, int failureCount, int totalCount) {
        // 임시 데이터 정리
        if (adapter != null) {
            adapter.removeEmptyItems();
            adapter.completeTempData();
        }
        
        // UI 업데이트 - 등록 완료 후 서버에서 최신 데이터 로드
        if (successCount > 0) {
            Log.d(TAG, "🔄 등록 완료 후 서버에서 최신 상품 목록 로드");
            loadGoodsFromServer(true); // 서버에서 최신 데이터를 가져와서 중복 방지
        } else {
            Log.d(TAG, "🔄 등록 실패 - UI만 정리");
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        
        // 통계 새로고침
        if (successCount > 0) {
            Log.d(TAG, "🔄 " + successCount + "개 상품 등록 완료 - AdminHome 통계 새로고침 요청");
            refreshAdminHomeStats();
        } else {
            Log.w(TAG, "⚠️ 등록 성공한 상품이 없어서 통계 새로고침 생략");
        }
        
        // 결과 메시지 표시
        if (failureCount == 0) {
            Toast.makeText(getContext(), 
                successCount + "개 상품이 모두 성공적으로 등록되었습니다.", 
                Toast.LENGTH_SHORT).show();
            Log.d(TAG, "다중 상품 등록 완료: " + successCount + "개 성공 (총 " + totalCount + "개 시도)");
        } else if (successCount == 0) {
            Toast.makeText(getContext(), 
                "상품 등록에 실패했습니다. 네트워크를 확인해주세요.", 
                Toast.LENGTH_LONG).show();
            Log.e(TAG, "다중 상품 등록 전체 실패: " + totalCount + "개 시도");
        } else {
            Toast.makeText(getContext(), 
                successCount + "개 성공, " + failureCount + "개 실패했습니다.", 
                Toast.LENGTH_LONG).show();
            Log.w(TAG, "다중 상품 등록 부분 완료: " + successCount + "개 성공, " + failureCount + "개 실패 (총 " + totalCount + "개 시도)");
        }
    }

    /**
     * 편집 모드 완료 처리
     */
    private void handleEditModeComplete(int updateSuccessCount, int deleteSuccessCount, int failureCount) {
        // UI 업데이트
        updateUIFromCache();
        
        // 통계 새로고침 (수정이나 삭제가 있었을 경우)
        if (updateSuccessCount > 0 || deleteSuccessCount > 0) {
            Log.d(TAG, "🔄 편집 완료 - 업데이트: " + updateSuccessCount + "개, 삭제: " + deleteSuccessCount + "개 - AdminHome 통계 새로고침 요청");
            refreshAdminHomeStats();
        } else {
            Log.w(TAG, "⚠️ 편집 완료했지만 변경사항이 없어서 통계 새로고침 생략");
        }
        
        // 결과 메시지 표시
        int totalSuccess = updateSuccessCount + deleteSuccessCount;
        
        if (failureCount == 0) {
            if (deleteSuccessCount > 0 && updateSuccessCount > 0) {
                Toast.makeText(getContext(), 
                    "상품 수정 완료: " + updateSuccessCount + "개 업데이트, " + deleteSuccessCount + "개 삭제", 
                    Toast.LENGTH_SHORT).show();
            } else if (deleteSuccessCount > 0) {
                Toast.makeText(getContext(), 
                    deleteSuccessCount + "개 빈 상품이 삭제되었습니다.", 
                    Toast.LENGTH_SHORT).show();
            } else if (updateSuccessCount > 0) {
                Toast.makeText(getContext(), 
                    updateSuccessCount + "개 상품이 성공적으로 수정되었습니다.", 
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "수정할 내용이 없었습니다.", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "편집 모드 완료: 업데이트 " + updateSuccessCount + "개, 삭제 " + deleteSuccessCount + "개 성공");
        } else if (totalSuccess == 0) {
            Toast.makeText(getContext(), 
                "상품 수정에 실패했습니다. 네트워크를 확인해주세요.", 
                Toast.LENGTH_LONG).show();
            Log.e(TAG, "편집 모드 전체 실패: " + failureCount + "개");
        } else {
            Toast.makeText(getContext(), 
                "수정 완료: " + totalSuccess + "개 성공, " + failureCount + "개 실패", 
                Toast.LENGTH_LONG).show();
            Log.w(TAG, "편집 모드 부분 완료: " + totalSuccess + "개 성공, " + failureCount + "개 실패");
        }
    }

    /**
     * 버튼 클릭 효과 추가
     */
    private void addButtonClickEffect(View button) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case android.view.MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100);
                    break;
                case android.view.MotionEvent.ACTION_UP:
                case android.view.MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100);
                    break;
            }
            return false;
        });
    }

    /**
     * 현재 모드에 따라 버튼 상태 업데이트
     */
    private void updateButtonStates() {
        // 모든 버튼을 기본 상태로 초기화
        resetButtonToDefault(insertBtn);
        resetButtonToDefault(editBtn); 
        resetButtonToDefault(deleteBtn);
        
        // 현재 활성화된 모드의 버튼을 선택 상태로 변경
        switch (currentMode) {
            case 1: // 삽입모드
                setButtonSelected(insertBtn, "#10B981", "@android:color/white");
                break;
            case 2: // 수정모드
                setButtonSelected(editBtn, "#F59E0B", "@android:color/white");
                break;
            case 3: // 삭제모드
                setButtonSelected(deleteBtn, "#EF4444", "@android:color/white");
                break;
        }
    }

    /**
     * 버튼을 기본 상태로 초기화
     */
    private void resetButtonToDefault(com.google.android.material.button.MaterialButton button) {
        // 모든 버튼을 outlined 스타일로 설정
        String color;
        if (button == insertBtn) {
            color = "#10B981";
        } else if (button == editBtn) {
            color = "#F59E0B";
        } else {
            color = "#EF4444";
        }
        
        button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.TRANSPARENT));
        button.setTextColor(android.graphics.Color.parseColor(color));
        button.setIconTint(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(color)));
        button.setStrokeWidth(4); // 2dp * density
        button.setStrokeColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(color)));
        
        // 스케일을 기본 크기로 복원
        button.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
    }

    /**
     * 버튼을 선택된 상태로 설정
     */
    private void setButtonSelected(com.google.android.material.button.MaterialButton button, String bgColor, String textColor) {
        // 배경색을 선택된 색으로 채우기
        button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(bgColor)));
        button.setTextColor(android.graphics.Color.WHITE);
        button.setIconTint(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE));
        button.setStrokeWidth(0); // 테두리 제거
        
        // 선택된 버튼을 살짝 확대하여 강조
        button.animate().scaleX(1.05f).scaleY(1.05f).setDuration(150).start();
    }


    

    /**
     * AdminHomeScreen의 통계 새로고침 (안전장치 강화)
     */
    private void refreshAdminHomeStats() {
        if (isFragmentSafe() && getActivity() instanceof AdminHomeScreen) {
            Log.d(TAG, "📊 AdminHomeScreen 통계 새로고침 시작...");
            ((AdminHomeScreen) getActivity()).refreshDashboardStats();
            Log.d(TAG, "📊 AdminHomeScreen 통계 새로고침 요청 완료");
        } else {
            Log.w(TAG, "⚠️ AdminHomeScreen 통계 새로고침 실패 - Fragment 상태 불안정 또는 Activity 타입 불일치");
        }
    }
    
    /**
     * Fragment 상태 안전성 검증
     */
    private boolean isFragmentSafe() {
        return getActivity() != null && 
               !isDetached() && 
               !isRemoving() && 
               getView() != null &&
               !getActivity().isFinishing();
    }
    
    
    
} 