package com.example.mostin.adapters;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputEditText;
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
                // 새로 추가된 아이템에 자동 포커스는 Fragment에서 처리
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
            // 기존 데이터는 비활성화, 새 데이터만 활성화
            productHolder.barcodeEdit.setEnabled(isNewItem);
            productHolder.nameEdit.setEnabled(isNewItem);
            Log.d("AdminGoodsAdapter", "등록 모드 - 위치: " + position + ", 원본크기: " + originalDataSize + ", 새아이템: " + isNewItem + ", 활성화: " + isNewItem);
            
        } else if (currentMode == 2) { // 편집 모드
            // 모든 데이터 편집 가능
            productHolder.barcodeEdit.setEnabled(true);
            productHolder.nameEdit.setEnabled(true);
        } else { // 일반 모드 및 삭제 모드
            // 모든 EditText 비활성화
            productHolder.barcodeEdit.setEnabled(false);
            productHolder.nameEdit.setEnabled(false);
        }
        
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
                // 편집 모드나 등록 모드 중에는 originalDataSize 보존
                boolean preserveOriginalSize = (originalDataSize > 0 && isEditMode);
                
                this.goodsList = new ArrayList<>(newGoodsList);
                this.originalGoodsList = new ArrayList<>(newGoodsList); // 원본 저장
                
                if (!preserveOriginalSize) {
                    this.originalDataSize = newGoodsList.size(); // 원본 크기 저장
                    Log.d("AdminGoodsAdapter", "🔄 updateData() 호출 - 새 원본 크기 설정: " + originalDataSize);
                } else {
                    Log.d("AdminGoodsAdapter", "🔄 updateData() 호출 - 편집 모드로 인해 원본 크기 보존: " + originalDataSize);
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
     * @deprecated setMode() 메서드를 대신 사용하세요
     */
    @Deprecated
    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        if (showCheckboxes) {
            showCheckboxes = false;
        }
        notifyDataSetChanged();
    }
    
    /**
     * 모드 설정 (0: 일반, 1: 등록, 2: 편집, 3: 삭제)
     */
    public void setMode(int mode) {
        int previousMode = this.currentMode;
        this.currentMode = mode;
        
        Log.d("AdminGoodsAdapter", "🔄 모드 변경: " + previousMode + " → " + mode + " (원본크기: " + originalDataSize + ", 전체크기: " + goodsList.size() + ")");
        
        // 모드 전환 시 임시 데이터 정리
        if (previousMode == 1 && mode != 1) {
            // 등록 모드에서 다른 모드로 전환할 때 새로 생긴 빈 텍스트필드 제거
            removeEmptyItems();
            Log.d("AdminGoodsAdapter", "등록 모드 종료 - 빈 텍스트필드 제거 완료");
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

    /**
     * @deprecated setMode() 메서드를 대신 사용하세요
     */
    @Deprecated
    public void showCheckboxes(boolean show) {
        showCheckboxes = show;
        if (!show) {
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
     */
    public void removeEmptyItems() {
        Runnable removeTask = () -> {
            // 역순으로 제거해서 인덱스 문제 방지
            for (int i = goodsList.size() - 1; i >= originalDataSize; i--) {
                GoodsModel item = goodsList.get(i);
                if (item.getBarcode().trim().isEmpty() || item.getName().trim().isEmpty()) {
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText barcodeEdit, nameEdit;
        CheckBox checkbox;
        TextWatcher barcodeWatcher, nameWatcher;

        ViewHolder(View itemView) {
            super(itemView);
            barcodeEdit = itemView.findViewById(R.id.barcodeEdit);
            nameEdit = itemView.findViewById(R.id.nameEdit);
            checkbox = itemView.findViewById(R.id.checkbox);
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