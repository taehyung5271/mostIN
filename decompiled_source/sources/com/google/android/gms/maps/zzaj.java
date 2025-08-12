package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbm;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzaj extends zzbm {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaChangeListener zza;

    zzaj(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaChangeListener onStreetViewPanoramaChangeListener) {
        this.zza = onStreetViewPanoramaChangeListener;
    }

    @Override // com.google.android.gms.maps.internal.zzbn
    public final void zzb(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        this.zza.onStreetViewPanoramaChange(streetViewPanoramaLocation);
    }
}
