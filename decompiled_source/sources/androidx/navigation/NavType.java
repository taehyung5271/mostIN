package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import okhttp3.HttpUrl;

/* compiled from: NavType.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u000e\b&\u0018\u0000 !*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0006!\"#$%&B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J \u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH¦\u0002¢\u0006\u0002\u0010\u000fJ%\u0010\u0010\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0012J/\u0010\u0010\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00028\u0000H\u0007¢\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\bH&¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0017J%\u0010\u0018\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\bH\u0016J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010 R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006'"}, d2 = {"Landroidx/navigation/NavType;", "T", "", "isNullableAllowed", "", "(Z)V", "()Z", "name", "", "getName", "()Ljava/lang/String;", "get", "bundle", "Landroid/os/Bundle;", "key", "(Landroid/os/Bundle;Ljava/lang/String;)Ljava/lang/Object;", "parseAndPut", "value", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", "previousValue", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "parseValue", "(Ljava/lang/String;)Ljava/lang/Object;", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "put", "", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/Object;)V", "serializeAsValue", "(Ljava/lang/Object;)Ljava/lang/String;", "toString", "valueEquals", "other", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "Companion", "EnumType", "ParcelableArrayType", "ParcelableType", "SerializableArrayType", "SerializableType", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public abstract class NavType<T> {
    private final boolean isNullableAllowed;
    private final String name = "nav_type";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final NavType<Integer> IntType = new NavType<Integer>() { // from class: androidx.navigation.NavType$Companion$IntType$1
        @Override // androidx.navigation.NavType
        public /* bridge */ /* synthetic */ void put(Bundle bundle, String key, Integer num) {
            put(bundle, key, num.intValue());
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return TypedValues.Custom.S_INT;
        }

        public void put(Bundle bundle, String key, int value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putInt(key, value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Integer get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = bundle.get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            return (Integer) obj;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Integer parseValue(String value) throws NumberFormatException {
            int i;
            Intrinsics.checkNotNullParameter(value, "value");
            if (StringsKt.startsWith$default(value, "0x", false, 2, (Object) null)) {
                String strSubstring = value.substring(2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                i = Integer.parseInt(strSubstring, CharsKt.checkRadix(16));
            } else {
                i = Integer.parseInt(value);
            }
            return Integer.valueOf(i);
        }
    };
    public static final NavType<Integer> ReferenceType = new NavType<Integer>() { // from class: androidx.navigation.NavType$Companion$ReferenceType$1
        @Override // androidx.navigation.NavType
        public /* bridge */ /* synthetic */ void put(Bundle bundle, String key, Integer num) {
            put(bundle, key, num.intValue());
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "reference";
        }

        public void put(Bundle bundle, String key, int value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putInt(key, value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Integer get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = bundle.get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            return (Integer) obj;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Integer parseValue(String value) throws NumberFormatException {
            int i;
            Intrinsics.checkNotNullParameter(value, "value");
            if (StringsKt.startsWith$default(value, "0x", false, 2, (Object) null)) {
                String strSubstring = value.substring(2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                i = Integer.parseInt(strSubstring, CharsKt.checkRadix(16));
            } else {
                i = Integer.parseInt(value);
            }
            return Integer.valueOf(i);
        }
    };
    public static final NavType<int[]> IntArrayType = new CollectionNavType<int[]>() { // from class: androidx.navigation.NavType$Companion$IntArrayType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return "integer[]";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, int[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putIntArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public int[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (int[]) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public int[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new int[]{NavType.IntType.parseValue(value).intValue()};
        }

        @Override // androidx.navigation.NavType
        public int[] parseValue(String value, int[] previousValue) {
            int[] iArrPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (iArrPlus = ArraysKt.plus(previousValue, parseValue(value))) == null) ? parseValue(value) : iArrPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(int[] value, int[] other) {
            Integer[] valueArray = value != null ? ArraysKt.toTypedArray(value) : null;
            Integer[] otherArray = other != null ? ArraysKt.toTypedArray(other) : null;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        @Override // androidx.navigation.CollectionNavType
        public List<String> serializeAsValues(int[] value) {
            Iterable list;
            if (value == null || (list = ArraysKt.toList(value)) == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = list;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                int it = ((Number) item$iv$iv).intValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public int[] emptyCollection() {
            return new int[0];
        }
    };
    public static final NavType<List<Integer>> IntListType = new CollectionNavType<List<? extends Integer>>() { // from class: androidx.navigation.NavType$Companion$IntListType$1
        @Override // androidx.navigation.CollectionNavType
        public /* bridge */ /* synthetic */ List serializeAsValues(List<? extends Integer> list) {
            return serializeAsValues2((List<Integer>) list);
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "List<Int>";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, List<Integer> value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putIntArray(key, value != null ? CollectionsKt.toIntArray(value) : null);
        }

        @Override // androidx.navigation.NavType
        public List<Integer> get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            int[] iArr = (int[]) bundle.get(key);
            if (iArr != null) {
                return ArraysKt.toList(iArr);
            }
            return null;
        }

        @Override // androidx.navigation.NavType
        public List<Integer> parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return CollectionsKt.listOf(NavType.IntType.parseValue(value));
        }

        @Override // androidx.navigation.NavType
        public List<Integer> parseValue(String value, List<Integer> previousValue) {
            List<Integer> listPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (listPlus = CollectionsKt.plus((Collection) previousValue, (Iterable) parseValue(value))) == null) ? parseValue(value) : listPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(List<Integer> value, List<Integer> other) {
            Integer[] valueArray;
            Integer[] numArr = null;
            if (value == null) {
                valueArray = null;
            } else {
                List<Integer> $this$toTypedArray$iv = value;
                valueArray = (Integer[]) $this$toTypedArray$iv.toArray(new Integer[0]);
            }
            if (other != null) {
                List<Integer> $this$toTypedArray$iv2 = other;
                numArr = (Integer[]) $this$toTypedArray$iv2.toArray(new Integer[0]);
            }
            Integer[] otherArray = numArr;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        /* renamed from: serializeAsValues, reason: avoid collision after fix types in other method */
        public List<String> serializeAsValues2(List<Integer> value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            List<Integer> $this$map$iv = value;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                int it = ((Number) item$iv$iv).intValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public List<? extends Integer> emptyCollection() {
            return CollectionsKt.emptyList();
        }
    };
    public static final NavType<Long> LongType = new NavType<Long>() { // from class: androidx.navigation.NavType$Companion$LongType$1
        @Override // androidx.navigation.NavType
        public /* bridge */ /* synthetic */ void put(Bundle bundle, String key, Long l) {
            put(bundle, key, l.longValue());
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "long";
        }

        public void put(Bundle bundle, String key, long value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putLong(key, value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Long get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = bundle.get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Long");
            return (Long) obj;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Long parseValue(String value) throws NumberFormatException {
            long j;
            Intrinsics.checkNotNullParameter(value, "value");
            String localValue = value;
            if (StringsKt.endsWith$default(value, "L", false, 2, (Object) null)) {
                String strSubstring = localValue.substring(0, value.length() - 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                localValue = strSubstring;
            }
            if (StringsKt.startsWith$default(value, "0x", false, 2, (Object) null)) {
                String strSubstring2 = localValue.substring(2);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                j = Long.parseLong(strSubstring2, CharsKt.checkRadix(16));
            } else {
                j = Long.parseLong(localValue);
            }
            return Long.valueOf(j);
        }
    };
    public static final NavType<long[]> LongArrayType = new CollectionNavType<long[]>() { // from class: androidx.navigation.NavType$Companion$LongArrayType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return "long[]";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, long[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putLongArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public long[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (long[]) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public long[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new long[]{NavType.LongType.parseValue(value).longValue()};
        }

        @Override // androidx.navigation.NavType
        public long[] parseValue(String value, long[] previousValue) {
            long[] jArrPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (jArrPlus = ArraysKt.plus(previousValue, parseValue(value))) == null) ? parseValue(value) : jArrPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(long[] value, long[] other) {
            Long[] valueArray = value != null ? ArraysKt.toTypedArray(value) : null;
            Long[] otherArray = other != null ? ArraysKt.toTypedArray(other) : null;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        @Override // androidx.navigation.CollectionNavType
        public List<String> serializeAsValues(long[] value) {
            Iterable list;
            if (value == null || (list = ArraysKt.toList(value)) == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = list;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                long it = ((Number) item$iv$iv).longValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public long[] emptyCollection() {
            return new long[0];
        }
    };
    public static final NavType<List<Long>> LongListType = new CollectionNavType<List<? extends Long>>() { // from class: androidx.navigation.NavType$Companion$LongListType$1
        @Override // androidx.navigation.CollectionNavType
        public /* bridge */ /* synthetic */ List serializeAsValues(List<? extends Long> list) {
            return serializeAsValues2((List<Long>) list);
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "List<Long>";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, List<Long> value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putLongArray(key, value != null ? CollectionsKt.toLongArray(value) : null);
        }

        @Override // androidx.navigation.NavType
        public List<Long> get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            long[] jArr = (long[]) bundle.get(key);
            if (jArr != null) {
                return ArraysKt.toList(jArr);
            }
            return null;
        }

        @Override // androidx.navigation.NavType
        public List<Long> parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return CollectionsKt.listOf(NavType.LongType.parseValue(value));
        }

        @Override // androidx.navigation.NavType
        public List<Long> parseValue(String value, List<Long> previousValue) {
            List<Long> listPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (listPlus = CollectionsKt.plus((Collection) previousValue, (Iterable) parseValue(value))) == null) ? parseValue(value) : listPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(List<Long> value, List<Long> other) {
            Long[] valueArray;
            Long[] lArr = null;
            if (value == null) {
                valueArray = null;
            } else {
                List<Long> $this$toTypedArray$iv = value;
                valueArray = (Long[]) $this$toTypedArray$iv.toArray(new Long[0]);
            }
            if (other != null) {
                List<Long> $this$toTypedArray$iv2 = other;
                lArr = (Long[]) $this$toTypedArray$iv2.toArray(new Long[0]);
            }
            Long[] otherArray = lArr;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        /* renamed from: serializeAsValues, reason: avoid collision after fix types in other method */
        public List<String> serializeAsValues2(List<Long> value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            List<Long> $this$map$iv = value;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                long it = ((Number) item$iv$iv).longValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public List<? extends Long> emptyCollection() {
            return CollectionsKt.emptyList();
        }
    };
    public static final NavType<Float> FloatType = new NavType<Float>() { // from class: androidx.navigation.NavType$Companion$FloatType$1
        @Override // androidx.navigation.NavType
        public /* bridge */ /* synthetic */ void put(Bundle bundle, String key, Float f) {
            put(bundle, key, f.floatValue());
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return TypedValues.Custom.S_FLOAT;
        }

        public void put(Bundle bundle, String key, float value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putFloat(key, value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Float get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            Object obj = bundle.get(key);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Float");
            return (Float) obj;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Float parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return Float.valueOf(Float.parseFloat(value));
        }
    };
    public static final NavType<float[]> FloatArrayType = new CollectionNavType<float[]>() { // from class: androidx.navigation.NavType$Companion$FloatArrayType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return "float[]";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, float[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putFloatArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public float[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (float[]) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public float[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new float[]{NavType.FloatType.parseValue(value).floatValue()};
        }

        @Override // androidx.navigation.NavType
        public float[] parseValue(String value, float[] previousValue) {
            float[] fArrPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (fArrPlus = ArraysKt.plus(previousValue, parseValue(value))) == null) ? parseValue(value) : fArrPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(float[] value, float[] other) {
            Float[] valueArray = value != null ? ArraysKt.toTypedArray(value) : null;
            Float[] otherArray = other != null ? ArraysKt.toTypedArray(other) : null;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        @Override // androidx.navigation.CollectionNavType
        public List<String> serializeAsValues(float[] value) {
            Iterable list;
            if (value == null || (list = ArraysKt.toList(value)) == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = list;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                float it = ((Number) item$iv$iv).floatValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public float[] emptyCollection() {
            return new float[0];
        }
    };
    public static final NavType<List<Float>> FloatListType = new CollectionNavType<List<? extends Float>>() { // from class: androidx.navigation.NavType$Companion$FloatListType$1
        @Override // androidx.navigation.CollectionNavType
        public /* bridge */ /* synthetic */ List serializeAsValues(List<? extends Float> list) {
            return serializeAsValues2((List<Float>) list);
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "List<Float>";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, List<Float> value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putFloatArray(key, value != null ? CollectionsKt.toFloatArray(value) : null);
        }

        @Override // androidx.navigation.NavType
        public List<Float> get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            float[] fArr = (float[]) bundle.get(key);
            if (fArr != null) {
                return ArraysKt.toList(fArr);
            }
            return null;
        }

        @Override // androidx.navigation.NavType
        public List<Float> parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return CollectionsKt.listOf(NavType.FloatType.parseValue(value));
        }

        @Override // androidx.navigation.NavType
        public List<Float> parseValue(String value, List<Float> previousValue) {
            List<Float> listPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (listPlus = CollectionsKt.plus((Collection) previousValue, (Iterable) parseValue(value))) == null) ? parseValue(value) : listPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(List<Float> value, List<Float> other) {
            Float[] valueArray;
            Float[] fArr = null;
            if (value == null) {
                valueArray = null;
            } else {
                List<Float> $this$toTypedArray$iv = value;
                valueArray = (Float[]) $this$toTypedArray$iv.toArray(new Float[0]);
            }
            if (other != null) {
                List<Float> $this$toTypedArray$iv2 = other;
                fArr = (Float[]) $this$toTypedArray$iv2.toArray(new Float[0]);
            }
            Float[] otherArray = fArr;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        /* renamed from: serializeAsValues, reason: avoid collision after fix types in other method */
        public List<String> serializeAsValues2(List<Float> value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            List<Float> $this$map$iv = value;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                float it = ((Number) item$iv$iv).floatValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public List<? extends Float> emptyCollection() {
            return CollectionsKt.emptyList();
        }
    };
    public static final NavType<Boolean> BoolType = new NavType<Boolean>() { // from class: androidx.navigation.NavType$Companion$BoolType$1
        @Override // androidx.navigation.NavType
        public /* bridge */ /* synthetic */ void put(Bundle bundle, String key, Boolean bool) {
            put(bundle, key, bool.booleanValue());
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return TypedValues.Custom.S_BOOLEAN;
        }

        public void put(Bundle bundle, String key, boolean value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putBoolean(key, value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Boolean get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (Boolean) bundle.get(key);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.navigation.NavType
        public Boolean parseValue(String value) {
            boolean z;
            Intrinsics.checkNotNullParameter(value, "value");
            if (Intrinsics.areEqual(value, "true")) {
                z = true;
            } else {
                if (!Intrinsics.areEqual(value, "false")) {
                    throw new IllegalArgumentException("A boolean NavType only accepts \"true\" or \"false\" values.");
                }
                z = false;
            }
            return Boolean.valueOf(z);
        }
    };
    public static final NavType<boolean[]> BoolArrayType = new CollectionNavType<boolean[]>() { // from class: androidx.navigation.NavType$Companion$BoolArrayType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return "boolean[]";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, boolean[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putBooleanArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public boolean[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (boolean[]) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public boolean[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new boolean[]{NavType.BoolType.parseValue(value).booleanValue()};
        }

        @Override // androidx.navigation.NavType
        public boolean[] parseValue(String value, boolean[] previousValue) {
            boolean[] zArrPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (zArrPlus = ArraysKt.plus(previousValue, parseValue(value))) == null) ? parseValue(value) : zArrPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(boolean[] value, boolean[] other) {
            Boolean[] valueArray = value != null ? ArraysKt.toTypedArray(value) : null;
            Boolean[] otherArray = other != null ? ArraysKt.toTypedArray(other) : null;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        @Override // androidx.navigation.CollectionNavType
        public List<String> serializeAsValues(boolean[] value) {
            Iterable list;
            if (value == null || (list = ArraysKt.toList(value)) == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = list;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                boolean it = ((Boolean) item$iv$iv).booleanValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public boolean[] emptyCollection() {
            return new boolean[0];
        }
    };
    public static final NavType<List<Boolean>> BoolListType = new CollectionNavType<List<? extends Boolean>>() { // from class: androidx.navigation.NavType$Companion$BoolListType$1
        @Override // androidx.navigation.CollectionNavType
        public /* bridge */ /* synthetic */ List serializeAsValues(List<? extends Boolean> list) {
            return serializeAsValues2((List<Boolean>) list);
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "List<Boolean>";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, List<Boolean> value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putBooleanArray(key, value != null ? CollectionsKt.toBooleanArray(value) : null);
        }

        @Override // androidx.navigation.NavType
        public List<Boolean> get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            boolean[] zArr = (boolean[]) bundle.get(key);
            if (zArr != null) {
                return ArraysKt.toList(zArr);
            }
            return null;
        }

        @Override // androidx.navigation.NavType
        public List<Boolean> parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return CollectionsKt.listOf(NavType.BoolType.parseValue(value));
        }

        @Override // androidx.navigation.NavType
        public List<Boolean> parseValue(String value, List<Boolean> previousValue) {
            List<Boolean> listPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (listPlus = CollectionsKt.plus((Collection) previousValue, (Iterable) parseValue(value))) == null) ? parseValue(value) : listPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(List<Boolean> value, List<Boolean> other) {
            Boolean[] valueArray;
            Boolean[] boolArr = null;
            if (value == null) {
                valueArray = null;
            } else {
                List<Boolean> $this$toTypedArray$iv = value;
                valueArray = (Boolean[]) $this$toTypedArray$iv.toArray(new Boolean[0]);
            }
            if (other != null) {
                List<Boolean> $this$toTypedArray$iv2 = other;
                boolArr = (Boolean[]) $this$toTypedArray$iv2.toArray(new Boolean[0]);
            }
            Boolean[] otherArray = boolArr;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        /* renamed from: serializeAsValues, reason: avoid collision after fix types in other method */
        public List<String> serializeAsValues2(List<Boolean> value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            List<Boolean> $this$map$iv = value;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                boolean it = ((Boolean) item$iv$iv).booleanValue();
                destination$iv$iv.add(String.valueOf(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public List<? extends Boolean> emptyCollection() {
            return CollectionsKt.emptyList();
        }
    };
    public static final NavType<String> StringType = new NavType<String>() { // from class: androidx.navigation.NavType$Companion$StringType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return TypedValues.Custom.S_STRING;
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, String value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putString(key, value);
        }

        @Override // androidx.navigation.NavType
        public String get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (String) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public String parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            if (Intrinsics.areEqual(value, "null")) {
                return null;
            }
            return value;
        }

        @Override // androidx.navigation.NavType
        public String serializeAsValue(String value) {
            String it = value != null ? Uri.encode(value) : null;
            return it == null ? "null" : it;
        }
    };
    public static final NavType<String[]> StringArrayType = new CollectionNavType<String[]>() { // from class: androidx.navigation.NavType$Companion$StringArrayType$1
        @Override // androidx.navigation.NavType
        public String getName() {
            return "string[]";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, String[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            bundle.putStringArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public String[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (String[]) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public String[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new String[]{value};
        }

        @Override // androidx.navigation.NavType
        public String[] parseValue(String value, String[] previousValue) {
            String[] strArr;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (strArr = (String[]) ArraysKt.plus((Object[]) previousValue, (Object[]) parseValue(value))) == null) ? parseValue(value) : strArr;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(String[] value, String[] other) {
            return ArraysKt.contentDeepEquals(value, other);
        }

        @Override // androidx.navigation.CollectionNavType
        public List<String> serializeAsValues(String[] value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            Collection destination$iv$iv = new ArrayList(value.length);
            for (String str : value) {
                String it = Uri.encode(str);
                destination$iv$iv.add(it);
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public String[] emptyCollection() {
            return new String[0];
        }
    };
    public static final NavType<List<String>> StringListType = new CollectionNavType<List<? extends String>>() { // from class: androidx.navigation.NavType$Companion$StringListType$1
        @Override // androidx.navigation.CollectionNavType
        public /* bridge */ /* synthetic */ List serializeAsValues(List<? extends String> list) {
            return serializeAsValues2((List<String>) list);
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            return "List<String>";
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, List<String> value) {
            String[] strArr;
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            if (value == null) {
                strArr = null;
            } else {
                List<String> $this$toTypedArray$iv = value;
                strArr = (String[]) $this$toTypedArray$iv.toArray(new String[0]);
            }
            bundle.putStringArray(key, strArr);
        }

        @Override // androidx.navigation.NavType
        public List<String> get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            String[] strArr = (String[]) bundle.get(key);
            if (strArr != null) {
                return ArraysKt.toList(strArr);
            }
            return null;
        }

        @Override // androidx.navigation.NavType
        public List<String> parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return CollectionsKt.listOf(value);
        }

        @Override // androidx.navigation.NavType
        public List<String> parseValue(String value, List<String> previousValue) {
            List<String> listPlus;
            Intrinsics.checkNotNullParameter(value, "value");
            return (previousValue == null || (listPlus = CollectionsKt.plus((Collection) previousValue, (Iterable) parseValue(value))) == null) ? parseValue(value) : listPlus;
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(List<String> value, List<String> other) {
            String[] valueArray;
            String[] strArr = null;
            if (value == null) {
                valueArray = null;
            } else {
                List<String> $this$toTypedArray$iv = value;
                valueArray = (String[]) $this$toTypedArray$iv.toArray(new String[0]);
            }
            if (other != null) {
                List<String> $this$toTypedArray$iv2 = other;
                strArr = (String[]) $this$toTypedArray$iv2.toArray(new String[0]);
            }
            String[] otherArray = strArr;
            return ArraysKt.contentDeepEquals(valueArray, otherArray);
        }

        /* renamed from: serializeAsValues, reason: avoid collision after fix types in other method */
        public List<String> serializeAsValues2(List<String> value) {
            if (value == null) {
                return CollectionsKt.emptyList();
            }
            List<String> $this$map$iv = value;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                destination$iv$iv.add(Uri.encode(it));
            }
            return (List) destination$iv$iv;
        }

        @Override // androidx.navigation.CollectionNavType
        public List<? extends String> emptyCollection() {
            return CollectionsKt.emptyList();
        }
    };

    public abstract T get(Bundle bundle, String key);

    public abstract T parseValue(String value);

    public abstract void put(Bundle bundle, String key, T value);

    public NavType(boolean isNullableAllowed) {
        this.isNullableAllowed = isNullableAllowed;
    }

    /* renamed from: isNullableAllowed, reason: from getter */
    public boolean getIsNullableAllowed() {
        return this.isNullableAllowed;
    }

    public T parseValue(String value, T previousValue) {
        Intrinsics.checkNotNullParameter(value, "value");
        return parseValue(value);
    }

    public final T parseAndPut(Bundle bundle, String key, String value) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        T value2 = parseValue(value);
        put(bundle, key, value2);
        return value2;
    }

    public final T parseAndPut(Bundle bundle, String key, String value, T previousValue) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Intrinsics.checkNotNullParameter(key, "key");
        if (!bundle.containsKey(key)) {
            throw new IllegalArgumentException("There is no previous value in this bundle.");
        }
        if (value != null) {
            T value2 = parseValue(value, previousValue);
            put(bundle, key, value2);
            return value2;
        }
        return previousValue;
    }

    public String serializeAsValue(T value) {
        return String.valueOf(value);
    }

    public String getName() {
        return this.name;
    }

    public boolean valueEquals(T value, T other) {
        return Intrinsics.areEqual(value, other);
    }

    public String toString() {
        return getName();
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u0010 \u001a\u0004\u0018\u00010\u001c2\b\u0010!\u001a\u0004\u0018\u00010\u001cH\u0017J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\u0006\u0010#\u001a\u00020\u001cH\u0007J\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u0007J'\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\n\u0010&\u001a\u0006\u0012\u0002\b\u00030'2\u0006\u0010(\u001a\u00020\bH\u0000¢\u0006\u0002\b)R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00120\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u001d\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u00070\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Landroidx/navigation/NavType$Companion;", "", "()V", "BoolArrayType", "Landroidx/navigation/NavType;", "", "BoolListType", "", "", "BoolType", "FloatArrayType", "", "FloatListType", "", "FloatType", "IntArrayType", "", "IntListType", "", "IntType", "LongArrayType", "", "LongListType", "", "LongType", "ReferenceType", "StringArrayType", "", "", "StringListType", "StringType", "fromArgType", "type", "packageName", "inferFromValue", "value", "inferFromValueType", "parseSerializableOrParcelableType", "clazz", "Ljava/lang/Class;", "isArray", "parseSerializableOrParcelableType$navigation_common_release", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public NavType<?> fromArgType(String type, String packageName) throws ClassNotFoundException {
            String str;
            if (Intrinsics.areEqual(NavType.IntType.getName(), type)) {
                return NavType.IntType;
            }
            if (Intrinsics.areEqual(NavType.IntArrayType.getName(), type)) {
                return NavType.IntArrayType;
            }
            if (Intrinsics.areEqual(NavType.IntListType.getName(), type)) {
                return NavType.IntListType;
            }
            if (Intrinsics.areEqual(NavType.LongType.getName(), type)) {
                return NavType.LongType;
            }
            if (Intrinsics.areEqual(NavType.LongArrayType.getName(), type)) {
                return NavType.LongArrayType;
            }
            if (Intrinsics.areEqual(NavType.LongListType.getName(), type)) {
                return NavType.LongListType;
            }
            if (Intrinsics.areEqual(NavType.BoolType.getName(), type)) {
                return NavType.BoolType;
            }
            if (Intrinsics.areEqual(NavType.BoolArrayType.getName(), type)) {
                return NavType.BoolArrayType;
            }
            if (Intrinsics.areEqual(NavType.BoolListType.getName(), type)) {
                return NavType.BoolListType;
            }
            if (Intrinsics.areEqual(NavType.StringType.getName(), type)) {
                return NavType.StringType;
            }
            if (Intrinsics.areEqual(NavType.StringArrayType.getName(), type)) {
                return NavType.StringArrayType;
            }
            if (Intrinsics.areEqual(NavType.StringListType.getName(), type)) {
                return NavType.StringListType;
            }
            if (Intrinsics.areEqual(NavType.FloatType.getName(), type)) {
                return NavType.FloatType;
            }
            if (Intrinsics.areEqual(NavType.FloatArrayType.getName(), type)) {
                return NavType.FloatArrayType;
            }
            if (Intrinsics.areEqual(NavType.FloatListType.getName(), type)) {
                return NavType.FloatListType;
            }
            if (Intrinsics.areEqual(NavType.ReferenceType.getName(), type)) {
                return NavType.ReferenceType;
            }
            String str2 = type;
            if (!(str2 == null || str2.length() == 0)) {
                try {
                    if (StringsKt.startsWith$default(type, ".", false, 2, (Object) null) && packageName != null) {
                        str = packageName + type;
                    } else {
                        str = type;
                    }
                    String str3 = str;
                    boolean isArray = StringsKt.endsWith$default(type, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, false, 2, (Object) null);
                    if (isArray) {
                        String strSubstring = str3.substring(0, str3.length() - 2);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                        str3 = strSubstring;
                    }
                    Class clazz = Class.forName(str3);
                    Intrinsics.checkNotNullExpressionValue(clazz, "clazz");
                    NavType<?> serializableOrParcelableType$navigation_common_release = parseSerializableOrParcelableType$navigation_common_release(clazz, isArray);
                    if (serializableOrParcelableType$navigation_common_release != null) {
                        return serializableOrParcelableType$navigation_common_release;
                    }
                    throw new IllegalArgumentException((str3 + " is not Serializable or Parcelable.").toString());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return NavType.StringType;
        }

        public final NavType<?> parseSerializableOrParcelableType$navigation_common_release(Class<?> clazz, boolean isArray) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            if (Parcelable.class.isAssignableFrom(clazz)) {
                if (isArray) {
                    return new ParcelableArrayType(clazz);
                }
                return new ParcelableType(clazz);
            }
            if (Enum.class.isAssignableFrom(clazz) && !isArray) {
                return new EnumType(clazz);
            }
            if (Serializable.class.isAssignableFrom(clazz)) {
                if (isArray) {
                    return new SerializableArrayType(clazz);
                }
                return new SerializableType(clazz);
            }
            return null;
        }

        @JvmStatic
        public final NavType<Object> inferFromValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                NavType.IntType.parseValue(value);
                NavType<Integer> navType = NavType.IntType;
                Intrinsics.checkNotNull(navType, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType;
            } catch (IllegalArgumentException e) {
                try {
                    NavType.LongType.parseValue(value);
                    NavType<Long> navType2 = NavType.LongType;
                    Intrinsics.checkNotNull(navType2, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                    return navType2;
                } catch (IllegalArgumentException e2) {
                    try {
                        NavType.FloatType.parseValue(value);
                        NavType<Float> navType3 = NavType.FloatType;
                        Intrinsics.checkNotNull(navType3, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                        return navType3;
                    } catch (IllegalArgumentException e3) {
                        try {
                            NavType.BoolType.parseValue(value);
                            NavType<Boolean> navType4 = NavType.BoolType;
                            Intrinsics.checkNotNull(navType4, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                            return navType4;
                        } catch (IllegalArgumentException e4) {
                            NavType<String> navType5 = NavType.StringType;
                            Intrinsics.checkNotNull(navType5, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                            return navType5;
                        }
                    }
                }
            }
        }

        @JvmStatic
        public final NavType<Object> inferFromValueType(Object value) {
            if (value instanceof Integer) {
                NavType<Integer> navType = NavType.IntType;
                Intrinsics.checkNotNull(navType, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType;
            }
            if (value instanceof int[]) {
                NavType<int[]> navType2 = NavType.IntArrayType;
                Intrinsics.checkNotNull(navType2, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType2;
            }
            if (value instanceof Long) {
                NavType<Long> navType3 = NavType.LongType;
                Intrinsics.checkNotNull(navType3, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType3;
            }
            if (value instanceof long[]) {
                NavType<long[]> navType4 = NavType.LongArrayType;
                Intrinsics.checkNotNull(navType4, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType4;
            }
            if (value instanceof Float) {
                NavType<Float> navType5 = NavType.FloatType;
                Intrinsics.checkNotNull(navType5, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType5;
            }
            if (value instanceof float[]) {
                NavType<float[]> navType6 = NavType.FloatArrayType;
                Intrinsics.checkNotNull(navType6, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType6;
            }
            if (value instanceof Boolean) {
                NavType<Boolean> navType7 = NavType.BoolType;
                Intrinsics.checkNotNull(navType7, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType7;
            }
            if (value instanceof boolean[]) {
                NavType<boolean[]> navType8 = NavType.BoolArrayType;
                Intrinsics.checkNotNull(navType8, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType8;
            }
            if ((value instanceof String) || value == null) {
                NavType<String> navType9 = NavType.StringType;
                Intrinsics.checkNotNull(navType9, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType9;
            }
            if ((value instanceof Object[]) && (((Object[]) value) instanceof String[])) {
                NavType<String[]> navType10 = NavType.StringArrayType;
                Intrinsics.checkNotNull(navType10, "null cannot be cast to non-null type androidx.navigation.NavType<kotlin.Any>");
                return navType10;
            }
            if (value.getClass().isArray()) {
                Class<?> componentType = value.getClass().getComponentType();
                Intrinsics.checkNotNull(componentType);
                if (Parcelable.class.isAssignableFrom(componentType)) {
                    Class<?> componentType2 = value.getClass().getComponentType();
                    Intrinsics.checkNotNull(componentType2, "null cannot be cast to non-null type java.lang.Class<android.os.Parcelable>");
                    return new ParcelableArrayType(componentType2);
                }
            }
            if (value.getClass().isArray()) {
                Class<?> componentType3 = value.getClass().getComponentType();
                Intrinsics.checkNotNull(componentType3);
                if (Serializable.class.isAssignableFrom(componentType3)) {
                    Class<?> componentType4 = value.getClass().getComponentType();
                    Intrinsics.checkNotNull(componentType4, "null cannot be cast to non-null type java.lang.Class<java.io.Serializable>");
                    return new SerializableArrayType(componentType4);
                }
            }
            if (value instanceof Parcelable) {
                return new ParcelableType(value.getClass());
            }
            if (value instanceof Enum) {
                return new EnumType(value.getClass());
            }
            if (value instanceof Serializable) {
                return new SerializableType(value.getClass());
            }
            throw new IllegalArgumentException("Object of type " + value.getClass().getName() + " is not supported for navigation arguments.");
        }
    }

    @JvmStatic
    public static NavType<?> fromArgType(String type, String packageName) {
        return INSTANCE.fromArgType(type, packageName);
    }

    @JvmStatic
    public static final NavType<Object> inferFromValue(String value) {
        return INSTANCE.inferFromValue(value);
    }

    @JvmStatic
    public static final NavType<Object> inferFromValueType(Object value) {
        return INSTANCE.inferFromValueType(value);
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J \u0010\u000e\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0015\u0010\u0015\u001a\u00028\u00012\u0006\u0010\u0016\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\u0017J%\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aR\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Landroidx/navigation/NavType$ParcelableType;", "D", "Landroidx/navigation/NavType;", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "name", "", "getName", "()Ljava/lang/String;", "equals", "", "other", "", "get", "bundle", "Landroid/os/Bundle;", "key", "(Landroid/os/Bundle;Ljava/lang/String;)Ljava/lang/Object;", "hashCode", "", "parseValue", "value", "(Ljava/lang/String;)Ljava/lang/Object;", "put", "", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/lang/Object;)V", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class ParcelableType<D> extends NavType<D> {
        private final Class<D> type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ParcelableType(Class<D> type) {
            super(true);
            Intrinsics.checkNotNullParameter(type, "type");
            boolean z = true;
            if (!Parcelable.class.isAssignableFrom(type) && !Serializable.class.isAssignableFrom(type)) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException((type + " does not implement Parcelable or Serializable.").toString());
            }
            this.type = type;
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            String name = this.type.getName();
            Intrinsics.checkNotNullExpressionValue(name, "type.name");
            return name;
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, D value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            this.type.cast(value);
            if (value == null || (value instanceof Parcelable)) {
                bundle.putParcelable(key, (Parcelable) value);
            } else if (value instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) value);
            }
        }

        @Override // androidx.navigation.NavType
        public D get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (D) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public D parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            throw new UnsupportedOperationException("Parcelables don't support default values.");
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
                return false;
            }
            ParcelableType that = (ParcelableType) other;
            return Intrinsics.areEqual(this.type, that.type);
        }

        public int hashCode() {
            return this.type.hashCode();
        }
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\u00040\u0003B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J&\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\nH\u0096\u0002¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u001b\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010\u0019\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u001aJ-\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u001dJ-\u0010\u001e\u001a\u00020\u000e2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00042\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u001fR\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006 "}, d2 = {"Landroidx/navigation/NavType$ParcelableArrayType;", "D", "Landroid/os/Parcelable;", "Landroidx/navigation/NavType;", "", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "arrayType", "name", "", "getName", "()Ljava/lang/String;", "equals", "", "other", "", "get", "bundle", "Landroid/os/Bundle;", "key", "(Landroid/os/Bundle;Ljava/lang/String;)[Landroid/os/Parcelable;", "hashCode", "", "parseValue", "value", "(Ljava/lang/String;)[Landroid/os/Parcelable;", "put", "", "(Landroid/os/Bundle;Ljava/lang/String;[Landroid/os/Parcelable;)V", "valueEquals", "([Landroid/os/Parcelable;[Landroid/os/Parcelable;)Z", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class ParcelableArrayType<D extends Parcelable> extends NavType<D[]> {
        private final Class<D[]> arrayType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ParcelableArrayType(Class<D> type) throws ClassNotFoundException {
            super(true);
            Intrinsics.checkNotNullParameter(type, "type");
            if (!Parcelable.class.isAssignableFrom(type)) {
                throw new IllegalArgumentException((type + " does not implement Parcelable.").toString());
            }
            try {
                Class arrayType = Class.forName("[L" + type.getName() + ';');
                Intrinsics.checkNotNull(arrayType, "null cannot be cast to non-null type java.lang.Class<kotlin.Array<D of androidx.navigation.NavType.ParcelableArrayType>>");
                this.arrayType = arrayType;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            String name = this.arrayType.getName();
            Intrinsics.checkNotNullExpressionValue(name, "arrayType.name");
            return name;
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, D[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            this.arrayType.cast(value);
            bundle.putParcelableArray(key, value);
        }

        @Override // androidx.navigation.NavType
        public D[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (D[]) ((Parcelable[]) bundle.get(key));
        }

        @Override // androidx.navigation.NavType
        public D[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
                return false;
            }
            ParcelableArrayType that = (ParcelableArrayType) other;
            return Intrinsics.areEqual(this.arrayType, that.arrayType);
        }

        public int hashCode() {
            return this.arrayType.hashCode();
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(D[] value, D[] other) {
            return ArraysKt.contentDeepEquals(value, other);
        }
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\b\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006B\u001d\b\u0010\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\tJ\u0013\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J \u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0096\u0002¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0015\u0010\u0018\u001a\u00028\u00012\u0006\u0010\u0019\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\u001aJ%\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001dR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Landroidx/navigation/NavType$SerializableType;", "D", "Ljava/io/Serializable;", "Landroidx/navigation/NavType;", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "nullableAllowed", "", "(ZLjava/lang/Class;)V", "name", "", "getName", "()Ljava/lang/String;", "equals", "other", "", "get", "bundle", "Landroid/os/Bundle;", "key", "(Landroid/os/Bundle;Ljava/lang/String;)Ljava/io/Serializable;", "hashCode", "", "parseValue", "value", "(Ljava/lang/String;)Ljava/io/Serializable;", "put", "", "(Landroid/os/Bundle;Ljava/lang/String;Ljava/io/Serializable;)V", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static class SerializableType<D extends Serializable> extends NavType<D> {
        private final Class<D> type;

        @Override // androidx.navigation.NavType
        public String getName() {
            String name = this.type.getName();
            Intrinsics.checkNotNullExpressionValue(name, "type.name");
            return name;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SerializableType(Class<D> type) {
            super(true);
            Intrinsics.checkNotNullParameter(type, "type");
            if (Serializable.class.isAssignableFrom(type)) {
                if (!(true ^ type.isEnum())) {
                    throw new IllegalArgumentException((type + " is an Enum. You should use EnumType instead.").toString());
                }
                this.type = type;
                return;
            }
            throw new IllegalArgumentException((type + " does not implement Serializable.").toString());
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SerializableType(boolean nullableAllowed, Class<D> type) {
            super(nullableAllowed);
            Intrinsics.checkNotNullParameter(type, "type");
            if (!Serializable.class.isAssignableFrom(type)) {
                throw new IllegalArgumentException((type + " does not implement Serializable.").toString());
            }
            this.type = type;
        }

        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, D value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            this.type.cast(value);
            bundle.putSerializable(key, value);
        }

        @Override // androidx.navigation.NavType
        public D get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (D) bundle.get(key);
        }

        @Override // androidx.navigation.NavType
        public D parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            throw new UnsupportedOperationException("Serializables don't support default values.");
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof SerializableType) {
                return Intrinsics.areEqual(this.type, ((SerializableType) other).type);
            }
            return false;
        }

        public int hashCode() {
            return this.type.hashCode();
        }
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000*\f\b\u0001\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00012\u0006\u0010\f\u001a\u00020\bH\u0016¢\u0006\u0002\u0010\rR\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Landroidx/navigation/NavType$EnumType;", "D", "", "Landroidx/navigation/NavType$SerializableType;", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "name", "", "getName", "()Ljava/lang/String;", "parseValue", "value", "(Ljava/lang/String;)Ljava/lang/Enum;", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class EnumType<D extends Enum<?>> extends SerializableType<D> {
        private final Class<D> type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EnumType(Class<D> type) {
            super(false, type);
            Intrinsics.checkNotNullParameter(type, "type");
            if (!type.isEnum()) {
                throw new IllegalArgumentException((type + " is not an Enum type.").toString());
            }
            this.type = type;
        }

        @Override // androidx.navigation.NavType.SerializableType, androidx.navigation.NavType
        public String getName() {
            String name = this.type.getName();
            Intrinsics.checkNotNullExpressionValue(name, "type.name");
            return name;
        }

        @Override // androidx.navigation.NavType.SerializableType, androidx.navigation.NavType
        public D parseValue(String value) {
            Enum element$iv;
            Intrinsics.checkNotNullParameter(value, "value");
            Enum[] $this$firstOrNull$iv = this.type.getEnumConstants();
            Intrinsics.checkNotNullExpressionValue($this$firstOrNull$iv, "type.enumConstants");
            int length = $this$firstOrNull$iv.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    element$iv = $this$firstOrNull$iv[i];
                    Enum constant = element$iv;
                    if (StringsKt.equals(constant.name(), value, true)) {
                        break;
                    }
                    i++;
                } else {
                    element$iv = null;
                    break;
                }
            }
            Enum r4 = element$iv;
            if (r4 != null) {
                return r4;
            }
            throw new IllegalArgumentException("Enum value " + value + " not found for type " + this.type.getName() + '.');
        }
    }

    /* compiled from: NavType.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\u00040\u0003B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J&\u0010\u0011\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\nH\u0096\u0002¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u001b\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010\u0019\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u001aJ-\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u001dJ-\u0010\u001e\u001a\u00020\u000e2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00042\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u001fR\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006 "}, d2 = {"Landroidx/navigation/NavType$SerializableArrayType;", "D", "Ljava/io/Serializable;", "Landroidx/navigation/NavType;", "", "type", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "arrayType", "name", "", "getName", "()Ljava/lang/String;", "equals", "", "other", "", "get", "bundle", "Landroid/os/Bundle;", "key", "(Landroid/os/Bundle;Ljava/lang/String;)[Ljava/io/Serializable;", "hashCode", "", "parseValue", "value", "(Ljava/lang/String;)[Ljava/io/Serializable;", "put", "", "(Landroid/os/Bundle;Ljava/lang/String;[Ljava/io/Serializable;)V", "valueEquals", "([Ljava/io/Serializable;[Ljava/io/Serializable;)Z", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class SerializableArrayType<D extends Serializable> extends NavType<D[]> {
        private final Class<D[]> arrayType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SerializableArrayType(Class<D> type) throws ClassNotFoundException {
            super(true);
            Intrinsics.checkNotNullParameter(type, "type");
            if (!Serializable.class.isAssignableFrom(type)) {
                throw new IllegalArgumentException((type + " does not implement Serializable.").toString());
            }
            try {
                Class arrayType = Class.forName("[L" + type.getName() + ';');
                Intrinsics.checkNotNull(arrayType, "null cannot be cast to non-null type java.lang.Class<kotlin.Array<D of androidx.navigation.NavType.SerializableArrayType>>");
                this.arrayType = arrayType;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // androidx.navigation.NavType
        public String getName() {
            String name = this.arrayType.getName();
            Intrinsics.checkNotNullExpressionValue(name, "arrayType.name");
            return name;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.navigation.NavType
        public void put(Bundle bundle, String key, D[] value) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            this.arrayType.cast(value);
            bundle.putSerializable(key, (Serializable) value);
        }

        @Override // androidx.navigation.NavType
        public D[] get(Bundle bundle, String key) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(key, "key");
            return (D[]) ((Serializable[]) bundle.get(key));
        }

        @Override // androidx.navigation.NavType
        public D[] parseValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !Intrinsics.areEqual(getClass(), other.getClass())) {
                return false;
            }
            SerializableArrayType that = (SerializableArrayType) other;
            return Intrinsics.areEqual(this.arrayType, that.arrayType);
        }

        public int hashCode() {
            return this.arrayType.hashCode();
        }

        @Override // androidx.navigation.NavType
        public boolean valueEquals(D[] value, D[] other) {
            return ArraysKt.contentDeepEquals(value, other);
        }
    }
}
