package retrofit2;

import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/* loaded from: classes2.dex */
class Platform {
    private static final Platform PLATFORM = findPlatform();
    private final boolean hasJava8Types;

    @Nullable
    private final Constructor<MethodHandles.Lookup> lookupConstructor;

    static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            return new Android();
        }
        return new Platform(true);
    }

    Platform(boolean hasJava8Types) throws NoSuchMethodException, SecurityException {
        this.hasJava8Types = hasJava8Types;
        Constructor<MethodHandles.Lookup> lookupConstructor = null;
        if (hasJava8Types) {
            try {
                lookupConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                lookupConstructor.setAccessible(true);
            } catch (NoClassDefFoundError e) {
            } catch (NoSuchMethodException e2) {
            }
        }
        this.lookupConstructor = lookupConstructor;
    }

    @Nullable
    Executor defaultCallbackExecutor() {
        return null;
    }

    List<? extends CallAdapter.Factory> defaultCallAdapterFactories(@Nullable Executor callbackExecutor) {
        DefaultCallAdapterFactory executorFactory = new DefaultCallAdapterFactory(callbackExecutor);
        return this.hasJava8Types ? Arrays.asList(CompletableFutureCallAdapterFactory.INSTANCE, executorFactory) : Collections.singletonList(executorFactory);
    }

    int defaultCallAdapterFactoriesSize() {
        return this.hasJava8Types ? 2 : 1;
    }

    List<? extends Converter.Factory> defaultConverterFactories() {
        return this.hasJava8Types ? Collections.singletonList(OptionalConverterFactory.INSTANCE) : Collections.emptyList();
    }

    int defaultConverterFactoriesSize() {
        return this.hasJava8Types ? 1 : 0;
    }

    boolean isDefaultMethod(Method method) {
        return this.hasJava8Types && method.isDefault();
    }

    @Nullable
    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object, Object... args) throws Throwable {
        MethodHandles.Lookup lookup;
        if (this.lookupConstructor != null) {
            lookup = this.lookupConstructor.newInstance(declaringClass, -1);
        } else {
            lookup = MethodHandles.lookup();
        }
        return lookup.unreflectSpecial(method, declaringClass).bindTo(object).invokeWithArguments(args);
    }

    static final class Android extends Platform {
        Android() {
            super(true);
        }

        @Override // retrofit2.Platform
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        @Override // retrofit2.Platform
        @Nullable
        Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object, Object... args) throws Throwable {
            return super.invokeDefaultMethod(method, declaringClass, object, args);
        }

        static final class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            MainThreadExecutor() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable r) {
                this.handler.post(r);
            }
        }
    }
}
