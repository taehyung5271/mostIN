package com.example.mostin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.AdminGoodsAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.GoodsModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class AdminGoodsFragment extends Fragment {
    private Button actionBtn;
    private AdminGoodsAdapter adapter;
    private int currentMode = 0;
    private Button deleteBtn;
    private Button editBtn;
    private List<GoodsModel> goodsList;
    private Button insertBtn;
    private RecyclerView recyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_goods, container, false);
        initializeViews(view);
        setupRecyclerView();
        setupButtons();
        return view;
    }

    private void initializeViews(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.adminGoodsRecyclerView);
        this.insertBtn = (Button) view.findViewById(R.id.insertBtn);
        this.editBtn = (Button) view.findViewById(R.id.editBtn);
        this.deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        this.actionBtn = (Button) view.findViewById(R.id.actionBtn);
        this.actionBtn.setVisibility(8);
    }

    private void setupRecyclerView() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllGoods().enqueue(new Callback<List<GoodsModel>>() { // from class: com.example.mostin.fragments.AdminGoodsFragment.1
            @Override // retrofit2.Callback
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminGoodsFragment.this.goodsList = response.body();
                    AdminGoodsFragment.this.adapter = new AdminGoodsAdapter(AdminGoodsFragment.this.goodsList);
                    AdminGoodsFragment.this.recyclerView.setLayoutManager(new LinearLayoutManager(AdminGoodsFragment.this.getContext()));
                    AdminGoodsFragment.this.recyclerView.setAdapter(AdminGoodsFragment.this.adapter);
                    return;
                }
                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품 목록을 불러오는데 실패했습니다.", 0).show();
                Log.e("AdminGoodsFragment", "Failed to fetch goods: " + response.message());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Toast.makeText(AdminGoodsFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
            }
        });
    }

    private void setupButtons() {
        this.insertBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AdminGoodsFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m108xe4330b00(view);
            }
        });
        this.editBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AdminGoodsFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m109xe3bca501(view);
            }
        });
        this.deleteBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AdminGoodsFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m110xe3463f02(view);
            }
        });
        this.actionBtn.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.AdminGoodsFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m111xe2cfd903(view);
            }
        });
    }

    /* renamed from: lambda$setupButtons$0$com-example-mostin-fragments-AdminGoodsFragment, reason: not valid java name */
    /* synthetic */ void m108xe4330b00(View v) {
        handleInsertMode();
    }

    /* renamed from: lambda$setupButtons$1$com-example-mostin-fragments-AdminGoodsFragment, reason: not valid java name */
    /* synthetic */ void m109xe3bca501(View v) {
        handleEditMode();
    }

    /* renamed from: lambda$setupButtons$2$com-example-mostin-fragments-AdminGoodsFragment, reason: not valid java name */
    /* synthetic */ void m110xe3463f02(View v) {
        handleDeleteMode();
    }

    /* renamed from: lambda$setupButtons$3$com-example-mostin-fragments-AdminGoodsFragment, reason: not valid java name */
    /* synthetic */ void m111xe2cfd903(View v) {
        handleActionButton();
    }

    private void handleInsertMode() {
        resetMode();
        this.currentMode = 1;
        this.actionBtn.setText("삽입완료");
        this.actionBtn.setVisibility(0);
        this.adapter.setEditMode(true);
        this.adapter.addNewRow();
        this.recyclerView.smoothScrollToPosition(this.goodsList.size());
    }

    private void handleEditMode() {
        resetMode();
        this.currentMode = 2;
        this.actionBtn.setText("수정완료");
        this.actionBtn.setVisibility(0);
        this.adapter.setEditMode(true);
    }

    private void handleDeleteMode() {
        resetMode();
        this.currentMode = 3;
        this.actionBtn.setText("삭제완료");
        this.actionBtn.setVisibility(0);
        this.adapter.showCheckboxes(true);
    }

    private void handleActionButton() {
        ApiService apiService = ApiClient.getApiService();
        switch (this.currentMode) {
            case 1:
                GoodsModel newItem = this.adapter.getNewItem();
                if (newItem != null) {
                    apiService.createGoods(newItem).enqueue(new Callback<GoodsModel>() { // from class: com.example.mostin.fragments.AdminGoodsFragment.2
                        @Override // retrofit2.Callback
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품이 성공적으로 추가되었습니다.", 0).show();
                                AdminGoodsFragment.this.refreshGoodsList();
                            } else {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품 추가 실패: " + response.message(), 0).show();
                                Log.e("AdminGoodsFragment", "Failed to create goods: " + response.message());
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(AdminGoodsFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                    break;
                }
                break;
            case 2:
                List<GoodsModel> updatedItems = this.adapter.getUpdatedItems();
                for (GoodsModel item : updatedItems) {
                    apiService.updateGoods(item.getBarcode(), item).enqueue(new Callback<GoodsModel>() { // from class: com.example.mostin.fragments.AdminGoodsFragment.3
                        @Override // retrofit2.Callback
                        public void onResponse(Call<GoodsModel> call, Response<GoodsModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품이 성공적으로 수정되었습니다.", 0).show();
                                AdminGoodsFragment.this.refreshGoodsList();
                            } else {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품 수정 실패: " + response.message(), 0).show();
                                Log.e("AdminGoodsFragment", "Failed to update goods: " + response.message());
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<GoodsModel> call, Throwable t) {
                            Toast.makeText(AdminGoodsFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                }
                break;
            case 3:
                List<String> selectedBarcodes = this.adapter.getSelectedItems();
                for (String barcode : selectedBarcodes) {
                    apiService.deleteGoods(barcode).enqueue(new Callback<Void>() { // from class: com.example.mostin.fragments.AdminGoodsFragment.4
                        @Override // retrofit2.Callback
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품이 성공적으로 삭제되었습니다.", 0).show();
                                AdminGoodsFragment.this.refreshGoodsList();
                            } else {
                                Toast.makeText(AdminGoodsFragment.this.getContext(), "상품 삭제 실패: " + response.message(), 0).show();
                                Log.e("AdminGoodsFragment", "Failed to delete goods: " + response.message());
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(AdminGoodsFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                            Log.e("AdminGoodsFragment", "API call failed: " + t.getMessage(), t);
                        }
                    });
                }
                break;
        }
        resetMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshGoodsList() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllGoods().enqueue(new Callback<List<GoodsModel>>() { // from class: com.example.mostin.fragments.AdminGoodsFragment.5
            @Override // retrofit2.Callback
            public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminGoodsFragment.this.goodsList = response.body();
                    AdminGoodsFragment.this.adapter.updateData(AdminGoodsFragment.this.goodsList);
                } else {
                    Toast.makeText(AdminGoodsFragment.this.getContext(), "상품 목록 새로고침 실패: " + response.message(), 0).show();
                    Log.e("AdminGoodsFragment", "Failed to refresh goods list: " + response.message());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
                Toast.makeText(AdminGoodsFragment.this.getContext(), "네트워크 오류: " + t.getMessage(), 0).show();
                Log.e("AdminGoodsFragment", "API call failed during refresh: " + t.getMessage(), t);
            }
        });
    }

    private void resetMode() {
        this.currentMode = 0;
        this.actionBtn.setVisibility(8);
        this.adapter.setEditMode(false);
        this.adapter.showCheckboxes(false);
    }
}
