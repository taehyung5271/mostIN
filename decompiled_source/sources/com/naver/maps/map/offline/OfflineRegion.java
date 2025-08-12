package com.naver.maps.map.offline;

import android.os.Handler;
import android.os.Looper;
import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.internal.a;

/* loaded from: classes.dex */
public class OfflineRegion {
    private FileSource a;
    private long b;
    private OfflineRegionDefinition c;
    private byte[] d;
    private final Handler e = new Handler(Looper.getMainLooper());
    private int f = 0;
    private boolean g = false;
    private long handle;

    public interface OfflineRegionDeleteCallback {
        void onDelete();

        void onError(String str);
    }

    public interface OfflineRegionInvalidateCallback {
        void onError(String str);

        void onInvalidate();
    }

    public interface OfflineRegionObserver {
        void onError(OfflineRegionError offlineRegionError);

        void onStatusChanged(OfflineRegionStatus offlineRegionStatus);
    }

    public interface OfflineRegionStatusCallback {
        void onError(String str);

        void onStatus(OfflineRegionStatus offlineRegionStatus);
    }

    public interface OfflineRegionUpdateMetadataCallback {
        void onError(String str);

        void onUpdate(byte[] bArr);
    }

    private native void deleteOfflineRegion(OfflineRegionDeleteCallback offlineRegionDeleteCallback);

    private native void getOfflineRegionStatus(OfflineRegionStatusCallback offlineRegionStatusCallback);

    private native void invalidateOfflineRegion(OfflineRegionInvalidateCallback offlineRegionInvalidateCallback);

    private native void nativeCreate(long j, FileSource fileSource);

    private native void nativeDestroy() throws Throwable;

    private native void setOfflineRegionDownloadState(int i);

    private native void setOfflineRegionObserver(OfflineRegionObserver offlineRegionObserver);

    private native void updateOfflineRegionMetadata(byte[] bArr, OfflineRegionUpdateMetadataCallback offlineRegionUpdateMetadataCallback);

    static {
        a.a();
    }

    private OfflineRegion(long offlineRegionPtr, FileSource fileSource, long id, OfflineRegionDefinition definition, byte[] metadata) {
        this.a = fileSource;
        this.b = id;
        this.c = definition;
        this.d = metadata;
        nativeCreate(offlineRegionPtr, fileSource);
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
