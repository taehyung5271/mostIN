package com.naver.maps.geometry;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class Tm128 implements Coord, Parcelable {
    private static final double BESSEL_A = 6377397.155d;
    private static final double BESSEL_A2 = 4.067119447260209E13d;
    private static final double BESSEL_B = 6356078.962818189d;
    private static final double BESSEL_B2 = 4.039973978157994E13d;
    private static final double BESSEL_E2 = 0.006674372231802045d;
    private static final double BESSEL_EP2 = 0.006719218799174659d;
    private static final double DX_BF = -145.907d;
    private static final double DY_BF = 505.034d;
    private static final double DZ_BF = 685.756d;
    private static final double GENAU = 1.0E-12d;
    private static final double GENAU_SQUARE = 1.0E-24d;
    private static final double LON_0 = 128.0d;
    public static final double MAXIMUM_X = 749976.0946343569d;
    public static final double MAXIMUM_Y = 643904.8888573726d;
    private static final int MAX_ITER = 6;
    public static final double MINIMUM_X = 30408.747066328477d;
    public static final double MINIMUM_Y = 158674.67403835512d;
    private static final double M_BF = 1.000006342d;
    private static final double RX_BF = -5.6335349744928075E-6d;
    private static final double RY_BF = 1.137857709564081E-5d;
    private static final double RZ_BF = 7.718233803263813E-6d;
    private static final double SCALE_FACTOR = 0.9999d;
    private static final double SECONDS_TO_RAD = 4.84813681109536E-6d;
    private static final double TOTAL_SCALE = 6377397.155d;
    private static final double X_0 = 400000.0d;
    private static final double Y_0 = 600000.0d;
    public final double x;
    public final double y;
    public static final Tm128 INVALID = new Tm128(Double.NaN, Double.NaN);
    public static final LatLngBounds COVERAGE = new LatLngBounds(new LatLng(33.96d, 124.0d), new LatLng(38.33d, 132.0d));
    private static final double LAT_0 = 38.0d;
    private static final double LAT_0_RADIAN = Math.toRadians(LAT_0);
    private static final double WGS84_A = 6378137.0d;
    private static final double WGS84_A2 = Math.pow(WGS84_A, 2.0d);
    private static final double WGS84_B = 6356752.314245179d;
    private static final double WGS84_B2 = Math.pow(WGS84_B, 2.0d);
    private static final double WGS84_E2 = (WGS84_A2 - WGS84_B2) / WGS84_A2;
    private static final double WGS84_EP2 = (WGS84_A2 - WGS84_B2) / WGS84_B2;
    private static final double BESSEL_FLATTERING = 0.003342773182174806d;
    private static final double BESSEL_ECCENTRICITY_2 = 0.006685546364349612d - Math.pow(BESSEL_FLATTERING, 2.0d);
    private static final double ESP = BESSEL_ECCENTRICITY_2 / (1.0d - BESSEL_ECCENTRICITY_2);
    private static final double[] EN = TmProjection.enfn(BESSEL_ECCENTRICITY_2);
    private static final double ML_0 = TmProjection.mlfn(LAT_0_RADIAN, Math.sin(LAT_0_RADIAN), Math.cos(LAT_0_RADIAN), EN);
    private static final double[] DATUM_PARAMS_BESSEL_TO_GRS80 = {-3159521.31d, 4068151.32d, 3748113.85d};
    private static final double[] DATUM_PARAMS_GRS80_TO_BESSEL = {-3159666.86d, 4068655.7d, 3748799.65d};
    public static final Parcelable.Creator<Tm128> CREATOR = new Parcelable.Creator<Tm128>() { // from class: com.naver.maps.geometry.Tm128.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tm128 createFromParcel(Parcel source) {
            return new Tm128(source.readDouble(), source.readDouble());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tm128[] newArray(int size) {
            return new Tm128[size];
        }
    };

    private static Xyz geodeticToGeocentric(Xyz geodetic, double a, double e2) {
        double lng = geodetic.x;
        double lat = geodetic.y;
        if (lat < -1.5707963267948966d && lat > -1.5723671231216914d) {
            lat = -1.5707963267948966d;
        } else if (lat > 1.5707963267948966d && lat < 1.5723671231216914d) {
            lat = 1.5707963267948966d;
        } else if (lat < -1.5707963267948966d || lat > 1.5707963267948966d) {
            return null;
        }
        if (lng > 3.141592653589793d) {
            lng -= 6.283185307179586d;
        }
        double sinLat = Math.sin(lat);
        double cosLat = Math.cos(lat);
        double rn = a / Math.sqrt(1.0d - ((e2 * sinLat) * sinLat));
        return new Xyz(rn * cosLat * Math.cos(lng), rn * cosLat * Math.sin(lng), (1.0d - e2) * rn * sinLat);
    }

    private static Xyz geocentricToGeodetic(Xyz geocentric, double a, double e2) {
        double lng;
        double height;
        double cphi;
        double sphi;
        double x = geocentric.x;
        double y = geocentric.y;
        double z = geocentric.z;
        double p = Math.sqrt((x * x) + (y * y));
        double rr = Math.sqrt((x * x) + (y * y) + (z * z));
        if (p / a >= GENAU) {
            lng = Math.atan2(y, x);
        } else {
            if (rr / a < GENAU) {
                return null;
            }
            lng = 0.0d;
        }
        double ct = z / rr;
        double st = p / rr;
        double rx = 1.0d / Math.sqrt(1.0d - ((((2.0d - e2) * e2) * st) * st));
        double cphi0 = (1.0d - e2) * st * rx;
        double sphi0 = ct * rx;
        double iter = 0.0d;
        do {
            double rn = a / Math.sqrt(1.0d - ((e2 * sphi0) * sphi0));
            height = ((p * cphi0) + (z * sphi0)) - ((1.0d - ((e2 * sphi0) * sphi0)) * rn);
            double rk = (e2 * rn) / (rn + height);
            double rx2 = 1.0d / Math.sqrt(1.0d - ((((2.0d - rk) * rk) * st) * st));
            cphi = (1.0d - rk) * st * rx2;
            sphi = ct * rx2;
            double sdphi = (sphi * cphi0) - (cphi * sphi0);
            cphi0 = cphi;
            sphi0 = sphi;
            iter += 1.0d;
            if (sdphi * sdphi <= GENAU_SQUARE) {
                break;
            }
        } while (iter < 6.0d);
        double lat = Math.atan(sphi / Math.abs(cphi));
        return new Xyz(lng, lat, height);
    }

    private static Xyz geocentricToWgs84(Xyz geocentric) {
        double x = geocentric.x - DATUM_PARAMS_BESSEL_TO_GRS80[0];
        double y = geocentric.y - DATUM_PARAMS_BESSEL_TO_GRS80[1];
        double z = geocentric.z - DATUM_PARAMS_BESSEL_TO_GRS80[2];
        return new Xyz(((((y * RZ_BF) + x) - (z * RY_BF)) * M_BF) + DATUM_PARAMS_BESSEL_TO_GRS80[0] + DX_BF, ((((-x) * RZ_BF) + y + (z * RX_BF)) * M_BF) + DATUM_PARAMS_BESSEL_TO_GRS80[1] + DY_BF, ((((RY_BF * x) - (RX_BF * y)) + z) * M_BF) + DATUM_PARAMS_BESSEL_TO_GRS80[2] + DZ_BF);
    }

    private static Xyz wgs84ToGeocentric(Xyz wgs84) {
        double x = ((wgs84.x - DATUM_PARAMS_GRS80_TO_BESSEL[0]) - DX_BF) / M_BF;
        double y = ((wgs84.y - DATUM_PARAMS_GRS80_TO_BESSEL[1]) - DY_BF) / M_BF;
        double z = ((wgs84.z - DATUM_PARAMS_GRS80_TO_BESSEL[2]) - DZ_BF) / M_BF;
        return new Xyz((x - (y * RZ_BF)) + (RY_BF * z) + DATUM_PARAMS_GRS80_TO_BESSEL[0], (((RZ_BF * x) + y) - (z * RX_BF)) + DATUM_PARAMS_GRS80_TO_BESSEL[1], ((-1.137857709564081E-5d) * x) + (RX_BF * y) + z + DATUM_PARAMS_GRS80_TO_BESSEL[2]);
    }

    public static Tm128 valueOf(LatLng latLng) {
        Xyz coord = geodeticToGeocentric(latLng.toRadians(), WGS84_A, WGS84_E2);
        if (coord == null) {
            return INVALID;
        }
        Xyz coord2 = geocentricToGeodetic(wgs84ToGeocentric(coord), 6377397.155d, BESSEL_E2);
        if (coord2 == null) {
            return INVALID;
        }
        Xyz coord3 = TmProjection.projectRadians(coord2, X_0, Y_0, LON_0, SCALE_FACTOR, 6377397.155d, BESSEL_ECCENTRICITY_2, ESP, ML_0, EN);
        return new Tm128(coord3.x, coord3.y);
    }

    public Tm128(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override // com.naver.maps.geometry.Coord
    public LatLng toLatLng() {
        Xyz coord = geodeticToGeocentric(TmProjection.inverseProjectRadians(new Xyz(this.x, this.y), X_0, Y_0, LON_0, 6377397.155d, SCALE_FACTOR, BESSEL_ECCENTRICITY_2, ESP, ML_0, EN), 6377397.155d, BESSEL_E2);
        if (coord == null) {
            return LatLng.INVALID;
        }
        Xyz coord2 = geocentricToGeodetic(geocentricToWgs84(coord), WGS84_A, WGS84_E2);
        if (coord2 == null) {
            return LatLng.INVALID;
        }
        return LatLng.fromRadians(coord2);
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isValid() {
        return (Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isInfinite(this.x) || Double.isInfinite(this.y)) ? false : true;
    }

    @Override // com.naver.maps.geometry.Coord
    public boolean isWithinCoverage() {
        return isValid() && this.x >= 30408.747066328477d && this.x <= 749976.0946343569d && this.y >= 158674.67403835512d && this.y <= 643904.8888573726d;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tm128 that = (Tm128) o;
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
        return "Tm128{x=" + this.x + ", y=" + this.y + '}';
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
