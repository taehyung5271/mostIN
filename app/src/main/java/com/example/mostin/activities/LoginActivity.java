package com.example.mostin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mostin.R;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.activities.AdminHomeScreen;
import com.example.mostin.activities.HomeScreen;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextId = findViewById(R.id.edit_text_id);
        editTextPassword = findViewById(R.id.edit_text_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> performLogin());
    }

    private void performLogin() {
        String id = editTextId.getText().toString();
        String password = editTextPassword.getText().toString();

        loginUser(id, password);
    }

    public void loginUser(String employeeId, String password) {
        ApiService apiService = ApiClient.getApiService();
        Map<String, String> credentials = new HashMap<>();
        credentials.put("employeeId", employeeId);
        credentials.put("password", password);

        Call<EmployeeModel> call = apiService.login(credentials);
        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeModel employee = response.body();
                    Intent intent;
                    if ("admin".equals(employee.getEmployeeType())) {
                        intent = new Intent(LoginActivity.this, AdminHomeScreen.class);
                        Bundle adminInfo = new Bundle();
                        adminInfo.putString("employee_id", employee.getEmployeeId());
                        adminInfo.putString("employee_name", employee.getEmployeeName());
                        intent.putExtras(adminInfo);
                    } else {
                        intent = new Intent(LoginActivity.this, HomeScreen.class);
                        Bundle userInfo = new Bundle();
                        userInfo.putString("employee_id", employee.getEmployeeId());
                        userInfo.putString("employee_name", employee.getEmployeeName());
                        userInfo.putString("employee_type", employee.getEmployeeType());
                        userInfo.putString("work_place_name", employee.getWorkPlaceName());
                        intent.putExtras(userInfo);

                        Log.d("LoginActivity", "Sending data - ID: " + employee.getEmployeeId()
                                + ", Name: " + employee.getEmployeeName()
                                + ", Type: " + employee.getEmployeeType()
                                + ", Workplace: " + employee.getWorkPlaceName());
                    }
                    startActivity(intent);
                    finish();
                } else {
                    // Handle unsuccessful login (e.g., password mismatch, user not found)
                    String errorMessage = "로그인 정보가 올바르지 않습니다.";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (Exception e) {
                            Log.e("LoginActivity", "Error parsing error body: " + e.getMessage());
                        }
                    }
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Log.e("LoginActivity", "Login API call failed: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}