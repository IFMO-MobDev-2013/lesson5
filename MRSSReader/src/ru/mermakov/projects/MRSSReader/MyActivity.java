package ru.mermakov.projects.MRSSReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;


public class MyActivity extends Activity {
    RSSFeed feed;
    FileCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cache=new FileCache(MyActivity.this);


        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null
                || !conMgr.getActiveNetworkInfo().isConnected()
                || !conMgr.getActiveNetworkInfo().isAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "Unable to reach server, \nPlease check your connectivity.")
                    .setTitle("MRSSReader")
                    .setCancelable(false);
            AlertDialog alert = builder.create();
            alert.show();


        } else {
            new AsyncLoadXMLFeed().execute();
        }
    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DOMParser myParser = new DOMParser(MyActivity.this);
            FileLoader fl=new FileLoader(MyActivity.this);
            //fl.saveRss(getString(R.string.link));
            feed = myParser.parseXml(getString(R.string.link));
            String line;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Bundle bundle = new Bundle();
            bundle.putSerializable("feed", feed);
            Intent intent = new Intent(MyActivity.this, FeedActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
