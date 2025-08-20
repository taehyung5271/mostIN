package kotlinx.serialization.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.UShortArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.serialization.ExperimentalSerializationApi;

/* compiled from: PrimitiveArraysSerializers.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0015\u0010\u0012\u001a\u00020\u0002H\u0010ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\bH\u0010¢\u0006\u0002\b\u0017R\u0016\u0010\u0005\u001a\u00020\u0002X\u0082\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0006R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@RX\u0090\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0018"}, d2 = {"Lkotlinx/serialization/internal/UShortArrayBuilder;", "Lkotlinx/serialization/internal/PrimitiveArrayBuilder;", "Lkotlin/UShortArray;", "bufferWithData", "([SLkotlin/jvm/internal/DefaultConstructorMarker;)V", "buffer", "[S", "<set-?>", "", "position", "getPosition$kotlinx_serialization_core", "()I", "append", "", "c", "Lkotlin/UShort;", "append-xj2QHRw$kotlinx_serialization_core", "(S)V", "build", "build-amswpOA$kotlinx_serialization_core", "()[S", "ensureCapacity", "requiredCapacity", "ensureCapacity$kotlinx_serialization_core", "kotlinx-serialization-core"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@ExperimentalSerializationApi
/* loaded from: classes2.dex */
public final class UShortArrayBuilder extends PrimitiveArrayBuilder<UShortArray> {
    private short[] buffer;
    private int position;

    public /* synthetic */ UShortArrayBuilder(short[] sArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(sArr);
    }

    @Override // kotlinx.serialization.internal.PrimitiveArrayBuilder
    public /* bridge */ /* synthetic */ UShortArray build$kotlinx_serialization_core() {
        return UShortArray.m559boximpl(m1823buildamswpOA$kotlinx_serialization_core());
    }

    private UShortArrayBuilder(short[] bufferWithData) {
        Intrinsics.checkNotNullParameter(bufferWithData, "bufferWithData");
        this.buffer = bufferWithData;
        this.position = UShortArray.m567getSizeimpl(bufferWithData);
        ensureCapacity$kotlinx_serialization_core(10);
    }

    @Override // kotlinx.serialization.internal.PrimitiveArrayBuilder
    /* renamed from: getPosition$kotlinx_serialization_core, reason: from getter */
    public int getPosition() {
        return this.position;
    }

    @Override // kotlinx.serialization.internal.PrimitiveArrayBuilder
    public void ensureCapacity$kotlinx_serialization_core(int requiredCapacity) {
        if (UShortArray.m567getSizeimpl(this.buffer) < requiredCapacity) {
            short[] sArrCopyOf = Arrays.copyOf(this.buffer, RangesKt.coerceAtLeast(requiredCapacity, UShortArray.m567getSizeimpl(this.buffer) * 2));
            Intrinsics.checkNotNullExpressionValue(sArrCopyOf, "copyOf(...)");
            this.buffer = UShortArray.m561constructorimpl(sArrCopyOf);
        }
    }

    /* renamed from: append-xj2QHRw$kotlinx_serialization_core, reason: not valid java name */
    public final void m1822appendxj2QHRw$kotlinx_serialization_core(short c) {
        PrimitiveArrayBuilder.ensureCapacity$kotlinx_serialization_core$default(this, 0, 1, null);
        short[] sArr = this.buffer;
        int position = getPosition();
        this.position = position + 1;
        UShortArray.m571set01HTLdE(sArr, position, c);
    }

    /* renamed from: build-amswpOA$kotlinx_serialization_core, reason: not valid java name */
    public short[] m1823buildamswpOA$kotlinx_serialization_core() {
        short[] sArrCopyOf = Arrays.copyOf(this.buffer, getPosition());
        Intrinsics.checkNotNullExpressionValue(sArrCopyOf, "copyOf(...)");
        return UShortArray.m561constructorimpl(sArrCopyOf);
    }
}
