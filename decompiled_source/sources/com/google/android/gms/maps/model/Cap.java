package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public class Cap extends AbstractSafeParcelable {
    private final int zzb;
    private final BitmapDescriptor zzc;
    private final Float zzd;
    private static final String zza = Cap.class.getSimpleName();
    public static final Parcelable.Creator<Cap> CREATOR = new zzb();

    protected Cap(int i) {
        this(i, (BitmapDescriptor) null, (Float) null);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cap)) {
            return false;
        }
        Cap cap = (Cap) o;
        return this.zzb == cap.zzb && Objects.equal(this.zzc, cap.zzc) && Objects.equal(this.zzd, cap.zzd);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzb), this.zzc, this.zzd);
    }

    public String toString() {
        return "[Cap: type=" + this.zzb + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int i) {
        int i2 = this.zzb;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 2, i2);
        BitmapDescriptor bitmapDescriptor = this.zzc;
        SafeParcelWriter.writeIBinder(out, 3, bitmapDescriptor == null ? null : bitmapDescriptor.zza().asBinder(), false);
        SafeParcelWriter.writeFloatObject(out, 4, this.zzd, false);
        SafeParcelWriter.finishObjectHeader(out, iBeginObjectHeader);
    }

    final Cap zza() {
        int i = this.zzb;
        switch (i) {
            case 0:
                return new ButtCap();
            case 1:
                return new SquareCap();
            case 2:
                return new RoundCap();
            case 3:
                Preconditions.checkState(this.zzc != null, "bitmapDescriptor must not be null");
                Preconditions.checkState(this.zzd != null, "bitmapRefWidth must not be null");
                return new CustomCap(this.zzc, this.zzd.floatValue());
            default:
                Log.w(zza, "Unknown Cap type: " + i);
                return this;
        }
    }

    Cap(int i, IBinder iBinder, Float f) {
        this(i, iBinder == null ? null : new BitmapDescriptor(IObjectWrapper.Stub.asInterface(iBinder)), f);
    }

    private Cap(int i, BitmapDescriptor bitmapDescriptor, Float f) {
        boolean z = true;
        boolean z2 = f != null && f.floatValue() > 0.0f;
        if (i == 3) {
            if (bitmapDescriptor != null && z2) {
                i = 3;
            } else {
                z = false;
                i = 3;
            }
        }
        Preconditions.checkArgument(z, String.format("Invalid Cap: type=%s bitmapDescriptor=%s bitmapRefWidth=%s", Integer.valueOf(i), bitmapDescriptor, f));
        this.zzb = i;
        this.zzc = bitmapDescriptor;
        this.zzd = f;
    }

    protected Cap(BitmapDescriptor bitmapDescriptor, float bitmapRefWidth) {
        this(3, bitmapDescriptor, Float.valueOf(bitmapRefWidth));
    }
}
