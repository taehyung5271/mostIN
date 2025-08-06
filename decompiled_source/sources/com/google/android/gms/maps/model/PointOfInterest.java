package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class PointOfInterest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PointOfInterest> CREATOR = new zzs();
    public final LatLng latLng;
    public final String name;
    public final String placeId;

    public PointOfInterest(LatLng latLng, String placeId, String name) {
        this.latLng = latLng;
        this.placeId = placeId;
        this.name = name;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        LatLng latLng = this.latLng;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeParcelable(out, 2, latLng, flags, false);
        SafeParcelWriter.writeString(out, 3, this.placeId, false);
        SafeParcelWriter.writeString(out, 4, this.name, false);
        SafeParcelWriter.finishObjectHeader(out, iBeginObjectHeader);
    }
}
