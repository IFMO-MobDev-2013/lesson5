package ru.ifmo.mobdev.shalamov.RssAgregator;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 15.10.13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class ShowActivity extends Activity {
    Intent intent;
    WebView webView;

    class LoadOneItemTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            webView.loadUrl(intent.getStringExtra("link"));
           // webView.loadDataWithBaseURL(null, intent.getStringExtra("link") ,"text/html", "UTF-8", null);
            return null;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywebview);
        intent = getIntent();

        webView = (WebView) findViewById(R.id.webView);


        LoadOneItemTask load = new LoadOneItemTask();
        load.execute();
    }
}
