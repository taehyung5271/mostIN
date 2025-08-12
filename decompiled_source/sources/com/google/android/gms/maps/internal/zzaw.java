package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public abstract class zzaw extends com.google.android.gms.internal.maps.zzb implements zzax {
    public zzaw() {
        super("com.google.android.gms.maps.internal.IOnMarkerDragListener");
    }

    @Override // com.google.android.gms.internal.maps.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                com.google.android.gms.internal.maps.zzah zzahVarZzb = com.google.android.gms.internal.maps.zzag.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                zzd(zzahVarZzb);
                break;
            case 2:
                com.google.android.gms.internal.maps.zzah zzahVarZzb2 = com.google.android.gms.internal.maps.zzag.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                zzb(zzahVarZzb2);
                break;
            case 3:
                com.google.android.gms.internal.maps.zzah zzahVarZzb3 = com.google.android.gms.internal.maps.zzag.zzb(parcel.readStrongBinder());
                com.google.android.gms.internal.maps.zzc.zzd(parcel);
                zzc(zzahVarZzb3);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
