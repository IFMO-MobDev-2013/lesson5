package ru.marsermd.fancyRSS.AsyncTasks;

import android.os.AsyncTask;
import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssParser;

/**
 * Created with IntelliJ IDEA.
 * User: marsermd
 * Date: 17.10.13
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class AsyncChannelLoader extends AsyncTask<Void, Void, RssFeed> {

    String url;
    public AsyncChannelLoader(String url) {
        this.url = url;
    }

    @Override
    protected RssFeed doInBackground(Void... params) {
        RssFeed feed = null;
        try {
            RssParser rssParser = new RssParser();
             feed = rssParser.load(url);
        }catch(Exception e){
        }
        return feed;

    }
}