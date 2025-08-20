package com.example.mostin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mostin.R;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.fragments.CommutingRegistrationFragment;
import com.example.mostin.models.EmployeeModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.editTextId = (EditText) findViewById(R.id.edit_text_id);
        this.editTextPassword = (EditText) findViewById(R.id.edit_text_password);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.activities.LoginActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m100lambda$onCreate$0$comexamplemostinactivitiesLoginActivity(view);
            }
        });
    }

    /* renamed from: lambda$onCreate$0$com-example-mostin-activities-LoginActivity, reason: not valid java name */
    /* synthetic */ void m100lambda$onCreate$0$comexamplemostinactivitiesLoginActivity(View v) {
        performLogin();
    }

    private void performLogin() {
        String id = this.editTextId.getText().toString();
        String password = this.editTextPassword.getText().toString();
        loginUser(id, password);
    }

    public void loginUser(String employeeId, String password) {
        ApiService apiService = ApiClient.getApiService();
        Map<String, String> credentials = new HashMap<>();
        credentials.put("employeeId", employeeId);
        credentials.put("password", password);
        Call<EmployeeModel> call = apiService.login(credentials);
        call.enqueue(new Callback<EmployeeModel>() { // from class: com.example.mostin.activities.LoginActivity.1
            @Override // retrofit2.Callback
            public void onResponse(Call<EmployeeModel> call2, Response<EmployeeModel> response) throws IOException {
                Intent intent;
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeModel employee = response.body();
                    if ("admin".equals(employee.getEmployeeType())) {
                        intent = new Intent(LoginActivity.this, (Class<?>) AdminHomeScreen.class);
                        Bundle adminInfo = new Bundle();
                        adminInfo.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, employee.getEmployeeId());
                        adminInfo.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, employee.getEmployeeName());
                        intent.putExtras(adminInfo);
                    } else {
                        Intent intent2 = new Intent(LoginActivity.this, (Class<?>) HomeScreen.class);
                        Bundle userInfo = new Bundle();
                        userInfo.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, employee.getEmployeeId());
                        userInfo.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, employee.getEmployeeName());
                        userInfo.putString("employee_type", employee.getEmployeeType());
                        userInfo.putString(CommutingRegistrationFragment.ARG_WORK_PLACE_NAME, employee.getWorkPlaceName());
                        intent2.putExtras(userInfo);
                        Log.d("LoginActivity", "Sending data - ID: " + employee.getEmployeeId() + ", Name: " + employee.getEmployeeName() + ", Type: " + employee.getEmployeeType() + ", Workplace: " + employee.getWorkPlaceName());
                        intent = intent2;
                    }
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                    return;
                }
                String errorMessage = "로그인 정보가 올바르지 않습니다.";
                if (response.errorBody() != null) {
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (Exception e) {
                        Log.e("LoginActivity", "Error parsing error body: " + e.getMessage());
                    }
                }
                Toast.makeText(LoginActivity.this, errorMessage, 0).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<EmployeeModel> call2, Throwable t) {
                Log.e("LoginActivity", "Login API call failed: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "네트워크 오류: " + t.getMessage(), 1).show();
            }
        });
    }
}
