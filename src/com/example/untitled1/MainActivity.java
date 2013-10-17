package com.example.untitled1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView feedList = (ListView)findViewById(R.id.listView);
        String link = "http://news.rambler.ru/rss/scitech/";
        RSSFeed feed = new RSSFeed(link);
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        try{
            data = feed.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] keys = {"title", "description", "pubDate"};
        int[] layouts = {R.id.title, R.id.description, R.id.date};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.node, keys, layouts);
        feedList.setAdapter(adapter);
        final ArrayList<Map<String, Object>> fdata = data;
        feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = (String)fdata.get(position).get("link");
                Intent intent = new Intent(MainActivity.this, NewsReader.class);
                intent.putExtra("link", link);
                startActivity(intent);
            }
        });

    }
}
