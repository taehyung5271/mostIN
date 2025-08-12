package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.GoodsModel;
import java.util.List;

/* loaded from: classes7.dex */
public class OrderingAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<GoodsModel> goodsList;
    private final OnCopyClickListener listener;

    public interface OnCopyClickListener {
        void onCopyClick(String str, int i);
    }

    public OrderingAdapter(List<GoodsModel> goodsList, OnCopyClickListener listener) {
        this.goodsList = goodsList;
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GoodsModel goods = this.goodsList.get(position);
        holder.textBarcode.setText(goods.getBarcode());
        holder.textGoodsName.setText(goods.getName());
        holder.btnCopy.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.OrderingAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NumberFormatException {
                this.f$0.m107x827f19ba(holder, goods, view);
            }
        });
    }

    /* renamed from: lambda$onBindViewHolder$0$com-example-mostin-adapters-OrderingAdapter, reason: not valid java name */
    /* synthetic */ void m107x827f19ba(ViewHolder holder, GoodsModel goods, View v) throws NumberFormatException {
        String boxCountStr = holder.editBoxCount.getText().toString();
        if (!boxCountStr.isEmpty()) {
            int boxCount = Integer.parseInt(boxCountStr);
            this.listener.onCopyClick(goods.getBarcode(), boxCount);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.goodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnCopy;
        EditText editBoxCount;
        TextView textBarcode;
        TextView textGoodsName;

        ViewHolder(View view) {
            super(view);
            this.textGoodsName = (TextView) view.findViewById(R.id.text_goods_name);
            this.textBarcode = (TextView) view.findViewById(R.id.text_barcode);
            this.editBoxCount = (EditText) view.findViewById(R.id.edit_box_count);
            this.btnCopy = (Button) view.findViewById(R.id.btn_copy);
        }
    }
}
