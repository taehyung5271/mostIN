package com.example.mostin.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.EmployeeAdapter;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.WorkPlace;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class EmployeeManagementFragment extends Fragment {
    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;
    private EditText searchEditText;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_management, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_employees);
        this.searchEditText = (EditText) view.findViewById(R.id.edit_search);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.adapter = new EmployeeAdapter(requireContext());
        this.adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda3
            @Override // com.example.mostin.adapters.EmployeeAdapter.OnItemClickListener
            public final void onItemClick(EmployeeModel employeeModel, int i) {
                this.f$0.m119xd3f28c06(employeeModel, i);
            }
        });
        this.recyclerView.setAdapter(this.adapter);
        Button btnAddEmployee = (Button) view.findViewById(R.id.btn_add_employee);
        btnAddEmployee.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m120xf9869507(view2);
            }
        });
        this.searchEditText.addTextChangedListener(new TextWatcher() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EmployeeManagementFragment.this.filterEmployees(s.toString());
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
        loadEmployees();
        return view;
    }

    /* renamed from: lambda$onCreateView$0$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m119xd3f28c06(EmployeeModel employee, int position) {
        showEditDialog(employee);
    }

    /* renamed from: lambda$onCreateView$1$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m120xf9869507(View v) {
        showAddEmployeeDialog();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadEmployees();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterEmployees(String query) {
        List<EmployeeModel> allEmployees = this.adapter.getAllEmployees();
        List<EmployeeModel> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            this.adapter.updateDisplayedEmployees(allEmployees);
            return;
        }
        String lowerCaseQuery = query.toLowerCase();
        for (EmployeeModel employee : allEmployees) {
            boolean matches = false;
            if (employee.getEmployeeName() != null && employee.getEmployeeName().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }
            if (!matches && employee.getEmployeeId() != null && employee.getEmployeeId().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }
            if (!matches && employee.getWorkPlaceName() != null && employee.getWorkPlaceName().toLowerCase().startsWith(lowerCaseQuery)) {
                matches = true;
            }
            if (matches) {
                filteredList.add(employee);
            }
        }
        this.adapter.updateDisplayedEmployees(filteredList);
    }

    private void showEditDialog(final EmployeeModel employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_employee, (ViewGroup) null);
        final EditText editName = (EditText) dialogView.findViewById(R.id.edit_employee_name);
        final EditText editPhone = (EditText) dialogView.findViewById(R.id.edit_phone);
        final Spinner spinnerWorkPlace = (Spinner) dialogView.findViewById(R.id.spinner_work_place);
        editName.setText(employee.getEmployeeName());
        editPhone.setText(employee.getPhoneNum());
        ApiService apiService = ApiClient.getApiService();
        Call<List<WorkPlace>> call = apiService.getAllWorkPlaces();
        call.enqueue(new Callback<List<WorkPlace>>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<List<WorkPlace>> call2, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> workPlaces = new ArrayList<>();
                    for (WorkPlace wp : response.body()) {
                        workPlaces.add(wp.getWorkPlaceName());
                    }
                    ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(EmployeeManagementFragment.this.requireContext(), android.R.layout.simple_spinner_item, workPlaces);
                    workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWorkPlace.setAdapter((SpinnerAdapter) workPlaceAdapter);
                    int workPlacePosition = workPlaces.indexOf(employee.getWorkPlaceName());
                    if (workPlacePosition >= 0) {
                        spinnerWorkPlace.setSelection(workPlacePosition);
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<WorkPlace>> call2, Throwable t) {
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "근무지 목록을 불러오는 데 실패했습니다.", 0).show();
            }
        });
        builder.setView(dialogView).setTitle("직원 정보 수정").setPositiveButton("수정", new DialogInterface.OnClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m123xac1137d7(employee, editName, editPhone, spinnerWorkPlace, dialogInterface, i);
            }
        }).setNegativeButton("삭제", new DialogInterface.OnClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m124xd1a540d8(employee, dialogInterface, i);
            }
        }).setNeutralButton("취소", (DialogInterface.OnClickListener) null).show();
    }

    /* renamed from: lambda$showEditDialog$2$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m123xac1137d7(EmployeeModel employee, EditText editName, EditText editPhone, Spinner spinnerWorkPlace, DialogInterface dialog, int which) {
        employee.setEmployeeName(editName.getText().toString());
        employee.setPhoneNum(editPhone.getText().toString());
        employee.setWorkPlaceName(spinnerWorkPlace.getSelectedItem().toString());
        updateEmployee(employee);
    }

    /* renamed from: lambda$showEditDialog$3$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m124xd1a540d8(EmployeeModel employee, DialogInterface dialog, int which) {
        showDeleteConfirmDialog(employee);
    }

    private void updateEmployee(EmployeeModel employee) {
        ApiService apiService = ApiClient.getApiService();
        Call<EmployeeModel> call = apiService.updateEmployee(employee.getEmployeeId(), employee);
        call.enqueue(new Callback<EmployeeModel>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.3
            @Override // retrofit2.Callback
            public void onResponse(Call<EmployeeModel> call2, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    EmployeeManagementFragment.this.loadEmployees();
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 정보가 수정되었습니다.", 0).show();
                } else {
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 정보 수정에 실패했습니다.", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<EmployeeModel> call2, Throwable t) {
                Log.e("EmployeeManagement", "Error updating employee", t);
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 정보 수정 중 오류가 발생했습니다.", 0).show();
            }
        });
    }

    private void showDeleteConfirmDialog(final EmployeeModel employee) {
        new AlertDialog.Builder(requireContext()).setTitle("직원 삭제").setMessage("정말로 이 직원을 삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m122x47dc09f0(employee, dialogInterface, i);
            }
        }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
    }

    /* renamed from: lambda$showDeleteConfirmDialog$4$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m122x47dc09f0(EmployeeModel employee, DialogInterface dialog, int which) {
        deleteEmployee(employee.getEmployeeId());
    }

    private void deleteEmployee(String employeeId) {
        ApiService apiService = ApiClient.getApiService();
        Call<Void> call = apiService.deleteEmployee(employeeId);
        call.enqueue(new Callback<Void>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.4
            @Override // retrofit2.Callback
            public void onResponse(Call<Void> call2, Response<Void> response) {
                if (response.isSuccessful()) {
                    EmployeeManagementFragment.this.loadEmployees();
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원이 삭제되었습니다.", 0).show();
                } else {
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 삭제에 실패했습니다.", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<Void> call2, Throwable t) {
                Log.e("EmployeeManagement", "Error deleting employee", t);
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 삭제 중 오류가 발생했습니다.", 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadEmployees() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<EmployeeModel>> call = apiService.getAllEmployees();
        call.enqueue(new Callback<List<EmployeeModel>>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.5
            @Override // retrofit2.Callback
            public void onResponse(Call<List<EmployeeModel>> call2, Response<List<EmployeeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeManagementFragment.this.adapter.setOriginalEmployees(response.body());
                } else {
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 목록을 불러오는 데 실패했습니다.", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<EmployeeModel>> call2, Throwable t) {
                Log.e("EmployeeManagement", "Error loading employees", t);
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 목록 로딩 중 오류가 발생했습니다.", 0).show();
            }
        });
    }

    private void showAddEmployeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_employee, (ViewGroup) null);
        final Spinner spinnerType = (Spinner) dialogView.findViewById(R.id.spinner_employee_type);
        final Spinner spinnerWorkPlace = (Spinner) dialogView.findViewById(R.id.spinner_work_place);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"MD", "SV"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter((SpinnerAdapter) typeAdapter);
        ApiService apiService = ApiClient.getApiService();
        Call<List<WorkPlace>> call = apiService.getAllWorkPlaces();
        call.enqueue(new Callback<List<WorkPlace>>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.6
            @Override // retrofit2.Callback
            public void onResponse(Call<List<WorkPlace>> call2, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> workPlaces = new ArrayList<>();
                    for (WorkPlace wp : response.body()) {
                        workPlaces.add(wp.getWorkPlaceName());
                    }
                    ArrayAdapter<String> workPlaceAdapter = new ArrayAdapter<>(EmployeeManagementFragment.this.requireContext(), android.R.layout.simple_spinner_item, workPlaces);
                    workPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerWorkPlace.setAdapter((SpinnerAdapter) workPlaceAdapter);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<List<WorkPlace>> call2, Throwable t) {
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "근무지 목록을 불러오는 데 실패했습니다.", 0).show();
            }
        });
        builder.setView(dialogView).setTitle("직원 추가").setPositiveButton("추가", new DialogInterface.OnClickListener() { // from class: com.example.mostin.fragments.EmployeeManagementFragment$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m121x1efa644b(dialogView, spinnerType, spinnerWorkPlace, dialogInterface, i);
            }
        }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
    }

    /* renamed from: lambda$showAddEmployeeDialog$5$com-example-mostin-fragments-EmployeeManagementFragment, reason: not valid java name */
    /* synthetic */ void m121x1efa644b(View dialogView, Spinner spinnerType, Spinner spinnerWorkPlace, DialogInterface dialog, int which) {
        String employeeId = ((EditText) dialogView.findViewById(R.id.edit_employee_id)).getText().toString();
        String name = ((EditText) dialogView.findViewById(R.id.edit_employee_name)).getText().toString();
        String password = ((EditText) dialogView.findViewById(R.id.edit_employee_password)).getText().toString();
        String phone = ((EditText) dialogView.findViewById(R.id.edit_phone)).getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        String workPlace = spinnerWorkPlace.getSelectedItem().toString();
        String address = ((EditText) dialogView.findViewById(R.id.edit_address)).getText().toString();
        EmployeeModel newEmployee = new EmployeeModel(employeeId, name, password, phone, type, address, workPlace);
        createEmployee(newEmployee);
    }

    private void createEmployee(EmployeeModel employee) {
        ApiService apiService = ApiClient.getApiService();
        Call<EmployeeModel> call = apiService.createEmployee(employee);
        call.enqueue(new Callback<EmployeeModel>() { // from class: com.example.mostin.fragments.EmployeeManagementFragment.7
            @Override // retrofit2.Callback
            public void onResponse(Call<EmployeeModel> call2, Response<EmployeeModel> response) {
                if (response.isSuccessful()) {
                    EmployeeManagementFragment.this.loadEmployees();
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원이 추가되었습니다.", 0).show();
                } else {
                    Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 추가에 실패했습니다.", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<EmployeeModel> call2, Throwable t) {
                Log.e("EmployeeManagement", "Error adding employee", t);
                Toast.makeText(EmployeeManagementFragment.this.getContext(), "직원 추가 중 오류가 발생했습니다.", 0).show();
            }
        });
    }
}
