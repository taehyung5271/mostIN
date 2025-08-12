package androidx.navigation.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.widget.Openable;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

/* compiled from: NavigationUI.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\fH\u0007J \u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0007J\"\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\"\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007J\"\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\"\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007J*\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J*\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000b\u001a\u00020\fH\u0007J \u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0007J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!2\u0006\u0010\u000b\u001a\u00020\fH\u0007J \u0010\u0019\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0007J\u001b\u0010\"\u001a\u00020\n*\u00020#2\b\b\u0001\u0010$\u001a\u00020%H\u0001¢\u0006\u0002\b&R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Landroidx/navigation/ui/NavigationUI;", "", "()V", "TAG", "", "findBottomSheetBehavior", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "view", "Landroid/view/View;", "navigateUp", "", "navController", "Landroidx/navigation/NavController;", "openableLayout", "Landroidx/customview/widget/Openable;", "configuration", "Landroidx/navigation/ui/AppBarConfiguration;", "onNavDestinationSelected", "item", "Landroid/view/MenuItem;", "saveState", "setupActionBarWithNavController", "", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "setupWithNavController", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "collapsingToolbarLayout", "Lcom/google/android/material/appbar/CollapsingToolbarLayout;", "navigationBarView", "Lcom/google/android/material/navigation/NavigationBarView;", "navigationView", "Lcom/google/android/material/navigation/NavigationView;", "matchDestination", "Landroidx/navigation/NavDestination;", "destId", "", "matchDestination$navigation_ui_release", "navigation-ui_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class NavigationUI {
    public static final NavigationUI INSTANCE = new NavigationUI();
    private static final String TAG = "NavigationUI";

    private NavigationUI() {
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00a0  */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean onNavDestinationSelected(android.view.MenuItem r8, androidx.navigation.NavController r9) throws android.content.res.Resources.NotFoundException {
        /*
            java.lang.String r0 = "item"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "navController"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            androidx.navigation.NavOptions$Builder r0 = new androidx.navigation.NavOptions$Builder
            r0.<init>()
            r1 = 1
            androidx.navigation.NavOptions$Builder r0 = r0.setLaunchSingleTop(r1)
            androidx.navigation.NavOptions$Builder r0 = r0.setRestoreState(r1)
            androidx.navigation.NavDestination r2 = r9.getCurrentDestination()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            androidx.navigation.NavGraph r2 = r2.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            int r3 = r8.getItemId()
            androidx.navigation.NavDestination r2 = r2.findNode(r3)
            boolean r2 = r2 instanceof androidx.navigation.ActivityNavigator.Destination
            if (r2 == 0) goto L4c
        L34:
            int r2 = androidx.navigation.ui.R.anim.nav_default_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r0.setEnterAnim(r2)
            int r3 = androidx.navigation.ui.R.anim.nav_default_exit_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setExitAnim(r3)
            int r3 = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setPopEnterAnim(r3)
            int r3 = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
            r2.setPopExitAnim(r3)
            goto L64
        L4c:
            int r2 = androidx.navigation.ui.R.animator.nav_default_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r0.setEnterAnim(r2)
            int r3 = androidx.navigation.ui.R.animator.nav_default_exit_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setExitAnim(r3)
            int r3 = androidx.navigation.ui.R.animator.nav_default_pop_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setPopEnterAnim(r3)
            int r3 = androidx.navigation.ui.R.animator.nav_default_pop_exit_anim
            r2.setPopExitAnim(r3)
        L64:
            int r2 = r8.getOrder()
            r3 = 196608(0x30000, float:2.75506E-40)
            r2 = r2 & r3
            r3 = 0
            if (r2 != 0) goto L82
        L6f:
            androidx.navigation.NavGraph$Companion r2 = androidx.navigation.NavGraph.INSTANCE
            androidx.navigation.NavGraph r4 = r9.getGraph()
            androidx.navigation.NavDestination r2 = r2.findStartDestination(r4)
            int r2 = r2.getId()
            r0.setPopUpTo(r2, r3, r1)
        L82:
            androidx.navigation.NavOptions r2 = r0.build()
            int r4 = r8.getItemId()     // Catch: java.lang.IllegalArgumentException -> La3
            r5 = 0
            r9.navigate(r4, r5, r2)     // Catch: java.lang.IllegalArgumentException -> La3
            androidx.navigation.NavDestination r4 = r9.getCurrentDestination()     // Catch: java.lang.IllegalArgumentException -> La3
            if (r4 == 0) goto La0
            int r5 = r8.getItemId()     // Catch: java.lang.IllegalArgumentException -> La3
            boolean r4 = matchDestination$navigation_ui_release(r4, r5)     // Catch: java.lang.IllegalArgumentException -> La3
            if (r4 != r1) goto La0
            goto La1
        La0:
            r1 = r3
        La1:
            r3 = r1
            goto Ldd
        La3:
            r1 = move-exception
            androidx.navigation.NavDestination$Companion r4 = androidx.navigation.NavDestination.INSTANCE
            android.content.Context r5 = r9.getContext()
            int r6 = r8.getItemId()
            java.lang.String r4 = r4.getDisplayName(r5, r6)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Ignoring onNavDestinationSelected for MenuItem "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r6 = " as it cannot be found from the current destination "
            java.lang.StringBuilder r5 = r5.append(r6)
            androidx.navigation.NavDestination r6 = r9.getCurrentDestination()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = r1
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.String r7 = "NavigationUI"
            android.util.Log.i(r7, r5, r6)
        Ldd:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.ui.NavigationUI.onNavDestinationSelected(android.view.MenuItem, androidx.navigation.NavController):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00a2  */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean onNavDestinationSelected(android.view.MenuItem r8, androidx.navigation.NavController r9, boolean r10) throws android.content.res.Resources.NotFoundException {
        /*
            java.lang.String r0 = "item"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "navController"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            r0 = r10 ^ 1
            if (r0 == 0) goto Le0
            androidx.navigation.NavOptions$Builder r0 = new androidx.navigation.NavOptions$Builder
            r0.<init>()
            r1 = 1
            androidx.navigation.NavOptions$Builder r0 = r0.setLaunchSingleTop(r1)
            androidx.navigation.NavDestination r2 = r9.getCurrentDestination()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            androidx.navigation.NavGraph r2 = r2.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            int r3 = r8.getItemId()
            androidx.navigation.NavDestination r2 = r2.findNode(r3)
            boolean r2 = r2 instanceof androidx.navigation.ActivityNavigator.Destination
            if (r2 == 0) goto L4c
        L34:
            int r2 = androidx.navigation.ui.R.anim.nav_default_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r0.setEnterAnim(r2)
            int r3 = androidx.navigation.ui.R.anim.nav_default_exit_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setExitAnim(r3)
            int r3 = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setPopEnterAnim(r3)
            int r3 = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
            r2.setPopExitAnim(r3)
            goto L64
        L4c:
            int r2 = androidx.navigation.ui.R.animator.nav_default_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r0.setEnterAnim(r2)
            int r3 = androidx.navigation.ui.R.animator.nav_default_exit_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setExitAnim(r3)
            int r3 = androidx.navigation.ui.R.animator.nav_default_pop_enter_anim
            androidx.navigation.NavOptions$Builder r2 = r2.setPopEnterAnim(r3)
            int r3 = androidx.navigation.ui.R.animator.nav_default_pop_exit_anim
            r2.setPopExitAnim(r3)
        L64:
            int r2 = r8.getOrder()
            r3 = 196608(0x30000, float:2.75506E-40)
            r2 = r2 & r3
            if (r2 != 0) goto L83
            androidx.navigation.NavGraph$Companion r2 = androidx.navigation.NavGraph.INSTANCE
            androidx.navigation.NavGraph r3 = r9.getGraph()
            androidx.navigation.NavDestination r2 = r2.findStartDestination(r3)
            int r3 = r2.getId()
            r4 = 0
            r5 = 0
            r6 = 4
            r7 = 0
            r2 = r0
            androidx.navigation.NavOptions.Builder.setPopUpTo$default(r2, r3, r4, r5, r6, r7)
        L83:
            androidx.navigation.NavOptions r2 = r0.build()
            r3 = 0
            int r4 = r8.getItemId()     // Catch: java.lang.IllegalArgumentException -> La5
            r5 = 0
            r9.navigate(r4, r5, r2)     // Catch: java.lang.IllegalArgumentException -> La5
            androidx.navigation.NavDestination r4 = r9.getCurrentDestination()     // Catch: java.lang.IllegalArgumentException -> La5
            if (r4 == 0) goto La2
            int r5 = r8.getItemId()     // Catch: java.lang.IllegalArgumentException -> La5
            boolean r4 = matchDestination$navigation_ui_release(r4, r5)     // Catch: java.lang.IllegalArgumentException -> La5
            if (r4 != r1) goto La2
            goto La3
        La2:
            r1 = r3
        La3:
            r3 = r1
            goto Ldf
        La5:
            r1 = move-exception
            androidx.navigation.NavDestination$Companion r4 = androidx.navigation.NavDestination.INSTANCE
            android.content.Context r5 = r9.getContext()
            int r6 = r8.getItemId()
            java.lang.String r4 = r4.getDisplayName(r5, r6)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Ignoring onNavDestinationSelected for MenuItem "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r6 = " as it cannot be found from the current destination "
            java.lang.StringBuilder r5 = r5.append(r6)
            androidx.navigation.NavDestination r6 = r9.getCurrentDestination()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = r1
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.String r7 = "NavigationUI"
            android.util.Log.i(r7, r5, r6)
        Ldf:
            return r3
        Le0:
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Leave the saveState parameter out entirely to use the non-experimental version of this API, which saves the state by default"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.ui.NavigationUI.onNavDestinationSelected(android.view.MenuItem, androidx.navigation.NavController, boolean):boolean");
    }

    @JvmStatic
    public static final boolean navigateUp(NavController navController, Openable openableLayout) {
        Intrinsics.checkNotNullParameter(navController, "navController");
        return navigateUp(navController, new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(openableLayout).build());
    }

    @JvmStatic
    public static final boolean navigateUp(NavController navController, AppBarConfiguration configuration) {
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Openable openableLayout = configuration.getOpenableLayout();
        NavDestination currentDestination = navController.getCurrentDestination();
        if (openableLayout != null && currentDestination != null && configuration.isTopLevelDestination(currentDestination)) {
            openableLayout.open();
            return true;
        }
        if (navController.navigateUp()) {
            return true;
        }
        AppBarConfiguration.OnNavigateUpListener fallbackOnNavigateUpListener = configuration.getFallbackOnNavigateUpListener();
        if (fallbackOnNavigateUpListener != null) {
            return fallbackOnNavigateUpListener.onNavigateUp();
        }
        return false;
    }

    @JvmStatic
    public static final void setupActionBarWithNavController(AppCompatActivity activity, NavController navController, Openable openableLayout) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupActionBarWithNavController(activity, navController, new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(openableLayout).build());
    }

    public static /* synthetic */ void setupActionBarWithNavController$default(AppCompatActivity appCompatActivity, NavController navController, AppBarConfiguration appBarConfiguration, int i, Object obj) {
        if ((i & 4) != 0) {
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        }
        setupActionBarWithNavController(appCompatActivity, navController, appBarConfiguration);
    }

    @JvmStatic
    public static final void setupActionBarWithNavController(AppCompatActivity activity, NavController navController, AppBarConfiguration configuration) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        navController.addOnDestinationChangedListener(new ActionBarOnDestinationChangedListener(activity, configuration));
    }

    @JvmStatic
    public static final void setupActionBarWithNavController(AppCompatActivity activity, NavController navController) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupActionBarWithNavController$default(activity, navController, null, 4, null);
    }

    @JvmStatic
    public static final void setupWithNavController(Toolbar toolbar, NavController navController, Openable openableLayout) {
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupWithNavController(toolbar, navController, new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(openableLayout).build());
    }

    public static /* synthetic */ void setupWithNavController$default(Toolbar toolbar, NavController navController, AppBarConfiguration appBarConfiguration, int i, Object obj) {
        if ((i & 4) != 0) {
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        }
        setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @JvmStatic
    public static final void setupWithNavController(Toolbar toolbar, final NavController navController, final AppBarConfiguration configuration) {
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        navController.addOnDestinationChangedListener(new ToolbarOnDestinationChangedListener(toolbar, configuration));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NavigationUI.setupWithNavController$lambda$1(navController, configuration, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupWithNavController$lambda$1(NavController navController, AppBarConfiguration configuration, View it) {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(configuration, "$configuration");
        navigateUp(navController, configuration);
    }

    @JvmStatic
    public static final void setupWithNavController(Toolbar toolbar, NavController navController) {
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupWithNavController$default(toolbar, navController, null, 4, null);
    }

    @JvmStatic
    public static final void setupWithNavController(CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, NavController navController, Openable openableLayout) {
        Intrinsics.checkNotNullParameter(collapsingToolbarLayout, "collapsingToolbarLayout");
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupWithNavController(collapsingToolbarLayout, toolbar, navController, new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(openableLayout).build());
    }

    public static /* synthetic */ void setupWithNavController$default(CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, NavController navController, AppBarConfiguration appBarConfiguration, int i, Object obj) {
        if ((i & 8) != 0) {
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        }
        setupWithNavController(collapsingToolbarLayout, toolbar, navController, appBarConfiguration);
    }

    @JvmStatic
    public static final void setupWithNavController(CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, final NavController navController, final AppBarConfiguration configuration) {
        Intrinsics.checkNotNullParameter(collapsingToolbarLayout, "collapsingToolbarLayout");
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        navController.addOnDestinationChangedListener(new CollapsingToolbarOnDestinationChangedListener(collapsingToolbarLayout, toolbar, configuration));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NavigationUI.setupWithNavController$lambda$2(navController, configuration, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupWithNavController$lambda$2(NavController navController, AppBarConfiguration configuration, View it) {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(configuration, "$configuration");
        navigateUp(navController, configuration);
    }

    @JvmStatic
    public static final void setupWithNavController(CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, NavController navController) {
        Intrinsics.checkNotNullParameter(collapsingToolbarLayout, "collapsingToolbarLayout");
        Intrinsics.checkNotNullParameter(toolbar, "toolbar");
        Intrinsics.checkNotNullParameter(navController, "navController");
        setupWithNavController$default(collapsingToolbarLayout, toolbar, navController, null, 8, null);
    }

    @JvmStatic
    public static final void setupWithNavController(final NavigationView navigationView, final NavController navController) {
        Intrinsics.checkNotNullParameter(navigationView, "navigationView");
        Intrinsics.checkNotNullParameter(navController, "navController");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda3
            @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return NavigationUI.setupWithNavController$lambda$3(navController, navigationView, menuItem);
            }
        });
        final WeakReference weakReference = new WeakReference(navigationView);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() { // from class: androidx.navigation.ui.NavigationUI.setupWithNavController.4
            @Override // androidx.navigation.NavController.OnDestinationChangedListener
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                Intrinsics.checkNotNullParameter(controller, "controller");
                Intrinsics.checkNotNullParameter(destination, "destination");
                NavigationView view = weakReference.get();
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this);
                    return;
                }
                if (destination instanceof FloatingWindow) {
                    return;
                }
                Menu $this$forEach$iv = view.getMenu();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "view.menu");
                int size = $this$forEach$iv.size();
                for (int index$iv = 0; index$iv < size; index$iv++) {
                    MenuItem item = $this$forEach$iv.getItem(index$iv);
                    Intrinsics.checkExpressionValueIsNotNull(item, "getItem(index)");
                    item.setChecked(NavigationUI.matchDestination$navigation_ui_release(destination, item.getItemId()));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupWithNavController$lambda$3(NavController navController, NavigationView navigationView, MenuItem item) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(navigationView, "$navigationView");
        Intrinsics.checkNotNullParameter(item, "item");
        boolean handled = onNavDestinationSelected(item, navController);
        if (handled) {
            ViewParent parent = navigationView.getParent();
            if (parent instanceof Openable) {
                ((Openable) parent).close();
            } else {
                BottomSheetBehavior bottomSheetBehavior = findBottomSheetBehavior(navigationView);
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.setState(5);
                }
            }
        }
        return handled;
    }

    @JvmStatic
    public static final void setupWithNavController(final NavigationView navigationView, final NavController navController, final boolean saveState) {
        Intrinsics.checkNotNullParameter(navigationView, "navigationView");
        Intrinsics.checkNotNullParameter(navController, "navController");
        if (!(!saveState)) {
            throw new IllegalStateException("Leave the saveState parameter out entirely to use the non-experimental version of this API, which saves the state by default".toString());
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda0
            @Override // com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return NavigationUI.setupWithNavController$lambda$5(navController, saveState, navigationView, menuItem);
            }
        });
        final WeakReference weakReference = new WeakReference(navigationView);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() { // from class: androidx.navigation.ui.NavigationUI.setupWithNavController.7
            @Override // androidx.navigation.NavController.OnDestinationChangedListener
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                Intrinsics.checkNotNullParameter(controller, "controller");
                Intrinsics.checkNotNullParameter(destination, "destination");
                NavigationView view = weakReference.get();
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this);
                    return;
                }
                if (destination instanceof FloatingWindow) {
                    return;
                }
                Menu $this$forEach$iv = view.getMenu();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "view.menu");
                int size = $this$forEach$iv.size();
                for (int index$iv = 0; index$iv < size; index$iv++) {
                    MenuItem item = $this$forEach$iv.getItem(index$iv);
                    Intrinsics.checkExpressionValueIsNotNull(item, "getItem(index)");
                    item.setChecked(NavigationUI.matchDestination$navigation_ui_release(destination, item.getItemId()));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupWithNavController$lambda$5(NavController navController, boolean $saveState, NavigationView navigationView, MenuItem item) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(navigationView, "$navigationView");
        Intrinsics.checkNotNullParameter(item, "item");
        boolean handled = onNavDestinationSelected(item, navController, $saveState);
        if (handled) {
            ViewParent parent = navigationView.getParent();
            if (parent instanceof Openable) {
                ((Openable) parent).close();
            } else {
                BottomSheetBehavior bottomSheetBehavior = findBottomSheetBehavior(navigationView);
                if (bottomSheetBehavior != null) {
                    bottomSheetBehavior.setState(5);
                }
            }
        }
        return handled;
    }

    @JvmStatic
    public static final BottomSheetBehavior<?> findBottomSheetBehavior(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            Object parent = view.getParent();
            if (parent instanceof View) {
                return findBottomSheetBehavior((View) parent);
            }
            return null;
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        return null;
    }

    @JvmStatic
    public static final void setupWithNavController(NavigationBarView navigationBarView, final NavController navController) {
        Intrinsics.checkNotNullParameter(navigationBarView, "navigationBarView");
        Intrinsics.checkNotNullParameter(navController, "navController");
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda2
            @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return NavigationUI.setupWithNavController$lambda$6(navController, menuItem);
            }
        });
        final WeakReference weakReference = new WeakReference(navigationBarView);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() { // from class: androidx.navigation.ui.NavigationUI.setupWithNavController.9
            @Override // androidx.navigation.NavController.OnDestinationChangedListener
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                Intrinsics.checkNotNullParameter(controller, "controller");
                Intrinsics.checkNotNullParameter(destination, "destination");
                NavigationBarView view = weakReference.get();
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this);
                    return;
                }
                if (destination instanceof FloatingWindow) {
                    return;
                }
                Menu $this$forEach$iv = view.getMenu();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "view.menu");
                int size = $this$forEach$iv.size();
                for (int index$iv = 0; index$iv < size; index$iv++) {
                    MenuItem item = $this$forEach$iv.getItem(index$iv);
                    Intrinsics.checkExpressionValueIsNotNull(item, "getItem(index)");
                    if (NavigationUI.matchDestination$navigation_ui_release(destination, item.getItemId())) {
                        item.setChecked(true);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupWithNavController$lambda$6(NavController navController, MenuItem item) {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(item, "item");
        return onNavDestinationSelected(item, navController);
    }

    @JvmStatic
    public static final void setupWithNavController(NavigationBarView navigationBarView, final NavController navController, final boolean saveState) {
        Intrinsics.checkNotNullParameter(navigationBarView, "navigationBarView");
        Intrinsics.checkNotNullParameter(navController, "navController");
        if (!(!saveState)) {
            throw new IllegalStateException("Leave the saveState parameter out entirely to use the non-experimental version of this API, which saves the state by default".toString());
        }
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // from class: androidx.navigation.ui.NavigationUI$$ExternalSyntheticLambda5
            @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
            public final boolean onNavigationItemSelected(MenuItem menuItem) {
                return NavigationUI.setupWithNavController$lambda$8(navController, saveState, menuItem);
            }
        });
        final WeakReference weakReference = new WeakReference(navigationBarView);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() { // from class: androidx.navigation.ui.NavigationUI.setupWithNavController.12
            @Override // androidx.navigation.NavController.OnDestinationChangedListener
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                Intrinsics.checkNotNullParameter(controller, "controller");
                Intrinsics.checkNotNullParameter(destination, "destination");
                NavigationBarView view = weakReference.get();
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this);
                    return;
                }
                if (destination instanceof FloatingWindow) {
                    return;
                }
                Menu $this$forEach$iv = view.getMenu();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "view.menu");
                int size = $this$forEach$iv.size();
                for (int index$iv = 0; index$iv < size; index$iv++) {
                    MenuItem item = $this$forEach$iv.getItem(index$iv);
                    Intrinsics.checkExpressionValueIsNotNull(item, "getItem(index)");
                    if (NavigationUI.matchDestination$navigation_ui_release(destination, item.getItemId())) {
                        item.setChecked(true);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupWithNavController$lambda$8(NavController navController, boolean $saveState, MenuItem item) {
        Intrinsics.checkNotNullParameter(navController, "$navController");
        Intrinsics.checkNotNullParameter(item, "item");
        return onNavDestinationSelected(item, navController, $saveState);
    }

    @JvmStatic
    public static final boolean matchDestination$navigation_ui_release(NavDestination $this$matchDestination, int destId) {
        boolean z;
        Intrinsics.checkNotNullParameter($this$matchDestination, "<this>");
        Sequence $this$any$iv = NavDestination.INSTANCE.getHierarchy($this$matchDestination);
        Iterator<NavDestination> it = $this$any$iv.iterator();
        do {
            z = false;
            if (!it.hasNext()) {
                return false;
            }
            Object element$iv = it.next();
            NavDestination it2 = (NavDestination) element$iv;
            if (it2.getId() == destId) {
                z = true;
            }
        } while (!z);
        return true;
    }
}
