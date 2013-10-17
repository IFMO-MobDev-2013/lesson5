package com.ifmoctd.korolyov.RSSReader;


import java.text.SimpleDateFormat;
import java.util.Date;

public class RSSItem {
    private String title;
    private String description;
    private Date pubDate;
    private String link;

    public RSSItem(String title, String description, Date pubDate, String link) {
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

    public Date getDate() {
        return pubDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM - HH:mm:ss z");
        return getTitle() + "  ( " + simpleDateFormat.format(getDate()) + " )";
    }

}
