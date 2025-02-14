package com.example.mostin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private List<OrderHistoryModel> orderHistory = new ArrayList<>();
    private String workPlaceName;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(OrderHistoryModel order);
    }

    public OrderHistoryAdapter(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void setOrderHistory(List<OrderHistoryModel> orderHistory, String workPlaceName) {
        this.orderHistory = orderHistory;
        this.workPlaceName = workPlaceName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderHistoryModel order = orderHistory.get(position);
        String displayText = String.format("%s %s 발주리스트",
                order.getOrderingDay(),
                workPlaceName);
        holder.orderText.setText(displayText);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistory.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderText;

        ViewHolder(View view) {
            super(view);
            orderText = view.findViewById(R.id.orderText);
        }
    }
}