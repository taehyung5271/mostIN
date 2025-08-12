package com.example.mostin.adapters;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.GoodsModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes7.dex */
public class AdminGoodsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<GoodsModel> goodsList;
    private boolean isEditMode = false;
    private boolean showCheckboxes = false;
    private Set<String> selectedItems = new HashSet();

    public AdminGoodsAdapter(List<GoodsModel> goodsList) {
        this.goodsList = goodsList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GoodsModel goods = this.goodsList.get(position);
        holder.barcodeEdit.removeTextChangedListener(holder.barcodeWatcher);
        holder.nameEdit.removeTextChangedListener(holder.nameWatcher);
        holder.barcodeEdit.setText(goods.getBarcode());
        holder.nameEdit.setText(goods.getName());
        holder.barcodeEdit.setEnabled(this.isEditMode);
        holder.nameEdit.setEnabled(this.isEditMode);
        holder.barcodeWatcher = new TextWatcher() { // from class: com.example.mostin.adapters.AdminGoodsAdapter.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                goods.setBarcode(s.toString());
            }
        };
        holder.nameWatcher = new TextWatcher() { // from class: com.example.mostin.adapters.AdminGoodsAdapter.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                goods.setName(s.toString());
            }
        };
        holder.barcodeEdit.addTextChangedListener(holder.barcodeWatcher);
        holder.nameEdit.addTextChangedListener(holder.nameWatcher);
        holder.checkbox.setVisibility(this.showCheckboxes ? 0 : 8);
        holder.checkbox.setChecked(this.selectedItems.contains(goods.getBarcode()));
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.example.mostin.adapters.AdminGoodsAdapter$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m102xf0d9ef27(goods, compoundButton, z);
            }
        });
    }

    /* renamed from: lambda$onBindViewHolder$0$com-example-mostin-adapters-AdminGoodsAdapter, reason: not valid java name */
    /* synthetic */ void m102xf0d9ef27(GoodsModel goods, CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.selectedItems.add(goods.getBarcode());
        } else {
            this.selectedItems.remove(goods.getBarcode());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.goodsList.size();
    }

    public void updateData(final List<GoodsModel> newGoodsList) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.example.mostin.adapters.AdminGoodsAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m103xe9d6f4ef(newGoodsList);
            }
        });
    }

    /* renamed from: lambda$updateData$2$com-example-mostin-adapters-AdminGoodsAdapter, reason: not valid java name */
    /* synthetic */ void m103xe9d6f4ef(List newGoodsList) {
        this.goodsList = new ArrayList(newGoodsList);
        Collections.sort(this.goodsList, new Comparator() { // from class: com.example.mostin.adapters.AdminGoodsAdapter$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((GoodsModel) obj).getName().compareTo(((GoodsModel) obj2).getName());
            }
        });
        notifyDataSetChanged();
    }

    public void addNewRow() {
        if (this.showCheckboxes) {
            this.showCheckboxes = false;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.example.mostin.adapters.AdminGoodsAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m101lambda$addNewRow$3$comexamplemostinadaptersAdminGoodsAdapter();
            }
        });
    }

    /* renamed from: lambda$addNewRow$3$com-example-mostin-adapters-AdminGoodsAdapter, reason: not valid java name */
    /* synthetic */ void m101lambda$addNewRow$3$comexamplemostinadaptersAdminGoodsAdapter() {
        this.goodsList.add(new GoodsModel("", ""));
        notifyItemInserted(this.goodsList.size() - 1);
    }

    public void setEditMode(boolean editMode) {
        this.isEditMode = editMode;
        if (this.showCheckboxes) {
            this.showCheckboxes = false;
        }
        notifyDataSetChanged();
    }

    public void showCheckboxes(boolean show) {
        this.showCheckboxes = show;
        if (!show) {
            this.selectedItems.clear();
        }
        notifyDataSetChanged();
    }

    public GoodsModel getNewItem() {
        if (this.goodsList.isEmpty()) {
            return null;
        }
        GoodsModel lastItem = this.goodsList.get(this.goodsList.size() - 1);
        if (lastItem.getBarcode().isEmpty() || lastItem.getName().isEmpty()) {
            return null;
        }
        return lastItem;
    }

    public List<GoodsModel> getUpdatedItems() {
        return new ArrayList(this.goodsList);
    }

    public List<String> getSelectedItems() {
        return new ArrayList(this.selectedItems);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText barcodeEdit;
        TextWatcher barcodeWatcher;
        CheckBox checkbox;
        EditText nameEdit;
        TextWatcher nameWatcher;

        ViewHolder(View itemView) {
            super(itemView);
            this.barcodeEdit = (EditText) itemView.findViewById(R.id.barcodeEdit);
            this.nameEdit = (EditText) itemView.findViewById(R.id.nameEdit);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
