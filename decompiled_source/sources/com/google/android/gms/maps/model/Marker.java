package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public class Marker {
    protected final com.google.android.gms.internal.maps.zzah zza;

    public Marker(com.google.android.gms.internal.maps.zzah zzahVar) {
        this.zza = (com.google.android.gms.internal.maps.zzah) Preconditions.checkNotNull(zzahVar);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Marker)) {
            return false;
        }
        try {
            return this.zza.zzE(((Marker) other).zza);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getAlpha() {
        try {
            return this.zza.zzd();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getId() {
        try {
            return this.zza.zzk();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public LatLng getPosition() {
        try {
            return this.zza.zzj();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getRotation() {
        try {
            return this.zza.zze();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getSnippet() {
        try {
            return this.zza.zzl();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public Object getTag() {
        try {
            return ObjectWrapper.unwrap(this.zza.zzi());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getTitle() {
        try {
            return this.zza.zzm();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getZIndex() {
        try {
            return this.zza.zzf();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.zza.zzg();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isDraggable() {
        try {
            return this.zza.zzF();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isFlat() {
        try {
            return this.zza.zzG();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isInfoWindowShown() {
        try {
            return this.zza.zzH();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isVisible() {
        try {
            return this.zza.zzI();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setIcon(BitmapDescriptor iconDescriptor) {
        try {
            if (iconDescriptor == null) {
                this.zza.zzt(null);
            } else {
                this.zza.zzt(iconDescriptor.zza());
            }
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(LatLng latlng) {
        if (latlng == null) {
            throw new IllegalArgumentException("latlng cannot be null - a position is required.");
        }
        try {
            this.zza.zzw(latlng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void hideInfoWindow() {
        try {
            this.zza.zzn();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void remove() {
        try {
            this.zza.zzo();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setAlpha(float alpha) {
        try {
            this.zza.zzp(alpha);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setAnchor(float anchorU, float anchorV) {
        try {
            this.zza.zzq(anchorU, anchorV);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setDraggable(boolean draggable) {
        try {
            this.zza.zzr(draggable);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setFlat(boolean flat) {
        try {
            this.zza.zzs(flat);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setInfoWindowAnchor(float anchorU, float anchorV) {
        try {
            this.zza.zzv(anchorU, anchorV);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setRotation(float rotation) {
        try {
            this.zza.zzx(rotation);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setSnippet(String snippet) {
        try {
            this.zza.zzy(snippet);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTag(Object tag) {
        try {
            this.zza.zzz(ObjectWrapper.wrap(tag));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTitle(String title) {
        try {
            this.zza.zzA(title);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setVisible(boolean visible) {
        try {
            this.zza.zzB(visible);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setZIndex(float zIndex) {
        try {
            this.zza.zzC(zIndex);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void showInfoWindow() {
        try {
            this.zza.zzD();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
