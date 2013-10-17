package com.example.RSSReader;

import java.util.Date;

public class Node {

    private String title;
    private String description;
    private Date date;
    private String link;

    Node (String title, String description, Date date, String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}
