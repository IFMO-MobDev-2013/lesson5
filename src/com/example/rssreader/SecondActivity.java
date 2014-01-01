package com.example.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;


public class SecondActivity extends Activity {

    WebView wv;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        WebView wv = (WebView) findViewById(R.id.webview);
        String text = getIntent().getStringExtra("text");
        wv.getSettings().setJavaScriptEnabled(true);

        wv.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
    }

    public void onBackPressed() {
        Intent i = new Intent(SecondActivity.this, MyActivity.class);
        startActivity(i);
        finish();
    }
}