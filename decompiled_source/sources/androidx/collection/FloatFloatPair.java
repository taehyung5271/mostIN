package androidx.collection;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;

/* compiled from: FloatFloatPair.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0087@\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006B\u0011\b\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\u0005\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u0003H\u0086\n¢\u0006\u0004\b\u0010\u0010\u000bJ\u0010\u0010\u0011\u001a\u00020\u0003H\u0086\n¢\u0006\u0004\b\u0012\u0010\u000bJ\u001a\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u0019HÖ\u0001¢\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u001dH\u0016¢\u0006\u0004\b\u001e\u0010\u001fR\u0012\u0010\u0002\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rR\u0012\u0010\u0004\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000b\u0088\u0001\u0007\u0092\u0001\u00020\b¨\u0006 "}, d2 = {"Landroidx/collection/FloatFloatPair;", "", "first", "", "second", "constructor-impl", "(FF)J", "packedValue", "", "(J)J", "getFirst-impl", "(J)F", "getPackedValue$annotations", "()V", "getSecond-impl", "component1", "component1-impl", "component2", "component2-impl", "equals", "", "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "", "hashCode-impl", "(J)I", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "collection"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@JvmInline
/* loaded from: classes.dex */
public final class FloatFloatPair {
    public final long packedValue;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FloatFloatPair m8boximpl(long j) {
        return new FloatFloatPair(j);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m12constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m13equalsimpl(long j, Object obj) {
        return (obj instanceof FloatFloatPair) && j == ((FloatFloatPair) obj).getPackedValue();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m14equalsimpl0(long j, long j2) {
        return j == j2;
    }

    public static /* synthetic */ void getPackedValue$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m17hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    public boolean equals(Object obj) {
        return m13equalsimpl(this.packedValue, obj);
    }

    public int hashCode() {
        return m17hashCodeimpl(this.packedValue);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getPackedValue() {
        return this.packedValue;
    }

    private /* synthetic */ FloatFloatPair(long packedValue) {
        this.packedValue = packedValue;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m11constructorimpl(float first, float second) {
        long v1$iv = Float.floatToRawIntBits(first);
        long v2$iv = Float.floatToRawIntBits(second);
        return m12constructorimpl((v1$iv << 32) | (4294967295L & v2$iv));
    }

    /* renamed from: getFirst-impl, reason: not valid java name */
    public static final float m15getFirstimpl(long arg0) {
        int bits$iv = (int) (arg0 >> 32);
        return Float.intBitsToFloat(bits$iv);
    }

    /* renamed from: getSecond-impl, reason: not valid java name */
    public static final float m16getSecondimpl(long arg0) {
        int bits$iv = (int) (4294967295L & arg0);
        return Float.intBitsToFloat(bits$iv);
    }

    /* renamed from: component1-impl, reason: not valid java name */
    public static final float m9component1impl(long arg0) {
        int bits$iv = (int) (arg0 >> 32);
        return Float.intBitsToFloat(bits$iv);
    }

    /* renamed from: component2-impl, reason: not valid java name */
    public static final float m10component2impl(long arg0) {
        int bits$iv = (int) (4294967295L & arg0);
        return Float.intBitsToFloat(bits$iv);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m18toStringimpl(long arg0) {
        int bits$iv$iv = (int) (arg0 >> 32);
        StringBuilder sbAppend = new StringBuilder().append('(').append(Float.intBitsToFloat(bits$iv$iv)).append(", ");
        int bits$iv$iv2 = (int) (4294967295L & arg0);
        return sbAppend.append(Float.intBitsToFloat(bits$iv$iv2)).append(')').toString();
    }

    public String toString() {
        return m18toStringimpl(this.packedValue);
    }
}
