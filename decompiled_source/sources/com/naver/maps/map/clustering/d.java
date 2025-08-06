package com.naver.maps.map.clustering;

import androidx.collection.LongSparseArray;
import androidx.core.util.Pair;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.TileId;
import com.naver.maps.map.clustering.ClusteringKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
class d<T extends ClusteringKey> {
    static final /* synthetic */ boolean a = true;
    private final e<T> b;
    private final ThresholdStrategy c;
    private final DistanceStrategy d;
    private final PositioningStrategy e;
    private final TagMergeStrategy f;
    private final int g;
    private final int h;
    private final int i;
    private final int j;
    private final double k;
    private final boolean l;
    private final g m;
    private Cluster q;
    private int s;
    private final Map<T, Node> n = new HashMap();
    private final LongSparseArray<List<Node>> o = new LongSparseArray<>();
    private final Set<Long> p = new HashSet();
    private int r = -2;

    d(e<T> eVar, ThresholdStrategy thresholdStrategy, DistanceStrategy distanceStrategy, PositioningStrategy positioningStrategy, TagMergeStrategy tagMergeStrategy, int i, int i2, int i3, int i4, double d, boolean z) {
        this.b = eVar;
        this.c = thresholdStrategy;
        this.d = distanceStrategy;
        this.e = positioningStrategy;
        this.f = tagMergeStrategy;
        this.g = i;
        this.h = i2;
        this.i = i3;
        this.j = i4;
        this.k = d;
        this.l = z;
        this.m = new g(i3, i4 + 1);
        b();
    }

    private void b() {
        int i = this.s;
        this.s = i + 1;
        this.q = new Cluster(i, this.e);
        this.q.a(-1);
        this.q.b(-1);
    }

    void a(OnClustererUpdateCallback onClustererUpdateCallback) {
        b();
        this.m.a();
        this.n.clear();
        this.o.clear();
        this.b.b(onClustererUpdateCallback);
    }

    void a(T t, Object obj, OnClustererUpdateCallback onClustererUpdateCallback) {
        Map<Long, Object> mapC = c();
        a((d<T>) t, obj, mapC);
        d(mapC, onClustererUpdateCallback);
    }

    void a(Map<T, ?> map, OnClustererUpdateCallback onClustererUpdateCallback) {
        if (this.n.isEmpty()) {
            b(map, onClustererUpdateCallback);
        } else {
            c(map, onClustererUpdateCallback);
        }
    }

    private void b(Map<T, ?> map, OnClustererUpdateCallback onClustererUpdateCallback) {
        Object obj;
        int i;
        int i2;
        Object obj2;
        Iterator<Map.Entry<T, ?>> it = map.entrySet().iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<T, ?> next = it.next();
            T key = next.getKey();
            if (key != null && !this.n.containsKey(key)) {
                LatLng position = key.getPosition();
                int i3 = this.s;
                this.s = i3 + 1;
                Leaf leaf = new Leaf(i3, key, position);
                leaf.a(next.getValue());
                leaf.a(this.h + 1);
                leaf.b(this.j + 1);
                this.n.put(key, leaf);
                a(leaf, (Map<Long, Object>) null);
            }
        }
        int i4 = this.h;
        while (true) {
            i = 0;
            if (i4 < this.g) {
                break;
            }
            b bVar = new b(this.c, i4);
            a aVar = new a(this.d, i4);
            int i5 = i4 + 1;
            LongSparseArray<List<Node>> longSparseArrayA = this.m.a(i5);
            int i6 = i4 == this.g ? this.i : i4;
            while (i < longSparseArrayA.size()) {
                for (Node node : longSparseArrayA.valueAt(i)) {
                    if (node.d() == null) {
                        int i7 = i6;
                        List<Node> listA = this.m.a(i5, node, 2.0d * this.k, bVar, aVar);
                        if (listA.size() <= 1) {
                            i2 = i7;
                            node.a(i2);
                            this.m.a(i2, i4, node);
                            obj2 = null;
                        } else {
                            i2 = i7;
                            int i8 = this.s;
                            this.s = i8 + 1;
                            Cluster cluster = new Cluster(i8, this.e);
                            cluster.a(i2);
                            cluster.b(i4);
                            Iterator<Node> it2 = listA.iterator();
                            while (it2.hasNext()) {
                                cluster.a(it2.next());
                            }
                            cluster.a(this.f.mergeTag(cluster));
                            obj2 = null;
                            a((Node) cluster, (Map<Long, Object>) null);
                        }
                        obj = obj2;
                        i6 = i2;
                    }
                }
                i++;
            }
            i4--;
        }
        LongSparseArray<List<Node>> longSparseArrayA2 = this.m.a(this.i);
        while (i < longSparseArrayA2.size()) {
            Iterator<Node> it3 = longSparseArrayA2.valueAt(i).iterator();
            while (it3.hasNext()) {
                this.q.a(it3.next());
            }
            i++;
        }
        Map<Long, Object> mapC = c();
        if (mapC != null) {
            Iterator<Long> it4 = this.p.iterator();
            while (it4.hasNext()) {
                long jLongValue = it4.next().longValue();
                List<Node> listA2 = this.m.a(jLongValue);
                this.o.put(jLongValue, new ArrayList(listA2));
                for (Node node2 : listA2) {
                    mapC.put(Long.valueOf(node2.c()), node2.b());
                }
            }
        }
        d(mapC, onClustererUpdateCallback);
    }

    private void c(Map<T, ?> map, OnClustererUpdateCallback onClustererUpdateCallback) {
        Map<Long, Object> mapC = c();
        for (Map.Entry<T, ?> entry : map.entrySet()) {
            a((d<T>) entry.getKey(), entry.getValue(), mapC);
        }
        d(mapC, onClustererUpdateCallback);
    }

    void a(T t, OnClustererUpdateCallback onClustererUpdateCallback) {
        Map<Long, Object> mapC = c();
        a((d<T>) t, mapC);
        d(mapC, onClustererUpdateCallback);
    }

    void a(Collection<T> collection, OnClustererUpdateCallback onClustererUpdateCallback) {
        Map<Long, Object> mapC = c();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            a((d<T>) it.next(), mapC);
        }
        d(mapC, onClustererUpdateCallback);
    }

    private void a(T t, Object obj, Map<Long, Object> map) {
        if (this.n.containsKey(t)) {
            return;
        }
        LatLng position = t.getPosition();
        int i = this.s;
        this.s = i + 1;
        Leaf leaf = new Leaf(i, t, position);
        leaf.a(obj);
        int i2 = this.h;
        Node nodeB = null;
        while (i2 >= this.g) {
            nodeB = this.m.b(i2, leaf, this.k, this.c, this.d);
            if (nodeB != null) {
                break;
            } else {
                i2--;
            }
        }
        if (nodeB == null) {
            this.q.a((Node) leaf);
            leaf.a(this.i);
        } else if ((nodeB instanceof Cluster) && nodeB.getMaxZoom() == i2) {
            Cluster cluster = (Cluster) nodeB;
            cluster.a((Node) leaf);
            a(cluster, map);
            leaf.a(i2 + 1);
        } else {
            Cluster clusterD = nodeB.d();
            if (!a && clusterD == null) {
                throw new AssertionError();
            }
            clusterD.b(nodeB);
            int minZoom = nodeB.getMinZoom();
            int i3 = i2 + 1;
            nodeB.a(i3);
            b(nodeB, minZoom, i2, map);
            int i4 = this.s;
            this.s = i4 + 1;
            Cluster cluster2 = new Cluster(i4, this.e);
            cluster2.a(minZoom);
            cluster2.b(i2);
            cluster2.a(nodeB);
            cluster2.a((Node) leaf);
            cluster2.a(this.f.mergeTag(cluster2));
            a((Node) cluster2, map);
            clusterD.a((Node) cluster2);
            a(clusterD, map);
            leaf.a(i3);
        }
        leaf.b(this.j + 1);
        a(leaf, map);
        this.n.put(t, leaf);
    }

    private void a(T t, Map<Long, Object> map) {
        Node node = this.n.get(t);
        if (node == null) {
            return;
        }
        b(node, map);
        this.n.remove(t);
        Cluster clusterD = node.d();
        if (!a && clusterD == null) {
            throw new AssertionError();
        }
        clusterD.b(node);
        if (clusterD != this.q) {
            if (clusterD.getChildren().size() == 1) {
                Cluster clusterD2 = clusterD.d();
                if (!a && clusterD2 == null) {
                    throw new AssertionError();
                }
                Node node2 = clusterD.getChildren().get(0);
                node2.a(clusterD.getMinZoom());
                b(clusterD, map);
                a(node2, clusterD.getMinZoom(), clusterD.getMaxZoom(), map);
                clusterD2.b(clusterD);
                clusterD2.a(node2);
                clusterD = clusterD2;
            }
            a(clusterD, map);
        }
    }

    private void a(Cluster cluster, Map<Long, Object> map) {
        while (cluster != this.q) {
            if (!a && cluster == null) {
                throw new AssertionError();
            }
            cluster.a(this.f.mergeTag(cluster));
            if (map != null && cluster.getMinZoom() <= this.r && this.r <= cluster.getMaxZoom() && this.o.containsKey(a(cluster))) {
                map.put(Long.valueOf(cluster.c()), cluster.b());
            }
            cluster = cluster.d();
        }
    }

    private void a(Node node, Map<Long, Object> map) {
        a(node, node.getMinZoom(), node.getMaxZoom(), map);
    }

    private void a(Node node, int i, int i2, Map<Long, Object> map) {
        List<Node> list;
        this.m.a(i, i2, node);
        if (map != null && i <= this.r && this.r <= i2 && (list = this.o.get(a(node))) != null && list.add(node)) {
            map.put(Long.valueOf(node.c()), node.b());
        }
    }

    private void b(Node node, Map<Long, Object> map) {
        b(node, node.getMinZoom(), node.getMaxZoom(), map);
    }

    private void b(Node node, int i, int i2, Map<Long, Object> map) {
        List<Node> list;
        this.m.b(i, i2, node);
        if (map != null && i <= this.r && this.r <= i2 && (list = this.o.get(a(node))) != null && list.remove(node)) {
            map.put(Long.valueOf(node.c()), Long.valueOf(node.c()));
        }
    }

    private Map<Long, Object> c() {
        if (this.p.isEmpty()) {
            return null;
        }
        return new HashMap();
    }

    private void d(Map<Long, Object> map, OnClustererUpdateCallback onClustererUpdateCallback) {
        Collection<Object> collectionValues;
        if (map == null) {
            if (onClustererUpdateCallback == null) {
                return;
            } else {
                collectionValues = null;
            }
        } else {
            collectionValues = map.values();
        }
        this.b.b(collectionValues, onClustererUpdateCallback);
    }

    void a() {
        this.o.clear();
        this.p.clear();
    }

    void a(int i, Collection<Long> collection, Collection<Long> collection2) {
        Long lValueOf;
        Long lValueOf2;
        int iClamp = MathUtils.clamp(i, this.i, this.j + 1);
        HashSet<Node> hashSet = new HashSet();
        HashSet<Node> hashSet2 = new HashSet();
        HashSet<Node> hashSet3 = new HashSet();
        Iterator<Long> it = collection.iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            this.p.add(Long.valueOf(jLongValue));
            List<Node> listA = this.m.a(jLongValue);
            hashSet.addAll(listA);
            this.o.put(jLongValue, new ArrayList(listA));
        }
        Iterator<Long> it2 = collection2.iterator();
        while (it2.hasNext()) {
            long jLongValue2 = it2.next().longValue();
            this.p.remove(Long.valueOf(jLongValue2));
            List<Node> list = this.o.get(jLongValue2);
            if (list != null) {
                hashSet2.addAll(list);
            }
            this.o.remove(jLongValue2);
        }
        HashSet hashSet4 = new HashSet(hashSet);
        hashSet4.retainAll(hashSet2);
        hashSet.removeAll(hashSet4);
        hashSet2.removeAll(hashSet4);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!this.l || this.r < 0 || iClamp == this.r) {
            Iterator it3 = hashSet.iterator();
            while (it3.hasNext()) {
                arrayList.add(new Pair(((Node) it3.next()).b(), null));
            }
            Iterator it4 = hashSet2.iterator();
            while (it4.hasNext()) {
                arrayList2.add(new Pair(Long.valueOf(((Node) it4.next()).c()), null));
            }
        } else if (iClamp > this.r) {
            for (Node node : hashSet) {
                Cluster clusterD = node.d();
                while (true) {
                    if (clusterD != null) {
                        if (!hashSet2.contains(clusterD)) {
                            clusterD = clusterD.d();
                        } else {
                            lValueOf2 = Long.valueOf(clusterD.c());
                            break;
                        }
                    } else {
                        lValueOf2 = null;
                        break;
                    }
                }
                arrayList.add(new Pair(node.b(), lValueOf2));
            }
            Iterator it5 = hashSet2.iterator();
            while (it5.hasNext()) {
                arrayList2.add(new Pair(Long.valueOf(((Node) it5.next()).c()), null));
            }
        } else {
            for (Node node2 : hashSet2) {
                Cluster clusterD2 = node2.d();
                while (true) {
                    if (clusterD2 != null) {
                        if (!hashSet.contains(clusterD2)) {
                            clusterD2 = clusterD2.d();
                        } else {
                            lValueOf = Long.valueOf(clusterD2.c());
                            hashSet3.add(clusterD2);
                            break;
                        }
                    } else {
                        lValueOf = null;
                        break;
                    }
                }
                arrayList2.add(new Pair(Long.valueOf(node2.c()), lValueOf));
            }
            for (Node node3 : hashSet) {
                if (!hashSet3.contains(node3)) {
                    arrayList.add(new Pair(node3.b(), null));
                }
            }
        }
        HashMap map = new HashMap();
        for (Node node4 : hashSet3) {
            map.put(Long.valueOf(node4.c()), node4.b());
        }
        this.r = iClamp;
        this.b.a(arrayList, arrayList2, map);
    }

    private long a(Node node) {
        return TileId.from(this.r, node.e());
    }
}
