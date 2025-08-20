package com.google.android.gms.maps.model;

import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class PlaceFeature extends Feature {
    private final com.google.android.gms.internal.maps.zzp zza;

    public PlaceFeature(com.google.android.gms.internal.maps.zzp zzpVar) {
        super(zzpVar);
        this.zza = zzpVar;
    }

    public String getPlaceId() {
        try {
            return this.zza.zzg();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
