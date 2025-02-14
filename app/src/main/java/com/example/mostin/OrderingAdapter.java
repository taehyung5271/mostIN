package com.example.mostin;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderingAdapter extends RecyclerView.Adapter<OrderingAdapter.ViewHolder> {
    private final List<GoodsModel> goodsList;
    private final OnCopyClickListener listener;

    public interface OnCopyClickListener {
        void onCopyClick(String barcode, int boxCount);
    }

    public OrderingAdapter(List<GoodsModel> goodsList, OnCopyClickListener listener) {
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsModel goods = goodsList.get(position);
        holder.textBarcode.setText(goods.getBarcode());
        holder.textGoodsName.setText(goods.getName());
        
        holder.btnCopy.setOnClickListener(v -> {
            String boxCountStr = holder.editBoxCount.getText().toString();
            if (!boxCountStr.isEmpty()) {
                int boxCount = Integer.parseInt(boxCountStr);
                listener.onCopyClick(goods.getBarcode(), boxCount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textGoodsName;
        TextView textBarcode;
        EditText editBoxCount;
        Button btnCopy;

        ViewHolder(View view) {
            super(view);
            textGoodsName = view.findViewById(R.id.text_goods_name);
            textBarcode = view.findViewById(R.id.text_barcode);
            editBoxCount = view.findViewById(R.id.edit_box_count);
            btnCopy = view.findViewById(R.id.btn_copy);
        }
    }
} 