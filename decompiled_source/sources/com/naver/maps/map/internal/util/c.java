package com.naver.maps.map.internal.util;

import com.naver.maps.map.CalledFromWrongThreadException;

/* loaded from: classes.dex */
public final class c {
    public static void a(Thread thread) {
        if (!Thread.currentThread().equals(thread)) {
            throw new CalledFromWrongThreadException();
        }
    }
}
