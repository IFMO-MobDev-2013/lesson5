package ru.zulyaev.ifmo.lesson5.feed.rss1;

import org.simpleframework.xml.Element;
import ru.zulyaev.ifmo.lesson5.feed.Entry;

/**
 * @author Никита
 */
public class Rss1Item implements Entry {
    @Element
    private String title;
    @Element
    private String link;
    @Element(required = false)
    private String description;


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLink() {
        return link;
    }
}
