package com.naver.maps.map.style.sources;

import com.naver.maps.geometry.LatLngBounds;

/* loaded from: classes.dex */
public class Tileset {
    private String attribution;
    private LatLngBounds bounds;
    private String encoding;
    private int maxZoom;
    private int minZoom;
    private String scheme;
    private String[] tiles;
    private String version;

    Tileset(String[] tiles, String version, String attribution, String scheme, int minZoom, int maxZoom, LatLngBounds bounds, String encoding) {
        this.minZoom = 0;
        this.maxZoom = 22;
        this.tiles = tiles;
        this.version = version;
        this.attribution = attribution;
        this.scheme = scheme;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.bounds = bounds;
        this.encoding = encoding;
    }
}
