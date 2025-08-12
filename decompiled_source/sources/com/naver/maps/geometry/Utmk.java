package com.naver.maps.geometry;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Utmk implements Coord, Parcelable {
    private static final double GRS80_A = 6378137.0d;
    private static final double GRS80_B = 6356752.3141403d;
    private static final double LON_0 = 127.5d;
    public static final double MAXIMUM_X = 1937760.8d;
    public static final double MAXIMUM_Y = 2619635.0d;
    public static final double MINIMUM_X = 283038.5d;
    public static final double MINIMUM_Y = 1248041.6d;
    private static final double SCALE_FACTOR = 0.9996d;
    private static final double TOTAL_SCALE = 6378137.0d;
    private static final double X_0 = 1000000.0d;
    private static final double Y_0 = 2000000.0d;
    public final double x;
    public final double y;
    public static final LatLngBounds COVERAGE = new LatLngBounds(new LatLng(31.0d, 120.0d), new LatLng(43.0d, 139.0d));
    private static final double LAT_0 = 38.0d;
    private static final double LAT_0_RADIAN = Math.toRadians(LAT_0);
    private static final double INV_RECIPROCAL_FLATTERING = 0.003352810681182319d;
    private static final double ECCENTRICITY_2 = 0.006705621362364638d - Math.pow(INV_RECIPROCAL_FLATTERING, 2.0d);
    private static final double ESP = ECCENTRICITY_2 / (1.0d - ECCENTRICITY_2);
    private static final double[] EN = TmProjection.enfn(ECCENTRICITY_2);
    private static final double ML_0 = TmProjection.mlfn(LAT_0_RADIAN, Math.sin(LAT_0_RADIAN), Math.cos(LAT_0_RADIAN), EN);
    public static final Parcelable.Creator<Utmk> CREATOR = new Parcelable.Creator<Utmk>() { // from class: com.naver.maps.geometry.Utmk.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Utmk createFromParcel(Parcel source) {
            return new Utmk(source.readDouble(), source.readDouble());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Utmk[] newArray(int size) {
            return new Utmk[size];
        }
    };

    public static Utmk valueOf(LatLng latLng) {
        Xyz coord = TmProjection.projectRadians(latLng.toRadians(), X_0, Y_0, LON_0, SCALE_FACTOR, 6378137.0d, ECCENTRICITY_2, ESP, ML_0, EN);
        return new Utmk(coord.x, coord.y);
    }

    public Utmk(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isValid() {
        return (Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isInfinite(this.x) || Double.isInfinite(this.y)) ? false : true;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isWithinCoverage() {
        return isValid() && this.x >= 283038.5d && this.x <= 1937760.8d && this.y >= 1248041.6d && this.y <= 2619635.0d;
    }

    @Override // com.naver.maps.geometry.Coord
    public LatLng toLatLng() {
        return LatLng.fromRadians(TmProjection.inverseProjectRadians(new Xyz(this.x, this.y), X_0, Y_0, LON_0, 6378137.0d, SCALE_FACTOR, ECCENTRICITY_2, ESP, ML_0, EN));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Utmk that = (Utmk) o;
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
        return "Utmk{x=" + this.x + ", y=" + this.y + '}';
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
