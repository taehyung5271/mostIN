package com.example.mostin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.AdminGoodsAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.utils.SQLiteHelper;

import java.util.List;

public class AdminGoodsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminGoodsAdapter adapter;
    private SQLiteHelper dbHelper;
    private Button insertBtn, editBtn, deleteBtn;
    private Button actionBtn;
    private List<GoodsModel> goodsList;
    private int currentMode = 0; // 0: 일반모드, 1: 삽입모드, 2: 수정모드, 3: 삭제모드

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_goods, container, false);
        
        dbHelper = new SQLiteHelper(getContext());
        initializeViews(view);
        setupRecyclerView();
        setupButtons();
        
        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.adminGoodsRecyclerView);
        insertBtn = view.findViewById(R.id.insertBtn);
        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        actionBtn = view.findViewById(R.id.actionBtn);
        actionBtn.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        goodsList = dbHelper.getAllGoods();
        adapter = new AdminGoodsAdapter(goodsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupButtons() {
        insertBtn.setOnClickListener(v -> handleInsertMode());
        editBtn.setOnClickListener(v -> handleEditMode());
        deleteBtn.setOnClickListener(v -> handleDeleteMode());
        actionBtn.setOnClickListener(v -> handleActionButton());
    }

    private void handleInsertMode() {
        resetMode();
        currentMode = 1;
        actionBtn.setText("삽입완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.setEditMode(true);
        adapter.addNewRow();
        recyclerView.smoothScrollToPosition(goodsList.size());
    }

    private void handleEditMode() {
        resetMode();
        currentMode = 2;
        actionBtn.setText("수정완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.setEditMode(true);
    }

    private void handleDeleteMode() {
        resetMode();
        currentMode = 3;
        actionBtn.setText("삭제완료");
        actionBtn.setVisibility(View.VISIBLE);
        adapter.showCheckboxes(true);
    }

    private void handleActionButton() {
        switch (currentMode) {
            case 1: // 삽입모드
                GoodsModel newItem = adapter.getNewItem();
                if (newItem != null) {
                    dbHelper.insertGoods(newItem);
                    goodsList = dbHelper.getAllGoods();
                    adapter.updateData(goodsList);
                }
                break;
            case 2: // 수정모드
                List<GoodsModel> updatedItems = adapter.getUpdatedItems();
                for (GoodsModel item : updatedItems) {
                    dbHelper.updateGoods(item);
                }
                goodsList = dbHelper.getAllGoods();
                adapter.updateData(goodsList);
                break;
            case 3: // 삭제모드
                List<String> selectedBarcodes = adapter.getSelectedItems();
                for (String barcode : selectedBarcodes) {
                    dbHelper.deleteGoods(barcode);
                }
                goodsList = dbHelper.getAllGoods();
                adapter.updateData(goodsList);
                break;
        }
        resetMode();
    }

    private void resetMode() {
        currentMode = 0;
        actionBtn.setVisibility(View.GONE);
        adapter.setEditMode(false);
        adapter.showCheckboxes(false);
    }
} 