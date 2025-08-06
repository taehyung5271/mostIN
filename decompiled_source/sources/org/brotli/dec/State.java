package org.brotli.dec;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
final class State {
    int bytesToWrite;
    int bytesWritten;
    int contextLookupOffset1;
    int contextLookupOffset2;
    byte[] contextMap;
    int contextMapSlice;
    byte[] contextModes;
    int copyDst;
    int copyLength;
    byte[] distContextMap;
    int distContextMapSlice;
    int distance;
    int distanceCode;
    int distancePostfixBits;
    int distancePostfixMask;
    boolean inputEnd;
    int insertLength;
    boolean isMetadata;
    boolean isUncompressed;
    int j;
    int literalTree;
    int maxBackwardDistance;
    int maxRingBufferSize;
    int metaBlockLength;
    int nextRunningState;
    int numDirectDistanceCodes;
    byte[] output;
    int outputLength;
    int outputOffset;
    int outputUsed;
    byte[] ringBuffer;
    int treeCommandOffset;
    int runningState = 0;
    final BitReader br = new BitReader();
    final int[] blockTypeTrees = new int[3240];
    final int[] blockLenTrees = new int[3240];
    final HuffmanTreeGroup hGroup0 = new HuffmanTreeGroup();
    final HuffmanTreeGroup hGroup1 = new HuffmanTreeGroup();
    final HuffmanTreeGroup hGroup2 = new HuffmanTreeGroup();
    final int[] blockLength = new int[3];
    final int[] numBlockTypes = new int[3];
    final int[] blockTypeRb = new int[6];
    final int[] distRb = {16, 15, 11, 4};
    int pos = 0;
    int maxDistance = 0;
    int distRbIdx = 0;
    boolean trivialLiteralContext = false;
    int literalTreeIndex = 0;
    int ringBufferSize = 0;
    long expectedTotalSize = 0;
    byte[] customDictionary = new byte[0];
    int bytesToIgnore = 0;

    State() {
    }

    private static int decodeWindowBits(BitReader br) {
        if (BitReader.readBits(br, 1) == 0) {
            return 16;
        }
        int n = BitReader.readBits(br, 3);
        if (n != 0) {
            return n + 17;
        }
        int n2 = BitReader.readBits(br, 3);
        if (n2 != 0) {
            return n2 + 8;
        }
        return 17;
    }

    static void setInput(State state, InputStream input) throws IOException {
        if (state.runningState != 0) {
            throw new IllegalStateException("State MUST be uninitialized");
        }
        BitReader.init(state.br, input);
        int windowBits = decodeWindowBits(state.br);
        if (windowBits == 9) {
            throw new BrotliRuntimeException("Invalid 'windowBits' code");
        }
        state.maxRingBufferSize = 1 << windowBits;
        state.maxBackwardDistance = state.maxRingBufferSize - 16;
        state.runningState = 1;
    }

    static void close(State state) throws IOException {
        if (state.runningState == 0) {
            throw new IllegalStateException("State MUST be initialized");
        }
        if (state.runningState == 11) {
            return;
        }
        state.runningState = 11;
        BitReader.close(state.br);
    }
}
