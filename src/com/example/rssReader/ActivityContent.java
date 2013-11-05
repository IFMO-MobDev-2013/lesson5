package com.example.rssReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityContent extends Activity {
    private WebView webView;

    public static final String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //url = "http://stackoverflow.com/questions/8464438/building-android-4-0-on-mac-os-x-lion";
        //String url = "http://lenta.ru/news/2013/10/24/replica/";
        Bundle extras = getIntent().getExtras();
        String urlt = extras.getString(url);
        new viewTask().execute(urlt);

    }

    private class viewTask extends AsyncTask<String, Void, Void> {

        String t;
        @Override
        protected Void doInBackground(String... params) {
            t = params[0];
            return null;
        }

        protected void onPostExecute(Void result) {
            webView = new WebView(ActivityContent.this);
            setContentView(webView);
            //webView.loadUrl(t);
            webView.loadDataWithBaseURL(null, "<html><head/><body>" + t + "</body></html>", "text/html", "UTF-8", null);

        }
    }
}
