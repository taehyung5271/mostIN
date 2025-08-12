package com.google.android.gms.auth.api.signin.internal;

/* compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* loaded from: classes.dex */
public class HashAccumulator {
    private int zaa = 1;

    public HashAccumulator addObject(Object object) {
        this.zaa = (this.zaa * 31) + (object == null ? 0 : object.hashCode());
        return this;
    }

    public int hash() {
        return this.zaa;
    }

    public final HashAccumulator zaa(boolean z) {
        this.zaa = (this.zaa * 31) + (z ? 1 : 0);
        return this;
    }
}
