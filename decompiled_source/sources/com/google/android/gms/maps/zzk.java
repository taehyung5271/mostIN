package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.IndoorBuilding;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzk extends com.google.android.gms.maps.internal.zzaa {
    final /* synthetic */ GoogleMap.OnIndoorStateChangeListener zza;

    zzk(GoogleMap googleMap, GoogleMap.OnIndoorStateChangeListener onIndoorStateChangeListener) {
        this.zza = onIndoorStateChangeListener;
    }

    @Override // com.google.android.gms.maps.internal.zzab
    public final void zzb() {
        this.zza.onIndoorBuildingFocused();
    }

    @Override // com.google.android.gms.maps.internal.zzab
    public final void zzc(com.google.android.gms.internal.maps.zzy zzyVar) {
        this.zza.onIndoorLevelActivated(new IndoorBuilding(zzyVar));
    }
}
