package okhttp3.internal.platform;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import org.conscrypt.Conscrypt;
import org.conscrypt.ConscryptHostnameVerifier;

/* compiled from: ConscryptPlatform.kt */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0002\u0018\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0011\u0010\u000b\u001a\r\u0012\t\u0012\u00070\r¢\u0006\u0002\b\u000e0\fH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0017\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lokhttp3/internal/platform/ConscryptPlatform;", "Lokhttp3/internal/platform/Platform;", "()V", "provider", "Ljava/security/Provider;", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "getSelectedProtocol", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "platformTrustManager", "sslSocketFactory", "Companion", "DisabledHostnameVerifier", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes2.dex */
public final class ConscryptPlatform extends Platform {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final boolean isSupported;
    private final Provider provider;

    public /* synthetic */ ConscryptPlatform(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ConscryptPlatform() {
        Provider providerNewProvider = Conscrypt.newProvider();
        Intrinsics.checkNotNullExpressionValue(providerNewProvider, "newProvider()");
        this.provider = providerNewProvider;
    }

    @Override // okhttp3.internal.platform.Platform
    public SSLContext newSSLContext() throws NoSuchAlgorithmException {
        SSLContext sSLContext = SSLContext.getInstance("TLS", this.provider);
        Intrinsics.checkNotNullExpressionValue(sSLContext, "getInstance(\"TLS\", provider)");
        return sSLContext;
    }

    @Override // okhttp3.internal.platform.Platform
    public X509TrustManager platformTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory $this$platformTrustManager_u24lambda_u2d0 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        $this$platformTrustManager_u24lambda_u2d0.init((KeyStore) null);
        TrustManager[] trustManagers = $this$platformTrustManager_u24lambda_u2d0.getTrustManagers();
        Intrinsics.checkNotNull(trustManagers);
        if (!(trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager))) {
            String string = Arrays.toString(trustManagers);
            Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
            throw new IllegalStateException(Intrinsics.stringPlus("Unexpected default trust managers: ", string).toString());
        }
        TrustManager trustManager = trustManagers[0];
        if (trustManager == null) {
            throw new NullPointerException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
        }
        X509TrustManager x509TrustManager = (X509TrustManager) trustManager;
        Conscrypt.setHostnameVerifier(x509TrustManager, DisabledHostnameVerifier.INSTANCE);
        return x509TrustManager;
    }

    /* compiled from: ConscryptPlatform.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J3\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\fJ\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b¨\u0006\r"}, d2 = {"Lokhttp3/internal/platform/ConscryptPlatform$DisabledHostnameVerifier;", "Lorg/conscrypt/ConscryptHostnameVerifier;", "()V", "verify", "", "certs", "", "Ljava/security/cert/X509Certificate;", "hostname", "", "session", "Ljavax/net/ssl/SSLSession;", "([Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljavax/net/ssl/SSLSession;)Z", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class DisabledHostnameVerifier implements ConscryptHostnameVerifier {
        public static final DisabledHostnameVerifier INSTANCE = new DisabledHostnameVerifier();

        private DisabledHostnameVerifier() {
        }

        public final boolean verify(String hostname, SSLSession session) {
            return true;
        }

        public boolean verify(X509Certificate[] certs, String hostname, SSLSession session) {
            return true;
        }
    }

    @Override // okhttp3.internal.platform.Platform
    public X509TrustManager trustManager(SSLSocketFactory sslSocketFactory) {
        Intrinsics.checkNotNullParameter(sslSocketFactory, "sslSocketFactory");
        return null;
    }

    @Override // okhttp3.internal.platform.Platform
    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        Intrinsics.checkNotNullParameter(protocols, "protocols");
        if (Conscrypt.isConscrypt(sslSocket)) {
            Conscrypt.setUseSessionTickets(sslSocket, true);
            Collection names = Platform.INSTANCE.alpnProtocolNames(protocols);
            Collection $this$toTypedArray$iv = names;
            Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
            if (array != null) {
                Conscrypt.setApplicationProtocols(sslSocket, (String[]) array);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        super.configureTlsExtensions(sslSocket, hostname, protocols);
    }

    @Override // okhttp3.internal.platform.Platform
    public String getSelectedProtocol(SSLSocket sslSocket) {
        Intrinsics.checkNotNullParameter(sslSocket, "sslSocket");
        if (Conscrypt.isConscrypt(sslSocket)) {
            return Conscrypt.getApplicationProtocol(sslSocket);
        }
        return super.getSelectedProtocol(sslSocket);
    }

    @Override // okhttp3.internal.platform.Platform
    public SSLSocketFactory newSslSocketFactory(X509TrustManager trustManager) throws NoSuchAlgorithmException, KeyManagementException {
        Intrinsics.checkNotNullParameter(trustManager, "trustManager");
        SSLContext $this$newSslSocketFactory_u24lambda_u2d2 = newSSLContext();
        $this$newSslSocketFactory_u24lambda_u2d2.init(null, new TrustManager[]{trustManager}, null);
        SSLSocketFactory socketFactory = $this$newSslSocketFactory_u24lambda_u2d2.getSocketFactory();
        Intrinsics.checkNotNullExpressionValue(socketFactory, "newSSLContext().apply {\n…null)\n    }.socketFactory");
        return socketFactory;
    }

    /* compiled from: ConscryptPlatform.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\bJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\r"}, d2 = {"Lokhttp3/internal/platform/ConscryptPlatform$Companion;", "", "()V", "isSupported", "", "()Z", "atLeastVersion", "major", "", "minor", "patch", "buildIfSupported", "Lokhttp3/internal/platform/ConscryptPlatform;", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isSupported() {
            return ConscryptPlatform.isSupported;
        }

        public final ConscryptPlatform buildIfSupported() {
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (isSupported()) {
                return new ConscryptPlatform(defaultConstructorMarker);
            }
            return null;
        }

        public static /* synthetic */ boolean atLeastVersion$default(Companion companion, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 2) != 0) {
                i2 = 0;
            }
            if ((i4 & 4) != 0) {
                i3 = 0;
            }
            return companion.atLeastVersion(i, i2, i3);
        }

        public final boolean atLeastVersion(int major, int minor, int patch) {
            Conscrypt.Version conscryptVersion = Conscrypt.version();
            return conscryptVersion.major() != major ? conscryptVersion.major() > major : conscryptVersion.minor() != minor ? conscryptVersion.minor() > minor : conscryptVersion.patch() >= patch;
        }
    }

    static {
        boolean z = false;
        try {
            Class.forName("org.conscrypt.Conscrypt$Version", false, INSTANCE.getClass().getClassLoader());
            if (Conscrypt.isAvailable()) {
                if (INSTANCE.atLeastVersion(2, 1, 0)) {
                    z = true;
                }
            }
        } catch (ClassNotFoundException e) {
        } catch (NoClassDefFoundError e2) {
        }
        isSupported = z;
    }
}
