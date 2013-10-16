package com.example.news;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
public class RssFull extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rssfull);

        Bundle e = getIntent().getExtras();
        String title =e.getString("Title");
        String content = e.getString("Content");
        WebView webView = (WebView)findViewById(R.id.webView);

        webView.loadData(title+"<br><br>"+content, "text/html; charset=UTF-8",null);
    }
}