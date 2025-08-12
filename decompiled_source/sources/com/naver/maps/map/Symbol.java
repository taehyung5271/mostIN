package com.naver.maps.map;

import com.naver.maps.geometry.LatLng;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class Symbol implements Pickable {
    private final String a;
    private final String b;
    private final LatLng c;
    private final String d;
    private final String[] e;

    private Symbol(String type, String id, LatLng position, String caption, String[] layerGroups) {
        this.a = type;
        this.b = id;
        this.c = position;
        this.d = caption;
        this.e = layerGroups;
    }

    public LatLng getPosition() {
        return this.c;
    }

    public String getCaption() {
        return this.d;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Symbol symbol = (Symbol) o;
        if (!this.a.equals(symbol.a) || !this.b.equals(symbol.b) || !this.c.equals(symbol.c) || !this.d.equals(symbol.d)) {
            return false;
        }
        return Arrays.equals(this.e, symbol.e);
    }

    public int hashCode() {
        return (((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + Arrays.hashCode(this.e);
    }

    public String toString() {
        return "Symbol{position=" + this.c + ", caption='" + this.d + "'}";
    }
}
