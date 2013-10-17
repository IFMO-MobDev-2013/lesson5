package ru.ifmo.ctddev.koval;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecieveRssFeedTask extends AsyncTask<String, Void, SyndFeed> {

    private final ArrayAdapter<SyndEntry> entriesAdapter;
    private SyndFeed feed;

    public RecieveRssFeedTask(ArrayAdapter<SyndEntry> entriesAdapter) {
        this.entriesAdapter = entriesAdapter;
    }

    @Override
    protected SyndFeed doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            SyndFeedInput input = new SyndFeedInput();
            return feed =  input.build(new XmlReader(connection));
        } catch (IOException | FeedException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(SyndFeed syndFeed) {
        super.onPostExecute(syndFeed);

        if (feed == null) {
            return;
        }

        for (Object entry : feed.getEntries()) {
            entriesAdapter.add((SyndEntry) entry);
        }

        entriesAdapter.notifyDataSetChanged();
    }
}
