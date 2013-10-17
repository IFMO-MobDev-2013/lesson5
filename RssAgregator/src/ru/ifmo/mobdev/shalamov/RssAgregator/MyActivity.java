package ru.ifmo.mobdev.shalamov.RssAgregator;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    ArrayList<FeedItem> feed;
//    final String DEFAULT_QUERY = "http://bash.im/rss/";
//   final String DEFAULT_QUERY = "http://habrahabr.ru/rss/hubs/";
    final String DEFAULT_QUERY = "http://lenta.ru/rss" ;
//    final String DEFAULT_QUERY = "http://stackoverflow.com/feeds/tag/android";
    String query;
    String answer;
    ListView lv;
    FeedAdapter adapter = null;

    class LoadRssTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // download data from query

//            URL url = null;
//            StringBuilder builder = null;
//            InputStream is = null;
//            BufferedReader reader = null;
//            HttpURLConnection connection = null;
//            try {
//                url = new URL(query);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setConnectTimeout(15000);
//                String s = null;
//                builder = new StringBuilder();
//                is = connection.getInputStream();     /// here
//                reader = new BufferedReader(new InputStreamReader(is, "windows-1251"));
//                while ((s = reader.readLine()) != null) {
//                    builder.append(s);
//                }
//            } catch (Exception exc) {
//                Log.d("fuck!", exc.getMessage());
//            } finally {
//                if (connection != null)
//                    connection.disconnect();
//            }
//
//            if (builder != null)
//                answer = builder.toString();
//            else
//                answer =  null;

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                answer = EntityUtils.toString(httpEntity);
            } catch (Exception exc) {
                Log.d("fuck!", exc.getMessage());
            }

            XMLParser parser = new XMLParser();
            try {
                feed = parser.getFeed(answer);
                if(feed.equals(feed)){}      // to catch nullpointer
                adapter = new FeedAdapter(MyActivity.this, feed);

            }
            catch (Exception exc)
            {
                Log.d("fuck! fuck!! fuck!!!!", exc.getMessage());
               // feed = null;
                //adapter = null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            lv.setAdapter(adapter);
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
                FeedItem fi =  adapter.feed.get(i);
                intent.putExtra("link", fi.link);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        try
        {
            query = intent.getStringExtra("url");
        }
        catch(Exception e)
        {
            query = DEFAULT_QUERY;
        }

        new LoadRssTask().execute(query);
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
//        try
//        {
//            query = data.getStringExtra("from");
//        }
//        catch(Exception exc)
//        {
//            query = DEFAULT_QUERY;
//        }
//        if(query == null || query == "")
//            query = DEFAULT_QUERY;
//    }
}



