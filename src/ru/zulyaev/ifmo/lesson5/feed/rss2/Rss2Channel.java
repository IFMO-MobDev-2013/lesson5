package ru.zulyaev.ifmo.lesson5.feed.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Feed;

import java.util.List;

/**
 * @author Никита
 */
@Root(name = "channel", strict = false)
public class Rss2Channel implements Feed {
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private String link;
    @ElementList(inline = true)
    private List<Rss2Item> entries;

    @Element(required = false)
    private String language;
    @Element(required = false)
    private Rss2Image image;

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

    @Override
    public List<Rss2Item> getEntries() {
        return entries;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getImage() {
        return image.getUrl();
    }
}
