package androidx.navigation;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NavAction.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0003H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Landroidx/navigation/NavAction;", "", "destinationId", "", "navOptions", "Landroidx/navigation/NavOptions;", "defaultArguments", "Landroid/os/Bundle;", "(ILandroidx/navigation/NavOptions;Landroid/os/Bundle;)V", "getDefaultArguments", "()Landroid/os/Bundle;", "setDefaultArguments", "(Landroid/os/Bundle;)V", "getDestinationId", "()I", "getNavOptions", "()Landroidx/navigation/NavOptions;", "setNavOptions", "(Landroidx/navigation/NavOptions;)V", "equals", "", "other", "hashCode", "toString", "", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class NavAction {
    private Bundle defaultArguments;
    private final int destinationId;
    private NavOptions navOptions;

    public NavAction(int destinationId, NavOptions navOptions, Bundle defaultArguments) {
        this.destinationId = destinationId;
        this.navOptions = navOptions;
        this.defaultArguments = defaultArguments;
    }

    public /* synthetic */ NavAction(int i, NavOptions navOptions, Bundle bundle, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : navOptions, (i2 & 4) != 0 ? null : bundle);
    }

    public final int getDestinationId() {
        return this.destinationId;
    }

    public final NavOptions getNavOptions() {
        return this.navOptions;
    }

    public final void setNavOptions(NavOptions navOptions) {
        this.navOptions = navOptions;
    }

    public final Bundle getDefaultArguments() {
        return this.defaultArguments;
    }

    public final void setDefaultArguments(Bundle bundle) {
        this.defaultArguments = bundle;
    }

    public NavAction(int destinationId) {
        this(destinationId, null, null, 6, null);
    }

    public NavAction(int destinationId, NavOptions navOptions) {
        this(destinationId, navOptions, null, 4, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean equals(java.lang.Object r12) {
        /*
            r11 = this;
            r0 = 1
            if (r11 != r12) goto L4
            return r0
        L4:
            r1 = 0
            if (r12 == 0) goto L88
            boolean r2 = r12 instanceof androidx.navigation.NavAction
            if (r2 != 0) goto Ld
            goto L88
        Ld:
            int r2 = r11.destinationId
            r3 = r12
            androidx.navigation.NavAction r3 = (androidx.navigation.NavAction) r3
            int r3 = r3.destinationId
            if (r2 != r3) goto L86
            androidx.navigation.NavOptions r2 = r11.navOptions
            r3 = r12
            androidx.navigation.NavAction r3 = (androidx.navigation.NavAction) r3
            androidx.navigation.NavOptions r3 = r3.navOptions
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 == 0) goto L86
            android.os.Bundle r2 = r11.defaultArguments
            r3 = r12
            androidx.navigation.NavAction r3 = (androidx.navigation.NavAction) r3
            android.os.Bundle r3 = r3.defaultArguments
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 != 0) goto L85
            android.os.Bundle r2 = r11.defaultArguments
            if (r2 == 0) goto L82
            java.util.Set r2 = r2.keySet()
            if (r2 == 0) goto L82
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            r3 = 0
            boolean r4 = r2 instanceof java.util.Collection
            if (r4 == 0) goto L4c
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L4c
            r2 = r0
            goto L7d
        L4c:
            java.util.Iterator r4 = r2.iterator()
        L50:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L7c
            java.lang.Object r5 = r4.next()
            r6 = r5
            java.lang.String r6 = (java.lang.String) r6
            r7 = 0
            android.os.Bundle r8 = r11.defaultArguments
            r9 = 0
            if (r8 == 0) goto L68
            java.lang.Object r8 = r8.get(r6)
            goto L69
        L68:
            r8 = r9
        L69:
            r10 = r12
            androidx.navigation.NavAction r10 = (androidx.navigation.NavAction) r10
            android.os.Bundle r10 = r10.defaultArguments
            if (r10 == 0) goto L74
            java.lang.Object r9 = r10.get(r6)
        L74:
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)
            if (r6 != 0) goto L50
            r2 = r1
            goto L7d
        L7c:
            r2 = r0
        L7d:
            if (r2 != r0) goto L82
            r2 = r0
            goto L83
        L82:
            r2 = r1
        L83:
            if (r2 == 0) goto L86
        L85:
            goto L87
        L86:
            r0 = r1
        L87:
            return r0
        L88:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavAction.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        Iterable iterableKeySet;
        int iHashCode = Integer.hashCode(this.destinationId) * 31;
        NavOptions navOptions = this.navOptions;
        int result = iHashCode + (navOptions != null ? navOptions.hashCode() : 0);
        Bundle bundle = this.defaultArguments;
        if (bundle != null && (iterableKeySet = bundle.keySet()) != null) {
            Iterable $this$forEach$iv = iterableKeySet;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String) element$iv;
                int i = result * 31;
                Bundle bundle2 = this.defaultArguments;
                Object obj = bundle2 != null ? bundle2.get(it) : null;
                result = i + (obj != null ? obj.hashCode() : 0);
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(0x");
        sb.append(Integer.toHexString(this.destinationId));
        sb.append(")");
        if (this.navOptions != null) {
            sb.append(" navOptions=");
            sb.append(this.navOptions);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
