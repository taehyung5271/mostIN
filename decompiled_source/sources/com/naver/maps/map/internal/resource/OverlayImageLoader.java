package com.naver.maps.map.internal.resource;

import android.content.Context;
import android.graphics.Bitmap;
import com.naver.maps.map.log.c;
import com.naver.maps.map.overlay.OverlayImage;

/* loaded from: classes.dex */
public final class OverlayImageLoader {
    private final Context a;
    private long handle;

    private native void nativeCreate(OverlayImageLoader overlayImageLoader);

    private native void nativeDestroy();

    public OverlayImageLoader(Context scaledContext) {
        nativeCreate(this);
        this.a = scaledContext;
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    public Bitmap[] loadOverlayImages(OverlayImage[] overlayImages) {
        Bitmap[] bitmapArr = new Bitmap[overlayImages.length];
        for (int i = 0; i < overlayImages.length; i++) {
            Bitmap bitmap = overlayImages[i].getBitmap(this.a);
            if (bitmap == null) {
                c.d("Cannot decode bitmap: " + overlayImages[i].toString(), new Object[0]);
            } else if (bitmap.isRecycled()) {
                c.d("Bitmap is recycled: " + overlayImages[i].toString(), new Object[0]);
            } else if (bitmap.getConfig() != Bitmap.Config.ARGB_8888 && (bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)) == null) {
                c.d("Failed to copy a bitmap: " + overlayImages[i].toString(), new Object[0]);
            } else {
                bitmapArr[i] = bitmap;
            }
        }
        return bitmapArr;
    }
}
