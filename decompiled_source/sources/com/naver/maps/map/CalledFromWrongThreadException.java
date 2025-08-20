package com.naver.maps.map;

/* loaded from: classes.dex */
public class CalledFromWrongThreadException extends RuntimeException {
    public CalledFromWrongThreadException() {
        super("Only the main thread can call this method.");
    }
}
