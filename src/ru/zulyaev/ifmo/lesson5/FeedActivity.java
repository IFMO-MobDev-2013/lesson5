package ru.zulyaev.ifmo.lesson5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ru.zulyaev.ifmo.lesson5.feed.Entry;
import ru.zulyaev.ifmo.lesson5.feed.Feed;
import ru.zulyaev.ifmo.lesson5.feed.FeedParser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Никита
 */
public class FeedActivity extends Activity {
    private ArrayListAdapter<FeedItem> adapter = new ArrayListAdapter<FeedItem>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feed);

        ListView listView = (ListView) findViewById(R.id.feed);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position).toggle();
            }
        });

        String url = getIntent().getStringExtra(MainActivity.URL_INDEX);
        new RssTask().execute(url);
    }

    class RssTask extends AsyncTask<String, Void, Feed> {
        @Override
        protected Feed doInBackground(String... params) {
            try {
                return new FeedParser().parse(new URL(params[0]).openStream());
            } catch (Exception e) {
                Log.wtf("WTF", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Feed feed) {
            if (feed == null) {
                // Todo: show error!
            } else {
                List<? extends Entry> entries = feed.getEntries();
                List<FeedItem> list = new ArrayList<FeedItem>(entries.size());
                for (Entry entry : entries) {
                    list.add(new FeedItem(entry, getLayoutInflater()));
                }
                adapter.addAll(list);
            }
        }
    }
}