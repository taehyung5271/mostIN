package com.naver.maps.map;

import android.os.Parcel;
import android.os.Parcelable;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.MathUtils;

/* loaded from: classes.dex */
public final class CameraPosition implements Parcelable {
    public final double bearing;
    public final LatLng target;
    public final double tilt;
    public final double zoom;
    public static final CameraPosition INVALID = new CameraPosition(LatLng.INVALID, Double.NaN, Double.NaN, Double.NaN);
    public static final Parcelable.Creator<CameraPosition> CREATOR = new Parcelable.Creator<CameraPosition>() { // from class: com.naver.maps.map.CameraPosition.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public CameraPosition createFromParcel(Parcel parcel) {
            return new CameraPosition(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public CameraPosition[] newArray(int i) {
            return new CameraPosition[i];
        }
    };

    public CameraPosition(LatLng target, double zoom) {
        this(target, zoom, 0.0d, 0.0d);
    }

    public CameraPosition(LatLng target, double zoom, double tilt, double bearing) {
        this.target = target;
        this.zoom = MathUtils.clamp(zoom, 0.0d, 21.0d);
        this.tilt = MathUtils.clamp(tilt, 0.0d, 63.0d);
        this.bearing = MathUtils.wrap(bearing, 0.0d, 360.0d);
    }

    protected CameraPosition(Parcel in) {
        this.target = (LatLng) in.readParcelable(LatLng.class.getClassLoader());
        this.zoom = in.readDouble();
        this.tilt = in.readDouble();
        this.bearing = in.readDouble();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) o;
        if (Double.compare(cameraPosition.zoom, this.zoom) != 0 || Double.compare(cameraPosition.tilt, this.tilt) != 0 || Double.compare(cameraPosition.bearing, this.bearing) != 0) {
            return false;
        }
        return this.target.equals(cameraPosition.target);
    }

    public int hashCode() {
        int iHashCode = this.target.hashCode();
        long jDoubleToLongBits = Double.doubleToLongBits(this.zoom);
        int i = (iHashCode * 31) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
        long jDoubleToLongBits2 = Double.doubleToLongBits(this.tilt);
        int i2 = (i * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)));
        long jDoubleToLongBits3 = Double.doubleToLongBits(this.bearing);
        return (i2 * 31) + ((int) (jDoubleToLongBits3 ^ (jDoubleToLongBits3 >>> 32)));
    }

    public String toString() {
        return "CameraPosition{target=" + this.target + ", zoom=" + this.zoom + ", tilt=" + this.tilt + ", bearing=" + this.bearing + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.target, flags);
        dest.writeDouble(this.zoom);
        dest.writeDouble(this.tilt);
        dest.writeDouble(this.bearing);
    }
}
