package com.naver.maps.map.internal.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class b extends BroadcastReceiver {
    private static volatile b a;
    private final Context b;
    private final List<a> c = new CopyOnWriteArrayList();
    private int d;

    public static b a(Context context) {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b(context.getApplicationContext());
                    a.a(new NativeConnectivityListener());
                }
            }
        }
        return a;
    }

    private b(Context context) {
        this.b = context;
    }

    public void a() {
        if (this.d == 0) {
            this.b.registerReceiver(a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
        this.d++;
    }

    public void b() {
        this.d--;
        if (this.d == 0) {
            this.b.unregisterReceiver(a);
        }
    }

    public void a(a aVar) {
        this.c.add(aVar);
    }

    public void b(a aVar) {
        this.c.remove(aVar);
    }

    public boolean c() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.b.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean zC = c();
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a(zC);
        }
    }
}
