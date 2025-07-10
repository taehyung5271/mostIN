package com.example.mostin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntroduceGoodsFragment extends Fragment {

    private RecyclerView goodsRecyclerView;
    private GoodsAdapter adapter;

    public IntroduceGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_goods, container, false);

        goodsRecyclerView = view.findViewById(R.id.goods_recyclerView);
        

        // GoodsItem 리스트 생성
        List<GoodsItem> itemList = new ArrayList<>();
        itemList.add(new GoodsItem(R.drawable.cafri, "카프리"));
        itemList.add(new GoodsItem(R.drawable.cass, "카스"));
        itemList.add(new GoodsItem(R.drawable.casslemon, "카스 레몬"));
        itemList.add(new GoodsItem(R.drawable.casslemonnab, "카스레몬\n논알콜"));
        itemList.add(new GoodsItem(R.drawable.cassnab, "카스 논알콜"));
        itemList.add(new GoodsItem(R.drawable.casslight, "카스라이트"));
        itemList.add(new GoodsItem(R.drawable.duckduckgoose, "덕덕구스"));
        itemList.add(new GoodsItem(R.drawable.budweiser, "버드와이저"));
        itemList.add(new GoodsItem(R.drawable.hanmac, "한맥"));
        itemList.add(new GoodsItem(R.drawable.hoegaardeen, "호가든"));
        itemList.add(new GoodsItem(R.drawable.gooseipa, "구스IPA"));

        // 이미지 리스트를 랜덤으로 섞음
        Collections.shuffle(itemList);

        // 어댑터 설정
        adapter = new GoodsAdapter(itemList);
        // 어댑터 연결 확인
        goodsRecyclerView.setAdapter(adapter);
        if (goodsRecyclerView.getAdapter() == null) {
            Log.e("RecyclerViewDebug", "Adapter is not set. Check your adapter assignment.");
        } else {
            Log.d("RecyclerViewDebug", "Adapter is set successfully.");
        }

        // LayoutManager 확인
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        goodsRecyclerView.setLayoutManager(layoutManager);
        return view;
    }
}
