package com.naver.maps.map.renderer.vulkan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class VulkanSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {
    private static final d a = new d();
    private final WeakReference<VulkanSurfaceView> b;
    private c c;
    private VulkanMapRenderer d;
    private a e;
    private boolean f;

    public interface a {
        void a();
    }

    public VulkanSurfaceView(Context context) {
        super(context);
        this.b = new WeakReference<>(this);
        e();
    }

    public VulkanSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.b = new WeakReference<>(this);
        e();
    }

    private void e() {
        getHolder().addCallback(this);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.c != null) {
                this.c.h();
            }
        } finally {
            super.finalize();
        }
    }

    public void setDetachedListener(a detachedListener) {
        if (this.e != null) {
            throw new IllegalArgumentException("Detached from window listener has been already set.");
        }
        this.e = detachedListener;
    }

    public void setRenderer(VulkanMapRenderer renderer) {
        f();
        this.d = renderer;
        this.c = new c(this.b);
        this.c.start();
    }

    public void setRenderMode(int renderMode) {
        this.c.a(renderMode);
    }

    public int getRenderMode() {
        return this.c.b();
    }

    public void a() {
        this.c.c();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        this.c.d();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder arg0) {
        this.c.e();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder_unused, int format, int w, int h) {
        this.c.a(w, h);
    }

    @Override // android.view.SurfaceHolder.Callback2
    public void surfaceRedrawNeededAsync(SurfaceHolder holder, Runnable finishDrawing) {
        if (this.c != null) {
            this.c.a(finishDrawing);
        }
    }

    @Override // android.view.SurfaceHolder.Callback2
    @Deprecated
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    public void b() {
        this.c.f();
    }

    public void c() {
        this.c.g();
    }

    public void a(Runnable runnable) {
        this.c.b(runnable);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onAttachedToWindow() {
        int iB;
        super.onAttachedToWindow();
        if (this.f && this.d != null) {
            if (this.c == null) {
                iB = 1;
            } else {
                iB = this.c.b();
            }
            this.c = new c(this.b);
            if (iB != 1) {
                this.c.a(iB);
            }
            this.c.start();
        }
        this.f = false;
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        if (this.e != null) {
            this.e.a();
        }
        if (this.c != null) {
            this.c.h();
        }
        this.f = true;
        super.onDetachedFromWindow();
    }

    private static class b {
        boolean a;
        boolean b;
        private final WeakReference<VulkanSurfaceView> c;

        private b(WeakReference<VulkanSurfaceView> weakReference) {
            this.a = false;
            this.b = false;
            this.c = weakReference;
        }

        public void a() {
            try {
                this.a = this.c.get() != null;
            } catch (Exception e) {
                com.naver.maps.map.log.c.d("createContext: %s", e.getMessage());
            }
        }

        boolean b() {
            e();
            VulkanSurfaceView vulkanSurfaceView = this.c.get();
            if (vulkanSurfaceView != null) {
                vulkanSurfaceView.d.a(vulkanSurfaceView.getHolder().getSurface());
                this.b = true;
            } else {
                this.b = false;
            }
            return this.b;
        }

        public int c() {
            return 0;
        }

        private void e() {
            if (this.b) {
                VulkanSurfaceView vulkanSurfaceView = this.c.get();
                if (vulkanSurfaceView != null) {
                    vulkanSurfaceView.d.g();
                }
                this.b = false;
            }
        }

        public void d() {
            this.a = false;
        }
    }

    static class c extends Thread {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private boolean q;
        private b t;
        private final WeakReference<VulkanSurfaceView> u;
        private boolean r = true;
        private Runnable s = null;
        private final ArrayList<Runnable> v = new ArrayList<>();
        private int l = 0;
        private int m = 0;
        private boolean o = true;
        private int n = 1;
        private boolean p = false;

        public c(WeakReference<VulkanSurfaceView> weakReference) {
            this.u = weakReference;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("VkThread " + getId());
            try {
                try {
                    k();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                VulkanSurfaceView.a.a(this);
                this.b = true;
            }
        }

        private void i() {
            if (this.i) {
                this.i = false;
            }
        }

        private void j() {
            if (this.h) {
                this.t.d();
                this.h = false;
                VulkanSurfaceView.a.b(this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:133:0x01c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private void k() throws java.lang.InterruptedException {
            /*
                Method dump skipped, instructions count: 460
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.naver.maps.map.renderer.vulkan.VulkanSurfaceView.c.k():void");
        }

        public boolean a() {
            return this.h && this.i && l();
        }

        private boolean l() {
            return !this.d && this.e && !this.f && this.l > 0 && this.m > 0 && (this.o || this.n == 1);
        }

        public void a(int i) {
            synchronized (VulkanSurfaceView.a) {
                this.n = i;
                VulkanSurfaceView.a.notifyAll();
            }
        }

        public int b() {
            int i;
            synchronized (VulkanSurfaceView.a) {
                i = this.n;
            }
            return i;
        }

        public void c() {
            synchronized (VulkanSurfaceView.a) {
                this.o = true;
                VulkanSurfaceView.a.notifyAll();
            }
        }

        public void a(Runnable runnable) {
            synchronized (VulkanSurfaceView.a) {
                if (Thread.currentThread() == this) {
                    return;
                }
                this.p = true;
                this.o = true;
                this.q = false;
                this.s = runnable;
                VulkanSurfaceView.a.notifyAll();
            }
        }

        public void d() {
            synchronized (VulkanSurfaceView.a) {
                this.e = true;
                this.j = false;
                VulkanSurfaceView.a.notifyAll();
                while (this.g && !this.j && !this.b) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void e() {
            synchronized (VulkanSurfaceView.a) {
                this.e = false;
                VulkanSurfaceView.a.notifyAll();
                while (!this.g && !this.b) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void f() {
            synchronized (VulkanSurfaceView.a) {
                this.c = true;
                VulkanSurfaceView.a.notifyAll();
                while (!this.b && !this.d) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void g() {
            synchronized (VulkanSurfaceView.a) {
                this.c = false;
                this.o = true;
                this.q = false;
                VulkanSurfaceView.a.notifyAll();
                while (!this.b && this.d && !this.q) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void a(int i, int i2) {
            synchronized (VulkanSurfaceView.a) {
                this.l = i;
                this.m = i2;
                this.r = true;
                this.o = true;
                this.q = false;
                if (Thread.currentThread() == this) {
                    return;
                }
                VulkanSurfaceView.a.notifyAll();
                while (!this.b && !this.d && !this.q && a()) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void h() {
            synchronized (VulkanSurfaceView.a) {
                this.a = true;
                VulkanSurfaceView.a.notifyAll();
                while (!this.b) {
                    try {
                        VulkanSurfaceView.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void b(Runnable runnable) {
            synchronized (VulkanSurfaceView.a) {
                this.v.add(runnable);
                VulkanSurfaceView.a.notifyAll();
            }
        }
    }

    private void f() {
        if (this.c != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private static class d {
        private d() {
        }

        synchronized void a(c cVar) {
            cVar.b = true;
            notifyAll();
        }

        void b(c cVar) {
            notifyAll();
        }
    }
}
