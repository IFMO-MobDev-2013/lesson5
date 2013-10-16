package ru.zulyaev.ifmo.lesson5.feed.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Entry;

/**
 * @author Никита
 */
@Root(name = "item", strict = false)
public class Rss2Item implements Entry {
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String description;
    @Element(required = false)
    private String link;

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
