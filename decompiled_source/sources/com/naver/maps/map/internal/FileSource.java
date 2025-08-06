package com.naver.maps.map.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.log.c;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class FileSource {
    private static volatile FileSource a;
    private final List<NaverMapSdk.CacheFlushCallback> b = new ArrayList();
    private long handle;

    private native void nativeActivate();

    private native void nativeAddPreloadGlyph(String str, String[] strArr, int i, int i2);

    private native void nativeAddPreloadTile(String str, String str2, String str3, String str4, int i, int i2, LatLngBounds latLngBounds);

    private native void nativeCreate(FileSource fileSource, AssetManager assetManager, String str, String str2, String str3);

    private native void nativeDeactivate();

    private native void nativeDestroy();

    private native void nativeFlushCache();

    private native void nativeRemovePreloadGlyph(String str);

    private native void nativeRemovePreloadTile(String str);

    private native void nativeSetPreloadFallbackToStreaming(boolean z);

    static {
        a.a();
    }

    public static FileSource a(Context context) {
        FileSource fileSource = a;
        if (fileSource == null) {
            synchronized (FileSource.class) {
                fileSource = a;
                if (fileSource == null) {
                    fileSource = new FileSource(context);
                    a = fileSource;
                }
            }
        }
        return fileSource;
    }

    private static String b(Context context) {
        String string;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle == null) {
                string = null;
            } else {
                string = bundle.getString(NaverMapSdk.METADATA_NAME_CACHE_LOCATION);
            }
        } catch (PackageManager.NameNotFoundException e) {
            string = null;
        }
        if (NaverMapSdk.METADATA_VALUE_CACHE_LOCATION_CACHE.equals(string)) {
            return context.getCacheDir().getAbsolutePath();
        }
        if (NaverMapSdk.METADATA_VALUE_CACHE_LOCATION_EXTERNAL.equals(string)) {
            String externalStorageState = Environment.getExternalStorageState();
            if ("mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState)) {
                File externalFilesDir = context.getExternalFilesDir(null);
                if (externalFilesDir != null) {
                    return externalFilesDir.getAbsolutePath();
                }
            } else {
                c.c("External storage is not readable.", new Object[0]);
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

    private FileSource(Context context) {
        String strB = b(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.naver.maps.map.NaverMapSdk", 0);
        String string = sharedPreferences.getString("cache_path", context.getFilesDir().getAbsolutePath());
        if (!strB.equals(string)) {
            sharedPreferences.edit().putString("cache_path", strB).apply();
        }
        nativeCreate(this, context.getResources().getAssets(), context.getCacheDir().getAbsolutePath(), strB, string);
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    public void a() {
        nativeActivate();
    }

    public void b() {
        nativeDeactivate();
    }

    public void a(NaverMapSdk.CacheFlushCallback cacheFlushCallback) {
        this.b.add(cacheFlushCallback);
        if (this.b.size() == 1) {
            nativeFlushCache();
        }
    }

    private void onCacheFlushed() {
        for (NaverMapSdk.CacheFlushCallback cacheFlushCallback : this.b) {
            if (cacheFlushCallback != null) {
                cacheFlushCallback.onCacheFlushed();
            }
        }
        this.b.clear();
    }
}
