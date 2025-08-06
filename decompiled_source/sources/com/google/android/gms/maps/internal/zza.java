package com.google.android.gms.maps.internal;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class zza {
    public static byte zza(Boolean bool) {
        if (bool != null) {
            return !bool.booleanValue() ? (byte) 0 : (byte) 1;
        }
        return (byte) -1;
    }

    public static Boolean zzb(byte b) {
        switch (b) {
            case 0:
                return Boolean.FALSE;
            case 1:
                return Boolean.TRUE;
            default:
                return null;
        }
    }
}
