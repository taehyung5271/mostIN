package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.OrderDetailModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private List<OrderDetailModel> details = new ArrayList<>();

    
    public void setOrderDetails(List<OrderDetailModel> details) {
        this.details = details != null ? details : new ArrayList<>();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailModel detail = details.get(position);
        holder.textGoodsName.setText(detail.getGoodsName());
        holder.textBoxCount.setText(detail.getBoxCount() + "박스");
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textGoodsName;
        TextView textBoxCount;

        ViewHolder(View view) {
            super(view);
            textGoodsName = view.findViewById(R.id.goodsName);
            textBoxCount = view.findViewById(R.id.boxCount);
        }

    }
} 