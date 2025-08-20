package com.naver.maps.map.internal.util;

import java.text.Normalizer;

/* loaded from: classes.dex */
public final class StringUtils {
    public static String unaccent(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("(\\p{InCombiningDiacriticalMarks}|\\p{InCombiningDiacriticalMarksForSymbols}|\\p{InCombiningDiacriticalMarksSupplement})+", "");
    }
}
