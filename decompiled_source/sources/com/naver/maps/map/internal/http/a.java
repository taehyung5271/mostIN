package com.naver.maps.map.internal.http;

import okhttp3.OkHttpClient;
import okhttp3.brotli.BrotliInterceptor;

/* loaded from: classes.dex */
public class a {
    private static volatile OkHttpClient a;

    public static void a(b bVar) {
        OkHttpClient okHttpClientA = bVar.a();
        synchronized (a.class) {
            a = okHttpClientA;
        }
    }

    public static OkHttpClient a() {
        OkHttpClient okHttpClientBuild = a;
        if (okHttpClientBuild == null) {
            synchronized (a.class) {
                okHttpClientBuild = a;
                if (okHttpClientBuild == null) {
                    okHttpClientBuild = new OkHttpClient.Builder().addInterceptor(BrotliInterceptor.INSTANCE).build();
                    a = okHttpClientBuild;
                }
            }
        }
        return okHttpClientBuild;
    }

    private a() {
    }
}
