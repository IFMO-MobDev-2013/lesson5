package ru.ifmo.ctddev.koval.rss_reader;

import android.os.AsyncTask;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecieveRssFeedTask extends AsyncTask<String, Void, SyndFeed> {
    @Override
    protected SyndFeed doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(httpcon));
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
