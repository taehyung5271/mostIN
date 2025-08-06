package androidx.collection;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LongList.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\b\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001J\u0018\u0010\u000e\u001a\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0001J\u0018\u0010\u000e\u001a\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0003J\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086\nJ\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\u0002J\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086\nJ\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\u0002J\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0017\u001a\u00020\f2\b\b\u0001\u0010\n\u001a\u00020\u0003J\u001a\u0010\u0018\u001a\u00020\t2\b\b\u0001\u0010\u0019\u001a\u00020\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u001b\u0010\u001c\u001a\u00020\f2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u0006\u0010\u001d\u001a\u00020\tJ\u0006\u0010\u001e\u001a\u00020\tJ\u0010\u0010\u001f\u001a\u00020\t2\b\b\u0002\u0010 \u001a\u00020\u0003R\u0012\u0010\u0005\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"Landroidx/collection/MutableLongList;", "Landroidx/collection/LongList;", "initialCapacity", "", "(I)V", "capacity", "getCapacity", "()I", "add", "", "index", "element", "", "", "addAll", "elements", "", "clear", "ensureCapacity", "minusAssign", "plusAssign", "remove", "removeAll", "removeAt", "removeRange", "start", "end", "retainAll", "set", "sort", "sortDescending", "trim", "minCapacity", "collection"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class MutableLongList extends LongList {
    public MutableLongList() {
        this(0, 1, null);
    }

    public /* synthetic */ MutableLongList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public MutableLongList(int initialCapacity) {
        super(initialCapacity, null);
    }

    public final int getCapacity() {
        return this.content.length;
    }

    public final boolean add(long element) {
        ensureCapacity(this._size + 1);
        this.content[this._size] = element;
        this._size++;
        return true;
    }

    public final void add(int index, long element) {
        boolean z = false;
        if (index >= 0 && index <= this._size) {
            z = true;
        }
        if (!z) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        ensureCapacity(this._size + 1);
        long[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, index + 1, index, this._size);
        }
        content[index] = element;
        this._size++;
    }

    public final boolean addAll(int index, long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (!(index >= 0 && index <= this._size)) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        if (elements.length == 0) {
            return false;
        }
        ensureCapacity(this._size + elements.length);
        long[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, elements.length + index, index, this._size);
        }
        ArraysKt.copyInto$default(elements, content, index, 0, 0, 12, (Object) null);
        this._size += elements.length;
        return true;
    }

    public final boolean addAll(int index, LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (!(index >= 0 && index <= this._size)) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        if (elements.isEmpty()) {
            return false;
        }
        ensureCapacity(this._size + elements._size);
        long[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, elements._size + index, index, this._size);
        }
        ArraysKt.copyInto(elements.content, content, index, 0, elements._size);
        this._size += elements._size;
        return true;
    }

    public final boolean addAll(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final boolean addAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final void plusAssign(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void plusAssign(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void clear() {
        this._size = 0;
    }

    public static /* synthetic */ void trim$default(MutableLongList mutableLongList, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = mutableLongList._size;
        }
        mutableLongList.trim(i);
    }

    public final void trim(int minCapacity) {
        int minSize = Math.max(minCapacity, this._size);
        if (this.content.length > minSize) {
            long[] jArrCopyOf = Arrays.copyOf(this.content, minSize);
            Intrinsics.checkNotNullExpressionValue(jArrCopyOf, "copyOf(this, newSize)");
            this.content = jArrCopyOf;
        }
    }

    public final void ensureCapacity(int capacity) {
        long[] oldContent = this.content;
        if (oldContent.length < capacity) {
            int newSize = Math.max(capacity, (oldContent.length * 3) / 2);
            long[] jArrCopyOf = Arrays.copyOf(oldContent, newSize);
            Intrinsics.checkNotNullExpressionValue(jArrCopyOf, "copyOf(this, newSize)");
            this.content = jArrCopyOf;
        }
    }

    public final void plusAssign(long element) {
        add(element);
    }

    public final void minusAssign(long element) {
        remove(element);
    }

    public final boolean remove(long element) {
        int index = indexOf(element);
        if (index >= 0) {
            removeAt(index);
            return true;
        }
        return false;
    }

    public final boolean removeAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        for (long j : elements) {
            remove(j);
        }
        int i = this._size;
        return initialSize != i;
    }

    public final boolean removeAll(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        int i = 0;
        int i2 = elements._size - 1;
        if (0 <= i2) {
            while (true) {
                remove(elements.get(i));
                if (i == i2) {
                    break;
                }
                i++;
            }
        }
        int i3 = this._size;
        return initialSize != i3;
    }

    public final void minusAssign(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        for (long element$iv : elements) {
            remove(element$iv);
        }
    }

    public final void minusAssign(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        long[] content$iv = elements.content;
        int i = elements._size;
        for (int i$iv = 0; i$iv < i; i$iv++) {
            long element = content$iv[i$iv];
            remove(element);
        }
    }

    public final long removeAt(int index) {
        boolean z = false;
        if (index >= 0 && index < this._size) {
            z = true;
        }
        if (!z) {
            MutableLongList this_$iv = this;
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + (this_$iv._size - 1));
        }
        long[] content = this.content;
        long item = content[index];
        MutableLongList this_$iv2 = this;
        if (index != this_$iv2._size - 1) {
            ArraysKt.copyInto(content, content, index, index + 1, this._size);
        }
        this._size--;
        return item;
    }

    public final void removeRange(int start, int end) {
        if (start >= 0 && start <= this._size) {
            if (end >= 0 && end <= this._size) {
                if (end < start) {
                    throw new IllegalArgumentException("Start (" + start + ") is more than end (" + end + ')');
                }
                if (end != start) {
                    if (end < this._size) {
                        ArraysKt.copyInto(this.content, this.content, start, end, this._size);
                    }
                    this._size -= end - start;
                    return;
                }
                return;
            }
        }
        throw new IndexOutOfBoundsException("Start (" + start + ") and end (" + end + ") must be in 0.." + this._size);
    }

    public final boolean retainAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        long[] content = this.content;
        MutableLongList this_$iv = this;
        int i = this_$iv._size - 1;
        while (true) {
            int i2 = -1;
            if (-1 >= i) {
                break;
            }
            long item = content[i];
            int index$iv = 0;
            int length = elements.length;
            while (true) {
                if (index$iv >= length) {
                    break;
                }
                long it = elements[index$iv];
                if (!(it == item)) {
                    index$iv++;
                } else {
                    i2 = index$iv;
                    break;
                }
            }
            if (i2 < 0) {
                removeAt(i);
            }
            i--;
        }
        int i3 = this._size;
        return initialSize != i3;
    }

    public final boolean retainAll(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        long[] content = this.content;
        MutableLongList this_$iv = this;
        for (int i = this_$iv._size - 1; -1 < i; i--) {
            long item = content[i];
            if (!elements.contains(item)) {
                removeAt(i);
            }
        }
        int i2 = this._size;
        return initialSize != i2;
    }

    public final long set(int index, long element) {
        boolean z = false;
        if (index >= 0 && index < this._size) {
            z = true;
        }
        if (!z) {
            MutableLongList this_$iv = this;
            throw new IndexOutOfBoundsException("set index " + index + " must be between 0 .. " + (this_$iv._size - 1));
        }
        long[] content = this.content;
        long old = content[index];
        content[index] = element;
        return old;
    }

    public final void sort() {
        ArraysKt.sort(this.content, 0, this._size);
    }

    public final void sortDescending() {
        ArraysKt.sortDescending(this.content, 0, this._size);
    }
}
