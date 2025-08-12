package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.TaskStackBuilder;
import androidx.core.os.BundleKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigator;
import androidx.navigation.serialization.RouteSerializerKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MagicApiIntrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.serialization.SerializersKt;

/* compiled from: NavController.kt */
@Metadata(d1 = {"\u0000Æ\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0016\u0018\u0000 Õ\u00012\u00020\u0001:\u0006Õ\u0001Ö\u0001×\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J2\u0010q\u001a\u00020\u00172\u0006\u0010r\u001a\u0002052\b\u0010s\u001a\u0004\u0018\u00010_2\u0006\u0010\u0016\u001a\u00020\b2\u000e\b\u0002\u0010t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\u0010\u0010u\u001a\u00020\u00172\u0006\u0010v\u001a\u00020fH\u0016J\u0015\u0010w\u001a\u000209\"\n\b\u0000\u0010x\u0018\u0001*\u00020\u0001H\u0087\bJ\u001f\u0010w\u001a\u000209\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002HxH\u0007¢\u0006\u0002\u0010zJ\u0012\u0010w\u001a\u0002092\b\b\u0001\u0010{\u001a\u00020\u001eH\u0007J\u0010\u0010w\u001a\u0002092\u0006\u0010y\u001a\u00020\u001fH\u0007J\u0012\u0010|\u001a\u0002092\b\b\u0001\u0010{\u001a\u00020\u001eH\u0003J\u0010\u0010|\u001a\u0002092\u0006\u0010y\u001a\u00020\u001fH\u0003J\b\u0010}\u001a\u00020~H\u0016J\b\u0010\u007f\u001a\u000209H\u0002J\u0012\u0010\u0080\u0001\u001a\u00020\u00172\u0007\u0010\u0081\u0001\u001a\u000209H\u0017J7\u0010\u0082\u0001\u001a\u0002092\u0011\u0010\u0083\u0001\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\\0\u00072\u0007\u0010\u0084\u0001\u001a\u0002052\u0007\u0010\u0085\u0001\u001a\u0002092\u0007\u0010\u0086\u0001\u001a\u000209H\u0002J;\u0010\u0087\u0001\u001a\u0002092\r\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_2\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0002J\u0015\u0010\u008e\u0001\u001a\u0004\u0018\u0001052\b\b\u0001\u0010{\u001a\u00020\u001eH\u0007J\u0013\u0010\u008e\u0001\u001a\u0004\u0018\u0001052\u0006\u0010y\u001a\u00020\u001fH\u0007J\u0015\u0010\u008f\u0001\u001a\u0004\u0018\u00010\u001f2\b\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0002J!\u0010\u0092\u0001\u001a\u00020\u001f\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002HxH\u0002¢\u0006\u0003\u0010\u0093\u0001J\u0016\u0010\u0094\u0001\u001a\u00020\b\"\n\b\u0000\u0010x\u0018\u0001*\u00020\u0001H\u0086\bJ\u001f\u0010\u0094\u0001\u001a\u00020\b\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002Hx¢\u0006\u0003\u0010\u0095\u0001J\u0013\u0010\u0094\u0001\u001a\u00020\b2\b\b\u0001\u0010{\u001a\u00020\u001eH\u0016J\u000f\u0010\u0094\u0001\u001a\u00020\b2\u0006\u0010y\u001a\u00020\u001fJ\u0015\u0010\u0096\u0001\u001a\u00030\u0097\u00012\t\b\u0001\u0010\u0098\u0001\u001a\u00020\u001eH\u0016J\u0015\u0010\u0099\u0001\u001a\u0002092\n\u0010\u009a\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0017J \u0010\u009c\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u000f\u0010\u009d\u0001\u001a\n\u0012\u0004\u0012\u00020!\u0018\u00010\u0019H\u0002J\u001c\u0010\u009e\u0001\u001a\u0002092\u0006\u0010r\u001a\u0002052\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_H\u0002J\u001b\u0010\u009f\u0001\u001a\u00020\u00172\u0007\u0010 \u0001\u001a\u00020\b2\u0007\u0010¡\u0001\u001a\u00020\bH\u0002J=\u0010¢\u0001\u001a\u00020\u0017\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002Hx2\f\b\u0002\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\f\b\u0002\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0007¢\u0006\u0003\u0010£\u0001J=\u0010¢\u0001\u001a\u00020\u0017\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002Hx2\u001a\u0010¤\u0001\u001a\u0015\u0012\u0005\u0012\u00030¥\u0001\u0012\u0004\u0012\u00020\u00170\u0013¢\u0006\u0003\b¦\u0001H\u0007¢\u0006\u0003\u0010§\u0001J\u0013\u0010¢\u0001\u001a\u00020\u00172\b\u0010\u0090\u0001\u001a\u00030¨\u0001H\u0017J\u001f\u0010¢\u0001\u001a\u00020\u00172\b\u0010\u0090\u0001\u001a\u00030¨\u00012\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001H\u0017J+\u0010¢\u0001\u001a\u00020\u00172\b\u0010\u0090\u0001\u001a\u00030¨\u00012\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0017J\u0013\u0010¢\u0001\u001a\u00020\u00172\b\u0010©\u0001\u001a\u00030ª\u0001H\u0017J\u001f\u0010¢\u0001\u001a\u00020\u00172\b\u0010©\u0001\u001a\u00030ª\u00012\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001H\u0017J+\u0010¢\u0001\u001a\u00020\u00172\b\u0010©\u0001\u001a\u00030ª\u00012\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0017J4\u0010¢\u0001\u001a\u00020\u00172\u0006\u0010r\u001a\u0002052\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_2\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0003J\u0013\u0010¢\u0001\u001a\u00020\u00172\b\u0010«\u0001\u001a\u00030¬\u0001H\u0017J\u001f\u0010¢\u0001\u001a\u00020\u00172\b\u0010«\u0001\u001a\u00030¬\u00012\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001H\u0017J\u001d\u0010¢\u0001\u001a\u00020\u00172\b\u0010«\u0001\u001a\u00030¬\u00012\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001H\u0017J\u0014\u0010¢\u0001\u001a\u00020\u00172\t\b\u0001\u0010\u00ad\u0001\u001a\u00020\u001eH\u0017J\u001f\u0010¢\u0001\u001a\u00020\u00172\t\b\u0001\u0010\u00ad\u0001\u001a\u00020\u001e2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_H\u0017J+\u0010¢\u0001\u001a\u00020\u00172\t\b\u0001\u0010\u00ad\u0001\u001a\u00020\u001e2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_2\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u0001H\u0017J7\u0010¢\u0001\u001a\u00020\u00172\t\b\u0001\u0010\u00ad\u0001\u001a\u00020\u001e2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_2\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0017J-\u0010¢\u0001\u001a\u00020\u00172\u0006\u0010y\u001a\u00020\u001f2\f\b\u0002\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\f\b\u0002\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0007J-\u0010¢\u0001\u001a\u00020\u00172\u0006\u0010y\u001a\u00020\u001f2\u001a\u0010¤\u0001\u001a\u0015\u0012\u0005\u0012\u00030¥\u0001\u0012\u0004\u0012\u00020\u00170\u0013¢\u0006\u0003\b¦\u0001H\u0007J\t\u0010®\u0001\u001a\u000209H\u0017J\u0014\u0010¯\u0001\u001a\u00020\u00172\t\u0010°\u0001\u001a\u0004\u0018\u00010_H\u0003J\t\u0010±\u0001\u001a\u000209H\u0017J5\u0010±\u0001\u001a\u000209\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002Hx2\u0007\u0010\u0085\u0001\u001a\u0002092\t\b\u0002\u0010\u0086\u0001\u001a\u000209H\u0007¢\u0006\u0003\u0010²\u0001J*\u0010±\u0001\u001a\u000209\"\n\b\u0000\u0010x\u0018\u0001*\u00020\u00012\u0007\u0010\u0085\u0001\u001a\u0002092\t\b\u0002\u0010\u0086\u0001\u001a\u000209H\u0087\bJ\u001c\u0010±\u0001\u001a\u0002092\b\b\u0001\u0010{\u001a\u00020\u001e2\u0007\u0010\u0085\u0001\u001a\u000209H\u0017J%\u0010±\u0001\u001a\u0002092\b\b\u0001\u0010{\u001a\u00020\u001e2\u0007\u0010\u0085\u0001\u001a\u0002092\u0007\u0010\u0086\u0001\u001a\u000209H\u0017J%\u0010±\u0001\u001a\u0002092\u0006\u0010y\u001a\u00020\u001f2\u0007\u0010\u0085\u0001\u001a\u0002092\t\b\u0002\u0010\u0086\u0001\u001a\u000209H\u0007J'\u0010³\u0001\u001a\u00020\u00172\u0006\u0010j\u001a\u00020\b2\u000e\u0010´\u0001\u001a\t\u0012\u0004\u0012\u00020\u00170µ\u0001H\u0000¢\u0006\u0003\b¶\u0001J5\u0010·\u0001\u001a\u000209\"\b\b\u0000\u0010x*\u00020\u00012\u0006\u0010y\u001a\u0002Hx2\u0007\u0010\u0085\u0001\u001a\u0002092\t\b\u0002\u0010\u0086\u0001\u001a\u000209H\u0002¢\u0006\u0003\u0010²\u0001J'\u0010·\u0001\u001a\u0002092\b\b\u0001\u0010{\u001a\u00020\u001e2\u0007\u0010\u0085\u0001\u001a\u0002092\t\b\u0002\u0010\u0086\u0001\u001a\u000209H\u0003J#\u0010·\u0001\u001a\u0002092\u0006\u0010y\u001a\u00020\u001f2\u0007\u0010\u0085\u0001\u001a\u0002092\u0007\u0010\u0086\u0001\u001a\u000209H\u0002J-\u0010¸\u0001\u001a\u00020\u00172\u0006\u0010j\u001a\u00020\b2\t\b\u0002\u0010\u0086\u0001\u001a\u0002092\u000f\b\u0002\u0010¹\u0001\u001a\b\u0012\u0004\u0012\u00020!0\u0019H\u0002J\u0015\u0010º\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0000¢\u0006\u0003\b»\u0001J\u0011\u0010¼\u0001\u001a\u00020\u00172\u0006\u0010v\u001a\u00020fH\u0016J\u0014\u0010½\u0001\u001a\u00020\u00172\t\u0010¾\u0001\u001a\u0004\u0018\u00010_H\u0017J5\u0010¿\u0001\u001a\u0002092\u0007\u0010À\u0001\u001a\u00020\u001e2\t\u0010\u0089\u0001\u001a\u0004\u0018\u00010_2\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0002J\u0011\u0010¿\u0001\u001a\u0002092\u0006\u0010y\u001a\u00020\u001fH\u0002J\u000b\u0010\u0086\u0001\u001a\u0004\u0018\u00010_H\u0017J\u001b\u0010C\u001a\u00020\u00172\u0006\u0010@\u001a\u00020\f2\t\u0010°\u0001\u001a\u0004\u0018\u00010_H\u0017J\u0013\u0010C\u001a\u00020\u00172\t\b\u0001\u0010Á\u0001\u001a\u00020\u001eH\u0017J\u001e\u0010C\u001a\u00020\u00172\t\b\u0001\u0010Á\u0001\u001a\u00020\u001e2\t\u0010°\u0001\u001a\u0004\u0018\u00010_H\u0017J\u0012\u0010Â\u0001\u001a\u00020\u00172\u0007\u0010Ã\u0001\u001a\u00020PH\u0017J\u0012\u0010Ä\u0001\u001a\u00020\u00172\u0007\u0010Å\u0001\u001a\u00020cH\u0017J\u0013\u0010Æ\u0001\u001a\u00020\u00172\b\u0010Ç\u0001\u001a\u00030È\u0001H\u0017J\t\u0010É\u0001\u001a\u000209H\u0002J\t\u0010Ê\u0001\u001a\u000209H\u0002J\u001a\u0010Ë\u0001\u001a\u0004\u0018\u00010\b2\u0007\u0010 \u0001\u001a\u00020\bH\u0000¢\u0006\u0003\bÌ\u0001J\u000f\u0010Í\u0001\u001a\u00020\u0017H\u0000¢\u0006\u0003\bÎ\u0001J\t\u0010Ï\u0001\u001a\u00020\u0017H\u0002J\"\u0010Ð\u0001\u001a\u0004\u0018\u000105*\u0002052\b\b\u0001\u0010{\u001a\u00020\u001e2\u0007\u0010Ñ\u0001\u001a\u000209H\u0007J\u0013\u0010Ò\u0001\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\b0\u0019H\u0002Jb\u0010Ó\u0001\u001a\u00020\u0017*\n\u0012\u0006\b\u0001\u0012\u0002050\\2\r\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\u0010\u008a\u0001\u001a\u0005\u0018\u00010\u008b\u00012\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u00012$\b\u0002\u0010Ô\u0001\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00170\u0013H\u0002JL\u0010·\u0001\u001a\u00020\u0017*\n\u0012\u0006\b\u0001\u0012\u0002050\\2\u0006\u0010j\u001a\u00020\b2\u0007\u0010\u0086\u0001\u001a\u0002092$\b\u0002\u0010Ô\u0001\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(j\u0012\u0004\u0012\u00020\u00170\u0013H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R+\u0010\u0012\u001a\u001f\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00020\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u00190\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\"\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010#X\u0082\u000e¢\u0006\u0004\n\u0002\u0010%R\u001a\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\"\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070*8GX\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0016\u0010-\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\b01¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0016\u00104\u001a\u0004\u0018\u0001058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u000e\u00108\u001a\u000209X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<R\u000e\u0010=\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u000209X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002090\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010@\u001a\u00020\f2\u0006\u0010@\u001a\u00020\f8W@WX\u0096\u000e¢\u0006\f\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001c\u0010E\u001a\u00020F8@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u0010\u0010K\u001a\u0004\u0018\u00010LX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020NX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010O\u001a\u0004\u0018\u00010PX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010Q\u001a\u00020L8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bT\u0010U\u001a\u0004\bR\u0010SR$\u0010V\u001a\u00020\u000e2\u0006\u0010V\u001a\u00020\u000e8V@WX\u0096\u000e¢\u0006\f\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR&\u0010[\u001a\u001a\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u0002050\\\u0012\b\u0012\u00060]R\u00020\u00000\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010^\u001a\u0004\u0018\u00010_X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010b\u001a\u0004\u0018\u00010cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010d\u001a\b\u0012\u0004\u0012\u00020f0eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010g\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020h0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010i\u001a\u001f\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(j\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010k\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bl\u0010/R\u0010\u0010m\u001a\u0004\u0018\u00010nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010o\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070*¢\u0006\b\n\u0000\u001a\u0004\bp\u0010,¨\u0006Ø\u0001"}, d2 = {"Landroidx/navigation/NavController;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_currentBackStack", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Landroidx/navigation/NavBackStackEntry;", "_currentBackStackEntryFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "_graph", "Landroidx/navigation/NavGraph;", "_navigatorProvider", "Landroidx/navigation/NavigatorProvider;", "_visibleEntries", "activity", "Landroid/app/Activity;", "addToBackStackHandler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "backStackEntry", "", "backQueue", "Lkotlin/collections/ArrayDeque;", "backStackEntriesToDispatch", "", "backStackMap", "", "", "", "backStackStates", "Landroidx/navigation/NavBackStackEntryState;", "backStackToRestore", "", "Landroid/os/Parcelable;", "[Landroid/os/Parcelable;", "childToParentEntries", "getContext", "()Landroid/content/Context;", "currentBackStack", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentBackStack", "()Lkotlinx/coroutines/flow/StateFlow;", "currentBackStackEntry", "getCurrentBackStackEntry", "()Landroidx/navigation/NavBackStackEntry;", "currentBackStackEntryFlow", "Lkotlinx/coroutines/flow/Flow;", "getCurrentBackStackEntryFlow", "()Lkotlinx/coroutines/flow/Flow;", "currentDestination", "Landroidx/navigation/NavDestination;", "getCurrentDestination", "()Landroidx/navigation/NavDestination;", "deepLinkHandled", "", "destinationCountOnBackStack", "getDestinationCountOnBackStack", "()I", "dispatchReentrantCount", "enableOnBackPressedCallback", "entrySavedState", "graph", "getGraph", "()Landroidx/navigation/NavGraph;", "setGraph", "(Landroidx/navigation/NavGraph;)V", "hostLifecycleState", "Landroidx/lifecycle/Lifecycle$State;", "getHostLifecycleState$navigation_runtime_release", "()Landroidx/lifecycle/Lifecycle$State;", "setHostLifecycleState$navigation_runtime_release", "(Landroidx/lifecycle/Lifecycle$State;)V", "inflater", "Landroidx/navigation/NavInflater;", "lifecycleObserver", "Landroidx/lifecycle/LifecycleObserver;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "navInflater", "getNavInflater", "()Landroidx/navigation/NavInflater;", "navInflater$delegate", "Lkotlin/Lazy;", "navigatorProvider", "getNavigatorProvider", "()Landroidx/navigation/NavigatorProvider;", "setNavigatorProvider", "(Landroidx/navigation/NavigatorProvider;)V", "navigatorState", "Landroidx/navigation/Navigator;", "Landroidx/navigation/NavController$NavControllerNavigatorState;", "navigatorStateToRestore", "Landroid/os/Bundle;", "onBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "onBackPressedDispatcher", "Landroidx/activity/OnBackPressedDispatcher;", "onDestinationChangedListeners", "Ljava/util/concurrent/CopyOnWriteArrayList;", "Landroidx/navigation/NavController$OnDestinationChangedListener;", "parentToChildCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "popFromBackStackHandler", "popUpTo", "previousBackStackEntry", "getPreviousBackStackEntry", "viewModel", "Landroidx/navigation/NavControllerViewModel;", "visibleEntries", "getVisibleEntries", "addEntryToBackStack", "node", "finalArgs", "restoredEntries", "addOnDestinationChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "clearBackStack", "T", "route", "(Ljava/lang/Object;)Z", "destinationId", "clearBackStackInternal", "createDeepLink", "Landroidx/navigation/NavDeepLinkBuilder;", "dispatchOnDestinationChanged", "enableOnBackPressed", "enabled", "executePopOperations", "popOperations", "foundDestination", "inclusive", "saveState", "executeRestoreState", "entries", "args", "navOptions", "Landroidx/navigation/NavOptions;", "navigatorExtras", "Landroidx/navigation/Navigator$Extras;", "findDestination", "findInvalidDestinationDisplayNameInDeepLink", "deepLink", "", "generateRouteFilled", "(Ljava/lang/Object;)Ljava/lang/String;", "getBackStackEntry", "(Ljava/lang/Object;)Landroidx/navigation/NavBackStackEntry;", "getViewModelStoreOwner", "Landroidx/lifecycle/ViewModelStoreOwner;", "navGraphId", "handleDeepLink", "intent", "Landroid/content/Intent;", "instantiateBackStack", "backStackState", "launchSingleTopInternal", "linkChildToParent", "child", "parent", "navigate", "(Ljava/lang/Object;Landroidx/navigation/NavOptions;Landroidx/navigation/Navigator$Extras;)V", "builder", "Landroidx/navigation/NavOptionsBuilder;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "Landroid/net/Uri;", "request", "Landroidx/navigation/NavDeepLinkRequest;", "directions", "Landroidx/navigation/NavDirections;", "resId", "navigateUp", "onGraphCreated", "startDestinationArgs", "popBackStack", "(Ljava/lang/Object;ZZ)Z", "popBackStackFromNavigator", "onComplete", "Lkotlin/Function0;", "popBackStackFromNavigator$navigation_runtime_release", "popBackStackInternal", "popEntryFromBackStack", "savedState", "populateVisibleEntries", "populateVisibleEntries$navigation_runtime_release", "removeOnDestinationChangedListener", "restoreState", "navState", "restoreStateInternal", "id", "graphResId", "setLifecycleOwner", "owner", "setOnBackPressedDispatcher", "dispatcher", "setViewModelStore", "viewModelStore", "Landroidx/lifecycle/ViewModelStore;", "tryRelaunchUpToExplicitStack", "tryRelaunchUpToGeneratedStack", "unlinkChildFromParent", "unlinkChildFromParent$navigation_runtime_release", "updateBackStackLifecycle", "updateBackStackLifecycle$navigation_runtime_release", "updateOnBackPressedCallbackEnabled", "findDestinationComprehensive", "searchChildren", "getTopGraph", "navigateInternal", "handler", "Companion", "NavControllerNavigatorState", "OnDestinationChangedListener", "navigation-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public class NavController {
    private static final String KEY_BACK_STACK = "android-support-nav:controller:backStack";
    private static final String KEY_BACK_STACK_DEST_IDS = "android-support-nav:controller:backStackDestIds";
    private static final String KEY_BACK_STACK_IDS = "android-support-nav:controller:backStackIds";
    private static final String KEY_BACK_STACK_STATES_IDS = "android-support-nav:controller:backStackStates";
    private static final String KEY_BACK_STACK_STATES_PREFIX = "android-support-nav:controller:backStackStates:";
    public static final String KEY_DEEP_LINK_ARGS = "android-support-nav:controller:deepLinkArgs";
    public static final String KEY_DEEP_LINK_EXTRAS = "android-support-nav:controller:deepLinkExtras";
    public static final String KEY_DEEP_LINK_HANDLED = "android-support-nav:controller:deepLinkHandled";
    public static final String KEY_DEEP_LINK_IDS = "android-support-nav:controller:deepLinkIds";
    public static final String KEY_DEEP_LINK_INTENT = "android-support-nav:controller:deepLinkIntent";
    private static final String KEY_NAVIGATOR_STATE = "android-support-nav:controller:navigatorState";
    private static final String KEY_NAVIGATOR_STATE_NAMES = "android-support-nav:controller:navigatorState:names";
    private static final String TAG = "NavController";
    private final MutableStateFlow<List<NavBackStackEntry>> _currentBackStack;
    private final MutableSharedFlow<NavBackStackEntry> _currentBackStackEntryFlow;
    private NavGraph _graph;
    private NavigatorProvider _navigatorProvider;
    private final MutableStateFlow<List<NavBackStackEntry>> _visibleEntries;
    private Activity activity;
    private Function1<? super NavBackStackEntry, Unit> addToBackStackHandler;
    private final ArrayDeque<NavBackStackEntry> backQueue;
    private final List<NavBackStackEntry> backStackEntriesToDispatch;
    private final Map<Integer, String> backStackMap;
    private final Map<String, ArrayDeque<NavBackStackEntryState>> backStackStates;
    private Parcelable[] backStackToRestore;
    private final Map<NavBackStackEntry, NavBackStackEntry> childToParentEntries;
    private final Context context;
    private final StateFlow<List<NavBackStackEntry>> currentBackStack;
    private final Flow<NavBackStackEntry> currentBackStackEntryFlow;
    private boolean deepLinkHandled;
    private int dispatchReentrantCount;
    private boolean enableOnBackPressedCallback;
    private final Map<NavBackStackEntry, Boolean> entrySavedState;
    private Lifecycle.State hostLifecycleState;
    private NavInflater inflater;
    private final LifecycleObserver lifecycleObserver;
    private LifecycleOwner lifecycleOwner;

    /* renamed from: navInflater$delegate, reason: from kotlin metadata */
    private final Lazy navInflater;
    private final Map<Navigator<? extends NavDestination>, NavControllerNavigatorState> navigatorState;
    private Bundle navigatorStateToRestore;
    private final OnBackPressedCallback onBackPressedCallback;
    private OnBackPressedDispatcher onBackPressedDispatcher;
    private final CopyOnWriteArrayList<OnDestinationChangedListener> onDestinationChangedListeners;
    private final Map<NavBackStackEntry, AtomicInteger> parentToChildCount;
    private Function1<? super NavBackStackEntry, Unit> popFromBackStackHandler;
    private NavControllerViewModel viewModel;
    private final StateFlow<List<NavBackStackEntry>> visibleEntries;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean deepLinkSaveState = true;

    /* compiled from: NavController.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Landroidx/navigation/NavController$OnDestinationChangedListener;", "", "onDestinationChanged", "", "controller", "Landroidx/navigation/NavController;", "destination", "Landroidx/navigation/NavDestination;", "arguments", "Landroid/os/Bundle;", "navigation-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public interface OnDestinationChangedListener {
        void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments);
    }

    public NavController(Context context) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Sequence $this$firstOrNull$iv = SequencesKt.generateSequence(this.context, new Function1<Context, Context>() { // from class: androidx.navigation.NavController$activity$1
            @Override // kotlin.jvm.functions.Function1
            public final Context invoke(Context it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof ContextWrapper) {
                    return ((ContextWrapper) it).getBaseContext();
                }
                return null;
            }
        });
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                Context it2 = (Context) element$iv;
                if (it2 instanceof Activity) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        this.activity = (Activity) element$iv;
        this.backQueue = new ArrayDeque<>();
        this._currentBackStack = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.currentBackStack = FlowKt.asStateFlow(this._currentBackStack);
        this._visibleEntries = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.visibleEntries = FlowKt.asStateFlow(this._visibleEntries);
        this.childToParentEntries = new LinkedHashMap();
        this.parentToChildCount = new LinkedHashMap();
        this.backStackMap = new LinkedHashMap();
        this.backStackStates = new LinkedHashMap();
        this.onDestinationChangedListeners = new CopyOnWriteArrayList<>();
        this.hostLifecycleState = Lifecycle.State.INITIALIZED;
        this.lifecycleObserver = new LifecycleEventObserver() { // from class: androidx.navigation.NavController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                NavController.lifecycleObserver$lambda$2(this.f$0, lifecycleOwner, event);
            }
        };
        this.onBackPressedCallback = new OnBackPressedCallback() { // from class: androidx.navigation.NavController$onBackPressedCallback$1
            {
                super(false);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                this.this$0.popBackStack();
            }
        };
        this.enableOnBackPressedCallback = true;
        this._navigatorProvider = new NavigatorProvider();
        this.navigatorState = new LinkedHashMap();
        this.entrySavedState = new LinkedHashMap();
        this._navigatorProvider.addNavigator(new NavGraphNavigator(this._navigatorProvider));
        this._navigatorProvider.addNavigator(new ActivityNavigator(this.context));
        this.backStackEntriesToDispatch = new ArrayList();
        this.navInflater = LazyKt.lazy(new Function0<NavInflater>() { // from class: androidx.navigation.NavController$navInflater$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final NavInflater invoke() {
                NavInflater navInflater = this.this$0.inflater;
                return navInflater == null ? new NavInflater(this.this$0.getContext(), this.this$0._navigatorProvider) : navInflater;
            }
        });
        this._currentBackStackEntryFlow = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2, null);
        this.currentBackStackEntryFlow = FlowKt.asSharedFlow(this._currentBackStackEntryFlow);
    }

    public final Context getContext() {
        return this.context;
    }

    public NavGraph getGraph() {
        if (this._graph == null) {
            throw new IllegalStateException("You must call setGraph() before calling getGraph()".toString());
        }
        NavGraph navGraph = this._graph;
        Intrinsics.checkNotNull(navGraph, "null cannot be cast to non-null type androidx.navigation.NavGraph");
        return navGraph;
    }

    public void setGraph(NavGraph graph) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(graph, "graph");
        setGraph(graph, (Bundle) null);
    }

    public final StateFlow<List<NavBackStackEntry>> getCurrentBackStack() {
        return this.currentBackStack;
    }

    public final StateFlow<List<NavBackStackEntry>> getVisibleEntries() {
        return this.visibleEntries;
    }

    private final void linkChildToParent(NavBackStackEntry child, NavBackStackEntry parent) {
        this.childToParentEntries.put(child, parent);
        if (this.parentToChildCount.get(parent) == null) {
            this.parentToChildCount.put(parent, new AtomicInteger(0));
        }
        AtomicInteger atomicInteger = this.parentToChildCount.get(parent);
        Intrinsics.checkNotNull(atomicInteger);
        atomicInteger.incrementAndGet();
    }

    public final NavBackStackEntry unlinkChildFromParent$navigation_runtime_release(NavBackStackEntry child) {
        Intrinsics.checkNotNullParameter(child, "child");
        NavBackStackEntry parent = this.childToParentEntries.remove(child);
        if (parent == null) {
            return null;
        }
        AtomicInteger atomicInteger = this.parentToChildCount.get(parent);
        Integer count = atomicInteger != null ? Integer.valueOf(atomicInteger.decrementAndGet()) : null;
        if (count != null && count.intValue() == 0) {
            NavigatorProvider $this$get$iv = this._navigatorProvider;
            String name$iv = parent.getDestination().getNavigatorName();
            Navigator navGraphNavigator = $this$get$iv.getNavigator(name$iv);
            NavControllerNavigatorState navControllerNavigatorState = this.navigatorState.get(navGraphNavigator);
            if (navControllerNavigatorState != null) {
                navControllerNavigatorState.markTransitionComplete(parent);
            }
            this.parentToChildCount.remove(parent);
        }
        return parent;
    }

    public final void setHostLifecycleState$navigation_runtime_release(Lifecycle.State state) {
        Intrinsics.checkNotNullParameter(state, "<set-?>");
        this.hostLifecycleState = state;
    }

    public final Lifecycle.State getHostLifecycleState$navigation_runtime_release() {
        if (this.lifecycleOwner == null) {
            return Lifecycle.State.CREATED;
        }
        return this.hostLifecycleState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lifecycleObserver$lambda$2(NavController this$0, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(lifecycleOwner, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(event, "event");
        this$0.hostLifecycleState = event.getTargetState();
        if (this$0._graph != null) {
            Iterator it = this$0.backQueue.iterator();
            while (it.hasNext()) {
                NavBackStackEntry entry = (NavBackStackEntry) it.next();
                entry.handleLifecycleEvent(event);
            }
        }
    }

    /* renamed from: getNavigatorProvider, reason: from getter */
    public NavigatorProvider get_navigatorProvider() {
        return this._navigatorProvider;
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        Intrinsics.checkNotNullParameter(navigatorProvider, "navigatorProvider");
        if (!this.backQueue.isEmpty()) {
            throw new IllegalStateException("NavigatorProvider must be set before setGraph call".toString());
        }
        this._navigatorProvider = navigatorProvider;
    }

    static /* synthetic */ void navigateInternal$default(NavController navController, Navigator navigator, List list, NavOptions navOptions, Navigator.Extras extras, Function1 function1, int i, Object obj) {
        Function1 function12;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: navigateInternal");
        }
        if ((i & 8) == 0) {
            function12 = function1;
        } else {
            function12 = new Function1<NavBackStackEntry, Unit>() { // from class: androidx.navigation.NavController.navigateInternal.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NavBackStackEntry navBackStackEntry) {
                    invoke2(navBackStackEntry);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NavBackStackEntry it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            };
        }
        navController.navigateInternal(navigator, list, navOptions, extras, function12);
    }

    private final void navigateInternal(Navigator<? extends NavDestination> navigator, List<NavBackStackEntry> list, NavOptions navOptions, Navigator.Extras navigatorExtras, Function1<? super NavBackStackEntry, Unit> function1) {
        this.addToBackStackHandler = function1;
        navigator.navigate(list, navOptions, navigatorExtras);
        this.addToBackStackHandler = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void popBackStackInternal$default(NavController navController, Navigator navigator, NavBackStackEntry navBackStackEntry, boolean z, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStackInternal");
        }
        if ((i & 4) != 0) {
            function1 = new Function1<NavBackStackEntry, Unit>() { // from class: androidx.navigation.NavController.popBackStackInternal.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NavBackStackEntry navBackStackEntry2) {
                    invoke2(navBackStackEntry2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NavBackStackEntry it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            };
        }
        navController.popBackStackInternal(navigator, navBackStackEntry, z, function1);
    }

    private final void popBackStackInternal(Navigator<? extends NavDestination> navigator, NavBackStackEntry popUpTo, boolean saveState, Function1<? super NavBackStackEntry, Unit> function1) {
        this.popFromBackStackHandler = function1;
        navigator.popBackStack(popUpTo, saveState);
        this.popFromBackStackHandler = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NavController.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001a\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0019\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Landroidx/navigation/NavController$NavControllerNavigatorState;", "Landroidx/navigation/NavigatorState;", "navigator", "Landroidx/navigation/Navigator;", "Landroidx/navigation/NavDestination;", "(Landroidx/navigation/NavController;Landroidx/navigation/Navigator;)V", "getNavigator", "()Landroidx/navigation/Navigator;", "addInternal", "", "backStackEntry", "Landroidx/navigation/NavBackStackEntry;", "createBackStackEntry", "destination", "arguments", "Landroid/os/Bundle;", "markTransitionComplete", "entry", "pop", "popUpTo", "saveState", "", "popWithTransition", "prepareForTransition", "push", "navigation-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    final class NavControllerNavigatorState extends NavigatorState {
        private final Navigator<? extends NavDestination> navigator;
        final /* synthetic */ NavController this$0;

        public NavControllerNavigatorState(NavController this$0, Navigator<? extends NavDestination> navigator) {
            Intrinsics.checkNotNullParameter(navigator, "navigator");
            this.this$0 = this$0;
            this.navigator = navigator;
        }

        public final Navigator<? extends NavDestination> getNavigator() {
            return this.navigator;
        }

        @Override // androidx.navigation.NavigatorState
        public void push(NavBackStackEntry backStackEntry) {
            Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
            NavigatorProvider $this$get$iv = this.this$0._navigatorProvider;
            String name$iv = backStackEntry.getDestination().getNavigatorName();
            Navigator destinationNavigator = $this$get$iv.getNavigator(name$iv);
            if (Intrinsics.areEqual(destinationNavigator, this.navigator)) {
                Function1 handler = this.this$0.addToBackStackHandler;
                if (handler != null) {
                    handler.invoke(backStackEntry);
                    addInternal(backStackEntry);
                    return;
                } else {
                    Log.i(NavController.TAG, "Ignoring add of destination " + backStackEntry.getDestination() + " outside of the call to navigate(). ");
                    return;
                }
            }
            Object obj = this.this$0.navigatorState.get(destinationNavigator);
            if (obj == null) {
                throw new IllegalStateException(("NavigatorBackStack for " + backStackEntry.getDestination().getNavigatorName() + " should already be created").toString());
            }
            NavControllerNavigatorState navigatorBackStack = (NavControllerNavigatorState) obj;
            navigatorBackStack.push(backStackEntry);
        }

        public final void addInternal(NavBackStackEntry backStackEntry) {
            Intrinsics.checkNotNullParameter(backStackEntry, "backStackEntry");
            super.push(backStackEntry);
        }

        @Override // androidx.navigation.NavigatorState
        public NavBackStackEntry createBackStackEntry(NavDestination destination, Bundle arguments) {
            Intrinsics.checkNotNullParameter(destination, "destination");
            return NavBackStackEntry.Companion.create$default(NavBackStackEntry.INSTANCE, this.this$0.getContext(), destination, arguments, this.this$0.getHostLifecycleState$navigation_runtime_release(), this.this$0.viewModel, null, null, 96, null);
        }

        @Override // androidx.navigation.NavigatorState
        public void pop(final NavBackStackEntry popUpTo, final boolean saveState) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(popUpTo, "popUpTo");
            NavigatorProvider $this$get$iv = this.this$0._navigatorProvider;
            String name$iv = popUpTo.getDestination().getNavigatorName();
            Navigator destinationNavigator = $this$get$iv.getNavigator(name$iv);
            this.this$0.entrySavedState.put(popUpTo, Boolean.valueOf(saveState));
            if (Intrinsics.areEqual(destinationNavigator, this.navigator)) {
                Function1 handler = this.this$0.popFromBackStackHandler;
                if (handler != null) {
                    handler.invoke(popUpTo);
                    super.pop(popUpTo, saveState);
                    return;
                } else {
                    this.this$0.popBackStackFromNavigator$navigation_runtime_release(popUpTo, new Function0<Unit>() { // from class: androidx.navigation.NavController$NavControllerNavigatorState$pop$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            super/*androidx.navigation.NavigatorState*/.pop(popUpTo, saveState);
                        }
                    });
                    return;
                }
            }
            Object obj = this.this$0.navigatorState.get(destinationNavigator);
            Intrinsics.checkNotNull(obj);
            ((NavControllerNavigatorState) obj).pop(popUpTo, saveState);
        }

        @Override // androidx.navigation.NavigatorState
        public void popWithTransition(NavBackStackEntry popUpTo, boolean saveState) {
            Intrinsics.checkNotNullParameter(popUpTo, "popUpTo");
            super.popWithTransition(popUpTo, saveState);
        }

        @Override // androidx.navigation.NavigatorState
        public void markTransitionComplete(NavBackStackEntry entry) {
            NavControllerViewModel navControllerViewModel;
            Intrinsics.checkNotNullParameter(entry, "entry");
            boolean z = true;
            boolean savedState = Intrinsics.areEqual(this.this$0.entrySavedState.get(entry), (Object) true);
            super.markTransitionComplete(entry);
            this.this$0.entrySavedState.remove(entry);
            if (!this.this$0.backQueue.contains(entry)) {
                this.this$0.unlinkChildFromParent$navigation_runtime_release(entry);
                if (entry.getLifecycle().getState().isAtLeast(Lifecycle.State.CREATED)) {
                    entry.setMaxLifecycle(Lifecycle.State.DESTROYED);
                }
                Iterable $this$none$iv = this.this$0.backQueue;
                if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                    Iterator it = $this$none$iv.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object element$iv = it.next();
                        NavBackStackEntry it2 = (NavBackStackEntry) element$iv;
                        if (Intrinsics.areEqual(it2.getId(), entry.getId())) {
                            z = false;
                            break;
                        }
                    }
                }
                if (z && !savedState && (navControllerViewModel = this.this$0.viewModel) != null) {
                    navControllerViewModel.clear(entry.getId());
                }
                this.this$0.updateBackStackLifecycle$navigation_runtime_release();
                this.this$0._visibleEntries.tryEmit(this.this$0.populateVisibleEntries$navigation_runtime_release());
                return;
            }
            if (!getIsNavigating()) {
                this.this$0.updateBackStackLifecycle$navigation_runtime_release();
                this.this$0._currentBackStack.tryEmit(CollectionsKt.toMutableList((Collection) this.this$0.backQueue));
                this.this$0._visibleEntries.tryEmit(this.this$0.populateVisibleEntries$navigation_runtime_release());
            }
        }

        @Override // androidx.navigation.NavigatorState
        public void prepareForTransition(NavBackStackEntry entry) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            super.prepareForTransition(entry);
            if (this.this$0.backQueue.contains(entry)) {
                entry.setMaxLifecycle(Lifecycle.State.STARTED);
                return;
            }
            throw new IllegalStateException("Cannot transition entry that is not in the back stack");
        }
    }

    public void addOnDestinationChangedListener(OnDestinationChangedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onDestinationChangedListeners.add(listener);
        if (!this.backQueue.isEmpty()) {
            NavBackStackEntry backStackEntry = this.backQueue.last();
            listener.onDestinationChanged(this, backStackEntry.getDestination(), backStackEntry.getArguments());
        }
    }

    public void removeOnDestinationChangedListener(OnDestinationChangedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onDestinationChangedListeners.remove(listener);
    }

    public boolean popBackStack() {
        if (this.backQueue.isEmpty()) {
            return false;
        }
        NavDestination currentDestination = getCurrentDestination();
        Intrinsics.checkNotNull(currentDestination);
        return popBackStack(currentDestination.getId(), true);
    }

    public boolean popBackStack(int destinationId, boolean inclusive) {
        return popBackStack(destinationId, inclusive, false);
    }

    public boolean popBackStack(int destinationId, boolean inclusive, boolean saveState) throws Resources.NotFoundException {
        boolean popped = popBackStackInternal(destinationId, inclusive, saveState);
        return popped && dispatchOnDestinationChanged();
    }

    public static /* synthetic */ boolean popBackStack$default(NavController navController, String str, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStack");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return navController.popBackStack(str, z, z2);
    }

    public final boolean popBackStack(String route, boolean inclusive, boolean saveState) {
        Intrinsics.checkNotNullParameter(route, "route");
        boolean popped = popBackStackInternal(route, inclusive, saveState);
        return popped && dispatchOnDestinationChanged();
    }

    public final boolean popBackStack(String route, boolean inclusive) {
        Intrinsics.checkNotNullParameter(route, "route");
        return popBackStack$default(this, route, inclusive, false, 4, (Object) null);
    }

    public static /* synthetic */ boolean popBackStack$default(NavController $this, boolean inclusive, boolean saveState, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStack");
        }
        if ((i & 2) != 0) {
            saveState = false;
        }
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        int id = RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null));
        if ($this.findDestinationComprehensive($this.getGraph(), id, true) == null) {
            StringBuilder sbAppend = new StringBuilder().append("Destination with route ");
            Intrinsics.reifiedOperationMarker(4, "T");
            throw new IllegalArgumentException(sbAppend.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).append(" cannot be found in navigation graph ").append($this.getGraph()).toString().toString());
        }
        return $this.popBackStack(id, inclusive, saveState);
    }

    public final /* synthetic */ <T> boolean popBackStack(boolean inclusive, boolean saveState) {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        int id = RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null));
        if (findDestinationComprehensive(getGraph(), id, true) == null) {
            StringBuilder sbAppend = new StringBuilder().append("Destination with route ");
            Intrinsics.reifiedOperationMarker(4, "T");
            throw new IllegalArgumentException(sbAppend.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).append(" cannot be found in navigation graph ").append(getGraph()).toString().toString());
        }
        return popBackStack(id, inclusive, saveState);
    }

    public static /* synthetic */ boolean popBackStack$default(NavController navController, Object obj, boolean z, boolean z2, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStack");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return navController.popBackStack((NavController) obj, z, z2);
    }

    public final <T> boolean popBackStack(T route, boolean inclusive, boolean saveState) {
        Intrinsics.checkNotNullParameter(route, "route");
        boolean popped = popBackStackInternal((NavController) route, inclusive, saveState);
        return popped && dispatchOnDestinationChanged();
    }

    public final <T> boolean popBackStack(T route, boolean inclusive) {
        Intrinsics.checkNotNullParameter(route, "route");
        return popBackStack$default(this, (Object) route, inclusive, false, 4, (Object) null);
    }

    static /* synthetic */ boolean popBackStackInternal$default(NavController navController, int i, boolean z, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStackInternal");
        }
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        return navController.popBackStackInternal(i, z, z2);
    }

    private final boolean popBackStackInternal(int destinationId, boolean inclusive, boolean saveState) throws Resources.NotFoundException {
        if (this.backQueue.isEmpty()) {
            return false;
        }
        List popOperations = new ArrayList();
        Iterator iterator = CollectionsKt.reversed(this.backQueue).iterator();
        NavDestination foundDestination = null;
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            NavDestination destination = ((NavBackStackEntry) iterator.next()).getDestination();
            Navigator navigator = this._navigatorProvider.getNavigator(destination.getNavigatorName());
            if (inclusive || destination.getId() != destinationId) {
                popOperations.add(navigator);
            }
            if (destination.getId() == destinationId) {
                foundDestination = destination;
                break;
            }
        }
        if (foundDestination == null) {
            String destinationName = NavDestination.INSTANCE.getDisplayName(this.context, destinationId);
            Log.i(TAG, "Ignoring popBackStack to destination " + destinationName + " as it was not found on the current back stack");
            return false;
        }
        return executePopOperations(popOperations, foundDestination, inclusive, saveState);
    }

    static /* synthetic */ boolean popBackStackInternal$default(NavController navController, Object obj, boolean z, boolean z2, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popBackStackInternal");
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return navController.popBackStackInternal((NavController) obj, z, z2);
    }

    private final <T> boolean popBackStackInternal(T route, boolean inclusive, boolean saveState) {
        String finalRoute = generateRouteFilled(route);
        return popBackStackInternal(finalRoute, inclusive, saveState);
    }

    private final boolean popBackStackInternal(String route, boolean inclusive, boolean saveState) {
        Object element$iv;
        if (this.backQueue.isEmpty()) {
            return false;
        }
        List popOperations = new ArrayList();
        List $this$lastOrNull$iv = this.backQueue;
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
                element$iv = iterator$iv.previous();
                NavBackStackEntry entry = (NavBackStackEntry) element$iv;
                boolean hasRoute = entry.getDestination().hasRoute(route, entry.getArguments());
                if (inclusive || !hasRoute) {
                    Navigator navigator = this._navigatorProvider.getNavigator(entry.getDestination().getNavigatorName());
                    popOperations.add(navigator);
                }
                if (hasRoute) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) element$iv;
        NavDestination foundDestination = navBackStackEntry != null ? navBackStackEntry.getDestination() : null;
        if (foundDestination == null) {
            Log.i(TAG, "Ignoring popBackStack to route " + route + " as it was not found on the current back stack");
            return false;
        }
        return executePopOperations(popOperations, foundDestination, inclusive, saveState);
    }

    private final boolean executePopOperations(List<? extends Navigator<?>> popOperations, NavDestination foundDestination, boolean inclusive, final boolean saveState) {
        final Ref.BooleanRef popped = new Ref.BooleanRef();
        final ArrayDeque savedState = new ArrayDeque();
        for (Navigator navigator : popOperations) {
            final Ref.BooleanRef receivedPop = new Ref.BooleanRef();
            popBackStackInternal(navigator, this.backQueue.last(), saveState, new Function1<NavBackStackEntry, Unit>() { // from class: androidx.navigation.NavController.executePopOperations.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NavBackStackEntry navBackStackEntry) {
                    invoke2(navBackStackEntry);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NavBackStackEntry entry) {
                    Intrinsics.checkNotNullParameter(entry, "entry");
                    receivedPop.element = true;
                    popped.element = true;
                    this.popEntryFromBackStack(entry, saveState, savedState);
                }
            });
            if (!receivedPop.element) {
                break;
            }
        }
        if (saveState) {
            if (!inclusive) {
                Sequence $this$forEach$iv = SequencesKt.takeWhile(SequencesKt.generateSequence(foundDestination, new Function1<NavDestination, NavDestination>() { // from class: androidx.navigation.NavController.executePopOperations.2
                    @Override // kotlin.jvm.functions.Function1
                    public final NavDestination invoke(NavDestination destination) {
                        Intrinsics.checkNotNullParameter(destination, "destination");
                        NavGraph parent = destination.getParent();
                        boolean z = false;
                        if (parent != null && parent.getStartDestId() == destination.getId()) {
                            z = true;
                        }
                        if (z) {
                            return destination.getParent();
                        }
                        return null;
                    }
                }), new Function1<NavDestination, Boolean>() { // from class: androidx.navigation.NavController.executePopOperations.3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(NavDestination destination) {
                        Intrinsics.checkNotNullParameter(destination, "destination");
                        return Boolean.valueOf(!NavController.this.backStackMap.containsKey(Integer.valueOf(destination.getId())));
                    }
                });
                for (Object element$iv : $this$forEach$iv) {
                    NavDestination destination = (NavDestination) element$iv;
                    Map<Integer, String> map = this.backStackMap;
                    Integer numValueOf = Integer.valueOf(destination.getId());
                    NavBackStackEntryState navBackStackEntryStateFirstOrNull = savedState.firstOrNull();
                    map.put(numValueOf, navBackStackEntryStateFirstOrNull != null ? navBackStackEntryStateFirstOrNull.getId() : null);
                }
            }
            if (!savedState.isEmpty()) {
                NavBackStackEntryState firstState = savedState.first();
                NavDestination firstStateDestination = findDestination(firstState.getDestinationId());
                Sequence $this$forEach$iv2 = SequencesKt.takeWhile(SequencesKt.generateSequence(firstStateDestination, new Function1<NavDestination, NavDestination>() { // from class: androidx.navigation.NavController.executePopOperations.5
                    @Override // kotlin.jvm.functions.Function1
                    public final NavDestination invoke(NavDestination destination2) {
                        Intrinsics.checkNotNullParameter(destination2, "destination");
                        NavGraph parent = destination2.getParent();
                        boolean z = false;
                        if (parent != null && parent.getStartDestId() == destination2.getId()) {
                            z = true;
                        }
                        if (z) {
                            return destination2.getParent();
                        }
                        return null;
                    }
                }), new Function1<NavDestination, Boolean>() { // from class: androidx.navigation.NavController.executePopOperations.6
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(NavDestination destination2) {
                        Intrinsics.checkNotNullParameter(destination2, "destination");
                        return Boolean.valueOf(!NavController.this.backStackMap.containsKey(Integer.valueOf(destination2.getId())));
                    }
                });
                for (Object element$iv2 : $this$forEach$iv2) {
                    NavDestination destination2 = (NavDestination) element$iv2;
                    this.backStackMap.put(Integer.valueOf(destination2.getId()), firstState.getId());
                }
                if (this.backStackMap.values().contains(firstState.getId())) {
                    this.backStackStates.put(firstState.getId(), savedState);
                }
            }
        }
        updateOnBackPressedCallbackEnabled();
        return popped.element;
    }

    public final void popBackStackFromNavigator$navigation_runtime_release(NavBackStackEntry popUpTo, Function0<Unit> onComplete) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(popUpTo, "popUpTo");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        int popIndex = this.backQueue.indexOf(popUpTo);
        if (popIndex < 0) {
            Log.i(TAG, "Ignoring pop of " + popUpTo + " as it was not found on the current back stack");
            return;
        }
        if (popIndex + 1 != this.backQueue.size()) {
            popBackStackInternal(this.backQueue.get(popIndex + 1).getDestination().getId(), true, false);
        }
        popEntryFromBackStack$default(this, popUpTo, false, null, 6, null);
        onComplete.invoke();
        updateOnBackPressedCallbackEnabled();
        dispatchOnDestinationChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void popEntryFromBackStack$default(NavController navController, NavBackStackEntry navBackStackEntry, boolean z, ArrayDeque arrayDeque, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: popEntryFromBackStack");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            arrayDeque = new ArrayDeque();
        }
        navController.popEntryFromBackStack(navBackStackEntry, z, arrayDeque);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void popEntryFromBackStack(NavBackStackEntry popUpTo, boolean saveState, ArrayDeque<NavBackStackEntryState> savedState) {
        NavControllerViewModel navControllerViewModel;
        StateFlow<Set<NavBackStackEntry>> transitionsInProgress;
        Set<NavBackStackEntry> value;
        NavBackStackEntry entry = this.backQueue.last();
        if (!Intrinsics.areEqual(entry, popUpTo)) {
            throw new IllegalStateException(("Attempted to pop " + popUpTo.getDestination() + ", which is not the top of the back stack (" + entry.getDestination() + ')').toString());
        }
        CollectionsKt.removeLast(this.backQueue);
        Navigator navigator = get_navigatorProvider().getNavigator(entry.getDestination().getNavigatorName());
        NavControllerNavigatorState state = this.navigatorState.get(navigator);
        boolean transitioning = true;
        if (!((state == null || (transitionsInProgress = state.getTransitionsInProgress()) == null || (value = transitionsInProgress.getValue()) == null || !value.contains(entry)) ? false : true) && !this.parentToChildCount.containsKey(entry)) {
            transitioning = false;
        }
        if (entry.getLifecycle().getState().isAtLeast(Lifecycle.State.CREATED)) {
            if (saveState) {
                entry.setMaxLifecycle(Lifecycle.State.CREATED);
                savedState.addFirst(new NavBackStackEntryState(entry));
            }
            if (!transitioning) {
                entry.setMaxLifecycle(Lifecycle.State.DESTROYED);
                unlinkChildFromParent$navigation_runtime_release(entry);
            } else {
                entry.setMaxLifecycle(Lifecycle.State.CREATED);
            }
        }
        if (saveState || transitioning || (navControllerViewModel = this.viewModel) == null) {
            return;
        }
        navControllerViewModel.clear(entry.getId());
    }

    public final boolean clearBackStack(String route) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        boolean cleared = clearBackStackInternal(route);
        return cleared && dispatchOnDestinationChanged();
    }

    public final boolean clearBackStack(int destinationId) throws Resources.NotFoundException {
        boolean cleared = clearBackStackInternal(destinationId);
        return cleared && dispatchOnDestinationChanged();
    }

    public final /* synthetic */ <T> boolean clearBackStack() {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        return clearBackStack(RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null)));
    }

    public final <T> boolean clearBackStack(T route) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        String finalRoute = generateRouteFilled(route);
        boolean cleared = clearBackStackInternal(finalRoute);
        return cleared && dispatchOnDestinationChanged();
    }

    private final boolean clearBackStackInternal(int destinationId) throws Resources.NotFoundException {
        Iterable $this$forEach$iv = this.navigatorState.values();
        for (Object element$iv : $this$forEach$iv) {
            NavControllerNavigatorState state = (NavControllerNavigatorState) element$iv;
            state.setNavigating(true);
        }
        boolean restored = restoreStateInternal(destinationId, null, NavOptionsBuilderKt.navOptions(new Function1<NavOptionsBuilder, Unit>() { // from class: androidx.navigation.NavController$clearBackStackInternal$restored$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                invoke2(navOptionsBuilder);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(NavOptionsBuilder navOptions) {
                Intrinsics.checkNotNullParameter(navOptions, "$this$navOptions");
                navOptions.setRestoreState(true);
            }
        }), null);
        Iterable $this$forEach$iv2 = this.navigatorState.values();
        for (Object element$iv2 : $this$forEach$iv2) {
            NavControllerNavigatorState state2 = (NavControllerNavigatorState) element$iv2;
            state2.setNavigating(false);
        }
        return restored && popBackStackInternal(destinationId, true, false);
    }

    private final boolean clearBackStackInternal(String route) throws Resources.NotFoundException {
        Iterable $this$forEach$iv = this.navigatorState.values();
        for (Object element$iv : $this$forEach$iv) {
            NavControllerNavigatorState state = (NavControllerNavigatorState) element$iv;
            state.setNavigating(true);
        }
        boolean restored = restoreStateInternal(route);
        Iterable $this$forEach$iv2 = this.navigatorState.values();
        for (Object element$iv2 : $this$forEach$iv2) {
            NavControllerNavigatorState state2 = (NavControllerNavigatorState) element$iv2;
            state2.setNavigating(false);
        }
        return restored && popBackStackInternal(route, true, false);
    }

    public boolean navigateUp() {
        Intent intent;
        if (getDestinationCountOnBackStack() == 1) {
            Activity activity = this.activity;
            Bundle extras = (activity == null || (intent = activity.getIntent()) == null) ? null : intent.getExtras();
            if ((extras != null ? extras.getIntArray(KEY_DEEP_LINK_IDS) : null) != null) {
                return tryRelaunchUpToExplicitStack();
            }
            return tryRelaunchUpToGeneratedStack();
        }
        return popBackStack();
    }

    private final boolean tryRelaunchUpToExplicitStack() throws Resources.NotFoundException {
        if (!this.deepLinkHandled) {
            return false;
        }
        Activity activity = this.activity;
        Intrinsics.checkNotNull(activity);
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        Intrinsics.checkNotNull(extras);
        int[] intArray = extras.getIntArray(KEY_DEEP_LINK_IDS);
        Intrinsics.checkNotNull(intArray);
        List deepLinkIds = ArraysKt.toMutableList(intArray);
        ArrayList deepLinkArgs = extras.getParcelableArrayList(KEY_DEEP_LINK_ARGS);
        int leafDestinationId = ((Number) CollectionsKt.removeLast(deepLinkIds)).intValue();
        if (deepLinkArgs != null) {
        }
        if (deepLinkIds.isEmpty()) {
            return false;
        }
        NavDestination $this$tryRelaunchUpToExplicitStack_u24lambda_u2413 = findDestinationComprehensive(getGraph(), leafDestinationId, false);
        if ($this$tryRelaunchUpToExplicitStack_u24lambda_u2413 instanceof NavGraph) {
            leafDestinationId = NavGraph.INSTANCE.findStartDestination((NavGraph) $this$tryRelaunchUpToExplicitStack_u24lambda_u2413).getId();
        }
        NavDestination currentDestination = getCurrentDestination();
        if (!(currentDestination != null && leafDestinationId == currentDestination.getId())) {
            return false;
        }
        NavDeepLinkBuilder navDeepLinkBuilder = createDeepLink();
        Bundle arguments = BundleKt.bundleOf(TuplesKt.to(KEY_DEEP_LINK_INTENT, intent));
        Bundle it = extras.getBundle(KEY_DEEP_LINK_EXTRAS);
        if (it != null) {
            arguments.putAll(it);
        }
        navDeepLinkBuilder.setArguments(arguments);
        List $this$forEachIndexed$iv = deepLinkIds;
        int index = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index$iv = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            int deepLinkId = ((Number) item$iv).intValue();
            navDeepLinkBuilder.addDestination(deepLinkId, deepLinkArgs != null ? (Bundle) deepLinkArgs.get(index) : null);
            index = index$iv;
        }
        navDeepLinkBuilder.createTaskStackBuilder().startActivities();
        Activity activity2 = this.activity;
        if (activity2 != null) {
            activity2.finish();
            return true;
        }
        return true;
    }

    private final boolean tryRelaunchUpToGeneratedStack() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        NavDestination currentDestination = getCurrentDestination();
        Intrinsics.checkNotNull(currentDestination);
        int destId = currentDestination.getId();
        for (NavGraph parent = currentDestination.getParent(); parent != null; parent = parent.getParent()) {
            if (parent.getStartDestId() != destId) {
                Bundle args = new Bundle();
                if (this.activity != null) {
                    Activity activity = this.activity;
                    Intrinsics.checkNotNull(activity);
                    if (activity.getIntent() != null) {
                        Activity activity2 = this.activity;
                        Intrinsics.checkNotNull(activity2);
                        Uri data = activity2.getIntent().getData();
                        if (data != null) {
                            Activity activity3 = this.activity;
                            Intrinsics.checkNotNull(activity3);
                            args.putParcelable(KEY_DEEP_LINK_INTENT, activity3.getIntent());
                            NavGraph currGraph = getTopGraph(this.backQueue);
                            Activity activity4 = this.activity;
                            Intrinsics.checkNotNull(activity4);
                            Intent intent = activity4.getIntent();
                            Intrinsics.checkNotNullExpressionValue(intent, "activity!!.intent");
                            NavDestination.DeepLinkMatch matchingDeepLink = currGraph.matchDeepLinkComprehensive(new NavDeepLinkRequest(intent), true, true, currGraph);
                            if ((matchingDeepLink != null ? matchingDeepLink.getMatchingArgs() : null) != null) {
                                Bundle destinationArgs = matchingDeepLink.getDestination().addInDefaultArgs(matchingDeepLink.getMatchingArgs());
                                args.putAll(destinationArgs);
                            }
                        }
                    }
                }
                TaskStackBuilder parentIntents = NavDeepLinkBuilder.setDestination$default(new NavDeepLinkBuilder(this), parent.getId(), (Bundle) null, 2, (Object) null).setArguments(args).createTaskStackBuilder();
                parentIntents.startActivities();
                Activity activity5 = this.activity;
                if (activity5 != null) {
                    activity5.finish();
                }
                return true;
            }
            destId = parent.getId();
        }
        return false;
    }

    private final int getDestinationCountOnBackStack() {
        Iterable $this$count$iv = this.backQueue;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            return 0;
        }
        int count$iv = 0;
        for (Object element$iv : $this$count$iv) {
            NavBackStackEntry entry = (NavBackStackEntry) element$iv;
            if ((!(entry.getDestination() instanceof NavGraph)) && (count$iv = count$iv + 1) < 0) {
                CollectionsKt.throwCountOverflow();
            }
        }
        return count$iv;
    }

    private final boolean dispatchOnDestinationChanged() {
        while (!this.backQueue.isEmpty() && (this.backQueue.last().getDestination() instanceof NavGraph)) {
            popEntryFromBackStack$default(this, this.backQueue.last(), false, null, 6, null);
        }
        NavBackStackEntry lastBackStackEntry = this.backQueue.lastOrNull();
        if (lastBackStackEntry != null) {
            this.backStackEntriesToDispatch.add(lastBackStackEntry);
        }
        this.dispatchReentrantCount++;
        updateBackStackLifecycle$navigation_runtime_release();
        this.dispatchReentrantCount--;
        if (this.dispatchReentrantCount == 0) {
            List<NavBackStackEntry> dispatchList = CollectionsKt.toMutableList((Collection) this.backStackEntriesToDispatch);
            this.backStackEntriesToDispatch.clear();
            for (NavBackStackEntry backStackEntry : dispatchList) {
                Iterator<OnDestinationChangedListener> it = this.onDestinationChangedListeners.iterator();
                while (it.hasNext()) {
                    OnDestinationChangedListener listener = it.next();
                    listener.onDestinationChanged(this, backStackEntry.getDestination(), backStackEntry.getArguments());
                }
                this._currentBackStackEntryFlow.tryEmit(backStackEntry);
            }
            this._currentBackStack.tryEmit(CollectionsKt.toMutableList((Collection) this.backQueue));
            this._visibleEntries.tryEmit(populateVisibleEntries$navigation_runtime_release());
        }
        return lastBackStackEntry != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void updateBackStackLifecycle$navigation_runtime_release() {
        /*
            Method dump skipped, instructions count: 377
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.updateBackStackLifecycle$navigation_runtime_release():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0070 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<androidx.navigation.NavBackStackEntry> populateVisibleEntries$navigation_runtime_release() {
        /*
            Method dump skipped, instructions count: 260
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.populateVisibleEntries$navigation_runtime_release():java.util.List");
    }

    public NavInflater getNavInflater() {
        return (NavInflater) this.navInflater.getValue();
    }

    public void setGraph(int graphResId) throws Resources.NotFoundException {
        setGraph(getNavInflater().inflate(graphResId), (Bundle) null);
    }

    public void setGraph(int graphResId, Bundle startDestinationArgs) throws Resources.NotFoundException {
        setGraph(getNavInflater().inflate(graphResId), startDestinationArgs);
    }

    public void setGraph(NavGraph graph, Bundle startDestinationArgs) throws Resources.NotFoundException {
        NavDestination newDest;
        int $i$f$forEach;
        NavDestination newDest2;
        NavDestination newDest3;
        Intrinsics.checkNotNullParameter(graph, "graph");
        if (!(this.backQueue.isEmpty() || getHostLifecycleState$navigation_runtime_release() != Lifecycle.State.DESTROYED)) {
            throw new IllegalStateException("You cannot set a new graph on a NavController with entries on the back stack after the NavController has been destroyed. Please ensure that your NavHost has the same lifetime as your NavController.".toString());
        }
        if (!Intrinsics.areEqual(this._graph, graph)) {
            NavGraph previousGraph = this._graph;
            if (previousGraph != null) {
                Iterable savedBackStackIds = new ArrayList(this.backStackMap.keySet());
                for (Object element$iv : savedBackStackIds) {
                    Integer id = (Integer) element$iv;
                    Intrinsics.checkNotNullExpressionValue(id, "id");
                    clearBackStackInternal(id.intValue());
                }
                int $i$f$forEach2 = previousGraph.getId();
                popBackStackInternal$default(this, $i$f$forEach2, true, false, 4, (Object) null);
            }
            this._graph = graph;
            onGraphCreated(startDestinationArgs);
            return;
        }
        int size = graph.getNodes().size();
        for (int i = 0; i < size; i++) {
            NavDestination newDestination = graph.getNodes().valueAt(i);
            NavGraph navGraph = this._graph;
            Intrinsics.checkNotNull(navGraph);
            int key = navGraph.getNodes().keyAt(i);
            NavGraph navGraph2 = this._graph;
            Intrinsics.checkNotNull(navGraph2);
            navGraph2.getNodes().replace(key, newDestination);
        }
        Iterable $this$forEach$iv = this.backQueue;
        int $i$f$forEach3 = 0;
        for (Object element$iv2 : $this$forEach$iv) {
            NavBackStackEntry entry = (NavBackStackEntry) element$iv2;
            Iterable hierarchy = CollectionsKt.asReversed(SequencesKt.toList(NavDestination.INSTANCE.getHierarchy(entry.getDestination())));
            Iterable $this$fold$iv = hierarchy;
            NavDestination initial$iv = this._graph;
            Intrinsics.checkNotNull(initial$iv);
            NavDestination accumulator$iv = initial$iv;
            for (Object element$iv3 : $this$fold$iv) {
                NavDestination oldDest = (NavDestination) element$iv3;
                NavDestination newDest4 = accumulator$iv;
                Iterable $this$forEach$iv2 = $this$forEach$iv;
                if (!Intrinsics.areEqual(oldDest, this._graph)) {
                    newDest = newDest4;
                } else {
                    newDest = newDest4;
                    if (Intrinsics.areEqual(newDest, graph)) {
                        newDest2 = newDest;
                        $i$f$forEach = $i$f$forEach3;
                    }
                    newDest3 = newDest2;
                    accumulator$iv = newDest3;
                    $i$f$forEach3 = $i$f$forEach;
                    $this$forEach$iv = $this$forEach$iv2;
                }
                $i$f$forEach = $i$f$forEach3;
                if (newDest instanceof NavGraph) {
                    newDest3 = ((NavGraph) newDest).findNode(oldDest.getId());
                    Intrinsics.checkNotNull(newDest3);
                    accumulator$iv = newDest3;
                    $i$f$forEach3 = $i$f$forEach;
                    $this$forEach$iv = $this$forEach$iv2;
                } else {
                    newDest2 = newDest;
                    newDest3 = newDest2;
                    accumulator$iv = newDest3;
                    $i$f$forEach3 = $i$f$forEach;
                    $this$forEach$iv = $this$forEach$iv2;
                }
            }
            NavDestination newDestination2 = accumulator$iv;
            entry.setDestination(newDestination2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0175  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void onGraphCreated(android.os.Bundle r17) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 399
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.onGraphCreated(android.os.Bundle):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean handleDeepLink(android.content.Intent r27) throws android.content.res.Resources.NotFoundException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 614
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.handleDeepLink(android.content.Intent):boolean");
    }

    private final String findInvalidDestinationDisplayNameInDeepLink(int[] deepLink) {
        NavGraph node;
        NavGraph graph = this._graph;
        int i = 0;
        int length = deepLink.length;
        while (true) {
            if (i >= length) {
                return null;
            }
            int destinationId = deepLink[i];
            if (i == 0) {
                NavGraph navGraph = this._graph;
                Intrinsics.checkNotNull(navGraph);
                node = navGraph.getId() == destinationId ? this._graph : null;
            } else {
                Intrinsics.checkNotNull(graph);
                node = graph.findNode(destinationId);
            }
            if (node == null) {
                return NavDestination.INSTANCE.getDisplayName(this.context, destinationId);
            }
            if (i != deepLink.length - 1 && (node instanceof NavGraph)) {
                NavDestination navDestinationFindNode = node;
                while (true) {
                    graph = (NavGraph) navDestinationFindNode;
                    Intrinsics.checkNotNull(graph);
                    if (graph.findNode(graph.getStartDestId()) instanceof NavGraph) {
                        navDestinationFindNode = graph.findNode(graph.getStartDestId());
                    }
                }
            }
            i++;
        }
    }

    public NavDestination getCurrentDestination() {
        NavBackStackEntry currentBackStackEntry = getCurrentBackStackEntry();
        if (currentBackStackEntry != null) {
            return currentBackStackEntry.getDestination();
        }
        return null;
    }

    public final NavDestination findDestination(int destinationId) {
        NavGraph currentNode;
        if (this._graph == null) {
            return null;
        }
        NavGraph navGraph = this._graph;
        Intrinsics.checkNotNull(navGraph);
        if (navGraph.getId() == destinationId) {
            return this._graph;
        }
        NavBackStackEntry navBackStackEntryLastOrNull = this.backQueue.lastOrNull();
        if (navBackStackEntryLastOrNull == null || (currentNode = navBackStackEntryLastOrNull.getDestination()) == null) {
            NavGraph navGraph2 = this._graph;
            Intrinsics.checkNotNull(navGraph2);
            currentNode = navGraph2;
        }
        return findDestinationComprehensive(currentNode, destinationId, false);
    }

    public final NavDestination findDestinationComprehensive(NavDestination $this$findDestinationComprehensive, int destinationId, boolean searchChildren) {
        NavGraph currentGraph;
        Intrinsics.checkNotNullParameter($this$findDestinationComprehensive, "<this>");
        if ($this$findDestinationComprehensive.getId() == destinationId) {
            return $this$findDestinationComprehensive;
        }
        if ($this$findDestinationComprehensive instanceof NavGraph) {
            currentGraph = (NavGraph) $this$findDestinationComprehensive;
        } else {
            currentGraph = $this$findDestinationComprehensive.getParent();
            Intrinsics.checkNotNull(currentGraph);
        }
        return currentGraph.findNodeComprehensive(destinationId, currentGraph, searchChildren);
    }

    public final NavDestination findDestination(String route) {
        Intrinsics.checkNotNullParameter(route, "route");
        if (this._graph == null) {
            return null;
        }
        NavGraph navGraph = this._graph;
        Intrinsics.checkNotNull(navGraph);
        if (!Intrinsics.areEqual(navGraph.getRoute(), route)) {
            NavGraph navGraph2 = this._graph;
            Intrinsics.checkNotNull(navGraph2);
            if (navGraph2.matchRoute(route) == null) {
                return getTopGraph(this.backQueue).findNode(route);
            }
        }
        return this._graph;
    }

    private final NavGraph getTopGraph(ArrayDeque<NavBackStackEntry> arrayDeque) {
        NavGraph currentNode;
        NavBackStackEntry navBackStackEntryLastOrNull = arrayDeque.lastOrNull();
        if (navBackStackEntryLastOrNull == null || (currentNode = navBackStackEntryLastOrNull.getDestination()) == null) {
            NavGraph navGraph = this._graph;
            Intrinsics.checkNotNull(navGraph);
            currentNode = navGraph;
        }
        if (currentNode instanceof NavGraph) {
            return (NavGraph) currentNode;
        }
        NavGraph parent = currentNode.getParent();
        Intrinsics.checkNotNull(parent);
        return parent;
    }

    private final <T> String generateRouteFilled(T route) {
        int id = RouteSerializerKt.generateHashCode(SerializersKt.serializer(Reflection.getOrCreateKotlinClass(route.getClass())));
        NavDestination destination = findDestinationComprehensive(getGraph(), id, true);
        if (destination == null) {
            throw new IllegalArgumentException(("Destination with route " + Reflection.getOrCreateKotlinClass(route.getClass()).getSimpleName() + " cannot be found in navigation graph " + this._graph).toString());
        }
        Map $this$mapValues$iv = destination.getArguments();
        Map destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
        Iterable $this$associateByTo$iv$iv$iv = $this$mapValues$iv.entrySet();
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            Map.Entry it$iv$iv = (Map.Entry) element$iv$iv$iv;
            Map.Entry it = (Map.Entry) element$iv$iv$iv;
            destination$iv$iv.put(it$iv$iv.getKey(), ((NavArgument) it.getValue()).getType());
        }
        return RouteSerializerKt.generateRouteWithArgs(route, destination$iv$iv);
    }

    public void navigate(int resId) throws Resources.NotFoundException {
        navigate(resId, (Bundle) null);
    }

    public void navigate(int resId, Bundle args) throws Resources.NotFoundException {
        navigate(resId, args, (NavOptions) null);
    }

    public void navigate(int resId, Bundle args, NavOptions navOptions) throws Resources.NotFoundException {
        navigate(resId, args, navOptions, (Navigator.Extras) null);
    }

    public void navigate(int resId, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        NavOptions finalNavOptions;
        int destId;
        Bundle combinedArgs;
        NavOptions finalNavOptions2 = navOptions;
        NavGraph destination = this.backQueue.isEmpty() ? this._graph : this.backQueue.last().getDestination();
        if (destination == null) {
            throw new IllegalStateException("No current destination found. Ensure a navigation graph has been set for NavController " + this + '.');
        }
        NavDestination currentNode = destination;
        NavAction navAction = currentNode.getAction(resId);
        Bundle combinedArgs2 = null;
        if (navAction != null) {
            if (finalNavOptions2 == null) {
                finalNavOptions2 = navAction.getNavOptions();
            }
            int destId2 = navAction.getDestinationId();
            Bundle navActionArgs = navAction.getDefaultArguments();
            if (navActionArgs != null) {
                combinedArgs2 = new Bundle();
                combinedArgs2.putAll(navActionArgs);
                finalNavOptions = finalNavOptions2;
                destId = destId2;
            } else {
                finalNavOptions = finalNavOptions2;
                destId = destId2;
            }
        } else {
            finalNavOptions = finalNavOptions2;
            destId = resId;
        }
        if (args != null) {
            if (combinedArgs2 == null) {
                combinedArgs2 = new Bundle();
            }
            combinedArgs2.putAll(args);
            combinedArgs = combinedArgs2;
        } else {
            combinedArgs = combinedArgs2;
        }
        if (destId != 0 || finalNavOptions == null || (finalNavOptions.getPopUpToId() == -1 && finalNavOptions.getPopUpToRoute() == null && finalNavOptions.getPopUpToRouteClass() == null)) {
            if (!(destId != 0)) {
                throw new IllegalArgumentException("Destination id == 0 can only be used in conjunction with a valid navOptions.popUpTo".toString());
            }
            NavDestination node = findDestination(destId);
            if (node != null) {
                navigate(node, combinedArgs, finalNavOptions, navigatorExtras);
                return;
            }
            String dest = NavDestination.INSTANCE.getDisplayName(this.context, destId);
            if (!(navAction == null)) {
                throw new IllegalArgumentException(("Navigation destination " + dest + " referenced from action " + NavDestination.INSTANCE.getDisplayName(this.context, resId) + " cannot be found from the current destination " + currentNode).toString());
            }
            throw new IllegalArgumentException("Navigation action/destination " + dest + " cannot be found from the current destination " + currentNode);
        }
        if (finalNavOptions.getPopUpToRoute() != null) {
            String popUpToRoute = finalNavOptions.getPopUpToRoute();
            Intrinsics.checkNotNull(popUpToRoute);
            popBackStack$default(this, popUpToRoute, finalNavOptions.getPopUpToInclusive(), false, 4, (Object) null);
        } else if (finalNavOptions.getPopUpToRouteClass() != null) {
            KClass<?> popUpToRouteClass = finalNavOptions.getPopUpToRouteClass();
            Intrinsics.checkNotNull(popUpToRouteClass);
            popBackStack(RouteSerializerKt.generateHashCode(SerializersKt.serializer(popUpToRouteClass)), finalNavOptions.getPopUpToInclusive());
        } else if (finalNavOptions.getPopUpToId() != -1) {
            popBackStack(finalNavOptions.getPopUpToId(), finalNavOptions.getPopUpToInclusive());
        }
    }

    public void navigate(Uri deepLink) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(deepLink, "deepLink");
        navigate(new NavDeepLinkRequest(deepLink, null, null));
    }

    public void navigate(Uri deepLink, NavOptions navOptions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(deepLink, "deepLink");
        navigate(new NavDeepLinkRequest(deepLink, null, null), navOptions, (Navigator.Extras) null);
    }

    public void navigate(Uri deepLink, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(deepLink, "deepLink");
        navigate(new NavDeepLinkRequest(deepLink, null, null), navOptions, navigatorExtras);
    }

    public void navigate(NavDeepLinkRequest request) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(request, "request");
        navigate(request, (NavOptions) null);
    }

    public void navigate(NavDeepLinkRequest request, NavOptions navOptions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(request, "request");
        navigate(request, navOptions, (Navigator.Extras) null);
    }

    public void navigate(NavDeepLinkRequest request, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(request, "request");
        if (this._graph == null) {
            throw new IllegalArgumentException(("Cannot navigate to " + request + ". Navigation graph has not been set for NavController " + this + '.').toString());
        }
        NavGraph currGraph = getTopGraph(this.backQueue);
        NavDestination.DeepLinkMatch deepLinkMatch = currGraph.matchDeepLinkComprehensive(request, true, true, currGraph);
        if (deepLinkMatch != null) {
            NavDestination destination = deepLinkMatch.getDestination();
            Bundle args = destination.addInDefaultArgs(deepLinkMatch.getMatchingArgs());
            if (args == null) {
                args = new Bundle();
            }
            NavDestination node = deepLinkMatch.getDestination();
            Intent $this$navigate_u24lambda_u2439 = new Intent();
            $this$navigate_u24lambda_u2439.setDataAndType(request.getUri(), request.getMimeType());
            $this$navigate_u24lambda_u2439.setAction(request.getAction());
            args.putParcelable(KEY_DEEP_LINK_INTENT, $this$navigate_u24lambda_u2439);
            navigate(node, args, navOptions, navigatorExtras);
            return;
        }
        throw new IllegalArgumentException("Navigation destination that matches request " + request + " cannot be found in the navigation graph " + this._graph);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void navigate(final androidx.navigation.NavDestination r22, android.os.Bundle r23, androidx.navigation.NavOptions r24, androidx.navigation.Navigator.Extras r25) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 380
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.navigate(androidx.navigation.NavDestination, android.os.Bundle, androidx.navigation.NavOptions, androidx.navigation.Navigator$Extras):void");
    }

    private final boolean launchSingleTopInternal(NavDestination node, Bundle args) {
        int iNextIndex;
        NavDestination destination;
        NavBackStackEntry currentBackStackEntry = getCurrentBackStackEntry();
        List $this$indexOfLast$iv = this.backQueue;
        ListIterator iterator$iv = $this$indexOfLast$iv.listIterator($this$indexOfLast$iv.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
                NavBackStackEntry it = iterator$iv.previous();
                NavBackStackEntry it2 = it.getDestination() == node ? 1 : null;
                if (it2 != null) {
                    iNextIndex = iterator$iv.nextIndex();
                    break;
                }
            } else {
                iNextIndex = -1;
                break;
            }
        }
        int nodeIndex = iNextIndex;
        if (nodeIndex == -1) {
            return false;
        }
        if (node instanceof NavGraph) {
            List childHierarchyId = SequencesKt.toList(SequencesKt.map(NavGraph.INSTANCE.childHierarchy((NavGraph) node), new Function1<NavDestination, Integer>() { // from class: androidx.navigation.NavController$launchSingleTopInternal$childHierarchyId$1
                @Override // kotlin.jvm.functions.Function1
                public final Integer invoke(NavDestination it3) {
                    Intrinsics.checkNotNullParameter(it3, "it");
                    return Integer.valueOf(it3.getId());
                }
            }));
            if (this.backQueue.size() - nodeIndex != childHierarchyId.size()) {
                return false;
            }
            Iterable $this$map$iv = this.backQueue.subList(nodeIndex, this.backQueue.size());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                NavBackStackEntry it3 = (NavBackStackEntry) item$iv$iv;
                destination$iv$iv.add(Integer.valueOf(it3.getDestination().getId()));
            }
            List backQueueId = (List) destination$iv$iv;
            if (!Intrinsics.areEqual(backQueueId, childHierarchyId)) {
                return false;
            }
        } else if (!((currentBackStackEntry == null || (destination = currentBackStackEntry.getDestination()) == null || node.getId() != destination.getId()) ? false : true)) {
            return false;
        }
        ArrayDeque tempBackQueue = new ArrayDeque();
        while (CollectionsKt.getLastIndex(this.backQueue) >= nodeIndex) {
            NavBackStackEntry oldEntry = (NavBackStackEntry) CollectionsKt.removeLast(this.backQueue);
            unlinkChildFromParent$navigation_runtime_release(oldEntry);
            tempBackQueue.addFirst(new NavBackStackEntry(oldEntry, oldEntry.getDestination().addInDefaultArgs(args)));
        }
        ArrayDeque $this$forEach$iv = tempBackQueue;
        for (Object element$iv : $this$forEach$iv) {
            NavBackStackEntry newEntry = (NavBackStackEntry) element$iv;
            NavGraph parent = newEntry.getDestination().getParent();
            if (parent != null) {
                NavBackStackEntry newParent = getBackStackEntry(parent.getId());
                linkChildToParent(newEntry, newParent);
            }
            this.backQueue.add(newEntry);
        }
        ArrayDeque $this$forEach$iv2 = tempBackQueue;
        for (Object element$iv2 : $this$forEach$iv2) {
            NavBackStackEntry newEntry2 = (NavBackStackEntry) element$iv2;
            Navigator navigator = this._navigatorProvider.getNavigator(newEntry2.getDestination().getNavigatorName());
            navigator.onLaunchSingleTop(newEntry2);
        }
        return true;
    }

    private final boolean restoreStateInternal(int id, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        if (!this.backStackMap.containsKey(Integer.valueOf(id))) {
            return false;
        }
        final String backStackId = this.backStackMap.get(Integer.valueOf(id));
        CollectionsKt.removeAll(this.backStackMap.values(), new Function1<String, Boolean>() { // from class: androidx.navigation.NavController.restoreStateInternal.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it) {
                return Boolean.valueOf(Intrinsics.areEqual(it, backStackId));
            }
        });
        ArrayDeque backStackState = (ArrayDeque) TypeIntrinsics.asMutableMap(this.backStackStates).remove(backStackId);
        List entries = instantiateBackStack(backStackState);
        return executeRestoreState(entries, args, navOptions, navigatorExtras);
    }

    private final boolean restoreStateInternal(String route) throws Resources.NotFoundException {
        NavBackStackEntryState navBackStackEntryStateFirstOrNull;
        int id = NavDestination.INSTANCE.createRoute(route).hashCode();
        if (this.backStackMap.containsKey(Integer.valueOf(id))) {
            return restoreStateInternal(id, null, null, null);
        }
        NavDestination matchingDestination = findDestination(route);
        if (!(matchingDestination != null)) {
            throw new IllegalStateException(("Restore State failed: route " + route + " cannot be found from the current destination " + getCurrentDestination()).toString());
        }
        final String backStackId = this.backStackMap.get(Integer.valueOf(matchingDestination.getId()));
        CollectionsKt.removeAll(this.backStackMap.values(), new Function1<String, Boolean>() { // from class: androidx.navigation.NavController.restoreStateInternal.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it) {
                return Boolean.valueOf(Intrinsics.areEqual(it, backStackId));
            }
        });
        ArrayDeque backStackState = (ArrayDeque) TypeIntrinsics.asMutableMap(this.backStackStates).remove(backStackId);
        NavDestination.DeepLinkMatch matchingDeepLink = matchingDestination.matchRoute(route);
        Intrinsics.checkNotNull(matchingDeepLink);
        boolean isCorrectStack = matchingDeepLink.hasMatchingArgs((backStackState == null || (navBackStackEntryStateFirstOrNull = backStackState.firstOrNull()) == null) ? null : navBackStackEntryStateFirstOrNull.getArgs());
        if (!isCorrectStack) {
            return false;
        }
        List entries = instantiateBackStack(backStackState);
        return executeRestoreState(entries, null, null, null);
    }

    private final boolean executeRestoreState(final List<NavBackStackEntry> entries, final Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        NavBackStackEntry navBackStackEntry;
        NavDestination destination;
        List<List> entriesGroupedByNavigator = new ArrayList();
        List<NavBackStackEntry> $this$filterNot$iv = entries;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filterNot$iv) {
            if (!(((NavBackStackEntry) element$iv$iv).getDestination() instanceof NavGraph)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$forEach$iv) {
            NavBackStackEntry entry = (NavBackStackEntry) element$iv;
            List previousEntryList = (List) CollectionsKt.lastOrNull(entriesGroupedByNavigator);
            String previousNavigatorName = (previousEntryList == null || (navBackStackEntry = (NavBackStackEntry) CollectionsKt.last(previousEntryList)) == null || (destination = navBackStackEntry.getDestination()) == null) ? null : destination.getNavigatorName();
            if (Intrinsics.areEqual(previousNavigatorName, entry.getDestination().getNavigatorName())) {
                previousEntryList.add(entry);
            } else {
                entriesGroupedByNavigator.add(CollectionsKt.mutableListOf(entry));
            }
        }
        final Ref.BooleanRef navigated = new Ref.BooleanRef();
        for (List entryList : entriesGroupedByNavigator) {
            Navigator navigator = this._navigatorProvider.getNavigator(((NavBackStackEntry) CollectionsKt.first(entryList)).getDestination().getNavigatorName());
            final Ref.IntRef lastNavigatedIndex = new Ref.IntRef();
            navigateInternal(navigator, entryList, navOptions, navigatorExtras, new Function1<NavBackStackEntry, Unit>() { // from class: androidx.navigation.NavController.executeRestoreState.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NavBackStackEntry navBackStackEntry2) {
                    invoke2(navBackStackEntry2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NavBackStackEntry entry2) {
                    List restoredEntries;
                    Intrinsics.checkNotNullParameter(entry2, "entry");
                    navigated.element = true;
                    int entryIndex = entries.indexOf(entry2);
                    if (entryIndex != -1) {
                        restoredEntries = entries.subList(lastNavigatedIndex.element, entryIndex + 1);
                        lastNavigatedIndex.element = entryIndex + 1;
                    } else {
                        restoredEntries = CollectionsKt.emptyList();
                    }
                    this.addEntryToBackStack(entry2.getDestination(), args, entry2, restoredEntries);
                }
            });
        }
        return navigated.element;
    }

    private final List<NavBackStackEntry> instantiateBackStack(ArrayDeque<NavBackStackEntryState> backStackState) throws Resources.NotFoundException {
        NavGraph graph;
        List backStack = new ArrayList();
        NavBackStackEntry navBackStackEntryLastOrNull = this.backQueue.lastOrNull();
        if (navBackStackEntryLastOrNull == null || (graph = navBackStackEntryLastOrNull.getDestination()) == null) {
            graph = getGraph();
        }
        NavDestination navDestination = graph;
        if (backStackState != null) {
            ArrayDeque<NavBackStackEntryState> $this$forEach$iv = backStackState;
            for (Object element$iv : $this$forEach$iv) {
                NavBackStackEntryState state = (NavBackStackEntryState) element$iv;
                NavDestination node = findDestinationComprehensive(navDestination, state.getDestinationId(), true);
                if (node == null) {
                    String dest = NavDestination.INSTANCE.getDisplayName(this.context, state.getDestinationId());
                    throw new IllegalStateException(("Restore State failed: destination " + dest + " cannot be found from the current destination " + navDestination).toString());
                }
                backStack.add(state.instantiate(this.context, node, getHostLifecycleState$navigation_runtime_release(), this.viewModel));
                navDestination = node;
            }
        }
        return backStack;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void addEntryToBackStack$default(NavController navController, NavDestination navDestination, Bundle bundle, NavBackStackEntry navBackStackEntry, List list, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addEntryToBackStack");
        }
        if ((i & 8) != 0) {
            list = CollectionsKt.emptyList();
        }
        navController.addEntryToBackStack(navDestination, bundle, navBackStackEntry, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addEntryToBackStack(NavDestination node, Bundle finalArgs, NavBackStackEntry backStackEntry, List<NavBackStackEntry> restoredEntries) {
        boolean z;
        ArrayDeque hierarchy;
        NavDestination newDest;
        NavBackStackEntry navBackStackEntry;
        Bundle bundle;
        Object element$iv;
        NavGraph parent;
        Object element$iv2;
        NavBackStackEntry navBackStackEntry2;
        Bundle bundle2;
        Bundle bundle3 = finalArgs;
        NavBackStackEntry navBackStackEntry3 = backStackEntry;
        NavDestination newDest2 = backStackEntry.getDestination();
        if (!(newDest2 instanceof FloatingWindow)) {
            while (!this.backQueue.isEmpty() && (this.backQueue.last().getDestination() instanceof FloatingWindow) && popBackStackInternal$default(this, this.backQueue.last().getDestination().getId(), true, false, 4, (Object) null)) {
            }
        }
        ArrayDeque hierarchy2 = new ArrayDeque();
        boolean z2 = true;
        Object obj = null;
        if (node instanceof NavGraph) {
            NavDestination destination = newDest2;
            while (true) {
                Intrinsics.checkNotNull(destination);
                NavGraph parent2 = destination.getParent();
                if (parent2 == null) {
                    parent = parent2;
                    z = z2;
                    hierarchy = hierarchy2;
                    newDest = newDest2;
                    navBackStackEntry = navBackStackEntry3;
                    bundle = bundle3;
                } else {
                    ListIterator iterator$iv = restoredEntries.listIterator(restoredEntries.size());
                    while (true) {
                        if (iterator$iv.hasPrevious()) {
                            element$iv2 = iterator$iv.previous();
                            NavBackStackEntry restoredEntry = (NavBackStackEntry) element$iv2;
                            if (Intrinsics.areEqual(restoredEntry.getDestination(), parent2)) {
                                break;
                            }
                        } else {
                            element$iv2 = null;
                            break;
                        }
                    }
                    NavBackStackEntry entry = (NavBackStackEntry) element$iv2;
                    if (entry != null) {
                        newDest = newDest2;
                        navBackStackEntry2 = navBackStackEntry3;
                        bundle2 = bundle3;
                    } else {
                        newDest = newDest2;
                        navBackStackEntry2 = navBackStackEntry3;
                        bundle2 = bundle3;
                        entry = NavBackStackEntry.Companion.create$default(NavBackStackEntry.INSTANCE, this.context, parent2, finalArgs, getHostLifecycleState$navigation_runtime_release(), this.viewModel, null, null, 96, null);
                    }
                    hierarchy2.addFirst(entry);
                    if (!(this.backQueue.isEmpty() ^ z2) || this.backQueue.last().getDestination() != parent2) {
                        navBackStackEntry = navBackStackEntry2;
                        bundle = bundle2;
                        parent = parent2;
                        z = z2;
                        hierarchy = hierarchy2;
                    } else {
                        navBackStackEntry = navBackStackEntry2;
                        bundle = bundle2;
                        parent = parent2;
                        z = z2;
                        hierarchy = hierarchy2;
                        popEntryFromBackStack$default(this, this.backQueue.last(), false, null, 6, null);
                    }
                }
                NavGraph destination2 = parent;
                if (destination2 == null || destination2 == node) {
                    break;
                }
                destination = destination2;
                z2 = z;
                hierarchy2 = hierarchy;
                bundle3 = bundle;
                navBackStackEntry3 = navBackStackEntry;
                newDest2 = newDest;
            }
        } else {
            z = true;
            hierarchy = hierarchy2;
            newDest = newDest2;
            navBackStackEntry = navBackStackEntry3;
            bundle = bundle3;
        }
        NavDestination destination3 = hierarchy.isEmpty() ? newDest : ((NavBackStackEntry) hierarchy.first()).getDestination();
        NavGraph destination4 = destination3;
        while (destination4 != null && findDestination(destination4.getId()) != destination4) {
            NavGraph parent3 = destination4.getParent();
            if (parent3 != null) {
                boolean z3 = false;
                if (bundle != null && finalArgs.isEmpty() == z) {
                    z3 = z;
                }
                Bundle args = z3 ? null : bundle;
                ListIterator iterator$iv2 = restoredEntries.listIterator(restoredEntries.size());
                while (true) {
                    if (iterator$iv2.hasPrevious()) {
                        element$iv = iterator$iv2.previous();
                        NavBackStackEntry restoredEntry2 = (NavBackStackEntry) element$iv;
                        if (Intrinsics.areEqual(restoredEntry2.getDestination(), parent3)) {
                            break;
                        }
                    } else {
                        element$iv = null;
                        break;
                    }
                }
                NavBackStackEntry entry2 = (NavBackStackEntry) element$iv;
                if (entry2 == null) {
                    entry2 = NavBackStackEntry.Companion.create$default(NavBackStackEntry.INSTANCE, this.context, parent3, parent3.addInDefaultArgs(args), getHostLifecycleState$navigation_runtime_release(), this.viewModel, null, null, 96, null);
                }
                hierarchy.addFirst(entry2);
            }
            destination4 = parent3;
        }
        NavDestination overlappingDestination = hierarchy.isEmpty() ? newDest : ((NavBackStackEntry) hierarchy.first()).getDestination();
        while (!this.backQueue.isEmpty() && (this.backQueue.last().getDestination() instanceof NavGraph)) {
            NavDestination destination5 = this.backQueue.last().getDestination();
            Intrinsics.checkNotNull(destination5, "null cannot be cast to non-null type androidx.navigation.NavGraph");
            if (((NavGraph) destination5).getNodes().get(overlappingDestination.getId()) != null) {
                break;
            } else {
                popEntryFromBackStack$default(this, this.backQueue.last(), false, null, 6, null);
            }
        }
        NavBackStackEntry firstEntry = this.backQueue.firstOrNull();
        if (firstEntry == null) {
            firstEntry = (NavBackStackEntry) hierarchy.firstOrNull();
        }
        if (!Intrinsics.areEqual(firstEntry != null ? firstEntry.getDestination() : null, this._graph)) {
            ListIterator iterator$iv3 = restoredEntries.listIterator(restoredEntries.size());
            while (true) {
                if (!iterator$iv3.hasPrevious()) {
                    break;
                }
                Object element$iv3 = iterator$iv3.previous();
                NavBackStackEntry restoredEntry3 = (NavBackStackEntry) element$iv3;
                NavDestination destination6 = restoredEntry3.getDestination();
                NavGraph navGraph = this._graph;
                Intrinsics.checkNotNull(navGraph);
                if (Intrinsics.areEqual(destination6, navGraph)) {
                    obj = element$iv3;
                    break;
                }
            }
            NavBackStackEntry entry3 = (NavBackStackEntry) obj;
            if (entry3 == null) {
                NavBackStackEntry.Companion companion = NavBackStackEntry.INSTANCE;
                Context context = this.context;
                NavGraph navGraph2 = this._graph;
                Intrinsics.checkNotNull(navGraph2);
                NavGraph navGraph3 = navGraph2;
                NavGraph navGraph4 = this._graph;
                Intrinsics.checkNotNull(navGraph4);
                entry3 = NavBackStackEntry.Companion.create$default(companion, context, navGraph3, navGraph4.addInDefaultArgs(bundle), getHostLifecycleState$navigation_runtime_release(), this.viewModel, null, null, 96, null);
            }
            hierarchy.addFirst(entry3);
        }
        Iterable $this$forEach$iv = hierarchy;
        for (Object element$iv4 : $this$forEach$iv) {
            NavBackStackEntry entry4 = (NavBackStackEntry) element$iv4;
            Navigator navigator = this._navigatorProvider.getNavigator(entry4.getDestination().getNavigatorName());
            NavControllerNavigatorState navControllerNavigatorState = this.navigatorState.get(navigator);
            if (navControllerNavigatorState == null) {
                throw new IllegalStateException(("NavigatorBackStack for " + node.getNavigatorName() + " should already be created").toString());
            }
            NavControllerNavigatorState navigatorBackStack = navControllerNavigatorState;
            navigatorBackStack.addInternal(entry4);
        }
        this.backQueue.addAll(hierarchy);
        this.backQueue.add(navBackStackEntry);
        Iterable $this$forEach$iv2 = CollectionsKt.plus((Collection<? extends NavBackStackEntry>) hierarchy, navBackStackEntry);
        for (Object element$iv5 : $this$forEach$iv2) {
            NavBackStackEntry it = (NavBackStackEntry) element$iv5;
            NavGraph parent4 = it.getDestination().getParent();
            if (parent4 != null) {
                linkChildToParent(it, getBackStackEntry(parent4.getId()));
            }
        }
    }

    public void navigate(NavDirections directions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(directions, "directions");
        navigate(directions.getActionId(), directions.getArguments(), (NavOptions) null);
    }

    public void navigate(NavDirections directions, NavOptions navOptions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(directions, "directions");
        navigate(directions.getActionId(), directions.getArguments(), navOptions);
    }

    public void navigate(NavDirections directions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(directions, "directions");
        Intrinsics.checkNotNullParameter(navigatorExtras, "navigatorExtras");
        navigate(directions.getActionId(), directions.getArguments(), (NavOptions) null, navigatorExtras);
    }

    public final void navigate(String route, Function1<? super NavOptionsBuilder, Unit> builder) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(builder, "builder");
        navigate$default(this, route, NavOptionsBuilderKt.navOptions(builder), (Navigator.Extras) null, 4, (Object) null);
    }

    public static /* synthetic */ void navigate$default(NavController navController, String str, NavOptions navOptions, Navigator.Extras extras, int i, Object obj) throws Resources.NotFoundException {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: navigate");
        }
        if ((i & 2) != 0) {
            navOptions = null;
        }
        if ((i & 4) != 0) {
            extras = null;
        }
        navController.navigate(str, navOptions, extras);
    }

    public final void navigate(String route, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        if (this._graph == null) {
            throw new IllegalArgumentException(("Cannot navigate to " + route + ". Navigation graph has not been set for NavController " + this + '.').toString());
        }
        NavGraph currGraph = getTopGraph(this.backQueue);
        NavDestination.DeepLinkMatch deepLinkMatch = currGraph.matchRouteComprehensive(route, true, true, currGraph);
        if (deepLinkMatch != null) {
            NavDestination destination = deepLinkMatch.getDestination();
            Bundle args = destination.addInDefaultArgs(deepLinkMatch.getMatchingArgs());
            if (args == null) {
                args = new Bundle();
            }
            NavDestination node = deepLinkMatch.getDestination();
            Intent $this$navigate_u24lambda_u2458 = new Intent();
            String $this$toUri$iv = NavDestination.INSTANCE.createRoute(destination.getRoute());
            Uri uri = Uri.parse($this$toUri$iv);
            Intrinsics.checkExpressionValueIsNotNull(uri, "Uri.parse(this)");
            $this$navigate_u24lambda_u2458.setDataAndType(uri, null);
            $this$navigate_u24lambda_u2458.setAction(null);
            args.putParcelable(KEY_DEEP_LINK_INTENT, $this$navigate_u24lambda_u2458);
            navigate(node, args, navOptions, navigatorExtras);
            return;
        }
        throw new IllegalArgumentException("Navigation destination that matches route " + route + " cannot be found in the navigation graph " + this._graph);
    }

    public final void navigate(String route) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        navigate$default(this, route, (NavOptions) null, (Navigator.Extras) null, 6, (Object) null);
    }

    public final void navigate(String route, NavOptions navOptions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        navigate$default(this, route, navOptions, (Navigator.Extras) null, 4, (Object) null);
    }

    public final <T> void navigate(T route, Function1<? super NavOptionsBuilder, Unit> builder) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(builder, "builder");
        navigate$default(this, route, NavOptionsBuilderKt.navOptions(builder), (Navigator.Extras) null, 4, (Object) null);
    }

    public static /* synthetic */ void navigate$default(NavController navController, Object obj, NavOptions navOptions, Navigator.Extras extras, int i, Object obj2) throws Resources.NotFoundException {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: navigate");
        }
        if ((i & 2) != 0) {
            navOptions = null;
        }
        if ((i & 4) != 0) {
            extras = null;
        }
        navController.navigate((NavController) obj, navOptions, extras);
    }

    public final <T> void navigate(T route, NavOptions navOptions, Navigator.Extras navigatorExtras) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        navigate(generateRouteFilled(route), navOptions, navigatorExtras);
    }

    public final <T> void navigate(T route) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        navigate$default(this, route, (NavOptions) null, (Navigator.Extras) null, 6, (Object) null);
    }

    public final <T> void navigate(T route, NavOptions navOptions) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(route, "route");
        navigate$default(this, route, navOptions, (Navigator.Extras) null, 4, (Object) null);
    }

    public NavDeepLinkBuilder createDeepLink() {
        return new NavDeepLinkBuilder(this);
    }

    public Bundle saveState() {
        Bundle b = null;
        ArrayList navigatorNames = new ArrayList();
        Bundle navigatorState = new Bundle();
        for (Map.Entry<String, Navigator<? extends NavDestination>> entry : this._navigatorProvider.getNavigators().entrySet()) {
            String name = entry.getKey();
            Navigator value = entry.getValue();
            Bundle savedState = value.onSaveState();
            if (savedState != null) {
                navigatorNames.add(name);
                navigatorState.putBundle(name, savedState);
            }
        }
        if (!navigatorNames.isEmpty()) {
            b = new Bundle();
            navigatorState.putStringArrayList(KEY_NAVIGATOR_STATE_NAMES, navigatorNames);
            b.putBundle(KEY_NAVIGATOR_STATE, navigatorState);
        }
        if (!this.backQueue.isEmpty()) {
            if (b == null) {
                b = new Bundle();
            }
            Parcelable[] backStack = new Parcelable[this.backQueue.size()];
            int index = 0;
            Iterator it = this.backQueue.iterator();
            while (it.hasNext()) {
                NavBackStackEntry backStackEntry = (NavBackStackEntry) it.next();
                backStack[index] = new NavBackStackEntryState(backStackEntry);
                index++;
            }
            b.putParcelableArray(KEY_BACK_STACK, backStack);
        }
        if (!this.backStackMap.isEmpty()) {
            if (b == null) {
                b = new Bundle();
            }
            int[] backStackDestIds = new int[this.backStackMap.size()];
            ArrayList backStackIds = new ArrayList();
            int index2 = 0;
            for (Map.Entry<Integer, String> entry2 : this.backStackMap.entrySet()) {
                int destId = entry2.getKey().intValue();
                String id = entry2.getValue();
                backStackDestIds[index2] = destId;
                backStackIds.add(id);
                index2++;
            }
            b.putIntArray(KEY_BACK_STACK_DEST_IDS, backStackDestIds);
            b.putStringArrayList(KEY_BACK_STACK_IDS, backStackIds);
        }
        if (!this.backStackStates.isEmpty()) {
            if (b == null) {
                b = new Bundle();
            }
            ArrayList backStackStateIds = new ArrayList();
            for (Map.Entry<String, ArrayDeque<NavBackStackEntryState>> entry3 : this.backStackStates.entrySet()) {
                String id2 = entry3.getKey();
                ArrayDeque backStackStates = entry3.getValue();
                backStackStateIds.add(id2);
                Parcelable[] states = new Parcelable[backStackStates.size()];
                ArrayDeque $this$forEachIndexed$iv = backStackStates;
                int stateIndex = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    int index$iv = stateIndex + 1;
                    if (stateIndex < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    NavBackStackEntryState backStackState = (NavBackStackEntryState) item$iv;
                    states[stateIndex] = backStackState;
                    stateIndex = index$iv;
                }
                b.putParcelableArray(KEY_BACK_STACK_STATES_PREFIX + id2, states);
            }
            b.putStringArrayList(KEY_BACK_STACK_STATES_IDS, backStackStateIds);
        }
        if (this.deepLinkHandled) {
            if (b == null) {
                b = new Bundle();
            }
            b.putBoolean(KEY_DEEP_LINK_HANDLED, this.deepLinkHandled);
        }
        return b;
    }

    public void restoreState(Bundle navState) {
        int[] backStackDestIds;
        ArrayList backStackIds;
        if (navState == null) {
            return;
        }
        navState.setClassLoader(this.context.getClassLoader());
        this.navigatorStateToRestore = navState.getBundle(KEY_NAVIGATOR_STATE);
        this.backStackToRestore = navState.getParcelableArray(KEY_BACK_STACK);
        this.backStackStates.clear();
        int[] backStackDestIds2 = navState.getIntArray(KEY_BACK_STACK_DEST_IDS);
        ArrayList backStackIds2 = navState.getStringArrayList(KEY_BACK_STACK_IDS);
        if (backStackDestIds2 != null && backStackIds2 != null) {
            int index$iv = 0;
            int length = backStackDestIds2.length;
            int i = 0;
            while (i < length) {
                int item$iv = backStackDestIds2[i];
                this.backStackMap.put(Integer.valueOf(item$iv), backStackIds2.get(index$iv));
                i++;
                index$iv++;
            }
        }
        Iterable backStackStateIds = navState.getStringArrayList(KEY_BACK_STACK_STATES_IDS);
        if (backStackStateIds != null) {
            Iterable $this$forEach$iv = backStackStateIds;
            for (Object element$iv : $this$forEach$iv) {
                String id = (String) element$iv;
                Parcelable[] backStackState = navState.getParcelableArray(KEY_BACK_STACK_STATES_PREFIX + id);
                if (backStackState == null) {
                    backStackDestIds = backStackDestIds2;
                    backStackIds = backStackIds2;
                } else {
                    Map<String, ArrayDeque<NavBackStackEntryState>> map = this.backStackStates;
                    Intrinsics.checkNotNullExpressionValue(id, "id");
                    ArrayDeque $this$restoreState_u24lambda_u2462_u24lambda_u2461 = new ArrayDeque(backStackState.length);
                    Iterator it = ArrayIteratorKt.iterator(backStackState);
                    while (it.hasNext()) {
                        int[] backStackDestIds3 = backStackDestIds2;
                        Parcelable parcelable = (Parcelable) it.next();
                        Intrinsics.checkNotNull(parcelable, "null cannot be cast to non-null type androidx.navigation.NavBackStackEntryState");
                        $this$restoreState_u24lambda_u2462_u24lambda_u2461.add((NavBackStackEntryState) parcelable);
                        backStackIds2 = backStackIds2;
                        backStackDestIds2 = backStackDestIds3;
                    }
                    backStackDestIds = backStackDestIds2;
                    backStackIds = backStackIds2;
                    map.put(id, $this$restoreState_u24lambda_u2462_u24lambda_u2461);
                }
                backStackIds2 = backStackIds;
                backStackDestIds2 = backStackDestIds;
            }
        }
        this.deepLinkHandled = navState.getBoolean(KEY_DEEP_LINK_HANDLED);
    }

    public void setLifecycleOwner(LifecycleOwner owner) {
        Lifecycle lifecycle;
        Intrinsics.checkNotNullParameter(owner, "owner");
        if (Intrinsics.areEqual(owner, this.lifecycleOwner)) {
            return;
        }
        LifecycleOwner lifecycleOwner = this.lifecycleOwner;
        if (lifecycleOwner != null && (lifecycle = lifecycleOwner.getLifecycle()) != null) {
            lifecycle.removeObserver(this.lifecycleObserver);
        }
        this.lifecycleOwner = owner;
        owner.getLifecycle().addObserver(this.lifecycleObserver);
    }

    public void setOnBackPressedDispatcher(OnBackPressedDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        if (Intrinsics.areEqual(dispatcher, this.onBackPressedDispatcher)) {
            return;
        }
        LifecycleOwner lifecycleOwner = this.lifecycleOwner;
        if (lifecycleOwner == null) {
            throw new IllegalStateException("You must call setLifecycleOwner() before calling setOnBackPressedDispatcher()".toString());
        }
        this.onBackPressedCallback.remove();
        this.onBackPressedDispatcher = dispatcher;
        dispatcher.addCallback(lifecycleOwner, this.onBackPressedCallback);
        Lifecycle $this$setOnBackPressedDispatcher_u24lambda_u2464 = lifecycleOwner.getLifecycle();
        $this$setOnBackPressedDispatcher_u24lambda_u2464.removeObserver(this.lifecycleObserver);
        $this$setOnBackPressedDispatcher_u24lambda_u2464.addObserver(this.lifecycleObserver);
    }

    public void enableOnBackPressed(boolean enabled) {
        this.enableOnBackPressedCallback = enabled;
        updateOnBackPressedCallbackEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void updateOnBackPressedCallbackEnabled() {
        /*
            r3 = this;
            androidx.activity.OnBackPressedCallback r0 = r3.onBackPressedCallback
            boolean r1 = r3.enableOnBackPressedCallback
            if (r1 == 0) goto Le
            int r1 = r3.getDestinationCountOnBackStack()
            r2 = 1
            if (r1 <= r2) goto Le
            goto Lf
        Le:
            r2 = 0
        Lf:
            r0.setEnabled(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.updateOnBackPressedCallbackEnabled():void");
    }

    public void setViewModelStore(ViewModelStore viewModelStore) {
        Intrinsics.checkNotNullParameter(viewModelStore, "viewModelStore");
        if (Intrinsics.areEqual(this.viewModel, NavControllerViewModel.INSTANCE.getInstance(viewModelStore))) {
            return;
        }
        if (!this.backQueue.isEmpty()) {
            throw new IllegalStateException("ViewModelStore should be set before setGraph call".toString());
        }
        this.viewModel = NavControllerViewModel.INSTANCE.getInstance(viewModelStore);
    }

    public ViewModelStoreOwner getViewModelStoreOwner(int navGraphId) {
        if (this.viewModel == null) {
            throw new IllegalStateException("You must call setViewModelStore() before calling getViewModelStoreOwner().".toString());
        }
        NavBackStackEntry lastFromBackStack = getBackStackEntry(navGraphId);
        if (!(lastFromBackStack.getDestination() instanceof NavGraph)) {
            throw new IllegalArgumentException(("No NavGraph with ID " + navGraphId + " is on the NavController's back stack").toString());
        }
        return lastFromBackStack;
    }

    public NavBackStackEntry getBackStackEntry(int destinationId) {
        Object element$iv;
        List $this$lastOrNull$iv = this.backQueue;
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
                element$iv = iterator$iv.previous();
                NavBackStackEntry entry = (NavBackStackEntry) element$iv;
                if (entry.getDestination().getId() == destinationId) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        NavBackStackEntry lastFromBackStack = (NavBackStackEntry) element$iv;
        if (lastFromBackStack != null) {
            return lastFromBackStack;
        }
        throw new IllegalArgumentException(("No destination with ID " + destinationId + " is on the NavController's back stack. The current destination is " + getCurrentDestination()).toString());
    }

    public final NavBackStackEntry getBackStackEntry(String route) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(route, "route");
        List $this$lastOrNull$iv = this.backQueue;
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
                element$iv = iterator$iv.previous();
                NavBackStackEntry entry = (NavBackStackEntry) element$iv;
                if (entry.getDestination().hasRoute(route, entry.getArguments())) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        NavBackStackEntry lastFromBackStack = (NavBackStackEntry) element$iv;
        if (lastFromBackStack != null) {
            return lastFromBackStack;
        }
        throw new IllegalArgumentException(("No destination with route " + route + " is on the NavController's back stack. The current destination is " + getCurrentDestination()).toString());
    }

    public final /* synthetic */ <T> NavBackStackEntry getBackStackEntry() {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        Object obj = null;
        int id = RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null));
        if (findDestinationComprehensive(getGraph(), id, true) == null) {
            StringBuilder sbAppend = new StringBuilder().append("Destination with route ");
            Intrinsics.reifiedOperationMarker(4, "T");
            throw new IllegalArgumentException(sbAppend.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).append(" cannot be found in navigation graph ").append(getGraph()).toString().toString());
        }
        List $this$lastOrNull$iv = getCurrentBackStack().getValue();
        ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
        while (true) {
            if (!iterator$iv.hasPrevious()) {
                break;
            }
            Object element$iv = iterator$iv.previous();
            NavBackStackEntry entry = (NavBackStackEntry) element$iv;
            if (entry.getDestination().getId() == id) {
                obj = element$iv;
                break;
            }
        }
        NavBackStackEntry lastFromBackStack = (NavBackStackEntry) obj;
        if (lastFromBackStack == null) {
            StringBuilder sbAppend2 = new StringBuilder().append("No destination with route ");
            Intrinsics.reifiedOperationMarker(4, "T");
            throw new IllegalArgumentException(sbAppend2.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).append(" is on the NavController's back stack. The current destination is ").append(getCurrentDestination()).toString().toString());
        }
        return lastFromBackStack;
    }

    public final <T> NavBackStackEntry getBackStackEntry(T route) {
        Intrinsics.checkNotNullParameter(route, "route");
        String finalRoute = generateRouteFilled(route);
        return getBackStackEntry(finalRoute);
    }

    public NavBackStackEntry getCurrentBackStackEntry() {
        return this.backQueue.lastOrNull();
    }

    public final Flow<NavBackStackEntry> getCurrentBackStackEntryFlow() {
        return this.currentBackStackEntryFlow;
    }

    public NavBackStackEntry getPreviousBackStackEntry() {
        Object element$iv;
        Iterator iterator = CollectionsKt.reversed(this.backQueue).iterator();
        if (iterator.hasNext()) {
            iterator.next();
        }
        Sequence $this$firstOrNull$iv = SequencesKt.asSequence(iterator);
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                NavBackStackEntry entry = (NavBackStackEntry) element$iv;
                if (!(entry.getDestination() instanceof NavGraph)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (NavBackStackEntry) element$iv;
    }

    /* compiled from: NavController.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u00020\u00048\u0006X\u0087T¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0002R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Landroidx/navigation/NavController$Companion;", "", "()V", "KEY_BACK_STACK", "", "KEY_BACK_STACK_DEST_IDS", "KEY_BACK_STACK_IDS", "KEY_BACK_STACK_STATES_IDS", "KEY_BACK_STACK_STATES_PREFIX", "KEY_DEEP_LINK_ARGS", "KEY_DEEP_LINK_EXTRAS", "getKEY_DEEP_LINK_EXTRAS$annotations", "KEY_DEEP_LINK_HANDLED", "KEY_DEEP_LINK_IDS", "KEY_DEEP_LINK_INTENT", "KEY_NAVIGATOR_STATE", "KEY_NAVIGATOR_STATE_NAMES", "TAG", "deepLinkSaveState", "", "enableDeepLinkSaveState", "", "saveState", "navigation-runtime_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getKEY_DEEP_LINK_EXTRAS$annotations() {
        }

        private Companion() {
        }

        @JvmStatic
        public final void enableDeepLinkSaveState(boolean saveState) {
            NavController.deepLinkSaveState = saveState;
        }
    }

    @JvmStatic
    public static final void enableDeepLinkSaveState(boolean saveState) {
        INSTANCE.enableDeepLinkSaveState(saveState);
    }

    public final /* synthetic */ <T> boolean popBackStack(boolean inclusive) throws Resources.NotFoundException {
        Intrinsics.reifiedOperationMarker(6, "T");
        MagicApiIntrinsics.voidMagicApiCall("kotlinx.serialization.serializer.simple");
        int id$iv = RouteSerializerKt.generateHashCode(SerializersKt.serializer((KType) null));
        if (findDestinationComprehensive(getGraph(), id$iv, true) == null) {
            StringBuilder sbAppend = new StringBuilder().append("Destination with route ");
            Intrinsics.reifiedOperationMarker(4, "T");
            throw new IllegalArgumentException(sbAppend.append(Reflection.getOrCreateKotlinClass(Object.class).getSimpleName()).append(" cannot be found in navigation graph ").append(getGraph()).toString().toString());
        }
        boolean saveState$iv = popBackStack(id$iv, inclusive, false);
        return saveState$iv;
    }
}
