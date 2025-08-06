package com.naver.maps.map;

import android.os.Bundle;
import com.naver.maps.map.style.sources.Source;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class f {
    private final NaverMap a;
    private final NativeMapView b;
    private final List<a> c = new CopyOnWriteArrayList();
    private String[] d;
    private String[] e;
    private boolean f;

    public interface a {
        void a();
    }

    f(NaverMap naverMap, NativeMapView nativeMapView) {
        this.a = naverMap;
        this.b = nativeMapView;
    }

    void a() {
        this.f = true;
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    void a(String str) {
        Source sourceB = b(str);
        if (sourceB != null) {
            sourceB.fireOnLoad();
        }
    }

    public String[] b() {
        return this.d;
    }

    public void a(String[] strArr) {
        this.f = false;
        this.d = strArr;
        this.e = null;
        this.a.g();
    }

    public String[] c() {
        return this.e;
    }

    public Source b(String str) {
        return this.b.d(str);
    }

    void a(NaverMapOptions naverMapOptions) {
        a(naverMapOptions.a());
    }

    void a(Bundle bundle) {
        bundle.putStringArray("Style00", this.d);
    }

    void b(Bundle bundle) {
        a(bundle.getStringArray("Style00"));
    }
}
