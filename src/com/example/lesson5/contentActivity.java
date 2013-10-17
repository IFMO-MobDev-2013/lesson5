package com.example.lesson5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 10/15/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class contentActivity extends Activity {
    private Intent intent;
    private String description, link;
    private WebView webView;
    private String encoding;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        webView = (WebView)findViewById(R.id.webView);
        intent = getIntent();
        description = intent.getStringExtra("Description");
        encoding = intent.getStringExtra("Encoding");
        link = intent.getStringExtra("Link");
        //WebSettings ws = webView.getSettings();
        //ws.setDefaultTextEncodingName("utf-8");
        if (description.length() > 0) {
            //webView.loadData(description, "text/html", null);
            webView.loadDataWithBaseURL(null, description, "text/html", encoding, null);
        }
        else {
            if (link.length() > 0) {
                webView.loadUrl(link);
            }
        }
    }
}