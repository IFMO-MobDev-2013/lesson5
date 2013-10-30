package com.example.PashaAC.RSSReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.*;
import android.view.WindowManager;

import java.util.ArrayList;


public class MyActivity extends Activity {
    ArrayList<String> sites = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sites.add("http://news.yandex.ru/computers.rss");
        sites.add("http://news.yandex.ru/world.rss");
        sites.add("http://news.yandex.ru/index.rss");
        sites.add("http://news.yandex.ru/games.rss");
        sites.add("http://news.yandex.ru/movies.rss");
        sites.add("http://news.yandex.ru/internet.rss");
        sites.add("http://news.yandex.ru/science.rss");
        sites.add("http://news.yandex.ru/politics.rss");
        sites.add("http://news.yandex.ru/software.rss");
        sites.add("http://news.yandex.ru/sport.rss");
        sites.add("http://news.yandex.ru/business.rss");

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, sites);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long number) {
                Intent intent = new Intent(MyActivity.this, WorkActivity.class);
                intent.putExtra("key", sites.get(index));
                startActivity(intent);
            }
        });

        Button send = (Button)  findViewById(R.id.ok);
        send.setOnClickListener(new View.OnClickListener() {
            EditText inputText = (EditText) findViewById(R.id.editText);
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, WorkActivity.class);
                intent.putExtra("key", inputText.getText().toString());
                startActivity(intent);
            }
        });
    }
}

