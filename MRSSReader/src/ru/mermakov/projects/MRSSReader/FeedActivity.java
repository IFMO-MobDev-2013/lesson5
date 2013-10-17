package ru.mermakov.projects.MRSSReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 15.10.13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class FeedActivity extends Activity {
    Application myApp;
    RSSFeed feed;
    RefreshingListView lv;
    CustomListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feeds);

        myApp = getApplication();

        // Get feed form the file
        feed = (RSSFeed) getIntent().getExtras().get("feed");
        if(feed.getItemCount()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(FeedActivity.this);
            builder.setMessage(
                    "Unsupported format")
                    .setTitle("MRSSReader")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        // Initialize the variables:
        lv = (RefreshingListView) findViewById(R.id.listView);

        lv.setVerticalFadingEdgeEnabled(true);

        // Set an Adapter to the ListView
        adapter = new CustomListAdapter(this);
        lv.setAdapter(adapter);

        lv.setOnRefreshListener(new RefreshingListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncLoadXMLFeed task = new AsyncLoadXMLFeed();
                task.execute();
                try {
                    task.get();
                } catch (Exception er) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedActivity.this);
                    builder.setMessage(
                            "Unable to reach server, \nPlease check your connectivity.")
                            .setTitle("MRSSReader")
                            .setCancelable(false)
                            .setPositiveButton("Exit",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            finish();
                                        }
                                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                if(feed.getItemCount()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FeedActivity.this);
                    builder.setMessage(
                            "Unsupported format")
                            .setTitle("MRSSReader")
                            .setPositiveButton("Exit",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            finish();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                lv.setVerticalFadingEdgeEnabled(true);
                adapter = new CustomListAdapter(FeedActivity.this);
                lv.setAdapter(adapter);

                lv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.onRefreshComplete();
                    }
                }, 2000);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int pos = arg2;
                Bundle bundle = new Bundle();
                bundle.putSerializable("feed", feed);
                Intent intent = new Intent(FeedActivity.this,
                        FullNewsActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.imageLoader.clearCache();
        adapter.notifyDataSetChanged();
    }

    class CustomListAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        public ImageLoader imageLoader;

        public CustomListAdapter(FeedActivity activity) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageLoader = new ImageLoader(activity.getApplicationContext());
        }

        @Override
        public int getCount() {
            return feed.getItemCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;
            int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.list_item, null);
            }
            ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
            TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
            TextView tvDate = (TextView) listItem.findViewById(R.id.date);
            imageLoader.DisplayImage(feed.getItem(pos).getImage(), iv);
            tvTitle.setText(feed.getItem(pos).getTitle());
            tvDate.setText(feed.getItem(pos).getDate());
            return listItem;
        }

    }

    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params){
            DOMParser myParser = new DOMParser(FeedActivity.this);
            FileLoader fl=new FileLoader(FeedActivity.this);
            try{
                feed = myParser.parseXml(getString(R.string.link));
            }
            catch(RSSFeedXMLParseException er) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FeedActivity.this);
                builder.setMessage(
                        "Unsupported format")
                        .setTitle("MRSSReader")
                        .setPositiveButton("Exit",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        finish();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            String line;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
