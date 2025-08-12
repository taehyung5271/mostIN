package com.naver.maps.map;

import android.os.Bundle;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.indoor.IndoorLevel;
import com.naver.maps.map.indoor.IndoorRegion;
import com.naver.maps.map.indoor.IndoorSelection;
import com.naver.maps.map.indoor.IndoorView;
import com.naver.maps.map.indoor.IndoorZone;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
final class a {
    private final NaverMap a;
    private final NativeMapView b;
    private final List<NaverMap.OnIndoorSelectionChangeListener> c = new CopyOnWriteArrayList();
    private boolean d;
    private IndoorRegion e;
    private IndoorSelection f;
    private IndoorView g;
    private IndoorView h;

    private static IndoorSelection a(IndoorRegion indoorRegion, IndoorView indoorView) {
        int zoneIndex = indoorRegion.getZoneIndex(indoorView.getZoneId());
        if (zoneIndex < 0 || zoneIndex >= indoorRegion.getZones().length) {
            return null;
        }
        IndoorZone indoorZone = indoorRegion.getZones()[zoneIndex];
        IndoorLevel[] levels = indoorZone.getLevels();
        int levelIndex = indoorZone.getLevelIndex(indoorView.getLevelId());
        if (levelIndex < 0 || levelIndex >= levels.length) {
            return null;
        }
        return new IndoorSelection(indoorRegion, zoneIndex, levelIndex);
    }

    a(NaverMap naverMap, NativeMapView nativeMapView) {
        this.a = naverMap;
        this.b = nativeMapView;
    }

    boolean a() {
        return this.d;
    }

    void a(boolean z) {
        if (this.d == z) {
            return;
        }
        this.d = z;
        if (z) {
            this.b.d(true);
            this.a.setLayerGroupEnabled("indoorgnd", true);
        } else {
            this.b.d(false);
            this.a.setLayerGroupEnabled("indoorgnd", false);
            b((IndoorRegion) null);
        }
    }

    void a(IndoorRegion indoorRegion) {
        b(indoorRegion);
    }

    IndoorSelection b() {
        return this.f;
    }

    void a(NaverMap.OnIndoorSelectionChangeListener onIndoorSelectionChangeListener) {
        this.c.add(onIndoorSelectionChangeListener);
    }

    void b(NaverMap.OnIndoorSelectionChangeListener onIndoorSelectionChangeListener) {
        this.c.remove(onIndoorSelectionChangeListener);
    }

    void a(IndoorView indoorView) {
        IndoorSelection indoorSelectionA;
        if (indoorView != null) {
            if (this.f != null && indoorView.equals(this.f.getLevel().getIndoorView())) {
                this.g = null;
                return;
            } else if (this.e != null && (indoorSelectionA = a(this.e, indoorView)) != null) {
                a(indoorSelectionA);
                this.g = null;
                return;
            }
        }
        this.g = indoorView;
    }

    private void b(IndoorRegion indoorRegion) {
        if (indoorRegion == null || indoorRegion.getZones().length == 0) {
            if (this.f != null) {
                if (this.d) {
                    this.a.setLayerGroupEnabled("indoorgnd", true);
                }
                this.b.a((IndoorView) null);
                this.h = this.f.getLevel().getIndoorView();
                this.e = null;
                b((IndoorSelection) null);
                return;
            }
            return;
        }
        this.e = indoorRegion;
        c(indoorRegion);
        this.h = null;
    }

    private void c(IndoorRegion indoorRegion) {
        IndoorSelection indoorSelectionA;
        IndoorSelection indoorSelectionA2;
        if (this.g != null && (indoorSelectionA2 = a(indoorRegion, this.g)) != null) {
            a(indoorSelectionA2);
            return;
        }
        if (this.f != null) {
            IndoorSelection indoorSelectionA3 = a(indoorRegion, this.f.getLevel().getIndoorView());
            if (indoorSelectionA3 != null) {
                b(indoorSelectionA3);
                return;
            }
            for (IndoorView indoorView : this.f.getLevel().getConnections()) {
                IndoorSelection indoorSelectionA4 = a(indoorRegion, indoorView);
                if (indoorSelectionA4 != null) {
                    b(indoorSelectionA4);
                    return;
                }
            }
        }
        if (this.h != null && (indoorSelectionA = a(indoorRegion, this.h)) != null) {
            a(indoorSelectionA);
        } else {
            a(new IndoorSelection(indoorRegion, 0, indoorRegion.getZones()[0].getDefultLevelIndex()));
        }
    }

    private void a(IndoorSelection indoorSelection) {
        this.b.a(indoorSelection.getLevel().getIndoorView());
        this.a.setLayerGroupEnabled("indoorgnd", false);
        b(indoorSelection);
    }

    private void b(IndoorSelection indoorSelection) {
        this.f = indoorSelection;
        Iterator<NaverMap.OnIndoorSelectionChangeListener> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().onIndoorSelectionChange(indoorSelection);
        }
    }

    void a(NaverMapOptions naverMapOptions) {
        a(naverMapOptions.isIndoorEnabled());
    }

    void a(Bundle bundle) {
        bundle.putBoolean("IndoorMap00", this.d);
    }

    void b(Bundle bundle) {
        a(bundle.getBoolean("IndoorMap00"));
    }
}
