package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mostin.R;
import com.example.mostin.activities.HomeScreen;
import com.example.mostin.fragments.IntroduceGoodsFragment;
import com.example.mostin.fragments.CommutingRegistrationFragment;
import com.example.mostin.fragments.AttendanceCalendarFragment;
import com.example.mostin.fragments.OrderingFragment;
import com.example.mostin.fragments.UserOrderHistoryFragment;
import com.example.mostin.models.MenuItems;

import java.util.List;

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.ViewHolder> {
    private List<MenuItems> menuItems;
    private Context context;

    public DrawerMenuAdapter(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_drawer_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItems item = menuItems.get(position);
        holder.textTitle.setText(item.getTitle());

        // 소분류 메뉴 설정
        LinearLayout subMenuContainer = holder.subMenuContainer;
        subMenuContainer.removeAllViews();
        
        for (String subItem : item.getSubItems()) {
            TextView textView = new TextView(context);
            textView.setText(subItem);
            textView.setPadding(60, 20, 20, 20);
            textView.setTextSize(16);
            
            // 소분류 클릭 이벤트 처리
            textView.setOnClickListener(v -> {
                if (context instanceof HomeScreen) {
                    HomeScreen activity = (HomeScreen) context;
                    Fragment selectedFragment = null;
                    String title = "";

                    // 메인 메뉴와 서브메뉴에 따라 Fragment 선택
                    switch (item.getTitle()) {
                        case "홈":
                            selectedFragment = new IntroduceGoodsFragment();
                            title = "회사 제품";
                            break;
                        case "출근":
                            if (subItem.equals("출근 등록")) {
                                selectedFragment = new CommutingRegistrationFragment();
                                title = "출근 등록";
                            } else if (subItem.equals("출근 장부")) {
                                selectedFragment = new AttendanceCalendarFragment();
                                title = "출근 근무표";
                            }
                            break;
                        case "발주":
                            if (subItem.equals("발주 신청")) {
                                selectedFragment = new OrderingFragment();
                                title = "발주 신청";
                            } else if (subItem.equals("발주 리스트")) {
                                selectedFragment = new UserOrderHistoryFragment();
                                title = "발주 내역";
                            }
                            break;
                    }

                    if (selectedFragment != null) {
                        // Fragment 교체 및 제목 설정
                        activity.updateFragment(selectedFragment, title);
                        // Drawer 닫기
                        if (activity.getDrawerLayout() != null) {
                            activity.getDrawerLayout().closeDrawers();
                        }
                    }
                }
            });
            
            subMenuContainer.addView(textView);
        }

        // 메인 메뉴 클릭시 서브메뉴 표시/숨김 처리
        holder.itemView.setOnClickListener(v -> {
            boolean isExpanded = subMenuContainer.getVisibility() == View.VISIBLE;
            subMenuContainer.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            holder.imageArrow.setRotation(isExpanded ? 0 : 180);
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        ImageView imageArrow;
        LinearLayout subMenuContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.menu_title);
            imageArrow = itemView.findViewById(R.id.menu_open_close);
            subMenuContainer = itemView.findViewById(R.id.sub_menu_container);
        }
    }
}
