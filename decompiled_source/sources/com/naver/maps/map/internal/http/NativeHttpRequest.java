package com.naver.maps.map.internal.http;

import android.os.Build;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;
import javax.net.ssl.SSLException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public final class NativeHttpRequest implements Callback {
    public static final String a = "NaverMapSDK/3.20.0 (Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + ")";
    private static final OkHttpClient b = a.a();
    private final int c;
    private Call d;
    private Request e;
    private long handle;

    private native void nativeOnFailure(int i, String str);

    private native void nativeOnResponse(int i, String str, String str2, String str3, String str4, String str5, String str6, byte[] bArr);

    private NativeHttpRequest(long handle, String resourceUrl, String etag, String modified, int attempts) {
        this.handle = handle;
        this.c = attempts;
        try {
            HttpUrl.parse(resourceUrl);
            Request.Builder builderAddHeader = new Request.Builder().url(resourceUrl).tag(resourceUrl.toLowerCase(Locale.ENGLISH)).addHeader("User-Agent", a).addHeader("Referer", "client://NaverMapSDK");
            if (etag.length() > 0) {
                builderAddHeader = builderAddHeader.addHeader("If-None-Match", etag);
            } else if (modified.length() > 0) {
                builderAddHeader = builderAddHeader.addHeader("If-Modified-Since", modified);
            }
            this.e = builderAddHeader.build();
            this.d = b.newCall(this.e);
            this.d.enqueue(this);
        } catch (Exception e) {
            a(e);
        }
    }

    private void cancel() {
        if (this.d != null) {
            this.d.cancel();
        }
        synchronized (this) {
            this.handle = 0L;
        }
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        ResponseBody responseBodyBody = response.body();
        try {
            if (responseBodyBody == null) {
                a(new Exception("body is null"));
                return;
            }
            byte[] bArrBytes = responseBodyBody.bytes();
            synchronized (this) {
                if (this.handle != 0) {
                    nativeOnResponse(response.code(), response.header("ETag"), response.header("Last-Modified"), response.header("Cache-Control"), response.header("Expires"), response.header("Retry-After"), response.header("x-rate-limit-reset"), bArrBytes);
                }
            }
        } catch (IOException e) {
            a(e);
        } finally {
            responseBodyBody.close();
        }
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException e) {
        a(e);
    }

    private void a(Exception exc) {
        int i;
        if ((exc instanceof UnknownHostException) || (exc instanceof SocketException) || (exc instanceof ProtocolException) || (exc instanceof SSLException)) {
            i = 0;
        } else if (exc instanceof InterruptedIOException) {
            i = 1;
        } else {
            i = 2;
        }
        String message = exc.getMessage() != null ? exc.getMessage() : "Error processing the request";
        synchronized (this) {
            if (this.handle != 0) {
                nativeOnFailure(i, message);
            }
        }
    }
}
