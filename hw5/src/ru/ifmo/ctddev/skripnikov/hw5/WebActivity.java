package ru.ifmo.ctddev.skripnikov.hw5;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
