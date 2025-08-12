package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* loaded from: classes.dex */
public class StatusCallback extends IStatusCallback.Stub {
    private final BaseImplementation.ResultHolder<Status> resultHolder;

    public StatusCallback(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.resultHolder = resultHolder;
    }

    @Override // com.google.android.gms.common.api.internal.IStatusCallback
    public void onResult(Status result) {
        this.resultHolder.setResult(result);
    }
}
