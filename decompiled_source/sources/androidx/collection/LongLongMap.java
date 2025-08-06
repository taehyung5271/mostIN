package androidx.collection;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LongLongMap.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0016\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J&\u0010\u0014\u001a\u00020\u00152\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u0017H\u0086\bø\u0001\u0000J\u0006\u0010\u0019\u001a\u00020\u0015J&\u0010\u0019\u001a\u00020\u00152\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u0017H\u0086\bø\u0001\u0000J\u0011\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0018H\u0086\u0002J\u000e\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0018J\u000e\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0018J\u0006\u0010\u001f\u001a\u00020\u0004J&\u0010\u001f\u001a\u00020\u00042\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u0017H\u0086\bø\u0001\u0000J\u0013\u0010 \u001a\u00020\u00152\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0018H\u0001JD\u0010#\u001a\u00020$26\u0010%\u001a2\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u00020$0\u0017H\u0086\bø\u0001\u0000J/\u0010(\u001a\u00020$2!\u0010%\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(*\u0012\u0004\u0012\u00020$0)H\u0081\bø\u0001\u0000J/\u0010+\u001a\u00020$2!\u0010%\u001a\u001d\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020$0)H\u0086\bø\u0001\u0000J/\u0010,\u001a\u00020$2!\u0010%\u001a\u001d\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u00020$0)H\u0086\bø\u0001\u0000J\u0011\u0010-\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018H\u0086\u0002J\u0016\u0010.\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u0018J\"\u00100\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00182\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001801H\u0086\bø\u0001\u0000J\b\u00102\u001a\u00020\u0004H\u0016J\u0006\u00103\u001a\u00020\u0015J\u0006\u00104\u001a\u00020\u0015J:\u00105\u001a\u0002062\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u0002082\b\b\u0002\u0010:\u001a\u0002082\b\b\u0002\u0010;\u001a\u00020\u00042\b\b\u0002\u0010<\u001a\u000208H\u0007Jx\u00105\u001a\u0002062\b\b\u0002\u00107\u001a\u0002082\b\b\u0002\u00109\u001a\u0002082\b\b\u0002\u0010:\u001a\u0002082\b\b\u0002\u0010;\u001a\u00020\u00042\b\b\u0002\u0010<\u001a\u00020828\b\u0004\u0010=\u001a2\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u0002080\u0017H\u0087\bø\u0001\u0000J\u0006\u0010>\u001a\u00020\u0015J\b\u0010?\u001a\u000206H\u0016R\u0018\u0010\u0003\u001a\u00020\u00048\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u0018\u0010\u0006\u001a\u00020\u00048\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0002R\u0011\u0010\b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\f8\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u0002R\u0018\u0010\u000e\u001a\u00020\f8\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u000f\u0010\u0002R\u0011\u0010\u0010\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\nR\u0018\u0010\u0012\u001a\u00020\f8\u0000@\u0000X\u0081\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0013\u0010\u0002\u0082\u0001\u0001@\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006A"}, d2 = {"Landroidx/collection/LongLongMap;", "", "()V", "_capacity", "", "get_capacity$collection$annotations", "_size", "get_size$collection$annotations", "capacity", "getCapacity", "()I", "keys", "", "getKeys$annotations", "metadata", "getMetadata$annotations", "size", "getSize", "values", "getValues$annotations", "all", "", "predicate", "Lkotlin/Function2;", "", "any", "contains", "key", "containsKey", "containsValue", "value", "count", "equals", "other", "findKeyIndex", "forEach", "", "block", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function1;", "index", "forEachKey", "forEachValue", "get", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "hashCode", "isEmpty", "isNotEmpty", "joinToString", "", "separator", "", "prefix", "postfix", "limit", "truncated", "transform", "none", "toString", "Landroidx/collection/MutableLongLongMap;", "collection"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public abstract class LongLongMap {
    public int _capacity;
    public int _size;
    public long[] keys;
    public long[] metadata;
    public long[] values;

    public /* synthetic */ LongLongMap(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static /* synthetic */ void getKeys$annotations() {
    }

    public static /* synthetic */ void getMetadata$annotations() {
    }

    public static /* synthetic */ void getValues$annotations() {
    }

    public static /* synthetic */ void get_capacity$collection$annotations() {
    }

    public static /* synthetic */ void get_size$collection$annotations() {
    }

    public final String joinToString() {
        return joinToString$default(this, null, null, null, 0, null, 31, null);
    }

    public final String joinToString(CharSequence separator) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        return joinToString$default(this, separator, null, null, 0, null, 30, null);
    }

    public final String joinToString(CharSequence separator, CharSequence prefix) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return joinToString$default(this, separator, prefix, null, 0, null, 28, null);
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        return joinToString$default(this, separator, prefix, postfix, 0, null, 24, null);
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix, int i) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        return joinToString$default(this, separator, prefix, postfix, i, null, 16, null);
    }

    private LongLongMap() {
        this.metadata = ScatterMapKt.EmptyGroup;
        this.keys = LongSetKt.getEmptyLongArray();
        this.values = LongSetKt.getEmptyLongArray();
    }

    /* renamed from: getCapacity, reason: from getter */
    public final int get_capacity() {
        return this._capacity;
    }

    /* renamed from: getSize, reason: from getter */
    public final int get_size() {
        return this._size;
    }

    public final boolean any() {
        return this._size != 0;
    }

    public final boolean none() {
        return this._size == 0;
    }

    public final boolean isEmpty() {
        return this._size == 0;
    }

    public final boolean isNotEmpty() {
        return this._size != 0;
    }

    public final long get(long key) {
        int index = findKeyIndex(key);
        if (index < 0) {
            throw new NoSuchElementException("Cannot find value for key " + key);
        }
        return this.values[index];
    }

    public final long getOrDefault(long key, long defaultValue) {
        int index = findKeyIndex(key);
        if (index >= 0) {
            return this.values[index];
        }
        return defaultValue;
    }

    public final long getOrElse(long key, Function0<Long> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        int index = findKeyIndex(key);
        if (index < 0) {
            return defaultValue.invoke().longValue();
        }
        return this.values[index];
    }

    public final void forEachIndexed(Function1<? super Integer, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long[] m = this.metadata;
        int lastIndex = m.length - 2;
        int i = 0;
        if (0 > lastIndex) {
            return;
        }
        while (true) {
            long slot = m[i];
            long $this$maskEmptyOrDeleted$iv = ((~slot) << 7) & slot & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv != -9187201950435737472L) {
                int bitCount = 8 - ((~(i - lastIndex)) >>> 31);
                for (int j = 0; j < bitCount; j++) {
                    long value$iv = 255 & slot;
                    if (value$iv < 128) {
                        int index = (i << 3) + j;
                        block.invoke(Integer.valueOf(index));
                    }
                    slot >>= 8;
                }
                if (bitCount != 8) {
                    return;
                }
            }
            if (i == lastIndex) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void forEach(Function2<? super Long, ? super Long, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long[] k = this.keys;
        long[] v = this.values;
        LongLongMap this_$iv = this;
        int $i$f$forEachIndexed = 0;
        long[] m$iv = this_$iv.metadata;
        int lastIndex$iv = m$iv.length - 2;
        int i$iv = 0;
        if (0 > lastIndex$iv) {
            return;
        }
        while (true) {
            long slot$iv = m$iv[i$iv];
            LongLongMap this_$iv2 = this_$iv;
            int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            if ((((~slot$iv) << 7) & slot$iv & (-9187201950435737472L)) != -9187201950435737472L) {
                int i = 8;
                int bitCount$iv = 8 - ((~(i$iv - lastIndex$iv)) >>> 31);
                int j$iv = 0;
                while (j$iv < bitCount$iv) {
                    long value$iv$iv = 255 & slot$iv;
                    if (value$iv$iv < 128) {
                        int index$iv = (i$iv << 3) + j$iv;
                        block.invoke(Long.valueOf(k[index$iv]), Long.valueOf(v[index$iv]));
                    }
                    slot$iv >>= 8;
                    j$iv++;
                    i = 8;
                }
                if (bitCount$iv != i) {
                    return;
                }
            }
            if (i$iv == lastIndex$iv) {
                return;
            }
            i$iv++;
            this_$iv = this_$iv2;
            $i$f$forEachIndexed = $i$f$forEachIndexed2;
        }
    }

    public final void forEachKey(Function1<? super Long, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long[] k = this.keys;
        long[] m$iv = this.metadata;
        int lastIndex$iv = m$iv.length - 2;
        int i$iv = 0;
        if (0 > lastIndex$iv) {
            return;
        }
        while (true) {
            long slot$iv = m$iv[i$iv];
            long $this$maskEmptyOrDeleted$iv$iv = ((~slot$iv) << 7) & slot$iv & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv$iv != -9187201950435737472L) {
                int i = 8;
                int bitCount$iv = 8 - ((~(i$iv - lastIndex$iv)) >>> 31);
                for (int j$iv = 0; j$iv < bitCount$iv; j$iv++) {
                    long value$iv$iv = 255 & slot$iv;
                    if (value$iv$iv < 128) {
                        int index$iv = (i$iv << 3) + j$iv;
                        block.invoke(Long.valueOf(k[index$iv]));
                    }
                    i = 8;
                    slot$iv >>= 8;
                }
                if (bitCount$iv != i) {
                    return;
                }
            }
            if (i$iv == lastIndex$iv) {
                return;
            } else {
                i$iv++;
            }
        }
    }

    public final void forEachValue(Function1<? super Long, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long[] v = this.values;
        long[] m$iv = this.metadata;
        int lastIndex$iv = m$iv.length - 2;
        int i$iv = 0;
        if (0 > lastIndex$iv) {
            return;
        }
        while (true) {
            long slot$iv = m$iv[i$iv];
            long $this$maskEmptyOrDeleted$iv$iv = ((~slot$iv) << 7) & slot$iv & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv$iv != -9187201950435737472L) {
                int i = 8;
                int bitCount$iv = 8 - ((~(i$iv - lastIndex$iv)) >>> 31);
                for (int j$iv = 0; j$iv < bitCount$iv; j$iv++) {
                    long value$iv$iv = 255 & slot$iv;
                    if (value$iv$iv < 128) {
                        int index$iv = (i$iv << 3) + j$iv;
                        block.invoke(Long.valueOf(v[index$iv]));
                    }
                    i = 8;
                    slot$iv >>= 8;
                }
                if (bitCount$iv != i) {
                    return;
                }
            }
            if (i$iv == lastIndex$iv) {
                return;
            } else {
                i$iv++;
            }
        }
    }

    public final boolean all(Function2<? super Long, ? super Long, Boolean> predicate) {
        int $i$f$all;
        int $i$f$all2;
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int $i$f$all3 = 0;
        long[] k$iv = this.keys;
        long[] v$iv = this.values;
        long[] m$iv$iv = this.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 > lastIndex$iv$iv) {
            return true;
        }
        while (true) {
            long slot$iv$iv = m$iv$iv[i$iv$iv];
            long slot$iv$iv2 = slot$iv$iv;
            if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                $i$f$all = $i$f$all3;
            } else {
                int i = 8;
                int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                int j$iv$iv = 0;
                while (j$iv$iv < bitCount$iv$iv) {
                    long value$iv$iv$iv = slot$iv$iv2 & 255;
                    if (!(value$iv$iv$iv < 128)) {
                        $i$f$all2 = $i$f$all3;
                    } else {
                        int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                        long key = k$iv[index$iv$iv];
                        long value = v$iv[index$iv$iv];
                        $i$f$all2 = $i$f$all3;
                        if (!predicate.invoke(Long.valueOf(key), Long.valueOf(value)).booleanValue()) {
                            return false;
                        }
                    }
                    slot$iv$iv2 >>= 8;
                    j$iv$iv++;
                    i = 8;
                    $i$f$all3 = $i$f$all2;
                }
                $i$f$all = $i$f$all3;
                int $i$f$all4 = i;
                if (bitCount$iv$iv != $i$f$all4) {
                    return true;
                }
            }
            if (i$iv$iv == lastIndex$iv$iv) {
                return true;
            }
            i$iv$iv++;
            $i$f$all3 = $i$f$all;
        }
    }

    public final boolean any(Function2<? super Long, ? super Long, Boolean> predicate) {
        int $i$f$any;
        int $i$f$any2;
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int $i$f$any3 = 0;
        long[] k$iv = this.keys;
        long[] v$iv = this.values;
        long[] m$iv$iv = this.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 > lastIndex$iv$iv) {
            return false;
        }
        while (true) {
            long slot$iv$iv = m$iv$iv[i$iv$iv];
            long slot$iv$iv2 = slot$iv$iv;
            if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                $i$f$any = $i$f$any3;
            } else {
                int i = 8;
                int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                int j$iv$iv = 0;
                while (j$iv$iv < bitCount$iv$iv) {
                    long value$iv$iv$iv = slot$iv$iv2 & 255;
                    if (!(value$iv$iv$iv < 128)) {
                        $i$f$any2 = $i$f$any3;
                    } else {
                        int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                        long key = k$iv[index$iv$iv];
                        long value = v$iv[index$iv$iv];
                        $i$f$any2 = $i$f$any3;
                        if (predicate.invoke(Long.valueOf(key), Long.valueOf(value)).booleanValue()) {
                            return true;
                        }
                    }
                    slot$iv$iv2 >>= 8;
                    j$iv$iv++;
                    i = 8;
                    $i$f$any3 = $i$f$any2;
                }
                $i$f$any = $i$f$any3;
                int $i$f$any4 = i;
                if (bitCount$iv$iv != $i$f$any4) {
                    return false;
                }
            }
            if (i$iv$iv == lastIndex$iv$iv) {
                return false;
            }
            i$iv$iv++;
            $i$f$any3 = $i$f$any;
        }
    }

    public final int count() {
        return get_size();
    }

    public final int count(Function2<? super Long, ? super Long, Boolean> predicate) {
        LongLongMap this_$iv;
        LongLongMap this_$iv2;
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int $i$f$count = 0;
        int count = 0;
        LongLongMap this_$iv3 = this;
        long[] k$iv = this_$iv3.keys;
        long[] v$iv = this_$iv3.values;
        long[] m$iv$iv = this_$iv3.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                int $i$f$count2 = $i$f$count;
                int count2 = count;
                if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                    this_$iv = this_$iv3;
                    count = count2;
                } else {
                    int i = 8;
                    int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    while (j$iv$iv < bitCount$iv$iv) {
                        long value$iv$iv$iv = 255 & slot$iv$iv;
                        if (!(value$iv$iv$iv < 128)) {
                            this_$iv2 = this_$iv3;
                        } else {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            long key = k$iv[index$iv$iv];
                            long value = v$iv[index$iv$iv];
                            this_$iv2 = this_$iv3;
                            if (predicate.invoke(Long.valueOf(key), Long.valueOf(value)).booleanValue()) {
                                count2++;
                            }
                        }
                        i = 8;
                        slot$iv$iv >>= 8;
                        j$iv$iv++;
                        this_$iv3 = this_$iv2;
                    }
                    this_$iv = this_$iv3;
                    if (bitCount$iv$iv != i) {
                        return count2;
                    }
                    count = count2;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                $i$f$count = $i$f$count2;
                this_$iv3 = this_$iv;
            }
        }
        return count;
    }

    public final boolean contains(long key) {
        return findKeyIndex(key) >= 0;
    }

    public final boolean containsKey(long key) {
        return findKeyIndex(key) >= 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean containsValue(long r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = 0
            long[] r2 = r0.values
            r3 = r0
            r4 = 0
            long[] r5 = r3.metadata
            int r6 = r5.length
            int r6 = r6 + (-2)
            r7 = 0
            r8 = 0
            if (r7 > r6) goto L62
        L10:
            r9 = r5[r7]
            r11 = r9
            r13 = 0
            long r14 = ~r11
            r16 = 7
            long r14 = r14 << r16
            long r14 = r14 & r11
            r16 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r14 & r16
            int r11 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r11 == 0) goto L5d
            int r11 = r7 - r6
            int r11 = ~r11
            int r11 = r11 >>> 31
            r12 = 8
            int r11 = 8 - r11
            r13 = 0
        L2f:
            if (r13 >= r11) goto L5b
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r9
            r16 = 0
            r17 = 128(0x80, double:6.3E-322)
            int r17 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            r18 = 1
            if (r17 >= 0) goto L41
            r14 = r18
            goto L42
        L41:
            r14 = r8
        L42:
            if (r14 == 0) goto L57
            int r14 = r7 << 3
            int r14 = r14 + r13
            r15 = r14
            r16 = 0
            r19 = r2[r15]
            r17 = 0
            int r21 = (r23 > r19 ? 1 : (r23 == r19 ? 0 : -1))
            if (r21 != 0) goto L53
            return r18
        L53:
        L57:
            long r9 = r9 >> r12
            int r13 = r13 + 1
            goto L2f
        L5b:
            if (r11 != r12) goto L63
        L5d:
            if (r7 == r6) goto L62
            int r7 = r7 + 1
            goto L10
        L62:
        L63:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LongLongMap.containsValue(long):boolean");
    }

    public static /* synthetic */ String joinToString$default(LongLongMap longLongMap, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: joinToString");
        }
        if ((i2 & 1) != 0) {
        }
        return longLongMap.joinToString(charSequence, (i2 & 2) != 0 ? "" : charSequence2, (i2 & 4) != 0 ? "" : charSequence3, (i2 & 8) != 0 ? -1 : i, (i2 & 16) != 0 ? "..." : charSequence4);
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, CharSequence truncated) {
        long[] k$iv;
        long[] v$iv;
        LongLongMap this_$iv$iv;
        int $i$f$forEachIndexed;
        long[] k$iv2;
        long[] v$iv2;
        LongLongMap this_$iv$iv2;
        int $i$f$forEachIndexed2;
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        Intrinsics.checkNotNullParameter(truncated, "truncated");
        StringBuilder $this$joinToString_u24lambda_u248 = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u248.append(prefix);
        int index = 0;
        LongLongMap this_$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv3 = this_$iv.keys;
        long[] v$iv3 = this_$iv.values;
        LongLongMap this_$iv$iv3 = this_$iv;
        int $i$f$forEachIndexed3 = 0;
        long[] m$iv$iv = this_$iv$iv3.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                int i2 = i;
                int index2 = index;
                LongLongMap this_$iv2 = this_$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv = ((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv == -9187201950435737472L) {
                    k$iv = k$iv3;
                    v$iv = v$iv3;
                    this_$iv$iv = this_$iv$iv3;
                    $i$f$forEachIndexed = $i$f$forEachIndexed3;
                    index = index2;
                } else {
                    int i3 = 8;
                    int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    int index3 = index2;
                    while (j$iv$iv < bitCount$iv$iv) {
                        long value$iv$iv$iv = slot$iv$iv & 255;
                        if (!(value$iv$iv$iv < 128)) {
                            k$iv2 = k$iv3;
                            v$iv2 = v$iv3;
                            this_$iv$iv2 = this_$iv$iv3;
                            $i$f$forEachIndexed2 = $i$f$forEachIndexed3;
                        } else {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            int index4 = index3;
                            long key = k$iv3[index$iv$iv];
                            this_$iv$iv2 = this_$iv$iv3;
                            $i$f$forEachIndexed2 = $i$f$forEachIndexed3;
                            long value = v$iv3[index$iv$iv];
                            k$iv2 = k$iv3;
                            v$iv2 = v$iv3;
                            if (index4 == limit) {
                                $this$joinToString_u24lambda_u248.append(truncated);
                                break loop0;
                            }
                            if (index4 != 0) {
                                $this$joinToString_u24lambda_u248.append(separator2);
                            }
                            $this$joinToString_u24lambda_u248.append(key);
                            $this$joinToString_u24lambda_u248.append('=');
                            $this$joinToString_u24lambda_u248.append(value);
                            index3 = index4 + 1;
                        }
                        slot$iv$iv >>= 8;
                        j$iv$iv++;
                        i3 = 8;
                        v$iv3 = v$iv2;
                        this_$iv$iv3 = this_$iv$iv2;
                        $i$f$forEachIndexed3 = $i$f$forEachIndexed2;
                        k$iv3 = k$iv2;
                        separator2 = separator;
                    }
                    k$iv = k$iv3;
                    v$iv = v$iv3;
                    this_$iv$iv = this_$iv$iv3;
                    $i$f$forEachIndexed = $i$f$forEachIndexed3;
                    int index5 = index3;
                    if (bitCount$iv$iv != i3) {
                        break;
                    }
                    index = index5;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                separator2 = separator;
                this_$iv = this_$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
                v$iv3 = v$iv;
                this_$iv$iv3 = this_$iv$iv;
                $i$f$forEachIndexed3 = $i$f$forEachIndexed;
                k$iv3 = k$iv;
            }
        }
        $this$joinToString_u24lambda_u248.append(postfix);
        String string = $this$joinToString_u24lambda_u248.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public static /* synthetic */ String joinToString$default(LongLongMap $this, CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, CharSequence truncated, Function2 transform, int i, Object obj) {
        CharSequence postfix2;
        CharSequence separator2;
        CharSequence separator3;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: joinToString");
        }
        CharSequence separator4 = (i & 1) != 0 ? ", " : separator;
        CharSequence prefix2 = (i & 2) != 0 ? "" : prefix;
        CharSequence postfix3 = (i & 4) != 0 ? "" : postfix;
        int limit2 = (i & 8) != 0 ? -1 : limit;
        CharSequence truncated2 = (i & 16) != 0 ? "..." : truncated;
        Intrinsics.checkNotNullParameter(separator4, "separator");
        Intrinsics.checkNotNullParameter(prefix2, "prefix");
        Intrinsics.checkNotNullParameter(postfix3, "postfix");
        Intrinsics.checkNotNullParameter(truncated2, "truncated");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410 = new StringBuilder();
        $this$joinToString_u24lambda_u2410.append(prefix2);
        int index = 0;
        LongLongMap this_$iv = $this;
        long[] k$iv = this_$iv.keys;
        long[] v$iv = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int $i$f$joinToString = m$iv$iv.length;
        int lastIndex$iv$iv = $i$f$joinToString - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                postfix2 = postfix3;
                int index2 = index;
                LongLongMap this_$iv2 = this_$iv;
                long[] m$iv$iv2 = m$iv$iv;
                if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8;
                    int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    int index3 = index2;
                    while (j$iv$iv < bitCount$iv$iv) {
                        long value$iv$iv$iv = slot$iv$iv & 255;
                        if (value$iv$iv$iv < 128) {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            long key = k$iv[index$iv$iv];
                            long value = v$iv[index$iv$iv];
                            if (index3 == limit2) {
                                $this$joinToString_u24lambda_u2410.append(truncated2);
                                break loop0;
                            }
                            if (index3 != 0) {
                                $this$joinToString_u24lambda_u2410.append(separator4);
                            }
                            separator3 = separator4;
                            $this$joinToString_u24lambda_u2410.append((CharSequence) transform.invoke(Long.valueOf(key), Long.valueOf(value)));
                            index3++;
                        } else {
                            separator3 = separator4;
                        }
                        slot$iv$iv >>= 8;
                        j$iv$iv++;
                        i2 = 8;
                        separator4 = separator3;
                    }
                    separator2 = separator4;
                    if (bitCount$iv$iv != i2) {
                        break;
                    }
                    index = index3;
                } else {
                    separator2 = separator4;
                    index = index2;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                m$iv$iv = m$iv$iv2;
                this_$iv = this_$iv2;
                postfix3 = postfix2;
                separator4 = separator2;
            }
        } else {
            postfix2 = postfix3;
        }
        $this$joinToString_u24lambda_u2410.append(postfix2);
        String string = $this$joinToString_u24lambda_u2410.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, CharSequence truncated, Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        long[] m$iv$iv;
        long[] m$iv$iv2;
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        Intrinsics.checkNotNullParameter(truncated, "truncated");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410 = new StringBuilder();
        $this$joinToString_u24lambda_u2410.append(prefix);
        int bitCount$iv$iv = 0;
        LongLongMap this_$iv = this;
        long[] k$iv = this_$iv.keys;
        long[] v$iv = this_$iv.values;
        LongLongMap this_$iv$iv = this_$iv;
        int $i$f$forEachIndexed = 0;
        long[] m$iv$iv3 = this_$iv$iv.metadata;
        int $i$f$joinToString = m$iv$iv3.length;
        int lastIndex$iv$iv = $i$f$joinToString - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv = m$iv$iv3[i$iv$iv];
                int index = bitCount$iv$iv;
                LongLongMap this_$iv2 = this_$iv;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                long $this$maskEmptyOrDeleted$iv$iv$iv = ((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv != -9187201950435737472L) {
                    int i = 8;
                    int bitCount$iv$iv2 = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    int index2 = index;
                    while (j$iv$iv < bitCount$iv$iv2) {
                        long value$iv$iv$iv = slot$iv$iv & 255;
                        if (value$iv$iv$iv < 128) {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            long key = k$iv[index$iv$iv];
                            long value = v$iv[index$iv$iv];
                            if (index2 == limit) {
                                $this$joinToString_u24lambda_u2410.append(truncated);
                                break loop0;
                            }
                            if (index2 != 0) {
                                $this$joinToString_u24lambda_u2410.append(separator2);
                            }
                            m$iv$iv2 = m$iv$iv3;
                            $this$joinToString_u24lambda_u2410.append(transform.invoke(Long.valueOf(key), Long.valueOf(value)));
                            index2++;
                        } else {
                            m$iv$iv2 = m$iv$iv3;
                        }
                        slot$iv$iv >>= 8;
                        j$iv$iv++;
                        i = 8;
                        m$iv$iv3 = m$iv$iv2;
                        separator2 = separator;
                    }
                    m$iv$iv = m$iv$iv3;
                    if (bitCount$iv$iv2 != i) {
                        break;
                    }
                    bitCount$iv$iv = index2;
                } else {
                    m$iv$iv = m$iv$iv3;
                    bitCount$iv$iv = index;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                separator2 = separator;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEachIndexed = $i$f$forEachIndexed2;
                this_$iv = this_$iv2;
                m$iv$iv3 = m$iv$iv;
            }
        }
        $this$joinToString_u24lambda_u2410.append(postfix);
        String string = $this$joinToString_u24lambda_u2410.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public int hashCode() {
        int bitCount$iv$iv = 0;
        LongLongMap this_$iv = this;
        long[] k$iv = this_$iv.keys;
        long[] v$iv = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                int hash = bitCount$iv$iv;
                LongLongMap this_$iv2 = this_$iv;
                if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                    bitCount$iv$iv = hash;
                } else {
                    int bitCount$iv$iv2 = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    for (int j$iv$iv = 0; j$iv$iv < bitCount$iv$iv2; j$iv$iv++) {
                        long value$iv$iv$iv = 255 & slot$iv$iv;
                        if (value$iv$iv$iv < 128) {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            long key = k$iv[index$iv$iv];
                            long value = v$iv[index$iv$iv];
                            hash += Long.hashCode(key) ^ Long.hashCode(value);
                        }
                        slot$iv$iv >>= 8;
                    }
                    if (bitCount$iv$iv2 != 8) {
                        return hash;
                    }
                    bitCount$iv$iv = hash;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                this_$iv = this_$iv2;
            }
        }
        return bitCount$iv$iv;
    }

    public boolean equals(Object other) {
        LongLongMap this_$iv;
        boolean z;
        LongLongMap this_$iv2;
        if (other == this) {
            return true;
        }
        if (!(other instanceof LongLongMap) || ((LongLongMap) other).get_size() != get_size()) {
            return false;
        }
        LongLongMap this_$iv3 = this;
        int $i$f$forEach = 0;
        long[] k$iv = this_$iv3.keys;
        long[] v$iv = this_$iv3.values;
        long[] m$iv$iv = this_$iv3.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 > lastIndex$iv$iv) {
            return true;
        }
        while (true) {
            long slot$iv$iv = m$iv$iv[i$iv$iv];
            int $i$f$forEach2 = $i$f$forEach;
            long $this$maskEmptyOrDeleted$iv$iv$iv = ((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L);
            if ($this$maskEmptyOrDeleted$iv$iv$iv == -9187201950435737472L) {
                this_$iv = this_$iv3;
                z = false;
            } else {
                int i = 8;
                int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                int j$iv$iv = 0;
                while (j$iv$iv < bitCount$iv$iv) {
                    long value$iv$iv$iv = slot$iv$iv & 255;
                    int $i$f$isFull = value$iv$iv$iv < 128 ? 1 : 0;
                    if ($i$f$isFull == 0) {
                        this_$iv2 = this_$iv3;
                    } else {
                        int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                        this_$iv2 = this_$iv3;
                        long key = k$iv[index$iv$iv];
                        long value = v$iv[index$iv$iv];
                        if (value != ((LongLongMap) other).get(key)) {
                            return false;
                        }
                    }
                    i = 8;
                    slot$iv$iv >>= 8;
                    j$iv$iv++;
                    this_$iv3 = this_$iv2;
                }
                this_$iv = this_$iv3;
                z = false;
                if (bitCount$iv$iv != i) {
                    return true;
                }
            }
            if (i$iv$iv == lastIndex$iv$iv) {
                return true;
            }
            i$iv$iv++;
            $i$f$forEach = $i$f$forEach2;
            this_$iv3 = this_$iv;
        }
    }

    public String toString() {
        int $i$f$forEach;
        long[] k$iv;
        long[] v$iv;
        int $i$f$forEach2;
        long[] k$iv2;
        long[] v$iv2;
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder s = new StringBuilder().append('{');
        int bitCount$iv$iv = 0;
        LongLongMap this_$iv = this;
        int $i$f$forEach3 = 0;
        long[] k$iv3 = this_$iv.keys;
        long[] v$iv3 = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                int i = bitCount$iv$iv;
                LongLongMap this_$iv2 = this_$iv;
                if ((((~slot$iv$iv) << 7) & slot$iv$iv & (-9187201950435737472L)) == -9187201950435737472L) {
                    $i$f$forEach = $i$f$forEach3;
                    k$iv = k$iv3;
                    v$iv = v$iv3;
                    bitCount$iv$iv = i;
                } else {
                    int i2 = 8;
                    int bitCount$iv$iv2 = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    while (j$iv$iv < bitCount$iv$iv2) {
                        long value$iv$iv$iv = 255 & slot$iv$iv;
                        if (!(value$iv$iv$iv < 128)) {
                            $i$f$forEach2 = $i$f$forEach3;
                            k$iv2 = k$iv3;
                            v$iv2 = v$iv3;
                        } else {
                            int index$iv$iv = (i$iv$iv << 3) + j$iv$iv;
                            long key = k$iv3[index$iv$iv];
                            $i$f$forEach2 = $i$f$forEach3;
                            long value = v$iv3[index$iv$iv];
                            k$iv2 = k$iv3;
                            v$iv2 = v$iv3;
                            s.append(key);
                            s.append("=");
                            s.append(value);
                            int i3 = i + 1;
                            if (i3 < this._size) {
                                s.append(',').append(' ');
                            }
                            i = i3;
                        }
                        i2 = 8;
                        slot$iv$iv >>= 8;
                        j$iv$iv++;
                        k$iv3 = k$iv2;
                        $i$f$forEach3 = $i$f$forEach2;
                        v$iv3 = v$iv2;
                    }
                    $i$f$forEach = $i$f$forEach3;
                    k$iv = k$iv3;
                    v$iv = v$iv3;
                    if (bitCount$iv$iv2 != i2) {
                        break;
                    }
                    bitCount$iv$iv = i;
                }
                if (i$iv$iv == lastIndex$iv$iv) {
                    break;
                }
                i$iv$iv++;
                this_$iv = this_$iv2;
                k$iv3 = k$iv;
                $i$f$forEach3 = $i$f$forEach;
                v$iv3 = v$iv;
            }
        }
        String string = s.append('}').toString();
        Intrinsics.checkNotNullExpressionValue(string, "s.append('}').toString()");
        return string;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0082, code lost:
    
        r8 = (((~r6) << 6) & r6) & (-9187201950435737472L);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0090, code lost:
    
        if (r8 == 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0093, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int findKeyIndex(long r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = 0
            int r2 = java.lang.Long.hashCode(r21)
            r3 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r2 = r2 * r3
            int r3 = r2 << 16
            r1 = r2 ^ r3
            r2 = 0
            r2 = r1 & 127(0x7f, float:1.78E-43)
            int r3 = r0._capacity
            r4 = 0
            int r4 = r1 >>> 7
            r4 = r4 & r3
            r5 = 0
        L1b:
            long[] r6 = r0.metadata
            r7 = 0
            int r8 = r4 >> 3
            r9 = r4 & 7
            int r9 = r9 << 3
            r10 = r6[r8]
            long r10 = r10 >>> r9
            int r12 = r8 + 1
            r12 = r6[r12]
            int r14 = 64 - r9
            long r12 = r12 << r14
            long r14 = (long) r9
            long r14 = -r14
            r16 = 63
            long r14 = r14 >> r16
            long r12 = r12 & r14
            long r6 = r10 | r12
            r8 = r6
            r10 = 0
            long r11 = (long) r2
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r11 = r11 * r13
            long r11 = r11 ^ r8
            long r13 = r11 - r13
            r15 = r1
            r16 = r2
            long r1 = ~r11
            long r1 = r1 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r1 = r1 & r13
        L51:
            r8 = r1
            r10 = 0
            r11 = 0
            int r17 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r17 == 0) goto L5c
            r17 = 1
            goto L5e
        L5c:
            r17 = 0
        L5e:
            if (r17 == 0) goto L82
            r8 = r1
            r10 = 0
            r11 = r8
            r17 = 0
            int r18 = java.lang.Long.numberOfTrailingZeros(r11)
            int r11 = r18 >> 3
            int r11 = r11 + r4
            r8 = r11 & r3
            long[] r9 = r0.keys
            r10 = r9[r8]
            int r9 = (r10 > r21 ? 1 : (r10 == r21 ? 0 : -1))
            if (r9 != 0) goto L78
            return r8
        L78:
            r9 = r1
            r11 = 0
            r17 = 1
            long r17 = r9 - r17
            long r9 = r9 & r17
            r1 = r9
            goto L51
        L82:
            r8 = r6
            r10 = 0
            long r11 = ~r8
            r19 = 6
            long r11 = r11 << r19
            long r11 = r11 & r8
            long r8 = r11 & r13
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 == 0) goto L95
        L93:
            r1 = -1
            return r1
        L95:
            int r5 = r5 + 8
            int r8 = r4 + r5
            r4 = r8 & r3
            r1 = r15
            r2 = r16
            goto L1b
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LongLongMap.findKeyIndex(long):int");
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        long[] m$iv$iv$iv;
        int index$iv;
        long[] m$iv$iv$iv2;
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410$iv = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u2410$iv.append(prefix);
        int index$iv2 = 0;
        LongLongMap this_$iv$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv$iv = this_$iv$iv.keys;
        long[] v$iv$iv = this_$iv$iv.values;
        long[] m$iv$iv$iv3 = this_$iv$iv.metadata;
        int $i$f$joinToString = m$iv$iv$iv3.length;
        int lastIndex$iv$iv$iv = $i$f$joinToString - 2;
        int i$iv$iv$iv = 0;
        if (0 <= lastIndex$iv$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv$iv = m$iv$iv$iv3[i$iv$iv$iv];
                int i2 = i;
                int index$iv3 = index$iv2;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv$iv = ((~slot$iv$iv$iv) << 7) & slot$iv$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv$iv != -9187201950435737472L) {
                    int i3 = 8;
                    int bitCount$iv$iv$iv = 8 - ((~(i$iv$iv$iv - lastIndex$iv$iv$iv)) >>> 31);
                    int j$iv$iv$iv = 0;
                    index$iv = index$iv3;
                    while (j$iv$iv$iv < bitCount$iv$iv$iv) {
                        long value$iv$iv$iv$iv = slot$iv$iv$iv & 255;
                        if (value$iv$iv$iv$iv < 128) {
                            int index$iv$iv$iv = (i$iv$iv$iv << 3) + j$iv$iv$iv;
                            long key$iv = k$iv$iv[index$iv$iv$iv];
                            long value$iv = v$iv$iv[index$iv$iv$iv];
                            if (index$iv == limit) {
                                $this$joinToString_u24lambda_u2410$iv.append(truncated$iv);
                                break loop0;
                            }
                            if (index$iv != 0) {
                                $this$joinToString_u24lambda_u2410$iv.append(separator2);
                            }
                            m$iv$iv$iv2 = m$iv$iv$iv3;
                            $this$joinToString_u24lambda_u2410$iv.append(transform.invoke(Long.valueOf(key$iv), Long.valueOf(value$iv)));
                            index$iv++;
                        } else {
                            m$iv$iv$iv2 = m$iv$iv$iv3;
                        }
                        slot$iv$iv$iv >>= 8;
                        j$iv$iv$iv++;
                        i3 = 8;
                        m$iv$iv$iv3 = m$iv$iv$iv2;
                        separator2 = separator;
                    }
                    m$iv$iv$iv = m$iv$iv$iv3;
                    if (bitCount$iv$iv$iv != i3) {
                        break;
                    }
                } else {
                    m$iv$iv$iv = m$iv$iv$iv3;
                    index$iv = index$iv3;
                }
                if (i$iv$iv$iv == lastIndex$iv$iv$iv) {
                    break;
                }
                i$iv$iv$iv++;
                separator2 = separator;
                index$iv2 = index$iv;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
                m$iv$iv$iv3 = m$iv$iv$iv;
            }
        }
        $this$joinToString_u24lambda_u2410$iv.append(postfix);
        String string = $this$joinToString_u24lambda_u2410$iv.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, CharSequence postfix, Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(postfix, "postfix");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410$iv = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u2410$iv.append(prefix);
        int index$iv = 0;
        LongLongMap this_$iv$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv$iv = this_$iv$iv.keys;
        long[] v$iv$iv = this_$iv$iv.values;
        long[] m$iv$iv$iv = this_$iv$iv.metadata;
        int lastIndex$iv$iv$iv = m$iv$iv$iv.length - 2;
        int i$iv$iv$iv = 0;
        if (0 <= lastIndex$iv$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv$iv = m$iv$iv$iv[i$iv$iv$iv];
                int i2 = i;
                int index$iv2 = index$iv;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv$iv = ((~slot$iv$iv$iv) << 7) & slot$iv$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv$iv == -9187201950435737472L) {
                    index$iv = index$iv2;
                } else {
                    int i3 = 8;
                    int bitCount$iv$iv$iv = 8 - ((~(i$iv$iv$iv - lastIndex$iv$iv$iv)) >>> 31);
                    int j$iv$iv$iv = 0;
                    int index$iv3 = index$iv2;
                    while (j$iv$iv$iv < bitCount$iv$iv$iv) {
                        long value$iv$iv$iv$iv = slot$iv$iv$iv & 255;
                        if (value$iv$iv$iv$iv < 128) {
                            int index$iv$iv$iv = (i$iv$iv$iv << 3) + j$iv$iv$iv;
                            long key$iv = k$iv$iv[index$iv$iv$iv];
                            long value$iv = v$iv$iv[index$iv$iv$iv];
                            if (index$iv3 == -1) {
                                $this$joinToString_u24lambda_u2410$iv.append(truncated$iv);
                                break loop0;
                            }
                            if (index$iv3 != 0) {
                                $this$joinToString_u24lambda_u2410$iv.append(separator2);
                            }
                            $this$joinToString_u24lambda_u2410$iv.append(transform.invoke(Long.valueOf(key$iv), Long.valueOf(value$iv)));
                            index$iv3++;
                        }
                        slot$iv$iv$iv >>= 8;
                        j$iv$iv$iv++;
                        i3 = 8;
                        separator2 = separator;
                    }
                    if (bitCount$iv$iv$iv != i3) {
                        break;
                    }
                    index$iv = index$iv3;
                }
                if (i$iv$iv$iv == lastIndex$iv$iv$iv) {
                    break;
                }
                i$iv$iv$iv++;
                separator2 = separator;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
            }
        }
        $this$joinToString_u24lambda_u2410$iv.append(postfix);
        String string = $this$joinToString_u24lambda_u2410$iv.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final String joinToString(CharSequence separator, CharSequence prefix, Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410$iv = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u2410$iv.append(prefix);
        int index$iv = 0;
        LongLongMap this_$iv$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv$iv = this_$iv$iv.keys;
        long[] v$iv$iv = this_$iv$iv.values;
        long[] m$iv$iv$iv = this_$iv$iv.metadata;
        int lastIndex$iv$iv$iv = m$iv$iv$iv.length - 2;
        int i$iv$iv$iv = 0;
        if (0 <= lastIndex$iv$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv$iv = m$iv$iv$iv[i$iv$iv$iv];
                int i2 = i;
                int index$iv2 = index$iv;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv$iv = ((~slot$iv$iv$iv) << 7) & slot$iv$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv$iv != -9187201950435737472L) {
                    int i3 = 8;
                    int bitCount$iv$iv$iv = 8 - ((~(i$iv$iv$iv - lastIndex$iv$iv$iv)) >>> 31);
                    int j$iv$iv$iv = 0;
                    int index$iv3 = index$iv2;
                    while (j$iv$iv$iv < bitCount$iv$iv$iv) {
                        long value$iv$iv$iv$iv = slot$iv$iv$iv & 255;
                        if (value$iv$iv$iv$iv < 128) {
                            int index$iv$iv$iv = (i$iv$iv$iv << 3) + j$iv$iv$iv;
                            long key$iv = k$iv$iv[index$iv$iv$iv];
                            long value$iv = v$iv$iv[index$iv$iv$iv];
                            if (index$iv3 == -1) {
                                $this$joinToString_u24lambda_u2410$iv.append(truncated$iv);
                                break loop0;
                            }
                            if (index$iv3 != 0) {
                                $this$joinToString_u24lambda_u2410$iv.append(separator2);
                            }
                            $this$joinToString_u24lambda_u2410$iv.append(transform.invoke(Long.valueOf(key$iv), Long.valueOf(value$iv)));
                            index$iv3++;
                        }
                        slot$iv$iv$iv >>= 8;
                        j$iv$iv$iv++;
                        i3 = 8;
                        separator2 = separator;
                    }
                    if (bitCount$iv$iv$iv != i3) {
                        break;
                    }
                    index$iv = index$iv3;
                } else {
                    index$iv = index$iv2;
                }
                if (i$iv$iv$iv == lastIndex$iv$iv$iv) {
                    break;
                }
                i$iv$iv$iv++;
                separator2 = separator;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
            }
        }
        $this$joinToString_u24lambda_u2410$iv.append(postfix$iv);
        String string = $this$joinToString_u24lambda_u2410$iv.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final String joinToString(CharSequence separator, Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        CharSequence separator2 = separator;
        Intrinsics.checkNotNullParameter(separator2, "separator");
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410$iv = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u2410$iv.append(prefix$iv);
        int index$iv = 0;
        LongLongMap this_$iv$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv$iv = this_$iv$iv.keys;
        long[] v$iv$iv = this_$iv$iv.values;
        long[] m$iv$iv$iv = this_$iv$iv.metadata;
        int lastIndex$iv$iv$iv = m$iv$iv$iv.length - 2;
        int i$iv$iv$iv = 0;
        if (0 <= lastIndex$iv$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv$iv = m$iv$iv$iv[i$iv$iv$iv];
                int i2 = i;
                int index$iv2 = index$iv;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv$iv = ((~slot$iv$iv$iv) << 7) & slot$iv$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv$iv != -9187201950435737472L) {
                    int i3 = 8;
                    int bitCount$iv$iv$iv = 8 - ((~(i$iv$iv$iv - lastIndex$iv$iv$iv)) >>> 31);
                    int j$iv$iv$iv = 0;
                    int index$iv3 = index$iv2;
                    while (j$iv$iv$iv < bitCount$iv$iv$iv) {
                        long value$iv$iv$iv$iv = slot$iv$iv$iv & 255;
                        if (value$iv$iv$iv$iv < 128) {
                            int index$iv$iv$iv = (i$iv$iv$iv << 3) + j$iv$iv$iv;
                            long key$iv = k$iv$iv[index$iv$iv$iv];
                            long value$iv = v$iv$iv[index$iv$iv$iv];
                            if (index$iv3 == -1) {
                                $this$joinToString_u24lambda_u2410$iv.append(truncated$iv);
                                break loop0;
                            }
                            if (index$iv3 != 0) {
                                $this$joinToString_u24lambda_u2410$iv.append(separator2);
                            }
                            $this$joinToString_u24lambda_u2410$iv.append(transform.invoke(Long.valueOf(key$iv), Long.valueOf(value$iv)));
                            index$iv3++;
                        }
                        slot$iv$iv$iv >>= 8;
                        j$iv$iv$iv++;
                        i3 = 8;
                        separator2 = separator;
                    }
                    if (bitCount$iv$iv$iv != i3) {
                        break;
                    }
                    index$iv = index$iv3;
                } else {
                    index$iv = index$iv2;
                }
                if (i$iv$iv$iv == lastIndex$iv$iv$iv) {
                    break;
                }
                i$iv$iv$iv++;
                separator2 = separator;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
            }
        }
        $this$joinToString_u24lambda_u2410$iv.append(postfix$iv);
        String string = $this$joinToString_u24lambda_u2410$iv.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public final String joinToString(Function2<? super Long, ? super Long, ? extends CharSequence> transform) {
        CharSequence separator$iv;
        CharSequence separator$iv2;
        Intrinsics.checkNotNullParameter(transform, "transform");
        StringBuilder $this$joinToString_u24lambda_u2410$iv = new StringBuilder();
        int i = 0;
        $this$joinToString_u24lambda_u2410$iv.append(prefix$iv);
        int index$iv = 0;
        LongLongMap this_$iv$iv = this;
        int $i$f$forEach = 0;
        long[] k$iv$iv = this_$iv$iv.keys;
        long[] v$iv$iv = this_$iv$iv.values;
        long[] m$iv$iv$iv = this_$iv$iv.metadata;
        int lastIndex$iv$iv$iv = m$iv$iv$iv.length - 2;
        int i$iv$iv$iv = 0;
        if (0 <= lastIndex$iv$iv$iv) {
            loop0: while (true) {
                long slot$iv$iv$iv = m$iv$iv$iv[i$iv$iv$iv];
                int i2 = i;
                int index$iv2 = index$iv;
                LongLongMap this_$iv$iv2 = this_$iv$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long $this$maskEmptyOrDeleted$iv$iv$iv$iv = ((~slot$iv$iv$iv) << 7) & slot$iv$iv$iv & (-9187201950435737472L);
                if ($this$maskEmptyOrDeleted$iv$iv$iv$iv != -9187201950435737472L) {
                    int i3 = 8;
                    int bitCount$iv$iv$iv = 8 - ((~(i$iv$iv$iv - lastIndex$iv$iv$iv)) >>> 31);
                    int j$iv$iv$iv = 0;
                    int index$iv3 = index$iv2;
                    while (j$iv$iv$iv < bitCount$iv$iv$iv) {
                        long value$iv$iv$iv$iv = slot$iv$iv$iv & 255;
                        if (value$iv$iv$iv$iv < 128) {
                            int index$iv$iv$iv = (i$iv$iv$iv << 3) + j$iv$iv$iv;
                            long key$iv = k$iv$iv[index$iv$iv$iv];
                            long value$iv = v$iv$iv[index$iv$iv$iv];
                            if (index$iv3 == -1) {
                                $this$joinToString_u24lambda_u2410$iv.append(truncated$iv);
                                break loop0;
                            }
                            if (index$iv3 != 0) {
                                $this$joinToString_u24lambda_u2410$iv.append(separator$iv);
                            }
                            separator$iv2 = separator$iv;
                            $this$joinToString_u24lambda_u2410$iv.append(transform.invoke(Long.valueOf(key$iv), Long.valueOf(value$iv)));
                            index$iv3++;
                        } else {
                            separator$iv2 = separator$iv;
                        }
                        slot$iv$iv$iv >>= 8;
                        j$iv$iv$iv++;
                        i3 = 8;
                        separator$iv = separator$iv2;
                    }
                    separator$iv = separator$iv;
                    if (bitCount$iv$iv$iv != i3) {
                        break;
                    }
                    index$iv = index$iv3;
                } else {
                    separator$iv = separator$iv;
                    index$iv = index$iv2;
                }
                if (i$iv$iv$iv == lastIndex$iv$iv$iv) {
                    break;
                }
                i$iv$iv$iv++;
                this_$iv$iv = this_$iv$iv2;
                $i$f$forEach = $i$f$forEach2;
                i = i2;
                separator$iv = separator$iv;
            }
        }
        $this$joinToString_u24lambda_u2410$iv.append(postfix$iv);
        String string = $this$joinToString_u24lambda_u2410$iv.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
