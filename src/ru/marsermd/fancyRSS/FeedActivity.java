package ru.marsermd.fancyRSS;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssFeed;
import ru.marsermd.fancyRSS.AsyncTasks.AsyncChannelLoader;
import ru.marsermd.fancyRSS.GUI.ItemsAdapter;

public class FeedActivity extends Activity {

    /**
     * Called when the activity is first created.
     */

    RssChannelBean channel = new RssChannelBean();

    EditText urlField;
    TextView title;
    ListView itemsList;

    AsyncChannelLoader channelLoader;
    ItemsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
        urlField = (EditText)findViewById(R.id.url_field);
        title = (TextView)findViewById(R.id.title);
        itemsList = (ListView)findViewById(R.id.items_list);
        adapter = new ItemsAdapter(this, R.id.items_list);
        itemsList.setAdapter(adapter);

        urlField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String url = urlField.getText().toString().replaceAll(" ", "");
                if (!url.startsWith("http://")){
                    url = "http://" + url;
                }

                channelLoader = new AsyncChannelLoader(url){
                    @Override
                    protected void onPreExecute() {
                        title.setText("searching...");
                    }
                    @Override
                    protected void onPostExecute(RssFeed feed) {
                        if (feed == null){
                            title.setText("invalid rss url");
                            return;
                        }
                        RssChannelBean channel = feed.getChannel();
                        title.setText(channel.getTitle());
                        adapter.init(feed.getItems());
                    }
                };
                channelLoader.execute();

                return false;
            }
        });
    }


}
