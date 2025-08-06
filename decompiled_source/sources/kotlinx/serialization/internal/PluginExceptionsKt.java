package kotlinx.serialization.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.InternalSerializationApi;
import kotlinx.serialization.MissingFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;

/* compiled from: PluginExceptions.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a \u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000b"}, d2 = {"throwArrayMissingFieldException", "", "seenArray", "", "goldenMaskArray", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "throwMissingFieldException", "seen", "", "goldenMask", "kotlinx-serialization-core"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes2.dex */
public final class PluginExceptionsKt {
    @InternalSerializationApi
    public static final void throwMissingFieldException(int seen, int goldenMask, SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List missingFields = new ArrayList();
        int missingFieldsBits = (~seen) & goldenMask;
        for (int i = 0; i < 32; i++) {
            if ((missingFieldsBits & 1) != 0) {
                missingFields.add(descriptor.getElementName(i));
            }
            missingFieldsBits >>>= 1;
        }
        throw new MissingFieldException((List<String>) missingFields, descriptor.getSerialName());
    }

    @InternalSerializationApi
    public static final void throwArrayMissingFieldException(int[] seenArray, int[] goldenMaskArray, SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(seenArray, "seenArray");
        Intrinsics.checkNotNullParameter(goldenMaskArray, "goldenMaskArray");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List missingFields = new ArrayList();
        int length = goldenMaskArray.length;
        for (int maskSlot = 0; maskSlot < length; maskSlot++) {
            int missingFieldsBits = goldenMaskArray[maskSlot] & (~seenArray[maskSlot]);
            if (missingFieldsBits != 0) {
                for (int i = 0; i < 32; i++) {
                    if ((missingFieldsBits & 1) != 0) {
                        missingFields.add(descriptor.getElementName((maskSlot * 32) + i));
                    }
                    missingFieldsBits >>>= 1;
                }
            }
        }
        throw new MissingFieldException((List<String>) missingFields, descriptor.getSerialName());
    }
}
