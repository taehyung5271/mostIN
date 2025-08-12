package com.naver.maps.geometry;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class WebMercatorCoord implements Coord, Parcelable {
    public static final LatLngBounds COVERAGE = new LatLngBounds(new LatLng(-85.05112877980659d, -180.0d), new LatLng(85.05112877980659d, 180.0d));
    public static final Parcelable.Creator<WebMercatorCoord> CREATOR = new Parcelable.Creator<WebMercatorCoord>() { // from class: com.naver.maps.geometry.WebMercatorCoord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebMercatorCoord createFromParcel(Parcel source) {
            return new WebMercatorCoord(source.readDouble(), source.readDouble());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WebMercatorCoord[] newArray(int size) {
            return new WebMercatorCoord[size];
        }
    };
    public static final double MAXIMUM_LATITUDE = 85.05112877980659d;
    public static final double MAXIMUM_XY = 2.0037508342789244E7d;
    public static final double MINIMUM_LATITUDE = -85.05112877980659d;
    public static final double MINIMUM_XY = -2.0037508342789244E7d;
    public final double x;
    public final double y;

    public static WebMercatorCoord valueOf(LatLng latLng) {
        return new WebMercatorCoord(Math.toRadians(latLng.longitude * 6378137.0d), Math.log(Math.tan(Math.toRadians(latLng.latitude + 90.0d) / 2.0d)) * 6378137.0d);
    }

    public WebMercatorCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isValid() {
        return (Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isInfinite(this.x) || Double.isInfinite(this.y)) ? false : true;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isWithinCoverage() {
        return isValid() && this.x >= -2.0037508342789244E7d && this.x <= 2.0037508342789244E7d && this.y >= -2.0037508342789244E7d && this.y <= 2.0037508342789244E7d;
    }

    public double distanceTo(WebMercatorCoord other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2.0d) + Math.pow(other.y - this.y, 2.0d));
    }

    public double bearingTo(WebMercatorCoord other) {
        double angle = Math.atan2(other.x - this.x, other.y - this.y);
        return (float) Math.toDegrees(angle < 0.0d ? 6.283185307179586d + angle : angle);
    }

    @Override // com.naver.maps.geometry.Coord
    public LatLng toLatLng() {
        return new LatLng(Math.toDegrees((Math.atan(Math.exp(this.y / 6378137.0d)) * 2.0d) - 1.5707963267948966d), Math.toDegrees(this.x) / 6378137.0d);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebMercatorCoord that = (WebMercatorCoord) o;
        if (Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.x);
        int result = (int) ((temp >>> 32) ^ temp);
        long temp2 = Double.doubleToLongBits(this.y);
        return (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
    }

    public String toString() {
        return "WebMercatorCoord{x=" + this.x + ", y=" + this.y + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
    }
}
