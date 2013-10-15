package ru.mermakov.projects.MRSSReader;

import java.io.Serializable;

public class RSSItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title = null;
    private String description = null;
    private String date = null;
    private String image = null;
    private String link=null;

    void setTitle(String title) {
        this.title = title;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setDate(String pubdate) {
        this.date = pubdate;
    }

    void setImage(String image) {
        this.image = image;
    }

    void setLink(String link) {
        this.link=link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }


}
