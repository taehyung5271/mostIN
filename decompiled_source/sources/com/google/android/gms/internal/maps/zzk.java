package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public abstract class zzk extends zzb implements zzl {
    public static zzl zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
        return iInterfaceQueryLocalInterface instanceof zzl ? (zzl) iInterfaceQueryLocalInterface : new zzj(iBinder);
    }
}
