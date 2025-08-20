package com.google.android.gms.internal.maps;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.Tile;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-maps@@19.0.0 */
/* loaded from: classes.dex */
public interface zzax extends IInterface {
    @Nullable
    Tile zzb(int i, int i2, int i3) throws RemoteException;
}
