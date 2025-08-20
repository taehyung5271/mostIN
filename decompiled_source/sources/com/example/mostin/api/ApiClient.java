package com.example.mostin.api;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes6.dex */
public class ApiClient {
    private static final String BASE_URL = "https://52.79.240.42:8443/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() throws NoSuchAlgorithmException, KeyManagementException {
        if (retrofit == null) {
            try {
                TrustManager[] trustAllCerts = {new X509TrustManager() { // from class: com.example.mostin.api.ApiClient.1
                    @Override // javax.net.ssl.X509TrustManager
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override // javax.net.ssl.X509TrustManager
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }};
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() { // from class: com.example.mostin.api.ApiClient.2
                    @Override // javax.net.ssl.HostnameVerifier
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                OkHttpClient okHttpClient = builder.build();
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return (ApiService) getClient().create(ApiService.class);
    }
}
