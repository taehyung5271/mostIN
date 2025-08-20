package com.naver.maps.map.style.layers;

/* loaded from: classes.dex */
public class TransitionOptions {
    private long a;
    private long b;

    public TransitionOptions(long duration, long delay) {
        this.a = duration;
        this.b = delay;
    }

    public static TransitionOptions fromTransitionOptions(long duration, long delay) {
        return new TransitionOptions(duration, delay);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransitionOptions transitionOptions = (TransitionOptions) o;
        if (this.a == transitionOptions.a && this.b == transitionOptions.b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((int) (this.a ^ (this.a >>> 32))) * 31) + ((int) (this.b ^ (this.b >>> 32)));
    }

    public String toString() {
        return "TransitionOptions{duration=" + this.a + ", delay=" + this.b + '}';
    }
}
