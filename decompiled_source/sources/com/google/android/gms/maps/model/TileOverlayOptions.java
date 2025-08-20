package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.maps.zzaw;
import com.google.android.gms.internal.maps.zzax;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class TileOverlayOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<TileOverlayOptions> CREATOR = new zzak();
    private zzax zza;
    private TileProvider zzb;
    private boolean zzc;
    private float zzd;
    private boolean zze;
    private float zzf;

    public TileOverlayOptions() {
        this.zzc = true;
        this.zze = true;
        this.zzf = 0.0f;
    }

    public TileOverlayOptions fadeIn(boolean z) {
        this.zze = z;
        return this;
    }

    public boolean getFadeIn() {
        return this.zze;
    }

    public TileProvider getTileProvider() {
        return this.zzb;
    }

    public float getTransparency() {
        return this.zzf;
    }

    public float getZIndex() {
        return this.zzd;
    }

    public boolean isVisible() {
        return this.zzc;
    }

    public TileOverlayOptions tileProvider(TileProvider tileProvider) {
        this.zzb = (TileProvider) Preconditions.checkNotNull(tileProvider, "tileProvider must not be null.");
        this.zza = new zzaj(this, tileProvider);
        return this;
    }

    public TileOverlayOptions transparency(float transparency) {
        boolean z = false;
        if (transparency >= 0.0f && transparency <= 1.0f) {
            z = true;
        }
        Preconditions.checkArgument(z, "Transparency must be in the range [0..1]");
        this.zzf = transparency;
        return this;
    }

    public TileOverlayOptions visible(boolean z) {
        this.zzc = z;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        zzax zzaxVar = this.zza;
        SafeParcelWriter.writeIBinder(out, 2, zzaxVar == null ? null : zzaxVar.asBinder(), false);
        SafeParcelWriter.writeBoolean(out, 3, isVisible());
        SafeParcelWriter.writeFloat(out, 4, getZIndex());
        SafeParcelWriter.writeBoolean(out, 5, getFadeIn());
        SafeParcelWriter.writeFloat(out, 6, getTransparency());
        SafeParcelWriter.finishObjectHeader(out, iBeginObjectHeader);
    }

    public TileOverlayOptions zIndex(float f) {
        this.zzd = f;
        return this;
    }

    TileOverlayOptions(IBinder iBinder, boolean z, float f, boolean z2, float f2) {
        this.zzc = true;
        this.zze = true;
        this.zzf = 0.0f;
        this.zza = zzaw.zzc(iBinder);
        this.zzb = this.zza == null ? null : new zzai(this);
        this.zzc = z;
        this.zzd = f;
        this.zze = z2;
        this.zzf = f2;
    }
}
