package com.google.android.gms.internal.base;

import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.ItemTouchHelper;

/* compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* loaded from: classes.dex */
public final class zak extends Drawable implements Drawable.Callback {
    private int zaa;
    private long zab;
    private int zac;
    private int zad;
    private int zae;
    private int zaf;
    private boolean zag;
    private boolean zah;
    private zaj zai;
    private Drawable zaj;
    private Drawable zak;
    private boolean zal;
    private boolean zam;
    private boolean zan;
    private int zao;

    public zak(Drawable drawable, Drawable drawable2) {
        this(null);
        drawable = drawable == null ? zai.zaa : drawable;
        this.zaj = drawable;
        drawable.setCallback(this);
        zaj zajVar = this.zai;
        zajVar.zab = drawable.getChangingConfigurations() | zajVar.zab;
        drawable2 = drawable2 == null ? zai.zaa : drawable2;
        this.zak = drawable2;
        drawable2.setCallback(this);
        zaj zajVar2 = this.zai;
        zajVar2.zab = drawable2.getChangingConfigurations() | zajVar2.zab;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        zaj zajVar = this.zai;
        return changingConfigurations | zajVar.zaa | zajVar.zab;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!zac()) {
            return null;
        }
        this.zai.zaa = getChangingConfigurations();
        return this.zai;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return Math.max(this.zaj.getIntrinsicHeight(), this.zak.getIntrinsicHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.max(this.zaj.getIntrinsicWidth(), this.zak.getIntrinsicWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        if (!this.zan) {
            this.zao = Drawable.resolveOpacity(this.zaj.getOpacity(), this.zak.getOpacity());
            this.zan = true;
        }
        return this.zao;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        if (!this.zah && super.mutate() == this) {
            if (!zac()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zaj.mutate();
            this.zak.mutate();
            this.zah = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    protected final void onBoundsChange(Rect rect) {
        this.zaj.setBounds(rect);
        this.zak.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.zaf == this.zad) {
            this.zaf = i;
        }
        this.zad = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.zaj.setColorFilter(colorFilter);
        this.zak.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zaa() {
        return this.zak;
    }

    public final void zab(int i) {
        this.zac = this.zad;
        this.zaf = 0;
        this.zae = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        this.zaa = 1;
        invalidateSelf();
    }

    public final boolean zac() {
        if (!this.zal) {
            boolean z = false;
            if (this.zaj.getConstantState() != null && this.zak.getConstantState() != null) {
                z = true;
            }
            this.zam = z;
            this.zal = true;
        }
        return this.zam;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void draw(android.graphics.Canvas r8) {
        /*
            r7 = this;
            int r0 = r7.zaa
            r1 = 1
            r2 = 0
            switch(r0) {
                case 1: goto L3a;
                case 2: goto L9;
                default: goto L7;
            }
        L7:
            r4 = r1
            goto L44
        L9:
            long r3 = r7.zab
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 < 0) goto L38
            long r3 = android.os.SystemClock.uptimeMillis()
            long r5 = r7.zab
            long r3 = r3 - r5
            int r0 = r7.zae
            float r0 = (float) r0
            float r3 = (float) r3
            float r3 = r3 / r0
            r0 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r4 < 0) goto L25
            r4 = r1
            goto L26
        L25:
            r4 = r2
        L26:
            if (r4 == 0) goto L2a
            r7.zaa = r2
        L2a:
            float r0 = java.lang.Math.min(r3, r0)
            int r3 = r7.zac
            float r3 = (float) r3
            float r3 = r3 * r0
            r0 = 0
            float r3 = r3 + r0
            int r0 = (int) r3
            r7.zaf = r0
            goto L44
        L38:
            r4 = r1
            goto L44
        L3a:
            long r3 = android.os.SystemClock.uptimeMillis()
            r7.zab = r3
            r0 = 2
            r7.zaa = r0
            r4 = r2
        L44:
            int r0 = r7.zaf
            boolean r3 = r7.zag
            android.graphics.drawable.Drawable r5 = r7.zaj
            android.graphics.drawable.Drawable r6 = r7.zak
            if (r4 == 0) goto L64
            if (r3 == 0) goto L54
            if (r0 != 0) goto L53
            goto L55
        L53:
            goto L59
        L54:
            r2 = r0
        L55:
            r5.draw(r8)
            r0 = r2
        L59:
            int r1 = r7.zad
            if (r0 != r1) goto L63
            r6.setAlpha(r1)
            r6.draw(r8)
        L63:
            return
        L64:
            if (r3 == 0) goto L6d
            int r2 = r7.zad
            int r2 = r2 - r0
            r5.setAlpha(r2)
            goto L6e
        L6d:
            r1 = r2
        L6e:
            r5.draw(r8)
            if (r1 == 0) goto L78
            int r1 = r7.zad
            r5.setAlpha(r1)
        L78:
            if (r0 <= 0) goto L85
            r6.setAlpha(r0)
            r6.draw(r8)
            int r8 = r7.zad
            r6.setAlpha(r8)
        L85:
            r7.invalidateSelf()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.base.zak.draw(android.graphics.Canvas):void");
    }

    zak(zaj zajVar) {
        this.zaa = 0;
        this.zad = 255;
        this.zaf = 0;
        this.zag = true;
        this.zai = new zaj(zajVar);
    }
}
