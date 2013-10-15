package ru.zulyaev.ifmo.lesson5.feed.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Feed;

import java.util.List;

/**
 * @author Никита
 */
@Root(name = "rss", strict = false)
public class Rss2Feed implements Feed {
    @Element
    private Rss2Channel channel;

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
    public List<Rss2Item> getEntries() {
        return channel.getEntries();
    }

    @Override
    public String getLanguage() {
        return channel.getLanguage();
    }

    @Override
    public String getImage() {
        return channel.getImage();
    }
}
