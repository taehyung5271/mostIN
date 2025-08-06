package org.brotli.dec;

import java.io.IOException;
import kotlin.UByte;

/* loaded from: classes2.dex */
final class Decode {
    private static final int CODE_LENGTH_CODES = 18;
    private static final int CODE_LENGTH_REPEAT_CODE = 16;
    private static final int DEFAULT_CODE_LENGTH = 8;
    private static final int DISTANCE_CONTEXT_BITS = 2;
    private static final int HUFFMAN_TABLE_BITS = 8;
    private static final int HUFFMAN_TABLE_MASK = 255;
    private static final int LITERAL_CONTEXT_BITS = 6;
    private static final int NUM_BLOCK_LENGTH_CODES = 26;
    private static final int NUM_DISTANCE_SHORT_CODES = 16;
    private static final int NUM_INSERT_AND_COPY_CODES = 704;
    private static final int NUM_LITERAL_CODES = 256;
    private static final int[] CODE_LENGTH_CODE_ORDER = {1, 2, 3, 4, 0, 5, 17, 6, 16, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] DISTANCE_SHORT_CODE_INDEX_OFFSET = {3, 2, 1, 0, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2};
    private static final int[] DISTANCE_SHORT_CODE_VALUE_OFFSET = {0, 0, 0, 0, -1, 1, -2, 2, -3, 3, -1, 1, -2, 2, -3, 3};
    private static final int[] FIXED_TABLE = {131072, 131076, 131075, 196610, 131072, 131076, 131075, 262145, 131072, 131076, 131075, 196610, 131072, 131076, 131075, 262149};

    Decode() {
    }

    private static int decodeVarLenUnsignedByte(BitReader br) {
        if (BitReader.readBits(br, 1) != 0) {
            int n = BitReader.readBits(br, 3);
            if (n == 0) {
                return 1;
            }
            return BitReader.readBits(br, n) + (1 << n);
        }
        return 0;
    }

    private static void decodeMetaBlockLength(BitReader br, State state) {
        state.inputEnd = BitReader.readBits(br, 1) == 1;
        state.metaBlockLength = 0;
        state.isUncompressed = false;
        state.isMetadata = false;
        if (state.inputEnd && BitReader.readBits(br, 1) != 0) {
            return;
        }
        int sizeNibbles = BitReader.readBits(br, 2) + 4;
        if (sizeNibbles == 7) {
            state.isMetadata = true;
            if (BitReader.readBits(br, 1) != 0) {
                throw new BrotliRuntimeException("Corrupted reserved bit");
            }
            int sizeBytes = BitReader.readBits(br, 2);
            if (sizeBytes == 0) {
                return;
            }
            for (int i = 0; i < sizeBytes; i++) {
                int bits = BitReader.readBits(br, 8);
                if (bits == 0 && i + 1 == sizeBytes && sizeBytes > 1) {
                    throw new BrotliRuntimeException("Exuberant nibble");
                }
                state.metaBlockLength |= bits << (i * 8);
            }
        } else {
            for (int i2 = 0; i2 < sizeNibbles; i2++) {
                int bits2 = BitReader.readBits(br, 4);
                if (bits2 == 0 && i2 + 1 == sizeNibbles && sizeNibbles > 4) {
                    throw new BrotliRuntimeException("Exuberant nibble");
                }
                state.metaBlockLength |= bits2 << (i2 * 4);
            }
        }
        int i3 = state.metaBlockLength;
        state.metaBlockLength = i3 + 1;
        if (!state.inputEnd) {
            state.isUncompressed = BitReader.readBits(br, 1) == 1;
        }
    }

    private static int readSymbol(int[] table, int offset, BitReader br) {
        int val = (int) (br.accumulator >>> br.bitOffset);
        int offset2 = offset + (val & 255);
        int bits = table[offset2] >> 16;
        int sym = table[offset2] & 65535;
        if (bits <= 8) {
            br.bitOffset += bits;
            return sym;
        }
        int mask = (1 << bits) - 1;
        int offset3 = offset2 + sym + ((val & mask) >>> 8);
        br.bitOffset += (table[offset3] >> 16) + 8;
        return 65535 & table[offset3];
    }

    private static int readBlockLength(int[] table, int offset, BitReader br) {
        BitReader.fillBitWindow(br);
        int code = readSymbol(table, offset, br);
        int n = Prefix.BLOCK_LENGTH_N_BITS[code];
        return Prefix.BLOCK_LENGTH_OFFSET[code] + BitReader.readBits(br, n);
    }

    private static int translateShortCodes(int code, int[] ringBuffer, int index) {
        if (code < 16) {
            return ringBuffer[(index + DISTANCE_SHORT_CODE_INDEX_OFFSET[code]) & 3] + DISTANCE_SHORT_CODE_VALUE_OFFSET[code];
        }
        return (code - 16) + 1;
    }

    private static void moveToFront(int[] v, int index) {
        int value = v[index];
        while (index > 0) {
            v[index] = v[index - 1];
            index--;
        }
        v[0] = value;
    }

    private static void inverseMoveToFrontTransform(byte[] v, int vLen) {
        int[] mtf = new int[256];
        for (int i = 0; i < 256; i++) {
            mtf[i] = i;
        }
        for (int i2 = 0; i2 < vLen; i2++) {
            int index = v[i2] & UByte.MAX_VALUE;
            v[i2] = (byte) mtf[index];
            if (index != 0) {
                moveToFront(mtf, index);
            }
        }
    }

    private static void readHuffmanCodeLengths(int[] codeLengthCodeLengths, int numSymbols, int[] codeLengths, BitReader br) throws IOException {
        BitReader bitReader = br;
        int prevCodeLen = 0;
        int prevCodeLen2 = 8;
        int repeat = 0;
        int repeatCodeLen = 0;
        int space = 32768;
        int[] table = new int[32];
        Huffman.buildHuffmanTable(table, 0, 5, codeLengthCodeLengths, 18);
        while (prevCodeLen < numSymbols && space > 0) {
            BitReader.readMoreInput(br);
            BitReader.fillBitWindow(br);
            int p = ((int) (bitReader.accumulator >>> bitReader.bitOffset)) & 31;
            bitReader.bitOffset += table[p] >> 16;
            int codeLen = table[p] & 65535;
            if (codeLen < 16) {
                repeat = 0;
                int symbol = prevCodeLen + 1;
                codeLengths[prevCodeLen] = codeLen;
                if (codeLen == 0) {
                    prevCodeLen = symbol;
                } else {
                    space -= 32768 >> codeLen;
                    prevCodeLen2 = codeLen;
                    prevCodeLen = symbol;
                }
            } else {
                int symbol2 = codeLen - 14;
                int newLen = 0;
                if (codeLen == 16) {
                    newLen = prevCodeLen2;
                }
                if (repeatCodeLen != newLen) {
                    repeat = 0;
                    repeatCodeLen = newLen;
                }
                int oldRepeat = repeat;
                if (repeat > 0) {
                    repeat = (repeat - 2) << symbol2;
                }
                repeat += BitReader.readBits(bitReader, symbol2) + 3;
                int repeatDelta = repeat - oldRepeat;
                if (prevCodeLen + repeatDelta > numSymbols) {
                    throw new BrotliRuntimeException("symbol + repeatDelta > numSymbols");
                }
                int i = 0;
                while (i < repeatDelta) {
                    codeLengths[prevCodeLen] = repeatCodeLen;
                    i++;
                    prevCodeLen++;
                }
                if (repeatCodeLen != 0) {
                    space -= repeatDelta << (15 - repeatCodeLen);
                }
            }
            bitReader = br;
        }
        if (space != 0) {
            throw new BrotliRuntimeException("Unused space");
        }
        Utils.fillWithZeroes(codeLengths, prevCodeLen, numSymbols - prevCodeLen);
    }

    static void readHuffmanCode(int alphabetSize, int[] table, int offset, BitReader br) throws IOException {
        boolean ok = true;
        BitReader.readMoreInput(br);
        int[] codeLengths = new int[alphabetSize];
        int simpleCodeOrSkip = BitReader.readBits(br, 2);
        if (simpleCodeOrSkip == 1) {
            int maxBitsCounter = alphabetSize - 1;
            int maxBits = 0;
            int[] symbols = new int[4];
            int numSymbols = BitReader.readBits(br, 2) + 1;
            while (maxBitsCounter != 0) {
                maxBitsCounter >>= 1;
                maxBits++;
            }
            for (int i = 0; i < numSymbols; i++) {
                symbols[i] = BitReader.readBits(br, maxBits) % alphabetSize;
                codeLengths[symbols[i]] = 2;
            }
            int i2 = symbols[0];
            codeLengths[i2] = 1;
            switch (numSymbols) {
                case 1:
                    break;
                case 2:
                    ok = symbols[0] != symbols[1];
                    codeLengths[symbols[1]] = 1;
                    break;
                case 3:
                    if (symbols[0] != symbols[1] && symbols[0] != symbols[2] && symbols[1] != symbols[2]) {
                        z = true;
                    }
                    ok = z;
                    break;
                default:
                    ok = (symbols[0] == symbols[1] || symbols[0] == symbols[2] || symbols[0] == symbols[3] || symbols[1] == symbols[2] || symbols[1] == symbols[3] || symbols[2] == symbols[3]) ? false : true;
                    if (BitReader.readBits(br, 1) != 1) {
                        codeLengths[symbols[0]] = 2;
                        break;
                    } else {
                        codeLengths[symbols[2]] = 3;
                        codeLengths[symbols[3]] = 3;
                        break;
                    }
            }
        } else {
            int[] codeLengthCodeLengths = new int[18];
            int space = 32;
            int numCodes = 0;
            for (int i3 = simpleCodeOrSkip; i3 < 18 && space > 0; i3++) {
                int codeLenIdx = CODE_LENGTH_CODE_ORDER[i3];
                BitReader.fillBitWindow(br);
                int p = ((int) (br.accumulator >>> br.bitOffset)) & 15;
                br.bitOffset += FIXED_TABLE[p] >> 16;
                int v = FIXED_TABLE[p] & 65535;
                codeLengthCodeLengths[codeLenIdx] = v;
                if (v != 0) {
                    space -= 32 >> v;
                    numCodes++;
                }
            }
            ok = numCodes == 1 || space == 0;
            readHuffmanCodeLengths(codeLengthCodeLengths, alphabetSize, codeLengths, br);
        }
        if (!ok) {
            throw new BrotliRuntimeException("Can't readHuffmanCode");
        }
        Huffman.buildHuffmanTable(table, offset, 8, codeLengths, alphabetSize);
    }

    private static int decodeContextMap(int contextMapSize, byte[] contextMap, BitReader br) throws IOException {
        BitReader.readMoreInput(br);
        int numTrees = decodeVarLenUnsignedByte(br) + 1;
        if (numTrees == 1) {
            Utils.fillWithZeroes(contextMap, 0, contextMapSize);
            return numTrees;
        }
        boolean useRleForZeros = BitReader.readBits(br, 1) == 1;
        int maxRunLengthPrefix = 0;
        if (useRleForZeros) {
            maxRunLengthPrefix = BitReader.readBits(br, 4) + 1;
        }
        int[] table = new int[1080];
        readHuffmanCode(numTrees + maxRunLengthPrefix, table, 0, br);
        int i = 0;
        while (i < contextMapSize) {
            BitReader.readMoreInput(br);
            BitReader.fillBitWindow(br);
            int code = readSymbol(table, 0, br);
            if (code == 0) {
                contextMap[i] = 0;
                i++;
            } else if (code <= maxRunLengthPrefix) {
                for (int reps = (1 << code) + BitReader.readBits(br, code); reps != 0; reps--) {
                    if (i >= contextMapSize) {
                        throw new BrotliRuntimeException("Corrupted context map");
                    }
                    contextMap[i] = 0;
                    i++;
                }
            } else {
                contextMap[i] = (byte) (code - maxRunLengthPrefix);
                i++;
            }
        }
        if (BitReader.readBits(br, 1) == 1) {
            inverseMoveToFrontTransform(contextMap, contextMapSize);
        }
        return numTrees;
    }

    private static void decodeBlockTypeAndLength(State state, int treeType) {
        int blockType;
        BitReader br = state.br;
        int[] ringBuffers = state.blockTypeRb;
        int offset = treeType * 2;
        BitReader.fillBitWindow(br);
        int blockType2 = readSymbol(state.blockTypeTrees, treeType * 1080, br);
        state.blockLength[treeType] = readBlockLength(state.blockLenTrees, treeType * 1080, br);
        if (blockType2 == 1) {
            blockType = ringBuffers[offset + 1] + 1;
        } else if (blockType2 == 0) {
            blockType = ringBuffers[offset];
        } else {
            blockType = blockType2 - 2;
        }
        if (blockType >= state.numBlockTypes[treeType]) {
            blockType -= state.numBlockTypes[treeType];
        }
        ringBuffers[offset] = ringBuffers[offset + 1];
        ringBuffers[offset + 1] = blockType;
    }

    private static void decodeLiteralBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 0);
        int literalBlockType = state.blockTypeRb[1];
        state.contextMapSlice = literalBlockType << 6;
        state.literalTreeIndex = state.contextMap[state.contextMapSlice] & UByte.MAX_VALUE;
        state.literalTree = state.hGroup0.trees[state.literalTreeIndex];
        int contextMode = state.contextModes[literalBlockType];
        state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[contextMode];
        state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[contextMode + 1];
    }

    private static void decodeCommandBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 1);
        state.treeCommandOffset = state.hGroup1.trees[state.blockTypeRb[3]];
    }

    private static void decodeDistanceBlockSwitch(State state) {
        decodeBlockTypeAndLength(state, 2);
        state.distContextMapSlice = state.blockTypeRb[5] << 2;
    }

    private static void maybeReallocateRingBuffer(State state) {
        int newSize = state.maxRingBufferSize;
        if (newSize > state.expectedTotalSize) {
            int minimalNewSize = ((int) state.expectedTotalSize) + state.customDictionary.length;
            while ((newSize >> 1) > minimalNewSize) {
                newSize >>= 1;
            }
            if (!state.inputEnd && newSize < 16384 && state.maxRingBufferSize >= 16384) {
                newSize = 16384;
            }
        }
        int minimalNewSize2 = state.ringBufferSize;
        if (newSize <= minimalNewSize2) {
            return;
        }
        int ringBufferSizeWithSlack = newSize + 37;
        byte[] newBuffer = new byte[ringBufferSizeWithSlack];
        if (state.ringBuffer != null) {
            System.arraycopy(state.ringBuffer, 0, newBuffer, 0, state.ringBufferSize);
        } else if (state.customDictionary.length != 0) {
            int length = state.customDictionary.length;
            int offset = 0;
            if (length > state.maxBackwardDistance) {
                offset = length - state.maxBackwardDistance;
                length = state.maxBackwardDistance;
            }
            System.arraycopy(state.customDictionary, offset, newBuffer, 0, length);
            state.pos = length;
            state.bytesToIgnore = length;
        }
        state.ringBuffer = newBuffer;
        state.ringBufferSize = newSize;
    }

    private static void readMetablockInfo(State state) throws IOException {
        BitReader br = state.br;
        if (state.inputEnd) {
            state.nextRunningState = 10;
            state.bytesToWrite = state.pos;
            state.bytesWritten = 0;
            state.runningState = 12;
            return;
        }
        state.hGroup0.codes = null;
        state.hGroup0.trees = null;
        state.hGroup1.codes = null;
        state.hGroup1.trees = null;
        state.hGroup2.codes = null;
        state.hGroup2.trees = null;
        BitReader.readMoreInput(br);
        decodeMetaBlockLength(br, state);
        if (state.metaBlockLength == 0 && !state.isMetadata) {
            return;
        }
        if (state.isUncompressed || state.isMetadata) {
            BitReader.jumpToByteBoundary(br);
            state.runningState = state.isMetadata ? 4 : 5;
        } else {
            state.runningState = 2;
        }
        if (state.isMetadata) {
            return;
        }
        state.expectedTotalSize += state.metaBlockLength;
        if (state.ringBufferSize < state.maxRingBufferSize) {
            maybeReallocateRingBuffer(state);
        }
    }

    private static void readMetablockHuffmanCodesAndContextMaps(State state) throws IOException {
        BitReader br = state.br;
        for (int i = 0; i < 3; i++) {
            state.numBlockTypes[i] = decodeVarLenUnsignedByte(br) + 1;
            state.blockLength[i] = 268435456;
            if (state.numBlockTypes[i] > 1) {
                readHuffmanCode(state.numBlockTypes[i] + 2, state.blockTypeTrees, i * 1080, br);
                readHuffmanCode(26, state.blockLenTrees, i * 1080, br);
                state.blockLength[i] = readBlockLength(state.blockLenTrees, i * 1080, br);
            }
        }
        BitReader.readMoreInput(br);
        state.distancePostfixBits = BitReader.readBits(br, 2);
        state.numDirectDistanceCodes = (BitReader.readBits(br, 4) << state.distancePostfixBits) + 16;
        state.distancePostfixMask = (1 << state.distancePostfixBits) - 1;
        int numDistanceCodes = state.numDirectDistanceCodes + (48 << state.distancePostfixBits);
        state.contextModes = new byte[state.numBlockTypes[0]];
        int i2 = 0;
        while (i2 < state.numBlockTypes[0]) {
            int limit = Math.min(i2 + 96, state.numBlockTypes[0]);
            while (i2 < limit) {
                state.contextModes[i2] = (byte) (BitReader.readBits(br, 2) << 1);
                i2++;
            }
            BitReader.readMoreInput(br);
        }
        state.contextMap = new byte[state.numBlockTypes[0] << 6];
        int numLiteralTrees = decodeContextMap(state.numBlockTypes[0] << 6, state.contextMap, br);
        state.trivialLiteralContext = true;
        int j = 0;
        while (true) {
            if (j >= (state.numBlockTypes[0] << 6)) {
                break;
            }
            if (state.contextMap[j] == (j >> 6)) {
                j++;
            } else {
                state.trivialLiteralContext = false;
                break;
            }
        }
        state.distContextMap = new byte[state.numBlockTypes[2] << 2];
        int numDistTrees = decodeContextMap(state.numBlockTypes[2] << 2, state.distContextMap, br);
        HuffmanTreeGroup.init(state.hGroup0, 256, numLiteralTrees);
        HuffmanTreeGroup.init(state.hGroup1, 704, state.numBlockTypes[1]);
        HuffmanTreeGroup.init(state.hGroup2, numDistanceCodes, numDistTrees);
        HuffmanTreeGroup.decode(state.hGroup0, br);
        HuffmanTreeGroup.decode(state.hGroup1, br);
        HuffmanTreeGroup.decode(state.hGroup2, br);
        state.contextMapSlice = 0;
        state.distContextMapSlice = 0;
        state.contextLookupOffset1 = Context.LOOKUP_OFFSETS[state.contextModes[0]];
        state.contextLookupOffset2 = Context.LOOKUP_OFFSETS[state.contextModes[0] + 1];
        state.literalTreeIndex = 0;
        state.literalTree = state.hGroup0.trees[0];
        state.treeCommandOffset = state.hGroup1.trees[0];
        int[] iArr = state.blockTypeRb;
        int[] iArr2 = state.blockTypeRb;
        state.blockTypeRb[4] = 1;
        iArr2[2] = 1;
        iArr[0] = 1;
        int[] iArr3 = state.blockTypeRb;
        int[] iArr4 = state.blockTypeRb;
        state.blockTypeRb[5] = 0;
        iArr4[3] = 0;
        iArr3[1] = 0;
    }

    private static void copyUncompressedData(State state) throws IOException {
        BitReader br = state.br;
        byte[] ringBuffer = state.ringBuffer;
        if (state.metaBlockLength <= 0) {
            BitReader.reload(br);
            state.runningState = 1;
            return;
        }
        int chunkLength = Math.min(state.ringBufferSize - state.pos, state.metaBlockLength);
        BitReader.copyBytes(br, ringBuffer, state.pos, chunkLength);
        state.metaBlockLength -= chunkLength;
        state.pos += chunkLength;
        if (state.pos == state.ringBufferSize) {
            state.nextRunningState = 5;
            state.bytesToWrite = state.ringBufferSize;
            state.bytesWritten = 0;
            state.runningState = 12;
            return;
        }
        BitReader.reload(br);
        state.runningState = 1;
    }

    private static boolean writeRingBuffer(State state) {
        if (state.bytesToIgnore != 0) {
            state.bytesWritten += state.bytesToIgnore;
            state.bytesToIgnore = 0;
        }
        int toWrite = Math.min(state.outputLength - state.outputUsed, state.bytesToWrite - state.bytesWritten);
        if (toWrite != 0) {
            System.arraycopy(state.ringBuffer, state.bytesWritten, state.output, state.outputOffset + state.outputUsed, toWrite);
            state.outputUsed += toWrite;
            state.bytesWritten += toWrite;
        }
        return state.outputUsed < state.outputLength;
    }

    static void setCustomDictionary(State state, byte[] data) {
        state.customDictionary = data == null ? new byte[0] : data;
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x0342  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0112 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0232 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x010e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0371 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0015 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0015 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x036c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:193:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0176 A[LOOP:2: B:53:0x0176->B:189:?, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void decompress(org.brotli.dec.State r22) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 990
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.brotli.dec.Decode.decompress(org.brotli.dec.State):void");
    }
}
