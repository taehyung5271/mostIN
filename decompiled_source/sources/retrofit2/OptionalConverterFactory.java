package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* loaded from: classes2.dex */
final class OptionalConverterFactory extends Converter.Factory {
    static final Converter.Factory INSTANCE = new OptionalConverterFactory();

    OptionalConverterFactory() {
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(type) != Optional.class) {
            return null;
        }
        Type innerType = getParameterUpperBound(0, (ParameterizedType) type);
        Converter<ResponseBody, Object> delegate = retrofit.responseBodyConverter(innerType, annotations);
        return new OptionalConverter(delegate);
    }

    static final class OptionalConverter<T> implements Converter<ResponseBody, Optional<T>> {
        final Converter<ResponseBody, T> delegate;

        OptionalConverter(Converter<ResponseBody, T> delegate) {
            this.delegate = delegate;
        }

        @Override // retrofit2.Converter
        public Optional<T> convert(ResponseBody value) throws IOException {
            return Optional.ofNullable(this.delegate.convert(value));
        }
    }
}
