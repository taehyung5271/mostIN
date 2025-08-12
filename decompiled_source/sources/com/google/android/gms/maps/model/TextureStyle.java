package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.StampStyle;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public class TextureStyle extends StampStyle {

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public static final class Builder extends StampStyle.Builder<Builder> {
        private Builder() {
            throw null;
        }

        /* synthetic */ Builder(zzaf zzafVar) {
        }

        public TextureStyle build() {
            return new TextureStyle(this.zza, null);
        }

        @Override // com.google.android.gms.maps.model.StampStyle.Builder
        protected final /* bridge */ /* synthetic */ StampStyle.Builder self() {
            return this;
        }

        @Override // com.google.android.gms.maps.model.StampStyle.Builder
        protected Builder self() {
            return this;
        }
    }

    /* synthetic */ TextureStyle(BitmapDescriptor bitmapDescriptor, zzag zzagVar) {
        super(bitmapDescriptor);
    }

    public static Builder newBuilder(BitmapDescriptor stamp) {
        return new Builder(null).stamp(stamp);
    }
}
