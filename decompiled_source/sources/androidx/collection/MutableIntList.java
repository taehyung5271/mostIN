package androidx.collection;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IntList.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003J\u0018\u0010\b\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003J\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0001J\u0018\u0010\r\u001a\u00020\t2\b\b\u0001\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0001J\u0018\u0010\r\u001a\u00020\t2\b\b\u0001\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0003J\u0011\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u0003H\u0086\nJ\u0011\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\u0002J\u0011\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u0003H\u0086\nJ\u0011\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\u0002J\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003J\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0001J\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0016\u001a\u00020\u00032\b\b\u0001\u0010\f\u001a\u00020\u0003J\u001a\u0010\u0017\u001a\u00020\u000b2\b\b\u0001\u0010\u0018\u001a\u00020\u00032\b\b\u0001\u0010\u0019\u001a\u00020\u0003J\u000e\u0010\u001a\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0001J\u000e\u0010\u001a\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001b\u0010\u001b\u001a\u00020\u00032\b\b\u0001\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0086\u0002J\u0006\u0010\u001c\u001a\u00020\u000bJ\u0006\u0010\u001d\u001a\u00020\u000bJ\u0010\u0010\u001e\u001a\u00020\u000b2\b\b\u0002\u0010\u001f\u001a\u00020\u0003R\u0012\u0010\u0005\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006 "}, d2 = {"Landroidx/collection/MutableIntList;", "Landroidx/collection/IntList;", "initialCapacity", "", "(I)V", "capacity", "getCapacity", "()I", "add", "", "element", "", "index", "addAll", "elements", "", "clear", "ensureCapacity", "minusAssign", "plusAssign", "remove", "removeAll", "removeAt", "removeRange", "start", "end", "retainAll", "set", "sort", "sortDescending", "trim", "minCapacity", "collection"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class MutableIntList extends IntList {
    public MutableIntList() {
        this(0, 1, null);
    }

    public /* synthetic */ MutableIntList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public MutableIntList(int initialCapacity) {
        super(initialCapacity, null);
    }

    public final int getCapacity() {
        return this.content.length;
    }

    public final boolean add(int element) {
        ensureCapacity(this._size + 1);
        this.content[this._size] = element;
        this._size++;
        return true;
    }

    public final void add(int index, int element) {
        boolean z = false;
        if (index >= 0 && index <= this._size) {
            z = true;
        }
        if (!z) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        ensureCapacity(this._size + 1);
        int[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, index + 1, index, this._size);
        }
        content[index] = element;
        this._size++;
    }

    public final boolean addAll(int index, int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (!(index >= 0 && index <= this._size)) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        if (elements.length == 0) {
            return false;
        }
        ensureCapacity(this._size + elements.length);
        int[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, elements.length + index, index, this._size);
        }
        ArraysKt.copyInto$default(elements, content, index, 0, 0, 12, (Object) null);
        this._size += elements.length;
        return true;
    }

    public final boolean addAll(int index, IntList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (!(index >= 0 && index <= this._size)) {
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + this._size);
        }
        if (elements.isEmpty()) {
            return false;
        }
        ensureCapacity(this._size + elements._size);
        int[] content = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(content, content, elements._size + index, index, this._size);
        }
        ArraysKt.copyInto(elements.content, content, index, 0, elements._size);
        this._size += elements._size;
        return true;
    }

    public final boolean addAll(IntList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final boolean addAll(int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final void plusAssign(IntList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void plusAssign(int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void clear() {
        this._size = 0;
    }

    public static /* synthetic */ void trim$default(MutableIntList mutableIntList, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = mutableIntList._size;
        }
        mutableIntList.trim(i);
    }

    public final void trim(int minCapacity) {
        int minSize = Math.max(minCapacity, this._size);
        if (this.content.length > minSize) {
            int[] iArrCopyOf = Arrays.copyOf(this.content, minSize);
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(this, newSize)");
            this.content = iArrCopyOf;
        }
    }

    public final void ensureCapacity(int capacity) {
        int[] oldContent = this.content;
        if (oldContent.length < capacity) {
            int newSize = Math.max(capacity, (oldContent.length * 3) / 2);
            int[] iArrCopyOf = Arrays.copyOf(oldContent, newSize);
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(this, newSize)");
            this.content = iArrCopyOf;
        }
    }

    public final void plusAssign(int element) {
        add(element);
    }

    public final void minusAssign(int element) {
        remove(element);
    }

    public final boolean remove(int element) {
        int index = indexOf(element);
        if (index >= 0) {
            removeAt(index);
            return true;
        }
        return false;
    }

    public final boolean removeAll(int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        for (int i : elements) {
            remove(i);
        }
        int i2 = this._size;
        return initialSize != i2;
    }

    public final boolean removeAll(IntList elements) {
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

    public final void minusAssign(int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        for (int element$iv : elements) {
            remove(element$iv);
        }
    }

    public final void minusAssign(IntList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int[] content$iv = elements.content;
        int i = elements._size;
        for (int i$iv = 0; i$iv < i; i$iv++) {
            int element = content$iv[i$iv];
            remove(element);
        }
    }

    public final int removeAt(int index) {
        boolean z = false;
        if (index >= 0 && index < this._size) {
            z = true;
        }
        if (!z) {
            MutableIntList this_$iv = this;
            throw new IndexOutOfBoundsException("Index " + index + " must be in 0.." + (this_$iv._size - 1));
        }
        int[] content = this.content;
        int item = content[index];
        MutableIntList this_$iv2 = this;
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

    public final boolean retainAll(int[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        int[] content = this.content;
        MutableIntList this_$iv = this;
        int i = this_$iv._size - 1;
        while (true) {
            int i2 = -1;
            if (-1 >= i) {
                break;
            }
            int item = content[i];
            int index$iv = 0;
            int length = elements.length;
            while (true) {
                if (index$iv >= length) {
                    break;
                }
                int it = elements[index$iv];
                int it2 = it == item ? 1 : 0;
                if (it2 == 0) {
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

    public final boolean retainAll(IntList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int initialSize = this._size;
        int[] content = this.content;
        MutableIntList this_$iv = this;
        for (int i = this_$iv._size - 1; -1 < i; i--) {
            int item = content[i];
            if (!elements.contains(item)) {
                removeAt(i);
            }
        }
        int i2 = this._size;
        return initialSize != i2;
    }

    public final int set(int index, int element) {
        boolean z = false;
        if (index >= 0 && index < this._size) {
            z = true;
        }
        if (!z) {
            MutableIntList this_$iv = this;
            throw new IndexOutOfBoundsException("set index " + index + " must be between 0 .. " + (this_$iv._size - 1));
        }
        int[] content = this.content;
        int old = content[index];
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
