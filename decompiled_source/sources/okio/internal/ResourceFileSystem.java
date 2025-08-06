package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.FileHandle;
import okio.FileMetadata;
import okio.FileSystem;
import okio.Path;
import okio.Sink;
import okio.Source;
import okio.ZipFileSystem;

/* compiled from: ResourceFileSystem.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000 *2\u00020\u0001:\u0001*B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0002J\u0018\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0005H\u0016J\u0018\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nH\u0016J\u0018\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0018\u0010 \u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\b2\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u0011\u001a\u00020\nH\u0016J \u0010%\u001a\u00020$2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0005H\u0016J\u0010\u0010\u0015\u001a\u00020'2\u0006\u0010\u0011\u001a\u00020\nH\u0016J\f\u0010(\u001a\u00020)*\u00020\nH\u0002R-\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006+"}, d2 = {"Lokio/internal/ResourceFileSystem;", "Lokio/FileSystem;", "classLoader", "Ljava/lang/ClassLoader;", "indexEagerly", "", "(Ljava/lang/ClassLoader;Z)V", "roots", "", "Lkotlin/Pair;", "Lokio/Path;", "getRoots", "()Ljava/util/List;", "roots$delegate", "Lkotlin/Lazy;", "appendingSink", "Lokio/Sink;", "file", "mustExist", "atomicMove", "", "source", TypedValues.AttributesType.S_TARGET, "canonicalize", "path", "canonicalizeInternal", "createDirectory", "dir", "mustCreate", "createSymlink", "delete", "list", "listOrNull", "metadataOrNull", "Lokio/FileMetadata;", "openReadOnly", "Lokio/FileHandle;", "openReadWrite", "sink", "Lokio/Source;", "toRelativePath", "", "Companion", "okio"}, k = 1, mv = {1, 5, 1}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes2.dex */
public final class ResourceFileSystem extends FileSystem {
    private static final Companion Companion = new Companion(null);

    @Deprecated
    private static final Path ROOT = Path.Companion.get$default(Path.INSTANCE, "/", false, 1, (Object) null);

    /* renamed from: roots$delegate, reason: from kotlin metadata */
    private final Lazy roots;

    public ResourceFileSystem(final ClassLoader classLoader, boolean indexEagerly) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.roots = LazyKt.lazy(new Function0<List<? extends Pair<? extends FileSystem, ? extends Path>>>() { // from class: okio.internal.ResourceFileSystem$roots$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Pair<? extends FileSystem, ? extends Path>> invoke() {
                return ResourceFileSystem.Companion.toClasspathRoots(classLoader);
            }
        });
        if (!indexEagerly) {
            return;
        }
        getRoots().size();
    }

    private final List<Pair<FileSystem, Path>> getRoots() {
        return (List) this.roots.getValue();
    }

    @Override // okio.FileSystem
    public Path canonicalize(Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return canonicalizeInternal(path);
    }

    private final Path canonicalizeInternal(Path path) {
        return ROOT.resolve(path, true);
    }

    /* JADX WARN: Incorrect condition in loop: B:4:0x0021 */
    @Override // okio.FileSystem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<okio.Path> list(okio.Path r19) throws java.io.FileNotFoundException {
        /*
            r18 = this;
            r1 = r19
            java.lang.String r0 = "dir"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            java.lang.String r2 = r18.toRelativePath(r19)
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            r3 = r0
            java.util.Set r3 = (java.util.Set) r3
            r0 = 0
            java.util.List r4 = r18.getRoots()
            java.util.Iterator r4 = r4.iterator()
            r5 = r0
        L1d:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto Lc4
            java.lang.Object r0 = r4.next()
            kotlin.Pair r0 = (kotlin.Pair) r0
            java.lang.Object r6 = r0.component1()
            okio.FileSystem r6 = (okio.FileSystem) r6
            java.lang.Object r0 = r0.component2()
            r7 = r0
            okio.Path r7 = (okio.Path) r7
            r0 = r3
            java.util.Collection r0 = (java.util.Collection) r0     // Catch: java.io.IOException -> Lbd
            okio.Path r8 = r7.resolve(r2)     // Catch: java.io.IOException -> Lbd
            java.util.List r8 = r6.list(r8)     // Catch: java.io.IOException -> Lbd
            java.lang.Iterable r8 = (java.lang.Iterable) r8     // Catch: java.io.IOException -> Lbd
            r9 = 0
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch: java.io.IOException -> Lbd
            r10.<init>()     // Catch: java.io.IOException -> Lbd
            java.util.Collection r10 = (java.util.Collection) r10     // Catch: java.io.IOException -> Lbd
            r11 = r8
            r12 = 0
            java.util.Iterator r13 = r11.iterator()     // Catch: java.io.IOException -> Lbd
        L53:
            boolean r14 = r13.hasNext()     // Catch: java.io.IOException -> Lbd
            if (r14 == 0) goto L72
            java.lang.Object r14 = r13.next()     // Catch: java.io.IOException -> Lbd
            r15 = r14
            okio.Path r15 = (okio.Path) r15     // Catch: java.io.IOException -> Lbd
            r16 = 0
            r17 = r2
            okio.internal.ResourceFileSystem$Companion r2 = okio.internal.ResourceFileSystem.Companion     // Catch: java.io.IOException -> Lbb
            boolean r2 = okio.internal.ResourceFileSystem.Companion.access$keepPath(r2, r15)     // Catch: java.io.IOException -> Lbb
            if (r2 == 0) goto L6f
            r10.add(r14)     // Catch: java.io.IOException -> Lbb
        L6f:
            r2 = r17
            goto L53
        L72:
            r17 = r2
            r2 = r10
            java.util.List r2 = (java.util.List) r2     // Catch: java.io.IOException -> Lbb
            java.lang.Iterable r2 = (java.lang.Iterable) r2     // Catch: java.io.IOException -> Lbb
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch: java.io.IOException -> Lbb
            r10 = 10
            int r10 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r2, r10)     // Catch: java.io.IOException -> Lbb
            r9.<init>(r10)     // Catch: java.io.IOException -> Lbb
            java.util.Collection r9 = (java.util.Collection) r9     // Catch: java.io.IOException -> Lbb
            r10 = r2
            r11 = 0
            java.util.Iterator r12 = r10.iterator()     // Catch: java.io.IOException -> Lbb
        L8f:
            boolean r13 = r12.hasNext()     // Catch: java.io.IOException -> Lbb
            if (r13 == 0) goto Lab
            java.lang.Object r13 = r12.next()     // Catch: java.io.IOException -> Lbb
            r14 = r13
            okio.Path r14 = (okio.Path) r14     // Catch: java.io.IOException -> Lbb
            r15 = 0
            r16 = r2
            okio.internal.ResourceFileSystem$Companion r2 = okio.internal.ResourceFileSystem.Companion     // Catch: java.io.IOException -> Lbb
            okio.Path r2 = r2.removeBase(r14, r7)     // Catch: java.io.IOException -> Lbb
            r9.add(r2)     // Catch: java.io.IOException -> Lbb
            r2 = r16
            goto L8f
        Lab:
            r16 = r2
            r2 = r9
            java.util.List r2 = (java.util.List) r2     // Catch: java.io.IOException -> Lbb
            java.lang.Iterable r2 = (java.lang.Iterable) r2     // Catch: java.io.IOException -> Lbb
            kotlin.collections.CollectionsKt.addAll(r0, r2)     // Catch: java.io.IOException -> Lbb
            r5 = 1
            r2 = r17
            goto L1d
        Lbb:
            r0 = move-exception
            goto Lc0
        Lbd:
            r0 = move-exception
            r17 = r2
        Lc0:
            r2 = r17
            goto L1d
        Lc4:
            r17 = r2
            if (r5 == 0) goto Ld0
            r0 = r3
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.List r0 = kotlin.collections.CollectionsKt.toList(r0)
            return r0
        Ld0:
            java.io.FileNotFoundException r0 = new java.io.FileNotFoundException
            java.lang.String r2 = "file not found: "
            java.lang.String r2 = kotlin.jvm.internal.Intrinsics.stringPlus(r2, r1)
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.ResourceFileSystem.list(okio.Path):java.util.List");
    }

    @Override // okio.FileSystem
    public List<Path> listOrNull(Path dir) {
        String relativePath;
        Intrinsics.checkNotNullParameter(dir, "dir");
        String relativePath2 = toRelativePath(dir);
        Set result = new LinkedHashSet();
        boolean foundAny = false;
        Iterator<Pair<FileSystem, Path>> it = getRoots().iterator();
        while (true) {
            Iterable iterable = null;
            if (!it.hasNext()) {
                break;
            }
            Pair<FileSystem, Path> next = it.next();
            FileSystem fileSystem = next.component1();
            Path base = next.component2();
            Iterable iterableListOrNull = fileSystem.listOrNull(base.resolve(relativePath2));
            if (iterableListOrNull != null) {
                Iterable $this$filter$iv = iterableListOrNull;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Path it2 = (Path) element$iv$iv;
                    String relativePath3 = relativePath2;
                    if (Companion.keepPath(it2)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    relativePath2 = relativePath3;
                }
                relativePath = relativePath2;
                Iterable $this$map$iv = (List) destination$iv$iv;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Path it3 = (Path) item$iv$iv;
                    destination$iv$iv2.add(Companion.removeBase(it3, base));
                }
                iterable = (List) destination$iv$iv2;
            } else {
                relativePath = relativePath2;
            }
            Iterable $this$map$iv2 = iterable;
            if ($this$map$iv2 == null) {
                relativePath2 = relativePath;
            } else {
                CollectionsKt.addAll(result, $this$map$iv2);
                foundAny = true;
                relativePath2 = relativePath;
            }
        }
        if (foundAny) {
            return CollectionsKt.toList(result);
        }
        return null;
    }

    @Override // okio.FileSystem
    public FileHandle openReadOnly(Path file) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(file, "file");
        String str = "file not found: ";
        if (!Companion.keepPath(file)) {
            throw new FileNotFoundException(Intrinsics.stringPlus("file not found: ", file));
        }
        String relativePath = toRelativePath(file);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            FileSystem fileSystem = pair.component1();
            Path base = pair.component2();
            try {
                return fileSystem.openReadOnly(base.resolve(relativePath));
            } catch (FileNotFoundException e) {
            }
        }
        throw new FileNotFoundException(Intrinsics.stringPlus(str, file));
    }

    @Override // okio.FileSystem
    public FileHandle openReadWrite(Path file, boolean mustCreate, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException("resources are not writable");
    }

    @Override // okio.FileSystem
    public FileMetadata metadataOrNull(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        if (!Companion.keepPath(path)) {
            return null;
        }
        String relativePath = toRelativePath(path);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            FileSystem fileSystem = pair.component1();
            Path base = pair.component2();
            FileMetadata fileMetadataMetadataOrNull = fileSystem.metadataOrNull(base.resolve(relativePath));
            if (fileMetadataMetadataOrNull != null) {
                return fileMetadataMetadataOrNull;
            }
        }
        return null;
    }

    @Override // okio.FileSystem
    public Source source(Path file) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(file, "file");
        String str = "file not found: ";
        if (!Companion.keepPath(file)) {
            throw new FileNotFoundException(Intrinsics.stringPlus("file not found: ", file));
        }
        String relativePath = toRelativePath(file);
        for (Pair<FileSystem, Path> pair : getRoots()) {
            FileSystem fileSystem = pair.component1();
            Path base = pair.component2();
            try {
                return fileSystem.source(base.resolve(relativePath));
            } catch (FileNotFoundException e) {
            }
        }
        throw new FileNotFoundException(Intrinsics.stringPlus(str, file));
    }

    @Override // okio.FileSystem
    public Sink sink(Path file, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public Sink appendingSink(Path file, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void createDirectory(Path dir, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void atomicMove(Path source, Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void delete(Path path, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        throw new IOException(this + " is read-only");
    }

    @Override // okio.FileSystem
    public void createSymlink(Path source, Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        throw new IOException(this + " is read-only");
    }

    private final String toRelativePath(Path $this$toRelativePath) {
        Path canonicalThis = canonicalizeInternal($this$toRelativePath);
        return canonicalThis.relativeTo(ROOT).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ResourceFileSystem.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0012\u0010\n\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001c\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00040\u000e0\r*\u00020\u0010J\u0018\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e*\u00020\u0012J\u0018\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e*\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lokio/internal/ResourceFileSystem$Companion;", "", "()V", "ROOT", "Lokio/Path;", "getROOT", "()Lokio/Path;", "keepPath", "", "path", "removeBase", "base", "toClasspathRoots", "", "Lkotlin/Pair;", "Lokio/FileSystem;", "Ljava/lang/ClassLoader;", "toFileRoot", "Ljava/net/URL;", "toJarRoot", "okio"}, k = 1, mv = {1, 5, 1}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Path getROOT() {
            return ResourceFileSystem.ROOT;
        }

        public final Path removeBase(Path $this$removeBase, Path base) {
            Intrinsics.checkNotNullParameter($this$removeBase, "<this>");
            Intrinsics.checkNotNullParameter(base, "base");
            String prefix = base.toString();
            return getROOT().resolve(StringsKt.replace$default(StringsKt.removePrefix($this$removeBase.toString(), (CharSequence) prefix), '\\', '/', false, 4, (Object) null));
        }

        public final List<Pair<FileSystem, Path>> toClasspathRoots(ClassLoader $this$toClasspathRoots) throws IOException {
            Intrinsics.checkNotNullParameter($this$toClasspathRoots, "<this>");
            Enumeration<URL> resources = $this$toClasspathRoots.getResources("");
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(\"\")");
            Iterable list = Collections.list(resources);
            Intrinsics.checkNotNullExpressionValue(list, "java.util.Collections.list(this)");
            Iterable $this$mapNotNull$iv = (List) list;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                URL it = (URL) element$iv$iv$iv;
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                Companion companion = ResourceFileSystem.Companion;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                Pair<FileSystem, Path> fileRoot = companion.toFileRoot(it);
                if (fileRoot != null) {
                    destination$iv$iv.add(fileRoot);
                }
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
            }
            ArrayList arrayList = (List) destination$iv$iv;
            Enumeration<URL> resources2 = $this$toClasspathRoots.getResources("META-INF/MANIFEST.MF");
            Intrinsics.checkNotNullExpressionValue(resources2, "getResources(\"META-INF/MANIFEST.MF\")");
            Iterable list2 = Collections.list(resources2);
            Intrinsics.checkNotNullExpressionValue(list2, "java.util.Collections.list(this)");
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv$iv2 : (List) list2) {
                URL it2 = (URL) element$iv$iv$iv2;
                Companion companion2 = ResourceFileSystem.Companion;
                Intrinsics.checkNotNullExpressionValue(it2, "it");
                Pair<FileSystem, Path> jarRoot = companion2.toJarRoot(it2);
                if (jarRoot != null) {
                    destination$iv$iv2.add(jarRoot);
                }
            }
            return CollectionsKt.plus((Collection) arrayList, destination$iv$iv2);
        }

        public final Pair<FileSystem, Path> toFileRoot(URL $this$toFileRoot) {
            Intrinsics.checkNotNullParameter($this$toFileRoot, "<this>");
            if (Intrinsics.areEqual($this$toFileRoot.getProtocol(), "file")) {
                return TuplesKt.to(FileSystem.SYSTEM, Path.Companion.get$default(Path.INSTANCE, new File($this$toFileRoot.toURI()), false, 1, (Object) null));
            }
            return null;
        }

        public final Pair<FileSystem, Path> toJarRoot(URL $this$toJarRoot) throws IOException {
            int suffixStart;
            Intrinsics.checkNotNullParameter($this$toJarRoot, "<this>");
            String urlString = $this$toJarRoot.toString();
            Intrinsics.checkNotNullExpressionValue(urlString, "toString()");
            if (!StringsKt.startsWith$default(urlString, "jar:file:", false, 2, (Object) null) || (suffixStart = StringsKt.lastIndexOf$default((CharSequence) urlString, "!", 0, false, 6, (Object) null)) == -1) {
                return null;
            }
            Path.Companion companion = Path.INSTANCE;
            String strSubstring = urlString.substring("jar:".length(), suffixStart);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            Path path = Path.Companion.get$default(companion, new File(URI.create(strSubstring)), false, 1, (Object) null);
            ZipFileSystem zip = ZipKt.openZip(path, FileSystem.SYSTEM, new Function1<ZipEntry, Boolean>() { // from class: okio.internal.ResourceFileSystem$Companion$toJarRoot$zip$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ZipEntry entry) {
                    Intrinsics.checkNotNullParameter(entry, "entry");
                    return Boolean.valueOf(ResourceFileSystem.Companion.keepPath(entry.getCanonicalPath()));
                }
            });
            return TuplesKt.to(zip, getROOT());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean keepPath(Path path) {
            return !StringsKt.endsWith(path.name(), ".class", true);
        }
    }
}
