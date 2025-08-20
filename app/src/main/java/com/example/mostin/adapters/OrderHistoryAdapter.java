package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.databinding.ItemOrderHistoryBinding;
import com.example.mostin.models.OrderHistoryModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        this.orderHistory = orderHistory != null ? new ArrayList<>(orderHistory) : new ArrayList<>();
        this.workPlaceName = workPlaceName;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.orderHistory.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding binding = ItemOrderHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderHistoryModel order = orderHistory.get(position);
        holder.bind(order, workPlaceName, listener);
    }

    @Override
    public int getItemCount() {
        return orderHistory.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderHistoryBinding binding;

        ViewHolder(ItemOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(OrderHistoryModel order, String workPlaceName, OnOrderClickListener listener) {
            // Format date for better display
            String formattedDate = formatDate(order.getOrderingDay());
            binding.tvOrderDate.setText(formattedDate);
            
            // Set workplace name
            binding.tvWorkplaceName.setText(workPlaceName);
            
            // Set summary info
            binding.tvTotalItems.setText(String.valueOf(order.getTotalItems()));
            binding.tvTotalBoxes.setText(String.valueOf(order.getTotalBoxes()));
            
            
            // Set click listener
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onOrderClick(order);
                }
            });
        }

        private String formatDate(String dateString) {
            try {
                LocalDate date = LocalDate.parse(dateString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
                return date.format(formatter);
            } catch (DateTimeParseException e) {
                return dateString; // Return original if parsing fails
            }
        }
    }
}