package com.example.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    public static RSSItem selectedRssItem = null;
    String feedUrl = "";
    ListView rssListView = null;
    public static ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
    public static ArrayAdapter<RSSItem> aa = null;
    public static ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        final EditText rssURLTV = (EditText) findViewById(R.id.rssURL);
        Button fetchRss = (Button) findViewById(R.id.fetchRss);
        rssListView = (ListView) findViewById(R.id.rssListView);
        aa = new ArrayAdapter<RSSItem>(this, R.layout.list_item, rssItems);
        rssListView.setAdapter(aa);
        feedUrl = rssURLTV.getText().toString();
        //refreshRssList();

        fetchRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedUrl = rssURLTV.getText().toString();
                aa.notifyDataSetChanged();
                refreshRssList();
            }
        });

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int index,long arg3) {
                selectedRssItem = rssItems.get(index);

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),RssItemDisplayer.class);
                startActivity(intent);
            }
        });
    }

    private void refreshRssList() {
        rssItems.clear();
        RSSItem.getRssItems(feedUrl);
    }
}
