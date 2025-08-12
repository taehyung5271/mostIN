package androidx.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayKt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDestination;
import androidx.navigation.serialization.RouteSerializerKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MagicApiIntrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.reflect.KType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializersKt;

/* compiled from: NavGraph.kt */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 K2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00010\u0002:\u0001KB\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0000J\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0001J\u001f\u0010\"\u001a\u00020\u001e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010#\"\u00020\u0001¢\u0006\u0002\u0010$J\u0016\u0010\"\u001a\u00020\u001e2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010%J\u0006\u0010&\u001a\u00020\u001eJ\u0013\u0010'\u001a\u00020(2\b\u0010\u001f\u001a\u0004\u0018\u00010)H\u0096\u0002J\u0013\u0010*\u001a\u0004\u0018\u00010\u0001\"\u0006\b\u0000\u0010+\u0018\u0001H\u0086\bJ\u001d\u0010*\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010+2\b\u0010,\u001a\u0004\u0018\u0001H+¢\u0006\u0002\u0010-J\u0012\u0010*\u001a\u0004\u0018\u00010\u00012\b\b\u0001\u0010.\u001a\u00020\u0011J\u001a\u0010*\u001a\u0004\u0018\u00010\u00012\u0006\u0010,\u001a\u00020\u00072\u0006\u0010/\u001a\u00020(H\u0007J\u0012\u0010*\u001a\u0004\u0018\u00010\u00012\b\u0010,\u001a\u0004\u0018\u00010\u0007J&\u00100\u001a\u0004\u0018\u00010\u00012\b\b\u0001\u0010.\u001a\u00020\u00112\b\u00101\u001a\u0004\u0018\u00010\u00012\u0006\u00102\u001a\u00020(H\u0007J\b\u00103\u001a\u00020\u0011H\u0007J\b\u00104\u001a\u00020\u0011H\u0016J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u000106H\u0086\u0002J\u0012\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:H\u0017J*\u0010;\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020:2\u0006\u00102\u001a\u00020(2\u0006\u0010<\u001a\u00020(2\u0006\u00101\u001a\u00020\u0001H\u0007J*\u0010=\u001a\u0004\u0018\u0001082\u0006\u0010,\u001a\u00020\u00072\u0006\u00102\u001a\u00020(2\u0006\u0010<\u001a\u00020(2\u0006\u00101\u001a\u00020\u0001H\u0007J\u0018\u0010>\u001a\u00020\u001e2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BH\u0016J\u000e\u0010C\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0001J\u0015\u0010D\u001a\u00020\u001e\"\n\b\u0000\u0010+\u0018\u0001*\u00020)H\u0086\bJ\u001d\u0010D\u001a\u00020\u001e\"\b\b\u0000\u0010+*\u00020)2\u0006\u0010\u0018\u001a\u0002H+¢\u0006\u0002\u0010EJ\u000e\u0010D\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010D\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u0007J0\u0010D\u001a\u00020\u001e\"\u0004\b\u0000\u0010+2\f\u0010F\u001a\b\u0012\u0004\u0012\u0002H+0G2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070IH\u0007J\b\u0010J\u001a\u00020\u0007H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078WX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0019\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b8G¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00118G@BX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R(\u0010\u0019\u001a\u0004\u0018\u00010\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0007@BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\t\"\u0004\b\u001b\u0010\u001c¨\u0006L"}, d2 = {"Landroidx/navigation/NavGraph;", "Landroidx/navigation/NavDestination;", "", "navGraphNavigator", "Landroidx/navigation/Navigator;", "(Landroidx/navigation/Navigator;)V", "displayName", "", "getDisplayName", "()Ljava/lang/String;", "nodes", "Landroidx/collection/SparseArrayCompat;", "getNodes", "()Landroidx/collection/SparseArrayCompat;", "startDestDisplayName", "getStartDestDisplayName", "startDestId", "", "startDestIdName", "startDestinationId", "getStartDestinationId", "()I", "setStartDestinationId", "(I)V", "startDestRoute", "startDestinationRoute", "getStartDestinationRoute", "setStartDestinationRoute", "(Ljava/lang/String;)V", "addAll", "", "other", "addDestination", "node", "addDestinations", "", "([Landroidx/navigation/NavDestination;)V", "", "clear", "equals", "", "", "findNode", "T", "route", "(Ljava/lang/Object;)Landroidx/navigation/NavDestination;", "resId", "searchParents", "findNodeComprehensive", "lastVisited", "searchChildren", "getStartDestination", "hashCode", "iterator", "", "matchDeepLink", "Landroidx/navigation/NavDestination$DeepLinkMatch;", "navDeepLinkRequest", "Landroidx/navigation/NavDeepLinkRequest;", "matchDeepLinkComprehensive", "searchParent", "matchRouteComprehensive", "onInflate", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "remove", "setStartDestination", "(Ljava/lang/Object;)V", "serializer", "Lkotlinx/serialization/KSerializer;", "parseRoute", "Lkotlin/Function1;", "toString", "Companion", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public class NavGraph extends NavDestination implements Iterable<NavDestination>, KMappedMarker {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final SparseArrayCompat<NavDestination> nodes;
    private int startDestId;
    private String startDestIdName;
    private String startDestinationRoute;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavGraph(Navigator<? extends NavGraph> navGraphNavigator) {
        super(navGraphNavigator);
        Intrinsics.checkNotNullParameter(navGraphNavigator, "navGraphNavigator");
        this.nodes = new SparseArrayCompat<>(0, 1, null);
    }

    public final SparseArrayCompat<NavDestination> getNodes() {
        return this.nodes;
    }

    @Override // androidx.navigation.NavDestination
    public void onInflate(Context context, AttributeSet attrs) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        super.onInflate(context, attrs);
        TypedArray $this$use$iv = context.getResources().obtainAttributes(attrs, androidx.navigation.common.R.styleable.NavGraphNavigator);
        Intrinsics.checkNotNullExpressionValue($this$use$iv, "context.resources.obtain…leable.NavGraphNavigator)");
        setStartDestinationId($this$use$iv.getResourceId(androidx.navigation.common.R.styleable.NavGraphNavigator_startDestination, 0));
        this.startDestIdName = NavDestination.INSTANCE.getDisplayName(context, this.startDestId);
        Unit unit = Unit.INSTANCE;
        $this$use$iv.recycle();
    }

    public final NavDestination.DeepLinkMatch matchRouteComprehensive(String route, boolean searchChildren, boolean searchParent, NavDestination lastVisited) {
        NavDestination.DeepLinkMatch bestChildMatch;
        Iterable $this$mapNotNull$iv;
        int $i$f$mapNotNull;
        NavDestination.DeepLinkMatch deepLinkMatchMatchRoute;
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(lastVisited, "lastVisited");
        NavDestination.DeepLinkMatch bestMatch = matchRoute(route);
        if (searchChildren) {
            NavGraph $this$mapNotNull$iv2 = this;
            int $i$f$mapNotNull2 = 0;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $this$mapNotNull$iv2) {
                NavDestination child = (NavDestination) element$iv$iv$iv;
                if (Intrinsics.areEqual(child, lastVisited)) {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    $i$f$mapNotNull = $i$f$mapNotNull2;
                    deepLinkMatchMatchRoute = null;
                } else if (child instanceof NavGraph) {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    $i$f$mapNotNull = $i$f$mapNotNull2;
                    deepLinkMatchMatchRoute = ((NavGraph) child).matchRouteComprehensive(route, true, false, this);
                } else {
                    $this$mapNotNull$iv = $this$mapNotNull$iv2;
                    $i$f$mapNotNull = $i$f$mapNotNull2;
                    deepLinkMatchMatchRoute = child.matchRoute(route);
                }
                if (deepLinkMatchMatchRoute != null) {
                    destination$iv$iv.add(deepLinkMatchMatchRoute);
                }
                $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                $i$f$mapNotNull2 = $i$f$mapNotNull;
            }
            bestChildMatch = (NavDestination.DeepLinkMatch) CollectionsKt.maxOrNull(destination$iv$iv);
        } else {
            bestChildMatch = null;
        }
        NavGraph it = getParent();
        NavDestination.DeepLinkMatch bestParentMatch = (it == null || !searchParent || Intrinsics.areEqual(it, lastVisited)) ? null : it.matchRouteComprehensive(route, searchChildren, true, this);
        return (NavDestination.DeepLinkMatch) CollectionsKt.maxOrNull((Iterable) CollectionsKt.listOfNotNull((Object[]) new NavDestination.DeepLinkMatch[]{bestMatch, bestChildMatch, bestParentMatch}));
    }

    public final NavDestination.DeepLinkMatch matchDeepLinkComprehensive(NavDeepLinkRequest navDeepLinkRequest, boolean searchChildren, boolean searchParent, NavDestination lastVisited) {
        NavDestination.DeepLinkMatch bestChildMatch;
        Intrinsics.checkNotNullParameter(navDeepLinkRequest, "navDeepLinkRequest");
        Intrinsics.checkNotNullParameter(lastVisited, "lastVisited");
        NavDestination.DeepLinkMatch bestMatch = super.matchDeepLink(navDeepLinkRequest);
        if (searchChildren) {
            NavGraph $this$mapNotNull$iv = this;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                NavDestination child = (NavDestination) element$iv$iv$iv;
                NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink = !Intrinsics.areEqual(child, lastVisited) ? child.matchDeepLink(navDeepLinkRequest) : null;
                if (deepLinkMatchMatchDeepLink != null) {
                    destination$iv$iv.add(deepLinkMatchMatchDeepLink);
                }
            }
            bestChildMatch = (NavDestination.DeepLinkMatch) CollectionsKt.maxOrNull(destination$iv$iv);
        } else {
            bestChildMatch = null;
        }
        NavGraph it = getParent();
        NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLinkComprehensive = (it == null || !searchParent || Intrinsics.areEqual(it, lastVisited)) ? null : it.matchDeepLinkComprehensive(navDeepLinkRequest, searchChildren, true, this);
        NavDestination.DeepLinkMatch bestParentMatch = deepLinkMatchMatchDeepLinkComprehensive;
        return (NavDestination.DeepLinkMatch) CollectionsKt.maxOrNull((Iterable) CollectionsKt.listOfNotNull((Object[]) new NavDestination.DeepLinkMatch[]{bestMatch, bestChildMatch, bestParentMatch}));
    }

    @Override // androidx.navigation.NavDestination
    public NavDestination.DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        Intrinsics.checkNotNullParameter(navDeepLinkRequest, "navDeepLinkRequest");
        return matchDeepLinkComprehensive(navDeepLinkRequest, true, false, this);
    }

    public final void addDestination(NavDestination node) {
        Intrinsics.checkNotNullParameter(node, "node");
        int id = node.getId();
        String innerRoute = node.getRoute();
        if (!((id == 0 && innerRoute == null) ? false : true)) {
            throw new IllegalArgumentException("Destinations must have an id or route. Call setId(), setRoute(), or include an android:id or app:route in your navigation XML.".toString());
        }
        if (getRoute() != null && !(!Intrinsics.areEqual(innerRoute, getRoute()))) {
            throw new IllegalArgumentException(("Destination " + node + " cannot have the same route as graph " + this).toString());
        }
        if (!(id != getId())) {
            throw new IllegalArgumentException(("Destination " + node + " cannot have the same id as graph " + this).toString());
        }
        NavDestination existingDestination = this.nodes.get(id);
        if (existingDestination == node) {
            return;
        }
        if (!(node.getParent() == null)) {
            throw new IllegalStateException("Destination already has a parent set. Call NavGraph.remove() to remove the previous parent.".toString());
        }
        if (existingDestination != null) {
            existingDestination.setParent(null);
        }
        node.setParent(this);
        this.nodes.put(node.getId(), node);
    }

    public final void addDestinations(Collection<? extends NavDestination> nodes) {
        Intrinsics.checkNotNullParameter(nodes, "nodes");
        for (NavDestination node : nodes) {
            if (node != null) {
                addDestination(node);
            }
        }
    }

    public final void addDestinations(NavDestination... nodes) {
        Intrinsics.checkNotNullParameter(nodes, "nodes");
        for (NavDestination node : nodes) {
            addDestination(node);
        }
    }

    public final NavDestination findNode(int resId) {
        return findNodeComprehensive(resId, this, false);
    }

    public final NavDestination findNodeComprehensive(int resId, NavDestination lastVisited, boolean searchChildren) {
        NavDestination navDestinationFindNodeComprehensive;
        NavDestination destination = this.nodes.get(resId);
        if (destination != null) {
            return destination;
        }
        if (searchChildren) {
            Iterator it = SequencesKt.asSequence(SparseArrayKt.valueIterator(this.nodes)).iterator();
            while (true) {
                if (!it.hasNext()) {
                    navDestinationFindNodeComprehensive = null;
                    break;
                }
                NavDestination child = (NavDestination) it.next();
                if ((child instanceof NavGraph) && !Intrinsics.areEqual(child, lastVisited)) {
                    navDestinationFindNodeComprehensive = ((NavGraph) child).findNodeComprehensive(resId, this, true);
                } else {
                    navDestinationFindNodeComprehensive = null;
                }
                if (navDestinationFindNodeComprehensive != null) {
                    break;
                }
            }
            destination = navDestinationFindNodeComprehensive;
        }
        if (destination != null) {
            return destination;
        }
        if (getParent() == null || Intrinsics.areEqual(getParent(), lastVisited)) {
            return null;
        }
        NavGraph parent = getParent();
        Intrinsics.checkNotNull(parent);
        return parent.findNodeComprehensive(resId, this, searchChildren);
    }

    public final NavDestination findNode(String route) {
        String str = route;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return findNode(route, true);
    }

    public final /* synthetic */ <T> NavDestination findNode() {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return findNode(RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null)));
    }

    public final <T> NavDestination findNode(T route) {
        if (route != null) {
            return findNode(RouteSerializerKt.generateHashCode(SerializersKt.serializer(Reflection.getOrCreateKotlinClass(route.getClass()))));
        }
        return null;
    }

    public final NavDestination findNode(String route, boolean searchParents) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(route, "route");
        Sequence $this$firstOrNull$iv = SequencesKt.asSequence(SparseArrayKt.valueIterator(this.nodes));
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                NavDestination it2 = (NavDestination) element$iv;
                boolean z = false;
                if (StringsKt.equals$default(it2.getRoute(), route, false, 2, null) || it2.matchRoute(route) != null) {
                    z = true;
                }
                if (z) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        NavDestination destination = (NavDestination) element$iv;
        if (destination != null) {
            return destination;
        }
        if (!searchParents || getParent() == null) {
            return null;
        }
        NavGraph parent = getParent();
        Intrinsics.checkNotNull(parent);
        return parent.findNode(route);
    }

    /* compiled from: NavGraph.kt */
    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0007\u001a\u00020\u0006H\u0096\u0002J\t\u0010\b\u001a\u00020\u0002H\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"androidx/navigation/NavGraph$iterator$1", "", "Landroidx/navigation/NavDestination;", "index", "", "wentToNext", "", "hasNext", "next", "remove", "", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* renamed from: androidx.navigation.NavGraph$iterator$1, reason: invalid class name */
    public static final class AnonymousClass1 implements Iterator<NavDestination>, KMutableIterator {
        private int index = -1;
        private boolean wentToNext;

        AnonymousClass1() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index + 1 < NavGraph.this.getNodes().size();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public NavDestination next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.wentToNext = true;
            SparseArrayCompat<NavDestination> nodes = NavGraph.this.getNodes();
            this.index++;
            return nodes.valueAt(this.index);
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.wentToNext) {
                throw new IllegalStateException("You must call next() before you can remove an element".toString());
            }
            SparseArrayCompat $this$remove_u24lambda_u241 = NavGraph.this.getNodes();
            $this$remove_u24lambda_u241.valueAt(this.index).setParent(null);
            $this$remove_u24lambda_u241.removeAt(this.index);
            this.index--;
            this.wentToNext = false;
        }
    }

    @Override // java.lang.Iterable
    public final Iterator<NavDestination> iterator() {
        return new AnonymousClass1();
    }

    public final void addAll(NavGraph other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Iterator iterator = other.iterator();
        while (iterator.hasNext()) {
            NavDestination destination = iterator.next();
            iterator.remove();
            addDestination(destination);
        }
    }

    public final void remove(NavDestination node) {
        Intrinsics.checkNotNullParameter(node, "node");
        int index = this.nodes.indexOfKey(node.getId());
        if (index >= 0) {
            this.nodes.valueAt(index).setParent(null);
            this.nodes.removeAt(index);
        }
    }

    public final void clear() {
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Override // androidx.navigation.NavDestination
    public String getDisplayName() {
        return getId() != 0 ? super.getDisplayName() : "the root navigation";
    }

    @Deprecated(message = "Use getStartDestinationId instead.", replaceWith = @ReplaceWith(expression = "startDestinationId", imports = {}))
    public final int getStartDestination() {
        return getStartDestId();
    }

    /* renamed from: getStartDestinationId, reason: from getter */
    public final int getStartDestId() {
        return this.startDestId;
    }

    private final void setStartDestinationId(int startDestId) {
        if (!(startDestId != getId())) {
            throw new IllegalArgumentException(("Start destination " + startDestId + " cannot use the same id as the graph " + this).toString());
        }
        if (this.startDestinationRoute != null) {
            setStartDestinationRoute(null);
        }
        this.startDestId = startDestId;
        this.startDestIdName = null;
    }

    public final void setStartDestination(int startDestId) {
        setStartDestinationId(startDestId);
    }

    public final void setStartDestination(String startDestRoute) {
        Intrinsics.checkNotNullParameter(startDestRoute, "startDestRoute");
        setStartDestinationRoute(startDestRoute);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ <T> void setStartDestination() {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        setStartDestination(SerializersKt.serializer((KType) null), new Function1<NavDestination, String>() { // from class: androidx.navigation.NavGraph.setStartDestination.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(NavDestination startDestination) {
                Intrinsics.checkNotNullParameter(startDestination, "startDestination");
                String route = startDestination.getRoute();
                Intrinsics.checkNotNull(route);
                return route;
            }
        });
    }

    public final <T> void setStartDestination(final T startDestRoute) {
        Intrinsics.checkNotNullParameter(startDestRoute, "startDestRoute");
        setStartDestination(SerializersKt.serializer(Reflection.getOrCreateKotlinClass(startDestRoute.getClass())), new Function1<NavDestination, String>() { // from class: androidx.navigation.NavGraph.setStartDestination.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(NavDestination startDestination) {
                Intrinsics.checkNotNullParameter(startDestination, "startDestination");
                Map $this$mapValues$iv = startDestination.getArguments();
                Map destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
                Iterable $this$associateByTo$iv$iv$iv = $this$mapValues$iv.entrySet();
                for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                    Map.Entry it$iv$iv = (Map.Entry) element$iv$iv$iv;
                    Map.Entry it = (Map.Entry) element$iv$iv$iv;
                    destination$iv$iv.put(it$iv$iv.getKey(), ((NavArgument) it.getValue()).getType());
                }
                return RouteSerializerKt.generateRouteWithArgs(startDestRoute, destination$iv$iv);
            }
        });
    }

    public final <T> void setStartDestination(KSerializer<T> serializer, Function1<? super NavDestination, String> parseRoute) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(parseRoute, "parseRoute");
        int id = RouteSerializerKt.generateHashCode(serializer);
        NavDestination startDest = findNode(id);
        if (startDest == null) {
            throw new IllegalStateException(("Cannot find startDestination " + serializer.getDescriptor().getSerialName() + " from NavGraph. Ensure the starting NavDestination was added with route from KClass.").toString());
        }
        setStartDestinationRoute(parseRoute.invoke(startDest));
        this.startDestId = id;
    }

    public final String getStartDestinationRoute() {
        return this.startDestinationRoute;
    }

    private final void setStartDestinationRoute(String startDestRoute) {
        int iHashCode;
        if (startDestRoute == null) {
            iHashCode = 0;
        } else {
            if (!(!Intrinsics.areEqual(startDestRoute, getRoute()))) {
                throw new IllegalArgumentException(("Start destination " + startDestRoute + " cannot use the same route as the graph " + this).toString());
            }
            if (!(!StringsKt.isBlank(startDestRoute))) {
                throw new IllegalArgumentException("Cannot have an empty start destination route".toString());
            }
            String internalRoute = NavDestination.INSTANCE.createRoute(startDestRoute);
            iHashCode = internalRoute.hashCode();
        }
        this.startDestId = iHashCode;
        this.startDestinationRoute = startDestRoute;
    }

    public final String getStartDestDisplayName() {
        if (this.startDestIdName == null) {
            String strValueOf = this.startDestinationRoute;
            if (strValueOf == null) {
                strValueOf = String.valueOf(this.startDestId);
            }
            this.startDestIdName = strValueOf;
        }
        String str = this.startDestIdName;
        Intrinsics.checkNotNull(str);
        return str;
    }

    @Override // androidx.navigation.NavDestination
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        NavDestination startDestination = findNode(this.startDestinationRoute);
        if (startDestination == null) {
            startDestination = findNode(getStartDestId());
        }
        sb.append(" startDestination=");
        if (startDestination == null) {
            if (this.startDestinationRoute != null) {
                sb.append(this.startDestinationRoute);
            } else if (this.startDestIdName != null) {
                sb.append(this.startDestIdName);
            } else {
                sb.append("0x" + Integer.toHexString(this.startDestId));
            }
        } else {
            sb.append("{");
            sb.append(startDestination.toString());
            sb.append("}");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    @Override // androidx.navigation.NavDestination
    public boolean equals(Object other) {
        Sequence $this$all$iv;
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof NavGraph)) {
            return false;
        }
        if (super.equals(other)) {
            SparseArrayCompat $this$size$iv = this.nodes;
            int size = $this$size$iv.size();
            SparseArrayCompat $this$size$iv2 = ((NavGraph) other).nodes;
            if (size == $this$size$iv2.size() && getStartDestId() == ((NavGraph) other).getStartDestId()) {
                Sequence $this$all$iv2 = SequencesKt.asSequence(SparseArrayKt.valueIterator(this.nodes));
                Iterator it = $this$all$iv2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        NavDestination it2 = (NavDestination) element$iv;
                        if (!Intrinsics.areEqual(it2, ((NavGraph) other).nodes.get(it2.getId()))) {
                            $this$all$iv = null;
                            break;
                        }
                    } else {
                        $this$all$iv = 1;
                        break;
                    }
                }
                if ($this$all$iv != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // androidx.navigation.NavDestination
    public int hashCode() {
        int result = getStartDestId();
        SparseArrayCompat $this$forEach$iv = this.nodes;
        int size = $this$forEach$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int key = $this$forEach$iv.keyAt(index$iv);
            NavDestination value = $this$forEach$iv.valueAt(index$iv);
            result = (((result * 31) + key) * 31) + value.hashCode();
        }
        return result;
    }

    /* compiled from: NavGraph.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u0006H\u0007J\f\u0010\u0007\u001a\u00020\u0005*\u00020\u0006H\u0007¨\u0006\b"}, d2 = {"Landroidx/navigation/NavGraph$Companion;", "", "()V", "childHierarchy", "Lkotlin/sequences/Sequence;", "Landroidx/navigation/NavDestination;", "Landroidx/navigation/NavGraph;", "findStartDestination", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final NavDestination findStartDestination(NavGraph $this$findStartDestination) {
            Intrinsics.checkNotNullParameter($this$findStartDestination, "<this>");
            return (NavDestination) SequencesKt.last(childHierarchy($this$findStartDestination));
        }

        public final Sequence<NavDestination> childHierarchy(NavGraph $this$childHierarchy) {
            Intrinsics.checkNotNullParameter($this$childHierarchy, "<this>");
            return SequencesKt.generateSequence($this$childHierarchy, new Function1<NavDestination, NavDestination>() { // from class: androidx.navigation.NavGraph$Companion$childHierarchy$1
                @Override // kotlin.jvm.functions.Function1
                public final NavDestination invoke(NavDestination it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof NavGraph) {
                        return ((NavGraph) it).findNode(((NavGraph) it).getStartDestId());
                    }
                    return null;
                }
            });
        }
    }

    @JvmStatic
    public static final NavDestination findStartDestination(NavGraph $this$findStartDestination) {
        return INSTANCE.findStartDestination($this$findStartDestination);
    }
}
