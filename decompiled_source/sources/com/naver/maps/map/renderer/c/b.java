package com.naver.maps.map.renderer.c;

import android.graphics.SurfaceTexture;
import android.view.TextureView;
import com.naver.maps.map.renderer.a.c;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
class b extends c implements TextureView.SurfaceTextureListener {

    private static class a implements c.b {
        private final WeakReference<TextureView> a;

        private a(TextureView textureView) {
            this.a = new WeakReference<>(textureView);
        }

        @Override // com.naver.maps.map.renderer.a.c.b
        public boolean a() {
            return this.a.get() != null;
        }

        @Override // com.naver.maps.map.renderer.a.c.b
        public Object b() {
            TextureView textureView = this.a.get();
            if (textureView == null) {
                return null;
            }
            return textureView.getSurfaceTexture();
        }
    }

    b(com.naver.maps.map.renderer.c.a aVar, TextureView textureView, boolean z, boolean z2) {
        super(aVar, new a(textureView), z, z2);
        textureView.setOpaque(!z2);
        textureView.setSurfaceTextureListener(this);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        synchronized (this.a) {
            this.k = surface;
            this.i = width;
            this.j = height;
            this.b = true;
            this.a.notifyAll();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        synchronized (this.a) {
            this.i = width;
            this.j = height;
            this.c = true;
            this.b = true;
            this.a.notifyAll();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this.a) {
            this.k = null;
            this.f = true;
            this.b = false;
            this.a.notifyAll();
        }
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}
