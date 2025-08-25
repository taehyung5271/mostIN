package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import com.example.mostin.R;
import com.example.mostin.models.GoodsModel;

import java.util.ArrayList;
import java.util.List;

public class AdminGoodsCategoryAdapter extends RecyclerView.Adapter<AdminGoodsCategoryAdapter.ViewHolder> {
    
    private List<GoodsModel> goodsList;
    private String[] categoryOptions = {"선택", "수입맥주", "국산맥주", "무알코올맥주"};
    private GoodsModel.Category[] categoryEnums = {null, GoodsModel.Category.IMPORTED_BEER, GoodsModel.Category.DOMESTIC_BEER, GoodsModel.Category.NON_ALCOHOL_BEER};
    
    public AdminGoodsCategoryAdapter(List<GoodsModel> goodsList) {
        this.goodsList = goodsList != null ? goodsList : new ArrayList<>();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_goods_category, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsModel goods = goodsList.get(position);
        
        // 상품명 표시
        holder.productName.setText(goods.getName());
        
        // 카테고리 드롭다운 설정 (커스텀 테마 적용)
        CategoryAdapter adapter = new CategoryAdapter(holder.itemView.getContext(), 
            java.util.Arrays.asList(categoryOptions));
        holder.categorySpinner.setAdapter(adapter);
        
        // 현재 카테고리 선택
        GoodsModel.Category currentCategory = goods.getCategory();
        if (currentCategory != null) {
            for (int i = 1; i < categoryEnums.length; i++) {
                if (categoryEnums[i] == currentCategory) {
                    holder.categorySpinner.setText(categoryOptions[i], false);
                    break;
                }
            }
        } else {
            holder.categorySpinner.setText(categoryOptions[0], false);
        }
        
        // 드롭다운 선택 리스너
        holder.categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && position < categoryEnums.length) {
                    // 선택된 카테고리를 모델에 저장
                    GoodsModel.Category selectedCategory = categoryEnums[position];
                    goods.setCategory(selectedCategory);
                    android.util.Log.d("CategoryAdapter", "상품 " + goods.getName() + "의 카테고리를 " + categoryOptions[position] + "로 변경");
                } else {
                    goods.setCategory(null);
                }
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return goodsList.size();
    }
    
    public List<GoodsModel> getGoodsList() {
        return goodsList;
    }
    
    /**
     * 카테고리가 변경된 상품들 반환
     */
    public List<GoodsModel> getUpdatedGoods() {
        return new ArrayList<>(goodsList);
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText productName;
        MaterialAutoCompleteTextView categorySpinner;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            categorySpinner = itemView.findViewById(R.id.categorySpinner);
        }
    }
}