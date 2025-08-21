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
import com.example.mostin.fragments.EmployeeAttendanceFragment;
import com.example.mostin.utils.SessionManager;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;

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
import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    
    // Admin 사용자 정보를 저장할 변수들
    private String employeeId;
    private String employeeName;
    
    // Dashboard card views
    private MaterialCardView cardEmployees, cardOrders, cardProducts, cardNotifications;
    private TextView textEmployeeCount, textOrdersCount, textProductsCount, textNotificationsCount;
    
    // UI 컴포넌트
    private MaterialButton btnLogout;
    
    // API Service
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        // SessionManager 초기화
        sessionManager = new SessionManager(this);
        
        // API Service 초기화
        apiService = ApiClient.getClient().create(ApiService.class);

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
        
        // 로그아웃 버튼 초기화
        initializeLogoutButton();

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
        
        // Fragment 카드 동적 크기 설정
        setupDynamicFragmentCardSize();
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
            performLogout();
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
                navigateToFragment(new EmployeeAttendanceFragment(), "직원 출근 확인");
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
     * Dashboard 통계 데이터 로드
     */
    private void loadDashboardStats() {
        // 직원 수 조회
        loadEmployeeCount();
        
        // 상품 수 조회
        loadGoodsCount();
        
        // 카드 텍스트는 XML에서 설정됨 (추가 설정 불필요)
        
        Log.d("AdminHomeScreen", "Dashboard stats loading started");
    }
    
    /**
     * 로그아웃 버튼 초기화
     */
    private void initializeLogoutButton() {
        btnLogout = findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> showLogoutConfirmDialog());
        }
    }
    
    /**
     * 직원 수 조회
     */
    private void loadEmployeeCount() {
        apiService.getEmployeeCount().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Long employeeCount = response.body();
                    if (textEmployeeCount != null) {
                        textEmployeeCount.setText(String.valueOf(employeeCount));
                    }
                    Log.d("AdminHomeScreen", "Employee count loaded: " + employeeCount);
                } else {
                    Log.e("AdminHomeScreen", "Failed to load employee count: " + response.code());
                    if (textEmployeeCount != null) {
                        textEmployeeCount.setText("0");
                    }
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("AdminHomeScreen", "Error loading employee count", t);
                if (textEmployeeCount != null) {
                    textEmployeeCount.setText("0");
                }
                Toast.makeText(AdminHomeScreen.this, "직원 수 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * 상품 수 조회
     */
    private void loadGoodsCount() {
        Log.d("AdminHomeScreen", "🔄 상품 수 조회 시작...");
        apiService.getGoodsCount().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Long goodsCount = response.body();
                    if (textProductsCount != null) {
                        String currentText = textProductsCount.getText().toString();
                        textProductsCount.setText(String.valueOf(goodsCount));
                        Log.d("AdminHomeScreen", "✅ 상품 수 업데이트: " + currentText + " → " + goodsCount);
                    }
                } else {
                    Log.e("AdminHomeScreen", "❌ 상품 수 조회 실패: " + response.code() + " - " + response.message());
                    if (textProductsCount != null) {
                        textProductsCount.setText("0");
                    }
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("AdminHomeScreen", "Error loading goods count", t);
                if (textProductsCount != null) {
                    textProductsCount.setText("0");
                }
                Toast.makeText(AdminHomeScreen.this, "상품 수 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * 통계 데이터 새로고침 (외부에서 호출 가능)
     */
    public void refreshDashboardStats() {
        Log.d("AdminHomeScreen", "Refreshing dashboard statistics");
        loadDashboardStats();
    }
    
    /**
     * 로그아웃 확인 다이얼로그 표시
     */
    private void showLogoutConfirmDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("로그아웃")
                .setMessage("정말로 로그아웃하시겠습니까?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton("로그아웃", (dialog, which) -> performLogout())
                .setNegativeButton("취소", null)
                .show();
    }
    
    /**
     * 로그아웃 수행
     */
    private void performLogout() {
        // 세션 정리 후 로그아웃
        sessionManager.logout();
        Log.d("AdminHomeScreen", "Admin logged out, session cleared");
        
        // 로그인 화면으로 이동
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        
        Toast.makeText(this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Fragment 카드를 화면 크기에 맞게 동적으로 설정
     */
    private void setupDynamicFragmentCardSize() {
        // Fragment 컨테이너와 카드 찾기
        android.view.View fragmentContainer = findViewById(R.id.fragment_container);
        com.google.android.material.card.MaterialCardView fragmentCard = null;
        
        if (fragmentContainer != null && fragmentContainer.getParent() instanceof com.google.android.material.card.MaterialCardView) {
            fragmentCard = (com.google.android.material.card.MaterialCardView) fragmentContainer.getParent();
        }
            
        if (fragmentCard != null) {
            // 화면 크기 정보 가져오기
            android.util.DisplayMetrics metrics = getResources().getDisplayMetrics();
            int screenHeight = metrics.heightPixels;
            float density = metrics.density;
            
            // AdminHomeScreen 헤더 높이 계산 (80dp + 16dp 패딩)
            int headerHeightDp = 80 + 16; // AppBar + padding
            int headerHeightPx = (int) (headerHeightDp * density);
            
            // 최소 여백만 계산 (카드 마진만 - 여유 공간 제거)
            int marginDp = 16 + 8; // top margin + minimal bottom margin
            int marginPx = (int) (marginDp * density);
            
            // Fragment 카드가 헤더 제외한 나머지 화면 전체 차지 (제한 없음)
            int optimalFragmentHeight = screenHeight - headerHeightPx - marginPx;
            
            // Fragment 카드 높이 설정
            android.view.ViewGroup.LayoutParams layoutParams = fragmentCard.getLayoutParams();
            layoutParams.height = optimalFragmentHeight;
            fragmentCard.setLayoutParams(layoutParams);
            
            // 로그 출력
            android.util.Log.d("AdminHomeScreen", String.format(
                "Fragment 카드 전체 화면 활용: 화면=%dpx, 헤더=%dpx, 여백=%dpx, Fragment=%dpx (%.0fdp) - 헤더 제외 %.1f%% 활용",
                screenHeight, headerHeightPx, marginPx, optimalFragmentHeight, optimalFragmentHeight / density,
                (float) optimalFragmentHeight / (screenHeight - headerHeightPx) * 100
            ));
        }
    }
} 