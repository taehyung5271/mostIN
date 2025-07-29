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
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.Toast;

public class AdminGoodsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminGoodsAdapter adapter;
    private Button insertBtn, editBtn, deleteBtn;
    private Button actionBtn;
    private List<GoodsModel> goodsList;
    private int currentMode = 0; // 0: 일반모드, 1: 삽입모드, 2: 수정모드, 3: 삭제모드

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_goods, container, false);
        
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
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllGoods().enqueue(new Callback<List<GoodsModel>>() {
            @Override
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    goodsList = response.body();
                    adapter = new AdminGoodsAdapter(goodsList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "상품 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("AdminGoodsFragment", "Failed to fetch goods: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
            }
        });
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
        ApiService apiService = ApiClient.getApiService();

        switch (currentMode) {
            case 1: // 삽입모드
                GoodsModel newItem = adapter.getNewItem();
                if (newItem != null) {
                    apiService.createGoods(newItem).enqueue(new Callback<GoodsModel>() {
                        @Override
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "상품이 성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                refreshGoodsList();
                            } else {
                                Toast.makeText(getContext(), "상품 추가 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e("AdminGoodsFragment", "Failed to create goods: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                }
                break;
            case 2: // 수정모드
                List<GoodsModel> updatedItems = adapter.getUpdatedItems();
                for (GoodsModel item : updatedItems) {
                    apiService.updateGoods(item.getBarcode(), item).enqueue(new Callback<GoodsModel>() {
                        @Override
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "상품이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                refreshGoodsList();
                            } else {
                                Toast.makeText(getContext(), "상품 수정 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e("AdminGoodsFragment", "Failed to update goods: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                }
                break;
            case 3: // 삭제모드
                List<String> selectedBarcodes = adapter.getSelectedItems();
                for (String barcode : selectedBarcodes) {
                    apiService.deleteGoods(barcode).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "상품이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                refreshGoodsList();
                            } else {
                                Toast.makeText(getContext(), "상품 삭제 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e("AdminGoodsFragment", "Failed to delete goods: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                }
                break;
        }
        resetMode();
    }

    private void refreshGoodsList() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllGoods().enqueue(new Callback<List<GoodsModel>>() {
            @Override
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    goodsList = response.body();
                    adapter.updateData(goodsList);
                } else {
                    Toast.makeText(getContext(), "상품 목록 새로고침 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("AdminGoodsFragment", "Failed to refresh goods list: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AdminGoodsFragment", "API call failed during refresh: " + t.getMessage(), t);
            }
        });
    }

    private void resetMode() {
        currentMode = 0;
        actionBtn.setVisibility(View.GONE);
        adapter.setEditMode(false);
        adapter.showCheckboxes(false);
    }
} 