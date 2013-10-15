package ru.ifmo.mobdev.shalamov;

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

    String[] items;
    ArrayList<FeedItem> feed;
    final String DEFAULT_QUERY = "http://bash.im/rss/";
    String query;
    String answer;
    ListView lv;
    FeedAdapter adapter = null;

    class LoadRssTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // download data from query

            URL url = null;
            StringBuilder builder = null;
            InputStream is = null;
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try {
                url = new URL(query);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                String s = null;
                builder = new StringBuilder();
                is = connection.getInputStream();     /// here
                reader = new BufferedReader(new InputStreamReader(is, "windows-1251"));
                while ((s = reader.readLine()) != null) {
                    builder.append(s);
                }
            } catch (Exception exc) {
                Log.d("fuck!", exc.getMessage());
            } finally {
                if (connection != null)
                    connection.disconnect();
            }

            if (builder != null)
                answer = builder.toString();
            else
                answer =  null;

            XMLParser parser = new XMLParser();
            try {
                feed = parser.getFeed(answer);
                if(feed.equals(feed)){}      // to catch nullpointer
                adapter = new FeedAdapter(MyActivity.this, feed);
                lv.setAdapter(adapter);
            }
            catch (Exception exc)
            {
                feed = null;
                adapter = null;
            }

            if(adapter == null)
            {
                adapter = null;
            }

            return null;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        query = DEFAULT_QUERY;

        Intent intent = new Intent(this, AskActivity.class);

        try
        {
            startActivityForResult(intent, 1);
        }
        catch (Exception exc)
        {
            System.exit(0);
        }

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyActivity.this, ShowActivity.class);
                FeedItem fi =  adapter.feed.get(i);
                intent.putExtra("title", fi.title);
                intent.putExtra("date", fi.date);
                intent.putExtra("description", fi.description);
                intent.putExtra("link", fi.link);
                intent.putExtra("rank", fi.rank);
                startActivity(intent);
            }
        });

        new LoadRssTask().execute(query);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
        try
        {
            query = data.getStringExtra("from");
        }
        catch(Exception exc)
        {
            query = DEFAULT_QUERY;
        }
        if(query == null || query == "")
            query = DEFAULT_QUERY;
    }
}



