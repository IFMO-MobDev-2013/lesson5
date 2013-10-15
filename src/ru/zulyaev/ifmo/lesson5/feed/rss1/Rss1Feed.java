package ru.zulyaev.ifmo.lesson5.feed.rss1;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Entry;
import ru.zulyaev.ifmo.lesson5.feed.Feed;

import java.util.List;

/**
 * @author Никита
 */
@Root(name = "rdf:RDF", strict = false)
public class Rss1Feed implements Feed {
    @Element
    private Rss1Channel channel;
    @Element(required = false)
    private Rss1Image image;
    @ElementList(inline = true)
    private List<Rss1Item> items;

    @Override
    public String getTitle() {
        return channel.getTitle();
    }

    @Override
    public String getDescription() {
        return channel.getDescription();
    }

    @Override
    public String getLink() {
        return channel.getLink();
    }

    @Override
    public List<? extends Entry> getEntries() {
        return items;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public String getImage() {
        return image.getUrl();
    }
}
