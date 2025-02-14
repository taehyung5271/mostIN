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
    private final List<Integer> imageList; // 이미지 리소스 ID 리스트
    public interface OnItemClickListener {
        void onItemClick(int position, String description);
    }
    public GoodsAdapter(List<Integer> itemList) {
        this.imageList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageRes = imageList.get(position);
        holder.imageView.setImageResource(imageRes);
        String goodsName[] = new String[]{"카프리","카스","카스 레몬","카스레몬\n논알콜","카스 논알콜","카스라이트","덕덕구스","버드와이저","한맥","호가든","구스IPA"};
        // 이미지 클릭 시 오버레이 표시
        holder.imageView.setOnClickListener(v -> {
            holder.overlay.setVisibility(View.VISIBLE);
            holder.descriptionText.setText(goodsName[position]);
        });

        // 오버레이 클릭 시 숨김
        holder.overlay.setOnClickListener(v -> {
            holder.overlay.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
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
