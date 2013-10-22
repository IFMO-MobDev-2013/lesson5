package com.polarnick.rss;

import java.util.Date;

/**
 * Date: 13.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class FeedEntry {

    private String link;
    private String title;
    private Date publishedDate;
    private Date updatedDate;
    private int rank;

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
