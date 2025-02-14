package com.example.mostin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<EmployeeModel> employees;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EmployeeModel employee, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EmployeeAdapter(Context context) {
        this.context = context;
        this.employees = new ArrayList<>();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeModel employee = employees.get(position);
        holder.textEmployeeName.setText(employee.getEmployeeName());
        holder.textEmployeeId.setText("ID: " + employee.getEmployeeId());
        holder.textEmployeeType.setText(employee.getEmployeeType());
        holder.textWorkPlace.setText("근무지: " + employee.getWorkPlaceName());
        holder.textPhone.setText(employee.getPhoneNum());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(employee, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setEmployees(List<EmployeeModel> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textEmployeeName;
        TextView textEmployeeId;
        TextView textEmployeeType;
        TextView textWorkPlace;
        TextView textPhone;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textEmployeeName = itemView.findViewById(R.id.text_employee_name);
            textEmployeeId = itemView.findViewById(R.id.text_employee_id);
            textEmployeeType = itemView.findViewById(R.id.text_employee_type);
            textWorkPlace = itemView.findViewById(R.id.text_work_place);
            textPhone = itemView.findViewById(R.id.text_phone);
        }
    }
} 