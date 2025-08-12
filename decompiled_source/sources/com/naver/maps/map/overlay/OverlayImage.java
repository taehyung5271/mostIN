package com.naver.maps.map.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class OverlayImage {
    public final String id;

    public abstract Bitmap getBitmap(Context context);

    private static class c extends OverlayImage {
        private static final AtomicInteger a = new AtomicInteger();
        private final Bitmap b;

        private c(Bitmap bitmap) {
            super("bitmap:" + Integer.toHexString(a.incrementAndGet()));
            this.b = bitmap;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public Bitmap getBitmap(Context context) {
            return this.b;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public int getIntrinsicWidth(Context context) {
            return this.b.getWidth();
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public int getIntrinsicHeight(Context context) {
            return this.b.getHeight();
        }
    }

    private static class e extends OverlayImage {
        private final int a;

        private e(int i) {
            super("resource:" + Integer.toHexString(i));
            this.a = i;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public Bitmap getBitmap(Context context) {
            return com.naver.maps.map.internal.util.a.b(context, a(context));
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public int getIntrinsicWidth(Context context) {
            Drawable drawableA = a(context);
            if (drawableA == null) {
                return 0;
            }
            return drawableA.getIntrinsicWidth();
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public int getIntrinsicHeight(Context context) {
            Drawable drawableA = a(context);
            if (drawableA == null) {
                return 0;
            }
            return drawableA.getIntrinsicHeight();
        }

        private Drawable a(Context context) {
            return com.naver.maps.map.internal.util.a.a(context, this.a);
        }
    }

    private static abstract class f extends OverlayImage {
        protected abstract InputStream a(Context context) throws IOException;

        private f(String str) {
            super(str);
        }

        @Override // com.naver.maps.map.overlay.OverlayImage
        public Bitmap getBitmap(Context context) throws Throwable {
            InputStream inputStreamA;
            Throwable th;
            try {
                inputStreamA = a(context);
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamA);
                    if (inputStreamA != null) {
                        try {
                            inputStreamA.close();
                        } catch (IOException e) {
                        }
                    }
                    return bitmapDecodeStream;
                } catch (IOException e2) {
                    if (inputStreamA != null) {
                        try {
                            inputStreamA.close();
                        } catch (IOException e3) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStreamA != null) {
                        try {
                            inputStreamA.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                inputStreamA = null;
            } catch (Throwable th3) {
                inputStreamA = null;
                th = th3;
            }
        }
    }

    private static class b extends f {
        private final String a;

        private b(String str) {
            super("asset:" + str);
            this.a = str;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage.f
        protected InputStream a(Context context) throws IOException {
            return context.getAssets().open(this.a);
        }
    }

    private static class d extends f {
        private final String a;

        private d(String str) {
            super("file:" + str);
            this.a = str;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage.f
        protected InputStream a(Context context) throws IOException {
            return context.openFileInput(this.a);
        }
    }

    private static class a extends f {
        private final File a;

        private a(File file) {
            super("file:" + file.getAbsolutePath());
            this.a = file;
        }

        @Override // com.naver.maps.map.overlay.OverlayImage.f
        protected InputStream a(Context context) throws IOException {
            return new FileInputStream(this.a);
        }
    }

    public static OverlayImage fromView(View view) {
        return new c(com.naver.maps.map.internal.util.a.a(view));
    }

    public static OverlayImage fromBitmap(Bitmap bitmap) {
        return new c(bitmap);
    }

    public static OverlayImage fromResource(int resourceId) {
        return new e(resourceId);
    }

    public static OverlayImage fromAsset(String assetName) {
        return new b(assetName);
    }

    public static OverlayImage fromPrivateFile(String fileName) {
        return new d(fileName);
    }

    public static OverlayImage fromPath(String absolutePath) {
        return new a(new File(absolutePath));
    }

    public static OverlayImage fromFile(File file) {
        return new a(file);
    }

    private OverlayImage(String id) {
        this.id = id;
    }

    public int getIntrinsicWidth(Context context) {
        return 0;
    }

    public int getIntrinsicHeight(Context context) {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.id.equals(((OverlayImage) o).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return "OverlayImage{id='" + this.id + "'}";
    }
}
