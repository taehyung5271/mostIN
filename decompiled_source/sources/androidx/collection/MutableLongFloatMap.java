package androidx.collection;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LongFloatMap.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0016\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\u0006\u0010\b\u001a\u00020\u0007J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0002J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0002J\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u0086\bø\u0001\u0000J\b\u0010\u0012\u001a\u00020\u0007H\u0002J\u0010\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0003H\u0002J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0011\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0018H\u0086\nJ\u0011\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0019H\u0086\nJ\u0011\u0010\u0016\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0086\nJ\u0011\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u001aH\u0086\nJ\u0011\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0001H\u0086\nJ\u0016\u0010\u001d\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u000fJ\u001e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u000fJ\u000e\u0010 \u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0001J\u000e\u0010!\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010!\u001a\u00020\"2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u000fJ\b\u0010#\u001a\u00020\u0007H\u0002J&\u0010$\u001a\u00020\u00072\u0018\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\"0&H\u0086\bø\u0001\u0000J\u0010\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0003H\u0001J\u0010\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0003H\u0002J\u0019\u0010+\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u000fH\u0086\u0002J\u0006\u0010,\u001a\u00020\u0003J\u0019\u0010-\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\rH\u0082\bR\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006."}, d2 = {"Landroidx/collection/MutableLongFloatMap;", "Landroidx/collection/LongFloatMap;", "initialCapacity", "", "(I)V", "growthLimit", "adjustStorage", "", "clear", "findFirstAvailableSlot", "hash1", "findInsertIndex", "key", "", "getOrPut", "", "defaultValue", "Lkotlin/Function0;", "initializeGrowth", "initializeMetadata", "capacity", "initializeStorage", "minusAssign", "keys", "Landroidx/collection/LongList;", "Landroidx/collection/LongSet;", "", "plusAssign", TypedValues.TransitionType.S_FROM, "put", "value", "default", "putAll", "remove", "", "removeDeletedMarkers", "removeIf", "predicate", "Lkotlin/Function2;", "removeValueAt", "index", "resizeStorage", "newCapacity", "set", "trim", "writeMetadata", "collection"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class MutableLongFloatMap extends LongFloatMap {
    private int growthLimit;

    public MutableLongFloatMap() {
        this(0, 1, null);
    }

    public /* synthetic */ MutableLongFloatMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableLongFloatMap(int initialCapacity) {
        super(null);
        if (!(initialCapacity >= 0)) {
            throw new IllegalArgumentException("Capacity must be a positive value.".toString());
        }
        initializeStorage(ScatterMapKt.unloadedCapacity(initialCapacity));
    }

    private final void initializeStorage(int initialCapacity) {
        int newCapacity;
        if (initialCapacity > 0) {
            newCapacity = Math.max(7, ScatterMapKt.normalizeCapacity(initialCapacity));
        } else {
            newCapacity = 0;
        }
        this._capacity = newCapacity;
        initializeMetadata(newCapacity);
        this.keys = new long[newCapacity];
        this.values = new float[newCapacity];
    }

    private final void initializeMetadata(int capacity) {
        long[] jArr;
        if (capacity == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            int size = ((((capacity + 1) + 7) + 7) & (-8)) >> 3;
            long[] $this$initializeMetadata_u24lambda_u241 = new long[size];
            ArraysKt.fill$default($this$initializeMetadata_u24lambda_u241, -9187201950435737472L, 0, 0, 6, (Object) null);
            jArr = $this$initializeMetadata_u24lambda_u241;
        }
        this.metadata = jArr;
        long[] data$iv = this.metadata;
        int i$iv = capacity >> 3;
        int b$iv = (capacity & 7) << 3;
        data$iv[i$iv] = (data$iv[i$iv] & (~(255 << b$iv))) | (255 << b$iv);
        initializeGrowth();
    }

    private final void initializeGrowth() {
        this.growthLimit = ScatterMapKt.loadedCapacity(get_capacity()) - this._size;
    }

    public final float getOrPut(long key, Function0<Float> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        int index = findKeyIndex(key);
        if (index < 0) {
            float defValue = defaultValue.invoke().floatValue();
            put(key, defValue);
            return defValue;
        }
        return this.values[index];
    }

    public final void set(long key, float value) {
        int index = findInsertIndex(key);
        if (index < 0) {
            index = ~index;
        }
        this.keys[index] = key;
        this.values[index] = value;
    }

    public final void put(long key, float value) {
        set(key, value);
    }

    public final float put(long key, float value, float f) {
        int index = findInsertIndex(key);
        float previous = f;
        if (index < 0) {
            index = ~index;
        } else {
            previous = this.values[index];
        }
        this.keys[index] = key;
        this.values[index] = value;
        return previous;
    }

    public final void putAll(LongFloatMap from) {
        int $i$f$forEach;
        long[] k$iv;
        float[] v$iv;
        int $i$f$forEach2;
        long[] k$iv2;
        float[] v$iv2;
        Intrinsics.checkNotNullParameter(from, "from");
        LongFloatMap this_$iv = from;
        int $i$f$forEach3 = 0;
        long[] k$iv3 = this_$iv.keys;
        float[] v$iv3 = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 > lastIndex$iv$iv) {
            return;
        }
        while (true) {
            long slot$iv$iv = m$iv$iv[i$iv$iv];
            LongFloatMap this_$iv2 = this_$iv;
            if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                $i$f$forEach = $i$f$forEach3;
                k$iv = k$iv3;
                v$iv = v$iv3;
            } else {
                int i = 8;
                int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                int j$iv$iv = 0;
                while (j$iv$iv < bitCount$iv$iv) {
                    long value$iv$iv$iv = 255 & slot$iv$iv;
                    if (!(value$iv$iv$iv < 128)) {
                        $i$f$forEach2 = $i$f$forEach3;
                        k$iv2 = k$iv3;
                        v$iv2 = v$iv3;
                    } else {
                        int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                        $i$f$forEach2 = $i$f$forEach3;
                        long key = k$iv3[index$iv$iv];
                        k$iv2 = k$iv3;
                        float value = v$iv3[index$iv$iv];
                        v$iv2 = v$iv3;
                        set(key, value);
                    }
                    i = 8;
                    slot$iv$iv >>= 8;
                    j$iv$iv++;
                    $i$f$forEach3 = $i$f$forEach2;
                    k$iv3 = k$iv2;
                    v$iv3 = v$iv2;
                }
                $i$f$forEach = $i$f$forEach3;
                k$iv = k$iv3;
                v$iv = v$iv3;
                if (bitCount$iv$iv != i) {
                    return;
                }
            }
            if (i$iv$iv == lastIndex$iv$iv) {
                return;
            }
            i$iv$iv++;
            this_$iv = this_$iv2;
            $i$f$forEach3 = $i$f$forEach;
            k$iv3 = k$iv;
            v$iv3 = v$iv;
        }
    }

    public final void plusAssign(LongFloatMap from) {
        Intrinsics.checkNotNullParameter(from, "from");
        putAll(from);
    }

    public final void remove(long key) {
        int index = findKeyIndex(key);
        if (index >= 0) {
            removeValueAt(index);
        }
    }

    public final boolean remove(long key, float value) {
        int index = findKeyIndex(key);
        if (index >= 0) {
            if (this.values[index] == value) {
                removeValueAt(index);
                return true;
            }
        }
        return false;
    }

    public final void removeIf(Function2<? super Long, ? super Float, Boolean> predicate) {
        int $i$f$removeIf;
        int $i$f$removeIf2;
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int $i$f$removeIf3 = 0;
        MutableLongFloatMap this_$iv = this;
        long[] m$iv = this_$iv.metadata;
        int lastIndex$iv = m$iv.length - 2;
        int i$iv = 0;
        if (0 > lastIndex$iv) {
            return;
        }
        while (true) {
            long slot$iv = m$iv[i$iv];
            long $this$maskEmptyOrDeleted$iv$iv = ((~slot$iv) << 7) & slot$iv & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv$iv == -9187201950435737472L) {
                $i$f$removeIf = $i$f$removeIf3;
            } else {
                int i = 8;
                int bitCount$iv = 8 - ((~(i$iv - lastIndex$iv)) >>> 31);
                int j$iv = 0;
                while (j$iv < bitCount$iv) {
                    long value$iv$iv = 255 & slot$iv;
                    if (!(value$iv$iv < 128)) {
                        $i$f$removeIf2 = $i$f$removeIf3;
                    } else {
                        int index$iv = (i$iv << 3) + j$iv;
                        $i$f$removeIf2 = $i$f$removeIf3;
                        if (predicate.invoke(Long.valueOf(this.keys[index$iv]), Float.valueOf(this.values[index$iv])).booleanValue()) {
                            removeValueAt(index$iv);
                        }
                    }
                    slot$iv >>= 8;
                    j$iv++;
                    i = 8;
                    $i$f$removeIf3 = $i$f$removeIf2;
                }
                $i$f$removeIf = $i$f$removeIf3;
                int $i$f$removeIf4 = i;
                if (bitCount$iv != $i$f$removeIf4) {
                    return;
                }
            }
            if (i$iv == lastIndex$iv) {
                return;
            }
            i$iv++;
            $i$f$removeIf3 = $i$f$removeIf;
        }
    }

    public final void minusAssign(long key) {
        remove(key);
    }

    public final void minusAssign(long[] keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (long key : keys) {
            remove(key);
        }
    }

    public final void minusAssign(LongSet keys) {
        LongSet this_$iv;
        int $i$f$forEach;
        LongSet this_$iv2;
        int $i$f$forEach2;
        Intrinsics.checkNotNullParameter(keys, "keys");
        int $i$f$minusAssign = 0;
        LongSet this_$iv3 = keys;
        int $i$f$forEach3 = 0;
        long[] k$iv = this_$iv3.elements;
        long[] m$iv$iv = this_$iv3.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 > lastIndex$iv$iv) {
            return;
        }
        while (true) {
            long slot$iv$iv = m$iv$iv[i$iv$iv];
            int $i$f$minusAssign2 = $i$f$minusAssign;
            if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                this_$iv = this_$iv3;
                $i$f$forEach = $i$f$forEach3;
            } else {
                int i = 8;
                int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                int j$iv$iv = 0;
                while (j$iv$iv < bitCount$iv$iv) {
                    long value$iv$iv$iv = 255 & slot$iv$iv;
                    if (!(value$iv$iv$iv < 128)) {
                        this_$iv2 = this_$iv3;
                        $i$f$forEach2 = $i$f$forEach3;
                    } else {
                        int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                        this_$iv2 = this_$iv3;
                        long key = k$iv[index$iv$iv];
                        $i$f$forEach2 = $i$f$forEach3;
                        remove(key);
                    }
                    i = 8;
                    slot$iv$iv >>= 8;
                    j$iv$iv++;
                    this_$iv3 = this_$iv2;
                    $i$f$forEach3 = $i$f$forEach2;
                }
                this_$iv = this_$iv3;
                $i$f$forEach = $i$f$forEach3;
                if (bitCount$iv$iv != i) {
                    return;
                }
            }
            if (i$iv$iv == lastIndex$iv$iv) {
                return;
            }
            i$iv$iv++;
            $i$f$minusAssign = $i$f$minusAssign2;
            this_$iv3 = this_$iv;
            $i$f$forEach3 = $i$f$forEach;
        }
    }

    public final void minusAssign(LongList keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        long[] content$iv = keys.content;
        int i = keys._size;
        for (int i$iv = 0; i$iv < i; i$iv++) {
            long key = content$iv[i$iv];
            remove(key);
        }
    }

    public final void removeValueAt(int index) {
        this._size--;
        long[] m$iv = this.metadata;
        int i$iv$iv = index >> 3;
        int b$iv$iv = (index & 7) << 3;
        m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (254 << b$iv$iv);
        int $i$f$writeRawMetadata = this._capacity;
        int cloneIndex$iv = ((index - 7) & $i$f$writeRawMetadata) + ($i$f$writeRawMetadata & 7);
        int i$iv$iv2 = cloneIndex$iv >> 3;
        int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
        m$iv[i$iv$iv2] = ((~(255 << b$iv$iv2)) & m$iv[i$iv$iv2]) | (254 << b$iv$iv2);
    }

    public final void clear() {
        this._size = 0;
        if (this.metadata != ScatterMapKt.EmptyGroup) {
            ArraysKt.fill$default(this.metadata, -9187201950435737472L, 0, 0, 6, (Object) null);
            long[] data$iv = this.metadata;
            int offset$iv = this._capacity;
            int i$iv = offset$iv >> 3;
            int b$iv = (offset$iv & 7) << 3;
            data$iv[i$iv] = (data$iv[i$iv] & (~(255 << b$iv))) | (255 << b$iv);
        }
        initializeGrowth();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x008c, code lost:
    
        r11 = (((~r7) << 6) & r7) & (-9187201950435737472L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x009d, code lost:
    
        if (r11 == 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00a0, code lost:
    
        r7 = r0.findFirstAvailableSlot(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a8, code lost:
    
        if (r0.growthLimit != 0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00bd, code lost:
    
        if (((r0.metadata[r7 >> 3] >> ((r7 & 7) << 3)) & 255) != 254) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00bf, code lost:
    
        r8 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c2, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00c4, code lost:
    
        if (r8 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00c6, code lost:
    
        adjustStorage();
        r7 = r0.findFirstAvailableSlot(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00cd, code lost:
    
        r0._size++;
        r8 = r0.growthLimit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e9, code lost:
    
        if (((r0.metadata[r7 >> 3] >> ((r7 & 7) << 3)) & 255) != 128) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00eb, code lost:
    
        r19 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ed, code lost:
    
        r0.growthLimit = r8 - r19;
        r11 = r3;
        r14 = r23.metadata;
        r16 = r7 >> 3;
        r17 = (r7 & 7) << 3;
        r14[r16] = (r14[r16] & (~(255 << r17))) | (r11 << r17);
        r0 = r23._capacity;
        r1 = ((r7 - 7) & r0) + (r0 & 7);
        r16 = r1 >> 3;
        r17 = (r1 & 7) << 3;
        r14[r16] = (r14[r16] & (~(255 << r17))) | (r11 << r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x012d, code lost:
    
        return ~r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int findInsertIndex(long r24) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableLongFloatMap.findInsertIndex(long):int");
    }

    private final int findFirstAvailableSlot(int hash1) {
        int probeMask = this._capacity;
        int probeOffset = hash1 & probeMask;
        int probeIndex = 0;
        while (true) {
            long[] metadata$iv = this.metadata;
            int i$iv = probeOffset >> 3;
            int b$iv = (probeOffset & 7) << 3;
            long g = (metadata$iv[i$iv] >>> b$iv) | ((metadata$iv[i$iv + 1] << (64 - b$iv)) & ((-b$iv) >> 63));
            long $this$maskEmptyOrDeleted$iv = ((~g) << 7) & g & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv != 0) {
                return ((Long.numberOfTrailingZeros($this$maskEmptyOrDeleted$iv) >> 3) + probeOffset) & probeMask;
            }
            probeIndex += 8;
            probeOffset = (probeOffset + probeIndex) & probeMask;
        }
    }

    public final int trim() {
        int previousCapacity = this._capacity;
        int newCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(this._size));
        if (newCapacity < previousCapacity) {
            resizeStorage(newCapacity);
            return previousCapacity - this._capacity;
        }
        return 0;
    }

    private final void adjustStorage() {
        if (this._capacity > 8 && Long.compareUnsigned(ULong.m401constructorimpl(ULong.m401constructorimpl(this._size) * 32), ULong.m401constructorimpl(ULong.m401constructorimpl(this._capacity) * 25)) <= 0) {
            removeDeletedMarkers();
        } else {
            resizeStorage(ScatterMapKt.nextCapacity(this._capacity));
        }
    }

    private final void resizeStorage(int newCapacity) {
        long[] previousMetadata;
        MutableLongFloatMap mutableLongFloatMap = this;
        long[] previousMetadata2 = mutableLongFloatMap.metadata;
        long[] previousKeys = mutableLongFloatMap.keys;
        float[] previousValues = mutableLongFloatMap.values;
        int previousCapacity = mutableLongFloatMap._capacity;
        initializeStorage(newCapacity);
        long[] newKeys = mutableLongFloatMap.keys;
        float[] newValues = mutableLongFloatMap.values;
        int i = 0;
        while (i < previousCapacity) {
            if (!(((previousMetadata2[i >> 3] >> ((i & 7) << 3)) & 255) < 128)) {
                previousMetadata = previousMetadata2;
            } else {
                long previousKey = previousKeys[i];
                int hash$iv = Long.hashCode(previousKey) * ScatterMapKt.MurmurHashC1;
                int $i$f$hash = hash$iv ^ (hash$iv << 16);
                int $i$f$h1 = $i$f$hash >>> 7;
                int index = mutableLongFloatMap.findFirstAvailableSlot($i$f$h1);
                int $i$f$h2 = $i$f$hash & 127;
                long value$iv = $i$f$h2;
                long[] m$iv = this.metadata;
                int i$iv$iv = index >> 3;
                int b$iv$iv = (index & 7) << 3;
                previousMetadata = previousMetadata2;
                m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (value$iv << b$iv$iv);
                int c$iv = this._capacity;
                int cloneIndex$iv = ((index - 7) & c$iv) + (c$iv & 7);
                int i$iv$iv2 = cloneIndex$iv >> 3;
                int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
                m$iv[i$iv$iv2] = (m$iv[i$iv$iv2] & (~(255 << b$iv$iv2))) | (value$iv << b$iv$iv2);
                newKeys[index] = previousKey;
                newValues[index] = previousValues[i];
            }
            i++;
            mutableLongFloatMap = this;
            previousMetadata2 = previousMetadata;
        }
    }

    private final void removeDeletedMarkers() {
        long[] m;
        int capacity;
        long[] m2 = this.metadata;
        int capacity2 = this._capacity;
        int removedDeletes = 0;
        int i = 0;
        while (i < capacity2) {
            long slot = (m2[i >> 3] >> ((i & 7) << 3)) & 255;
            if (slot != 254) {
                m = m2;
                capacity = capacity2;
            } else {
                long[] m$iv = this.metadata;
                int i$iv$iv = i >> 3;
                int b$iv$iv = (i & 7) << 3;
                m = m2;
                capacity = capacity2;
                m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (128 << b$iv$iv);
                int c$iv = this._capacity;
                int cloneIndex$iv = ((i - 7) & c$iv) + (c$iv & 7);
                int i$iv$iv2 = cloneIndex$iv >> 3;
                int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
                m$iv[i$iv$iv2] = (m$iv[i$iv$iv2] & (~(255 << b$iv$iv2))) | (128 << b$iv$iv2);
                removedDeletes++;
            }
            i++;
            m2 = m;
            capacity2 = capacity;
        }
        this.growthLimit += removedDeletes;
    }

    private final void writeMetadata(int index, long value) {
        long[] m = this.metadata;
        int i$iv = index >> 3;
        int b$iv = (index & 7) << 3;
        m[i$iv] = (m[i$iv] & (~(255 << b$iv))) | (value << b$iv);
        int $i$f$writeRawMetadata = this._capacity;
        int cloneIndex = ((index - 7) & $i$f$writeRawMetadata) + ($i$f$writeRawMetadata & 7);
        int i$iv2 = cloneIndex >> 3;
        int b$iv2 = (cloneIndex & 7) << 3;
        m[i$iv2] = ((~(255 << b$iv2)) & m[i$iv2]) | (value << b$iv2);
    }
}
