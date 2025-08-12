package com.naver.maps.map.renderer.c;

import android.content.Context;
import android.view.TextureView;
import com.naver.maps.map.renderer.a.d;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public class a extends d {
    private final b a;

    public a(Context context, TextureView textureView, float f, Class<? extends TypefaceFactory> cls, boolean z, boolean z2, boolean z3) {
        super(context, f, cls, z);
        this.a = new b(this, textureView, z2, z3);
        this.a.start();
    }

    @Override // com.naver.maps.map.renderer.a.d
    /* renamed from: g, reason: merged with bridge method [inline-methods] */
    public b f() {
        return this.a;
    }
}
