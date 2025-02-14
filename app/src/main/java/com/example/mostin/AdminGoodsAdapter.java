package com.example.mostin;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.GoodsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminGoodsAdapter extends RecyclerView.Adapter<AdminGoodsAdapter.ViewHolder> {
    private List<GoodsModel> goodsList;
    private boolean isEditMode = false;
    private boolean showCheckboxes = false;
    private Set<String> selectedItems = new HashSet<>();

    public AdminGoodsAdapter(List<GoodsModel> goodsList) {
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoodsModel goods = goodsList.get(position);
        
        holder.barcodeEdit.removeTextChangedListener(holder.barcodeWatcher);
        holder.nameEdit.removeTextChangedListener(holder.nameWatcher);
        
        holder.barcodeEdit.setText(goods.getBarcode());
        holder.nameEdit.setText(goods.getName());
        
        holder.barcodeEdit.setEnabled(isEditMode);
        holder.nameEdit.setEnabled(isEditMode);
        
        holder.barcodeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                goods.setBarcode(s.toString());
            }
        };
        
        holder.nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                goods.setName(s.toString());
            }
        };
        
        holder.barcodeEdit.addTextChangedListener(holder.barcodeWatcher);
        holder.nameEdit.addTextChangedListener(holder.nameWatcher);
        
        holder.checkbox.setVisibility(showCheckboxes ? View.VISIBLE : View.GONE);
        holder.checkbox.setChecked(selectedItems.contains(goods.getBarcode()));
        
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(goods.getBarcode());
            } else {
                selectedItems.remove(goods.getBarcode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public void updateData(List<GoodsModel> newGoodsList) {
        new Handler(Looper.getMainLooper()).post(() -> {
            this.goodsList = new ArrayList<>(newGoodsList);
            Collections.sort(goodsList, (g1, g2) -> g1.getName().compareTo(g2.getName()));
            notifyDataSetChanged();
        });
    }

    public void addNewRow() {
        if (showCheckboxes) {
            showCheckboxes = false;
        }
        new Handler(Looper.getMainLooper()).post(() -> {
            goodsList.add(new GoodsModel("", ""));
            notifyItemInserted(goodsList.size() - 1);
        });
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        if (showCheckboxes) {
            showCheckboxes = false;
        }
        notifyDataSetChanged();
    }

    public void showCheckboxes(boolean show) {
        showCheckboxes = show;
        if (!show) {
            selectedItems.clear();
        }
        notifyDataSetChanged();
    }

    public GoodsModel getNewItem() {
        if (goodsList.isEmpty()) return null;
        GoodsModel lastItem = goodsList.get(goodsList.size() - 1);
        if (lastItem.getBarcode().isEmpty() || lastItem.getName().isEmpty()) {
            return null;
        }
        return lastItem;
    }

    public List<GoodsModel> getUpdatedItems() {
        return new ArrayList<>(goodsList);
    }

    public List<String> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText barcodeEdit, nameEdit;
        CheckBox checkbox;
        TextWatcher barcodeWatcher, nameWatcher;

        ViewHolder(View itemView) {
            super(itemView);
            barcodeEdit = itemView.findViewById(R.id.barcodeEdit);
            nameEdit = itemView.findViewById(R.id.nameEdit);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
} 