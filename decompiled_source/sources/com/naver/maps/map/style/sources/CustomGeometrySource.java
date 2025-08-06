package com.naver.maps.map.style.sources;

import androidx.collection.LongSparseArray;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.TileId;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/* loaded from: classes.dex */
public class CustomGeometrySource extends Source {
    private static final AtomicInteger a = new AtomicInteger();
    private final Lock b;
    private ThreadPoolExecutor c;
    private com.naver.maps.map.style.sources.a d;
    private final LongSparseArray<a> e;
    private final LongSparseArray<AtomicBoolean> f;

    private native void nativeCreate(String str, Object obj);

    private native void nativeDestroy() throws Throwable;

    private native void nativeInvalidateBounds(LatLngBounds latLngBounds);

    private native void nativeInvalidateTile(int i, int i2, int i3);

    private native void nativeSetTileData(int i, int i2, int i3, String str);

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    public void a(int i, int i2, int i3, String str) {
        nativeSetTileData(i, i2, i3, str);
    }

    private void fetchTile(int z, int x, int y) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        long jFrom = TileId.from(z, x, y);
        a aVar = new a(jFrom, this.d, this.e, this.f, this, atomicBoolean);
        synchronized (this.e) {
            synchronized (this.f) {
                if (this.c.getQueue().contains(aVar)) {
                    this.c.remove(aVar);
                    a(aVar);
                } else if (this.f.containsKey(jFrom)) {
                    this.e.put(jFrom, aVar);
                } else {
                    a(aVar);
                }
            }
        }
    }

    private void a(a aVar) {
        this.b.lock();
        try {
            if (this.c != null && !this.c.isShutdown()) {
                this.c.execute(aVar);
            }
        } finally {
            this.b.unlock();
        }
    }

    private void cancelTile(int z, int x, int y) {
        long jFrom = TileId.from(z, x, y);
        synchronized (this.e) {
            synchronized (this.f) {
                AtomicBoolean atomicBoolean = this.f.get(jFrom);
                if (atomicBoolean == null || !atomicBoolean.compareAndSet(false, true)) {
                    if (!this.c.getQueue().remove(new a(jFrom, null, null, null, null, null))) {
                        this.e.remove(jFrom);
                    }
                }
            }
        }
    }

    private void startThreads() {
        this.b.lock();
        try {
            if (this.c != null && !this.c.isShutdown()) {
                this.c.shutdownNow();
            }
            this.c = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.naver.maps.map.style.sources.CustomGeometrySource.1
                final AtomicInteger a = new AtomicInteger();
                final int b = CustomGeometrySource.a.getAndIncrement();

                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, String.format(Locale.US, "%s-%d-%d", "CustomGeom", Integer.valueOf(this.b), Integer.valueOf(this.a.getAndIncrement())));
                }
            });
        } finally {
            this.b.unlock();
        }
    }

    private void releaseThreads() {
        this.b.lock();
        try {
            this.c.shutdownNow();
        } finally {
            this.b.unlock();
        }
    }

    private boolean isCancelled(int z, int x, int y) {
        return this.f.get(TileId.from(z, x, y)).get();
    }

    private static class a implements Runnable {
        private final long a;
        private final com.naver.maps.map.style.sources.a b;
        private final LongSparseArray<a> c;
        private final LongSparseArray<AtomicBoolean> d;
        private final WeakReference<CustomGeometrySource> e;
        private final AtomicBoolean f;

        a(long j, com.naver.maps.map.style.sources.a aVar, LongSparseArray<a> longSparseArray, LongSparseArray<AtomicBoolean> longSparseArray2, CustomGeometrySource customGeometrySource, AtomicBoolean atomicBoolean) {
            this.a = j;
            this.b = aVar;
            this.c = longSparseArray;
            this.d = longSparseArray2;
            this.e = new WeakReference<>(customGeometrySource);
            this.f = atomicBoolean;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.c) {
                synchronized (this.d) {
                    if (this.d.containsKey(this.a)) {
                        if (!this.c.containsKey(this.a)) {
                            this.c.put(this.a, this);
                        }
                        return;
                    }
                    this.d.put(this.a, this.f);
                    if (!a().booleanValue()) {
                        String strA = this.b.a(TileId.toLatLngBounds(this.a), TileId.z(this.a));
                        CustomGeometrySource customGeometrySource = this.e.get();
                        if (!a().booleanValue() && customGeometrySource != null && strA != null) {
                            customGeometrySource.a(TileId.z(this.a), TileId.x(this.a), TileId.y(this.a), strA);
                        }
                    }
                    synchronized (this.c) {
                        synchronized (this.d) {
                            this.d.remove(this.a);
                            if (this.c.containsKey(this.a)) {
                                a aVar = this.c.get(this.a);
                                CustomGeometrySource customGeometrySource2 = this.e.get();
                                if (customGeometrySource2 != null && aVar != null) {
                                    customGeometrySource2.c.execute(aVar);
                                }
                                this.c.remove(this.a);
                            }
                        }
                    }
                }
            }
        }

        private Boolean a() {
            return Boolean.valueOf(this.f.get());
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o != null && getClass() == o.getClass() && this.a == ((a) o).a) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (int) (this.a ^ (this.a >>> 32));
        }
    }
}
