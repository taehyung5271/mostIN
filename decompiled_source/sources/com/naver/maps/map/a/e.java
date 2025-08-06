package com.naver.maps.map.a;

/* loaded from: classes.dex */
class e {
    static float a(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float fA = a((float) Math.toDegrees((float) Math.atan2(f2 - f4, f - f3)), (float) Math.toDegrees((float) Math.atan2(f6 - f8, f5 - f7)));
        if (Math.abs(fA) > 90.0f) {
            return ((fA > 0.0f ? 1 : -1) * 180) - Math.abs(fA);
        }
        return fA;
    }

    private static float a(float f, float f2) {
        float f3 = (f % 360.0f) - (f2 % 360.0f);
        if (f3 < -180.0f) {
            return f3 + 360.0f;
        }
        if (f3 > 180.0f) {
            return f3 - 360.0f;
        }
        return f3;
    }
}
