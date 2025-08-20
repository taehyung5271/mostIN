package com.naver.maps.map.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.naver.maps.map.BuildConfig;
import com.naver.maps.map.R;
import com.naver.maps.map.app.LegalNoticeActivity;
import com.naver.maps.map.app.LegendActivity;
import com.naver.maps.map.app.OpenSourceLicenseActivity;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes.dex */
class InfoView extends LinearLayout {

    private static class a extends RecyclerView.Adapter<ViewOnClickListenerC0016a> {
        private static final List<Class<? extends Activity>> a = Arrays.asList(LegendActivity.class, LegalNoticeActivity.class, OpenSourceLicenseActivity.class);
        private final Context b;
        private final String[] c;
        private final LayoutInflater d;

        /* renamed from: com.naver.maps.map.widget.InfoView$a$a, reason: collision with other inner class name */
        public class ViewOnClickListenerC0016a extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView b;
            private int c;

            public ViewOnClickListenerC0016a(View view) {
                super(view);
                this.b = (TextView) view.findViewById(R.id.navermap_menu_title);
                view.setOnClickListener(this);
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                a.this.b.startActivity(new Intent(a.this.b, (Class<?>) a.a.get(this.c)));
            }

            public void a(int i) {
                this.c = i;
                this.b.setText(a.this.c[i]);
            }
        }

        public a(Context context) {
            this.b = context;
            this.c = context.getResources().getStringArray(R.array.navermap_info_menu);
            this.d = LayoutInflater.from(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public ViewOnClickListenerC0016a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewOnClickListenerC0016a(this.d.inflate(R.layout.navermap_info_menu_item, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onBindViewHolder(ViewOnClickListenerC0016a viewOnClickListenerC0016a, int i) {
            viewOnClickListenerC0016a.a(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.c.length;
        }
    }

    public InfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public InfoView(Context context) {
        super(context);
        a();
    }

    public InfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    private void a() {
        inflate(getContext(), R.layout.navermap_info_view, this);
        setOrientation(1);
        ((TextView) findViewById(R.id.navermap_version)).setText(getContext().getString(R.string.navermap_version, BuildConfig.VERSION_NAME));
        ((TextView) findViewById(R.id.navermap_copyright)).setText(getContext().getString(R.string.navermap_copyright, Integer.valueOf(Calendar.getInstance().get(1))));
        ((RecyclerView) findViewById(R.id.navermap_recycler_view)).setAdapter(new a(getContext()));
    }
}
