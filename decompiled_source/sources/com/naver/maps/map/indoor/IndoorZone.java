package com.naver.maps.map.indoor;

/* loaded from: classes.dex */
public final class IndoorZone {
    private final String a;
    private final int b;
    private final IndoorLevel[] c;

    private IndoorZone(String zoneId, int defultLevelIndex, IndoorLevel[] levels) {
        this.a = zoneId;
        this.b = defultLevelIndex;
        this.c = levels;
    }

    public String getZoneId() {
        return this.a;
    }

    public int getDefultLevelIndex() {
        return this.b;
    }

    public IndoorLevel getDefultLevel() {
        return this.c[this.b];
    }

    public IndoorLevel[] getLevels() {
        return this.c;
    }

    public int getLevelIndex(String levelId) {
        int i = 0;
        for (IndoorLevel indoorLevel : this.c) {
            if (indoorLevel.getIndoorView().getLevelId().equals(levelId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public IndoorLevel getlevel(String levelId) {
        int levelIndex = getLevelIndex(levelId);
        if (levelIndex < 0) {
            return null;
        }
        return this.c[levelIndex];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.a.equals(((IndoorZone) o).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
