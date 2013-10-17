package com.ifmoctd.korolyov.RSSReader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
public class RSSItemActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_rss_item);
        Bundle extras = getIntent().getExtras();
        String title = extras.getString(MyActivity.ID_EXTRA1);
        String content = extras.getString(MyActivity.ID_EXTRA2);
        WebView webView = (WebView) findViewById(R.id.webView);
        content = "<b>" + title + "</b>" + "<br>" + "<br>" + content;


        webView.loadData(content, "text/html; charset=UTF-8", null);


    }
}