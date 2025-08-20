package com.google.android.gms.maps;

import android.location.Location;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzba;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzg extends zzba {
    final /* synthetic */ GoogleMap.OnMyLocationChangeListener zza;

    zzg(GoogleMap googleMap, GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener) {
        this.zza = onMyLocationChangeListener;
    }

    @Override // com.google.android.gms.maps.internal.zzbb
    public final void zzb(IObjectWrapper iObjectWrapper) {
        this.zza.onMyLocationChange((Location) ObjectWrapper.unwrap(iObjectWrapper));
    }
}
