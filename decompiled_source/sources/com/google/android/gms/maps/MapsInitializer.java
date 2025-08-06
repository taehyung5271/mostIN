package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.internal.zzcc;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class MapsInitializer {
    private static final String zza = MapsInitializer.class.getSimpleName();
    private static boolean zzb = false;
    private static Renderer zzc = Renderer.LEGACY;

    /* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
    public enum Renderer {
        LEGACY,
        LATEST
    }

    private MapsInitializer() {
    }

    public static synchronized int initialize(Context context) {
        return initialize(context, null, null);
    }

    public static synchronized int initialize(Context context, Renderer preferredRenderer, OnMapsSdkInitializedCallback callback) {
        Preconditions.checkNotNull(context, "Context is null");
        Log.d(zza, "preferredRenderer: ".concat(String.valueOf(String.valueOf(preferredRenderer))));
        if (!zzb) {
            try {
                com.google.android.gms.maps.internal.zzf zzfVarZza = zzcc.zza(context, preferredRenderer);
                try {
                    CameraUpdateFactory.zza(zzfVarZza.zze());
                    BitmapDescriptorFactory.zza(zzfVarZza.zzj());
                    int i = 1;
                    zzb = true;
                    if (preferredRenderer != null) {
                        switch (preferredRenderer) {
                            case LEGACY:
                                break;
                            case LATEST:
                                i = 2;
                                break;
                            default:
                                i = 0;
                                break;
                        }
                    } else {
                        i = 0;
                    }
                    try {
                        if (zzfVarZza.zzd() == 2) {
                            zzc = Renderer.LATEST;
                        }
                        zzfVarZza.zzl(ObjectWrapper.wrap(context), i);
                    } catch (RemoteException e) {
                        Log.e(zza, "Failed to retrieve renderer type or log initialization.", e);
                    }
                    Log.d(zza, "loadedRenderer: ".concat(String.valueOf(String.valueOf(zzc))));
                    if (callback != null) {
                        callback.onMapsSdkInitialized(zzc);
                    }
                } catch (RemoteException e2) {
                    throw new RuntimeRemoteException(e2);
                }
            } catch (GooglePlayServicesNotAvailableException e3) {
                return e3.errorCode;
            }
        } else if (callback != null) {
            callback.onMapsSdkInitialized(zzc);
        }
        return 0;
    }
}
