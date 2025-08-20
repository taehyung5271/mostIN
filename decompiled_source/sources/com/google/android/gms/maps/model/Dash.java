package com.google.android.gms.maps.model;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class Dash extends PatternItem {
    public final float length;

    public Dash(float length) {
        super(0, Float.valueOf(Math.max(length, 0.0f)));
        this.length = Math.max(length, 0.0f);
    }

    @Override // com.google.android.gms.maps.model.PatternItem
    public String toString() {
        return "[Dash: length=" + this.length + "]";
    }
}
