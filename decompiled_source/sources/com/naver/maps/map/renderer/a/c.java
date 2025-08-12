package com.naver.maps.map.renderer.a;

import com.naver.maps.map.renderer.MapRenderer;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class c extends Thread {
    protected boolean b;
    protected boolean c;
    protected boolean d;
    protected boolean e;
    protected boolean f;
    protected boolean g;
    protected boolean h;
    protected int i;
    protected int j;
    protected Object k;
    private final MapRenderer m;
    private final a n;
    protected final Object a = new Object();
    private final List<Runnable> l = new ArrayList();

    public interface b {
        boolean a();

        Object b();
    }

    public c(MapRenderer mapRenderer, b bVar, boolean z, boolean z2) {
        this.m = mapRenderer;
        this.n = new a(bVar, z, z2);
    }

    public void a() {
        synchronized (this.a) {
            this.b = true;
            this.a.notifyAll();
        }
    }

    public void a(Runnable runnable) {
        synchronized (this.a) {
            this.l.add(runnable);
            this.a.notifyAll();
        }
    }

    public void b() {
        synchronized (this.a) {
            this.g = true;
            this.a.notifyAll();
            while (!this.h) {
                try {
                    this.a.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Object obj;
        int i;
        Runnable runnableRemove;
        int i2;
        boolean z;
        boolean z2;
        while (true) {
            try {
                synchronized (this.a) {
                    obj = this.k;
                    while (!this.g) {
                        i = -1;
                        if (this.l.isEmpty()) {
                            if (this.f) {
                                this.n.g();
                                this.f = false;
                            } else if (this.e) {
                                this.n.h();
                                this.e = false;
                            } else if (this.k == null || this.i == 0 || this.j == 0 || this.d || !this.b) {
                                this.a.wait();
                            } else {
                                i = this.i;
                                int i3 = this.j;
                                if (this.n.g == EGL10.EGL_NO_CONTEXT) {
                                    z = true;
                                    i2 = i3;
                                    runnableRemove = null;
                                    z2 = false;
                                } else if (this.n.h == EGL10.EGL_NO_SURFACE) {
                                    z2 = true;
                                    i2 = i3;
                                    runnableRemove = null;
                                    z = false;
                                } else {
                                    this.b = false;
                                    i2 = i3;
                                    runnableRemove = null;
                                    z = false;
                                    z2 = false;
                                }
                            }
                            i2 = -1;
                            runnableRemove = null;
                            z = false;
                            z2 = false;
                        } else {
                            runnableRemove = this.l.remove(0);
                            i2 = -1;
                            z = false;
                            z2 = false;
                        }
                    }
                    this.n.f();
                    synchronized (this.a) {
                        this.h = true;
                        this.a.notifyAll();
                    }
                    return;
                }
                if (runnableRemove == null) {
                    GL10 gl10B = this.n.b();
                    if (!z) {
                        if (!z2) {
                            if (!this.c) {
                                if (this.n.h != EGL10.EGL_NO_SURFACE) {
                                    this.m.onDrawFrame(gl10B);
                                    int iE = this.n.e();
                                    switch (iE) {
                                        case 12288:
                                            break;
                                        case 12302:
                                            com.naver.maps.map.log.c.c("Context lost. Waiting for re-aquire", new Object[0]);
                                            synchronized (this.a) {
                                                if (obj == this.k) {
                                                    this.k = null;
                                                    this.f = true;
                                                    this.e = true;
                                                }
                                            }
                                            break;
                                        default:
                                            com.naver.maps.map.log.c.c("eglSwapBuffer error: %d. Waiting or new surface", Integer.valueOf(iE));
                                            synchronized (this.a) {
                                                if (obj == this.k) {
                                                    this.k = null;
                                                    this.f = true;
                                                }
                                            }
                                            break;
                                    }
                                }
                            } else {
                                this.m.onSurfaceChanged(gl10B, i, i2);
                                this.c = false;
                            }
                        } else {
                            synchronized (this.a) {
                                this.n.c();
                            }
                            this.m.onSurfaceChanged(gl10B, i, i2);
                        }
                    } else {
                        this.n.a();
                        synchronized (this.a) {
                            if (this.n.c()) {
                                this.m.onSurfaceCreated(gl10B, this.n.e);
                                this.m.onSurfaceChanged(gl10B, i, i2);
                            } else {
                                this.f = true;
                            }
                        }
                    }
                } else {
                    runnableRemove.run();
                }
            } catch (InterruptedException e) {
                this.n.f();
                synchronized (this.a) {
                    this.h = true;
                    this.a.notifyAll();
                    return;
                }
            } catch (Throwable th) {
                this.n.f();
                synchronized (this.a) {
                    this.h = true;
                    this.a.notifyAll();
                    throw th;
                }
            }
        }
    }

    private static class a {
        private final b a;
        private final boolean b;
        private final boolean c;
        private EGL10 d;
        private EGLConfig e;
        private EGLDisplay f = EGL10.EGL_NO_DISPLAY;
        private EGLContext g = EGL10.EGL_NO_CONTEXT;
        private EGLSurface h = EGL10.EGL_NO_SURFACE;

        a(b bVar, boolean z, boolean z2) {
            this.a = bVar;
            this.b = z;
            this.c = z2;
        }

        void a() {
            this.d = (EGL10) EGLContext.getEGL();
            if (this.f == EGL10.EGL_NO_DISPLAY) {
                this.f = this.d.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                if (this.f == EGL10.EGL_NO_DISPLAY) {
                    throw new RuntimeException("eglGetDisplay failed");
                }
                if (!this.d.eglInitialize(this.f, new int[2])) {
                    throw new RuntimeException("eglInitialize failed");
                }
            }
            if (!this.a.a()) {
                this.e = null;
                this.g = EGL10.EGL_NO_CONTEXT;
            } else if (this.g == EGL10.EGL_NO_CONTEXT) {
                this.e = new com.naver.maps.map.renderer.a.a(this.b, this.c).chooseConfig(this.d, this.f);
                this.g = this.d.eglCreateContext(this.f, this.e, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            }
            if (this.g == EGL10.EGL_NO_CONTEXT) {
                throw new RuntimeException("createContext");
            }
        }

        GL10 b() {
            return (GL10) this.g.getGL();
        }

        boolean c() {
            g();
            Object objB = this.a.b();
            if (objB != null) {
                try {
                    this.h = this.d.eglCreateWindowSurface(this.f, this.e, objB, new int[]{12344});
                } catch (UnsupportedOperationException e) {
                    this.h = EGL10.EGL_NO_SURFACE;
                    com.naver.maps.map.log.c.d("createWindowSurface failed with window " + objB, new Object[0]);
                }
            } else {
                this.h = EGL10.EGL_NO_SURFACE;
            }
            if (this.h == null || this.h == EGL10.EGL_NO_SURFACE) {
                if (this.d.eglGetError() == 12299) {
                    com.naver.maps.map.log.c.c("createWindowSurface returned EGL_BAD_NATIVE_WINDOW.", new Object[0]);
                }
                return false;
            }
            return d();
        }

        boolean d() {
            if (!this.d.eglMakeCurrent(this.f, this.h, this.h, this.g)) {
                com.naver.maps.map.log.c.c("eglMakeCurrent: %d", Integer.valueOf(this.d.eglGetError()));
                return false;
            }
            return true;
        }

        int e() {
            if (!this.d.eglSwapBuffers(this.f, this.h)) {
                return this.d.eglGetError();
            }
            return 12288;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            if (this.h == EGL10.EGL_NO_SURFACE) {
                return;
            }
            if (!this.d.eglDestroySurface(this.f, this.h)) {
                com.naver.maps.map.log.c.c("Could not destroy egl surface: Display %s, Surface %s", this.f.toString(), this.h.toString());
            }
            this.h = EGL10.EGL_NO_SURFACE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            if (this.g == EGL10.EGL_NO_CONTEXT) {
                return;
            }
            if (!this.d.eglDestroyContext(this.f, this.g)) {
                com.naver.maps.map.log.c.c("Could not destroy egl context: Display %s, Context %s", this.f.toString(), this.g.toString());
            }
            this.g = EGL10.EGL_NO_CONTEXT;
        }

        private void i() {
            if (this.f == EGL10.EGL_NO_DISPLAY) {
                return;
            }
            if (!this.d.eglTerminate(this.f)) {
                com.naver.maps.map.log.c.c("Could not terminate egl: Display %s", this.f.toString());
            }
            this.f = EGL10.EGL_NO_DISPLAY;
        }

        void f() {
            g();
            h();
            i();
        }
    }
}
