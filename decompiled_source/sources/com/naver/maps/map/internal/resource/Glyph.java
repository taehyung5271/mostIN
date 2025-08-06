package com.naver.maps.map.internal.resource;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
class Glyph {
    public final int advance;
    public final Bitmap bitmap;
    public final int id;
    public final int left;
    public final int top;

    Glyph(int id, Bitmap bitmap, int left, int top, int advance) {
        this.id = id;
        this.bitmap = bitmap;
        this.left = left;
        this.top = top;
        this.advance = advance;
    }
}
