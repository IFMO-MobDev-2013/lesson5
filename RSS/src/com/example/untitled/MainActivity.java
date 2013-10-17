package com.example.untitled;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        String str = "http://news.rambler.ru/rss/scitech/";
        getXml xml = new getXml(str);
        ArrayList<RssItem> data = new ArrayList<RssItem>();
        try {
            data = xml.execute().get();

        } catch (Exception e) {

        }
        if (data.size() == 0)
            textView.setVisibility(View.VISIBLE);
        ListView lv = (ListView) findViewById(R.id.listView);


        myAdapter adapter = new myAdapter(MainActivity.this,data);
        lv.setAdapter(adapter);

        final ArrayList<RssItem> finalData = data;
        final ArrayList<RssItem> finalData1 = data;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                RssItem rssItem = finalData.get(position);
                Intent intent = new Intent(MainActivity.this, showNews.class);
                intent.putExtra("link", finalData1.get(position).getLink());
                startActivity(intent);
            }
        });
    }
}
