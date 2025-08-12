package com.getkeepsafe.relinker;

import android.os.Build;
import com.getkeepsafe.relinker.ReLinker;

/* loaded from: classes.dex */
final class SystemLibraryLoader implements ReLinker.LibraryLoader {
    SystemLibraryLoader() {
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadLibrary(final String libraryName) {
        System.loadLibrary(libraryName);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public void loadPath(final String libraryPath) {
        System.load(libraryPath);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String mapLibraryName(final String libraryName) {
        if (libraryName.startsWith("lib") && libraryName.endsWith(".so")) {
            return libraryName;
        }
        return System.mapLibraryName(libraryName);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String unmapLibraryName(String mappedLibraryName) {
        return mappedLibraryName.substring(3, mappedLibraryName.length() - 3);
    }

    @Override // com.getkeepsafe.relinker.ReLinker.LibraryLoader
    public String[] supportedAbis() {
        if (Build.SUPPORTED_ABIS.length > 0) {
            return Build.SUPPORTED_ABIS;
        }
        if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
            return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        return new String[]{Build.CPU_ABI};
    }
}
