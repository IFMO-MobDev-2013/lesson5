package ru.zulyaev.ifmo.lesson5.feed.rss1;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Никита
 */
@Root(name = "channel", strict = false)
public class Rss1Channel {
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private String link;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
