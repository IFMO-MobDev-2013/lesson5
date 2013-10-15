package ru.zulyaev.ifmo.lesson5.feed.atom;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Feed;

import java.util.List;

/**
 * @author Никита
 */
@Root(name = "feed", strict = false)
public class AtomFeed implements Feed {
    @Element
    private String title;
    @ElementList(inline = true)
    private List<AtomEntry> entries;
    @Element(required = false)
    private String link;

    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLink() {
        return link;
    }

    public List<AtomEntry> getEntries() {
        return entries;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public String getImage() {
        return null;
    }
}
