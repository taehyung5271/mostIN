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
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 더미 데이터 생성
        List<Integer> itemList = new ArrayList<>();
        itemList.add(R.drawable.cafri);
        itemList.add(R.drawable.cass);
        itemList.add(R.drawable.casslemon);
        itemList.add(R.drawable.casslemonnab);
        itemList.add(R.drawable.cassnab);
        itemList.add(R.drawable.casslight);
        itemList.add(R.drawable.duckduckgoose);
        itemList.add(R.drawable.budweiser);
        itemList.add(R.drawable.hanmac);
        itemList.add(R.drawable.hoegaardeen);
        itemList.add(R.drawable.gooseipa);


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
