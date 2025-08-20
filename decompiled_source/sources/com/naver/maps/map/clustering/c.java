package com.naver.maps.map.clustering;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import androidx.collection.LongSparseArray;
import androidx.core.util.Pair;
import com.naver.maps.geometry.WebMercatorCoord;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.util.TileCoverHelper;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
class c {
    private static final TypeEvaluator<WebMercatorCoord> a = new TypeEvaluator<WebMercatorCoord>() { // from class: com.naver.maps.map.clustering.c.1
        @Override // android.animation.TypeEvaluator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public WebMercatorCoord evaluate(float f, WebMercatorCoord webMercatorCoord, WebMercatorCoord webMercatorCoord2) {
            double d = f;
            return new WebMercatorCoord(webMercatorCoord.x + ((webMercatorCoord2.x - webMercatorCoord.x) * d), webMercatorCoord.y + ((webMercatorCoord2.y - webMercatorCoord.y) * d));
        }
    };
    private final e<?> b;
    private final MarkerManager c;
    private final ClusterMarkerUpdater d;
    private final LeafMarkerUpdater e;
    private final int f;
    private final LongSparseArray<h> g = new LongSparseArray<>();
    private final TileCoverHelper h = new TileCoverHelper();
    private NaverMap i;
    private Animator j;

    c(e<?> eVar, MarkerManager markerManager, ClusterMarkerUpdater clusterMarkerUpdater, LeafMarkerUpdater leafMarkerUpdater, int i, int i2, int i3, boolean z) {
        this.b = eVar;
        this.c = markerManager;
        this.d = clusterMarkerUpdater;
        this.e = leafMarkerUpdater;
        this.f = i3;
        this.h.setMinZoom(i);
        this.h.setMaxZoom(i2 + 1);
        this.h.setUpdateOnChange(z);
        this.h.setListener(new TileCoverHelper.Listener() { // from class: com.naver.maps.map.clustering.c.2
            @Override // com.naver.maps.map.util.TileCoverHelper.Listener
            public void onTileChanged(List<Long> addedTiles, List<Long> removedTiles) {
                if (c.this.i != null) {
                    c.this.b.a((int) c.this.i.getCameraPosition().zoom, addedTiles, removedTiles);
                }
            }
        });
    }

    MarkerManager a() {
        return this.c;
    }

    ClusterMarkerUpdater b() {
        return this.d;
    }

    LeafMarkerUpdater c() {
        return this.e;
    }

    void a(NaverMap naverMap) {
        if (this.i == naverMap) {
            return;
        }
        a((OnClustererUpdateCallback) null);
        this.i = naverMap;
        this.b.a();
        this.h.setMap(naverMap);
    }

    void a(OnClustererUpdateCallback onClustererUpdateCallback) {
        if (this.i != null) {
            a(false);
            for (int i = 0; i < this.g.size(); i++) {
                this.g.valueAt(i).a((NaverMap) null);
            }
            this.g.clear();
        }
        if (onClustererUpdateCallback != null) {
            onClustererUpdateCallback.onClustererUpdate();
        }
    }

    void a(Collection<Object> collection, OnClustererUpdateCallback onClustererUpdateCallback) {
        if (this.i != null && collection != null) {
            a(true);
            this.g.size();
            for (Object obj : collection) {
                if (obj instanceof MarkerInfo) {
                    a((MarkerInfo) obj);
                } else if (obj instanceof Long) {
                    a(((Long) obj).longValue());
                }
            }
        }
        if (onClustererUpdateCallback != null) {
            onClustererUpdateCallback.onClustererUpdate();
        }
    }

    void a(Collection<Pair<MarkerInfo, Long>> collection, Collection<Pair<Long, Long>> collection2, final Map<Long, MarkerInfo> map) {
        if (this.i == null) {
            return;
        }
        a(false);
        AnimatorSet animatorSet = this.f > 0 ? new AnimatorSet() : null;
        this.g.size();
        for (Pair<MarkerInfo, Long> pair : collection) {
            final h hVarA = a(pair.first);
            if (pair.second != null && animatorSet != null) {
                h hVar = this.g.get(pair.second.longValue());
                if (hVar == null) {
                    d();
                } else {
                    ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(a, hVar.b(), hVarA.a().b());
                    valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.naver.maps.map.clustering.c.3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            hVarA.a((WebMercatorCoord) animation.getAnimatedValue());
                        }
                    });
                    valueAnimatorOfObject.addListener(new AnimatorListenerAdapter() { // from class: com.naver.maps.map.clustering.c.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animation) {
                            hVarA.a((WebMercatorCoord) null);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            hVarA.a((WebMercatorCoord) null);
                        }
                    });
                    animatorSet.play(valueAnimatorOfObject);
                    hVarA.a(hVar.b());
                }
            }
        }
        for (Pair<Long, Long> pair2 : collection2) {
            if (pair2.second == null || animatorSet == null) {
                a(pair2.first.longValue());
            } else {
                final h hVar2 = this.g.get(pair2.first.longValue());
                if (hVar2 == null) {
                    d();
                } else {
                    MarkerInfo markerInfo = map.get(pair2.second);
                    if (markerInfo == null) {
                        d();
                    } else {
                        ValueAnimator valueAnimatorOfObject2 = ValueAnimator.ofObject(a, hVar2.b(), markerInfo.b());
                        valueAnimatorOfObject2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.naver.maps.map.clustering.c.5
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(ValueAnimator animation) {
                                hVar2.a((WebMercatorCoord) animation.getAnimatedValue());
                            }
                        });
                        valueAnimatorOfObject2.addListener(new AnimatorListenerAdapter() { // from class: com.naver.maps.map.clustering.c.6
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animation) {
                                hVar2.a((NaverMap) null);
                                c.this.g.remove(hVar2.a().a());
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                hVar2.a((NaverMap) null);
                                c.this.g.remove(hVar2.a().a());
                            }
                        });
                        animatorSet.play(valueAnimatorOfObject2);
                        hVar2.a(hVar2.b());
                    }
                }
            }
        }
        if (animatorSet != null && !animatorSet.getChildAnimations().isEmpty()) {
            animatorSet.setDuration(this.f);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.naver.maps.map.clustering.c.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                    c.this.j = null;
                    Iterator it = map.values().iterator();
                    while (it.hasNext()) {
                        c.this.a((MarkerInfo) it.next());
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    c.this.j = null;
                    Iterator it = map.values().iterator();
                    while (it.hasNext()) {
                        c.this.a((MarkerInfo) it.next());
                    }
                }
            });
            animatorSet.start();
            this.j = animatorSet;
        }
    }

    private void a(boolean z) {
        if (this.j == null) {
            return;
        }
        if (z) {
            this.j.end();
        } else {
            this.j.cancel();
        }
        this.j = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public h a(MarkerInfo markerInfo) {
        h hVar = this.g.get(markerInfo.a());
        if (hVar != null) {
            if (markerInfo.equals(hVar.a())) {
                return hVar;
            }
            this.g.remove(markerInfo.a());
            hVar.a((NaverMap) null);
        }
        h hVar2 = new h(this, markerInfo);
        this.g.put(markerInfo.a(), hVar2);
        hVar2.a(this.i);
        return hVar2;
    }

    private void a(long j) {
        h hVar = this.g.get(j);
        if (hVar == null) {
            return;
        }
        hVar.a((NaverMap) null);
        this.g.remove(j);
    }

    private static void d() {
        throw new AssertionError();
    }
}
