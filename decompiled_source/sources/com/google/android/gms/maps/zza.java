package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zza extends com.google.android.gms.maps.internal.zzau {
    final /* synthetic */ GoogleMap.OnMarkerClickListener zza;

    zza(GoogleMap googleMap, GoogleMap.OnMarkerClickListener onMarkerClickListener) {
        this.zza = onMarkerClickListener;
    }

    @Override // com.google.android.gms.maps.internal.zzav
    public final boolean zzb(com.google.android.gms.internal.maps.zzah zzahVar) {
        return this.zza.onMarkerClick(new Marker(zzahVar));
    }
}
