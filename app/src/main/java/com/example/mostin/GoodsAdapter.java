package com.example.mostin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private final List<GoodsItem> itemList;

    public GoodsAdapter(List<GoodsItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsItem item = itemList.get(position);
        holder.imageView.setImageResource(item.getImageRes());

        // 이미지 클릭 시 오버레이 표시
        holder.imageView.setOnClickListener(v -> {
            holder.overlay.setVisibility(View.VISIBLE);
            holder.descriptionText.setText(item.getName());
        });

        // 오버레이 클릭 시 숨김
        holder.overlay.setOnClickListener(v -> {
            holder.overlay.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        FrameLayout overlay;
        TextView descriptionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            overlay = itemView.findViewById(R.id.overlay);
            descriptionText = itemView.findViewById(R.id.descriptionText);
        }
    }
}
