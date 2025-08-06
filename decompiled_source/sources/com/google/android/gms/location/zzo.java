package com.google.android.gms.location;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
public final class zzo {
    public static int zza(int i) {
        boolean z = true;
        if (i != 0 && i != 1) {
            if (i == 2) {
                i = 2;
            } else {
                z = false;
            }
        }
        Preconditions.checkArgument(z, "granularity %d must be a Granularity.GRANULARITY_* constant", Integer.valueOf(i));
        return i;
    }

    public static String zzb(int i) {
        switch (i) {
            case 0:
                return "GRANULARITY_PERMISSION_LEVEL";
            case 1:
                return "GRANULARITY_COARSE";
            case 2:
                return "GRANULARITY_FINE";
            default:
                throw new IllegalArgumentException();
        }
    }
}
