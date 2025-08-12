package com.naver.maps.map.util;

import androidx.core.math.MathUtils;
import com.naver.maps.map.NaverMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class TileCoverHelper {
    static final /* synthetic */ boolean a = true;
    private final NaverMap.OnCameraChangeListener b = new NaverMap.OnCameraChangeListener() { // from class: com.naver.maps.map.util.TileCoverHelper.1
        @Override // com.naver.maps.map.NaverMap.OnCameraChangeListener
        public void onCameraChange(int reason, boolean animated) {
            TileCoverHelper.this.a();
        }
    };
    private final NaverMap.OnCameraIdleListener c = new NaverMap.OnCameraIdleListener() { // from class: com.naver.maps.map.util.TileCoverHelper.2
        @Override // com.naver.maps.map.NaverMap.OnCameraIdleListener
        public void onCameraIdle() {
            TileCoverHelper.this.a();
        }
    };
    private Set<Long> d = new HashSet();
    private int e = 0;
    private int f = 21;
    private boolean g;
    private Listener h;
    private NaverMap i;

    public interface Listener {
        void onTileChanged(List<Long> list, List<Long> list2);
    }

    public int getMinZoom() {
        return this.e;
    }

    public void setMinZoom(int minZoom) {
        if (this.e == minZoom) {
            return;
        }
        this.e = minZoom;
        if (this.i != null) {
            a();
        }
    }

    public int getMaxZoom() {
        return this.f;
    }

    public void setMaxZoom(int maxZoom) {
        if (this.f == maxZoom) {
            return;
        }
        this.f = maxZoom;
        if (this.i != null) {
            a();
        }
    }

    public boolean isUpdateOnChange() {
        return this.g;
    }

    public void setUpdateOnChange(boolean updateOnChange) {
        if (this.g == updateOnChange) {
            return;
        }
        this.g = updateOnChange;
        if (this.i != null) {
            if (updateOnChange) {
                this.i.removeOnCameraIdleListener(this.c);
                this.i.addOnCameraChangeListener(this.b);
            } else {
                this.i.removeOnCameraChangeListener(this.b);
                this.i.addOnCameraIdleListener(this.c);
            }
        }
    }

    public Listener getListener() {
        return this.h;
    }

    public void setListener(Listener listener) {
        this.h = listener;
    }

    public NaverMap getMap() {
        return this.i;
    }

    public void setMap(NaverMap map) {
        if (this.i == map) {
            return;
        }
        if (this.i != null) {
            if (this.g) {
                this.i.removeOnCameraChangeListener(this.b);
            } else {
                this.i.removeOnCameraIdleListener(this.c);
            }
        }
        this.i = map;
        if (map != null) {
            if (this.g) {
                map.addOnCameraChangeListener(this.b);
            } else {
                map.addOnCameraIdleListener(this.c);
            }
        }
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = this.h == null ? null : new ArrayList();
        ArrayList arrayList2 = this.h != null ? new ArrayList() : null;
        if (this.i != null) {
            for (long j : this.i.getCoveringTileIdsAtZoom(MathUtils.clamp((int) this.i.getCameraPosition().zoom, this.e, this.f))) {
                if (!this.d.remove(Long.valueOf(j)) && arrayList != null) {
                    arrayList.add(Long.valueOf(j));
                }
                hashSet.add(Long.valueOf(j));
            }
        }
        if (arrayList2 != null) {
            arrayList2.addAll(this.d);
        }
        this.d = hashSet;
        if (this.h != null) {
            if (!a && arrayList == null) {
                throw new AssertionError();
            }
            if (!a && arrayList2 == null) {
                throw new AssertionError();
            }
            if (!arrayList.isEmpty() || !arrayList2.isEmpty()) {
                this.h.onTileChanged(arrayList, arrayList2);
            }
        }
    }
}
