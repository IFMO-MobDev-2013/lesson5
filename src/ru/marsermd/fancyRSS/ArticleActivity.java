package ru.marsermd.fancyRSS;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: marsermd
 * Date: 17.10.13
 * Time: 23:54
 * To change this template use File | Settings | File Templates.
 */
public class ArticleActivity extends Activity{
    private WebView article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);

        Bundle extra = getIntent().getExtras();

        article = (WebView) findViewById(R.id.article);
        article.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        article.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        article.loadUrl(extra.getString("ArticleUrl"));
        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    }
}
