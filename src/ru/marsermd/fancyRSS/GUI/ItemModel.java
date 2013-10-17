package ru.marsermd.fancyRSS.GUI;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import ru.marsermd.fancyRSS.ArticleActivity;

/**
 * Created with IntelliJ IDEA.
 * User: marsermd
 * Date: 17.10.13
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */
public class ItemModel {
    public static Activity mainActivity;
    public TextView titleView;
    public WebView descriptionView;
    private String title;
    private String link;
    private String description;

    ItemModel (String title, String link, String description){
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public void setViews(TextView titleView, WebView descriptionView){
        this.titleView = titleView;
        this.descriptionView = descriptionView;

        titleView.setText(title);
        descriptionView.loadDataWithBaseURL("", description, "text/html", "UTF-8", "");

        setListeners();
    }

    private void setListeners() {
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ArticleActivity.class).putExtra("ArticleUrl", link);
                mainActivity.startActivity(intent);
            }
        });
    }

    public void cancel() {
        if (titleView != null)
            titleView.setOnClickListener(null);
        if (descriptionView != null)
            descriptionView.setOnClickListener(null);

        titleView = null;
        descriptionView = null;
    }

}
