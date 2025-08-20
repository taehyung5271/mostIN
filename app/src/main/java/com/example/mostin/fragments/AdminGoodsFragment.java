package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.content.res.ColorStateList;
import com.google.android.material.textfield.TextInputEditText;
import com.example.mostin.utils.KeyboardHeightDetector;
import com.example.mostin.utils.KeyboardStateListener;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class AdminGoodsFragment extends Fragment implements KeyboardStateListener {
    private static final String TAG = "AdminGoodsFragment";
    
    private RecyclerView recyclerView;
    private AdminGoodsAdapter adapter;
    private com.google.android.material.button.MaterialButton insertBtn, editBtn, deleteBtn;
    
    // 스크롤 상태 추적 변수
    private boolean isScrolling = false;
    private boolean isUserDragging = false;
    private boolean isProgrammaticScroll = false;
    
    // 새로운 키보드 감지 시스템
    private KeyboardHeightDetector keyboardDetector;
    private boolean isKeyboardVisible = false;
    private int currentKeyboardHeight = 0;
    
    // 스마트 포커싱 관련 변수
    private View lastFocusedEditText = null;
    private int lastFocusedPosition = -1;
    private Handler keyboardHandler = new Handler(Looper.getMainLooper());
    private Runnable keyboardCheckRunnable = null;
    
    // 패딩 조정 최적화를 위한 변수들
    private int lastAppliedKeyboardHeight = -1;
    private long lastPaddingChangeTime = 0;
    private static final long PADDING_CHANGE_DEBOUNCE_MS = 100; // 100ms 디바운싱
    
    // 새로운 상태 바 관련 변수들
    private LinearLayout statusBar;
    private ImageView statusIcon;
    private TextView statusText;
    private com.google.android.material.button.MaterialButton fixedActionBtn;
    
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
        
        // 새로운 키보드 감지 시스템 초기화
        initializeKeyboardDetector();
        
        // 포커스 변경 리스너 설정
        setupFocusChangeListener();
        
        // 뒤로가기 버튼 처리를 위한 콜백 등록
        setupBackPressCallback();
        
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
     * 상태 바 초기화
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
            
            // RecyclerView 초기 설정
            recyclerView.setClipToPadding(false); // 패딩 영역에서도 스크롤 가능
            
            // RecyclerView 빈 공간 터치 시 키보드 닫기
            setupTouchListener();
            
            Log.d(TAG, "⚡ 즉시 UI 표시 완료 (" + goodsList.size() + "개 상품)");
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
                        setupAdapterImmediate();
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
        
        Log.d(TAG, "🚀 등록 모드 완전 자동화 시퀀스 시작");
        
        // 버튼 상태 업데이트
        updateButtonStates();
        
        // 상태 바 표시
        showStatusBar(1);
        
        // 어댑터 모드 설정 (1: 등록 모드) - setMode에서 자동으로 첫 번째 새 필드 추가됨
        adapter.setMode(1);
        
        // 완전 자동화된 포커싱 시퀀스 실행
        startAutomatedFocusSequence();
    }

    /**
     * 등록 모드 완전 자동화 시퀀스
     * 1. 새 필드 생성 완료 대기
     * 2. 바코드 입력칸에 자동 포커싱
     * 3. 키보드 자동 표시
     * 4. 입력필드가 키보드 바로 위에 위치하도록 스크롤 조정
     * 5. 키보드 높이만큼 패딩 적용
     */
    private void startAutomatedFocusSequence() {
        Log.d(TAG, "🔄 자동화 시퀀스 1단계: 새 필드 생성 완료 대기");
        
        // 1단계: 어댑터의 새 필드 생성이 완료될 때까지 대기 (200ms로 증가)
        recyclerView.postDelayed(() -> {
            executeAutomatedFocusStep2();
        }, 200);
    }

    private void executeAutomatedFocusStep2() {
        Log.d(TAG, "🎯 자동화 시퀀스 2단계: 첫 번째 새 필드 포커스 및 키보드 요청");
        
        if (adapter == null) {
            Log.w(TAG, "❌ adapter가 null이므로 자동화 시퀀스 중단");
            return;
        }
        
        int newItemPosition = adapter.getOriginalDataSize();
        
        // 2단계: RecyclerView 레이아웃 완료 후 포커스 설정 (post 중첩으로 확실하게)
        recyclerView.post(() -> recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(newItemPosition);
            if (viewHolder instanceof AdminGoodsAdapter.ViewHolder) {
                AdminGoodsAdapter.ViewHolder goodsHolder = (AdminGoodsAdapter.ViewHolder) viewHolder;
                if (goodsHolder.barcodeEdit != null) {
                    
                    // 즉시 포커스 설정
                    goodsHolder.barcodeEdit.requestFocus();
                    lastFocusedEditText = goodsHolder.barcodeEdit;
                    lastFocusedPosition = newItemPosition;
                    
                    Log.d(TAG, "✅ 포커스 설정 완료 - 위치: " + newItemPosition);
                    
                    // 3단계: 키보드 강제 표시 실행 (post로 확실하게)
                    goodsHolder.barcodeEdit.post(() -> {
                        executeAutomatedFocusStep3(goodsHolder.barcodeEdit, newItemPosition);
                    });
                } else {
                    Log.w(TAG, "❌ barcodeEdit가 null이므로 포커스 설정 불가");
                }
            } else {
                // ViewHolder를 찾지 못한 경우 재시도 (최대 3번)
                Log.d(TAG, "🔄 ViewHolder 찾기 재시도 (위치: " + newItemPosition + ")");
                recyclerView.postDelayed(() -> {
                    executeAutomatedFocusStep2();
                }, 100);
            }
        }));
    }

    private void executeAutomatedFocusStep3(TextInputEditText editText, int position) {
        Log.d(TAG, "⌨️ 자동화 시퀀스 3단계: 키보드 표시 및 스마트 패딩 적용");
        
        // 키보드 강제 표시 (SHOW_IMPLICIT 사용으로 변경하여 더 자연스럽게)
        android.view.inputmethod.InputMethodManager imm = 
            (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean keyboardShown = imm.showSoftInput(editText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
            Log.d(TAG, "키보드 표시 요청 결과: " + keyboardShown);
        }
        
        // 키보드 높이 감지를 위한 개선된 대기 로직
        executeAutomatedFocusStep4Enhanced(editText, position);
    }

    /**
     * 개선된 키보드 감지 및 스크롤 조정 로직
     * 키보드 높이 변화를 실시간으로 감지하고 즉시 반응
     */
    private void executeAutomatedFocusStep4Enhanced(TextInputEditText editText, int position) {
        Log.d(TAG, "📍 개선된 자동화 시퀀스 4단계: 실시간 키보드 감지 및 스마트 스크롤");
        
        // KeyboardStateListener를 통한 실시간 키보드 감지
        KeyboardStateListener automationListener = new KeyboardStateListener() {
            @Override
            public void onKeyboardShown(int keyboardHeight) {
                Log.d(TAG, "✅ 키보드 감지됨 (높이: " + keyboardHeight + "px) - 즉시 스마트 스크롤 실행");
                // 키보드가 나타나자마자 즉시 스크롤 조정
                executeOptimalScrollAdjustment(editText, position, keyboardHeight);
                
                // 일회성 리스너이므로 제거
                if (keyboardDetector != null) {
                    keyboardDetector.removeKeyboardStateListener(this);
                }
            }
            
            @Override
            public void onKeyboardHidden(int previousKeyboardHeight) {
                // 등록 모드에서는 키보드 숨김 이벤트 무시
            }
            
            @Override
            public void onKeyboardHeightChanged(int oldHeight, int newHeight) {
                // 키보드 높이가 변경되면 스크롤 재조정
                if (newHeight > 200) {
                    Log.d(TAG, "📏 키보드 높이 변경 감지 - 스크롤 재조정");
                    executeOptimalScrollAdjustment(editText, position, newHeight);
                }
            }
        };
        
        // 임시 리스너 등록
        if (keyboardDetector != null) {
            keyboardDetector.addKeyboardStateListener(automationListener);
        }
        
        // 키보드가 이미 표시되어 있는 경우를 대비한 즉시 체크
        editText.postDelayed(() -> {
            if (isKeyboardVisible && currentKeyboardHeight > 200) {
                Log.d(TAG, "🔍 키보드가 이미 표시되어 있음 - 즉시 스크롤 조정");
                executeOptimalScrollAdjustment(editText, position, currentKeyboardHeight);
                keyboardDetector.removeKeyboardStateListener(automationListener);
            } else {
                // 백업 타이머: 300ms 후에도 키보드가 감지되지 않으면 예상 높이로 진행
                editText.postDelayed(() -> {
                    if (!isKeyboardVisible) {
                        Log.d(TAG, "⚠️ 키보드 감지 타임아웃 - 예상 높이로 스크롤 조정");
                        int estimatedHeight = getEstimatedKeyboardHeight();
                        executeOptimalScrollAdjustment(editText, position, estimatedHeight);
                        // 예상 패딩도 함께 적용
                        applySmartPadding(estimatedHeight);
                    }
                    keyboardDetector.removeKeyboardStateListener(automationListener);
                }, 300);
            }
        }, 50);
    }
    
    private void executeAutomatedFocusStep4(int position) {
        Log.d(TAG, "📍 자동화 시퀀스 4단계: 키보드 표시 대기 및 정확한 스크롤 조정");
        
        // 키보드가 표시될 때까지 여러 번 체크 (최대 5번, 100ms 간격)
        final int[] checkCount = {0};
        final int maxChecks = 5;
        
        Handler automationHandler = new Handler(Looper.getMainLooper());
        Runnable keyboardCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkCount[0]++;
                
                if (isKeyboardVisible && currentKeyboardHeight > 0) {
                    // 키보드가 완전히 표시됨 - 정확한 스크롤 조정
                    Log.d(TAG, "✅ 키보드 감지됨 (높이: " + currentKeyboardHeight + "px) - 정확한 스크롤 조정 실행");
                    executeAutomatedFocusStep5(position, currentKeyboardHeight);
                } else if (checkCount[0] < maxChecks) {
                    // 아직 키보드가 표시되지 않음 - 재시도
                    Log.d(TAG, "⏳ 키보드 대기 중... (" + checkCount[0] + "/" + maxChecks + ")");
                    automationHandler.postDelayed(this, 100);
                } else {
                    // 최대 체크 횟수 초과 - 예상 높이로 진행
                    Log.d(TAG, "⚠️ 키보드 감지 타임아웃 - 예상 높이로 스크롤 조정");
                    executeAutomatedFocusStep5(position, getEstimatedKeyboardHeight());
                }
            }
        };
        
        // 첫 번째 체크 시작
        automationHandler.postDelayed(keyboardCheckRunnable, 100);
    }

    private void executeAutomatedFocusStep5(int position, int keyboardHeight) {
        Log.d(TAG, "🎯 자동화 시퀀스 5단계: 최종 스크롤 조정 및 패딩 동기화 (키보드 높이: " + keyboardHeight + "px)");
        
        // 정확한 키보드 높이로 패딩 재조정
        if (keyboardHeight != currentKeyboardHeight && keyboardHeight > 200) {
            applySmartPadding(keyboardHeight);
        }
        
        // 최종 스크롤 위치 조정
        scrollToFocusedFieldWithAnimation(position, keyboardHeight);
        
        Log.d(TAG, "🎉 등록 모드 완전 자동화 시퀀스 완료!");
    }
    
    /**
     * 최적화된 스크롤 조정 메서드
     * 키보드 위에 EditText가 정확히 위치하도록 개선된 계산 로직
     */
    private void executeOptimalScrollAdjustment(TextInputEditText editText, int position, int keyboardHeight) {
        if (recyclerView == null || editText == null || position < 0) {
            Log.w(TAG, "스크롤 조정 실패: 필수 컴포넌트가 null이거나 잘못된 위치");
            return;
        }
        
        // 패딩과 스크롤을 동시에 처리하여 깜빡임 방지
        recyclerView.post(() -> {
            // 1. 먼저 패딩 적용
            applySmartPadding(keyboardHeight);
            
            // 2. 패딩 적용 후 스크롤 위치 계산
            recyclerView.post(() -> {
                // 최상단 아이템들(0,1,2번째)은 기존 방식 사용 (포커스 안정성 보장)
                if (position <= 2) {
                    Log.d(TAG, "🔝 최상단 아이템 (" + position + ") - 기존 스크롤 방식 사용");
                    executeClassicScrollAdjustment(editText, position, keyboardHeight);
                    return;
                }
                
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    Log.w(TAG, "LayoutManager를 찾을 수 없음 - 기존 방식으로 폴백");
                    executeClassicScrollAdjustment(editText, position, keyboardHeight);
                    return;
                }
                
                float density = getResources().getDisplayMetrics().density;
                
                // 고정 헤더 높이 계산 (ToolBar + 기타 고정 요소들)
                int fixedHeaderHeight = calculateFixedHeaderHeight();
                
                // 키보드 위 100dp 위치를 목표로 하되, 고정 헤더 높이를 고려
                int targetOffsetFromTop = fixedHeaderHeight + (int)(100 * density);
                
                // 편집 안전 영역 검사 - 안전 영역에 있으면 포커스만 처리하고 스크롤하지 않음
                if (isInEditSafeZone(position, keyboardHeight)) {
                    Log.d(TAG, "🛡️ 편집 안전 영역 내 위치 - 포커스만 처리, 스크롤 생략 (위치: " + position + ")");
                    // 포커스 우선 정책: 키보드만 표시하고 스크롤하지 않음
                    executeFocusOnlyPolicy(editText, position);
                    return;
                }
                
                // 스크롤 필요성 사전 검증
                if (!needsScrolling(position, targetOffsetFromTop)) {
                    Log.d(TAG, "✅ 스크롤 불필요 - 이미 적절한 위치에 있음 (위치: " + position + ")");
                    return;
                }
                
                Log.d(TAG, String.format("📐 개선된 스크롤 계산 - 위치: %d, 고정헤더: %dpx, 목표오프셋: %dpx, 키보드: %dpx", 
                        position, fixedHeaderHeight, targetOffsetFromTop, keyboardHeight));
                
                // 프로그래매틱 스크롤 플래그 설정
                isProgrammaticScroll = true;
                
                // LinearLayoutManager의 scrollToPositionWithOffset 사용
                // 이 방법은 고정 헤더를 고려하여 안전한 스크롤을 보장
                layoutManager.scrollToPositionWithOffset(position, targetOffsetFromTop);
                
                Log.d(TAG, "🎯 LinearLayoutManager 스크롤 실행 - 위치: " + position + ", 오프셋: " + targetOffsetFromTop + "px");
                
                // 스크롤 완료 후 플래그 리셋
                recyclerView.postDelayed(() -> {
                    isProgrammaticScroll = false;
                    Log.d(TAG, "🔄 LinearLayoutManager 스크롤 플래그 리셋");
                    
                    // 스크롤 완료 후 최종 검증 및 미세 조정
                    performFinalScrollVerification(position, keyboardHeight);
                }, 600);
            });
        });
    }
    
    /**
     * 편집 안전 영역(Edit Safe Zone) 내에 있는지 확인하는 메서드
     */
    private boolean isInEditSafeZone(int position, int keyboardHeight) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
            return false; // ViewHolder가 없으면 안전 영역이 아님
        }
        
        float density = getResources().getDisplayMetrics().density;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        
        // 편집 안전 영역 정의
        int fixedHeaderHeight = calculateFixedHeaderHeight();
        int safeZoneTop = fixedHeaderHeight + (int)(50 * density);  // 헤더 + 50dp 여유공간
        int safeZoneBottom = screenHeight - keyboardHeight - (int)(100 * density); // 키보드 위 100dp 여유공간
        
        // 아이템의 현재 위치
        int itemTop = viewHolder.itemView.getTop();
        int itemBottom = viewHolder.itemView.getBottom();
        int itemCenter = (itemTop + itemBottom) / 2;
        
        // 아이템이 안전 영역 내에 완전히 포함되는지 확인
        boolean isInSafeZone = itemTop >= safeZoneTop && itemBottom <= safeZoneBottom;
        
        Log.d(TAG, String.format("🛡️ 편집 안전 영역 검사 - 위치: %d, 아이템영역: %d~%d(중앙:%d), 안전영역: %d~%d, 안전여부: %s", 
                position, itemTop, itemBottom, itemCenter, safeZoneTop, safeZoneBottom, isInSafeZone));
        
        return isInSafeZone;
    }
    
    /**
     * 스크롤이 실제로 필요한지 검증하는 메서드
     */
    private boolean needsScrolling(int position, int targetOffsetFromTop) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) {
            return true; // ViewHolder가 없으면 스크롤 필요
        }
        
        int currentTop = viewHolder.itemView.getTop();
        int threshold = (int)(20 * getResources().getDisplayMetrics().density); // 20dp 임계값
        
        // 현재 위치와 목표 위치의 차이가 임계값보다 크면 스크롤 필요
        boolean needsScroll = Math.abs(currentTop - targetOffsetFromTop) > threshold;
        
        Log.d(TAG, String.format("🔍 스크롤 필요성 검증 - 현재위치: %dpx, 목표위치: %dpx, 차이: %dpx, 필요여부: %s", 
                currentTop, targetOffsetFromTop, Math.abs(currentTop - targetOffsetFromTop), needsScroll));
        
        return needsScroll;
    }
    
    /**
     * 포커스 우선 정책 - 포커스만 처리하고 스크롤하지 않음 (편집 안전 영역용)
     */
    private void executeFocusOnlyPolicy(TextInputEditText editText, int position) {
        if (editText == null) {
            Log.w(TAG, "executeFocusOnlyPolicy: EditText가 null");
            return;
        }
        
        Log.d(TAG, "🎯 포커스 우선 정책 실행 - 위치: " + position);
        
        // 1단계: 포커스 먼저 확정
        editText.requestFocus();
        
        // 2단계: 키보드 강제 표시
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            // 키보드 표시 강제 실행
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            Log.d(TAG, "⌨️ 키보드 강제 표시 요청");
        }
        
        // 3단계: 포커스 상태 저장 (필요시 나중에 미세 조정용)
        lastFocusedEditText = editText;
        lastFocusedPosition = position;
        
        Log.d(TAG, "✅ 포커스 우선 정책 완료 - 스크롤 없이 키보드만 표시");
    }
    
    /**
     * 기존 방식의 스크롤 조정 (최상단 아이템용)
     */
    private void executeClassicScrollAdjustment(TextInputEditText editText, int position, int keyboardHeight) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null || viewHolder.itemView == null) {
            Log.w(TAG, "ViewHolder를 찾을 수 없음 - 위치: " + position);
            return;
        }
        
        // 화면 정보 수집
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float density = getResources().getDisplayMetrics().density;
        
        // 개선된 목표 위치 계산
        // 키보드 바로 위 100dp 위치에 EditText의 중앙이 오도록 설정
        int availableHeight = screenHeight - keyboardHeight;
        int targetY = availableHeight - (int)(100 * density);
        
        // EditText의 실제 위치 (바코드 입력칸을 기준으로)
        View barcodeEditView = viewHolder.itemView.findViewById(R.id.barcodeEdit);
        if (barcodeEditView != null) {
            int[] editTextLocation = new int[2];
            barcodeEditView.getLocationOnScreen(editTextLocation);
            
            int editTextCenterY = editTextLocation[1] + barcodeEditView.getHeight() / 2;
            int scrollOffset = editTextCenterY - targetY;
            
            Log.d(TAG, String.format("🔝 기존 방식 스크롤 계산 - 화면높이: %d, 키보드높이: %d, 사용가능높이: %d, 목표Y: %d, 현재Y: %d, 오프셋: %d", 
                    screenHeight, keyboardHeight, availableHeight, targetY, editTextCenterY, scrollOffset));
            
            if (Math.abs(scrollOffset) > 20) { // 20px 이상 차이날 때만 스크롤
                // 프로그래매틱 스크롤 플래그 설정
                isProgrammaticScroll = true;
                
                // 부드러운 스크롤 실행
                recyclerView.smoothScrollBy(0, scrollOffset);
                Log.d(TAG, "🎬 기존 방식 부드러운 스크롤 실행 - 위치: " + position + ", 오프셋: " + scrollOffset + "px");
                
                // 스크롤 완료 후 플래그 리셋
                recyclerView.postDelayed(() -> {
                    isProgrammaticScroll = false;
                    Log.d(TAG, "🔄 기존 방식 스크롤 플래그 리셋");
                    
                    // 스크롤 완료 후 최종 검증 및 미세 조정
                    performFinalScrollVerification(position, keyboardHeight);
                }, 600);
            } else {
                Log.d(TAG, "✅ 스크롤 불필요 - 이미 적절한 위치 (오프셋: " + scrollOffset + "px)");
                // 자동화 완료 로그
                Log.d(TAG, "🎉 기존 방식 자동화 시퀀스 완료!");
            }
        } else {
            Log.w(TAG, "barcodeEdit를 찾을 수 없음 - 폴백 로직 사용");
            scrollToFocusedFieldWithAnimation(position, keyboardHeight);
        }
    }
    
    /**
     * 고정 헤더의 높이를 계산하는 메서드
     */
    private int calculateFixedHeaderHeight() {
        int headerHeight = 0;
        
        // ToolBar 높이 추가
        if (getActivity() != null) {
            androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                headerHeight += toolbar.getHeight();
            }
        }
        
        // 기본값으로 ActionBar 높이 사용 (ToolBar를 찾을 수 없는 경우)
        if (headerHeight == 0) {
            android.util.TypedValue tv = new android.util.TypedValue();
            if (getActivity() != null && getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                headerHeight = android.util.TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        }
        
        // 상태바 높이도 고려 (필요한 경우)
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        
        int totalHeight = headerHeight + statusBarHeight;
        Log.d(TAG, String.format("📏 헤더 높이 계산 - ToolBar: %dpx, StatusBar: %dpx, 총합: %dpx", 
                headerHeight, statusBarHeight, totalHeight));
        
        return totalHeight;
    }
    
    /**
     * 최종 스크롤 위치 검증 및 미세 조정
     */
    private void performFinalScrollVerification(int position, int keyboardHeight) {
        if (recyclerView == null || position < 0) return;
        
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null) return;
        
        View barcodeEditView = viewHolder.itemView.findViewById(R.id.barcodeEdit);
        if (barcodeEditView == null) return;
        
        int[] editTextLocation = new int[2];
        barcodeEditView.getLocationOnScreen(editTextLocation);
        
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float density = getResources().getDisplayMetrics().density;
        int availableHeight = screenHeight - keyboardHeight;
        int idealY = availableHeight - (int)(100 * density);
        
        int currentY = editTextLocation[1] + barcodeEditView.getHeight() / 2;
        int finalOffset = currentY - idealY;
        
        if (Math.abs(finalOffset) > 30) {
            Log.d(TAG, "🔧 최종 미세 조정 필요 - 오프셋: " + finalOffset + "px");
            recyclerView.smoothScrollBy(0, finalOffset);
        }
        
        Log.d(TAG, "🎉 개선된 등록 모드 자동화 시퀀스 최종 완료!");
    }
    
    /**
     * 어댑터에서 호출되는 새 아이템 자동 포커스 요청 메서드
     * + 버튼 클릭 시 새로 추가된 EditText에 자동 포커스 및 키보드 표시
     */
    public void requestAutoFocusForNewItem(int position) {
        Log.d(TAG, "🎯 + 버튼으로 새 아이템 자동 포커스 요청 - 위치: " + position);
        
        // 새 아이템이 생성된 후 자동 포커스 시퀀스 실행
        recyclerView.postDelayed(() -> {
            executeAutomatedFocusForNewItem(position);
        }, 150); // addNewRow의 애니메이션 완료 대기
    }
    
    /**
     * 새로 추가된 아이템에 대한 자동 포커스 시퀀스
     */
    private void executeAutomatedFocusForNewItem(int position) {
        Log.d(TAG, "🔄 새 아이템 자동 포커스 시퀀스 시작 - 위치: " + position);
        
        recyclerView.post(() -> recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder instanceof AdminGoodsAdapter.ViewHolder) {
                AdminGoodsAdapter.ViewHolder goodsHolder = (AdminGoodsAdapter.ViewHolder) viewHolder;
                if (goodsHolder.barcodeEdit != null) {
                    
                    // 즉시 포커스 설정
                    goodsHolder.barcodeEdit.requestFocus();
                    lastFocusedEditText = goodsHolder.barcodeEdit;
                    lastFocusedPosition = position;
                    
                    Log.d(TAG, "✅ 새 아이템 포커스 설정 완료 - 위치: " + position);
                    
                    // 키보드 표시 및 스크롤 조정
                    goodsHolder.barcodeEdit.post(() -> {
                        // 키보드 표시
                        android.view.inputmethod.InputMethodManager imm = 
                            (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            boolean keyboardShown = imm.showSoftInput(goodsHolder.barcodeEdit, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
                            Log.d(TAG, "새 아이템 키보드 표시 요청 결과: " + keyboardShown);
                        }
                        
                        // 개선된 자동화 시퀀스 실행
                        executeAutomatedFocusStep4Enhanced(goodsHolder.barcodeEdit, position);
                    });
                } else {
                    Log.w(TAG, "❌ 새 아이템 barcodeEdit가 null이므로 포커스 설정 불가");
                }
            } else {
                // ViewHolder를 찾지 못한 경우 재시도
                Log.d(TAG, "🔄 새 아이템 ViewHolder 찾기 재시도 (위치: " + position + ")");
                recyclerView.postDelayed(() -> {
                    executeAutomatedFocusForNewItem(position);
                }, 100);
            }
        }));
    }

    /**
     * 애니메이션과 함께 부드러운 스크롤 조정
     */
    private void scrollToFocusedFieldWithAnimation(int position, int keyboardHeight) {
        if (recyclerView == null || position < 0) return;
        
        recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null && viewHolder.itemView != null) {
                
                // 화면 정보 계산
                int[] recyclerLocation = new int[2];
                recyclerView.getLocationOnScreen(recyclerLocation);
                
                int[] itemLocation = new int[2];
                viewHolder.itemView.getLocationOnScreen(itemLocation);
                
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int visibleHeight = screenHeight - keyboardHeight;
                float density = getResources().getDisplayMetrics().density;
                
                // 입력 필드가 키보드 바로 위(120dp)에 위치하도록 목표 Y 좌표 계산
                int targetY = visibleHeight - (int)(120 * density);
                
                int itemTop = itemLocation[1];
                int itemHeight = viewHolder.itemView.getHeight();
                int desiredItemY = targetY - itemHeight / 2;
                
                int scrollOffset = itemTop - desiredItemY;
                
                if (Math.abs(scrollOffset) > 30) { // 30px 이상 차이날 때만 스크롤
                    // 프로그래매틱 스크롤 플래그 설정
                    isProgrammaticScroll = true;
                    
                    // 부드러운 스크롤 실행
                    recyclerView.smoothScrollBy(0, scrollOffset);
                    Log.d(TAG, "🎬 부드러운 스크롤 실행 - 위치: " + position + ", 오프셋: " + scrollOffset + "px");
                    
                    // 스크롤 완료 후 플래그 리셋
                    recyclerView.postDelayed(() -> {
                        isProgrammaticScroll = false;
                        Log.d(TAG, "🔄 자동화 스크롤 플래그 리셋");
                    }, 800); // 부드러운 스크롤 시간 고려
                } else {
                    Log.d(TAG, "✅ 스크롤 불필요 - 이미 적절한 위치 (오프셋: " + scrollOffset + "px)");
                }
            }
        });
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
        
        Log.d(TAG, "편집 모드 진입 - 키보드 감지 시스템 확인");
        
        Log.d(TAG, "편집 모드 진입 - 초기 상태 정리");
        
        // 편집 모드 진입 시 스마트 패딩 초기 상태 설정
        recyclerView.postDelayed(() -> {
            View currentFocus = getActivity() != null ? getActivity().getCurrentFocus() : null;
            if (!(currentFocus instanceof com.google.android.material.textfield.TextInputEditText)) {
                Log.d(TAG, "편집 모드 진입 - 포커스 없으므로 스마트 패딩 리셋하여 스크롤 가능하도록 설정");
                resetSmartPadding();
            }
        }, 50);
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
                // 등록 완료 시 부분적으로 빈 상품 자동 정리
                if (adapter != null) {
                    adapter.removePartiallyEmptyItems();
                    Log.d(TAG, "등록 완료 - 부분적으로 빈 상품 자동 정리 실행");
                }
                
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
                    // 완전히 빈 데이터 정리 (바코드 AND 상품명이 둘 다 비어있는 경우)
                    if (adapter != null) {
                        adapter.removeEmptyItems();
                    }
                }
                break;
                
            case 2: // 수정모드
                // 편집 완료 시 부분적으로 빈 상품 자동 정리
                if (adapter != null) {
                    adapter.removePartiallyEmptyItems();
                    Log.d(TAG, "편집 완료 - 부분적으로 빈 상품 자동 정리 실행");
                }
                
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
        
        // 등록 모드나 편집 모드에서는 완전히 빈 데이터 정리 후 모드 리셋
        if ((currentMode == 1 || currentMode == 2) && adapter != null) {
            // 등록/편집 완료 또는 취소 시 완전히 빈 데이터 정리 (바코드 AND 상품명이 둘 다 비어있는 경우)
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
     * 특정 위치로 스크롤 후 스마트 포커스 설정 (어댑터에서 호출)
     */
    public void scrollToPositionAndFocus(int position) {
        Log.d(TAG, "scrollToPositionAndFocus 호출: 위치 " + position);
        
        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
            recyclerView.postDelayed(() -> {
                smartFocusOnPosition(position);
            }, 150);
        }
    }
    
    /**
     * 특정 위치에 스마트 포커스 적용
     */
    private void smartFocusOnPosition(int position) {
        if (recyclerView == null || isScrolling) {
            Log.d(TAG, "스마트 포커스 - 조건 불충족 (recyclerView: " + (recyclerView != null) + ", 스크롤중: " + isScrolling + ")");
            return;
        }
        
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof AdminGoodsAdapter.ViewHolder) {
            AdminGoodsAdapter.ViewHolder goodsHolder = (AdminGoodsAdapter.ViewHolder) viewHolder;
            if (goodsHolder.barcodeEdit != null) {
                
                // 포커스 설정
                goodsHolder.barcodeEdit.requestFocus();
                lastFocusedEditText = goodsHolder.barcodeEdit;
                lastFocusedPosition = position;
                
                // 키보드 표시
                android.view.inputmethod.InputMethodManager imm = 
                    (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(goodsHolder.barcodeEdit, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
                }
                
                // 스마트 패딩 적용 (키보드가 나타나기 전 예상치로)
                applyEstimatedPadding();
                
                // 키보드 나타난 후 정확한 스크롤 조정
                keyboardHandler.postDelayed(() -> {
                    if (isKeyboardVisible) {
                        scrollToFocusedField(position, currentKeyboardHeight);
                    }
                }, 300);
                
                Log.d(TAG, "✅ 스마트 포커스 완료 - 위치: " + position);
            }
        } else {
            // ViewHolder를 찾지 못한 경우 재시도
            recyclerView.postDelayed(() -> {
                Log.d(TAG, "스마트 포커스 재시도 - 위치: " + position);
                smartFocusOnPosition(position);
            }, 100);
        }
    }


    /**
     * 새로운 키보드 감지 시스템 초기화
     * KeyboardHeightDetector를 사용하여 키보드 상태를 실시간으로 감지합니다.
     */
    private void initializeKeyboardDetector() {
        if (getActivity() == null) {
            Log.w(TAG, "Activity가 null이므로 키보드 감지 시스템을 초기화할 수 없습니다.");
            return;
        }
        
        // KeyboardHeightDetector 초기화
        keyboardDetector = new KeyboardHeightDetector(getActivity());
        keyboardDetector.addKeyboardStateListener(this);
        
        // 키보드 감지 시작
        keyboardDetector.start();
        
        Log.i(TAG, "새로운 키보드 감지 시스템이 초기화되었습니다.");
    }
    
    /**
     * 키보드가 나타날 때 호출되는 콜백 메서드
     * KeyboardStateListener 인터페이스 구현 - 완전히 통합된 패딩 및 스크롤 처리
     * 
     * @param keyboardHeight 키보드의 높이 (픽셀 단위)
     */
    @Override
    public void onKeyboardShown(int keyboardHeight) {
        Log.i(TAG, "🔺 키보드 표시됨 - 높이: " + keyboardHeight + "px");
        
        isKeyboardVisible = true;
        currentKeyboardHeight = keyboardHeight;
        
        // 통합된 패딩 및 스크롤 처리
        handleKeyboardStateChange(keyboardHeight, true);
    }
    
    /**
     * 키보드가 숨겨질 때 호출되는 콜백 메서드
     * KeyboardStateListener 인터페이스 구현
     * 
     * @param previousKeyboardHeight 이전 키보드의 높이 (픽셀 단위)
     */
    @Override
    public void onKeyboardHidden(int previousKeyboardHeight) {
        Log.i(TAG, "🔻 키보드 숨김됨 - 이전 높이: " + previousKeyboardHeight + "px");
        
        isKeyboardVisible = false;
        currentKeyboardHeight = 0;
        
        // 통합된 패딩 및 스크롤 처리 (키보드 숨김)
        handleKeyboardStateChange(0, false);
    }
    
    /**
     * 키보드 높이가 변경될 때 호출되는 콜백 메서드
     * KeyboardStateListener 인터페이스 구현 - 실시간 높이 변화 대응
     * 
     * @param oldHeight 이전 키보드 높이 (픽셀 단위)
     * @param newHeight 새로운 키보드 높이 (픽셀 단위)
     */
    @Override
    public void onKeyboardHeightChanged(int oldHeight, int newHeight) {
        Log.i(TAG, "📏 키보드 높이 변경됨 - " + oldHeight + "px -> " + newHeight + "px");
        
        currentKeyboardHeight = newHeight;
        
        // 통합된 패딩 및 스크롤 처리 (높이 변화)
        handleKeyboardStateChange(newHeight, true);
    }
    
    /**
     * 키보드 상태 변화에 대한 통합 처리 메서드
     * 패딩 조정과 스크롤 위치 보정을 종합적으로 관리
     * 
     * @param keyboardHeight 현재 키보드 높이 (0이면 키보드 숨김 상태)
     * @param isKeyboardVisible 키보드 표시 여부
     */
    private void handleKeyboardStateChange(int keyboardHeight, boolean isKeyboardVisible) {
        if (recyclerView == null) {
            Log.w(TAG, "키보드 상태 변화 처리 건너뜀 - recyclerView null");
            return;
        }
        
        Log.d(TAG, String.format("🔄 키보드 상태 변화 통합 처리 - 높이: %dpx, 표시: %s, 포커스위치: %d", 
                keyboardHeight, isKeyboardVisible, lastFocusedPosition));
        
        if (isKeyboardVisible && keyboardHeight > 200) {
            // 키보드가 표시되거나 높이가 변경된 경우
            handleKeyboardShow(keyboardHeight);
        } else {
            // 키보드가 숨겨진 경우
            handleKeyboardHide();
        }
    }
    
    /**
     * 키보드 표시/높이 변경 시 처리
     */
    private void handleKeyboardShow(int keyboardHeight) {
        // 1단계: 스마트 패딩 적용
        applySmartPadding(keyboardHeight);
        
        // 2단계: 포커스된 필드가 있다면 스크롤 조정
        if (lastFocusedPosition >= 0 && lastFocusedEditText != null) {
            // 패딩 적용 후 스크롤 조정을 지연 실행하여 자연스럽게 처리
            recyclerView.post(() -> {
                ensureFocusedFieldVisible(lastFocusedPosition, keyboardHeight);
            });
        }
    }
    
    /**
     * 키보드 숨김 시 처리
     */
    private void handleKeyboardHide() {
        // 부드러운 패딩 제거
        resetSmartPaddingWithAnimation();
    }
    
    /**
     * 포커스된 필드가 키보드에 가리지 않도록 보장
     */
    private void ensureFocusedFieldVisible(int position, int keyboardHeight) {
        if (recyclerView == null || position < 0) return;
        
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder == null || viewHolder.itemView == null) return;
        
        int[] location = new int[2];
        viewHolder.itemView.getLocationOnScreen(location);
        int itemBottom = location[1] + viewHolder.itemView.getHeight();
        
        int[] recyclerLocation = new int[2];
        recyclerView.getLocationOnScreen(recyclerLocation);
        int availableHeight = recyclerLocation[1] + recyclerView.getHeight() - keyboardHeight;
        
        if (itemBottom > availableHeight) {
            int scrollOffset = itemBottom - availableHeight + (int) (20 * getResources().getDisplayMetrics().density);
            recyclerView.smoothScrollBy(0, scrollOffset);
            Log.d(TAG, String.format("📍 포커스된 필드 가시성 보장 - %dpx 스크롤 조정", scrollOffset));
        }
    }
    
    /**
     * Fragment가 사용자에게 보여질 때 호출됩니다.
     * 키보드 감지를 시작합니다.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (keyboardDetector != null) {
            keyboardDetector.start();
            Log.d(TAG, "Fragment onResume - 키보드 감지 시작");
        }
    }
    
    /**
     * Fragment가 사용자에게 보이지 않을 때 호출됩니다.
     * 키보드 감지를 일시 중지합니다.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (keyboardDetector != null) {
            keyboardDetector.stop();
            Log.d(TAG, "Fragment onPause - 키보드 감지 중지");
        }
    }
    
    /**
     * Fragment가 소멸될 때 호출됩니다.
     * 키보드 감지 시스템을 완전히 정리합니다.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keyboardDetector != null) {
            keyboardDetector.removeKeyboardStateListener(this);
            keyboardDetector.stop();
            keyboardDetector = null;
            Log.d(TAG, "Fragment onDestroy - 키보드 감지 시스템 정리 완료");
        }
    }
    
    /**
     * 스마트 포커싱을 위한 포커스 변경 리스너 설정
     * 기존 키보드 시스템에서 사용되던 포커스 처리 로직
     */
    private void setupFocusChangeListener() {
        // 포커스 변경 리스너 - 스마트 포커싱
        if (getView() != null) {
            getView().getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
                handleFocusChange(oldFocus, newFocus);
            });
        }
        
        // 스크롤 리스너 설정
        setupScrollListener();
        
        Log.d(TAG, "✅ 포커스 변경 리스너 설정 완료");
    }

    /**
     * 스마트 패딩 적용 - 포커스된 텍스트 필드에 따라 최적 스크롤 위치 계산
     * 하단 필드 처리와의 충돌 방지 로직 포함
     */
    /**
     * 완전히 개선된 스마트 패딩 적용 메서드
     * 키보드 높이 변화에 따른 동적 패딩 조정과 스크롤 위치 보정을 통합 처리
     */
    private void applySmartPadding(int keyboardHeight) {
        if (recyclerView == null) {
            Log.d(TAG, "패딩 적용 건너뜀 - recyclerView null");
            return;
        }
        
        // 스크롤 중이거나 프로그래밍 방식 스크롤 중에는 패딩 조정을 지연
        if (isScrolling || isProgrammaticScroll) {
            Log.d(TAG, "패딩 적용 지연 - 스크롤 중 (isScrolling: " + isScrolling + ", isProgrammatic: " + isProgrammaticScroll + ")");
            recyclerView.post(() -> applySmartPaddingDelayed(keyboardHeight));
            return;
        }
        
        applySmartPaddingInternal(keyboardHeight);
    }
    
    /**
     * 지연된 패딩 적용 (스크롤 완료 후 실행)
     */
    private void applySmartPaddingDelayed(int keyboardHeight) {
        if (isScrolling || isProgrammaticScroll) {
            // 아직 스크롤이 진행 중이면 다시 지연
            recyclerView.postDelayed(() -> applySmartPaddingDelayed(keyboardHeight), 50);
            return;
        }
        applySmartPaddingInternal(keyboardHeight);
    }
    
    /**
     * 실제 패딩 적용 로직 (중복 처리 방지 및 디바운싱 최적화 포함)
     */
    private void applySmartPaddingInternal(int keyboardHeight) {
        // 중복 처리 방지: 같은 키보드 높이가 짧은 시간 내에 반복 요청되는 것을 방지
        long currentTime = System.currentTimeMillis();
        if (keyboardHeight == lastAppliedKeyboardHeight && 
            (currentTime - lastPaddingChangeTime) < PADDING_CHANGE_DEBOUNCE_MS) {
            Log.d(TAG, "중복 패딩 요청 방지 - 같은 높이: " + keyboardHeight + "px, 시간차: " + 
                  (currentTime - lastPaddingChangeTime) + "ms");
            return;
        }
        
        float density = getResources().getDisplayMetrics().density;
        int currentPadding = recyclerView.getPaddingBottom();
        
        // 하단 필드 특수 처리가 진행 중인지 확인
        int bottomFieldPaddingThreshold = keyboardHeight + (int) (150 * density);
        if (currentPadding > bottomFieldPaddingThreshold) {
            Log.d(TAG, "🔽 하단 필드 특수 패딩 적용 중이므로 일반 패딩 조정 건너뜀 (현재: " + currentPadding + "px, 임계값: " + bottomFieldPaddingThreshold + "px)");
            return;
        }
        
        // 새로운 패딩 계산 (키보드 위 80dp 여유 공간)
        int baseMargin = (int) (80 * density);
        int newPaddingBottom = keyboardHeight + baseMargin;
        
        // 패딩 변화량 계산 및 임계값 검사
        int paddingDiff = Math.abs(newPaddingBottom - currentPadding);
        int minChangeThreshold = (int) (15 * density); // 15dp 이상 차이날 때만 변경
        
        if (paddingDiff > minChangeThreshold) {
            Log.d(TAG, String.format("🎯 스마트 패딩 적용 - 키보드: %dpx, 현재: %dpx, 새로운: %dpx, 차이: %dpx", 
                    keyboardHeight, currentPadding, newPaddingBottom, paddingDiff));
            
            // 포커스된 필드의 현재 위치 저장 (패딩 변화 후 스크롤 보정용)
            View focusedView = lastFocusedEditText;
            int scrollOffsetBeforePadding = 0;
            
            if (focusedView != null && lastFocusedPosition >= 0) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lastFocusedPosition);
                if (viewHolder != null) {
                    scrollOffsetBeforePadding = viewHolder.itemView.getTop();
                }
            }
            
            // 중복 처리 방지를 위한 상태 업데이트
            lastAppliedKeyboardHeight = keyboardHeight;
            lastPaddingChangeTime = currentTime;
            
            // 애니메이션과 함께 패딩 적용
            applyPaddingWithAnimation(newPaddingBottom, scrollOffsetBeforePadding, focusedView);
            
        } else {
            Log.d(TAG, "패딩 변화량 미미하여 적용 건너뜀 - 차이: " + paddingDiff + "px (임계값: " + minChangeThreshold + "px)");
        }
    }
    
    /**
     * 애니메이션과 함께 패딩을 적용하고 스크롤 위치를 자동 보정
     */
    private void applyPaddingWithAnimation(int newPadding, int previousScrollOffset, View focusedView) {
        if (recyclerView == null) return;
        
        int currentPadding = recyclerView.getPaddingBottom();
        
        // 패딩 즉시 적용 (RecyclerView는 애니메이션 지원하지 않으므로)
        recyclerView.setPadding(
            recyclerView.getPaddingLeft(),
            recyclerView.getPaddingTop(),
            recyclerView.getPaddingRight(),
            newPadding
        );
        recyclerView.setClipToPadding(false);
        
        // 패딩 변화 후 스크롤 위치 보정
        recyclerView.post(() -> {
            if (focusedView != null && lastFocusedPosition >= 0) {
                adjustScrollPositionAfterPaddingChange(previousScrollOffset, currentPadding, newPadding);
            }
        });
        
        Log.d(TAG, String.format("✅ 패딩 적용 완료 - %dpx -> %dpx", currentPadding, newPadding));
    }
    
    /**
     * 패딩 변화 후 스크롤 위치 자동 보정
     */
    private void adjustScrollPositionAfterPaddingChange(int previousScrollOffset, int oldPadding, int newPadding) {
        if (recyclerView == null || lastFocusedPosition < 0) return;
        
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lastFocusedPosition);
        if (viewHolder == null) return;
        
        int currentScrollOffset = viewHolder.itemView.getTop();
        int paddingDelta = newPadding - oldPadding;
        
        // 패딩 증가로 인해 포커스된 필드가 너무 위로 올라간 경우 보정
        if (paddingDelta > 0 && currentScrollOffset < previousScrollOffset) {
            int adjustmentNeeded = Math.min(paddingDelta / 2, previousScrollOffset - currentScrollOffset);
            if (adjustmentNeeded > 20) { // 20px 이상일 때만 보정
                recyclerView.smoothScrollBy(0, -adjustmentNeeded);
                Log.d(TAG, String.format("📍 패딩 증가 후 스크롤 위치 보정 - %dpx 상향 조정", adjustmentNeeded));
            }
        }
        // 패딩 감소로 인해 포커스된 필드가 너무 아래로 내려간 경우 보정
        else if (paddingDelta < 0 && currentScrollOffset > previousScrollOffset) {
            int adjustmentNeeded = Math.min(Math.abs(paddingDelta) / 2, currentScrollOffset - previousScrollOffset);
            if (adjustmentNeeded > 20) { // 20px 이상일 때만 보정
                recyclerView.smoothScrollBy(0, adjustmentNeeded);
                Log.d(TAG, String.format("📍 패딩 감소 후 스크롤 위치 보정 - %dpx 하향 조정", adjustmentNeeded));
            }
        }
    }
    
    /**
     * 스마트 패딩 제거 (기존 호환성 유지용)
     */
    private void resetSmartPadding() {
        resetSmartPaddingWithAnimation();
    }
    
    /**
     * 애니메이션과 함께 스마트 패딩을 부드럽게 제거
     */
    private void resetSmartPaddingWithAnimation() {
        if (recyclerView == null) return;
        
        int currentPadding = recyclerView.getPaddingBottom();
        
        // 패딩이 이미 0이면 제거할 필요 없음
        if (currentPadding <= 20) { // 20px 이하는 사실상 패딩 없음으로 간주
            Log.d(TAG, "패딩이 이미 최소값이므로 제거 건너뜀 - 현재: " + currentPadding + "px");
            return;
        }
        
        // 중복 처리 방지를 위한 상태 업데이트
        lastAppliedKeyboardHeight = 0;
        lastPaddingChangeTime = System.currentTimeMillis();
        
        Log.d(TAG, String.format("🔄 스마트 패딩 부드럽게 제거 - 현재: %dpx -> 0px", currentPadding));
        
        // 포커스된 필드의 현재 위치 저장 (패딩 제거 후 스크롤 보정용)
        View focusedView = lastFocusedEditText;
        int scrollOffsetBeforePaddingReset = 0;
        
        if (focusedView != null && lastFocusedPosition >= 0) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lastFocusedPosition);
            if (viewHolder != null) {
                scrollOffsetBeforePaddingReset = viewHolder.itemView.getTop();
            }
        }
        
        // final 변수로 람다에서 사용
        final int finalScrollOffset = scrollOffsetBeforePaddingReset;
        
        // 패딩 즉시 제거
        recyclerView.setPadding(
            recyclerView.getPaddingLeft(),
            recyclerView.getPaddingTop(),
            recyclerView.getPaddingRight(),
            0
        );
        recyclerView.setClipToPadding(true);
        
        // 패딩 제거 후 스크롤 위치 보정
        if (focusedView != null && finalScrollOffset != 0) {
            recyclerView.post(() -> {
                adjustScrollPositionAfterPaddingRemoval(finalScrollOffset, currentPadding);
            });
        }
        
        // 포커스 정보 초기화 (키보드가 완전히 사라진 후)
        recyclerView.postDelayed(() -> {
            if (!isKeyboardVisible) {
                lastFocusedEditText = null;
                lastFocusedPosition = -1;
                Log.d(TAG, "🧹 포커스 정보 초기화 완료");
            }
        }, 200);
        
        Log.d(TAG, "✅ 스마트 패딩 제거 완료");
    }
    
    /**
     * 패딩 제거 후 스크롤 위치 보정
     */
    private void adjustScrollPositionAfterPaddingRemoval(int previousScrollOffset, int removedPadding) {
        if (recyclerView == null || lastFocusedPosition < 0) return;
        
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lastFocusedPosition);
        if (viewHolder == null) return;
        
        int currentScrollOffset = viewHolder.itemView.getTop();
        
        // 패딩 제거로 인해 필드가 너무 아래로 내려간 경우 보정
        if (currentScrollOffset > previousScrollOffset + removedPadding / 4) {
            int adjustmentNeeded = Math.min(removedPadding / 3, currentScrollOffset - previousScrollOffset);
            if (adjustmentNeeded > 30) { // 30px 이상일 때만 보정
                recyclerView.smoothScrollBy(0, -adjustmentNeeded);
                Log.d(TAG, String.format("📍 패딩 제거 후 스크롤 위치 보정 - %dpx 상향 조정", adjustmentNeeded));
            }
        }
    }
    
    /**
     * 포커스 변경 처리 - 스마트 포커싱 로직
     */
    private void handleFocusChange(View oldFocus, View newFocus) {
        Log.d(TAG, "🎯 포커스 변경 감지 - 이전: " + getViewInfo(oldFocus) + " → 새로운: " + getViewInfo(newFocus));
        
        if (newFocus instanceof com.google.android.material.textfield.TextInputEditText) {
            // EditText에 포커스가 왔을 때
            if (!isScrolling && !isProgrammaticScroll) {
                lastFocusedEditText = newFocus;
                lastFocusedPosition = findEditTextPosition(newFocus);
                
                if (keyboardCheckRunnable != null) {
                    keyboardHandler.removeCallbacks(keyboardCheckRunnable);
                }
                
                keyboardCheckRunnable = () -> {
                    if (!isKeyboardVisible && !isScrolling && !isProgrammaticScroll) {
                        // 키보드가 아직 나타나지 않았으면 예상 패딩 적용 후 스크롤
                        applyEstimatedPadding();
                        if (lastFocusedPosition >= 0) {
                            scrollToFocusedField(lastFocusedPosition, getEstimatedKeyboardHeight());
                        }
                    }
                };
                keyboardHandler.postDelayed(keyboardCheckRunnable, 150);
                
                Log.d(TAG, "📝 EditText 포커스 획득 - 위치: " + lastFocusedPosition);
            }
        } else if (oldFocus instanceof com.google.android.material.textfield.TextInputEditText && 
                   !(newFocus instanceof com.google.android.material.textfield.TextInputEditText)) {
            // EditText에서 다른 뷰로 포커스가 이동했을 때
            if (!isKeyboardVisible) {
                resetSmartPadding();
            }
        }
    }
    
    /**
     * 포커스된 필드로 스마트 스크롤
     */
    private void scrollToFocusedField(int position, int keyboardHeight) {
        if (recyclerView == null || position < 0) return;
        
        recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null && viewHolder.itemView != null) {
                
                // 화면 정보 계산
                int[] recyclerLocation = new int[2];
                recyclerView.getLocationOnScreen(recyclerLocation);
                
                int[] itemLocation = new int[2];
                viewHolder.itemView.getLocationOnScreen(itemLocation);
                
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int visibleHeight = screenHeight - keyboardHeight;
                int targetY = visibleHeight - keyboardHeight - (int)(100 * getResources().getDisplayMetrics().density); // 키보드 위 100dp
                
                int itemTop = itemLocation[1];
                int itemHeight = viewHolder.itemView.getHeight();
                int desiredItemY = targetY - itemHeight / 2;
                
                int scrollOffset = itemTop - desiredItemY;
                
                if (Math.abs(scrollOffset) > 50) { // 50px 이상 차이날 때만 스크롤
                    // 프로그래매틱 스크롤 플래그 설정
                    isProgrammaticScroll = true;
                    recyclerView.smoothScrollBy(0, scrollOffset);
                    Log.d(TAG, "📍 프로그래매틱 스크롤 실행 - 위치: " + position + ", 오프셋: " + scrollOffset + "px");
                    
                    // 스크롤 완료 후 플래그 리셋 (1초 후)
                    recyclerView.postDelayed(() -> {
                        isProgrammaticScroll = false;
                        Log.d(TAG, "🔄 프로그래매틱 스크롤 플래그 리셋");
                    }, 1000);
                }
            }
        });
    }
    
    /**
     * EditText의 RecyclerView 내 위치 찾기
     */
    private int findEditTextPosition(View editText) {
        if (recyclerView == null || editText == null) return -1;
        
        View itemView = editText;
        while (itemView != null && itemView.getParent() != recyclerView) {
            if (itemView.getParent() instanceof View) {
                itemView = (View) itemView.getParent();
            } else {
                break;
            }
        }
        
        if (itemView != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(itemView);
            if (viewHolder != null) {
                return viewHolder.getAdapterPosition();
            }
        }
        
        return -1;
    }
    
    /**
     * 예상 키보드 높이 계산 (Adapter에서 접근 가능하도록 public으로 변경)
     */
    public int getEstimatedKeyboardHeight() {
        if (currentKeyboardHeight > 0) {
            return currentKeyboardHeight;
        }
        // 화면 크기의 35%를 기본값으로 사용
        return (int) (getResources().getDisplayMetrics().heightPixels * 0.35f);
    }
    
    /**
     * 필드가 화면 하단 영역에 있는지 확인
     */
    private boolean isBottomField(TextInputEditText editText) {
        if (editText == null || recyclerView == null) return false;
        
        try {
            // 화면 높이 정보
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            
            // 현재 키보드 높이 (추정값 또는 실제값)
            int keyboardHeight = Math.max(currentKeyboardHeight, getEstimatedKeyboardHeight());
            
            // 가용한 화면 높이 (키보드 제외)
            int availableHeight = screenHeight - keyboardHeight;
            
            // 하단 영역 계산 (가용 높이의 하단 1/3 영역)
            int bottomThreshold = (int) (availableHeight * 0.67f); // 상단에서 67% 지점이 하단 영역의 시작점
            
            // 입력 필드의 화면상 Y 좌표 계산
            int[] fieldLocation = new int[2];
            editText.getLocationOnScreen(fieldLocation);
            int fieldY = fieldLocation[1];
            
            boolean isBottom = fieldY > bottomThreshold;
            Log.d(TAG, "하단 필드 판별 - Y좌표: " + fieldY + ", 임계값: " + bottomThreshold + 
                      ", 하단여부: " + isBottom + " (화면: " + screenHeight + ", 키보드: " + keyboardHeight + ")");
            
            return isBottom;
        } catch (Exception e) {
            Log.w(TAG, "하단 필드 판별 중 오류: " + e.getMessage());
            return false;
        }
    }

    /**
     * 예상 패딩 적용
     */
    private void applyEstimatedPadding() {
        if (recyclerView != null && recyclerView.getPaddingBottom() < 200) {
            int estimatedHeight = getEstimatedKeyboardHeight();
            applySmartPadding(estimatedHeight);
        }
    }
    
    /**
     * 기존 데이터 필드 터치 시 전용 처리 메서드 (Adapter에서 호출)
     * 편집 모드에서 모든 기존 데이터 필드 터치 시 즉시 패딩 적용 및 정밀 스크롤 조정
     */
    public void handleExistingDataFieldTouch(TextInputEditText editText, int position) {
        Log.d(TAG, "📝 기존 데이터 필드 터치 처리 시작 - 위치: " + position + ", 모드: " + currentMode);
        
        if (currentMode != 2 && currentMode != 1) { // 편집 모드 또는 등록 모드가 아닌 경우 무시
            Log.w(TAG, "편집/등록 모드가 아니므로 기존 데이터 필드 처리 건너뜀 (현재 모드: " + currentMode + ")");
            return;
        }
        
        if (isScrolling || isProgrammaticScroll) { // 스크롤 중인 경우 무시
            Log.w(TAG, "스크롤 중이므로 기존 데이터 필드 처리 건너뜀");
            return;
        }
        
        // 1. 포커스 정보 업데이트
        lastFocusedEditText = editText;
        lastFocusedPosition = position;
        
        // 2. 모드별 키보드 처리
        boolean shouldShowKeyboard = currentMode == 2; // 편집 모드에서만 키보드 표시
        if (shouldShowKeyboard) {
            // 편집 모드: 즉시 키보드 표시
            android.view.inputmethod.InputMethodManager imm = 
                (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                boolean keyboardShown = imm.showSoftInput(editText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
                Log.d(TAG, "📝 편집 모드 - 기존 데이터 필드 키보드 표시 요청 결과: " + keyboardShown);
            }
        } else {
            // 등록 모드: 키보드 표시하지 않고 스크롤만 수행 (읽기 전용 필드이므로)
            Log.d(TAG, "📝 등록 모드 - 기존 데이터 필드는 읽기 전용이므로 키보드 표시 생략, 스크롤만 수행");
        }
        
        // 3. 모드별 스크롤 및 패딩 처리
        if (shouldShowKeyboard) {
            // 편집 모드: 키보드를 고려한 패딩 및 스크롤
            boolean isBottomField = isBottomField(editText);
            if (isBottomField) {
                // 하단 필드인 경우 강화된 패딩 적용
                applyBottomFieldPadding();
                startBottomFieldScrollSequence(position);
            } else {
                // 중상단 필드인 경우 일반 패딩 적용
                applyEstimatedPadding();
                // 키보드 표시 후 스크롤 조정
                keyboardHandler.postDelayed(() -> {
                    if (isKeyboardVisible && currentKeyboardHeight > 0) {
                        scrollToFocusedField(position, currentKeyboardHeight);
                    } else {
                        scrollToFocusedField(position, getEstimatedKeyboardHeight());
                    }
                }, 200);
            }
        } else {
            // 등록 모드: 키보드 없이 단순 스크롤만 수행
            Log.d(TAG, "📝 등록 모드 - 기존 데이터 필드 단순 스크롤 수행");
            keyboardHandler.postDelayed(() -> {
                recyclerView.scrollToPosition(position); // 키보드 고려 없는 단순 스크롤
            }, 100);
        }
    }

    /**
     * 하단 필드 터치 시 전용 처리 메서드 (Adapter에서 호출)
     * 편집 모드에서 하단 필드 터치 시 즉시 패딩 적용 및 정밀 스크롤 조정
     */
    public void handleBottomFieldTouch(TextInputEditText editText, int position) {
        Log.d(TAG, "🔽 하단 필드 터치 처리 시작 - 위치: " + position);
        
        if (currentMode != 2) { // 편집 모드가 아닌 경우 무시
            Log.w(TAG, "편집 모드가 아니므로 하단 필드 처리 건너뜀");
            return;
        }
        
        if (isScrolling || isProgrammaticScroll) { // 스크롤 중인 경우 무시
            Log.w(TAG, "스크롤 중이므로 하단 필드 처리 건너뜀");
            return;
        }
        
        // 1. 포커스 정보 업데이트
        lastFocusedEditText = editText;
        lastFocusedPosition = position;
        
        // 2. 즉시 키보드 표시
        android.view.inputmethod.InputMethodManager imm = 
            (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean keyboardShown = imm.showSoftInput(editText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
            Log.d(TAG, "🔽 하단 필드 키보드 표시 요청 결과: " + keyboardShown);
        }
        
        // 3. 즉시 예상 패딩 적용 (하단 필드용 강화된 패딩)
        applyBottomFieldPadding();
        
        // 4. 하단 필드 전용 스크롤 조정 시퀀스 시작
        startBottomFieldScrollSequence(position);
    }
    
    /**
     * 하단 필드용 강화된 패딩 적용 - 일반 패딩보다 더 큰 여유 공간 제공
     */
    private void applyBottomFieldPadding() {
        if (recyclerView != null) {
            int keyboardHeight = getEstimatedKeyboardHeight();
            float density = getResources().getDisplayMetrics().density;
            
            // 하단 필드용 확장된 패딩 (일반 80dp 대신 120dp 여유 공간)
            int paddingBottom = keyboardHeight + (int) (120 * density);
            
            recyclerView.setPadding(
                recyclerView.getPaddingLeft(),
                recyclerView.getPaddingTop(),
                recyclerView.getPaddingRight(),
                paddingBottom
            );
            recyclerView.setClipToPadding(false);
            
            Log.d(TAG, "🔽 하단 필드용 강화된 패딩 적용 - 키보드: " + keyboardHeight + "px, 패딩: " + paddingBottom + "px");
        }
    }
    
    /**
     * 하단 필드 전용 스크롤 조정 시퀀스
     */
    private void startBottomFieldScrollSequence(int position) {
        Log.d(TAG, "🔽 하단 필드 스크롤 시퀀스 시작 - 위치: " + position);
        
        // 즉시 예비 스크롤 (키보드가 나타나기 전 대략적 위치 조정)
        executeBottomFieldPreScroll(position);
        
        // 키보드 표시 후 정밀 스크롤 조정
        Handler bottomFieldHandler = new Handler(Looper.getMainLooper());
        final int[] checkCount = {0};
        final int maxChecks = 8; // 하단 필드는 더 많은 체크 (800ms)
        
        Runnable preciseScrollCheck = new Runnable() {
            @Override
            public void run() {
                checkCount[0]++;
                
                if (isKeyboardVisible && currentKeyboardHeight > 0) {
                    // 키보드 감지됨 - 정밀 스크롤 실행
                    Log.d(TAG, "🔽 하단 필드 키보드 감지됨 (높이: " + currentKeyboardHeight + "px) - 정밀 스크롤 실행");
                    executeBottomFieldPreciseScroll(position, currentKeyboardHeight);
                } else if (checkCount[0] < maxChecks) {
                    // 키보드 대기 계속
                    Log.d(TAG, "🔽 하단 필드 키보드 대기 중... (" + checkCount[0] + "/" + maxChecks + ")");
                    bottomFieldHandler.postDelayed(this, 100);
                } else {
                    // 타임아웃 - 예상 높이로 최종 조정
                    Log.d(TAG, "🔽 하단 필드 키보드 감지 타임아웃 - 예상 높이로 최종 조정");
                    executeBottomFieldPreciseScroll(position, getEstimatedKeyboardHeight());
                }
            }
        };
        
        // 첫 번째 체크 시작 (100ms 후)
        bottomFieldHandler.postDelayed(preciseScrollCheck, 100);
    }
    
    /**
     * 하단 필드 예비 스크롤 - 키보드 나타나기 전 대략적 위치 조정
     */
    private void executeBottomFieldPreScroll(int position) {
        if (recyclerView == null || position < 0) return;
        
        recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null && viewHolder.itemView != null) {
                
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int estimatedKeyboardHeight = getEstimatedKeyboardHeight();
                int visibleHeight = screenHeight - estimatedKeyboardHeight;
                float density = getResources().getDisplayMetrics().density;
                
                // 하단 필드는 키보드 바로 위 150dp에 위치하도록 목표 설정
                int targetY = visibleHeight - (int)(150 * density);
                
                int[] itemLocation = new int[2];
                viewHolder.itemView.getLocationOnScreen(itemLocation);
                int itemTop = itemLocation[1];
                int itemHeight = viewHolder.itemView.getHeight();
                int desiredItemY = targetY - itemHeight / 2;
                
                int scrollOffset = itemTop - desiredItemY;
                
                if (Math.abs(scrollOffset) > 20) { // 20px 이상 차이날 때만 스크롤
                    isProgrammaticScroll = true;
                    recyclerView.smoothScrollBy(0, scrollOffset);
                    Log.d(TAG, "🔽 하단 필드 예비 스크롤 실행 - 오프셋: " + scrollOffset + "px");
                    
                    // 스크롤 완료 후 플래그 리셋
                    recyclerView.postDelayed(() -> {
                        isProgrammaticScroll = false;
                    }, 600);
                } else {
                    Log.d(TAG, "🔽 하단 필드 예비 스크롤 불필요 - 이미 적절한 위치");
                }
            }
        });
    }
    
    /**
     * 하단 필드 처리가 현재 진행 중인지 확인
     */
    private boolean isBottomFieldCurrentlyProcessing() {
        if (recyclerView == null) return false;
        
        // 하단 필드 처리 중이면 패딩이 100dp 이상으로 크게 설정되어 있음
        float density = getResources().getDisplayMetrics().density;
        int currentPadding = recyclerView.getPaddingBottom();
        int keyboardHeight = Math.max(currentKeyboardHeight, getEstimatedKeyboardHeight());
        int bottomFieldThreshold = keyboardHeight + (int) (100 * density);
        
        return currentPadding > bottomFieldThreshold;
    }
    
    /**
     * 하단 필드 정밀 스크롤 - 키보드 표시 후 정확한 위치 조정
     */
    private void executeBottomFieldPreciseScroll(int position, int keyboardHeight) {
        if (recyclerView == null || position < 0) return;
        
        // 정확한 키보드 높이로 패딩 재조정
        if (Math.abs(keyboardHeight - getEstimatedKeyboardHeight()) > 50) {
            float density = getResources().getDisplayMetrics().density;
            int paddingBottom = keyboardHeight + (int) (120 * density);
            
            recyclerView.setPadding(
                recyclerView.getPaddingLeft(),
                recyclerView.getPaddingTop(),
                recyclerView.getPaddingRight(),
                paddingBottom
            );
            Log.d(TAG, "🔽 하단 필드 패딩 재조정 - 실제 키보드 높이: " + keyboardHeight + "px");
        }
        
        recyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null && viewHolder.itemView != null) {
                
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int visibleHeight = screenHeight - keyboardHeight;
                float density = getResources().getDisplayMetrics().density;
                
                // 하단 필드 최적 위치: 키보드 바로 위 120dp
                int targetY = visibleHeight - (int)(120 * density);
                
                int[] itemLocation = new int[2];
                viewHolder.itemView.getLocationOnScreen(itemLocation);
                int itemTop = itemLocation[1];
                int itemHeight = viewHolder.itemView.getHeight();
                int desiredItemY = targetY - itemHeight / 2;
                
                int scrollOffset = itemTop - desiredItemY;
                
                if (Math.abs(scrollOffset) > 15) { // 15px 이상 차이날 때만 미세 조정
                    isProgrammaticScroll = true;
                    recyclerView.smoothScrollBy(0, scrollOffset);
                    Log.d(TAG, "🔽 하단 필드 정밀 스크롤 실행 - 최종 오프셋: " + scrollOffset + "px");
                    
                    // 스크롤 완료 후 플래그 리셋
                    recyclerView.postDelayed(() -> {
                        isProgrammaticScroll = false;
                        Log.d(TAG, "🔽 하단 필드 스크롤 시퀀스 완료!");
                    }, 600);
                } else {
                    Log.d(TAG, "🔽 하단 필드 정밀 스크롤 불필요 - 이미 최적 위치 (오프셋: " + scrollOffset + "px)");
                }
            }
        });
    }
    
    /**
     * 뷰 정보 문자열 반환 (디버깅용)
     */
    private String getViewInfo(View view) {
        if (view == null) return "null";
        return view.getClass().getSimpleName() + "@" + Integer.toHexString(view.hashCode());
    }

    /**
     * Adapter에서 호출 가능한 스마트 패딩 메서드
     */
    public void applyKeyboardPaddingFromAdapter() {
        if (!isScrolling && !isProgrammaticScroll) {
            if (isKeyboardVisible) {
                applySmartPadding(currentKeyboardHeight);
            } else {
                applyEstimatedPadding();
            }
        } else {
            Log.d(TAG, "Adapter에서 패딩 요청했지만 스크롤 중이므로 무시 (isScrolling: " + isScrolling + ", isProgrammatic: " + isProgrammaticScroll + ")");
        }
    }

    /**
     * 현재 스크롤 상태 반환 (Adapter에서 확인용)
     */
    public boolean isScrolling() {
        return isScrolling;
    }

    /**
     * 스크롤 리스너 설정 - 스마트 스크롤 및 키보드 상태 관리
     */
    private void setupScrollListener() {
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_DRAGGING:
                            // 사용자가 직접 드래그 중
                            isUserDragging = true;
                            if (!isProgrammaticScroll) {
                                isScrolling = true;
                                Log.d(TAG, "📜 사용자 스크롤 시작");
                            } else {
                                Log.d(TAG, "🤖 프로그래매틱 스크롤 감지됨");
                            }
                            break;
                        case RecyclerView.SCROLL_STATE_SETTLING:
                            if (!isProgrammaticScroll) {
                                isScrolling = true;
                                Log.d(TAG, "📜 사용자 스크롤 정착 중");
                            } else {
                                Log.d(TAG, "🤖 프로그래매틱 스크롤 정착 중");
                            }
                            break;
                        case RecyclerView.SCROLL_STATE_IDLE:
                            // 스크롤 완료 후 상태 정리
                            recyclerView.postDelayed(() -> {
                                boolean wasUserScroll = isUserDragging && !isProgrammaticScroll;
                                
                                isScrolling = false;
                                isUserDragging = false;
                                
                                if (wasUserScroll) {
                                    Log.d(TAG, "📜 사용자 스크롤 완료 - 상태 정리 시작");
                                    handleUserScrollComplete();
                                } else {
                                    Log.d(TAG, "🤖 프로그래매틱 스크롤 완료 - 무한루프 방지로 상태 정리 생략");
                                }
                            }, 100); // 지연시간 단축
                            break;
                    }
                }
                
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    isScrolling = true;
                }
            });
            
            Log.d(TAG, "✅ 스마트 스크롤 리스너 설정 완료");
        }
    }
    
    /**
     * 사용자 스크롤 완료 후 상태 정리 (무한루프 방지)
     */
    private void handleUserScrollComplete() {
        if (getActivity() != null) {
            View currentFocus = getActivity().getCurrentFocus();
            if (currentFocus instanceof com.google.android.material.textfield.TextInputEditText) {
                // EditText에 포커스가 있을 때만 처리 (재트리거 방지)
                lastFocusedEditText = currentFocus;
                lastFocusedPosition = findEditTextPosition(currentFocus);
                Log.d(TAG, "🎯 사용자 스크롤 완료 - EditText 포커스 유지됨");
            } else if (recyclerView.getPaddingBottom() > 50 && !isKeyboardVisible) {
                // 키보드가 없고 포커스도 없으면 패딩 제거
                resetSmartPadding();
                Log.d(TAG, "🔄 사용자 스크롤 완료 - 불필요한 패딩 제거");
            }
        }
    }

    /**
     * 터치 리스너 설정 - 빈 공간 터치 시 키보드 닫기
     */
    private void setupTouchListener() {
        if (recyclerView != null) {
            recyclerView.setOnTouchListener((v, event) -> {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    View focusedView = getActivity() != null ? getActivity().getCurrentFocus() : null;
                    if (focusedView instanceof com.google.android.material.textfield.TextInputEditText) {
                        // 터치된 위치가 EditText가 아닌 경우 키보드 닫기
                        View touchedChild = recyclerView.findChildViewUnder(event.getX(), event.getY());
                        if (touchedChild == null) {
                            // 빈 공간을 터치한 경우
                            hideKeyboardAndResetPadding();
                            return true; // 이벤트 소비
                        }
                    }
                }
                return false; // 이벤트 전파
            });
        }
    }

    /**
     * 키보드 닫기 및 스마트 패딩 리셋
     */
    private void hideKeyboardAndResetPadding() {
        if (getActivity() == null) return;
        
        Log.d(TAG, "🔧 키보드 닫기 및 스마트 패딩 리셋 시작");
        
        // 현재 포커스된 뷰에서 포커스 제거
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            focusedView.clearFocus();
            Log.d(TAG, "📝 포커스 제거 완료");
        }
        
        // 키보드 강제 닫기
        android.view.inputmethod.InputMethodManager imm = 
            (android.view.inputmethod.InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            } else {
                View rootView = getView();
                if (rootView != null) {
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                }
            }
        }
        
        // 스마트 패딩 리셋
        resetSmartPadding();
        
        // 키보드 상태 업데이트
        isKeyboardVisible = false;
        currentKeyboardHeight = 0;
        
        Log.d(TAG, "✅ 키보드 닫기 및 스마트 패딩 리셋 완료");
    }


    /**
     * 뒤로가기 버튼 처리 - 키보드 및 스마트 패딩 우선 처리
     */
    private void setupBackPressCallback() {
        if (getActivity() == null) return;
        
        androidx.activity.OnBackPressedCallback callback = new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 키보드가 보이거나 패딩이 있는 경우 우선 처리
                boolean hasKeyboardOrPadding = isKeyboardVisible || 
                    (recyclerView != null && recyclerView.getPaddingBottom() > 50);
                
                Log.d(TAG, "🔙 뒤로가기 - 키보드: " + isKeyboardVisible + 
                      ", 패딩: " + (recyclerView != null ? recyclerView.getPaddingBottom() : "null"));
                
                if (hasKeyboardOrPadding) {
                    hideKeyboardAndResetPadding();
                } else {
                    // 기본 뒤로가기 동작
                    setEnabled(false);
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                    setEnabled(true);
                }
            }
        };
        
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        Log.d(TAG, "✅ 스마트 뒤로가기 콜백 등록 완료");
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