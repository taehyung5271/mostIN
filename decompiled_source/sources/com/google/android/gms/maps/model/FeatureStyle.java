package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class FeatureStyle extends AbstractSafeParcelable {
    public static final Parcelable.Creator<FeatureStyle> CREATOR = new zzi();
    private final Integer zza;
    private final Integer zzb;
    private final Float zzc;
    private final Float zzd;

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public static final class Builder {
        private Integer zza;
        private Integer zzb;
        private Float zzc;
        private Float zzd;

        public FeatureStyle build() {
            return new FeatureStyle(this, null);
        }

        public Builder fillColor(int fillColor) {
            this.zza = Integer.valueOf(fillColor);
            return this;
        }

        public Builder pointRadius(float pointRadius) {
            Preconditions.checkArgument(pointRadius >= 0.0f, "Point radius cannot be negative.");
            Preconditions.checkArgument(pointRadius <= 128.0f, "The max allowed pointRadius value is 128px.");
            this.zzd = Float.valueOf(pointRadius);
            return this;
        }

        public Builder strokeColor(int strokeColor) {
            this.zzb = Integer.valueOf(strokeColor);
            return this;
        }

        public Builder strokeWidth(float strokeWidth) {
            Preconditions.checkArgument(strokeWidth >= 0.0f, "Stroke width cannot be negative.");
            this.zzc = Float.valueOf(strokeWidth);
            return this;
        }
    }

    /* synthetic */ FeatureStyle(Builder builder, zzh zzhVar) {
        this.zza = builder.zza;
        this.zzb = builder.zzb;
        this.zzc = builder.zzc;
        this.zzd = builder.zzd;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getFillColor() {
        return this.zza;
    }

    public Float getPointRadius() {
        return this.zzd;
    }

    public Integer getStrokeColor() {
        return this.zzb;
    }

    public Float getStrokeWidth() {
        return this.zzc;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerObject(parcel, 1, getFillColor(), false);
        SafeParcelWriter.writeIntegerObject(parcel, 2, getStrokeColor(), false);
        SafeParcelWriter.writeFloatObject(parcel, 3, getStrokeWidth(), false);
        SafeParcelWriter.writeFloatObject(parcel, 4, getPointRadius(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    FeatureStyle(Integer num, Integer num2, Float f, Float f2) {
        this.zza = num;
        this.zzb = num2;
        this.zzc = f;
        this.zzd = f2;
    }
}
