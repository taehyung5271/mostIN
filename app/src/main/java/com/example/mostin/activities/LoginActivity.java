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
            boolean autoLoginEnabled = sessionManager.isAutoLoginEnabled();
            Log.d("LoginActivity", "=== CHECKBOX SETUP ===");
            Log.d("LoginActivity", "SessionManager.isAutoLoginEnabled(): " + autoLoginEnabled);
            Log.d("LoginActivity", "Is definitely new install: " + isDefinitelyNewInstall());
            
            // 새 설치라면 무조건 false로 설정
            if (isDefinitelyNewInstall()) {
                Log.d("LoginActivity", "🚫 New install detected - forcing checkbox to FALSE");
                checkBoxAutoLogin.setChecked(false);
                sessionManager.setAutoLoginEnabled(false);
            } else {
                checkBoxAutoLogin.setChecked(autoLoginEnabled);
            }
            
            Log.d("LoginActivity", "Final checkbox state: " + checkBoxAutoLogin.isChecked());
            Log.d("LoginActivity", "=== END CHECKBOX SETUP ===");
        }

        btnLogin.setOnClickListener(v -> performLogin());
    }

    /**
     * 자동 로그인 확인
     */
    private void checkAutoLogin() {
        Log.d("LoginActivity", "=== AUTO LOGIN CHECK START ===");
        Log.d("LoginActivity", "checkAutoLogin() called");
        
        // 절대적으로 새 설치인지 확인 (매우 강력한 체크)
        if (isDefinitelyNewInstall()) {
            Log.d("LoginActivity", "🚫 DEFINITELY NEW INSTALL - BLOCKING ALL AUTO LOGIN");
            return;
        }
        
        // 먼저 SharedPreferences의 모든 상태를 확인
        boolean autoLoginEnabled = sessionManager.isAutoLoginEnabled();
        boolean validSession = sessionManager.isValidSession();
        
        Log.d("LoginActivity", "Auto login enabled: " + autoLoginEnabled);
        Log.d("LoginActivity", "Valid session: " + validSession);
        
        // 새 설치나 로그아웃 후라면 자동 로그인을 시도하지 않음
        if (!autoLoginEnabled || !validSession) {
            Log.d("LoginActivity", "No auto login - showing login screen");
            return;
        }
        
        // 저장된 사용자 정보 가져오기
        String employeeId = sessionManager.getEmployeeId();
        String employeeName = sessionManager.getEmployeeName();
        String employeeType = sessionManager.getEmployeeType();
        String workPlaceName = sessionManager.getWorkPlaceName();
        
        Log.d("LoginActivity", "Retrieved session data:");
        Log.d("LoginActivity", "  employeeId: '" + employeeId + "'");
        Log.d("LoginActivity", "  employeeName: '" + employeeName + "'");
        Log.d("LoginActivity", "  employeeType: '" + employeeType + "'");
        Log.d("LoginActivity", "  workPlaceName: '" + workPlaceName + "'");
        
        // 필수 정보가 모두 유효한지 확인
        if (employeeId == null || employeeId.isEmpty() || 
            employeeName == null || employeeName.isEmpty() ||
            employeeType == null || employeeType.isEmpty()) {
            
            Log.d("LoginActivity", "Invalid session data - clearing all data");
            sessionManager.clearSession();
            sessionManager.setAutoLoginEnabled(false);
            return;
        }
        
        // 의심스러운 "admin" 값 강제 차단
        if ("admin".equals(employeeType) && (employeeId == null || employeeId.isEmpty() || employeeName == null || employeeName.isEmpty())) {
            Log.e("LoginActivity", "🚨 SUSPICIOUS ADMIN TYPE WITH EMPTY DATA - BLOCKING");
            sessionManager.clearSession();
            sessionManager.setAutoLoginEnabled(false);
            return;
        }
        
        Log.d("LoginActivity", "All session data valid - proceeding with auto login");
        
        // 로딩 인디케이터 표시
        showAutoLoginProgress(true);
        
        // 자동 로그인 처리를 위한 딜레이 (사용자 경험 개선)
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            // 세션 갱신
            sessionManager.refreshSession();

            // 로딩 인디케이터 숨김
            showAutoLoginProgress(false);

            // 사용자 타입에 따라 적절한 화면으로 이동
            navigateToHomeScreen(employeeId, employeeName, employeeType, workPlaceName);
        }, 1500); // 1.5초 딜레이
    }
    
    /**
     * 확실한 새 설치인지 확인 (절대적 체크)
     */
    private boolean isDefinitelyNewInstall() {
        try {
            // 앱의 최초 설치 시간과 현재 시간 비교
            android.content.pm.PackageManager pm = getPackageManager();
            android.content.pm.PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            long installTime = info.firstInstallTime;
            long currentTime = System.currentTimeMillis();
            long timeDifference = currentTime - installTime;
            
            // 설치 후 30초 이내라면 새 설치로 간주
            boolean isNewInstall = timeDifference < 30000; // 30초
            
            Log.d("LoginActivity", "Install time: " + installTime);
            Log.d("LoginActivity", "Current time: " + currentTime);
            Log.d("LoginActivity", "Time difference: " + timeDifference + "ms");
            Log.d("LoginActivity", "Is definitely new install: " + isNewInstall);
            
            return isNewInstall;
            
        } catch (Exception e) {
            Log.e("LoginActivity", "Error checking install time", e);
            // 에러 발생 시 안전하게 새 설치로 간주하지 않음
            return false;
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
        Log.d("LoginActivity", "navigateToHomeScreen() called with:");
        Log.d("LoginActivity", "  employeeId: '" + employeeId + "'");
        Log.d("LoginActivity", "  employeeName: '" + employeeName + "'");
        Log.d("LoginActivity", "  employeeType: '" + employeeType + "'");
        Log.d("LoginActivity", "  workPlaceName: '" + workPlaceName + "'");
        
        // 새 설치에서 이상한 데이터가 들어오는 경우 차단
        if (employeeId == null || employeeId.isEmpty() ||
            employeeName == null || employeeName.isEmpty() ||
            employeeType == null || employeeType.isEmpty()) {
            Log.e("LoginActivity", "Invalid navigation data - staying on login screen");
            sessionManager.clearSession();
            sessionManager.setAutoLoginEnabled(false);
            return;
        }
        
        Intent intent;
        if ("admin".equals(employeeType)) {
            Log.d("LoginActivity", "Navigating to AdminHomeScreen");
            intent = new Intent(LoginActivity.this, AdminHomeScreen.class);
            Bundle adminInfo = new Bundle();
            adminInfo.putString("employee_id", employeeId);
            adminInfo.putString("employee_name", employeeName);
            intent.putExtras(adminInfo);
        } else {
            Log.d("LoginActivity", "Navigating to HomeScreen");
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