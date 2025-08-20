package com.naver.maps.map.log;

/* loaded from: classes.dex */
final class NativeLogger {
    private static void debug(String message) {
        c.a(a(message), new Object[0]);
    }

    private static void info(String message) {
        c.b(a(message), new Object[0]);
    }

    private static void warning(String message) {
        c.c(a(message), new Object[0]);
    }

    private static void error(String message) {
        c.d(a(message), new Object[0]);
    }

    private static void setLastMessage(String message) {
        c.a(message);
    }

    private static String a(String str) {
        return str.replaceAll("%", "%%");
    }

    private NativeLogger() {
    }
}
