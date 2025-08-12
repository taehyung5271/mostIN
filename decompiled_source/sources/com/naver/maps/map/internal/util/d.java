package com.naver.maps.map.internal.util;

import android.content.Context;
import androidx.core.os.ConfigurationCompat;
import java.util.Locale;

/* loaded from: classes.dex */
public final class d {
    public static String a(Context context, Locale locale) {
        if (locale == null) {
            locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        }
        return a(locale);
    }

    private static String a(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (language.isEmpty() || !language.matches("\\p{Alpha}{2,8}")) {
            language = "und";
        } else if ("iw".equals(language)) {
            language = "he";
        } else if ("in".equals(language)) {
            language = "id";
        } else if ("ji".equals(language)) {
            language = "yi";
        }
        if (!country.matches("\\p{Alpha}{2}|\\p{Digit}{3}")) {
            country = "";
        }
        return country.isEmpty() ? language : language + '-' + country;
    }
}
