package com.naver.maps.map;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class MapView extends FrameLayout {
    private e a;
    private g b;
    private d c;
    private c d;

    public MapView(Context context) {
        super(context);
        a(context, NaverMapOptions.a(context, (AttributeSet) null));
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, NaverMapOptions.a(context, attrs));
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context, NaverMapOptions.a(context, attrs));
    }

    public MapView(Context context, NaverMapOptions options) {
        super(context);
        a(context, options == null ? NaverMapOptions.a(context, (AttributeSet) null) : options);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void a(android.content.Context r18, final com.naver.maps.map.NaverMapOptions r19) {
        /*
            r17 = this;
            r11 = r17
            r12 = r18
            boolean r0 = r17.isInEditMode()
            if (r0 == 0) goto Lb
            return
        Lb:
            com.naver.maps.map.internal.a.a(r18)
            int r0 = com.naver.maps.map.R.layout.navermap_map_view
            inflate(r12, r0, r11)
            int r0 = com.naver.maps.map.R.string.navermap_map
            java.lang.String r0 = r12.getString(r0)
            r11.setContentDescription(r0)
            r13 = 0
            r11.setWillNotDraw(r13)
            boolean r0 = r19.e()
            if (r0 == 0) goto L56
            com.naver.maps.map.renderer.vulkan.VulkanSurfaceView r8 = new com.naver.maps.map.renderer.vulkan.VulkanSurfaceView
            android.content.Context r0 = r17.getContext()
            r8.<init>(r0)
            com.naver.maps.map.MapView$1 r9 = new com.naver.maps.map.MapView$1
            android.content.Context r2 = r17.getContext()
            float r4 = r19.d()
            java.lang.Class r5 = r19.g()
            boolean r6 = r19.h()
            boolean r7 = r19.isZOrderMediaOverlay()
            r0 = r9
            r1 = r17
            r3 = r8
            r0.<init>(r2, r3, r4, r5, r6, r7)
            boolean r0 = r9.f()
            if (r0 == 0) goto L56
        L55:
            goto L58
        L56:
            r8 = 0
            r9 = r8
        L58:
            if (r9 != 0) goto Lc6
            boolean r0 = r19.isUseTextureView()
            if (r0 == 0) goto L90
            android.view.TextureView r9 = new android.view.TextureView
            android.content.Context r0 = r17.getContext()
            r9.<init>(r0)
            com.naver.maps.map.MapView$2 r10 = new com.naver.maps.map.MapView$2
            android.content.Context r2 = r17.getContext()
            float r4 = r19.d()
            java.lang.Class r5 = r19.g()
            boolean r6 = r19.h()
            boolean r7 = r19.isMsaaEnabled()
            boolean r8 = r19.isTranslucentTextureSurface()
            r0 = r10
            r1 = r17
            r3 = r9
            r0.<init>(r2, r3, r4, r5, r6, r7, r8)
            r15 = r19
            r8 = r9
            r3 = r10
            goto Lc9
        L90:
            com.naver.maps.map.MapView$3 r14 = new com.naver.maps.map.MapView$3
            android.content.Context r0 = r17.getContext()
            r15 = r19
            r14.<init>(r0)
            com.naver.maps.map.MapView$4 r16 = new com.naver.maps.map.MapView$4
            android.content.Context r2 = r17.getContext()
            float r4 = r19.d()
            java.lang.Class r5 = r19.g()
            boolean r6 = r19.h()
            boolean r7 = r19.isMsaaEnabled()
            boolean r8 = r19.isZOrderMediaOverlay()
            boolean r9 = r19.isPreserveEGLContextOnPause()
            r0 = r16
            r1 = r17
            r3 = r14
            r10 = r14
            r0.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            r8 = r14
            r3 = r16
            goto Lc9
        Lc6:
            r15 = r19
            r3 = r9
        Lc9:
            r11.addView(r8, r13)
            int r0 = com.naver.maps.map.R.id.navermap_map_controls
            android.view.View r0 = r11.findViewById(r0)
            r4 = r0
            com.naver.maps.map.MapControlsView r4 = (com.naver.maps.map.MapControlsView) r4
            com.naver.maps.map.e r6 = new com.naver.maps.map.e
            com.naver.maps.map.MapView$5 r5 = new com.naver.maps.map.MapView$5
            r5.<init>()
            r0 = r6
            r1 = r18
            r2 = r19
            r0.<init>(r1, r2, r3, r4, r5)
            r11.a = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.naver.maps.map.MapView.a(android.content.Context, com.naver.maps.map.NaverMapOptions):void");
    }

    public void onCreate(Bundle bundle) {
        setBackgroundColor(0);
        this.a.a(bundle);
    }

    public void onStart() {
        this.a.d();
    }

    public void onResume() {
        this.a.e();
    }

    public void onPause() {
        this.a.f();
    }

    public void onStop() {
        this.a.g();
    }

    public void onDestroy() {
        if (this.a.b() != null) {
            setBackgroundColor(this.a.b().getBackgroundColor());
        }
        this.a.h();
    }

    public void onSaveInstanceState(Bundle outState) {
        this.a.b(outState);
    }

    public void onLowMemory() {
        this.a.i();
    }

    public void getMapAsync(OnMapReadyCallback callback) {
        this.a.a(callback);
    }

    @Override // android.view.View
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        if (isInEditMode()) {
            return;
        }
        this.a.a(width, height);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return (this.b != null && this.b.g(event)) || super.onTouchEvent(event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (this.d != null && this.d.a(keyCode, event)) || super.onKeyDown(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return (this.d != null && this.d.b(keyCode, event)) || super.onKeyLongPress(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return (this.d != null && this.d.c(keyCode, event)) || super.onKeyUp(keyCode, event);
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent event) {
        return (this.d != null && this.d.a(event)) || super.onTrackballEvent(event);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        return (this.c != null && this.c.a(event)) || super.onGenericMotionEvent(event);
    }
}
