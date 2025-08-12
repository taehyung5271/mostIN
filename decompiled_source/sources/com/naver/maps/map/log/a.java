package com.naver.maps.map.log;

import android.util.Log;

/* loaded from: classes.dex */
public class a implements b {
    @Override // com.naver.maps.map.log.b
    public void a(String str, Object... objArr) {
        Log.d("NaverMap", String.format(str, objArr));
    }

    @Override // com.naver.maps.map.log.b
    public void b(String str, Object... objArr) {
        Log.i("NaverMap", String.format(str, objArr));
    }

    @Override // com.naver.maps.map.log.b
    public void c(String str, Object... objArr) {
        Log.w("NaverMap", String.format(str, objArr));
    }

    @Override // com.naver.maps.map.log.b
    public void d(String str, Object... objArr) {
        Log.e("NaverMap", String.format(str, objArr));
    }

    @Override // com.naver.maps.map.log.b
    public void a(String str) {
        Log.wtf("NaverMap", str);
    }
}
