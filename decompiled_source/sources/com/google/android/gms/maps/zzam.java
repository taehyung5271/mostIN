package com.google.android.gms.maps;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.internal.zzbq;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzam extends zzbq {
    final /* synthetic */ StreetViewPanorama.OnStreetViewPanoramaLongClickListener zza;

    zzam(StreetViewPanorama streetViewPanorama, StreetViewPanorama.OnStreetViewPanoramaLongClickListener onStreetViewPanoramaLongClickListener) {
        this.zza = onStreetViewPanoramaLongClickListener;
    }

    @Override // com.google.android.gms.maps.internal.zzbr
    public final void zzb(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        this.zza.onStreetViewPanoramaLongClick(streetViewPanoramaOrientation);
    }
}
