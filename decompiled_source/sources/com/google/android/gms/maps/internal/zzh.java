package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public abstract class zzh extends com.google.android.gms.internal.maps.zzb implements zzi {
    public zzh() {
        super("com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                com.google.android.gms.internal.maps.zzah zzahVarZzb = com.google.android.gms.internal.maps.zzag.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                IObjectWrapper iObjectWrapperZzc = zzc(zzahVarZzb);
                parcel2.writeNoException();
                com.google.android.gms.internal.maps.zzc.zzg(parcel2, iObjectWrapperZzc);
                return true;
            case 2:
                com.google.android.gms.internal.maps.zzah zzahVarZzb2 = com.google.android.gms.internal.maps.zzag.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                IObjectWrapper iObjectWrapperZzb = zzb(zzahVarZzb2);
                parcel2.writeNoException();
                com.google.android.gms.internal.maps.zzc.zzg(parcel2, iObjectWrapperZzb);
                return true;
            default:
                return false;
        }
    }
}
