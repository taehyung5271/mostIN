package com.example.mostin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mostin.EmployeeModel;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new SQLiteHelper(this);
        // 앱 최초 실행 시 초기 데이터 삽입
        dbHelper.insertInitialData();

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

    public void loginUser(String username, String password) {
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        EmployeeModel employee = dbHelper.validateLogin(username, password);

        if (employee != null) {
            Intent intent;
            if ("admin".equals(employee.getEmployeeType())) {
                // 관리자는 AdminHomeScreen으로 이동
                intent = new Intent(this, AdminHomeScreen.class);
                Bundle adminInfo = new Bundle();
                adminInfo.putString("employee_id", employee.getEmployeeId());
                adminInfo.putString("employee_name", employee.getEmployeeName());
                intent.putExtras(adminInfo);
            } else {
                // 일반 사용자는 HomeScreen으로 이동
                intent = new Intent(this, HomeScreen.class);
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
            Toast.makeText(this, "로그인 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}