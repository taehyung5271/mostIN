package androidx.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;

/* compiled from: ActivityViewModelLazy.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a1\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0087\b\u001aC\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0010\b\n\u0010\b\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\u00062\u0010\b\n\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006H\u0087\b¨\u0006\n"}, d2 = {"viewModels", "Lkotlin/Lazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/activity/ComponentActivity;", "factoryProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "extrasProducer", "Landroidx/lifecycle/viewmodel/CreationExtras;", "activity-ktx_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ActivityViewModelLazyKt {
    public static /* synthetic */ Lazy viewModels$default(ComponentActivity $this$viewModels_u24default, Function0 factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            factoryProducer = null;
        }
        ActivityViewModelLazyKt$viewModels$factoryPromise$1 factoryPromise = factoryProducer == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels_u24default) : factoryProducer;
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy(Reflection.getOrCreateKotlinClass(ViewModel.class), new AnonymousClass1($this$viewModels_u24default), factoryPromise, new AnonymousClass2($this$viewModels_u24default));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Superseded by viewModels that takes a CreationExtras")
    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> viewModels(ComponentActivity $this$viewModels, Function0<? extends ViewModelProvider.Factory> function0) {
        Function0 factoryPromise = function0 == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels) : function0;
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy(Reflection.getOrCreateKotlinClass(ViewModel.class), new AnonymousClass1($this$viewModels), factoryPromise, new AnonymousClass2($this$viewModels));
    }

    /* compiled from: ActivityViewModelLazy.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelStore;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 176)
    /* renamed from: androidx.activity.ActivityViewModelLazyKt$viewModels$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function0<ViewModelStore> {
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ComponentActivity componentActivity) {
            super(0);
            this.$this_viewModels = componentActivity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModelStore invoke() {
            return this.$this_viewModels.getViewModelStore();
        }
    }

    /* compiled from: ActivityViewModelLazy.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/viewmodel/CreationExtras;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 176)
    /* renamed from: androidx.activity.ActivityViewModelLazyKt$viewModels$2, reason: invalid class name */
    public static final class AnonymousClass2 extends Lambda implements Function0<CreationExtras> {
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ComponentActivity componentActivity) {
            super(0);
            this.$this_viewModels = componentActivity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final CreationExtras invoke() {
            return this.$this_viewModels.getDefaultViewModelCreationExtras();
        }
    }

    public static /* synthetic */ Lazy viewModels$default(ComponentActivity $this$viewModels_u24default, Function0 extrasProducer, Function0 factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            extrasProducer = null;
        }
        if ((i & 2) != 0) {
            factoryProducer = null;
        }
        ActivityViewModelLazyKt$viewModels$factoryPromise$2 factoryPromise = factoryProducer == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$2($this$viewModels_u24default) : factoryProducer;
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy(Reflection.getOrCreateKotlinClass(ViewModel.class), new AnonymousClass3($this$viewModels_u24default), factoryPromise, new AnonymousClass4(extrasProducer, $this$viewModels_u24default));
    }

    public static final /* synthetic */ <VM extends ViewModel> Lazy<VM> viewModels(ComponentActivity $this$viewModels, Function0<? extends CreationExtras> function0, Function0<? extends ViewModelProvider.Factory> function02) {
        Function0 factoryPromise = function02 == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$2($this$viewModels) : function02;
        Intrinsics.reifiedOperationMarker(4, "VM");
        return new ViewModelLazy(Reflection.getOrCreateKotlinClass(ViewModel.class), new AnonymousClass3($this$viewModels), factoryPromise, new AnonymousClass4(function0, $this$viewModels));
    }

    /* compiled from: ActivityViewModelLazy.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelStore;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 176)
    /* renamed from: androidx.activity.ActivityViewModelLazyKt$viewModels$3, reason: invalid class name */
    public static final class AnonymousClass3 extends Lambda implements Function0<ViewModelStore> {
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ComponentActivity componentActivity) {
            super(0);
            this.$this_viewModels = componentActivity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ViewModelStore invoke() {
            return this.$this_viewModels.getViewModelStore();
        }
    }

    /* compiled from: ActivityViewModelLazy.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/lifecycle/viewmodel/CreationExtras;", "VM", "Landroidx/lifecycle/ViewModel;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 176)
    /* renamed from: androidx.activity.ActivityViewModelLazyKt$viewModels$4, reason: invalid class name */
    public static final class AnonymousClass4 extends Lambda implements Function0<CreationExtras> {
        final /* synthetic */ Function0<CreationExtras> $extrasProducer;
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass4(Function0<? extends CreationExtras> function0, ComponentActivity componentActivity) {
            super(0);
            this.$extrasProducer = function0;
            this.$this_viewModels = componentActivity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final CreationExtras invoke() {
            CreationExtras creationExtrasInvoke;
            Function0<CreationExtras> function0 = this.$extrasProducer;
            return (function0 == null || (creationExtrasInvoke = function0.invoke()) == null) ? this.$this_viewModels.getDefaultViewModelCreationExtras() : creationExtrasInvoke;
        }
    }
}
