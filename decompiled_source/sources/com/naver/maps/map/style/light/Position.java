package com.naver.maps.map.style.light;

/* loaded from: classes.dex */
public class Position {
    private float azimuthalAngle;
    private float polarAngle;
    private float radialCoordinate;

    public Position(float radialCoordinate, float azimuthalAngle, float polarAngle) {
        this.radialCoordinate = radialCoordinate;
        this.azimuthalAngle = azimuthalAngle;
        this.polarAngle = polarAngle;
    }

    public static Position fromPosition(float radialCoordinate, float azimuthalAngle, float polarAngle) {
        return new Position(radialCoordinate, azimuthalAngle, polarAngle);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        if (Float.compare(position.radialCoordinate, this.radialCoordinate) == 0 && Float.compare(position.azimuthalAngle, this.azimuthalAngle) == 0 && Float.compare(position.polarAngle, this.polarAngle) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.radialCoordinate != 0.0f ? Float.floatToIntBits(this.radialCoordinate) : 0) * 31) + (this.azimuthalAngle != 0.0f ? Float.floatToIntBits(this.azimuthalAngle) : 0)) * 31) + (this.polarAngle != 0.0f ? Float.floatToIntBits(this.polarAngle) : 0);
    }

    public String toString() {
        return "Position{radialCoordinate=" + this.radialCoordinate + ", azimuthalAngle=" + this.azimuthalAngle + ", polarAngle=" + this.polarAngle + '}';
    }
}
