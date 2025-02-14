package com.example.mostin;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        // 기존 user 타입을 MD로 업데이트
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        dbHelper.updateAllUserTypesToMD();

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout 설정
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EmployeeManagementFragment())
                    .commit();
            setTitle("직원 관리");
        } else if (itemId == R.id.action_order_history) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new OrderHistoryFragment())
                    .commit();
            setTitle("발주 내역");
        } else if (itemId == R.id.action_manage_goods) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AdminGoodsFragment())
                    .commit();
            setTitle("상품 관리");
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
} 