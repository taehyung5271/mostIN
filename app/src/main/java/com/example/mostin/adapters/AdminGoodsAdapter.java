package com.example.mostin.adapters;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.fragments.AdminGoodsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PRODUCT = 0;
    private static final int TYPE_ADD_BUTTON = 1;
    
    private List<GoodsModel> goodsList;
    private List<GoodsModel> originalGoodsList; // 원본 데이터 저장
    private int originalDataSize = 0; // 원본 데이터 크기 추적
    private boolean isEditMode = false;
    private boolean showCheckboxes = false;
    private int currentMode = 0; // 0: 일반, 1: 등록, 2: 편집, 3: 삭제
    private Set<String> selectedItems = new HashSet<>();
    private boolean hasTempData = false; // 임시 데이터 존재 여부 추적
    private AdminGoodsFragment parentFragment; // 부모 프래그먼트 참조
    private int firstNewItemPosition = -1; // 등록 모드에서 첫 번째 새 필드(A)의 위치

    public AdminGoodsAdapter(List<GoodsModel> goodsList) {
        this.goodsList = goodsList;
    }

    public AdminGoodsAdapter(List<GoodsModel> goodsList, AdminGoodsFragment fragment) {
        this.goodsList = goodsList;
        this.parentFragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        // 등록 모드(1)에서만 + 버튼 표시, 편집 모드(2)에서는 숨김
        if (currentMode == 1 && position == goodsList.size()) {
            return TYPE_ADD_BUTTON;
        }
        return TYPE_PRODUCT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD_BUTTON) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_add_button, parent, false);
            return new AddButtonViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_admin_goods, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddButtonViewHolder) {
            AddButtonViewHolder addHolder = (AddButtonViewHolder) holder;
            addHolder.btnAddMore.setOnClickListener(v -> {
                addNewRow();
                // 새로 추가된 아이템에 자동 포커스 및 키보드 표시를 Fragment에 요청
                if (parentFragment != null) {
                    // 새로 추가된 아이템의 위치 (현재 리스트 크기 - 1)
                    int newItemPosition = goodsList.size() - 1;
                    parentFragment.requestAutoFocusForNewItem(newItemPosition);
                }
            });
        } else if (holder instanceof ViewHolder) {
            ViewHolder productHolder = (ViewHolder) holder;
            GoodsModel goods = goodsList.get(position);
        
        productHolder.barcodeEdit.removeTextChangedListener(productHolder.barcodeWatcher);
        productHolder.nameEdit.removeTextChangedListener(productHolder.nameWatcher);
        
        productHolder.barcodeEdit.setText(goods.getBarcode());
        productHolder.nameEdit.setText(goods.getName());
        
        // 모드별 EditText 활성화/비활성화 제어
        boolean isNewItem = position >= originalDataSize; // 새로 추가된 아이템인지 확인
        
        if (currentMode == 1) { // 등록 모드
            // 기존 데이터는 완전히 편집 불가능, 새 데이터는 편집 가능
            if (!isNewItem) {
                // 기존 데이터는 완전히 비활성화 (편집 불가능)
                productHolder.barcodeEdit.setEnabled(false);
                productHolder.nameEdit.setEnabled(false);
                productHolder.barcodeEdit.setKeyListener(null); // 키보드 입력 차단
                productHolder.nameEdit.setKeyListener(null); // 키보드 입력 차단
                productHolder.barcodeEdit.setFocusable(false);
                productHolder.nameEdit.setFocusable(false);
                productHolder.barcodeEdit.setFocusableInTouchMode(false);
                productHolder.nameEdit.setFocusableInTouchMode(false);
                // 편집 불가능 필드는 키보드가 올라오지 않도록 설정
                productHolder.barcodeEdit.setShowSoftInputOnFocus(false);
                productHolder.nameEdit.setShowSoftInputOnFocus(false);
            } else {
                // 새 데이터는 편집 가능하도록 활성화
                productHolder.barcodeEdit.setEnabled(true);
                productHolder.nameEdit.setEnabled(true);
                productHolder.barcodeEdit.setKeyListener(android.text.method.DigitsKeyListener.getInstance());
                productHolder.nameEdit.setKeyListener(android.text.method.TextKeyListener.getInstance());
                productHolder.barcodeEdit.setFocusable(true);
                productHolder.nameEdit.setFocusable(true);
                productHolder.barcodeEdit.setFocusableInTouchMode(true);
                productHolder.nameEdit.setFocusableInTouchMode(true);
                // 새 필드는 키보드가 정상적으로 올라오도록 설정
                productHolder.barcodeEdit.setShowSoftInputOnFocus(true);
                productHolder.nameEdit.setShowSoftInputOnFocus(true);
            }
            Log.d("AdminGoodsAdapter", "등록 모드 - 위치: " + position + ", 원본크기: " + originalDataSize + ", 새아이템: " + isNewItem + ", 활성화: true, 읽기전용: " + (!isNewItem));
            
        } else if (currentMode == 2) { // 편집 모드
            // 모든 데이터 편집 가능
            productHolder.barcodeEdit.setEnabled(true);
            productHolder.nameEdit.setEnabled(true);
            // 키보드 입력 복원
            productHolder.barcodeEdit.setKeyListener(android.text.method.DigitsKeyListener.getInstance());
            productHolder.nameEdit.setKeyListener(android.text.method.TextKeyListener.getInstance());
            // 편집 모드에서는 모든 필드에서 키보드가 정상적으로 올라오도록 설정
            productHolder.barcodeEdit.setShowSoftInputOnFocus(true);
            productHolder.nameEdit.setShowSoftInputOnFocus(true);
        } else { // 일반 모드 및 삭제 모드
            // 모든 EditText 비활성화
            productHolder.barcodeEdit.setEnabled(false);
            productHolder.nameEdit.setEnabled(false);
            // 키보드 입력 차단
            productHolder.barcodeEdit.setKeyListener(null);
            productHolder.nameEdit.setKeyListener(null);
            // 비활성화된 필드는 키보드가 올라오지 않도록 설정
            productHolder.barcodeEdit.setShowSoftInputOnFocus(false);
            productHolder.nameEdit.setShowSoftInputOnFocus(false);
        }
        
        // 마이너스 버튼 표시 조건: 등록 모드이고 상품 추가 버튼으로 생성된 아이템
        // 첫 번째 새 필드(A)는 제외하고, 상품 추가 버튼으로 생성된 필드들(B, C, D...)에만 마이너스 버튼 표시
        boolean isAddedByPlusButton = (currentMode == 1) && (position > firstNewItemPosition) && (firstNewItemPosition >= 0);
        productHolder.btnRemove.setVisibility(isAddedByPlusButton ? View.VISIBLE : View.GONE);
        
        // 마이너스 버튼 클릭 이벤트
        productHolder.btnRemove.setOnClickListener(v -> {
            if (position < goodsList.size()) {
                removeItem(position);
            }
        });
        
        Log.d("AdminGoodsAdapter", "📍 위치: " + position + ", 원본크기: " + originalDataSize + 
              ", 첫새필드위치: " + firstNewItemPosition + ", 새아이템: " + isNewItem + 
              ", 플러스버튼생성: " + isAddedByPlusButton + ", 모드: " + currentMode + 
              ", 바코드: '" + goods.getBarcode() + "'");
        
        productHolder.barcodeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                goods.setBarcode(s.toString());
            }
        };
        
        productHolder.nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                goods.setName(s.toString());
            }
        };
        
        productHolder.barcodeEdit.addTextChangedListener(productHolder.barcodeWatcher);
        productHolder.nameEdit.addTextChangedListener(productHolder.nameWatcher);
        
        // EditText 포커스 리스너 추가 (편집 모드와 등록 모드에서 키보드 패딩 지원)
        if (currentMode == 1 || currentMode == 2) { // 등록 모드 또는 편집 모드
            setupEditTextFocusListener(productHolder.barcodeEdit, position);
            setupEditTextFocusListener(productHolder.nameEdit, position);
        }
        
        productHolder.checkbox.setVisibility(showCheckboxes ? View.VISIBLE : View.GONE);
        productHolder.checkbox.setChecked(selectedItems.contains(goods.getBarcode()));
        
        productHolder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(goods.getBarcode());
            } else {
                selectedItems.remove(goods.getBarcode());
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        // 등록 모드(1)에서만 + 버튼을 위한 +1, 편집 모드(2)에서는 기본 크기만
        return currentMode == 1 ? goodsList.size() + 1 : goodsList.size();
    }

    public void updateData(List<GoodsModel> newGoodsList) {
        // For testing, execute immediately on the same thread
        // In real app, this would be posted to main thread
        Runnable updateTask = () -> {
            if (newGoodsList != null) {
                // 등록 모드일 때는 절대로 originalDataSize를 변경하지 않음
                boolean preserveOriginalSize = (currentMode == 1) || (originalDataSize > 0 && isEditMode);
                
                this.goodsList = new ArrayList<>(newGoodsList);
                this.originalGoodsList = new ArrayList<>(newGoodsList); // 원본 저장
                
                if (!preserveOriginalSize) {
                    this.originalDataSize = newGoodsList.size(); // 원본 크기 저장
                    Log.d("AdminGoodsAdapter", "🔄 updateData() 호출 - 새 원본 크기 설정: " + originalDataSize);
                } else {
                    Log.d("AdminGoodsAdapter", "🔄 updateData() 호출 - 모드 " + currentMode + "로 인해 원본 크기 보존: " + originalDataSize + " (새 데이터 크기: " + newGoodsList.size() + ")");
                }
                
                Collections.sort(goodsList, (g1, g2) -> g1.getName().compareTo(g2.getName()));
            } else {
                this.goodsList = new ArrayList<>();
                this.originalGoodsList = new ArrayList<>();
                this.originalDataSize = 0;
            }
            notifyDataSetChanged();
        };
        
        // Check if we're already on the main thread (for testing)
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                updateTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(updateTask);
            }
        } catch (RuntimeException e) {
            // In test environment, Looper might not be available, execute directly
            updateTask.run();
        }
    }

    public void addNewRow() {
        if (showCheckboxes) {
            showCheckboxes = false;
        }
        
        Runnable addTask = () -> {
            int insertPosition = goodsList.size();
            goodsList.add(new GoodsModel("", ""));
            hasTempData = true; // 임시 데이터 추가 표시
            Log.d("AdminGoodsAdapter", "➕ addNewRow() 호출 - 위치: " + insertPosition + ", 원본 크기: " + originalDataSize + ", 새 전체 크기: " + goodsList.size());
            notifyItemInserted(insertPosition);
        };
        
        // Check if we're already on the main thread (for testing)
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                addTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(addTask);
            }
        } catch (RuntimeException e) {
            // In test environment, Looper might not be available, execute directly
            addTask.run();
        }
    }

    
    /**
     * 모드 설정 (0: 일반, 1: 등록, 2: 편집, 3: 삭제)
     */
    public void setMode(int mode) {
        int previousMode = this.currentMode;
        this.currentMode = mode;
        
        Log.d("AdminGoodsAdapter", "🔄 모드 변경: " + previousMode + " → " + mode + " (원본크기: " + originalDataSize + ", 전체크기: " + goodsList.size() + ")");
        
        // 등록 모드로 진입할 때 현재 데이터 크기를 원본 크기로 설정
        if (previousMode != 1 && mode == 1) {
            // 등록 모드 진입 시 현재 데이터 크기를 원본 크기로 고정
            this.originalDataSize = goodsList.size();
            this.firstNewItemPosition = goodsList.size(); // 첫 번째 새 필드 위치 기록
            Log.d("AdminGoodsAdapter", "✅ 등록 모드 진입 - originalDataSize 설정: " + originalDataSize + ", 첫 번째 새 필드 위치: " + firstNewItemPosition);
            
            // 첫 번째 새 필드 추가
            addNewRow();
            Log.d("AdminGoodsAdapter", "➕ 등록 모드 진입 - 첫 번째 새 필드(A) 자동 추가 (새 전체크기: " + goodsList.size() + ")");
        }
        
        // 모드 전환 시 임시 데이터 정리
        if (previousMode == 1 && mode != 1) {
            // 등록 모드에서 다른 모드로 전환할 때 새로 생긴 빈 텍스트필드 제거
            removeEmptyItems();
            firstNewItemPosition = -1; // 첫 번째 새 필드 위치 초기화
            Log.d("AdminGoodsAdapter", "등록 모드 종료 - 빈 텍스트필드 제거 완료, 첫 번째 새 필드 위치 초기화");
        } else if (previousMode == 2 && mode != 2) {
            // 편집 모드에서 다른 모드로 전환할 때 부분적으로 빈 상품 자동 정리
            removePartiallyEmptyItems();
            Log.d("AdminGoodsAdapter", "편집 모드 종료 - 부분적으로 빈 상품 자동 정리 완료");
        }
        
        // 편집 모드 설정 (등록 모드와 편집 모드에서만 true)
        isEditMode = (mode == 1 || mode == 2);
        
        // 체크박스 표시는 삭제 모드에서만
        if (mode == 3) {
            showCheckboxes = true;
        } else {
            showCheckboxes = false;
            selectedItems.clear();
        }
        
        notifyDataSetChanged();
    }


    public GoodsModel getNewItem() {
        if (goodsList.isEmpty()) return null;
        GoodsModel lastItem = goodsList.get(goodsList.size() - 1);
        if (lastItem.getBarcode().isEmpty() || lastItem.getName().isEmpty()) {
            return null;
        }
        return lastItem;
    }

    public List<GoodsModel> getUpdatedItems() {
        return new ArrayList<>(goodsList);
    }
    
    /**
     * 편집 모드에서 유효한 데이터만 반환 (바코드와 상품명이 모두 있는 것)
     */
    public List<GoodsModel> getValidEditedItems() {
        List<GoodsModel> validItems = new ArrayList<>();
        for (GoodsModel item : goodsList) {
            if (!item.getBarcode().trim().isEmpty() && !item.getName().trim().isEmpty()) {
                validItems.add(item);
            }
        }
        return validItems;
    }
    
    /**
     * 편집 모드에서 삭제할 아이템들의 바코드 반환 (원본에 있었지만 현재 빈 데이터가 된 것들)
     */
    public List<String> getItemsToDeleteInEditMode() {
        List<String> itemsToDelete = new ArrayList<>();
        
        if (originalGoodsList == null) return itemsToDelete;
        
        // 원본 데이터와 현재 데이터를 비교
        for (GoodsModel originalItem : originalGoodsList) {
            String originalBarcode = originalItem.getBarcode();
            
            // 현재 데이터에서 해당 바코드를 찾기
            boolean foundValidItem = false;
            for (GoodsModel currentItem : goodsList) {
                if (originalBarcode.equals(currentItem.getBarcode()) && 
                    !currentItem.getBarcode().trim().isEmpty() && 
                    !currentItem.getName().trim().isEmpty()) {
                    foundValidItem = true;
                    break;
                }
            }
            
            // 원본에는 있었지만 현재에는 유효하지 않은 경우 삭제 대상
            if (!foundValidItem) {
                itemsToDelete.add(originalBarcode);
                Log.d("AdminGoodsAdapter", "편집 모드 삭제 대상: " + originalBarcode);
            }
        }
        
        return itemsToDelete;
    }

    public List<String> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    /**
     * 원본 데이터 크기 반환 (포커스 설정용)
     */
    public int getOriginalDataSize() {
        return originalDataSize;
    }

    /**
     * 첫 번째 새 필드에 포커스 요청 (어댑터에서 직접 처리)
     */
    public void requestFocusOnFirstNewItem() {
        if (firstNewItemPosition >= 0 && firstNewItemPosition < goodsList.size()) {
            Log.d("AdminGoodsAdapter", "🎯 첫 번째 새 필드에 포커스 요청: 위치 " + firstNewItemPosition);
            
            // RecyclerView에서 해당 위치로 스크롤 및 포커스 설정을 Fragment에서 처리하도록 콜백
            if (parentFragment != null) {
                parentFragment.getView().post(() -> {
                    parentFragment.scrollToPositionAndFocus(firstNewItemPosition);
                });
            }
        } else {
            Log.w("AdminGoodsAdapter", "❌ 첫 번째 새 필드 위치가 유효하지 않음: " + firstNewItemPosition);
        }
    }

    /**
     * 임시 데이터(빈 데이터) 제거
     */
    public void removeTempData() {
        if (!hasTempData || goodsList.isEmpty()) {
            return;
        }
        
        Runnable removeTask = () -> {
            // 마지막 항목이 빈 데이터인지 확인하고 제거
            int lastIndex = goodsList.size() - 1;
            if (lastIndex >= 0) {
                GoodsModel lastItem = goodsList.get(lastIndex);
                if (lastItem.getBarcode().isEmpty() && lastItem.getName().isEmpty()) {
                    goodsList.remove(lastIndex);
                    hasTempData = false;
                    notifyItemRemoved(lastIndex);
                }
            }
        };
        
        // Check if we're already on the main thread (for testing)
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                removeTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(removeTask);
            }
        } catch (RuntimeException e) {
            // In test environment, Looper might not be available, execute directly
            removeTask.run();
        }
    }

    /**
     * 임시 데이터 완료 처리 (등록 완료 시 호출)
     */
    public void completeTempData() {
        hasTempData = false;
        // 등록 완료 후 현재 크기를 새로운 원본 크기로 설정
        originalDataSize = goodsList.size();
        Log.d("AdminGoodsAdapter", "등록 완료 - 새로운 원본 크기: " + originalDataSize);
    }

    /**
     * 등록 가능한 완전한 상품들 반환 (새로 추가된 아이템 중 바코드와 상품명이 모두 입력된 것들)
     */
    public List<GoodsModel> getCompletedNewItems() {
        List<GoodsModel> completedItems = new ArrayList<>();
        
        Log.d("AdminGoodsAdapter", "🔍 getCompletedNewItems() 시작 - 원본 데이터 크기: " + originalDataSize + ", 전체 크기: " + goodsList.size());
        
        // originalDataSize가 0이거나 비정상적인 경우 보정
        int effectiveOriginalSize = originalDataSize;
        if (originalDataSize <= 0 || originalDataSize >= goodsList.size()) {
            // 비어있지 않은 기존 아이템들을 찾아서 원본 크기 추정
            int nonEmptyCount = 0;
            for (GoodsModel item : goodsList) {
                if (!item.getBarcode().trim().isEmpty() && !item.getName().trim().isEmpty()) {
                    nonEmptyCount++;
                }
            }
            
            // 마지막 몇 개가 비어있는 새 아이템이라고 가정
            effectiveOriginalSize = Math.max(0, nonEmptyCount - 1); // 최소 1개는 새 아이템으로 처리
            Log.d("AdminGoodsAdapter", "⚠️ originalDataSize 보정: " + originalDataSize + " → " + effectiveOriginalSize);
        }
        
        // 원본 데이터 크기 이후에 추가된 아이템들만 확인
        for (int i = effectiveOriginalSize; i < goodsList.size(); i++) {
            GoodsModel item = goodsList.get(i);
            if (!item.getBarcode().trim().isEmpty() && !item.getName().trim().isEmpty()) {
                completedItems.add(new GoodsModel(item.getBarcode().trim(), item.getName().trim()));
                Log.d("AdminGoodsAdapter", "✅ 새 아이템 발견 (위치 " + i + "): " + item.getBarcode() + " - " + item.getName());
            } else {
                Log.d("AdminGoodsAdapter", "❌ 빈 새 아이템 (위치 " + i + "): '" + item.getBarcode() + "' - '" + item.getName() + "'");
            }
        }
        
        Log.d("AdminGoodsAdapter", "🏁 getCompletedNewItems() 완료 - 새 아이템: " + completedItems.size() + "개");
        return completedItems;
    }

    /**
     * 빈 아이템들 제거 (등록 완료 후 정리) - 새로 추가된 아이템 중 빈 것들만
     * 조건: 바코드 AND 상품명이 둘 다 비어있는 경우 삭제
     */
    public void removeEmptyItems() {
        Runnable removeTask = () -> {
            // 역순으로 제거해서 인덱스 문제 방지
            for (int i = goodsList.size() - 1; i >= originalDataSize; i--) {
                GoodsModel item = goodsList.get(i);
                if (item.getBarcode().trim().isEmpty() && item.getName().trim().isEmpty()) {
                    goodsList.remove(i);
                    notifyItemRemoved(i);
                    Log.d("AdminGoodsAdapter", "빈 아이템 제거됨: " + i);
                }
            }
            hasTempData = false;
        };
        
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                removeTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(removeTask);
            }
        } catch (RuntimeException e) {
            removeTask.run();
        }
    }

    /**
     * 부분적으로 비어있는 아이템들 제거 (모드 종료 시 자동 정리)
     * 조건: 바코드 OR 상품명 중 하나라도 비어있는 경우 삭제 (더 엄격한 조건)
     * 예외: 현재 포커스된 아이템과 등록 모드의 첫 번째 새 필드(A)는 보호
     */
    public void removePartiallyEmptyItems() {
        if (parentFragment == null) {
            Log.w("AdminGoodsAdapter", "parentFragment가 null이므로 부분 정리 건너뜀");
            return;
        }

        Runnable removeTask = () -> {
            int removedCount = 0;
            
            // 현재 포커스된 EditText의 위치 찾기
            int currentFocusPosition = getCurrentFocusedPosition();
            
            // 역순으로 제거해서 인덱스 문제 방지
            for (int i = goodsList.size() - 1; i >= 0; i--) {
                GoodsModel item = goodsList.get(i);
                String barcode = item.getBarcode() != null ? item.getBarcode().trim() : "";
                String name = item.getName() != null ? item.getName().trim() : "";
                
                // 삭제 조건: 바코드 OR 상품명 중 하나라도 비어있음
                boolean isEmpty = barcode.isEmpty() || name.isEmpty();
                
                // 보호 조건 확인
                boolean isProtected = false;
                
                // 1. 현재 포커스된 아이템은 보호
                if (i == currentFocusPosition) {
                    isProtected = true;
                    Log.d("AdminGoodsAdapter", "현재 포커스된 아이템 보호: " + i);
                }
                
                // 2. 등록 모드의 첫 번째 새 필드(A)는 보호 (최소 1개 입력 필드 유지)
                if (currentMode == 1 && i == firstNewItemPosition && firstNewItemPosition >= 0) {
                    isProtected = true;
                    Log.d("AdminGoodsAdapter", "등록 모드의 첫 번째 새 필드(A) 보호: " + i);
                }
                
                // 삭제 실행
                if (isEmpty && !isProtected) {
                    goodsList.remove(i);
                    notifyItemRemoved(i);
                    removedCount++;
                    Log.d("AdminGoodsAdapter", "부분적으로 빈 아이템 제거됨 - 위치: " + i + 
                          ", 바코드: '" + barcode + "', 상품명: '" + name + "'");
                } else if (isEmpty && isProtected) {
                    Log.d("AdminGoodsAdapter", "부분적으로 빈 아이템이지만 보호되어 유지됨 - 위치: " + i + 
                          ", 바코드: '" + barcode + "', 상품명: '" + name + "'");
                }
            }
            
            if (removedCount > 0) {
                hasTempData = false;
                Log.d("AdminGoodsAdapter", "부분 정리 완료 - " + removedCount + "개 아이템 제거됨");
            } else {
                Log.d("AdminGoodsAdapter", "부분 정리 완료 - 제거할 아이템 없음");
            }
        };
        
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                removeTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(removeTask);
            }
        } catch (RuntimeException e) {
            removeTask.run();
        }
    }

    /**
     * 현재 포커스된 EditText의 위치 찾기
     */
    private int getCurrentFocusedPosition() {
        if (parentFragment == null || parentFragment.getActivity() == null) {
            return -1;
        }

        android.view.View currentFocus = parentFragment.getActivity().getCurrentFocus();
        if (!(currentFocus instanceof TextInputEditText)) {
            return -1;
        }

        // RecyclerView에서 해당 EditText가 포함된 ViewHolder 찾기
        android.view.View itemView = currentFocus;
        while (itemView != null && itemView.getParent() != null) {
            if (itemView.getParent() instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) itemView.getParent();
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(itemView);
                if (viewHolder != null) {
                    int position = viewHolder.getAdapterPosition();
                    Log.d("AdminGoodsAdapter", "현재 포커스된 위치: " + position);
                    return position;
                }
                break;
            }
            if (itemView.getParent() instanceof android.view.View) {
                itemView = (android.view.View) itemView.getParent();
            } else {
                break;
            }
        }

        return -1;
    }

    /**
     * EditText 스마트 터치 및 포커스 리스너 설정
     */
    private void setupEditTextFocusListener(TextInputEditText editText, int position) {
        if (editText == null || parentFragment == null) return;
        
        // 포커스 변경 리스너
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            Log.d("AdminGoodsAdapter", "🎯 EditText 포커스 변경 - 위치: " + position + ", 포커스: " + hasFocus);
            
            if (hasFocus && !parentFragment.isScrolling()) {
                // 키보드 표시 및 패딩 적용
                showKeyboardAndApplyPadding(editText);
            }
        });
        
        // 개선된 터치 리스너 - 스크롤과 편집 의도를 더 정확하게 구분
        editText.setOnTouchListener(new SmartTouchListener(editText, position));
    }
    
    /**
     * 키보드 표시 및 패딩 적용
     */
    private void showKeyboardAndApplyPadding(TextInputEditText editText) {
        if (parentFragment.getView() != null) {
            parentFragment.getView().post(() -> {
                parentFragment.applyKeyboardPaddingFromAdapter();
            });
        }
        
        // 키보드 표시
        android.content.Context context = editText.getContext();
        android.view.inputmethod.InputMethodManager imm = 
            (android.view.inputmethod.InputMethodManager) context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
        }
    }
    
    /**
     * 스마트 터치 리스너 - 스크롤과 편집 의도를 정확하게 구분하고 하단 필드 처리
     */
    private class SmartTouchListener implements android.view.View.OnTouchListener {
        private final TextInputEditText editText;
        private final int position;
        
        private float startX = 0, startY = 0;
        private long startTime = 0;
        private boolean possibleScroll = false;
        private boolean definiteScroll = false;
        
        public SmartTouchListener(TextInputEditText editText, int position) {
            this.editText = editText;
            this.position = position;
        }
        
        @Override
        public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
            switch (event.getAction()) {
                case android.view.MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    startTime = System.currentTimeMillis();
                    possibleScroll = false;
                    definiteScroll = false;
                    break;
                    
                case android.view.MotionEvent.ACTION_MOVE:
                    float deltaX = Math.abs(event.getX() - startX);
                    float deltaY = Math.abs(event.getY() - startY);
                    
                    // 스크롤 감지 로직 개선
                    if (deltaY > 20 || deltaX > 30) { // Y축 20px 또는 X축 30px 이상 움직이면 스크롤 가능성
                        possibleScroll = true;
                    }
                    
                    if (deltaY > 40) { // Y축 40px 이상 움직이면 확실한 스크롤
                        definiteScroll = true;
                    }
                    break;
                    
                case android.view.MotionEvent.ACTION_UP:
                    long duration = System.currentTimeMillis() - startTime;
                    float totalDelta = Math.abs(event.getY() - startY) + Math.abs(event.getX() - startX);
                    
                    // 편집 의도 판단 조건을 더 엄격하게
                    boolean isEditIntent = !definiteScroll && 
                                         duration < 250 && // 250ms 이하
                                         totalDelta < 15 && // 총 움직임 15px 이하
                                         !parentFragment.isScrolling(); // Fragment 스크롤 상태 확인
                    
                    Log.d("AdminGoodsAdapter", String.format(
                        "터치 분석 - 위치:%d, 시간:%dms, 움직임:%.1fpx, 스크롤상태:%b/%b, 편집의도:%b", 
                        position, duration, totalDelta, possibleScroll, definiteScroll, isEditIntent));
                    
                    if (isEditIntent) {
                        // 편집 모드 또는 등록 모드에서 기존 데이터 필드에 대한 스마트 처리
                        if (currentMode == 2 || currentMode == 1) { // 편집 모드 또는 등록 모드
                            boolean isExistingData = position < originalDataSize;
                            if (isExistingData) {
                                Log.d("AdminGoodsAdapter", "📝 기존 데이터 필드 터치 감지 - 스마트 처리 시작 (위치: " + position + ", 모드: " + currentMode + ")");
                                handleExistingDataFieldTouch(v, position);
                                return false;
                            }
                        }
                        
                        // 일반적인 편집 의도 처리 (등록 모드의 새 필드 등)
                        v.requestFocus();
                        showKeyboardAndApplyPadding(editText);
                        Log.d("AdminGoodsAdapter", "✏️ 편집 의도 감지 - 포커스 및 키보드 적용");
                    } else {
                        Log.d("AdminGoodsAdapter", "📜 스크롤 또는 의도 불분명 - 키보드 요청 생략");
                    }
                    break;
            }
            return false; // 다른 터치 이벤트도 처리되도록 함
        }
        
        /**
         * 하단 필드 여부 판별 - 화면 하단 1/3 영역에 있는 필드를 하단 필드로 판별
         */
        private boolean isBottomField(android.view.View view) {
            if (parentFragment == null || parentFragment.getView() == null) return false;
            
            try {
                // RecyclerView와 화면 정보 가져오기
                RecyclerView recyclerView = parentFragment.getView().findViewById(R.id.adminGoodsRecyclerView);
                if (recyclerView == null) return false;
                
                // 화면 높이 정보
                int screenHeight = parentFragment.getResources().getDisplayMetrics().heightPixels;
                
                // 현재 키보드 높이 (Fragment에서 추정값 또는 실제값)
                int keyboardHeight = parentFragment.getEstimatedKeyboardHeight();
                
                // 가용한 화면 높이 (키보드 제외)
                int availableHeight = screenHeight - keyboardHeight;
                
                // 하단 영역 계산 (가용 높이의 하단 1/3 + 키보드 높이만큼 위의 영역)
                int bottomThreshold = (int) (availableHeight * 0.67f); // 상단에서 67% 지점이 하단 영역의 시작점
                
                // 입력 필드의 화면상 Y 좌표 계산
                int[] viewLocation = new int[2];
                view.getLocationOnScreen(viewLocation);
                int viewCenterY = viewLocation[1] + (view.getHeight() / 2);
                
                boolean isBottom = viewCenterY > bottomThreshold;
                
                Log.d("AdminGoodsAdapter", String.format(
                    "하단필드 판별 - 위치:%d, 화면높이:%d, 키보드높이:%d, 가용높이:%d, 하단임계값:%d, 필드Y:%d, 하단여부:%b", 
                    position, screenHeight, keyboardHeight, availableHeight, bottomThreshold, viewCenterY, isBottom));
                
                return isBottom;
                
            } catch (Exception e) {
                Log.w("AdminGoodsAdapter", "하단 필드 판별 중 오류: " + e.getMessage());
                return false;
            }
        }
        
        /**
         * 기존 데이터 필드 터치 시 특별 처리 - 즉시 패딩 적용 및 정밀 위치 조정
         */
        private void handleExistingDataFieldTouch(android.view.View view, int position) {
            if (parentFragment == null) return;
            
            // 1. 즉시 포커스 설정
            view.requestFocus();
            Log.d("AdminGoodsAdapter", "🎯 기존 데이터 필드 즉시 포커스 설정 (위치: " + position + ")");
            
            // 2. Fragment의 기존 데이터 필드 전용 처리 메서드 호출
            parentFragment.handleExistingDataFieldTouch(editText, position);
        }
    }

    /**
     * 특정 위치의 아이템 제거 (마이너스 버튼으로 호출)
     */
    public void removeItem(int position) {
        if (position < 0 || position >= goodsList.size()) {
            Log.w("AdminGoodsAdapter", "잘못된 위치의 아이템 제거 시도: " + position);
            return;
        }
        
        // 원본 데이터와 첫 번째 새 필드(A)는 제거하지 않도록 보호
        // firstNewItemPosition 이하는 제거 불가 (기존 데이터 + 첫 번째 새 필드 A)
        if (position <= firstNewItemPosition || firstNewItemPosition < 0) {
            Log.w("AdminGoodsAdapter", "원본 데이터 또는 첫 번째 새 필드(A) 제거 시도 차단: " + position + " (첫 번째 새 필드 위치: " + firstNewItemPosition + ")");
            return;
        }
        
        Runnable removeTask = () -> {
            GoodsModel removedItem = goodsList.get(position);
            goodsList.remove(position);
            notifyItemRemoved(position);
            // 제거 후 인덱스가 변경되므로 뒤의 아이템들 업데이트
            notifyItemRangeChanged(position, goodsList.size() - position);
            
            Log.d("AdminGoodsAdapter", "아이템 제거됨 - 위치: " + position + 
                  ", 바코드: " + removedItem.getBarcode() + 
                  ", 상품명: " + removedItem.getName() + 
                  ", 남은 개수: " + goodsList.size());
        };
        
        try {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                removeTask.run();
            } else {
                new Handler(Looper.getMainLooper()).post(removeTask);
            }
        } catch (RuntimeException e) {
            removeTask.run();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText barcodeEdit, nameEdit;
        public CheckBox checkbox;
        public MaterialButton btnRemove;
        public TextWatcher barcodeWatcher, nameWatcher;

        ViewHolder(View itemView) {
            super(itemView);
            barcodeEdit = itemView.findViewById(R.id.barcodeEdit);
            nameEdit = itemView.findViewById(R.id.nameEdit);
            checkbox = itemView.findViewById(R.id.checkbox);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    static class AddButtonViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.button.MaterialButton btnAddMore;

        AddButtonViewHolder(View itemView) {
            super(itemView);
            btnAddMore = itemView.findViewById(R.id.btnAddMore);
        }
    }
} 