package ru.ifmo.smelik.rssReader;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple RSS Reader
 * Created by Nick Smelik on 16.10.13.
 */
public class RssItem {

    private String title;
    private String description;
    private Date pubDate;
    private String link;

    public RssItem(String title, String description, Date pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy  hh:mm");
        return getTitle() + "\n  " + sdf.format(this.getPubDate());
    }


}
