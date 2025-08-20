package com.getkeepsafe.relinker;

import android.content.Context;
import android.util.Log;
import com.getkeepsafe.relinker.ReLinker;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class ReLinkerInstance {
    private static final String LIB_DIR = "lib";
    protected boolean force;
    protected final ReLinker.LibraryInstaller libraryInstaller;
    protected final ReLinker.LibraryLoader libraryLoader;
    protected final Set<String> loadedLibraries;
    protected ReLinker.Logger logger;
    protected boolean recursive;

    protected ReLinkerInstance() {
        this(new SystemLibraryLoader(), new ApkLibraryInstaller());
    }

    protected ReLinkerInstance(final ReLinker.LibraryLoader libraryLoader, final ReLinker.LibraryInstaller libraryInstaller) {
        this.loadedLibraries = new HashSet();
        if (libraryLoader == null) {
            throw new IllegalArgumentException("Cannot pass null library loader");
        }
        if (libraryInstaller == null) {
            throw new IllegalArgumentException("Cannot pass null library installer");
        }
        this.libraryLoader = libraryLoader;
        this.libraryInstaller = libraryInstaller;
    }

    public ReLinkerInstance log(final ReLinker.Logger logger) {
        this.logger = logger;
        return this;
    }

    public ReLinkerInstance force() {
        this.force = true;
        return this;
    }

    public ReLinkerInstance recursively() {
        this.recursive = true;
        return this;
    }

    public void loadLibrary(final Context context, final String library) {
        loadLibrary(context, library, null, null);
    }

    public void loadLibrary(final Context context, final String library, final String version) {
        loadLibrary(context, library, version, null);
    }

    public void loadLibrary(final Context context, final String library, final ReLinker.LoadListener listener) {
        loadLibrary(context, library, null, listener);
    }

    public void loadLibrary(final Context context, final String library, final String version, final ReLinker.LoadListener listener) {
        if (context == null) {
            throw new IllegalArgumentException("Given context is null");
        }
        if (TextUtils.isEmpty(library)) {
            throw new IllegalArgumentException("Given library is either null or empty");
        }
        log("Beginning load of %s...", library);
        if (listener == null) {
            loadLibraryInternal(context, library, version);
        } else {
            new Thread(new Runnable() { // from class: com.getkeepsafe.relinker.ReLinkerInstance.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ReLinkerInstance.this.loadLibraryInternal(context, library, version);
                        listener.success();
                    } catch (MissingLibraryException e) {
                        listener.failure(e);
                    } catch (UnsatisfiedLinkError e2) {
                        listener.failure(e2);
                    }
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadLibraryInternal(final Context context, final String library, final String version) {
        if (this.loadedLibraries.contains(library) && !this.force) {
            log("%s already loaded previously!", library);
            return;
        }
        try {
            this.libraryLoader.loadLibrary(library);
            this.loadedLibraries.add(library);
            log("%s (%s) was loaded normally!", library, version);
        } catch (UnsatisfiedLinkError e) {
            log("Loading the library normally failed: %s", Log.getStackTraceString(e));
            log("%s (%s) was not loaded normally, re-linking...", library, version);
            File workaroundFile = getWorkaroundLibFile(context, library, version);
            if (!workaroundFile.exists() || this.force) {
                if (this.force) {
                    log("Forcing a re-link of %s (%s)...", library, version);
                }
                cleanupOldLibFiles(context, library, version);
                this.libraryInstaller.installLibrary(context, this.libraryLoader.supportedAbis(), this.libraryLoader.mapLibraryName(library), workaroundFile, this);
            }
            try {
                if (this.recursive) {
                    ElfParser parser = null;
                    try {
                        parser = new ElfParser(workaroundFile);
                        List<String> dependencies = parser.parseNeededDependencies();
                        parser.close();
                        for (String dependency : dependencies) {
                            loadLibrary(context, this.libraryLoader.unmapLibraryName(dependency));
                        }
                    } catch (Throwable th) {
                        parser.close();
                        throw th;
                    }
                }
            } catch (IOException e2) {
            }
            this.libraryLoader.loadPath(workaroundFile.getAbsolutePath());
            this.loadedLibraries.add(library);
            log("%s (%s) was re-linked!", library, version);
        }
    }

    protected File getWorkaroundLibDir(final Context context) {
        return context.getDir(LIB_DIR, 0);
    }

    protected File getWorkaroundLibFile(final Context context, final String library, final String version) {
        String libName = this.libraryLoader.mapLibraryName(library);
        if (TextUtils.isEmpty(version)) {
            return new File(getWorkaroundLibDir(context), libName);
        }
        return new File(getWorkaroundLibDir(context), libName + "." + version);
    }

    protected void cleanupOldLibFiles(final Context context, final String library, final String currentVersion) {
        File workaroundDir = getWorkaroundLibDir(context);
        File workaroundFile = getWorkaroundLibFile(context, library, currentVersion);
        final String mappedLibraryName = this.libraryLoader.mapLibraryName(library);
        File[] existingFiles = workaroundDir.listFiles(new FilenameFilter() { // from class: com.getkeepsafe.relinker.ReLinkerInstance.2
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String filename) {
                return filename.startsWith(mappedLibraryName);
            }
        });
        if (existingFiles == null) {
            return;
        }
        for (File file : existingFiles) {
            if (this.force || !file.getAbsolutePath().equals(workaroundFile.getAbsolutePath())) {
                file.delete();
            }
        }
    }

    public void log(final String format, final Object... args) {
        log(String.format(Locale.US, format, args));
    }

    public void log(final String message) {
        if (this.logger != null) {
            this.logger.log(message);
        }
    }
}
