package com.google.android.gms.internal.maps;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
final class zzbg extends zzbc {
    private final zzbi zza;

    zzbg(zzbi zzbiVar, int i) {
        super(zzbiVar.size(), i);
        this.zza = zzbiVar;
    }

    @Override // com.google.android.gms.internal.maps.zzbc
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
