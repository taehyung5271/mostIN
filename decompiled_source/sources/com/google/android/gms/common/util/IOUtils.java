package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;
import kotlin.UByte;

/* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
@Deprecated
/* loaded from: classes.dex */
public final class IOUtils {
    private IOUtils() {
    }

    public static void closeQuietly(@Nullable ParcelFileDescriptor p) throws IOException {
        if (p != null) {
            try {
                p.close();
            } catch (IOException e) {
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    public static long copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copyStream(inputStream, outputStream, false, 1024);
    }

    public static boolean isGzipByteBuffer(byte[] inputBytes) {
        if (inputBytes.length > 1) {
            if ((((inputBytes[1] & UByte.MAX_VALUE) << 8) | (inputBytes[0] & UByte.MAX_VALUE)) == 35615) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static byte[] readInputStreamFully(InputStream is) throws IOException {
        return readInputStreamFully(is, true);
    }

    @Deprecated
    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(byteArrayOutputStream);
        byte[] bArr = new byte[4096];
        while (true) {
            int i = in.read(bArr);
            if (i == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    public static void closeQuietly(@Nullable Closeable c) throws IOException {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean closeWhenDone, int bufferSizeBytes) throws IOException {
        byte[] bArr = new byte[bufferSizeBytes];
        long j = 0;
        while (true) {
            try {
                int i = inputStream.read(bArr, 0, bufferSizeBytes);
                if (i == -1) {
                    break;
                }
                j += i;
                outputStream.write(bArr, 0, i);
            } catch (Throwable th) {
                if (closeWhenDone) {
                    closeQuietly(inputStream);
                    closeQuietly(outputStream);
                }
                throw th;
            }
        }
        if (closeWhenDone) {
            closeQuietly(inputStream);
            closeQuietly(outputStream);
        }
        return j;
    }

    @Deprecated
    public static byte[] readInputStreamFully(InputStream is, boolean closeWhenDone) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyStream(is, byteArrayOutputStream, closeWhenDone, 1024);
        return byteArrayOutputStream.toByteArray();
    }
}
