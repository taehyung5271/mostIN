package com.naver.maps.map.indoor;

/* loaded from: classes.dex */
public final class IndoorLevel {
    private final String a;
    private final IndoorView b;
    private final IndoorView[] c;

    private IndoorLevel(String name, IndoorView indoorView, IndoorView[] connections) {
        this.a = name;
        this.b = indoorView;
        this.c = connections;
    }

    public String getName() {
        return this.a;
    }

    public IndoorView getIndoorView() {
        return this.b;
    }

    public IndoorView[] getConnections() {
        return this.c;
    }

    public int getConnectionIndex(String zoneId) {
        int i = 0;
        for (IndoorView indoorView : this.c) {
            if (indoorView.getZoneId().equals(zoneId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public IndoorView getConnection(String zoneId) {
        int connectionIndex = getConnectionIndex(zoneId);
        if (connectionIndex < 0) {
            return null;
        }
        return this.c[connectionIndex];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.b.equals(((IndoorLevel) o).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }
}
