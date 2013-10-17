package com.java.android.dronov;

import android.os.AsyncTask;
import com.java.android.dronov.RSS.FeedBack;
import com.java.android.dronov.RSS.RSSException;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 17.10.13
 * Time: 0:00
 * To change this template use File | Settings | File Templates.
 */
public class RSSLoad extends AsyncTask<String, Void, FeedBack> {

    @Override
    protected FeedBack doInBackground(String... strings) {
        FeedBack result = null;
        RSSReader reader = new RSSReader();
        try {
            result = reader.parse(strings[0]);
        } catch (RSSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
