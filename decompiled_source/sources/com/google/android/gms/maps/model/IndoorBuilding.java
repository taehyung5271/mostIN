package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class IndoorBuilding {
    private final com.google.android.gms.internal.maps.zzy zza;

    public IndoorBuilding(com.google.android.gms.internal.maps.zzy zzyVar) {
        zzl zzlVar = zzl.zza;
        this.zza = (com.google.android.gms.internal.maps.zzy) Preconditions.checkNotNull(zzyVar, "delegate");
    }

    public boolean equals(@Nullable Object other) {
        if (!(other instanceof IndoorBuilding)) {
            return false;
        }
        try {
            return this.zza.zzh(((IndoorBuilding) other).zza);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getActiveLevelIndex() {
        try {
            return this.zza.zzd();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getDefaultLevelIndex() {
        try {
            return this.zza.zze();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public List<IndoorLevel> getLevels() {
        try {
            List listZzg = this.zza.zzg();
            ArrayList arrayList = new ArrayList(listZzg.size());
            Iterator it = listZzg.iterator();
            while (it.hasNext()) {
                arrayList.add(new IndoorLevel(com.google.android.gms.internal.maps.zzaa.zzb((IBinder) it.next())));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.zza.zzf();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isUnderground() {
        try {
            return this.zza.zzi();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
