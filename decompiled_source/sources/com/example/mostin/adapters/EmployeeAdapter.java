package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.EmployeeModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes7.dex */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<EmployeeModel> employees = new ArrayList();
    private List<EmployeeModel> originalEmployees = new ArrayList();

    public interface OnItemClickListener {
        void onItemClick(EmployeeModel employeeModel, int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EmployeeAdapter(Context context) {
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(EmployeeViewHolder holder, final int position) {
        final EmployeeModel employee = this.employees.get(position);
        holder.textEmployeeName.setText(employee.getEmployeeName());
        holder.textEmployeeId.setText("ID: " + employee.getEmployeeId());
        holder.textEmployeeType.setText(employee.getEmployeeType());
        holder.textWorkPlace.setText("근무지: " + employee.getWorkPlaceName());
        holder.textPhone.setText(employee.getPhoneNum());
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.EmployeeAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m105x7a568fa0(employee, position, view);
            }
        });
    }

    /* renamed from: lambda$onBindViewHolder$0$com-example-mostin-adapters-EmployeeAdapter, reason: not valid java name */
    /* synthetic */ void m105x7a568fa0(EmployeeModel employee, int position, View v) {
        if (this.listener != null) {
            this.listener.onItemClick(employee, position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.employees.size();
    }

    public void updateDisplayedEmployees(List<EmployeeModel> newEmployees) {
        this.employees = newEmployees;
        notifyDataSetChanged();
    }

    public void setOriginalEmployees(List<EmployeeModel> originalEmployees) {
        this.originalEmployees = new ArrayList(originalEmployees);
        this.employees = new ArrayList(originalEmployees);
        notifyDataSetChanged();
    }

    public List<EmployeeModel> getAllEmployees() {
        return this.originalEmployees;
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textEmployeeId;
        TextView textEmployeeName;
        TextView textEmployeeType;
        TextView textPhone;
        TextView textWorkPlace;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            this.textEmployeeName = (TextView) itemView.findViewById(R.id.text_employee_name);
            this.textEmployeeId = (TextView) itemView.findViewById(R.id.text_employee_id);
            this.textEmployeeType = (TextView) itemView.findViewById(R.id.text_employee_type);
            this.textWorkPlace = (TextView) itemView.findViewById(R.id.text_work_place);
            this.textPhone = (TextView) itemView.findViewById(R.id.text_phone);
        }
    }
}
