package org.brotli.dec;

/* loaded from: classes2.dex */
final class Transform {
    static final Transform[] TRANSFORMS = {new Transform("", 0, ""), new Transform("", 0, " "), new Transform(" ", 0, " "), new Transform("", 12, ""), new Transform("", 10, " "), new Transform("", 0, " the "), new Transform(" ", 0, ""), new Transform("s ", 0, " "), new Transform("", 0, " of "), new Transform("", 10, ""), new Transform("", 0, " and "), new Transform("", 13, ""), new Transform("", 1, ""), new Transform(", ", 0, " "), new Transform("", 0, ", "), new Transform(" ", 10, " "), new Transform("", 0, " in "), new Transform("", 0, " to "), new Transform("e ", 0, " "), new Transform("", 0, "\""), new Transform("", 0, "."), new Transform("", 0, "\">"), new Transform("", 0, "\n"), new Transform("", 3, ""), new Transform("", 0, "]"), new Transform("", 0, " for "), new Transform("", 14, ""), new Transform("", 2, ""), new Transform("", 0, " a "), new Transform("", 0, " that "), new Transform(" ", 10, ""), new Transform("", 0, ". "), new Transform(".", 0, ""), new Transform(" ", 0, ", "), new Transform("", 15, ""), new Transform("", 0, " with "), new Transform("", 0, "'"), new Transform("", 0, " from "), new Transform("", 0, " by "), new Transform("", 16, ""), new Transform("", 17, ""), new Transform(" the ", 0, ""), new Transform("", 4, ""), new Transform("", 0, ". The "), new Transform("", 11, ""), new Transform("", 0, " on "), new Transform("", 0, " as "), new Transform("", 0, " is "), new Transform("", 7, ""), new Transform("", 1, "ing "), new Transform("", 0, "\n\t"), new Transform("", 0, ":"), new Transform(" ", 0, ". "), new Transform("", 0, "ed "), new Transform("", 20, ""), new Transform("", 18, ""), new Transform("", 6, ""), new Transform("", 0, "("), new Transform("", 10, ", "), new Transform("", 8, ""), new Transform("", 0, " at "), new Transform("", 0, "ly "), new Transform(" the ", 0, " of "), new Transform("", 5, ""), new Transform("", 9, ""), new Transform(" ", 10, ", "), new Transform("", 10, "\""), new Transform(".", 0, "("), new Transform("", 11, " "), new Transform("", 10, "\">"), new Transform("", 0, "=\""), new Transform(" ", 0, "."), new Transform(".com/", 0, ""), new Transform(" the ", 0, " of the "), new Transform("", 10, "'"), new Transform("", 0, ". This "), new Transform("", 0, ","), new Transform(".", 0, " "), new Transform("", 10, "("), new Transform("", 10, "."), new Transform("", 0, " not "), new Transform(" ", 0, "=\""), new Transform("", 0, "er "), new Transform(" ", 11, " "), new Transform("", 0, "al "), new Transform(" ", 11, ""), new Transform("", 0, "='"), new Transform("", 11, "\""), new Transform("", 10, ". "), new Transform(" ", 0, "("), new Transform("", 0, "ful "), new Transform(" ", 10, ". "), new Transform("", 0, "ive "), new Transform("", 0, "less "), new Transform("", 11, "'"), new Transform("", 0, "est "), new Transform(" ", 10, "."), new Transform("", 11, "\">"), new Transform(" ", 0, "='"), new Transform("", 10, ","), new Transform("", 0, "ize "), new Transform("", 11, "."), new Transform("Â ", 0, ""), new Transform(" ", 0, ","), new Transform("", 10, "=\""), new Transform("", 11, "=\""), new Transform("", 0, "ous "), new Transform("", 11, ", "), new Transform("", 10, "='"), new Transform(" ", 10, ","), new Transform(" ", 11, "=\""), new Transform(" ", 11, ", "), new Transform("", 11, ","), new Transform("", 11, "("), new Transform("", 11, ". "), new Transform(" ", 11, "."), new Transform("", 11, "='"), new Transform(" ", 11, ". "), new Transform(" ", 10, "=\""), new Transform(" ", 11, "='"), new Transform(" ", 10, "='")};
    private final byte[] prefix;
    private final byte[] suffix;
    private final int type;

    Transform(String prefix, int type, String suffix) {
        this.prefix = readUniBytes(prefix);
        this.type = type;
        this.suffix = readUniBytes(suffix);
    }

    static byte[] readUniBytes(String uniBytes) {
        byte[] result = new byte[uniBytes.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) uniBytes.charAt(i);
        }
        return result;
    }

    static int transformDictionaryWord(byte[] dst, int dstOffset, byte[] word, int wordOffset, int len, Transform transform) {
        int offset = dstOffset;
        for (byte b : transform.prefix) {
            dst[offset] = b;
            offset++;
        }
        int offset2 = transform.type;
        int tmp = WordTransformType.getOmitFirst(offset2);
        if (tmp > len) {
            tmp = len;
        }
        int wordOffset2 = wordOffset + tmp;
        int len2 = (len - tmp) - WordTransformType.getOmitLast(offset2);
        int i = len2;
        while (i > 0) {
            dst[offset] = word[wordOffset2];
            i--;
            offset++;
            wordOffset2++;
        }
        if (offset2 == 11 || offset2 == 10) {
            int uppercaseOffset = offset - len2;
            if (offset2 == 10) {
                len2 = 1;
            }
            while (len2 > 0) {
                int tmp2 = dst[uppercaseOffset] & 255;
                if (tmp2 < 192) {
                    if (tmp2 >= 97 && tmp2 <= 122) {
                        dst[uppercaseOffset] = (byte) (dst[uppercaseOffset] ^ 32);
                    }
                    uppercaseOffset++;
                    len2--;
                } else if (tmp2 < 224) {
                    int i2 = uppercaseOffset + 1;
                    dst[i2] = (byte) (dst[i2] ^ 32);
                    uppercaseOffset += 2;
                    len2 -= 2;
                } else {
                    int i3 = uppercaseOffset + 2;
                    dst[i3] = (byte) (dst[i3] ^ 5);
                    uppercaseOffset += 3;
                    len2 -= 3;
                }
            }
        }
        byte[] string = transform.suffix;
        for (byte b2 : string) {
            dst[offset] = b2;
            offset++;
        }
        return offset - dstOffset;
    }
}
