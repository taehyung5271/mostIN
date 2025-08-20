package com.naver.maps.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.widget.Toast;
import com.naver.maps.map.internal.FileSource;
import com.naver.maps.map.internal.http.NativeHttpRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public final class NaverMapSdk {

    @Deprecated
    public static final String METADATA_NAME = "com.naver.maps.map.CLIENT_ID";
    public static final String METADATA_NAME_CACHE_LOCATION = "com.naver.maps.map.CACHE_LOCATION";
    public static final String METADATA_NAME_CLIENT_ID = "com.naver.maps.map.CLIENT_ID";
    public static final String METADATA_NAME_CLIENT_TYPE = "com.naver.maps.map.CLIENT_TYPE";
    public static final String METADATA_VALUE_CACHE_LOCATION_CACHE = "cache";
    public static final String METADATA_VALUE_CACHE_LOCATION_DATA = "data";
    public static final String METADATA_VALUE_CACHE_LOCATION_EXTERNAL = "external";
    public static final String METADATA_VALUE_CLIENT_TYPE_DEFAULT = "";
    public static final String METADATA_VALUE_CLIENT_TYPE_GOV = "gov";
    private static NaverMapSdk a;
    private final Context b;
    private final a c;
    private final SharedPreferences d;
    private Client e;
    private OnAuthFailedListener f;
    private c g;

    public interface CacheFlushCallback {
        void onCacheFlushed();
    }

    public interface OnAuthFailedListener {
        void onAuthFailed(AuthFailedException authFailedException);
    }

    interface b {
        void a(AuthFailedException authFailedException);

        void a(String[] strArr);

        void a(String[] strArr, Exception exc);
    }

    interface d {
        void a(Exception exc);

        void a(String str);
    }

    public static abstract class Client {
        abstract c a(NaverMapSdk naverMapSdk);

        private Client() {
        }
    }

    public static final class NaverCloudPlatformClient extends Client {
        public final boolean a;
        public final boolean b;
        public final String clientId;

        public NaverCloudPlatformClient(String clientId) {
            super();
            this.clientId = clientId;
            this.a = false;
            this.b = false;
        }

        @Override // com.naver.maps.map.NaverMapSdk.Client
        c a(NaverMapSdk naverMapSdk) {
            return new e(this.clientId, this.a, this.b, false);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NaverCloudPlatformClient naverCloudPlatformClient = (NaverCloudPlatformClient) o;
            if (this.a != naverCloudPlatformClient.a || this.b != naverCloudPlatformClient.b) {
                return false;
            }
            return this.clientId.equals(naverCloudPlatformClient.clientId);
        }

        public int hashCode() {
            return (((this.clientId.hashCode() * 31) + (this.a ? 1 : 0)) * 31) + (this.b ? 1 : 0);
        }
    }

    public static final class NaverCloudPlatformGovClient extends Client {
        public final boolean a;
        public final String clientId;

        public NaverCloudPlatformGovClient(String clientId) {
            super();
            this.clientId = clientId;
            this.a = false;
        }

        @Override // com.naver.maps.map.NaverMapSdk.Client
        c a(NaverMapSdk naverMapSdk) {
            return new e(this.clientId, this.a, false, true);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NaverCloudPlatformGovClient naverCloudPlatformGovClient = (NaverCloudPlatformGovClient) o;
            if (this.a != naverCloudPlatformGovClient.a) {
                return false;
            }
            return this.clientId.equals(naverCloudPlatformGovClient.clientId);
        }

        public int hashCode() {
            return (this.clientId.hashCode() * 31) + (this.a ? 1 : 0);
        }
    }

    public static class AuthFailedException extends RuntimeException {
        private final String a;

        private AuthFailedException(String errorCode, String message) {
            super("[" + errorCode + "] " + message);
            this.a = errorCode;
        }

        public String getErrorCode() {
            return this.a;
        }
    }

    public static class ClientUnspecifiedException extends AuthFailedException {
        private ClientUnspecifiedException() {
            super("800", "Client is unspecified. You should set the metadata in your application manifest, or call setClient() first.");
        }
    }

    public static class UnauthorizedClientException extends AuthFailedException {
        private UnauthorizedClientException() {
            super("401", "Unauthorized client");
        }
    }

    public static class QuotaExceededException extends AuthFailedException {
        private QuotaExceededException() {
            super("429", "Quota exceeded");
        }
    }

    private static class a {
        public final String a;
        public final String b;
        public final String c;

        private a(Context context) {
            String str;
            this.a = context.getPackageName();
            try {
                str = context.getPackageManager().getPackageInfo(this.a, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                str = "x.x";
            }
            this.b = str;
            this.c = "openapi_android_" + this.a + "_" + str;
        }
    }

    private static abstract class c {
        protected final NaverMapSdk a;
        protected final Handler b = new Handler(Looper.getMainLooper());
        private final String[] c;
        private String[] d;

        protected interface a {
            void a(AuthFailedException authFailedException);

            void a(Exception exc);

            void a(ResponseBody responseBody) throws Exception;
        }

        protected abstract String a(a aVar) throws Exception;

        protected abstract ResponseBody a(Response response) throws Exception;

        protected abstract void a(String str, a aVar, d dVar);

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0083, code lost:
        
            if (r4 >= 2) goto L52;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x008b, code lost:
        
            if (android.text.TextUtils.isEmpty(r1[r4]) != false) goto L61;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x008d, code lost:
        
            r4 = r4 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0095, code lost:
        
            throw new java.io.IOException("Internal error");
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected static java.lang.String[] a(okhttp3.ResponseBody r6) throws java.lang.Exception {
            /*
                android.util.JsonReader r0 = new android.util.JsonReader
                java.io.Reader r6 = r6.charStream()
                r0.<init>(r6)
                r6 = 2
                java.lang.String[] r1 = new java.lang.String[r6]     // Catch: java.lang.Throwable -> L9d
                r0.beginObject()     // Catch: java.lang.Throwable -> L9d
            Lf:
                boolean r2 = r0.hasNext()     // Catch: java.lang.Throwable -> L9d
                java.lang.String r3 = "Internal error"
                r4 = 0
                if (r2 == 0) goto L82
                java.lang.String r2 = r0.nextName()     // Catch: java.lang.Throwable -> L9d
                java.lang.String r5 = "error_code"
                boolean r5 = r5.equals(r2)     // Catch: java.lang.Throwable -> L9d
                if (r5 == 0) goto L46
                java.lang.String r6 = r0.nextString()     // Catch: java.lang.Throwable -> L9d
                java.lang.String r1 = "052"
                boolean r1 = r1.equals(r6)     // Catch: java.lang.Throwable -> L9d
                if (r1 != 0) goto L3f
                java.lang.String r1 = "053"
                boolean r6 = r1.equals(r6)     // Catch: java.lang.Throwable -> L9d
                if (r6 == 0) goto L39
                goto L3f
            L39:
                java.io.IOException r6 = new java.io.IOException     // Catch: java.lang.Throwable -> L9d
                r6.<init>(r3)     // Catch: java.lang.Throwable -> L9d
                throw r6     // Catch: java.lang.Throwable -> L9d
            L3f:
                com.naver.maps.map.NaverMapSdk$UnauthorizedClientException r6 = new com.naver.maps.map.NaverMapSdk$UnauthorizedClientException     // Catch: java.lang.Throwable -> L9d
                r1 = 0
                r6.<init>()     // Catch: java.lang.Throwable -> L9d
                throw r6     // Catch: java.lang.Throwable -> L9d
            L46:
                java.lang.String r5 = "styleUrls"
                boolean r2 = r5.equals(r2)     // Catch: java.lang.Throwable -> L9d
                if (r2 == 0) goto L7e
                r0.beginObject()     // Catch: java.lang.Throwable -> L9d
            L51:
                boolean r2 = r0.hasNext()     // Catch: java.lang.Throwable -> L9d
                if (r2 == 0) goto L82
                java.lang.String r2 = r0.nextName()     // Catch: java.lang.Throwable -> L9d
                java.lang.String r5 = "default"
                boolean r5 = r5.equals(r2)     // Catch: java.lang.Throwable -> L9d
                if (r5 == 0) goto L6a
                java.lang.String r2 = r0.nextString()     // Catch: java.lang.Throwable -> L9d
                r1[r4] = r2     // Catch: java.lang.Throwable -> L9d
                goto L7d
            L6a:
                java.lang.String r5 = "lite"
                boolean r2 = r5.equals(r2)     // Catch: java.lang.Throwable -> L9d
                if (r2 == 0) goto L7a
                java.lang.String r2 = r0.nextString()     // Catch: java.lang.Throwable -> L9d
                r5 = 1
                r1[r5] = r2     // Catch: java.lang.Throwable -> L9d
                goto L7d
            L7a:
                r0.skipValue()     // Catch: java.lang.Throwable -> L9d
            L7d:
                goto L51
            L7e:
                r0.skipValue()     // Catch: java.lang.Throwable -> L9d
                goto Lf
            L82:
            L83:
                if (r4 >= r6) goto L96
                r2 = r1[r4]     // Catch: java.lang.Throwable -> L9d
                boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L9d
                if (r2 != 0) goto L90
                int r4 = r4 + 1
                goto L83
            L90:
                java.io.IOException r6 = new java.io.IOException     // Catch: java.lang.Throwable -> L9d
                r6.<init>(r3)     // Catch: java.lang.Throwable -> L9d
                throw r6     // Catch: java.lang.Throwable -> L9d
            L96:
                r0.close()     // Catch: java.io.IOException -> L9b
                goto L9c
            L9b:
                r6 = move-exception
            L9c:
                return r1
            L9d:
                r6 = move-exception
                r0.close()     // Catch: java.io.IOException -> La2
                goto La3
            La2:
                r0 = move-exception
            La3:
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.naver.maps.map.NaverMapSdk.c.a(okhttp3.ResponseBody):java.lang.String[]");
        }

        protected static String b(ResponseBody responseBody) throws Exception {
            JsonReader jsonReader = new JsonReader(responseBody.charStream());
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    if ("default".equals(jsonReader.nextName())) {
                        return jsonReader.nextString();
                    }
                    jsonReader.skipValue();
                }
                throw new IOException("Internal error");
            } finally {
                try {
                    jsonReader.close();
                } catch (IOException e) {
                }
            }
        }

        c(NaverMapSdk naverMapSdk, String... strArr) {
            this.a = naverMapSdk;
            this.c = strArr;
            this.d = new String[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                this.d[i] = naverMapSdk.d.getString(strArr[i], null);
            }
        }

        protected void a(String str, final a aVar) {
            com.naver.maps.map.internal.http.a.a().newBuilder().connectTimeout(5L, TimeUnit.SECONDS).callTimeout(5L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build().newCall(new Request.Builder().url(str).addHeader("User-Agent", NativeHttpRequest.a).addHeader("Referer", "client://NaverMapSDK").build()).enqueue(new Callback() { // from class: com.naver.maps.map.NaverMapSdk.c.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException e) {
                    aVar.a(e);
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    try {
                        aVar.a(c.this.a(response));
                    } catch (AuthFailedException e) {
                        aVar.a(e);
                    } catch (Exception e2) {
                        aVar.a(e2);
                    }
                }
            });
        }

        protected void a(a aVar, final b bVar) {
            try {
                a(a(aVar), new a() { // from class: com.naver.maps.map.NaverMapSdk.c.2
                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(ResponseBody responseBody) throws Exception {
                        c.this.a(bVar, c.a(responseBody));
                    }

                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(Exception exc) {
                        c.this.a(bVar, exc);
                    }

                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(AuthFailedException authFailedException) {
                        c.this.a(bVar, authFailedException);
                    }
                });
            } catch (Exception e) {
                a(bVar, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final b bVar, final String[] strArr) {
            this.b.post(new Runnable() { // from class: com.naver.maps.map.NaverMapSdk.c.3
                @Override // java.lang.Runnable
                public void run() {
                    SharedPreferences.Editor editorEdit = c.this.a.d.edit();
                    c.this.d = new String[c.this.c.length];
                    for (int i = 0; i < c.this.c.length && i < strArr.length; i++) {
                        String str = strArr[i];
                        editorEdit.putString(c.this.c[i], str);
                        c.this.d[i] = str;
                    }
                    editorEdit.apply();
                    bVar.a(c.this.d);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final b bVar, final Exception exc) {
            this.b.post(new Runnable() { // from class: com.naver.maps.map.NaverMapSdk.c.4
                @Override // java.lang.Runnable
                public void run() {
                    com.naver.maps.map.log.c.c("Authorization pending: %s", exc.getMessage());
                    bVar.a(c.this.d, exc);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final b bVar, final AuthFailedException authFailedException) {
            this.b.post(new Runnable() { // from class: com.naver.maps.map.NaverMapSdk.c.5
                @Override // java.lang.Runnable
                public void run() {
                    c.this.a.a(authFailedException, bVar);
                }
            });
        }
    }

    private static class e extends c {
        private final String c;
        private final boolean d;
        private final boolean e;
        private final boolean f;

        private e(NaverMapSdk naverMapSdk, String str, boolean z, boolean z2, boolean z3) {
            super(naverMapSdk, "NaverCloudPlatformClient.StyleUrl", "NaverCloudPlatformClient.LiteStyleUrl");
            this.c = str;
            this.d = z;
            this.e = z2;
            this.f = z3;
        }

        @Override // com.naver.maps.map.NaverMapSdk.c
        protected String a(a aVar) {
            String str = this.e ? "https://naveropenapi.%sapigw.%sntruss.com/maps/v1/mobile/v3/props?X-NCP-APIGW-API-KEY-ID=%s&url=%s" : "https://naveropenapi.%sapigw.%sntruss.com/map-mobile/v3-vector/props?X-NCP-APIGW-API-KEY-ID=%s&url=%s";
            Object[] objArr = new Object[4];
            objArr[0] = this.d ? "beta-" : "";
            objArr[1] = this.f ? "gov-" : "";
            objArr[2] = Uri.encode(this.c);
            objArr[3] = Uri.encode(aVar.a);
            return String.format(str, objArr);
        }

        @Override // com.naver.maps.map.NaverMapSdk.c
        protected ResponseBody a(Response response) throws Exception {
            int iCode = response.code();
            ResponseBody responseBodyBody = response.body();
            if (iCode == 200 && responseBodyBody != null) {
                return responseBodyBody;
            }
            if (iCode == 401) {
                throw new UnauthorizedClientException();
            }
            if (iCode == 429) {
                throw new QuotaExceededException();
            }
            if (iCode >= 400 && iCode < 500) {
                throw new AuthFailedException(Integer.toString(iCode), "Auth failed");
            }
            throw new IOException("Network error: " + iCode);
        }

        @Override // com.naver.maps.map.NaverMapSdk.c
        protected void a(String str, a aVar, final d dVar) {
            try {
                Object[] objArr = new Object[5];
                objArr[0] = this.d ? "beta-" : "";
                objArr[1] = this.f ? "gov-" : "";
                objArr[2] = Uri.encode(this.c);
                objArr[3] = Uri.encode(aVar.a);
                objArr[4] = Uri.encode(str);
                a(String.format("https://naveropenapi.%sapigw.%sntruss.com/maps/v1/mobile/v3/styler/props?X-NCP-APIGW-API-KEY-ID=%s&url=%s&styleMetadataId=%s", objArr), new c.a() { // from class: com.naver.maps.map.NaverMapSdk.e.1
                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(ResponseBody responseBody) throws Exception {
                        e.this.a(dVar, c.b(responseBody));
                    }

                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(Exception exc) {
                        e.this.a(dVar, exc);
                    }

                    @Override // com.naver.maps.map.NaverMapSdk.c.a
                    public void a(AuthFailedException authFailedException) {
                        e.this.a(dVar, authFailedException);
                    }
                });
            } catch (Exception e) {
                a(dVar, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final d dVar, final String str) {
            this.b.post(new Runnable() { // from class: com.naver.maps.map.NaverMapSdk.e.2
                @Override // java.lang.Runnable
                public void run() {
                    dVar.a(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final d dVar, final Exception exc) {
            this.b.post(new Runnable() { // from class: com.naver.maps.map.NaverMapSdk.e.3
                @Override // java.lang.Runnable
                public void run() {
                    dVar.a(exc);
                }
            });
        }
    }

    public static NaverMapSdk getInstance(Context context) throws ClassNotFoundException {
        if (a == null) {
            Context applicationContext = context.getApplicationContext();
            com.naver.maps.map.internal.http.b bVarB = b(applicationContext);
            if (bVarB != null) {
                com.naver.maps.map.internal.http.a.a(bVarB);
            }
            com.naver.maps.map.internal.a.a(applicationContext);
            com.naver.maps.map.internal.net.b.a(applicationContext);
            a = new NaverMapSdk(applicationContext);
        }
        return a;
    }

    private static Bundle a(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    private static com.naver.maps.map.internal.http.b b(Context context) throws ClassNotFoundException {
        String string;
        Bundle bundleA = a(context);
        if (bundleA == null || (string = bundleA.getString("com.naver.maps.map.OK_HTTP_CLIENT_PROVIDER")) == null) {
            return null;
        }
        try {
            Class<?> cls = Class.forName(string);
            if (!com.naver.maps.map.internal.http.b.class.isAssignableFrom(cls)) {
                return null;
            }
            return (com.naver.maps.map.internal.http.b) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e2) {
            com.naver.maps.map.log.c.c("Warning: " + e2.getMessage(), new Object[0]);
            return null;
        }
    }

    private static Client c(Context context) {
        String string;
        Bundle bundleA = a(context);
        if (bundleA == null || (string = bundleA.getString("com.naver.maps.map.CLIENT_ID")) == null) {
            return null;
        }
        String strTrim = string.trim();
        if (strTrim.isEmpty()) {
            return null;
        }
        if (METADATA_VALUE_CLIENT_TYPE_GOV.equalsIgnoreCase(bundleA.getString(METADATA_NAME_CLIENT_TYPE))) {
            return new NaverCloudPlatformGovClient(strTrim);
        }
        return new NaverCloudPlatformClient(strTrim);
    }

    private NaverMapSdk(Context context) {
        this.b = context;
        this.c = new a(context);
        this.d = context.getSharedPreferences("com.naver.maps.map.NaverMapSdk", 0);
    }

    public void flushCache(CacheFlushCallback callback) {
        this.d.edit().clear().apply();
        FileSource.a(this.b).a(callback);
    }

    public Client getClient() {
        d(this.b);
        if (this.e == null) {
            throw new ClientUnspecifiedException();
        }
        return this.e;
    }

    public void setClient(Client client) {
        if (client.equals(this.e)) {
            return;
        }
        this.e = client;
        this.g = client.a(this);
    }

    private void d(Context context) {
        Client clientC;
        if (this.e != null || (clientC = c(context)) == null) {
            return;
        }
        setClient(clientC);
    }

    public OnAuthFailedListener getOnAuthFailedListener() {
        return this.f;
    }

    public void setOnAuthFailedListener(OnAuthFailedListener listener) {
        this.f = listener;
    }

    void a(b bVar) {
        d(this.b);
        if (this.g == null) {
            a(new ClientUnspecifiedException(), bVar);
        } else {
            this.g.a(this.c, bVar);
        }
    }

    void a(String str, d dVar) {
        d(this.b);
        if (this.g == null) {
            dVar.a(new ClientUnspecifiedException());
        } else {
            this.g.a(str, this.c, dVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AuthFailedException authFailedException, b bVar) {
        bVar.a(authFailedException);
        if (this.f == null) {
            com.naver.maps.map.log.c.d("Authorization failed: %s", authFailedException.getMessage());
            Toast.makeText(this.b, "[NaverMapSdk] Authorization failed:\n" + authFailedException.getMessage(), 1).show();
        } else {
            this.f.onAuthFailed(authFailedException);
        }
    }
}
