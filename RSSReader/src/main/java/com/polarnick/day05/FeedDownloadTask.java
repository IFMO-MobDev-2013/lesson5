package com.polarnick.day05;

import android.os.AsyncTask;
import com.google.common.base.Preconditions;
import com.polarnick.rss.Feed;
import com.polarnick.rss.StackOverflowRSSReader;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Date: 15.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public abstract class FeedDownloadTask extends AsyncTask<Void, Integer, Feed> {
    private Exception exception;

    public abstract void onSuccess(Feed feed);

    public abstract void onFailure(Exception exception);

    @Override
    protected void onPostExecute(Feed feed) {
        if (exception == null) {
            Preconditions.checkNotNull(feed);
            onSuccess(feed);
        } else {
            onFailure(exception);
        }
    }

    @Override
    protected Feed doInBackground(Void... params) {
        Preconditions.checkArgument(params.length == 0);

        exception = null;
        Feed feed = null;
        try {
            StackOverflowRSSReader rssReader = new StackOverflowRSSReader();
            feed = rssReader.readFeed();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            exception = new Exception("RSS parsing exception!", e);
        } catch (ParseException e) {
            e.printStackTrace();
            exception = new Exception("Parsing exception!", e);
        } catch (IOException e) {
            e.printStackTrace();
            exception = new Exception("Internet problems!", e);
        }
        return feed;
    }
}
