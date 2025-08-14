package com.example.mostin.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import com.example.mostin.R;
import com.example.mostin.fragments.EmployeeManagementFragment;
import com.example.mostin.fragments.OrderHistoryFragment;
import com.example.mostin.fragments.AdminGoodsFragment;
import com.example.mostin.utils.SessionManager;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.card.MaterialCardView;
import com.example.mostin.activities.LoginActivity;

public class AdminHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    
    // Admin 사용자 정보를 저장할 변수들
    private String employeeId;
    private String employeeName;
    
    // Dashboard card views
    private MaterialCardView cardEmployees, cardOrders, cardProducts, cardNotifications;
    private TextView textEmployeeCount, textOrdersCount, textProductsCount, textNotificationsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        // SessionManager 초기화
        sessionManager = new SessionManager(this);

        // LoginActivity에서 전달받은 사용자 정보 저장
        Intent intent = getIntent();
        employeeId = intent.getStringExtra("employee_id");
        employeeName = intent.getStringExtra("employee_name");

        // 디버깅을 위한 로그
        Log.d("AdminHomeScreen", "Received admin data - ID: " + employeeId + 
            ", Name: " + employeeName);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout 설정
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 네비게이션 드로어 헤더에 관리자 정보 설정
        View headerView = navigationView.getHeaderView(0);
        TextView adminEmployeeName = headerView.findViewById(R.id.admin_employee_name);
        
        if (adminEmployeeName != null && employeeName != null) {
            adminEmployeeName.setText(employeeName + " 관리자");
        }

        // Dashboard cards 초기화
        initializeDashboardCards();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 기본 Fragment로 직원 관리 Fragment 설정
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EmployeeManagementFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.action_manage_employee);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.action_manage_employee) {
            navigateToFragment(new EmployeeManagementFragment(), "직원 관리");
        } else if (itemId == R.id.action_order_history) {
            navigateToFragment(new OrderHistoryFragment(), "발주 내역");
        } else if (itemId == R.id.action_manage_goods) {
            navigateToFragment(new AdminGoodsFragment(), "상품 관리");
        } else if (itemId == R.id.action_admin_settings) {
            // 설정 화면으로 이동 (추후 구현)
            Toast.makeText(this, "설정 기능 준비 중입니다", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.action_admin_logout) {
            // 세션 정리 후 로그아웃
            sessionManager.logout();
            Log.d("AdminHomeScreen", "Admin logged out, session cleared");
            
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            
            Toast.makeText(this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
        }
        
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Dashboard 카드들 초기화
     */
    private void initializeDashboardCards() {
        // Dashboard card views 찾기 (ID로 직접 찾기)
        cardEmployees = findViewById(R.id.card_employees);
        cardOrders = findViewById(R.id.card_orders);
        cardProducts = findViewById(R.id.card_products);
        cardNotifications = findViewById(R.id.card_notifications);

        // TextViews 찾기
        textEmployeeCount = findViewById(R.id.text_employee_count);
        textOrdersCount = findViewById(R.id.text_orders_count);
        textProductsCount = findViewById(R.id.text_products_count);
        textNotificationsCount = findViewById(R.id.text_notifications_count);

        // 카드 클릭 리스너 설정
        if (cardEmployees != null) {
            cardEmployees.setOnClickListener(v -> {
                animateCardClick(cardEmployees);
                navigateToFragment(new EmployeeManagementFragment(), "직원 관리");
            });
        }

        if (cardOrders != null) {
            cardOrders.setOnClickListener(v -> {
                animateCardClick(cardOrders);
                navigateToFragment(new OrderHistoryFragment(), "발주 내역");
            });
        }

        if (cardProducts != null) {
            cardProducts.setOnClickListener(v -> {
                animateCardClick(cardProducts);
                navigateToFragment(new AdminGoodsFragment(), "상품 관리");
            });
        }

        if (cardNotifications != null) {
            cardNotifications.setOnClickListener(v -> {
                animateCardClick(cardNotifications);
                Toast.makeText(this, "알림 기능 준비 중입니다", Toast.LENGTH_SHORT).show();
            });
        }

        // 통계 데이터 로드 (임시 데이터)
        loadDashboardStats();
    }

    /**
     * 카드 클릭 애니메이션
     */
    private void animateCardClick(View card) {
        // 스케일 애니메이션
        card.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction(() -> {
                card.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(100);
            });
    }

    /**
     * Fragment 전환 with 애니메이션
     */
    private void navigateToFragment(androidx.fragment.app.Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        // 슬라이드 애니메이션 설정
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        );
        
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        
        setTitle(title);
    }

    /**
     * Dashboard 통계 데이터 로드 (임시 데이터)
     */
    private void loadDashboardStats() {
        // 실제 구현에서는 API에서 데이터를 가져와야 함
        if (textEmployeeCount != null) textEmployeeCount.setText("24");
        if (textOrdersCount != null) textOrdersCount.setText("156");
        if (textProductsCount != null) textProductsCount.setText("89");
        if (textNotificationsCount != null) textNotificationsCount.setText("3");
        
        Log.d("AdminHomeScreen", "Dashboard stats loaded");
    }
} 