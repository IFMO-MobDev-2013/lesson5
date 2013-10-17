package com.example.untitled1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Browser;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: Руслан
 * Date: 17.10.13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class NewsReader extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_reader);
        Intent intent = getIntent();
        WebView webView = (WebView)findViewById(R.id.webView);
        String link = intent.getStringExtra("link");
        webView.loadUrl(link);
    }
}