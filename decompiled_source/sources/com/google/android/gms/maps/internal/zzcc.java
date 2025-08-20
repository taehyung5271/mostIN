package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.Objects;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public final class zzcc {
    private static final String zza = zzcc.class.getSimpleName();
    private static Context zzb = null;
    private static zzf zzc;

    public static zzf zza(Context context, MapsInitializer.Renderer renderer) throws GooglePlayServicesNotAvailableException {
        Preconditions.checkNotNull(context);
        Log.d(zza, "preferredRenderer: ".concat(String.valueOf(String.valueOf(renderer))));
        zzf zzfVar = zzc;
        if (zzfVar != null) {
            return zzfVar;
        }
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context, 13400000);
        switch (iIsGooglePlayServicesAvailable) {
            case 0:
                zzc = zzd(context, renderer);
                try {
                    if (zzc.zzd() == 2) {
                        try {
                            zzc.zzm(ObjectWrapper.wrap(zzc(context, renderer)));
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        } catch (UnsatisfiedLinkError e2) {
                            Log.w(zza, "Caught UnsatisfiedLinkError attempting to load the LATEST renderer's native library. Attempting to use the LEGACY renderer instead.");
                            zzb = null;
                            zzc = zzd(context, MapsInitializer.Renderer.LEGACY);
                        }
                    }
                    try {
                        zzc.zzk(ObjectWrapper.wrap(((Context) Objects.requireNonNull(zzc(context, renderer))).getResources()), 19000000);
                        return zzc;
                    } catch (RemoteException e3) {
                        throw new RuntimeRemoteException(e3);
                    }
                } catch (RemoteException e4) {
                    throw new RuntimeRemoteException(e4);
                }
            default:
                throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
        }
    }

    private static Context zzb(Exception exc, Context context) {
        Log.e(zza, "Failed to load maps module, use pre-Chimera", exc);
        return GooglePlayServicesUtil.getRemoteContext(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.content.Context zzc(android.content.Context r4, com.google.android.gms.maps.MapsInitializer.Renderer r5) throws java.lang.ClassNotFoundException {
        /*
            java.lang.String r0 = "com.google.android.gms.maps_legacy_dynamite"
            java.lang.String r1 = "com.google.android.gms.maps_core_dynamite"
            java.lang.String r2 = "com.google.android.gms.maps_dynamite"
            android.content.Context r3 = com.google.android.gms.maps.internal.zzcc.zzb
            if (r3 != 0) goto L58
            java.lang.String r3 = "com.google.android.gms.maps.internal.UseLegacyRendererAsDefault"
            java.lang.Class.forName(r3)     // Catch: java.lang.ClassNotFoundException -> L1e
            if (r5 == 0) goto L1c
            int r5 = r5.ordinal()
            switch(r5) {
                case 0: goto L1b;
                case 1: goto L19;
                default: goto L18;
            }
        L18:
            goto L1c
        L19:
            r0 = r1
            goto L25
        L1b:
            goto L25
        L1c:
            r0 = r2
            goto L25
        L1e:
            r3 = move-exception
            com.google.android.gms.maps.MapsInitializer$Renderer r3 = com.google.android.gms.maps.MapsInitializer.Renderer.LEGACY
            if (r5 != r3) goto L24
            goto L25
        L24:
            r0 = r1
        L25:
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy r5 = com.google.android.gms.dynamite.DynamiteModule.PREFER_REMOTE     // Catch: java.lang.Exception -> L30
            com.google.android.gms.dynamite.DynamiteModule r5 = com.google.android.gms.dynamite.DynamiteModule.load(r4, r5, r0)     // Catch: java.lang.Exception -> L30
            android.content.Context r4 = r5.getModuleContext()     // Catch: java.lang.Exception -> L30
            goto L53
        L30:
            r5 = move-exception
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L4f
            java.lang.String r5 = com.google.android.gms.maps.internal.zzcc.zza     // Catch: java.lang.Exception -> L49
            java.lang.String r0 = "Attempting to load maps_dynamite again."
            android.util.Log.d(r5, r0)     // Catch: java.lang.Exception -> L49
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy r5 = com.google.android.gms.dynamite.DynamiteModule.PREFER_REMOTE     // Catch: java.lang.Exception -> L49
            com.google.android.gms.dynamite.DynamiteModule r5 = com.google.android.gms.dynamite.DynamiteModule.load(r4, r5, r2)     // Catch: java.lang.Exception -> L49
            android.content.Context r4 = r5.getModuleContext()     // Catch: java.lang.Exception -> L49
            goto L53
        L49:
            r5 = move-exception
            android.content.Context r4 = zzb(r5, r4)
            goto L53
        L4f:
            android.content.Context r4 = zzb(r5, r4)
        L53:
            com.google.android.gms.maps.internal.zzcc.zzb = r4
            android.content.Context r4 = com.google.android.gms.maps.internal.zzcc.zzb
            return r4
        L58:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.zzcc.zzc(android.content.Context, com.google.android.gms.maps.MapsInitializer$Renderer):android.content.Context");
    }

    private static zzf zzd(Context context, MapsInitializer.Renderer renderer) {
        Log.i(zza, "Making Creator dynamically");
        try {
            IBinder iBinder = (IBinder) zze(((ClassLoader) Preconditions.checkNotNull(zzc(context, renderer).getClassLoader())).loadClass("com.google.android.gms.maps.internal.CreatorImpl"));
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            return iInterfaceQueryLocalInterface instanceof zzf ? (zzf) iInterfaceQueryLocalInterface : new zze(iBinder);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class com.google.android.gms.maps.internal.CreatorImpl", e);
        }
    }

    private static Object zze(Class cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to call the default constructor of ".concat(String.valueOf(cls.getName())), e);
        } catch (InstantiationException e2) {
            throw new IllegalStateException("Unable to instantiate the dynamic class ".concat(String.valueOf(cls.getName())), e2);
        }
    }
}
