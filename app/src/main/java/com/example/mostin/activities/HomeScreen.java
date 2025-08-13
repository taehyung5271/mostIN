package com.example.mostin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import com.example.mostin.R;
import com.example.mostin.models.MenuItems;
import com.example.mostin.adapters.DrawerMenuAdapter;
import com.example.mostin.fragments.IntroduceGoodsFragment;
import com.example.mostin.fragments.CommutingRegistrationFragment;
import com.example.mostin.fragments.AttendanceCalendarFragment;
import com.example.mostin.fragments.OrderingFragment;
import com.example.mostin.fragments.UserOrderHistoryFragment;
import com.example.mostin.activities.AdminHomeScreen;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private RecyclerView drawerMenu;
    private NavigationView navigationView;
    private ExtendedFloatingActionButton fabQuickAction;
    private Toolbar toolbar;
    private TextView fragmentTitle;
    private TextView collapsingTitle;

    // 사용자 정보를 저장할 변수들
    private String employeeId;
    private String employeeName;
    private String employeeType;
    private String workPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // LoginActivity에서 전달받은 사용자 정보 저장
        Intent intent = getIntent();
        employeeId = intent.getStringExtra("employee_id");
        employeeName = intent.getStringExtra("employee_name");
        employeeType = intent.getStringExtra("employee_type");
        workPlaceName = intent.getStringExtra("work_place_name");

        // 디버깅을 위한 로그
        Log.d("HomeScreen", "Received user data - ID: " + employeeId + 
            ", Name: " + employeeName + 
            ", Type: " + employeeType + 
            ", Workplace: " + workPlaceName);

        // UI 초기화
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerMenu = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.nav_view);
        fabQuickAction = findViewById(R.id.fab_quick_action);
        toolbar = findViewById(R.id.toolbar);
        fragmentTitle = findViewById(R.id.fragment_title);
        collapsingTitle = findViewById(R.id.collapsing_title);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu_24);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView 설정
        navigationView.setNavigationItemSelectedListener(this);
        
        // 네비게이션 드로어 헤더에 사용자 정보 설정
        View headerView = navigationView.getHeaderView(0);
        TextView navEmployeeName = headerView.findViewById(R.id.nav_employee_name);
        TextView navWorkplaceName = headerView.findViewById(R.id.nav_workplace_name);
        
        if (navEmployeeName != null && employeeName != null) {
            navEmployeeName.setText(employeeName + " MD");
        }
        if (navWorkplaceName != null && workPlaceName != null) {
            navWorkplaceName.setText(workPlaceName);
        }

        // FloatingActionButton 설정
        fabQuickAction.setOnClickListener(v -> {
            CommutingRegistrationFragment fragment = new CommutingRegistrationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("employee_id", employeeId);
            bundle.putString("employee_name", employeeName);
            bundle.putString("work_place_name", workPlaceName);
            fragment.setArguments(bundle);
            replaceFragment(fragment);
            fragmentTitle.setText("출근 등록");
            if (collapsingTitle != null) {
                collapsingTitle.setText("출근 등록");
            }
        });

        // 기존 Drawer 메뉴 설정 (호환성 유지)
        List<MenuItems> menuItems = new ArrayList<>();
        menuItems.add(new MenuItems("출근", Arrays.asList("출근 등록", "출근 장부")));
        menuItems.add(new MenuItems("발주", Arrays.asList("발주 신청", "발주 리스트")));

        DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(menuItems);
        drawerMenu.setAdapter(drawerMenuAdapter);
        drawerMenu.setLayoutManager(new LinearLayoutManager(this));

        // 초기 Fragment 설정
        if (savedInstanceState == null) {
            replaceFragment(new IntroduceGoodsFragment());
            fragmentTitle.setText("상품 소개");
            if (collapsingTitle != null) {
                collapsingTitle.setText("상품 소개");
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        // Fragment에 따라 필요한 데이터 전달
        if (fragment instanceof CommutingRegistrationFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("employee_id", employeeId);
            bundle.putString("employee_name", employeeName);
            bundle.putString("work_place_name", workPlaceName);
            fragment.setArguments(bundle);
            
            Log.d("HomeScreen", "Sending data to CommutingRegistrationFragment - " +
                "ID: " + employeeId + 
                ", Name: " + employeeName + 
                ", Workplace: " + workPlaceName);
        } else if (fragment instanceof AttendanceCalendarFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("employee_id", employeeId);
            bundle.putString("employee_name", employeeName);
            fragment.setArguments(bundle);
            
            Log.d("HomeScreen", "Sending data to AttendanceCalendarFragment");
        } else if (fragment instanceof OrderingFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("employee_id", employeeId);
            bundle.putString("employee_name", employeeName);
            fragment.setArguments(bundle);
            
            Log.d("HomeScreen", "Sending data to OrderingFragment - " +
                "ID: " + employeeId + 
                ", Name: " + employeeName);
        } else if (fragment instanceof UserOrderHistoryFragment) {
            Bundle bundle = new Bundle();
            bundle.putString("employee_id", employeeId);
            bundle.putString("employee_name", employeeName);
            bundle.putString("work_place_name", workPlaceName);
            fragment.setArguments(bundle);
            
            Log.d("HomeScreen", "Sending data to UserOrderHistoryFragment - " +
                "ID: " + employeeId + 
                ", Name: " + employeeName + 
                ", Workplace: " + workPlaceName);
        }
        // 다른 Fragment들에 대한 데이터 전달도 필요한 경우 여기에 추가

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴 리소스를 툴바에 연결
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        Log.d("MenuDebug", "Menu successfully created");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;

        if (itemId == R.id.action_home) {
            selectedFragment = new IntroduceGoodsFragment();
            fragmentTitle.setText("상품 소개");
            if (collapsingTitle != null) {
                collapsingTitle.setText("상품 소개");
            }
        } else if (itemId == R.id.action_commuting) {
            selectedFragment = new CommutingRegistrationFragment();
            fragmentTitle.setText("출근 등록");
            if (collapsingTitle != null) {
                collapsingTitle.setText("출근 등록");
            }
        } else if (itemId == R.id.action_calendar) {
            selectedFragment = new AttendanceCalendarFragment();
            fragmentTitle.setText("출근 근무표");
            if (collapsingTitle != null) {
                collapsingTitle.setText("출근 근무표");
            }
        } else if (itemId == R.id.action_ordering) {
            selectedFragment = new OrderingFragment();
            fragmentTitle.setText("발주 신청");
            if (collapsingTitle != null) {
                collapsingTitle.setText("발주 신청");
            }
        } else if (itemId == R.id.action_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        
        if (selectedFragment != null) {
            replaceFragment(selectedFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    // DrawerAdapter에서 사용할 메서드
    public void updateFragment(Fragment fragment, String title) {
        fragmentTitle.setText(title);
        if (collapsingTitle != null) {
            collapsingTitle.setText(title);
        }
        replaceFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        String title = "";

        int itemId = item.getItemId();
        if (itemId == R.id.nav_commuting) {
            selectedFragment = new CommutingRegistrationFragment();
            title = "출근 등록";
        } else if (itemId == R.id.nav_attendance) {
            selectedFragment = new AttendanceCalendarFragment();
            title = "출석 캘린더";
        } else if (itemId == R.id.nav_goods) {
            selectedFragment = new IntroduceGoodsFragment();
            title = "상품 소개";
        } else if (itemId == R.id.nav_ordering) {
            selectedFragment = new OrderingFragment();
            title = "발주하기";
        } else if (itemId == R.id.nav_order_history) {
            selectedFragment = new UserOrderHistoryFragment();
            title = "발주 내역";
        } else if (itemId == R.id.nav_settings) {
            // 설정 화면으로 이동 (추후 구현)
            Toast.makeText(this, "설정 기능 준비 중입니다", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
            return true;
        } else if (itemId == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            drawerLayout.closeDrawers();
            return true;
        }

        if (selectedFragment != null) {
            replaceFragment(selectedFragment);
            fragmentTitle.setText(title);
            if (collapsingTitle != null) {
                collapsingTitle.setText(title);
            }
            drawerLayout.closeDrawers();
        }
        return true;
    }

    // DrawerLayout getter 추가
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }
}