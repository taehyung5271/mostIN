package com.naver.maps.map.renderer.b;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.naver.maps.map.renderer.GLMapRenderer;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public class a extends GLMapRenderer implements GLSurfaceView.Renderer {
    private final GLSurfaceView a;

    public a(Context context, GLSurfaceView gLSurfaceView, float f, Class<? extends TypefaceFactory> cls, boolean z, boolean z2, boolean z3, boolean z4) {
        super(context, f, cls, z);
        this.a = gLSurfaceView;
        gLSurfaceView.setEGLContextClientVersion(2);
        gLSurfaceView.setEGLConfigChooser(new com.naver.maps.map.renderer.a.a(z2, false));
        gLSurfaceView.setRenderer(this);
        gLSurfaceView.setRenderMode(0);
        gLSurfaceView.setZOrderMediaOverlay(z3);
        gLSurfaceView.setPreserveEGLContextOnPause(z4);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    public void c() {
        this.a.onPause();
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    public void d() {
        this.a.onResume();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void requestRender() {
        this.a.requestRender();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void queueEvent(Runnable runnable) {
        this.a.queueEvent(runnable);
    }
}
