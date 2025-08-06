package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public abstract class zzaa extends com.google.android.gms.internal.maps.zzb implements zzab {
    public zzaa() {
        super("com.google.android.gms.maps.internal.IOnIndoorStateChangeListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zzb();
                break;
            case 2:
                com.google.android.gms.internal.maps.zzy zzyVarZzb = com.google.android.gms.internal.maps.zzx.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                zzc(zzyVarZzb);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
