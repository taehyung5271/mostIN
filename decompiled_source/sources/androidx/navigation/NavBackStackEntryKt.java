package androidx.navigation;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.serialization.RouteDeserializerKt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MagicApiIntrinsics;
import kotlin.reflect.KType;
import kotlinx.serialization.SerializersKt;

/* compiled from: NavBackStackEntry.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u0002H\u0001\"\u0006\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002H\u0086\b¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"toRoute", "T", "Landroidx/navigation/NavBackStackEntry;", "(Landroidx/navigation/NavBackStackEntry;)Ljava/lang/Object;", "navigation-common_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class NavBackStackEntryKt {
    public static final /* synthetic */ <T> T toRoute(NavBackStackEntry navBackStackEntry) {
        Intrinsics.checkNotNullParameter(navBackStackEntry, "<this>");
        Bundle arguments = navBackStackEntry.getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        Map<String, NavArgument> arguments2 = navBackStackEntry.getDestination().getArguments();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(arguments2.size()));
        for (T t : arguments2.entrySet()) {
            linkedHashMap.put(((Map.Entry) t).getKey(), ((NavArgument) ((Map.Entry) t).getValue()).getType());
        }
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return (T) RouteDeserializerKt.decodeArguments(SerializersKt.serializer((KType) null), arguments, linkedHashMap);
    }
}
