package com.polarnick.day05;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.polarnick.rss.Feed;
import com.polarnick.rss.FeedEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 16.09.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class FeedActivity extends Activity {
    private static final String FEED_LAST_UPDATED = "Feed last update: ";
    private static final String FEED_LAST_DOWNLOADED = "Feed downloaded: ";

    private final DateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entries_list_view);
        loadFeed();
    }

    private void loadFeed() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Feed...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final Activity activity = this;

        new FeedDownloadTask() {
            @Override
            public void onSuccess(Feed feed) {
                ((TextView) findViewById(R.id.feedLastChange)).setText(
                        FEED_LAST_UPDATED + dateFormatter.format(feed.getUpdatedDate()));
                ((TextView) findViewById(R.id.feedLastDownloaded)).setText(
                        FEED_LAST_DOWNLOADED + dateFormatter.format(new Date()));

                progressDialog.dismiss();
                feed.sortEntriesByRank();
                ArrayAdapter<FeedEntry> adapter
                        = new FeedEntriesAdapter(activity, feed.getEntries()) {
                    @Override
                    protected void onClick(FeedEntry entry) {
                        Intent intent = new Intent(FeedActivity.this, EntryWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(EntryWebActivity.URL_KEY, entry.getLink());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                };
                ((ListView) findViewById(R.id.entriesList)).setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.dismiss();
                new AlertDialog.Builder(activity)
                        .setMessage(exception.getMessage() + "\nApplication will be closed!")
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        activity.finish();
                                    }
                                }).create().show();
            }
        }.execute();
    }
}
