package com.naver.maps.map.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.R;
import com.naver.maps.map.indoor.IndoorLevel;
import com.naver.maps.map.indoor.IndoorSelection;
import com.naver.maps.map.indoor.IndoorZone;

/* loaded from: classes.dex */
public class IndoorLevelPickerView extends FrameLayout {
    private final NaverMap.OnIndoorSelectionChangeListener a;
    private float b;
    private float c;
    private RecyclerView d;
    private b e;
    private NaverMap f;

    private interface c {
        void a(int i);
    }

    private static class a extends LinearLayoutManager {
        public a(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            C0015a c0015a = new C0015a(recyclerView.getContext());
            c0015a.setTargetPosition(position);
            startSmoothScroll(c0015a);
        }

        /* renamed from: com.naver.maps.map.widget.IndoorLevelPickerView$a$a, reason: collision with other inner class name */
        private static class C0015a extends LinearSmoothScroller {
            public C0015a(Context context) {
                super(context);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return super.calculateSpeedPerPixel(displayMetrics) * 5.0f;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return (((boxEnd - boxStart) / 2) + boxStart) - (((viewEnd - viewStart) / 2) + viewStart);
            }
        }
    }

    private static class b extends RecyclerView.Adapter<a> {
        private final LayoutInflater a;
        private final IndoorZone b;
        private int c;
        private c d;

        public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView b;
            private final View c;

            private a(View view) {
                super(view);
                view.setOnClickListener(this);
                this.b = (TextView) view.findViewById(R.id.navermap_level);
                this.c = view.findViewById(R.id.navermap_connection);
            }

            public void a(IndoorLevel indoorLevel) {
                this.b.setText(indoorLevel.getName());
                this.c.setVisibility(indoorLevel.getConnections().length == 0 ? 8 : 0);
                this.itemView.setSelected(getAdapterPosition() == b.this.c);
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                int i = b.this.c;
                b.this.c = getAdapterPosition();
                b.this.notifyItemChanged(i);
                this.itemView.setSelected(true);
                if (b.this.d != null) {
                    b.this.d.a(getAdapterPosition());
                }
            }
        }

        public b(Context context, IndoorZone indoorZone, int i) {
            this.a = LayoutInflater.from(context);
            this.b = indoorZone;
            this.c = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new a(this.a.inflate(R.layout.navermap_indoor_level_item, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onBindViewHolder(a aVar, int i) {
            aVar.a(this.b.getLevels()[i]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.b.getLevels().length;
        }

        public void a(c cVar) {
            this.d = cVar;
        }

        public int a() {
            return this.c;
        }

        public void a(int i) {
            if (this.c == i) {
                return;
            }
            notifyItemChanged(this.c);
            this.c = i;
            notifyItemChanged(i);
        }
    }

    public IndoorLevelPickerView(Context context) {
        super(context);
        this.a = new NaverMap.OnIndoorSelectionChangeListener() { // from class: com.naver.maps.map.widget.IndoorLevelPickerView.1
            @Override // com.naver.maps.map.NaverMap.OnIndoorSelectionChangeListener
            public void onIndoorSelectionChange(IndoorSelection indoorSelection) {
                IndoorLevelPickerView.this.a(indoorSelection);
            }
        };
        a();
    }

    public IndoorLevelPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = new NaverMap.OnIndoorSelectionChangeListener() { // from class: com.naver.maps.map.widget.IndoorLevelPickerView.1
            @Override // com.naver.maps.map.NaverMap.OnIndoorSelectionChangeListener
            public void onIndoorSelectionChange(IndoorSelection indoorSelection) {
                IndoorLevelPickerView.this.a(indoorSelection);
            }
        };
        a();
    }

    public IndoorLevelPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = new NaverMap.OnIndoorSelectionChangeListener() { // from class: com.naver.maps.map.widget.IndoorLevelPickerView.1
            @Override // com.naver.maps.map.NaverMap.OnIndoorSelectionChangeListener
            public void onIndoorSelectionChange(IndoorSelection indoorSelection) {
                IndoorLevelPickerView.this.a(indoorSelection);
            }
        };
        a();
    }

    private void a() {
        inflate(getContext(), R.layout.navermap_indoor_level_picker, this);
        this.b = getResources().getDisplayMetrics().density;
        this.c = this.b * 40.0f;
        this.d = (RecyclerView) findViewById(R.id.navermap_recycler_view);
        this.d.setLayoutManager(new a(getContext()));
        new LinearSnapHelper().attachToRecyclerView(this.d);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(this.d.getPaddingTop() + this.d.getPaddingBottom() + ((int) ((this.c * Math.min((int) (View.MeasureSpec.getSize(heightMeasureSpec) / this.c), 5)) - this.b)), BasicMeasure.EXACTLY));
    }

    public NaverMap getMap() {
        return this.f;
    }

    public void setMap(NaverMap map) {
        if (this.f == map) {
            return;
        }
        if (map == null) {
            this.f.removeOnIndoorSelectionChangeListener(this.a);
            setVisibility(8);
        } else {
            setVisibility(0);
            map.addOnIndoorSelectionChangeListener(this.a);
            a(map.getIndoorSelection());
        }
        this.f = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IndoorSelection indoorSelection) {
        if (indoorSelection == null) {
            this.e = null;
            this.d.setAdapter(null);
            this.d.setVisibility(8);
            return;
        }
        final IndoorZone zone = indoorSelection.getZone();
        if (this.e != null && this.e.b.equals(zone)) {
            if (this.e.c != indoorSelection.getLevelIndex()) {
                this.e.a(indoorSelection.getLevelIndex());
                this.d.smoothScrollToPosition(indoorSelection.getLevelIndex());
                return;
            }
            return;
        }
        this.e = new b(getContext(), zone, indoorSelection.getLevelIndex());
        this.e.a(new c() { // from class: com.naver.maps.map.widget.IndoorLevelPickerView.2
            @Override // com.naver.maps.map.widget.IndoorLevelPickerView.c
            public void a(int i) {
                if (IndoorLevelPickerView.this.f != null) {
                    IndoorLevelPickerView.this.f.requestIndoorView(zone.getLevels()[i].getIndoorView());
                }
            }
        });
        this.d.setAdapter(this.e);
        this.d.setVisibility(0);
        post(new Runnable() { // from class: com.naver.maps.map.widget.IndoorLevelPickerView.3
            @Override // java.lang.Runnable
            public void run() {
                if (IndoorLevelPickerView.this.e != null) {
                    IndoorLevelPickerView.this.d.smoothScrollToPosition(IndoorLevelPickerView.this.e.a());
                }
            }
        });
    }
}
