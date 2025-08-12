package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.OrderDetailModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes7.dex */
public class OrderDetailAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<OrderDetailModel> details = new ArrayList();

    public void setOrderDetails(List<OrderDetailModel> details) {
        this.details = details != null ? details : new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDetailModel detail = this.details.get(position);
        holder.textGoodsName.setText(detail.getGoodsName());
        holder.textBoxCount.setText(detail.getBoxCount() + "박스");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.details.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textBoxCount;
        TextView textGoodsName;

        ViewHolder(View view) {
            super(view);
            this.textGoodsName = (TextView) view.findViewById(R.id.goodsName);
            this.textBoxCount = (TextView) view.findViewById(R.id.boxCount);
        }
    }
}
