package com.gmail.mazinva.RSSReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyActivity extends Activity {

    public static final String[] сhannels = {
            "http://lenta.ru/rss",
            "http://stackoverflow.com/feeds/tag/android",
            "http://habrahabr.ru/rss",
            "http://bash.im/rss"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                                                R.layout.url_item,
                                                                сhannels);
        ListView listView = (ListView) findViewById(R.id.channels);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) adapterView.getAdapter().getItem(i);
                ((EditText) findViewById(R.id.editText)).setText(str, TextView.BufferType.EDITABLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ((EditText) findViewById(R.id.editText)).getText().toString();
                Intent intent = new Intent(view.getContext(), TitlesActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}

