package com.java.android.dronov;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.java.android.dronov.RSS.Entry;
import com.java.android.dronov.RSS.FeedBack;

import java.util.ArrayList;

public class ListRssActivity extends Activity {

    private ArrayAdapter<Entry> adapter;
    private ArrayList<Entry> links = new ArrayList<Entry>();
    private ListView listView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<Entry>(this, R.layout.view_layout, links);
        listView.setAdapter(adapter);
        final Activity activity = this;

        new RSSLoad() {
            @Override
            protected void onPostExecute(FeedBack feed) {
                try {
                    if (feed.getException() != null) {
                        new AlertDialog.Builder(activity)
                                .setMessage("It's not a RSS. Please check your URL")
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                activity.finish();
                                            }
                                        }).create().show();
                    }
                    links.clear();
                    for (int i = 0; i < feed.getArray().size(); i++)
                        links.add(feed.getArray().get(i));
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(link);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Entry entry = links.get(i);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), WebActivity.class);
                intent.putExtra("link", entry.getLink());
                if (entry.getDescription() != null) {
                    intent.putExtra("Content", entry.getTitle() + "<br>" + entry.getDescription());
                }
                startActivity(intent);
            }

        });
    }
}
