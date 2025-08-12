package com.example.mostin.utils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;

/* loaded from: classes6.dex */
public class NonScrollingGridLayoutManager extends GridLayoutManager {
    public NonScrollingGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }
}
