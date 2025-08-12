package androidx.navigation;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: NavigatorState.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0006H\u0016J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u0006H\u0017J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u0006H\u0017J\u0018\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0010H\u0016J\u0018\u0010%\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0010H\u0016J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0006H\u0017J\u0010\u0010'\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u0006H\u0016J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u0006H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\f¨\u0006)"}, d2 = {"Landroidx/navigation/NavigatorState;", "", "()V", "_backStack", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Landroidx/navigation/NavBackStackEntry;", "_transitionsInProgress", "", "backStack", "Lkotlinx/coroutines/flow/StateFlow;", "getBackStack", "()Lkotlinx/coroutines/flow/StateFlow;", "backStackLock", "Ljava/util/concurrent/locks/ReentrantLock;", "<set-?>", "", "isNavigating", "()Z", "setNavigating", "(Z)V", "transitionsInProgress", "getTransitionsInProgress", "createBackStackEntry", "destination", "Landroidx/navigation/NavDestination;", "arguments", "Landroid/os/Bundle;", "markTransitionComplete", "", "entry", "onLaunchSingleTop", "backStackEntry", "onLaunchSingleTopWithTransition", "pop", "popUpTo", "saveState", "popWithTransition", "prepareForTransition", "push", "pushWithTransition", "navigation-common_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public abstract class NavigatorState {
    private boolean isNavigating;
    private final ReentrantLock backStackLock = new ReentrantLock(true);
    private final MutableStateFlow<List<NavBackStackEntry>> _backStack = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
    private final MutableStateFlow<Set<NavBackStackEntry>> _transitionsInProgress = StateFlowKt.MutableStateFlow(SetsKt.emptySet());
    private final StateFlow<List<NavBackStackEntry>> backStack = FlowKt.asStateFlow(this._backStack);
    private final StateFlow<Set<NavBackStackEntry>> transitionsInProgress = FlowKt.asStateFlow(this._transitionsInProgress);

    public abstract NavBackStackEntry createBackStackEntry(NavDestination destination, Bundle arguments);

    /* renamed from: isNavigating, reason: from getter */
    public final boolean getIsNavigating() {
        return this.isNavigating;
    }

    public final void setNavigating(boolean z) {
        this.isNavigating = z;
    }

    public final StateFlow<List<NavBackStackEntry>> getBackStack() {
        return this.backStack;
    }

    public final StateFlow<Set<NavBackStackEntry>> getTransitionsInProgress() {
        return this.transitionsInProgress;
    }

    public void push(NavBackStackEntry backStackEntry) {
        Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
        ReentrantLock reentrantLock = this.backStackLock;
        reentrantLock.lock();
        try {
            this._backStack.setValue(CollectionsKt.plus((Collection<? extends NavBackStackEntry>) this._backStack.getValue(), backStackEntry));
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void pushWithTransition(NavBackStackEntry backStackEntry) {
        Iterable $this$any$iv;
        Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
        Iterable $this$any$iv2 = this._transitionsInProgress.getValue();
        boolean z = true;
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    NavBackStackEntry it2 = (NavBackStackEntry) element$iv;
                    NavBackStackEntry it3 = it2 == backStackEntry ? 1 : null;
                    if (it3 != null) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        if ($this$any$iv != null) {
            Iterable $this$any$iv3 = this.backStack.getValue();
            if (!($this$any$iv3 instanceof Collection) || !((Collection) $this$any$iv3).isEmpty()) {
                Iterator it4 = $this$any$iv3.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        Object element$iv2 = it4.next();
                        NavBackStackEntry it5 = (NavBackStackEntry) element$iv2;
                        NavBackStackEntry it6 = it5 == backStackEntry ? 1 : null;
                        if (it6 != null) {
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                return;
            }
        }
        NavBackStackEntry previousEntry = (NavBackStackEntry) CollectionsKt.lastOrNull((List) this.backStack.getValue());
        if (previousEntry != null) {
            this._transitionsInProgress.setValue(SetsKt.plus(this._transitionsInProgress.getValue(), previousEntry));
        }
        this._transitionsInProgress.setValue(SetsKt.plus(this._transitionsInProgress.getValue(), backStackEntry));
        push(backStackEntry);
    }

    public void pop(NavBackStackEntry popUpTo, boolean saveState) {
        Intrinsics.checkNotNullParameter(popUpTo, "popUpTo");
        ReentrantLock reentrantLock = this.backStackLock;
        reentrantLock.lock();
        try {
            MutableStateFlow<List<NavBackStackEntry>> mutableStateFlow = this._backStack;
            Iterable $this$takeWhile$iv = this._backStack.getValue();
            ArrayList list$iv = new ArrayList();
            for (Object item$iv : $this$takeWhile$iv) {
                NavBackStackEntry it = (NavBackStackEntry) item$iv;
                if (!(!Intrinsics.areEqual(it, popUpTo))) {
                    break;
                } else {
                    list$iv.add(item$iv);
                }
            }
            mutableStateFlow.setValue(list$iv);
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void popWithTransition(NavBackStackEntry popUpTo, boolean saveState) {
        Iterable $this$any$iv;
        Object element$iv;
        Iterable $this$none$iv;
        Intrinsics.checkNotNullParameter(popUpTo, "popUpTo");
        Iterable $this$any$iv2 = this._transitionsInProgress.getValue();
        if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv2 = it.next();
                    NavBackStackEntry it2 = (NavBackStackEntry) element$iv2;
                    NavBackStackEntry it3 = it2 == popUpTo ? 1 : null;
                    if (it3 != null) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        if ($this$any$iv != null) {
            Iterable $this$none$iv2 = this.backStack.getValue();
            if (!($this$none$iv2 instanceof Collection) || !((Collection) $this$none$iv2).isEmpty()) {
                Iterator it4 = $this$none$iv2.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        Object element$iv3 = it4.next();
                        NavBackStackEntry it5 = (NavBackStackEntry) element$iv3;
                        NavBackStackEntry it6 = it5 == popUpTo ? 1 : null;
                        if (it6 != null) {
                            $this$none$iv = null;
                            break;
                        }
                    } else {
                        $this$none$iv = 1;
                        break;
                    }
                }
            } else {
                $this$none$iv = 1;
            }
            if ($this$none$iv != null) {
                return;
            }
        }
        this._transitionsInProgress.setValue(SetsKt.plus(this._transitionsInProgress.getValue(), popUpTo));
        List $this$lastOrNull$iv = this.backStack.getValue();
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
                element$iv = iterator$iv.previous();
                NavBackStackEntry entry = (NavBackStackEntry) element$iv;
                if (((Intrinsics.areEqual(entry, popUpTo) || this.backStack.getValue().lastIndexOf(entry) >= this.backStack.getValue().lastIndexOf(popUpTo)) ? null : 1) != null) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        NavBackStackEntry incomingEntry = (NavBackStackEntry) element$iv;
        if (incomingEntry != null) {
            this._transitionsInProgress.setValue(SetsKt.plus(this._transitionsInProgress.getValue(), incomingEntry));
        }
        pop(popUpTo, saveState);
    }

    public void onLaunchSingleTop(NavBackStackEntry backStackEntry) {
        int iNextIndex;
        Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
        ReentrantLock reentrantLock = this.backStackLock;
        reentrantLock.lock();
        try {
            List tempStack = CollectionsKt.toMutableList((Collection) this.backStack.getValue());
            ListIterator iterator$iv = tempStack.listIterator(tempStack.size());
            while (true) {
                if (iterator$iv.hasPrevious()) {
                    NavBackStackEntry it = iterator$iv.previous();
                    if (Intrinsics.areEqual(it.getId(), backStackEntry.getId())) {
                        iNextIndex = iterator$iv.nextIndex();
                        break;
                    }
                } else {
                    iNextIndex = -1;
                    break;
                }
            }
            int idx = iNextIndex;
            tempStack.set(idx, backStackEntry);
            this._backStack.setValue(tempStack);
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public void onLaunchSingleTopWithTransition(NavBackStackEntry backStackEntry) {
        Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
        List $this$last$iv = this.backStack.getValue();
        ListIterator iterator$iv = $this$last$iv.listIterator($this$last$iv.size());
        while (iterator$iv.hasPrevious()) {
            Object element$iv = iterator$iv.previous();
            NavBackStackEntry it = (NavBackStackEntry) element$iv;
            if (Intrinsics.areEqual(it.getId(), backStackEntry.getId())) {
                NavBackStackEntry oldEntry = (NavBackStackEntry) element$iv;
                this._transitionsInProgress.setValue(SetsKt.plus((Set<? extends NavBackStackEntry>) SetsKt.plus(this._transitionsInProgress.getValue(), oldEntry), backStackEntry));
                onLaunchSingleTop(backStackEntry);
                return;
            }
        }
        throw new NoSuchElementException("List contains no element matching the predicate.");
    }

    public void markTransitionComplete(NavBackStackEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        this._transitionsInProgress.setValue(SetsKt.minus(this._transitionsInProgress.getValue(), entry));
    }

    public void prepareForTransition(NavBackStackEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        this._transitionsInProgress.setValue(SetsKt.plus(this._transitionsInProgress.getValue(), entry));
    }
}
