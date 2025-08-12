package com.naver.maps.map.internal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;

/* loaded from: classes.dex */
public final class a {
    public static Drawable a(Context context, int i) {
        if (i <= 0) {
            return null;
        }
        return AppCompatResources.getDrawable(context, i);
    }

    public static Bitmap a(View view) {
        view.measure(0, 0);
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        view.layout(0, 0, measuredWidth, measuredHeight);
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        if (measuredWidth <= 0) {
            measuredWidth = 16;
        }
        if (measuredHeight <= 0) {
            measuredHeight = 16;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(displayMetrics, measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap a(Context context, Drawable drawable) {
        Bitmap bitmap;
        if ((drawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
            return bitmap;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 16;
        }
        if (intrinsicHeight <= 0) {
            intrinsicHeight = 16;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(displayMetrics, intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap b(Context context, Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return a(context, drawable);
    }
}
