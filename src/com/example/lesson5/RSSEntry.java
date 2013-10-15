package com.example.lesson5;

/**
 * Created with IntelliJ IDEA.
 * User: satori
 * Date: 10/15/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSSEntry {
    public String title;
    public String link;
    public String description;
    public RSSEntry(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;

    }
    @Override
    public String toString() {
        return title;
    }
}
