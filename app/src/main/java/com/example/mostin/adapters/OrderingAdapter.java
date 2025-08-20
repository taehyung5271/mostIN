package com.example.mostin.adapters;

import android.content.Context;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

import com.example.mostin.R;
import com.example.mostin.models.GoodsModel;

import java.util.List;

public class OrderingAdapter extends RecyclerView.Adapter<OrderingAdapter.ViewHolder> {
    private final List<GoodsModel> goodsList;
    private final OnCopyClickListener listener;

    public interface OnCopyClickListener {
        void onCopyClick(String barcode, String productName, int quantity);
    }

    public OrderingAdapter(List<GoodsModel> goodsList, OnCopyClickListener listener) {
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsModel goods = goodsList.get(position);
        
        // 기본 상품 정보 설정
        holder.textProductName.setText(goods.getName());
        holder.textProductBarcode.setText(goods.getBarcode());
        
        // 수량 입력 필드 초기화 (빈 값으로)
        holder.editQuantity.setText("");
        
        // 복사 버튼 클릭 리스너
        holder.btnCopy.setOnClickListener(v -> {
            String quantityStr = holder.editQuantity.getText().toString().trim();
            if (!quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        listener.onCopyClick(goods.getBarcode(), goods.getName(), quantity);
                    }
                } catch (NumberFormatException e) {
                    // 잘못된 숫자 입력 시 무시
                }
            }
        });
    }
    

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName;
        TextView textProductBarcode;
        EditText editQuantity;
        MaterialButton btnCopy;

        ViewHolder(View view) {
            super(view);
            textProductName = view.findViewById(R.id.text_product_name);
            textProductBarcode = view.findViewById(R.id.text_product_barcode);
            editQuantity = view.findViewById(R.id.edit_quantity);
            btnCopy = view.findViewById(R.id.btn_copy);
        }
    }
} 