package com.example.mostin.utils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;

public class NonScrollingGridLayoutManager extends GridLayoutManager {
    public NonScrollingGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
