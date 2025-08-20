package com.example.mostin.utils;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpacing;
    private final int horizontalSpacing;

    public SpaceItemDecoration(int verticalSpacing, int horizontalSpacing) {
        this.verticalSpacing = verticalSpacing;
        this.horizontalSpacing = horizontalSpacing;
    }

    public SpaceItemDecoration(int spacing) {
        this(spacing, spacing);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, 
                              @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        
        // 첫 번째 아이템이 아니면 상단 여백 추가
        if (position != 0) {
            outRect.top = verticalSpacing;
        }
        
        // 좌우 여백 추가
        outRect.left = horizontalSpacing;
        outRect.right = horizontalSpacing;
        
        // 하단 여백 추가
        outRect.bottom = verticalSpacing / 2;
    }
}