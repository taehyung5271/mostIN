package org.brotli.dec;

import kotlin.UByte;

/* loaded from: classes2.dex */
final class IntReader {
    private byte[] byteBuffer;
    private int[] intBuffer;

    IntReader() {
    }

    static void init(IntReader ir, byte[] byteBuffer, int[] intBuffer) {
        ir.byteBuffer = byteBuffer;
        ir.intBuffer = intBuffer;
    }

    static void convert(IntReader ir, int intLen) {
        for (int i = 0; i < intLen; i++) {
            ir.intBuffer[i] = (ir.byteBuffer[i * 4] & UByte.MAX_VALUE) | ((ir.byteBuffer[(i * 4) + 1] & UByte.MAX_VALUE) << 8) | ((ir.byteBuffer[(i * 4) + 2] & UByte.MAX_VALUE) << 16) | ((ir.byteBuffer[(i * 4) + 3] & UByte.MAX_VALUE) << 24);
        }
    }
}
