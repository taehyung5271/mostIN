package com.naver.maps.map.util;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.WebMercatorCoord;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class GeometryUtils {

    private static class a implements Iterator<LatLng> {
        private final List<List<LatLng>> a;
        private Iterator<LatLng> b;
        private int c;

        public a(List<List<LatLng>> list) {
            this.a = list;
            this.b = list.get(0).iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!this.b.hasNext()) {
                int i = this.c + 1;
                this.c = i;
                if (i >= this.a.size()) {
                    return false;
                }
                this.b = this.a.get(this.c).iterator();
            }
            return true;
        }

        @Override // java.util.Iterator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public LatLng next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return this.b.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static WebMercatorCoord a(WebMercatorCoord webMercatorCoord, WebMercatorCoord webMercatorCoord2, WebMercatorCoord webMercatorCoord3) {
        double d = webMercatorCoord.x - webMercatorCoord2.x;
        double d2 = webMercatorCoord.y - webMercatorCoord2.y;
        double d3 = (d * d) + (d2 * d2);
        if (d3 == 0.0d) {
            return webMercatorCoord;
        }
        double d4 = (((webMercatorCoord3.x - webMercatorCoord.x) * (webMercatorCoord2.x - webMercatorCoord.x)) + ((webMercatorCoord3.y - webMercatorCoord.y) * (webMercatorCoord2.y - webMercatorCoord.y))) / d3;
        if (d4 < 0.0d) {
            return webMercatorCoord;
        }
        if (d4 > 1.0d) {
            return webMercatorCoord2;
        }
        return new WebMercatorCoord(webMercatorCoord.x + ((webMercatorCoord2.x - webMercatorCoord.x) * d4), webMercatorCoord.y + (d4 * (webMercatorCoord2.y - webMercatorCoord.y)));
    }

    private static double a(Iterator<LatLng> it, LatLng latLng) {
        if (!it.hasNext()) {
            return 0.0d;
        }
        WebMercatorCoord webMercatorCoordValueOf = WebMercatorCoord.valueOf(latLng);
        LatLng next = it.next();
        WebMercatorCoord webMercatorCoordValueOf2 = WebMercatorCoord.valueOf(next);
        double d = Double.POSITIVE_INFINITY;
        double d2 = 0.0d;
        double dDistanceTo = 0.0d;
        while (it.hasNext()) {
            LatLng next2 = it.next();
            WebMercatorCoord webMercatorCoordValueOf3 = WebMercatorCoord.valueOf(next2);
            double dDistanceTo2 = next.distanceTo(next2);
            WebMercatorCoord webMercatorCoordA = a(webMercatorCoordValueOf2, webMercatorCoordValueOf3, webMercatorCoordValueOf);
            double dDistanceTo3 = webMercatorCoordA.distanceTo(webMercatorCoordValueOf);
            if (dDistanceTo3 < d) {
                dDistanceTo = d2 + ((webMercatorCoordValueOf2.distanceTo(webMercatorCoordA) * dDistanceTo2) / webMercatorCoordValueOf2.distanceTo(webMercatorCoordValueOf3));
                d = dDistanceTo3;
            }
            d2 += dDistanceTo2;
            next = next2;
            webMercatorCoordValueOf2 = webMercatorCoordValueOf3;
        }
        if (d2 == 0.0d) {
            return 0.0d;
        }
        return dDistanceTo / d2;
    }

    public static double getProgress(List<LatLng> coords, LatLng target) {
        if (coords.size() < 2) {
            throw new IllegalArgumentException("coords.size() < 2");
        }
        return a(coords.iterator(), target);
    }

    public static double getProgressForCoordParts(List<List<LatLng>> coordParts, LatLng target) {
        if (coordParts.isEmpty()) {
            throw new IllegalArgumentException("coordParts is empty");
        }
        return a(new a(coordParts), target);
    }

    private GeometryUtils() {
    }
}
