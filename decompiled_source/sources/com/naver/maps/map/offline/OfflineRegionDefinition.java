package com.naver.maps.map.offline;

import android.os.Parcelable;
import com.naver.maps.geometry.LatLngBounds;

/* loaded from: classes.dex */
public interface OfflineRegionDefinition extends Parcelable {
    LatLngBounds getBounds();

    double getMaxZoom();

    double getMinZoom();

    float getPixelRatio();

    String getStyleURL();
}
