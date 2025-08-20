package com.naver.maps.geometry;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class LatLng implements Coord, Parcelable {
    public static final double MAXIMUM_LATITUDE = 90.0d;
    public static final double MAXIMUM_LONGITUDE = 180.0d;
    public static final double MINIMUM_LATITUDE = -90.0d;
    public static final double MINIMUM_LONGITUDE = -180.0d;
    public final double latitude;
    public final double longitude;
    public static final LatLng INVALID = new LatLng(Double.NaN, Double.NaN);
    public static LatLngBounds COVERAGE = new LatLngBounds(new LatLng(-90.0d, -180.0d), new LatLng(90.0d, 180.0d));
    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() { // from class: com.naver.maps.geometry.LatLng.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLng createFromParcel(Parcel source) {
            return new LatLng(source.readDouble(), source.readDouble());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };

    static LatLng fromRadians(Xyz src) {
        return new LatLng(Math.toDegrees(src.y), Math.toDegrees(src.x));
    }

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng(Location location) {
        this(location.getLatitude(), location.getLongitude());
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isValid() {
        return (Double.isNaN(this.latitude) || Double.isNaN(this.longitude) || Double.isInfinite(this.latitude) || Double.isInfinite(this.longitude)) ? false : true;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isWithinCoverage() {
        if (!isValid()) {
            return false;
        }
        return COVERAGE.contains(this);
    }

    public LatLng wrap() {
        if (this.longitude < -180.0d || this.longitude > 180.0d) {
            return new LatLng(this.latitude, MathUtils.wrap(this.longitude, -180.0d, 180.0d));
        }
        return this;
    }

    public double distanceTo(LatLng other) {
        if (this.latitude == other.latitude && this.longitude == other.longitude) {
            return 0.0d;
        }
        double lat1 = Math.toRadians(this.latitude);
        double lng1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(other.latitude);
        double lng2 = Math.toRadians(other.longitude);
        return Math.asin(Math.sqrt(Math.pow(Math.sin((lat1 - lat2) / 2.0d), 2.0d) + (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lng1 - lng2) / 2.0d), 2.0d)))) * 1.2756274E7d;
    }

    public LatLng offset(double northMeter, double eastMeter) {
        return new LatLng(this.latitude + Math.toDegrees(northMeter / 6378137.0d), this.longitude + Math.toDegrees(eastMeter / (Math.cos(Math.toRadians(this.latitude)) * 6378137.0d)));
    }

    @Override // com.naver.maps.geometry.Coord
    public LatLng toLatLng() {
        return this;
    }

    Xyz toRadians() {
        return new Xyz(Math.toRadians(this.longitude), Math.toRadians(this.latitude));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LatLng latLng = (LatLng) o;
        if (Double.compare(latLng.latitude, this.latitude) == 0 && Double.compare(latLng.longitude, this.longitude) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.latitude);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.longitude);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }

    public String toString() {
        return "LatLng{latitude=" + this.latitude + ", longitude=" + this.longitude + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }
}
