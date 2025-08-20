package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<EmployeeModel> employees; // This will be the currently displayed list
    private List<EmployeeModel> originalEmployees; // This will hold the full, unfiltered list
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
        this.originalEmployees = new ArrayList<>();
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

    // This method is now used to update the displayed list (for filtering)
    public void updateDisplayedEmployees(List<EmployeeModel> newEmployees) {
        if (newEmployees != null) {
            this.employees = new ArrayList<>(newEmployees);
        } else {
            this.employees = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    // This method is used to set the initial full list of employees
    public void setOriginalEmployees(List<EmployeeModel> originalEmployees) {
        this.originalEmployees = new ArrayList<>(originalEmployees); // Create a new list to avoid reference issues
        this.employees = new ArrayList<>(originalEmployees); // Also set the displayed list initially
        notifyDataSetChanged();
    }

    public List<EmployeeModel> getAllEmployees() {
        return new ArrayList<>(this.originalEmployees); // Return a copy of the full, unfiltered list
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