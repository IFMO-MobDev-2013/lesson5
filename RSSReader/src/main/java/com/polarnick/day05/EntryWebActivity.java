package com.polarnick.day05;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Date: 16.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class EntryWebActivity extends Activity {
    public static final String URL_KEY = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(URL_KEY);

        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl(url);
    }
}
