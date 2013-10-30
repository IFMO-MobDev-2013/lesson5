package com.example.PashaAC.RSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThisArticle extends Activity {
    ArrayList<String> rss = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        String description = getIntent().getStringExtra("Description");
        String title = getIntent().getStringExtra("Title");
        String pubdate = getIntent().getStringExtra("PubDate");

        String htmlText = "<html><body>" + title + "<br /><br /><br />" + description + "<br /><br /><br />" + pubdate + "</body></html>";
        WebView mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadDataWithBaseURL(null,htmlText,"text/html","UTF-8","about:blank");

    }
}