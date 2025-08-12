package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class StreetViewSource extends AbstractSafeParcelable {
    public static final Parcelable.Creator<StreetViewSource> CREATOR;
    public static final StreetViewSource DEFAULT;
    public static final StreetViewSource OUTDOOR;
    private final int zza;

    static {
        StreetViewSource.class.getSimpleName();
        CREATOR = new zzab();
        DEFAULT = new StreetViewSource(0);
        OUTDOOR = new StreetViewSource(1);
    }

    public StreetViewSource(int i) {
        this.zza = i;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof StreetViewSource) && this.zza == ((StreetViewSource) o).zza;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza));
    }

    public String toString() {
        String str;
        int i = this.zza;
        switch (i) {
            case 0:
                str = "DEFAULT";
                break;
            case 1:
                str = "OUTDOOR";
                break;
            default:
                str = String.format("UNKNOWN(%s)", Integer.valueOf(i));
                break;
        }
        return String.format("StreetViewSource:%s", str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int i) {
        int i2 = this.zza;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeInt(out, 2, i2);
        SafeParcelWriter.finishObjectHeader(out, iBeginObjectHeader);
    }
}
