package org.brotli.dec;

/* loaded from: classes2.dex */
final class Huffman {
    static final int HUFFMAN_MAX_TABLE_SIZE = 1080;
    private static final int MAX_LENGTH = 15;

    Huffman() {
    }

    private static int getNextKey(int key, int len) {
        int step = 1 << (len - 1);
        while ((key & step) != 0) {
            step >>= 1;
        }
        return ((step - 1) & key) + step;
    }

    private static void replicateValue(int[] table, int offset, int step, int end, int item) {
        do {
            end -= step;
            table[offset + end] = item;
        } while (end > 0);
    }

    private static int nextTableBitSize(int[] count, int len, int rootBits) {
        int left;
        int left2 = 1 << (len - rootBits);
        while (len < 15 && (left = left2 - count[len]) > 0) {
            len++;
            left2 = left << 1;
        }
        return len - rootBits;
    }

    static void buildHuffmanTable(int[] rootTable, int tableOffset, int rootBits, int[] codeLengths, int codeLengthsSize) {
        int i;
        int[] sorted = new int[codeLengthsSize];
        int[] count = new int[16];
        int[] offset = new int[16];
        for (int symbol = 0; symbol < codeLengthsSize; symbol++) {
            int i2 = codeLengths[symbol];
            count[i2] = count[i2] + 1;
        }
        offset[1] = 0;
        int len = 1;
        while (true) {
            if (len >= 15) {
                break;
            }
            offset[len + 1] = offset[len] + count[len];
            len++;
        }
        for (int symbol2 = 0; symbol2 < codeLengthsSize; symbol2++) {
            if (codeLengths[symbol2] != 0) {
                int i3 = codeLengths[symbol2];
                int i4 = offset[i3];
                offset[i3] = i4 + 1;
                sorted[i4] = symbol2;
            }
        }
        int tableSize = 1 << rootBits;
        int totalSize = tableSize;
        if (offset[15] == 1) {
            for (int key = 0; key < totalSize; key++) {
                rootTable[tableOffset + key] = sorted[0];
            }
            return;
        }
        int key2 = 0;
        int symbol3 = 0;
        int len2 = 1;
        int step = 2;
        while (len2 <= rootBits) {
            while (count[len2] > 0) {
                replicateValue(rootTable, tableOffset + key2, step, tableSize, (len2 << 16) | sorted[symbol3]);
                key2 = getNextKey(key2, len2);
                count[len2] = count[len2] - 1;
                symbol3++;
            }
            len2++;
            step <<= 1;
        }
        int mask = totalSize - 1;
        int low = -1;
        int currentOffset = tableOffset;
        int len3 = rootBits + 1;
        int len4 = len3;
        int step2 = 2;
        for (i = 15; len4 <= i; i = 15) {
            while (count[len4] > 0) {
                if ((key2 & mask) != low) {
                    currentOffset += tableSize;
                    int tableBits = nextTableBitSize(count, len4, rootBits);
                    tableSize = 1 << tableBits;
                    totalSize += tableSize;
                    low = key2 & mask;
                    rootTable[tableOffset + low] = ((tableBits + rootBits) << 16) | ((currentOffset - tableOffset) - low);
                }
                replicateValue(rootTable, (key2 >> rootBits) + currentOffset, step2, tableSize, ((len4 - rootBits) << 16) | sorted[symbol3]);
                key2 = getNextKey(key2, len4);
                count[len4] = count[len4] - 1;
                symbol3++;
            }
            len4++;
            step2 <<= 1;
        }
    }
}
