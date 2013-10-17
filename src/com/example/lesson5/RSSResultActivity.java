package com.example.lesson5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Genyaz
 * Date: 12.10.13
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public class RSSResultActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        WebView webView = (WebView) findViewById(R.id.resultWebView);
        Intent intent = getIntent();
        String html = intent.getStringExtra("html");
        webView.loadDataWithBaseURL(null, html ,"text/html", "UTF-8", null);
    }
}