package com.naver.maps.map.style.sources;

import android.os.Parcel;
import android.os.Parcelable;
import com.naver.maps.geometry.LatLng;

/* loaded from: classes.dex */
public class LatLngQuad implements Parcelable {
    public static final Parcelable.Creator<LatLngQuad> CREATOR = new Parcelable.Creator<LatLngQuad>() { // from class: com.naver.maps.map.style.sources.LatLngQuad.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public LatLngQuad createFromParcel(Parcel parcel) {
            return LatLngQuad.b(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public LatLngQuad[] newArray(int i) {
            return new LatLngQuad[i];
        }
    };
    private final LatLng bottomLeft;
    private final LatLng bottomRight;
    private final LatLng topLeft;
    private final LatLng topRight;

    public LatLngQuad(LatLng topLeft, LatLng topRight, LatLng bottomRight, LatLng bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public int hashCode() {
        int iHashCode = this.topLeft.hashCode();
        int iHashCode2 = (iHashCode ^ (iHashCode >>> 31)) + this.topRight.hashCode();
        int iHashCode3 = (iHashCode2 ^ (iHashCode2 >>> 31)) + this.bottomRight.hashCode();
        return (iHashCode3 ^ (iHashCode3 >>> 31)) + this.bottomLeft.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int arg1) {
        this.topLeft.writeToParcel(out, arg1);
        this.topRight.writeToParcel(out, arg1);
        this.bottomRight.writeToParcel(out, arg1);
        this.bottomLeft.writeToParcel(out, arg1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LatLngQuad b(Parcel parcel) {
        return new LatLngQuad((LatLng) parcel.readParcelable(LatLng.class.getClassLoader()), (LatLng) parcel.readParcelable(LatLng.class.getClassLoader()), (LatLng) parcel.readParcelable(LatLng.class.getClassLoader()), (LatLng) parcel.readParcelable(LatLng.class.getClassLoader()));
    }
}
