package com.getkeepsafe.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.getkeepsafe.relinker.ReLinker;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    private String[] sourceDirectories(final Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        if (appInfo.splitSourceDirs != null && appInfo.splitSourceDirs.length != 0) {
            String[] apks = new String[appInfo.splitSourceDirs.length + 1];
            apks[0] = appInfo.sourceDir;
            System.arraycopy(appInfo.splitSourceDirs, 0, apks, 1, appInfo.splitSourceDirs.length);
            return apks;
        }
        return new String[]{appInfo.sourceDir};
    }

    private static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile, ZipEntry zipEntry) {
            this.zipFile = zipFile;
            this.zipEntry = zipEntry;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(final Context context, final String[] abis, final String mappedLibraryName, final ReLinkerInstance instance) throws IOException {
        int i;
        for (String sourceDir : sourceDirectories(context)) {
            ZipFile zipFile = null;
            int tries = 0;
            while (true) {
                int tries2 = tries + 1;
                i = 5;
                if (tries >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(sourceDir), 1);
                    break;
                } catch (IOException e) {
                    tries = tries2;
                }
            }
            if (zipFile != null) {
                int tries3 = 0;
                while (true) {
                    int tries4 = tries3 + 1;
                    if (tries3 < i) {
                        for (String abi : abis) {
                            String jniNameInApk = "lib" + File.separatorChar + abi + File.separatorChar + mappedLibraryName;
                            instance.log("Looking for %s in APK %s...", jniNameInApk, sourceDir);
                            ZipEntry libraryEntry = zipFile.getEntry(jniNameInApk);
                            if (libraryEntry != null) {
                                return new ZipFileInZipEntry(zipFile, libraryEntry);
                            }
                        }
                        tries3 = tries4;
                        i = 5;
                    } else {
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException e2) {
                        }
                    }
                }
            }
        }
        return null;
    }

    private String[] getSupportedABIs(Context context, String mappedLibraryName) {
        String p = "lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + mappedLibraryName;
        Pattern pattern = Pattern.compile(p);
        Set<String> supportedABIs = new HashSet<>();
        for (String sourceDir : sourceDirectories(context)) {
            try {
                ZipFile zipFile = new ZipFile(new File(sourceDir), 1);
                Enumeration<? extends ZipEntry> elements = zipFile.entries();
                while (elements.hasMoreElements()) {
                    ZipEntry el = elements.nextElement();
                    Matcher match = pattern.matcher(el.getName());
                    if (match.matches()) {
                        supportedABIs.add(match.group(1));
                    }
                }
            } catch (IOException e) {
            }
        }
        String[] result = new String[supportedABIs.size()];
        return (String[]) supportedABIs.toArray(result);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.getkeepsafe.relinker.ReLinker.LibraryInstaller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void installLibrary(final android.content.Context r11, final java.lang.String[] r12, final java.lang.String r13, final java.io.File r14, final com.getkeepsafe.relinker.ReLinkerInstance r15) throws java.io.IOException {
        /*
            r10 = this;
            r0 = 0
            com.getkeepsafe.relinker.ApkLibraryInstaller$ZipFileInZipEntry r1 = r10.findAPKWithLibrary(r11, r12, r13, r15)     // Catch: java.lang.Throwable -> Lb9
            r0 = r1
            r1 = 0
            r2 = 1
            if (r0 == 0) goto La4
            r3 = 0
        Lb:
            int r4 = r3 + 1
            r5 = 5
            if (r3 >= r5) goto L8f
            java.lang.String r3 = "Found %s! Extracting..."
            java.lang.Object[] r5 = new java.lang.Object[]{r13}     // Catch: java.lang.Throwable -> Lb9
            r15.log(r3, r5)     // Catch: java.lang.Throwable -> Lb9
            boolean r3 = r14.exists()     // Catch: java.io.IOException -> L8a java.lang.Throwable -> Lb9
            if (r3 != 0) goto L26
            boolean r3 = r14.createNewFile()     // Catch: java.io.IOException -> L8a java.lang.Throwable -> Lb9
            if (r3 != 0) goto L26
            goto L8c
        L26:
            r3 = 0
            r5 = 0
            java.util.zip.ZipFile r6 = r0.zipFile     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            java.util.zip.ZipEntry r7 = r0.zipEntry     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            java.io.InputStream r6 = r6.getInputStream(r7)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            r3 = r6
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            r6.<init>(r14)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            r5 = r6
            long r6 = r10.copy(r3, r5)     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            java.io.FileDescriptor r8 = r5.getFD()     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            r8.sync()     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            long r8 = r14.length()     // Catch: java.lang.Throwable -> L71 java.io.IOException -> L7a java.io.FileNotFoundException -> L82
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 == 0) goto L52
            r10.closeSilently(r3)     // Catch: java.lang.Throwable -> Lb9
            r10.closeSilently(r5)     // Catch: java.lang.Throwable -> Lb9
            goto L8c
        L52:
            r10.closeSilently(r3)     // Catch: java.lang.Throwable -> Lb9
            r10.closeSilently(r5)     // Catch: java.lang.Throwable -> Lb9
            r14.setReadable(r2, r1)     // Catch: java.lang.Throwable -> Lb9
            r14.setExecutable(r2, r1)     // Catch: java.lang.Throwable -> Lb9
            r14.setWritable(r2)     // Catch: java.lang.Throwable -> Lb9
            if (r0 == 0) goto L6f
            java.util.zip.ZipFile r1 = r0.zipFile     // Catch: java.io.IOException -> L6e
            if (r1 == 0) goto L6f
            java.util.zip.ZipFile r1 = r0.zipFile     // Catch: java.io.IOException -> L6e
            r1.close()     // Catch: java.io.IOException -> L6e
            goto L6f
        L6e:
            r1 = move-exception
        L6f:
            return
        L71:
            r1 = move-exception
            r10.closeSilently(r3)     // Catch: java.lang.Throwable -> Lb9
            r10.closeSilently(r5)     // Catch: java.lang.Throwable -> Lb9
            throw r1     // Catch: java.lang.Throwable -> Lb9
        L7a:
            r6 = move-exception
            r10.closeSilently(r3)     // Catch: java.lang.Throwable -> Lb9
            r10.closeSilently(r5)     // Catch: java.lang.Throwable -> Lb9
            goto L8c
        L82:
            r6 = move-exception
            r10.closeSilently(r3)     // Catch: java.lang.Throwable -> Lb9
            r10.closeSilently(r5)     // Catch: java.lang.Throwable -> Lb9
            goto L8c
        L8a:
            r3 = move-exception
        L8c:
            r3 = r4
            goto Lb
        L8f:
            java.lang.String r1 = "FATAL! Couldn't extract the library from the APK!"
            r15.log(r1)     // Catch: java.lang.Throwable -> Lb9
            if (r0 == 0) goto La2
            java.util.zip.ZipFile r1 = r0.zipFile     // Catch: java.io.IOException -> La0
            if (r1 == 0) goto La2
            java.util.zip.ZipFile r1 = r0.zipFile     // Catch: java.io.IOException -> La0
            r1.close()     // Catch: java.io.IOException -> La0
            goto La2
        La0:
            r1 = move-exception
            goto La3
        La2:
        La3:
            return
        La4:
            java.lang.String[] r1 = r10.getSupportedABIs(r11, r13)     // Catch: java.lang.Exception -> La9 java.lang.Throwable -> Lb9
            goto Lb3
        La9:
            r3 = move-exception
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r4 = r3.toString()     // Catch: java.lang.Throwable -> Lb9
            r2[r1] = r4     // Catch: java.lang.Throwable -> Lb9
            r1 = r2
        Lb3:
            com.getkeepsafe.relinker.MissingLibraryException r2 = new com.getkeepsafe.relinker.MissingLibraryException     // Catch: java.lang.Throwable -> Lb9
            r2.<init>(r13, r12, r1)     // Catch: java.lang.Throwable -> Lb9
            throw r2     // Catch: java.lang.Throwable -> Lb9
        Lb9:
            r1 = move-exception
            if (r0 == 0) goto Lc7
            java.util.zip.ZipFile r2 = r0.zipFile     // Catch: java.io.IOException -> Lc6
            if (r2 == 0) goto Lc7
            java.util.zip.ZipFile r2 = r0.zipFile     // Catch: java.io.IOException -> Lc6
            r2.close()     // Catch: java.io.IOException -> Lc6
            goto Lc7
        Lc6:
            r2 = move-exception
        Lc7:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getkeepsafe.relinker.ApkLibraryInstaller.installLibrary(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.getkeepsafe.relinker.ReLinkerInstance):void");
    }

    private long copy(InputStream in, OutputStream out) throws IOException {
        long copied = 0;
        byte[] buf = new byte[4096];
        while (true) {
            int read = in.read(buf);
            if (read != -1) {
                out.write(buf, 0, read);
                copied += read;
            } else {
                out.flush();
                return copied;
            }
        }
    }

    private void closeSilently(final Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
