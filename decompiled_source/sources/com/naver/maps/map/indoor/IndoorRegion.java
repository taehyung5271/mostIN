package com.naver.maps.map.indoor;

/* loaded from: classes.dex */
public final class IndoorRegion {
    private final IndoorZone[] a;

    private IndoorRegion(IndoorZone[] zones) {
        this.a = zones;
    }

    public IndoorZone[] getZones() {
        return this.a;
    }

    public int getZoneIndex(String zoneId) {
        int i = 0;
        for (IndoorZone indoorZone : this.a) {
            if (indoorZone.getZoneId().equals(zoneId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public IndoorZone getZone(String zoneId) {
        int zoneIndex = getZoneIndex(zoneId);
        if (zoneIndex < 0) {
            return null;
        }
        return this.a[zoneIndex];
    }
}
