package com.example.mostin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mostin.R;
import com.example.mostin.models.MenuItems;
import java.util.List;

/* loaded from: classes7.dex */
public class DrawerMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<MenuItems> menuItems;

    public DrawerMenuAdapter(List<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_drawer_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MenuItems item = this.menuItems.get(position);
        holder.textTitle.setText(item.getTitle());
        final LinearLayout subMenuContainer = holder.subMenuContainer;
        subMenuContainer.removeAllViews();
        for (final String subItem : item.getSubItems()) {
            TextView textView = new TextView(this.context);
            textView.setText(subItem);
            textView.setPadding(60, 20, 20, 20);
            textView.setTextSize(16.0f);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.DrawerMenuAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m104x8e15ca5e(item, subItem, view);
                }
            });
            subMenuContainer.addView(textView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.adapters.DrawerMenuAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DrawerMenuAdapter.lambda$onBindViewHolder$1(subMenuContainer, holder, view);
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0037  */
    /* renamed from: lambda$onBindViewHolder$0$com-example-mostin-adapters-DrawerMenuAdapter, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    /* synthetic */ void m104x8e15ca5e(com.example.mostin.models.MenuItems r6, java.lang.String r7, android.view.View r8) {
        /*
            r5 = this;
            android.content.Context r0 = r5.context
            boolean r0 = r0 instanceof com.example.mostin.activities.HomeScreen
            if (r0 == 0) goto L9b
            android.content.Context r0 = r5.context
            com.example.mostin.activities.HomeScreen r0 = (com.example.mostin.activities.HomeScreen) r0
            r1 = 0
            java.lang.String r2 = ""
            java.lang.String r3 = r6.getTitle()
            int r4 = r3.hashCode()
            switch(r4) {
                case 54856: goto L2d;
                case 1544288: goto L23;
                case 1676256: goto L19;
                default: goto L18;
            }
        L18:
            goto L37
        L19:
            java.lang.String r4 = "출근"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L18
            r3 = 1
            goto L38
        L23:
            java.lang.String r4 = "발주"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L18
            r3 = 2
            goto L38
        L2d:
            java.lang.String r4 = "홈"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L18
            r3 = 0
            goto L38
        L37:
            r3 = -1
        L38:
            switch(r3) {
                case 0: goto L80;
                case 1: goto L5e;
                case 2: goto L3c;
                default: goto L3b;
            }
        L3b:
            goto L89
        L3c:
            java.lang.String r3 = "발주 신청"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L4d
            com.example.mostin.fragments.OrderingFragment r3 = new com.example.mostin.fragments.OrderingFragment
            r3.<init>()
            r1 = r3
            java.lang.String r2 = "발주 신청"
            goto L89
        L4d:
            java.lang.String r3 = "발주 리스트"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L89
            com.example.mostin.fragments.UserOrderHistoryFragment r3 = new com.example.mostin.fragments.UserOrderHistoryFragment
            r3.<init>()
            r1 = r3
            java.lang.String r2 = "발주 내역"
            goto L89
        L5e:
            java.lang.String r3 = "출근 등록"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L6f
            com.example.mostin.fragments.CommutingRegistrationFragment r3 = new com.example.mostin.fragments.CommutingRegistrationFragment
            r3.<init>()
            r1 = r3
            java.lang.String r2 = "출근 등록"
            goto L89
        L6f:
            java.lang.String r3 = "출근 장부"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L89
            com.example.mostin.fragments.AttendanceCalendarFragment r3 = new com.example.mostin.fragments.AttendanceCalendarFragment
            r3.<init>()
            r1 = r3
            java.lang.String r2 = "출근 근무표"
            goto L89
        L80:
            com.example.mostin.fragments.IntroduceGoodsFragment r3 = new com.example.mostin.fragments.IntroduceGoodsFragment
            r3.<init>()
            r1 = r3
            java.lang.String r2 = "회사 제품"
        L89:
            if (r1 == 0) goto L9b
            r0.updateFragment(r1, r2)
            androidx.drawerlayout.widget.DrawerLayout r3 = r0.getDrawerLayout()
            if (r3 == 0) goto L9b
            androidx.drawerlayout.widget.DrawerLayout r3 = r0.getDrawerLayout()
            r3.closeDrawers()
        L9b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.mostin.adapters.DrawerMenuAdapter.m104x8e15ca5e(com.example.mostin.models.MenuItems, java.lang.String, android.view.View):void");
    }

    static /* synthetic */ void lambda$onBindViewHolder$1(LinearLayout subMenuContainer, ViewHolder holder, View v) {
        boolean isExpanded = subMenuContainer.getVisibility() == 0;
        subMenuContainer.setVisibility(isExpanded ? 8 : 0);
        holder.imageArrow.setRotation(isExpanded ? 0.0f : 180.0f);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageArrow;
        LinearLayout subMenuContainer;
        TextView textTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textTitle = (TextView) itemView.findViewById(R.id.menu_title);
            this.imageArrow = (ImageView) itemView.findViewById(R.id.menu_open_close);
            this.subMenuContainer = (LinearLayout) itemView.findViewById(R.id.sub_menu_container);
        }
    }
}
