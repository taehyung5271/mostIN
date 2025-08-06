package com.naver.maps.map.text;

import android.content.Context;
import android.graphics.Typeface;

/* loaded from: classes.dex */
public class DefaultTypefaceFactory implements TypefaceFactory {
    public DefaultTypefaceFactory(Context context) {
    }

    @Override // com.naver.maps.map.text.TypefaceFactory
    public Typeface getTypeface(boolean bold, int codePoint) {
        return bold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT;
    }
}
