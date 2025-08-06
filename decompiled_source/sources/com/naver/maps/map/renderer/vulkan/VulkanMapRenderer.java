package com.naver.maps.map.renderer.vulkan;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.naver.maps.map.internal.resource.LocalGlyphRasterizer;
import com.naver.maps.map.internal.resource.OverlayImageLoader;
import com.naver.maps.map.renderer.MapRenderer;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public class VulkanMapRenderer extends MapRenderer {
    private final VulkanSurfaceView a;

    private native void nativeCreate(MapRenderer mapRenderer, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer);

    private native void nativeCreateSurface(Surface surface);

    private native void nativeDestroy();

    private native void nativeDestroySurface();

    private native boolean nativeIsSupported();

    public VulkanMapRenderer(Context context, VulkanSurfaceView vkSurfaceView, float mapScale, Class<? extends TypefaceFactory> localTypefaceFactoryClass, boolean cjkLocalGlyphRasterizationEnabled, boolean zOrderMediaOverlay) {
        super(context, mapScale, localTypefaceFactoryClass, cjkLocalGlyphRasterizationEnabled);
        this.a = vkSurfaceView;
        vkSurfaceView.setRenderer(this);
        vkSurfaceView.setRenderMode(0);
        vkSurfaceView.setZOrderMediaOverlay(zOrderMediaOverlay);
        vkSurfaceView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.naver.maps.map.renderer.vulkan.VulkanMapRenderer.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
                Display display = ViewCompat.getDisplay(v);
                if (display != null) {
                    VulkanMapRenderer.this.b((int) display.getRefreshRate());
                } else {
                    VulkanMapRenderer.this.b(60);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
            }
        });
        b(60);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    protected final void a(MapRenderer mapRenderer, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer) {
        nativeCreate(mapRenderer, overlayImageLoader, localGlyphRasterizer);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    protected final void b() {
        nativeDestroy();
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    public void c() {
        this.a.b();
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    public void d() {
        this.a.c();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void requestRender() {
        this.a.a();
    }

    @Override // com.naver.maps.map.renderer.MapRendererScheduler
    public void queueEvent(Runnable runnable) {
        this.a.a(runnable);
    }

    public void a(Surface surface) {
        nativeCreateSurface(surface);
    }

    public boolean f() {
        return nativeIsSupported();
    }

    public void g() {
        nativeDestroySurface();
    }
}
