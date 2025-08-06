package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class FeatureLayer {
    private final com.google.android.gms.internal.maps.zzs zza;
    private StyleFactory zzb;
    private final Map zzc = new HashMap();

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnFeatureClickListener {
        void onFeatureClick(FeatureClickEvent featureClickEvent);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface StyleFactory {
        FeatureStyle getStyle(Feature feature);
    }

    public FeatureLayer(com.google.android.gms.internal.maps.zzs zzsVar) {
        this.zza = (com.google.android.gms.internal.maps.zzs) Preconditions.checkNotNull(zzsVar);
    }

    public String getDatasetId() {
        try {
            return this.zza.zzd();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public StyleFactory getFeatureStyle() {
        return this.zzb;
    }

    @FeatureType
    public String getFeatureType() {
        try {
            return this.zza.zze();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isAvailable() {
        try {
            return this.zza.zzi();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void removeOnFeatureClickListener(OnFeatureClickListener listener) {
        try {
            if (this.zzc.containsKey(listener)) {
                this.zza.zzg((com.google.android.gms.internal.maps.zzaj) this.zzc.get(listener));
                this.zzc.remove(listener);
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setFeatureStyle(StyleFactory styleFactory) {
        this.zzb = styleFactory;
        if (styleFactory == null) {
            try {
                this.zza.zzh(null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            try {
                this.zza.zzh(new zzd(this, styleFactory));
            } catch (RemoteException e2) {
                throw new RuntimeRemoteException(e2);
            }
        }
    }

    public final void addOnFeatureClickListener(OnFeatureClickListener listener) {
        try {
            zze zzeVar = new zze(this, listener);
            this.zzc.put(listener, zzeVar);
            this.zza.zzf(zzeVar);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
