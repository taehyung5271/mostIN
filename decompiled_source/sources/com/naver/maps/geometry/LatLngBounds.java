package com.naver.maps.geometry;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public final class LatLngBounds implements Parcelable {
    private final LatLng northEast;
    private final LatLng southWest;
    public static final LatLngBounds INVALID = new LatLngBounds(LatLng.INVALID, LatLng.INVALID);
    public static final LatLngBounds WORLD = new LatLngBounds(new LatLng(-90.0d, -180.0d), new LatLng(90.0d, 180.0d));
    public static final Parcelable.Creator<LatLngBounds> CREATOR = new Parcelable.Creator<LatLngBounds>() { // from class: com.naver.maps.geometry.LatLngBounds.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLngBounds createFromParcel(Parcel source) {
            return new LatLngBounds((LatLng) source.readParcelable(LatLng.class.getClassLoader()), (LatLng) source.readParcelable(LatLng.class.getClassLoader()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLngBounds[] newArray(int size) {
            return new LatLngBounds[size];
        }
    };

    public static final class Builder {
        private final List<LatLng> coords = new ArrayList();

        public Builder include(LatLng latLng) {
            if (latLng != null && latLng.isValid()) {
                this.coords.add(latLng);
            }
            return this;
        }

        public Builder include(LatLng... latLngs) {
            return include(Arrays.asList(latLngs));
        }

        public Builder include(Collection<LatLng> latLngs) {
            for (LatLng latLng : latLngs) {
                include(latLng);
            }
            return this;
        }

        public LatLngBounds build() {
            LatLngBounds result = buildOrNull();
            if (result == null) {
                throw new IllegalStateException("coordinates are empty; call include() first");
            }
            return result;
        }

        public LatLngBounds buildOrNull() {
            if (this.coords.isEmpty()) {
                return null;
            }
            double minLatitude = 90.0d;
            double maxLatitude = -90.0d;
            double minLongitude = 180.0d;
            double maxLongitude = -180.0d;
            for (LatLng latLng : this.coords) {
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                if (latitude < minLatitude) {
                    minLatitude = latitude;
                }
                if (latitude > maxLatitude) {
                    maxLatitude = latitude;
                }
                if (longitude < minLongitude) {
                    minLongitude = longitude;
                }
                if (longitude > maxLongitude) {
                    maxLongitude = longitude;
                }
            }
            return new LatLngBounds(maxLatitude, maxLongitude, minLatitude, minLongitude);
        }
    }

    public static LatLngBounds from(LatLng... latLngs) {
        return from(Arrays.asList(latLngs));
    }

    public static LatLngBounds fromOrNull(LatLng... latLngs) {
        return fromOrNull(Arrays.asList(latLngs));
    }

    public static LatLngBounds from(Collection<LatLng> latLngs) {
        LatLngBounds result = fromOrNull(latLngs);
        if (result == null) {
            throw new IllegalArgumentException("latLngs are empty");
        }
        return result;
    }

    public static LatLngBounds fromOrNull(Collection<LatLng> latLngs) {
        return new Builder().include(latLngs).buildOrNull();
    }

    private LatLngBounds(double northLatitude, double eastLongitude, double southLatitude, double westLongitude) {
        this(new LatLng(southLatitude, westLongitude), new LatLng(northLatitude, eastLongitude));
    }

    public LatLngBounds(LatLng southWest, LatLng northEast) {
        this.southWest = southWest;
        this.northEast = northEast;
    }

    public LatLng getSouthWest() {
        return this.southWest;
    }

    public LatLng getNorthEast() {
        return this.northEast;
    }

    public LatLng getSouthEast() {
        return new LatLng(getSouthLatitude(), getEastLongitude());
    }

    public LatLng getNorthWest() {
        return new LatLng(getNorthLatitude(), getWestLongitude());
    }

    public double getSouthLatitude() {
        return this.southWest.latitude;
    }

    public double getWestLongitude() {
        return this.southWest.longitude;
    }

    public double getNorthLatitude() {
        return this.northEast.latitude;
    }

    public double getEastLongitude() {
        return this.northEast.longitude;
    }

    public LatLng[] getVertexes() {
        return new LatLng[]{getSouthWest(), getNorthWest(), getNorthEast(), getSouthEast()};
    }

    public LatLng[] toPolygon() {
        return new LatLng[]{getSouthWest(), getNorthWest(), getNorthEast(), getSouthEast(), getSouthWest()};
    }

    public boolean isValid() {
        return getSouthWest().isValid() && getNorthWest().isValid();
    }

    public boolean isEmpty() {
        return !isValid() || getSouthLatitude() >= getNorthLatitude() || getWestLongitude() >= getEastLongitude();
    }

    public LatLng getCenter() {
        return new LatLng((getSouthLatitude() + getNorthLatitude()) / 2.0d, (getWestLongitude() + getEastLongitude()) / 2.0d);
    }

    public boolean contains(LatLng latLng) {
        return getSouthLatitude() <= latLng.latitude && getWestLongitude() <= latLng.longitude && getNorthLatitude() >= latLng.latitude && getEastLongitude() >= latLng.longitude;
    }

    public boolean contains(LatLngBounds bounds) {
        return getSouthLatitude() <= bounds.getSouthLatitude() && getWestLongitude() <= bounds.getWestLongitude() && getNorthLatitude() >= bounds.getNorthLatitude() && getEastLongitude() >= bounds.getEastLongitude();
    }

    public boolean intersects(LatLngBounds bounds) {
        return getSouthLatitude() <= bounds.getNorthLatitude() && getWestLongitude() <= bounds.getEastLongitude() && getNorthLatitude() >= bounds.getSouthLatitude() && getEastLongitude() >= bounds.getWestLongitude();
    }

    public LatLngBounds intersection(LatLngBounds bounds) {
        double maxWest = Math.max(getWestLongitude(), bounds.getWestLongitude());
        double minEast = Math.min(getEastLongitude(), bounds.getEastLongitude());
        if (minEast < maxWest) {
            return null;
        }
        double maxSouth = Math.max(getSouthLatitude(), bounds.getSouthLatitude());
        double minNorth = Math.min(getNorthLatitude(), bounds.getNorthLatitude());
        if (minNorth < maxSouth) {
            return null;
        }
        return new LatLngBounds(minNorth, minEast, maxSouth, maxWest);
    }

    public LatLngBounds expand(LatLng latLng) {
        if (contains(latLng)) {
            return this;
        }
        return new LatLngBounds(Math.max(getNorthLatitude(), latLng.latitude), Math.max(getEastLongitude(), latLng.longitude), Math.min(getSouthLatitude(), latLng.latitude), Math.min(getWestLongitude(), latLng.longitude));
    }

    public LatLngBounds buffer(double meter) {
        return new LatLngBounds(this.southWest.offset(-meter, -meter), this.northEast.offset(meter, meter));
    }

    public LatLngBounds union(LatLngBounds bounds) {
        if (contains(bounds)) {
            return this;
        }
        return new LatLngBounds(Math.max(getNorthLatitude(), bounds.getNorthLatitude()), Math.max(getEastLongitude(), bounds.getEastLongitude()), Math.min(getSouthLatitude(), bounds.getSouthLatitude()), Math.min(getWestLongitude(), bounds.getWestLongitude()));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LatLngBounds that = (LatLngBounds) o;
        if (this.southWest.equals(that.southWest) && this.northEast.equals(that.northEast)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.southWest.hashCode() * 31) + this.northEast.hashCode();
    }

    public String toString() {
        return "LatLngBounds{southWest=" + this.southWest + ", northEast=" + this.northEast + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.southWest, flags);
        dest.writeParcelable(this.northEast, flags);
    }
}
