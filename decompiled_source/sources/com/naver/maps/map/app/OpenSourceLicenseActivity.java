package com.naver.maps.map.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.naver.maps.map.R;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class OpenSourceLicenseActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.navermap_open_source_license_activity);
        a();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void a() {
        final WeakReference weakReference = new WeakReference(this);
        new Thread(new Runnable() { // from class: com.naver.maps.map.app.OpenSourceLicenseActivity.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                if (((Activity) weakReference.get()) != null) {
                    final String strB = OpenSourceLicenseActivity.this.b();
                    OpenSourceLicenseActivity.this.runOnUiThread(new Runnable() { // from class: com.naver.maps.map.app.OpenSourceLicenseActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (((Activity) weakReference.get()) != null) {
                                OpenSourceLicenseActivity.this.a(strB);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() throws Throwable {
        InputStreamReader inputStreamReader;
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader2 = null;
        try {
            try {
                inputStreamReader = new InputStreamReader(getAssets().open("navermap-sdk/NOTICE"));
            } catch (IOException e) {
            }
        } catch (IOException e2) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            char[] cArr = new char[4096];
            while (true) {
                int i = inputStreamReader.read(cArr, 0, 4096);
                if (i == -1) {
                    break;
                }
                sb.append(cArr, 0, i);
            }
            inputStreamReader.close();
        } catch (IOException e3) {
            inputStreamReader2 = inputStreamReader;
            if (inputStreamReader2 != null) {
                inputStreamReader2.close();
            }
            return sb.toString().trim();
        } catch (Throwable th2) {
            th = th2;
            inputStreamReader2 = inputStreamReader;
            if (inputStreamReader2 != null) {
                try {
                    inputStreamReader2.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
        return sb.toString().trim();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        findViewById(R.id.navermap_progress).setVisibility(8);
        findViewById(R.id.navermap_container).setVisibility(0);
        ((TextView) findViewById(R.id.navermap_open_source_license)).setText(str);
    }
}
