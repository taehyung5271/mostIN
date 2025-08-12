package com.naver.maps.map.util;

import android.graphics.PointF;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.NaverMap;

/* loaded from: classes.dex */
public class CameraUtils {
    private static final double a = Math.log(2.0d);

    public static double getFittableZoom(NaverMap map, LatLngBounds bounds) {
        return getFittableZoom(map, bounds, 0, 0, 0, 0);
    }

    public static double getFittableZoom(NaverMap map, LatLngBounds bounds, int padding) {
        return getFittableZoom(map, bounds, padding, padding, padding, padding);
    }

    public static double getFittableZoom(NaverMap map, LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        double d = map.getCameraPosition().zoom;
        LatLng[] vertexes = bounds.getVertexes();
        int length = vertexes.length;
        float f = Float.POSITIVE_INFINITY;
        float f2 = Float.POSITIVE_INFINITY;
        float f3 = Float.NEGATIVE_INFINITY;
        float f4 = Float.NEGATIVE_INFINITY;
        int i = 0;
        while (i < length) {
            int i2 = i;
            PointF screenLocationAt = map.getProjection().toScreenLocationAt(vertexes[i], d, 0.0d, 0.0d, false);
            if (screenLocationAt.x < f) {
                f = screenLocationAt.x;
            }
            if (screenLocationAt.y < f2) {
                f2 = screenLocationAt.y;
            }
            if (screenLocationAt.x > f3) {
                f3 = screenLocationAt.x;
            }
            if (screenLocationAt.y > f4) {
                f4 = screenLocationAt.y;
            }
            i = i2 + 1;
        }
        return MathUtils.clamp(d + (Math.log(Math.min(((map.getContentWidth() - paddingLeft) - paddingRight) / (f3 - f), ((map.getContentHeight() - paddingTop) - paddingBottom) / (f4 - f2))) / a), map.getMinZoom(), map.getMaxZoom());
    }

    private CameraUtils() {
    }
}
