package com.naver.maps.map.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class LegalNoticeActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://ssl.pstatic.net/static/maps/sdk/legal.html");
        setContentView(webView);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
