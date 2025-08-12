package com.naver.maps.map.offline;

import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.internal.a;

/* loaded from: classes.dex */
public class OfflineManager {
    private long handle;

    public interface CreateOfflineRegionCallback {
        void onCreate(OfflineRegion offlineRegion);

        void onError(String str);
    }

    public interface FileSourceCallback {
        void onError(String str);

        void onSuccess();
    }

    public interface ListOfflineRegionsCallback {
        void onError(String str);

        void onList(OfflineRegion[] offlineRegionArr);
    }

    public interface MergeOfflineRegionsCallback {
        void onError(String str);

        void onMerge(OfflineRegion[] offlineRegionArr);
    }

    private native void createOfflineRegion(FileSource fileSource, OfflineRegionDefinition offlineRegionDefinition, byte[] bArr, CreateOfflineRegionCallback createOfflineRegionCallback);

    private native void initialize(FileSource fileSource);

    private native void listOfflineRegions(FileSource fileSource, ListOfflineRegionsCallback listOfflineRegionsCallback);

    private native void mergeOfflineRegions(FileSource fileSource, String str, MergeOfflineRegionsCallback mergeOfflineRegionsCallback);

    private native void nativeClearAmbientCache(FileSourceCallback fileSourceCallback);

    private native void nativeCreate(FileSource fileSource);

    private native void nativeDestroy() throws Throwable;

    private native void nativeInvalidateAmbientCache(FileSourceCallback fileSourceCallback);

    private native void nativePackDatabase(FileSourceCallback fileSourceCallback);

    private native void nativeResetDatabase(FileSourceCallback fileSourceCallback);

    private native void nativeSetMaximumAmbientCacheSize(long j, FileSourceCallback fileSourceCallback);

    public native void putResourceWithUrl(String str, byte[] bArr, long j, long j2, String str2, boolean z);

    public native void runPackDatabaseAutomatically(boolean z);

    static {
        a.a();
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }
}
