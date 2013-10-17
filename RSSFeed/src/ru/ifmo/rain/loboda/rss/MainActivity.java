package ru.ifmo.rain.loboda.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    public static final String[] someChannels = {
            "http://stackoverflow.com/feeds/tag/android",
            "http://habrahabr.ru/rss",
            "http://bash.im/rss",
            "http://news.yandex.ru/ecology.rss",
            "http://news.yandex.ru/sport.rss",
            "http://news.yandex.ru/index.rss",
            "http://www.kremlin.ru/feeds",
            "http://www.5-tv.ru/news/rss/",
            "http://st.kinopoisk.ru/rss/news.rss",
            "http://feeds.feedburner.com/afisha_spb_cinema"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, someChannels);
        ListView listView = (ListView) findViewById(R.id.channels);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String obj = (String) adapterView.getAdapter().getItem(i);
                ((EditText) findViewById(R.id.editText)).setText(obj, TextView.BufferType.EDITABLE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AnnotationsActivity.class);
                String text = ((EditText) findViewById(R.id.editText)).getText().toString();
                intent.putExtra("Url", text);
                startActivity(intent);
            }
        });
    }
}
