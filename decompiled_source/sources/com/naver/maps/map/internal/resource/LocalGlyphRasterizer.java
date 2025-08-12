package com.naver.maps.map.internal.resource;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.FrameMetricsAggregator;
import com.naver.maps.map.text.TypefaceFactory;

/* loaded from: classes.dex */
public class LocalGlyphRasterizer {
    private final TypefaceFactory a;
    private final a b;
    private final a c;
    private long handle;

    private native void nativeCreate(LocalGlyphRasterizer localGlyphRasterizer, boolean z);

    private native void nativeDestroy();

    private static String a(int i) {
        switch (i) {
            case 1:
                return "\u200d⚕️";
            case 2:
                return "\u200d⚖️";
            case 3:
                return "\u200d✈️";
            case 4:
                return "\u200d🌾";
            case 5:
                return "\u200d🍳";
            case 6:
                return "\u200d🎓";
            case 7:
                return "\u200d🎤";
            case 8:
                return "\u200d🎨";
            case 9:
                return "\u200d🏫";
            case 10:
                return "\u200d🏭";
            case 11:
                return "\u200d💻";
            case 12:
                return "\u200d💼";
            case 13:
                return "\u200d🔧";
            case 14:
                return "\u200d🔬";
            case 15:
                return "\u200d🚀";
            case 16:
                return "\u200d🚒";
            case 17:
                return "\u200d🦰";
            case 18:
                return "\u200d🦱";
            case 19:
                return "\u200d🦲";
            case 20:
                return "\u200d🦳";
            case 21:
            case 22:
            case 23:
            case 24:
            default:
                return "";
            case 25:
                return "\ue0067\ue0062\ue0077\ue006c\ue0073\ue007f";
            case 26:
                return "\ue0067\ue0062\ue0073\ue0063\ue0074\ue007f";
            case 27:
                return "\ue0067\ue0062\ue0065\ue006e\ue0067\ue007f";
            case 28:
                return "\u200d☠️";
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                return "️\u200d🌈";
            case 30:
                return "️\u200d🗨️";
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                return "️⃣";
        }
    }

    private static String b(int i) {
        switch (i) {
            case 1:
                return "\u200d❤️\u200d💋\u200d👨";
            case 2:
                return "\u200d❤️\u200d👨";
            case 3:
                return "\u200d👦\u200d👦";
            case 4:
                return "\u200d👦";
            case 5:
                return "\u200d👧\u200d👦";
            case 6:
                return "\u200d👧\u200d👧";
            case 7:
                return "\u200d👧";
            case 8:
                return "\u200d👨\u200d👦\u200d👦";
            case 9:
                return "\u200d👨\u200d👦";
            case 10:
                return "\u200d👨\u200d👧\u200d👦";
            case 11:
                return "\u200d👨\u200d👧\u200d👧";
            case 12:
                return "\u200d👨\u200d👧";
            case 13:
                return "\u200d👩\u200d👦\u200d👦";
            case 14:
                return "\u200d👩\u200d👦";
            case 15:
                return "\u200d👩\u200d👧\u200d👦";
            case 16:
                return "\u200d👩\u200d👧\u200d👧";
            case 17:
                return "\u200d👩\u200d👧";
            case TypedValues.TYPE_TARGET /* 101 */:
                return "\u200d❤️\u200d💋\u200d👨";
            case 102:
                return "\u200d❤️\u200d💋\u200d👩";
            case 103:
                return "\u200d❤️\u200d👨";
            case 104:
                return "\u200d❤️\u200d👩";
            case 105:
                return "\u200d👦\u200d👦";
            case 106:
                return "\u200d👦";
            case 107:
                return "\u200d👧\u200d👦";
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                return "\u200d👧\u200d👧";
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                return "\u200d👧";
            case 110:
                return "\u200d👩\u200d👦\u200d👦";
            case 111:
                return "\u200d👩\u200d👦";
            case 112:
                return "\u200d👩\u200d👧\u200d👦";
            case 113:
                return "\u200d👩\u200d👧\u200d👧";
            case 114:
                return "\u200d👩\u200d👧";
            default:
                return "";
        }
    }

    private static class a {
        private final int a;
        private final int b;
        private final int c;
        private final Bitmap d;
        private final Paint e;
        private final Canvas f;
        private final Rect g;
        private Typeface h;

        private a(float f, boolean z) {
            this.a = (!z || f <= 1.0f) ? 1 : 2;
            this.b = this.a * 24;
            this.c = this.a * 4;
            this.d = Bitmap.createBitmap(this.b + (this.c * 2), this.b + (this.c * 2), z ? Bitmap.Config.ARGB_8888 : Bitmap.Config.ALPHA_8);
            this.e = new Paint();
            this.e.setAntiAlias(true);
            this.e.setTextSize(this.b);
            this.f = new Canvas();
            this.f.setBitmap(this.d);
            this.g = new Rect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Glyph a(int i, String str, Typeface typeface) {
            if (!typeface.equals(this.h)) {
                this.e.setTypeface(typeface);
                this.h = typeface;
            }
            this.e.getTextBounds(str, 0, str.length(), this.g);
            this.f.drawColor(0, PorterDuff.Mode.CLEAR);
            this.f.drawText(str, 0.0f, this.b, this.e);
            return new Glyph(i, this.d.copy(this.d.getConfig(), false), 4 - (this.g.left / this.a), -4, ((this.g.width() + this.g.left) / this.a) + 1);
        }
    }

    public LocalGlyphRasterizer(TypefaceFactory typefaceFactory, float pixelRatio, boolean enableForCjk) {
        this.a = typefaceFactory;
        this.b = new a(pixelRatio, false);
        this.c = new a(pixelRatio, true);
        nativeCreate(this, enableForCjk);
    }

    protected void finalize() throws Throwable {
        try {
            nativeDestroy();
        } finally {
            super.finalize();
        }
    }

    private Glyph[] drawGlyphs(boolean bold, int[] emojiXCodePoints, boolean[] colors) {
        int length = emojiXCodePoints.length;
        Glyph[] glyphArr = new Glyph[length];
        for (int i = 0; i < length; i++) {
            int i2 = emojiXCodePoints[i];
            int i3 = 2097151 & i2;
            String strValueOf = String.valueOf(Character.toChars(i3));
            if ((Integer.MIN_VALUE & i2) != 0) {
                int i4 = (i2 >> 21) & FrameMetricsAggregator.EVERY_DURATION;
                if ((1073741824 & i2) != 0) {
                    strValueOf = strValueOf + b(i4);
                } else {
                    strValueOf = strValueOf + String.valueOf(Character.toChars(i4 + 127462));
                }
            } else if (i3 != i2) {
                int i5 = i2 >>> 28;
                if (i5 != 0) {
                    strValueOf = strValueOf + String.valueOf(Character.toChars(i5 + 127994));
                }
                if ((134217728 & i2) != 0) {
                    strValueOf = strValueOf + "\u200d♀️";
                } else if ((67108864 & i2) != 0) {
                    strValueOf = strValueOf + "\u200d♂️";
                } else {
                    int i6 = 65011712 & i2;
                    if (i6 != 0) {
                        strValueOf = strValueOf + a(i6 >>> 21);
                    }
                }
            }
            Typeface typeface = this.a.getTypeface(bold, i3);
            if (typeface == null) {
                typeface = bold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT;
            }
            glyphArr[i] = (colors[i] ? this.c : this.b).a(i2, strValueOf, typeface);
        }
        return glyphArr;
    }
}
