package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.naver.maps.map.NaverMapSdk;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: UShort.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 v2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001vB\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u000f\u0010\u0010J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u001c\u0010\u0010J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u001d\u0010\u0013J\u0018\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u001e\u0010\u001fJ\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b'\u0010\u0010J\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b(\u0010\u0013J\u0018\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b)\u0010\u001fJ\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0013\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0013\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b3\u0010\u0010J\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b4\u0010\u0013J\u0018\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b5\u0010\u001fJ\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b6\u0010\u0018J\u0018\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b8\u00109J\u0018\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b:\u0010\u0013J\u0018\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b;\u0010\u001fJ\u0018\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b<\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b>\u0010\u000bJ\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b@\u0010\u0010J\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bA\u0010\u0013J\u0018\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bB\u0010\u001fJ\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bC\u0010\u0018J\u0018\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bF\u0010GJ\u0018\u0010H\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bI\u0010GJ\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\bK\u0010\u0010J\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bL\u0010\u0013J\u0018\u0010J\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bM\u0010\u001fJ\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bN\u0010\u0018J\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\bP\u0010\u0010J\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bQ\u0010\u0013J\u0018\u0010O\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bR\u0010\u001fJ\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bS\u0010\u0018J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u0010\u0010\\\u001a\u00020]H\u0087\b¢\u0006\u0004\b^\u0010_J\u0010\u0010`\u001a\u00020\rH\u0087\b¢\u0006\u0004\ba\u0010-J\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bg\u0010\u0005J\u000f\u0010h\u001a\u00020iH\u0016¢\u0006\u0004\bj\u0010kJ\u0013\u0010l\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\bm\u0010WJ\u0013\u0010n\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bo\u0010-J\u0013\u0010p\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\bq\u0010eJ\u0013\u0010r\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bs\u0010\u0005J\u0018\u0010t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bu\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u0004\n\u0002\b!¨\u0006w"}, d2 = {"Lkotlin/UShort;", "", NaverMapSdk.METADATA_VALUE_CACHE_LOCATION_DATA, "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-xj2QHRw", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@JvmInline
/* loaded from: classes.dex */
public final class UShort implements Comparable<UShort> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m502boximpl(short s) {
        return new UShort(s);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static short m508constructorimpl(short s) {
        return s;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m514equalsimpl(short s, Object obj) {
        return (obj instanceof UShort) && s == ((UShort) obj).getData();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m515equalsimpl0(short s, short s2) {
        return s == s2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m520hashCodeimpl(short s) {
        return Short.hashCode(s);
    }

    public boolean equals(Object obj) {
        return m514equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m520hashCodeimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ short getData() {
        return this.data;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return Intrinsics.compare(getData() & MAX_VALUE, uShort.getData() & MAX_VALUE);
    }

    private /* synthetic */ UShort(short data) {
        this.data = data;
    }

    /* compiled from: UShort.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m503compareTo7apg3OU(short arg0, byte other) {
        return Intrinsics.compare(65535 & arg0, other & UByte.MAX_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private int m506compareToxj2QHRw(short other) {
        return Intrinsics.compare(getData() & MAX_VALUE, 65535 & other);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static int m507compareToxj2QHRw(short arg0, short other) {
        return Intrinsics.compare(arg0 & MAX_VALUE, 65535 & other);
    }

    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m505compareToWZ4Q5Ns(short arg0, int other) {
        return Integer.compareUnsigned(UInt.m322constructorimpl(65535 & arg0), other);
    }

    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m504compareToVKZWuLQ(short arg0, long other) {
        return Long.compareUnsigned(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m532plus7apg3OU(short arg0, byte other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) + UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m535plusxj2QHRw(short arg0, short other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(arg0 & MAX_VALUE) + UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m534plusWZ4Q5Ns(short arg0, int other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) + other);
    }

    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m533plusVKZWuLQ(short arg0, long other) {
        return ULong.m401constructorimpl(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) + other);
    }

    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m523minus7apg3OU(short arg0, byte other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) - UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m526minusxj2QHRw(short arg0, short other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(arg0 & MAX_VALUE) - UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m525minusWZ4Q5Ns(short arg0, int other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) - other);
    }

    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m524minusVKZWuLQ(short arg0, long other) {
        return ULong.m401constructorimpl(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) - other);
    }

    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m542times7apg3OU(short arg0, byte other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) * UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m545timesxj2QHRw(short arg0, short other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(arg0 & MAX_VALUE) * UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m544timesWZ4Q5Ns(short arg0, int other) {
        return UInt.m322constructorimpl(UInt.m322constructorimpl(65535 & arg0) * other);
    }

    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m543timesVKZWuLQ(short arg0, long other) {
        return ULong.m401constructorimpl(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) * other);
    }

    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m510div7apg3OU(short arg0, byte other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(65535 & arg0), UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m513divxj2QHRw(short arg0, short other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m512divWZ4Q5Ns(short arg0, int other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(65535 & arg0), other);
    }

    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m511divVKZWuLQ(short arg0, long other) {
        return Long.divideUnsigned(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m538rem7apg3OU(short arg0, byte other) {
        return Integer.remainderUnsigned(UInt.m322constructorimpl(65535 & arg0), UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m541remxj2QHRw(short arg0, short other) {
        return Integer.remainderUnsigned(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m540remWZ4Q5Ns(short arg0, int other) {
        return Integer.remainderUnsigned(UInt.m322constructorimpl(65535 & arg0), other);
    }

    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m539remVKZWuLQ(short arg0, long other) {
        return Long.remainderUnsigned(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: floorDiv-7apg3OU, reason: not valid java name */
    private static final int m516floorDiv7apg3OU(short arg0, byte other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(65535 & arg0), UInt.m322constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: floorDiv-xj2QHRw, reason: not valid java name */
    private static final int m519floorDivxj2QHRw(short arg0, short other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: floorDiv-WZ4Q5Ns, reason: not valid java name */
    private static final int m518floorDivWZ4Q5Ns(short arg0, int other) {
        return Integer.divideUnsigned(UInt.m322constructorimpl(65535 & arg0), other);
    }

    /* renamed from: floorDiv-VKZWuLQ, reason: not valid java name */
    private static final long m517floorDivVKZWuLQ(short arg0, long other) {
        return Long.divideUnsigned(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: mod-7apg3OU, reason: not valid java name */
    private static final byte m527mod7apg3OU(short arg0, byte other) {
        return UByte.m245constructorimpl((byte) Integer.remainderUnsigned(UInt.m322constructorimpl(65535 & arg0), UInt.m322constructorimpl(other & UByte.MAX_VALUE)));
    }

    /* renamed from: mod-xj2QHRw, reason: not valid java name */
    private static final short m530modxj2QHRw(short arg0, short other) {
        return m508constructorimpl((short) Integer.remainderUnsigned(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other)));
    }

    /* renamed from: mod-WZ4Q5Ns, reason: not valid java name */
    private static final int m529modWZ4Q5Ns(short arg0, int other) {
        return Integer.remainderUnsigned(UInt.m322constructorimpl(65535 & arg0), other);
    }

    /* renamed from: mod-VKZWuLQ, reason: not valid java name */
    private static final long m528modVKZWuLQ(short arg0, long other) {
        return Long.remainderUnsigned(ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: inc-Mh2AYeg, reason: not valid java name */
    private static final short m521incMh2AYeg(short arg0) {
        return m508constructorimpl((short) (arg0 + 1));
    }

    /* renamed from: dec-Mh2AYeg, reason: not valid java name */
    private static final short m509decMh2AYeg(short arg0) {
        return m508constructorimpl((short) (arg0 - 1));
    }

    /* renamed from: rangeTo-xj2QHRw, reason: not valid java name */
    private static final UIntRange m536rangeToxj2QHRw(short arg0, short other) {
        return new UIntRange(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other), null);
    }

    /* renamed from: rangeUntil-xj2QHRw, reason: not valid java name */
    private static final UIntRange m537rangeUntilxj2QHRw(short arg0, short other) {
        return URangesKt.m1496untilJ1ME1BU(UInt.m322constructorimpl(arg0 & MAX_VALUE), UInt.m322constructorimpl(65535 & other));
    }

    /* renamed from: and-xj2QHRw, reason: not valid java name */
    private static final short m501andxj2QHRw(short arg0, short other) {
        return m508constructorimpl((short) (arg0 & other));
    }

    /* renamed from: or-xj2QHRw, reason: not valid java name */
    private static final short m531orxj2QHRw(short arg0, short other) {
        return m508constructorimpl((short) (arg0 | other));
    }

    /* renamed from: xor-xj2QHRw, reason: not valid java name */
    private static final short m557xorxj2QHRw(short arg0, short other) {
        return m508constructorimpl((short) (arg0 ^ other));
    }

    /* renamed from: inv-Mh2AYeg, reason: not valid java name */
    private static final short m522invMh2AYeg(short arg0) {
        return m508constructorimpl((short) (~arg0));
    }

    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m546toByteimpl(short arg0) {
        return (byte) arg0;
    }

    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m551toShortimpl(short arg0) {
        return arg0;
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m549toIntimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m550toLongimpl(short arg0) {
        return arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    /* renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m553toUBytew2LRezQ(short arg0) {
        return UByte.m245constructorimpl((byte) arg0);
    }

    /* renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m556toUShortMh2AYeg(short arg0) {
        return arg0;
    }

    /* renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m554toUIntpVg5ArA(short arg0) {
        return UInt.m322constructorimpl(65535 & arg0);
    }

    /* renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m555toULongsVKNKU(short arg0) {
        return ULong.m401constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m548toFloatimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m547toDoubleimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m552toStringimpl(short arg0) {
        return String.valueOf(65535 & arg0);
    }

    public String toString() {
        return m552toStringimpl(this.data);
    }
}
