package com.naver.maps.map.clustering;

import androidx.collection.LongSparseArray;
import com.naver.maps.geometry.WebMercatorCoord;
import com.naver.maps.map.TileId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
class g {
    private final int a;
    private final List<LongSparseArray<List<Node>>> b;

    private static int a(int i, double d) {
        return (int) ((d + 2.0037508342789244E7d) / f.a[i]);
    }

    private static int b(int i, double d) {
        return (int) ((2.0037508342789244E7d - d) / f.a[i]);
    }

    private static long a(int i, WebMercatorCoord webMercatorCoord) {
        return a(a(i, webMercatorCoord.x), b(i, webMercatorCoord.y));
    }

    private static long a(int i, int i2) {
        return i2 | (i << 32);
    }

    g(int i, int i2) {
        this.a = i;
        int i3 = (i2 - i) + 1;
        this.b = new ArrayList(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            this.b.add(new LongSparseArray<>());
        }
    }

    public void a() {
        Iterator<LongSparseArray<List<Node>>> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    public void a(int i, int i2, Node node) {
        WebMercatorCoord webMercatorCoordE = node.e();
        while (i <= i2) {
            long jA = a(i, webMercatorCoordE);
            LongSparseArray<List<Node>> longSparseArray = this.b.get(i - this.a);
            List<Node> arrayList = longSparseArray.get(jA);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                longSparseArray.put(jA, arrayList);
            }
            arrayList.add(node);
            i++;
        }
    }

    public void b(int i, int i2, Node node) {
        WebMercatorCoord webMercatorCoordE = node.e();
        while (i <= i2) {
            long jA = a(i, webMercatorCoordE);
            LongSparseArray<List<Node>> longSparseArray = this.b.get(i - this.a);
            List<Node> list = longSparseArray.get(jA);
            if (list != null) {
                Iterator<Node> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (node.equals(it.next())) {
                        list.remove(node);
                        if (list.isEmpty()) {
                            longSparseArray.remove(jA);
                        }
                    }
                }
            }
            i++;
        }
    }

    private List<Node> a(int i, long j) {
        List<Node> list = this.b.get(i - this.a).get(j);
        return list == null ? Collections.emptyList() : list;
    }

    public List<Node> a(long j) {
        return a(TileId.z(j), TileId.x(j), TileId.y(j));
    }

    public List<Node> a(int i, int i2, int i3) {
        return a(i, a(i2, i3));
    }

    public LongSparseArray<List<Node>> a(int i) {
        return this.b.get(i - this.a);
    }

    public List<Node> a(int i, Node node, double d, ThresholdStrategy thresholdStrategy, DistanceStrategy distanceStrategy) {
        ArrayList arrayList = new ArrayList();
        WebMercatorCoord coord = node.getCoord();
        double d2 = d * f.b[i];
        int iB = b(i, coord.y + d2);
        int iA = a(i, coord.x + d2);
        int iB2 = b(i, coord.y - d2);
        LongSparseArray<List<Node>> longSparseArrayA = a(i);
        double threshold = thresholdStrategy.getThreshold(i);
        for (int iA2 = a(i, coord.x - d2); iA2 <= iA; iA2++) {
            for (int i2 = iB; i2 <= iB2; i2++) {
                List<Node> list = longSparseArrayA.get(a(iA2, i2));
                if (list != null) {
                    for (Node node2 : list) {
                        if (distanceStrategy.getDistance(i, node, node2) <= threshold) {
                            arrayList.add(node2);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public Node b(int i, Node node, double d, ThresholdStrategy thresholdStrategy, DistanceStrategy distanceStrategy) {
        WebMercatorCoord coord = node.getCoord();
        double d2 = d * f.b[i];
        int iB = b(i, coord.y + d2);
        int iA = a(i, coord.x + d2);
        int iB2 = b(i, coord.y - d2);
        LongSparseArray<List<Node>> longSparseArrayA = a(i);
        double threshold = thresholdStrategy.getThreshold(i);
        Node node2 = null;
        for (int iA2 = a(i, coord.x - d2); iA2 <= iA; iA2++) {
            for (int i2 = iB; i2 <= iB2; i2++) {
                List<Node> list = longSparseArrayA.get(a(iA2, i2));
                if (list != null) {
                    for (Node node3 : list) {
                        double distance = distanceStrategy.getDistance(i, node, node3);
                        if (distance <= threshold) {
                            node2 = node3;
                            threshold = distance;
                        }
                    }
                }
            }
        }
        return node2;
    }
}
