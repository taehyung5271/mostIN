package com.naver.maps.map.internal.util;

import android.graphics.Color;
import java.util.Locale;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class b {
    private static final Pattern a = Pattern.compile("rgba?\\s*\\(\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,\\s*(\\d+\\.?\\d*)\\s*,?\\s*(\\d+\\.?\\d*)?\\s*\\)");

    public static String a(int i) {
        return String.format(Locale.US, "rgba(%d, %d, %d, %.3f)", Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Float.valueOf(Color.alpha(i) / 255.0f));
    }
}
