package com.naver.maps.map.renderer;

import android.content.Context;
import com.naver.maps.map.internal.resource.LocalGlyphRasterizer;
import com.naver.maps.map.internal.resource.OverlayImageLoader;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public abstract class GLMapRenderer extends MapRenderer {
    private native void nativeCreate(MapRenderer mapRenderer, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer);

    private native void nativeDestroy();

    public GLMapRenderer(Context context, float mapScale, Class<? extends TypefaceFactory> localTypefaceFactoryClass, boolean cjkLocalGlyphRasterizationEnabled) {
        super(context, mapScale, localTypefaceFactoryClass, cjkLocalGlyphRasterizationEnabled);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    protected final void a(MapRenderer mapRenderer, OverlayImageLoader overlayImageLoader, LocalGlyphRasterizer localGlyphRasterizer) {
        nativeCreate(mapRenderer, overlayImageLoader, localGlyphRasterizer);
    }

    @Override // com.naver.maps.map.renderer.MapRenderer
    protected final void b() {
        nativeDestroy();
    }
}
