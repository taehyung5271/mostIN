package com.naver.maps.geometry;

/* loaded from: classes.dex */
public class MathUtils {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double wrap(double value, double min, double max) {
        if (min == max) {
            return min;
        }
        double d = max - min;
        return ((((value - min) % d) + d) % d) + min;
    }

    public static int wrap(int value, int min, int max) {
        if (min == max) {
            return min;
        }
        int d = max - min;
        return ((((value - min) % d) + d) % d) + min;
    }

    private MathUtils() {
    }
}
