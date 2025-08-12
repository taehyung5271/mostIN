package com.naver.maps.map.indoor;

/* loaded from: classes.dex */
public final class IndoorSelection {
    private final IndoorRegion a;
    private final int b;
    private final int c;

    public IndoorSelection(IndoorRegion region, int zoneIndex, int levelIndex) {
        this.a = region;
        this.b = zoneIndex;
        this.c = levelIndex;
    }

    public IndoorRegion getRegion() {
        return this.a;
    }

    public int getZoneIndex() {
        return this.b;
    }

    public IndoorZone getZone() {
        return this.a.getZones()[this.b];
    }

    public int getLevelIndex() {
        return this.c;
    }

    public IndoorLevel getLevel() {
        return getZone().getLevels()[this.c];
    }
}
