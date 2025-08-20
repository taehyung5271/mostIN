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
import android.widget.TextView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import com.example.mostin.R;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.activities.AdminHomeScreen;
import com.example.mostin.activities.HomeScreen;
import com.example.mostin.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;
    private CheckBox checkBoxAutoLogin;
    private CircularProgressIndicator progressAutoLogin;
    private TextView textAutoLoginStatus;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SessionManager 초기화
        sessionManager = new SessionManager(this);

        // 자동 로그인 확인
        checkAutoLogin();

        editTextId = findViewById(R.id.edit_text_id);
        editTextPassword = findViewById(R.id.edit_text_password);
        checkBoxAutoLogin = findViewById(R.id.checkbox_auto_login);
        progressAutoLogin = findViewById(R.id.progress_auto_login);
        textAutoLoginStatus = findViewById(R.id.text_auto_login_status);
        Button btnLogin = findViewById(R.id.btn_login);

        // 자동 로그인 체크박스 상태 설정
        if (checkBoxAutoLogin != null) {
            checkBoxAutoLogin.setChecked(sessionManager.isAutoLoginEnabled());
        }

        btnLogin.setOnClickListener(v -> performLogin());
    }

    /**
     * 자동 로그인 확인
     */
    private void checkAutoLogin() {
        if (sessionManager.isValidSession()) {
            // 로딩 인디케이터 표시
            showAutoLoginProgress(true);
            
            // 자동 로그인 처리를 위한 딜레이 (사용자 경험 개선)
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                String employeeId = sessionManager.getEmployeeId();
                String employeeName = sessionManager.getEmployeeName();
                String employeeType = sessionManager.getEmployeeType();
                String workPlaceName = sessionManager.getWorkPlaceName();

                Log.d("LoginActivity", "Auto login - ID: " + employeeId + ", Type: " + employeeType);

                // 세션 갱신
                sessionManager.refreshSession();

                // 로딩 인디케이터 숨김
                showAutoLoginProgress(false);

                // 사용자 타입에 따라 적절한 화면으로 이동
                navigateToHomeScreen(employeeId, employeeName, employeeType, workPlaceName);
            }, 1500); // 1.5초 딜레이
        }
    }
    
    /**
     * 자동 로그인 진행 상태 표시/숨김
     */
    private void showAutoLoginProgress(boolean show) {
        if (progressAutoLogin != null && textAutoLoginStatus != null) {
            progressAutoLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            textAutoLoginStatus.setVisibility(show ? View.VISIBLE : View.GONE);
        }
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
                    
                    // 자동 로그인 설정 확인 및 세션 저장
                    boolean autoLoginEnabled = checkBoxAutoLogin != null && checkBoxAutoLogin.isChecked();
                    sessionManager.setAutoLoginEnabled(autoLoginEnabled);
                    
                    if (autoLoginEnabled) {
                        // 로그인 성공 시 세션 저장
                        sessionManager.createLoginSession(
                            employee.getEmployeeId(),
                            employee.getEmployeeName(),
                            employee.getEmployeeType(),
                            employee.getWorkPlaceName()
                        );
                        Log.d("LoginActivity", "Session saved for auto login");
                    }

                    // 홈 화면으로 이동
                    navigateToHomeScreen(
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getEmployeeType(),
                        employee.getWorkPlaceName()
                    );
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

    /**
     * 사용자 타입에 따라 적절한 홈 화면으로 이동
     */
    private void navigateToHomeScreen(String employeeId, String employeeName, 
                                     String employeeType, String workPlaceName) {
        Intent intent;
        if ("admin".equals(employeeType)) {
            intent = new Intent(LoginActivity.this, AdminHomeScreen.class);
            Bundle adminInfo = new Bundle();
            adminInfo.putString("employee_id", employeeId);
            adminInfo.putString("employee_name", employeeName);
            intent.putExtras(adminInfo);
        } else {
            intent = new Intent(LoginActivity.this, HomeScreen.class);
            Bundle userInfo = new Bundle();
            userInfo.putString("employee_id", employeeId);
            userInfo.putString("employee_name", employeeName);
            userInfo.putString("employee_type", employeeType);
            userInfo.putString("work_place_name", workPlaceName);
            intent.putExtras(userInfo);

            Log.d("LoginActivity", "Sending data - ID: " + employeeId
                    + ", Name: " + employeeName
                    + ", Type: " + employeeType
                    + ", Workplace: " + workPlaceName);
        }
        startActivity(intent);
        finish();
    }
}