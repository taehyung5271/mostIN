package com.naver.maps.map.clustering;

import com.naver.maps.geometry.WebMercatorCoord;
import java.util.Iterator;

/* loaded from: classes.dex */
public class DefaultPositioningStrategy implements PositioningStrategy {
    @Override // com.naver.maps.map.clustering.PositioningStrategy
    public WebMercatorCoord getPosition(Cluster cluster) {
        Iterator<Node> it = cluster.getChildren().iterator();
        double d = 0.0d;
        double d2 = 0.0d;
        while (it.hasNext()) {
            WebMercatorCoord coord = it.next().getCoord();
            d += coord.x;
            d2 += coord.y;
        }
        double size = cluster.getChildren().size();
        return new WebMercatorCoord(d / size, d2 / size);
    }
}
