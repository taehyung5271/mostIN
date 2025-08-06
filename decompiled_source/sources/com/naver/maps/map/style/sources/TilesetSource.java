package com.naver.maps.map.style.sources;

/* loaded from: classes.dex */
public abstract class TilesetSource extends Source {
    private native Tileset nativeGetAppliedTileset();

    private native Tileset nativeGetTileset();

    private native String nativeGetUrl();

    private native void nativeSetTileset(Tileset tileset);

    private native void nativeSetUrl(String str);

    TilesetSource(long handle) {
        super(handle);
    }

    public TilesetSource() {
    }
}
