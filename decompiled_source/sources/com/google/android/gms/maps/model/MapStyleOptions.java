package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.IOUtils;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class MapStyleOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<MapStyleOptions> CREATOR;
    private final String zza;

    static {
        MapStyleOptions.class.getSimpleName();
        CREATOR = new zzo();
    }

    public MapStyleOptions(String json) {
        Preconditions.checkNotNull(json, "json must not be null");
        this.zza = json;
    }

    public static MapStyleOptions loadRawResourceStyle(Context clientContext, int resourceId) throws Resources.NotFoundException {
        try {
            return new MapStyleOptions(new String(IOUtils.readInputStreamFully(clientContext.getResources().openRawResource(resourceId)), "UTF-8"));
        } catch (IOException e) {
            throw new Resources.NotFoundException("Failed to read resource " + resourceId + ": " + e.toString());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int i) {
        String str = this.zza;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(out);
        SafeParcelWriter.writeString(out, 2, str, false);
        SafeParcelWriter.finishObjectHeader(out, iBeginObjectHeader);
    }
}
