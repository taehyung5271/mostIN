package com.google.android.gms.internal.maps;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzbd {
    static void zza(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("null key in entry: null=".concat(String.valueOf(String.valueOf(obj2))));
        }
        if (obj2 != null) {
            return;
        }
        throw new NullPointerException("null value in entry: " + obj.toString() + "=null");
    }
}
