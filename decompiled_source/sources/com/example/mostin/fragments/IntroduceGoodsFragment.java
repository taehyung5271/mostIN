package com.example.mostin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.mostin.R;
import com.example.mostin.adapters.GoodsAdapter;
import com.example.mostin.models.GoodsItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class IntroduceGoodsFragment extends Fragment {
    private GoodsAdapter adapter;
    private RecyclerView goodsRecyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_goods, container, false);
        this.goodsRecyclerView = (RecyclerView) view.findViewById(R.id.goods_recyclerView);
        this.goodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<GoodsItem> itemList = new ArrayList<>();
        itemList.add(new GoodsItem(R.drawable.cafri, "카프리"));
        itemList.add(new GoodsItem(R.drawable.cass, "카스"));
        itemList.add(new GoodsItem(R.drawable.casslemon, "카스 레몬"));
        itemList.add(new GoodsItem(R.drawable.casslemonnab, "카스레몬 논알콜"));
        itemList.add(new GoodsItem(R.drawable.cassnab, "카스 논알콜"));
        itemList.add(new GoodsItem(R.drawable.casslight, "카스라이트"));
        itemList.add(new GoodsItem(R.drawable.duckduckgoose, "덕덕구스"));
        itemList.add(new GoodsItem(R.drawable.budweiser, "버드와이저"));
        itemList.add(new GoodsItem(R.drawable.hanmac, "한맥"));
        itemList.add(new GoodsItem(R.drawable.hoegaardeen, "호가든"));
        itemList.add(new GoodsItem(R.drawable.gooseipa, "구스IPA"));
        Collections.shuffle(itemList);
        this.adapter = new GoodsAdapter(itemList);
        this.goodsRecyclerView.setAdapter(this.adapter);
        if (this.goodsRecyclerView.getAdapter() == null) {
            Log.e("RecyclerViewDebug", "Adapter is not set. Check your adapter assignment.");
        } else {
            Log.d("RecyclerViewDebug", "Adapter is set successfully.");
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        this.goodsRecyclerView.setLayoutManager(layoutManager);
        return view;
    }
}
