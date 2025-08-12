package com.google.android.gms.common.images;

/* compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* loaded from: classes.dex */
public final class Size {
    private final int zaa;
    private final int zab;

    public Size(int i, int i2) {
        this.zaa = i;
        this.zab = i2;
    }

    public static Size parseSize(String string) throws NumberFormatException {
        if (string == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int iIndexOf = string.indexOf(42);
        if (iIndexOf < 0) {
            iIndexOf = string.indexOf(120);
        }
        if (iIndexOf < 0) {
            throw zaa(string);
        }
        try {
            return new Size(Integer.parseInt(string.substring(0, iIndexOf)), Integer.parseInt(string.substring(iIndexOf + 1)));
        } catch (NumberFormatException e) {
            throw zaa(string);
        }
    }

    private static NumberFormatException zaa(String str) {
        throw new NumberFormatException("Invalid Size: \"" + str + "\"");
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            if (this.zaa == size.zaa && this.zab == size.zab) {
                return true;
            }
        }
        return false;
    }

    public int getHeight() {
        return this.zab;
    }

    public int getWidth() {
        return this.zaa;
    }

    public int hashCode() {
        int i = this.zaa;
        return ((i >>> 16) | (i << 16)) ^ this.zab;
    }

    public String toString() {
        return this.zaa + "x" + this.zab;
    }
}
