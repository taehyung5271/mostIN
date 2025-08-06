package com.example.mostin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.adapters.DrawerMenuAdapter;
import com.example.mostin.fragments.AttendanceCalendarFragment;
import com.example.mostin.fragments.CommutingRegistrationFragment;
import com.example.mostin.fragments.IntroduceGoodsFragment;
import com.example.mostin.fragments.OrderingFragment;
import com.example.mostin.fragments.UserOrderHistoryFragment;
import com.example.mostin.models.MenuItems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class HomeScreen extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView drawerMenu;
    private String employeeId;
    private String employeeName;
    private String employeeType;
    private TextView fragmentTitle;
    private Toolbar toolbar;
    private String workPlaceName;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        this.employeeId = intent.getStringExtra(CommutingRegistrationFragment.ARG_EMPLOYEE_ID);
        this.employeeName = intent.getStringExtra(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME);
        this.employeeType = intent.getStringExtra("employee_type");
        this.workPlaceName = intent.getStringExtra(CommutingRegistrationFragment.ARG_WORK_PLACE_NAME);
        Log.d("HomeScreen", "Received user data - ID: " + this.employeeId + ", Name: " + this.employeeName + ", Type: " + this.employeeType + ", Workplace: " + this.workPlaceName);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerMenu = (RecyclerView) findViewById(R.id.drawer_menu);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.fragmentTitle = (TextView) findViewById(R.id.fragment_title);
        setSupportActionBar(this.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        this.toolbar.setNavigationIcon(R.drawable.ic_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.open, R.string.close);
        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        List<MenuItems> menuItems = new ArrayList<>();
        menuItems.add(new MenuItems("출근", Arrays.asList("출근 등록", "출근 장부")));
        menuItems.add(new MenuItems("발주", Arrays.asList("발주 신청", "발주 리스트")));
        DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(menuItems);
        this.drawerMenu.setAdapter(drawerMenuAdapter);
        this.drawerMenu.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState == null) {
            replaceFragment(new IntroduceGoodsFragment());
            this.fragmentTitle.setText("회사 제품");
        }
    }

    private void replaceFragment(Fragment fragment) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }
        if (fragment instanceof CommutingRegistrationFragment) {
            Bundle bundle = new Bundle();
            bundle.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, this.employeeId);
            bundle.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, this.employeeName);
            bundle.putString(CommutingRegistrationFragment.ARG_WORK_PLACE_NAME, this.workPlaceName);
            fragment.setArguments(bundle);
            Log.d("HomeScreen", "Sending data to CommutingRegistrationFragment - ID: " + this.employeeId + ", Name: " + this.employeeName + ", Workplace: " + this.workPlaceName);
        } else if (fragment instanceof AttendanceCalendarFragment) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, this.employeeId);
            bundle2.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, this.employeeName);
            fragment.setArguments(bundle2);
            Log.d("HomeScreen", "Sending data to AttendanceCalendarFragment");
        } else if (fragment instanceof OrderingFragment) {
            Bundle bundle3 = new Bundle();
            bundle3.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, this.employeeId);
            bundle3.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, this.employeeName);
            fragment.setArguments(bundle3);
            Log.d("HomeScreen", "Sending data to OrderingFragment - ID: " + this.employeeId + ", Name: " + this.employeeName);
        } else if (fragment instanceof UserOrderHistoryFragment) {
            Bundle bundle4 = new Bundle();
            bundle4.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_ID, this.employeeId);
            bundle4.putString(CommutingRegistrationFragment.ARG_EMPLOYEE_NAME, this.employeeName);
            bundle4.putString(CommutingRegistrationFragment.ARG_WORK_PLACE_NAME, this.workPlaceName);
            fragment.setArguments(bundle4);
            Log.d("HomeScreen", "Sending data to UserOrderHistoryFragment - ID: " + this.employeeId + ", Name: " + this.employeeName + ", Workplace: " + this.workPlaceName);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        Log.d("MenuDebug", "Menu successfully created");
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;
        if (itemId == R.id.action_home) {
            selectedFragment = new IntroduceGoodsFragment();
            this.fragmentTitle.setText("회사 제품");
        } else if (itemId == R.id.action_commuting) {
            selectedFragment = new CommutingRegistrationFragment();
            this.fragmentTitle.setText("출근 등록");
        } else if (itemId == R.id.action_calendar) {
            selectedFragment = new AttendanceCalendarFragment();
            this.fragmentTitle.setText("출근 근무표");
        } else if (itemId == R.id.action_ordering) {
            selectedFragment = new OrderingFragment();
            this.fragmentTitle.setText("발주 신청");
        } else if (itemId == R.id.action_logout) {
            Intent intent = new Intent(this, (Class<?>) LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (selectedFragment != null) {
            replaceFragment(selectedFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateFragment(Fragment fragment, String title) {
        this.fragmentTitle.setText(title);
        replaceFragment(fragment);
    }

    public DrawerLayout getDrawerLayout() {
        return this.drawerLayout;
    }
}
