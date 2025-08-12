package com.naver.maps.map.style.sources;

import android.os.Looper;
import com.naver.maps.map.log.c;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public abstract class Source {
    private long handle;
    private final List<a> onSourceLoadListeners = new CopyOnWriteArrayList();

    public interface a {
        void a();
    }

    private native String nativeGetAttribution();

    private native String nativeGetId();

    private native boolean nativeIsLoaded();

    static {
        com.naver.maps.map.internal.a.a();
    }

    Source(long handle) {
        this.handle = handle;
    }

    public Source() {
        checkThread();
    }

    public long getHandle() {
        return this.handle;
    }

    public boolean isDestroyedOn(String methodName) {
        if (this.handle == 0) {
            c.c("Cannot execute %s(): Source was already destroyed.", methodName);
            return true;
        }
        return false;
    }

    public String getId() {
        if (isDestroyedOn("getId")) {
            return "";
        }
        checkThread();
        return nativeGetId();
    }

    public String getAttribution() {
        if (isDestroyedOn("getAttribution")) {
            return "";
        }
        checkThread();
        return nativeGetAttribution();
    }

    public boolean isLoaded() {
        if (isDestroyedOn("isLoaded")) {
            return false;
        }
        checkThread();
        return nativeIsLoaded();
    }

    public void addOnSourceLoadListener(a listener) {
        this.onSourceLoadListeners.add(listener);
        if (isLoaded()) {
            listener.a();
        }
    }

    public void removeOnSourceLoadListener(a listener) {
        this.onSourceLoadListeners.remove(listener);
    }

    public void fireOnLoad() {
        Iterator<a> it = this.onSourceLoadListeners.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    protected void checkThread() {
        if (this.handle != 0) {
            com.naver.maps.map.internal.util.c.a(Looper.getMainLooper().getThread());
        }
    }
}
