package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.AdvancedMarker;
import com.google.android.gms.maps.model.AdvancedMarkerOptions;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.FeatureLayer;
import com.google.android.gms.maps.model.FeatureLayerOptions;
import com.google.android.gms.maps.model.FeatureType;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapCapabilities;
import com.google.android.gms.maps.model.MapColorScheme;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public class GoogleMap {
    public static final String DEMO_MAP_ID = "demo_map_id";
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate zza;
    private MapCapabilities zzb;
    private final Map zzc = new HashMap();
    private final Map zzd = new HashMap();
    private UiSettings zze;

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    @Deprecated
    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnCameraIdleListener {
        void onCameraIdle();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnCameraMoveCanceledListener {
        void onCameraMoveCanceled();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnCameraMoveListener {
        void onCameraMove();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnCameraMoveStartedListener {
        public static final int REASON_API_ANIMATION = 2;
        public static final int REASON_DEVELOPER_ANIMATION = 3;
        public static final int REASON_GESTURE = 1;

        void onCameraMoveStarted(int i);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnCircleClickListener {
        void onCircleClick(Circle circle);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnGroundOverlayClickListener {
        void onGroundOverlayClick(GroundOverlay groundOverlay);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(IndoorBuilding indoorBuilding);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnInfoWindowCloseListener {
        void onInfoWindowClose(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnInfoWindowLongClickListener {
        void onInfoWindowLongClick(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMapCapabilitiesChangedListener {
        void onMapCapabilitiesChanged(MapCapabilities mapCapabilities);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnMyLocationClickListener {
        void onMyLocationClick(Location location);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnPoiClickListener {
        void onPoiClick(PointOfInterest pointOfInterest);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnPolygonClickListener {
        void onPolygonClick(Polygon polygon);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface OnPolylineClickListener {
        void onPolylineClick(Polyline polyline);
    }

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    public GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        this.zza = (IGoogleMapDelegate) Preconditions.checkNotNull(iGoogleMapDelegate);
    }

    public final Circle addCircle(CircleOptions options) {
        try {
            Preconditions.checkNotNull(options, "CircleOptions must not be null.");
            return new Circle(this.zza.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
        try {
            Preconditions.checkNotNull(options, "GroundOverlayOptions must not be null.");
            com.google.android.gms.internal.maps.zzv zzvVarAddGroundOverlay = this.zza.addGroundOverlay(options);
            if (zzvVarAddGroundOverlay != null) {
                return new GroundOverlay(zzvVarAddGroundOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions options) {
        if (options instanceof AdvancedMarkerOptions) {
            options.zzf(1);
        }
        try {
            Preconditions.checkNotNull(options, "MarkerOptions must not be null.");
            com.google.android.gms.internal.maps.zzah zzahVarAddMarker = this.zza.addMarker(options);
            if (zzahVarAddMarker != null) {
                return options.zzb() == 1 ? new AdvancedMarker(zzahVarAddMarker) : new Marker(zzahVarAddMarker);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions options) {
        try {
            Preconditions.checkNotNull(options, "PolygonOptions must not be null");
            return new Polygon(this.zza.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions options) {
        try {
            Preconditions.checkNotNull(options, "PolylineOptions must not be null");
            return new Polyline(this.zza.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions options) {
        try {
            Preconditions.checkNotNull(options, "TileOverlayOptions must not be null.");
            com.google.android.gms.internal.maps.zzau zzauVarAddTileOverlay = this.zza.addTileOverlay(options);
            if (zzauVarAddTileOverlay != null) {
                return new TileOverlay(zzauVarAddTileOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.zza.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public FeatureLayer getFeatureLayer(FeatureLayerOptions featureLayerOptions) {
        String featureType = featureLayerOptions.getFeatureType();
        String datasetId = featureLayerOptions.getDatasetId();
        boolean zEquals = featureType.equals(FeatureType.DATASET);
        FeatureLayer featureLayer = zEquals ? (FeatureLayer) this.zzd.get(datasetId) : (FeatureLayer) this.zzd.get(featureType);
        if (featureLayer == null) {
            try {
                featureLayer = new FeatureLayer(this.zza.getFeatureLayer(featureLayerOptions));
                if (zEquals) {
                    this.zzd.put(datasetId, featureLayer);
                } else {
                    this.zzd.put(featureType, featureLayer);
                }
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        return featureLayer;
    }

    public IndoorBuilding getFocusedBuilding() {
        try {
            com.google.android.gms.internal.maps.zzy focusedBuilding = this.zza.getFocusedBuilding();
            if (focusedBuilding != null) {
                return new IndoorBuilding(focusedBuilding);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public MapCapabilities getMapCapabilities() {
        if (this.zzb == null) {
            try {
                this.zzb = new MapCapabilities(this.zza.getMapCapabilities());
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        return this.zzb;
    }

    @MapColorScheme
    public int getMapColorScheme() {
        try {
            return this.zza.getMapColorScheme();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.zza.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zza.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.zza.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.zza.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.zza.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zze == null) {
                this.zze = new UiSettings(this.zza.getUiSettings());
            }
            return this.zze;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.zza.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.zza.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.zza.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.zza.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void removeOnMapCapabilitiesChangedListener(OnMapCapabilitiesChangedListener listener) {
        try {
            if (this.zzc.containsKey(listener)) {
                this.zza.removeOnMapCapabilitiesChangedListener((com.google.android.gms.maps.internal.zzal) this.zzc.get(listener));
                this.zzc.remove(listener);
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.zza.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(InfoWindowAdapter adapter) {
        try {
            if (adapter == null) {
                this.zza.setInfoWindowAdapter(null);
            } else {
                this.zza.setInfoWindowAdapter(new zzf(this, adapter));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setLocationSource(LocationSource source) {
        try {
            if (source == null) {
                this.zza.setLocationSource(null);
            } else {
                this.zza.setLocationSource(new zzt(this, source));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean setMapStyle(MapStyleOptions style) {
        try {
            return this.zza.setMapStyle(style);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final void setOnCameraChangeListener(OnCameraChangeListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCameraChangeListener(null);
            } else {
                this.zza.setOnCameraChangeListener(new zzu(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraIdleListener(OnCameraIdleListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCameraIdleListener(null);
            } else {
                this.zza.setOnCameraIdleListener(new zzy(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraMoveCanceledListener(OnCameraMoveCanceledListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCameraMoveCanceledListener(null);
            } else {
                this.zza.setOnCameraMoveCanceledListener(new zzx(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraMoveListener(OnCameraMoveListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCameraMoveListener(null);
            } else {
                this.zza.setOnCameraMoveListener(new zzw(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraMoveStartedListener(OnCameraMoveStartedListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCameraMoveStartedListener(null);
            } else {
                this.zza.setOnCameraMoveStartedListener(new zzv(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCircleClickListener(OnCircleClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnCircleClickListener(null);
            } else {
                this.zza.setOnCircleClickListener(new zzn(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnGroundOverlayClickListener(OnGroundOverlayClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnGroundOverlayClickListener(null);
            } else {
                this.zza.setOnGroundOverlayClickListener(new zzm(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnIndoorStateChangeListener(OnIndoorStateChangeListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnIndoorStateChangeListener(null);
            } else {
                this.zza.setOnIndoorStateChangeListener(new zzk(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnInfoWindowClickListener(null);
            } else {
                this.zza.setOnInfoWindowClickListener(new zzc(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnInfoWindowCloseListener(OnInfoWindowCloseListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnInfoWindowCloseListener(null);
            } else {
                this.zza.setOnInfoWindowCloseListener(new zze(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnInfoWindowLongClickListener(OnInfoWindowLongClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnInfoWindowLongClickListener(null);
            } else {
                this.zza.setOnInfoWindowLongClickListener(new zzd(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMapClickListener(OnMapClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMapClickListener(null);
            } else {
                this.zza.setOnMapClickListener(new zzz(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
        try {
            if (callback == null) {
                this.zza.setOnMapLoadedCallback(null);
            } else {
                this.zza.setOnMapLoadedCallback(new zzj(this, callback));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMapLongClickListener(null);
            } else {
                this.zza.setOnMapLongClickListener(new zzaa(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMarkerClickListener(null);
            } else {
                this.zza.setOnMarkerClickListener(new zza(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMarkerDragListener(null);
            } else {
                this.zza.setOnMarkerDragListener(new zzb(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMyLocationButtonClickListener(null);
            } else {
                this.zza.setOnMyLocationButtonClickListener(new zzh(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMyLocationChangeListener(null);
            } else {
                this.zza.setOnMyLocationChangeListener(new zzg(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnMyLocationClickListener(OnMyLocationClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnMyLocationClickListener(null);
            } else {
                this.zza.setOnMyLocationClickListener(new zzi(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnPoiClickListener(OnPoiClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnPoiClickListener(null);
            } else {
                this.zza.setOnPoiClickListener(new zzr(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnPolygonClickListener(OnPolygonClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnPolygonClickListener(null);
            } else {
                this.zza.setOnPolygonClickListener(new zzo(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnPolylineClickListener(OnPolylineClickListener listener) {
        try {
            if (listener == null) {
                this.zza.setOnPolylineClickListener(null);
            } else {
                this.zza.setOnPolylineClickListener(new zzp(this, listener));
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback) {
        Preconditions.checkNotNull(callback, "Callback must not be null.");
        snapshot(callback, null);
    }

    public final void clear() {
        try {
            this.zza.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void resetMinMaxZoomPreference() {
        try {
            this.zza.resetMinMaxZoomPreference();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean enabled) {
        try {
            this.zza.setBuildingsEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setContentDescription(String description) {
        try {
            this.zza.setContentDescription(description);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setLatLngBoundsForCameraTarget(LatLngBounds bounds) {
        try {
            this.zza.setLatLngBoundsForCameraTarget(bounds);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setMapColorScheme(@MapColorScheme int mapColorScheme) {
        try {
            this.zza.setMapColorScheme(mapColorScheme);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMapType(int type) {
        try {
            this.zza.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setMaxZoomPreference(float maxZoomPreference) {
        try {
            this.zza.setMaxZoomPreference(maxZoomPreference);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setMinZoomPreference(float minZoomPreference) {
        try {
            this.zza.setMinZoomPreference(minZoomPreference);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.zza.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.zza.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.zza.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.zza.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.animateCamera(update.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate update) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.moveCamera(update.zza());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback, Bitmap bitmap) {
        IObjectWrapper iObjectWrapperWrap;
        Preconditions.checkNotNull(callback, "Callback must not be null.");
        if (bitmap != null) {
            iObjectWrapperWrap = ObjectWrapper.wrap(bitmap);
        } else {
            iObjectWrapperWrap = null;
        }
        try {
            this.zza.snapshot(new zzq(this, callback), (ObjectWrapper) iObjectWrapperWrap);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void addOnMapCapabilitiesChangedListener(OnMapCapabilitiesChangedListener listener) {
        try {
            zzs zzsVar = new zzs(this, listener);
            this.zzc.put(listener, zzsVar);
            this.zza.addOnMapCapabilitiesChangedListener(zzsVar);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.animateCameraWithDurationAndCallback(update.zza(), durationMs, callback == null ? null : new zzab(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
        try {
            Preconditions.checkNotNull(update, "CameraUpdate must not be null.");
            this.zza.animateCameraWithCallback(update.zza(), callback == null ? null : new zzab(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
