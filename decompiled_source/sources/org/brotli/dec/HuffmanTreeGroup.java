package org.brotli.dec;

/* loaded from: classes2.dex */
final class HuffmanTreeGroup {
    private int alphabetSize;
    int[] codes;
    int[] trees;

    HuffmanTreeGroup() {
    }

    static void init(HuffmanTreeGroup group, int alphabetSize, int n) {
        group.alphabetSize = alphabetSize;
        group.codes = new int[n * 1080];
        group.trees = new int[n];
    }

    static void decode(HuffmanTreeGroup group, BitReader br) {
        int next = 0;
        int n = group.trees.length;
        for (int i = 0; i < n; i++) {
            group.trees[i] = next;
            Decode.readHuffmanCode(group.alphabetSize, group.codes, next, br);
            next += 1080;
        }
    }
}
