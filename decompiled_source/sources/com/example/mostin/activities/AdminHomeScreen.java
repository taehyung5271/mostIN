package com.example.mostin.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.mostin.R;
import com.example.mostin.fragments.AdminGoodsFragment;
import com.example.mostin.fragments.EmployeeManagementFragment;
import com.example.mostin.fragments.OrderHistoryFragment;
import com.google.android.material.navigation.NavigationView;

/* loaded from: classes4.dex */
public class AdminHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeManagementFragment()).commit();
            navigationView.setCheckedItem(R.id.action_manage_employee);
        }
    }

    @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_manage_employee) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeManagementFragment()).commit();
            setTitle("직원 관리");
        } else if (itemId == R.id.action_order_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrderHistoryFragment()).commit();
            setTitle("발주 내역");
        } else if (itemId == R.id.action_manage_goods) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminGoodsFragment()).commit();
            setTitle("상품 관리");
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
