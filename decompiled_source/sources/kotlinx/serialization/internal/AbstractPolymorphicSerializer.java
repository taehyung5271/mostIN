package kotlinx.serialization.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.reflect.KClass;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.InternalSerializationApi;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.PolymorphicSerializerKt;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;

/* compiled from: AbstractPolymorphicSerializer.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b'\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0000¢\u0006\u0002\u0010\u0004J\u0015\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0002¢\u0006\u0002\u0010\fJ\u0013\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0017J%\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000H\u0017¢\u0006\u0002\u0010\u0019J\u001b\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cR\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u001d"}, d2 = {"Lkotlinx/serialization/internal/AbstractPolymorphicSerializer;", "T", "", "Lkotlinx/serialization/KSerializer;", "()V", "baseClass", "Lkotlin/reflect/KClass;", "getBaseClass", "()Lkotlin/reflect/KClass;", "decodeSequentially", "compositeDecoder", "Lkotlinx/serialization/encoding/CompositeDecoder;", "(Lkotlinx/serialization/encoding/CompositeDecoder;)Ljava/lang/Object;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "(Lkotlinx/serialization/encoding/Decoder;)Ljava/lang/Object;", "findPolymorphicSerializerOrNull", "Lkotlinx/serialization/DeserializationStrategy;", "klassName", "", "Lkotlinx/serialization/SerializationStrategy;", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "(Lkotlinx/serialization/encoding/Encoder;Ljava/lang/Object;)Lkotlinx/serialization/SerializationStrategy;", "serialize", "", "(Lkotlinx/serialization/encoding/Encoder;Ljava/lang/Object;)V", "kotlinx-serialization-core"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@InternalSerializationApi
/* loaded from: classes2.dex */
public abstract class AbstractPolymorphicSerializer<T> implements KSerializer<T> {
    public abstract KClass<T> getBaseClass();

    @Override // kotlinx.serialization.SerializationStrategy
    public final void serialize(Encoder encoder, T value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        SerializationStrategy actualSerializer = PolymorphicSerializerKt.findPolymorphicSerializer(this, encoder, value);
        SerialDescriptor descriptor$iv = getDescriptor();
        CompositeEncoder composite$iv = encoder.beginStructure(descriptor$iv);
        composite$iv.encodeStringElement(getDescriptor(), 0, actualSerializer.getDescriptor().getSerialName());
        SerialDescriptor descriptor = getDescriptor();
        Intrinsics.checkNotNull(actualSerializer, "null cannot be cast to non-null type kotlinx.serialization.SerializationStrategy<T of kotlinx.serialization.internal.Platform_commonKt.cast>");
        composite$iv.encodeSerializableElement(descriptor, 1, actualSerializer, value);
        composite$iv.endStructure(descriptor$iv);
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    public final T deserialize(Decoder decoder) {
        Object objDecodeSequentially;
        AbstractPolymorphicSerializer<T> abstractPolymorphicSerializer = this;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor descriptor = getDescriptor();
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(descriptor);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            objDecodeSequentially = abstractPolymorphicSerializer.decodeSequentially(compositeDecoderBeginStructure);
        } else {
            Object objDecodeSerializableElement$default = null;
            while (true) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(getDescriptor());
                switch (iDecodeElementIndex) {
                    case -1:
                        Object obj = objDecodeSerializableElement$default;
                        if (obj == null) {
                            throw new IllegalArgumentException(("Polymorphic value has not been read for class " + ((String) objectRef.element)).toString());
                        }
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of kotlinx.serialization.internal.AbstractPolymorphicSerializer.deserialize$lambda$3");
                        objDecodeSequentially = obj;
                        break;
                    case 0:
                        objectRef.element = (T) compositeDecoderBeginStructure.decodeStringElement(getDescriptor(), iDecodeElementIndex);
                        abstractPolymorphicSerializer = this;
                        continue;
                    case 1:
                        T t = objectRef.element;
                        if (t == null) {
                            throw new IllegalArgumentException("Cannot read polymorphic value before its type token".toString());
                        }
                        objectRef.element = t;
                        objDecodeSerializableElement$default = CompositeDecoder.DefaultImpls.decodeSerializableElement$default(compositeDecoderBeginStructure, getDescriptor(), iDecodeElementIndex, PolymorphicSerializerKt.findPolymorphicSerializer(abstractPolymorphicSerializer, compositeDecoderBeginStructure, (String) objectRef.element), null, 8, null);
                        abstractPolymorphicSerializer = this;
                        continue;
                    default:
                        StringBuilder sbAppend = new StringBuilder().append("Invalid index in polymorphic deserialization of ");
                        String str = (String) objectRef.element;
                        if (str == null) {
                            str = "unknown class";
                        }
                        throw new SerializationException(sbAppend.append(str).append("\n Expected 0, 1 or DECODE_DONE(-1), but found ").append(iDecodeElementIndex).toString());
                }
            }
        }
        T t2 = (T) objDecodeSequentially;
        compositeDecoderBeginStructure.endStructure(descriptor);
        return t2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final T decodeSequentially(CompositeDecoder compositeDecoder) {
        return (T) CompositeDecoder.DefaultImpls.decodeSerializableElement$default(compositeDecoder, getDescriptor(), 1, PolymorphicSerializerKt.findPolymorphicSerializer(this, compositeDecoder, compositeDecoder.decodeStringElement(getDescriptor(), 0)), null, 8, null);
    }

    @InternalSerializationApi
    public DeserializationStrategy<T> findPolymorphicSerializerOrNull(CompositeDecoder decoder, String klassName) {
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        return decoder.getSerializersModule().getPolymorphic((KClass) getBaseClass(), klassName);
    }

    @InternalSerializationApi
    public SerializationStrategy<T> findPolymorphicSerializerOrNull(Encoder encoder, T value) {
        Intrinsics.checkNotNullParameter(encoder, "encoder");
        Intrinsics.checkNotNullParameter(value, "value");
        return encoder.getSerializersModule().getPolymorphic((KClass<? super KClass<T>>) getBaseClass(), (KClass<T>) value);
    }
}
