package com.example.mostin.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.OrderingAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.models.Ordering;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class OrderingFragment extends Fragment {
    private OrderingAdapter adapter;
    private Button copyAllButton;
    private String employeeId;
    private String employeeName;
    private List<GoodsModel> goodsList;
    private EditText orderSummaryEdit;
    private RecyclerView recyclerView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_goods);
        this.orderSummaryEdit = (EditText) view.findViewById(R.id.edit_order_summary);
        this.copyAllButton = (Button) view.findViewById(R.id.btn_copy_all);
        Bundle args = getArguments();
        if (args != null) {
            this.employeeId = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID);
            this.employeeName = args.getString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME);
        }
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadGoodsData();
        this.copyAllButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.OrderingFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m126x8b8b1789(view2);
            }
        });
        return view;
    }

    /* renamed from: lambda$onCreateView$0$com-example-mostin-fragments-OrderingFragment, reason: not valid java name */
    /* synthetic */ void m126x8b8b1789(View v) {
        String orderText = this.orderSummaryEdit.getText().toString();
        if (!orderText.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService("clipboard");
            ClipData clip = ClipData.newPlainText("order_summary", orderText);
            clipboard.setPrimaryClip(clip);
            saveOrderToDB(orderText);
            Toast.makeText(requireContext(), "주문 내용이 복사되고 저장되었습니다.", 0).show();
        }
    }

    private void loadGoodsData() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<GoodsModel>> call = apiService.getAllGoods();
        call.enqueue(new AnonymousClass1());
    }

    /* renamed from: com.example.mostin.fragments.OrderingFragment$1, reason: invalid class name */
    class AnonymousClass1 implements Callback<List<GoodsModel>> {
        AnonymousClass1() {
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<List<GoodsModel>> call, Response<List<GoodsModel>> response) {
            if (response.isSuccessful() && response.body() != null) {
                OrderingFragment.this.goodsList = response.body();
                OrderingFragment.this.adapter = new OrderingAdapter(OrderingFragment.this.goodsList, new OrderingAdapter.OnCopyClickListener() { // from class: com.example.mostin.fragments.OrderingFragment$1$$ExternalSyntheticLambda0
                    @Override // com.example.mostin.adapters.OrderingAdapter.OnCopyClickListener
                    public final void onCopyClick(String str, int i) {
                        this.f$0.m127x5485af56(str, i);
                    }
                });
                OrderingFragment.this.recyclerView.setAdapter(OrderingFragment.this.adapter);
                return;
            }
            Toast.makeText(OrderingFragment.this.getContext(), "상품 목록을 불러오는 데 실패했습니다.", 0).show();
        }

        /* renamed from: lambda$onResponse$0$com-example-mostin-fragments-OrderingFragment$1, reason: not valid java name */
        /* synthetic */ void m127x5485af56(String barcode, int boxCount) {
            String orderLine = String.format(Locale.getDefault(), "%s %d 박스", barcode, Integer.valueOf(boxCount));
            String currentText = OrderingFragment.this.orderSummaryEdit.getText().toString();
            if (!currentText.isEmpty()) {
                orderLine = currentText + "\n" + orderLine;
            }
            OrderingFragment.this.orderSummaryEdit.setText(orderLine);
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<List<GoodsModel>> call, Throwable t) {
            Log.e("OrderingFragment", "Error loading goods data", t);
            Toast.makeText(OrderingFragment.this.getContext(), "상품 목록 로딩 중 오류가 발생했습니다.", 0).show();
        }
    }

    private void saveOrderToDB(String orderText) {
        final String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        final String[] orderLines = orderText.split("\n");
        Log.d("OrderSave", "Saving orders for " + this.employeeName + " on " + currentDate);
        Log.d("OrderSave", "Total orders to save: " + orderLines.length);
        final ApiService apiService = ApiClient.getApiService();
        Call<Void> deleteCall = apiService.deleteOrdersByDate(this.employeeId, currentDate);
        deleteCall.enqueue(new Callback<Void>() { // from class: com.example.mostin.fragments.OrderingFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<Void> call, Response<Void> response) throws NumberFormatException {
                String goodsName;
                if (response.isSuccessful()) {
                    Log.d("OrderSave", "Previous orders deleted successfully.");
                    for (String line : orderLines) {
                        String[] parts = line.split(" ");
                        if (parts.length >= 2) {
                            final String barcode = parts[0];
                            int boxCount = Integer.parseInt(parts[1]);
                            if (OrderingFragment.this.goodsList != null) {
                                for (GoodsModel goods : OrderingFragment.this.goodsList) {
                                    if (goods.getBarcode().equals(barcode)) {
                                        String goodsName2 = goods.getName();
                                        goodsName = goodsName2;
                                        break;
                                    }
                                }
                                goodsName = "";
                                Log.d("OrderSave", "Saving order: Barcode=" + barcode + ", BoxCount=" + boxCount + ", GoodsName=" + goodsName);
                                Ordering newOrder = new Ordering(currentDate, OrderingFragment.this.employeeId, barcode, OrderingFragment.this.employeeName, Integer.valueOf(boxCount), goodsName);
                                Call<Ordering> createCall = apiService.createOrder(newOrder);
                                createCall.enqueue(new Callback<Ordering>() { // from class: com.example.mostin.fragments.OrderingFragment.2.1
                                    @Override // retrofit2.Callback
                                    public void onResponse(Call<Ordering> call2, Response<Ordering> response2) {
                                        if (response2.isSuccessful()) {
                                            Log.d("OrderSave", "Order saved successfully: " + barcode);
                                        } else {
                                            Log.e("OrderSave", "Failed to save order: " + barcode + ", Code: " + response2.code());
                                        }
                                    }

                                    @Override // retrofit2.Callback
                                    public void onFailure(Call<Ordering> call2, Throwable t) {
                                        Log.e("OrderSave", "Error saving order: " + barcode, t);
                                    }
                                });
                            } else {
                                goodsName = "";
                                Log.d("OrderSave", "Saving order: Barcode=" + barcode + ", BoxCount=" + boxCount + ", GoodsName=" + goodsName);
                                Ordering newOrder2 = new Ordering(currentDate, OrderingFragment.this.employeeId, barcode, OrderingFragment.this.employeeName, Integer.valueOf(boxCount), goodsName);
                                Call<Ordering> createCall2 = apiService.createOrder(newOrder2);
                                createCall2.enqueue(new Callback<Ordering>() { // from class: com.example.mostin.fragments.OrderingFragment.2.1
                                    @Override // retrofit2.Callback
                                    public void onResponse(Call<Ordering> call2, Response<Ordering> response2) {
                                        if (response2.isSuccessful()) {
                                            Log.d("OrderSave", "Order saved successfully: " + barcode);
                                        } else {
                                            Log.e("OrderSave", "Failed to save order: " + barcode + ", Code: " + response2.code());
                                        }
                                    }

                                    @Override // retrofit2.Callback
                                    public void onFailure(Call<Ordering> call2, Throwable t) {
                                        Log.e("OrderSave", "Error saving order: " + barcode, t);
                                    }
                                });
                            }
                        }
                    }
                    return;
                }
                Log.e("OrderSave", "Failed to delete previous orders, Code: " + response.code());
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("OrderSave", "Error deleting previous orders", t);
            }
        });
    }
}
