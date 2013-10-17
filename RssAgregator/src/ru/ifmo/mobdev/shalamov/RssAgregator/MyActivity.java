package ru.ifmo.mobdev.shalamov.RssAgregator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    ArrayList<FeedItem> feed;
    //  final String DEFAULT_QUERY = "http://bash.im/rss/";
//   final String DEFAULT_QUERY = "http://habrahabr.ru/rss/hubs/";
//    final String DEFAULT_QUERY = "http://lenta.ru/rss" ;
    final String DEFAULT_QUERY = "http://stackoverflow.com/feeds/tag/android";
    String query;
    String answer;
    ListView lv;
    FeedAdapter adapter = null;
    ProgressDialog progressDialog = null;


    class LoadRssTask extends AsyncTask<String, Void, Void> {

        boolean loadFailed = false;
        boolean parseFailed = false;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MyActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(String... params) {


            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                answer = EntityUtils.toString(httpEntity);
            } catch (Exception exc) {
                loadFailed = true;
                return null;
            }

            XMLParser parser = new XMLParser();
            try {
                feed = parser.getFeed(answer);
                adapter = new FeedAdapter(MyActivity.this, feed);

            } catch (Exception exc) {
                parseFailed = true;
                // feed = null;
                //adapter = null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (loadFailed) {
                Toast.makeText(MyActivity.this, "downloading failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
                return;
            }
            if (parseFailed) {
                Toast.makeText(MyActivity.this, "xml parsing failed! \n Relax, it's usual. ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
                return;
            }

            try {
                lv.setAdapter(adapter);
                progressDialog.dismiss();
            }
            catch (Exception exc)
            {
                progressDialog.dismiss();
                Toast.makeText(MyActivity.this, "something goes wrong. check your input", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyActivity.this, ShowActivity.class);
                FeedItem fi = adapter.feed.get(i);
                intent.putExtra("link", fi.link);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        try {
            query = intent.getStringExtra("url");
        } catch (Exception e) {
            query = DEFAULT_QUERY;
        }
        if (query == null || "".equals(query))
            query = DEFAULT_QUERY;

        new LoadRssTask().execute(query);
    }
}



