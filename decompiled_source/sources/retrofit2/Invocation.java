package retrofit2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class Invocation {
    private final List<?> arguments;
    private final Method method;

    public static Invocation of(Method method, List<?> arguments) {
        Objects.requireNonNull(method, "method == null");
        Objects.requireNonNull(arguments, "arguments == null");
        return new Invocation(method, new ArrayList(arguments));
    }

    Invocation(Method method, List<?> arguments) {
        this.method = method;
        this.arguments = Collections.unmodifiableList(arguments);
    }

    public Method method() {
        return this.method;
    }

    public List<?> arguments() {
        return this.arguments;
    }

    public String toString() {
        return String.format("%s.%s() %s", this.method.getDeclaringClass().getName(), this.method.getName(), this.arguments);
    }
}
