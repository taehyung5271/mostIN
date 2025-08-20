package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.OrderHistoryModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes7.dex */
public class OrderHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private OnOrderClickListener listener;
    private List<OrderHistoryModel> orderHistory = new ArrayList();
    private String workPlaceName;

    public interface OnOrderClickListener {
        void onOrderClick(OrderHistoryModel orderHistoryModel);
    }

    public OrderHistoryAdapter(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void setOrderHistory(List<OrderHistoryModel> orderHistory, String workPlaceName) {
        this.orderHistory = orderHistory;
        this.workPlaceName = workPlaceName;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderHistoryModel order = this.orderHistory.get(position);
        String displayText = String.format("%s %s 발주리스트", order.getOrderingDay(), this.workPlaceName);
        holder.orderText.setText(displayText);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.OrderHistoryAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m106x47952e68(order, view);
            }
        });
    }

    /* renamed from: lambda$onBindViewHolder$0$com-example-mostin-adapters-OrderHistoryAdapter, reason: not valid java name */
    /* synthetic */ void m106x47952e68(OrderHistoryModel order, View v) {
        if (this.listener != null) {
            this.listener.onOrderClick(order);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.orderHistory.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderText;

        ViewHolder(View view) {
            super(view);
            this.orderText = (TextView) view.findViewById(R.id.orderText);
        }
    }
}
