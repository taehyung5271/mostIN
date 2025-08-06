package com.naver.maps.map.renderer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import com.naver.maps.map.internal.resource.LocalGlyphRasterizer;
import com.naver.maps.map.internal.resource.OverlayImageLoader;
import com.naver.maps.map.log.c;
import com.naver.maps.map.text.TypefaceFactory;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import okhttp3.internal.http2.Http2Connection;

/* loaded from: classes.dex */
public abstract class MapRenderer implements MapRendererScheduler {
    private final OverlayImageLoader a;
    private final LocalGlyphRasterizer b;
    private a c;
    private long d;
    private long e;
    private long f;
    private long handle;

    public interface a {
        void a();

        void b();
    }

    private native void nativeOnSurfaceChanged(int i, int i2);

    private native void nativeOnSurfaceCreated();

    private native void nativeOnSurfaceDestroyed();

    private native void nativeRender();

    protected abstract void a(MapRenderer mapRenderer, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer);

    protected abstract void b();

    static {
        com.naver.maps.map.internal.a.a();
    }

    private static int c(int i) {
        if (i <= 0) {
            return 0;
        }
        return Http2Connection.DEGRADED_PONG_TIMEOUT_NS / i;
    }

    public MapRenderer(Context context, float mapScale, Class<? extends TypefaceFactory> localTypefaceFactoryClass, boolean cjkLocalGlyphRasterizationEnabled) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.densityDpi = (int) (configuration.densityDpi * mapScale);
        Context contextCreateConfigurationContext = context.createConfigurationContext(configuration);
        this.a = new OverlayImageLoader(contextCreateConfigurationContext);
        try {
            this.b = new LocalGlyphRasterizer(localTypefaceFactoryClass.getConstructor(Context.class).newInstance(contextCreateConfigurationContext), contextCreateConfigurationContext.getResources().getDisplayMetrics().density, cjkLocalGlyphRasterizationEnabled);
            a(this, this.a, this.b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void finalize() throws Throwable {
        try {
            b();
        } finally {
            super.finalize();
        }
    }

    public void c() {
    }

    public void d() {
    }

    public void e() {
    }

    void queueEvent(MapRendererRunnable runnable) {
        queueEvent((Runnable) runnable);
    }

    public void a(int i) {
        this.f = c(i);
    }

    public void b(int i) {
        this.e = c(i);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        nativeOnSurfaceCreated();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (gl != null) {
            gl.glViewport(0, 0, width, height);
        }
        nativeOnSurfaceChanged(width, height);
    }

    public void a() {
        nativeOnSurfaceDestroyed();
    }

    public void onDrawFrame(GL10 gl) {
        f();
        if (this.c != null) {
            this.c.a();
        }
        try {
            nativeRender();
        } catch (Error e) {
            c.c("onDrawFrame(): " + e.getMessage(), new Object[0]);
        }
        if (this.c != null) {
            this.c.b();
        }
    }

    private void f() {
        long j;
        if (this.f == 0) {
            j = this.e;
        } else {
            j = this.f;
        }
        if (j == 0) {
            return;
        }
        long jNanoTime = System.nanoTime() - this.d;
        if (jNanoTime < j) {
            SystemClock.sleep((j - jNanoTime) / 1000000);
        }
        this.d = System.nanoTime();
    }
}
