package androidx.navigation.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfigurationKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Toolbar.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"setupWithNavController", "", "Landroidx/appcompat/widget/Toolbar;", "navController", "Landroidx/navigation/NavController;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "configuration", "Landroidx/navigation/ui/AppBarConfiguration;", "navigation-ui_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ToolbarKt {
    public static final void setupWithNavController(Toolbar $this$setupWithNavController, NavController navController, DrawerLayout drawerLayout) {
        Intrinsics.checkNotNullParameter($this$setupWithNavController, "<this>");
        Intrinsics.checkNotNullParameter(navController, "navController");
        NavGraph navGraph$iv = navController.getGraph();
        Function0 fallbackOnNavigateUpListener$iv = AppBarConfigurationKt.AnonymousClass1.INSTANCE;
        NavigationUI.setupWithNavController($this$setupWithNavController, navController, new AppBarConfiguration.Builder(navGraph$iv).setOpenableLayout(drawerLayout).setFallbackOnNavigateUpListener(new AppBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0(fallbackOnNavigateUpListener$iv)).build());
    }

    public static /* synthetic */ void setupWithNavController$default(Toolbar toolbar, NavController navController, AppBarConfiguration appBarConfiguration, int i, Object obj) {
        if ((i & 2) != 0) {
            NavGraph navGraph$iv = navController.getGraph();
            Function0 fallbackOnNavigateUpListener$iv = AppBarConfigurationKt.AnonymousClass1.INSTANCE;
            appBarConfiguration = new AppBarConfiguration.Builder(navGraph$iv).setOpenableLayout(null).setFallbackOnNavigateUpListener(new AppBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0(fallbackOnNavigateUpListener$iv)).build();
        }
        setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    public static final void setupWithNavController(Toolbar $this$setupWithNavController, NavController navController, AppBarConfiguration configuration) {
        Intrinsics.checkNotNullParameter($this$setupWithNavController, "<this>");
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        NavigationUI.setupWithNavController($this$setupWithNavController, navController, configuration);
    }
}
