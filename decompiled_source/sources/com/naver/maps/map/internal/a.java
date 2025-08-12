package com.naver.maps.map.internal;

import android.content.Context;
import com.getkeepsafe.relinker.ReLinker;

/* loaded from: classes.dex */
public class a {
    private static volatile Context a;
    private static volatile boolean b;

    public static void a(Context context) {
        if (a != null) {
            return;
        }
        synchronized (a.class) {
            if (a == null) {
                a = context.getApplicationContext();
            }
        }
    }

    public static void a() {
        if (b) {
            return;
        }
        synchronized (a.class) {
            if (b) {
                return;
            }
            b = true;
            Context context = a;
            if (context != null) {
                try {
                    ReLinker.loadLibrary(context, "navermap");
                    return;
                } catch (Throwable th) {
                }
            }
            System.loadLibrary("navermap");
        }
    }
}
