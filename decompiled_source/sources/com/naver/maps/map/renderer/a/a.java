package com.naver.maps.map.renderer.a;

import android.opengl.GLSurfaceView;
import android.os.Build;
import androidx.core.os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.Collections;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/* loaded from: classes.dex */
public class a implements GLSurfaceView.EGLConfigChooser {
    private final boolean a;
    private final boolean b;

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(boolean z, boolean z2) {
        if (z == z2) {
            return 0;
        }
        return z ? 1 : -1;
    }

    private static int[] a(EGL10 egl10, EGLDisplay eGLDisplay, int[] iArr) {
        int[] iArr2 = new int[1];
        if (!egl10.eglChooseConfig(eGLDisplay, iArr, null, 0, iArr2)) {
            throw new com.naver.maps.map.renderer.a.b("eglChooseConfig(NULL) failed: " + egl10.eglGetError());
        }
        return iArr2;
    }

    private static EGLConfig[] a(EGL10 egl10, EGLDisplay eGLDisplay, int[] iArr, int[] iArr2) {
        EGLConfig[] eGLConfigArr = new EGLConfig[iArr2[0]];
        if (!egl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, iArr2[0], iArr2)) {
            throw new com.naver.maps.map.renderer.a.b("eglChooseConfig() failed: " + egl10.eglGetError());
        }
        return eGLConfigArr;
    }

    private enum b {
        Format16Bit(3),
        Format32BitNoAlpha(1),
        Format32BitAlpha(2),
        Format24Bit(0),
        Unknown(4);

        int f;

        b(int i) {
            this.f = i;
        }
    }

    private enum c {
        Format16Depth8Stencil(1),
        Format24Depth8Stencil(0);

        int c;

        c(int i) {
            this.c = i;
        }
    }

    /* renamed from: com.naver.maps.map.renderer.a.a$a, reason: collision with other inner class name */
    class C0013a implements Comparable<C0013a> {
        private final b a;
        private final c b;
        private final boolean c;
        private final boolean d;
        private final int e;
        private final int f;
        private final EGLConfig g;

        public C0013a(b bVar, c cVar, boolean z, boolean z2, int i, int i2, EGLConfig eGLConfig) {
            this.a = bVar;
            this.b = cVar;
            this.c = z;
            this.d = z2;
            this.e = i;
            this.f = i2;
            this.g = eGLConfig;
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(C0013a c0013a) {
            int iB = a.b(this.a.f, c0013a.a.f);
            if (iB == 0) {
                int iB2 = a.b(this.b.c, c0013a.b.c);
                if (iB2 == 0) {
                    int iB3 = a.b(this.c, c0013a.c);
                    if (iB3 == 0) {
                        int iB4 = a.b(this.d, c0013a.d);
                        if (iB4 == 0) {
                            int iB5 = a.b(this.f, c0013a.f);
                            if (iB5 == 0) {
                                int iB6 = a.b(this.e, c0013a.e);
                                if (iB6 != 0) {
                                    return iB6;
                                }
                                return 0;
                            }
                            return iB5;
                        }
                        return iB4;
                    }
                    return iB3;
                }
                return iB2;
            }
            return iB;
        }
    }

    private static EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr, boolean z) {
        b bVar;
        c cVar;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < eGLConfigArr.length; i++) {
            EGLConfig eGLConfig = eGLConfigArr[i];
            if (eGLConfig != null) {
                int iA = a(egl10, eGLDisplay, eGLConfig, 12325);
                int iA2 = a(egl10, eGLDisplay, eGLConfig, 12326);
                if ((iA == 24 || iA == 16) && iA2 == 8) {
                    int iA3 = a(egl10, eGLDisplay, eGLConfig, 12337);
                    if (z || (iA3 == 0 && a(egl10, eGLDisplay, eGLConfig, 12338) == 0)) {
                        int iA4 = a(egl10, eGLDisplay, eGLConfig, 12320);
                        int iA5 = a(egl10, eGLDisplay, eGLConfig, 12324);
                        int iA6 = a(egl10, eGLDisplay, eGLConfig, 12323);
                        int iA7 = a(egl10, eGLDisplay, eGLConfig, 12322);
                        int iA8 = a(egl10, eGLDisplay, eGLConfig, 12321);
                        if (iA4 == 16 && iA5 == 5 && iA6 == 6 && iA7 == 5 && iA8 == 0) {
                            bVar = b.Format16Bit;
                        } else if (iA4 == 32 && iA5 == 8 && iA6 == 8 && iA7 == 8 && iA8 == 0) {
                            bVar = b.Format32BitNoAlpha;
                        } else if (iA4 == 32 && iA5 == 8 && iA6 == 8 && iA7 == 8 && iA8 == 8) {
                            bVar = b.Format32BitAlpha;
                        } else if (iA4 == 24 && iA5 == 8 && iA6 == 8 && iA7 == 8 && iA8 == 0) {
                            bVar = b.Format24Bit;
                        }
                        if (iA == 16) {
                            cVar = c.Format16Depth8Stencil;
                        } else {
                            cVar = c.Format24Depth8Stencil;
                        }
                        arrayList.add(new C0013a(bVar, cVar, (a(egl10, eGLDisplay, eGLConfig, 12354) & 4) != 4, a(egl10, eGLDisplay, eGLConfig, 12327) != 12344, iA3, i, eGLConfig));
                    }
                }
            }
        }
        Collections.sort(arrayList);
        if (arrayList.size() == 0) {
            throw new com.naver.maps.map.renderer.a.b("No matching configurations after filtering");
        }
        C0013a c0013a = (C0013a) arrayList.get(0);
        if (c0013a.d) {
            com.naver.maps.map.log.c.c("Chosen config has a caveat.", new Object[0]);
        }
        if (c0013a.c) {
            com.naver.maps.map.log.c.c("Chosen config is not conformant.", new Object[0]);
        }
        return c0013a.g;
    }

    private static int a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i) {
        try {
            int[] iArr = new int[1];
            if (!egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr)) {
                throw new com.naver.maps.map.renderer.a.b("eglGetConfigAttrib(" + i + ") failed: " + egl10.eglGetError());
            }
            return iArr[0];
        } catch (IllegalArgumentException e) {
            throw new com.naver.maps.map.renderer.a.b("eglGetConfigAttrib(" + i + ") failed: " + e.getMessage());
        }
    }

    private static boolean a() {
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MANUFACTURER.contains("Genymotion") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT) || "vbox86p".equals(Build.PRODUCT) || System.getProperty("ro.kernel.qemu") != null;
    }

    private static int[] c(boolean z, boolean z2) {
        boolean zA = a();
        if (zA) {
            com.naver.maps.map.log.c.b("Running in emulator", new Object[0]);
        }
        int[] iArr = new int[29];
        iArr[0] = 12327;
        iArr[1] = 12344;
        iArr[2] = 12339;
        iArr[3] = 4;
        iArr[4] = 12320;
        iArr[5] = 16;
        iArr[6] = 12324;
        iArr[7] = 5;
        iArr[8] = 12323;
        iArr[9] = 6;
        iArr[10] = 12322;
        iArr[11] = 5;
        iArr[12] = 12321;
        iArr[13] = z2 ? '\b' : (char) 0;
        iArr[14] = 12325;
        iArr[15] = 16;
        iArr[16] = 12326;
        iArr[17] = 8;
        iArr[18] = zA ? 12344 : 12354;
        iArr[19] = 4;
        iArr[20] = zA ? 12344 : 12351;
        iArr[21] = 12430;
        iArr[22] = 12352;
        iArr[23] = 4;
        iArr[24] = 12338;
        iArr[25] = z ? 1 : 0;
        iArr[26] = 12337;
        iArr[27] = z ? 4 : 0;
        iArr[28] = 12344;
        return iArr;
    }

    public static EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, boolean z, boolean z2) {
        int[] iArrC = c(z, z2);
        int[] iArrA = a(egl10, eGLDisplay, iArrC);
        if (iArrA[0] < 1) {
            throw new com.naver.maps.map.renderer.a.b("eglChooseConfig() failed: returned no configs");
        }
        EGLConfig eGLConfigA = a(egl10, eGLDisplay, a(egl10, eGLDisplay, iArrC, iArrA), z);
        if (eGLConfigA == null) {
            throw new com.naver.maps.map.renderer.a.b("No config chosen");
        }
        return eGLConfigA;
    }

    public a(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
    }

    @Override // android.opengl.GLSurfaceView.EGLConfigChooser
    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        try {
            return a(egl, display, this.a, this.b);
        } catch (com.naver.maps.map.renderer.a.b e) {
            if (this.a) {
                com.naver.maps.map.log.c.d("MSAA not supported: " + e.getMessage(), new Object[0]);
                return a(egl, display, false, this.b);
            }
            throw e;
        }
    }
}
