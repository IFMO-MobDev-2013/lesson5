package ru.ifmo.ctddev.koval;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sun.syndication.feed.synd.SyndEntry;

public class MainActivity extends Activity {

    private ListView entries;
    private ArrayAdapter<SyndEntry> entriesAdapter;

    private String FEED_URL = "http://news.yandex.ru/world.rss";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        entries = (ListView) findViewById(R.id.entries);

        entriesAdapter = new SyndEntryAdapter(this);
        entries.setAdapter(entriesAdapter);

        new RecieveRssFeedTask(entriesAdapter).execute(FEED_URL);

        entries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SyndEntry entry = (SyndEntry) parent.getAdapter().getItem(position);
                String url = entry.getLink();

                Intent intent = new Intent(view.getContext(), EntryViewActivity.class);
                intent.putExtra(getResources().getString(R.string.url), url);
                startActivity(intent);
            }
        });
    }

}
