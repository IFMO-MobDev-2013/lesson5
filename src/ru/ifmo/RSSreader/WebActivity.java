package ru.ifmo.RSSreader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 15.10.13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class WebActivity extends Activity {
    WebView myWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new HelloWebViewClient());

        new Browser().execute(url);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class Browser extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... s) {
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(s[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void a) {

        }
    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

    }

}
