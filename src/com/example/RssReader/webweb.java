package com.example.RssReader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;



public class webweb extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webmain);
        WebView web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(getIntent().getStringExtra("link"));
    }


}
