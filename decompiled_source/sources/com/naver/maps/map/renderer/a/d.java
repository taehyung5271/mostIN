package com.naver.maps.map.renderer.a;

import android.content.Context;
import com.naver.maps.map.renderer.GLMapRenderer;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public abstract class d extends GLMapRenderer {
    protected abstract c f();

    public d(Context context, float f, Class<? extends TypefaceFactory> cls, boolean z) {
        super(context, f, cls, z);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    public void e() {
        f().b();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void requestRender() {
        f().a();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void queueEvent(Runnable runnable) {
        f().a(runnable);
    }
}
