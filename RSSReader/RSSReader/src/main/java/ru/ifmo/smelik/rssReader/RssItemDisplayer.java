package ru.ifmo.smelik.rssReader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import ru.ifmo.smelik.rss.R;

/**
 * Simple RSS Reader
 * Created by Nick Smelik on 16.10.13.
 */
public class RssItemDisplayer extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);

        RssItem selectedRssItem = RssActivity.selectedRssItem;

        String title = selectedRssItem.toString();
        String content = "<b>" + title + "</b>" + "<br>" + "<br>" + selectedRssItem.getDescription()
                + "<br>" + "<a href=\"" + selectedRssItem.getLink() + "\">Continue</a>";

        WebView wb = (WebView) findViewById(R.id.webView);

        WebSettings settings = wb.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        wb.loadDataWithBaseURL(null, content, "text/html", "ru_RU", null);
    }
}
