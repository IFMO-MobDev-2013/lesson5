package ru.zulyaev.ifmo.lesson5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import ru.zulyaev.ifmo.lesson5.feed.Entry;
import ru.zulyaev.ifmo.lesson5.feed.Feed;
import ru.zulyaev.ifmo.lesson5.feed.FeedParser;
import ru.zulyaev.ifmo.lesson5.feed.ParseException;

import java.io.IOException;
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

        String url = getIntent().getStringExtra(MainActivity.URL_INDEX);
        new RssTask().execute(url);
    }

    class RssTask extends AsyncTask<String, Void, Feed> {
        private int error;

        @Override
        protected Feed doInBackground(String... params) {
            try {
                return new FeedParser().parse(new URL(params[0]).openStream());
            } catch (ParseException e) {
                error = R.string.error_parse;
            } catch (IOException e) {
                error = R.string.error_net;
            } catch (Exception e) {
                error = R.string.error_unknown;
                Log.wtf("WTF", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Feed feed) {
            if (feed == null) {
                Toast.makeText(FeedActivity.this, error, Toast.LENGTH_SHORT).show();
                finish();
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