package com.java.android.dronov.RSS;

import org.w3c.dom.Element;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 16.10.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */

public class Entry {
    private String title = null;
    private String description = null;
    private String link = null;
    private String publishedDate = null;

    public Entry(String title, String description, String link, String publishedDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.publishedDate = publishedDate;
    }
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.link;
    }

    public String getPublishedDate() {
        return this.publishedDate;
    }

    @Override
    public String toString() {
        return getTitle() + "(" + publishedDate + ")";
    }
}
