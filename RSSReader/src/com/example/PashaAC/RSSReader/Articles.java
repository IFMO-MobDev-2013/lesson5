package com.example.PashaAC.RSSReader;

import java.text.SimpleDateFormat;

public class Articles {
    private String title = "";
    private String description = "";
    private String url = "";
    private String link = "";
    private String pubdate = "";
    public Articles(String title, String description, String url, String link, String pubdate) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.link = link;
        this.pubdate = pubdate;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getPubDate() {
        return pubdate;
    }
    public String getLink() {
        return link;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        //pubdate = pubdate.substring(0, pubdate.length() - 4);
        return title + "...\n" + pubdate.substring(0, pubdate.indexOf("+"));
    }
}
