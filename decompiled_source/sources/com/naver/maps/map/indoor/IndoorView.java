package com.naver.maps.map.indoor;

/* loaded from: classes.dex */
public final class IndoorView {
    private final String levelId;
    private final String zoneId;

    public IndoorView(String zoneId, String levelId) {
        this.zoneId = zoneId;
        this.levelId = levelId;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public String getLevelId() {
        return this.levelId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IndoorView indoorView = (IndoorView) o;
        if (!this.zoneId.equals(indoorView.zoneId)) {
            return false;
        }
        return this.levelId.equals(indoorView.levelId);
    }

    public int hashCode() {
        return (this.zoneId.hashCode() * 31) + this.levelId.hashCode();
    }
}
