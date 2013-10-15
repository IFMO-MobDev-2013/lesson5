package ru.mermakov.projects.MRSSReader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

public class FullNewsActivity extends Activity {
    RSSFeed feed;
    TextView title;
    WebView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullnews);

        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        sv.setVerticalFadingEdgeEnabled(true);
        feed = (RSSFeed) getIntent().getExtras().get("feed");
        int pos = getIntent().getExtras().getInt("pos");
        title = (TextView) findViewById(R.id.title);
        desc = (WebView) findViewById(R.id.desc);
        WebSettings ws = desc.getSettings();
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.getPluginState();
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setJavaScriptEnabled(true);
        ws.setBuiltInZoomControls(true);
        title.setText(feed.getItem(pos).getTitle());
        if (feed.getItem(pos).getLink() != null)
            desc.loadDataWithBaseURL("http://yandex.ru", feed.getItem(pos).getDescription() + "<p><a href=" + feed.getItem(pos).getLink() + ">Link to the news</a></p>", "text/html", "UTF-8", null);
        else
            desc.loadDataWithBaseURL("http://www.yandex.ru/", feed
                    .getItem(pos).getDescription(), "text/html", "UTF-8", null);
    }
}
