package com.example.mostin.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.adapters.OrderingAdapter;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.utils.SQLiteHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderingFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText orderSummaryEdit;
    private Button copyAllButton;
    private SQLiteHelper dbHelper;
    private String employeeId;
    private String employeeName;
    private OrderingAdapter adapter;

    public OrderingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);

        // 초기화
        recyclerView = view.findViewById(R.id.recycler_goods);
        orderSummaryEdit = view.findViewById(R.id.edit_order_summary);
        copyAllButton = view.findViewById(R.id.btn_copy_all);
        dbHelper = new SQLiteHelper(requireContext());

        // 사용자 정보 가져오기
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
        }

        // RecyclerView 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadGoodsData();

        // 전체 복사 버튼 설정
        copyAllButton.setOnClickListener(v -> {
            String orderText = orderSummaryEdit.getText().toString();
            if (!orderText.isEmpty()) {
                // 클립보드에 복사
                ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("order_summary", orderText);
                clipboard.setPrimaryClip(clip);

                // DB에 저장
                saveOrderToDB(orderText);

                Toast.makeText(requireContext(), "주문 내용이 복사되고 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadGoodsData() {
        List<GoodsModel> goodsList = dbHelper.getAllGoods();
        adapter = new OrderingAdapter(goodsList, (barcode, boxCount) -> {
            String orderLine = String.format(Locale.getDefault(), "%s %d 박스", barcode, boxCount);
            String currentText = orderSummaryEdit.getText().toString();
            if (!currentText.isEmpty()) {
                orderLine = currentText + "\n" + orderLine;
            }
            orderSummaryEdit.setText(orderLine);
        });
        recyclerView.setAdapter(adapter);
    }

    private void saveOrderToDB(String orderText) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String[] orderLines = orderText.split("\n");
        
        Log.d("OrderSave", "Saving orders for " + employeeName + " on " + currentDate);
        Log.d("OrderSave", "Total orders to save: " + orderLines.length);
        
        // 먼저 해당 날짜의 이전 주문을 삭제
        dbHelper.deleteOrdersByDate(currentDate, employeeId, employeeName);
        
        // 그 다음 모든 주문을 저장
        for (String line : orderLines) {
            String[] parts = line.split(" ");
            if (parts.length >= 2) {
                String barcode = parts[0];
                int boxCount = Integer.parseInt(parts[1]);
                
                Log.d("OrderSave", "Saving order: Barcode=" + barcode + ", BoxCount=" + boxCount);
                dbHelper.saveOrder(currentDate, employeeId, employeeName, boxCount, barcode);
            }
        }
    }
} 