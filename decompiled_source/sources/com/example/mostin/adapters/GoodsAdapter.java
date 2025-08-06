package com.example.mostin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.GoodsItem;
import java.util.List;

/* loaded from: classes7.dex */
public class GoodsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<GoodsItem> itemList;

    public GoodsAdapter(List<GoodsItem> itemList) {
        this.itemList = itemList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GoodsItem item = this.itemList.get(position);
        holder.imageView.setImageResource(item.getImageRes());
        holder.imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.GoodsAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GoodsAdapter.lambda$onBindViewHolder$0(holder, item, view);
            }
        });
        holder.overlay.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.GoodsAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                holder.overlay.setVisibility(8);
            }
        });
    }

    static /* synthetic */ void lambda$onBindViewHolder$0(ViewHolder holder, GoodsItem item, View v) {
        holder.overlay.setVisibility(0);
        holder.descriptionText.setText(item.getName());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionText;
        ImageView imageView;
        FrameLayout overlay;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.overlay = (FrameLayout) itemView.findViewById(R.id.overlay);
            this.descriptionText = (TextView) itemView.findViewById(R.id.descriptionText);
        }
    }
}
