package com.naver.maps.geometry;

/* loaded from: classes.dex */
class TmProjection {
    private static final double C00 = 1.0d;
    private static final double C02 = 0.25d;
    private static final double C04 = 0.046875d;
    private static final double C06 = 0.01953125d;
    private static final double C08 = 0.01068115234375d;
    private static final double C22 = 0.75d;
    private static final double C44 = 0.46875d;
    private static final double C46 = 0.013020833333333334d;
    private static final double C48 = 0.007120768229166667d;
    private static final double C66 = 0.3645833333333333d;
    private static final double C68 = 0.005696614583333333d;
    private static final double C88 = 0.3076171875d;
    private static final double FC1 = 1.0d;
    private static final double FC2 = 0.5d;
    private static final double FC3 = 0.16666666666666666d;
    private static final double FC4 = 0.08333333333333333d;
    private static final double FC5 = 0.05d;
    private static final double FC6 = 0.03333333333333333d;
    private static final double FC7 = 0.023809523809523808d;
    private static final double FC8 = 0.017857142857142856d;
    public static final double HALFPI = 1.5707963267948966d;
    private static final int MAX_ITER = 6;
    public static final double TWOPI = 6.283185307179586d;

    private static double normalizeLongitude(double radian) {
        if (Double.isInfinite(radian) || Double.isNaN(radian)) {
            return Double.NaN;
        }
        while (radian > 3.141592653589793d) {
            radian -= 6.283185307179586d;
        }
        while (radian < -3.141592653589793d) {
            radian += 6.283185307179586d;
        }
        return radian;
    }

    public static double mlfn(double phi, double sphi, double cphi, double[] en) {
        double sphi2 = sphi * sphi;
        return (en[0] * phi) - ((en[1] + ((en[2] + ((en[3] + (en[4] * sphi2)) * sphi2)) * sphi2)) * (cphi * sphi));
    }

    public static double inv_mlfn(double arg, double es, double[] en) {
        double k = 1.0d / (1.0d - es);
        double phi = arg;
        for (int i = 0; i < 6; i++) {
            double s = Math.sin(phi);
            double t = 1.0d - ((es * s) * s);
            double t2 = (mlfn(phi, s, Math.cos(phi), en) - arg) * Math.sqrt(t) * t * k;
            phi -= t2;
            if (Math.abs(t2) < 1.0E-11d) {
                return phi;
            }
        }
        return phi;
    }

    public static double[] enfn(double es) {
        double t = es * es;
        double u = ((((C08 * es) + C06) * es) + C04) * es;
        double t2 = t * es;
        double[] en = {1.0d - ((C02 + u) * es), (C22 - u) * es, (C44 - (((C48 * es) + C46) * es)) * t, (C66 - (C68 * es)) * t2, t2 * es * C88};
        return en;
    }

    public static Xyz projectRadians(Xyz src, double x0, double y0, double lon0, double scaleFactor, double totalScale, double eccentricity2, double esp, double ml0, double[] en) {
        double x = src.x;
        double y = src.y;
        double projectionLongitude = Math.toRadians(lon0);
        if (projectionLongitude != 0.0d) {
            x = normalizeLongitude(x - projectionLongitude);
        }
        double sinphi = Math.sin(y);
        double cosphi = Math.cos(y);
        double t = Math.abs(cosphi) > 1.0E-10d ? sinphi / cosphi : 0.0d;
        double al = cosphi * x;
        double als = al * al;
        double n = esp * cosphi * cosphi;
        double t2 = t * t;
        double al2 = al / Math.sqrt(1.0d - ((eccentricity2 * sinphi) * sinphi));
        double outX = scaleFactor * al2 * ((FC3 * als * ((1.0d - t2) + n + (FC5 * als * (((t2 - 18.0d) * t2) + 5.0d + ((14.0d - (t2 * 58.0d)) * n) + (FC7 * als * (((((179.0d - t2) * t2) - 479.0d) * t2) + 61.0d)))))) + 1.0d);
        double outY = ((mlfn(y, sinphi, cosphi, en) - ml0) + (sinphi * al2 * x * FC2 * ((FC4 * als * ((5.0d - t2) + (((4.0d * n) + 9.0d) * n) + (FC6 * als * (((t2 - 58.0d) * t2) + 61.0d + ((270.0d - (330.0d * t2)) * n) + (FC8 * als * (((((543.0d - t2) * t2) - 3111.0d) * t2) + 1385.0d)))))) + 1.0d))) * scaleFactor;
        return new Xyz((totalScale * outX) + x0, (totalScale * outY) + y0);
    }

    public static Xyz inverseProjectRadians(Xyz src, double x0, double y0, double lon0, double totalScale, double scaleFactor, double eccentricity2, double esp, double ml0, double[] en) {
        double outY;
        double sinphi;
        double x = (src.x - x0) / totalScale;
        double y = (src.y - y0) / totalScale;
        double projectionLongitude = Math.toRadians(lon0);
        double outY2 = inv_mlfn(ml0 + (y / scaleFactor), eccentricity2, en);
        if (Math.abs(y) > 1.5707963267948966d) {
            outY = y < 0.0d ? -1.5707963267948966d : 1.5707963267948966d;
            sinphi = 0.0d;
        } else {
            double sinphi2 = Math.sin(outY2);
            double cosphi = Math.cos(outY2);
            double con = 1.0d - ((eccentricity2 * sinphi2) * sinphi2);
            double t = Math.abs(cosphi) > 1.0E-10d ? sinphi2 / cosphi : 0.0d;
            double n = esp * cosphi * cosphi;
            double d = (Math.sqrt(con) * x) / scaleFactor;
            double ds = d * d;
            double con2 = con * t;
            double t2 = t * t;
            outY = outY2 - ((((con2 * ds) / (1.0d - eccentricity2)) * FC2) * (1.0d - ((FC4 * ds) * (((((3.0d - (9.0d * n)) * t2) + 5.0d) + ((1.0d - (4.0d * n)) * n)) - ((FC6 * ds) * ((((((90.0d - (252.0d * n)) + (45.0d * t2)) * t2) + 61.0d) + (46.0d * n)) - ((FC8 * ds) * ((((((1574.0d * t2) + 4095.0d) * t2) + 3633.0d) * t2) + 1385.0d))))))));
            sinphi = ((1.0d - ((FC3 * ds) * ((((2.0d * t2) + 1.0d) + n) - ((FC5 * ds) * (((((((24.0d * t2) + 28.0d) + (8.0d * n)) * t2) + 5.0d) + (6.0d * n)) - ((FC7 * ds) * ((((((720.0d * t2) + 1320.0d) * t2) + 662.0d) * t2) + 61.0d))))))) * d) / cosphi;
        }
        if (sinphi < -3.141592653589793d) {
            sinphi = -3.141592653589793d;
        } else if (sinphi > 3.141592653589793d) {
            sinphi = 3.141592653589793d;
        }
        if (projectionLongitude != 0.0d) {
            sinphi = normalizeLongitude(sinphi + projectionLongitude);
        }
        return new Xyz(sinphi, outY);
    }

    private TmProjection() {
    }
}
