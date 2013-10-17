package com.example.PashaAC.RSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        String str = getIntent().getStringExtra("key2");
        rss.add(str.substring(0, str.indexOf("[][][]")));
        rss.add(str.substring(str.indexOf("[][][]") + 6, str.length()));

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, rss);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long number) {
                if (rss.get(index).indexOf("http://") == 0) {
                    WebView mWebView = (WebView) findViewById(R.id.webView);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    mWebView.loadUrl(rss.get(index));
                }

            }
        });
    }
}